/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
 *                                                                                                                    * 
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License       * 
 * v. 2.0 which is available at https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0 which is    *
 * available at https://www.apache.org/licenses/LICENSE-2.0.                                                          *
 *                                                                                                                    *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0                                                                     *
 *                                                                                                                    *
 * Contributors:                                                                                                      *
 *    Mike Millson - initial API and implementation                                                                   *
 *********************************************************************************************************************/

package org.github.krashpad;

import static org.github.krashpad.OptionsParser.parseOptions;
import static org.github.krashpad.util.Constants.OPTION_HELP_LONG;
import static org.github.krashpad.util.Constants.OPTION_REPORT_CONSOLE_LONG;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.github.joa.domain.GarbageCollector;
import org.github.krashpad.domain.jdk.ExceptionCounts;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.NativeMemoryTrackingSummary;
import org.github.krashpad.domain.jdk.Stack;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * krashpad main class. A controller that prepares the model (by parsinglog entries) and provides analysis (the report
 * view).
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Main {

    private static Options options;

    /**
     * The maximum number of rejected log lines to track. A throttle to limit memory consumption.
     */
    public static final int REJECT_LIMIT = 1000;

    static {
        // Declare command line options
        options = new Options();
        options.addOption(Constants.OPTION_HELP_SHORT, Constants.OPTION_HELP_LONG, false, "help");
        options.addOption(Constants.OPTION_OUTPUT_SHORT, Constants.OPTION_OUTPUT_LONG, true,
                "output file name (default " + Constants.OUTPUT_FILE_NAME + ")");
    }

    public static void createReport(CommandLine cmd) throws IOException {
        String logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
        File logFile = new File(logFileName);
        String outputFileName;
        if (cmd.hasOption(Constants.OPTION_OUTPUT_LONG)) {
            outputFileName = cmd.getOptionValue(Constants.OPTION_OUTPUT_SHORT);
        } else {
            outputFileName = Constants.OUTPUT_FILE_NAME;
        }
        File reportFile = new File(outputFileName);
        if (logFile.equals(reportFile)) {
            throw new IllegalArgumentException("Fatal error log and report are the same file.");
        }
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(logFile);
        boolean reportConsole = cmd.hasOption(OPTION_REPORT_CONSOLE_LONG);
        createReport(fel, reportConsole, reportFile, logFile.getName());
    }

    /**
     * Create VM Log Analysis report.
     * 
     * @param fel
     *            Fatal error log object.
     * @param reportConsole
     *            Whether print the report to the console or to a file.
     * @param reportFile
     *            Report file.
     * @param logFileName
     *            The fatal error log that was parsed.
     */
    private static void createReport(FatalErrorLog fel, boolean reportConsole, File reportFile, String logFileName) {
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        boolean isMemoryLimitedByContainer = fel.getOsMemoryTotal() > 0 && fel.getMemoryTotal() > 0
                && fel.getOsMemoryTotal() != fel.getMemoryTotal();
        try {
            fileWriter = new FileWriter(reportFile);
            if (reportConsole) {
                printWriter = new PrintWriter(System.out);
            } else {
                printWriter = new PrintWriter(fileWriter);
            }

            printWriter.write(logFileName + Constants.LINE_SEPARATOR);
            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Host:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            // Account for generic OS strings
            if (fel.getOsString() != null && !fel.getOsString().equals("Linux")) {
                printWriter.write("Version: " + fel.getOsString() + Constants.LINE_SEPARATOR);
            } else {
                printWriter.write("Version: " + fel.getOsVersion() + Constants.LINE_SEPARATOR);
            }
            printWriter.write("ARCH: " + fel.getArchOs() + Constants.LINE_SEPARATOR);
            if (fel.getCpusLogical() > Integer.MIN_VALUE) {
                printWriter.write(
                        "CPUs (cpu x cpu cores x hyperthreading): " + fel.getCpusLogical() + Constants.LINE_SEPARATOR);
            }
            if (fel.getOsMemoryTotal() > 0) {
                printWriter.write("Memory: "
                        + JdkUtil.convertSize(fel.getOsMemoryTotal(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            if (fel.getAnonHugePages() >= 0) {
                printWriter.write("Transparent Huge Pages (THP): "
                        + JdkUtil.convertSize(fel.getAnonHugePages(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getOsMemoryTotal() > 0) {
                    printPercentage(printWriter, fel.getAnonHugePages(), fel.getOsMemoryTotal(), null);
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getExplicitHugePagesPoolSize() > 0) {
                printWriter.write("Explicit Huge Pages Pool: "
                        + JdkUtil.convertSize(fel.getExplicitHugePagesPoolSize(), 'B',
                                org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getOsMemoryTotal() > 0) {
                    printPercentage(printWriter, fel.getExplicitHugePagesPoolSize(), fel.getOsMemoryTotal(), null);
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getOsMemoryFree() >= 0) {
                printWriter.write("Memory Free: "
                        + JdkUtil.convertSize(fel.getOsMemoryFree(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getOsMemoryTotal() > 0) {
                    printPercentage(printWriter, fel.getOsMemoryFree(), fel.getOsMemoryTotal(), null);
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getOsMemoryAvailable() >= 0) {
                printWriter.write("Memory Available: "
                        + JdkUtil.convertSize(fel.getOsMemoryAvailable(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getOsMemoryTotal() > 0) {
                    printPercentage(printWriter, fel.getOsMemoryAvailable(), fel.getOsMemoryTotal(), null);
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getOsSwapTotal() >= 0) {
                printWriter.write(
                        "Swap: " + JdkUtil.convertSize(fel.getOsSwapTotal(), 'B', org.github.joa.util.Constants.UNITS)
                                + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
                if (fel.getOsSwapTotal() > 0 && fel.getOsSwapFree() >= 0) {
                    printWriter.write("Swap Free: "
                            + JdkUtil.convertSize(fel.getOsSwapFree(), 'B', org.github.joa.util.Constants.UNITS)
                            + Character.toString(org.github.joa.util.Constants.UNITS));
                    printPercentage(printWriter, fel.getOsSwapFree(), fel.getOsSwapTotal(), null);
                    printWriter.write(Constants.LINE_SEPARATOR);
                }
            }
            if (fel.getHardwareCorrupted() > 0) {
                printWriter.write("HardwareCorrupted: ");
                long hardwareCorrupted = JdkUtil.convertSize(fel.getHardwareCorrupted(), 'B',
                        org.github.joa.util.Constants.UNITS);
                if (hardwareCorrupted == 0) {
                    // Provide rounding clue
                    printWriter.write("~");
                }
                printWriter.write(hardwareCorrupted + Character.toString(org.github.joa.util.Constants.UNITS)
                        + Constants.LINE_SEPARATOR);
            }
            if (isMemoryLimitedByContainer) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Container:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                if (fel.getMemoryTotal() > 0) {
                    printWriter.write("Memory: "
                            + JdkUtil.convertSize(fel.getMemoryTotal(), 'B', org.github.joa.util.Constants.UNITS)
                            + Character.toString(org.github.joa.util.Constants.UNITS));
                    if (fel.getOsMemoryTotal() > 0) {
                        printPercentage(printWriter, fel.getMemoryTotal(), fel.getOsMemoryTotal(), "Host Memory");
                    }
                    printWriter.write(Constants.LINE_SEPARATOR);
                    if (fel.getMemoryFree() >= 0) {
                        printWriter.write("Memory Free: "
                                + JdkUtil.convertSize(fel.getMemoryFree(), 'B', org.github.joa.util.Constants.UNITS)
                                + Character.toString(org.github.joa.util.Constants.UNITS));
                        if (fel.getMemoryTotal() > 0) {
                            printPercentage(printWriter, fel.getMemoryFree(), fel.getMemoryTotal(), null);
                        }
                        printWriter.write(Constants.LINE_SEPARATOR);
                    }
                }
                if (fel.getSwapTotal() >= 0) {
                    printWriter.write(
                            "Swap: " + JdkUtil.convertSize(fel.getSwapTotal(), 'B', org.github.joa.util.Constants.UNITS)
                                    + Character.toString(org.github.joa.util.Constants.UNITS));
                    if (fel.getOsSwapTotal() > 0) {
                        printPercentage(printWriter, fel.getSwapTotal(), fel.getOsSwapTotal(), "Host Swap");
                    }
                    printWriter.write(Constants.LINE_SEPARATOR);
                    if (fel.getSwapTotal() > 0 && fel.getSwapFree() >= 0) {
                        printWriter.write("Swap Free: "
                                + JdkUtil.convertSize(fel.getSwapFree(), 'B', org.github.joa.util.Constants.UNITS)
                                + Character.toString(org.github.joa.util.Constants.UNITS));
                        printPercentage(printWriter, fel.getSwapFree(), fel.getSwapTotal(), null);
                        printWriter.write(Constants.LINE_SEPARATOR);
                    }
                }
            }
            if (fel.hasRlimitAnalysis()) {
                if (fel.getRlimit() != null) {
                    printWriter.write(fel.getRlimit().getLogEntry() + Constants.LINE_SEPARATOR);
                }
                if (fel.getThreadsMaxLimit() > 0) {
                    printWriter.write("threads-max: " + fel.getThreadsMaxLimit() + Constants.LINE_SEPARATOR);
                }
                if (fel.getMaxMapCountLimit() > 0) {
                    printWriter.write("max_map_count: " + fel.getMaxMapCountLimit() + Constants.LINE_SEPARATOR);
                }
                if (fel.getPidMaxLimit() > 0) {
                    printWriter.write("pid_max: " + fel.getPidMaxLimit() + Constants.LINE_SEPARATOR);
                }
            }

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("JVM:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            if (fel.isRhRpmMatch()) {
                if (fel.getRhRpmName() != null) {
                    printWriter.write("RPM: " + fel.getRhRpmName() + Constants.LINE_SEPARATOR);
                } else {
                    printWriter.write("RPM: " + fel.getJdkReleaseString() + Constants.LINE_SEPARATOR);
                }
            } else {
                printWriter.write("Version: " + fel.getJdkReleaseString() + Constants.LINE_SEPARATOR);
            }
            printWriter.write("Vendor: " + fel.getJavaVendor() + Constants.LINE_SEPARATOR);
            if (fel.getUsername() != null && fel.getJvmUser() != null && !fel.getUsername().equals(fel.getJvmUser())) {
                printWriter.write("USERNAME: " + fel.getUsername() + Constants.LINE_SEPARATOR);
                printWriter.write("JVM User: " + fel.getJvmUser() + Constants.LINE_SEPARATOR);
            } else if (fel.getJvmUser() != null) {
                printWriter.write("JVM User: " + fel.getJvmUser() + Constants.LINE_SEPARATOR);
            } else if (fel.getUsername() != null) {
                printWriter.write("JVM User: " + fel.getUsername() + Constants.LINE_SEPARATOR);
            }
            if (fel.getVmState() != null) {
                printWriter.write("VM State: " + fel.getVmState().getState() + Constants.LINE_SEPARATOR);
            }
            if (!fel.getCrashTimeString().equals("")) {
                printWriter.write("Date: " + fel.getCrashTimeString() + Constants.LINE_SEPARATOR);
            }
            if (fel.getElapsedTime() != null) {
                printWriter.write("Run Time: " + fel.getElapsedTime() + Constants.LINE_SEPARATOR);
            }
            List<GarbageCollector> garbageCollectors = fel.getGarbageCollectors();
            if (!garbageCollectors.isEmpty()) {
                printWriter.write("Garbage Collectors: ");
                Iterator<GarbageCollector> iteratorGarbageCollectors = garbageCollectors.iterator();
                boolean punctuate = false;
                while (iteratorGarbageCollectors.hasNext()) {
                    GarbageCollector garbageCollector = iteratorGarbageCollectors.next();
                    if (punctuate) {
                        printWriter.write(", ");
                    }
                    printWriter.write(garbageCollector.toString());
                    punctuate = true;
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            // ZGC collects concurrently, so GC time is not pause time
            if (!fel.getGarbageCollectors().contains(GarbageCollector.ZGC_GENERATIONAL)
                    && !fel.getGarbageCollectors().contains(GarbageCollector.ZGC_NON_GENERATIONAL)
                    && !fel.getGarbageCollections().isEmpty()) {
                BigDecimal maxGcPause = JdkMath.convertMillisToSecs(fel.getGarbageCollectionDurationMax());
                printWriter.write("GC Pause Max: ");
                if (maxGcPause.compareTo(BigDecimal.ZERO) == 0) {
                    // Provide rounding clue
                    printWriter.write("~");
                }
                printWriter.write(maxGcPause.toString());
                printWriter.write(" secs" + Constants.LINE_SEPARATOR);
                printWriter.write("GC Throughput: ");
                if (fel.getGarbageCollectionThroughput() == 100 || fel.getGarbageCollectionThroughput() == 0) {
                    // Provide clue it's rounded to 100
                    printWriter.write("~");
                }
                printWriter.write(fel.getGarbageCollectionThroughput() + "%" + Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryHeapReserved() > 0) {
                printWriter.write("Heap Reserved: "
                        + JdkUtil.convertSize(fel.getJvmMemoryHeapReserved(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryHeapCommitted() > 0) {
                printWriter.write("Heap Committed: "
                        + JdkUtil.convertSize(fel.getJvmMemoryHeapCommitted(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getJvmMemoryHeapReserved() > 0) {
                    printPercentage(printWriter, fel.getJvmMemoryHeapCommitted(), fel.getJvmMemoryHeapReserved(),
                            "Reserved");
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryHeapUsed() >= 0) {
                printWriter.write("Heap Used: "
                        + JdkUtil.convertSize(fel.getJvmMemoryHeapUsed(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getJvmMemoryHeapCommitted() > 0) {
                    printPercentage(printWriter, fel.getJvmMemoryHeapUsed(), fel.getJvmMemoryHeapCommitted(),
                            "Committed");
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getHeapStartingAddress() > 0) {
                printWriter.write("Heap Starting Address: "
                        + JdkUtil.convertSize(fel.getHeapStartingAddress(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            printWriter.write("Compressed oops mode: " + fel.getCompressedOopMode() + Constants.LINE_SEPARATOR);
            if (fel.getJvmMemoryMetaspaceReserved() > 0) {
                printWriter.write("Metaspace Reserved: "
                        + JdkUtil.convertSize(fel.getJvmMemoryMetaspaceReserved(), 'B',
                                org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryMetaspaceCommitted() > 0) {
                printWriter.write("Metaspace Committed: "
                        + JdkUtil.convertSize(fel.getJvmMemoryMetaspaceCommitted(), 'B',
                                org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                printPercentage(printWriter, fel.getJvmMemoryMetaspaceCommitted(), fel.getJvmMemoryMetaspaceReserved(),
                        "Reserved");
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryMetaspaceUsed() >= 0) {
                printWriter.write("Metaspace Used: "
                        + JdkUtil.convertSize(fel.getJvmMemoryMetaspaceUsed(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                printPercentage(printWriter, fel.getJvmMemoryMetaspaceUsed(), fel.getJvmMemoryMetaspaceCommitted(),
                        "Committed");
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getThreadStackSize() > 0) {
                printWriter.write("Thread Stack Size: " + fel.getThreadStackSize() + "K" + Constants.LINE_SEPARATOR);
            }
            printWriter.write("# Java threads: " + fel.getJavaThreadCount() + Constants.LINE_SEPARATOR);
            if (fel.getJvmMemoryThreadStackReserved() > 0) {
                printWriter.write("Thread Stack Reserved: "
                        + JdkUtil.convertSize(fel.getJvmMemoryThreadStackReserved(), 'B',
                                org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryCodeCacheReserved() > 0) {
                printWriter.write("Code Cache Reserved: "
                        + JdkUtil.convertSize(fel.getJvmMemoryCodeCacheReserved(), 'B',
                                org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryDirectMemoryReserved() > 0) {
                printWriter.write("Direct Memory Reserved: "
                        + JdkUtil.convertSize(fel.getJvmMemoryDirectMemoryReserved(), 'B',
                                org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS) + Constants.LINE_SEPARATOR);
            }
            if (fel.isCrashOnStartup()) {
                // Display JVM initial memory if it fails to start
                if (fel.getJvmMemoryTotalCommitted() > 0) {
                    printWriter.write("JVM Memory Committed: >"
                            + JdkUtil.convertSize(fel.getJvmMemoryTotalCommitted(), 'B',
                                    org.github.joa.util.Constants.UNITS)
                            + Character.toString(org.github.joa.util.Constants.UNITS));
                    if (fel.getMemoryTotal() > 0) {
                        String literal = isMemoryLimitedByContainer ? "Container Memory" : "Host Memory";
                        printPercentage(printWriter, fel.getJvmMemoryTotalCommitted(), fel.getMemoryTotal(), literal);
                    }
                    printWriter.write(Constants.LINE_SEPARATOR);
                }
            } else if (fel.getJvmMemoryTotalReserved() > 0) {
                printWriter.write("JVM Memory Reserved: "
                        + JdkUtil.convertSize(fel.getJvmMemoryTotalReserved(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getMemoryTotal() > 0) {
                    String literal = isMemoryLimitedByContainer ? "Container Memory" : "Host Memory";
                    printPercentage(printWriter, fel.getJvmMemoryTotalReserved(), fel.getMemoryTotal(), literal);
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmMemoryTotalUsed() > 0) {
                printWriter.write("JVM Process Size: "
                        + JdkUtil.convertSize(fel.getJvmMemoryTotalUsed(), 'B', org.github.joa.util.Constants.UNITS)
                        + Character.toString(org.github.joa.util.Constants.UNITS));
                if (fel.getMemoryTotal() > 0) {
                    String literal = isMemoryLimitedByContainer ? "Container Memory" : "Host Memory";
                    printPercentage(printWriter, fel.getJvmMemoryTotalUsed(), fel.getMemoryTotal(), literal);
                }
                printWriter.write(Constants.LINE_SEPARATOR);
            }

            if (!fel.getNativeMemoryTrackings().isEmpty()) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Native Memory Tracking:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                if (fel.getNativeMemoryTrackingTotalCommitted() > 0) {
                    printWriter.write("NMT Committed: "
                            + JdkUtil.convertSize(fel.getNativeMemoryTrackingTotalCommitted(), 'K',
                                    org.github.joa.util.Constants.UNITS)
                            + Character.toString(org.github.joa.util.Constants.UNITS));
                    if (fel.getJvmMemoryTotalUsed() > 0) {
                        printPercentage(printWriter,
                                JdkUtil.convertSize(fel.getNativeMemoryTrackingTotalCommitted(), 'K', 'B'),
                                fel.getJvmMemoryTotalUsed(), "JVM Process Size");
                    }
                    printWriter.write(Constants.LINE_SEPARATOR);

                }
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                List<NativeMemoryTrackingSummary> summaries = fel.getNativeMemoryTrackingSummaries();
                Iterator<NativeMemoryTrackingSummary> iterator = summaries.iterator();
                while (iterator.hasNext()) {
                    NativeMemoryTrackingSummary summary = iterator.next();
                    BigDecimal percent = new BigDecimal(summary.getCommitted());
                    percent = percent.divide(new BigDecimal(fel.getNativeMemoryTrackingTotalCommitted()), 2,
                            RoundingMode.HALF_EVEN);
                    percent = percent.movePointRight(2);
                    String committedString = null;
                    if (JdkUtil.convertSize(summary.getCommitted(), 'K', org.github.joa.util.Constants.UNITS) == 0
                            && summary.getCommitted() > 0) {
                        // give rounding hint
                        committedString = "~"
                                + JdkUtil.convertSize(summary.getCommitted(), 'K', org.github.joa.util.Constants.UNITS);
                    } else {
                        committedString = ""
                                + JdkUtil.convertSize(summary.getCommitted(), 'K', org.github.joa.util.Constants.UNITS);
                    }
                    String percentString = null;
                    if (percent.intValue() == 0 && summary.getCommitted() > 0) {
                        // give rounding hint
                        percentString = "~" + percent.toString();
                    } else {
                        percentString = percent.toString();
                    }
                    printWriter.printf("%-28s%12s" + org.github.joa.util.Constants.UNITS + "%6s%%%n",
                            summary.getCategory(), committedString, percentString);
                }
            }

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Application:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            printWriter.write("ID: " + fel.getApplication() + Constants.LINE_SEPARATOR);
            if (fel.getJavaCommand() != null) {
                printWriter.write("Java Command: " + fel.getJavaCommand() + Constants.LINE_SEPARATOR);
            }
            if (fel.getJvmArgs() != null) {
                printWriter.write("JVM Args: " + fel.getJvmArgs() + Constants.LINE_SEPARATOR);
            }

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Threads:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            printWriter.write("Current thread: " + fel.getCurrentThreadName() + Constants.LINE_SEPARATOR);
            if (fel.getCurrentThreadName() != null && fel.getCurrentThreadName().matches("^VMThread.+$")
                    && fel.getVmOperation() != null) {
                printWriter.write(
                        "VM operation: " + fel.getVmOperation().getVmOperationString() + Constants.LINE_SEPARATOR);
            }

            if (!fel.getError().equals("")) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Errors:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                if (!fel.getExceptionCounts().isEmpty()) {
                    Iterator<ExceptionCounts> iteratorExceptionCounts = fel.getExceptionCounts().iterator();
                    while (iteratorExceptionCounts.hasNext()) {
                        ExceptionCounts exceptionCountsEvent = iteratorExceptionCounts.next();
                        if (!exceptionCountsEvent.isHeader()) {
                            printWriter.write(exceptionCountsEvent.getLogEntry() + Constants.LINE_SEPARATOR);
                        }
                    }
                }
                printWriter.write(fel.getError() + Constants.LINE_SEPARATOR);
            }

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Stack:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            List<Stack> stack = fel.getStacks();
            Iterator<Stack> iteratorStackEvents = stack.iterator();
            // Limit stack output for report readability
            int stackLength = 0;
            while (iteratorStackEvents.hasNext() && stackLength < 10) {
                Stack se = iteratorStackEvents.next();
                printWriter.write(se.getLogEntry() + Constants.LINE_SEPARATOR);
                stackLength++;
            }
            if (stack.size() > 10) {
                printWriter.write("..." + Constants.LINE_SEPARATOR);
            }
            printWriter.write("========================================" + Constants.LINE_SEPARATOR);

            // Analysis
            List<String[]> analysis = fel.getAnalysis();
            if (!analysis.isEmpty()) {

                // Determine analysis levels
                List<String[]> error = new ArrayList<String[]>();
                List<String[]> warn = new ArrayList<String[]>();
                List<String[]> info = new ArrayList<String[]>();

                Iterator<String[]> iteratorAnalysis = analysis.iterator();
                while (iteratorAnalysis.hasNext()) {
                    String[] a = iteratorAnalysis.next();
                    String level = a[0].split("\\.")[0];
                    if (level.equals("error")) {
                        error.add(a);
                    } else if (level.equals("warn")) {
                        warn.add(a);
                    } else if (level.equals("info")) {
                        info.add(a);
                    }
                }

                printWriter.write("ANALYSIS:" + Constants.LINE_SEPARATOR);

                iteratorAnalysis = error.iterator();
                boolean printHeader = true;
                // ERROR
                while (iteratorAnalysis.hasNext()) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("error" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    }
                    printHeader = false;
                    String[] a = iteratorAnalysis.next();
                    printWriter.write("*");
                    printWriter.write(a[1]);
                    printWriter.write(Constants.LINE_SEPARATOR);
                }

                // WARN
                iteratorAnalysis = warn.iterator();
                printHeader = true;
                while (iteratorAnalysis.hasNext()) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("warn" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    }
                    printHeader = false;
                    String[] a = iteratorAnalysis.next();
                    printWriter.write("*");
                    printWriter.write(a[1]);
                    printWriter.write(Constants.LINE_SEPARATOR);
                }

                // INFO
                iteratorAnalysis = info.iterator();
                printHeader = true;
                while (iteratorAnalysis.hasNext()) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("info" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    }
                    printHeader = false;
                    String[] a = iteratorAnalysis.next();
                    printWriter.write("*");
                    printWriter.write(a[1]);
                    printWriter.write(Constants.LINE_SEPARATOR);
                }
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            }

            // Unidentified log lines
            List<String> unidentifiedLogLines = fel.getUnidentifiedLogLines();
            if (!unidentifiedLogLines.isEmpty()) {
                printWriter.write(unidentifiedLogLines.size() + " UNIDENTIFIED LOG LINES:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);

                Iterator<String> iterator = unidentifiedLogLines.iterator();
                while (iterator.hasNext()) {
                    String unidentifiedLogLine = iterator.next();
                    printWriter.write(unidentifiedLogLine);
                    printWriter.write(Constants.LINE_SEPARATOR);
                }
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            }
        } catch (

        FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close streams
            if (printWriter != null) {
                try {
                    printWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param args
     *            The argument list includes one or more scope options followed by the name of the fatal error log file
     *            to inspect.
     * @throws IOException
     *             if fatal error log file cannot be read.
     */
    public static void main(String[] args) throws IOException {
        try {
            CommandLine cmd = parseOptions(args);
            if (cmd == null || cmd.hasOption(OPTION_HELP_LONG) || cmd.hasOption(OPTION_HELP_LONG)) {
                usage();
            } else {
                createReport(cmd);
            }
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            usage();
        }
    }

    /**
     * Output percentage block " ([part/whole] of [literal])"
     * 
     * For example: " (2870% Host Memory)"
     * 
     * @param printWriter
     *            The PrintWrite.
     * @param part
     *            Percent fraction numerator.
     * @param whole
     *            Percent fraction denominator.
     * @param literal
     *            The "whole" literal, or null if none.
     */
    private static void printPercentage(PrintWriter printWriter, long part, long whole, String literal) {
        long percentMemory = JdkMath.calcPercent(part, whole);
        printWriter.write(" (");
        // provide rounding indicator
        if ((percentMemory == 0 || percentMemory == 100) && part != whole) {
            printWriter.write("~");
        }
        printWriter.write(percentMemory + "%");
        if (literal != null) {
            printWriter.write(" ");
            printWriter.write(literal);
        }
        printWriter.write(")");
    }

    /**
     * Output usage help.
     */
    private static void usage() {
        // Use the built in formatter class
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("krashpad [OPTION]... [FILE]", options);
    }
}

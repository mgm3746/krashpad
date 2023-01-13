/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.github.joa.domain.GarbageCollector;
import org.github.krashpad.domain.jdk.ExceptionCountsEvent;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.StackEvent;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.Analysis;
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
    ***REMOVED***

    public static void createReport(CommandLine cmd) throws IOException {
        String logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
        File logFile = new File(logFileName);
        String outputFileName;
        if (cmd.hasOption(Constants.OPTION_OUTPUT_LONG)) {
            outputFileName = cmd.getOptionValue(Constants.OPTION_OUTPUT_SHORT);
        ***REMOVED*** else {
            outputFileName = Constants.OUTPUT_FILE_NAME;
        ***REMOVED***
        File reportFile = new File(outputFileName);
        if (logFile.equals(reportFile)) {
            throw new IllegalArgumentException("Fatal error log and report are the same file.");
        ***REMOVED***
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(logFile);
        boolean reportConsole = cmd.hasOption(OPTION_REPORT_CONSOLE_LONG);
        createReport(fel, reportConsole, reportFile, logFile.getName());
    ***REMOVED***

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
        try {
            fileWriter = new FileWriter(reportFile);
            if (reportConsole) {
                printWriter = new PrintWriter(System.out);
            ***REMOVED*** else {
                printWriter = new PrintWriter(fileWriter);
            ***REMOVED***

            printWriter.write(logFileName + Constants.LINE_SEPARATOR);
            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("OS:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            // Account for generic OS strings
            if (fel.getOsString() != null && !fel.getOsString().equals("Linux")) {
                printWriter.write("Version: " + fel.getOsString() + Constants.LINE_SEPARATOR);
            ***REMOVED*** else {
                printWriter.write("Version: " + fel.getOsVersion() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            printWriter.write("ARCH: " + fel.getArch() + Constants.LINE_SEPARATOR);
            if (fel.getCpusLogical() > Integer.MIN_VALUE) {
                printWriter.write(
                        "CPUs (cpu x cpu cores x hyperthreading): " + fel.getCpusLogical() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getOsMemTotal() > 0) {
                printWriter.write("Memory: " + fel.getOsMemTotal() + Character.toString(Constants.PRECISION_REPORTING)
                        + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getOsMemFree() > 0) {
                printWriter
                        .write("Memory Free: " + fel.getOsMemFree() + Character.toString(Constants.PRECISION_REPORTING)
                                + " (" + JdkMath.calcPercent(fel.getOsMemFree(), fel.getOsMemTotal()) + "%)"
                                + Constants.LINE_SEPARATOR);
                if (fel.getOsMemAvailable() >= 0) {
                    printWriter.write("Memory Available: " + fel.getOsMemAvailable()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getOsMemAvailable(), fel.getOsMemTotal()) + "%)"
                            + Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED***
            if (fel.getOsSwap() >= 0) {
                printWriter.write("Swap: " + fel.getOsSwap() + Character.toString(Constants.PRECISION_REPORTING)
                        + Constants.LINE_SEPARATOR);
                if (fel.getOsSwap() > 0) {
                    printWriter.write(
                            "Swap Free: " + fel.getOsSwapFree() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getOsSwapFree(), fel.getOsSwap()) + "%)"
                                    + Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED***
            if (fel.hasAnalysis(Analysis.INFO_CGROUP)) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Container:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                if (fel.getJvmMemoryMax() > 0) {
                    printWriter
                            .write("Memory: " + fel.getJvmMemTotal() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getJvmMemTotal(), fel.getOsMemTotal())
                                    + "% OS Memory)" + Constants.LINE_SEPARATOR);
                    printWriter.write(
                            "Memory Free: " + fel.getJvmMemFree() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getJvmMemFree(), fel.getJvmMemTotal()) + "%)"
                                    + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getOsSwap() > 0) {
                    printWriter.write("Swap: " + fel.getJvmSwap() + Character.toString(Constants.PRECISION_REPORTING)
                            + " (" + JdkMath.calcPercent(fel.getJvmSwap(), fel.getOsSwap()) + "% OS Swap)"
                            + Constants.LINE_SEPARATOR);
                    printWriter.write(
                            "Swap Free: " + fel.getJvmSwapFree() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getJvmSwapFree(), fel.getJvmSwap()) + "%)"
                                    + Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED***
            if ((fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT) || fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT_STARTUP)
                    || fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT_OOPS)
                    || fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT_OOPS_STARTUP)
                    || fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_LIMIT)
                    || fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP))) {
                if (fel.getRlimitEvent() != null) {
                    printWriter.write(fel.getRlimitEvent().getLogEntry() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getThreadsMax() > 0) {
                    printWriter.write("threads-max: " + fel.getThreadsMax() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getMaxMapCount() > 0) {
                    printWriter.write("max_map_count: " + fel.getMaxMapCount() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getPidMax() > 0) {
                    printWriter.write("pid_max: " + fel.getPidMax() + Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED***

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("JVM:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            if (fel.isRhRpmInstall()) {
                printWriter.write("RPM: " + fel.getRpmDirectory() + Constants.LINE_SEPARATOR);
            ***REMOVED*** else {
                printWriter.write("Version: " + fel.getJdkReleaseString() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            printWriter.write("Vendor: " + fel.getJavaVendor() + Constants.LINE_SEPARATOR);
            if (fel.getUsername() != null && fel.getJvmUser() != null && !fel.getUsername().equals(fel.getJvmUser())) {
                printWriter.write("USERNAME: " + fel.getUsername() + Constants.LINE_SEPARATOR);
                printWriter.write("JVM User: " + fel.getJvmUser() + Constants.LINE_SEPARATOR);
            ***REMOVED*** else if (fel.getJvmUser() != null) {
                printWriter.write("JVM User: " + fel.getJvmUser() + Constants.LINE_SEPARATOR);
            ***REMOVED*** else if (fel.getUsername() != null) {
                printWriter.write("JVM User: " + fel.getUsername() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getVmStateEvent() != null) {
                printWriter.write("VM State: " + fel.getVmState() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (!fel.getCrashTimeString().equals("")) {
                printWriter.write("Crash Date: " + fel.getCrashTimeString() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getElapsedTime() != null) {
                printWriter.write("Run Time: " + fel.getElapsedTime() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            List<GarbageCollector> garbageCollectors = fel.getGarbageCollectors();
            if (!garbageCollectors.isEmpty()) {
                printWriter.write("Garbage Collectors: ");
                Iterator<GarbageCollector> iteratorGarbageCollectors = garbageCollectors.iterator();
                boolean punctuate = false;
                while (iteratorGarbageCollectors.hasNext()) {
                    GarbageCollector garbageCollector = iteratorGarbageCollectors.next();
                    if (punctuate) {
                        printWriter.write(", ");
                    ***REMOVED***
                    printWriter.write(garbageCollector.toString());
                    punctuate = true;
                ***REMOVED***
                printWriter.write(Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getElapsedTime() != null && fel.getElapsedTime().matches("0d 0h 0m 0s")) {
                // Display JVM initial memory if it fails to start
                if (fel.getHeapMaxSize() > 0) {
                    printWriter.write("Heap Initial: " + fel.getHeapInitialSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED*** else {
                if (fel.getHeapMaxSize() > 0) {
                    printWriter.write("Heap Max: " + fel.getHeapMaxSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                ***REMOVED***

            ***REMOVED***
            if (fel.getHeapAllocation() > 0) {
                printWriter.write("Heap Allocation: " + fel.getHeapAllocation()
                        + Character.toString(Constants.PRECISION_REPORTING) + " ("
                        + JdkMath.calcPercent(fel.getHeapAllocation(), fel.getHeapMaxSize()) + "% Heap Max)"
                        + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getHeapUsed() >= 0) {
                printWriter
                        .write("Heap Used: " + fel.getHeapUsed() + Character.toString(Constants.PRECISION_REPORTING));
                if (fel.getHeapAllocation() > 0) {
                    printWriter.write(" (" + JdkMath.calcPercent(fel.getHeapUsed(), fel.getHeapAllocation())
                            + "% Heap Allocation)");
                ***REMOVED***
                printWriter.write(Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getHeapStartingAddress() > 0) {
                printWriter.write("Heap Starting Address: " + fel.getHeapStartingAddress()
                        + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
            ***REMOVED***
            printWriter.write("Compressed oops mode: " + fel.getCompressedOopMode() + Constants.LINE_SEPARATOR);
            if (fel.getMetaspaceMaxSize() > 0) {
                printWriter.write("Metaspace Max: " + fel.getMetaspaceMaxSize()
                        + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getMetaspaceAllocation() > 0) {
                printWriter.write("Metaspace Allocation: " + fel.getMetaspaceAllocation()
                        + Character.toString(Constants.PRECISION_REPORTING) + " ("
                        + JdkMath.calcPercent(fel.getMetaspaceAllocation(), fel.getMetaspaceMaxSize())
                        + "% Metaspace Max)" + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getMetaspaceUsed() >= 0) {
                printWriter.write(
                        "Metaspace Used: " + fel.getMetaspaceUsed() + Character.toString(Constants.PRECISION_REPORTING)
                                + " (" + JdkMath.calcPercent(fel.getMetaspaceUsed(), fel.getMetaspaceAllocation())
                                + "% Metaspace Allocation)" + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getThreadStackSize() > 0) {
                printWriter.write("Thread Stack Size: " + fel.getThreadStackSize() + "K" + Constants.LINE_SEPARATOR);
            ***REMOVED***
            printWriter.write("***REMOVED*** Java threads: " + fel.getJavaThreadCount() + Constants.LINE_SEPARATOR);
            if (fel.getThreadStackMemory() > 0) {
                printWriter.write("Thread Stack Memory: " + fel.getThreadStackMemory()
                        + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getCodeCacheSize() > 0) {
                printWriter.write("Code Cache Max: " + fel.getCodeCacheSize()
                        + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getDirectMemoryMaxSize() > 0) {
                printWriter.write("Direct Memory Max: " + fel.getDirectMemoryMaxSize()
                        + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.isCrashOnStartup()) {
                // Display JVM initial memory if it fails to start
                if (fel.getJvmMemoryInitial() > 0) {
                    long percentMemory;
                    if (fel.hasAnalysis(Analysis.INFO_CGROUP)) {
                        percentMemory = JdkMath.calcPercent(fel.getJvmMemoryInitial(), fel.getJvmMemTotal());
                    ***REMOVED*** else {
                        percentMemory = JdkMath.calcPercent(fel.getJvmMemoryInitial(), fel.getOsMemTotal());
                    ***REMOVED***
                    printWriter.write("JVM Memory Initial: >" + fel.getJvmMemoryInitial()
                            + Character.toString(Constants.PRECISION_REPORTING));
                    if (fel.getOsMemTotal() > 0) {
                        printWriter.write(" (");
                        // provide rounding indicator
                        if (fel.hasAnalysis(Analysis.INFO_CGROUP)) {
                            if (percentMemory == 0
                                    || (percentMemory == 100 && fel.getJvmMemoryInitial() != fel.getJvmMemTotal())) {
                                printWriter.write("~");
                            ***REMOVED***
                        ***REMOVED*** else {
                            if (percentMemory == 0
                                    || (percentMemory == 100 && fel.getJvmMemoryInitial() != fel.getOsMemTotal())) {
                                printWriter.write("~");
                            ***REMOVED***
                        ***REMOVED***
                        printWriter.write(percentMemory + "% ");
                        if (fel.hasAnalysis(Analysis.INFO_CGROUP)) {
                            printWriter.write("Container Memory");
                        ***REMOVED*** else {
                            printWriter.write("OS Memory");
                        ***REMOVED***
                        if (fel.getOsMemAvailable() >= 0) {
                            // Memory Available n/a RHEL6
                            long percentMemoryAvailable = JdkMath.calcPercent(fel.getJvmMemoryInitial(),
                                    fel.getOsMemAvailable());
                            printWriter.write(", ");
                            // provide rounding indicator
                            if (percentMemoryAvailable == 0 || (percentMemoryAvailable == 100
                                    && fel.getJvmMemoryMax() != fel.getOsMemAvailable())) {
                                printWriter.write("~");
                            ***REMOVED***
                            printWriter.write(percentMemoryAvailable + "% OS Memory Available");
                        ***REMOVED***
                        printWriter.write(")");
                    ***REMOVED***
                    printWriter.write(Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED*** else if (fel.getJvmMemoryMax() > 0) {
                long percentMemory = Long.MIN_VALUE;
                if (fel.hasAnalysis(Analysis.INFO_CGROUP) && fel.getJvmMemTotal() >= 0) {
                    percentMemory = JdkMath.calcPercent(fel.getJvmMemoryMax(), fel.getJvmMemTotal());

                ***REMOVED*** else if (fel.getOsMemTotal() >= 0) {
                    percentMemory = JdkMath.calcPercent(fel.getJvmMemoryMax(), fel.getOsMemTotal());

                ***REMOVED***
                printWriter.write("JVM Memory Max: >" + fel.getJvmMemoryMax()
                        + Character.toString(Constants.PRECISION_REPORTING));
                if (fel.getOsMemTotal() > 0) {
                    printWriter.write(" (");
                    // provide rounding indicator
                    if (fel.hasAnalysis(Analysis.INFO_CGROUP)) {
                        if (percentMemory == 0
                                || (percentMemory == 100 && fel.getJvmMemoryMax() != fel.getJvmMemTotal())) {
                            printWriter.write("~");
                        ***REMOVED***
                    ***REMOVED*** else {
                        if (percentMemory == 0
                                || (percentMemory == 100 && fel.getJvmMemoryMax() != fel.getOsMemTotal())) {
                            printWriter.write("~");
                        ***REMOVED***
                    ***REMOVED***
                    printWriter.write(percentMemory + "% ");
                    if (fel.hasAnalysis(Analysis.INFO_CGROUP)) {
                        printWriter.write("Container Memory");
                    ***REMOVED*** else {
                        printWriter.write("OS Memory");
                    ***REMOVED***
                    printWriter.write(")");
                ***REMOVED***
                printWriter.write(Constants.LINE_SEPARATOR);
            ***REMOVED***

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Application:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            printWriter.write("ID: " + fel.getApplication() + Constants.LINE_SEPARATOR);
            if (fel.getJavaCommand() != null) {
                printWriter.write("Java Command: " + fel.getJavaCommand() + Constants.LINE_SEPARATOR);
            ***REMOVED***
            if (fel.getJvmArgs() != null) {
                printWriter.write("JVM Args: " + fel.getJvmArgs() + Constants.LINE_SEPARATOR);
            ***REMOVED***

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Threads:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            printWriter.write("Current thread: " + fel.getCurrentThread() + Constants.LINE_SEPARATOR);
            if (fel.getCurrentThread() != null && fel.getCurrentThread().matches("^VMThread.+$")
                    && fel.getVmOperationEvent() != null) {
                printWriter.write(
                        "VM operation: " + fel.getVmOperationEvent().getVmOperationString() + Constants.LINE_SEPARATOR);
            ***REMOVED***

            if (!fel.getError().equals("")) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Errors:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                if (!fel.getExceptionCountsEvents().isEmpty()) {
                    Iterator<ExceptionCountsEvent> iteratorExceptionCounts = fel.getExceptionCountsEvents().iterator();
                    while (iteratorExceptionCounts.hasNext()) {
                        ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                        if (!exceptionCountsEvent.isHeader()) {
                            printWriter.write(exceptionCountsEvent.getLogEntry() + Constants.LINE_SEPARATOR);
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                printWriter.write(fel.getError() + Constants.LINE_SEPARATOR);
            ***REMOVED***

            printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            printWriter.write("Stack:" + Constants.LINE_SEPARATOR);
            printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
            List<StackEvent> stack = fel.getStackEvents();
            Iterator<StackEvent> iteratorStackEvents = stack.iterator();
            // Limit stack output for report readability
            int stackLength = 0;
            while (iteratorStackEvents.hasNext() && stackLength < 10) {
                StackEvent se = iteratorStackEvents.next();
                printWriter.write(se.getLogEntry() + Constants.LINE_SEPARATOR);
                stackLength++;
            ***REMOVED***
            if (stack.size() > 10) {
                printWriter.write("..." + Constants.LINE_SEPARATOR);
            ***REMOVED***
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
                    ***REMOVED*** else if (level.equals("warn")) {
                        warn.add(a);
                    ***REMOVED*** else if (level.equals("info")) {
                        info.add(a);
                    ***REMOVED***
                ***REMOVED***

                printWriter.write("ANALYSIS:" + Constants.LINE_SEPARATOR);

                iteratorAnalysis = error.iterator();
                boolean printHeader = true;
                // ERROR
                while (iteratorAnalysis.hasNext()) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("error" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    printHeader = false;
                    String[] a = iteratorAnalysis.next();
                    printWriter.write("*");
                    printWriter.write(a[1]);
                    if (a[0].equals(Analysis.ERROR_CRASH_ON_OOME_HEAP.getKey())
                            && JdkUtil.isOptionEnabled(fel.getJvmOptions().getHeapDumpOnOutOfMemoryError())
                            && fel.getJvmOptions().getHeapDumpPath() != null) {
                        printWriter.write(" Check the following location for a heap dump: ");
                        printWriter.write(fel.getJvmOptions().getHeapDumpPath());
                        printWriter.write(".");
                    ***REMOVED***
                    if (a[0].equals(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN.getKey())) {
                        printWriter.write(fel.getNativeLibraryInCrash());
                        printWriter.write(".");
                    ***REMOVED***
                    printWriter.write(Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getJvmOptions() != null && fel.getJvmOptions().getDuplicates() != null) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("error" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    printHeader = false;
                    printWriter.write("*Duplicate jvm options: ");
                    printWriter.write(fel.getJvmOptions().getDuplicates());
                    printWriter.write(".");
                    printWriter.write(Constants.LINE_SEPARATOR);
                ***REMOVED***

                // WARN
                iteratorAnalysis = warn.iterator();
                printHeader = true;
                while (iteratorAnalysis.hasNext()) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("warn" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    printHeader = false;
                    String[] a = iteratorAnalysis.next();
                    printWriter.write("*");
                    printWriter.write(a[1]);
                    if (a[0].equals(Analysis.WARN_JDK_NOT_LATEST.getKey())) {
                        printWriter.write(JdkUtil.getLatestJdkReleaseString(fel));
                        // Add latest release info
                        int releaseDayDiff = ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel),
                                JdkUtil.getLatestJdkReleaseDate(fel));
                        int releaseNumberDiff = JdkUtil.getLatestJdkReleaseNumber(fel)
                                - JdkUtil.getJdkReleaseNumber(fel);
                        if (releaseDayDiff > 0 && releaseNumberDiff > 0) {
                            printWriter.write(" (newer by ");
                            printWriter.write("" + releaseNumberDiff);
                            printWriter.write(" version");
                            if (releaseNumberDiff > 1) {
                                printWriter.write("s");
                            ***REMOVED***
                            printWriter.write(" and ");
                            printWriter.write("" + releaseDayDiff);
                            printWriter.write(" day");
                            if (releaseDayDiff > 1) {
                                printWriter.write("s");
                            ***REMOVED***
                            printWriter.write(")");
                        ***REMOVED***
                        printWriter.write(".");
                    ***REMOVED***
                    printWriter.write(Constants.LINE_SEPARATOR);
                ***REMOVED***

                // INFO
                iteratorAnalysis = info.iterator();
                printHeader = true;
                while (iteratorAnalysis.hasNext()) {
                    if (printHeader) {
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                        printWriter.write("info" + Constants.LINE_SEPARATOR);
                        printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    printHeader = false;
                    String[] a = iteratorAnalysis.next();
                    printWriter.write("*");
                    printWriter.write(a[1]);
                    if (a[0].equals(org.github.joa.util.Analysis.INFO_OPTS_UNDEFINED.getKey())) {
                        Iterator<String> iterator = fel.getJvmOptions().getUndefined().iterator();
                        while (iterator.hasNext()) {
                            String option = iterator.next();
                            printWriter.write(" ");
                            printWriter.write(option);
                        ***REMOVED***
                        printWriter.write(". Please submit an issue so we can investigate: "
                                + "https://github.com/mgm3746/krashpad/issues. "
                                + "If attaching a fatal error log, be sure to review it and remove any sensitive "
                                + "information.");
                    ***REMOVED*** else if (a[0].equals(org.github.joa.util.Analysis.INFO_INSTRUMENTATION.getKey())) {
                        Iterator<String> iterator = fel.getJvmOptions().getJavaagent().iterator();
                        while (iterator.hasNext()) {
                            String option = iterator.next();
                            printWriter.write(" ");
                            printWriter.write(option);
                        ***REMOVED***
                        printWriter.write(".");
                    ***REMOVED*** else if (a[0].equals(org.github.joa.util.Analysis.INFO_NATIVE_AGENT.getKey())) {
                        if (!fel.getJvmOptions().getAgentlib().isEmpty()) {
                            Iterator<String> iterator = fel.getJvmOptions().getAgentlib().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            ***REMOVED***
                        ***REMOVED***
                        if (!fel.getJvmOptions().getAgentpath().isEmpty()) {
                            Iterator<String> iterator = fel.getJvmOptions().getAgentpath().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            ***REMOVED***
                        ***REMOVED***
                        printWriter.write(".");
                    ***REMOVED*** else if (a[0].equals(Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN.getKey())) {
                        Iterator<String> iterator = fel.getNativeLibrariesUnknown().iterator();
                        boolean punctuate = false;
                        while (iterator.hasNext()) {
                            String library = iterator.next();
                            if (punctuate) {
                                printWriter.write(", ");
                            ***REMOVED***
                            printWriter.write(library);
                            punctuate = true;
                        ***REMOVED***
                        printWriter.write(".");
                    ***REMOVED***
                    printWriter.write(Constants.LINE_SEPARATOR);
                ***REMOVED***
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            ***REMOVED***

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
                ***REMOVED***
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
            ***REMOVED***
            // ***REMOVED***
        ***REMOVED*** catch (FileNotFoundException e) {
            e.printStackTrace();
        ***REMOVED*** catch (IOException e) {
            e.printStackTrace();
        ***REMOVED*** finally {
            // Close streams
            if (printWriter != null) {
                try {
                    printWriter.close();
                ***REMOVED*** catch (Exception e) {
                    e.printStackTrace();
                ***REMOVED***
            ***REMOVED***
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                ***REMOVED*** catch (IOException e) {
                    e.printStackTrace();
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

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
            ***REMOVED*** else {
                createReport(cmd);
            ***REMOVED***
        ***REMOVED*** catch (ParseException pe) {
            System.out.println(pe.getMessage());
            usage();
        ***REMOVED***
    ***REMOVED***

    /**
     * Output usage help.
     */
    private static void usage() {
        // Use the built in formatter class
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("krashpad [OPTION]... [FILE]", options);
    ***REMOVED***
***REMOVED***

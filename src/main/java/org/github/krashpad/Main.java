/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.github.krashpad.domain.jdk.ExceptionCountsEvent;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.StackEvent;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;
import org.json.JSONObject;

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

    /**
     * The maximum number of rejected log lines to track. A throttle to limit memory consumption.
     */
    public static final int REJECT_LIMIT = 1000;

    private static Options options;

    static {
        // Declare command line options
        options = new Options();
        options.addOption(Constants.OPTION_HELP_SHORT, Constants.OPTION_HELP_LONG, false, "help");
        options.addOption(Constants.OPTION_VERSION_SHORT, Constants.OPTION_VERSION_LONG, false, "version");
        options.addOption(Constants.OPTION_LATEST_VERSION_SHORT, Constants.OPTION_LATEST_VERSION_LONG, false,
                "latest version");
        options.addOption(Constants.OPTION_OUTPUT_SHORT, Constants.OPTION_OUTPUT_LONG, true,
                "output file name (default " + Constants.OUTPUT_FILE_NAME + ")");
    }

    /**
     * @param args
     *            The argument list includes one or more scope options followed by the name of the vm log file to
     *            inspect.
     */
    public static void main(String[] args) {

        CommandLine cmd = null;

        try {
            cmd = parseOptions(args);
        } catch (ParseException pe) {
            System.out.println(pe.getMessage());
            usage(options);
        }

        if (cmd != null) {
            if (cmd.hasOption(Constants.OPTION_HELP_LONG)) {
                usage(options);
            } else {

                String logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
                File logFile = new File(logFileName);
                Manager manager = new Manager();
                FatalErrorLog fel = manager.parse(logFile);

                String outputFileName;
                if (cmd.hasOption(Constants.OPTION_OUTPUT_LONG)) {
                    outputFileName = cmd.getOptionValue(Constants.OPTION_OUTPUT_SHORT);
                } else {
                    outputFileName = Constants.OUTPUT_FILE_NAME;
                }
                boolean version = cmd.hasOption(Constants.OPTION_VERSION_LONG);
                boolean latestVersion = cmd.hasOption(Constants.OPTION_LATEST_VERSION_LONG);

                createReport(fel, outputFileName, version, latestVersion, logFile.getName());
            }
        }
    }

    /**
     * Parse command line options.
     * 
     * @return
     */
    private static final CommandLine parseOptions(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        CommandLine cmd = null;
        // Allow user to just specify help or version.
        if (args.length == 1 && (args[0].equals("-" + Constants.OPTION_HELP_SHORT)
                || args[0].equals("--" + Constants.OPTION_HELP_LONG))) {
            usage(options);
        } else if (args.length == 1 && (args[0].equals("-" + Constants.OPTION_VERSION_SHORT)
                || args[0].equals("--" + Constants.OPTION_VERSION_LONG))) {
            System.out.println("Running krashpad version: " + getVersion());
        } else if (args.length == 1 && (args[0].equals("-" + Constants.OPTION_LATEST_VERSION_SHORT)
                || args[0].equals("--" + Constants.OPTION_LATEST_VERSION_LONG))) {
            System.out.println("Latest krashpad version/tag: " + getLatestVersion());
        } else if (args.length == 2 && (((args[0].equals("-" + Constants.OPTION_VERSION_SHORT)
                || args[0].equals("--" + Constants.OPTION_VERSION_LONG))
                && (args[1].equals("-" + Constants.OPTION_LATEST_VERSION_SHORT)
                        || args[1].equals("--" + Constants.OPTION_LATEST_VERSION_LONG)))
                || ((args[1].equals("-" + Constants.OPTION_VERSION_SHORT)
                        || args[1].equals("--" + Constants.OPTION_VERSION_LONG))
                        && (args[0].equals("-" + Constants.OPTION_LATEST_VERSION_SHORT)
                                || args[0].equals("--" + Constants.OPTION_LATEST_VERSION_LONG))))) {
            System.out.println("Running krashpad version: " + getVersion());
            System.out.println("Latest krashpad version/tag: " + getLatestVersion());
        } else {
            cmd = parser.parse(options, args);
            validateOptions(cmd);
        }
        return cmd;
    }

    /**
     * Output usage help.
     * 
     * @param options
     */
    private static void usage(Options options) {
        // Use the built in formatter class
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("krashpad [OPTION]... [FILE]", options);
    }

    /**
     * Validate command line options.
     * 
     * @param cmd
     *            The command line options.
     * 
     * @throws ParseException
     *             Command line options not valid.
     */
    public static void validateOptions(CommandLine cmd) throws ParseException {
        // Ensure log file specified.
        if (cmd.getArgList().isEmpty()) {
            throw new ParseException("Missing log file");
        }
        String logFileName = null;
        if (!cmd.getArgList().isEmpty()) {
            logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
        }
        // Ensure vm log file exists.
        if (logFileName == null) {
            throw new ParseException("Missing log file not");
        }
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            throw new ParseException("Invalid log file: '" + logFileName + "'");
        }
        // threshold
        if (cmd.hasOption(Constants.OPTION_THRESHOLD_LONG)) {
            String thresholdRegEx = "^\\d{1,3}$";
            String thresholdOptionValue = cmd.getOptionValue(Constants.OPTION_THRESHOLD_SHORT);
            Pattern pattern = Pattern.compile(thresholdRegEx);
            Matcher matcher = pattern.matcher(thresholdOptionValue);
            if (!matcher.find()) {
                throw new ParseException("Invalid threshold: '" + thresholdOptionValue + "'");
            }
        }
    }

    /**
     * @return version string.
     */
    private static String getVersion() {
        ResourceBundle rb = ResourceBundle.getBundle("META-INF/maven/krashpad/krashpad/pom");
        return rb.getString("version");
    }

    /**
     * @return version string.
     */
    private static String getLatestVersion() {
        String url = "https://github.com/mgm3746/krashpad/releases/latest";
        String name = null;
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                    .build();
            HttpGet request = new HttpGet(url);
            request.addHeader("Accept", "application/json");
            request.addHeader("content-type", "application/json");
            HttpResponse result = httpClient.execute(request);
            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            JSONObject jsonObj = new JSONObject(json);
            name = jsonObj.getString("tag_name");
        }

        catch (Exception ex) {
            name = "Unable to retrieve";
            ex.printStackTrace();
        }
        return name;
    }

    /**
     * Create VM Log Analysis report.
     * 
     * @param fel
     *            Fatal error log object.
     * @param reportFileName
     *            Report file name.
     * @param version
     *            krashpad version.
     * @param latestVersion
     *            krashpad version.
     * @param logFileName
     *            The fatal error log that was parsed.
     */
    private static void createReport(FatalErrorLog fel, String reportFileName, boolean version, boolean latestVersion,
            String logFileName) {
        File reportFile = new File(reportFileName);
        FileWriter fileWriter = null;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter(reportFile);
            printWriter = new PrintWriter(fileWriter);

            printWriter.write(logFileName + Constants.LINE_SEPARATOR);

            if (version || latestVersion) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                if (version) {
                    printWriter
                            .write("Running krashpad version: " + getVersion() + System.getProperty("line.separator"));
                }
                if (latestVersion) {
                    printWriter.write("Latest krashpad version/tag: " + getLatestVersion()
                            + System.getProperty("line.separator"));
                }
            }

            if (fel.getJavaSpecification() == JavaSpecification.JDK6
                    || fel.getJavaSpecification() == JavaSpecification.JDK7) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("ERROR:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("*It appears the fatal error log is from " + fel.getJavaSpecification() + "."
                        + Constants.LINE_SEPARATOR + "*Fatal error log analysis prior to JDK8 is not supported."
                        + Constants.LINE_SEPARATOR);
            } else {

                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("OS:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("Version: " + fel.getOsString() + Constants.LINE_SEPARATOR);
                printWriter.write("ARCH: " + fel.getArch() + Constants.LINE_SEPARATOR);
                if (fel.getCpus() > Integer.MIN_VALUE) {
                    printWriter.write(
                            "CPUs (cpu x cpu cores x hyperthreading): " + fel.getCpus() + Constants.LINE_SEPARATOR);
                }
                if (fel.getMemTotal() > 0) {
                    printWriter.write("Memory: " + fel.getMemTotal() + Character.toString(Constants.PRECISION_REPORTING)
                            + Constants.LINE_SEPARATOR);
                    printWriter.write(
                            "Memory Free: " + fel.getMemFree() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getMemFree(), fel.getMemTotal()) + "%)"
                                    + Constants.LINE_SEPARATOR);
                    if (fel.getMemAvailable() > 0) {
                        printWriter.write("Memory Available: " + fel.getMemAvailable()
                                + Character.toString(Constants.PRECISION_REPORTING) + " ("
                                + JdkMath.calcPercent(fel.getMemAvailable(), fel.getMemTotal()) + "%)"
                                + Constants.LINE_SEPARATOR);
                    }
                }
                if (fel.getSystemSwap() >= 0) {
                    printWriter.write("Swap: " + fel.getSystemSwap() + Character.toString(Constants.PRECISION_REPORTING)
                            + Constants.LINE_SEPARATOR);
                    if (fel.getSystemSwap() > 0) {
                        printWriter.write("Swap Free: " + fel.getSystemSwapFree()
                                + Character.toString(Constants.PRECISION_REPORTING) + " ("
                                + JdkMath.calcPercent(fel.getSystemSwapFree(), fel.getSystemSwap()) + "%)"
                                + Constants.LINE_SEPARATOR);
                    }
                }

                if (fel.getAnalysis().contains(Analysis.INFO_CGROUP)) {
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                    printWriter.write("Container:" + Constants.LINE_SEPARATOR);
                    printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    if (fel.getJvmMemoryMax() > 0) {
                        printWriter.write(
                                "Memory: " + fel.getJvmMemTotal() + Character.toString(Constants.PRECISION_REPORTING)
                                        + " (" + JdkMath.calcPercent(fel.getJvmMemTotal(), fel.getMemTotal()) + "%)"
                                        + Constants.LINE_SEPARATOR);
                        printWriter.write("Memory Free: " + fel.getJvmMemFree()
                                + Character.toString(Constants.PRECISION_REPORTING) + " ("
                                + JdkMath.calcPercent(fel.getJvmMemFree(), fel.getJvmMemTotal()) + "%)"
                                + Constants.LINE_SEPARATOR);
                    }
                    if (fel.getSystemSwap() > 0) {
                        printWriter
                                .write("Swap: " + fel.getJvmSwap() + Character.toString(Constants.PRECISION_REPORTING)
                                        + " (" + JdkMath.calcPercent(fel.getJvmSwap(), fel.getSystemSwap()) + "%)"
                                        + Constants.LINE_SEPARATOR);
                        printWriter.write("Swap Free: " + fel.getSystemSwapFree()
                                + Character.toString(Constants.PRECISION_REPORTING) + " ("
                                + JdkMath.calcPercent(fel.getSystemSwapFree(), fel.getSystemSwap()) + "%)"
                                + Constants.LINE_SEPARATOR);
                    }
                }
                if ((fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_LIMIT)
                        || fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_LIMIT_OOPS)
                        || fel.getAnalysis().contains(Analysis.ERROR_OOME_LIMIT)
                        || fel.getAnalysis().contains(Analysis.ERROR_OOME_LIMIT_OOPS))
                        && fel.getRlimitEvent() != null) {
                    printWriter.write(fel.getRlimitEvent().getLogEntry() + Constants.LINE_SEPARATOR);

                }

                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("JVM:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("Version: " + fel.getJdkReleaseString() + Constants.LINE_SEPARATOR);
                printWriter.write("Vendor: " + fel.getJavaVendor() + Constants.LINE_SEPARATOR);
                printWriter.write("Application: " + fel.getApplication() + Constants.LINE_SEPARATOR);
                if (fel.getVmStateEvent() != null) {
                    printWriter.write("VM State: " + fel.getVmState() + Constants.LINE_SEPARATOR);
                }
                if (!fel.getCrashTime().equals("")) {
                    printWriter.write("Crash Date: " + fel.getCrashTime() + Constants.LINE_SEPARATOR);
                }
                if (fel.getElapsedTime() != null) {
                    printWriter.write("Run Time: " + fel.getElapsedTime() + Constants.LINE_SEPARATOR);
                }
                List<GarbageCollector> garbageCollectors = fel.getGarbageCollectors();
                if (!garbageCollectors.isEmpty()) {
                    printWriter.write("Garbage Collector(s): ");
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
                if (fel.getHeapMaxSize() > 0) {
                    printWriter.write("Heap Max: " + fel.getHeapMaxSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                }
                if (fel.getHeapAllocation() > 0) {
                    printWriter.write("Heap Allocation: " + fel.getHeapAllocation()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getHeapAllocation(), fel.getHeapMaxSize()) + "% Heap Max)"
                            + Constants.LINE_SEPARATOR);
                }
                if (fel.getHeapUsed() > 0) {
                    printWriter
                            .write("Heap Used: " + fel.getHeapUsed() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getHeapUsed(), fel.getHeapAllocation())
                                    + "% Heap Allocation)" + Constants.LINE_SEPARATOR);
                }
                if (fel.getMetaspaceMaxSize() > 0) {
                    printWriter.write("Metaspace Max: " + fel.getMetaspaceMaxSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                }
                if (fel.getMetaspaceAllocation() > 0) {
                    printWriter.write("Metaspace Allocation: " + fel.getMetaspaceAllocation()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getMetaspaceAllocation(), fel.getMetaspaceMaxSize())
                            + "% Metaspace Max)" + Constants.LINE_SEPARATOR);
                }
                if (fel.getMetaspaceUsed() > 0) {
                    printWriter.write("Metaspace Used: " + fel.getMetaspaceUsed()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getMetaspaceUsed(), fel.getMetaspaceAllocation())
                            + "% Metaspace Allocation)" + Constants.LINE_SEPARATOR);
                }
                if (fel.getThreadStackSize() > 0) {
                    printWriter
                            .write("Thread Stack Size: " + fel.getThreadStackSize() + "K" + Constants.LINE_SEPARATOR);
                }
                if (fel.getThreadStackMemory() > 0) {
                    printWriter.write("Thread Stack Memory: " + fel.getThreadStackMemory()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                }
                if (fel.getReservedCodeCacheSize() > 0) {
                    printWriter.write("Code Cache Max: " + fel.getReservedCodeCacheSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                }
                if (fel.getDirectMemoryMaxSize() > 0) {
                    printWriter.write("Direct Memory Max: " + fel.getDirectMemoryMaxSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                }
                if (fel.getJvmMemoryMax() > 0) {
                    printWriter.write("JVM Memory Max: >" + fel.getJvmMemoryMax()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getJvmMemoryMax(), fel.getJvmMemTotal()) + "% Available Memory)"
                            + Constants.LINE_SEPARATOR);
                }

                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Threads:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("Current thread: " + fel.getCurrentThread() + Constants.LINE_SEPARATOR);
                printWriter.write("# Java threads: " + fel.getJavaThreadCount() + Constants.LINE_SEPARATOR);

                if (!fel.getError().equals("")) {
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                    printWriter.write("Error(s):" + Constants.LINE_SEPARATOR);
                    printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    if (!fel.getExceptionCountsEvents().isEmpty()) {
                        Iterator<ExceptionCountsEvent> iteratorExceptionCounts = fel.getExceptionCountsEvents()
                                .iterator();
                        while (iteratorExceptionCounts.hasNext()) {
                            ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
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
                List<StackEvent> stack = fel.getStackEvents();
                Iterator<StackEvent> iteratorStackEvents = stack.iterator();
                // Limit stack output for report readability
                int stackLength = 0;
                while (iteratorStackEvents.hasNext() && stackLength < 10) {
                    StackEvent se = iteratorStackEvents.next();
                    printWriter.write(se.getLogEntry() + Constants.LINE_SEPARATOR);
                    stackLength++;
                }
                if (stack.size() > 10) {
                    printWriter.write("..." + Constants.LINE_SEPARATOR);
                }
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);

                // Analysis
                List<Analysis> analysis = fel.getAnalysis();
                if (!analysis.isEmpty()) {

                    // Determine analysis levels
                    List<Analysis> error = new ArrayList<Analysis>();
                    List<Analysis> warn = new ArrayList<Analysis>();
                    List<Analysis> info = new ArrayList<Analysis>();

                    Iterator<Analysis> iteratorAnalysis = analysis.iterator();
                    while (iteratorAnalysis.hasNext()) {
                        Analysis a = iteratorAnalysis.next();
                        String level = a.getKey().split("\\.")[0];
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
                        Analysis a = iteratorAnalysis.next();
                        printWriter.write("*");
                        printWriter.write(a.getValue());
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
                        Analysis a = iteratorAnalysis.next();
                        printWriter.write("*");
                        printWriter.write(a.getValue());
                        if (a.equals(Analysis.WARN_JDK_NOT_LATEST)) {
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
                                }
                                printWriter.write(" and ");
                                printWriter.write("" + releaseDayDiff);
                                printWriter.write(" day");
                                if (releaseDayDiff > 1) {
                                    printWriter.write("s");
                                }
                                printWriter.write(")");
                            }
                            printWriter.write(".");
                        }
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
                        Analysis a = iteratorAnalysis.next();
                        printWriter.write("*");
                        printWriter.write(a.getValue());
                        if (a.equals(Analysis.INFO_OPT_UNDEFINED)) {
                            Iterator<String> iterator = fel.getJvmOptions().getUndefined().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            }
                        } else if (a.equals(Analysis.INFO_OPT_INSTRUMENTATION)) {
                            Iterator<String> iterator = fel.getJvmOptions().getJavaagent().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            }
                            printWriter.write(".");
                        } else if (a.equals(Analysis.INFO_OPT_NATIVE)) {
                            Iterator<String> iterator = fel.getJvmOptions().getAgentpath().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            }
                            printWriter.write(".");
                        }
                        printWriter.write(Constants.LINE_SEPARATOR);
                    }
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                }

                // Unidentified log lines
                List<String> unidentifiedLogLines = fel.getUnidentifiedLogLines();
                if (!unidentifiedLogLines.isEmpty()) {
                    printWriter.write(
                            unidentifiedLogLines.size() + " UNIDENTIFIED LOG LINE(S):" + Constants.LINE_SEPARATOR);
                    printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);

                    Iterator<String> iterator = unidentifiedLogLines.iterator();
                    while (iterator.hasNext()) {
                        String unidentifiedLogLine = iterator.next();
                        printWriter.write(unidentifiedLogLine);
                        printWriter.write(Constants.LINE_SEPARATOR);
                    }
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                }
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
}

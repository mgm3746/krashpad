/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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

package org.github.errcat;

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
import org.github.errcat.domain.jdk.ExceptionCountsEvent;
import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.domain.jdk.StackEvent;
import org.github.errcat.service.Manager;
import org.github.errcat.util.Constants;
import org.github.errcat.util.ErrUtil;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkMath;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.GarbageCollector;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.json.JSONObject;

/**
 * <p>
 * vmcat main class. A controller that prepares the model (by parsinglog entries) and provides analysis (the report
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
    ***REMOVED***

    /**
     * @param args
     *            The argument list includes one or more scope options followed by the name of the vm log file to
     *            inspect.
     */
    public static void main(String[] args) {

        CommandLine cmd = null;

        try {
            cmd = parseOptions(args);
        ***REMOVED*** catch (ParseException pe) {
            System.out.println(pe.getMessage());
            usage(options);
        ***REMOVED***

        if (cmd != null) {
            if (cmd.hasOption(Constants.OPTION_HELP_LONG)) {
                usage(options);
            ***REMOVED*** else {

                String logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
                File logFile = new File(logFileName);
                Manager manager = new Manager();
                FatalErrorLog fel = manager.parse(logFile);

                String outputFileName;
                if (cmd.hasOption(Constants.OPTION_OUTPUT_LONG)) {
                    outputFileName = cmd.getOptionValue(Constants.OPTION_OUTPUT_SHORT);
                ***REMOVED*** else {
                    outputFileName = Constants.OUTPUT_FILE_NAME;
                ***REMOVED***
                boolean version = cmd.hasOption(Constants.OPTION_VERSION_LONG);
                boolean latestVersion = cmd.hasOption(Constants.OPTION_LATEST_VERSION_LONG);

                createReport(fel, outputFileName, version, latestVersion, logFile.getName());
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

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
        ***REMOVED*** else if (args.length == 1 && (args[0].equals("-" + Constants.OPTION_VERSION_SHORT)
                || args[0].equals("--" + Constants.OPTION_VERSION_LONG))) {
            System.out.println("Running vmcat version: " + getVersion());
        ***REMOVED*** else if (args.length == 1 && (args[0].equals("-" + Constants.OPTION_LATEST_VERSION_SHORT)
                || args[0].equals("--" + Constants.OPTION_LATEST_VERSION_LONG))) {
            System.out.println("Latest vmcat version/tag: " + getLatestVersion());
        ***REMOVED*** else if (args.length == 2 && (((args[0].equals("-" + Constants.OPTION_VERSION_SHORT)
                || args[0].equals("--" + Constants.OPTION_VERSION_LONG))
                && (args[1].equals("-" + Constants.OPTION_LATEST_VERSION_SHORT)
                        || args[1].equals("--" + Constants.OPTION_LATEST_VERSION_LONG)))
                || ((args[1].equals("-" + Constants.OPTION_VERSION_SHORT)
                        || args[1].equals("--" + Constants.OPTION_VERSION_LONG))
                        && (args[0].equals("-" + Constants.OPTION_LATEST_VERSION_SHORT)
                                || args[0].equals("--" + Constants.OPTION_LATEST_VERSION_LONG))))) {
            System.out.println("Running vmcat version: " + getVersion());
            System.out.println("Latest vmcat version/tag: " + getLatestVersion());
        ***REMOVED*** else {
            cmd = parser.parse(options, args);
            validateOptions(cmd);
        ***REMOVED***
        return cmd;
    ***REMOVED***

    /**
     * Output usage help.
     * 
     * @param options
     */
    private static void usage(Options options) {
        // Use the built in formatter class
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("vmcat [OPTION]... [FILE]", options);
    ***REMOVED***

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
        if (cmd.getArgList().size() == 0) {
            throw new ParseException("Missing log file");
        ***REMOVED***
        String logFileName = null;
        if (cmd.getArgList().size() > 0) {
            logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
        ***REMOVED***
        // Ensure vm log file exists.
        if (logFileName == null) {
            throw new ParseException("Missing log file not");
        ***REMOVED***
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            throw new ParseException("Invalid log file: '" + logFileName + "'");
        ***REMOVED***
        // threshold
        if (cmd.hasOption(Constants.OPTION_THRESHOLD_LONG)) {
            String thresholdRegEx = "^\\d{1,3***REMOVED***$";
            String thresholdOptionValue = cmd.getOptionValue(Constants.OPTION_THRESHOLD_SHORT);
            Pattern pattern = Pattern.compile(thresholdRegEx);
            Matcher matcher = pattern.matcher(thresholdOptionValue);
            if (!matcher.find()) {
                throw new ParseException("Invalid threshold: '" + thresholdOptionValue + "'");
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    /**
     * @return version string.
     */
    private static String getVersion() {
        ResourceBundle rb = ResourceBundle.getBundle("META-INF/maven/vmcat/vmcat/pom");
        return rb.getString("version");
    ***REMOVED***

    /**
     * @return version string.
     */
    private static String getLatestVersion() {
        String url = "https://github.com/mgm3746/errcat/releases/latest";
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
        ***REMOVED***

        catch (Exception ex) {
            name = "Unable to retrieve";
            ex.printStackTrace();
        ***REMOVED***
        return name;
    ***REMOVED***

    /**
     * Create VM Log Analysis report.
     * 
     * @param fel
     *            Fatal error log object.
     * @param reportFileName
     *            Report file name.
     * @param version
     *            Whether or not to report errcat version.
     * @param latestVersion
     *            Whether or not to report latest errcat version.
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

            if (version || latestVersion) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                if (version) {
                    printWriter.write("Running errcat version: " + getVersion() + System.getProperty("line.separator"));
                ***REMOVED***
                if (latestVersion) {
                    printWriter.write(
                            "Latest errcat version/tag: " + getLatestVersion() + System.getProperty("line.separator"));
                ***REMOVED***
            ***REMOVED***

            printWriter.write(logFileName + Constants.LINE_SEPARATOR);
            if (fel.getJavaSpecification() == JavaSpecification.JDK6
                    || fel.getJavaSpecification() == JavaSpecification.JDK7) {
                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("ERROR:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("*It appears the fatal error log is from " + fel.getJavaSpecification() + "."
                        + Constants.LINE_SEPARATOR + "*Fatal error log analysis prior to JDK8 is not supported."
                        + Constants.LINE_SEPARATOR);
            ***REMOVED*** else {

                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("OS:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("Version: " + fel.getOsString() + Constants.LINE_SEPARATOR);
                printWriter.write("ARCH: " + fel.getArch() + Constants.LINE_SEPARATOR);
                if (fel.getCpus() > Integer.MIN_VALUE) {
                    printWriter.write(
                            "CPUs (cpu x cpu cores x hyperthreading): " + fel.getCpus() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getSystemPhysicalMemory() > 0) {
                    printWriter.write("Memory: " + fel.getSystemPhysicalMemory()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                    printWriter.write("Memory Free: " + fel.getSystemPhysicalMemoryFree()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getSystemPhysicalMemoryFree(), fel.getSystemPhysicalMemory())
                            + "%)" + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getSystemSwap() > 0) {
                    printWriter.write("Swap: " + fel.getSystemSwap() + Character.toString(Constants.PRECISION_REPORTING)
                            + Constants.LINE_SEPARATOR);
                    printWriter.write(
                            "Swap Free: " + fel.getSystemSwapFree() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getSystemSwapFree(), fel.getSystemSwap()) + "%)"
                                    + Constants.LINE_SEPARATOR);
                ***REMOVED***

                if (fel.getAnalysis().contains(Analysis.INFO_CGROUP)) {
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                    printWriter.write("Container:" + Constants.LINE_SEPARATOR);
                    printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    if (fel.getJvmMemory() > 0) {
                        printWriter.write("Memory: " + fel.getJvmPhysicalMemory()
                                + Character.toString(Constants.PRECISION_REPORTING) + " ("
                                + JdkMath.calcPercent(fel.getJvmPhysicalMemory(), fel.getSystemPhysicalMemory()) + "%)"
                                + Constants.LINE_SEPARATOR);
                        printWriter
                                .write("Memory Free: " + fel.getJvmPhysicalMemoryFree()
                                        + Character.toString(Constants.PRECISION_REPORTING) + " (" + JdkMath
                                                .calcPercent(fel.getJvmPhysicalMemoryFree(), fel.getJvmPhysicalMemory())
                                        + "%)" + Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    if (fel.getSystemSwap() > 0) {
                        printWriter
                                .write("Swap: " + fel.getJvmSwap() + Character.toString(Constants.PRECISION_REPORTING)
                                        + " (" + JdkMath.calcPercent(fel.getJvmSwap(), fel.getSystemSwap()) + "%)"
                                        + Constants.LINE_SEPARATOR);
                        printWriter.write("Swap Free: " + fel.getSystemSwapFree()
                                + Character.toString(Constants.PRECISION_REPORTING) + " ("
                                + JdkMath.calcPercent(fel.getSystemSwapFree(), fel.getSystemSwap()) + "%)"
                                + Constants.LINE_SEPARATOR);
                    ***REMOVED***
                ***REMOVED***
                if (fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_LIMIT) && fel.getRlimitEvent() != null
                        && fel.getRlimitEvent().getNproc() != null) {
                    printWriter.write("NPROC: " + fel.getRlimitEvent().getNproc() + Constants.LINE_SEPARATOR);

                ***REMOVED***

                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("JVM:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("Version: " + fel.getJdkReleaseString() + Constants.LINE_SEPARATOR);
                printWriter.write("Vendor: " + fel.getJavaVendor() + Constants.LINE_SEPARATOR);
                printWriter.write("Application: " + fel.getApplication() + Constants.LINE_SEPARATOR);
                if (fel.getVmStateEvent() != null) {
                    printWriter.write("VM State: " + fel.getVmState() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (!fel.getCrashTime().equals("")) {
                    printWriter.write("Crash Date: " + fel.getCrashTime() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getElapsedTime() != null) {
                    printWriter.write("Run Time: " + fel.getElapsedTime() + Constants.LINE_SEPARATOR);
                ***REMOVED***
                List<GarbageCollector> garbageCollectors = fel.getGarbageCollectors();
                if (garbageCollectors.size() > 0) {
                    printWriter.write("Garbage Collector(s): ");
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
                if (fel.getHeapMaxSize() > 0) {
                    printWriter.write("Heap Max: " + fel.getHeapMaxSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getHeapAllocation() > 0) {
                    printWriter.write("Heap Allocation: " + fel.getHeapAllocation()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getHeapAllocation(), fel.getHeapMaxSize()) + "% Heap Max)"
                            + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getHeapUsed() > 0) {
                    printWriter
                            .write("Heap Used: " + fel.getHeapUsed() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getHeapUsed(), fel.getHeapAllocation())
                                    + "% Heap Allocation)" + Constants.LINE_SEPARATOR);
                ***REMOVED***
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
                if (fel.getMetaspaceUsed() > 0) {
                    printWriter.write("Metaspace Used: " + fel.getMetaspaceUsed()
                            + Character.toString(Constants.PRECISION_REPORTING) + " ("
                            + JdkMath.calcPercent(fel.getMetaspaceUsed(), fel.getMetaspaceAllocation())
                            + "% Metaspace Allocation)" + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getThreadStackSize() > 0) {
                    printWriter
                            .write("Thread Stack Size: " + fel.getThreadStackSize() + "K" + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getThreadStackMemory() > 0) {
                    printWriter.write("Thread Stack Memory: " + fel.getThreadStackMemory()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getDirectMemoryMaxSize() > 0) {
                    printWriter.write("Direct Memory Max: " + fel.getDirectMemoryMaxSize()
                            + Character.toString(Constants.PRECISION_REPORTING) + Constants.LINE_SEPARATOR);
                ***REMOVED***
                if (fel.getJvmMemory() > 0) {
                    printWriter.write(
                            "JVM Memory: >" + fel.getJvmMemory() + Character.toString(Constants.PRECISION_REPORTING)
                                    + " (" + JdkMath.calcPercent(fel.getJvmMemory(), fel.getJvmPhysicalMemory())
                                    + "% Available Memory)" + Constants.LINE_SEPARATOR);
                ***REMOVED***

                printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                printWriter.write("Threads:" + Constants.LINE_SEPARATOR);
                printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                printWriter.write("Current thread: " + fel.getCurrentThread() + Constants.LINE_SEPARATOR);
                printWriter.write("***REMOVED*** Java threads: " + fel.getJavaThreadCount() + Constants.LINE_SEPARATOR);

                if (!fel.getError().equals("")) {
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                    printWriter.write("Error(s):" + Constants.LINE_SEPARATOR);
                    printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);
                    if (fel.getExceptionCountsEvents().size() > 0) {
                        Iterator<ExceptionCountsEvent> iteratorExceptionCounts = fel.getExceptionCountsEvents()
                                .iterator();
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
                        Analysis a = iteratorAnalysis.next();
                        printWriter.write("*");
                        printWriter.write(a.getValue());
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
                        Analysis a = iteratorAnalysis.next();
                        printWriter.write("*");
                        printWriter.write(a.getValue());
                        if (a.equals(Analysis.INFO_OPT_UNDEFINED)) {
                            Iterator<String> iterator = fel.getJvmOptions().getUndefined().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            ***REMOVED***
                        ***REMOVED*** else if (a.equals(Analysis.INFO_OPT_INSTRUMENTATION)) {
                            Iterator<String> iterator = fel.getJvmOptions().getJavaagent().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
                            ***REMOVED***
                            printWriter.write(".");
                        ***REMOVED*** else if (a.equals(Analysis.INFO_OPT_NATIVE)) {
                            Iterator<String> iterator = fel.getJvmOptions().getAgentpath().iterator();
                            while (iterator.hasNext()) {
                                String option = iterator.next();
                                printWriter.write(" ");
                                printWriter.write(option);
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
                    printWriter.write(
                            unidentifiedLogLines.size() + " UNIDENTIFIED LOG LINE(S):" + Constants.LINE_SEPARATOR);
                    printWriter.write("----------------------------------------" + Constants.LINE_SEPARATOR);

                    Iterator<String> iterator = unidentifiedLogLines.iterator();
                    while (iterator.hasNext()) {
                        String unidentifiedLogLine = iterator.next();
                        printWriter.write(unidentifiedLogLine);
                        printWriter.write(Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    printWriter.write("========================================" + Constants.LINE_SEPARATOR);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** catch (

        FileNotFoundException e) {
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
***REMOVED***

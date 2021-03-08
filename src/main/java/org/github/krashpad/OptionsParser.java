package org.github.krashpad;

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
import static org.github.krashpad.util.Constants.OPTION_HELP_LONG;
import static org.github.krashpad.util.Constants.OPTION_HELP_SHORT;
import static org.github.krashpad.util.Constants.OPTION_LATEST_VERSION_LONG;
import static org.github.krashpad.util.Constants.OPTION_LATEST_VERSION_SHORT;
import static org.github.krashpad.util.Constants.OPTION_OUTPUT_LONG;
import static org.github.krashpad.util.Constants.OPTION_OUTPUT_SHORT;
import static org.github.krashpad.util.Constants.OPTION_THRESHOLD_LONG;
import static org.github.krashpad.util.Constants.OPTION_THRESHOLD_SHORT;
import static org.github.krashpad.util.Constants.OPTION_VERSION_LONG;
import static org.github.krashpad.util.Constants.OPTION_VERSION_SHORT;
import static org.github.krashpad.util.Constants.OUTPUT_FILE_NAME;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
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
import org.json.JSONObject;

/**
 * @author <a href="https://github.com/pfichtner">Peter Fichtner</a>
 */
public class OptionsParser {

    static Options options;

    static {
        // Declare command line options
        options = new Options();
        options.addOption(OPTION_HELP_SHORT, OPTION_HELP_LONG, false, "help");
        options.addOption(OPTION_VERSION_SHORT, OPTION_VERSION_LONG, false, "version");
        options.addOption(OPTION_LATEST_VERSION_SHORT, OPTION_LATEST_VERSION_LONG, false, "latest version");
        options.addOption(OPTION_THRESHOLD_SHORT, OPTION_THRESHOLD_LONG, true,
                "threshold (0-100) for throughput bottleneck reporting");
        options.addOption(OPTION_OUTPUT_SHORT, OPTION_OUTPUT_LONG, true,
                "output file name (default " + OUTPUT_FILE_NAME + ")");
    ***REMOVED***

    /**
     * Parse command line options.
     * 
     * @return
     */
    public static final CommandLine parseOptions(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        // Allow user to just specify help or version.
        if (args.length == 1 && (args[0].equals("-" + OPTION_HELP_SHORT) || args[0].equals("--" + OPTION_HELP_LONG))) {
            return null;
        ***REMOVED*** else if (args.length == 1
                && (args[0].equals("-" + OPTION_VERSION_SHORT) || args[0].equals("--" + OPTION_VERSION_LONG))) {
            System.out.println("Running garbagecat version: " + getVersion());
        ***REMOVED*** else if (args.length == 1 && (args[0].equals("-" + OPTION_LATEST_VERSION_SHORT)
                || args[0].equals("--" + OPTION_LATEST_VERSION_LONG))) {
            System.out.println("Latest garbagecat version/tag: " + getLatestVersion());
        ***REMOVED*** else if (args.length == 2
                && (((args[0].equals("-" + OPTION_VERSION_SHORT) || args[0].equals("--" + OPTION_VERSION_LONG))
                        && (args[1].equals("-" + OPTION_LATEST_VERSION_SHORT)
                                || args[1].equals("--" + OPTION_LATEST_VERSION_LONG)))
                        || ((args[1].equals("-" + OPTION_VERSION_SHORT) || args[1].equals("--" + OPTION_VERSION_LONG))
                                && (args[0].equals("-" + OPTION_LATEST_VERSION_SHORT)
                                        || args[0].equals("--" + OPTION_LATEST_VERSION_LONG))))) {
            System.out.println("Running garbagecat version: " + getVersion());
            System.out.println("Latest garbagecat version/tag: " + getLatestVersion());
        ***REMOVED*** else {
            CommandLine cmd = parser.parse(options, args);
            validateOptions(cmd);
            return cmd;
        ***REMOVED***
        return null;
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
    private static void validateOptions(CommandLine cmd) throws ParseException {
        // Ensure log file specified.
        if (cmd.getArgList().isEmpty()) {
            throw new ParseException("Missing log file");
        ***REMOVED***
        if (cmd.getArgList().isEmpty()) {
            throw new ParseException("Missing log file not");
        ***REMOVED***
        String logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            throw new ParseException("Invalid log file: '" + logFileName + "'");
        ***REMOVED***
    ***REMOVED***

    /**
     * @return version string.
     */
    static String getVersion() {
        return ResourceBundle.getBundle("META-INF/maven/krashpad/krashpad/pom").getString("version");
    ***REMOVED***

    /**
     * @return version string.
     */
    static String getLatestVersion() {
        String url = "https://github.com/mgm3746/krashpad/releases/latest";
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
            return new JSONObject(json).getString("tag_name");
        ***REMOVED***

        catch (Exception ex) {
            ex.printStackTrace();
            return "Unable to retrieve";
        ***REMOVED***
    ***REMOVED***

***REMOVED***

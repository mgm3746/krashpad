/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
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

import static org.github.krashpad.util.Constants.OPTION_HELP_LONG;
import static org.github.krashpad.util.Constants.OPTION_HELP_SHORT;
import static org.github.krashpad.util.Constants.OPTION_OUTPUT_LONG;
import static org.github.krashpad.util.Constants.OPTION_OUTPUT_SHORT;
import static org.github.krashpad.util.Constants.OPTION_REPORT_CONSOLE_LONG;
import static org.github.krashpad.util.Constants.OPTION_REPORT_CONSOLE_SHORT;
import static org.github.krashpad.util.Constants.OUTPUT_FILE_NAME;

import java.io.File;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * @author <a href="https://github.com/pfichtner">Peter Fichtner</a>
 */
public class OptionsParser {

    static Options options;

    static {
        // Declare command line options
        options = new Options();
        options.addOption(OPTION_HELP_SHORT, OPTION_HELP_LONG, false, "help");
        options.addOption(OPTION_OUTPUT_SHORT, OPTION_OUTPUT_LONG, true,
                "output file name (default " + OUTPUT_FILE_NAME + ")");
        options.addOption(OPTION_REPORT_CONSOLE_SHORT, OPTION_REPORT_CONSOLE_LONG, false,
                "print report to stdout instead of file");
    }

    /**
     * @param args
     *            The command line options.
     * @return Create <code>CommandLineParser</code> from the command line options.
     * @throws ParseException
     *             for invalid command line options.
     */
    public static final CommandLine parseOptions(String[] args) throws ParseException {
        CommandLineParser parser = new BasicParser();
        // Allow user to just specify help or version.
        if (args.length == 1 && (args[0].equals("-" + OPTION_HELP_SHORT) || args[0].equals("--" + OPTION_HELP_LONG))) {
            return null;
        } else {
            CommandLine cmd = parser.parse(options, args);
            validateOptions(cmd);
            return cmd;
        }
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
    private static void validateOptions(CommandLine cmd) throws ParseException {
        // Ensure command line input.
        if (cmd.getArgList().size() == 0) {
            throw new ParseException("Missing input");
        } else {
            // Ensure file input.
            String logFileName = (String) cmd.getArgList().get(cmd.getArgList().size() - 1);
            if (logFileName == null) {
                throw new ParseException("Missing file");
            } else {
                // Ensure file exists.
                File logFile = new File(logFileName);
                if (!logFile.exists()) {
                    throw new ParseException("Invalid file: '" + logFileName + "'");
                }
            }
        }
    }
}

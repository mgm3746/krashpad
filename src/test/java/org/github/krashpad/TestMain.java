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

import static org.github.krashpad.util.Constants.OPTION_HELP_LONG;
import static org.github.krashpad.util.Constants.OPTION_HELP_SHORT;
import static org.github.krashpad.util.Constants.OPTION_LATEST_VERSION_LONG;
import static org.github.krashpad.util.Constants.OPTION_LATEST_VERSION_SHORT;
import static org.github.krashpad.util.Constants.OPTION_OUTPUT_LONG;
import static org.github.krashpad.util.Constants.OPTION_OUTPUT_SHORT;
import static org.github.krashpad.util.Constants.OPTION_VERSION_LONG;
import static org.github.krashpad.util.Constants.OPTION_VERSION_SHORT;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TestMain {

    @Test
    void testShortOptions(@TempDir File temporaryFolder) throws Exception {
        // Method arguments
        String[] args = new String[] { //
                "-h", //
                "-o", //
                "12345678.txt", //
                "-v", //
                "-l", //
                // Instead of a file, use a location sure to exist.
                temporaryFolder.getAbsolutePath() //
        ***REMOVED***;
        CommandLine cmd = OptionsParser.parseOptions(args);
        assertNotNull(cmd);
        assertHasOption(cmd, OPTION_HELP_SHORT);
        assertHasOption(cmd, OPTION_OUTPUT_SHORT);
        assertHasOption(cmd, OPTION_VERSION_SHORT);
        assertHasOption(cmd, OPTION_LATEST_VERSION_SHORT);
    ***REMOVED***

    @Test
    void testLongOptions(@TempDir File temporaryFolder) throws Exception {
        // Method arguments
        String[] args = new String[] { //
                "--help", //
                "--output", //
                "12345678.txt", //
                "--version", //
                "--latest", //
                // Instead of a file, use a location sure to exist.
                temporaryFolder.getAbsolutePath() //
        ***REMOVED***;
        CommandLine cmd = OptionsParser.parseOptions(args);
        assertNotNull(cmd);
        assertHasOption(cmd, OPTION_HELP_LONG);
        assertHasOption(cmd, OPTION_OUTPUT_LONG);
        assertHasOption(cmd, OPTION_VERSION_LONG);
        assertHasOption(cmd, OPTION_LATEST_VERSION_LONG);

    ***REMOVED***

    @Test
    void testShortHelpOption() throws Exception {
        // Method arguments
        String[] args = new String[] { "-h" ***REMOVED***;
        CommandLine cmd = OptionsParser.parseOptions(args);
        // CommandLine will be null if only the help option is passed in.
        assertNull(cmd);
    ***REMOVED***

    @Test
    void testLongHelpOption() throws Exception {
        // Method arguments
        String[] args = new String[] { "--help" ***REMOVED***;
        // Pass null object since parseOptions is static
        CommandLine cmd = OptionsParser.parseOptions(args);
        // CommandLine will be null if only the help option is passed in.
        assertNull(cmd);
    ***REMOVED***

    private static void assertHasOption(CommandLine cmd, String option) {
        assertTrue(cmd.hasOption(option), "'-" + option + "' is a valid option");
    ***REMOVED***
***REMOVED***

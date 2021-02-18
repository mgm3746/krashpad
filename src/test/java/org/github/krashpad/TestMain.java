/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.github.krashpad.util.Constants;
import org.junit.Assert;

import junit.framework.TestCase;

public class TestMain extends TestCase {

    public void testShortOptions() {
        try {
            Class<?> c = Class.forName("org.github.krashpad.Main");
            Class.forName("java.lang.IllegalArgumentException");
            Class<?>[] argTypes = new Class[] { String[].class ***REMOVED***;
            Method parseOptions = c.getDeclaredMethod("parseOptions", argTypes);
            // Make private method accessible
            parseOptions.setAccessible(true);
            // Method arguments
            String[] args = new String[6];
            args[0] = "-h";
            args[1] = "-o";
            args[2] = "12345678.txt";
            args[3] = "-v";
            args[4] = "-l";
            // Instead of a file, use a location sure to exist.
            args[5] = System.getProperty("user.dir");
            // Pass null object since parseOptions is static
            Object o = parseOptions.invoke(null, (Object) args);
            CommandLine cmd = (CommandLine) o;
            Assert.assertNotNull(cmd);
            Assert.assertTrue("'-" + Constants.OPTION_HELP_SHORT + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_HELP_SHORT));
            Assert.assertTrue("'-" + Constants.OPTION_OUTPUT_SHORT + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_OUTPUT_SHORT));
            Assert.assertTrue("'-" + Constants.OPTION_VERSION_SHORT + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_VERSION_SHORT));
            Assert.assertTrue("'-" + Constants.OPTION_LATEST_VERSION_SHORT + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_LATEST_VERSION_SHORT));
        ***REMOVED*** catch (ClassNotFoundException e) {
            Assert.fail(e.getMessage());
        ***REMOVED*** catch (SecurityException e) {
            Assert.fail("SecurityException: " + e.getMessage());
        ***REMOVED*** catch (NoSuchMethodException e) {
            Assert.fail("NoSuchMethodException: " + e.getMessage());
        ***REMOVED*** catch (IllegalArgumentException e) {
            Assert.fail("IllegalArgumentException: " + e.getMessage());
        ***REMOVED*** catch (IllegalAccessException e) {
            Assert.fail("IllegalAccessException: " + e.getMessage());
        ***REMOVED*** catch (InvocationTargetException e) {
            // Anything the invoked method throws is wrapped by InvocationTargetException.
            Assert.fail("InvocationTargetException: " + e.getMessage());
        ***REMOVED***
    ***REMOVED***

    public void testLongOptions() {
        try {
            Class<?> c = Class.forName("org.github.krashpad.Main");
            Class<?>[] argTypes = new Class[] { String[].class ***REMOVED***;
            Method parseOptions = c.getDeclaredMethod("parseOptions", argTypes);
            // Make private method accessible
            parseOptions.setAccessible(true);
            // Method arguments
            String[] args = new String[6];
            args[0] = "--help";
            args[1] = "--output";
            args[2] = "12345678.txt";
            args[3] = "--version";
            args[4] = "--latest";
            // Instead of a file, use a location sure to exist.
            args[5] = System.getProperty("user.dir");
            // Pass null object since parseOptions is static
            Object o = parseOptions.invoke(null, (Object) args);
            CommandLine cmd = (CommandLine) o;
            Assert.assertNotNull(cmd);
            Assert.assertTrue("'-" + Constants.OPTION_HELP_LONG + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_HELP_LONG));
            Assert.assertTrue("'-" + Constants.OPTION_OUTPUT_LONG + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_OUTPUT_LONG));
            Assert.assertTrue("'-" + Constants.OPTION_VERSION_LONG + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_VERSION_LONG));
            Assert.assertTrue("'-" + Constants.OPTION_LATEST_VERSION_LONG + "' is a valid option",
                    cmd.hasOption(Constants.OPTION_LATEST_VERSION_LONG));
        ***REMOVED*** catch (ClassNotFoundException e) {
            Assert.fail(e.getMessage());
        ***REMOVED*** catch (SecurityException e) {
            Assert.fail("SecurityException: " + e.getMessage());
        ***REMOVED*** catch (NoSuchMethodException e) {
            Assert.fail("NoSuchMethodException: " + e.getMessage());
        ***REMOVED*** catch (IllegalArgumentException e) {
            Assert.fail("IllegalArgumentException: " + e.getMessage());
        ***REMOVED*** catch (IllegalAccessException e) {
            Assert.fail("IllegalAccessException: " + e.getMessage());
        ***REMOVED*** catch (InvocationTargetException e) {
            Assert.fail("InvocationTargetException: " + e.getMessage());
        ***REMOVED***
    ***REMOVED***

    public void testInvalidOption() {
        try {
            Class<?> c = Class.forName("org.github.krashpad.Main");
            Class<?>[] argTypes = new Class[] { String[].class ***REMOVED***;
            Method parseOptions = c.getDeclaredMethod("parseOptions", argTypes);
            // Make private method accessible
            parseOptions.setAccessible(true);
            // Method arguments
            String[] args = new String[2];
            // Test typo (extra 'h')
            args[0] = "--hhelp";
            args[1] = System.getProperty("user.dir");
            // Pass null object since parseOptions is static
            Object o = parseOptions.invoke(null, (Object) args);
            CommandLine cmd = (CommandLine) o;
            // An unrecognized option throws an <code>UnrecognizedOptionException</code>,
            // which is
            // caught and the usage line output.
            Assert.assertNull("An invalid option was accepted.", cmd);
        ***REMOVED*** catch (ClassNotFoundException e) {
            Assert.fail(e.getMessage());
        ***REMOVED*** catch (SecurityException e) {
            Assert.fail("SecurityException: " + e.getMessage());
        ***REMOVED*** catch (NoSuchMethodException e) {
            Assert.fail("NoSuchMethodException: " + e.getMessage());
        ***REMOVED*** catch (IllegalArgumentException e) {
            Assert.fail("IllegalArgumentException: " + e.getMessage());
        ***REMOVED*** catch (IllegalAccessException e) {
            Assert.fail("IllegalAccessException: " + e.getMessage());
        ***REMOVED*** catch (InvocationTargetException e) {
            // Anything the invoked method throws is wrapped by InvocationTargetException.
            Assert.assertTrue("Epected ParseException not thrown.", e.getTargetException() instanceof ParseException);
        ***REMOVED***
    ***REMOVED***

    public void testShortHelpOption() {
        try {
            Class<?> c = Class.forName("org.github.krashpad.Main");
            Class<?>[] argTypes = new Class[] { String[].class ***REMOVED***;
            Method parseOptions = c.getDeclaredMethod("parseOptions", argTypes);
            // Make private method accessible
            parseOptions.setAccessible(true);
            // Method arguments
            String[] args = new String[1];
            args[0] = "-h";
            // Pass null object since parseOptions is static
            parseOptions.invoke(null, (Object) args);
            Object o = parseOptions.invoke(null, (Object) args);
            CommandLine cmd = (CommandLine) o;
            // CommandLine will be null if only the help option is passed in.
            Assert.assertNull(cmd);
            Assert.assertTrue("'-h' is a valid option", true);
        ***REMOVED*** catch (ClassNotFoundException e) {
            Assert.fail(e.getMessage());
        ***REMOVED*** catch (SecurityException e) {
            Assert.fail("SecurityException: " + e.getMessage());
        ***REMOVED*** catch (NoSuchMethodException e) {
            Assert.fail("NoSuchMethodException: " + e.getMessage());
        ***REMOVED*** catch (IllegalArgumentException expected) {
            Assert.assertNotNull(expected.getMessage());
        ***REMOVED*** catch (IllegalAccessException e) {
            Assert.fail("IllegalAccessException: " + e.getMessage());
        ***REMOVED*** catch (InvocationTargetException e) {
            Assert.fail("InvocationTargetException: " + e.getMessage());
        ***REMOVED***
    ***REMOVED***

    public void testLongHelpOption() {
        try {
            Class<?> c = Class.forName("org.github.krashpad.Main");
            Class<?>[] argTypes = new Class[] { String[].class ***REMOVED***;
            Method parseOptions = c.getDeclaredMethod("parseOptions", argTypes);
            // Make private method accessible
            parseOptions.setAccessible(true);
            // Method arguments
            String[] args = new String[1];
            args[0] = "--help";
            // Pass null object since parseOptions is static
            parseOptions.invoke(null, (Object) args);
            Object o = parseOptions.invoke(null, (Object) args);
            CommandLine cmd = (CommandLine) o;
            // CommandLine will be null if only the help option is passed in.
            Assert.assertNull(cmd);
            Assert.assertTrue("'--help' is a valid option", true);
        ***REMOVED*** catch (ClassNotFoundException e) {
            Assert.fail(e.getMessage());
        ***REMOVED*** catch (SecurityException e) {
            Assert.fail("SecurityException: " + e.getMessage());
        ***REMOVED*** catch (NoSuchMethodException e) {
            Assert.fail("NoSuchMethodException: " + e.getMessage());
        ***REMOVED*** catch (IllegalArgumentException expected) {
            Assert.assertNotNull(expected.getMessage());
        ***REMOVED*** catch (IllegalAccessException e) {
            Assert.fail("IllegalAccessException: " + e.getMessage());
        ***REMOVED*** catch (InvocationTargetException e) {
            Assert.fail("InvocationTargetException: " + e.getMessage());
        ***REMOVED***
    ***REMOVED***
***REMOVED***
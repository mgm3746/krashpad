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
package org.github.krashpad.domain.jdk;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestEnvironmentVariablesEvent extends TestCase {

    public void testIdentity() {
        String logLine = "PATH=/path/to/bin";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "PATH=/path/to/bin";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof EnvironmentVariablesEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testLdLibraryPath() {
        String logLine = "LD_LIBRARY_PATH=:/path/to/lib";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testShell() {
        String logLine = "SHELL=/bin/ksh";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testJavaHome() {
        String logLine = "JAVA_HOME=/etc/alternatives/jre_1.8.0";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testJreHome() {
        String logLine = "JRE_HOME=/usr/java/jdk1.8.0_241-amd64/jre";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testClasspath() {
        String logLine = "CLASSPATH=/usr/java/jdk1.8.0_241-amd64/lib/tools.jar";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testUsername() {
        String logLine = "USERNAME=bob";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testCommandLine() {
        String logLine = "USERNAME=bob";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testDisplay() {
        String logLine = "DISPLAY=:1";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testLang() {
        String logLine = "LANG=en_US.UTF-8";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testOs() {
        String logLine = "OS=Windows_NT";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testProcessorIdentifier() {
        String logLine = "PROCESSOR_IDENTIFIER=Intel64 Family 6 Model 62 Stepping 4, GenuineIntel";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testDyldLibraryPath() {
        String logLine = "DYLD_LIBRARY_PATH=/opt/tibco/ftl5.4.2-x86_64/lib";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testLdPreload() {
        String logLine = "LD_PRELOAD=/home/jboss/libc_hooks3.so";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testJavaOptions() {
        String logLine = "_JAVA_OPTIONS=-XX:OnOutOfMemoryError='/bin/kill -ABRT %p'";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testHostType() {
        String logLine = "HOSTTYPE=x86_64-linux";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testOsType() {
        String logLine = "OSTYPE=linux";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***

    public void testMachType() {
        String logLine = "MACHTYPE=x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES);
    ***REMOVED***
***REMOVED***
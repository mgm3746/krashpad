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
package org.github.krashpad.domain.jdk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestEnvironmentVariablesEvent {

    @Test
    void testArch() {
        String logLine = "ARCH=x86_64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClasspath() {
        String logLine = "CLASSPATH=/usr/java/jdk1.8.0_241-amd64/lib/tools.jar";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCommandLine() {
        String logLine = "USERNAME=bob";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testDisplay() {
        String logLine = "DISPLAY=:1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testDyldLibraryPath() {
        String logLine = "DYLD_LIBRARY_PATH=/opt/tibco/ftl5.4.2-x86_64/lib";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHostType() {
        String logLine = "HOSTTYPE=x86_64-linux";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "PATH=/path/to/bin";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testJavaHome() {
        String logLine = "JAVA_HOME=/etc/alternatives/jre_1.8.0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testJavaOptions() {
        String logLine = "_JAVA_OPTIONS=-XX:OnOutOfMemoryError='/bin/kill -ABRT %p'";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testJreHome() {
        String logLine = "JRE_HOME=/usr/java/jdk1.8.0_241-amd64/jre";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLang() {
        String logLine = "LANG=en_US.UTF-8";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLdLibraryPath() {
        String logLine = "LD_LIBRARY_PATH=:/path/to/lib";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLdPreload() {
        String logLine = "LD_PRELOAD=/home/jboss/libc_hooks3.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMachType() {
        String logLine = "MACHTYPE=x86_64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOs() {
        String logLine = "OS=Windows_NT";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOsType() {
        String logLine = "OSTYPE=linux";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "PATH=/path/to/bin";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof EnvironmentVariablesEvent,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testProcessorIdentifier() {
        String logLine = "PROCESSOR_IDENTIFIER=Intel64 Family 6 Model 62 Stepping 4, GenuineIntel";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShell() {
        String logLine = "SHELL=/bin/ksh";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTz() {
        String logLine = "TZ=Asia/Hong_Kong";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testUsername() {
        String logLine = "USERNAME=bob";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    ***REMOVED***
***REMOVED***
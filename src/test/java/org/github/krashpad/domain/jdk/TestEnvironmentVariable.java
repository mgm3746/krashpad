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
package org.github.krashpad.domain.jdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestEnvironmentVariable {

    @Test
    void testArch() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "ARCH=x86_64";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testClasspath() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "CLASSPATH=/usr/java/jdk1.8.0_241-amd64/lib/tools.jar";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testCommandLine() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "USERNAME=bob";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testDisplay() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "DISPLAY=:1";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testDyldLibraryPath() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "DYLD_LIBRARY_PATH=/opt/tibco/ftl5.4.2-x86_64/lib";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Environment Variables:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testHostType() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "HOSTTYPE=x86_64-linux";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "PATH=/path/to/bin";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.ENVIRONMENT_VARIABLES,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testJavaHome() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "JAVA_HOME=/etc/alternatives/jre_1.8.0";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testJavaOptions() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "_JAVA_OPTIONS=-XX:OnOutOfMemoryError='/bin/kill -ABRT %p'";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testJreHome() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "JRE_HOME=/usr/java/jdk1.8.0_241-amd64/jre";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testLang() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "LANG=en_US.UTF-8";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testLcAll() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "LC_ALL=POSIX";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testLdLibraryPath() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "LD_LIBRARY_PATH=:/path/to/lib";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testLdPreload() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "LD_PRELOAD=/home/jboss/libc_hooks3.so";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testMachType() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "MACHTYPE=x86_64";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testOs() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "OS=Windows_NT";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testOsType() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "OSTYPE=linux";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "PATH=/path/to/bin";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof EnvironmentVariable,
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not parsed.");
    }

    @Test
    void testProcessorIdentifier() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "PROCESSOR_IDENTIFIER=Intel64 Family 6 Model 62 Stepping 4, GenuineIntel";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testShell() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "SHELL=/bin/ksh";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testTerm() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "TERM=xterm";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }

    @Test
    void testTz() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "TZ=Asia/Hong_Kong";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
        ;
    }

    @Test
    void testUsername() {
        EnvironmentVariable priorLogEvent = new EnvironmentVariable("Environment Variables:");
        String logLine = "USERNAME=bob";
        assertEquals(JdkUtil.LogEventType.ENVIRONMENT_VARIABLES, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString() + " not identified.");
    }
}
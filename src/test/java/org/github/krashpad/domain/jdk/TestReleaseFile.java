/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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
class TestReleaseFile {

    @Test
    void testIdentity() {
        String logLine = "Release file:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.RELEASE_FILE,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not identified.");
    }

    @Test
    void testImplementor() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "IMPLEMENTOR=\"Red Hat, Inc.\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testImplementorVersion() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "IMPLEMENTOR_VERSION=\"(Red_Hat-21.0.9.0.10-1)\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testJavaRuntimeVersion() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "JAVA_RUNTIME_VERSION=\"21.0.9+10-LTS\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testJavaVersion() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "JAVA_VERSION=\"21.0.9\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testJavaVersionDate() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "JAVA_VERSION_DATE=\"2025-10-21\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testLibc() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "LIBC=\"gnu\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testModules() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "MODULES=\"java.base java.compiler java.datatransfer\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testOsArch() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "OS_ARCH=\"x86_64\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testOsName() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "OS_NAME=\"Linux\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Release file:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testReleaseFileHasNotBeenRead() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "<release file has not been read>";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }

    @Test
    void testSource() {
        ReleaseFile priorEvent = new ReleaseFile(null);
        String logLine = "SOURCE=\".:git:7f14648260ff\"";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof ReleaseFile,
                JdkUtil.LogEventType.RELEASE_FILE.toString() + " not parsed.");
    }
}
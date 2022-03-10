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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestOsEvent {

    @Test
    void testAssembled() {
        String logLine = "                             Assembled 23 June 2020";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBugReportUrl() {
        String logLine = "BUG_REPORT_URL=\"https://bugs.debian.org/\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCentOs() {
        String logLine = "OS:CentOS Linux release 7.7.1908 (Core)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCopyright() {
        String logLine = "             Copyright (c) 1983, 2020, Oracle and/or its affiliates.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testError() {
        String logLine = "[error occurred during error reporting (printing OS information), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeaderOnSeparateLine() {
        String logLine = "OS:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHomeUrl() {
        String logLine = "HOME_URL=\"https://www.debian.org/\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testId() {
        String logLine = "ID=debian";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testName() {
        String logLine = "NAME=\"Debian GNU/Linux\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOracleLinux() {
        String logLine = "OS:Oracle Linux Server release 6.10";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOracleSolaris() {
        String logLine = "OS:                            Oracle Solaris 11.4 SPARC";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOsOnSeparateLine() {
        String logLine = "Red Hat Enterprise Linux release 8.5 (Ootpa)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOsPrettyName() {
        String logLine = "OS:PRETTY_NAME=\"Debian GNU/Linux 10 (buster)\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof OsEvent,
                JdkUtil.LogEventType.OS.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPatchlevel() {
        String logLine = "PATCHLEVEL = 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPleaseCheckEtcOsRelease() {
        LogEvent priorEvent = new OsEvent(null);
        String logLine = "***REMOVED*** Please check /etc/os-release for details about this release.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRhel6() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 6.10 (Santiago)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRhel7Workstation() {
        String logLine = "OS:Red Hat Enterprise Linux Workstation release 7.4 (Maipo)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRhel8() {
        String logLine = "OS:Red Hat Enterprise Linux release 8.2 (Ootpa)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSupportUrl() {
        String logLine = "SUPPORT_URL=\"https://www.debian.org/support\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThisFileIsDeprecated() {
        LogEvent priorEvent = new OsEvent(null);
        String logLine = "***REMOVED*** This file is deprecated and will be removed in a future service pack or release.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVersion() {
        String logLine = "VERSION=\"10 (buster)\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVersionCodeName() {
        String logLine = "VERSION_CODENAME=buster";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVersionId() {
        String logLine = "VERSION_ID=\"10\"";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVersionNoQuotes() {
        String logLine = "VERSION = 12";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testWindows() {
        String logLine = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.3630)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS,
                JdkUtil.LogEventType.OS.toString() + " not identified.");

    ***REMOVED***
***REMOVED***

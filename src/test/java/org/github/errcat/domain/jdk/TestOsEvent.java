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
package org.github.errcat.domain.jdk;

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestOsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof OsEvent);
    ***REMOVED***

    public void testOracleSolaris() {
        String logLine = "OS:                            Oracle Solaris 11.4 SPARC";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testOracleLinux() {
        String logLine = "OS:Oracle Linux Server release 6.10";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testRhel6() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 6.10 (Santiago)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testRhel7Workstation() {
        String logLine = "OS:Red Hat Enterprise Linux Workstation release 7.4 (Maipo)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testRhel8() {
        String logLine = "OS:Red Hat Enterprise Linux release 8.2 (Ootpa)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testWindows() {
        String logLine = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.3630)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);

    ***REMOVED***

    public void testCentOs() {
        String logLine = "OS:CentOS Linux release 7.7.1908 (Core)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testCopyright() {
        String logLine = "             Copyright (c) 1983, 2020, Oracle and/or its affiliates.";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testSAssembled() {
        String logLine = "                             Assembled 23 June 2020";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***
***REMOVED***

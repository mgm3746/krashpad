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

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.Constants.OsVendor;
import org.github.errcat.util.Constants.OsVersion;
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
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof OsEvent);
    ***REMOVED***

    public void testLinux() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("OS type not correct.", OsType.Linux, ((OsEvent) event).getOsType());
        Assert.assertEquals("OS vendor not correct.", OsVendor.RedHat, ((OsEvent) event).getOsVendor());
    ***REMOVED***

    public void testOracle() {
        String logLine = "OS:                            Oracle Solaris 11.4 SPARC";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("OS type not correct.", OsType.Solaris, ((OsEvent) event).getOsType());
        Assert.assertEquals("OS vendor not correct.", OsVendor.Oracle, ((OsEvent) event).getOsVendor());
    ***REMOVED***

    public void testIsRhel() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.8 (Maipo)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertTrue("RHEL not identified.", ((OsEvent) event).isRhel());
    ***REMOVED***

    public void testWindows() {
        String logLine = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.3630)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("OS type not correct.", OsType.Windows, ((OsEvent) event).getOsType());
        Assert.assertEquals("OS vendor not correct.", OsVendor.Microsoft, ((OsEvent) event).getOsVendor());
        Assert.assertTrue("Windows not identified.", ((OsEvent) event).isWindows());
    ***REMOVED***

    public void testCentOs() {
        String logLine = "OS:CentOS Linux release 7.7.1908 (Core)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("OS type not correct.", OsType.Linux, ((OsEvent) event).getOsType());
        Assert.assertEquals("OS vendor not correct.", OsVendor.CentOS, ((OsEvent) event).getOsVendor());
        Assert.assertEquals("OS version not correct.", OsVersion.CENTOS7, ((OsEvent) event).getOsVersion());
    ***REMOVED***
***REMOVED***

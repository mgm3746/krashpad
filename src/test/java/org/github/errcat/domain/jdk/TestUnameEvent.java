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
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestUnameEvent extends TestCase {

    public void testIdentity() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof UnameEvent);
    ***REMOVED***

    public void testSolaris() {
        String logLine = "uname:SunOS 5.11 11.4.23.69.3 sun4v";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.SPARC, ((UnameEvent) event).getArch());
        Assert.assertEquals("OS type not correct.", OsType.SOLARIS, ((UnameEvent) event).getOsType());
    ***REMOVED***

    public void testRhel7() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((UnameEvent) event).getArch());
        Assert.assertEquals("Version not correct.", OsType.LINUX, ((UnameEvent) event).getOsType());
        Assert.assertEquals("Vendor not correct.", OsVendor.REDHAT, ((UnameEvent) event).getOsVendor());
        Assert.assertEquals("Version not correct.", OsVersion.RHEL7, ((UnameEvent) event).getOsVersion());
    ***REMOVED***

    public void testRhel7Ppc64le() {
        String logLine = "uname:Linux 3.10.0-862.9.1.el7.ppc64le ***REMOVED***1 SMP Wed Jun 27 08:33:42 UTC 2018 ppc64le";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.PPC64LE, ((UnameEvent) event).getArch());
        Assert.assertEquals("Version not correct.", OsType.LINUX, ((UnameEvent) event).getOsType());
        Assert.assertEquals("Vendor not correct.", OsVendor.REDHAT, ((UnameEvent) event).getOsVendor());
        Assert.assertEquals("Version not correct.", OsVersion.RHEL7, ((UnameEvent) event).getOsVersion());
    ***REMOVED***

    public void testRhel8() {
        String logLine = "uname:Linux 4.18.0-193.14.3.el8_2.x86_64 ***REMOVED***1 SMP Mon Jul 20 15:02:29 UTC 2020 x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((UnameEvent) event).getArch());
        Assert.assertEquals("Version not correct.", OsType.LINUX, ((UnameEvent) event).getOsType());
        Assert.assertEquals("Vendor not correct.", OsVendor.REDHAT, ((UnameEvent) event).getOsVendor());
        Assert.assertEquals("Version not correct.", OsVersion.RHEL8, ((UnameEvent) event).getOsVersion());
    ***REMOVED***

    public void testI686Pc() {
        String logLine = "uname:SunOS 5.11 11.3 i86pc  (T2 libthread)";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.I86PC, ((UnameEvent) event).getArch());
        Assert.assertEquals("Version not correct.", OsType.SOLARIS, ((UnameEvent) event).getOsType());
        Assert.assertEquals("Vendor not correct.", OsVendor.ORACLE, ((UnameEvent) event).getOsVendor());
    ***REMOVED***
***REMOVED***

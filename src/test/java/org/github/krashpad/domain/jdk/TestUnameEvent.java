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
package org.github.krashpad.domain.jdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.Constants.OsType;
import org.github.krashpad.util.Constants.OsVendor;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.Arch;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestUnameEvent {

    @Test
    void testIdentity() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 #1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 #1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof UnameEvent,
                JdkUtil.LogEventType.OS.toString() + " not parsed.");
    }

    @Test
    void testSolaris() {
        String logLine = "uname:SunOS 5.11 11.4.23.69.3 sun4v";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.SPARC, ((UnameEvent) event).getArch(), "Arch not correct.");
        assertEquals(OsType.SOLARIS, ((UnameEvent) event).getOsType(), "OS type not correct.");
    }

    @Test
    void testRhel7() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 #1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.X86_64, ((UnameEvent) event).getArch(), "Arch not correct.");
        assertEquals(OsType.LINUX, ((UnameEvent) event).getOsType(), "Version not correct.");
        assertEquals(OsVendor.REDHAT, ((UnameEvent) event).getOsVendor(), "Vendor not correct.");
        assertEquals(OsVersion.RHEL7, ((UnameEvent) event).getOsVersion(), "Version not correct.");
    }

    @Test
    void testRhel7Ppc64() {
        String logLine = "uname:Linux 3.10.0-693.el7.ppc64 #1 SMP Thu Jul 6 20:01:28 EDT 2017 ppc64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.PPC64, ((UnameEvent) event).getArch(), "Arch not correct.");
        assertEquals(OsType.LINUX, ((UnameEvent) event).getOsType(), "Version not correct.");
        assertEquals(OsVendor.REDHAT, ((UnameEvent) event).getOsVendor(), "Vendor not correct.");
        assertEquals(OsVersion.RHEL7, ((UnameEvent) event).getOsVersion(), "Version not correct.");
    }

    @Test
    void testRhel7Ppc64le() {
        String logLine = "uname:Linux 3.10.0-862.9.1.el7.ppc64le #1 SMP Wed Jun 27 08:33:42 UTC 2018 ppc64le";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.PPC64LE, ((UnameEvent) event).getArch(), "Arch not correct.");
        assertEquals(OsType.LINUX, ((UnameEvent) event).getOsType(), "Version not correct.");
        assertEquals(OsVendor.REDHAT, ((UnameEvent) event).getOsVendor(), "Vendor not correct.");
        assertEquals(OsVersion.RHEL7, ((UnameEvent) event).getOsVersion(), "Version not correct.");
    }

    @Test
    void testRhel8() {
        String logLine = "uname:Linux 4.18.0-193.14.3.el8_2.x86_64 #1 SMP Mon Jul 20 15:02:29 UTC 2020 x86_64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.X86_64, ((UnameEvent) event).getArch(), "Arch not correct.");
        assertEquals(OsType.LINUX, ((UnameEvent) event).getOsType(), "Version not correct.");
        assertEquals(OsVendor.REDHAT, ((UnameEvent) event).getOsVendor(), "Vendor not correct.");
        assertEquals(OsVersion.RHEL8, ((UnameEvent) event).getOsVersion(), "Version not correct.");
    }

    @Test
    void testI686Pc() {
        String logLine = "uname:SunOS 5.11 11.3 i86pc  (T2 libthread)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UNAME,
                JdkUtil.LogEventType.UNAME.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.I86PC, ((UnameEvent) event).getArch(), "Arch not correct.");
        assertEquals(OsType.SOLARIS, ((UnameEvent) event).getOsType(), "Version not correct.");
        assertEquals(OsVendor.ORACLE, ((UnameEvent) event).getOsVendor(), "Vendor not correct.");
    }
}

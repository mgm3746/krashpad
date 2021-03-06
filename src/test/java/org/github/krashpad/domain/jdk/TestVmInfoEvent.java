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

import java.util.Calendar;
import java.util.Date;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.Arch;
import org.github.krashpad.util.jdk.JdkUtil.BuiltBy;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestVmInfoEvent {

    @Test
    void testIdentity() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
    }

    @Test
    void testIdentifyZulu() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmInfoEvent,
                JdkUtil.LogEventType.VM_INFO.toString() + " not parsed.");
    }

    @Test
    void testJavaSpecification8() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(JavaSpecification.JDK8, ((VmInfoEvent) event).getJavaSpecification(), "Version not correct.");
    }

    @Test
    void testJavaSpecification11() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(JavaSpecification.JDK11, ((VmInfoEvent) event).getJavaSpecification(), "Version not correct.");
    }

    @Test
    void testJavaSpecification12() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (12.0.1+12) for linux-amd64 JRE (12.0.1+12), "
                + "built on Apr  1 2019 23:46:56 by \"mach5one\" with gcc 7.3.0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(JavaSpecification.JDK12, ((VmInfoEvent) event).getJavaSpecification(), "Version not correct.");
    }

    @Test
    void testJdkReleaseString() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals("11.0.5+10-LTS", ((VmInfoEvent) event).getJdkReleaseString(), "Version not correct.");
    }

    @Test
    void testArchX86_64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.X86_64, ((VmInfoEvent) event).getArch(), "Arch not correct.");
    }

    @Test
    void testArchZuluX86_64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.X86_64, ((VmInfoEvent) event).getArch(), "Arch not correct.");
    }

    @Test
    void testArchPpc64Le() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-ppc64le JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 11:16:00 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.PPC64LE, ((VmInfoEvent) event).getArch(), "Arch not correct.");
    }

    @Test
    void testArchPpc64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.181-b13) for linux-ppc64 JRE (1.8.0_181-b13), "
                + "built on Jul 16 2018 11:33:43 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-28)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.PPC64, ((VmInfoEvent) event).getArch(), "Arch not correct.");
    }

    @Test
    void testBuildDate() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), "
                + "built on Jul 12 2020 18:55:08 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        Date buildDate = ((VmInfoEvent) event).getBuildDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(buildDate);
        // Java Calendar month is 0 based
        assertEquals(6, calendar.get(Calendar.MONTH), "Start month not parsed correctly.");
        assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH), "Start day not parsed correctly.");
        assertEquals(2020, calendar.get(Calendar.YEAR), "Start year not parsed correctly.");
        assertEquals(18, calendar.get(Calendar.HOUR_OF_DAY), "Start hour not parsed correctly.");
        assertEquals(55, calendar.get(Calendar.MINUTE), "Start minute not parsed correctly.");
        assertEquals(8, calendar.get(Calendar.SECOND), "Start second not parsed correctly.");
    }

    @Test
    void testWindowsOracleJdk8() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.25-b02) for windows-amd64 JRE (1.8.0_25-b18), "
                + "built on Oct  7 2014 14:25:37 by \"java_re\" with MS VC++ 10.0 (VS2010)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not parsed.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(Arch.X86_64, ((VmInfoEvent) event).getArch(), "Arch not correct.");
        assertEquals(JavaSpecification.JDK8, ((VmInfoEvent) event).getJavaSpecification(), "Version not correct.");
    }

    @Test
    void testBuiltByJenkins() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.JENKINS, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
    }

    @Test
    void testBuiltByZuluRe() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.ZULU_RE, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
    }

    @Test
    void testBuiltByJavaRe() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.25-b02) for windows-amd64 JRE (1.8.0_25-b18), "
                + "built on Oct  7 2014 14:25:37 by \"java_re\" with MS VC++ 10.0 (VS2010)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.JAVA_RE, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
    }

    @Test
    void testBuiltByMockbuild() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-ppc64le JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 11:16:00 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.MOCKBUILD, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
    }

    @Test
    void testX86() {
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.MOCKBUILD, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
        assertEquals(Arch.X86, ((VmInfoEvent) event).getArch(), "Arch not correct.");
    }

    @Test
    void testSparc() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for solaris-sparc JRE "
                + "(1.8.0_231-b11), built on Oct  5 2019 10:35:34 by \"java_re\" with Sun Studio 12u1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.JAVA_RE, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
    }

    @Test
    void testMicrosoft() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.10+9) for linux-amd64 JRE (11.0.10+9), built on "
                + "Jan 22 2021 19:24:16 by \"vsts\" with gcc 7.3.0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_INFO,
                JdkUtil.LogEventType.VM_INFO.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals(BuiltBy.VSTS, ((VmInfoEvent) event).getBuiltBy(), "JDK builder not correct.");
    }
}

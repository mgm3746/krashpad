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

import java.util.Calendar;
import java.util.Date;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.BuiltBy;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestVmInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
    ***REMOVED***

    public void testIdentifyZulu() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof VmInfoEvent);
    ***REMOVED***

    public void testJavaSpecification8() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", JavaSpecification.JDK8,
                ((VmInfoEvent) event).getJavaSpecification());
    ***REMOVED***

    public void testJavaSpecification11() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", JavaSpecification.JDK11,
                ((VmInfoEvent) event).getJavaSpecification());
    ***REMOVED***

    public void testJavaSpecification12() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (12.0.1+12) for linux-amd64 JRE (12.0.1+12), "
                + "built on Apr  1 2019 23:46:56 by \"mach5one\" with gcc 7.3.0";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", JavaSpecification.JDK12,
                ((VmInfoEvent) event).getJavaSpecification());
    ***REMOVED***

    public void testJdkReleaseString() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", "11.0.5+10-LTS", ((VmInfoEvent) event).getJdkReleaseString());
    ***REMOVED***

    public void testArchX86_64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testArchZuluX86_64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testArchPpc64Le() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-ppc64le JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 11:16:00 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.PPC64LE, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testArchPpc64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.181-b13) for linux-ppc64 JRE (1.8.0_181-b13), "
                + "built on Jul 16 2018 11:33:43 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-28)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.PPC64, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testBuildDate() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), "
                + "built on Jul 12 2020 18:55:08 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Date buildDate = ((VmInfoEvent) event).getBuildDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(buildDate);
        // Java Calendar month is 0 based
        Assert.assertEquals("Start month not parsed correctly.", 6, calendar.get(Calendar.MONTH));
        Assert.assertEquals("Start day not parsed correctly.", 12, calendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals("Start year not parsed correctly.", 2020, calendar.get(Calendar.YEAR));
        Assert.assertEquals("Start hour not parsed correctly.", 18, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals("Start minute not parsed correctly.", 55, calendar.get(Calendar.MINUTE));
        Assert.assertEquals("Start second not parsed correctly.", 8, calendar.get(Calendar.SECOND));
    ***REMOVED***

    public void testWindowsOracleJdk8() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.25-b02) for windows-amd64 JRE (1.8.0_25-b18), "
                + "built on Oct  7 2014 14:25:37 by \"java_re\" with MS VC++ 10.0 (VS2010)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((VmInfoEvent) event).getArch());
        Assert.assertEquals("Version not correct.", JavaSpecification.JDK8,
                ((VmInfoEvent) event).getJavaSpecification());
    ***REMOVED***

    public void testBuiltByJenkins() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("JDK builder not correct.", BuiltBy.JENKINS, ((VmInfoEvent) event).getBuiltBy());
    ***REMOVED***

    public void testBuiltByZuluRe() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("JDK builder not correct.", BuiltBy.ZULU_RE, ((VmInfoEvent) event).getBuiltBy());
    ***REMOVED***

    public void testBuiltByJavaRe() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.25-b02) for windows-amd64 JRE (1.8.0_25-b18), "
                + "built on Oct  7 2014 14:25:37 by \"java_re\" with MS VC++ 10.0 (VS2010)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("JDK builder not correct.", BuiltBy.JAVA_RE, ((VmInfoEvent) event).getBuiltBy());
    ***REMOVED***

    public void testBuiltByMockbuild() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-ppc64le JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 11:16:00 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("JDK builder not correct.", BuiltBy.MOCKBUILD, ((VmInfoEvent) event).getBuiltBy());
    ***REMOVED***

    public void testX86() {
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("JDK builder not correct.", BuiltBy.MOCKBUILD, ((VmInfoEvent) event).getBuiltBy());
        Assert.assertEquals("Arch not correct.", Arch.X86, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testSparc() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for solaris-sparc JRE "
                + "(1.8.0_231-b11), built on Oct  5 2019 10:35:34 by \"java_re\" with Sun Studio 12u1";
        Assert.assertTrue(JdkUtil.LogEventType.VM_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_INFO);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("JDK builder not correct.", BuiltBy.JAVA_RE, ((VmInfoEvent) event).getBuiltBy());
    ***REMOVED***
***REMOVED***

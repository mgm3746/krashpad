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
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.github.errcat.util.jdk.JdkUtil.JavaVendor;
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
        Assert.assertTrue(JdkUtil.LogEventType.JVM_INFO.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.JVM_INFO);
    ***REMOVED***

    public void testIdentifyZulu() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        Assert.assertTrue(JdkUtil.LogEventType.JVM_INFO.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.JVM_INFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        Assert.assertTrue(JdkUtil.LogEventType.JVM_INFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof VmInfoEvent);
    ***REMOVED***

    public void testJavaSpecification8() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", JavaSpecification.JDK8,
                ((VmInfoEvent) event).getJavaSpecification());
    ***REMOVED***

    public void testJavaSpecification11() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", JavaSpecification.JDK11,
                ((VmInfoEvent) event).getJavaSpecification());
    ***REMOVED***

    public void testJdkReleaseString() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", "11.0.5+10-LTS", ((VmInfoEvent) event).getJdkReleaseString());
    ***REMOVED***

    public void testArchX86_64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testArchZuluX86_64() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.X86_64, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testArchPpc64Le() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-ppc64le JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 11:16:00 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", Arch.PPC64LE, ((VmInfoEvent) event).getArch());
    ***REMOVED***

    public void testVendorOpenJdk() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), "
                + "built on Jul 12 2020 18:55:08 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", JavaVendor.OpenJDK, ((VmInfoEvent) event).getJavaVendor());
    ***REMOVED***

    public void testVendorAzul() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Arch not correct.", JavaVendor.Azul, ((VmInfoEvent) event).getJavaVendor());
    ***REMOVED***
***REMOVED***

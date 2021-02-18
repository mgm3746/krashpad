/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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

import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestDynamicLibraryEvent extends TestCase {

    public void testIdentity() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof DynamicLibraryEvent);
    ***REMOVED***

    public void testLogLineAllData() {
        String logLine = "7f95d7f78000-7f95d7f79000 r--p 00015000 fd:0d 134366667                  "
                + "/path/to/jdk/jre/lib/amd64/libnet.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testLogLineMemoryRangeLength8() {
        String logLine = "00400000-00401000 r-xp 00000000 fd:0d 201327127                          "
                + "/path/to/jdk/bin/java";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testLogLineMemoryRangeLength16() {
        String logLine = "ffffffffff600000-ffffffffff601000 r-xp 00000000 00:00 0                  [vsyscall]";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testLogLineNoFile() {
        String logLine = "6c0000000-7c1ab0000 rw-p 00000000 00:00 0 ";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testFilePathAmd64() {
        String logLine = "7f908ba68000-7f908c80e000 r-xp 00000000 fd:0a 140891                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        Assert.assertEquals("File path not correct.",
                "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath());
        Assert.assertEquals("Device not correct.", Device.FIXED_DISK, event.getDevice());
    ***REMOVED***

    public void testFilePathPpc64le() {
        String logLine = "7fff99cf0000-7fff9a950000 r-xp 00000000 fd:00 1275071932                 "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        Assert.assertEquals("File path not correct.",
                "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so",
                event.getFilePath());
        Assert.assertEquals("Device not correct.", Device.FIXED_DISK, event.getDevice());
    ***REMOVED***

    public void testJdk11() {
        String logLine = "7fd072161000-7fd0733ae000 r-xp 00000000 fd:00 10120317                   "
                + "/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        Assert.assertEquals("File path not correct.",
                "/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so", event.getFilePath());
        Assert.assertEquals("Device not correct.", Device.FIXED_DISK, event.getDevice());
    ***REMOVED***

    public void testAws() {
        String logLine = "7ffafebb0000-7ffafedb0000 ---p 00d91000 103:03 51649                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        Assert.assertEquals("File path not correct.",
                "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath());
        Assert.assertEquals("Device not correct.", Device.AWS_BLOCK_STORAGE, event.getDevice());
    ***REMOVED***

    public void testSolarisMemoryRegionAndFileOnly() {
        String logLine = "0xffffffff49400000      "
                + "/apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        Assert.assertEquals("File path not correct.",
                "/apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so", event.getFilePath());
    ***REMOVED***

    public void testDbghelp() {
        String logLine = "dbghelp: loaded successfully - version: 4.0.5 - missing functions: none";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testSymbolEngine() {
        String logLine = "symbol engine: initialized successfully - sym options: 0x614 - pdb path: .;"
                + "C:\\Program Files\\Java\\java-11-openjdk-11.0.7-1\\bin;C:\\windows\\SYSTEM32;"
                + "C:\\windows\\WinSxS\\amd64_microsoft.windows.common-controls_6595b64144ccf1df_"
                + "6.0.14393.3053_none_7de042968342015d;C:\\Program Files\\McAfee\\Endpoint Security\\Threat "
                + "Prevention\\Ips;C:\\Program Files\\Java\\java-11-openjdk-11.0.7-1\\bin\\serve";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***

    public void testAwsBlockStorage() {
        String logLine = "7ffafde1f000-7ffafebb0000 r-xp 00000000 103:03 51649                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        Assert.assertTrue(JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.DYNAMIC_LIBRARY);
    ***REMOVED***
***REMOVED***
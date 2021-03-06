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

import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestDynamicLibraryEvent {

    @Test
    void testIdentity() {
        String logLine = "Dynamic libraries:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Dynamic libraries:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof DynamicLibraryEvent,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not parsed.");
    }

    @Test
    void testLogLineAllData() {
        String logLine = "7f95d7f78000-7f95d7f79000 r--p 00015000 fd:0d 134366667                  "
                + "/path/to/jdk/jre/lib/amd64/libnet.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testLogLineMemoryRangeLength8() {
        String logLine = "00400000-00401000 r-xp 00000000 fd:0d 201327127                          "
                + "/path/to/jdk/bin/java";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testLogLineMemoryRangeLength16() {
        String logLine = "ffffffffff600000-ffffffffff601000 r-xp 00000000 00:00 0                  [vsyscall]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testLogLineNoFile() {
        String logLine = "6c0000000-7c1ab0000 rw-p 00000000 00:00 0 ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testFilePathAmd64() {
        String logLine = "7f908ba68000-7f908c80e000 r-xp 00000000 fd:0a 140891                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
    }

    @Test
    void testFilePathPpc64le() {
        String logLine = "7fff99cf0000-7fff9a950000 r-xp 00000000 fd:00 1275071932                 "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
    }

    @Test
    void testJdk11() {
        String logLine = "7fd072161000-7fd0733ae000 r-xp 00000000 fd:00 10120317                   "
                + "/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
    }

    @Test
    void testAws() {
        String logLine = "7ffafebb0000-7ffafedb0000 ---p 00d91000 103:03 51649                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.AWS_BLOCK_STORAGE, event.getDevice(), "Device not correct.");
    }

    @Test
    void testNfs() {
        String logLine = "7f6d359b3000-7f6d3669b000 r-xp 00000000 00:3d 2629821830"
                + "                 /tools/java/jdk1.8.0_201/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/tools/java/jdk1.8.0_201/jre/lib/amd64/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.NFS, event.getDevice(), "Device not correct.");
    }

    @Test
    void testScsi() {
        String logLine = "7f8c138c3000-7f8c1459f000 r-xp 00000000 08:01 772476                     "
                + "/apps/java/jdk1.8.0_181/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/apps/java/jdk1.8.0_181/jre/lib/amd64/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.SCSI_DISK, event.getDevice(), "Device not correct.");
    }

    @Test
    void testSolarisMemoryRegionAndFileOnly() {
        String logLine = "0xffffffff49400000      "
                + "/apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        assertEquals("/apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
    }

    @Test
    void testDbghelp() {
        String logLine = "dbghelp: loaded successfully - version: 4.0.5 - missing functions: none";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testSymbolEngine() {
        String logLine = "symbol engine: initialized successfully - sym options: 0x614 - pdb path: .;"
                + "C:\\Program Files\\Java\\java-11-openjdk-11.0.7-1\\bin;C:\\windows\\SYSTEM32;"
                + "C:\\windows\\WinSxS\\amd64_microsoft.windows.common-controls_6595b64144ccf1df_"
                + "6.0.14393.3053_none_7de042968342015d;C:\\Program Files\\McAfee\\Endpoint Security\\Threat "
                + "Prevention\\Ips;C:\\Program Files\\Java\\java-11-openjdk-11.0.7-1\\bin\\serve";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testAwsBlockStorage() {
        String logLine = "7ffafde1f000-7ffafebb0000 r-xp 00000000 103:03 51649                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testCannotGetLibraryInformation() {
        String logLine = "Can not get library information for pid = 123456";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }
}

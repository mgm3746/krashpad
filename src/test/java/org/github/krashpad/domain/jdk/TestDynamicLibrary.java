/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestDynamicLibrary {

    @Test
    void testAnonHugepageDeleted() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7bf800000-800000000 rw-p 5bf800000 00:0f 7262262                         "
                + "/anon_hugepage (deleted)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testAreaAioDeleted() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f7b4d651000-7f7b4d692000 rw-s 00000000 00:12 145684686                  /[aio] (deleted)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testAws() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7ffafebb0000-7ffafedb0000 ---p 00d91000 103:03 51649                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.AWS_BLOCK_STORAGE, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testAwsBlockStorage10300() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f4fe5ad8000-7f4fe67b1000 r-xp 00000000 103:00 2361173                   "
                + "/data/jdk1.8.0_171/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/data/jdk1.8.0_171/jre/lib/amd64/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.AWS_BLOCK_STORAGE, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testAwsBlockStorage10303() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7ffafde1f000-7ffafebb0000 r-xp 00000000 103:03 51649                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.AWS_BLOCK_STORAGE, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testCannotGetLibraryInformation() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "Can not get library information for pid = 123456";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testDeviceId4Char() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f9c5d1f2000-7f9c5dec0000 r-xp 00000000 c7:c738 14627                    "
                + "/path/to/jre/jre1.8.0_121/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/path/to/jre/jre1.8.0_121/lib/amd64/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.UNIDENTIFIED, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testDll() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "0x0000000052380000 - 0x0000000052bda000         E:\\path\\java\\bin\\server\\jvm.dll";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("E:\\path\\java\\bin\\server\\jvm.dll", event.getFilePath(), "File path not correct.");
        assertEquals(Device.UNIDENTIFIED, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testDllTabPreceedingPath() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "0x00007ffd61b50000 - 0x00007ffd61d3d000\tC:\\Windows\\SYSTEM32\\ntdll.dll";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("C:\\Windows\\SYSTEM32\\ntdll.dll", event.getFilePath(), "File path not correct.");
        assertEquals(Device.UNIDENTIFIED, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testFilePathAmd64() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f908ba68000-7f908c80e000 r-xp 00000000 fd:0a 140891                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testFilePathParenthesis() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7fff99cf0000-7fff9a950000 r-xp 00000000 fd:00 1275071932                 "
                + "/path/to/myapp/tmp/vfs/deployment/deployment0123456789123456/myjar(abc).jar-abc123/myjar(abc).jar";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals(
                "/path/to/myapp/tmp/vfs/deployment/deployment0123456789123456/myjar(abc).jar-abc123/myjar(abc).jar",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
        assertFalse(event.isNativeLibrary(), "Native library incorrectly identified.");
        assertTrue(event.isJar(), "Jar not identified.");
    }

    @Test
    void testFilePathPpc64le() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7fff99cf0000-7fff9a950000 r-xp 00000000 fd:00 1275071932                 "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testFooter() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "Total number of mappings: 197";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "Dynamic libraries:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testInode19digit() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f8e356ea000-7f8e363e5000 r-xp 00000000 00:28 9605293506115416793        "
                + "/path/to/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/path/to/java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64/jre/lib/amd64/server/libjvm.so",
                event.getFilePath(), "File path not correct.");
        assertEquals(Device.NFS, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testIsInteresting() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f680ddd2001-7f680ded3001 rw-s 15abc000 00:29 4075807                    "
                + "/path/to/data/AAA.BBB.CCC.TEMP%420";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/path/to/data/AAA.BBB.CCC.TEMP%420", event.getFilePath(), "File path not correct.");
        assertTrue(event.isInteresting(), "Interesting native libary not identified.");
    }

    @Test
    void testJar() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f2a4f8bc000-7f2a4f8be000 r--s 00000000 08:02 6976                       /path/to/modules/"
                + "system/layers/base/.overlays/layer-base-jboss-eap-7.3.10.CP/org/wildfly/clustering/marshalling/"
                + "infinispan/main/wildfly-clustering-marshalling-infinispan-7.3.10.GA-redhat-00003.jar";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertTrue(event.isJar(), "Jar not identified.");
    }

    @Test
    void testJdk11() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7fd072161000-7fd0733ae000 r-xp 00000000 fd:00 10120317                   "
                + "/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.FIXED_DISK, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testJdkLibrary() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f95d7f78000-7f95d7f79000 r--p 00015000 fd:0d 134366667                  "
                + "/path/to/jdk/jre/lib/amd64/libnet.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testMappingsCount() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset90.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(181, fel.getDynamicLibrariesMappingCount(), "Mappings count not correct.");
        assertEquals(74, fel.getDynamicLibraries().size(), "Dynamic library count not correct.");
    }

    @Test
    void testMemfd() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7fefef549000-7fefefd00000 rw-s 00000000 00:05 1218110                    /memfd:gdk-wayland "
                + "(deleted)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testMemoryRangeLength16() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "ffffffffff600000-ffffffffff601000 r-xp 00000000 00:00 0                  [vsyscall]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testMemoryRangeLength8() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "00400000-00401000 r-xp 00000000 fd:0d 201327127                          "
                + "/path/to/jdk/bin/java";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testNativeLibraryDeleted() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7e89648c6000-7e89649c7000 r-xp 00000000 fd:08 11814549870                "
                + "/path/to/mylib-jni.so (deleted)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
        assertEquals("/path/to/mylib-jni.so", event.getFilePath(), "File path not correct.");
    }

    @Test
    void testNfs() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f6d359b3000-7f6d3669b000 r-xp 00000000 00:3d 2629821830"
                + "                 /tools/java/jdk1.8.0_201/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/tools/java/jdk1.8.0_201/jre/lib/amd64/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.NFS, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testNoFile() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7fefef349000-7fefef549000 rw-p 00000000 00:00 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testNoFileSpaceAtEnd() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7fefef349000-7fefef549000 rw-p 00000000 00:00 0 ";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Dynamic libraries:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof DynamicLibrary,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not parsed.");
    }

    @Test
    void testScsi() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7f8c138c3000-7f8c1459f000 r-xp 00000000 08:01 772476                     "
                + "/apps/java/jdk1.8.0_181/jre/lib/amd64/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/apps/java/jdk1.8.0_181/jre/lib/amd64/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.SCSI_DISK, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testSolarisMemoryRegionAndFileOnly() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "0xffffffff49400000      "
                + "/apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("/apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so", event.getFilePath(),
                "File path not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }

    @Test
    void testStack() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7ff34869c000-7ff34879a000 rw-p 00000000 00:00 0                          [stack:47945]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
    }

    @Test
    void testVdso() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "7ffc79d4f000-7ffc79d50000 r-xp 00000000 00:00 0                          [vdso]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("[vdso]", event.getFilePath(), "File path not correct.");
        assertEquals(Device.NFS, event.getDevice(), "Device not correct.");
        assertFalse(event.isNativeLibrary(), "Native library incorrectly identified.");
    }

    @Test
    void testWindowsShortFileName() {
        DynamicLibrary priorLogEvent = new DynamicLibrary(null);
        String logLine = "0x00007ffa89b60000 - 0x00007ffa89b89000         "
                + "C:\\Users\\MYUSER~1\\AppData\\Local\\Temp\\jna-1234\\jna5678.dll";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DYNAMIC_LIBRARY,
                JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString() + " not identified.");
        DynamicLibrary event = new DynamicLibrary(logLine);
        assertEquals("C:\\Users\\MYUSER~1\\AppData\\Local\\Temp\\jna-1234\\jna5678.dll", event.getFilePath(),
                "File path not correct.");
        assertEquals(Device.UNIDENTIFIED, event.getDevice(), "Device not correct.");
        assertTrue(event.isNativeLibrary(), "Native library not identified.");
    }
}

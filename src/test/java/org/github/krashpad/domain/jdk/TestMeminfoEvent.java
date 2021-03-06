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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestMeminfoEvent {

    @Test
    void testIdentity() {
        String logLine = "MemTotal:       65305448 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "MemTotal:       65305448 kB";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MeminfoEvent,
                JdkUtil.LogEventType.MEMINFO.toString() + " not parsed.");
    }

    @Test
    void testHeader() {
        String logLine = "/proc/meminfo:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testBuffers() {
        String logLine = "Buffers:          817980 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testCached() {
        String logLine = "Cached:          4248912 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSwapCached() {
        String logLine = "SwapCached:            0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testActive() {
        String logLine = "Active:         27699800 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testInactive() {
        String logLine = "Inactive:        1601588 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testUnevictable() {
        String logLine = "Unevictable:           0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testMlocked() {
        String logLine = "Mlocked:               0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testDirty() {
        String logLine = "Dirty:              1732 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testWriteback() {
        String logLine = "Writeback:             0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testAnonPages() {
        String logLine = "AnonPages:      24234268 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testMapped() {
        String logLine = "Mapped:           378036 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testShmem() {
        String logLine = "Shmem:             48464 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSlab() {
        String logLine = "Slab:            4564604 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSReclaimable() {
        String logLine = "SReclaimable:    4475112 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSUnreclaim() {
        String logLine = "SUnreclaim:        89492 k";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testKernelStack() {
        String logLine = "KernelStack:       67264 k";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testPageTables() {
        String logLine = "PageTables:        72112 k";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testNfs() {
        String logLine = "NFS_Unstable:          0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testVmallocTotal() {
        String logLine = "VmallocTotal:   34359738367 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testHugePages() {
        String logLine = "HugePages_Total:       0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testDirectMap() {
        String logLine = "DirectMap4k:        8192 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testMemAvailable() {
        String logLine = "MemAvailable:    9627712 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testPerCpu() {
        String logLine = "Percpu:             3584 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testKReclaimable() {
        String logLine = "KReclaimable:     241604 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }
}
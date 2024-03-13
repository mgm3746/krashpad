/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
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
class TestMeminfo {

    @Test
    void testActive() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Active:         27699800 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testAnonPages() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "AnonPages:      24234268 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testBuffers() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Buffers:          817980 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testCached() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Cached:          4248912 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testCommitLimit() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "CommitLimit:    13329920 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testCommitLimitAs() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Committed_AS:   13301540 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testDirectMap() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "DirectMap4k:        8192 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testDirty() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Dirty:              1732 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testFileHugePages() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "FileHugePages:         0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testFilePmdMapped() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "FilePmdMapped:         0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "/proc/meminfo:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testHugePages() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "HugePages_Total:       0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "MemTotal:       65305448 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testInactive() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Inactive:        1601588 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testKernelStack() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "KernelStack:       67264 k";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testKReclaimable() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "KReclaimable:     241604 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testMapped() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Mapped:           378036 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testMemAvailable() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "MemAvailable:    9627712 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testMlocked() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Mlocked:               0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testNfs() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "NFS_Unstable:          0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testPageTables() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "PageTables:        72112 k";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "MemTotal:       65305448 kB";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof Meminfo,
                JdkUtil.LogEventType.MEMINFO.toString() + " not parsed.");
    }

    @Test
    void testPerCpu() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Percpu:             3584 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSecPageTables() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "SecPageTables:         0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testShmem() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Shmem:             48464 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSlab() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Slab:            4564604 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSReclaimable() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "SReclaimable:    4475112 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSUnreclaim() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "SUnreclaim:        89492 k";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testSwapCached() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "SwapCached:            0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testUnevictable() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Unevictable:           0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testVmallocTotal() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "VmallocTotal:   34359738367 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testWriteback() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Writeback:             0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testZswap() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Zswap:                 0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }

    @Test
    void testZswapped() {
        Meminfo priorLogEvent = new Meminfo("/proc/meminfo:");
        String logLine = "Zswapped:              0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    }
}
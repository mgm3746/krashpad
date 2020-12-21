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

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestMeminfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "MemTotal:       65305448 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testParseLogLine() {
        String logLine = "MemTotal:       65305448 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof MeminfoEvent);
    }

    public void testHeader() {
        String logLine = "/proc/meminfo:";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testBuffers() {
        String logLine = "Buffers:          817980 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testCached() {
        String logLine = "Cached:          4248912 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testSwapCached() {
        String logLine = "SwapCached:            0 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testActive() {
        String logLine = "Active:         27699800 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testInactive() {
        String logLine = "Inactive:        1601588 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testUnevictable() {
        String logLine = "Unevictable:           0 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testMlocked() {
        String logLine = "Mlocked:               0 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testDirty() {
        String logLine = "Dirty:              1732 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testWriteback() {
        String logLine = "Writeback:             0 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testAnonPages() {
        String logLine = "AnonPages:      24234268 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testMapped() {
        String logLine = "Mapped:           378036 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testShmem() {
        String logLine = "Shmem:             48464 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testSlab() {
        String logLine = "Slab:            4564604 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testSReclaimable() {
        String logLine = "SReclaimable:    4475112 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testSUnreclaim() {
        String logLine = "SUnreclaim:        89492 k";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testKernelStack() {
        String logLine = "KernelStack:       67264 k";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testPageTables() {
        String logLine = "PageTables:        72112 k";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testNfs() {
        String logLine = "NFS_Unstable:          0 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testVmallocTotal() {
        String logLine = "VmallocTotal:   34359738367 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testHugePages() {
        String logLine = "HugePages_Total:       0";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testDirectMap() {
        String logLine = "DirectMap4k:        8192 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testMemAvailable() {
        String logLine = "MemAvailable:    9627712 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testPerCpu() {
        String logLine = "Percpu:             3584 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }

    public void testKReclaimable() {
        String logLine = "KReclaimable:     241604 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    }
}
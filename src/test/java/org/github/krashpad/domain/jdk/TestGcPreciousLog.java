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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestGcPreciousLog {

    @Test
    void testAddressSpaceSize() {
        String logLine = " Address Space Size: 16777216M x 3 = 50331648M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testAddressSpaceType() {
        String logLine = " Address Space Type: Contiguous/Unrestricted/Complete";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testAvailableSpaceOnBackingFilesystem() {
        String logLine = " Available space on backing filesystem: N/A";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCardSetContainerConfiguration() {
        String logLine = " Card Set container configuration: InlinePtr #cards 4 size 8 Array Of Cards #cards 32 size "
                + "80 Howl #buckets 8 coarsen threshold 7372 Howl Bitmap #cards 1024 size 144 coarsen threshold 921 "
                + "Card regions per heap region 1 cards per card region 8192";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCardTableEntrySize() {
        String logLine = " CardTable entry size: 512";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCompressedOops() {
        String logLine = " Compressed Oops: Enabled (Zero based)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testConcurrentRefinementWorkers() {
        String logLine = " Concurrent Refinement Workers: 10";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testConcurrentWorkers() {
        String logLine = " Concurrent Workers: 3";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCpus() {
        String logLine = " CPUs: 12 total, 12 available";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testEmpty() {
        String logLine = "<Empty>";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkers() {
        String logLine = " GC Workers: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkersForOldGeneration() {
        String logLine = " GC Workers for Old Generation: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkersForYoungGeneration() {
        String logLine = " GC Workers for Young Generation: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkersMax() {
        String logLine = " GC Workers Max: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapBackingFile() {
        String logLine = " Heap Backing File: /memfd:java_heap.hugetlb";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapBackingFilesystem() {
        String logLine = " Heap Backing Filesystem: hugetlbfs (0x958458f6)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapInitialCapacity() {
        String logLine = " Heap Initial Capacity: 500M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapMaxCapacity() {
        String logLine = " Heap Max Capacity: 7980M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapMinCapacity() {
        String logLine = " Heap Min Capacity: 8M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapRegionSize() {
        String logLine = " Heap Region Size: 4M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "GC Precious Log:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GC_PRECIOUS_LOG,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not identified.");
    }

    @Test
    void testInitialCapacity() {
        String logLine = " Initial Capacity: 471040M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testLargePageSupport() {
        String logLine = " Large Page Support: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMaxCapacity() {
        String logLine = " Max Capacity: 4710400M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMediumPageSize() {
        String logLine = " Medium Page Size: 32M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMemory() {
        String logLine = " Memory: 31907M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMinCapacity() {
        String logLine = " Min Capacity: 471040M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testNumaNodes() {
        String logLine = " NUMA Nodes: 4";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testNumaSupport() {
        String logLine = " NUMA Support: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testParallelWorkers() {
        String logLine = " Parallel Workers: 10";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "GC Precious Log:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testPeriodicGc() {
        String logLine = " Periodic GC: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testPreTouch() {
        String logLine = " Pre-touch: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testProbingAddressSpaceForTheHighestValidBit() {
        String logLine = " Probing address space for the highest valid bit: 47";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testRuntimeWorkers() {
        String logLine = " Runtime Workers: 135";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testSkipped() {
        String logLine = "<Skipped>";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testSoftMaxCapacity() {
        String logLine = " Soft Max Capacity: 860160M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testStringDeduplication() {
        String logLine = " String Deduplication is enabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testUncommit() {
        String logLine = " Uncommit: Implicitly Disabled (-Xms equals -Xmx)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningHeader() {
        String logLine = " ***** WARNING! INCORRECT SYSTEM CONFIGURATION DETECTED! *****";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine1() {
        String logLine = " The system limit on number of memory mappings per process might be too low for the given";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine2() {
        String logLine = " max Java heap size (1228800M). Please adjust /proc/sys/vm/max_map_count to allow for at";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine3() {
        String logLine = " least 2211840 mappings (current limit is 65530). Continuing execution with the current";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine4() {
        String logLine = " limit could lead to a premature OutOfMemoryError being thrown, due to failure to map "
                + "memory.";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }
}
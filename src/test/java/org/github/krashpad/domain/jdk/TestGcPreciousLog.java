/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Address Space Size: 16777216M x 3 = 50331648M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testAddressSpaceType() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Address Space Type: Contiguous/Unrestricted/Complete";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testAlignments() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Alignments: Space 512K, Generation 512K, Heap 2M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testAvailableSpaceOnBackingFilesystem() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Available space on backing filesystem: N/A";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCardSetContainerConfiguration() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Card Set container configuration: InlinePtr #cards 4 size 8 Array Of Cards #cards 32 size "
                + "80 Howl #buckets 8 coarsen threshold 7372 Howl Bitmap #cards 1024 size 144 coarsen threshold 921 "
                + "Card regions per heap region 1 cards per card region 8192";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCardTableEntrySize() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " CardTable entry size: 512";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testClassPointerCheck() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Class pointer check: Updated min and max addresses: 0x00007dfdb700e740 - 0x00007dfdb700e740 "
                + "CompressedKlassPointers::decode_raw result";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCompressedOops() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Compressed Oops: Enabled (Zero based)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testConcurrentRefinementWorkers() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Concurrent Refinement Workers: 10";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testConcurrentWorkers() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Concurrent Workers: 3";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testCpus() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " CPUs: 12 total, 12 available";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testEmpty() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = "<Empty>";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkers() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " GC Workers: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkersForOldGeneration() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " GC Workers for Old Generation: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkersForYoungGeneration() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " GC Workers for Young Generation: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testGcWorkersMax() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " GC Workers Max: 32 (dynamic)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapBackingFile() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Heap Backing File: /memfd:java_heap.hugetlb";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapBackingFilesystem() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Heap Backing Filesystem: hugetlbfs (0x958458f6)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapInitialCapacity() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Heap Initial Capacity: 500M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapMaxCapacity() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Heap Max Capacity: 7980M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapMinCapacity() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Heap Min Capacity: 8M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testHeapRegionSize() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Heap Region Size: 4M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
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
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Initial Capacity: 471040M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testLargePageSupport() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Large Page Support: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMaxCapacity() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Max Capacity: 4710400M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMediumPageSize() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Medium Page Size: 32M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMemory() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Memory: 31907M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testMinCapacity() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Min Capacity: 471040M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testNumaNodes() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " NUMA Nodes: 4";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testNumaSupport() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " NUMA Support: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testParallelWorkers() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Parallel Workers: 10";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = "GC Precious Log:";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testPeriodicGc() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Periodic GC: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testPreTouch() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Pre-touch: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testProbingAddressSpaceForTheHighestValidBit() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Probing address space for the highest valid bit: 47";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testRuntimeWorkers() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Runtime Workers: 135";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testSkipped() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = "<Skipped>";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testSoftMaxCapacity() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Soft Max Capacity: 860160M";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testStringDeduplication() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " String Deduplication is enabled";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testUncommit() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " Uncommit: Implicitly Disabled (-Xms equals -Xmx)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningHeader() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " ***** WARNING! INCORRECT SYSTEM CONFIGURATION DETECTED! *****";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine1() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " The system limit on number of memory mappings per process might be too low for the given";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine2() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " max Java heap size (1228800M). Please adjust /proc/sys/vm/max_map_count to allow for at";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine3() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " least 2211840 mappings (current limit is 65530). Continuing execution with the current";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }

    @Test
    void testWarningMaxMapCountLine4() {
        GcPreciousLog priorEvent = new GcPreciousLog(null);
        String logLine = " limit could lead to a premature OutOfMemoryError being thrown, due to failure to map "
                + "memory.";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcPreciousLog,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    }
}
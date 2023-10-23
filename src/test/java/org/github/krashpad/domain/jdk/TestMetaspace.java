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
class TestMetaspace {

    @Test
    void testBotD() {
        String logLine = "        BotD:  47.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testBoth() {
        String logLine = "       Both:    154.76 MB capacity,   144.43 MB ( 93%) used,     9.90 MB (  6%) "
                + "free+waste,   448.81 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity2LeadingSpaces() {
        String logLine = "  414.40 MB capacity,   395.36 MB ( 95%) used,    17.73 MB (  4%) free+waste,     1.30 MB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity4LeadingSpaces() {
        String logLine = "    1.66 GB capacity,     1.35 GB ( 81%) used,   308.17 MB ( 18%) free+waste,     8.50 MB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity5Spaces() {
        String logLine = "  251.04 MB capacity,   241.20 MB ( 96%) used,     8.88 MB (  4%) free+waste,   988.88 KB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCdsOn() {
        String logLine = "CDS: on";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelists() {
        String logLine = "Chunk freelists:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelistsDataBytes() {
        String logLine = "0 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelistsDataKilobytes() {
        String logLine = "3.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testClass() {
        String logLine = "      Class:     17.93 MB capacity,    14.53 MB ( 81%) used,     3.26 MB ( 18%) "
                + "free+waste,   143.81 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCommitGranuelBytes() {
        String logLine = " - commit_granule_bytes: 65536.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCommitGranuelWords() {
        String logLine = " - commit_granule_words: 8192.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCompressedClassSpaceSize() {
        String logLine = "CompressedClassSpaceSize: 1.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCurrentGcThreshold() {
        String logLine = "Current GC threshold: 2.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testEnlargeChunksInPlace() {
        String logLine = " - enlarge_chunks_in_place: 1.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testHandleDeallocations() {
        String logLine = " - handle_deallocations: 1.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Metaspace:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "Usage:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testInitialGcThreshold() {
        String logLine = "Initial GC threshold: 2.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testMaxMetaspaceSize() {
        String logLine = "MaxMetaspaceSize: unlimited";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testMetasapceReclaimPolicy() {
        String logLine = "MetaspaceReclaimPolicy: balanced";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNewChunksAreFullyCommitted() {
        String logLine = " - new_chunks_are_fully_committed: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNoClassSpace() {
        String logLine = "No class space";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNonClass() {
        String logLine = "  Non-class:    136.84 MB capacity,   129.90 MB ( 95%) used,     6.64 MB (  5%) "
                + "free+waste,   305.00 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNonClassCapitalC() {
        String logLine = "   Non-Class:  507.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Usage:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Metaspace,
                JdkUtil.LogEventType.METASPACE.toString() + " not parsed.");
    }

    @Test
    void testReserved() {
        String logLine = "    416.00 MB reserved,     414.50 MB (>99%) committed";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testReservedCommitted100() {
        String logLine = "    460.00 MB reserved,     460.00 MB (100%) committed";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testUncommitFreeChunks() {
        String logLine = " - uncommit_free_chunks: 1.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testUseAllocationGuard() {
        String logLine = " - use_allocation_guard: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testVirtualSpace() {
        String logLine = "Virtual space:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testVirtualSpaceNodeDefaultSize() {
        String logLine = " - virtual_space_node_default_size: 1048576.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }
}

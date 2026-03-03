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
class TestMetaspace {

    @Test
    void testBotD() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "        BotD:  47.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testBoth() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "       Both:    154.76 MB capacity,   144.43 MB ( 93%) used,     9.90 MB (  6%) "
                + "free+waste,   448.81 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "    1.74 GB capacity,     1.06 GB ( 61%) used,   677.69 MB ( 38%) free+waste,    24.97 MB "
                + "(  1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity2LeadingSpaces() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "  414.40 MB capacity,   395.36 MB ( 95%) used,    17.73 MB (  4%) free+waste,     1.30 MB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity4LeadingSpaces() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "    1.66 GB capacity,     1.35 GB ( 81%) used,   308.17 MB ( 18%) free+waste,     8.50 MB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCapacity5Spaces() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "  251.04 MB capacity,   241.20 MB ( 96%) used,     8.88 MB (  4%) free+waste,   988.88 KB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCdsOn() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "CDS: on";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelist() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Chunk freelists:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelists() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Chunk freelists:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelistsDataBytes() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "0 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testChunkFreelistsDataKilobytes() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "3.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testClass() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "      Class:     17.93 MB capacity,    14.53 MB ( 81%) used,     3.26 MB ( 18%) "
                + "free+waste,   143.81 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testClassSpace() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " class space     used 25K, committed 128K, reserved 1048576K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCommitGranuelBytes() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - commit_granule_bytes: 65536.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCommitGranuelWords() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - commit_granule_words: 8192.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCompressedClassSpaceSize() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "CompressedClassSpaceSize: 1.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testCurrentGcThreshold() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Current GC threshold: 2.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testEnlargeChunksInPlace() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - enlarge_chunks_in_place: 1.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testHandleDeallocations() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - handle_deallocations: 1.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
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
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Usage:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testInitialGcThreshold() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Initial GC threshold: 2.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testMaxMetaspaceSize() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "MaxMetaspaceSize: unlimited";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testMetasapceReclaimPolicy() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "MetaspaceReclaimPolicy: balanced";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testMetaspace() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Metaspace        used 374K, committed 576K, reserved 1114112K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNewChunksAreFullyCommitted() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - new_chunks_are_fully_committed: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNoClassSpace() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "No class space";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNonClass() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "  Non-class:    136.84 MB capacity,   129.90 MB ( 95%) used,     6.64 MB (  5%) "
                + "free+waste,   305.00 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testNonClassCapitalC() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "   Non-Class:  507.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Usage:";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof Metaspace,
                JdkUtil.LogEventType.METASPACE.toString() + " not parsed.");
    }

    @Test
    void testReserved() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "    416.00 MB reserved,     414.50 MB (>99%) committed";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testReservedCommitted100() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "    460.00 MB reserved,     460.00 MB (100%) committed";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testUncommitFreeChunks() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - uncommit_free_chunks: 1.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testUsageDetails() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "  263.14 MB capacity,   260.61 MB (>99%) used,     1.76 MB ( <1%) free+waste,   791.06 KB "
                + "( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testVirtualSpace() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = "Virtual space:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }

    @Test
    void testVirtualSpaceNodeDefaultSize() {
        Metaspace priorEvent = new Metaspace(null);
        String logLine = " - virtual_space_node_default_size: 1048576.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    }
}

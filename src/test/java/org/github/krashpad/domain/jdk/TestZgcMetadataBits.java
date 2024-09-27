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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestZgcMetadataBits {

    @Test
    void testBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Bad:               0x0000b00000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testGood() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Good:              0x0000400000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "ZGC Metadata Bits:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ZGC_METADATA_BITS,
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testLoadBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " LoadBad:            0x0000000000001000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testLoadGood() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " LoadGood:           0x000000000000e000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testMarkBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " MarkBad:            0x00000000000015c0";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testMarked() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Marked:            0x0000100000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testMarkedOld() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " MarkedOld:          0x0000000000000800";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testMarkedYoung() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " MarkedYoung:        0x0000000000000200";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testMarkGood() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " MarkGood:           0x000000000000ea00";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "ZGC Metadata Bits:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ZgcMetadataBits,
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not parsed.");
    }

    @Test
    void testRemapped() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Remapped:          0x0000400000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testRemappedOld() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " RemappedOld:        0x0000000000003000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testRemappedYoung() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " RemappedYoung:      0x0000000000005000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testRemembered() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Remembered:         0x0000000000000020";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testStoreBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " StoreBad:           0x00000000000015d0";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testStoreGood() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " StoreGood:          0x000000000000ea20";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

    @Test
    void testWeakBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " WeakBad:           0x0000300000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

}
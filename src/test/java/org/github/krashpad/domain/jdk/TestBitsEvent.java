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
class TestBitsEvent {

    @Test
    void testBeginBits() {
        String logLine = " Begin Bits: [0x00007f45d8c22000, 0x00007f45d9422000)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testEndBits() {
        String logLine = " End Bits:   [0x00007f45d9422000, 0x00007f45d9c22000)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testHeaderMarkingBits() {
        String logLine = "Marking Bits: (CMSBitMap*) 0x00007fcbc8249ce8";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testHeaderModUnionTable() {
        String logLine = "Mod Union Table: (CMSBitMap*) 0x00007fcbc8249da8";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testHeaderPrevNext() {
        String logLine = "Marking Bits (Prev, Next): (CMBitMap*) 0x00003fff74037098, (CMBitMap*) 0x00003fff740370f0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = " Bits: [0x00007f677d83f000, 0x00007f6900a58c00)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testNextBits() {
        String logLine = " Next Bits: [0x00003fff3c000000, 0x00003fff44000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = " Bits: [0x00007f677d83f000, 0x00007f6900a58c00)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof BitsEvent,
                JdkUtil.LogEventType.BITS.toString() + " not parsed.");
    }

    @Test
    void testPrevBits() {
        String logLine = " Prev Bits: [0x00003fff44000000, 0x00003fff4c000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.BITS,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    }

}
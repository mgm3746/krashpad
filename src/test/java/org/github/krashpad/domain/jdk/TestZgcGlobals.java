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
class TestZgcGlobals {

    @Test
    void testGlobalPhase() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " GlobalPhase:       2 (Relocate)";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testGlobalSeqNum() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " GlobalSeqNum:      753";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "ZGC Globals:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ZGC_GLOBALS,
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testOffsetMax() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " Offset Max:        16384G (0x0000100000000000)";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testOldCollection() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " Old Collection:     Mark/10";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testPageSizeMedium() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " Page Size Medium:  32M";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testPageSizeSmall() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " Page Size Small:   2M";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "ZGC Globals:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ZgcGlobals,
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not parsed.");
    }

    @Test
    void testYoungCollection() {
        ZgcGlobals priorLogEvent = new ZgcGlobals("ZGC Globals:");
        String logLine = " Young Collection:   Mark/138";
        assertEquals(JdkUtil.LogEventType.ZGC_GLOBALS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_GLOBALS.toString() + " not identified.");
    }

}
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestZgcPageTable {

    @Test
    void testIdentity() {
        String logLine = "ZGC Page Table:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ZGC_PAGE_TABLE,
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not identified.");
    }

    @Test
    void testLargeRelocatable() {
        ZgcPageTable priorLogEvent = new ZgcPageTable("ZGC Page Table:");
        String logLine = " Large   0x00000001e9200000 0x0000000200000000 0x0000000200000000  Relocatable";
        assertEquals(JdkUtil.LogEventType.ZGC_PAGE_TABLE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not identified.");
    }

    @Test
    void testMediumRelocatable() {
        ZgcPageTable priorLogEvent = new ZgcPageTable("ZGC Page Table:");
        String logLine = " Medium  0x0000000f3dc00000 0x0000000f3fbea000 0x0000000f3fc00000  Relocatable";
        assertEquals(JdkUtil.LogEventType.ZGC_PAGE_TABLE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "ZGC Page Table:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ZgcPageTable,
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not parsed.");
    }

    @Test
    void testSmallAllocating() {
        ZgcPageTable priorLogEvent = new ZgcPageTable("ZGC Page Table:");
        String logLine = " Small   0x000000001de00000 0x000000001e000000 0x000000001e000000  Allocating";
        assertEquals(JdkUtil.LogEventType.ZGC_PAGE_TABLE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not identified.");
    }
    
    @Test
    void testSmallRelocatable() {
        ZgcPageTable priorLogEvent = new ZgcPageTable("ZGC Page Table:");
        String logLine = " Small   0x0000000007200000 0x00000000073fffa8 0x0000000007400000  Relocatable";
        assertEquals(JdkUtil.LogEventType.ZGC_PAGE_TABLE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not identified.");
    }

    @Test
    void testZBarrierSet() {
        ZgcPageTable priorLogEvent = new ZgcPageTable("ZGC Page Table:");
        String logLine = "ZBarrierSet";
        assertEquals(JdkUtil.LogEventType.ZGC_PAGE_TABLE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_PAGE_TABLE.toString() + " not identified.");
    }
}
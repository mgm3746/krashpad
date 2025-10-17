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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestMaxMapCount {

    @Test
    void testIdentity() {
        String logLine = "/proc/sys/vm/max_map_count (maximum number of memory map areas a process may have):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MAX_MAP_COUNT,
                JdkUtil.LogEventType.MAX_MAP_COUNT.toString() + " not identified.");
    }

    @Test
    void testJdk11() {
        MaxMapCount priorEvent = new MaxMapCount("");
        String logLine = "65530";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.MAX_MAP_COUNT,
                JdkUtil.LogEventType.MAX_MAP_COUNT.toString() + " not identified.");
        MaxMapCount event = new MaxMapCount(logLine);
        assertEquals(65530L, event.getLimit(), "max_map_count not correct.");
    }

    @Test
    void testJdk17() {
        MaxMapCount priorEvent = new MaxMapCount("");
        String logLine = "/proc/sys/vm/max_map_count (maximum number of memory map areas a process may have): 65530";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.MAX_MAP_COUNT,
                JdkUtil.LogEventType.MAX_MAP_COUNT.toString() + " not identified.");
        MaxMapCount event = new MaxMapCount(logLine);
        assertEquals(65530L, event.getLimit(), "max_map_count not correct.");
    }

    @Test
    void testNotAvailable() {
        MaxMapCount priorEvent = new MaxMapCount("");
        String logLine = "/proc/sys/vm/max_map_count (maximum number of memory map areas a process may have): "
                + "<Not Available>";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.MAX_MAP_COUNT,
                JdkUtil.LogEventType.MAX_MAP_COUNT.toString() + " not identified.");
        MaxMapCount event = new MaxMapCount(logLine);
        assertEquals(Long.MIN_VALUE, event.getLimit(), "max_map_count not correct.");
    }

    @Test
    void testParseLogLineHeader() {
        String logLine = "/proc/sys/vm/max_map_count (maximum number of memory map areas a process may have):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MaxMapCount,
                JdkUtil.LogEventType.MAX_MAP_COUNT.toString() + " not parsed.");
    }

    @Test
    void testParseLogLineSingle() {
        String logLine = "/proc/sys/vm/max_map_count (maximum number of memory map areas a process may have): 65530";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MaxMapCount,
                JdkUtil.LogEventType.MAX_MAP_COUNT.toString() + " not parsed.");
    }
}
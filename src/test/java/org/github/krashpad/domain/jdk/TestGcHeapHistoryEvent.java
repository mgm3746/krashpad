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
class TestGcHeapHistoryEvent {

    @Test
    void testBraceHeapAfterGc() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "{Heap after GC invocations=10 (full 0):";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testClosingBrace() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testHeaderGcHeapHistory() {
        GcHeapHistoryEvent priorEvent = null;
        String logLine = "GC Heap History (48 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testHeapAfterGc() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "Heap after GC invocations=1 (full 0):";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testHeapAfterGcWithTimestamp() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "Event: 345.632 GC heap after";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testHeapBeforeGc() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "{Heap before GC invocations=1 (full 0):";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "Event: 1.905 GC heap before";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsUppercaseE() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "No Events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        GcHeapHistoryEvent priorEvent = new GcHeapHistoryEvent(null);
        String logLine = "Event: 1.905 GC heap before";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GcHeapHistoryEvent,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " not parsed.");
    }
}

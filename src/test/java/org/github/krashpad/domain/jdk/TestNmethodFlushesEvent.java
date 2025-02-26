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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestNmethodFlushesEvent {

    @Test
    void testEvent() {
        NmethodFlushesEvent priorLogEvent = new NmethodFlushesEvent("");
        String logLine = "Event: 39.992 Thread 0x00007f128cf57870 flushing  nmethod 0x00007f1235b2ae90";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT,
                JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "Nmethod flushes (20 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT,
                JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        NmethodFlushesEvent priorLogEvent = new NmethodFlushesEvent("");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT,
                JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Nmethod flushes (20 events):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof NmethodFlushesEvent,
                JdkUtil.LogEventType.NMETHOD_FLUSHES_EVENT.toString() + " not identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof MemoryProtectionEvent,
                JdkUtil.LogEventType.MEMORY_PROTECTION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ClassesUnloadedEvent,
                JdkUtil.LogEventType.CLASSES_UNLOADED_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof GcHeapHistoryEvent,
                JdkUtil.LogEventType.GC_HEAP_HISTORY_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof DllOperationEvent,
                JdkUtil.LogEventType.DLL_OPERATION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ClassesRedefinedEvent,
                JdkUtil.LogEventType.CLASSES_REDEFINED_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof InternalExceptionEvent,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof VmOperation,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof Event,
                JdkUtil.LogEventType.EVENT.toString() + " incorrectly identified.");
    }

}

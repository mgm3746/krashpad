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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestStackSlotToMemoryMapping {

    @Test
    void testBufferBlob() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "BufferBlob (0x00007f486c4e8f10) used for C1 temporary CodeBuffer";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testCodeBlob() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "[CodeBlob (0x00007f486c4e8f10)]";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testError() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "[error occurred during error reporting (inspecting top of stack), id 0xb, SIGSEGV (0xb) at "
                + "pc=0x00007f68376aea9e]";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFramesize() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "Framesize: 0";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Stack slot to memory mapping:";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING, JdkUtil.identifyEventType(logLine, null),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testLeadingCurlyBrace() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "{0x00007f104bc73728} - klass: 'java/util/concurrent/ConcurrentHashMap$Node'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testMethodEntryPoint() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "method entry point (kind = native)  [0x00007fa9b48141c0, 0x00007fa9b4814a20]  2144 bytes";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testObject() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "org.xnio.nio.WorkerThread {0x0000000800c89d28}";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }
}
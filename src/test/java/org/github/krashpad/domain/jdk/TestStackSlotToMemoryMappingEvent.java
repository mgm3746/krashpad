/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
class TestStackSlotToMemoryMappingEvent {

    @Test
    void testError() {
        String logLine = "[error occurred during error reporting (inspecting top of stack), id 0xb, SIGSEGV (0xb) at "
                + "pc=0x00007f68376aea9e]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Stack slot to memory mapping:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof StackSlotToMemoryMappingEvent,
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
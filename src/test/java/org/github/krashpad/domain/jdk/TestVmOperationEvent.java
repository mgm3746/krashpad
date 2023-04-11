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
class TestVmOperationEvent {

    @Test
    void testCleanup() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 31.306 Executing VM operation: Cleanup";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testHandshakeAllThreads() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 31.627 Executing VM operation: HandshakeAllThreads done";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testIcBufferFull() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 32.322 Executing VM operation: ICBufferFull done";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "VM Operations (0 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (0 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsUppercaseE() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (0 events):");
        String logLine = "No Events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "VM Operations (0 events):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }
}

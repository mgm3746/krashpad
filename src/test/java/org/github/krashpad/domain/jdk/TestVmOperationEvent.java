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
class TestVmOperationEvent {

    @Test
    void testBulkRevokeBias() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 738.677 Executing VM operation: BulkRevokeBias";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testClassLoaderStatsOperation() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 123.456 Executing VM operation: ClassLoaderStatsOperation";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testCleanClassLoaderDataMetaspaces() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 163.426 Executing VM operation: CleanClassLoaderDataMetaspaces";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testCleanup() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 31.306 Executing VM operation: Cleanup";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testFindDeadlocks() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 31.306 Executing VM operation: FindDeadlocks";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testG1CollectForAllocation() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 12.345 Executing VM operation: G1CollectForAllocation";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testG1CollectFull() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 123.105 Executing VM operation: G1CollectFull";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testG1PauseCleanupk() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 509237.156 Executing VM operation: G1PauseCleanup";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testG1PauseRemark() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 509236.987 Executing VM operation: G1PauseRemark";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testG1TryInitiateConcMark() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 42576.463 Executing VM operation: G1TryInitiateConcMark";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testGenCollectForAllocation() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 884.592 Executing VM operation: GenCollectForAllocation";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testGetAllStackTraces() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 70710.290 Executing VM operation: GetAllStackTraces";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testGetThreadListStackTraces() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 14.340 Executing VM operation: GetThreadListStackTraces";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testHandshakeAllThreads() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 31.627 Executing VM operation: HandshakeAllThreads done";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testHeapDumper() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 338630.102 Executing VM operation: HeapDumper";
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
        String logLine = "VM Operations (1 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testJfrCheckpoint() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 123.456 Executing VM operation: JFRCheckpoint";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsUppercaseE() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "No Events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testParallelGcFailedAllocation() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 7218.065 Executing VM operation: ParallelGCFailedAllocation";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testParallelGcSystemGc() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 5621.396 Executing VM operation: ParallelGCSystemGC";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "VM Operations (1 events):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testPrintJni() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 54166.258 Executing VM operation: PrintJNI";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testPrintThreads() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 54166.258 Executing VM operation: PrintThreads";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testRedefineClasses() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 102.736 Executing VM operation: RedefineClasses";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testRendezvousGcThreads() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 1862.245 Executing VM operation: RendezvousGCThreads";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testSetNotifyJvmtiEventsMode() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 2381475,680 Executing VM operation: SetNotifyJvmtiEventsMode";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testShenandoahFinalMarkAndStartEvacuation() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 35135.563 Executing VM operation: Shenandoah Final Mark and Start Evacuation";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testShenandoahFinalUpdateReferences() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 34923.372 Executing VM operation: Shenandoah Final Update References";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testShenandoahInitMarking() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 35134.676 Executing VM operation: Shenandoah Init Marking";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testShenandoahInitUpdateReferences() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 34922.982 Executing VM operation: Shenandoah Init Update References";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testThreadDump() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 1204492.867 Executing VM operation: ThreadDump";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testThreadDumpDone() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("Event: 1204492.867 Executing VM operation: ThreadDump");
        String logLine = "Event: 1204492.867 Executing VM operation: ThreadDump done";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testXMarkStart() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 8365.455 Executing VM operation: XMarkStart";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZMarkEnd() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 54166.258 Executing VM operation: ZMarkEnd";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZMarkEndOld() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 1855.373 Executing VM operation: ZMarkEndOld (Allocation Rate)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZMarkFlushOperation() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 1855.373 Executing VM operation: ZMarkFlushOperation";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZMarkStart() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 10.024 Executing VM operation: ZMarkStart";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZMarkStartYoung() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 56.164 Executing VM operation: ZMarkStartYoung (Allocation Rate)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZMarkStartYoungAndOld() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 1876.996 Executing VM operation: ZMarkStartYoungAndOld (Allocation Rate)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZRelocateStart() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (1 events):");
        String logLine = "Event: 54166.258 Executing VM operation: ZRelocateStart";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.VM_OPERATION_EVENT,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testZRendezvousGcThreads() {
        VmOperationEvent priorLogEvent = new VmOperationEvent("VM Operations (2 events):");
        String logLine = "Event: 1855.513 Executing VM operation: ZRendezvousGCThreads";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION_EVENT.toString() + " not parsed.");
    }
}

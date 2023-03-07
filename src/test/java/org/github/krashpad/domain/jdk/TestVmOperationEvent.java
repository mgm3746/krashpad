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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestVmOperationEvent {

    @Test
    void testCGCOperation() {
        String logLine = "VM_Operation (0x0000008e276ff410): CGC_Operation, mode: safepoint, requested by thread "
                + "0x000001d9d3e12800";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("CGC_Operation, mode: safepoint, requested by thread 0x000001d9d3e12800",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***

    @Test
    void testCollectForMetadataAllocation() {
        String logLine = "VM_Operation (0x00007f7d9cd68720): CollectForMetadataAllocation, mode: safepoint, requested "
                + "by thread 0x00007f7dc1c44000";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("CollectForMetadataAllocation, mode: safepoint, requested by thread 0x00007f7dc1c44000",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***

    @Test
    void testG1CollectFull() {
        String logLine = "VM_Operation (0x00007f2764076d80): G1CollectFull, mode: safepoint, requested by thread "
                + "0x00007f29ec6cf800";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("G1CollectFull, mode: safepoint, requested by thread 0x00007f29ec6cf800",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***

    @Test
    void testGetAllStackTraces() {
        String logLine = "VM_Operation (0x0000000003a5f250): GetAllStackTraces, mode: safepoint, requested by thread "
                + "0x0000000018af9800";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("GetAllStackTraces, mode: safepoint, requested by thread 0x0000000018af9800",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***

    @Test
    void testGetThreadListStackTraces() {
        String logLine = "VM_Operation (0x00007efff5d6d830): GetThreadListStackTraces, mode: safepoint, requested by "
                + "thread 0x000055b2423e2800";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("GetThreadListStackTraces, mode: safepoint, requested by thread 0x000055b2423e2800",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***

    @Test
    void testHeapDumper() {
        String logLine = "VM_Operation (0x0000000054ede490): HeapDumper, mode: safepoint, requested by thread "
                + "0x000000004d180000";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread "
                + "0x0000000001b2a";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_OPERATION,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIsNotThrowaway() {
        String logLine = "VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread "
                + "0x0000000001b2a";
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ThrowAwayEvent,
                "ThrowAwayEvent incorrectly identified.");
    ***REMOVED***

    @Test
    void testParallelGCFailedAllocation() {
        String logLine = "VM_Operation (0x00007ffa3a2de290): ParallelGCFailedAllocation, mode: safepoint, requested by "
                + "thread 0x0000557d902f1000";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("ParallelGCFailedAllocation, mode: safepoint, requested by thread 0x0000557d902f1000",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread "
                + "0x0000000001b2a";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPrintThreads() {
        String logLine = "VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread "
                + "0x0000000001b2a";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("PrintThreads, mode: safepoint, requested by thread 0x0000000001b2a", event.getVmOperationString(),
                "VM operation not correct.");
    ***REMOVED***

    @Test
    void testShenandoahFullGC() {
        String logLine = "VM_Operation (0x00007f25169a9ba0): ShenandoahFullGC, mode: safepoint, requested by thread "
                + "0x0000560e86b75800";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
        VmOperationEvent event = new VmOperationEvent(logLine);
        assertEquals("ShenandoahFullGC, mode: safepoint, requested by thread 0x0000560e86b75800",
                event.getVmOperationString(), "VM operation not correct.");
    ***REMOVED***
***REMOVED***
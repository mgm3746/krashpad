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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestClassesUnloadedEvent {

    @Test
    void testIdentity() {
        String logLine = "Classes unloaded (13 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASSES_UNLOADED,
                JdkUtil.LogEventType.CLASSES_UNLOADED.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLambda() {
        ClassesUnloadedEvent priorLogEvent = new ClassesUnloadedEvent("");
        String logLine = "Event: 7.661 Thread 0x00007f282c2174f0 Unloading class 0x0000000801591000 "
                + "'java/lang/invoke/LambdaForm$DMH+0x0000000801591000'";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.CLASSES_UNLOADED,
                JdkUtil.LogEventType.CLASSES_UNLOADED.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoEventsLowercaseE() {
        ClassesUnloadedEvent priorLogEvent = new ClassesUnloadedEvent("");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.CLASSES_UNLOADED,
                JdkUtil.LogEventType.CLASSES_UNLOADED.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoEventsUppercaseE() {
        ClassesUnloadedEvent priorLogEvent = new ClassesUnloadedEvent("");
        String logLine = "No Events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.CLASSES_UNLOADED,
                JdkUtil.LogEventType.CLASSES_UNLOADED.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Classes unloaded (13 events):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ClassesUnloadedEvent,
                JdkUtil.LogEventType.CLASSES_UNLOADED.toString() + " not identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof GcHeapHistoryEvent,
                JdkUtil.LogEventType.GC_HEAP_HISTORY.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof DllOperationEvent,
                JdkUtil.LogEventType.DLL_OPERATION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ClassesRedefinedEvent,
                JdkUtil.LogEventType.CLASSES_REDEFINED.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ExceptionEvent,
                JdkUtil.LogEventType.EXCEPTION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof VmOperation,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof EventEvent,
                JdkUtil.LogEventType.EVENT.toString() + " incorrectly identified.");
    ***REMOVED***

***REMOVED***

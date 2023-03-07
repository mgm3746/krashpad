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
class TestDllOperationEvent {

    @Test
    void testIdentity() {
        String logLine = "Dll operation events (18 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DLL_OPERATION_EVENT,
                JdkUtil.LogEventType.DLL_OPERATION_EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testJbds() {
        DllOperationEvent priorLogEvent = new DllOperationEvent("");
        String logLine = "Event: 2.030 Loaded shared library /path/to/codereadystudio/studio/configuration/"
                + "org.eclipse.osgi/149/0/.cp/os/linux/x86_64/libunixfile_1_0_0.s";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DLL_OPERATION_EVENT,
                JdkUtil.LogEventType.DLL_OPERATION_EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLibJava() {
        DllOperationEvent priorLogEvent = new DllOperationEvent("");
        String logLine = "Event: 0.001 Loaded shared library /usr/lib/jvm/java-17-openjdk-17.0.6.0.10-3.el8_7.x86_64/"
                + "lib/libjava.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DLL_OPERATION_EVENT,
                JdkUtil.LogEventType.DLL_OPERATION_EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Dll operation events (18 events):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof DllOperationEvent,
                JdkUtil.LogEventType.DLL_OPERATION_EVENT.toString() + " not identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof GcHeapHistoryEvent,
                JdkUtil.LogEventType.GC_HEAP_HISTORY.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ClassesUnloadedEvent,
                JdkUtil.LogEventType.CLASSES_UNLOADED.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof ClassesRedefinedEvent,
                JdkUtil.LogEventType.CLASSES_REDEFINED.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof InternalExceptionEvent,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof VmOperation,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " incorrectly identified.");
        assertFalse(JdkUtil.parseLogLine(logLine, null) instanceof EventEvent,
                JdkUtil.LogEventType.EVENT.toString() + " incorrectly identified.");
    ***REMOVED***

***REMOVED***

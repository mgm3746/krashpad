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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestCurrentThreadEvent {

    @Test
    void test32BitAddress() {
        String logLine = "Current thread (0x08ec6400):  JavaThread \"main\" [_thread_blocked, id=29301, "
                + "stack(0xffc15000,0xffc65000)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_THREAD,
                JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals("JavaThread \"main\" [_thread_blocked, id=29301, stack(0xffc15000,0xffc65000)]",
                ((CurrentThreadEvent) event).getCurrentThread(), "Current thread not correct.");
    ***REMOVED***

    @Test
    void testCurrentThreadCompilerThread() {
        String logLine = "Current thread (0x00005630bc167000):  JavaThread \"C2 CompilerThread1\" daemon "
                + "[_thread_in_native, id=1956021, stack(0x00007f1572d1f000,0x00007f1572e20000)]";
        CurrentThreadEvent event = new CurrentThreadEvent(logLine);
        assertEquals(
                "JavaThread \"C2 CompilerThread1\" daemon [_thread_in_native, id=1956021, "
                        + "stack(0x00007f1572d1f000,0x00007f1572e20000)]",
                ((CurrentThreadEvent) event).getCurrentThread(), "Current thread not correct.");
        assertTrue(event.isCompilerThread(), "CompilerThread not identified");
    ***REMOVED***

    @Test
    void testCurrentThreadIsNativeThread() {
        String logLine = "Current thread is native thread";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_THREAD,
                JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not identified.");
        LogEvent event = JdkUtil.parseLogLine(logLine, null);
        assertEquals("is native thread", ((CurrentThreadEvent) event).getCurrentThread());
    ***REMOVED***

    @Test
    void testCurrentThreadVmOperation() {
        String logLine = "Current thread (0x000001e1fb66f000):  VMThread \"VM Thread\" [stack: 0x0000008e27a00000,"
                + "0x0000008e27b00000] [id=19320]";
        CurrentThreadEvent event = new CurrentThreadEvent(logLine);
        assertEquals("VMThread \"VM Thread\" [stack: 0x0000008e27a00000,0x0000008e27b00000] [id=19320]",
                ((CurrentThreadEvent) event).getCurrentThread(), "Current thread not correct.");
        assertTrue(event.isVmThread(), "VMThread not identified");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Current thread (0x00007f127434f800):  JavaThread \"ajp-/hostname:8109-16\" daemon "
                + "[_thread_in_native, id=112672, stack(0x00007f11e11a2000,0x00007f11e12a3000)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_THREAD,
                JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Current thread (0x00007f127434f800):  JavaThread \"ajp-/hostname:8109-16\" daemon "
                + "[_thread_in_native, id=112672, stack(0x00007f11e11a2000,0x00007f11e12a3000)]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CurrentThreadEvent,
                JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***

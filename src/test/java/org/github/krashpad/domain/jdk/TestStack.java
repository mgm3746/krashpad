/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestStack {

    /**
     * TODO: Does this error apply to multiple events and should be moved to a dedicated ErrorEvent?
     */
    @Test
    void testError() {
        String logLine = "[error occurred during error reporting (printing native stack), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testErrorLong() {
        String logLine = "[error occurred during error reporting (printing native stack), id 0xb, "
                + "SIGSEGV (0xb) at pc=0x00007f68370d8504]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testErrorPrintingJavaStack() {
        String logLine = "[error occurred during error reporting (printing Java stack), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testErrorPrintingStackBounds() {
        String logLine = "[error occurred during error reporting (printing stack bounds), id 0xc0000005]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
        Stack logEvent = new Stack(logLine);
        assertTrue(logEvent.isErrorOccurredDuringErrorReporting(), "Error not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
        Stack event = new Stack(logLine);
        assertTrue(event.isHeader(), "Header not identified.");
        assertEquals(1018, event.getStackFreeSpace(), "Stack free space not correct.");
    }

    @Test
    void testHeaderNoFreeSpace() {
        String logLine = "Stack: [0x000000005a740000,0x000000005a840000]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Stack,
                JdkUtil.LogEventType.STACK.toString() + " not parsed.");
        Stack event = new Stack(logLine);
        assertTrue(event.isHeader(), "Header not identified.");
        assertEquals(Long.MIN_VALUE, event.getStackFreeSpace(), "Stack free space not correct.");
    }

    @Test
    void testIdentity() {
        String logLine = "Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testInterpretedCode() {
        String logLine = "j  org.eclipse.ui.internal.Workbench$$Lambda$166.run()V+12";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testJavaCompiledCode() {
        String logLine = "J 7595  org.eclipse.swt.internal.gtk.GTK.gtk_main_do_event(J)V (0 bytes) @ "
                + "0x00007fcd7c4b2f91 [0x00007fcd7c4b2f40+0x0000000000000051]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testJavaCompiledCodeWithPercent() {
        String logLine = "J 7595%  org.eclipse.swt.internal.gtk.GTK.gtk_main_do_event(J)V (0 bytes) @ "
                + "0x00007fcd7c4b2f91 [0x00007fcd7c4b2f40+0x0000000000000051]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testJavaFrames() {
        String logLine = "Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testJavaThreadBeingProcessed() {
        String logLine = "JavaThread 0x000055ae261db800 (nid = 76044) was being processed";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testMoreFrames() {
        String logLine = "...<more frames>...";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testNativeCode() {
        String logLine = "C  [libcairo.so.2+0x66e64]  cairo_region_num_rectangles+0x4";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testNativeFrames() {
        String logLine = "Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Stack,
                JdkUtil.LogEventType.STACK.toString() + " not parsed.");
    }

    @Test
    void testVmFrameLargeV() {
        String logLine = "V  [libjvm.so+0x93a382]  java_start(Thread*)+0xf2";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
        Stack stackEvent = new Stack(logLine);
        assertTrue(stackEvent.isVmFrame(), "VM code not identified.");
    }

    @Test
    void testVmFrameSmallV() {
        String logLine = "v  ~StubRoutines::call_stub";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
        Stack stackEvent = new Stack(logLine);
        assertFalse(stackEvent.isVmFrame(), "VM frame incorrectly identified.");
    }

    @Test
    void testVmGeneratedCodeFrameSmallV() {
        String logLine = "v  ~StubRoutines::call_stub";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STACK,
                JdkUtil.LogEventType.STACK.toString() + " not identified.");
        Stack stackEvent = new Stack(logLine);
        assertTrue(stackEvent.isVmGeneratedCodeFrame(), "VM generated code frame not identified.");
    }
}

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
class TestInternalExceptionEvent {

    @Test
    void testHeader() {
        String logLine = "Internal exceptions (250 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "Event: 101.811 Thread 0x00007ff0ec698000 Exception "
                + "<a 'java/lang/ArrayIndexOutOfBoundsException'> (0x00000000ef71fd30) thrown at "
                + "[/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64/openjdk/hotspot/src/share/"
                + "vm/runtime/sharedRuntime.cpp, line 609]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testImplicitDivisionByZeroException() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "Event: 153.611 Thread 0x00007f2ce8042800 Implicit division by zero exception at "
                + "0x00007f2ca9133501 to 0x00007f2ca913844a";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testMeta() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=0> (0x000000079d702b90) "
                + "thrown at [/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.265";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsUppercaseE() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "No Events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNullPointerException() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "Event: 134938.340 Thread 0x000055c53e54f000 NullPointerException at vtable entry "
                + "0x00007f6a048a7a77";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "Event: 101.811 Thread 0x00007ff0ec698000 Exception "
                + "<a 'java/lang/ArrayIndexOutOfBoundsException'> (0x00000000ef71fd30) thrown at "
                + "[/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64/openjdk/hotspot/src/share/"
                + "vm/runtime/sharedRuntime.cpp, line 609]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof InternalExceptionEvent,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testStackOverflow() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (10 events):");
        String logLine = "Event: 1787840.598 Thread 0x00000000e2291000 StackOverflowError at 0x0000000006f058c0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testThreadImplicitNullException() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "Event: 32.906 Thread 0x00007ffff1dac800 Implicit null exception at 0x00007fffe09b9782 to "
                + "0x00007fffe09ba54e";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testThrown() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "thrown [src/hotspot/share/prims/jni.cpp, line 516]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }

    @Test
    void testThrownNoDetails() {
        InternalExceptionEvent priorLogEvent = new InternalExceptionEvent("Internal exceptions (250 events):");
        String logLine = "thrown";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT,
                JdkUtil.LogEventType.INTERNAL_EXCEPTION_EVENT.toString() + " not identified.");
    }
}

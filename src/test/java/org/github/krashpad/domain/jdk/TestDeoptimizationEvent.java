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
class TestDeoptimizationEvent {

    @Test
    void testDeoptPacking() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "Event: 624.202 Thread 0x00007ffff0017800 DEOPT PACKING pc=0x00007fffe0a0cbc8 "
                + "sp=0x00007ffff540fc50";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.");
    }

    /**
     * TODO: Does this error apply to multiple events and should be moved to a dedicated ErrorEvent?
     */
    @Test
    void testError() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "[error occurred during error reporting (printing ring buffers), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Deoptimization events (250 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "Event: 5689.207 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if "
                + "action=reinterpret pc=0x00007ff0df4a0408 "
                + "method=org.eclipse.jface.text.ListLineTracker.getLineLength(I)I @ 28";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testNoEventsUppercaseE() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "No Events";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "Event: 5689.207 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if "
                + "action=reinterpret pc=0x00007ff0df4a0408 "
                + "method=org.eclipse.jface.text.ListLineTracker.getLineLength(I)I @ 28";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.");
    }

    @Test
    void testThreadException() {
        DeoptimizationEvent priorLogEvent = new DeoptimizationEvent("Deoptimization events (250 events):");
        String logLine = "Event: 6446.059 Thread 0x00007ff0ac001800 Exception <a 'sun/nio/fs/UnixException'> "
                + "(0x00000000ebbe2688) thrown at [/builddir/build/BUILD/"
                + "java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64/openjdk/hotspot/src/share/vm/prims/jni.cpp, "
                + "line 711]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.");
    }

}

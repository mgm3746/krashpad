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
class TestMachCode {

    void testDeoptHandlerCode() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[Deopt Handler Code]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testEntryPoint() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[Entry Point]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testExceptionHandler() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[Exception Handler]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testFooter() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[/MachCode]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testHash() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  #           [sp+0x50]  (sp of caller)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "[MachCode]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MACH_CODE,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not identified.");
    }

    @Test
    void testMethod() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  # {method} {0x00007fcfed1c5dd8} 'invoke' '(Ljava/lang/Object;[Ljava/lang/Object;)"
                + "Ljava/lang/Object;' in 'jdk/internal/reflect/NativeMethodAccessorImpl'";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "[MachCode]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testPartialLine() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  0x00007ff4011cb5a0: e95b 25fd | f7e8 0000 | 0000 4883 | 2c24 05e9 | 6c45 f3f7 | f4f4 f4f4";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testSemicolon() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "                      ;*invokevirtual isHidden {reexecute=0 rethrow=0 return_oop=0}";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testStubCodeHeader() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[Stub Code]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testVerifiedEntryPoint() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[Verified Entry Point]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

}

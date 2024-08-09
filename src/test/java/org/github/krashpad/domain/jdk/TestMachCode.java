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
class TestMachCode {

    @Test
    void testCompiledMethod() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "Compiled method (c1)   20567 9656   !   3       "
                + "jdk.internal.reflect.NativeMethodAccessorImpl::invoke (137 bytes)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testDependencies() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " dependencies   [0x00007fd021c161c0,0x00007fd021c161c8] = 8";
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
    void testFullLine() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  0x00007ff4011cb4a0: 448b 5608 | 49bb 0000 | 0000 0800 | 0000 4d03 | d349 3bc2 | 0f85 c6b7 |"
                + " f2f7 6690 | 0f1f 4000";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testHandlerTable() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " handler table  [0x00007fd021c161c8,0x00007fd021c16300] = 312";
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
    void testMainCode() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " main code      [0x00007fd021c15200,0x00007fd021c15e00] = 3072";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testMetadata() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " metadata       [0x00007fd021c15e80,0x00007fd021c15ec8] = 72";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
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
    void testNulChkTable() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " nul chk table  [0x00007fd021c16300,0x00007fd021c16348] = 72";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testParm() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  # parm0:    rsi:rsi   = 'java/lang/reflect/Method'";
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
    void testRelocation() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " relocation     [0x00007fd021c150f0,0x00007fd021c151e8] = 248";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testScopesData() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " scopes data    [0x00007fd021c15ec8,0x00007fd021c16020] = 344";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testScopesPcs() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " scopes pcs     [0x00007fd021c16020,0x00007fd021c161c0] = 416";
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
    void testStubCode() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " stub code      [0x00007fd021c15e00,0x00007fd021c15e80] = 128";
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
    void testTotalInHeap() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = " total in heap  [0x00007fd021c14f90,0x00007fd021c16348] = 5048";
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

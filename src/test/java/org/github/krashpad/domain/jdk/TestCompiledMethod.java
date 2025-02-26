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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestCompiledMethod {

    @Test
    void testDependencies() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " dependencies   [0x00007fd021c161c0,0x00007fd021c161c8] = 8";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testHandlerTable() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " handler table  [0x00007fd021c161c8,0x00007fd021c16300] = 312";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testHeader() {
        String logLine = "Compiled method (c2) 11770727 16746  s    4       java.lang.StringBuffer::toString "
                + "(59 bytes)";
        assertEquals(JdkUtil.LogEventType.COMPILED_METHOD, JdkUtil.identifyEventType(logLine, null),
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "Compiled method (c2) 11770727 16746  s    4       java.lang.StringBuffer::toString "
                + "(59 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.COMPILED_METHOD,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not identified.");
    }

    @Test
    void testMainCode() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " main code      [0x00007fd021c15200,0x00007fd021c15e00] = 3072";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testMetadata() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " metadata       [0x00007fd021c15e80,0x00007fd021c15ec8] = 72";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testNulChkTable() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " nul chk table  [0x00007fd021c16300,0x00007fd021c16348] = 72";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testOops() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " oops           [0x00007fabe0e9b180,0x00007fabe0e9b188] = 8";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Compiled method (c2) 11770727 16746  s    4       java.lang.StringBuffer::toString "
                + "(59 bytes)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testRelocation() {
        CompiledMethod priorLogEvent = new CompiledMethod(null);
        String logLine = " relocation     [0x00007fd021c150f0,0x00007fd021c151e8] = 248";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testScopesData() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " scopes data    [0x00007fd021c15ec8,0x00007fd021c16020] = 344";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testScopesPcs() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " scopes pcs     [0x00007fd021c16020,0x00007fd021c161c0] = 416";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testStubCode() {
        CompiledMethod priorLogEvent = new CompiledMethod(null);
        String logLine = " stub code      [0x00007fd021c15e00,0x00007fd021c15e80] = 128";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }

    @Test
    void testTotalInHeap() {
        CompiledMethod priorLogEvent = new CompiledMethod("[CompiledMethod]");
        String logLine = " total in heap  [0x00007fd021c14f90,0x00007fd021c16348] = 5048";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompiledMethod,
                JdkUtil.LogEventType.COMPILED_METHOD.toString() + " not parsed.");
    }
}

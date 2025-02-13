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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestRegister {

    @Test
    void test2R0() {
        Register priorEvent = new Register("Registers:");
        String logLine = "R0=0x0000229851cd4000";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void test2RegistersNoSpacesAtEnd() {
        Register priorEvent = new Register("Registers:");
        String logLine = "RIP=0x00007ff8193e0c3b, EFLAGS=0x0000000000010202";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void test2RegistersSpacesAtEnd() {
        Register priorEvent = new Register("Registers:");
        String logLine = "r30=0x00007ffe958dde20  r31=0x00007ffe958ddcc0  ";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testBlankLine() {
        Register priorEvent = new Register("Registers:");
        String logLine = "";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "Registers:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testMxcsr2LeadingSpaces() {
        Register priorEvent = new Register("Registers:");
        String logLine = "  MXCSR=0x00001fa0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Registers:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Register,
                JdkUtil.LogEventType.REGISTER.toString() + " not parsed.");
    }

    @Test
    void testPcSmallLetters() {
        Register priorEvent = new Register("Registers:");
        String logLine = "pc =0x00003fff7a9ddba0  lr =0x00003fff7a9ddb54  ctr=0x000000000000000f";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testR12() {
        Register priorEvent = new Register("Registers:");
        String logLine = "R12=0x00007f673d50bfe0, R13=0x00007f6a3a004628, R14=0x00007f6a3a004620, "
                + "R15=0x00007f673d50bdf0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testR8() {
        Register priorEvent = new Register("Registers:");
        String logLine = "R8 =0x0000000000000005, R9 =0x0000000000000010, R10=0x0000000000000000, "
                + "R11=0x0000000000000000";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRax() {
        Register priorEvent = new Register("Registers:");
        String logLine = "RAX=0x0000000000000001, RBX=0x00007f67383dc748, RCX=0x0000000000000004, "
                + "RDX=0x00007f69b031f898";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRegisterEax() {
        Register priorEvent = new Register("Registers:");
        String logLine = "EAX=0xfffffffc, EBX=0x086c8d44, ECX=0x00000080, EDX=0x00000001";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRegisterEip() {
        Register priorEvent = new Register("Registers:");
        String logLine = "EIP=0xf7752430, EFLAGS=0x00000246, CR2=0x00000000";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRegisterEsp() {
        Register priorEvent = new Register("Registers:");
        String logLine = "ESP=0xffc2c69c, EBP=0xffc2c720, ESI=0xffc2c6b0, EDI=0x086c8d28";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRegisterSmallLetter() {
        Register priorEvent = new Register("Registers:");
        String logLine = "r0 =0x00003fff7aa0c5d8  r1 =0x00003fff79b2ddb0  r2 =0x00003fff7b0b7e00";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRop() {
        Register priorEvent = new Register("Registers:");
        String logLine = "RIP=0x00007fcbd05a3b71, EFLAGS=0x0000000000010293, CSGSFS=0x0000000000000033, "
                + "ERR=0x0000000000000004";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testRsp() {
        Register priorEvent = new Register("Registers:");
        String logLine = "RSP=0x00007fcbcc676c50, RBP=0x00007fcbcc676cb0, RSI=0x0000000000000000, "
                + "RDI=0x00007f69b031f898";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testSpaceAtEnd() {
        Register priorEvent = new Register("Registers:");
        String logLine = "pc =0x00007fff7fd30634  lr =0x00007fff7fd30614  ctr=0x00007fff7f481a90 ";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testTrapno1LeadingSpace() {
        Register priorEvent = new Register("Registers:");
        String logLine = " TRAPNO=0x000000000000000e";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testTrapno2LeadingSpaces() {
        Register priorEvent = new Register("Registers:");
        String logLine = "  TRAPNO=0x000000000000000e";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }

    @Test
    void testXmm() {
        Register priorEvent = new Register("Registers:");
        String logLine = "XMM[0]=0x0f4800f87e8349d2 0x33f8468d49368d49";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER,
                JdkUtil.LogEventType.REGISTER.toString() + " not identified.");
    }
}
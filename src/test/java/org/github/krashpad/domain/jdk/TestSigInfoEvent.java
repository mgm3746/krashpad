/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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
import org.github.krashpad.util.jdk.JdkUtil.SignalCode;
import org.github.krashpad.util.jdk.JdkUtil.SignalNumber;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestSigInfoEvent {

    @Test
    void testIdentity() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGINFO,
                JdkUtil.LogEventType.SIGINFO.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof SigInfoEvent,
                JdkUtil.LogEventType.SIGINFO.toString() + " not parsed.");
    }

    @Test
    void testSigsegvSegvMaperr() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGINFO,
                JdkUtil.LogEventType.SIGINFO.toString() + " not identified.");
        SigInfoEvent event = new SigInfoEvent(logLine);
        assertEquals(SignalNumber.SIGSEGV, event.getSignalNumber(), "Signal number not correct.");
        assertEquals(SignalCode.SEGV_MAPERR, event.getSignalCode(), "Signal code not correct.");
        assertEquals("0x0000000000000008", event.getSignalAddress(), "Signal address not correct.");
    }

    @Test
    void testExceptionAccessViolation() {
        String logLine = "siginfo: ExceptionCode=0xc0000005, reading address 0x0000000000000048";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGINFO,
                JdkUtil.LogEventType.SIGINFO.toString() + " not identified.");
        SigInfoEvent event = new SigInfoEvent(logLine);
        assertEquals(SignalNumber.EXCEPTION_ACCESS_VIOLATION, event.getSignalNumber(), "Signal number not correct.");
    }

    @Test
    void testSignalCodeSiKernel() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 128 (SI_KERNEL), si_addr: 0x0000000000000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGINFO,
                JdkUtil.LogEventType.SIGINFO.toString() + " not identified.");
        SigInfoEvent event = new SigInfoEvent(logLine);
        assertEquals(SignalNumber.SIGSEGV, event.getSignalNumber(), "Signal number not correct.");
        assertEquals(SignalCode.SI_KERNEL, event.getSignalCode(), "Signal code not correct.");
    }

    @Test
    void testSignalCodeSiUser() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 0 (SI_USER), sent from pid: 107614 (uid: 1000)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGINFO,
                JdkUtil.LogEventType.SIGINFO.toString() + " not identified.");
        SigInfoEvent event = new SigInfoEvent(logLine);
        assertEquals(SignalNumber.SIGSEGV, event.getSignalNumber(), "Signal number not correct.");
        assertEquals(SignalCode.SI_USER, event.getSignalCode(), "Signal code not correct.");
    }

    @Test
    void testSignalCodeIllIllOpn() {
        String logLine = "siginfo: si_signo: 4 (SIGILL), si_code: 2 (ILL_ILLOPN), si_addr: 0x00007f682098912c";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGINFO,
                JdkUtil.LogEventType.SIGINFO.toString() + " not identified.");
        SigInfoEvent event = new SigInfoEvent(logLine);
        assertEquals(SignalNumber.SIGILL, event.getSignalNumber(), "Signal number not correct.");
        assertEquals(SignalCode.ILL_ILLOPN, event.getSignalCode(), "Signal code not correct.");
    }
}
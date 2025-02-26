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
class TestSignalHandlers {

    @Test
    void testExpected() {
        String logLine = "  *** Expected: javaSignalHandler in libjvm.so, mask=11100100110111111111111111111110, "
                + "flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testHandlerWasModified() {
        String logLine = "  *** Handler was modified!";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Signal Handlers:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "SIGSEGV: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testJdk17() {
        String logLine = "   SIGSEGV: crash_handler in libjvm.so, mask=11100100010111111101111111111110, "
                + "flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "SIGSEGV: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof SignalHandlers,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not parsed.");
    }

    @Test
    void testSig39() {
        String logLine = "SIG39: [libjvm.so+0xa9e080], sa_mask[0]=00000000000000000000000000000000, "
                + "sa_flags=SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSig40() {
        String logLine = "SIG40: [libjvm.so+0xa9e030], sa_mask[0]=11111111011111111111110111111111, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigbus() {
        String logLine = "SIGBUS: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigfpe() {
        String logLine = "SIGFPE: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSighup() {
        String logLine = "SIGHUP: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigill() {
        String logLine = "SIGILL: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigint() {
        String logLine = "SIGINT: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigpipe() {
        String logLine = "SIGPIPE: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigpwr() {
        String logLine = "SIGPWR: [libjvm.so+0xc12340], sa_mask[0]=00000000000100000000000000000000, sa_flags="
                + "SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigquit() {
        String logLine = "SIGQUIT: [libjvm.so+0x964430], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigterm() {
        String logLine = "SIGTERM: [libjvm.so+0x964430], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigtrap() {
        String logLine = "SIGTRAP: [libjvm.so+0x840670], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigusr1() {
        String logLine = "SIGUSR1: SIG_DFL, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigusr2() {
        String logLine = "SIGUSR2: [libjvm.so+0x9628d0], sa_mask[0]=00000000000000000000000000000000, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }

    @Test
    void testSigxfsz() {
        String logLine = "SIGXFSZ: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS,
                JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.");
    }
}
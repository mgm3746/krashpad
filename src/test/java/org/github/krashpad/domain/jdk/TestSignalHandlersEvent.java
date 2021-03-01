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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestSignalHandlersEvent extends TestCase {

    public void testIdentity() {
        String logLine = "SIGSEGV: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "SIGSEGV: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof SignalHandlersEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigbus() {
        String logLine = "SIGBUS: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigfpe() {
        String logLine = "SIGFPE: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigpipe() {
        String logLine = "SIGPIPE: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigxfsz() {
        String logLine = "SIGXFSZ: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigill() {
        String logLine = "SIGILL: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigusr1() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigusr2() {
        String logLine = "SIGUSR2: [libjvm.so+0x9628d0], sa_mask[0]=00000000000000000000000000000000, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSighup() {
        String logLine = "SIGHUP: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigint() {
        String logLine = "SIGINT: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigterm() {
        String logLine = "SIGTERM: [libjvm.so+0x964430], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigquit() {
        String logLine = "SIGQUIT: [libjvm.so+0x964430], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSigtrap() {
        String logLine = "SIGTRAP: [libjvm.so+0x840670], sa_mask[0]=11111111011111111101111111111110, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSig39() {
        String logLine = "SIG39: [libjvm.so+0xa9e080], sa_mask[0]=00000000000000000000000000000000, "
                + "sa_flags=SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***

    public void testSig40() {
        String logLine = "SIG40: [libjvm.so+0xa9e030], sa_mask[0]=11111111011111111111110111111111, "
                + "sa_flags=SA_RESTART|SA_SIGINFO";
        Assert.assertTrue(JdkUtil.LogEventType.SIGNAL_HANDLERS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SIGNAL_HANDLERS);
    ***REMOVED***
***REMOVED***
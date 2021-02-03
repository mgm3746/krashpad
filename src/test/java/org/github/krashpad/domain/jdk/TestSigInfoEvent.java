/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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
import org.github.krashpad.util.jdk.JdkUtil.SignalCode;
import org.github.krashpad.util.jdk.JdkUtil.SignalNumber;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestSigInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof SigInfoEvent);
    ***REMOVED***

    public void testSigsegvSegvMaperr() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
        SigInfoEvent event = new SigInfoEvent(logLine);
        Assert.assertEquals("Signal number not correct.", SignalNumber.SIGSEGV, event.getSignalNumber());
        Assert.assertEquals("Signal code not correct.", SignalCode.SEGV_MAPERR, event.getSignalCode());
        Assert.assertEquals("Signal address not correct.", "0x0000000000000008", event.getSignalAddress());
    ***REMOVED***

    public void testExceptionAccessViolation() {
        String logLine = "siginfo: ExceptionCode=0xc0000005, reading address 0x0000000000000048";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
        SigInfoEvent event = new SigInfoEvent(logLine);
        Assert.assertEquals("Signal number not correct.", SignalNumber.EXCEPTION_ACCESS_VIOLATION,
                event.getSignalNumber());
    ***REMOVED***

    public void testSignalCodeSiKernel() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 128 (SI_KERNEL), si_addr: 0x0000000000000000";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
        SigInfoEvent event = new SigInfoEvent(logLine);
        Assert.assertEquals("Signal number not correct.", SignalNumber.SIGSEGV, event.getSignalNumber());
        Assert.assertEquals("Signal code not correct.", SignalCode.SI_KERNEL, event.getSignalCode());
    ***REMOVED***

    public void testSignalCodeSiUser() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 0 (SI_USER), sent from pid: 107614 (uid: 1000)";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
        SigInfoEvent event = new SigInfoEvent(logLine);
        Assert.assertEquals("Signal number not correct.", SignalNumber.SIGSEGV, event.getSignalNumber());
        Assert.assertEquals("Signal code not correct.", SignalCode.SI_USER, event.getSignalCode());
    ***REMOVED***

    public void testSignalCodeIllIllOpn() {
        String logLine = "siginfo: si_signo: 4 (SIGILL), si_code: 2 (ILL_ILLOPN), si_addr: 0x00007f682098912c";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
        SigInfoEvent event = new SigInfoEvent(logLine);
        Assert.assertEquals("Signal number not correct.", SignalNumber.SIGILL, event.getSignalNumber());
        Assert.assertEquals("Signal code not correct.", SignalCode.ILL_ILLOPN, event.getSignalCode());
    ***REMOVED***
***REMOVED***
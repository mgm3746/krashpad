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
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestRegisterEvent extends TestCase {

    public void testIdentity() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof RegisterEvent);
    ***REMOVED***

    public void testRax() {
        String logLine = "RAX=0x0000000000000001, RBX=0x00007f67383dc748, RCX=0x0000000000000004, "
                + "RDX=0x00007f69b031f898";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testRsp() {
        String logLine = "RSP=0x00007fcbcc676c50, RBP=0x00007fcbcc676cb0, RSI=0x0000000000000000, "
                + "RDI=0x00007f69b031f898";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testR8() {
        String logLine = "R8 =0x0000000000000005, R9 =0x0000000000000010, R10=0x0000000000000000, "
                + "R11=0x0000000000000000";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testR12() {
        String logLine = "R12=0x00007f673d50bfe0, R13=0x00007f6a3a004628, R14=0x00007f6a3a004620, "
                + "R15=0x00007f673d50bdf0";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void test2RegistersSpacesAtEnd() {
        String logLine = "r30=0x00007ffe958dde20  r31=0x00007ffe958ddcc0  ";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testRop() {
        String logLine = "RIP=0x00007fcbd05a3b71, EFLAGS=0x0000000000010293, CSGSFS=0x0000000000000033, "
                + "ERR=0x0000000000000004";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testTrapno() {
        String logLine = "  TRAPNO=0x000000000000000e";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testPcSmallLetters() {
        String logLine = "pc =0x00003fff7a9ddba0  lr =0x00003fff7a9ddb54  ctr=0x000000000000000f";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testSpaceAtEnd() {
        String logLine = "pc =0x00007fff7fd30634  lr =0x00007fff7fd30614  ctr=0x00007fff7f481a90 ";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testRegisterSmallLetter() {
        String logLine = "r0 =0x00003fff7aa0c5d8  r1 =0x00003fff79b2ddb0  r2 =0x00003fff7b0b7e00";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testRegisterEax() {
        String logLine = "EAX=0xfffffffc, EBX=0x086c8d44, ECX=0x00000080, EDX=0x00000001";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testRegisterEsp() {
        String logLine = "ESP=0xffc2c69c, EBP=0xffc2c720, ESI=0xffc2c6b0, EDI=0x086c8d28";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***

    public void testRegisterEip() {
        String logLine = "EIP=0xf7752430, EFLAGS=0x00000246, CR2=0x00000000";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER);
    ***REMOVED***
***REMOVED***
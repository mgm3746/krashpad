/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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
package org.github.errcat.domain.jdk;

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestHeaderEvent extends TestCase {

    public void testIdentity() {
        String logLine = "#";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEADER);
    }

    public void testParseLogLine() {
        String logLine = "#";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
    }

    public void testProblematicFrameNativeCode() {
        String logLine = "# C  0x0000000000000000";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Problematic frame not identified.", headerEvent.isProblematicFrame());
    }

    public void testProblematicFrameVmCodeCapitalV() {
        String logLine = "# V  [libjvm.so+0xa41a10]";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Problematic frame not identified.", headerEvent.isProblematicFrame());
    }

    public void testProblematicFrameVmCodeSmallV() {
        String logLine = "# v  ~StubRoutines::jbyte_disjoint_arraycopy";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Problematic frame not identified.", headerEvent.isProblematicFrame());
    }

    public void testProblematicFrameJavaCompiledCode() {
        String logLine = "# J 3285 c2 java.util.AbstractList.subListRangeCheck(III)V java.base@12.0.1 (110 bytes) "
                + "@ 0x00007f682098912c [0x00007f68209891a0+0xffffffffffffff8c]";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Problematic frame not identified.", headerEvent.isProblematicFrame());
    }

    public void testSigSegv() {
        String logLine = "#  SIGSEGV (0xb) at pc=0x00007f11cbec3480, pid=101755, tid=139714768807680";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("SIGSEGV not identified.", headerEvent.isSignalNumber());
    }

    public void testSigBus() {
        String logLine = "#  SIGBUS (0x7) at pc=0x00007f824afe3410, pid=14689, tid=0x00007f81a3452700";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("SIGBUS not identified.", headerEvent.isSignalNumber());
    }

    public void testSigIll() {
        String logLine = "#  SIGILL (0x4) at pc=0x00007f682098912c, pid=12005, tid=44979";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("SIGILL not identified.", headerEvent.isSignalNumber());
    }

    public void testInternalError() {
        String logLine = "#  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Internal Errror not identified.", headerEvent.isInternalError());
    }

    public void testError() {
        String logLine = "#  Error: ShouldNotReachHere()";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Error not identified.", headerEvent.isError());
    }

    public void testJavaVm() {
        String logLine = "# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed "
                + "oops)";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Java VM not identified.", headerEvent.isJavaVm());
    }

    public void testJreVersion() {
        String logLine = "# JRE version: Java(TM) SE Runtime Environment (8.0_251-b08) (build 1.8.0_251-b08)";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("JRE version not identified.", headerEvent.isJreVersion());
    }
}

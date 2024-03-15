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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.joa.domain.Os;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestHeader {

    @Test
    void testAccessViolation() {
        String logLine = "#  EXCEPTION_ACCESS_VIOLATION (0xc0000005) at pc=0x000000006ee76520, pid=11556, "
                + "tid=0x000000000000040c";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isSignalNumber(), "EXCEPTION_ACCESS_VIOLATION not identified.");
    }

    @Test
    void testError() {
        String logLine = "#  Error: ShouldNotReachHere()";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isError(), "Error not identified.");
    }

    @Test
    void testErrorPrintingProblematicFrame() {
        String logLine = "[error occurred during error reporting (printing problematic frame), id 0x7]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
    }

    @Test
    void testFailedToMapMemory() {
        String logLine = "#  fatal error: Failed to map memory (Not enough space)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isError(), "Error not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "#";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
    }

    @Test
    void testInsufficientMemoroy() {
        String logLine = "# There is insufficient memory for the Java Runtime Environment to continue.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isInsufficient(), "Insufficient not identified.");
    }

    @Test
    void testInternalError() {
        String logLine = "#  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isInternalError(), "Internal Errror not identified.");
    }

    @Test
    void testJavaVm() {
        String logLine = "# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed "
                + "oops)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isJavaVm(), "Java VM not identified.");
    }

    @Test
    void testJreVersion() {
        String logLine = "# JRE version: Java(TM) SE Runtime Environment (8.0_251-b08) (build 1.8.0_251-b08)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isJreVersion(), "JRE version not identified.");
    }

    @Test
    void testNativeMemoryAllocationFailed() {
        String logLine = "# Native memory allocation (mmap) failed to map 754974720 bytes for committing reserved "
                + "memory.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isFailed(), "Failed not identified.");
    }

    @Test
    void testNoCoreDumpWillBeWritten() {
        String logLine = "# No core dump will be written. Core dumps have been disabled. To enable core dumping, try "
                + "\"ulimit -c unlimited\" before starting Java again";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
    }

    @Test
    void testOomeHeap() {
        String logLine = "#  fatal error: OutOfMemory encountered: Java heap space";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isError(), "Error not identified.");
    }

    @Test
    void testOsTypeLinux() {
        String logLine = "# Java VM: Java HotSpot(TM) 64-Bit Server VM (24.85-b06 mixed mode linux-amd64 compressed "
                + "oops)";
        Header headerEvent = new Header(logLine);
        assertEquals(Os.LINUX, headerEvent.getOsType(), "OS Type not identified.");
    }

    @Test
    void testOsTypeSolaris() {
        String logLine = "# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed "
                + "oops)";
        Header headerEvent = new Header(logLine);
        assertEquals(Os.SOLARIS, headerEvent.getOsType(), "OS Type not identified.");
    }

    @Test
    void testOsTypeWindows() {
        String logLine = "# Java VM: Java HotSpot(TM) 64-Bit Server VM (25.25-b02 mixed mode windows-amd64 compressed "
                + "oops)";
        Header headerEvent = new Header(logLine);
        assertEquals(Os.WINDOWS, headerEvent.getOsType(), "OS Type not identified.");
    }

    @Test
    void testOutOfMemoryError() {
        String logLine = "#  Out of Memory Error (os_solaris_sparc.cpp:570), pid=1129, tid=0x0000000000008488";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isOutOf(), "Out of Memory Error not identified.");
    }

    @Test
    void testOutOfSwapSpace() {
        String logLine = "# Out of swap space to map in thread stack.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isOutOf(), "'Out of' not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "#";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Header,
                JdkUtil.LogEventType.HEADER.toString() + " not parsed.");
    }

    @Test
    void testPleaseCheckEtcOsRelease() {
        LogEvent priorEvent = new OsInfo(null);
        String logLine = "# Please check /etc/os-release for details about this release.";
        assertFalse(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " incorrectly identified.");
    }

    @Test
    void testProblematicFrameJavaCompiledCode() {
        String logLine = "# J 3285 c2 java.util.AbstractList.subListRangeCheck(III)V java.base@12.0.1 (110 bytes) "
                + "@ 0x00007f682098912c [0x00007f68209891a0+0xffffffffffffff8c]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    }

    @Test
    void testProblematicFrameNativeCode() {
        String logLine = "# C  0x0000000000000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    }

    @Test
    void testProblematicFrameVmCodeCapitalV() {
        String logLine = "# V  [libjvm.so+0xa41a10]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    }

    @Test
    void testProblematicFrameVmCodeSmallV() {
        String logLine = "# v  ~StubRoutines::jbyte_disjoint_arraycopy";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    }

    @Test
    void testSigBus() {
        String logLine = "#  SIGBUS (0x7) at pc=0x00007f824afe3410, pid=14689, tid=0x00007f81a3452700";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isSignalNumber(), "SIGBUS not identified.");
    }

    @Test
    void testSigIll() {
        String logLine = "#  SIGILL (0x4) at pc=0x00007f682098912c, pid=12005, tid=44979";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isSignalNumber(), "SIGILL not identified.");
    }

    @Test
    void testSigSegv() {
        String logLine = "#  SIGSEGV (0xb) at pc=0x00007f11cbec3480, pid=101755, tid=139714768807680";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isSignalNumber(), "SIGSEGV not identified.");
    }

    @Test
    void testStackOverflow() {
        String logLine = "#  EXCEPTION_STACK_OVERFLOW (0xc00000fd) at pc=0x00007ff8b9b47447, pid=4304, "
                + "tid=0x0000000000002aec";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isSignalNumber(), "EXCEPTION_STACK_OVERFLOW not identified.");
    }

    @Test
    void testThisFileIsDeprecated() {
        LogEvent priorEvent = new OsInfo(null);
        String logLine = "# This file is deprecated and will be removed in a future service pack or release.";
        assertFalse(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " incorrectly identified.");
    }

    @Test
    void testTimeout() {
        String priorLogLine = "# C  [libc.so.6+0x822e3]";
        LogEvent priorEvent = new Header(priorLogLine);
        String logLine = "[timeout occurred during error reporting in step \"printing problematic frame\"] after "
                + "30 s.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isTimeout(), "Timeout not identified.");
    }

    @Test
    void testVendorBugUrl() {
        String logLine = "#   https://github.com/adoptium/adoptium-support/issues";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        Header headerEvent = new Header(logLine);
        assertTrue(headerEvent.isVendorBugUrl(), "Vendor bug url not identified.");
    }
}

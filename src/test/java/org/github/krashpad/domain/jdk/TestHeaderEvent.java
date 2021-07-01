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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestHeaderEvent {

    @Test
    void testIdentity() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof HeaderEvent,
                JdkUtil.LogEventType.HEADER.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testProblematicFrameNativeCode() {
        String logLine = "***REMOVED*** C  0x0000000000000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    ***REMOVED***

    @Test
    void testProblematicFrameVmCodeCapitalV() {
        String logLine = "***REMOVED*** V  [libjvm.so+0xa41a10]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    ***REMOVED***

    @Test
    void testProblematicFrameVmCodeSmallV() {
        String logLine = "***REMOVED*** v  ~StubRoutines::jbyte_disjoint_arraycopy";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    ***REMOVED***

    @Test
    void testProblematicFrameJavaCompiledCode() {
        String logLine = "***REMOVED*** J 3285 c2 java.util.AbstractList.subListRangeCheck(III)V java.base@12.0.1 (110 bytes) "
                + "@ 0x00007f682098912c [0x00007f68209891a0+0xffffffffffffff8c]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isProblematicFrame(), "Problematic frame not identified.");
    ***REMOVED***

    @Test
    void testSigSegv() {
        String logLine = "***REMOVED***  SIGSEGV (0xb) at pc=0x00007f11cbec3480, pid=101755, tid=139714768807680";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isSignalNumber(), "SIGSEGV not identified.");
    ***REMOVED***

    @Test
    void testSigBus() {
        String logLine = "***REMOVED***  SIGBUS (0x7) at pc=0x00007f824afe3410, pid=14689, tid=0x00007f81a3452700";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isSignalNumber(), "SIGBUS not identified.");
    ***REMOVED***

    @Test
    void testSigIll() {
        String logLine = "***REMOVED***  SIGILL (0x4) at pc=0x00007f682098912c, pid=12005, tid=44979";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isSignalNumber(), "SIGILL not identified.");
    ***REMOVED***

    @Test
    void testInternalError() {
        String logLine = "***REMOVED***  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isInternalError(), "Internal Errror not identified.");
    ***REMOVED***

    @Test
    void testError() {
        String logLine = "***REMOVED***  Error: ShouldNotReachHere()";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isError(), "Error not identified.");
    ***REMOVED***

    @Test
    void testJavaVm() {
        String logLine = "***REMOVED*** Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed "
                + "oops)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isJavaVm(), "Java VM not identified.");
    ***REMOVED***

    @Test
    void testJreVersion() {
        String logLine = "***REMOVED*** JRE version: Java(TM) SE Runtime Environment (8.0_251-b08) (build 1.8.0_251-b08)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isJreVersion(), "JRE version not identified.");
    ***REMOVED***

    @Test
    void testInsufficientMemoroy() {
        String logLine = "***REMOVED*** There is insufficient memory for the Java Runtime Environment to continue.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isInsufficient(), "Insufficient not identified.");
    ***REMOVED***

    @Test
    void testOutOfSwapSpace() {
        String logLine = "***REMOVED*** Out of swap space to map in thread stack.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isOutOf(), "Out of not identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryError() {
        String logLine = "***REMOVED***  Out of Memory Error (os_solaris_sparc.cpp:570), pid=1129, tid=0x0000000000008488";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isOutOf(), "Out of not identified.");
    ***REMOVED***

    @Test
    void testNativeMemoryAllocationFailed() {
        String logLine = "***REMOVED*** Native memory allocation (mmap) failed to map 754974720 bytes for committing reserved "
                + "memory.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        assertTrue(headerEvent.isFailed(), "Failed not identified.");
    ***REMOVED***

    @Test
    void testErrorPrintingProblematicFrame() {
        String logLine = "[error occurred during error reporting (printing problematic frame), id 0x7]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADER,
                JdkUtil.LogEventType.HEADER.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
class TestRegisterToMemoryMappingEvent {

    @Test
    void testAdapterForSignature() {
        String logLine = "Adapter for signature: 0x00007fb285046c20 is at code_begin+0 in";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testB() {
        String logLine = "[B";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBufferBlob() {
        String logLine = "BufferBlob (0x00007ffae9049e10) used for adapters";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testC() {
        String logLine = "[C";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClass() {
        String logLine = "com.example.MyClass";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClassWithInnerClass2Packages() {
        RegisterToMemoryMappingEvent priorEvent = new RegisterToMemoryMappingEvent(null);
        String logLine = "java.util.LinkedList$Node";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClassWithInnerClass3Packages() {
        RegisterToMemoryMappingEvent priorEvent = new RegisterToMemoryMappingEvent(null);
        String logLine = "org.jboss.logmanager.CopyOnWriteWeakMap$Node";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCodeBlob() {
        String logLine = "[CodeBlob (0x00007f45c5205f90)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testErrorReportingRegisterInfo() {
        String logLine = "[error occurred during error reporting (printing register info), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testExceptionHandling() {
        String logLine = "exception handling  [0x00007f7ed5a842a0, 0x00007f7ed5a84e80]  3040 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFramesize() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testI() {
        String logLine = "[I";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "RAX=0x0000000000000001 is an unknown value";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testInvokeReturnEntryPoints() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIReturn() {
        String logLine = "ireturn  172 ireturn  [0x00007fff4c023000, 0x00007fff4c023400]  1024 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIWithSpaceAtEnd() {
        String logLine = "[I ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKlass() {
        String logLine = " - klass: 'java/util/LinkedList$Node'";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKlassWithAddress() {
        String logLine = "{0x0000000090057468***REMOVED*** - klass: 'org/elasticsearch/common/util/concurrent/ThreadContext"
                + "$ContextPreservingAbstractRunnable'";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testL() {
        String logLine = "[Ljava.lang.Object;";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLength() {
        String logLine = " - length: 4096";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMethodEntryPoint() {
        String logLine = "method entry point (kind = zerolocals)  [0x00007f95e5519600, 0x00007f95e5519ac0]  "
                + "1216 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoRbxInformation() {
        String logLine = "RBX=";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoRegisterInformation() {
        String logLine = "R13=";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testObject() {
        RegisterToMemoryMappingEvent priorEvent = new RegisterToMemoryMappingEvent(null);
        String logLine = "org.xnio.nio.WorkerThread {0x0000000800c89d28***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "RAX=0x0000000000000001 is an unknown value";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof RegisterToMemoryMappingEvent,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testR8Space() {
        String logLine = "R8 ={method***REMOVED*** {0x00007fdd9a9b91a8***REMOVED*** 'readBlock' '(Ljava/nio/ByteBuffer;II)I' in "
                + "'sun/font/TrueTypeFont'";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRBP() {
        String logLine = "RBP=0x0000000000000003 is an unknown value";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRCX() {
        String logLine = "RCX=";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRDI() {
        String logLine = "RDI=0x0 is NULL";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRDX() {
        String logLine = "RDX=0x0000000000000e8c is an unknown value";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testReturn() {
        String logLine = "return  177 return  [0x00003fff79024b80, 0x00003fff79024f80]  1024 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRip() {
        String logLine = "RIP=0x00007ff8193e0c3b TGenClient.dll";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRSI() {
        String logLine = "RSI=";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRSP() {
        String logLine = "RSP=0x00007f4c86318ce0 is pointing into the stack for thread: 0x00007f4e2de48c30";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSmallR() {
        String logLine = "r0 =0x00007fff7fd30614: <offset 0xbe0614> in /usr/lib/jvm/"
                + "java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.ppc64le/jre/lib/ppc64le/server/libjvm.so at "
                + "0x00007fff7f150000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testStubroutines() {
        String logLine = "StubRoutines::unsafe_arraycopy [0x00007feac1053080, 0x00007feac10530bb[ (59 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    ***REMOVED***
***REMOVED***
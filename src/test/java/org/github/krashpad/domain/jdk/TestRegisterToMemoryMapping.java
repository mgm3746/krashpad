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

import java.io.File;

import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestRegisterToMemoryMapping {

    @Test
    void testAdapterForSignature() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "Adapter for signature: 0x00007fb285046c20 is at code_begin+0 in";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testB() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[B";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testBufferBlob() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "BufferBlob (0x00007ffae9049e10) used for adapters";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testC() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[C";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClass() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "com.example.MyClass";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClassInformation() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset85.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getRegisterToMemoryMappings().isEmpty(),
                LogEventType.REGISTER_TO_MEMORY_MAPPING + " events not identified.");
        assertTrue(fel.getUnidentifiedLogLines().isEmpty(), "Unidentified events.");
        assertEquals(48, fel.getRegisterToMemoryMappings().size(),
                "Events other than " + LogEventType.REGISTER_TO_MEMORY_MAPPING + " identified.");
    }

    @Test
    void testClassWithAddress() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "com.example.MyClass$anon$exportedDoer$macro$1234$1$$Lambda$5668/0x0000000801200000 "
                + "{0x0000000801200000}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClassWithInnerClass2Packages() {
        RegisterToMemoryMapping priorEvent = new RegisterToMemoryMapping(null);
        String logLine = "java.util.LinkedList$Node";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClassWithInnerClass3Packages() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "org.jboss.logmanager.CopyOnWriteWeakMap$Node";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testCodeBlob() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[CodeBlob (0x00007f45c5205f90)]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testErrorReportingRegisterInfo() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[error occurred during error reporting (printing register info), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testExceptionHandling() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "exception handling  [0x00007f7ed5a842a0, 0x00007f7ed5a84e80]  3040 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFakeEntry() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - fake entry for array: NULL";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFields() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - ---- fields (total size 7 words):";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFinal() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - final 'hash' 'I' @12  352138954 (14fd36ca)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFramesize() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "Framesize: 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Register to memory mapping:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testI() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[I";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RAX=0x0000000000000001 is an unknown value";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testInvokeReturnEntryPoints() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "invoke return entry points  [0x00007fff47eb9380, 0x00007fff47eba180]  3584 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testIReturn() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "ireturn  172 ireturn  [0x00007fff4c023000, 0x00007fff4c023400]  1024 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testIWithSpaceAtEnd() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[I ";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testKlass() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - klass: 'java/util/LinkedList$Node'";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testKlassWithAddress() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "{0x0000000090057468} - klass: 'org/elasticsearch/common/util/concurrent/ThreadContext"
                + "$ContextPreservingAbstractRunnable'";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testL() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "[Ljava.lang.Object;";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testLength() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - length: 4096";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testMethodEntryPointNative() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "method entry point (kind = native)  [0x00007fa9b48141c0, 0x00007fa9b4814a20]  2144 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testMethodEntryPointZerolocals() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "method entry point (kind = zerolocals)  [0x00007f95e5519600, 0x00007f95e5519ac0]  "
                + "1216 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNext() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - 'next' 'Ljava/util/HashMap$Node;' @24  a 'java/util/HashMap$Node'{0x00000000e9acd048} "
                + "(e9acd048)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNoRbxInformation() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RBX=";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNoRegisterInformation() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "R13=";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testObject() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "org.xnio.nio.WorkerThread {0x0000000800c89d28}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RAX=0x0000000000000001 is an unknown value";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof RegisterToMemoryMapping,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not parsed.");
    }

    @Test
    void testPrivate() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - private volatile transient 'closed' 'I' @12  0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testProtected() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - protected 'offset' 'J' @40  2820145160 (a8180008 0)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testR8Space() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "R8 ={method} {0x00007fdd9a9b91a8} 'readBlock' '(Ljava/nio/ByteBuffer;II)I' in "
                + "'sun/font/TrueTypeFont'";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRBP() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RBP=0x0000000000000003 is an unknown value";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRCX() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RCX=";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRDI() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RDI=0x0 is NULL";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRDX() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RDX=0x0000000000000e8c is an unknown value";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testReturn() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "return  177 return  [0x00003fff79024b80, 0x00003fff79024f80]  1024 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRip() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RIP=0x00007ff8193e0c3b TGenClient.dll";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRSI() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RSI=";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRSP() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "RSP=0x00007f4c86318ce0 is pointing into the stack for thread: 0x00007f4e2de48c30";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSignature() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - signature: Lcom/example/MyClass$$AnotherClass$$98c697a5";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSize() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - 'size' 'I' @20  2";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSmallR() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "r0 =0x00007fff7fd30614: <offset 0xbe0614> in /usr/lib/jvm/"
                + "java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.ppc64le/jre/lib/ppc64le/server/libjvm.so at "
                + "0x00007fff7f150000";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testStubroutines() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = "StubRoutines::unsafe_arraycopy [0x00007feac1053080, 0x00007feac10530bb[ (59 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testTransient() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - transient 'myField' 'Ljava/lang/ClassValue$ClassValueMap;' @64  NULL (0)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testValue() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - 'value' 'Ljava/lang/Object;' @28  a 'java/lang/ref/WeakReference'{0x00000007fed009e0} "
                + "(ffda013c)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testVolatile() {
        RegisterToMemoryMapping priorLogEvent = new RegisterToMemoryMapping("Register to memory mapping:");
        String logLine = " - volatile 'next' 'Ljava/lang/ref/Reference;' @20  NULL (0)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING,
                JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

}
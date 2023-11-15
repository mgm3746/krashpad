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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestStackSlotToMemoryMapping {

    @Test
    void testAccess() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - access:            public final synchronized";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testArrays() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - arrays:            'java/lang/String'[]";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testBufferBlob() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "BufferBlob (0x00007f486c4e8f10) used for C1 temporary CodeBuffer";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClassAnnotations() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - class annotations:       Array<T>(0x0000000000000000)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClassLoaderData() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - class loader data:  loader data: 0x00007fe5ee621330 of 'bootstrap'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClassTypeAnnotations() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - class type annotations:  Array<T>(0x0000000000000000)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testClosables() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - 'closeables' 'Ljava/util/Collection;' @96  a 'java/util/ArrayList'{0x00000007291a3f90} "
                + "(e52347f2)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testCodeBlob() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "[CodeBlob (0x00007f486c4e8f10)]";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testConstants() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - constants:         constant pool [878] {0x00007fe5c1601308} for 'java/lang/String' "
                + "cache=0x00007fe5c1708118";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testDefaultMethods() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - default_methods:   Array<T>(0x0000000000000000)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testError() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "[error occurred during error reporting (inspecting top of stack), id 0xb, SIGSEGV (0xb) at "
                + "pc=0x00007f68376aea9e]";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFieldAnnotations() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - field annotations:       Array<T>(0x00007fe5c1603fb8)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFields() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - ---- fields (total size 13 words):";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFieldTypeAnnotations() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - field type annotations:  Array<T>(0x0000000000000000)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFinal() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - final 'nextEntryToBeIndexed' 'Lcom/example/MyClass1;' @56  a 'com/example/MyClass2'"
                + "{0x00000007291a3eb0} (e52347d6)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testFramesize() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "Framesize: 0";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testGenericSignature() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - generic signature: 'Ljava/lang/Object;Ljava/io/Serializable;"
                + "Ljava/lang/Comparable<Ljava/lang/String;>;Ljava/lang/CharSequence;'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Stack slot to memory mapping:";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING, JdkUtil.identifyEventType(logLine, null),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testHostClass() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - host class:        NULL";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testInnerClasses() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - inner classes:     Array<T>(0x00007fe5c160a998)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testInstanceSize() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - instance size:     3";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testInvokeReturnEntryPoints() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "invoke return entry points  [0x00007fd8387ff940, 0x00007fd838800360]  2592 bytes";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testItableLength() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - itable length      13 (start addr: 0x0000000800001a00)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testJavaMirror() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - java mirror:       a 'java/lang/Class'{0x000000071085a7b0} = 'java/lang/String'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testKlassSize() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - klass size:        77";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testLeadingCurlyBrace() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "{0x00007f104bc73728} - klass: 'java/util/concurrent/ConcurrentHashMap$Node'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testLength() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - length: 24";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testLinearScanCount() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - 'linearScanCount' 'I' @52  0";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testLocalInterfaces() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - local interfaces:  Array<T>(0x00007fe5c1603070)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testMethodEntryPoint() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "method entry point (kind = native)  [0x00007fa9b48141c0, 0x00007fa9b4814a20]  2144 bytes";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testMethodOrdering() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - method ordering:   Array<T>(0x00007fe5c1600058)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testMethods() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - methods:           Array<T>(0x00007fe5c1604080)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testName() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - name:              'java/lang/String'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNativeMethodEntryPoint() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "native method entry point (kind = native)  [0x00007fc733a8b560, 0x00007fc733a8bfd0]  2672 "
                + "bytes";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNestMembers() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - nest members:     Array<T>(0x00007fe5c160a9d8)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNonStaticFields() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - ---- non-static fields (3 words):";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testNonStaticOopMaps() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - non-static oop maps: 12-12";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testObject() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "org.xnio.nio.WorkerThread {0x0000000800c89d28}";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testPrivate() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - private 'referenceId' 'I' @16  0";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testProtected() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - protected 'm_myMethod' 'I' @20  1000 (3e8";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testPublicStaticFinal() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - public static final strict 'CASE_INSENSITIVE_ORDER' 'Ljava/util/Comparator;' @124";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testReturnEntryPoints() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "return entry points  [0x00007ff3f90b2de0, 0x00007ff3f90b38e0]  2816 bytes";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testRuntimeStub() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "Runtime Stub (0x00007fe5a5af9590): _new_array_nozero_Java";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSequence() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - 'sequence' 'Lcom/example/MyClass;' @92  a 'com/example/MyClass2'{0x00000007291a3f70} "
                + "(e52347ee)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSourceFile() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - source file:       'String.java'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testState() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - state:             fully_initialized";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testStaticFields() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - ---- static fields (3 words):";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testStaticFinal() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - static final 'COMPACT_STRINGS' 'Z' @136";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testStubRoutines() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = "StubRoutines::jbyte_disjoint_arraycopy [0x00007fe5a5a2f160, 0x00007fe5a5a2f208[ (168 bytes)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSub() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - sub:";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testSuper() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - super:             'java/lang/Object'";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testTransInterfaces() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - trans. interfaces: Array<T>(0x00007fe5c1603070)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testVtableLength() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - vtable length      5  (start addr: 0x00000008000019d8)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }

    @Test
    void testWritePosition() {
        StackSlotToMemoryMapping priorLogEvent = new StackSlotToMemoryMapping("Stack slot to memory mapping:");
        String logLine = " - 'writePosition' 'Lcom/example/MyClass1;' @88  a 'com/example/MyClass2'"
                + "{0x00000007291a3f40} (e52347e8)";
        assertEquals(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.");
    }
}
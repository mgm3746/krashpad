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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.domain.BlankLine;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestNativeMemoryTracking {

    @Test
    void testArena() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (arena=7124KB #12155)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testArenaChunk() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-               Arena Chunk (reserved=305008KB, committed=305008KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testArguments() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                 Arguments (reserved=29KB, committed=29KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testClass() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                     Class (reserved=1236886KB, committed=214626KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testClasses() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (classes #32343)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testClassesLoadedHeader() {
        BlankLine priorLogEvent = new BlankLine("");
        String logLine = "Classes loaded (20 events):";
        assertFalse(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " incorrectly identified.");
    }

    @Test
    void testClassSpace() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (  Class space:)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof NativeMemoryTracking,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    }

    @Test
    void testCode() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                      Code (reserved=264372KB, committed=84452KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testCompiler() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                  Compiler (reserved=26862KB, committed=26862KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testFree() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (    free=5775KB)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof NativeMemoryTracking,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    }

    @Test
    void testGc() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                        GC (reserved=417612KB, committed=417612KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Native Memory Tracking:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "Total: reserved=18369225KB, committed=17150661KB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testInstanceClasses() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (  instance classes #7301, array classes #543)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testInternal() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                  Internal (reserved=1385758KB, committed=1385758KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testInternalStatisticsHeader() {
        BlankLine priorLogEvent = new BlankLine("");
        String logLine = "Internal statistics:";
        assertFalse(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " incorrectly identified.");
    }

    @Test
    void testJavaHeap() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                 Java Heap (reserved=8388608KB, committed=8388608KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testLogging() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                   Logging (reserved=7KB, committed=7KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testMalloc() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (malloc=6038KB #64830)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testMallocNoParenthesis() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "       malloc: 29364KB #216192";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testMetadata() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (  Metadata:   ";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testMetaspace() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                 Metaspace (reserved=65697KB, committed=27937KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testMmap() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (mmap: reserved=8388608KB, committed=8388608KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testMmapNoParenthesis() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "       mmap:   reserved=3171724KB, committed=250828KB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testModule() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                    Module (reserved=80KB, committed=80KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testNativeMemoryTracking() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-    Native Memory Tracking (reserved=13441KB, committed=13441KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testObjectMonitors() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-           Object Monitors (reserved=215KB, committed=215KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testOmittingCategories() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "(Omitting categories weighting less than 1KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testOther() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                     Other (reserved=12KB, committed=12KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "Total: reserved=18369225KB, committed=17150661KB";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof NativeMemoryTracking,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    }

    @Test
    void testPreinitMallocs() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "pre-init mallocs: 123, pre-init reallocs: 1, pre-init frees: 12";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testPreinitState() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "Preinit state:entries: 123 (primary: 123, empties: 1234), sum bytes: 12345, longest chain "
                + "length: 1";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testReserved() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (    reserved=65536KB, committed=27776KB)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof NativeMemoryTracking,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    }

    @Test
    void testSafepoint() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                 Safepoint (reserved=8KB, committed=8KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testServiceability() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-            Serviceability (reserved=1KB, committed=1KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testSharedClassSpace() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-        Shared class space (reserved=16384KB, committed=12068KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testStack() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (stack: reserved=6250236KB, committed=6250236KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testStringDeduplication() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-      String Deduplication (reserved=1KB, committed=1KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testSymbol() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                    Symbol (reserved=36100KB, committed=36100KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testSynchronization() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-           Synchronization (reserved=67KB, committed=67KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testSynchronizer() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-              Synchronizer (reserved=614KB, committed=614KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testThread() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                    Thread (reserved=6278193KB, committed=6278193KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testThreadNumber() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (thread #6079)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testTracing() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                   Tracing (reserved=64KB, committed=64KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testTracking() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (tracking overhead=12618KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testUnknown() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "-                   Unknown (reserved=16384KB, committed=0KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    }

    @Test
    void testUsed() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (    used=27501KB)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof NativeMemoryTracking,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    }

    @Test
    void testWaste() {
        NativeMemoryTracking priorLogEvent = new NativeMemoryTracking("Native Memory Tracking:");
        String logLine = "                            (    waste=275KB =0.99%)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof NativeMemoryTracking,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    }
}

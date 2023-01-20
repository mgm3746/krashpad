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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestNativeMemoryTrackingEvent {

    @Test
    void testArena() {
        String logLine = "                            (arena=7124KB ***REMOVED***12155)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testArenaChunk() {
        String logLine = "-               Arena Chunk (reserved=305008KB, committed=305008KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClass() {
        String logLine = "-                     Class (reserved=1236886KB, committed=214626KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClasses() {
        String logLine = "                            (classes ***REMOVED***32343)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCode() {
        String logLine = "-                      Code (reserved=264372KB, committed=84452KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCompiler() {
        String logLine = "-                  Compiler (reserved=26862KB, committed=26862KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testGc() {
        String logLine = "-                        GC (reserved=417612KB, committed=417612KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Native Memory Tracking:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Total: reserved=18369225KB, committed=17150661KB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testInternal() {
        String logLine = "-                  Internal (reserved=1385758KB, committed=1385758KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testInternalStatisticsHeader() {
        String logLine = "Internal statistics:";
        assertFalse(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " incorrectly identified.");
    ***REMOVED***

    @Test
    void testJavaHeap() {
        String logLine = "-                 Java Heap (reserved=8388608KB, committed=8388608KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMalloc() {
        String logLine = "                            (malloc=6038KB ***REMOVED***64830)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNativeMemoryTracking() {
        String logLine = "-    Native Memory Tracking (reserved=13441KB, committed=13441KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNMap() {
        String logLine = "                            (mmap: reserved=8388608KB, committed=8388608KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Total: reserved=18369225KB, committed=17150661KB";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof NativeMemoryTrackingEvent,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testStack() {
        String logLine = "                            (stack: reserved=6250236KB, committed=6250236KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSymbol() {
        String logLine = "-                    Symbol (reserved=36100KB, committed=36100KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThread() {
        String logLine = "-                    Thread (reserved=6278193KB, committed=6278193KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThreadNumber() {
        String logLine = "                            (thread ***REMOVED***6079)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTracking() {
        String logLine = "                            (tracking overhead=12618KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testUnknown() {
        String logLine = "-                   Unknown (reserved=16384KB, committed=0KB)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING,
                JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

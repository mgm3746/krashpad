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
public class TestNativeMemoryTrackingEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Total: reserved=18369225KB, committed=17150661KB";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Total: reserved=18369225KB, committed=17150661KB";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof NativeMemoryTrackingEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Native Memory Tracking:";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testJavaHeap() {
        String logLine = "-                 Java Heap (reserved=8388608KB, committed=8388608KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testNMap() {
        String logLine = "                            (mmap: reserved=8388608KB, committed=8388608KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testClass() {
        String logLine = "-                     Class (reserved=1236886KB, committed=214626KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testClasses() {
        String logLine = "                            (classes ***REMOVED***32343)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testMalloc() {
        String logLine = "                            (malloc=6038KB ***REMOVED***64830)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testThread() {
        String logLine = "-                    Thread (reserved=6278193KB, committed=6278193KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testThreadNumber() {
        String logLine = "                            (thread ***REMOVED***6079)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testStack() {
        String logLine = "                            (stack: reserved=6250236KB, committed=6250236KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testArena() {
        String logLine = "                            (arena=7124KB ***REMOVED***12155)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testCode() {
        String logLine = "-                      Code (reserved=264372KB, committed=84452KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testGc() {
        String logLine = "-                        GC (reserved=417612KB, committed=417612KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testCompiler() {
        String logLine = "-                  Compiler (reserved=26862KB, committed=26862KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testInternal() {
        String logLine = "-                  Internal (reserved=1385758KB, committed=1385758KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testSymbol() {
        String logLine = "-                    Symbol (reserved=36100KB, committed=36100KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testNativeMemoryTracking() {
        String logLine = "-    Native Memory Tracking (reserved=13441KB, committed=13441KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testTracking() {
        String logLine = "                            (tracking overhead=12618KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testArenaChunk() {
        String logLine = "-               Arena Chunk (reserved=305008KB, committed=305008KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

    public void testUnknown() {
        String logLine = "-                   Unknown (reserved=16384KB, committed=0KB)";
        Assert.assertTrue(JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING);
    ***REMOVED***

***REMOVED***

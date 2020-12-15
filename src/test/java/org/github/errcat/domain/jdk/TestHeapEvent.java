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
public class TestHeapEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Event: 1.905 GC heap before";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEAP);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Event: 1.905 GC heap before";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testHeaderHeap() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testHeaderGcHeapHistory() {
        String logLine = "GC Heap History (48 events):";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testHeapBeforeGc() {
        String logLine = "{Heap before GC invocations=1 (full 0):";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testHeapAfterGc() {
        String logLine = "Heap after GC invocations=1 (full 0):";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testHeapAfterGcWithTimestamp() {
        String logLine = "Event: 345.632 GC heap after";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testPsYoungGen() {
        String logLine = " PSYoungGen      total 153088K, used 116252K [0x00000000eab00000, 0x00000000f5580000, "
                + "0x0000000100000000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
        HeapEvent event = new HeapEvent(logLine);
        Assert.assertTrue("Young gen not identified.", event.isYoungGen());
    ***REMOVED***

    public void testEden() {
        String logLine = "  eden space 131584K, 88% used [0x00000000eab00000,0x00000000f1c87328,0x00000000f2b80000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testFrom() {
        String logLine = "  from space 21504K, 0% used [0x00000000f4080000,0x00000000f4080000,0x00000000f5580000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testTo() {
        String logLine = "  to   space 21504K, 0% used [0x00000000f2b80000,0x00000000f2b80000,0x00000000f4080000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testParNew() {
        String logLine = " par new generation   total 766784K, used 37193K [0x0000000261000000, 0x0000000295000000, "
                + "0x0000000295000000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
        HeapEvent event = new HeapEvent(logLine);
        Assert.assertTrue("Young gen not identified.", event.isYoungGen());
    ***REMOVED***

    public void testParOldGen() {
        String logLine = " ParOldGen       total 699392K, used 91187K [0x00000000c0000000, 0x00000000eab00000, "
                + "0x00000000eab00000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
        HeapEvent event = new HeapEvent(logLine);
        Assert.assertTrue("Old gen not identified.", event.isOldGen());
    ***REMOVED***

    public void testConcurrentMarkSweep() {
        String logLine = " concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, "
                + "0x00000007c0000000, 0x00000007c0000000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
        HeapEvent event = new HeapEvent(logLine);
        Assert.assertTrue("Old gen not identified.", event.isOldGen());
    ***REMOVED***

    public void testObjectSpace() {
        String logLine = "  object space 349696K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000d5580000)";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testMetaspace() {
        String logLine = " Metaspace       used 19510K, capacity 21116K, committed 21248K, reserved 1069056K";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
        HeapEvent event = new HeapEvent(logLine);
        Assert.assertTrue("Metaspace not identified.", event.isMetaspace());
    ***REMOVED***

    public void testClassSpace() {
        String logLine = "  class space    used 1971K, capacity 2479K, committed 2560K, reserved 1048576K";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***

    public void testEndBrace() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapEvent);
    ***REMOVED***
***REMOVED***

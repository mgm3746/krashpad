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

import org.github.joa.domain.GarbageCollector;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestHeapEvent {

    @Test
    void testClassSpace() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "  class space    used 1971K, capacity 2479K, committed 2560K, reserved 1048576K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testConcurrentMarkSweep() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, "
                + "0x00000007c0000000, 0x00000007c0000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isOldGen(), "Old gen not identified.");
    ***REMOVED***

    @Test
    void testDefNewGeneration() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " def new generation   total 629440K, used 511995K [0x00000006c0000000, 0x00000006eaaf0000, "
                + "0x0000000715550000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isYoungGen(), "New gen not identified.");
    ***REMOVED***

    @Test
    void testEden() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "  eden space 131584K, 88% used [0x00000000eab00000,0x00000000f1c87328,0x00000000f2b80000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFrom() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "  from space 21504K, 0% used [0x00000000f4080000,0x00000000f4080000,0x00000000f5580000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testG1() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " garbage-first heap   total 1933312K, used 1030565K [0x0000000500000000, 0x0000000800000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.G1), "G1 heap event not recognized.");
    ***REMOVED***

    @Test
    void testG1Region() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "  region size 2048K, 417 young (854016K), 55 survivors (112640K)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeaderHeap() {
        HeapEvent priorEvent = null;
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        HeapEvent priorEvent = null;
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMetaspace() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " Metaspace       used 19510K, capacity 21116K, committed 21248K, reserved 1069056K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isMetaspace(), "Metaspace not identified.");
    ***REMOVED***

    @Test
    void testMetaspaceJdk17() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " Metaspace       used 117K, committed 320K, reserved 1056768K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isMetaspace(), "Metaspace not identified.");
    ***REMOVED***

    @Test
    void testObjectSpace() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "  object space 349696K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000d5580000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParNew() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " par new generation   total 766784K, used 37193K [0x0000000261000000, 0x0000000295000000, "
                + "0x0000000295000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isYoungGen(), "Young gen not identified.");
    ***REMOVED***

    @Test
    void testParOldGen() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " ParOldGen       total 699392K, used 91187K [0x00000000c0000000, 0x00000000eab00000, "
                + "0x00000000eab00000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isOldGen(), "Old gen not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        HeapEvent priorEvent = null;
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof HeapEvent,
                JdkUtil.LogEventType.HEAP.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPsYoungGen() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " PSYoungGen      total 153088K, used 116252K [0x00000000eab00000, 0x00000000f5580000, "
                + "0x0000000100000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isYoungGen(), "Young gen not identified.");
    ***REMOVED***

    @Test
    void testShenandoah() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset74.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(fel.getGarbageCollectors().size(), 1, "Number of collectors incorrect.");
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                "Uknown collector incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH),
                "Shenandoah heap event not recognized.");
        assertFalse(fel.getUnidentifiedLogLines().size() > 0, "Uknown log lines.");
    ***REMOVED***

    @Test
    void testShenandoahCollectionSet() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " - map (vanilla): 0x00007ffb91f5e3d1";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahCollectionSetHeader() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "Collection set:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahHeader() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "Shenandoah Heap";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahRegions() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " 2867 x 2048K regions";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahReservedRegion() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " - [0x000000067a200000, 0x00000007e0800000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahReservedRegionHeader() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "Reserved region:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahSoftMax() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " 3456M max, 3456M soft max, 3200M committed, 2325M used";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.SHENANDOAH), "Shenandoah heap event not recognized.");
    ***REMOVED***

    @Test
    void testShenandoahStatus() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "Status: has forwarded objects, updating refs, degenerated gc, not cancelled";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShenandoahTotal() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " 5734M total, 5734M committed, 3795M used";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.SHENANDOAH), "Shenandoah heap event not recognized.");
    ***REMOVED***

    @Test
    void testTenuredGeneration() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = " tenured generation   total 2165440K, used 937560K [0x000000073bd50000, 0x00000007c0000000, "
                + "0x00000007c0000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        HeapEvent event = new HeapEvent(logLine);
        assertTrue(event.isOldGen(), "Old gen not identified.");
    ***REMOVED***

    @Test
    void testThe() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "   the space 2165440K,  43% used [0x000000073bd50000, 0x0000000774e145c0, "
                + "0x0000000774e14600, 0x00000007c0000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTo() {
        HeapEvent priorEvent = new HeapEvent(null);
        String logLine = "  to   space 21504K, 0% used [0x00000000f2b80000,0x00000000f2b80000,0x00000000f4080000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    ***REMOVED***

***REMOVED***

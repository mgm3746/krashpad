/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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
class TestHeap {

    @Test
    void testClassSpace() {
        Heap priorEvent = new Heap(null);
        String logLine = "  class space    used 1971K, capacity 2479K, committed 2560K, reserved 1048576K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testConcurrentMarkSweep() {
        Heap priorEvent = new Heap(null);
        String logLine = " concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, "
                + "0x00000007c0000000, 0x00000007c0000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isOldGen(), "Old gen not identified.");
    }

    @Test
    void testDefNewGeneration() {
        Heap priorEvent = new Heap(null);
        String logLine = " def new generation   total 629440K, used 511995K [0x00000006c0000000, 0x00000006eaaf0000, "
                + "0x0000000715550000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isYoungGen(), "New gen not identified.");
    }

    @Test
    void testEden() {
        Heap priorEvent = new Heap(null);
        String logLine = "  eden space 131584K, 88% used [0x00000000eab00000,0x00000000f1c87328,0x00000000f2b80000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testError() {
        Heap priorEvent = new Heap(null);
        String logLine = "[error occurred during error reporting (printing heap information), id 0xb, SIGSEGV (0xb) "
                + "at pc=0x00007f256fdc78aa]";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap logEvent = new Heap(logLine);
        assertTrue(logEvent.isErrorOccurredDuringErrorReporting(), "Error not identified.");
    }

    @Test
    void testFrom() {
        Heap priorEvent = new Heap(null);
        String logLine = "  from space 21504K, 0% used [0x00000000f4080000,0x00000000f4080000,0x00000000f5580000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testG1() {
        Heap priorEvent = new Heap(null);
        String logLine = " garbage-first heap   total 1933312K, used 1030565K [0x0000000500000000, 0x0000000800000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.G1), "G1 heap event not recognized.");
    }

    @Test
    void testG1Region() {
        Heap priorEvent = new Heap(null);
        String logLine = "  region size 2048K, 417 young (854016K), 55 survivors (112640K)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testHeaderHeap() {
        Heap priorEvent = null;
        String logLine = "Heap:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        Heap priorEvent = null;
        String logLine = "Heap:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testLocalityGroup() {
        Heap priorEvent = new Heap(null);
        String logLine = "    lgrp 0 space 262400K, 5% used [0x00000000d5580000,0x00000000d62cdd08,"
                + "0x00000000e55c0000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testMetaspace() {
        Heap priorEvent = new Heap(null);
        String logLine = " Metaspace       used 19510K, capacity 21116K, committed 21248K, reserved 1069056K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isMetaspace(), "Metaspace not identified.");
    }

    @Test
    void testMetaspaceJdk17() {
        Heap priorEvent = new Heap(null);
        String logLine = " Metaspace       used 117K, committed 320K, reserved 1056768K";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isMetaspace(), "Metaspace not identified.");
    }

    @Test
    void testObjectSpace() {
        Heap priorEvent = new Heap(null);
        String logLine = "  object space 349696K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000d5580000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testParNew() {
        Heap priorEvent = new Heap(null);
        String logLine = " par new generation   total 766784K, used 37193K [0x0000000261000000, 0x0000000295000000, "
                + "0x0000000295000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isYoungGen(), "Young gen not identified.");
    }

    @Test
    void testParOldGen() {
        Heap priorEvent = new Heap(null);
        String logLine = " ParOldGen       total 699392K, used 91187K [0x00000000c0000000, 0x00000000eab00000, "
                + "0x00000000eab00000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isOldGen(), "Old gen not identified.");
    }

    @Test
    void testParseLogLine() {
        Heap priorEvent = null;
        String logLine = "Heap:";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof Heap,
                JdkUtil.LogEventType.HEAP.toString() + " not parsed.");
    }

    @Test
    void testPsYoungGen() {
        Heap priorEvent = new Heap(null);
        String logLine = " PSYoungGen      total 153088K, used 116252K [0x00000000eab00000, 0x00000000f5580000, "
                + "0x0000000100000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isYoungGen(), "Young gen not identified.");
    }

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
    }

    @Test
    void testShenandoahCollectionSet() {
        Heap priorEvent = new Heap(null);
        String logLine = " - map (vanilla): 0x00007ffb91f5e3d1";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahCollectionSetHeader() {
        Heap priorEvent = new Heap(null);
        String logLine = "Collection set:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahHeader() {
        Heap priorEvent = new Heap(null);
        String logLine = "Shenandoah Heap";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahRegions() {
        Heap priorEvent = new Heap(null);
        String logLine = " 2867 x 2048K regions";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahReservedRegion() {
        Heap priorEvent = new Heap(null);
        String logLine = " - [0x000000067a200000, 0x00000007e0800000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahReservedRegionHeader() {
        Heap priorEvent = new Heap(null);
        String logLine = "Reserved region:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahSoftMax() {
        Heap priorEvent = new Heap(null);
        String logLine = " 3456M max, 3456M soft max, 3200M committed, 2325M used";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.SHENANDOAH), "Shenandoah heap event not recognized.");
    }

    @Test
    void testShenandoahStatus() {
        Heap priorEvent = new Heap(null);
        String logLine = "Status: has forwarded objects, updating refs, degenerated gc, not cancelled";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testShenandoahTotal() {
        Heap priorEvent = new Heap(null);
        String logLine = " 5734M total, 5734M committed, 3795M used";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.SHENANDOAH), "Shenandoah heap event not recognized.");
    }

    @Test
    void testTenuredGeneration() {
        Heap priorEvent = new Heap(null);
        String logLine = " tenured generation   total 2165440K, used 937560K [0x000000073bd50000, 0x00000007c0000000, "
                + "0x00000007c0000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        Heap event = new Heap(logLine);
        assertTrue(event.isOldGen(), "Old gen not identified.");
    }

    @Test
    void testThe() {
        Heap priorEvent = new Heap(null);
        String logLine = "   the space 2165440K,  43% used [0x000000073bd50000, 0x0000000774e145c0, "
                + "0x0000000774e14600, 0x00000007c0000000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testTo() {
        Heap priorEvent = new Heap(null);
        String logLine = "  to   space 21504K, 0% used [0x00000000f2b80000,0x00000000f2b80000,0x00000000f4080000)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
    }

    @Test
    void testZgc() {
        Heap priorEvent = new Heap(null);
        String logLine = " ZHeap           used 3999154M, capacity 4710400M, max capacity 4710400M";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.HEAP,
                JdkUtil.LogEventType.HEAP.toString() + " not identified.");
        assertTrue(logLine.matches(JdkRegEx.Z), "Z heap event not recognized.");
    }

}

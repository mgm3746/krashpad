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
class TestHeapRegionsEvent {

    @Test
    void testAc() {
        String logLine = "AC   0  O    TS     0 PTAMS 0x00000005d0400000 NTAMS 0x00000005d0400000 space 4096K, 100% "
                + "used [0x00000005d0000000, 0x00000005d0400000)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBarrierSet() {
        String logLine = "ShenandoahBarrierSet";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBte() {
        String logLine = "BTE=bottom/top/end, U=used, T=TLAB allocs, G=GCLAB allocs, S=shared allocs, L=live data";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testEu() {
        String logLine = "EU=empty-uncommitted, EC=empty-committed, R=regular, H=humongous start, HC=humongous "
                + "continuation, CS=collection set, T=trash, P=pinned";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Heap Regions:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "|    0|CS |BTE    67a200000,    67a400000,    67a400000|TAMS    67a400000|UWM    "
                + "67a400000|U  2048K|T  2047K|G     0B|S    56B|L 31152B|CP   0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "|    0|CS |BTE    67a200000,    67a400000,    67a400000|TAMS    67a400000|UWM    "
                + "67a400000|U  2048K|T  2047K|G     0B|S    56B|L 31152B|CP   0";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof HeapRegionsEvent,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testR() {
        String logLine = "R=root, CP=critical pins, TAMS=top-at-mark-start, UWM=update watermark";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSn() {
        String logLine = "SN=alloc sequence number";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_REGIONS,
                JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

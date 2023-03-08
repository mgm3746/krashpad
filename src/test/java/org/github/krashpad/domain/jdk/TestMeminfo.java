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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestMeminfo {

    @Test
    void testActive() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Active:         27699800 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testAnonPages() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "AnonPages:      24234268 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBuffers() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Buffers:          817980 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCached() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Cached:          4248912 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCommitLimit() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "CommitLimit:    13329920 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCommitLimitAs() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Committed_AS:   13301540 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testDirectMap() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "DirectMap4k:        8192 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testDirty() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Dirty:              1732 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFileHugePages() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFilePmdMapped() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHugePages() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "MemTotal:       65305448 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testInactive() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Inactive:        1601588 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKernelStack() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "KernelStack:       67264 k";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKReclaimable() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "KReclaimable:     241604 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMapped() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Mapped:           378036 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemAvailable() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "MemAvailable:    9627712 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMlocked() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNfs() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPageTables() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "PageTables:        72112 k";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "MemTotal:       65305448 kB";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof Meminfo,
                JdkUtil.LogEventType.MEMINFO.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPerCpu() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Percpu:             3584 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testShmem() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Shmem:             48464 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSlab() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "Slab:            4564604 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSReclaimable() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "SReclaimable:    4475112 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSUnreclaim() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "SUnreclaim:        89492 k";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSwapCached() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "SwapCached:            0 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testUnevictable() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmallocTotal() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "VmallocTotal:   34359738367 kB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testWriteback() {
        Meminfo priorLogEvent = new Meminfo("***REMOVED***");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.MEMINFO,
                JdkUtil.LogEventType.MEMINFO.toString() + " not identified.");
    ***REMOVED***
***REMOVED***
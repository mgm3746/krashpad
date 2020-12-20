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
public class TestMeminfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "MemTotal:       65305448 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "MemTotal:       65305448 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof MeminfoEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testBuffers() {
        String logLine = "Buffers:          817980 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testCached() {
        String logLine = "Cached:          4248912 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testSwapCached() {
        String logLine = "SwapCached:            0 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testActive() {
        String logLine = "Active:         27699800 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testInactive() {
        String logLine = "Inactive:        1601588 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testUnevictable() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testMlocked() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testDirty() {
        String logLine = "Dirty:              1732 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testWriteback() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testAnonPages() {
        String logLine = "AnonPages:      24234268 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testMapped() {
        String logLine = "Mapped:           378036 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testShmem() {
        String logLine = "Shmem:             48464 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testSlab() {
        String logLine = "Slab:            4564604 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testSReclaimable() {
        String logLine = "SReclaimable:    4475112 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testSUnreclaim() {
        String logLine = "SUnreclaim:        89492 k";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testKernelStack() {
        String logLine = "KernelStack:       67264 k";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testPageTables() {
        String logLine = "PageTables:        72112 k";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testNfs() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testVmallocTotal() {
        String logLine = "VmallocTotal:   34359738367 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testHugePages() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testDirectMap() {
        String logLine = "DirectMap4k:        8192 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testMemAvailable() {
        String logLine = "MemAvailable:    9627712 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***

    public void testPerCpu() {
        String logLine = "Percpu:             3584 kB";
        Assert.assertTrue(JdkUtil.LogEventType.MEMINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMINFO);
    ***REMOVED***
***REMOVED***
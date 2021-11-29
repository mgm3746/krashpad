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
class TestGcPreciousLogEvent {

    @Test
    void testIdentity() {
        String logLine = "GC Precious Log:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GC_PRECIOUS_LOG,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "GC Precious Log:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testCpus() {
        String logLine = " CPUs: 12 total, 12 available";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testMemory() {
        String logLine = " Memory: 31907M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testLargePageSupport() {
        String logLine = " Large Page Support: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testNumaSupport() {
        String logLine = " NUMA Support: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testCompressedOops() {
        String logLine = " Compressed Oops: Enabled (Zero based)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testHeapRegionSize() {
        String logLine = " Heap Region Size: 4M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testHeapMinCapacity() {
        String logLine = " Heap Min Capacity: 8M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testHeapInitialCapacity() {
        String logLine = " Heap Initial Capacity: 500M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testHeapMaxCapacity() {
        String logLine = " Heap Max Capacity: 7980M";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPreTouch() {
        String logLine = " Pre-touch: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testParallelWorkers() {
        String logLine = " Parallel Workers: 10";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testConcurrentWorkers() {
        String logLine = " Concurrent Workers: 3";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testConcurrentRefinementWorkers() {
        String logLine = " Concurrent Refinement Workers: 10";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPeriodicGc() {
        String logLine = " Periodic GC: Disabled";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GcPreciousLogEvent,
                JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
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
class TestInternalStatistic {

    @Test
    void testIdentity() {
        String logLine = "Internal statistics:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumAllocsFailedLimit() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_allocs_failed_limit: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumArenaBirths() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_arena_births: 4.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumArenaDeaths() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_arena_deaths: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunkEnlarged() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_chunks_enlarged: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunkMerges() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_chunk_merges: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunkSplits() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_chunk_splits: 2.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunksReturnedToFreelist() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_chunks_returned_to_freelist: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunksTakenFromFreelist() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_chunks_taken_from_freelist: 5.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumInconsistentStats() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_inconsistent_stats: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumPurges() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_purges: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumSpaceCommitted() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_space_committed: 5.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumSpaceUncommitted() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_space_uncommitted: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumVsnodesBirths() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_vsnodes_births: 2.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumVsnodesDeaths() {
        InternalStatistic priorLogEvent = new InternalStatistic("Internal statistics:");
        String logLine = "num_vsnodes_deaths: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.INTERNAL_STATISTICS,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Internal statistics:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof InternalStatistic,
                JdkUtil.LogEventType.INTERNAL_STATISTICS.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***

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
class TestStatisticsEvent {

    @Test
    void testIdentity() {
        String logLine = "Internal statistics:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Internal statistics:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof StatisticsEvent,
                JdkUtil.LogEventType.STATISTICS.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testNumAllocsFailedLimit() {
        String logLine = "num_allocs_failed_limit: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumArenaBirths() {
        String logLine = "num_arena_births: 4.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumArenaDeaths() {
        String logLine = "num_arena_deaths: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumVsnodesBirths() {
        String logLine = "num_vsnodes_births: 2.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumVsnodesDeaths() {
        String logLine = "num_vsnodes_deaths: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumSpaceCommitted() {
        String logLine = "num_space_committed: 5.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumSpaceUncommitted() {
        String logLine = "num_space_uncommitted: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunksReturnedToFreelist() {
        String logLine = "num_chunks_returned_to_freelist: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunksTakenFromFreelist() {
        String logLine = "num_chunks_taken_from_freelist: 5.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunkMerges() {
        String logLine = "num_chunk_merges: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunkSplits() {
        String logLine = "num_chunk_splits: 2.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumChunkEnlarged() {
        String logLine = "num_chunks_enlarged: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumPurges() {
        String logLine = "num_purges: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumInconsistentStats() {
        String logLine = "num_inconsistent_stats: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.STATISTICS,
                JdkUtil.LogEventType.STATISTICS.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

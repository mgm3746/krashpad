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
class TestProcessMemory {

    @Test
    void testCHeapOutstandingAllocations() {
        String logLine = "C-Heap outstanding allocations: 323983K (may have wrapped)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ProcessMemory,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testGlibcMallocTunables() {
        String logLine = "glibc malloc tunables: MALLOC_ARENA_MAX=1";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ProcessMemory,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Process Memory:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.PROCESS_MEMORY,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Process Memory:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ProcessMemory,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testResidentSetSize() {
        String logLine = "Resident Set Size: 9169564K (peak: 9198848K) (anon: 9144372K, file: 25192K, shmem: 0K)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ProcessMemory,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testSwappedOut() {
        String logLine = "Swapped out: 0K";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ProcessMemory,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testVirtualSize() {
        String logLine = "Virtual Size: 11384200K (peak: 19821176K)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ProcessMemory,
                JdkUtil.LogEventType.PROCESS_MEMORY.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***

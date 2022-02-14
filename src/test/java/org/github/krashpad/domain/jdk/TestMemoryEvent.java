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
class TestMemoryEvent {

    @Test
    void test10Digits() {
        String logLine = "Memory: 4k page, physical 1584737836k(118786168k free), swap 33554428k(33554428k free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentProcessCommitCharge() {
        String logLine = "current process commit charge (\"private bytes\"): 61M, peak: 8253M";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentProcessWorkingSet() {
        String logLine = "current process WorkingSet (physical memory assigned to process): 11M, peak: 11M";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemory() {
        String logLine = "Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemory64kPage() {
        String logLine = "Memory: 64k page, physical 254200832k(247780928k free), swap 4194240k(4069376k free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemory9Digits() {
        String logLine = "Memory: 4k page, physical 263868708k(8753840k free), swap 2097148k(36k free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemoryWindows() {
        String logLine = "Memory: 4k page, system-wide physical 16383M (5994M free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoSwapInfo() {
        String logLine = "Memory: 8k page, physical 267386880k(88275744k free)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MemoryEvent,
                JdkUtil.LogEventType.MEMORY.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testTotalPageFile() {
        String logLine = "TotalPageFile size 20479M (AvailPageFile size 7532M)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MEMORY,
                JdkUtil.LogEventType.MEMORY.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
class TestEventEvent {

    @Test
    void testCoalescedSafepoint() {
        String logLine = "Event: 38778.824 Executing coalesced safepoint VM operation: RevokeBias";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testConcurrent() {
        String logLine = "Event: 1683619.717 Concurrent reset";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Event: 6665.311 Executing VM operation: RevokeBias done";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLoadedSharedLibrary() {
        String logLine = "Event: 0.001 Loaded shared library "
                + "/usr/lib/jvm/java-11-openjdk-11.0.8.10-0.el8_2.x86_64/lib/libzip.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLoadingClass() {
        String logLine = "Event: 6669.549 loading class projectNature";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Event: 6665.311 Executing VM operation: RevokeBias done";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof EventEvent,
                JdkUtil.LogEventType.EVENT.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPause() {
        String logLine = "Event: 1683619.731 Pause Init Mark (unload classes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testProtectingMemory() {
        String logLine = "Event: 949.037 Protecting memory [0x00007fffaa12b000,0x00007fffaa12f000] with protection "
                + "modes 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThreadAdded() {
        String logLine = "Event: 6668.297 Thread 0x00007fefe944f000 Thread added: 0x00007fefe944f000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThreadExited() {
        String logLine = "Event: 6665.311 Thread 0x00007fefe944f000 Thread exited: 0x00007fefe944f00";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

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
class TestEventEvent {

    @Test
    void testCoalescedSafepoint() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 38778.824 Executing coalesced safepoint VM operation: RevokeBias";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testConcurrent() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 1683619.717 Concurrent reset";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
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
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 6665.311 Executing VM operation: RevokeBias done";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLoadedSharedLibrary() {
        String priorLogLine = "***REMOVED***";
        EventEvent priorEvent = new EventEvent(priorLogLine);
        String logLine = "Event: 0.001 Loaded shared library "
                + "/usr/lib/jvm/java-11-openjdk-11.0.8.10-0.el8_2.x86_64/lib/libzip.so";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testLoadingClass() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 6669.549 loading class projectNature";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoEvents() {
        String priorLogLine = "Events (0 events):";
        EventEvent priorEvent = new EventEvent(priorLogLine);
        String logLine = "No Events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 6665.311 Executing VM operation: RevokeBias done";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof EventEvent,
                JdkUtil.LogEventType.EVENT.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPause() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 1683619.731 Pause Init Mark (unload classes)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testProtectingMemory() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 949.037 Protecting memory [0x00007fffaa12b000,0x00007fffaa12f000] with protection "
                + "modes 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRevokeBias() {
        String priorLogLine = "***REMOVED***";
        EventEvent priorEvent = new EventEvent(priorLogLine);
        String logLine = "Event: 215.872 Executing VM operation: RevokeBias";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThreadAdded() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 6668.297 Thread 0x00007fefe944f000 Thread added: 0x00007fefe944f000";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testThreadExited() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 6665.311 Thread 0x00007fefe944f000 Thread exited: 0x00007fefe944f00";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testUncommonTrap() {
        EventEvent priorLogEvent = new EventEvent("***REMOVED***");
        String logLine = "Event: 215.872 Thread 0x000055e856d9b800 Uncommon trap: trap_request=0xffffff65 "
                + "fr.pc=0x00007f4a7bfdd140";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.EVENT,
                JdkUtil.LogEventType.EVENT.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

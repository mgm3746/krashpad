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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestElapsedTimeEvent {

    @Test
    void testElapsedTime() {
        String logLine = "elapsed time: 855185 seconds (9d 21h 33m 5s)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ELAPSED_TIME,
                JdkUtil.LogEventType.ELAPSED_TIME.toString() + " not identified.");
        ElapsedTimeEvent event = new ElapsedTimeEvent(logLine);
        assertEquals("9d 21h 33m 5s", event.getElapsedTime(), "Elapsed time not correct.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "elapsed time: 855185 seconds (9d 21h 33m 5s)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ELAPSED_TIME,
                JdkUtil.LogEventType.ELAPSED_TIME.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "elapsed time: 855185 seconds (9d 21h 33m 5s)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ElapsedTimeEvent,
                JdkUtil.LogEventType.ELAPSED_TIME.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testSecondsOnly() {
        String logLine = "elapsed time: 228058 seconds";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ELAPSED_TIME,
                JdkUtil.LogEventType.ELAPSED_TIME.toString() + " not identified.");
        ElapsedTimeEvent event = new ElapsedTimeEvent(logLine);
        assertEquals("228058 seconds", event.getElapsedTime(), "Elapsed time not correct.");
    ***REMOVED***

    @Test
    void testZero() {
        String logLine = "elapsed time: 0.606413 seconds (0d 0h 0m 0s)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ELAPSED_TIME,
                JdkUtil.LogEventType.ELAPSED_TIME.toString() + " not identified.");
        ElapsedTimeEvent event = new ElapsedTimeEvent(logLine);
        assertEquals("0d 0h 0m 0s", event.getElapsedTime(), "Elapsed time not correct.");
    ***REMOVED***
***REMOVED***

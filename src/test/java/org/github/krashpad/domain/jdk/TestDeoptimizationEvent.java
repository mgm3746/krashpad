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
class TestDeoptimizationEvent {

    /**
     * TODO: Does this error apply to multiple events and should be moved to a dedicated ErrorEvent?
     */
    @Test
    void testError() {
        String logLine = "[error occurred during error reporting (printing ring buffers), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Deoptimization events (250 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Event: 5689.207 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if "
                + "action=reinterpret pc=0x00007ff0df4a0408 "
                + "method=org.eclipse.jface.text.ListLineTracker.getLineLength(I)I @ 28";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Event: 5689.207 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if "
                + "action=reinterpret pc=0x00007ff0df4a0408 "
                + "method=org.eclipse.jface.text.ListLineTracker.getLineLength(I)I @ 28";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof DeoptimizationEvent,
                JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***

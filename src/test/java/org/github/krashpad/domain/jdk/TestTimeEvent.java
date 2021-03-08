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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestTimeEvent {

    @Test
    void testIdentity() {
        String logLine = "time: Tue Aug 18 14:10:59 2020";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TIME,
                JdkUtil.LogEventType.TIME.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "time: Tue Aug 18 14:10:59 2020";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof TimeEvent,
                JdkUtil.LogEventType.TIME.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testTime() {
        String logLine = "time: Tue Aug 18 14:10:59 2020";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TIME,
                JdkUtil.LogEventType.TIME.toString() + " not identified.");
        TimeEvent event = new TimeEvent(logLine);
        assertEquals("Tue Aug 18 14:10:59 2020", event.getTime(), "Time not correct.");
    ***REMOVED***
***REMOVED***
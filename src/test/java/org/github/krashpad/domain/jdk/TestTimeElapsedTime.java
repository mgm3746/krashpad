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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestTimeElapsedTime {

    @Test
    void testElapsedTime() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TIME_ELAPSED_TIME,
                JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not identified.");
        TimeElapsedTime event = new TimeElapsedTime(logLine);
        assertEquals("0d 0h 15m 56s", event.getLiteral(), "Elapsed time not correct.");
        assertEquals(956000L, event.getUptime(), "Uptime not correct.");
    }

    @Test
    void testIdentity() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TIME_ELAPSED_TIME,
                JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof TimeElapsedTime,
                JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not parsed.");
    }

    @Test
    void testTime() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TIME_ELAPSED_TIME,
                JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not identified.");
        TimeElapsedTime event = new TimeElapsedTime(logLine);
        assertEquals("Tue May  5 18:32:04 2020 CEST", event.getTimeString(), "Time not correct.");
    }
}
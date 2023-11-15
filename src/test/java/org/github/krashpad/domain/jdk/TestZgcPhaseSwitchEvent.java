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
class TestZgcPhaseSwitchEvent {

    @Test
    void testHeader() {
        String logLine = "ZGC Phase Switch (0 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT,
                JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        ZgcPhaseSwitchEvent priorLogEvent = new ZgcPhaseSwitchEvent("ZGC Phase Switch (0 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT,
                JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT.toString() + " not identified.");
    }

    @Test
    void testNoEventsLowercaseE() {
        ZgcPhaseSwitchEvent priorLogEvent = new ZgcPhaseSwitchEvent("ZGC Phase Switch (0 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT,
                JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        ZgcPhaseSwitchEvent priorLogEvent = new ZgcPhaseSwitchEvent("Internal exceptions (250 events):");
        String logLine = "No events";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof ZgcPhaseSwitchEvent,
                JdkUtil.LogEventType.ZGC_PHASE_SWITCH_EVENT.toString() + " not parsed.");
    }
}

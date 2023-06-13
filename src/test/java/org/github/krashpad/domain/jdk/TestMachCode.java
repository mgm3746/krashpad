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
class TestMachCode {

    @Test
    void testFooter() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "[/MachCode]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testFullLine() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  0x00007ff4011cb4a0: 448b 5608 | 49bb 0000 | 0000 0800 | 0000 4d03 | d349 3bc2 | 0f85 c6b7 |"
                + " f2f7 6690 | 0f1f 4000";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "[MachCode]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.MACH_CODE,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "[MachCode]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }

    @Test
    void testPartialLine() {
        MachCode priorLogEvent = new MachCode("[MachCode]");
        String logLine = "  0x00007ff4011cb5a0: e95b 25fd | f7e8 0000 | 0000 4883 | 2c24 05e9 | 6c45 f3f7 | f4f4 f4f4";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof MachCode,
                JdkUtil.LogEventType.MACH_CODE.toString() + " not parsed.");
    }
}

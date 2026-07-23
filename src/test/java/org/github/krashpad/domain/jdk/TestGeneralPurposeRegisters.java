/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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
class TestGeneralPurposeRegisters {

    @Test
    void testDashLine() {
        GeneralPurposeRegisters priorEvent = new GeneralPurposeRegisters(null);
        String logLine = "--------------------------";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GeneralPurposeRegisters,
                JdkUtil.LogEventType.GENERAL_PURPOSE_REGISTERS.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "General Purpose Registers:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GENERAL_PURPOSE_REGISTERS,
                JdkUtil.LogEventType.GENERAL_PURPOSE_REGISTERS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "General Purpose Registers:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GeneralPurposeRegisters,
                JdkUtil.LogEventType.GENERAL_PURPOSE_REGISTERS.toString() + " not parsed.");
    }

    @Test
    void testR14() {
        GeneralPurposeRegisters priorEvent = new GeneralPurposeRegisters(null);
        String logLine = "  r14 = 0x000003ff95560ef1    r15 = 0x000003ff95560e31  |  r14 =           4396256988913    "
                + "r15 =           4396256988721";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GeneralPurposeRegisters,
                JdkUtil.LogEventType.GENERAL_PURPOSE_REGISTERS.toString() + " not parsed.");
    }

}

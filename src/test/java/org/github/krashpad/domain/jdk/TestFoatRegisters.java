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
class TestFoatRegisters {

    @Test
    void testAr14() {
        FloatRegisters priorEvent = new FloatRegisters(null);
        String logLine = "  fr14 = 0x0000000000000001    fr15 = 0x0000000000000001  |  "
                + "fr14 =  2.170670038611329e-300    fr15 =  2.170769854760952e-300";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof FloatRegisters,
                JdkUtil.LogEventType.FLOAT_REGISTERS.toString() + " not parsed.");
    }

    @Test
    void testDashLine() {
        FloatRegisters priorEvent = new FloatRegisters(null);
        String logLine = "----------------";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof FloatRegisters,
                JdkUtil.LogEventType.FLOAT_REGISTERS.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "Float Registers:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.FLOAT_REGISTERS,
                JdkUtil.LogEventType.FLOAT_REGISTERS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Float Registers:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof FloatRegisters,
                JdkUtil.LogEventType.FLOAT_REGISTERS.toString() + " not parsed.");
    }

}

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
class TestAccessRegisters {

    @Test
    void testAr14() {
        AccessRegisters priorEvent = new AccessRegisters(null);
        String logLine = "  ar14 = 0x00000000    ar15 = 0x00000001";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof AccessRegisters,
                JdkUtil.LogEventType.ACCESS_REGISTERS.toString() + " not parsed.");
    }

    @Test
    void testDashLine() {
        AccessRegisters priorEvent = new AccessRegisters(null);
        String logLine = "-----------------";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof AccessRegisters,
                JdkUtil.LogEventType.ACCESS_REGISTERS.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "Access Registers:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ACCESS_REGISTERS,
                JdkUtil.LogEventType.ACCESS_REGISTERS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Access Registers:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof AccessRegisters,
                JdkUtil.LogEventType.ACCESS_REGISTERS.toString() + " not parsed.");
    }

}

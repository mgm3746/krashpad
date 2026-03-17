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
class TestConstantPool {

    @Test
    void testAddressHex4Hex8() {
        ConstantPool priorLogEvent = new ConstantPool("[Constant Pool]");
        String logLine = "             Address          hex4                    hex8      ";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof ConstantPool,
                JdkUtil.LogEventType.CONSTANT_POOL.toString() + " not parsed.");
    }

    @Test
    void testDataAddressHex4() {
        ConstantPool priorLogEvent = new ConstantPool("[Constant Pool]");
        String logLine = "  0x00007f8479693524:   0x3fe62e42                              ";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof ConstantPool,
                JdkUtil.LogEventType.CONSTANT_POOL.toString() + " not parsed.");
    }

    @Test
    void testDataAddressHex4Hex8() {
        ConstantPool priorLogEvent = new ConstantPool("[Constant Pool]");
        String logLine = "  0x00007f8479693520:   0xfefa39ef      0x3fe62e42fefa39ef      ";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof ConstantPool,
                JdkUtil.LogEventType.CONSTANT_POOL.toString() + " not parsed.");
    }

    @Test
    void testHeaderEmpty() {
        String logLine = "[Constant Pool (empty)]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ConstantPool,
                JdkUtil.LogEventType.CONSTANT_POOL.toString() + " not parsed.");
    }

    @Test
    void testIdentity() {
        String logLine = "[Constant Pool]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONSTANT_POOL,
                JdkUtil.LogEventType.CONSTANT_POOL.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "[Constant Pool]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ConstantPool,
                JdkUtil.LogEventType.CONSTANT_POOL.toString() + " not parsed.");
    }

}

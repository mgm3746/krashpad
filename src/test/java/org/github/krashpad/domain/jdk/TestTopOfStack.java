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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestTopOfStack {

    @Test
    void testHeader() {
        String logLine = "Top of Stack: (sp=0x00007fcbcc676c50)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK,
                JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "0x00007fcbcc676c50:   00007fcbcc676cb0 00007fcbd0596b86";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK,
                JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not identified.");
    }

    @Test
    void testNotInstructions() {
        String logLine = "0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03";
        assertFalse(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK,
                JdkUtil.LogEventType.TOP_OF_STACK.toString() + " incorrectly identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "0x00007fcbcc676c50:   00007fcbcc676cb0 00007fcbd0596b86";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof TopOfStack,
                JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not parsed.");
    }

    @Test
    void testSpaceAtEnd() {
        String logLine = "0x00007fcbcc676e40:   00007fcbc8056a98 00000000000000d8 ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK,
                JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not identified.");
    }
}
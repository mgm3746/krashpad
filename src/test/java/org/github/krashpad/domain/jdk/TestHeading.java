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
class TestHeading {

    @Test
    void testDashes19SpaceBeforeAndAfter() {
        String logLine = " ------------------- ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }

    @Test
    void testDashes70() {
        String logLine = "----------------------------------------------------------------------";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }

    @Test
    void testDashes80() {
        String logLine = "--------------------------------------------------------------------------------";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "---------------  T H R E A D  ---------------";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "---------------  T H R E A D  ---------------";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Heading,
                JdkUtil.LogEventType.HEADING.toString() + " not parsed.");
    }

    @Test
    void testProcess() {
        String logLine = "---------------  S Y S T E M  ---------------";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }

    @Test
    void testSummary() {
        String logLine = "---------------  S U M M A R Y ------------";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }

    @Test
    void testSystem() {
        String logLine = "---------------  P R O C E S S  ---------------";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEADING,
                JdkUtil.LogEventType.HEADING.toString() + " not identified.");
    }
}
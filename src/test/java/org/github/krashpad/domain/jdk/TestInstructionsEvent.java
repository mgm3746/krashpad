/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
class TestInstructionsEvent {

    @Test
    void testBlocksOf8() {
        String logLine = "0x00003fff7a9ddb60:   2fa40000 7d491b96 79470020 409e0044";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testError() {
        String logLine = "[error occurred during error reporting (printing registers, top of stack, "
                + "instructions near pc), id 0xb]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Instructions: (pc=0x00007fcbd05a3b71)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNotTopOfStack() {
        String logLine = "0x00007fcbcc676d10:   00007fcbcc676d90 00007fcbd088f2ca";
        assertFalse(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " incorrectly identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof InstructionsEvent,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testQuestionMarks() {
        String logLine = "0x00007f1126127434:   ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ?? ??";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof InstructionsEvent,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testSingleAddress() {
        String logLine = "0x000000002fffffe0:   ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSpaceAtEnd() {
        String logLine = "0x00007fcbd05a3b81:   75 0f c1 f8 03 5d c3 0f 1f 84 00 00 00 00 00 75 ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.INSTRUCTIONS,
                JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.");
    ***REMOVED***
***REMOVED***
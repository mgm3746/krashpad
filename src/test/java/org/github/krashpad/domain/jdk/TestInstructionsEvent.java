/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestInstructionsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof InstructionsEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Instructions: (pc=0x00007fcbd05a3b71)";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***

    public void testNotTopOfStack() {
        String logLine = "0x00007fcbcc676d10:   00007fcbcc676d90 00007fcbd088f2ca";
        Assert.assertFalse(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " incorrectly identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***

    public void testSpaceAtEnd() {
        String logLine = "0x00007fcbd05a3b81:   75 0f c1 f8 03 5d c3 0f 1f 84 00 00 00 00 00 75 ";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***

    public void testBlocksOf8() {
        String logLine = "0x00003fff7a9ddb60:   2fa40000 7d491b96 79470020 409e0044";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***

    public void testSingleAddress() {
        String logLine = "0x000000002fffffe0:   ";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***

    /**
     * TODO: Does this error apply to multiple events and should be moved to a dedicated ErrorEvent?
     */
    public void testError() {
        String logLine = "[error occurred during error reporting (printing registers, top of stack, "
                + "instructions near pc), id 0xb]";
        Assert.assertTrue(JdkUtil.LogEventType.INSTRUCTIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.INSTRUCTIONS);
    ***REMOVED***
***REMOVED***
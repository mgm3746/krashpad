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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestGlobalFlag {

    @Test
    void testCcstrlist() {
        String logLine = "ccstrlist OnOutOfMemoryError                       = /u/search/bin/on-oom.sh"
                + "                   {product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testGlobalFlagBool() {
        String logLine = "     bool SegmentedCodeCache                       = true"
                + "                                      {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testGlobalFlagCcstr() {
        String logLine = "    ccstr ErrorFile                                = /path/to/vm_crash_%p.log            "
                + "{product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testGlobalFlagSizeT() {
        String logLine = "   size_t G1HeapRegionSize                         = 2097152"
                + "                                   {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testGlobalFlagUint() {
        String logLine = "     uint ConcGCThreads                            = 2"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testGlobalFlagUintx() {
        String logLine = "    uintx GCDrainStackTargetSize                   = 64"
                + "                                        {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "[Global flags]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GlobalFlag,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not parsed.");
        GlobalFlag globalFlag = new GlobalFlag(logLine);
        assertEquals("CICompilerCount", globalFlag.getFlag(), "Flag not correct.");
        assertEquals("4", globalFlag.getValue(), "Value not correct.");
    }

    @Test
    void testSweeperThreshold() {
        String logLine = "   double SweeperThreshold                         = 0.058594"
                + "                                  {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    }
}
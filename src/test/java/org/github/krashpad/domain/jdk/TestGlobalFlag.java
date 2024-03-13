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
    void testBool() {
        String logLine = "     bool SegmentedCodeCache                       = true"
                + "                                      {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testC2Product() {
        String logLine = "     intx AutoBoxCacheMax                          = 8192                                   "
                + "{C2 product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCcstr() {
        String logLine = "    ccstr ErrorFile                                = /path/to/vm_crash_%p.log            "
                + "{product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCcstrlist() {
        String logLine = "ccstrlist OnOutOfMemoryError                       = /u/search/bin/on-oom.sh"
                + "                   {product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCommandLineErgonomic() {
        String logLine = "    uintx NonProfiledCodeHeapSize                  = 1600000000                             "
                + "{pd product} {command line, ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testDiagnostic() {
        String logLine = "     bool DebugNonSafepoints                       = true                                   "
                + "{diagnostic} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "[Global flags]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testOptionValueWithSpaces() {
        String logLine = "ccstrlist OnOutOfMemoryError                       = "
                + "/bin/kill -ABRT %p                        {product} {environment}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GlobalFlag,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not parsed.");
        GlobalFlag globalFlag = new GlobalFlag(logLine);
        assertEquals("CICompilerCount", globalFlag.getFlag(), "Flag not correct.");
        assertEquals("4", globalFlag.getValue(), "Value not correct.");
    }

    @Test
    void testSizeT() {
        String logLine = "   size_t G1HeapRegionSize                         = 2097152"
                + "                                   {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testSweeperThreshold() {
        String logLine = "   double SweeperThreshold                         = 0.058594"
                + "                                  {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testUint() {
        String logLine = "     uint ConcGCThreads                            = 2"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testUint64T() {
        String logLine = " uint64_t MaxDirectMemorySize                      = 21474836480"
                + "                               {product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testUintx() {
        String logLine = "    uintx GCDrainStackTargetSize                   = 64"
                + "                                        {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }
}
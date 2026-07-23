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
    void tesPlusEquals() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "ccstrlist CompileCommand                           += exclude,com/example/MyClass.myMethod";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testBool() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     bool SegmentedCodeCache                       = true"
                + "                                      {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testC2Product() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     intx AutoBoxCacheMax                          = 8192                                   "
                + "{C2 product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCcstr() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "    ccstr ErrorFile                                = /path/to/vm_crash_%p.log            "
                + "{product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCcstrlist() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "ccstrlist OnOutOfMemoryError                       = /u/search/bin/on-oom.sh"
                + "                   {product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCcstrlistCompileCommand() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "ccstrlist CompileCommand                           = exclude,com/example/MyClass.myMethod";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testCommandLineErgonomic() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "    uintx NonProfiledCodeHeapSize                  = 1600000000                             "
                + "{pd product} {command line, ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testDiagnostic() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     bool DebugNonSafepoints                       = true                                   "
                + "{diagnostic} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testFlightRecorder() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     bool FlightRecorder                           = true"
                + "                                      {product} {management}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
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
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testInt() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "      int ActiveProcessorCount                     = "
                + "16                                        {product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testOptionValueWithSpaces() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "ccstrlist OnOutOfMemoryError                       = "
                + "/bin/kill -ABRT %p                        {product} {environment}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof GlobalFlag,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not parsed.");
        GlobalFlag globalFlag = new GlobalFlag(logLine);
        assertEquals("CICompilerCount", globalFlag.getFlag(), "Flag not correct.");
        assertEquals("4", globalFlag.getValue(), "Value not correct.");
    }

    @Test
    void testSizeT() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "   size_t G1HeapRegionSize                         = 2097152"
                + "                                   {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testSweeperThreshold() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "   double SweeperThreshold                         = 0.058594"
                + "                                  {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testUint() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "     uint ConcGCThreads                            = 2"
                + "                                         {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testUint64T() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = " uint64_t MaxDirectMemorySize                      = 21474836480"
                + "                               {product} {command line}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }

    @Test
    void testUintx() {
        GlobalFlag priorEvent = new GlobalFlag(null);
        String logLine = "    uintx GCDrainStackTargetSize                   = 64"
                + "                                        {product} {ergonomic}";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.GLOBAL_FLAG,
                JdkUtil.LogEventType.GLOBAL_FLAG.toString() + " not identified.");
    }
}
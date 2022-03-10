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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestGlobalFlagsEvent {

    @Test
    void testCcstrlist() {
        String logLine = "ccstrlist OnOutOfMemoryError                       = /u/search/bin/on-oom.sh"
                + "                   {product***REMOVED*** {command line***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testGlobalFlagBool() {
        String logLine = "     bool SegmentedCodeCache                       = true"
                + "                                      {product***REMOVED*** {ergonomic***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testGlobalFlagCcstr() {
        String logLine = "    ccstr ErrorFile                                = /path/to/vm_crash_%p.log            "
                + "{product***REMOVED*** {command line***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testGlobalFlagSizeT() {
        String logLine = "   size_t G1HeapRegionSize                         = 2097152"
                + "                                   {product***REMOVED*** {ergonomic***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testGlobalFlagUint() {
        String logLine = "     uint ConcGCThreads                            = 2"
                + "                                         {product***REMOVED*** {ergonomic***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testGlobalFlagUintx() {
        String logLine = "    uintx GCDrainStackTargetSize                   = 64"
                + "                                        {product***REMOVED*** {ergonomic***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "[Global flags]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product***REMOVED*** {ergonomic***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product***REMOVED*** {ergonomic***REMOVED***";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof GlobalFlagsEvent,
                JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
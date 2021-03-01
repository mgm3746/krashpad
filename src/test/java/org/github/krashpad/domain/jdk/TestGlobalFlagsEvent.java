/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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
public class TestGlobalFlagsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product***REMOVED*** {ergonomic***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product***REMOVED*** {ergonomic***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof GlobalFlagsEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "[Global flags]";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testGlobalFlagUint() {
        String logLine = "     uint ConcGCThreads                            = 2"
                + "                                         {product***REMOVED*** {ergonomic***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testGlobalFlagCcstr() {
        String logLine = "    ccstr ErrorFile                                = /path/to/vm_crash_%p.log            "
                + "{product***REMOVED*** {command line***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testGlobalFlagSizeT() {
        String logLine = "   size_t G1HeapRegionSize                         = 2097152"
                + "                                   {product***REMOVED*** {ergonomic***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testGlobalFlagUintx() {
        String logLine = "    uintx GCDrainStackTargetSize                   = 64"
                + "                                        {product***REMOVED*** {ergonomic***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testGlobalFlagBool() {
        String logLine = "     bool SegmentedCodeCache                       = true"
                + "                                      {product***REMOVED*** {ergonomic***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***

    public void testCcstrlist() {
        String logLine = "ccstrlist OnOutOfMemoryError                       = /u/search/bin/on-oom.sh"
                + "                   {product***REMOVED*** {command line***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.GLOBAL_FLAGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.GLOBAL_FLAGS);
    ***REMOVED***
***REMOVED***
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
public class TestThreadEvent extends TestCase {

    public void testIdentity() {
        String logLine = "  0x00007f19aa5128e0 JavaThread \"Thread-8\" daemon [_thread_blocked, id=18881, "
                + "stack(0x00007f199cf04000,0x00007f199d005000)]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "  0x00007f19aa5128e0 JavaThread \"Thread-8\" daemon [_thread_blocked, id=18881, "
                + "stack(0x00007f199cf04000,0x00007f199d005000)]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof ThreadEvent);
    ***REMOVED***

    public void testJavaThreadHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testOtherThreadHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void test32Bit() {
        String logLine = "  0x08f59000 JavaThread \"Service Thread\" daemon [_thread_blocked, id=29308, "
                + "stack(0xd6b0d000,0xd6b5e000)]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testCurrentThreadJavaThread() {
        String logLine = "=>0x00007ff0ec053800 JavaThread \"main\" [_thread_in_native, id=92334, "
                + "stack(0x00007ff0f60c5000,0x00007ff0f61c5000)]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testCurrentThreadThread() {
        String logLine = "=>0x00007ffeb4ee1800 (exited) Thread [stack: 0x00007ffe956e0000,0x00007ffe958e0000] "
                + "[id=107493]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testGcTaskThread() {
        String logLine = "=>0x00007fcbc8056000 (exited) GCTaskThread [stack: 0x00007fcbcc578000,0x00007fcbcc678000] "
                + "[id=52418]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testConcurrentGcThread() {
        String logLine = "  0x00007ffff0087000 ConcurrentGCThread \"G1 Main Marker\" [stack: 0x00007fffba4b0000,"
                + "0x00007fffba5b0000] [id=24734]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***

    public void testError() {
        String logLine = "[error occurred during error reporting (printing all threads), id 0xb, SIGSEGV (0xb) at "
                + "pc=0x00007fc88aaa9a74]";
        Assert.assertTrue(JdkUtil.LogEventType.THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD);
    ***REMOVED***
***REMOVED***

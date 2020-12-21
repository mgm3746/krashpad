/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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
package org.github.errcat.domain.jdk;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestCurrentThreadEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Current thread (0x00007f127434f800):  JavaThread \"ajp-/hostname:8109-16\" daemon "
                + "[_thread_in_native, id=112672, stack(0x00007f11e11a2000,0x00007f11e12a3000)]";
        Assert.assertTrue(JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CURRENT_THREAD);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Current thread (0x00007f127434f800):  JavaThread \"ajp-/hostname:8109-16\" daemon "
                + "[_thread_in_native, id=112672, stack(0x00007f11e11a2000,0x00007f11e12a3000)]";
        Assert.assertTrue(JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CurrentThreadEvent);
    ***REMOVED***

    public void test32BitAddress() {
        String logLine = "Current thread (0x08ec6400):  JavaThread \"main\" [_thread_blocked, id=29301, "
                + "stack(0xffc15000,0xffc65000)]";
        Assert.assertTrue(JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CURRENT_THREAD);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Current thread not correct.",
                "JavaThread \"main\" [_thread_blocked, id=29301, stack(0xffc15000,0xffc65000)]",
                ((CurrentThreadEvent) event).getCurrentThread());
    ***REMOVED***

    public void testCurrentThreadIsNativeThread() {
        String logLine = "Current thread is native thread";
        Assert.assertTrue(JdkUtil.LogEventType.CURRENT_THREAD.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CURRENT_THREAD);
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("is native thread", ((CurrentThreadEvent) event).getCurrentThread());
    ***REMOVED***
***REMOVED***

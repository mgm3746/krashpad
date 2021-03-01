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
public class TestDeoptimizationEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Event: 5689.207 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if "
                + "action=reinterpret pc=0x00007ff0df4a0408 "
                + "method=org.eclipse.jface.text.ListLineTracker.getLineLength(I)I @ 28";
        Assert.assertTrue(JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Event: 5689.207 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if "
                + "action=reinterpret pc=0x00007ff0df4a0408 "
                + "method=org.eclipse.jface.text.ListLineTracker.getLineLength(I)I @ 28";
        Assert.assertTrue(JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof DeoptimizationEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Deoptimization events (250 events):";
        Assert.assertTrue(JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT);
    ***REMOVED***

    /**
     * TODO: Does this error apply to multiple events and should be moved to a dedicated ErrorEvent?
     */
    public void testError() {
        String logLine = "[error occurred during error reporting (printing ring buffers), id 0xb]";
        Assert.assertTrue(JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DEOPTIMIZATION_EVENT);
    ***REMOVED***
***REMOVED***

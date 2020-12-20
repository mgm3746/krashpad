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

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestTimeElapsedTimeEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        Assert.assertTrue(JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.TIME_ELAPSED_TIME);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        Assert.assertTrue(JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof TimeElapsedTimeEvent);
    ***REMOVED***

    public void testTime() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        Assert.assertTrue(JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.TIME_ELAPSED_TIME);
        TimeElapsedTimeEvent event = new TimeElapsedTimeEvent(logLine);
        Assert.assertEquals("Time not correct.", "Tue May  5 18:32:04 2020 CEST", event.getTime());
    ***REMOVED***

    public void testElapsedTime() {
        String logLine = "Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)";
        Assert.assertTrue(JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.TIME_ELAPSED_TIME);
        TimeElapsedTimeEvent event = new TimeElapsedTimeEvent(logLine);
        Assert.assertEquals("Elapsed time not correct.", "0d 0h 15m 56s", event.getElapsedTime());
    ***REMOVED***
***REMOVED***
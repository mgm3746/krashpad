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
public class TestRlimitEvent extends TestCase {

    public void testIdentity() {
        String logLine = "rlimit: STACK 10240k, CORE 0k, NPROC 16384, NOFILE 16384, AS infinity";
        Assert.assertTrue(JdkUtil.LogEventType.RLIMIT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.RLIMIT);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "rlimit: STACK 10240k, CORE 0k, NPROC 16384, NOFILE 16384, AS infinity";
        Assert.assertTrue(JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof RlimitEvent);
    ***REMOVED***

    public void testSoftHard() {
        String logLine = "rlimit (soft/hard): STACK 8192k/infinity , CORE infinity/infinity , NPROC 62502/62502 , "
                + "NOFILE 262144/262144 , AS infinity/infinity , CPU infinity/infinity , DATA infinity/infinity , "
                + "FSIZE infinity/infinity , MEMLOCK 64k/64k";
        Assert.assertTrue(JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof RlimitEvent);
        RlimitEvent event = new RlimitEvent(logLine);
        Assert.assertEquals("NPROC not correct.", "62502", event.getNproc());
    ***REMOVED***

    public void testNprocInfinity() {
        String logLine = "rlimit: STACK 8192k, CORE 0k, NPROC infinity, NOFILE 240000, AS infinity";
        Assert.assertTrue(JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof RlimitEvent);
        RlimitEvent event = new RlimitEvent(logLine);
        Assert.assertEquals("NPROC not correct.", "infinity", event.getNproc());
    ***REMOVED***

***REMOVED***
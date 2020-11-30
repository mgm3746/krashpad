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
public class TestHeaderEvent extends TestCase {

    public void testIdentity() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEADER);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
    ***REMOVED***

    public void testProblematicFrameNativeCode() {
        String logLine = "***REMOVED*** C  0x0000000000000000";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Problematic frame not identified.", headerEvent.isProblematicFrame());
    ***REMOVED***

    public void testProblematicFrameVmCode() {
        String logLine = "***REMOVED*** V  [libjvm.so+0xa41a10]";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Problematic frame not identified.", headerEvent.isProblematicFrame());
    ***REMOVED***

    public void testInternalError() {
        String logLine = "***REMOVED***  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Internal Errror not identified.", headerEvent.isInternalError());
    ***REMOVED***

    public void testError() {
        String logLine = "***REMOVED***  Error: ShouldNotReachHere()";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Error not identified.", headerEvent.isError());
    ***REMOVED***

    public void testJavaVm() {
        String logLine = "***REMOVED*** Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed "
                + "oops)";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("Java VM not identified.", headerEvent.isJavaVm());
    ***REMOVED***

    public void testJreVersion() {
        String logLine = "***REMOVED*** JRE version: Java(TM) SE Runtime Environment (8.0_251-b08) (build 1.8.0_251-b08)";
        Assert.assertTrue(JdkUtil.LogEventType.HEADER.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeaderEvent);
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        Assert.assertTrue("JRE version not identified.", headerEvent.isJreVersion());
    ***REMOVED***
***REMOVED***

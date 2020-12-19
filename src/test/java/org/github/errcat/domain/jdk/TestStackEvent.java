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
public class TestStackEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.STACK);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
    ***REMOVED***

    public void testNativeFrames() {
        String logLine = "Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
    ***REMOVED***

    public void testJavaFrames() {
        String logLine = "Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
    ***REMOVED***

    public void testNativeCode() {
        String logLine = "C  [libcairo.so.2+0x66e64]  cairo_region_num_rectangles+0x4";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
    ***REMOVED***

    public void testJavaCompiledCode() {
        String logLine = "J 7595  org.eclipse.swt.internal.gtk.GTK.gtk_main_do_event(J)V (0 bytes) @ "
                + "0x00007fcd7c4b2f91 [0x00007fcd7c4b2f40+0x0000000000000051]";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
    ***REMOVED***

    public void testInterpretedCode() {
        String logLine = "j  org.eclipse.ui.internal.Workbench$$Lambda$166.run()V+12";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
    ***REMOVED***

    public void testVmFrameSmallV() {
        String logLine = "v  ~StubRoutines::call_stub";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
        StackEvent stackEvent = new StackEvent(logLine);
        Assert.assertFalse("VM frame incorrectly identified.", stackEvent.isVmFrame());
    ***REMOVED***

    public void testVmGeneratedCodeFrameSmallV() {
        String logLine = "v  ~StubRoutines::call_stub";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
        StackEvent stackEvent = new StackEvent(logLine);
        Assert.assertTrue("VM generated code frame not identified.", stackEvent.isVmGeneratedCodeFrame());
    ***REMOVED***

    public void testVmFrameLargeV() {
        String logLine = "V  [libjvm.so+0x93a382]  java_start(Thread*)+0xf2";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackEvent);
        StackEvent stackEvent = new StackEvent(logLine);
        Assert.assertTrue("VM code not identified.", stackEvent.isVmFrame());
    ***REMOVED***

    public void testHeader() {
        String logLine = "Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k";
        Assert.assertTrue(JdkUtil.LogEventType.STACK.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.STACK);
        StackEvent event = new StackEvent(logLine);
        Assert.assertTrue("Header not identified.", event.isHeader());
        Assert.assertEquals("Stack free space not correct.", 1018, event.getStackFreeSpace());
    ***REMOVED***
***REMOVED***

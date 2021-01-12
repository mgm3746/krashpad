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
public class TestThreadsClassSmrInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "_java_thread_list=0x00000000020a0100, length=58, elements={";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "_java_thread_list=0x00000000020a0100, length=58, elements={";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof ThreadsClassSmrInfoEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Threads class SMR info:";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***

    public void testAdress4() {
        String logLine = "0x00007ffff0017800, 0x00007ffff0450000, 0x00007ffff0452000, 0x00007ffff0460000,";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***

    public void testAdress2() {
        String logLine = "0x00007fff5d5c6000, 0x0000000001b2a000";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***

    public void testAdress1() {
        String logLine = "0x00007f66f00a8800";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***

    public void testAdress3() {
        String logLine = "0x00007fdbb0001800, 0x00007fdb0c001800, 0x00007fdbf00b1800";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***

    public void testAdress4NoEndComma() {
        String logLine = "0x00007f99b80117c0, 0x00007f98f8024750, 0x00007f995001c480, 0x00007f99b0003e20";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO);
    ***REMOVED***
***REMOVED***
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
public class TestCpuEvent extends TestCase {

    public void testIdentity() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testCpuInfo() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testProcessor() {
        String logLine = "processor       : 0";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testCpu() {
        String logLine = "cpu             : POWER9 (architected), altivec supported";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testClock() {
        String logLine = "clock           : 2500.000000MHz";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testTimebase() {
        String logLine = "timebase    : 512000000";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testPlatform() {
        String logLine = "platform    : pSeries";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testModel() {
        String logLine = "model       : IBM,9008-22L";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testMachine() {
        String logLine = "machine     : CHRP IBM,9008-22L";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testRevision() {
        String logLine = "revision        : 2.2 (pvr 004e 0202)";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***

    public void testMmu() {
        String logLine = "MMU     : Hash";
        Assert.assertTrue(JdkUtil.LogEventType.CPU.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuEvent);
    ***REMOVED***
***REMOVED***

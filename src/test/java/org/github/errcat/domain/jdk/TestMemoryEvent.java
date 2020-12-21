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
public class TestMemoryEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof MemoryEvent);
    ***REMOVED***

    public void testMemory() {
        String logLine = "Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testMemoryWindows() {
        String logLine = "Memory: 4k page, system-wide physical 16383M (5994M free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testMemory9Digits() {
        String logLine = "Memory: 4k page, physical 263868708k(8753840k free), swap 2097148k(36k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testMemory64kPage() {
        String logLine = "Memory: 64k page, physical 254200832k(247780928k free), swap 4194240k(4069376k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testNoSwapInfo() {
        String logLine = "Memory: 8k page, physical 267386880k(88275744k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testTotalPageFile() {
        String logLine = "TotalPageFile size 20479M (AvailPageFile size 7532M)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testCurrentProcessWorkingSet() {
        String logLine = "current process WorkingSet (physical memory assigned to process): 11M, peak: 11M";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void testCurrentProcessCommitCharge() {
        String logLine = "current process commit charge (\"private bytes\"): 61M, peak: 8253M";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***

    public void test10Digits() {
        String logLine = "Memory: 4k page, physical 1584737836k(118786168k free), swap 33554428k(33554428k free)";
        Assert.assertTrue(JdkUtil.LogEventType.MEMORY.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.MEMORY);
    ***REMOVED***
***REMOVED***

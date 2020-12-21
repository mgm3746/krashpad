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
public class TestVmEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Event: 6665.311 Executing VM operation: RevokeBias done";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Event: 6665.311 Executing VM operation: RevokeBias done";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof VmEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testThreadExited() {
        String logLine = "Event: 6665.311 Thread 0x00007fefe944f000 Thread exited: 0x00007fefe944f00";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testThreadAdded() {
        String logLine = "Event: 6668.297 Thread 0x00007fefe944f000 Thread added: 0x00007fefe944f000";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testLoadingClass() {
        String logLine = "Event: 6669.549 loading class projectNature";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testConcurrent() {
        String logLine = "Event: 1683619.717 Concurrent reset";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testPause() {
        String logLine = "Event: 1683619.731 Pause Init Mark (unload classes)";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testCoalescedSafepoint() {
        String logLine = "Event: 38778.824 Executing coalesced safepoint VM operation: RevokeBias";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testProtectingMemory() {
        String logLine = "Event: 949.037 Protecting memory [0x00007fffaa12b000,0x00007fffaa12f000] with protection "
                + "modes 0";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***

    public void testLoadedSharedLibrary() {
        String logLine = "Event: 0.001 Loaded shared library "
                + "/usr/lib/jvm/java-11-openjdk-11.0.8.10-0.el8_2.x86_64/lib/libzip.so";
        Assert.assertTrue(JdkUtil.LogEventType.VM_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_EVENT);
    ***REMOVED***
***REMOVED***

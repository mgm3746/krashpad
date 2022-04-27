/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestVirtualizationInfoEvent {

    @Test
    void testHyperDashV() {
        String logLine = "Hyper-V virtualization detected";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHyperV() {
        String logLine = "HyperV virtualization detected";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "HyperV virtualization detected";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKvm() {
        String logLine = "KVM virtualization detected";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKvmStealTicks() {
        String logLine = "Steal ticks since vm start: 152";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testKvmStealTicksPercentage() {
        String logLine = "Steal ticks percentage since vm start:  0.000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "HyperV virtualization detected";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VirtualizationInfoEvent,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testVmWare() {
        String logLine = "VMWare virtualization detected";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareGuestMem() {
        String logLine = "guest.mem.reserved = 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareHostCpu() {
        String logLine = "host.cpu.processorMHz = 2095";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareOvhdMem() {
        String logLine = "ovhd.mem.swapped = 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareVmCpu() {
        String logLine = "vm.cpu.reserved = 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareVmNuma() {
        String logLine = "vm.numa.local = 66932356";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareVSphereHostInfoHeader() {
        String logLine = "vSphere host information:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVmWareVSphereResourceInfoNowHeader() {
        String logLine = "vSphere resource information available now:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    ***REMOVED***

***REMOVED***
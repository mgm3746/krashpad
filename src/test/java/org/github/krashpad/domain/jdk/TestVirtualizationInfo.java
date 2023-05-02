/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestVirtualizationInfo {

    @Test
    void testHyperDashV() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "Hyper-V virtualization detected";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testHyperV() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "HyperV virtualization detected";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "HyperV virtualization detected";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testKvm() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "KVM virtualization detected";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testKvmStealTicks() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "Steal ticks since vm start: 152";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testKvmStealTicksPercentage() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "Steal ticks percentage since vm start:  0.000";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {        
        String logLine = "HyperV virtualization detected";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO, JdkUtil.identifyEventType(logLine, null),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWare() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "VMWare virtualization detected";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareGuestMem() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "guest.mem.reserved = 0";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareHostCpu() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "host.cpu.processorMHz = 2095";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareOvhdMem() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "ovhd.mem.swapped = 0";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareVmCpu() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "vm.cpu.reserved = 0";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO,
                JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareVmNuma() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "vm.numa.local = 66932356";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareVSphereHostInfoHeader() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "vSphere host information:";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

    @Test
    void testVmWareVSphereResourceInfoNowHeader() {
        VirtualizationInfo priorLogEvent = new VirtualizationInfo("Hyper-V virtualization detected");
        String logLine = "vSphere resource information available now:";
        assertEquals(JdkUtil.LogEventType.VIRTUALIZATION_INFO, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.VIRTUALIZATION_INFO.toString() + " not identified.");
    }

}
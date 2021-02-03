/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestContainerInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "container_type: cgroupv1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testParseLogLine() {
        String logLine = "container_type: cgroupv1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof ContainerInfoEvent);
    }

    public void testHeader() {
        String logLine = "container (cgroup) information:";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testCpuCpusetCpus() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testCpuMemoryNodes() {
        String logLine = "cpu_memory_nodes: 0";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testActiveProcessorCount() {
        String logLine = "active_processor_count: 8";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testBuffers() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testCpuQuota() {
        String logLine = "cpu_quota: -1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testCpuPeriod() {
        String logLine = "cpu_period: 100000";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testCpuShares() {
        String logLine = "cpu_shares: -1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testMemoryLimit() {
        String logLine = "memory_limit_in_bytes: -1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testMemoryAndSwapLimit() {
        String logLine = "memory_and_swap_limit_in_bytes: -1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testMemorySoftLimit() {
        String logLine = "memory_soft_limit_in_bytes: -1";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testMemoryUsage() {
        String logLine = "memory_usage_in_bytes: 3469758464";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testMemoryMaxUsage() {
        String logLine = "memory_max_usage_in_bytes: 0";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testKvmVirtualizationDetected() {
        String logLine = "KVM virtualization detected";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testStealTicks() {
        String logLine = "Steal ticks since vm start: 152";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testStealTicksPercentage() {
        String logLine = "Steal ticks percentage since vm start:  0.000";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }

    public void testVmWare() {
        String logLine = "VMWare virtualization detected";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    }
}
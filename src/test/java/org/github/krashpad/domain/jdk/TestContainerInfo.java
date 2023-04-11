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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestContainerInfo {

    @Test
    void testActiveProcessorCount() {
        String logLine = "active_processor_count: 8";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testBuffers() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testCpuCpusetCpus() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testCpuMemoryNodes() {
        String logLine = "cpu_memory_nodes: 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testCpuPeriod() {
        String logLine = "cpu_period: 100000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testCpuQuota() {
        String logLine = "cpu_quota: -1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testCpuShares() {
        String logLine = "cpu_shares: -1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testCurrentNumberOfTasks() {
        String logLine = "current number of tasks: 39";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "container (cgroup) information:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "container_type: cgroupv1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testKernelMemoryUsageInBytes() {
        String logLine = "kernel_memory_usage_in_bytes: 599564 k";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testMaximumNumberOfTasks() {
        String logLine = "maximum number of tasks: 75254";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testMemoryAndSwapLimit() {
        String logLine = "memory_and_swap_limit_in_bytes: -1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testMemoryLimit() {
        String logLine = "memory_limit_in_bytes: -1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testMemoryMaxUsage() {
        String logLine = "memory_max_usage_in_bytes: 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testMemorySoftLimit() {
        String logLine = "memory_soft_limit_in_bytes: -1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testMemoryUsage() {
        String logLine = "memory_usage_in_bytes: 3469758464";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "container_type: cgroupv1";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ContainerInfo,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not parsed.");
    }
}
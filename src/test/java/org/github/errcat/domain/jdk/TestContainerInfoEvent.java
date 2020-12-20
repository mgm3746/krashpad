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
public class TestContainerInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof ContainerInfoEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testCpuCpusetCpus() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testCpuMemoryNodes() {
        String logLine = "cpu_memory_nodes: 0";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testActiveProcessorCount() {
        String logLine = "active_processor_count: 8";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testBuffers() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testCpuQuota() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testCpuPeriod() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testCpuShares() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testMemoryLimit() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testMemoryAndSwapLimit() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testMemorySoftLimit() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testMemoryUsage() {
        String logLine = "memory_usage_in_bytes: 3469758464";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testMemoryMaxUsage() {
        String logLine = "memory_max_usage_in_bytes: 0";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testKvmVirtualizationDetected() {
        String logLine = "KVM virtualization detected";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testStealTicks() {
        String logLine = "Steal ticks since vm start: 152";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testStealTicksPercentage() {
        String logLine = "Steal ticks percentage since vm start:  0.000";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***

    public void testVmWare() {
        String logLine = "VMWare virtualization detected";
        Assert.assertTrue(JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CONTAINER_INFO);
    ***REMOVED***
***REMOVED***
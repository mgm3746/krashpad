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
class TestContainerInfoEvent {

    @Test
    void testActiveProcessorCount() {
        String logLine = "active_processor_count: 8";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBuffers() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuCpusetCpus() {
        String logLine = "cpu_cpuset_cpus: 0-7";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuMemoryNodes() {
        String logLine = "cpu_memory_nodes: 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuPeriod() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuQuota() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuShares() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemoryAndSwapLimit() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemoryLimit() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemoryMaxUsage() {
        String logLine = "memory_max_usage_in_bytes: 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemorySoftLimit() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMemoryUsage() {
        String logLine = "memory_usage_in_bytes: 3469758464";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CONTAINER_INFO,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ContainerInfoEvent,
                JdkUtil.LogEventType.CONTAINER_INFO.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
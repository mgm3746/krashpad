/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestJvmOptions {

    @Test
    void testCommonOptions() {
        String jvmArgs = "-Xmx1500m -Xms1000m -Xss512k -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=2048m";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-Xss512k", jvmOptions.getThreadStackSize(), "Thread stack size not correct.");
        assertEquals("-Xms1000m", jvmOptions.getInitialHeapSize(), "Initial heap size not correct.");
        assertEquals("-Xmx1500m", jvmOptions.getMaxHeapSize(), "Max heap size not correct.");
        assertEquals("-XX:MetaspaceSize=256M", jvmOptions.getMetaspaceSize(), "Metaspace size not correct.");
        assertEquals("-XX:MaxMetaspaceSize=2048m", jvmOptions.getMaxMetaspaceSize(), "Max metaspace size not correct.");
    ***REMOVED***

    @Test
    void testAbrt() {
        String jvmArgs = "-XX:OnOutOfMemoryError=/bin/kill -ABRT %p";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-ABRT %p", jvmOptions.getAbrt(), "ABRT not correct.");
    ***REMOVED***

    @Test
    void testDebugging() {
        String jvmArgs = "-XX:OnOutOfMemoryError=/bin/kill "
                + "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n -ABRT %p";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n",
                jvmOptions.getJpdaSocketTransport(), "JDPA socket transport (debugging) not correct.");
    ***REMOVED***

    @Test
    void testSystemProperties() {
        String jvmArgs = "-Xmx1500m -Xms1000m -Dcatalina.base=/path/to/tomcat -Xss512k";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(1, jvmOptions.getSystemProperties().size(), "System properties count not correct.");
        assertEquals("-Dcatalina.base=/path/to/tomcat", jvmOptions.getSystemProperties().get(0),
                "System property not correct.");
    ***REMOVED***

    @Test
    void testGcLoggingOptions() {
        String jvmArgs = "-Xmx1500m -Xms1000m -verbose:gc -Xloggc:/path/to/EAP-7.1.0/standalone/log/gc.log "
                + "-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 "
                + "-XX:GCLogFileSize=3M -Xss512k";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-Xloggc:/path/to/EAP-7.1.0/standalone/log/gc.log", jvmOptions.getLogGc(),
                "Gc log location not correct.");
        assertEquals(true, jvmOptions.isVerboseGc(), "-verbose:gc not correct.");
        assertEquals("-XX:NumberOfGCLogFiles=5", jvmOptions.getNumberOfGcLogFiles(), "NumberOfGCLogFiles not correct.");
        assertEquals("-XX:GCLogFileSize=3M", jvmOptions.getGcLogFileSize(), "GCLogFileSize not correct.");
        assertEquals("-XX:+UseGCLogFileRotation", jvmOptions.getUseGcLogFileRotation(),
                "UseGCLogFileRotation not correct.");
        assertEquals("-XX:+PrintGCDetails", jvmOptions.getPrintGcDetails(), "PrintGCDetails not correct.");
        assertEquals("-XX:+PrintGCDateStamps", jvmOptions.getPrintGcDateStamps(), "PrintGCDateStamps not correct.");
    ***REMOVED***

    @Test
    void testTraceClassUnloading() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:-TraceClassUnloading -Xss512k";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-XX:-TraceClassUnloading", jvmOptions.getTraceClassUnloading(),
                "TraceClassUnloading not correct.");
    ***REMOVED***

    @Test
    void testGarbageCollectorsSerialSerialOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseSerialGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL),
                GarbageCollector.SERIAL + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL_OLD),
                GarbageCollector.SERIAL_OLD + " collector not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorsParallelScavengeSerialOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelGC -XX:-UseParallelOldGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL_OLD),
                GarbageCollector.SERIAL_OLD + " collector not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorsParallelScavengeParallelOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
        jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelOldGC";
        jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
        jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelGC -XX:+UseParallelOldGC";
        jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorsParNewSerialOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParNewGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PAR_NEW),
                GarbageCollector.PAR_NEW + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL_OLD),
                GarbageCollector.SERIAL_OLD + " collector not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorsParNewConcurrentMarkSweep() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseConcMarkSweepGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PAR_NEW),
                GarbageCollector.PAR_NEW + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.CMS),
                GarbageCollector.CMS + " collector not identified.");
        jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC";
        jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(2, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.PAR_NEW),
                GarbageCollector.PAR_NEW + " collector not identified.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.CMS),
                GarbageCollector.CMS + " collector not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorG1() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseG1GC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(1, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.G1),
                GarbageCollector.G1 + " collector not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorShenandoah() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseShenandoahGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals(1, jvmOptions.getGarbageCollectors().size(), "Number of garbage collector not correct.");
        assertTrue(jvmOptions.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH),
                GarbageCollector.G1 + " collector not identified.");
    ***REMOVED***

    @Test
    void test7NewOptions() {
        String jvmArgs = "-XX:MaxJavaStackTraceDepth=50000 -XX:MaxGCPauseMillis=500 -XX:G1HeapRegionSize=4m "
                + "-XX:+UseStringDeduplication -XX:OnOutOfMemoryError=\"pmap %p\"  -XX:+DebugNonSafepoints "
                + "-XX:FlightRecorderOptions=stackdepth=256";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-XX:MaxJavaStackTraceDepth=50000", jvmOptions.getMaxJavaStackTraceDepth(),
                "MaxJavaStackTraceDepth not correct.");
        assertEquals("-XX:MaxGCPauseMillis=500", jvmOptions.getMaxGcPauseMillis(), "MaxGCPauseMillis not correct.");
        assertEquals("-XX:G1HeapRegionSize=4m", jvmOptions.getG1HeapRegionSize(), "G1HeapRegionSize not correct.");
        assertEquals("-XX:+UseStringDeduplication", jvmOptions.getUseStringDeduplication(),
                "UseStringDeduplication not correct.");
        assertEquals("-XX:OnOutOfMemoryError=\"pmap %p\"", jvmOptions.getOnOutOfMemoryError(),
                "OnOutOfMemoryError not correct.");
        assertEquals("-XX:+DebugNonSafepoints", jvmOptions.getDebugNonSafepoints(), "DebugNonSafepoints not correct.");
        assertEquals("-XX:FlightRecorderOptions=stackdepth=256", jvmOptions.getFlightRecorderOptions(),
                "FlightRecorderOptions not correct.");
    ***REMOVED***

    @Test
    void testVerify() {
        String jvmArgs = "-Xverify:none";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-Xverify:none", jvmOptions.getVerify(), "Verify not correct.");
    ***REMOVED***

    @Test
    void testMaxNewSize() {
        String jvmArgs = "-XX:MaxNewSize=512m";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-XX:MaxNewSize=512m", jvmOptions.getMaxNewSize(), "MaxNewSize not correct.");
    ***REMOVED***

    @Test
    void testDisableAttachMechanism() {
        String jvmArgs = "-XX:+DisableAttachMechanism";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-XX:+DisableAttachMechanism", jvmOptions.getDisableAttachMechanism(),
                "DisableAttachMechanism not correct.");
    ***REMOVED***

    @Test
    void testNewRatio() {
        String jvmArgs = "-XX:NewRatio=3";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-XX:NewRatio=3", jvmOptions.getNewRatio(), "NewRatio not correct.");
    ***REMOVED***

    @Test
    void testAddExports() {
        String jvmArgs = "-Xmx1g --add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("--add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED", jvmOptions.getAddExports(),
                "NewRatio not correct.");
    ***REMOVED***

    @Test
    void testUseCodeCacheFlushing() {
        String jvmArgs = "-Xmx1g -XX:+UseCodeCacheFlushing";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        assertEquals("-XX:+UseCodeCacheFlushing", jvmOptions.getUseCodeCacheFlushing(),
                "UseCodeCacheFlushing not correct.");
    ***REMOVED***
***REMOVED***
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

import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestJvmOptions extends TestCase {

    public void testCommonOptions() {
        String jvmArgs = "-Xmx1500m -Xms1000m -Xss512k -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=2048m";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Thread stack size not correct.", "-Xss512k", jvmOptions.getThreadStackSize());
        Assert.assertEquals("Initial heap size not correct.", "-Xms1000m", jvmOptions.getInitialHeapSize());
        Assert.assertEquals("Max heap size not correct.", "-Xmx1500m", jvmOptions.getMaxHeapSize());
        Assert.assertEquals("Metaspace size not correct.", "-XX:MetaspaceSize=256M", jvmOptions.getMetaspaceSize());
        Assert.assertEquals("Max metaspace size not correct.", "-XX:MaxMetaspaceSize=2048m",
                jvmOptions.getMaxMetaspaceSize());
    ***REMOVED***

    public void testAbrt() {
        String jvmArgs = "-XX:OnOutOfMemoryError=/bin/kill -ABRT %p";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("ABRT not correct.", "-ABRT %p", jvmOptions.getAbrt());
    ***REMOVED***

    public void testDebugging() {
        String jvmArgs = "-XX:OnOutOfMemoryError=/bin/kill "
                + "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n -ABRT %p";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("JDPA socket transport (debugging) not correct.",
                "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n",
                jvmOptions.getJpdaSocketTransport());
    ***REMOVED***

    public void testSystemProperties() {
        String jvmArgs = "-Xmx1500m -Xms1000m -Dcatalina.base=/path/to/tomcat -Xss512k";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("System properties count not correct.", 1, jvmOptions.getSystemProperties().size());
        Assert.assertEquals("System property not correct.", "-Dcatalina.base=/path/to/tomcat",
                jvmOptions.getSystemProperties().get(0));
    ***REMOVED***

    public void testGcLoggingOptions() {
        String jvmArgs = "-Xmx1500m -Xms1000m -verbose:gc -Xloggc:/path/to/EAP-7.1.0/standalone/log/gc.log "
                + "-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 "
                + "-XX:GCLogFileSize=3M -Xss512k";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Gc log location not correct.", "-Xloggc:/path/to/EAP-7.1.0/standalone/log/gc.log",
                jvmOptions.getLogGc());
        Assert.assertEquals("-verbose:gc not correct.", true, jvmOptions.isVerboseGc());
        Assert.assertEquals("NumberOfGCLogFiles not correct.", "-XX:NumberOfGCLogFiles=5",
                jvmOptions.getNumberOfGcLogFiles());
        Assert.assertEquals("GCLogFileSize not correct.", "-XX:GCLogFileSize=3M", jvmOptions.getGcLogFileSize());
        Assert.assertEquals("UseGCLogFileRotation not correct.", "-XX:+UseGCLogFileRotation",
                jvmOptions.getUseGcLogFileRotation());
        Assert.assertEquals("PrintGCDetails not correct.", "-XX:+PrintGCDetails", jvmOptions.getPrintGcDetails());
        Assert.assertEquals("PrintGCDateStamps not correct.", "-XX:+PrintGCDateStamps",
                jvmOptions.getPrintGcDateStamps());
    ***REMOVED***

    public void testTraceClassUnloading() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:-TraceClassUnloading -Xss512k";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("TraceClassUnloading not correct.", "-XX:-TraceClassUnloading",
                jvmOptions.getTraceClassUnloading());
    ***REMOVED***

    public void testGarbageCollectorsSerialSerialOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseSerialGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.SERIAL + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL));
        Assert.assertTrue(GarbageCollector.SERIAL_OLD + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL_OLD));
    ***REMOVED***

    public void testGarbageCollectorsParallelScavengeSerialOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelGC -XX:-UseParallelOldGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE));
        Assert.assertTrue(GarbageCollector.SERIAL_OLD + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL_OLD));
    ***REMOVED***

    public void testGarbageCollectorsParallelScavengeParallelOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE));
        Assert.assertTrue(GarbageCollector.PARALLEL_OLD + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD));
        jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelOldGC";
        jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE));
        Assert.assertTrue(GarbageCollector.PARALLEL_OLD + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD));
        jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParallelGC -XX:+UseParallelOldGC";
        jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE));
        Assert.assertTrue(GarbageCollector.PARALLEL_OLD + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD));
    ***REMOVED***

    public void testGarbageCollectorsParNewSerialOld() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParNewGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PAR_NEW + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PAR_NEW));
        Assert.assertTrue(GarbageCollector.SERIAL_OLD + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.SERIAL_OLD));
    ***REMOVED***

    public void testGarbageCollectorsParNewConcurrentMarkSweep() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseConcMarkSweepGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PAR_NEW + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PAR_NEW));
        Assert.assertTrue(GarbageCollector.CMS + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.CMS));
        jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC";
        jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 2, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.PAR_NEW + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.PAR_NEW));
        Assert.assertTrue(GarbageCollector.CMS + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.CMS));
    ***REMOVED***

    public void testGarbageCollectorG1() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseG1GC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 1, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.G1 + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.G1));
    ***REMOVED***

    public void testGarbageCollectorShenandoah() {
        String jvmArgs = "-Xmx1500m -Xms1000m -XX:+UseShenandoahGC";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Number of garbage collector not correct.", 1, jvmOptions.getGarbageCollectors().size());
        Assert.assertTrue(GarbageCollector.G1 + " collector not identified.",
                jvmOptions.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH));
    ***REMOVED***

    public void test7NewOptions() {
        String jvmArgs = "-XX:MaxJavaStackTraceDepth=50000 -XX:MaxGCPauseMillis=500 -XX:G1HeapRegionSize=4m "
                + "-XX:+UseStringDeduplication -XX:OnOutOfMemoryError=\"pmap %p\"  -XX:+DebugNonSafepoints "
                + "-XX:FlightRecorderOptions=stackdepth=256";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("MaxJavaStackTraceDepth not correct.", "-XX:MaxJavaStackTraceDepth=50000",
                jvmOptions.getMaxJavaStackTraceDepth());
        Assert.assertEquals("MaxGCPauseMillis not correct.", "-XX:MaxGCPauseMillis=500",
                jvmOptions.getMaxGcPauseMillis());
        Assert.assertEquals("G1HeapRegionSize not correct.", "-XX:G1HeapRegionSize=4m",
                jvmOptions.getG1HeapRegionSize());
        Assert.assertEquals("UseStringDeduplication not correct.", "-XX:+UseStringDeduplication",
                jvmOptions.getUseStringDeduplication());
        Assert.assertEquals("OnOutOfMemoryError not correct.", "-XX:OnOutOfMemoryError=\"pmap %p\"",
                jvmOptions.getOnOutOfMemoryError());
        Assert.assertEquals("DebugNonSafepoints not correct.", "-XX:+DebugNonSafepoints",
                jvmOptions.getDebugNonSafepoints());
        Assert.assertEquals("FlightRecorderOptions not correct.", "-XX:FlightRecorderOptions=stackdepth=256",
                jvmOptions.getFlightRecorderOptions());
    ***REMOVED***
***REMOVED***
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
package org.github.krashpad.util.jdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.github.krashpad.domain.jdk.ContainerInfoEvent;
import org.github.krashpad.domain.jdk.CpuInfoEvent;
import org.github.krashpad.domain.jdk.CurrentCompileTaskEvent;
import org.github.krashpad.domain.jdk.CurrentThreadEvent;
import org.github.krashpad.domain.jdk.DynamicLibraryEvent;
import org.github.krashpad.domain.jdk.EnvironmentVariablesEvent;
import org.github.krashpad.domain.jdk.EventEvent;
import org.github.krashpad.domain.jdk.ExceptionCountsEvent;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.HeaderEvent;
import org.github.krashpad.domain.jdk.HeapEvent;
import org.github.krashpad.domain.jdk.MemoryEvent;
import org.github.krashpad.domain.jdk.OsEvent;
import org.github.krashpad.domain.jdk.SigInfoEvent;
import org.github.krashpad.domain.jdk.StackEvent;
import org.github.krashpad.domain.jdk.TimeElapsedTimeEvent;
import org.github.krashpad.domain.jdk.TimeEvent;
import org.github.krashpad.domain.jdk.VmArgumentsEvent;
import org.github.krashpad.domain.jdk.VmInfoEvent;
import org.github.krashpad.domain.jdk.VmOperationEvent;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.JdkUtil.Application;
import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;
import org.github.krashpad.util.jdk.JdkUtil.JavaVendor;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestAnalysis {
    @Test
    void testAdoptOpenJdk() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset17.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_LINUX_ZIP),
                Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_ADOPTOPENJDK_POSSIBLE),
                Analysis.INFO_ADOPTOPENJDK_POSSIBLE + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX),
                Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX + " analysis not identified.");
    ***REMOVED***

    @Test
    void testAttachMechanismDisabled() {

    ***REMOVED***

    @Test
    void testAws() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset34.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_STORAGE_AWS),
                Analysis.INFO_STORAGE_AWS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testBisasedLockingDisabledNotShenandoah() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseBiasedLocking -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED),
                Analysis.WARN_OPT_BIASED_LOCKING_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testBisasedLockingDisabledShenandoah() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+UseShenandoahGC -Xss128k -XX:-UseBiasedLocking -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED),
                Analysis.WARN_OPT_BIASED_LOCKING_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testBuildDateIsNotRedHat() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f72e3ca8000-7f72e4a45000 r-xp 00000000 103:02 45812172                  "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.322.b06-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.322-b06) for linux-amd64 JRE (1.8.0_322-b06), built on "
                + "Jan 27 2022 17:54:59 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        String os = "OS:CentOS Linux release 7.9.2009 (Core)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.isRhBuildString(), "Red Hat build string not identified.");
        assertTrue(fel.isRhVersion(), "Red Hat version not identified.");
        assertEquals(ErrUtil.getDate("Jan 27 2022 17:54:59"), fel.getJdkBuildDate(), "Build date not correct.");
        assertFalse(fel.isRhBuildDate(), "Red Hat build date incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_POSSIBLE),
                Analysis.INFO_RH_BUILD_POSSIBLE + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_NOT),
                Analysis.INFO_RH_BUILD_NOT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testBytecodeBackgroundCompilationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xbatch -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED),
                Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED + " analysis not identified.");
        fel.getVmArgumentsEvents().clear();
        jvm_args = "jvm_args: -Xss128k -XX:-BackgroundCompilation -Xms2048M";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED),
                Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCannotGetLibraryInformation() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "Can not get library information for pid = 123456";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION),
                Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCGroupMemoryLimit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CGROUP_MEMORY_LIMIT),
                Analysis.WARN_OPT_CGROUP_MEMORY_LIMIT + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED),
                Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testClassUnloadingDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-ClassUnloading";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CLASS_UNLOADING_DISABLED),
                Analysis.WARN_OPT_CLASS_UNLOADING_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testClassVerificationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -Xverify:none";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_VERIFY_NONE),
                Analysis.WARN_OPT_VERIFY_NONE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testClientFlag() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -client -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        // 64-bit is assumed
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_CLIENT),
                Analysis.INFO_OPT_CLIENT + " analysis not identified.");
        // Specify 32-bit
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CLIENT),
                Analysis.INFO_OPT_CLIENT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCloudPerfDataDisk() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        ContainerInfoEvent containerInfoEvent = new ContainerInfoEvent("TEST");
        fel.getContainerInfoEvents().add(containerInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CONTAINER_PERF_DATA_DISK),
                Analysis.WARN_OPT_CONTAINER_PERF_DATA_DISK + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_PERF_DATA_DISABLED),
                Analysis.INFO_OPT_PERF_DATA_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCmsClassUnloadingDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:-CMSClassUnloadingEnabled";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED),
                Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED + " analysis not identified.");
        // Don't report if CMS collector not being used
        fel.getVmArgumentsEvents().clear();
        fel.getAnalysis().clear();
        jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:-CMSClassUnloadingEnabled";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED),
                Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCmsCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PAR_NEW),
                GarbageCollector.PAR_NEW + " collector not identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.CMS),
                GarbageCollector.CMS + " collector not identified.");
    ***REMOVED***

    /**
     * Test if CMS collector disabled with -XX:-UseConcMarkSweepGC -XX:-UseParNewGC.
     */
    @Test
    void testCmsDisabledJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().clear();
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if CMS collector disabled with -XX:-UseConcMarkSweepGC -XX:-UseParNewGC.
     */
    @Test
    void testCmsDisabledJdkUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseParNewGC -XX:-UseConcMarkSweepGC "
                + "-XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCmsIncrementalMode() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode ";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String cpu = "CPU:total 8 (2 cores per cpu, 1 threads per core)";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpu);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_CMS_INCREMENTAL_MODE),
                Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis not identified.");
        // Don't report if CMS collector not being used
        fel.getVmArgumentsEvents().clear();
        fel.getAnalysis().clear();
        jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:+CMSIncrementalMode ";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_CMS_INCREMENTAL_MODE),
                Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCmsIncrementalModeWithInitatingOccupancyFraction() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode "
                + "-XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT),
                Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT + " analysis not identified.");
        // Don't report if CMS collector not being used
        fel.getVmArgumentsEvents().clear();
        fel.getAnalysis().clear();
        jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:+CMSIncrementalMode "
                + "-XX:CMSInitiatingOccupancyFraction=70";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT),
                Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCmsInitatingOccupancyOnlyMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING),
                Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING + " analysis not identified.");
        // Don't report if CMS collector not being used
        fel.getVmArgumentsEvents().clear();
        fel.getAnalysis().clear();
        jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING),
                Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCmsParallelInitialMarkEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:-CMSParallelInitialMarkEnabled";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED),
                Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED + " analysis not identified.");
        // Don't report if CMS collector not being used
        fel.getVmArgumentsEvents().clear();
        fel.getAnalysis().clear();
        jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:-UseConcMarkSweepGC -XX:-CMSParallelInitialMarkEnabled";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED),
                Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCmsParallelRemarkEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:-CMSParallelRemarkEnabled";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED),
                Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED + " analysis not identified.");
        // Don't report if CMS collector not being used
        fel.getVmArgumentsEvents().clear();
        fel.getAnalysis().clear();
        jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:-UseConcMarkSweepGC -XX:-CMSParallelRemarkEnabled";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED),
                Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    /**
     * Test if PAR_NEW collector is enabled/disabled when the CMS collector is not used.
     */
    @Test
    void testCmsParNewCruftJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParNewGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if PAR_NEW collector is enabled/disabled when the CMS collector is not used.
     */
    @Test
    void testCmsParNewCruftJdkUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseParNewGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if PAR_NEW collector is enabled/disabled when the CMS collector is not used.
     */
    @Test
    void testCmsParNewCruftNone() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT + " analysis incorrectly identified.");
    ***REMOVED***

    /**
     * Test analysis just in time (JIT) compiler disabled.
     */
    @Test
    void testCompilationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xint -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_COMPILE_DISABLED),
                Analysis.WARN_OPT_BYTECODE_COMPILE_DISABLED + " analysis not identified.");
    ***REMOVED***

    /**
     * Test analysis compilation on first invocation enabled.
     */
    @Test
    void testCompilationOnFirstInvocation() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xcomp -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION),
                Analysis.WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompilerThreadC2BeautifyLoops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset72.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS),
                Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompilerThreadC2MininodeIdeal() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset76.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL),
                Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompilerThreads() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:CICompilerCount=2 -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_CI_COMPILER_COUNT),
                Analysis.INFO_OPT_CI_COMPILER_COUNT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompileThresholdIgnored() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -XX:CompileThreshold=200 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_COMPILE_THRESHOLD_IGNORED),
                Analysis.INFO_OPT_COMPILE_THRESHOLD_IGNORED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedClassPointersDisabledHeapLt32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedClassPointers -XX:+UseCompressedOops -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G),
                Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedClassPointersDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedClassPointers -XX:+UseCompressedOops";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK),
                Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedClassPointersEnabledCompressedOopsDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK),
                Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedClassPointersEnabledHeapGt32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseCompressedClassPointers -Xmx32g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G),
                Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G),
                Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G + " incorrectly identified.");
    ***REMOVED***

    @Test
    void testCompressedClassSpaceSizeWithCompressedClassPointersDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:CompressedClassSpaceSize=1G -XX:-UseCompressedClassPointers "
                + "-Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK),
                Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED),
                Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedClassSpaceSizeWithCompressedOopsDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:CompressedClassSpaceSize=1G -XX:-UseCompressedOops -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK),
                Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED),
                Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedOopsDisabledHeapEqual32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xmx32G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G),
                Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK),
                Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCompressedOopsDisabledHeapGreater32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xmx40G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G),
                Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCompressedOopsDisabledHeapLess32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G),
                Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompressedOopsEnabledHeapGreater32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseCompressedOops -Xmx40G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G),
                Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G),
                Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G + " incorrectly identified.");
    ***REMOVED***

    @Test
    void testContainer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset47.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_CGROUP),
                Analysis.INFO_CGROUP + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_MEMORY_JVM_NE_SYSTEM),
                Analysis.INFO_MEMORY_JVM_NE_SYSTEM + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_CGROUP_MEMORY_LIMIT),
                Analysis.INFO_CGROUP_MEMORY_LIMIT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCrashStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset33.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_JVM_STARTUP_FAILS),
                Analysis.INFO_JVM_STARTUP_FAILS + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGNO_SIGSEGV),
                Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testD64Flag() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -d64 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        // 64-bit is assumed
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_D64_REDUNDANT),
                Analysis.INFO_OPT_D64_REDUNDANT + " analysis not identified.");
        // Specify 32-bit
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_D64_REDUNDANT),
                Analysis.INFO_OPT_D64_REDUNDANT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testDbcp2Postgresql() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "j  org.postgresql.Driver.connect(Ljava/lang/String;Ljava/util/Properties;)Ljava/sql/"
                + "Connection;+222";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "j  org.apache.commons.dbcp2.BasicDataSource.getConnection()Ljava/sql/Connection;+55";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_DBCP2), Analysis.INFO_DBCP2 + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_POSTGRESQL_CONNECTION),
                Analysis.INFO_POSTGRESQL_CONNECTION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testDebug() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xdebug -XX:+PrintHeapAtGC -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_DEBUG),
                Analysis.INFO_OPT_DEBUG + " analysis not identified.");
    ***REMOVED***

    @Test
    void testDefaultCollectorJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.G1),
                GarbageCollector.G1 + " collector not identified.");
    ***REMOVED***

    @Test
    void testDefaultCollectorJdk17() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.1+12-LTS) for linux-amd64 JRE (17.0.1+12-LTS), built "
                + "on Oct 28 2021 01:59:13 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-3)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.G1),
                GarbageCollector.G1 + " collector not identified.");
    ***REMOVED***

    @Test
    void testDefaultCollectorJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
    ***REMOVED***

    /**
     * Test analysis huge DGC intervals.
     */
    @Test
    void testDgcHugeIntervals() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Dsun.rmi.dgc.client.gcInteraval=9223372036854775807 "
                + "-Dsun.rmi.dgc.server.gcInterval=9223372036854775807";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_LARGE),
                Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_LARGE + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_LARGE),
                Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_LARGE + " analysis nto identified.");
    ***REMOVED***

    /**
     * Test analysis not small DGC intervals.
     */
    @Test
    void testDgcNotSmallIntervals() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL + " analysis incorrectly identified.");
    ***REMOVED***

    /**
     * Test DGC redundant options analysis.
     */
    @Test
    void testDgcRedundantOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+DisableExplicitGC -Dsun.rmi.dgc.client.gcInterval=14400000 "
                + "-Dsun.rmi.dgc.server.gcInterval=24400000";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT),
                Analysis.INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT),
                Analysis.INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT + " analysis not identified.");
    ***REMOVED***

    /**
     * Test analysis small DGC intervals
     */
    @Test
    void testDgcSmallIntervals() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Dsun.rmi.dgc.client.gcInterval=3599999 -Dsun.rmi.dgc.server.gcInterval=3599999";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL + " analysis not identified.");
    ***REMOVED***

    /**
     * Test analysis small DGC intervals with explicit gc disable
     */
    @Test
    void testDgcSmallIntervalsDisableExplicitGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Dsun.rmi.dgc.client.gcInterval=3599999 -Dsun.rmi.dgc.server.gcInterval=3599999 "
                + "-XX:+DisableExplicitGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL),
                Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testDirectByteBufferUnsynchronizedAccess() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset24.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(Application.JBOSS_EAP7, fel.getApplication(), "Application not correct.");
        assertEquals("v  ~StubRoutines::jbyte_disjoint_arraycopy", fel.getStackFrameTop(),
                "To stack frame not correct.");
        assertTrue(fel.isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER), "DirectByteBuffer class not identified in stack.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION),
                Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7),
                Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7 + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED),
                Analysis.WARN_OPT_EXPLICIT_GC_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    /**
     * Test DisableExplicitGC in combination with ExplicitGCInvokesConcurrent.
     */
    @Test
    void testDisableExplictGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+DisableExplicitGC -XX:+ExplicitGCInvokesConcurrent -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED),
                Analysis.WARN_OPT_EXPLICIT_GC_DISABLED + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT),
                Analysis.WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testEliminateLocks() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -XX:+EliminateLocks";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_ELIMINATE_LOCKS_ENABLED),
                Analysis.INFO_OPT_ELIMINATE_LOCKS_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testErrorJdk8LibcCfree() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset78.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_LIBC_CFREE),
                Analysis.ERROR_JDK8_LIBC_CFREE + " analysis not identified.");

    ***REMOVED***

    @Test
    void testErrorStubroutinesHeaderOnly() {
        FatalErrorLog fel = new FatalErrorLog();
        String logLine = "***REMOVED*** v  ~StubRoutines::jbyte_disjoint_arraycopy";
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        fel.getHeaderEvents().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_STUBROUTINES),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");
    ***REMOVED***

    @Test
    void testExplicitGCInvokesConcurrentAndUnloadsClassesDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+DisableExplicitGC -XX:-ExplicitGCInvokesConcurrentAndUnloadsClasses";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA),
                Analysis.INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    @Test
    void testExplictGcNotConcurrent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String logLine = "concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, "
                + "0x00000007c0000000, 0x00000007c0000000)";
        HeapEvent heapEvent = new HeapEvent(logLine);
        fel.getHeapEvents().add(heapEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_NOT_CONCURRENT),
                Analysis.WARN_OPT_EXPLICIT_GC_NOT_CONCURRENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFailedToMapBytes() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset58.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_LIMIT_OOPS),
                Analysis.ERROR_OOME_LIMIT_OOPS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFailedToMapBytesNoSwap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset75.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_JVM),
                Analysis.ERROR_OOME_JVM + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFatalErrorLogAncient() {
        FatalErrorLog fel = new FatalErrorLog();
        String time = "time: Tue Aug 18 14:10:59 2020";
        TimeEvent timeEven = new TimeEvent(time);
        fel.setTimeEvent(timeEven);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_FATAL_ERROR_LOG_ANCIENT),
                Analysis.WARN_FATAL_ERROR_LOG_ANCIENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFpe() {
        FatalErrorLog fel = new FatalErrorLog();
        String siginfo = "siginfo: si_signo: 8 (SIGFPE), si_code: 1 (FPE_INTDIV), si_addr: 0x00007fdfe95e789f";
        SigInfoEvent event = new SigInfoEvent(siginfo);
        fel.setSigInfoEvent(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGNO_SIGFPE),
                Analysis.INFO_SIGNO_SIGFPE + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGCODE_FPE_INTDIV),
                Analysis.INFO_SIGCODE_FPE_INTDIV + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_FPE), Analysis.ERROR_FPE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFreetypeFontScalerGetGlyphImageNative() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset54.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE),
                Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    @Test
    void testG1MixedGCLiveThresholdPercent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -XX:+UseG1GC -XX:G1MixedGCLiveThresholdPercent=50";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT),
                Analysis.WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testG1ParScanThreadStateCopyToSurvivorSpace() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset60.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String stackFrame1 = "V  [libjvm.so+0x5b4ab3]  G1ParScanThreadState::copy_to_survivor_space(InCSetState, "
                + "oopDesc*, markOopDesc*)+0x2e3";
        String stackFrame2 = "V  [libjvm.so+0x5b54ae]  G1ParScanThreadState::trim_queue()+0x59e";
        assertEquals(stackFrame1, fel.getStackFrame(1), "Stack frame 1 not correct.");
        assertEquals(stackFrame2, fel.getStackFrame(2), "Stack frame 2 not correct.");
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE),
                Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testG1SummarizeRSetStats() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+G1SummarizeRSetStats -XX:G1SummarizeRSetStatsPeriod=1";
        VmArgumentsEvent vmArgumentEvent = new VmArgumentsEvent(jvm_args);
        HeapEvent heapEvent = new HeapEvent(
                "garbage-first heap   total 15728640K, used 2720924K [0x0000000300000000, 0x0000000300407800, "
                        + "0x00000006c0000000)");
        fel.getHeapEvents().add(heapEvent);
        fel.getVmArgumentsEvents().add(vmArgumentEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_G1_SUMMARIZE_RSET_STATS_OUTPUT),
                Analysis.INFO_OPT_G1_SUMMARIZE_RSET_STATS_OUTPUT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testGuaranteedSafepointInterval() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockDiagnosticVMOptions -XX:GuaranteedSafepointInterval=100000 "
                + "-Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED),
                Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_DIAGNOSTICS_GUARANTEED_SAFEPOINT_INTERVAL),
                Analysis.WARN_OPT_DIAGNOSTICS_GUARANTEED_SAFEPOINT_INTERVAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHashMap() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "J 28841 C2 java.util.HashMap.putVal(ILjava/lang/Object;Ljava/lang/Object;ZZ)Ljava/lang/"
                + "Object; (300 bytes) @ 0x00007f5c5613467a [0x00007f5c561320a0+0x25da]";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 34843 C2 com.example.Service.save(Lcom/example/Entity;Ljava/lang/String;Z)V (83 bytes) "
                + "@ 0x00007f5c5514d8fc [0x00007f5c5514d420+0x4dc]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        String logline = "7f5c61494000-7f5c62233000 r-xp 00000000 fd:00 17171138                   "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.312-b07) for linux-amd64 JRE (1.8.0_312-b07), "
                + "built on Oct 15 2021 04:33:40 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_HASHMAP),
                Analysis.ERROR_HASHMAP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHeapDumpOnOutOfMemoryErrorDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:-HeapDumpOnOutOfMemoryError";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED),
                Analysis.WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHeapDumpOnOutOfMemoryErrorMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_DUMP_ON_OOME_MISSING),
                Analysis.INFO_OPT_HEAP_DUMP_ON_OOME_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHeapDumpOnOutOfMemoryErrorPathIsDirectory() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError "
                + "-XX:HeapDumpPath=/path/to";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_HEAP_DUMP_PATH_FILENAME),
                Analysis.WARN_OPT_HEAP_DUMP_PATH_FILENAME + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testHeapDumpOnOutOfMemoryErrorPathIsFileName() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError "
                + "-XX:HeapDumpPath=/path/to/heap.hprof";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_HEAP_DUMP_PATH_FILENAME),
                Analysis.WARN_OPT_HEAP_DUMP_PATH_FILENAME + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHeapDumpOnOutOfMemoryErrorPathMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_DUMP_PATH_MISSING),
                Analysis.INFO_OPT_HEAP_DUMP_PATH_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHeapMaxMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset56.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_MAX_MISSING),
                Analysis.INFO_OPT_HEAP_MAX_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testIbmToolkit() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff46c40000-7fff46c80000 r--s 00520000 fd:0a 67109322                   "
                + "/path/to/jt400.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_IBM_TOOLKIT),
                Analysis.INFO_IBM_TOOLKIT + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    @Test
    void testInitialNotEqualMaxHeap() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xmx4096M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_MIN_NOT_EQUAL_MAX),
                Analysis.INFO_OPT_HEAP_MIN_NOT_EQUAL_MAX + " analysis not identified.");
    ***REMOVED***

    @Test
    void testInstrumentation() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -javaagent:/path/to/appdynamics/javaagent.jar -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_INSTRUMENTATION),
                Analysis.INFO_OPT_INSTRUMENTATION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testInsufficientMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset27.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.isRhBuildOpenJdk(), "RH build of OpenJDK incorrectly identified.");
        long physicalMemory = JdkUtil.convertSize(15995796, 'K', Constants.PRECISION_REPORTING);
        assertEquals(physicalMemory, fel.getJvmMemTotal(), "Physical memory not correct.");
        long physicalMemoryFree = JdkUtil.convertSize(241892, 'K', Constants.PRECISION_REPORTING);
        assertEquals(physicalMemoryFree, fel.getJvmMemFree(), "Physical memory free not correct.");
        long swap = JdkUtil.convertSize(10592252, 'K', Constants.PRECISION_REPORTING);
        assertEquals(swap, fel.getJvmSwap(), "Swap not correct.");
        long swapFree = JdkUtil.convertSize(4, 'K', Constants.PRECISION_REPORTING);
        assertEquals(swapFree, fel.getJvmSwapFree(), "Swap free not correct.");
        long heapInitial = JdkUtil.convertSize(2048, 'M', Constants.PRECISION_REPORTING);
        assertEquals(heapInitial, fel.getHeapInitialSize(), "Heap initial size not correct.");
        long heapMax = JdkUtil.convertSize(8192, 'M', Constants.PRECISION_REPORTING);
        assertEquals(heapMax, fel.getHeapMaxSize(), "Heap max size not correct.");
        long heapAllocationYoung = JdkUtil.convertSize(2761728, 'K', Constants.PRECISION_REPORTING);
        long heapAllocationOld = JdkUtil.convertSize(4838912, 'K', Constants.PRECISION_REPORTING);
        long heapAllocation = heapAllocationYoung + heapAllocationOld;
        assertEquals(heapAllocation, fel.getHeapAllocation(), "Heap allocation not correct.");
        long heapUsed = JdkUtil.convertSize(0 + 2671671, 'K', Constants.PRECISION_REPORTING);
        assertEquals(heapUsed, fel.getHeapUsed(), "Heap used not correct.");
        long metaspaceMax = JdkUtil.convertSize(8192, 'M', Constants.PRECISION_REPORTING);
        assertEquals(metaspaceMax, fel.getMetaspaceMaxSize(), "Metaspace max size not correct.");
        long metaspaceAllocation = JdkUtil.convertSize(471808, 'K', Constants.PRECISION_REPORTING);
        assertEquals(metaspaceAllocation, fel.getMetaspaceAllocation(), "Metaspace allocation not correct.");
        long metaspaceUsed = JdkUtil.convertSize(347525, 'K', Constants.PRECISION_REPORTING);
        assertEquals(metaspaceUsed, fel.getMetaspaceUsed(), "Metaspace used not correct.");
        long directMemoryMax = JdkUtil.convertSize(0, 'G', Constants.PRECISION_REPORTING);
        assertEquals(directMemoryMax, fel.getDirectMemoryMaxSize(), "Direct Memory mx not correct.");
        assertEquals(1024, fel.getThreadStackSize(), "Thread stack size not correct.");
        assertEquals(225, fel.getJavaThreadCount(), "Thread count not correct.");
        long threadMemory = JdkUtil.convertSize(1024 * 225, 'K', Constants.PRECISION_REPORTING);
        assertEquals(threadMemory, fel.getThreadStackMemory(), "Thread memory not correct.");
        long codeCacheSize = JdkUtil.convertSize(420, 'M', Constants.PRECISION_REPORTING);
        assertEquals(codeCacheSize, fel.getCodeCacheSize(), "Code cache size not correct.");
        assertEquals(heapMax + metaspaceMax + directMemoryMax + threadMemory + codeCacheSize, fel.getJvmMemoryMax(),
                "Jvm memory not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testItext() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff467a0000-7fff467c0000 r--s 00220000 fd:0a 67109364                   "
                + "/path/to/itextpdf-5.5.13.1.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_ITEXT), Analysis.INFO_ITEXT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk11AutomaticLogFileRotationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xlog:gc*:file=/path/to/gc.log::filesize=0";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_0),
                Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_0 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk11GcLogFileOverwrite() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xlog:gc*,safepoint=info:file=gc.log:uptimemillis:filecount=0,filesize=50M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_OVERWRITE),
                Analysis.WARN_OPT_JDK11_GC_LOG_FILE_OVERWRITE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk11LogFileRotationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xlog:gc*:file=/path/to/gc.log::filecount=0,filesize=50M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_ROTATION_DISABLED),
                Analysis.WARN_OPT_JDK11_GC_LOG_FILE_ROTATION_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk11LogFileSizeSmall() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xlog:gc*:file=/path/to/gc.log::filecount=10,filesize=4M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_SMALL),
                Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_SMALL + " analysis not identified.");
    ***REMOVED***

    /**
     * HEAP_MIN_NOT_EQUAL_MAX Test with gc details missing.
     */
    @Test
    void testJdk11PrintGCDetailsMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xlog:gc=debug:file=/path/to/gc-%t.log:time,pid,tid,level,"
                + "tags:filesize=1G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PRINT_GC_DETAILS_MISSING),
                Analysis.INFO_OPT_JDK11_PRINT_GC_DETAILS_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk7() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (24.51-b03) for linux-amd64 JRE (1.7.0_55-b13), "
                + "built on Apr  9 2014 12:07:12 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-4)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK_VERSION_UNSUPPORTED),
                Analysis.ERROR_JDK_VERSION_UNSUPPORTED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8DeflaterContention() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset59.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        String stackFrameTopCompiledJavaCode = "J 3602  java.util.zip.Deflater.deflateBytes(J[BIII)I (0 bytes) @ "
                + "0x00007f641d41accd [0x00007f641d41ac00+0xcd]";
        assertEquals(stackFrameTopCompiledJavaCode, fel.getStackFrameTopCompiledJavaCode(),
                "Top compiled Java code (J) stack frame not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_DEFLATER_CONTENTION),
                Analysis.ERROR_JDK8_DEFLATER_CONTENTION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8GcLogFileRotationDisabledOverwrite() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+PrintGC -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps "
                + "-XX:+PrintGCApplicationStoppedTime -XX:-UseGCLogFileRotation";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE),
                Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8GcLogFileRotationNotEnabledOverwrite() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+PrintGC -Xloggc:gc.log -XX:+PrintGCDetails -XX:+PrintGCTimeStamps "
                + "-XX:+PrintGCApplicationStoppedTime";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE),
                Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8LogFileRotationNotEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xloggc:gc.log";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED),
                Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8LogFileSizeSmall() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:NumberOfGCLogFiles=5 -XX:+UseGCLogFileRotation "
                + "-XX:GCLogFileSize=8192";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_SIZE_SMALL),
                Analysis.WARN_OPT_JDK8_GC_LOG_FILE_SIZE_SMALL + " analysis not identified.");
    ***REMOVED***

    /**
     * Test with PrintGCDetails missing.
     */
    @Test
    void testJdk8PrintGCDetailsMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_GC_DETAILS_MISSING),
                Analysis.INFO_OPT_JDK8_PRINT_GC_DETAILS_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8ZipFileContention() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset23.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        String stackFrameTopCompiledJavaCode = "J 302  java.util.zip.ZipFile.getEntry(J[BZ)J (0 bytes) @ "
                + "0x00007fa287303dce [0x00007fa287303d00+0xce]";
        assertEquals(stackFrameTopCompiledJavaCode, fel.getStackFrameTopCompiledJavaCode(),
                "Top compiled Java code (J) stack frame not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdkDebugSymbolsMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset18.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_LINUX_ZIP),
                Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS),
                Analysis.WARN_DEBUG_SYMBOLS + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_JDK_ANCIENT),
                Analysis.INFO_JDK_ANCIENT + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_COMPILED_JAVA_CODE),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdkUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: test";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK_VERSION_UNKNOWN),
                Analysis.ERROR_JDK_VERSION_UNKNOWN + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJffi() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "3ffe9c800000-3ffe9c820000 r-xp 00000000 fd:00 1107498958                 "
                + "/path/to/jffi3667428567419554714.so (deleted)";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_JFFI), Analysis.INFO_JFFI + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJfr() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset52.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JFR),
                Analysis.INFO_OPT_JFR + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJfrClassTransformed() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [libjvm.so+0x6b67f7]  JfrEventClassTransformer::on_klass_creation(InstanceKlass*&, "
                + "ClassFileParser&, Thread*)+0xa17";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String logline = "7f55dbe9a000-7f55dcc26000 r-xp 00000000 08:05 34105880                   "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-1.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), "
                + "built on Jul 12 2020 18:53:50 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED),
                Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJfrPdGetTopFrame() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset52.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JFR_PD_GET_TOP_FRAME),
                Analysis.ERROR_JFR_PD_GET_TOP_FRAME + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJmx() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+ManagementServer -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JMX_ENABLED),
                Analysis.INFO_OPT_JMX_ENABLED + " analysis not identified.");
        fel.getVmArgumentsEvents().clear();
        jvm_args = "jvm_args: -Xss128k -Dcom.sun.management.jmxremote -Xms2048M";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JMX_ENABLED),
                Analysis.INFO_OPT_JMX_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJna() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "7f99774f8000-7f99775f7000 ---p 00017000 00:27 165351280                  "
                + "/tmp/jna-100343/jna17878442429968131541.tmp (deleted)";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_JNA), Analysis.INFO_JNA + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJnaInvolvedRedHatJdk() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  [libcrypt.so+0x1825]";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 14265  com.sun.jna.Native.invokePointer(Lcom/sun/jna/Function;JI[Ljava/lang/Object;)J "
                + "(0 bytes) @ 0x00007fb6f9855969 [0x00007fb6f9855900+0x69]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        String logline = "7f7dc59c6000-7f7dc673b000 r-xp 00000000 fd:01 17006104                   "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os = "OS:Red Hat Enterprise Linux release 8.3 (Ootpa)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.282-b08) for linux-amd64 JRE (1.8.0_282-b08), "
                + "built on Jan 17 2021 16:21:17 by \"mockbuild\" with gcc 8.3.1 20191121 (Red Hat 8.3.1-5)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.isJnaCrash(), "JNA crash not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JNA_RH),
                Analysis.ERROR_JNA_RH + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJnaRedHatJdk() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset22.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String stackFrame1 = "C  [jna8588255081773605818.tmp+0x13c480]  CkMultiByteBase::nextIdx()+0x10";
        String stackFrame2 = "j  com.sun.jna.Native.invokePointer(Lcom/sun/jna/Function;JI[Ljava/lang/Object;)J+0";
        assertEquals(stackFrame1, fel.getStackFrame(1), "Stack frame 1 not correct.");
        assertEquals(stackFrame2, fel.getStackFrame(2), "Stack frame 2 not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JNA_RH),
                Analysis.ERROR_JNA_RH + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJvmUserNeUsername() {
        FatalErrorLog fel = new FatalErrorLog();
        String username = "USERNAME=user1";
        EnvironmentVariablesEvent environmentVariablesEvent = new EnvironmentVariablesEvent(username);
        fel.getEnvironmentVariablesEvents().add(environmentVariablesEvent);
        String hsperfdata = "7ff0f61d2000-7ff0f61da000 rw-s 00000000 fd:01 33563495                   "
                + "/tmp/hsperfdata_user2/92333";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(hsperfdata);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_JVM_USER_NE_USERNAME),
                Analysis.INFO_JVM_USER_NE_USERNAME + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLargePageSizeInBytesLinux() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -XX:+UseLargePages -XX:LargePageSizeInBytes=4m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_LINUX),
                Analysis.INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_LINUX + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLargePageSizeInBytesWindows() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -XX:+UseLargePages -XX:LargePageSizeInBytes=4m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String os = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.3630)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS),
                Analysis.INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLatestRelease() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset14.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LATEST),
                Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.");
        assertEquals(736, ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)),
                "Release days diff not correct.");
        assertEquals(9, JdkUtil.getLatestJdkReleaseNumber(fel) - JdkUtil.getJdkReleaseNumber(fel),
                "Release ***REMOVED*** diff not correct.");
    ***REMOVED***

    @Test
    void testLibaioContextDone() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset46.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_LIBAIO_CONTEXT_DONE),
                Analysis.ERROR_LIBAIO_CONTEXT_DONE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLinkageError() {
        String logLine = "LinkageErrors=5276";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_LINKAGE),
                Analysis.ERROR_LINKAGE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLogFileNumberWithRotationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:NumberOfGCLogFiles=5 -XX:-UseGCLogFileRotation -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_DISABLED),
                Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_DISABLED + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_NUM_ROTATION_DISABLED),
                Analysis.WARN_OPT_JDK8_GC_LOG_FILE_NUM_ROTATION_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMaxPermSize() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_MAX_PERM_SIZE),
                Analysis.INFO_OPT_MAX_PERM_SIZE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMetadataOnStackMark() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset73.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK),
                Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMetaspaceEq32g() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx32g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_METASPACE_CLASS_METADATA),
                Analysis.INFO_OPT_METASPACE_CLASS_METADATA + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMetaspaceGt32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_METASPACE_CLASS_METADATA),
                Analysis.INFO_OPT_METASPACE_CLASS_METADATA + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMetaspaceLt32g() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_METASPACE_CLASS_METADATA_AND_COMP_CLASS_SPACE),
                Analysis.INFO_OPT_METASPACE_CLASS_METADATA_AND_COMP_CLASS_SPACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMinHeapDeltaBytes() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1g -XX:MinHeapDeltaBytes=12345 -Xmx1g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_MIN_HEAP_DELTA_BYTES),
                Analysis.INFO_OPT_MIN_HEAP_DELTA_BYTES + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMmapDeleted() {
        FatalErrorLog fel = new FatalErrorLog();
        String library = "7ca8cf3d6000-7ca8cfdd6000 rw-s 00000000 fd:00 1074566196                 "
                + "/var/lib/kafka/data/kafka-log0/something/00000000000002627674.index.deleted (deleted)";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_MMAP_DELETED),
                Analysis.WARN_MMAP_DELETED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testModuleEntryPurgeAllModuleReads() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [jvm.dll+0x5ab390]  ModuleEntryTable::purge_all_module_reads+0x140  (moduleentry.cpp:444)";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "V  [jvm.dll+0x1ce246]  ClassLoaderDataGraph::do_unloading+0x1c6  (classloaderdata.cpp:1435)";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        String logline = "0x00007ffdd2230000 - 0x00007ffdd2d76000         "
                + "D:\\Java\\jdk11.0.7.10\\bin\\server\\jvm.dll";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.4651)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.7+10-LTS) for windows-amd64 JRE (11.0.7+10-LTS), "
                + "built on Apr  9 2020 00:20:14 by \"\" with MS VC++ 15.9 (VS2017)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_MODULE_ENTRY_PURGE_READS),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_JVM_DLL),
                Analysis.ERROR_JVM_DLL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testModuleEntryPurgeReads() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [libjvm.so+0xbfb228]  ModuleEntry::purge_reads()+0x118";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "V  [libjvm.so+0xbfb338]  ModuleEntryTable::purge_all_module_reads()+0x38";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        String logline = "7f03a6cbf000-7f03a7ef9000 r-xp 00000000 fd:00 1180422                    "
                + "/usr/lib/jvm/java-11-openjdk-11.0.12.0.7-0.el7_9.x86_64/lib/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.12+7-LTS) for linux-amd64 JRE (11.0.12+7-LTS), built "
                + "on Jul 14 2021 00:06:01 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_MODULE_ENTRY_PURGE_READS),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_JVM_DLL),
                Analysis.ERROR_JVM_DLL + " analysis not identified.");
    ***REMOVED***

    /**
     * Test analysis if native library being used.
     */
    @Test
    void testNative() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xms2048M -agentpath:/path/to/agent.so -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_NATIVE),
                Analysis.INFO_OPT_NATIVE + " analysis not identified.");
    ***REMOVED***

    /**
     * Test analysis if new space &gt; old space.
     */
    @Test
    void testNewRatioInverted() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:NewSize=2g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_NEW_RATIO_INVERTED),
                Analysis.INFO_OPT_NEW_RATIO_INVERTED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testNfs() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "7f5f66892000-7f5f6757a000 r-xp 00000000 00:38 1062721                    "
                + "/tools/java/jdk1.8.0_201/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_STORAGE_NFS),
                Analysis.INFO_STORAGE_NFS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testNoMemoryEvent() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_SWAP_DISABLED),
                Analysis.INFO_SWAP_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testNoStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset49.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_STACK_NO_VM_CODE),
                Analysis.INFO_STACK_NO_VM_CODE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testNotLts() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset16.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaSpecification.JDK12, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LTS),
                Analysis.WARN_JDK_NOT_LTS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testNullPointer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset51.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_NULL_POINTER),
                Analysis.ERROR_NULL_POINTER + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomAmqCli() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset67.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_AMQ_CLI),
                Analysis.ERROR_OOME_AMQ_CLI + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomCompilerThreadC2SslDecode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset79.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE),
                Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOomCompilerThreadWindows() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset80.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_EXTERNAL),
                Analysis.ERROR_OOME_EXTERNAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomeCompressedOops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset42.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_LIMIT_OOPS),
                Analysis.ERROR_OOME_LIMIT_OOPS + " analysis not identified.");
    ***REMOVED***

    void testOomeJavaHeap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset36.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_THROWN_JAVA_HEAP),
                Analysis.ERROR_OOME_THROWN_JAVA_HEAP + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGNO_SIGSEGV),
                Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGCODE_SEGV_MAPERR),
                Analysis.INFO_SIGCODE_SEGV_MAPERR + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomePhysicalMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset29.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.isError("Out of Memory Error"), "Out Of Memory Error not identified.");
        long physicalMemory = JdkUtil.convertSize(24609684, 'K', Constants.PRECISION_REPORTING);
        assertEquals(physicalMemory, fel.getJvmMemTotal(), "Physical memory not correct.");
        long heapInitial = JdkUtil.convertSize(1303, 'M', Constants.PRECISION_REPORTING);
        assertEquals(heapInitial, fel.getHeapInitialSize(), "Heap initial size not correct.");
        long heapMax = JdkUtil.convertSize(16000, 'M', Constants.PRECISION_REPORTING);
        assertEquals(heapMax, fel.getHeapMaxSize(), "Heap max size not correct.");
        long metaspaceMax = JdkUtil.convertSize(1148928, 'K', Constants.PRECISION_REPORTING);
        assertEquals(metaspaceMax, fel.getMetaspaceMaxSize(), "Metaspace max size not correct.");
        long directMemoryMax = JdkUtil.convertSize(0, 'G', Constants.PRECISION_REPORTING);
        assertEquals(directMemoryMax, fel.getDirectMemoryMaxSize(), "Direct Memory mx not correct.");
        assertEquals(1024, fel.getThreadStackSize(), "Thread stack size not correct.");
        assertEquals(67, fel.getJavaThreadCount(), "Thread count not correct.");
        long threadMemory = JdkUtil.convertSize(1024 * 67, 'K', Constants.PRECISION_REPORTING);
        assertEquals(threadMemory, fel.getThreadStackMemory(), "Thread memory not correct.");
        long codeCacheSize = JdkUtil.convertSize(420, 'M', Constants.PRECISION_REPORTING);
        assertEquals(codeCacheSize, fel.getCodeCacheSize(), "Code cache size not correct.");
        assertEquals(heapMax + metaspaceMax + directMemoryMax + threadMemory + codeCacheSize, fel.getJvmMemoryMax(),
                "Jvm memory max not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOomJBossVersion() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset71.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaVendor.AZUL, fel.getJavaVendor(), "Java vendor not correct.");
        assertEquals(Application.JBOSS_VERSION, fel.getApplication(), "Application not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_JBOSS_VERSION),
                Analysis.ERROR_OOME_JBOSS_VERSION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomLibJvm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset61.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        StringBuilder error = new StringBuilder();
        error.append("***REMOVED*** There is insufficient memory for the Java Runtime Environment to continue.");
        error.append(Constants.LINE_SEPARATOR);
        error.append(
                "***REMOVED*** Native memory allocation (mmap) failed to map 32304529408 bytes for committing reserved memory.");
        error.append(Constants.LINE_SEPARATOR);
        error.append("***REMOVED***  INVALID (0xe0000002) at pc=0x0000000000000000, pid=108047, tid=0x00007f67eb450700");
        assertEquals(error.toString(), fel.getError());
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP),
                Analysis.ERROR_OOME_STARTUP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomLimit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset57.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_LIMIT),
                Analysis.ERROR_OOME_LIMIT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset55.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP),
                Analysis.ERROR_OOME_STARTUP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomStartupExternal() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset56.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_MISSING),
                Analysis.INFO_OPT_MISSING + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_EXTERNAL),
                Analysis.ERROR_OOME_STARTUP_EXTERNAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomStartupOvercommit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset66.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_LIMIT_OVERCOMMIT),
                Analysis.ERROR_OOME_STARTUP_LIMIT_OVERCOMMIT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomSwapDisabledG1() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset62.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OOM_G1),
                Analysis.WARN_OOM_G1 + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_SWAP_DISABLED_G1),
                Analysis.WARN_SWAP_DISABLED_G1 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomTomcatShutdown() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset68.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN),
                Analysis.ERROR_OOME_TOMCAT_SHUTDOWN + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomTomcatShutdownStopStop() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset63.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN),
                Analysis.ERROR_OOME_TOMCAT_SHUTDOWN + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOracleJdbcDriverNotTopFrame() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [libjvm.so+0x290d84]  AccessInternal::PostRuntimeDispatch<G1BarrierSet::AccessBarrier"
                + "<548964ul, G1BarrierSet>, (AccessInternal::BarrierType)2, 548964ul>::oop_access_barrier(void*)+0x4";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "C  [libocijdbc11.so+0x458c]  Java_oracle_jdbc_driver_T2CConnection_t2cSetSessionTimeZone+0x5";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        String logline = "7f6e73a91000-7f6e74d08000 r-xp 00000000 fd:00 8632767                    "
                + "/usr/lib/jvm/java-17-openjdk-17.0.4.0.8-2.el8_6.x86_64/lib/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        String os1 = "OS:";
        OsEvent osEvent1 = new OsEvent(os1);
        fel.getOsEvents().add(osEvent1);
        String os2 = "Red Hat Enterprise Linux release 8.6 (Ootpa)";
        OsEvent osEvent2 = new OsEvent(os2);
        fel.getOsEvents().add(osEvent2);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), "
                + "built on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_ORACLE_JDBC_DRIVER),
                Analysis.ERROR_ORACLE_JDBC_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_ORACLE_JDBC_OCI_DRIVER),
                Analysis.WARN_ORACLE_JDBC_OCI_DRIVER + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOracleJdbcOciDriverWarning() {
        FatalErrorLog fel = new FatalErrorLog();
        StackEvent event1 = new StackEvent(
                "Stack: [0x00007f069a811000,0x00007f069a912000],  sp=0x00007f069a90d248,  free space=1008k");
        StackEvent event2 = new StackEvent(
                "Native frames: (J=compiled Java code, A=aot compiled Java code, j=interpreted, Vv=VM code, "
                        + "C=native code)");
        StackEvent event3 = new StackEvent(
                "V  [libjvm.so+0x5b3c94]  AccessInternal::PostRuntimeDispatch<G1BarrierSet::AccessBarrier<1097844ul, "
                        + "G1BarrierSet>, (AccessInternal::BarrierType)2, 1097844ul>::oop_access_barrier(void*)+0x4");
        StackEvent event4 = new StackEvent(
                "C  [libocijdbc11.so+0x458c]  Java_oracle_jdbc_driver_T2CConnection_t2cSetSessionTimeZone+0x5a");
        fel.getStackEvents().add(event1);
        fel.getStackEvents().add(event2);
        fel.getStackEvents().add(event3);
        fel.getStackEvents().add(event4);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_ORACLE_JDBC_DRIVER),
                Analysis.ERROR_ORACLE_JDBC_DRIVER + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING),
                Analysis.ERROR_ORACLE_JDBC_OCI_LOADING + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_ORACLE_JDBC_OCI_DRIVER),
                Analysis.WARN_ORACLE_JDBC_OCI_DRIVER + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOracleJdbcOciDriverError() {
        FatalErrorLog fel = new FatalErrorLog();
        EventEvent eventEvent = new EventEvent(
                "Event: 86.139 Loaded shared library /ora01/app/oracle/product/11.2.0/client_1/lib/libocijdbc11.so");
        fel.getEventEvents().add(eventEvent);
        TimeElapsedTimeEvent timeElapsedTimeEvent = new TimeElapsedTimeEvent(
                "Time: Mon Sep 12 13:46:23 2022 EDT elapsed time: 86.182142 seconds (0d 0h 1m 26s)");
        fel.setTimeElapsedTimeEvent(timeElapsedTimeEvent);
        assertEquals(86139L,
                fel.getEventTimestamp("^Event: (\\d{1,***REMOVED***\\.\\d{3***REMOVED***) Loaded shared library .+libocijdbc11.so$"),
                "OCI driver load timestamp not correct.");
        assertEquals(86182L, fel.getUptime(), "Uptime not correct.");
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_ORACLE_JDBC_DRIVER),
                Analysis.ERROR_ORACLE_JDBC_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING),
                Analysis.ERROR_ORACLE_JDBC_OCI_LOADING + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_ORACLE_JDBC_OCI_DRIVER),
                Analysis.WARN_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryErrorThrownCompressedClassSpace() {
        String logLine = "OutOfMemoryError class_metaspace_errors=7";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE),
                Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryErrorThrownJavaHeap() {
        String logLine = "OutOfMemoryError java_heap_errors=13";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_THROWN_JAVA_HEAP),
                Analysis.ERROR_OOME_THROWN_JAVA_HEAP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryErrorThrownMetaspace() {
        String logLine = "OutOfMemoryError metaspace_errors=48";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_THROWN_METASPACE),
                Analysis.ERROR_OOME_THROWN_METASPACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testParalleGcThreads() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:ParallelGCThreads=4 -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1),
                Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1 + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_PARALLEL_GC_THREADS_SERIAL),
                Analysis.INFO_OPT_PARALLEL_GC_THREADS_SERIAL + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_PARALLEL_GC_THREADS),
                Analysis.INFO_OPT_PARALLEL_GC_THREADS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testParalleGcThreads1() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:ParallelGCThreads=1 -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1),
                Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testParalleGcThreadsSerial() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseSerialGC -XX:ParallelGCThreads=1 -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1),
                Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1 + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_PARALLEL_GC_THREADS_SERIAL),
                Analysis.INFO_OPT_PARALLEL_GC_THREADS_SERIAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testParallelClassLoading() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockDiagnosticVMOptions -XX:+UnsyncloadClass -Xmx2G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_DIAGNOSTIC_UNSYNCLOAD_CLASS),
                Analysis.WARN_OPT_DIAGNOSTIC_UNSYNCLOAD_CLASS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testParallelCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset26.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
    ***REMOVED***

    /**
     * Test if PARALLEL_OLD collector disabled with -XX:-UseParallelOldGC.
     */
    @Test
    void testParallelOldDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParallelGC -XX:-UseParallelOldGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED),
                Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if PARALLEL_OLD collector disabled when using default collector on JDK8.
     */
    @Test
    void testParallelOldDisabledJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseParallelOldGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED),
                Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if PARALLEL_OLD collector is enabled/disabled when the parallel collector is not used. G1 is the default
     * collector on JDK11.
     */
    @Test
    void testParallelOldfCruftJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParallelOldGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT + " analysis incorrectly identified.");

    ***REMOVED***

    /**
     * Test if PARALLEL_OLD collector is enabled/disabled when the parallel collector is not used. Parallel is the
     * default collector in JDK8.
     */
    @Test
    void testParallelOldfCruftJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParallelOldGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT + " analysis not identified.");

    ***REMOVED***

    /**
     * Test PARALLEL_OLD collector is enabled/disabled when the parallel collector is not used.
     */
    @Test
    void testParallelOldfCruftJdkUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseParallelGC -XX:+UseParallelOldGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED),
                Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    /**
     * Test if PARALLEL_OLD collector is enabled/disabled when the parallel collector is not used false positive.
     */
    @Test
    void testParallelOldfCruftNone() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED),
                Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testPerfDataDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -XX:-UsePerfData -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_PERF_DATA_DISABLED),
                Analysis.INFO_OPT_PERF_DATA_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPermSize() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:PermSize=256m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_PERM_SIZE),
                Analysis.INFO_OPT_PERM_SIZE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPhysicalMemoryInsufficientJvmStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset30.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.isError("Out of Memory Error"), "Out Of Memory Error not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_LIMIT),
                Analysis.ERROR_OOME_STARTUP_LIMIT + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testPrintAdaptiveSizePolicyDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:-PrintAdaptiveSizePolicy";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED),
                Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintAdaptiveSizePolicyEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:+PrintAdaptiveSizePolicy";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED),
                Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintClassHistogram() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintClassHistogram";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM),
                Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintClassHistogramAfterFullGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintClassHistogramAfterFullGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC),
                Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintClassHistogramBeforeFullGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintClassHistogramBeforeFullGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC),
                Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintFLSStatistics() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:PrintFLSStatistics=1 -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_FLS_STATISTICS),
                Analysis.INFO_OPT_JDK8_PRINT_FLS_STATISTICS + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    @Test
    void testPrintGCApplicationConcurrentTime() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xmx4096M -XX:+PrintGCApplicationConcurrentTime";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME),
                Analysis.INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintGcCause() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:+PrintGCCause -XX:-HeapDumpOnOutOfMemoryError";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_GC_CAUSE),
                Analysis.INFO_OPT_JDK8_PRINT_GC_CAUSE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintGcCauseDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:-PrintGCCause -XX:-HeapDumpOnOutOfMemoryError";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_PRINT_GC_CAUSE_DISABLED),
                Analysis.WARN_OPT_JDK8_PRINT_GC_CAUSE_DISABLED + " analysis not identified.");
    ***REMOVED***

    /**
     * Test with PrintGCDetails disabled with -XX:-PrintGCDetails.
     */
    @Test
    void testPrintGCDetailsDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-PrintGCDetails -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_PRINT_GC_DETAILS_DISABLED),
                Analysis.WARN_OPT_JDK8_PRINT_GC_DETAILS_DISABLED + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_GC_DETAILS_MISSING),
                Analysis.INFO_OPT_JDK8_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testPrintHeapAtGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintHeapAtGC -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_HEAP_AT_GC),
                Analysis.INFO_OPT_JDK8_PRINT_HEAP_AT_GC + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintPromotionFailure() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:+PrintPromotionFailure";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_PROMOTION_FAILURE),
                Analysis.INFO_OPT_JDK8_PRINT_PROMOTION_FAILURE + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if -XX:+PrintReferenceGC enabled
     */
    @Test
    void testPrintReferenceGC() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintReferenceGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_REFERENCE_GC_ENABLED),
                Analysis.INFO_OPT_JDK8_PRINT_REFERENCE_GC_ENABLED + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if -XX:+PrintStringDeduplicationStatistics enabled
     */
    @Test
    void testPrintStringDeduplicationStatistics() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintStringDeduplicationStatistics -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_STRING_DEDUP_STATS_ENABLED),
                Analysis.INFO_OPT_JDK8_PRINT_STRING_DEDUP_STATS_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPrintTenuringDistribution() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintTenuringDistribution -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_PRINT_TENURING_DISTRIBUTION),
                Analysis.INFO_OPT_JDK8_PRINT_TENURING_DISTRIBUTION + " analysis not identified.");
    ***REMOVED***

    /**
     * Verify analysis file property key/value lookup.
     */
    @Test
    void testPropertyKeyValueLookup() {
        Analysis[] analysis = Analysis.values();
        for (int i = 0; i < analysis.length; i++) {
            assertNotNull(analysis[i].getKey() + " not found.", analysis[i].getValue());
        ***REMOVED***
    ***REMOVED***

    @Test
    void testPthreadGetcpuclockid() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset32.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_PTHREAD_GETCPUCLOCKID),
                Analysis.ERROR_PTHREAD_GETCPUCLOCKID + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRemoteDebuggingEnabledAgentlib() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset45.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED),
                Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_VMWARE),
                Analysis.INFO_VMWARE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRemoteDebuggingEnabledRunjdwp() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx2g -Xrunjdwp:transport=dt_socket,server=y,address=8787,suspend=n -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED),
                Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRhel6() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fd421b89000-7fd4227ab000 r-xp 00000000 fd:01 264289                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.71-1.b15.el6_7.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.71-b15) for linux-amd64 JRE (1.8.0_71-b15), built on "
                + "Jan 13 2016 21:08:08 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-16)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        String os = "OS:Red Hat Enterprise Linux Server release 6.7 (Santiago)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.isRhBuildOpenJdk(), "Red Hat build of OpenJDK not identified.");
        assertTrue(fel.isRhBuildString(), "Red Hat build string not identified.");
        assertTrue(fel.isRhVersion(), "Red Hat version not identified.");
        assertEquals(ErrUtil.getDate("Jan 13 2016 21:08:08"), fel.getJdkBuildDate(), "Build date not correct.");
        assertTrue(fel.isRhBuildDate(), "Red Hat build date not identified.");
        assertFalse(fel.isRhBuildDateUnknown(), "Red Hat build of OpenJDK date unknown incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_POSSIBLE),
                Analysis.INFO_RH_BUILD_POSSIBLE + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_NOT),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testRhel7WorkstationrRpm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset19.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM_INSTALL),
                Analysis.INFO_RH_BUILD_RPM_INSTALL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRhelJdkRpmMismatchJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux release 8.5 (Ootpa)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String library = "7ff001124000-7ff001ecf000 r-xp 00000000 fd:00 17385                      "
                + "/usr/lib/jvm/java-11-openjdk-11.0.13.0.8-1.el8_4.x86_64/lib/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("8.5", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8.4", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRhelJdkRpmMismatchJdk17() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux release 8.5 (Ootpa)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String library = "7f7b5e35f000-7f7b5f5d6000 r-xp 00000000 fd:01 67638415                   "
                + "/usr/lib/jvm/java-17-openjdk-17.0.4.0.8-2.el8_6.x86_64/lib/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built on "
                + "Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("8.5", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8.6", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRhelJdkRpmMismatchJdk8() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset69.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals("java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.ppc64le", fel.getRpmDirectory(),
                "Rpm directory not correct.");
        assertEquals("8.4", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8.5", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LATEST),
                Analysis.WARN_JDK_NOT_LATEST + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testRhelJdkRpmMismatchRhel7Power9() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.6 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String library = "3fff7c2d0000-3fff7cf40000 r-xp 00000000 fd:08 138908                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-ppc64le JRE (1.8.0_275-b01), built "
                + "on Nov  6 2020 06:43:55 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("7.6", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("7.9", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_RHEL7_POWER9),
                Analysis.WARN_RHEL7_POWER9 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRpmPpc64le() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset15.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM_INSTALL),
                Analysis.INFO_RH_BUILD_RPM_INSTALL + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LATEST),
                Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9),
                Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSafepointLogging() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockDiagnosticVMOptions -XX:+PrintSafepointStatistics "
                + "-XX:PrintSafepointStatisticsCount=1 -XX:+LogVMOutput -XX:LogFile=/path/to/vm.log -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED),
                Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_DIAGNOSTIC_PRINT_SAFEPOINT_STATISTICS),
                Analysis.WARN_OPT_DIAGNOSTIC_PRINT_SAFEPOINT_STATISTICS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testServerFlag() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -server -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        // 64-bit is assumed
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_SERVER_REDUNDANT),
                Analysis.INFO_OPT_SERVER_REDUNDANT + " analysis not identified.");
        // Specify 32-bit
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_SERVER_REDUNDANT),
                Analysis.INFO_OPT_SERVER_REDUNDANT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testShenandoahCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset31.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH),
                GarbageCollector.SHENANDOAH + " collector not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_METASPACE),
                Analysis.INFO_OPT_METASPACE + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_METASPACE_LT_COMP_CLASS),
                Analysis.WARN_OPT_METASPACE_LT_COMP_CLASS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testShenandoahMarkLoopWork() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset44.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK),
                Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSignalHandlingDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -Xrs";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_RS),
                Analysis.WARN_OPT_RS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSiKernel() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset39.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SIGCODE_SI_KERNEL),
                Analysis.INFO_SIGCODE_SI_KERNEL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSslDecode() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "***REMOVED*** There is insufficient memory for the Java Runtime Environment to continue.";
        HeaderEvent headerEvent1 = new HeaderEvent(header1);
        fel.getHeaderEvents().add(headerEvent1);
        String header2 = "***REMOVED*** Native memory allocation (malloc) failed to allocate 4294967312 bytes for Chunk::new";
        HeaderEvent headerEvent2 = new HeaderEvent(header2);
        fel.getHeaderEvents().add(headerEvent2);
        String header3 = "***REMOVED***  Out of Memory Error (arena.cpp:197), pid=2907, tid=2927";
        HeaderEvent headerEvent3 = new HeaderEvent(header3);
        fel.getHeaderEvents().add(headerEvent3);
        String currentThread = "Current thread (0x00007f5134b1b000):  JavaThread \"C2 CompilerThread0\" daemon "
                + "[_thread_in_native, id=2927, stack(0x00007f5138229000,0x00007f513832a000)]";
        CurrentThreadEvent currentThreadEvent = new CurrentThreadEvent(currentThread);
        fel.setCurrentThreadEvent(currentThreadEvent);
        String currentCompileTask = "C2:299829840 17165   !   4       "
                + "sun.security.ssl.SSLEngineInputRecord::decodeInputRecord (812 bytes)";
        CurrentCompileTaskEvent currentCompileTaskEvent = new CurrentCompileTaskEvent(currentCompileTask);
        fel.getCurrentCompileTaskEvents().add(currentCompileTaskEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.16+8-LTS) for linux-amd64 JRE (11.0.16+8-LTS), "
                + "built on Jul 18 2022 19:50:20 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String memory = "Memory: 4k page, physical 65686152k(438884k free), swap 2097148k(0k free)";
        MemoryEvent memoryEvent = new MemoryEvent(memory);
        fel.getMemoryEvents().add(memoryEvent);
        String jvmArgs = "-Xms3g -Xmx3g -XX:ThreadStackSize=640";
        VmArgumentsEvent vmArgumentsEvent = new VmArgumentsEvent(jvmArgs);
        fel.getVmArgumentsEvents().add(vmArgumentsEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE),
                Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testStackFreeSpaceGtStackSize() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset37.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE),
                Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testStackOverflowError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_STACKOVERFLOW),
                Analysis.ERROR_STACKOVERFLOW + " analysis not identified.");
    ***REMOVED***

    @Test
    void testStubroutines() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset17.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_STUBROUTINES),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");
        testFile = new File(Constants.TEST_DATA_DIR + "dataset43.txt");
        fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_STUBROUTINES),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");

    ***REMOVED***

    @Test
    void testSurvivorRatio() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:SurvivorRatio=6 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_SURVIVOR_RATIO),
                Analysis.INFO_OPT_SURVIVOR_RATIO + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSwapDisabled() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset28.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.isRhBuildOpenJdk(), "RH build of OpenJDK incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SWAP_DISABLED),
                Analysis.INFO_SWAP_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSwappingInfo() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset11.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_SWAPPING),
                Analysis.INFO_SWAPPING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSwappingWarn() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset12.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_SWAPPING),
                Analysis.WARN_SWAPPING + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_JVM_DLL),
                Analysis.ERROR_JVM_DLL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTargetSurvivorRatio() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:TargetSurvivorRatio=90 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_SURVIVOR_RATIO_TARGET),
                Analysis.INFO_OPT_SURVIVOR_RATIO_TARGET + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTenuringDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:MaxTenuringThreshold=0 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_TENURING_DISABLED),
                Analysis.WARN_OPT_TENURING_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTenuringMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        fel.getVmArgumentsEvents().clear();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE),
                Analysis.INFO_OPT_MAX_TENURING_OVERRIDE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testTenuringNotDefault() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:MaxTenuringThreshold=8 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE),
                Analysis.INFO_OPT_MAX_TENURING_OVERRIDE + " analysis not identified.");
        fel.getVmArgumentsEvents().clear();
    ***REMOVED***

    @Test
    void testThreadStackSizeTiny() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -XX:TargetSurvivorRatio=90 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_THREAD_STACK_SIZE_TINY),
                Analysis.WARN_THREAD_STACK_SIZE_TINY + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTieredCompilation() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+TieredCompilation -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_TIERED_COMPILATION_ENABLED),
                Analysis.INFO_OPT_TIERED_COMPILATION_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTraceClassLoading() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+TraceClassLoading -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_TRACE_CLASS_LOADING),
                Analysis.INFO_OPT_TRACE_CLASS_LOADING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTraceClassUnloading() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+TraceClassUnloading -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_TRACE_CLASS_UNLOADING),
                Analysis.INFO_OPT_TRACE_CLASS_UNLOADING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTruncatedLog() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset48.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.getAnalysis().contains(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_NOT),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_TRUNCATED),
                Analysis.INFO_TRUNCATED + " analysis not identified.");
        assertTrue(fel.getAnalysis().contains(Analysis.ERROR_OOME_OOPS),
                Analysis.ERROR_OOME_OOPS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUnknownStorageFalseReporting() {
        FatalErrorLog fel = new FatalErrorLog();
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_STORAGE_UNKNOWN),
                Analysis.INFO_STORAGE_UNKNOWN + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testUnlockDiagnosticVMOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockDiagnosticVMOptions -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED),
                Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUnlockExperimentalVMOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockExperimentalVMOptions -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED),
                Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUseAdaptiveSizePolicyDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:-UseAdaptiveSizePolicy";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED),
                Analysis.WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUseFastUnorderedTimeStamps() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+UnlockExperimentalVMOptions -XX:+UseFastUnorderedTimeStamps";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_FAST_UNORDERED_TIMESTAMPS),
                Analysis.WARN_OPT_FAST_UNORDERED_TIMESTAMPS + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED),
                Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testUseMembar() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xmx4096M -XX:+UseMembar";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_USE_MEMBAR),
                Analysis.WARN_OPT_USE_MEMBAR + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUseParallelOldGcDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParallelGC -XX:-UseParallelOldGC ";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED),
                Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUseParallelOldGcRedundant() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParallelGC -XX:+UseParallelOldGC ";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT),
                Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT + " analysis not identified.");
    ***REMOVED***

    /**
     * Test if PAR_NEW collector disabled with -XX:-UseParNewGC with the CMS collector.
     */
    @Test
    void testUseParNewGcDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:-UseParNewGC "
                + "-XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testUseParNewGcRedundant() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:+UseParNewGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_REDUNDANT),
                Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_REDUNDANT + " analysis not identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_DISABLED),
                Analysis.INFO_OPT_CMS_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED),
                Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testUseVmInterruptibleIo() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -XX:-UseVMInterruptibleIO";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.WARN_OPT_JDK8_USE_VM_INTERRUPTIBLE_IO),
                Analysis.WARN_OPT_JDK8_USE_VM_INTERRUPTIBLE_IO + " analysis not identified.");
    ***REMOVED***

    @Test
    void testVerboseClass() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -verbose:class -Xmx2G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_OPT_VERBOSE_CLASS),
                Analysis.INFO_OPT_VERBOSE_CLASS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testVersionEol() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 6.10 (Santiago)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String library = "7ff001124000-7ff001ecf000 r-xp 00000000 fd:00 17385                      "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.275.b01-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_JDK_ANCIENT),
                Analysis.INFO_JDK_ANCIENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testVmOperationConcurrentGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmOperation = "VM_Operation (0x0000008e276ff410): CGC_Operation, mode: safepoint, requested by thread "
                + "0x000001d9d3e12800";
        VmOperationEvent event = new VmOperationEvent(vmOperation);
        fel.setVmOperationEvent(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_VM_OPERATION_CONCURRENT_GC),
                Analysis.INFO_VM_OPERATION_CONCURRENT_GC + " analysis not identified.");
    ***REMOVED***

    @Test
    void testVmOperationHeapDump() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmOperation = "VM_Operation (0x0000000054ede490): HeapDumper, mode: safepoint, requested by thread "
                + "0x000000004d180000";
        VmOperationEvent event = new VmOperationEvent(vmOperation);
        fel.setVmOperationEvent(event);
        fel.doAnalysis();
        assertTrue(fel.getAnalysis().contains(Analysis.INFO_VM_OPERATION_HEAP_DUMP),
                Analysis.INFO_VM_OPERATION_HEAP_DUMP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testWarnNotLatestJdkValue() {
        assertEquals("JDK is not the latest version. Latest version is ", Analysis.WARN_JDK_NOT_LATEST.getValue(),
                Analysis.WARN_JDK_NOT_LATEST + "value not correct.");
    ***REMOVED***

    @Test
    void testZGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String zHeap = " ZHeap           used 4M, capacity 500M, max capacity 7978M";
        HeapEvent heapEvent = new HeapEvent(zHeap);
        fel.getHeapEvents().add(heapEvent);
        assertTrue(fel.getGarbageCollectors().contains(JdkUtil.GarbageCollector.ZGC),
                JdkUtil.GarbageCollector.ZGC + " collector not identified.");
        fel.getHeapEvents().clear();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseZGC -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getGarbageCollectors().contains(JdkUtil.GarbageCollector.ZGC),
                JdkUtil.GarbageCollector.ZGC + " collector not identified.");
    ***REMOVED***
***REMOVED***

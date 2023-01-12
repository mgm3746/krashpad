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

import org.github.joa.domain.GarbageCollector;
import org.github.krashpad.domain.jdk.CompilationEvent;
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
import org.github.krashpad.domain.jdk.LdPreloadFileEvent;
import org.github.krashpad.domain.jdk.MeminfoEvent;
import org.github.krashpad.domain.jdk.MemoryEvent;
import org.github.krashpad.domain.jdk.OsEvent;
import org.github.krashpad.domain.jdk.SigInfoEvent;
import org.github.krashpad.domain.jdk.StackEvent;
import org.github.krashpad.domain.jdk.StackSlotToMemoryMappingEvent;
import org.github.krashpad.domain.jdk.ThreadEvent;
import org.github.krashpad.domain.jdk.TimeElapsedTimeEvent;
import org.github.krashpad.domain.jdk.TimeEvent;
import org.github.krashpad.domain.jdk.VmArgumentsEvent;
import org.github.krashpad.domain.jdk.VmInfoEvent;
import org.github.krashpad.domain.jdk.VmOperationEvent;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.JdkUtil.Application;
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
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_LINUX_ZIP),
                Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_ADOPTOPENJDK_POSSIBLE),
                Analysis.INFO_ADOPTOPENJDK_POSSIBLE + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX),
                Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX + " analysis not identified.");
    ***REMOVED***

    @Test
    void testAppDynamicsDetectedCompilationEvent() {
        FatalErrorLog fel = new FatalErrorLog();
        String compilation = "Event: 1234.567 Thread 0x000002a0fb87b000 124536   !   4       "
                + "com.singularity.ee.agent.util.reflect.ReflectionUtility::getDeclaredField (79 bytes)";
        CompilationEvent event = new CompilationEvent(compilation);
        fel.getCompilationEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_DETECTED),
                Analysis.INFO_APP_DYNAMICS_DETECTED + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_POSSIBLE),
                Analysis.INFO_APP_DYNAMICS_POSSIBLE + " analysis incorreclty identified.");
    ***REMOVED***

    @Test
    void testAppDynamicsDetectedJavaAgent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -javaagent:C:\\appdynamics\\appagent\\javaagent\\javaagent.jar -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_DETECTED),
                Analysis.INFO_APP_DYNAMICS_DETECTED + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_POSSIBLE),
                Analysis.INFO_APP_DYNAMICS_POSSIBLE + " analysis incorreclty identified.");
    ***REMOVED***

    @Test
    void testAppDynamicsPossible() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -javaagent:C:\\path\\to\\javaagent.jar -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_POSSIBLE),
                Analysis.INFO_APP_DYNAMICS_POSSIBLE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_DETECTED),
                Analysis.INFO_APP_DYNAMICS_DETECTED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testAvx2StringCompareInHeader() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "***REMOVED*** Problematic frame:";
        HeaderEvent headerEvent1 = new HeaderEvent(header1);
        fel.getHeaderEvents().add(headerEvent1);
        String header2 = "***REMOVED*** J 3917 C2 java.lang.String.compareTo(Ljava/lang/Object;)I (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        HeaderEvent headerEvent2 = new HeaderEvent(header2);
        fel.getHeaderEvents().add(headerEvent2);
        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpuInfo);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testAvx2StringCompareToAvxDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "J 3917 C2 com.example.Object.someMethod() (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        StackEvent stackEvent = new StackEvent(stack);
        fel.getStackEvents().add(stackEvent);
        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpuInfo);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        String jvm_args = "jvm_args: -Xms256m -XX:UseAVX=0 -Xmx2048m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testAvx2StringCompareToInStackJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "J 3917 C2 java.lang.String.compareTo(Ljava/lang/Object;)I (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        StackEvent stackEvent = new StackEvent(stack);
        fel.getStackEvents().add(stackEvent);
        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpuInfo);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        String jvm_args = "jvm_args: -Xms256m -Xmx2048m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testAvx2StringCompareToInStackNoJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "J 3917 C2 java.lang.String.compareTo(Ljava/lang/Object;)I (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        StackEvent stackEvent = new StackEvent(stack);
        fel.getStackEvents().add(stackEvent);

        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpuInfo);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testAws() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset34.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_STORAGE_AWS), Analysis.INFO_STORAGE_AWS + " analysis not identified.");
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
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_POSSIBLE),
                Analysis.INFO_RH_BUILD_POSSIBLE + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT),
                Analysis.INFO_RH_BUILD_NOT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCannotGetLibraryInformation() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "Can not get library information for pid = 123456";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION),
                Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testClientFlag32Bit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -client -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_64_CLIENT),
                org.github.joa.util.Analysis.INFO_64_CLIENT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCloudPerfDataDisk() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        ContainerInfoEvent containerInfoEvent = new ContainerInfoEvent("TEST");
        fel.getContainerInfoEvents().add(containerInfoEvent);
        MeminfoEvent meminfoEvent = new MeminfoEvent("SwapTotal:       0 kB");
        fel.getMeminfoEvents().add(meminfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_CONTAINER_PERF_DATA_DISK),
                org.github.joa.util.Analysis.WARN_CONTAINER_PERF_DATA_DISK + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_PERF_DATA_DISABLED),
                org.github.joa.util.Analysis.INFO_PERF_DATA_DISABLED + " analysis incorrectly identified.");
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
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_CMS_PAR_NEW_DISABLED),
                org.github.joa.util.Analysis.WARN_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT),
                org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_CMS_DISABLED),
                org.github.joa.util.Analysis.INFO_CMS_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCmsIncrementalModeCollectorCms() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode ";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String cpu = "CPU:total 8 (2 cores per cpu, 1 threads per core)";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpu);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_CMS_INCREMENTAL_MODE),
                Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCmsIncrementalModeCollectorCmsDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:+CMSIncrementalMode ";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String cpu = "CPU:total 8 (2 cores per cpu, 1 threads per core)";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpu);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_CMS_INCREMENTAL_MODE),
                Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis incorrectly identified.");
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
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_CMS_PAR_NEW_DISABLED),
                org.github.joa.util.Analysis.WARN_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_CMS_DISABLED),
                org.github.joa.util.Analysis.INFO_CMS_DISABLED + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT),
                org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompilerThreadC2BeautifyLoops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset72.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS),
                Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCompilerThreadC2MininodeIdeal() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset76.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL),
                Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testContainer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset47.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_CGROUP), Analysis.INFO_CGROUP + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_MEMORY_JVM_NE_SYSTEM),
                Analysis.INFO_MEMORY_JVM_NE_SYSTEM + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_CGROUP_MEMORY_LIMIT),
                Analysis.INFO_CGROUP_MEMORY_LIMIT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCrash3rdPartyLibrary() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  [my-library_123.so+0x22c30d]";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String dynamicLibrary = "7f4d6fd25000-7f4d70359000 r-xp 00000000 fd:04 402192                     "
                + "/path/to/my-library_123.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN),
                Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN + " analysis not identified.");
    ***REMOVED***

    @Test
    void testCrashOnOomeHeap() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+CrashOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError "
                + "-XX:HeapDumpPath=/path/to/mydir";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String header = "***REMOVED***  fatal error: OutOfMemory encountered: Java heap space";
        HeaderEvent headerEvent = new HeaderEvent(header);
        fel.getHeaderEvents().add(headerEvent);
        String stack = "V  [libjvm.so+0xb2caf6]  TypeArrayKlass::allocate_common(int, bool, Thread*)+0x796";
        StackEvent stackEvent = new StackEvent(stack);
        fel.getStackEvents().add(stackEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CRASH_ON_OOME_HEAP),
                Analysis.ERROR_CRASH_ON_OOME_HEAP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testCrashStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset33.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_JVM_STARTUP_FAILS),
                Analysis.INFO_JVM_STARTUP_FAILS + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_SIGSEGV),
                Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testD64Flag32Bit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -d64 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_64_D64_REDUNDANT),
                org.github.joa.util.Analysis.INFO_64_D64_REDUNDANT + " analysis incorrectly identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.INFO_DBCP2), Analysis.INFO_DBCP2 + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_POSTGRESQL_CONNECTION),
                Analysis.INFO_POSTGRESQL_CONNECTION + " analysis not identified.");
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

    @Test
    void testDeprecatedLoggingOptionsJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintGC -XX:+PrintGCDetails -Xloggc:gc.log -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built "
                + "on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_OVERWRITE),
                org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_OVERWRITE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_OVERWRITE),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_OVERWRITE + " analysis incorrectly identified.");
        assertFalse(
                fel.getJvmOptions()
                        .hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED),
                org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED
                        + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_LOGGC),
                org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_LOGGC + " analysis not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC),
                org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC + " analysis not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC_DETAILS),
                org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC_DETAILS + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION),
                Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7),
                Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7 + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_EXPLICIT_GC_DISABLED),
                org.github.joa.util.Analysis.WARN_EXPLICIT_GC_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testDynatraceCrash() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "C  [liboneagentproc.so+0x17993]";
        StackEvent stackEvent = new StackEvent(stack);
        fel.getStackEvents().add(stackEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_DYNATRACE), Analysis.ERROR_DYNATRACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testDynatraceDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7effff525000-7effff526000 rw-p 000ce000 fd:02 4238968 "
                + "/usr/lib64/liboneagentproc.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_DYNATRACE), Analysis.INFO_DYNATRACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testDynatraceInStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset55.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.WARN_DYNATRACE), Analysis.WARN_DYNATRACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testErrorJdk8LibcCfree() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset78.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_LIBC_CFREE),
                Analysis.ERROR_JDK8_LIBC_CFREE + " analysis not identified.");

    ***REMOVED***

    @Test
    void testErrorStubroutinesHeaderOnly() {
        FatalErrorLog fel = new FatalErrorLog();
        String logLine = "***REMOVED*** v  ~StubRoutines::jbyte_disjoint_arraycopy";
        HeaderEvent headerEvent = new HeaderEvent(logLine);
        fel.getHeaderEvents().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STUBROUTINES),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_EXPLICIT_GC_NOT_CONCURRENT),
                org.github.joa.util.Analysis.WARN_EXPLICIT_GC_NOT_CONCURRENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFailedToMapBytes() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset58.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT_OOPS),
                Analysis.ERROR_OOME_LIMIT_OOPS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFailedToMapBytesNoSwap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset75.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_JVM), Analysis.ERROR_OOME_JVM + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_UNIDENTIFIED_LOG_LINE),
                Analysis.WARN_UNIDENTIFIED_LOG_LINE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testFatalErrorLogAncient() {
        FatalErrorLog fel = new FatalErrorLog();
        String time = "time: Tue Aug 18 14:10:59 2020";
        TimeEvent timeEven = new TimeEvent(time);
        fel.setTimeEvent(timeEven);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_FATAL_ERROR_LOG_ANCIENT),
                Analysis.WARN_FATAL_ERROR_LOG_ANCIENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFpe() {
        FatalErrorLog fel = new FatalErrorLog();
        String siginfo = "siginfo: si_signo: 8 (SIGFPE), si_code: 1 (FPE_INTDIV), si_addr: 0x00007fdfe95e789f";
        SigInfoEvent event = new SigInfoEvent(siginfo);
        fel.setSigInfoEvent(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_SIGFPE),
                Analysis.INFO_SIGNO_SIGFPE + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_FPE_INTDIV),
                Analysis.INFO_SIGCODE_FPE_INTDIV + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_FPE), Analysis.ERROR_FPE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testFreetypeFontScalerGetGlyphImageNative() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset54.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE),
                Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE),
                Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        // Test header only
        fel.getAnalysis().clear();
        fel.getStackEvents().clear();
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE),
                Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_G1_SUMMARIZE_RSET_STATS_OUTPUT),
                org.github.joa.util.Analysis.INFO_G1_SUMMARIZE_RSET_STATS_OUTPUT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testGarbageCollectorsJvmOptionsMismatch() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseG1GC";
        VmArgumentsEvent vmArgumentEvent = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(vmArgumentEvent);
        // Test a combination not yet seen
        HeapEvent heapEvent1 = new HeapEvent("Shenandoah Heap");
        fel.getHeapEvents().add(heapEvent1);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_GC_IGNORED),
                org.github.joa.util.Analysis.ERROR_GC_IGNORED + " analysis not identified.");
        // Test real world
        fel.getAnalysis().clear();
        fel.getHeapEvents().clear();
        HeapEvent heapEvent2 = new HeapEvent(
                " PSYoungGen      total 611840K, used 524800K [0x00000000d5580000, 0x0000000100000000, "
                        + "0x0000000100000000)");
        fel.getHeapEvents().add(heapEvent2);
        HeapEvent heapEvent3 = new HeapEvent(
                " ParOldGen       total 1398272K, used 16K [0x0000000080000000, 0x00000000d5580000, "
                        + "0x00000000d5580000)");
        fel.getHeapEvents().add(heapEvent3);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_G1_IGNORED_PARALLEL),
                org.github.joa.util.Analysis.ERROR_G1_IGNORED_PARALLEL + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_HASHMAP), Analysis.ERROR_HASHMAP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testHeapMaxMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset56.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_HEAP_MAX_MISSING),
                org.github.joa.util.Analysis.INFO_HEAP_MAX_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testIbmToolkit() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff46c40000-7fff46c80000 r--s 00520000 fd:0a 67109322                   "
                + "/path/to/jt400.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_IBM_TOOLKIT), Analysis.INFO_IBM_TOOLKIT + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testItextDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff467a0000-7fff467c0000 r--s 00220000 fd:0a 67109364                   "
                + "/path/to/itextpdf-5.5.13.1.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_ITEXT), Analysis.INFO_ITEXT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testItextInStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff467a0000-7fff467c0000 r--s 00220000 fd:0a 67109364                   "
                + "/path/to/itextpdf-5.5.13.1.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String stack1 = "v  ~BufferBlob::StubRoutines (2)";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 42480 C2 com.itextpdf.text.pdf.RandomAccessFileOrArray.readFully([B)V (9 bytes) @ "
                + "0x00007f8d2057c449 [0x00007f8d2057c2c0+0x189]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        fel.getAnalysis().clear();
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_ITEXT), Analysis.WARN_ITEXT + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_ITEXT), Analysis.INFO_ITEXT + " analysis incorrectly identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_0),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_0 + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_ROTATION_DISABLED),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_ROTATION_DISABLED + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_SMALL),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_SMALL + " analysis not identified.");
    ***REMOVED***

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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING),
                org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk11PrintGCDetailsNotMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xlog:gc*:file=/path/to/gc.log:time,uptimemillis:filecount=5,"
                + "filesize=3M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("-Xlog:gc*:file=/path/to/gc.log:time,uptimemillis:filecount=5,filesize=3M",
                fel.getJvmOptions().getLog().get(0), "-Xlog not correct.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING),
                org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testJdk7() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (24.51-b03) for linux-amd64 JRE (1.7.0_55-b13), "
                + "built on Apr  9 2014 12:07:12 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-4)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK_VERSION_UNSUPPORTED),
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_DEFLATER_CONTENTION),
                Analysis.ERROR_JDK8_DEFLATER_CONTENTION + " analysis not identified.");
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
        assertTrue(
                fel.getJvmOptions()
                        .hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED),
                org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8PrintGCDetailsMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xloggc:gc.log  -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_PRINT_GC_DETAILS_MISSING),
                org.github.joa.util.Analysis.INFO_JDK8_PRINT_GC_DETAILS_MISSING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8PrintGCDetailsMissingGcLogging() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_PRINT_GC_DETAILS_MISSING),
                org.github.joa.util.Analysis.INFO_JDK8_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testJdk8PrintGCDetailsMissingNoGcLogging() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_PRINT_GC_DETAILS_MISSING),
                org.github.joa.util.Analysis.INFO_JDK8_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testJdk8ZipFileContentionGetEntry() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset23.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        String stackFrameTopCompiledJavaCode = "J 302  java.util.zip.ZipFile.getEntry(J[BZ)J (0 bytes) @ "
                + "0x00007fa287303dce [0x00007fa287303d00+0xce]";
        assertEquals(stackFrameTopCompiledJavaCode, fel.getStackFrameTopCompiledJavaCode(),
                "Top compiled Java code (J) stack frame not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8ZipFileContentionReadCen() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.342-b07) for linux-amd64 JRE (1.8.0_342-b07), built on "
                + "Jul 18 2022 23:53:30 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        String stack = "C  [libzip.so+0x4c59]  readCEN+0x8e9";
        StackEvent stackEvent = new StackEvent(stack);
        fel.getStackEvents().add(stackEvent);
        fel.doAnalysis();
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdk8ZipFileContentionReadCenHeaderOnly() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "***REMOVED*** JRE version: OpenJDK Runtime Environment (8.0_342-b07) (build 1.8.0_342-b07)";
        HeaderEvent headerEvent1 = new HeaderEvent(header1);
        fel.getHeaderEvents().add(headerEvent1);
        String header2 = "***REMOVED*** C  [libzip.so+0x4c59]  readCEN+0x8e9";
        HeaderEvent headerEvent2 = new HeaderEvent(header2);
        fel.getHeaderEvents().add(headerEvent2);
        fel.doAnalysis();
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdkDebugSymbolsMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset18.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_LINUX_ZIP),
                Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_DEBUG_SYMBOLS),
                Analysis.WARN_DEBUG_SYMBOLS + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_JDK_ANCIENT), Analysis.INFO_JDK_ANCIENT + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJdkUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: test";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK_VERSION_UNKNOWN),
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
        assertTrue(fel.hasAnalysis(Analysis.INFO_JFFI), Analysis.INFO_JFFI + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJfr() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset52.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JFR),
                org.github.joa.util.Analysis.INFO_JFR + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED),
                Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJfrPdGetTopFrame() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset52.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JFR_PD_GET_TOP_FRAME),
                Analysis.ERROR_JFR_PD_GET_TOP_FRAME + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJliLaunchStackSize() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss256k -XX:TargetSurvivorRatio=90 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String stack1 = "Stack: [0x00007fff14ecc000,0x00007fff156ca000],  sp=0x00007fff156c50c0,  free space=8164k";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "C  [libjli.so+0x877f]  JLI_Launch+0x15bf";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        String currentThread = "Current thread (0x000055c487db2800):  JavaThread \"Unknown thread\" [_thread_in_vm, "
                + "id=11, stack(0x00007fff14ecc000,0x00007fff156ca000)]";
        CurrentThreadEvent currentThreadEvent = new CurrentThreadEvent(currentThread);
        fel.setCurrentThreadEvent(currentThreadEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE),
                Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testJna() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "7f99774f8000-7f99775f7000 ---p 00017000 00:27 165351280                  "
                + "/tmp/jna-100343/jna17878442429968131541.tmp (deleted)";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logline);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JNA), Analysis.INFO_JNA + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJnaFfiPrepClosureLoc() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset84.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(Application.CASSANDRA, fel.getApplication(), "Application not correct.");
        assertTrue(fel.isJnaCrash(), "JNA crash not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JNA_FFI_PREP_CLOSURE_LOC),
                Analysis.ERROR_JNA_FFI_PREP_CLOSURE_LOC + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JNA_RH), Analysis.ERROR_JNA_RH + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JNA_RH), Analysis.ERROR_JNA_RH + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJssCrash() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  [libc.so.6+0x36e5b]  __memcpy_sse2_unaligned_erms+0x1b";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 13417  org.mozilla.jss.nss.PR.Shutdown(Lorg/mozilla/jss/nss/PRFDProxy;I)I (0 bytes) "
                + "@ 0x00007f47ea93da92 [0x00007f47ea93da40+0x52]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JSS), Analysis.ERROR_JSS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJssDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f47d6b82000-7f47d6bc6000 r-xp 00000000 fd:00 201485134                  "
                + "/usr/lib64/jss/libjss4.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JSS), Analysis.INFO_JSS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testJssInStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "J  12345 com.example.MyClass";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 13417  org.mozilla.jss.nss.PR.Shutdown(Lorg/mozilla/jss/nss/PRFDProxy;I)I (0 bytes) "
                + "@ 0x00007f47ea93da92 [0x00007f47ea93da40+0x52]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_JSS), Analysis.WARN_JSS + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.INFO_JVM_USER_NE_USERNAME),
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGE_SIZE_IN_BYTES_LINUX),
                org.github.joa.util.Analysis.INFO_LARGE_PAGE_SIZE_IN_BYTES_LINUX + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS),
                org.github.joa.util.Analysis.INFO_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLatestRelease() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset14.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LATEST),
                Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.");
        assertEquals(825, ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)),
                "Release days diff not correct.");
        assertEquals(11, JdkUtil.getLatestJdkReleaseNumber(fel) - JdkUtil.getJdkReleaseNumber(fel),
                "Release ***REMOVED*** diff not correct.");
    ***REMOVED***

    @Test
    void testLdPreloadFile() {
        String logLine = "/etc/ld.so.preload:";
        LdPreloadFileEvent event = new LdPreloadFileEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getLdPreloadFileEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_LD_SO_PRELOAD),
                Analysis.INFO_LD_SO_PRELOAD + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLibaioContextDone() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset46.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LIBAIO_CONTEXT_DONE),
                Analysis.ERROR_LIBAIO_CONTEXT_DONE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLinkageError() {
        String logLine = "LinkageErrors=5276";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LINKAGE), Analysis.ERROR_LINKAGE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testLuceneInStackSlotToMemoryMapping() {
        FatalErrorLog fel = new FatalErrorLog();
        String stackSlotToMemoryMapping = "stack at sp + 2 slots: 0x00000007abcf3808 is an oop: org.apache.lucene."
                + "store.BufferedChecksumIndexInput";
        StackSlotToMemoryMappingEvent stackSlotToMemoryMappingEvent = new StackSlotToMemoryMappingEvent(
                stackSlotToMemoryMapping);
        fel.getStackSlotToMemoryMappingEvents().add(stackSlotToMemoryMappingEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_LUCENE), Analysis.WARN_LUCENE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMetadataOnStackMark() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset73.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK),
                Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMicrosoftSqlServerNativeDriverDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "0x00007fff77490000 - 0x00007fff774de000         "
                + "C:\\Windows\\System32\\mssql-jdbc_auth-8.2.2.x64.dll";
        DynamicLibraryEvent event = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_MICROSOFT_SQL_SERVER_NATIVE),
                Analysis.INFO_MICROSOFT_SQL_SERVER_NATIVE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testMmapDeleted() {
        FatalErrorLog fel = new FatalErrorLog();
        String library = "7ca8cf3d6000-7ca8cfdd6000 rw-s 00000000 fd:00 1074566196                 "
                + "/var/lib/kafka/data/kafka-log0/something/00000000000002627674.index.deleted (deleted)";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_MMAP_DELETED),
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO), Analysis.ERROR_LIBJVM_SO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_JVM_DLL), Analysis.ERROR_JVM_DLL + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_JVM_DLL),
                Analysis.ERROR_JVM_DLL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testModuleEntryPurgeReadsPossible() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset82.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS_POSSIBLE),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS_POSSIBLE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_JVM_DLL),
                Analysis.ERROR_JVM_DLL + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_POINTER_INVALID),
                Analysis.ERROR_POINTER_INVALID + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.INFO_STORAGE_NFS), Analysis.INFO_STORAGE_NFS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testNoMemoryEvent() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.INFO_SWAP_DISABLED),
                Analysis.INFO_SWAP_DISABLED + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testNoStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset49.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.INFO_STACK_NO_VM_CODE),
                Analysis.INFO_STACK_NO_VM_CODE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testNotLts() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset16.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaSpecification.JDK12, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LTS), Analysis.WARN_JDK_NOT_LTS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testNullPointer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset51.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_POINTER_NULL),
                Analysis.ERROR_POINTER_NULL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOnOutOfMemoryErrorKillJdk8u101() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:OnOutOfMemoryError=\"kill -9 %p\" -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String header = "***REMOVED*** JRE version: Java(TM) SE Runtime Environment (8.0_101-b13) (build 1.8.0_101-b13)";
        HeaderEvent headerEvent = new HeaderEvent(header);
        fel.getHeaderEvents().add(headerEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME),
                org.github.joa.util.Analysis.INFO_ON_OOME + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME_KILL),
                org.github.joa.util.Analysis.INFO_ON_OOME_KILL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOnOutOfMemoryErrorKillJdk8u91() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:OnOutOfMemoryError=\"kill -9 %p\" -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String header = "***REMOVED*** JRE version: Java(TM) SE Runtime Environment (8.0_91-b14) (build 1.8.0_91-b14)";
        HeaderEvent headerEvent = new HeaderEvent(header);
        fel.getHeaderEvents().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME),
                org.github.joa.util.Analysis.INFO_ON_OOME + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME_KILL),
                org.github.joa.util.Analysis.INFO_ON_OOME_KILL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOomAmqCli() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset67.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_AMQ_CLI),
                Analysis.ERROR_OOME_AMQ_CLI + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomCompilerThreadC2SslDecode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset79.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE),
                Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOomCompilerThreadWindows() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset80.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_EXTERNAL),
                Analysis.ERROR_OOME_EXTERNAL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomeCompressedOops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset42.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT_OOPS),
                Analysis.ERROR_OOME_LIMIT_OOPS + " analysis not identified.");
    ***REMOVED***

    void testOomeJavaHeap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset36.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_JAVA_HEAP),
                Analysis.ERROR_OOME_THROWN_JAVA_HEAP + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_SIGSEGV),
                Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_SEGV_MAPERR),
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOomeThreadLeakEapExecutorPool() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset83.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(Application.WILDFLY, fel.getApplication(), "Application not correct.");
        assertEquals(8304, fel.getJavaThreadCount(JdkRegEx.WILDFLY_EXECUTOR_POOL_THREAD),
                "JBoss EAP executor pool thread count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native libraries unknown count not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL),
                Analysis.ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_THREAD_LEAK),
                Analysis.ERROR_OOME_THREAD_LEAK + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP + " analysis incorrectly identified.");

    ***REMOVED***

    @Test
    void testOomJBossVersion() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset71.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(JavaVendor.AZUL, fel.getJavaVendor(), "Java vendor not correct.");
        assertEquals(Application.JBOSS_VERSION, fel.getApplication(), "Application not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_JBOSS_VERSION),
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
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP),
                Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomLimit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset57.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT), Analysis.ERROR_OOME_LIMIT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset55.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomStartupExternal() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset56.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.INFO_OPTS_NONE),
                Analysis.INFO_OPTS_NONE + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_EXTERNAL_STARTUP),
                Analysis.ERROR_OOME_EXTERNAL_STARTUP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomStartupOvercommit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset66.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP),
                Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOomStartupOvercommitLimit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset81.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP),
                Analysis.ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX),
                Analysis.INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomSwapDisabledG1() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset62.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.WARN_OOM_G1), Analysis.WARN_OOM_G1 + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_SWAP_DISABLED_G1),
                Analysis.WARN_SWAP_DISABLED_G1 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomTomcatShutdown() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset68.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN),
                Analysis.ERROR_OOME_TOMCAT_SHUTDOWN + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOomTomcatShutdownStopStop() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset63.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN),
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
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOracleJdbcJdkIncompatible() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built "
                + "on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        String dynamicLibrary = "7fd01e1b3000-7fd01e1d3000 r-xp 00000000 fd:03 2100954                    "
                + "/ora01/app/oracle/product/11.2.0/client_1/lib/libocijdbc11.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_ORACLE_JDBC_OCI),
                Analysis.INFO_ORACLE_JDBC_OCI + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_JDK_INCOMPATIBLE),
                Analysis.ERROR_ORACLE_JDBC_JDK_INCOMPATIBLE + " analysis not identified.");
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
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING),
                Analysis.ERROR_ORACLE_JDBC_OCI_LOADING + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis incorrectly identified.");
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
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING),
                Analysis.ERROR_ORACLE_JDBC_OCI_LOADING + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOracleJdbcOciNot() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built "
                + "on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        String dynamicLibrary = "7fd01e1b3000-7fd01e1d3000 r-xp 00000000 fd:03 2100954 /path/to/my.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.INFO_ORACLE_JDBC_OCI),
                Analysis.INFO_ORACLE_JDBC_OCI + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryErrorThrownCompressedClassSpace() {
        String logLine = "OutOfMemoryError class_metaspace_errors=7";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE),
                Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryErrorThrownJavaHeap() {
        String logLine = "OutOfMemoryError java_heap_errors=13";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_JAVA_HEAP),
                Analysis.ERROR_OOME_THROWN_JAVA_HEAP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testOutOfMemoryErrorThrownMetaspace() {
        String logLine = "OutOfMemoryError metaspace_errors=48";
        ExceptionCountsEvent event = new ExceptionCountsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCountsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_METASPACE),
                Analysis.ERROR_OOME_THROWN_METASPACE + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_PARALLEL_OLD_DISABLED),
                org.github.joa.util.Analysis.WARN_JDK11_PARALLEL_OLD_DISABLED + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_PARALLEL_OLD_DISABLED),
                org.github.joa.util.Analysis.WARN_JDK11_PARALLEL_OLD_DISABLED + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_CRUFT),
                org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_CRUFT + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_REDUNDANT),
                org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_REDUNDANT + " analysis incorrectly identified.");

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
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_CRUFT),
                org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_CRUFT + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_REDUNDANT),
                org.github.joa.util.Analysis.INFO_JDK11_PARALLEL_OLD_REDUNDANT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPhysicalMemoryInsufficientJvmStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset30.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.isError("Out of Memory Error"), "Out Of Memory Error not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_LIMIT_STARTUP),
                Analysis.ERROR_OOME_LIMIT_STARTUP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testPkiTomcatJar() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f47d77d3000-7f47d77d5000 r--s 00003000 fd:00 135061429                  "
                + "/usr/share/java/pki/pki-tomcat.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.getApplication() == JdkUtil.Application.PKI_TOMCAT,
                JdkUtil.Application.PKI_TOMCAT + " application not identified.");
        assertFalse(fel.getApplication() == JdkUtil.Application.TOMCAT,
                JdkUtil.Application.TOMCAT + " application incorrectlyu identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_PKI_TOMCAT), Analysis.INFO_PKI_TOMCAT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPkiTomcatJvmArgs() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvmArgs = "jvm_args: -Dcom.redhat.fips=false -Dcatalina.base=/var/lib/pki/pki-tomcat "
                + "-Dcatalina.home=/usr/share/tomcat -Djava.endorsed.dirs= "
                + "-Djava.io.tmpdir=/var/lib/pki/pki-tomcat/temp "
                + "-Djava.util.logging.config.file=/var/lib/pki/pki-tomcat/conf/logging.properties "
                + "-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djava.security.manager "
                + "-Djava.security.policy==/var/lib/pki/pki-tomcat/conf/catalina.policy";
        VmArgumentsEvent vmArgumentsEvent = new VmArgumentsEvent(jvmArgs);
        fel.getVmArgumentsEvents().add(vmArgumentsEvent);
        fel.doAnalysis();
        assertTrue(fel.getApplication() == JdkUtil.Application.PKI_TOMCAT,
                JdkUtil.Application.PKI_TOMCAT + " application not identified.");
        assertFalse(fel.getApplication() == JdkUtil.Application.TOMCAT,
                JdkUtil.Application.TOMCAT + " application incorrectlyu identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_PKI_TOMCAT), Analysis.INFO_PKI_TOMCAT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPkiTomcatThread() {
        FatalErrorLog fel = new FatalErrorLog();
        String thread = "  0x00007f47f9449800 JavaThread \"ACMEEngineConfigFileSource\" [_thread_blocked, id=369917, "
                + "stack(0x00007f47cc792000,0x00007f47cc893000)]";
        ThreadEvent threadEvent = new ThreadEvent(thread);
        fel.getThreadEvents().add(threadEvent);
        fel.doAnalysis();
        assertTrue(fel.getApplication() == JdkUtil.Application.PKI_TOMCAT,
                JdkUtil.Application.PKI_TOMCAT + " application not identified.");
        assertFalse(fel.getApplication() == JdkUtil.Application.TOMCAT,
                JdkUtil.Application.TOMCAT + " application incorrectlyu identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_PKI_TOMCAT), Analysis.INFO_PKI_TOMCAT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testPostgresqlJdbcJdkIncompatible() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.302-b08) for linux-amd64 JRE (1.8.0_302-b08), built on "
                + "Jul 16 2021 12:35:49 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        String dynamicLibrary = "7f7028969000-7f7028973000 r--s 000c0000 fd:06 131786                     "
                + "/path/to/postgresql-42.2.5.jar";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_POSTGRESQL_JDBC_JDK8_INCOMPATIBLE),
                Analysis.ERROR_POSTGRESQL_JDBC_JDK8_INCOMPATIBLE + " analysis not identified.");
    ***REMOVED***

    /**
     * Verify analysis file property key/value lookup.
     */
    @Test
    void testPropertyKeyValueLookup() {
        Analysis[] analysis = Analysis.values();
        for (int i = 0; i < analysis.length; i++) {
            assertNotNull(analysis[i] + " not found.", analysis[i].getValue());
        ***REMOVED***
    ***REMOVED***

    @Test
    void testPsPromotionManagerCopyToSurvivorSpace() {
        FatalErrorLog fel = new FatalErrorLog();
        String stackFrame1 = "V  [libjvm.so+0x9b43cc]  oopDesc* PSPromotionManager::copy_to_survivor_space<false>"
                + "(oopDesc*)+0x70c";
        StackEvent stackEvent = new StackEvent(stackFrame1);
        fel.getStackEvents().add(stackEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE),
                Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        // Test header only
        fel.getAnalysis().clear();
        fel.getStackEvents().clear();
        String header = "***REMOVED*** V  [libjvm.so+0x9b43cc]  oopDesc* PSPromotionManager::copy_to_survivor_space<false>"
                + "(oopDesc*)+0x70c";
        HeaderEvent headerEvent = new HeaderEvent(header);
        fel.getHeaderEvents().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE),
                Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testPthreadGetcpuclockid() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset32.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_PTHREAD_GETCPUCLOCKID),
                Analysis.ERROR_PTHREAD_GETCPUCLOCKID + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRemoteDebuggingEnabledAgentlib() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset45.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED),
                org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_VMWARE), Analysis.INFO_VMWARE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRemoteDebuggingEnabledRunjdwp() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx2g -Xrunjdwp:transport=dt_socket,server=y,address=8787,suspend=n -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED),
                org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED + " analysis not identified.");
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
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_POSSIBLE),
                Analysis.INFO_RH_BUILD_POSSIBLE + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testRhel7WorkstationrRpm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset19.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_RH_BUILD_RPM_INSTALL),
                Analysis.INFO_RH_BUILD_RPM_INSTALL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRhel9OpenJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.345-b01) for linux-amd64 JRE (1.8.0_345-b01), built on "
                + "Aug  4 2022 05:08:02 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfoEvent vmEvent = new VmInfoEvent(vm_info);
        fel.setVmInfoEvent(vmEvent);
        String os = "OS:Red Hat Enterprise Linux release 9.0 (Plow)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        fel.doAnalysis();
        assertEquals(OsVersion.RHEL9, fel.getOsVersion(), "OS version not correct.");
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_RHEL9_JDK8), Analysis.INFO_RHEL9_JDK8 + " analysis not identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LATEST),
                Analysis.WARN_JDK_NOT_LATEST + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testRhelJdkRpmMismatchNot() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.1 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        String library = "7fcb5ea06000-7fcb5f6ee000 r-xp 00000000 fd:01 121728675                  "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.222.b03-1.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent dynamicLibraryEvent = new DynamicLibraryEvent(library);
        fel.getDynamicLibraryEvents().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.222-b03) for linux-amd64 JRE (1.8.0_222-ea-b03), built "
                + "on May 22 2019 13:05:27 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("7.1", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("7", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis incorrectly identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_RHEL_JDK_RPM_MISMATCH),
                Analysis.ERROR_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL7_POWER9),
                Analysis.WARN_RHEL7_POWER9 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testRpmPpc64le() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset15.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_RH_BUILD_RPM_INSTALL),
                Analysis.INFO_RH_BUILD_RPM_INSTALL + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LATEST),
                Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9),
                Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9 + " analysis not identified.");
    ***REMOVED***

    @Test
    void testServerFlag32Bit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -server -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        // Specify 32-bit
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_64_SERVER_REDUNDANT),
                org.github.joa.util.Analysis.INFO_64_SERVER_REDUNDANT + " analysis incorrectly identified.");
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
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_METASPACE),
                org.github.joa.util.Analysis.INFO_METASPACE + " analysis not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_METASPACE_LT_COMP_CLASS),
                org.github.joa.util.Analysis.WARN_METASPACE_LT_COMP_CLASS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testShenandoahMarkLoopWork() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset44.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK),
                Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSiKernel() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset39.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_SI_KERNEL),
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
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE),
                Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testStackFreeSpaceGtStackSize() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset37.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE),
                Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE + " analysis not identified.");
    ***REMOVED***

    @Test
    void testStackOverflowError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STACKOVERFLOW),
                Analysis.ERROR_STACKOVERFLOW + " analysis not identified.");
    ***REMOVED***

    @Test
    void testStubroutines() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset17.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STUBROUTINES),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");
        testFile = new File(Constants.TEST_DATA_DIR + "dataset43.txt");
        fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STUBROUTINES),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");

    ***REMOVED***

    @Test
    void testSwapDisabled() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset28.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.isRhBuildOpenJdk(), "RH build of OpenJDK incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SWAP_DISABLED),
                Analysis.INFO_SWAP_DISABLED + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSwappingInfo() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset11.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.INFO_SWAPPING), Analysis.INFO_SWAPPING + " analysis not identified.");
    ***REMOVED***

    @Test
    void testSwappingWarn() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset12.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertTrue(fel.hasAnalysis(Analysis.WARN_SWAPPING), Analysis.WARN_SWAPPING + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JVM_DLL), Analysis.ERROR_JVM_DLL + " analysis not identified.");
    ***REMOVED***

    @Test
    void testTruncatedLog() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset48.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_TRUNCATED), Analysis.INFO_TRUNCATED + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OOPS), Analysis.ERROR_OOME_OOPS + " analysis not identified.");
    ***REMOVED***

    @Test
    void testUnidentifiedNativeLibrariesWindows() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset71.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(31, fel.getNativeLibraries().size(), "Number of native libraries not correct.");
        assertEquals(1, fel.getNativeLibrariesUnknown().size(), "Number of unidentified native libraries not correct.");
        assertEquals("C:\\Program Files\\Cylance\\Desktop\\CyMemDef64.dll", fel.getNativeLibrariesUnknown().get(0),
                "Unidentified native library not correct.");
    ***REMOVED***

    @Test
    void testUnknownStorageFalseReporting() {
        FatalErrorLog fel = new FatalErrorLog();
        assertFalse(fel.hasAnalysis(Analysis.INFO_STORAGE_UNKNOWN),
                Analysis.INFO_STORAGE_UNKNOWN + " analysis incorrectly identified.");
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
        assertTrue(fel.hasAnalysis(Analysis.INFO_JDK_ANCIENT), Analysis.INFO_JDK_ANCIENT + " analysis not identified.");
    ***REMOVED***

    @Test
    void testVmOperationConcurrentGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmOperation = "VM_Operation (0x0000008e276ff410): CGC_Operation, mode: safepoint, requested by thread "
                + "0x000001d9d3e12800";
        VmOperationEvent event = new VmOperationEvent(vmOperation);
        fel.setVmOperationEvent(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_VM_OPERATION_CONCURRENT_GC),
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
        assertTrue(fel.hasAnalysis(Analysis.INFO_VM_OPERATION_HEAP_DUMP),
                Analysis.INFO_VM_OPERATION_HEAP_DUMP + " analysis not identified.");
    ***REMOVED***

    @Test
    void testWarnNotLatestJdkValue() {
        assertEquals("JDK is not the latest version. Latest version is ", Analysis.WARN_JDK_NOT_LATEST.getValue(),
                Analysis.WARN_JDK_NOT_LATEST + "value not correct.");
    ***REMOVED***

    @Test
    void testWilyCrash() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  0x0000000000000e76";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 16666  com.wily.introscope.agent.platform.linux.LinuxPlatformStatisticsBackEnd."
                + "getAggregateCPUUsage(Ljava/lang/String;)[J (0 bytes) @ 0x00007f6dabd0e7fc [0x00007f6dabd0e740+0xbc]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_WILY), Analysis.ERROR_WILY + " analysis not identified.");
    ***REMOVED***

    @Test
    void testWilyDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f6d9a3b7000-7f6d9a4b6000 ---p 00003000 fd:08 98413                      "
                + "/app/jbossas/wily10.7/core/ext/libIntroscopeLinuxIntelAmd64Stats.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(dynamicLibrary);
        fel.getDynamicLibraryEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_WILY), Analysis.INFO_WILY + " analysis not identified.");
    ***REMOVED***

    @Test
    void testWilyInStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "J  12345 com.example.MyClass";
        StackEvent stackEvent1 = new StackEvent(stack1);
        fel.getStackEvents().add(stackEvent1);
        String stack2 = "J 16666  com.wily.introscope.agent.platform.linux.LinuxPlatformStatisticsBackEnd."
                + "getAggregateCPUUsage(Ljava/lang/String;)[J (0 bytes) @ 0x00007f6dabd0e7fc [0x00007f6dabd0e740+0xbc]";
        StackEvent stackEvent2 = new StackEvent(stack2);
        fel.getStackEvents().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_WILY), Analysis.WARN_WILY + " analysis not identified.");
    ***REMOVED***

    @Test
    void testZGcHeapEvent() {
        FatalErrorLog fel = new FatalErrorLog();
        String zHeap = " ZHeap           used 4M, capacity 500M, max capacity 7978M";
        HeapEvent heapEvent = new HeapEvent(zHeap);
        fel.getHeapEvents().add(heapEvent);
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.ZGC),
                GarbageCollector.ZGC + " collector not identified.");
    ***REMOVED***

    @Test
    void testZGcJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseZGC -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.ZGC),
                GarbageCollector.ZGC + " collector not identified.");
    ***REMOVED***
***REMOVED***

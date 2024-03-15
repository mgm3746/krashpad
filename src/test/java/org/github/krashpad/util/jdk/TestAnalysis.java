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
package org.github.krashpad.util.jdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Date;

import org.github.joa.domain.GarbageCollector;
import org.github.krashpad.domain.jdk.BarrierSet;
import org.github.krashpad.domain.jdk.CompilationEvent;
import org.github.krashpad.domain.jdk.ContainerInfo;
import org.github.krashpad.domain.jdk.CpuInfo;
import org.github.krashpad.domain.jdk.CurrentCompileTask;
import org.github.krashpad.domain.jdk.CurrentThread;
import org.github.krashpad.domain.jdk.DynamicLibrary;
import org.github.krashpad.domain.jdk.EnvironmentVariable;
import org.github.krashpad.domain.jdk.Event;
import org.github.krashpad.domain.jdk.ExceptionCounts;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.GlobalFlag;
import org.github.krashpad.domain.jdk.Header;
import org.github.krashpad.domain.jdk.Heap;
import org.github.krashpad.domain.jdk.LdPreloadFile;
import org.github.krashpad.domain.jdk.MaxMapCount;
import org.github.krashpad.domain.jdk.Meminfo;
import org.github.krashpad.domain.jdk.Memory;
import org.github.krashpad.domain.jdk.OsInfo;
import org.github.krashpad.domain.jdk.RegisterToMemoryMapping;
import org.github.krashpad.domain.jdk.SigInfo;
import org.github.krashpad.domain.jdk.Stack;
import org.github.krashpad.domain.jdk.StackSlotToMemoryMapping;
import org.github.krashpad.domain.jdk.Thread;
import org.github.krashpad.domain.jdk.Time;
import org.github.krashpad.domain.jdk.TimeElapsedTime;
import org.github.krashpad.domain.jdk.Timeout;
import org.github.krashpad.domain.jdk.TransparentHugepageEnabled;
import org.github.krashpad.domain.jdk.VmArguments;
import org.github.krashpad.domain.jdk.VmInfo;
import org.github.krashpad.domain.jdk.VmOperation;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.KrashUtil;
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
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_LINUX_ZIP.getKey()),
                Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.");
        assertEquals(JavaVendor.ADOPTOPENJDK, fel.getJavaVendor(), "Java vendor not correct.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX.getKey()),
                Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testAppDynamicsDetectedCompilationEvent() {
        FatalErrorLog fel = new FatalErrorLog();
        String compilation = "Event: 1234.567 Thread 0x000002a0fb87b000 124536   !   4       "
                + "com.singularity.ee.agent.util.reflect.ReflectionUtility::getDeclaredField (79 bytes)";
        CompilationEvent event = new CompilationEvent(compilation);
        fel.getCompilationEvents().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_DETECTED.getKey()),
                Analysis.INFO_APP_DYNAMICS_DETECTED + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_POSSIBLE.getKey()),
                Analysis.INFO_APP_DYNAMICS_POSSIBLE + " analysis incorreclty identified.");
    }

    @Test
    void testAppDynamicsDetectedJavaAgent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -javaagent:C:\\appdynamics\\appagent\\javaagent\\javaagent.jar -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_DETECTED.getKey()),
                Analysis.INFO_APP_DYNAMICS_DETECTED + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_POSSIBLE.getKey()),
                Analysis.INFO_APP_DYNAMICS_POSSIBLE + " analysis incorreclty identified.");
    }

    @Test
    void testAppDynamicsPossible() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -javaagent:C:\\path\\to\\javaagent.jar -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_POSSIBLE.getKey()),
                Analysis.INFO_APP_DYNAMICS_POSSIBLE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_APP_DYNAMICS_DETECTED.getKey()),
                Analysis.INFO_APP_DYNAMICS_DETECTED + " analysis incorrectly identified.");
    }

    @Test
    void testAvx2StringCompareInHeader() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# Problematic frame:";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# J 3917 C2 java.lang.String.compareTo(Ljava/lang/Object;)I (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfo cpuInfoEvent = new CpuInfo(cpuInfo);
        fel.getCpuInfos().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO.getKey()),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2.getKey()),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE.getKey()),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis incorrectly identified.");
    }

    @Test
    void testAvx2StringCompareToAvxDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "J 3917 C2 com.example.Object.someMethod() (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);
        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfo cpuInfoEvent = new CpuInfo(cpuInfo);
        fel.getCpuInfos().add(cpuInfoEvent);
        String jvm_args = "jvm_args: -Xms256m -XX:UseAVX=0 -Xmx2048m";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO.getKey()),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2.getKey()),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE.getKey()),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis not identified.");
    }

    @Test
    void testAvx2StringCompareToInStackJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "J 3917 C2 java.lang.String.compareTo(Ljava/lang/Object;)I (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);
        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfo cpuInfoEvent = new CpuInfo(cpuInfo);
        fel.getCpuInfos().add(cpuInfoEvent);
        String jvm_args = "jvm_args: -Xms256m -Xmx2048m";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO.getKey()),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2.getKey()),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE.getKey()),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis incorrectly identified.");
    }

    @Test
    void testAvx2StringCompareToInStackNoJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "J 3917 C2 java.lang.String.compareTo(Ljava/lang/Object;)I (9 bytes) @ 0x00007f414557b275 "
                + "[0x00007f414557b1a0+0xd5]";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);

        String cpuInfo = "CPU:total 16 (initial active 16) (1 cores per cpu, 1 threads per core) family 6 model 85 "
                + "stepping 4, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx";
        CpuInfo cpuInfoEvent = new CpuInfo(cpuInfo);
        fel.getCpuInfos().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_AVX2_STRING_COMPARE_TO.getKey()),
                Analysis.ERROR_AVX2_STRING_COMPARE_TO + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_COMPILED_JAVA_CODE_AVX2.getKey()),
                Analysis.INFO_COMPILED_JAVA_CODE_AVX2 + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE.getKey()),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis incorrectly identified.");
    }

    @Test
    void testAws() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset34.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_STORAGE_AWS.getKey()),
                Analysis.INFO_STORAGE_AWS + " analysis not identified.");
    }

    @Test
    void testBuildDateIsNotRedHat() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f72e3ca8000-7f72e4a45000 r-xp 00000000 103:02 45812172                  "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.322.b06-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.322-b06) for linux-amd64 JRE (1.8.0_322-b06), built on "
                + "Jan 27 2022 17:54:59 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String os = "OS:CentOS Linux release 7.9.2009 (Core)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.isRhBuildString(), "Red Hat build string not identified.");
        assertTrue(fel.isRhVersion(), "Red Hat version not identified.");
        assertEquals(KrashUtil.getDate("Jan 27 2022 17:54:59"), fel.getJdkBuildDate(), "Build date not correct.");
        assertFalse(fel.isRhBuildDate(), "Red Hat build date incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_POSSIBLE.getKey()),
                Analysis.INFO_RH_BUILD_POSSIBLE + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT.getKey()),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
    }

    @Test
    void testCannotGetLibraryInformation() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline1 = "Dynamic libraries:";
        DynamicLibrary event1 = new DynamicLibrary(logline1);
        fel.getDynamicLibraries().add(event1);
        String logline2 = "Can not get library information for pid = 123456";
        DynamicLibrary event2 = new DynamicLibrary(logline2);
        fel.getDynamicLibraries().add(event2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION.getKey()),
                Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION + " analysis not identified.");
    }

    @Test
    void testClientFlag32Bit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -client -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfo vmInfoEvent = new VmInfo(logLine);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_64_CLIENT.getKey()),
                org.github.joa.util.Analysis.INFO_64_CLIENT + " analysis incorrectly identified.");
    }

    @Test
    void testCloudPerfDataDisk() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        ContainerInfo containerInfoEvent = new ContainerInfo("container (cgroup) information:");
        fel.getContainerInfos().add(containerInfoEvent);
        Meminfo meminfoEvent = new Meminfo("SwapTotal:       0 kB");
        fel.getMeminfos().add(meminfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_CONTAINER_PERF_DATA_DISK.getKey()),
                org.github.joa.util.Analysis.WARN_CONTAINER_PERF_DATA_DISK + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_PERF_DATA_DISABLED.getKey()),
                org.github.joa.util.Analysis.INFO_PERF_DATA_DISABLED + " analysis incorrectly identified.");
    }

    @Test
    void testCmsCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PAR_NEW),
                GarbageCollector.PAR_NEW + " collector not identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.CMS),
                GarbageCollector.CMS + " collector not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    /**
     * Test if CMS collector disabled with -XX:-UseConcMarkSweepGC -XX:-UseParNewGC.
     */
    @Test
    void testCmsDisabledJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().clear();
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_JDK8_CMS_PAR_NEW_DISABLED.getKey()),
                org.github.joa.util.Analysis.ERROR_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT.getKey()),
                org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_CMS_DISABLED.getKey()),
                org.github.joa.util.Analysis.INFO_CMS_DISABLED + " analysis not identified.");
    }

    @Test
    void testCmsIncrementalModeCollectorCms() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode ";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String cpu = "CPU:total 8 (2 cores per cpu, 1 threads per core)";
        CpuInfo cpuInfoEvent = new CpuInfo(cpu);
        fel.getCpuInfos().add(cpuInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_CMS_INCREMENTAL_MODE.getKey()),
                Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis not identified.");
    }

    @Test
    void testCmsIncrementalModeCollectorCmsDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-UseConcMarkSweepGC -XX:+CMSIncrementalMode ";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String cpu = "CPU:total 8 (2 cores per cpu, 1 threads per core)";
        CpuInfo cpuInfoEvent = new CpuInfo(cpu);
        fel.getCpuInfos().add(cpuInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_CMS_INCREMENTAL_MODE.getKey()),
                Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis incorrectly identified.");
    }

    /**
     * Test if PAR_NEW collector is enabled/disabled when the CMS collector is not used.
     */
    @Test
    void testCmsParNewCruftJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseParNewGC";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_JDK8_CMS_PAR_NEW_DISABLED.getKey()),
                org.github.joa.util.Analysis.ERROR_JDK8_CMS_PAR_NEW_DISABLED + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_CMS_DISABLED.getKey()),
                org.github.joa.util.Analysis.INFO_CMS_DISABLED + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT.getKey()),
                org.github.joa.util.Analysis.INFO_JDK8_CMS_PAR_NEW_CRUFT + " analysis not identified.");
    }

    @Test
    void testCompilerThreadC2BeautifyLoops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset72.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS.getKey()),
                Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testCompilerThreadC2IfNodeFoldComparesJdk11Update7() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# Problematic frame:";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# V  [libjvm.so+0xe3d524]  SubINode::Ideal(PhaseGVN*, bool)+0x24";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        String currentThread = "Current thread (0x000055e5d82b1800):  JavaThread \"C2 CompilerThread0\" daemon "
                + "[_thread_in_native, id=1226739, stack(0x00007f4c95bd6000,0x00007f4c95cd7000)]";
        CurrentThread currentThreadEvent = new CurrentThread(currentThread);
        fel.setCurrentThread(currentThreadEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.7+10-LTS) for linux-amd64 JRE (11.0.7+10-LTS), built "
                + "on Apr 15 2020 12:25:53 by \"mockbuild\" with gcc 8.3.1 20190507 (Red Hat 8.3.1-4)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_IFNODE_FOLDCOMPARES.getKey()),
                Analysis.ERROR_COMPILER_THREAD_C2_IFNODE_FOLDCOMPARES + " analysis not identified.");
    }

    @Test
    void testCompilerThreadC2IfNodeFoldComparesJdk11Update9() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# Problematic frame:";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# V  [libjvm.so+0xe3d524]  SubINode::Ideal(PhaseGVN*, bool)+0x24";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        String currentThread = "Current thread (0x000055e5d82b1800):  JavaThread \"C2 CompilerThread0\" daemon "
                + "[_thread_in_native, id=1226739, stack(0x00007f4c95bd6000,0x00007f4c95cd7000)]";
        CurrentThread currentThreadEvent = new CurrentThread(currentThread);
        fel.setCurrentThread(currentThreadEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9.1+1-LTS) for linux-amd64 JRE (11.0.9.1+1-LTS), "
                + "built on Nov 11 2020 12:19:11 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_IFNODE_FOLDCOMPARES.getKey()),
                Analysis.ERROR_COMPILER_THREAD_C2_IFNODE_FOLDCOMPARES + " analysis incorrectly identified.");
    }

    @Test
    void testCompilerThreadC2MininodeIdealJdk11() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset87.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL.getKey()),
                Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testCompilerThreadC2MininodeIdealJdk8() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset76.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL.getKey()),
                Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testContainer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset47.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_CGROUP_VERSION.getKey()),
                Analysis.INFO_CGROUP_VERSION + " analysis not identified.");
        assertEquals("cgroup version: cgroupv1.", fel.getAnalysisLiteral(Analysis.INFO_CGROUP_VERSION.getKey()),
                Analysis.INFO_CGROUP_VERSION + " not correct.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_MEMORY_JVM_NE_SYSTEM.getKey()),
                Analysis.INFO_MEMORY_JVM_NE_SYSTEM + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_CGROUP_MEMORY_LIMIT.getKey()),
                Analysis.INFO_CGROUP_MEMORY_LIMIT + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testCrash3rdPartyLibrary() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  [my-library_123.so+0x22c30d]";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String dynamicLibrary = "7f4d6fd25000-7f4d70359000 r-xp 00000000 fd:04 402192                     "
                + "/path/to/my-library_123.so";
        DynamicLibrary event = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN.getKey()),
                Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN + " analysis not identified.");
    }

    @Test
    void testCrashInUnknownNativeLibraryHeader() {
        FatalErrorLog fel = new FatalErrorLog();
        String header = "# C  [libpbul_aca-elf64.so+0x10319]  vfork+0x30c";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        String dynamicLibrary = "7fb6447c4000-7fb6447e8000 r-xp 00000000 08:05 525209                     "
                + "/path/to/libpbul_aca-elf64.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN.getKey()),
                Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN + " analysis not identified.");
    }

    @Test
    void testCrashInUnknownNativeLibraryStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "C  [libpbul_aca-elf64.so+0x10319]  vfork+0x30c";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);
        String dynamicLibrary = "7fb6447c4000-7fb6447e8000 r-xp 00000000 08:05 525209                     "
                + "/path/to/libpbul_aca-elf64.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN.getKey()),
                Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN + " analysis not identified.");
    }

    @Test
    void testCrashOnOomeHeap() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+CrashOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError "
                + "-XX:HeapDumpPath=/path/to/mydir";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String header = "#  fatal error: OutOfMemory encountered: Java heap space";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        String stack = "V  [libjvm.so+0xb2caf6]  TypeArrayKlass::allocate_common(int, bool, Thread*)+0x796";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_CRASH_ON_OOME_HEAP.getKey()),
                Analysis.ERROR_CRASH_ON_OOME_HEAP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    }

    @Test
    void testCrashStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset33.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_JVM_STARTUP_FAILS.getKey()),
                Analysis.INFO_JVM_STARTUP_FAILS + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_SIGSEGV.getKey()),
                Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testD64Flag32Bit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -d64 -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfo vmInfoEvent = new VmInfo(logLine);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_64_D64_REDUNDANT.getKey()),
                org.github.joa.util.Analysis.INFO_64_D64_REDUNDANT + " analysis incorrectly identified.");
    }

    @Test
    void testDbcp2Postgresql() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "j  org.postgresql.Driver.connect(Ljava/lang/String;Ljava/util/Properties;)Ljava/sql/"
                + "Connection;+222";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "j  org.apache.commons.dbcp2.BasicDataSource.getConnection()Ljava/sql/Connection;+55";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_DBCP2.getKey()), Analysis.INFO_DBCP2 + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_POSTGRESQL_CONNECTION.getKey()),
                Analysis.INFO_POSTGRESQL_CONNECTION + " analysis not identified.");
    }

    @Test
    void testDefaultCollectorJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String jvmArgs = "jvm_args: -Xss128k";
        VmArguments vmArgumentsEvent = new VmArguments(jvmArgs);
        fel.getVmArguments().add(vmArgumentsEvent);
        fel.doAnalysis();
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.G1),
                GarbageCollector.G1 + " collector not identified.");
    }

    @Test
    void testDefaultCollectorJdk17() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.1+12-LTS) for linux-amd64 JRE (17.0.1+12-LTS), built "
                + "on Oct 28 2021 01:59:13 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-3)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String jvmArgs = "jvm_args: -Xss128k";
        VmArguments vmArgumentsEvent = new VmArguments(jvmArgs);
        fel.getVmArguments().add(vmArgumentsEvent);
        fel.doAnalysis();
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.G1),
                GarbageCollector.G1 + " collector not identified.");
    }

    @Test
    void testDefaultCollectorJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String jvm_args = "jvm_args: -Xss128k";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
    }

    @Test
    void testDeprecatedLoggingOptionsJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintGC -XX:+PrintGCDetails -Xloggc:gc.log -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built "
                + "on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_OVERWRITE.getKey()),
                org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_OVERWRITE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_OVERWRITE.getKey()),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_OVERWRITE + " analysis incorrectly identified.");
        assertFalse(
                fel.getJvmOptions()
                        .hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED.getKey()),
                org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED
                        + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_LOGGC.getKey()),
                org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_LOGGC + " analysis not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC.getKey()),
                org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC + " analysis not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC_DETAILS.getKey()),
                org.github.joa.util.Analysis.INFO_JDK9_DEPRECATED_PRINT_GC_DETAILS + " analysis not identified.");
    }

    @Test
    void testDirectByteBufferUnsynchronizedAccess() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset24.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(Application.JBOSS_EAP7, fel.getApplication(), "Application not correct.");
        assertEquals("v  ~StubRoutines::jbyte_disjoint_arraycopy", fel.getStackFrameTop(),
                "To stack frame not correct.");
        assertTrue(fel.isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER), "DirectByteBuffer class not identified in stack.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION.getKey()),
                Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7.getKey()),
                Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7 + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_EXPLICIT_GC_DISABLED.getKey()),
                org.github.joa.util.Analysis.WARN_EXPLICIT_GC_DISABLED + " analysis incorrectly identified.");
        assertEquals(0, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testDynatraceCrash() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack = "C  [liboneagentproc.so+0x17993]";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_DYNATRACE.getKey()),
                Analysis.ERROR_DYNATRACE + " analysis not identified.");
    }

    @Test
    void testDynatraceDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7effff525000-7effff526000 rw-p 000ce000 fd:02 4238968 "
                + "/usr/lib64/liboneagentproc.so";
        DynamicLibrary event = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_DYNATRACE.getKey()),
                Analysis.INFO_DYNATRACE + " analysis not identified.");
    }

    @Test
    void testDynatraceInStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset55.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_DYNATRACE.getKey()),
                Analysis.WARN_DYNATRACE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testErrorJdk8LibcCfree() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset78.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_LIBC_CFREE.getKey()),
                Analysis.ERROR_JDK8_LIBC_CFREE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");

    }

    @Test
    void testErrorStubroutinesHeaderOnly() {
        FatalErrorLog fel = new FatalErrorLog();
        String logLine = "# v  ~StubRoutines::jbyte_disjoint_arraycopy";
        Header headerEvent = new Header(logLine);
        fel.getHeaders().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STUBROUTINES.getKey()),
                Analysis.ERROR_STUBROUTINES + " analysis not identified.");
    }

    @Test
    void testExceptionAccessViolationHeader() {
        FatalErrorLog fel = new FatalErrorLog();
        String header = "#  EXCEPTION_ACCESS_VIOLATION (0xc0000005) at pc=0x000000006ee76520, pid=11556, "
                + "tid=0x000000000000040c";
        Header event = new Header(header);
        fel.getHeaders().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION.getKey()),
                Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION + " analysis not identified.");
    }

    @Test
    void testExceptionAccessViolationSiginfo() {
        FatalErrorLog fel = new FatalErrorLog();
        String siginfo = "siginfo: EXCEPTION_ACCESS_VIOLATION (0xc0000005), reading address 0xffffffffffffffff";
        SigInfo event = new SigInfo(siginfo);
        fel.setSigInfo(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION.getKey()),
                Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION + " analysis not identified.");
    }

    @Test
    void testExceptionStackOverflowHeader() {
        FatalErrorLog fel = new FatalErrorLog();
        String header = "#  EXCEPTION_STACK_OVERFLOW (0xc00000fd) at pc=0x00007ff8b9b47447, pid=4304, "
                + "tid=0x0000000000002aec";
        Header event = new Header(header);
        fel.getHeaders().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_EXCEPTION_STACK_OVERFLOW.getKey()),
                Analysis.INFO_SIGNO_EXCEPTION_STACK_OVERFLOW + " analysis not identified.");
    }

    @Test
    void testExceptionStackOverflowSiginfo() {
        FatalErrorLog fel = new FatalErrorLog();
        String siginfo = "siginfo: ExceptionCode=0xc00000fd, ExceptionInformation=0x0000000000000001 "
                + "0x00000000c9dd0000";
        SigInfo event = new SigInfo(siginfo);
        fel.setSigInfo(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_EXCEPTION_STACK_OVERFLOW.getKey()),
                Analysis.INFO_SIGNO_EXCEPTION_STACK_OVERFLOW + " analysis not identified.");
    }

    @Test
    void testExperimentalErgonomic() {
        FatalErrorLog fel = new FatalErrorLog();
        String logLine = "     bool UseFastUnorderedTimeStamps               = true                                 "
                + "{experimental} {ergonomic}";
        GlobalFlag event = new GlobalFlag(logLine);
        fel.getGlobalFlags().add(event);
        fel.doAnalysis();
        assertEquals(1, fel.getGlobalFlagsExperimentalErgonomic().size(),
                "Global flags experimental ergonomic count not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_EXPERIMENTAL_ERGONOMIC.getKey()),
                Analysis.WARN_EXPERIMENTAL_ERGONOMIC + " analysis not identified.");
        assertEquals("The following experimental options are being set by ergonomics: UseFastUnorderedTimeStamps=true.",
                fel.getAnalysisLiteral(Analysis.WARN_EXPERIMENTAL_ERGONOMIC.getKey()),
                Analysis.WARN_EXPERIMENTAL_ERGONOMIC + " not correct.");
    }

    /**
     * Test if explicit not GC handled concurrently.
     */
    @Test
    void testExplictGcNotConcurrent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String logLine = "concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, "
                + "0x00000007c0000000, 0x00000007c0000000)";
        Heap heapEvent = new Heap(logLine);
        fel.getHeaps().add(heapEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_EXPLICIT_GC_NOT_CONCURRENT.getKey()),
                org.github.joa.util.Analysis.WARN_EXPLICIT_GC_NOT_CONCURRENT + " analysis not identified.");
    }

    @Test
    void testFailedToMapBytes() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset58.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_RLIMIT_OOPS.getKey()),
                Analysis.ERROR_OOME_RLIMIT_OOPS + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testFailedToMapBytesNoSwap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset75.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_JVM.getKey()),
                Analysis.ERROR_OOME_JVM + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP.getKey()),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_UNIDENTIFIED_LOG_LINE.getKey()),
                Analysis.WARN_UNIDENTIFIED_LOG_LINE + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testFatalErrorLogAncient() {
        FatalErrorLog fel = new FatalErrorLog();
        String time = "time: Tue Aug 18 14:10:59 2020";
        Time timeEven = new Time(time);
        fel.setTime(timeEven);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_FATAL_ERROR_LOG_ANCIENT.getKey()),
                Analysis.WARN_FATAL_ERROR_LOG_ANCIENT + " analysis not identified.");
    }

    @Test
    void testFpe() {
        FatalErrorLog fel = new FatalErrorLog();
        String siginfo = "siginfo: si_signo: 8 (SIGFPE), si_code: 1 (FPE_INTDIV), si_addr: 0x00007fdfe95e789f";
        SigInfo event = new SigInfo(siginfo);
        fel.setSigInfo(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_SIGFPE.getKey()),
                Analysis.INFO_SIGNO_SIGFPE + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_FPE_INTDIV.getKey()),
                Analysis.INFO_SIGCODE_FPE_INTDIV + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_FPE.getKey()), Analysis.ERROR_FPE + " analysis not identified.");
    }

    @Test
    void testFreetypeFontScalerGetGlyphImageNative() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset54.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE.getKey()),
                Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testG1ParScanThreadStateCopyToSurvivorSpace() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset60.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        String stackFrame1 = "V  [libjvm.so+0x5b4ab3]  G1ParScanThreadState::copy_to_survivor_space(InCSetState, "
                + "oopDesc*, markOopDesc*)+0x2e3";
        String stackFrame2 = "V  [libjvm.so+0x5b54ae]  G1ParScanThreadState::trim_queue()+0x59e";
        assertEquals(stackFrame1, fel.getStackFrame(1), "Stack frame 1 not correct.");
        assertEquals(stackFrame2, fel.getStackFrame(2), "Stack frame 2 not correct.");
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE.getKey()),
                Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        // Test header only
        fel.getAnalysis().clear();
        fel.getStacks().clear();
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE.getKey()),
                Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testG1ParScanThreadStateCopyToSurvivorSpaceNot() {
        FatalErrorLog fel = new FatalErrorLog();
        String stackFrame1 = "V  [libjvm.so+0x7d7b76]  G1ParScanThreadState::copy_to_survivor_space(InCSetState, "
                + "oopDesc*, markOopDesc*)+0x56";
        Stack stackEvent1 = new Stack(stackFrame1);
        fel.getStacks().add(stackEvent1);
        String stackFrame2 = "V  [libjvm.so+0x7d8968]  G1ParScanThreadState::trim_queue()+0x648";
        Stack stackEvent2 = new Stack(stackFrame2);
        fel.getStacks().add(stackEvent2);
        assertEquals(stackFrame1, fel.getStackFrame(1), "Stack frame 1 not correct.");
        assertEquals(stackFrame2, fel.getStackFrame(2), "Stack frame 2 not correct.");
        String header = "# JRE version: OpenJDK Runtime Environment (Red_Hat-11.0.17.0.8-2.el8_6) (11.0.17+8) "
                + "(build 11.0.17+8-LTS)";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE.getKey()),
                Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis not identified.");
    }

    @Test
    void testG1SummarizeRSetStats() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+G1SummarizeRSetStats -XX:G1SummarizeRSetStatsPeriod=1";
        VmArguments vmArgumentEvent = new VmArguments(jvm_args);
        Heap heapEvent = new Heap(
                "garbage-first heap   total 15728640K, used 2720924K [0x0000000300000000, 0x0000000300407800, "
                        + "0x00000006c0000000)");
        fel.getHeaps().add(heapEvent);
        fel.getVmArguments().add(vmArgumentEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_G1_SUMMARIZE_RSET_STATS_OUTPUT.getKey()),
                org.github.joa.util.Analysis.INFO_G1_SUMMARIZE_RSET_STATS_OUTPUT + " analysis not identified.");
    }

    @Test
    void testGarbageCollectorsJvmOptionsMismatch() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseG1GC";
        VmArguments vmArgumentEvent = new VmArguments(jvm_args);
        fel.getVmArguments().add(vmArgumentEvent);
        // Test a combination not yet seen
        Heap heapEvent1 = new Heap("Shenandoah Heap");
        fel.getHeaps().add(heapEvent1);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_GC_IGNORED.getKey()),
                org.github.joa.util.Analysis.INFO_GC_IGNORED + " analysis not identified.");
        // Test real world
        fel.getAnalysis().clear();
        fel.getHeaps().clear();
        Heap heapEvent2 = new Heap(
                " PSYoungGen      total 611840K, used 524800K [0x00000000d5580000, 0x0000000100000000, "
                        + "0x0000000100000000)");
        fel.getHeaps().add(heapEvent2);
        Heap heapEvent3 = new Heap(" ParOldGen       total 1398272K, used 16K [0x0000000080000000, 0x00000000d5580000, "
                + "0x00000000d5580000)");
        fel.getHeaps().add(heapEvent3);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_GC_IGNORED.getKey()),
                org.github.joa.util.Analysis.INFO_GC_IGNORED + " analysis not identified.");
    }

    @Test
    void testGregorianCalendarComputeTimeJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "Stack: [0x00007f3d8974c000,0x00007f3d8984d000],  sp=0x00007f3d8984af90,  free space=1019k";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "Native frames: (J=compiled Java code, A=aot compiled Java code, j=interpreted, Vv=VM code, "
                + "C=native code)";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String stack3 = "J 35755 c2 java.util.GregorianCalendar.computeTime()V java.base@11.0.19 (970 bytes) "
                + "@ 0x00007f3dcdbc0433 [0x00007f3dcdbbf440+0x0000000000000ff3]";
        Stack stackEvent3 = new Stack(stack3);
        fel.getStacks().add(stackEvent3);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.19+7-LTS) for linux-amd64 JRE (11.0.19+7-LTS), built "
                + "on Apr 14 2023 17:03:28 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-16)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_GREGORIANCALENDAR_COMPUTETIME.getKey()),
                Analysis.ERROR_GREGORIANCALENDAR_COMPUTETIME + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_POINTER_NULL.getKey()),
                Analysis.ERROR_POINTER_NULL + " analysis incorrectly identified.");

    }

    @Test
    void testGregorianCalendarComputeTimeJdk17() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset86.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_GREGORIANCALENDAR_COMPUTETIME.getKey()),
                Analysis.ERROR_GREGORIANCALENDAR_COMPUTETIME + " analysis not identified.");
    }

    @Test
    void testHashMap() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "J 28841 C2 java.util.HashMap.putVal(ILjava/lang/Object;Ljava/lang/Object;ZZ)Ljava/lang/"
                + "Object; (300 bytes) @ 0x00007f5c5613467a [0x00007f5c561320a0+0x25da]";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 34843 C2 com.example.Service.save(Lcom/example/Entity;Ljava/lang/String;Z)V (83 bytes) "
                + "@ 0x00007f5c5514d8fc [0x00007f5c5514d420+0x4dc]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String logline = "7f5c61494000-7f5c62233000 r-xp 00000000 fd:00 17171138                   "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.312-b07) for linux-amd64 JRE (1.8.0_312-b07), "
                + "built on Oct 15 2021 04:33:40 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_HASHMAP.getKey()),
                Analysis.ERROR_HASHMAP + " analysis not identified.");
    }

    @Test
    void testHeapMaxMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset56.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_HEAP_MAX_MISSING.getKey()),
                org.github.joa.util.Analysis.INFO_HEAP_MAX_MISSING + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testIbmToolkit() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff46c40000-7fff46c80000 r--s 00520000 fd:0a 67109322                   "
                + "/path/to/jt400.jar";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_IBM_TOOLKIT.getKey()),
                Analysis.INFO_IBM_TOOLKIT + " analysis not identified.");
    }

    @Test
    void testInsufficientMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset27.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.isRhBuildOpenJdk(), "RH build of OpenJDK incorrectly identified.");
        long physicalMemory = JdkUtil.convertSize(15995796, 'K', 'B');
        assertEquals(physicalMemory, fel.getJvmMemTotal(), "Physical memory not correct.");
        long physicalMemoryFree = JdkUtil.convertSize(241892, 'K', 'B');
        assertEquals(physicalMemoryFree, fel.getJvmMemFree(), "Physical memory free not correct.");
        long swap = JdkUtil.convertSize(10592252, 'K', 'B');
        assertEquals(swap, fel.getJvmSwapTotal(), "Swap not correct.");
        long swapFree = JdkUtil.convertSize(4, 'K', 'B');
        assertEquals(swapFree, fel.getJvmSwapFree(), "Swap free not correct.");
        long heapInitial = JdkUtil.convertSize(2048, 'M', 'B');
        assertEquals(heapInitial, fel.getHeapInitialSize(), "Heap initial size not correct.");
        long heapMax = JdkUtil.convertSize(8192, 'M', 'B');
        assertEquals(heapMax, fel.getHeapMaxSize(), "Heap max size not correct.");
        long heapAllocationYoung = JdkUtil.convertSize(2761728, 'K', 'B');
        long heapAllocationOld = JdkUtil.convertSize(4838912, 'K', 'B');
        long heapAllocation = heapAllocationYoung + heapAllocationOld;
        assertEquals(heapAllocation, fel.getHeapAllocation(), "Heap allocation not correct.");
        long heapUsed = JdkUtil.convertSize(0 + 2671671, 'K', 'B');
        assertEquals(heapUsed, fel.getHeapUsed(), "Heap used not correct.");
        long metaspaceMax = JdkUtil.convertSize(8192, 'M', 'B');
        assertEquals(metaspaceMax, fel.getMetaspaceMaxSize(), "Metaspace max size not correct.");
        long metaspaceAllocation = JdkUtil.convertSize(471808, 'K', 'B');
        assertEquals(metaspaceAllocation, fel.getMetaspaceAllocation(), "Metaspace allocation not correct.");
        long metaspaceUsed = JdkUtil.convertSize(347525, 'K', 'B');
        assertEquals(metaspaceUsed, fel.getMetaspaceUsed(), "Metaspace used not correct.");
        long directMemoryMax = JdkUtil.convertSize(0, 'G', 'B');
        assertEquals(directMemoryMax, fel.getDirectMemoryMaxSize(), "Direct Memory mx not correct.");
        assertEquals(1024, fel.getThreadStackSize(), "Thread stack size not correct.");
        assertEquals(2, fel.getJavaThreadCount(), "Thread count not correct.");
        long threadMemory = JdkUtil.convertSize(1024 * 2, 'K', 'B');
        assertEquals(threadMemory, fel.getThreadStackMemory(), "Thread memory not correct.");
        long codeCacheSize = JdkUtil.convertSize(420, 'M', 'B');
        assertEquals(codeCacheSize, fel.getCodeCacheSize(), "Code cache size not correct.");
        assertEquals(heapMax + metaspaceMax + directMemoryMax + threadMemory + codeCacheSize, fel.getJvmMemoryMax(),
                "Jvm memory not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP.getKey()),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testItextDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff467a0000-7fff467c0000 r--s 00220000 fd:0a 67109364                   "
                + "/path/to/itextpdf-5.5.13.1.jar";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_ITEXT.getKey()), Analysis.INFO_ITEXT + " analysis not identified.");
    }

    @Test
    void testItextInStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fff467a0000-7fff467c0000 r--s 00220000 fd:0a 67109364                   "
                + "/path/to/itextpdf-5.5.13.1.jar";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String stack1 = "v  ~BufferBlob::StubRoutines (2)";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 42480 C2 com.itextpdf.text.pdf.RandomAccessFileOrArray.readFully([B)V (9 bytes) @ "
                + "0x00007f8d2057c449 [0x00007f8d2057c2c0+0x189]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        fel.getAnalysis().clear();
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_ITEXT.getKey()), Analysis.WARN_ITEXT + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_ITEXT.getKey()),
                Analysis.INFO_ITEXT + " analysis incorrectly identified.");
    }

    @Test
    void testItextIoStubRoutinesFileChannelRandomAccessSource() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset43.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ITEXT_IO.getKey()),
                Analysis.ERROR_ITEXT_IO + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testItextIoStubRoutinesJbyteDisjointArrayCopyIndependentRandomAccessSource() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset17.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ITEXT_IO.getKey()),
                Analysis.ERROR_ITEXT_IO + " analysis not identified.");
    }

    @Test
    void testItextIoStubRoutinesJbyteDisjointArrayCopyPagedChannelRandomAccessSource() {
        FatalErrorLog fel = new FatalErrorLog();
        String header = "# v  ~StubRoutines::jbyte_disjoint_arraycopy";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        String registerToMemoryMapping = " - klass: 'com/itextpdf/text/io/PagedChannelRandomAccessSource'";
        RegisterToMemoryMapping registerToMemoryMappingEvent = new RegisterToMemoryMapping(registerToMemoryMapping);
        fel.getRegisterToMemoryMappings().add(registerToMemoryMappingEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ITEXT_IO.getKey()),
                Analysis.ERROR_ITEXT_IO + " analysis not identified.");
    }

    @Test
    void testItextIoStubRoutinesJintDisjointArrayCopyRandomAccessFileOrArray() {
        FatalErrorLog fel = new FatalErrorLog();
        String header = "v  ~StubRoutines::jint_disjoint_arraycopy";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        String stack1 = "v  ~StubRoutines::jint_disjoint_arraycopy";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 23636 C2 java.nio.Bits.copyToArray(JLjava/lang/Object;JJJ)V (68 bytes) @ "
                + "0x00007f59389b1201 [0x00007f59389b11a0+0x61]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String stack3 = "J 25959 C2 com.itextpdf.text.pdf.RandomAccessFileOrArray.read([BII)I (97 bytes) @ "
                + "0x00007f5936737800 [0x00007f5936737500+0x300]";
        Stack stackEvent3 = new Stack(stack3);
        fel.getStacks().add(stackEvent3);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ITEXT_IO.getKey()),
                Analysis.ERROR_ITEXT_IO + " analysis not identified.");
    }

    @Test
    void testJavaSrSignum() {
        FatalErrorLog fel = new FatalErrorLog();
        String username = "_JAVA_SR_SIGNUM=30";
        EnvironmentVariable environmentVariablesEvent = new EnvironmentVariable(username);
        fel.getEnvironmentVariables().add(environmentVariablesEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JAVA_SR_SIGNO.getKey()),
                Analysis.INFO_JAVA_SR_SIGNO + " analysis not identified.");
    }

    @Test
    void testJdk11AutomaticLogFileRotationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xlog:gc*:file=/path/to/gc.log::filesize=0";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_0.getKey()),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_0 + " analysis not identified.");
    }

    @Test
    void testJdk11LogFileRotationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xlog:gc*:file=/path/to/gc.log::filecount=0,filesize=50M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_ROTATION_DISABLED.getKey()),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_ROTATION_DISABLED + " analysis not identified.");
    }

    @Test
    void testJdk11LogFileSizeSmall() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xlog:gc*:file=/path/to/gc.log::filecount=10,filesize=4M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_SMALL.getKey()),
                org.github.joa.util.Analysis.WARN_JDK11_GC_LOG_FILE_SIZE_SMALL + " analysis not identified.");
    }

    @Test
    void testJdk11PrintGCDetailsMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xlog:gc=debug:file=/path/to/gc-%t.log:time,pid,tid,level,"
                + "tags:filesize=1G";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING.getKey()),
                org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING + " analysis not identified.");
    }

    @Test
    void testJdk11PrintGCDetailsNotMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xlog:gc*:file=/path/to/gc.log:time,uptimemillis:filecount=5,"
                + "filesize=3M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.9+11-LTS) for linux-amd64 JRE (11.0.9+11-LTS), built "
                + "on Oct 15 2020 11:45:12 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("-Xlog:gc*:file=/path/to/gc.log:time,uptimemillis:filecount=5,filesize=3M",
                fel.getJvmOptions().getLog().get(0), "-Xlog not correct.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING.getKey()),
                org.github.joa.util.Analysis.INFO_JDK11_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    }

    @Test
    void testJdk7() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (24.51-b03) for linux-amd64 JRE (1.7.0_55-b13), "
                + "built on Apr  9 2014 12:07:12 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-4)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK_VERSION_UNSUPPORTED.getKey()),
                Analysis.ERROR_JDK_VERSION_UNSUPPORTED + " analysis not identified.");
    }

    @Test
    void testJdk8DeflaterContention() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset59.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        String stackFrameTopCompiledJavaCode = "J 3602  java.util.zip.Deflater.deflateBytes(J[BIII)I (0 bytes) @ "
                + "0x00007f641d41accd [0x00007f641d41ac00+0xcd]";
        assertEquals(stackFrameTopCompiledJavaCode, fel.getStackFrameTopCompiledJavaCode(),
                "Top compiled Java code (J) stack frame not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_DEFLATER_CONTENTION.getKey()),
                Analysis.ERROR_JDK8_DEFLATER_CONTENTION + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testJdk8LogFileRotationNotEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xloggc:gc.log";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(
                fel.getJvmOptions()
                        .hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED.getKey()),
                org.github.joa.util.Analysis.WARN_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED + " analysis not identified.");
    }

    @Test
    void testJdk8PrintGCDetailsMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xloggc:gc.log  -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_PRINT_GC_DETAILS_MISSING.getKey()),
                org.github.joa.util.Analysis.WARN_JDK8_PRINT_GC_DETAILS_MISSING + " analysis not identified.");
    }

    @Test
    void testJdk8PrintGCDetailsMissingGcLogging() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_PRINT_GC_DETAILS_MISSING.getKey()),
                org.github.joa.util.Analysis.WARN_JDK8_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    }

    @Test
    void testJdk8PrintGCDetailsMissingNoGcLogging() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_JDK8_PRINT_GC_DETAILS_MISSING.getKey()),
                org.github.joa.util.Analysis.WARN_JDK8_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.");
    }

    @Test
    void testJdk8ZipFileContentionGetEntry() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset23.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        // "MGM was here!" used for testing purposes
        assertEquals(1, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        String stackFrameTopCompiledJavaCode = "J 302  java.util.zip.ZipFile.getEntry(J[BZ)J (0 bytes) @ "
                + "0x00007fa287303dce [0x00007fa287303d00+0xce]";
        assertEquals(stackFrameTopCompiledJavaCode, fel.getStackFrameTopCompiledJavaCode(),
                "Top compiled Java code (J) stack frame not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION.getKey()),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testJdk8ZipFileContentionReadCen() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.342-b07) for linux-amd64 JRE (1.8.0_342-b07), built on "
                + "Jul 18 2022 23:53:30 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String stack = "C  [libzip.so+0x4c59]  readCEN+0x8e9";
        Stack stackEvent = new Stack(stack);
        fel.getStacks().add(stackEvent);
        fel.doAnalysis();
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION.getKey()),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
    }

    @Test
    void testJdk8ZipFileContentionReadCenHeaderOnly() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# JRE version: OpenJDK Runtime Environment (8.0_342-b07) (build 1.8.0_342-b07)";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# C  [libzip.so+0x4c59]  readCEN+0x8e9";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        fel.doAnalysis();
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION.getKey()),
                Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.");
    }

    @Test
    void testJdkDebugSymbolsMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset18.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_LINUX_ZIP.getKey()),
                Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_DEBUG_SYMBOLS.getKey()),
                Analysis.WARN_DEBUG_SYMBOLS + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_JDK_ANCIENT.getKey()),
                Analysis.INFO_JDK_ANCIENT + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILED_JAVA_CODE.getKey()),
                Analysis.ERROR_COMPILED_JAVA_CODE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testJdkUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: test";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK_VERSION_UNKNOWN.getKey()),
                Analysis.ERROR_JDK_VERSION_UNKNOWN + " analysis not identified.");
    }

    @Test
    void testJffi() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "3ffe9c800000-3ffe9c820000 r-xp 00000000 fd:00 1107498958                 "
                + "/path/to/jffi3667428567419554714.so (deleted)";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JFFI.getKey()), Analysis.INFO_JFFI + " analysis not identified.");
    }

    @Test
    void testJfr() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset52.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_JFR.getKey()),
                org.github.joa.util.Analysis.INFO_JFR + " analysis not identified.");
    }

    @Test
    void testJfrClassTransformed() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [libjvm.so+0x6b67f7]  JfrEventClassTransformer::on_klass_creation(InstanceKlass*&, "
                + "ClassFileParser&, Thread*)+0xa17";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String logline = "7f55dbe9a000-7f55dcc26000 r-xp 00000000 08:05 34105880                   "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-1.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), "
                + "built on Jul 12 2020 18:53:50 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED.getKey()),
                Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED + " analysis not identified.");
    }

    @Test
    void testJfrPdGetTopFrame() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset52.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JFR_PD_GET_TOP_FRAME.getKey()),
                Analysis.ERROR_JFR_PD_GET_TOP_FRAME + " analysis not identified.");
    }

    @Test
    void testJliLaunchStackSize() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss256k -XX:TargetSurvivorRatio=90 -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String stack1 = "Stack: [0x00007fff14ecc000,0x00007fff156ca000],  sp=0x00007fff156c50c0,  free space=8164k";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "C  [libjli.so+0x877f]  JLI_Launch+0x15bf";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String currentThread = "Current thread (0x000055c487db2800):  JavaThread \"Unknown thread\" [_thread_in_vm, "
                + "id=11, stack(0x00007fff14ecc000,0x00007fff156ca000)]";
        CurrentThread currentThreadEvent = new CurrentThread(currentThread);
        fel.setCurrentThread(currentThreadEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.INFO_STACK_FREESPACE_GT_STACK_SIZE.getKey()),
                Analysis.INFO_STACK_FREESPACE_GT_STACK_SIZE + " analysis incorrectly identified.");
    }

    @Test
    void testJna() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "7f99774f8000-7f99775f7000 ---p 00017000 00:27 165351280                  "
                + "/tmp/jna-100343/jna17878442429968131541.tmp (deleted)";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JNA.getKey()), Analysis.INFO_JNA + " analysis not identified.");
    }

    @Test
    void testJnaFfiPrepClosureLoc() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset84.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(Application.CASSANDRA, fel.getApplication(), "Application not correct.");
        assertTrue(fel.isJnaCrash(), "JNA crash not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JNA_FFI_PREP_CLOSURE_LOC.getKey()),
                Analysis.ERROR_JNA_FFI_PREP_CLOSURE_LOC + " analysis not identified.");
        assertEquals(29, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(1, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testJnaInvolvedRedHatJdk() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  [libcrypt.so+0x1825]";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 14265  com.sun.jna.Native.invokePointer(Lcom/sun/jna/Function;JI[Ljava/lang/Object;)J "
                + "(0 bytes) @ 0x00007fb6f9855969 [0x00007fb6f9855900+0x69]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String logline = "7f7dc59c6000-7f7dc673b000 r-xp 00000000 fd:01 17006104                   "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os = "OS:Red Hat Enterprise Linux release 8.3 (Ootpa)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.282-b08) for linux-amd64 JRE (1.8.0_282-b08), "
                + "built on Jan 17 2021 16:21:17 by \"mockbuild\" with gcc 8.3.1 20191121 (Red Hat 8.3.1-5)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.isJnaCrash(), "JNA crash not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JNA_RH.getKey()),
                Analysis.ERROR_JNA_RH + " analysis not identified.");
    }

    @Test
    void testJnaRedHatJdk() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset22.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        String stackFrame1 = "C  [jna8588255081773605818.tmp+0x13c480]  CkMultiByteBase::nextIdx()+0x10";
        String stackFrame2 = "j  com.sun.jna.Native.invokePointer(Lcom/sun/jna/Function;JI[Ljava/lang/Object;)J+0";
        assertEquals(stackFrame1, fel.getStackFrame(1), "Stack frame 1 not correct.");
        assertEquals(stackFrame2, fel.getStackFrame(2), "Stack frame 2 not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JNA_RH.getKey()),
                Analysis.ERROR_JNA_RH + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testJssCrash() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  [libc.so.6+0x36e5b]  __memcpy_sse2_unaligned_erms+0x1b";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 13417  org.mozilla.jss.nss.PR.Shutdown(Lorg/mozilla/jss/nss/PRFDProxy;I)I (0 bytes) "
                + "@ 0x00007f47ea93da92 [0x00007f47ea93da40+0x52]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JSS.getKey()), Analysis.ERROR_JSS + " analysis not identified.");
    }

    @Test
    void testJssDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f47d6b82000-7f47d6bc6000 r-xp 00000000 fd:00 201485134                  "
                + "/usr/lib64/jss/libjss4.so";
        DynamicLibrary event = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JSS.getKey()), Analysis.INFO_JSS + " analysis not identified.");
    }

    @Test
    void testJssInStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "J  12345 com.example.MyClass";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 13417  org.mozilla.jss.nss.PR.Shutdown(Lorg/mozilla/jss/nss/PRFDProxy;I)I (0 bytes) "
                + "@ 0x00007f47ea93da92 [0x00007f47ea93da40+0x52]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_JSS.getKey()), Analysis.WARN_JSS + " analysis not identified.");
    }

    @Test
    void testJvmUserNeUsername() {
        FatalErrorLog fel = new FatalErrorLog();
        String username = "USERNAME=user1";
        EnvironmentVariable environmentVariablesEvent = new EnvironmentVariable(username);
        fel.getEnvironmentVariables().add(environmentVariablesEvent);
        String hsperfdata = "7ff0f61d2000-7ff0f61da000 rw-s 00000000 fd:01 33563495                   "
                + "/tmp/hsperfdata_user2/92333";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(hsperfdata);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JVM_USER_NE_USERNAME.getKey()),
                Analysis.INFO_JVM_USER_NE_USERNAME + " analysis not identified.");
    }

    @Test
    void testJvmYesOsNoUseLargePages() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G -XX:+UseLargePages";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LARGE_PAGES_HUGETLBFS_EXPLICIT_JVM_YES_OS_NO.getKey()),
                Analysis.ERROR_LARGE_PAGES_HUGETLBFS_EXPLICIT_JVM_YES_OS_NO + " analysis not identified.");
    }

    @Test
    void testLargePagesConsiderThpOsAlwaysJdkVersionUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String transparentHugepageEnabled = "[always] madvise never";
        TransparentHugepageEnabled transparentHugePageEnabledEvent = new TransparentHugepageEnabled(
                transparentHugepageEnabled);
        fel.getTransparentHugepageEnableds().add(transparentHugePageEnabledEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGES_CONSIDER.getKey()),
                org.github.joa.util.Analysis.INFO_LARGE_PAGES_CONSIDER + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_LARGE_PAGES_CONSIDER_THP_OS_ALWAYS.getKey()),
                Analysis.INFO_LARGE_PAGES_CONSIDER_THP_OS_ALWAYS + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_THP_OS_ALWAYS.getKey()),
                Analysis.WARN_THP_OS_ALWAYS + " analysis not identified.");
    }

    @Test
    void testLargePagesJvmYesOsNoMetaspace() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G -XX:+UseLargePagesInMetaspace";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LARGE_PAGES_HUGETLBFS_EXPLICIT_JVM_YES_OS_NO.getKey()),
                Analysis.ERROR_LARGE_PAGES_HUGETLBFS_EXPLICIT_JVM_YES_OS_NO + " analysis incorrectly identified.");
    }

    @Test
    void testLargePagesLargePageSizeInBytesLinux() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -XX:+UseLargePages -XX:LargePageSizeInBytes=4m";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertTrue(
                fel.hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGES_LARGE_PAGE_SIZE_IN_BYTES_LINUX.getKey()),
                org.github.joa.util.Analysis.INFO_LARGE_PAGES_LARGE_PAGE_SIZE_IN_BYTES_LINUX
                        + " analysis not identified.");
    }

    @Test
    void testLargePagesLargePageSizeInBytesWindows() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx33g -XX:+UseLargePages -XX:LargePageSizeInBytes=4m";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String os = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.3630)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertTrue(
                fel.hasAnalysis(
                        org.github.joa.util.Analysis.INFO_LARGE_PAGES_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS.getKey()),
                org.github.joa.util.Analysis.INFO_LARGE_PAGES_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS
                        + " analysis not identified.");
    }

    @Test
    void testLargePagesOsYesJvmNoMeminfo() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String meminfo = "Hugetlb:         4194304 kB";
        Meminfo meminfoEvent = new Meminfo(meminfo);
        fel.getMeminfos().add(meminfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_EXPLICIT_HUGE_PAGES_OS_YES_JVM_NO.getKey()),
                Analysis.WARN_EXPLICIT_HUGE_PAGES_OS_YES_JVM_NO + " analysis not identified.");
    }

    @Test
    void testLargePagesThpJvmYesOsAlways() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G -XX:+UseTransparentHugePages";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String transparentHugepageEnabled = "[always] madvise never";
        TransparentHugepageEnabled transparentHugePageEnabledEvent = new TransparentHugepageEnabled(
                transparentHugepageEnabled);
        fel.getTransparentHugepageEnableds().add(transparentHugePageEnabledEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LARGE_PAGES_THP_JVM_YES_OS_ALWAYS.getKey()),
                Analysis.ERROR_LARGE_PAGES_THP_JVM_YES_OS_ALWAYS + " analysis not identified.");
    }

    @Test
    void testLargePagesThpJvmYesOsMadvise() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G -XX:+UseTransparentHugePages";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String transparentHugepageEnabled = "always [madvise] never";
        TransparentHugepageEnabled transparentHugePageEnabledEvent = new TransparentHugepageEnabled(
                transparentHugepageEnabled);
        fel.getTransparentHugepageEnableds().add(transparentHugePageEnabledEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_LARGE_PAGES_THP_JVM_YES_OS_MADVISE.getKey()),
                Analysis.INFO_LARGE_PAGES_THP_JVM_YES_OS_MADVISE + " analysis not identified.");
    }

    @Test
    void testLargePagesThpJvmYesOsNever() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G -XX:+UseTransparentHugePages";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String transparentHugepageEnabled = "always madvise [never]";
        TransparentHugepageEnabled transparentHugePageEnabledEvent = new TransparentHugepageEnabled(
                transparentHugepageEnabled);
        fel.getTransparentHugepageEnableds().add(transparentHugePageEnabledEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LARGE_PAGES_THP_JVM_YES_OS_NEVER.getKey()),
                Analysis.ERROR_LARGE_PAGES_THP_JVM_YES_OS_NEVER + " analysis not identified.");
    }

    @Test
    void testLargePagesThpJvmYesOsUndetermined() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx10G -XX:+UseTransparentHugePages";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_LARGE_PAGES_THP_JVM_YES_OS_UNDETERMINED.getKey()),
                Analysis.INFO_LARGE_PAGES_THP_JVM_YES_OS_UNDETERMINED + " analysis not identified.");
    }

    @Test
    void testLatestRelease() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset14.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LATEST.getKey()),
                Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.");
        assertEquals(1278, KrashUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)),
                "Release days diff not correct.");
        assertEquals(16, JdkUtil.getLatestJdkReleaseNumber(fel) - JdkUtil.getJdkReleaseNumber(fel),
                "Release # diff not correct.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testLdPreloadFile() {
        String logLine = "/etc/ld.so.preload:";
        LdPreloadFile event = new LdPreloadFile(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getLdPreloadFiles().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_LD_SO_PRELOAD.getKey()),
                Analysis.INFO_LD_SO_PRELOAD + " analysis not identified.");
    }

    @Test
    void testLibaioContextDone() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset46.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LIBAIO_CONTEXT_DONE.getKey()),
                Analysis.ERROR_LIBAIO_CONTEXT_DONE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testLinkageError() {
        String logLine = "LinkageErrors=5276";
        ExceptionCounts event = new ExceptionCounts(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCounts().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_LINKAGE.getKey()),
                Analysis.ERROR_LINKAGE + " analysis not identified.");
    }

    @Test
    void testLuceneInStackSlotToMemoryMapping() {
        FatalErrorLog fel = new FatalErrorLog();
        String stackSlotToMemoryMapping = "stack at sp + 2 slots: 0x00000007abcf3808 is an oop: org.apache.lucene."
                + "store.BufferedChecksumIndexInput";
        StackSlotToMemoryMapping stackSlotToMemoryMappingEvent = new StackSlotToMemoryMapping(stackSlotToMemoryMapping);
        fel.getStackSlotToMemoryMappings().add(stackSlotToMemoryMappingEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_LUCENE.getKey()), Analysis.WARN_LUCENE + " analysis not identified.");
    }

    @Test
    void testMaxMapCountLimit() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "Red Hat Enterprise Linux release 8.5 (Ootpa)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String maxMapCount = "/proc/sys/vm/max_map_count (maximum number of memory map areas a process may have): 100";
        MaxMapCount maxMapCountEvent = new MaxMapCount(maxMapCount);
        fel.setMaxMapCount(maxMapCountEvent);
        fel.setDynamicLibrariesMappingCount(99);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_MAX_MAP_COUNT_RLIMIT.getKey()),
                Analysis.WARN_MAX_MAP_COUNT_RLIMIT + " analysis not identified.");
        assertEquals(
                "The number of memory map areas (99) in the Dynamic Libraries section is within 1% of the "
                        + "max_map_count limit (100).",
                fel.getAnalysisLiteral(Analysis.WARN_MAX_MAP_COUNT_RLIMIT.getKey()),
                Analysis.WARN_MAX_MAP_COUNT_RLIMIT + " not correct.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE.getKey()),
                Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE + " analysis incorrectly identified.");
    }

    @Test
    void testMaxMapCountLimitPossible() {
        FatalErrorLog fel = new FatalErrorLog();
        fel.setDynamicLibrariesMappingCount(65529);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE.getKey()),
                Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE + " analysis not identified.");
        assertEquals(
                "The number of memory map areas (65529) in the Dynamic Libraries section is very close to the default "
                        + "max_map_count limit (65530).",
                fel.getAnalysisLiteral(Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE.getKey()),
                Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE + " not correct.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_MAX_MAP_COUNT_RLIMIT.getKey()),
                Analysis.WARN_MAX_MAP_COUNT_RLIMIT + " analysis incorrectly identified.");
    }

    @Test
    void testMaxRamLimit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:MaxRAMPercentage=80";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String memory = "Memory: 4k page, physical 134217729k(92429972k free), swap 0k(0k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.20+8-LTS) for linux-amd64 JRE (11.0.20+8-LTS), built "
                + "on Jul 15 2023 00:41:55 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-18)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_MAX_RAM_LIMIT.getKey()),
                Analysis.WARN_MAX_RAM_LIMIT + " analysis not identified.");
    }

    @Test
    void testMaxRamLimitJdk17() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:MaxRAMPercentage=80";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String memory = "Memory: 4k page, physical 134217729k(92429972k free), swap 0k(0k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.8+7-LTS) for linux-amd64 JRE (17.0.8+7-LTS), built on "
                + "Jul 14 2023 15:48:52 by \"mockbuild\" with gcc 8.3.1 20190311 (Red Hat 8.3.1-3)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_MAX_RAM_LIMIT.getKey()),
                Analysis.WARN_MAX_RAM_LIMIT + " analysis incorrectly identified.");
    }

    @Test
    void testMaxRamLimitMaxHeap() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:MaxRAMPercentage=80 -Xmx256g";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String memory = "Memory: 4k page, physical 134217729k(92429972k free), swap 0k(0k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.20+8-LTS) for linux-amd64 JRE (11.0.20+8-LTS), built "
                + "on Jul 15 2023 00:41:55 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-18)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        jvm_args = "jvm_args: -XX:MaxRAMPercentage=80 -Xmx256g";
        event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_MAX_RAM_LIMIT.getKey()),
                Analysis.WARN_MAX_RAM_LIMIT + " analysis incorrectly identified.");
    }

    @Test
    void testMaxRamLimitMaxRam() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:MaxRAMPercentage=80 -XX:MaxRAM=134217729k";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String memory = "Memory: 4k page, physical 134217729k(92429972k free), swap 0k(0k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.20+8-LTS) for linux-amd64 JRE (11.0.20+8-LTS), built "
                + "on Jul 15 2023 00:41:55 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-18)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_MAX_RAM_LIMIT.getKey()),
                Analysis.WARN_MAX_RAM_LIMIT + " analysis incorrectly identified.");
    }

    @Test
    void testMetadataOnStackMark() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset73.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK.getKey()),
                Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK + " analysis not identified.");
    }

    @Test
    void testMicrosoftSqlServerNativeDriverDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "0x00007fff77490000 - 0x00007fff774de000         "
                + "C:\\Windows\\System32\\mssql-jdbc_auth-8.2.2.x64.dll";
        DynamicLibrary event = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_MICROSOFT_SQL_SERVER_NATIVE.getKey()),
                Analysis.INFO_MICROSOFT_SQL_SERVER_NATIVE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testMmapDeleted() {
        FatalErrorLog fel = new FatalErrorLog();
        String library = "7ca8cf3d6000-7ca8cfdd6000 rw-s 00000000 fd:00 1074566196                 "
                + "/var/lib/kafka/data/kafka-log0/something/00000000000002627674.index.deleted (deleted)";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_MMAP_DELETED.getKey()),
                Analysis.WARN_MMAP_DELETED + " analysis not identified.");
    }

    @Test
    void testModuleEntryPurgeAllModuleReads() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [jvm.dll+0x5ab390]  ModuleEntryTable::purge_all_module_reads+0x140  (moduleentry.cpp:444)";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "V  [jvm.dll+0x1ce246]  ClassLoaderDataGraph::do_unloading+0x1c6  (classloaderdata.cpp:1435)";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String logline = "0x00007ffdd2230000 - 0x00007ffdd2d76000         "
                + "D:\\Java\\jdk11.0.7.10\\bin\\server\\jvm.dll";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os = "OS: Windows Server 2016 , 64 bit Build 14393 (10.0.14393.4651)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.7+10-LTS) for windows-amd64 JRE (11.0.7+10-LTS), "
                + "built on Apr  9 2020 00:20:14 by \"\" with MS VC++ 15.9 (VS2017)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS.getKey()),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_JVM_DLL.getKey()),
                Analysis.ERROR_JVM_DLL + " analysis incorrectly identified.");
    }

    @Test
    void testModuleEntryPurgeReads() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [libjvm.so+0xbfb228]  ModuleEntry::purge_reads()+0x118";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "V  [libjvm.so+0xbfb338]  ModuleEntryTable::purge_all_module_reads()+0x38";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String logline = "7f03a6cbf000-7f03a7ef9000 r-xp 00000000 fd:00 1180422                    "
                + "/usr/lib/jvm/java-11-openjdk-11.0.12.0.7-0.el7_9.x86_64/lib/server/libjvm.so";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.12+7-LTS) for linux-amd64 JRE (11.0.12+7-LTS), built "
                + "on Jul 14 2021 00:06:01 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS.getKey()),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_JVM_DLL.getKey()),
                Analysis.ERROR_JVM_DLL + " analysis incorrectly identified.");
    }

    @Test
    void testModuleEntryPurgeReadsPossible() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset82.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS.getKey()),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_MODULE_ENTRY_PURGE_READS_POSSIBLE.getKey()),
                Analysis.ERROR_MODULE_ENTRY_PURGE_READS_POSSIBLE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_JVM_DLL.getKey()),
                Analysis.ERROR_JVM_DLL + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_POINTER_INVALID.getKey()),
                Analysis.ERROR_POINTER_INVALID + " analysis not identified.");
        assertEquals(79, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(1, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testNativeLibraryJBoss() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f8a71051000-7f8a71052000 rw-p 00004000 08:01 1852476                    "
                + "/path/to/libartemis-native-64.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_NATIVE_LIBRARIES_JBOSS.getKey()),
                Analysis.INFO_NATIVE_LIBRARIES_JBOSS + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN.getKey()),
                Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN + " analysis incorrectly identified.");
    }

    @Test
    void testNativeLibraryUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f8a71051000-7f8a71052000 rw-p 00004000 08:01 1852476                    "
                + "/path/to/mgm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN.getKey()),
                Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_NATIVE_LIBRARIES_JBOSS.getKey()),
                Analysis.INFO_NATIVE_LIBRARIES_JBOSS + " analysis incorrectly identified.");
    }

    @Test
    void testNfs() {
        FatalErrorLog fel = new FatalErrorLog();
        String logline = "7f5f66892000-7f5f6757a000 r-xp 00000000 00:38 1062721                    "
                + "/tools/java/jdk1.8.0_201/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_STORAGE_NFS.getKey()),
                Analysis.INFO_STORAGE_NFS + " analysis not identified.");
    }

    @Test
    void testNoMemoryEvent() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_SWAP_DISABLED.getKey()),
                Analysis.INFO_SWAP_DISABLED + " analysis incorrectly identified.");
    }

    @Test
    void testNoStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset49.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_STACK_NO_VM_CODE.getKey()),
                Analysis.INFO_STACK_NO_VM_CODE + " analysis incorrectly identified.");
    }

    @Test
    void testNotLts() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset16.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(JavaSpecification.JDK12, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LTS.getKey()),
                Analysis.WARN_JDK_NOT_LTS + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testNullPointer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset51.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_POINTER_NULL.getKey()),
                Analysis.ERROR_POINTER_NULL + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOnOutOfMemoryErrorKillJdk8u101() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:OnOutOfMemoryError=\"kill -9 %p\" -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String header = "# JRE version: Java(TM) SE Runtime Environment (8.0_101-b13) (build 1.8.0_101-b13)";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME.getKey()),
                org.github.joa.util.Analysis.INFO_ON_OOME + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME_KILL.getKey()),
                org.github.joa.util.Analysis.INFO_ON_OOME_KILL + " analysis not identified.");
    }

    @Test
    void testOnOutOfMemoryErrorKillJdk8u91() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:OnOutOfMemoryError=\"kill -9 %p\" -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        String header = "# JRE version: Java(TM) SE Runtime Environment (8.0_91-b14) (build 1.8.0_91-b14)";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME.getKey()),
                org.github.joa.util.Analysis.INFO_ON_OOME + " analysis not identified.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_ON_OOME_KILL.getKey()),
                org.github.joa.util.Analysis.INFO_ON_OOME_KILL + " analysis incorrectly identified.");
    }

    @Test
    void testOomAmqCli() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset67.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_AMQ_CLI.getKey()),
                Analysis.ERROR_OOME_AMQ_CLI + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_OOM_G1.getKey()),
                Analysis.WARN_OOM_G1 + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomCompilerThreadC2SslDecode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset79.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE.getKey()),
                Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL.getKey()),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomCompilerThreadWindows() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset80.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_WLIMIT_PAGE_FILE.getKey()),
                Analysis.ERROR_OOME_WLIMIT_PAGE_FILE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_SWAPPING.getKey()),
                Analysis.WARN_SWAPPING + " analysis incorrectly identified.");
    }

    @Test
    void testOomeCompressedOops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset42.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.isMemoryAllocationFail(), "Memory allocation failure not identified.");
        assertFalse(fel.isCrashOnStartup(), "Crash on startup incorrectly identified.");
        assertEquals(Long.MIN_VALUE, fel.getMemoryAllocation(), "Memory allocation not correct.");
        assertEquals(32593993728L, fel.getJvmMemFree(), "JVM reported free memory not correct.");
        assertEquals(Long.MIN_VALUE, fel.getJvmSwapFree(), "JVM reported swap not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_RLIMIT_OOPS.getKey()),
                Analysis.ERROR_OOME_RLIMIT_OOPS + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    void testOomeJavaHeap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset36.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_JAVA_HEAP.getKey()),
                Analysis.ERROR_OOME_THROWN_JAVA_HEAP + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGNO_SIGSEGV.getKey()),
                Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_SEGV_MAPERR.getKey()),
                Analysis.INFO_SIGCODE_SEGV_MAPERR + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomePhysicalMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset29.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.isError("Out of Memory Error"), "Out Of Memory Error not identified.");
        long physicalMemory = JdkUtil.convertSize(24609684, 'K', 'B');
        assertEquals(physicalMemory, fel.getJvmMemTotal(), "Physical memory not correct.");
        long heapInitial = JdkUtil.convertSize(1303, 'M', 'B');
        assertEquals(heapInitial, fel.getHeapInitialSize(), "Heap initial size not correct.");
        long heapMax = JdkUtil.convertSize(16000, 'M', 'B');
        assertEquals(heapMax, fel.getHeapMaxSize(), "Heap max size not correct.");
        long metaspaceMax = JdkUtil.convertSize(1148928, 'K', 'B');
        assertEquals(metaspaceMax, fel.getMetaspaceMaxSize(), "Metaspace max size not correct.");
        long directMemoryMax = JdkUtil.convertSize(0, 'G', 'B');
        assertEquals(directMemoryMax, fel.getDirectMemoryMaxSize(), "Direct Memory mx not correct.");
        assertEquals(1024, fel.getThreadStackSize(), "Thread stack size not correct.");
        assertEquals(55, fel.getJavaThreadCount(), "Thread count not correct.");
        long threadMemory = JdkUtil.convertSize(1024 * 55, 'K', 'B');
        assertEquals(threadMemory, fel.getThreadStackMemory(), "Thread memory not correct.");
        long codeCacheSize = JdkUtil.convertSize(420, 'M', 'B');
        assertEquals(codeCacheSize, fel.getCodeCacheSize(), "Code cache size not correct.");
        assertEquals(heapMax + metaspaceMax + directMemoryMax + threadMemory + codeCacheSize, fel.getJvmMemoryMax(),
                "Jvm memory max not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL.getKey()),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomeThreadLeakEapExecutorPool() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset83.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(Application.WILDFLY, fel.getApplication(), "Application not correct.");
        assertEquals(8304, fel.getJavaThreadCount(JdkRegEx.WILDFLY_EXECUTOR_POOL_THREAD),
                "JBoss EAP executor pool thread count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native libraries unknown count not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL.getKey()),
                Analysis.ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_THREAD_LEAK.getKey()),
                Analysis.ERROR_OOME_THREAD_LEAK + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP.getKey()),
                Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");

    }

    @Test
    void testOomExternalStartupSwap() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# There is insufficient memory for the Java Runtime Environment to continue.";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# Native memory allocation (mmap) failed to map 2147483648 bytes for committing reserved "
                + "memory.";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        String header3 = "#  Out of Memory Error (os_linux.cpp:2749), pid=123456, tid=0x00007f544c72a700";
        Header headerEvent3 = new Header(header3);
        fel.getHeaders().add(headerEvent3);
        String os = "OS: Red Hat Enterprise Linux release 8.5 (Ootpa)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String timeElapsedTime = "Time: Mon Dec 11 11:15:36 2023 EST elapsed time: 0.021790 seconds (0d 0h 0m 0s)";
        TimeElapsedTime timeElapsedTimeEvent = new TimeElapsedTime(timeElapsedTime);
        fel.setTimeElapsedTime(timeElapsedTimeEvent);
        String heap1 = "Heap:";
        Heap heapEvent1 = new Heap(heap1);
        fel.getHeaps().add(heapEvent1);
        String heap2 = " garbage-first heap   total 0K, used 0K [0x0000000680000000, 0x0000000800000000)";
        Heap heapEvent2 = new Heap(heap2);
        fel.getHeaps().add(heapEvent2);
        String meminfo1 = "MemTotal:        7881824 kB";
        Meminfo meminfoEvent1 = new Meminfo(meminfo1);
        fel.getMeminfos().add(meminfoEvent1);
        String meminfo2 = "MemFree:          190220 kB";
        Meminfo meminfoEvent2 = new Meminfo(meminfo2);
        fel.getMeminfos().add(meminfoEvent2);
        String meminfo3 = "MemAvailable:     273300 kB";
        Meminfo meminfoEvent3 = new Meminfo(meminfo3);
        fel.getMeminfos().add(meminfoEvent3);
        String meminfo4 = "CommitLimit:    12149292 kB";
        Meminfo meminfoEvent4 = new Meminfo(meminfo4);
        fel.getMeminfos().add(meminfoEvent4);
        String meminfo5 = "Committed_AS:    1798952 kB";
        Meminfo meminfoEvent5 = new Meminfo(meminfo5);
        fel.getMeminfos().add(meminfoEvent5);
        String memory = "Memory: 4k page, physical 7881824k(190220k free), swap 8208380k(520828k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        fel.doAnalysis();
        assertTrue(fel.isMemoryAllocationFail(), "Memory allocation failure not identified.");
        assertTrue(fel.isCrashOnStartup(), "Crash on startup not identified.");
        assertEquals(2147483648L, fel.getMemoryAllocation(), "Memory allocation not correct.");
        assertEquals(194785280L, fel.getJvmMemFree(), "JVM reported free memory not correct.");
        assertEquals(533327872L, fel.getJvmSwapFree(), "JVM reported swap not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_EXTERNAL_STARTUP.getKey()),
                Analysis.ERROR_OOME_EXTERNAL_STARTUP + " analysis not identified.");
    }

    @Test
    void testOomExternalStartupSwapDisabled() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset55.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_EXTERNAL_STARTUP.getKey()),
                Analysis.ERROR_OOME_EXTERNAL_STARTUP + " analysis not identified.");
    }

    @Test
    void testOomJBossVersion() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset71.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(JavaVendor.AZUL, fel.getJavaVendor(), "Java vendor not correct.");
        assertEquals(Application.JBOSS_VERSION, fel.getApplication(), "Application not correct.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_JBOSS_VERSION.getKey()),
                Analysis.ERROR_OOME_JBOSS_VERSION + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_OOM_G1.getKey()),
                Analysis.WARN_OOM_G1 + " analysis incorrectly identified.");
    }

    @Test
    void testOomLibJvm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset61.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        StringBuilder error = new StringBuilder();
        error.append("# There is insufficient memory for the Java Runtime Environment to continue.");
        error.append(Constants.LINE_SEPARATOR);
        error.append(
                "# Native memory allocation (mmap) failed to map 32304529408 bytes for committing reserved memory.");
        error.append(Constants.LINE_SEPARATOR);
        error.append("#  INVALID (0xe0000002) at pc=0x0000000000000000, pid=108047, tid=0x00007f67eb450700");
        assertEquals(error.toString(), fel.getError());
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP.getKey()),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP.getKey()),
                Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomLimit1() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset57.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_RLIMIT.getKey()),
                Analysis.ERROR_OOME_RLIMIT + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomLimit2() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# There is insufficient memory for the Java Runtime Environment to continue.";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# Native memory allocation (mmap) failed to map 12288 bytes for committing reserved memory.";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        String header3 = "#  Out of Memory Error (os_linux.cpp:2749), pid=115777, tid=0x00007f544c72a700";
        Header headerEvent3 = new Header(header3);
        fel.getHeaders().add(headerEvent3);
        String heap1 = "Heap:";
        Heap heapEvent1 = new Heap(heap1);
        fel.getHeaps().add(heapEvent1);
        String heap2 = " garbage-first heap   total 33120256K, used 20387191K [0x00007f5cb7800000, 0x00007f5cb8007e58, "
                + "0x00007f64b7800000)";
        Heap heapEvent2 = new Heap(heap2);
        fel.getHeaps().add(heapEvent2);
        String heap3 = "  region size 8192K, 1110 young (9093120K), 88 survivors (720896K)";
        Heap heapEvent3 = new Heap(heap3);
        fel.getHeaps().add(heapEvent3);
        String heap4 = " Metaspace       used 901044K, capacity 1070727K, committed 1070848K, reserved 1071104K";
        Heap heapEvent4 = new Heap(heap4);
        fel.getHeaps().add(heapEvent4);
        String meminfo1 = "MemTotal:       65803904 kB";
        Meminfo meminfoEvent1 = new Meminfo(meminfo1);
        fel.getMeminfos().add(meminfoEvent1);
        String meminfo2 = "MemFree:        18818296 kB";
        Meminfo meminfoEvent2 = new Meminfo(meminfo2);
        fel.getMeminfos().add(meminfoEvent2);
        String meminfo3 = "MemAvailable:   25213784 kB";
        Meminfo meminfoEvent3 = new Meminfo(meminfo3);
        fel.getMeminfos().add(meminfoEvent3);
        String meminfo4 = "CommitLimit:    39193404 kB";
        Meminfo meminfoEvent4 = new Meminfo(meminfo4);
        fel.getMeminfos().add(meminfoEvent4);
        String memory = "Memory: 4k page, physical 65803904k(18818296k free), swap 6291452k(6291452k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        fel.doAnalysis();
        assertTrue(fel.isMemoryAllocationFail(), "Memory allocation failure not identified.");
        assertFalse(fel.isCrashOnStartup(), "Crash on startup incorrectly identified.");
        assertEquals(12288, fel.getMemoryAllocation(), "Memory allocation not correct.");
        assertEquals(19269935104L, fel.getJvmMemFree(), "JVM reported free memory not correct.");
        assertEquals(6442446848L, fel.getJvmSwapFree(), "JVM reported swap not correct.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME.getKey()),
                Analysis.ERROR_OOME + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_RLIMIT.getKey()),
                Analysis.ERROR_OOME_RLIMIT + " analysis not identified.");
    }

    @Test
    void testOomStartupExternal() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset56.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_OPTS_NONE.getKey()),
                Analysis.INFO_OPTS_NONE + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_EXTERNAL_STARTUP.getKey()),
                Analysis.ERROR_OOME_EXTERNAL_STARTUP + " analysis not identified.");
    }

    @Test
    void testOomStartupOvercommit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset66.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP.getKey()),
                Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP.getKey()),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomStartupOvercommitLimit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset81.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP.getKey()),
                Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_JVM_STARTUP.getKey()),
                Analysis.ERROR_OOME_JVM_STARTUP + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX.getKey()),
                Analysis.INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX + " analysis not identified.");
        assertEquals(2, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomSwapDisabledG1() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset62.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_OOM_G1.getKey()), Analysis.WARN_OOM_G1 + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_SWAP_DISABLED_G1.getKey()),
                Analysis.WARN_SWAP_DISABLED_G1 + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomTomcatShutdown() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset68.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN.getKey()),
                Analysis.ERROR_OOME_TOMCAT_SHUTDOWN + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_OOM_G1.getKey()),
                Analysis.WARN_OOM_G1 + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOomTomcatShutdownStopStop() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset63.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN.getKey()),
                Analysis.ERROR_OOME_TOMCAT_SHUTDOWN + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_OOM_G1.getKey()),
                Analysis.WARN_OOM_G1 + " analysis incorrectly identified.");
        assertEquals(17, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testOracleEnterpriseLinux() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Oracle Linux Server release 7.9";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.392-b08) for linux-amd64 JRE (1.8.0_392-b08), built on "
                + "Oct 18 2023 15:26:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44.0.3)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String dynamicLibrary = "7f47f522b000-7f47f5fce000 r-xp 00000000 00:22 5274776                    "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.392.b08-2.el7_9.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT.getKey()),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
        assertTrue(fel.isOracleLinux(), "Oracle linux not identified.");
        assertEquals(JavaVendor.ORACLE, fel.getJavaVendor(), "Java vendor not correct.");
    }

    @Test
    void testOracleJdbcDriverNotTopFrame() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "V  [libjvm.so+0x290d84]  AccessInternal::PostRuntimeDispatch<G1BarrierSet::AccessBarrier"
                + "<548964ul, G1BarrierSet>, (AccessInternal::BarrierType)2, 548964ul>::oop_access_barrier(void*)+0x4";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "C  [libocijdbc11.so+0x458c]  Java_oracle_jdbc_driver_T2CConnection_t2cSetSessionTimeZone+0x5";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        String logline = "7f6e73a91000-7f6e74d08000 r-xp 00000000 fd:00 8632767                    "
                + "/usr/lib/jvm/java-17-openjdk-17.0.4.0.8-2.el8_6.x86_64/lib/server/libjvm.so";
        DynamicLibrary event = new DynamicLibrary(logline);
        fel.getDynamicLibraries().add(event);
        String os1 = "OS:";
        OsInfo osEvent1 = new OsInfo(os1);
        fel.getOsInfos().add(osEvent1);
        String os2 = "Red Hat Enterprise Linux release 8.6 (Ootpa)";
        OsInfo osEvent2 = new OsInfo(os2);
        fel.getOsInfos().add(osEvent2);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), "
                + "built on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER.getKey()),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION.getKey()),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis not identified.");
    }

    @Test
    void testOracleJdbcDriverOciClientInRegisterToMemoryMapping() {
        FatalErrorLog fel = new FatalErrorLog();
        String mapping = "RSI=0x00007f893ffec848: kpummgl+0x1728 in /opt/oraclient/latest/lib/libclntsh.so.19.1 at "
                + "0x00007f893be59000";
        RegisterToMemoryMapping registerToMemoryMappingEvent = new RegisterToMemoryMapping(mapping);
        fel.getRegisterToMemoryMappings().add(registerToMemoryMappingEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER.getKey()),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION.getKey()),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis not identified.");
    }

    @Test
    void testOracleJdbcJdkIncompatible() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built "
                + "on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String dynamicLibrary = "7fd01e1b3000-7fd01e1d3000 r-xp 00000000 fd:03 2100954                    "
                + "/ora01/app/oracle/product/11.2.0/client_1/lib/libocijdbc11.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_ORACLE_JDBC_OCI.getKey()),
                Analysis.INFO_ORACLE_JDBC_OCI + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_JDK_INCOMPATIBLE.getKey()),
                Analysis.ERROR_ORACLE_JDBC_JDK_INCOMPATIBLE + " analysis not identified.");
    }

    @Test
    void testOracleJdbcOciDriverError() {
        FatalErrorLog fel = new FatalErrorLog();
        Event eventEvent = new Event(
                "Event: 86.139 Loaded shared library /ora01/app/oracle/product/11.2.0/client_1/lib/libocijdbc11.so");
        fel.getEvents().add(eventEvent);
        TimeElapsedTime timeElapsedTimeEvent = new TimeElapsedTime(
                "Time: Mon Sep 12 13:46:23 2022 EDT elapsed time: 86.182142 seconds (0d 0h 1m 26s)");
        fel.setTimeElapsedTime(timeElapsedTimeEvent);
        assertEquals(86139L,
                fel.getEventTimestamp("^Event: (\\d{1,}\\.\\d{3}) Loaded shared library .+libocijdbc11.so$"),
                "OCI driver load timestamp not correct.");
        assertEquals(86182L, fel.getUptime(), "Uptime not correct.");
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER.getKey()),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING.getKey()),
                Analysis.ERROR_ORACLE_JDBC_OCI_LOADING + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION.getKey()),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis incorrectly identified.");
    }

    @Test
    void testOracleJdbcOciDriverWarning() {
        FatalErrorLog fel = new FatalErrorLog();
        Stack event1 = new Stack(
                "Stack: [0x00007f069a811000,0x00007f069a912000],  sp=0x00007f069a90d248,  free space=1008k");
        Stack event2 = new Stack(
                "Native frames: (J=compiled Java code, A=aot compiled Java code, j=interpreted, Vv=VM code, "
                        + "C=native code)");
        Stack event3 = new Stack(
                "V  [libjvm.so+0x5b3c94]  AccessInternal::PostRuntimeDispatch<G1BarrierSet::AccessBarrier<1097844ul, "
                        + "G1BarrierSet>, (AccessInternal::BarrierType)2, 1097844ul>::oop_access_barrier(void*)+0x4");
        Stack event4 = new Stack(
                "C  [libocijdbc11.so+0x458c]  Java_oracle_jdbc_driver_T2CConnection_t2cSetSessionTimeZone+0x5a");
        fel.getStacks().add(event1);
        fel.getStacks().add(event2);
        fel.getStacks().add(event3);
        fel.getStacks().add(event4);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER.getKey()),
                Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING.getKey()),
                Analysis.ERROR_ORACLE_JDBC_OCI_LOADING + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION.getKey()),
                Analysis.WARN_ORACLE_JDBC_OCI_CONNECION + " analysis not identified.");
    }

    @Test
    void testOracleJdbcOciNot() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built "
                + "on Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String dynamicLibrary = "7fd01e1b3000-7fd01e1d3000 r-xp 00000000 fd:03 2100954 /path/to/my.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.INFO_ORACLE_JDBC_OCI.getKey()),
                Analysis.INFO_ORACLE_JDBC_OCI + " analysis incorrectly identified.");
    }

    @Test
    void testOutOfMemoryErrorThrownCompressedClassSpace() {
        String logLine = "OutOfMemoryError class_metaspace_errors=7";
        ExceptionCounts event = new ExceptionCounts(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCounts().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE.getKey()),
                Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE + " analysis not identified.");
    }

    @Test
    void testOutOfMemoryErrorThrownJavaHeap() {
        String logLine = "OutOfMemoryError java_heap_errors=13";
        ExceptionCounts event = new ExceptionCounts(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCounts().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_JAVA_HEAP.getKey()),
                Analysis.ERROR_OOME_THROWN_JAVA_HEAP + " analysis not identified.");
    }

    @Test
    void testOutOfMemoryErrorThrownMetaspace() {
        String logLine = "OutOfMemoryError metaspace_errors=48";
        ExceptionCounts event = new ExceptionCounts(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getExceptionCounts().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_THROWN_METASPACE.getKey()),
                Analysis.ERROR_OOME_THROWN_METASPACE + " analysis not identified.");
    }

    @Test
    void testOverCommitDisabledRatio100OomeLimit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset88.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.isMemoryAllocationFail(), "Memory allocation failure not identified.");
        assertFalse(fel.isCrashOnStartup(), "Crash on startup incorrectly identified.");
        assertEquals(12288, fel.getMemoryAllocation(), "Memory allocation not correct.");
        assertEquals(858320896L, fel.getJvmMemFree(), "JVM reported free memory not correct.");
        assertEquals(0, fel.getJvmSwapFree(), "JVM reported swap not correct.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_OVERCOMMIT_DISABLED_RATIO_100.getKey()),
                Analysis.INFO_OVERCOMMIT_DISABLED_RATIO_100 + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_RLIMIT_OOPS.getKey()),
                Analysis.ERROR_OOME_RLIMIT_OOPS + " analysis not identified.");
    }

    @Test
    void testOverCommitDisabledRatio100OomeOvercommitLimit() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset89.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_OVERCOMMIT_DISABLED_RATIO_100.getKey()),
                Analysis.INFO_OVERCOMMIT_DISABLED_RATIO_100 + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT.getKey()),
                Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT + " analysis not identified.");
    }

    @Test
    void testParallelCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset26.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE),
                GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD),
                GarbageCollector.PARALLEL_OLD + " collector not identified.");
    }

    @Test
    void testPhysicalMemoryInsufficientJvmStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset30.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.isError("Out of Memory Error"), "Out Of Memory Error not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_RLIMIT_STARTUP.getKey()),
                Analysis.ERROR_OOME_RLIMIT_STARTUP + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testPkiTomcatJar() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f47d77d3000-7f47d77d5000 r--s 00003000 fd:00 135061429                  "
                + "/usr/share/java/pki/pki-tomcat.jar";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.getApplication() == JdkUtil.Application.PKI_TOMCAT,
                JdkUtil.Application.PKI_TOMCAT + " application not identified.");
        assertFalse(fel.getApplication() == JdkUtil.Application.TOMCAT,
                JdkUtil.Application.TOMCAT + " application incorrectlyu identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_PKI_TOMCAT.getKey()),
                Analysis.INFO_PKI_TOMCAT + " analysis not identified.");
    }

    @Test
    void testPkiTomcatJvmArgs() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvmArgs = "jvm_args: -Dcom.redhat.fips=false -Dcatalina.base=/var/lib/pki/pki-tomcat "
                + "-Dcatalina.home=/usr/share/tomcat -Djava.endorsed.dirs= "
                + "-Djava.io.tmpdir=/var/lib/pki/pki-tomcat/temp "
                + "-Djava.util.logging.config.file=/var/lib/pki/pki-tomcat/conf/logging.properties "
                + "-Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djava.security.manager "
                + "-Djava.security.policy==/var/lib/pki/pki-tomcat/conf/catalina.policy";
        VmArguments vmArgumentsEvent = new VmArguments(jvmArgs);
        fel.getVmArguments().add(vmArgumentsEvent);
        fel.doAnalysis();
        assertTrue(fel.getApplication() == JdkUtil.Application.PKI_TOMCAT,
                JdkUtil.Application.PKI_TOMCAT + " application not identified.");
        assertFalse(fel.getApplication() == JdkUtil.Application.TOMCAT,
                JdkUtil.Application.TOMCAT + " application incorrectlyu identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_PKI_TOMCAT.getKey()),
                Analysis.INFO_PKI_TOMCAT + " analysis not identified.");
    }

    @Test
    void testPkiTomcatThread() {
        FatalErrorLog fel = new FatalErrorLog();
        String thread = "  0x00007f47f9449800 JavaThread \"ACMEEngineConfigFileSource\" [_thread_blocked, id=369917, "
                + "stack(0x00007f47cc792000,0x00007f47cc893000)]";
        Thread threadEvent = new Thread(thread);
        fel.getThreads().add(threadEvent);
        fel.doAnalysis();
        assertTrue(fel.getApplication() == JdkUtil.Application.PKI_TOMCAT,
                JdkUtil.Application.PKI_TOMCAT + " application not identified.");
        assertFalse(fel.getApplication() == JdkUtil.Application.TOMCAT,
                JdkUtil.Application.TOMCAT + " application incorrectlyu identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_PKI_TOMCAT.getKey()),
                Analysis.INFO_PKI_TOMCAT + " analysis not identified.");
    }

    @Test
    void testPostgresqlJdbcJdkIncompatible() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.302-b08) for linux-amd64 JRE (1.8.0_302-b08), built on "
                + "Jul 16 2021 12:35:49 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String dynamicLibrary = "7f7028969000-7f7028973000 r--s 000c0000 fd:06 131786                     "
                + "/path/to/postgresql-42.2.5.jar";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_POSTGRESQL_JDBC_JDK8_INCOMPATIBLE.getKey()),
                Analysis.ERROR_POSTGRESQL_JDBC_JDK8_INCOMPATIBLE + " analysis not identified.");
    }

    /**
     * Verify analysis file property key/value lookup.
     */
    @Test
    void testPropertyKeyValueLookup() {
        Analysis[] analysis = Analysis.values();
        for (int i = 0; i < analysis.length; i++) {
            assertNotNull(analysis[i] + " not found.", analysis[i].getValue());
        }
    }

    @Test
    void testPsPromotionManagerCopyToSurvivorSpace() {
        FatalErrorLog fel = new FatalErrorLog();
        String stackFrame1 = "V  [libjvm.so+0x9b43cc]  oopDesc* PSPromotionManager::copy_to_survivor_space<false>"
                + "(oopDesc*)+0x70c";
        Stack stackEvent = new Stack(stackFrame1);
        fel.getStacks().add(stackEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE.getKey()),
                Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
        // Test header only
        fel.getAnalysis().clear();
        fel.getStacks().clear();
        String header = "# V  [libjvm.so+0x9b43cc]  oopDesc* PSPromotionManager::copy_to_survivor_space<false>"
                + "(oopDesc*)+0x70c";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE.getKey()),
                Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_LIBJVM_SO.getKey()),
                Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.");
    }

    @Test
    void testPthreadGetcpuclockid() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset32.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_PTHREAD_GETCPUCLOCKID.getKey()),
                Analysis.ERROR_PTHREAD_GETCPUCLOCKID + " analysis not identified.");
    }

    @Test
    void testRemoteDebuggingEnabledAgentlib() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset45.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED.getKey()),
                org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_VMWARE.getKey()), Analysis.INFO_VMWARE + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testRemoteDebuggingEnabledRunjdwp() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xmx2g -Xrunjdwp:transport=dt_socket,server=y,address=8787,suspend=n -Xms2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED.getKey()),
                org.github.joa.util.Analysis.ERROR_REMOTE_DEBUGGING_ENABLED + " analysis not identified.");
    }

    @Test
    void testRhel6() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7fd421b89000-7fd4227ab000 r-xp 00000000 fd:01 264289                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.71-1.b15.el6_7.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.71-b15) for linux-amd64 JRE (1.8.0_71-b15), built on "
                + "Jan 13 2016 21:08:08 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-16)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String os = "OS:Red Hat Enterprise Linux Server release 6.7 (Santiago)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertTrue(fel.isRhBuildOpenJdk(), "Red Hat build of OpenJDK not identified.");
        assertTrue(fel.isRhBuildString(), "Red Hat build string not identified.");
        assertTrue(fel.isRhVersion(), "Red Hat version not identified.");
        assertEquals(KrashUtil.getDate("Jan 13 2016 21:08:08"), fel.getJdkBuildDate(), "Build date not correct.");
        assertTrue(fel.isRhBuildDate(), "Red Hat build date not identified.");
        assertFalse(fel.isRhBuildDateUnknown(), "Red Hat build of OpenJDK date unknown incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_POSSIBLE.getKey()),
                Analysis.INFO_RH_BUILD_POSSIBLE + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT.getKey()),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
    }

    @Test
    void testRhel7ElsHasBegunVersionLastMinorRelease() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.9 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String time = "time: Mon Jul 1 00:00:00 2024";
        Time timeEvent = new Time(time);
        fel.setTime(timeEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION.getKey()),
                Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION + " incorrectly not identified.");
    }

    @Test
    void testRhel7ElsHasBegunVersionUnsupported() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.1 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String time = "time: Mon Jul 1 00:00:00 2024";
        Time timeEvent = new Time(time);
        fel.setTime(timeEvent);
        fel.doAnalysis();
        assertFalse((new Date()).compareTo(KrashUtil.RHEL7_ELS_START) >= 0,
                "TODO: Remove this line and uncomment the one below.");
        // assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION.getKey()),
        // Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION + " analysis not identified.");
    }

    @Test
    void testRhel7ElsHasNotBegun() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.1 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String time = "time: Sun Jun 30 23:59:59 2024";
        Time timeEvent = new Time(time);
        fel.setTime(timeEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION.getKey()),
                Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION + " analysis incorrectly identified.");
    }

    @Test
    void testRhel7WorkstationrRpm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset19.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_RH_BUILD_RPM_INSTALL.getKey()),
                Analysis.INFO_RH_BUILD_RPM_INSTALL + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testRhel9OpenJdk8() {
        FatalErrorLog fel = new FatalErrorLog();
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (25.345-b01) for linux-amd64 JRE (1.8.0_345-b01), built on "
                + "Aug  4 2022 05:08:02 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        String os = "OS:Red Hat Enterprise Linux release 9.0 (Plow)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        fel.doAnalysis();
        assertEquals(OsVersion.RHEL9, fel.getOsVersion(), "OS version not correct.");
        assertEquals(JavaSpecification.JDK8, fel.getJavaSpecification(), "Java specification not correct.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_RHEL9_JDK8.getKey()),
                Analysis.INFO_RHEL9_JDK8 + " analysis not identified.");
    }

    @Test
    void testRhelJdkRpmMismatchJdk11() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS: Red Hat Enterprise Linux release 8.5 (Ootpa)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String library = "7ff001124000-7ff001ecf000 r-xp 00000000 fd:00 17385                      "
                + "/usr/lib/jvm/java-11-openjdk-11.0.13.0.8-1.el8_4.x86_64/lib/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.13+8-LTS) for linux-amd64 JRE (11.0.13+8-LTS), built "
                + "on Oct 13 2021 11:20:31 by \"mockbuild\" with gcc 8.4.1 20200928 (Red Hat 8.4.1-1)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("8.5", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8.4", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertEquals("RHEL/JDK rpm version mismatch: RHEL 8.5 + java-11-openjdk-11.0.13.0.8-1.el8_4.x86_64.",
                fel.getAnalysisLiteral(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " not correct.");
    }

    @Test
    void testRhelJdkRpmMismatchJdk17() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux release 8.5 (Ootpa)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String library = "7f7b5e35f000-7f7b5f5d6000 r-xp 00000000 fd:01 67638415                   "
                + "/usr/lib/jvm/java-17-openjdk-17.0.4.0.8-2.el8_6.x86_64/lib/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (17.0.4+8-LTS) for linux-amd64 JRE (17.0.4+8-LTS), built on "
                + "Jul 20 2022 13:03:41 by \"mockbuild\" with gcc 8.5.0 20210514 (Red Hat 8.5.0-10)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("8.5", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8.6", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
    }

    @Test
    void testRhelJdkRpmMismatchJdk8() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset69.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals("java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.ppc64le", fel.getRpmDirectory(),
                "Rpm directory not correct.");
        assertEquals("8.4", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8.5", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LATEST.getKey()),
                Analysis.WARN_JDK_NOT_LATEST + " analysis incorrectly identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testRhelJdkRpmMismatchNot() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.1 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String library = "7fcb5ea06000-7fcb5f6ee000 r-xp 00000000 fd:01 121728675                  "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.222.b03-1.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.222-b03) for linux-amd64 JRE (1.8.0_222-ea-b03), built "
                + "on May 22 2019 13:05:27 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("7.1", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("7", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " analysis incorrectly identified.");
    }

    @Test
    void testRhelJdkRpmMismatchRhel7Power9() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.6 (Maipo)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String library = "3fff7c2d0000-3fff7cf40000 r-xp 00000000 fd:08 138908                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-ppc64le JRE (1.8.0_275-b01), built "
                + "on Nov  6 2020 06:43:55 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("7.6", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("7.9", fel.getJdkRhelVersion(), "JDK RHEL version not correct.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_RHEL7_POWER9.getKey()),
                Analysis.WARN_RHEL7_POWER9 + " analysis not identified.");
    }

    @Test
    void testRhelVersionJdk21() {
        FatalErrorLog fel = new FatalErrorLog();
        String os1 = "OS:";
        OsInfo osEvent1 = new OsInfo(os1);
        fel.getOsInfos().add(osEvent1);
        String os2 = "Red Hat Enterprise Linux release 8.9 (Ootpa)";
        OsInfo osEvent2 = new OsInfo(os2);
        fel.getOsInfos().add(osEvent2);
        String library = "7ff4e3851000-7ff4e3ae9000 r--p 00000000 fd:01 67416842                   "
                + "/usr/lib/jvm/java-21-openjdk-21.0.1.0.12-3.el8.x86_64/lib/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (21.0.1+12-LTS) for linux-amd64 JRE (21.0.1+12-LTS), "
                + "built on 2023-11-06T21:59:41Z by \"mockbuild\" with gcc 10.2.1 20210130 (Red Hat 10.2.1-11)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertEquals("8.9", fel.getRhelVersion(), "RHEL version not correct.");
        assertEquals("8", fel.getJdkRhelVersion(), "RHEL version not correct.");
        assertFalse(fel.hasAnalysis(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.getKey()),
                Analysis.WARN_RHEL_JDK_RPM_MISMATCH + " analysis not identified.");
    }

    @Test
    void testRpmPpc64le() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset15.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_RH_BUILD_RPM_INSTALL.getKey()),
                Analysis.INFO_RH_BUILD_RPM_INSTALL + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.WARN_JDK_NOT_LATEST.getKey()),
                Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9.getKey()),
                Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9 + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testServerFlag32Bit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -server -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        // Specify 32-bit
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        VmInfo vmInfoEvent = new VmInfo(logLine);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_64_SERVER_REDUNDANT.getKey()),
                org.github.joa.util.Analysis.INFO_64_SERVER_REDUNDANT + " analysis incorrectly identified.");
    }

    @Test
    void testShenandoahCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset31.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN),
                GarbageCollector.UNKNOWN + " incorrectly identified.");
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH),
                GarbageCollector.SHENANDOAH + " collector not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_METASPACE.getKey()),
                org.github.joa.util.Analysis.INFO_METASPACE + " analysis not identified.");
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_METASPACE_LT_COMP_CLASS.getKey()),
                org.github.joa.util.Analysis.WARN_METASPACE_LT_COMP_CLASS + " analysis not identified.");
    }

    @Test
    void testShenandoahMarkLoopWork() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset44.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK.getKey()),
                Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testSiKernel() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset39.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SIGCODE_SI_KERNEL.getKey()),
                Analysis.INFO_SIGCODE_SI_KERNEL + " analysis not identified.");
    }

    @Test
    void testSslDecode() {
        FatalErrorLog fel = new FatalErrorLog();
        String header1 = "# There is insufficient memory for the Java Runtime Environment to continue.";
        Header headerEvent1 = new Header(header1);
        fel.getHeaders().add(headerEvent1);
        String header2 = "# Native memory allocation (malloc) failed to allocate 4294967312 bytes for Chunk::new";
        Header headerEvent2 = new Header(header2);
        fel.getHeaders().add(headerEvent2);
        String header3 = "#  Out of Memory Error (arena.cpp:197), pid=2907, tid=2927";
        Header headerEvent3 = new Header(header3);
        fel.getHeaders().add(headerEvent3);
        String currentThread = "Current thread (0x00007f5134b1b000):  JavaThread \"C2 CompilerThread0\" daemon "
                + "[_thread_in_native, id=2927, stack(0x00007f5138229000,0x00007f513832a000)]";
        CurrentThread currentThreadEvent = new CurrentThread(currentThread);
        fel.setCurrentThread(currentThreadEvent);
        String currentCompileTask = "C2:299829840 17165   !   4       "
                + "sun.security.ssl.SSLEngineInputRecord::decodeInputRecord (812 bytes)";
        CurrentCompileTask currentCompileTaskEvent = new CurrentCompileTask(currentCompileTask);
        fel.getCurrentCompileTasks().add(currentCompileTaskEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (11.0.16+8-LTS) for linux-amd64 JRE (11.0.16+8-LTS), "
                + "built on Jul 18 2022 19:50:20 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-44)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        String memory = "Memory: 4k page, physical 65686152k(438884k free), swap 2097148k(0k free)";
        Memory memoryEvent = new Memory(memory);
        fel.getMemories().add(memoryEvent);
        String jvmArgs = "-Xms3g -Xmx3g -XX:ThreadStackSize=640";
        VmArguments vmArgumentsEvent = new VmArguments(jvmArgs);
        fel.getVmArguments().add(vmArgumentsEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE.getKey()),
                Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE + " analysis not identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_COMPILER_THREAD.getKey()),
                Analysis.ERROR_COMPILER_THREAD + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL.getKey()),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
    }

    @Test
    void testStackFreeSpaceGtStackSize() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset37.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_STACK_FREESPACE_GT_STACK_SIZE.getKey()),
                Analysis.INFO_STACK_FREESPACE_GT_STACK_SIZE + " analysis not identified.");
    }

    @Test
    void testStackOverflowError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_STACKOVERFLOW.getKey()),
                Analysis.ERROR_STACKOVERFLOW + " analysis not identified.");
    }

    @Test
    void testStackSizeSmallNoJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        fel.doAnalysis();
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.WARN_THREAD_STACK_SIZE_SMALL.getKey()),
                org.github.joa.util.Analysis.WARN_THREAD_STACK_SIZE_SMALL + " analysis incorrectly identified.");
    }

    @Test
    void testSwapDisabled() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset28.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.isRhBuildOpenJdk(), "RH build of OpenJDK incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SWAP_DISABLED.getKey()),
                Analysis.INFO_SWAP_DISABLED + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testSwappingInfo() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset11.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_SWAPPING.getKey()),
                Analysis.INFO_SWAPPING + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testTimeout() {
        FatalErrorLog fel = new FatalErrorLog();
        String timeout = "[timeout occurred during error reporting in step \"printing summary machine and OS info\"] "
                + "after 30 s.";
        Timeout timeoutEvent = new Timeout(timeout);
        fel.getTimeouts().add(timeoutEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_TIMEOUT.getKey()),
                Analysis.ERROR_TIMEOUT + " analysis not identified.");
    }

    @Test
    void testTimeoutHeader() {
        FatalErrorLog fel = new FatalErrorLog();
        String header = "[timeout occurred during error reporting in step \"printing problematic frame\"] after 30 s.";
        Header headerEvent = new Header(header);
        fel.getHeaders().add(headerEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_TIMEOUT.getKey()),
                Analysis.ERROR_TIMEOUT + " analysis not identified.");
    }

    @Test
    void testTomcatNativeConnector() {
        FatalErrorLog fel = new FatalErrorLog();
        String library = "7f15ba1b0000-7f15ba1b2000 rw-p 0002c000 fd:0b 17                         "
                + "/path/to/tomcat/lib/libtcnative-1.so.0.2.30";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_NATIVE_LIBRARIES_TOMCAT.getKey()),
                Analysis.INFO_NATIVE_LIBRARIES_TOMCAT + " analysis not identified.");
        assertEquals(1, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testTruncatedLog() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset48.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertFalse(fel.hasAnalysis(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL.getKey()),
                Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL + " analysis incorrectly identified.");
        assertFalse(fel.hasAnalysis(Analysis.INFO_RH_BUILD_NOT.getKey()),
                Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.");
        assertTrue(fel.hasAnalysis(Analysis.INFO_TRUNCATED.getKey()),
                Analysis.INFO_TRUNCATED + " analysis not identified.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_OOME_OOPS.getKey()),
                Analysis.ERROR_OOME_OOPS + " analysis not identified.");
    }

    @Test
    void testUnidentifiedNativeLibrariesWindows() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset71.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertEquals(31, fel.getNativeLibraries().size(), "Number of native libraries not correct.");
        assertEquals(1, fel.getNativeLibrariesUnknown().size(), "Number of unidentified native libraries not correct.");
        assertEquals("C:\\Program Files\\Cylance\\Desktop\\CyMemDef64.dll", fel.getNativeLibrariesUnknown().get(0),
                "Unidentified native library not correct.");
    }

    @Test
    void testUnknownJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+Mike";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        String undefined = "Undefined JVM option(s): -XX:+Mike. Please submit an issue so we can investigate: "
                + "https://github.com/mgm3746/krashpad/issues. If attaching a fatal error log, be sure to review it "
                + "and remove any sensitive information.";
        assertTrue(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_OPTS_UNDEFINED.getKey()),
                org.github.joa.util.Analysis.INFO_OPTS_UNDEFINED + " analysis not identified.");
        assertEquals(undefined, fel.getAnalysisLiteral(org.github.joa.util.Analysis.INFO_OPTS_UNDEFINED.getKey()),
                org.github.joa.util.Analysis.INFO_UNACCOUNTED_OPTIONS_DISABLED + " not correct.");
        assertFalse(fel.hasAnalysis(org.github.joa.util.Analysis.INFO_UNACCOUNTED_OPTIONS_DISABLED.getKey()),
                org.github.joa.util.Analysis.INFO_UNACCOUNTED_OPTIONS_DISABLED + " analysis incorrectly identified.");
    }

    @Test
    void testUnknownStorageFalseReporting() {
        FatalErrorLog fel = new FatalErrorLog();
        assertFalse(fel.hasAnalysis(Analysis.INFO_STORAGE_UNKNOWN.getKey()),
                Analysis.INFO_STORAGE_UNKNOWN + " analysis incorrectly identified.");
    }

    @Test
    void testVersionEol() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 6.10 (Santiago)";
        OsInfo osEvent = new OsInfo(os);
        fel.getOsInfos().add(osEvent);
        String library = "7ff001124000-7ff001ecf000 r-xp 00000000 fd:00 17385                      "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.275.b01-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibrary dynamicLibraryEvent = new DynamicLibrary(library);
        fel.getDynamicLibraries().add(dynamicLibraryEvent);
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.275-b01) for linux-amd64 JRE (1.8.0_275-b01), "
                + "built on Nov  6 2020 02:01:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfo vmInfoEvent = new VmInfo(vmInfo);
        fel.setVmInfo(vmInfoEvent);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_JDK_ANCIENT.getKey()),
                Analysis.INFO_JDK_ANCIENT + " analysis not identified.");
    }

    @Test
    void testVmOperationConcurrentGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmOperation = "VM_Operation (0x0000008e276ff410): CGC_Operation, mode: safepoint, requested by thread "
                + "0x000001d9d3e12800";
        VmOperation event = new VmOperation(vmOperation);
        fel.setVmOperation(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_VM_OPERATION_CONCURRENT_GC.getKey()),
                Analysis.INFO_VM_OPERATION_CONCURRENT_GC + " analysis not identified.");
    }

    @Test
    void testVmOperationHeapDump() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmOperation = "VM_Operation (0x0000000054ede490): HeapDumper, mode: safepoint, requested by thread "
                + "0x000000004d180000";
        VmOperation event = new VmOperation(vmOperation);
        fel.setVmOperation(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_VM_OPERATION_HEAP_DUMP.getKey()),
                Analysis.INFO_VM_OPERATION_HEAP_DUMP + " analysis not identified.");
    }

    @Test
    void testVmWareNativeLibraries() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary1 = "0x0000000062a40000 - 0x0000000062aa1000         C:\\Windows\\SYSTEM32\\"
                + "vmGuestLib.DLL";
        DynamicLibrary dynamicLibraryEvent1 = new DynamicLibrary(dynamicLibrary1);
        fel.getDynamicLibraries().add(dynamicLibraryEvent1);
        String dynamicLibrary2 = "0x00007ffaab4f0000 - 0x00007ffaab4fa000         C:\\Windows\\system32\\vsocklib.dll";
        DynamicLibrary dynamicLibraryEvent2 = new DynamicLibrary(dynamicLibrary2);
        fel.getDynamicLibraries().add(dynamicLibraryEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_VMWARE.getKey()), Analysis.INFO_VMWARE + " analysis not identified.");
        assertEquals(2, fel.getNativeLibraries().size(), "Native library count not correct.");
        assertEquals(0, fel.getNativeLibrariesUnknown().size(), "Native library unknown count not correct.");
    }

    @Test
    void testWarnNotLatestJdkValue() {
        assertEquals("JDK is not the latest release", Analysis.WARN_JDK_NOT_LATEST.getValue(),
                Analysis.WARN_JDK_NOT_LATEST + "value not correct.");
    }

    @Test
    void testWilyCrash() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "C  0x0000000000000e76";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 16666  com.wily.introscope.agent.platform.linux.LinuxPlatformStatisticsBackEnd."
                + "getAggregateCPUUsage(Ljava/lang/String;)[J (0 bytes) @ 0x00007f6dabd0e7fc [0x00007f6dabd0e740+0xbc]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.ERROR_WILY.getKey()), Analysis.ERROR_WILY + " analysis not identified.");
    }

    @Test
    void testWilyDetected() {
        FatalErrorLog fel = new FatalErrorLog();
        String dynamicLibrary = "7f6d9a3b7000-7f6d9a4b6000 ---p 00003000 fd:08 98413                      "
                + "/app/jbossas/wily10.7/core/ext/libIntroscopeLinuxIntelAmd64Stats.so";
        DynamicLibrary event = new DynamicLibrary(dynamicLibrary);
        fel.getDynamicLibraries().add(event);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.INFO_WILY.getKey()), Analysis.INFO_WILY + " analysis not identified.");
    }

    @Test
    void testWilyInStack() {
        FatalErrorLog fel = new FatalErrorLog();
        String stack1 = "J  12345 com.example.MyClass";
        Stack stackEvent1 = new Stack(stack1);
        fel.getStacks().add(stackEvent1);
        String stack2 = "J 16666  com.wily.introscope.agent.platform.linux.LinuxPlatformStatisticsBackEnd."
                + "getAggregateCPUUsage(Ljava/lang/String;)[J (0 bytes) @ 0x00007f6dabd0e7fc [0x00007f6dabd0e740+0xbc]";
        Stack stackEvent2 = new Stack(stack2);
        fel.getStacks().add(stackEvent2);
        fel.doAnalysis();
        assertTrue(fel.hasAnalysis(Analysis.WARN_WILY.getKey()), Analysis.WARN_WILY + " analysis not identified.");
    }

    @Test
    void testWindowsCrashInJvmDll() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset12.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(0, fel.getUnidentifiedLogLines().size(), "Unidentified log lines.");
        assertTrue(fel.hasAnalysis(Analysis.ERROR_JVM_DLL.getKey()),
                Analysis.ERROR_JVM_DLL + " analysis not identified.");
    }

    @Test
    void testZGcGenerationalEvent() {
        FatalErrorLog fel = new FatalErrorLog();
        String zHeap = " ZHeap           used 4M, capacity 500M, max capacity 7978M";
        Heap heapEvent = new Heap(zHeap);
        fel.getHeaps().add(heapEvent);
        String barrierSet = "ZBarrierSet";
        BarrierSet barrierSetEvent = new BarrierSet(barrierSet);
        fel.setBarrierSet(barrierSetEvent);
        String vm_info = "vm_info: OpenJDK 64-Bit Server VM (21.0.2+13-LTS) for linux-amd64 JRE (21.0.2+13-LTS), built "
                + "on 2024-01-09T22:49:35Z by \"mockbuild\" with gcc 10.2.1 20210130 (Red Hat 10.2.1-11)";
        VmInfo vmEvent = new VmInfo(vm_info);
        fel.setVmInfo(vmEvent);
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.ZGC_GENERATIONAL),
                GarbageCollector.ZGC_GENERATIONAL + " collector not identified.");
    }

    @Test
    void testZGcGenerationalJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseZGC -XX:+ZGenerational -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.ZGC_GENERATIONAL),
                GarbageCollector.ZGC_GENERATIONAL + " collector not identified.");
    }

    @Test
    void testZGcNonGenerationalEvent() {
        FatalErrorLog fel = new FatalErrorLog();
        String zHeap = " ZHeap           used 4M, capacity 500M, max capacity 7978M";
        Heap heapEvent = new Heap(zHeap);
        fel.getHeaps().add(heapEvent);
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.ZGC_NON_GENERATIONAL),
                GarbageCollector.ZGC_NON_GENERATIONAL + " collector not identified.");
    }

    @Test
    void testZGcNonGenerationalJvmOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseZGC -Xmx2048M";
        VmArguments event = new VmArguments(jvm_args);
        fel.getVmArguments().add(event);
        fel.doAnalysis();
        assertTrue(fel.getGarbageCollectors().contains(GarbageCollector.ZGC_NON_GENERATIONAL),
                GarbageCollector.ZGC_NON_GENERATIONAL + " collector not identified.");
    }
}

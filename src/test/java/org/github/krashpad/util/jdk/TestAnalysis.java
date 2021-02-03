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
package org.github.krashpad.util.jdk;

import java.io.File;

import org.github.krashpad.domain.jdk.ContainerInfoEvent;
import org.github.krashpad.domain.jdk.CpuInfoEvent;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.HeapEvent;
import org.github.krashpad.domain.jdk.VmArgumentsEvent;
import org.github.krashpad.domain.jdk.VmInfoEvent;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.JdkUtil.Application;
import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestAnalysis extends TestCase {
    /**
     * Verify analysis file property key/value lookup.
     */
    public void testPropertyKeyValueLookup() {
        Analysis[] analysis = Analysis.values();
        for (int i = 0; i < analysis.length; i++) {
            Assert.assertNotNull(analysis[i].getKey() + " not found.", analysis[i].getValue());
        ***REMOVED***
    ***REMOVED***

    public void testWarnNotLatestJdkValue() {
        Assert.assertEquals(Analysis.WARN_JDK_NOT_LATEST + "value not correct.",
                "JDK is not the latest version. Latest version is ", Analysis.WARN_JDK_NOT_LATEST.getValue());
    ***REMOVED***

    public void testNoMemoryEvent() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(Analysis.INFO_SWAP_DISABLED + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_SWAP_DISABLED));
    ***REMOVED***

    public void testSwappingInfo() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset11.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_SWAPPING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SWAPPING));
    ***REMOVED***

    public void testSwappingWarn() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset12.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.WARN_SWAPPING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_SWAPPING));
        Assert.assertTrue(Analysis.ERROR_JVM_DLL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_JVM_DLL));
    ***REMOVED***

    public void testLatestRelease() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset14.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LATEST));
        Assert.assertEquals("Release days diff not correct.", 189,
                ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)));
        Assert.assertEquals("Release ***REMOVED*** diff not correct.", 3,
                JdkUtil.getLatestJdkReleaseNumber(fel) - JdkUtil.getJdkReleaseNumber(fel));
    ***REMOVED***

    public void testRpmPpc64le() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset15.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_RH_BUILD_RPM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertTrue(Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LATEST));
        Assert.assertTrue(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9 + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9));
    ***REMOVED***

    public void testNotLts() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset16.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Release ***REMOVED*** diff not correct.", JavaSpecification.JDK12, fel.getJavaSpecification());
        Assert.assertTrue(Analysis.WARN_JDK_NOT_LTS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LTS));
    ***REMOVED***

    public void testAdoptOpenJdk() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset17.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_LINUX_ZIP));
        Assert.assertTrue(Analysis.INFO_ADOPTOPENJDK_POSSIBLE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_ADOPTOPENJDK_POSSIBLE));
        Assert.assertTrue(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX));
    ***REMOVED***

    public void testJdkDebugSymbolsMissing() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset18.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(Analysis.INFO_RH_BUILD_LINUX_ZIP + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_LINUX_ZIP));
        Assert.assertTrue(Analysis.WARN_DEBUG_SYMBOLS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertTrue(Analysis.INFO_JDK_ANCIENT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_JDK_ANCIENT));
    ***REMOVED***

    public void testRhel7WorkstationrRpm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset19.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_RH_BUILD_RPM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
    ***REMOVED***

    public void testJnaRedHatJdk() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset22.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String stackFrame1 = "C  [jna8588255081773605818.tmp+0x13c480]  CkMultiByteBase::nextIdx()+0x10";
        String stackFrame2 = "j  com.sun.jna.Native.invokePointer(Lcom/sun/jna/Function;JI[Ljava/lang/Object;)J+0";
        Assert.assertEquals("Stack frame 1 not correct.", stackFrame1, fel.getStackFrame(1));
        Assert.assertEquals("Stack frame 2 not correct.", stackFrame2, fel.getStackFrame(2));
        Assert.assertTrue(Analysis.ERROR_JNA_RH + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_JNA_RH));
    ***REMOVED***

    public void testJdk8ZipFileContention() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset23.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Release ***REMOVED*** diff not correct.", JavaSpecification.JDK8, fel.getJavaSpecification());
        String stackFrameTopCompiledJavaCode = "J 302  java.util.zip.ZipFile.getEntry(J[BZ)J (0 bytes) @ "
                + "0x00007fa287303dce [0x00007fa287303d00+0xce]";
        Assert.assertEquals("Top compiled Java code (J) stack frame not correct.", stackFrameTopCompiledJavaCode,
                fel.getStackFrameTopCompiledJavaCode());
        Assert.assertTrue(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION));
    ***REMOVED***

    public void testDirectByteBufferUnsynchronizedAccess() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset24.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Application not correct.", Application.JBOSS_EAP7, fel.getApplication());
        Assert.assertEquals("To stack frame not correct.", "v  ~StubRoutines::jbyte_disjoint_arraycopy",
                fel.getStackFrameTop());
        Assert.assertTrue("DirectByteBuffer class not identified in stack.",
                fel.isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER));
        Assert.assertTrue(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION));
        Assert.assertTrue(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7 + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7));
        Assert.assertFalse(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED));
    ***REMOVED***

    public void testParallelCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset26.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(GarbageCollector.UNKNOWN + " incorrectly identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN));
        Assert.assertTrue(GarbageCollector.PARALLEL_SCAVENGE + " collector not identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_SCAVENGE));
        Assert.assertTrue(GarbageCollector.PARALLEL_OLD + " collector not identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.PARALLEL_OLD));
    ***REMOVED***

    public void testInsufficientMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset27.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long physicalMemory = JdkUtil.convertSize(15995796, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(241892, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory free not correct.", physicalMemoryFree, fel.getJvmPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(10592252, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap not correct.", swap, fel.getJvmSwap());
        long swapFree = JdkUtil.convertSize(4, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap free not correct.", swapFree, fel.getJvmSwapFree());
        long heapMax = JdkUtil.convertSize(8192, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocationYoung = JdkUtil.convertSize(2761728, 'K', Constants.PRECISION_REPORTING);
        long heapAllocationOld = JdkUtil.convertSize(4838912, 'K', Constants.PRECISION_REPORTING);
        long heapAllocation = heapAllocationYoung + heapAllocationOld;
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(0 + 2671671, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(8192, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(471808, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(347525, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        long compressedClassSpace = JdkUtil.convertSize(1024, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Compressed Class Space size not correct.", compressedClassSpace,
                fel.getCompressedClassSpaceSize());
        long directMemoryMax = JdkUtil.convertSize(0, 'G', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Direct Memory mx not correct.", directMemoryMax, fel.getDirectMemoryMaxSize());
        Assert.assertEquals("Thread stack size not correct.", 1024, fel.getThreadStackSize());
        Assert.assertEquals("Thread count not correct.", 225, fel.getJavaThreadCount());
        long threadMemory = JdkUtil.convertSize(1024 * 225, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Thread memory not correct.", threadMemory, fel.getThreadStackMemory());
        Assert.assertEquals("Jvm memory not correct.",
                heapMax + metaspaceMax + compressedClassSpace + directMemoryMax + threadMemory, fel.getJvmMemory());
        Assert.assertTrue(Analysis.ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY));
        Assert.assertFalse(Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO));
    ***REMOVED***

    public void testSwapDisabled() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset28.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_SWAP_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SWAP_DISABLED));
    ***REMOVED***

    public void testOomePhysicalMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset29.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("Out Of Memory Error not identified.", fel.isError("Out of Memory Error"));
        long physicalMemory = JdkUtil.convertSize(24609684, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long heapMax = JdkUtil.convertSize(16000, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long metaspaceMax = JdkUtil.convertSize(1148928, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long compressedClassSpace = JdkUtil.convertSize(1024, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Compressed Class Space size not correct.", compressedClassSpace,
                fel.getCompressedClassSpaceSize());
        long directMemoryMax = JdkUtil.convertSize(0, 'G', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Direct Memory mx not correct.", directMemoryMax, fel.getDirectMemoryMaxSize());
        Assert.assertEquals("Thread stack size not correct.", 1024, fel.getThreadStackSize());
        Assert.assertEquals("Thread count not correct.", 67, fel.getJavaThreadCount());
        long threadMemory = JdkUtil.convertSize(1024 * 67, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Thread memory not correct.", threadMemory, fel.getThreadStackMemory());
        Assert.assertEquals("Jvm memory not correct.",
                heapMax + metaspaceMax + compressedClassSpace + directMemoryMax + threadMemory, fel.getJvmMemory());
        Assert.assertTrue(Analysis.ERROR_OOME_EXTERNAL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_EXTERNAL));
        Assert.assertFalse(Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO));
    ***REMOVED***

    public void testPhysicalMemoryInsufficientJvmStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset30.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("Out Of Memory Error not identified.", fel.isError("Out of Memory Error"));
        Assert.assertTrue(Analysis.ERROR_OOME_STARTUP_LIMIT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP_LIMIT));
        Assert.assertFalse(Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO));
    ***REMOVED***

    public void testShenandoahCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset31.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(GarbageCollector.UNKNOWN + " incorrectly identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN));
        Assert.assertTrue(GarbageCollector.SHENANDOAH + " collector not identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH));
        Assert.assertTrue(Analysis.INFO_OPT_METASPACE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_METASPACE));
        Assert.assertTrue(Analysis.WARN_OPT_METASPACE_LT_COMP_CLASS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_METASPACE_LT_COMP_CLASS));
    ***REMOVED***

    public void testPthreadGetcpuclockid() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset32.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_PTHREAD_GETCPUCLOCKID + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_PTHREAD_GETCPUCLOCKID));
    ***REMOVED***

    public void testCrashStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset33.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_JVM_STARTUP_FAILS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_JVM_STARTUP_FAILS));
        Assert.assertTrue(Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SIGNO_SIGSEGV));
        Assert.assertTrue(Analysis.ERROR_COMPILER_THREAD + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_COMPILER_THREAD));
        Assert.assertFalse(Analysis.ERROR_LIBJVM_SO + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO));
    ***REMOVED***

    public void testAws() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset34.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_STORAGE_AWS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_STORAGE_AWS));
    ***REMOVED***

    public void testStackOverflowError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_STACKOVERFLOW + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_STACKOVERFLOW));
    ***REMOVED***

    public void testCmsCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset35.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(GarbageCollector.UNKNOWN + " incorrectly identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN));
        Assert.assertTrue(GarbageCollector.PAR_NEW + " collector not identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.PAR_NEW));
        Assert.assertTrue(GarbageCollector.CMS + " collector not identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.CMS));
    ***REMOVED***

    public void testOomeJavaHeap() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset36.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_OOME_JAVA_HEAP + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_JAVA_HEAP));
        Assert.assertTrue(Analysis.INFO_SIGNO_SIGSEGV + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SIGNO_SIGSEGV));
        Assert.assertTrue(Analysis.INFO_SIGCODE_SEGV_MAPERR + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SIGCODE_SEGV_MAPERR));
    ***REMOVED***

    public void testStackFreeSpaceGtStackSize() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset37.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE));
    ***REMOVED***

    public void testSiKernel() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset39.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_SIGCODE_SI_KERNEL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SIGCODE_SI_KERNEL));
    ***REMOVED***

    public void testOomeCompressedOops() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset42.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_OOME_COMPRESSED_OOPS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_COMPRESSED_OOPS));
    ***REMOVED***

    public void testStubroutines() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset17.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_STUBROUTINES + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_STUBROUTINES));
        testFile = new File(Constants.TEST_DATA_DIR + "dataset43.txt");
        fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_STUBROUTINES + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_STUBROUTINES));

    ***REMOVED***

    public void testShenandoahMarkLoopWork() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset44.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK));
    ***REMOVED***

    public void testRemoteDebuggingEnabled() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset45.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED));
    ***REMOVED***

    public void testLibaioContextDone() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset46.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_LIBAIO_CONTEXT_DONE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_LIBAIO_CONTEXT_DONE));
    ***REMOVED***

    public void testContainer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset47.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_CGROUP + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_CGROUP));
        Assert.assertTrue(Analysis.INFO_MEMORY_JVM_NE_SYSTEM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_MEMORY_JVM_NE_SYSTEM));
        Assert.assertTrue(Analysis.INFO_CGROUP_MEMORY_LIMIT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_CGROUP_MEMORY_LIMIT));
    ***REMOVED***

    public void testTruncatedLog() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset48.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(Analysis.ERROR_OOME_EXTERNAL + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_EXTERNAL));
        Assert.assertFalse(Analysis.INFO_RH_BUILD_NOT + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_NOT));
        Assert.assertTrue(Analysis.INFO_TRUNCATED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_TRUNCATED));
        Assert.assertTrue(Analysis.ERROR_OOME + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME));
    ***REMOVED***

    public void testNoStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset49.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(Analysis.INFO_STACK_NO_VM_CODE + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_STACK_NO_VM_CODE));
    ***REMOVED***

    public void testNullPointer() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset51.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_NULL_POINTER + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_NULL_POINTER));
    ***REMOVED***

    public void testUseAdaptiveSizePolicyDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:-UseAdaptiveSizePolicy";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED));
    ***REMOVED***

    public void testMaxPermSize() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_MAX_PERM_SIZE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_MAX_PERM_SIZE));
    ***REMOVED***

    public void testPermSize() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:PermSize=256m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PERM_SIZE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PERM_SIZE));
    ***REMOVED***

    public void testHeapDumpOnOutOfMemoryErrorMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_HEAP_DUMP_ON_OOME_MISSING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_DUMP_ON_OOME_MISSING));
    ***REMOVED***

    public void testHeapDumpOnOutOfMemoryErrorDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:-HeapDumpOnOutOfMemoryError";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED));
    ***REMOVED***

    public void testHeapDumpOnOutOfMemoryErrorPathMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_HEAP_DUMP_PATH_MISSING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_DUMP_PATH_MISSING));
    ***REMOVED***

    public void testHeapDumpOnOutOfMemoryErrorPathIsFileName() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError "
                + "-XX:HeapDumpPath=/path/to/heap.hprof";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_HEAP_DUMP_PATH_FILENAME + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_DUMP_PATH_FILENAME));
    ***REMOVED***

    public void testHeapDumpOnOutOfMemoryErrorPathIsDirectory() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:+HeapDumpOnOutOfMemoryError "
                + "-XX:HeapDumpPath=/path/to";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertFalse(Analysis.INFO_OPT_HEAP_DUMP_PATH_FILENAME + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_DUMP_PATH_FILENAME));
    ***REMOVED***

    public void testCmsParallelInitialMarkEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:-CMSParallelInitialMarkEnabled";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED));
    ***REMOVED***

    public void testCmsParallelRemarkEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xms1024m -Xmx2048m -XX:MaxPermSize=256m -XX:-CMSParallelRemarkEnabled";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED));
    ***REMOVED***

    public void testCompressedClassPointersEnabledCompressedOopsDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK));
    ***REMOVED***

    public void testCompressedClassPointersEnabledHeapGt32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseCompressedClassPointers -Xmx32g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G));
        Assert.assertFalse(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G + " incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G));
    ***REMOVED***

    public void testCompressedClassPointersDisabledHeapLt32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedClassPointers -XX:+UseCompressedOops -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G));
    ***REMOVED***

    public void testCompressedClassPointersDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedClassPointers -XX:+UseCompressedOops";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK));
    ***REMOVED***

    public void testCompressedClassSpaceSizeWithCompressedOopsDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:CompressedClassSpaceSize=1G -XX:-UseCompressedOops -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK));
        Assert.assertTrue(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED));
    ***REMOVED***

    public void testCompressedClassSpaceSizeWithCompressedClassPointersDisabledHeapUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:CompressedClassSpaceSize=1G -XX:-UseCompressedClassPointers "
                + "-Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK));
        Assert.assertTrue(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED));
    ***REMOVED***

    public void testCompressedOopsDisabledHeapLess32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G));
    ***REMOVED***

    public void testCompressedOopsDisabledHeapEqual32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xmx32G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertFalse(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G));
        Assert.assertFalse(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK));
    ***REMOVED***

    public void testCompressedOopsDisabledHeapGreater32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseCompressedOops -Xmx40G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertFalse(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G));
    ***REMOVED***

    public void testCompressedOopsEnabledHeapGreater32G() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UseCompressedOops -Xmx40G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G));
        Assert.assertFalse(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G + " incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G));
    ***REMOVED***

    public void testVerboseClass() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -verbose:class -Xmx2G";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_VERBOSE_CLASS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_VERBOSE_CLASS));
    ***REMOVED***

    public void testTieredCompilation() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+TieredCompilation -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_TIERED_COMPILATION_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_TIERED_COMPILATION_ENABLED));
    ***REMOVED***

    public void testBisasedLockingDisabledNotShenandoah() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-UseBiasedLocking -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED));
    ***REMOVED***

    public void testBisasedLockingDisabledShenandoah() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+UseShenandoahGC -Xss128k -XX:-UseBiasedLocking -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertFalse(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED));
    ***REMOVED***

    public void testPrintHeapAtGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintHeapAtGC -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_HEAP_AT_GC + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_HEAP_AT_GC));
    ***REMOVED***

    public void testPrintTenuringDistribution() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintTenuringDistribution -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_TENURING_DISTRIBUTION + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_TENURING_DISTRIBUTION));
    ***REMOVED***

    public void testPrintFLSStatistics() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:PrintFLSStatistics=1 -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_FLS_STATISTICS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_FLS_STATISTICS));
    ***REMOVED***

    public void testUnlockExperimentalVMOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockExperimentalVMOptions -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED));
    ***REMOVED***

    public void testUnlockDiagnosticVMOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+UnlockDiagnosticVMOptions -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED));
    ***REMOVED***

    public void testInstrumentation() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -javaagent:/path/to/appdynamics/javaagent.jar -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_INSTRUMENTATION + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_INSTRUMENTATION));
    ***REMOVED***

    public void testExplicitGCInvokesConcurrentAndUnloadsClassesDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+DisableExplicitGC -XX:-ExplicitGCInvokesConcurrentAndUnloadsClasses";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA));
    ***REMOVED***

    public void testG1SummarizeRSetStats() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+G1SummarizeRSetStats -XX:G1SummarizeRSetStatsPeriod=1";
        VmArgumentsEvent vmArgumentEvent = new VmArgumentsEvent(jvm_args);
        HeapEvent heapEvent = new HeapEvent(
                "garbage-first heap   total 15728640K, used 2720924K [0x0000000300000000, 0x0000000300407800, "
                        + "0x00000006c0000000)");
        fel.getHeapEvents().add(heapEvent);
        fel.getVmArgumentsEvents().add(vmArgumentEvent);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_G1_SUMMARIZE_RSET_STATS_OUTPUT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_G1_SUMMARIZE_RSET_STATS_OUTPUT));
    ***REMOVED***

    public void testLogFileRotationNotEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_GC_LOG_FILE_ROTATION_NOT_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_GC_LOG_FILE_ROTATION_NOT_ENABLED));
    ***REMOVED***

    public void testLogFileNumberWithRotationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:NumberOfGCLogFiles=5 -XX:-UseGCLogFileRotation -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_GC_LOG_FILE_ROTATION_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_GC_LOG_FILE_ROTATION_DISABLED));
        Assert.assertTrue(Analysis.WARN_OPT_GC_LOG_FILE_NUM_ROTATION_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_GC_LOG_FILE_NUM_ROTATION_DISABLED));
    ***REMOVED***

    public void testLogFileSizeSmall() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:NumberOfGCLogFiles=5 -XX:+UseGCLogFileRotation "
                + "-XX:GCLogFileSize=8192";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_GC_LOG_FILE_SIZE_SMALL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_GC_LOG_FILE_SIZE_SMALL));
    ***REMOVED***

    public void testJmx() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+ManagementServer -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_JMX_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_JMX_ENABLED));
        fel.getVmArgumentsEvents().clear();
        jvm_args = "jvm_args: -Xss128k -Dcom.sun.management.jmxremote -Xms2048M";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_JMX_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_JMX_ENABLED));
    ***REMOVED***

    /**
     * Test analysis if native library being used.
     */
    public void testNative() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xms2048M -agentpath:/path/to/agent.so -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_NATIVE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_NATIVE));
    ***REMOVED***

    /**
     * Test analysis if new space &gt; old space.
     */
    public void testNewRatioInverted() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:NewSize=2g";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_NEW_RATIO_INVERTED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_NEW_RATIO_INVERTED));
    ***REMOVED***

    public void testPrintAdaptiveSizePolicyDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:-PrintAdaptiveSizePolicy";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED));
    ***REMOVED***

    public void testPrintAdaptiveSizePolicyEnabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:+PrintAdaptiveSizePolicy";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED));
    ***REMOVED***

    public void testPrintPromotionFailure() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: Xss128k -Xmx4g -XX:+PrintPromotionFailure";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_PROMOTION_FAILURE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_PROMOTION_FAILURE));
    ***REMOVED***

    public void testBytecodeBackgroundCompilationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xbatch -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED));
        fel.getVmArgumentsEvents().clear();
        jvm_args = "jvm_args: -Xss128k -XX:-BackgroundCompilation -Xms2048M";
        event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED));
    ***REMOVED***

    /**
     * Test analysis just in time (JIT) compiler disabled.
     */
    public void testCompilationDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xint -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_BYTECODE_COMPILE_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_COMPILE_DISABLED));
    ***REMOVED***

    /**
     * Test analysis compilation on first invocation enabled.
     */
    public void testCompilationOnFirstInvocation() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xcomp -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION));
    ***REMOVED***

    public void testCGroupMemoryLimit() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CGROUP_MEMORY_LIMIT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CGROUP_MEMORY_LIMIT));
        Assert.assertFalse(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED));
    ***REMOVED***

    public void testUseFastUnorderedTimeStamps() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+UnlockExperimentalVMOptions -XX:+UseFastUnorderedTimeStamps";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_FAST_UNORDERED_TIMESTAMPS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_FAST_UNORDERED_TIMESTAMPS));
        Assert.assertFalse(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED));
    ***REMOVED***

    public void testPrintClassHistogram() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintClassHistogram";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM));
    ***REMOVED***

    public void testPrintClassHistogramAfterFullGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintClassHistogramAfterFullGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC));
    ***REMOVED***

    public void testPrintClassHistogramBeforeFullGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintClassHistogramBeforeFullGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC));
    ***REMOVED***

    public void testClassUnloadingDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-ClassUnloading";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CLASS_UNLOADING_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CLASS_UNLOADING_DISABLED));
    ***REMOVED***

    public void testCmsClassUnloadingDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:-CMSClassUnloadingEnabled";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED));
    ***REMOVED***

    public void testCmsIncrementalModeWithInitatingOccupancyFraction() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseCMS -XX:+CMSIncrementalMode "
                + "-XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT));
    ***REMOVED***

    public void testCmsIncrementalMode() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseCMS -XX:+CMSIncrementalMode ";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String cpu = "CPU:total 8 (2 cores per cpu, 1 threads per core)";
        CpuInfoEvent cpuInfoEvent = new CpuInfoEvent(cpu);
        fel.getCpuInfoEvents().add(cpuInfoEvent);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_CMS_INCREMENTAL_MODE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_CMS_INCREMENTAL_MODE));
    ***REMOVED***

    public void testCmsInitatingOccupancyOnlyMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseCMS -XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING));
    ***REMOVED***

    /**
     * Test if PAR_NEW collector disabled with -XX:-UseParNewGC.
     */
    public void testUseParNewGcDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+UseCMS -XX:-UseParNewGC "
                + "-XX:CMSInitiatingOccupancyFraction=70";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CMS_PAR_NEW_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CMS_PAR_NEW_DISABLED));
    ***REMOVED***

    /**
     * Test DisableExplicitGC in combination with ExplicitGCInvokesConcurrent.
     */
    public void testDisableExplictGc() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+DisableExplicitGC -XX:+ExplicitGCInvokesConcurrent -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED));
        Assert.assertTrue(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT));
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    public void testExplictGcNotConcurrent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        String logLine = "concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, "
                + "0x00000007c0000000, 0x00000007c0000000)";
        HeapEvent heapEvent = new HeapEvent(logLine);
        fel.getHeapEvents().add(heapEvent);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_EXPLICIT_GC_NOT_CONCURRENT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_EXPLICIT_GC_NOT_CONCURRENT));
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    public void testG1MixedGCLiveThresholdPercent() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -XX:+UseG1GC -XX:G1MixedGCLiveThresholdPercent=50";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT));
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    public void testInitialNotEqualMaxHeap() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xmx4096M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_HEAP_MIN_NOT_EQUAL_MAX + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_HEAP_MIN_NOT_EQUAL_MAX));
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    public void testPrintGCApplicationConcurrentTime() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xmx4096M -XX:+PrintGCApplicationConcurrentTime";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME));
    ***REMOVED***

    /**
     * Test with PrintGCDetails disabled with -XX:-PrintGCDetails.
     */
    public void testPrintGCDetailsDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:-PrintGCDetails -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_PRINT_GC_DETAILS_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_PRINT_GC_DETAILS_DISABLED));
        Assert.assertFalse(Analysis.INFO_OPT_PRINT_GC_DETAILS_MISSING + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_GC_DETAILS_MISSING));
    ***REMOVED***

    /**
     * Test with PrintGCDetails missing.
     */
    public void testPrintGCDetailsMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_GC_DETAILS_MISSING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_GC_DETAILS_MISSING));
    ***REMOVED***

    public void testTenuringDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:MaxTenuringThreshold=0 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_TENURING_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_TENURING_DISABLED));
    ***REMOVED***

    public void testTenuringNotDefault() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:MaxTenuringThreshold=8 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE));
        fel.getVmArgumentsEvents().clear();
    ***REMOVED***

    public void testTenuringMissing() {
        FatalErrorLog fel = new FatalErrorLog();
        fel.getVmArgumentsEvents().clear();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertFalse(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE));
    ***REMOVED***

    /**
     * Test if explicit not GC handled concurrently.
     */
    public void testUseMembar() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xms2048M -Xmx4096M -XX:+UseMembar";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_USE_MEMBAR + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_USE_MEMBAR));
    ***REMOVED***

    /**
     * Test DGC redundant options analysis.
     */
    public void testDgcRedundantOptions() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -XX:+DisableExplicitGC -Dsun.rmi.dgc.client.gcInterval=14400000 "
                + "-Dsun.rmi.dgc.server.gcInterval=24400000";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT));
        Assert.assertTrue(Analysis.INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT));
    ***REMOVED***

    /**
     * Test analysis not small DGC intervals.
     */
    public void testDgcNotSmallIntervals() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL));
        Assert.assertTrue(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL));
    ***REMOVED***

    /**
     * Test analysis small DGC intervals
     */
    public void testDgcSmallIntervals() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Dsun.rmi.dgc.client.gcInterval=3599999 -Dsun.rmi.dgc.server.gcInterval=3599999";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL));
        Assert.assertTrue(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL));
    ***REMOVED***

    /**
     * Test if -XX:+PrintReferenceGC enabled
     */
    public void testPrintReferenceGC() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -Xmx2048M -XX:+PrintReferenceGC";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_REFERENCE_GC_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_REFERENCE_GC_ENABLED));
    ***REMOVED***

    /**
     * Test if -XX:+PrintStringDeduplicationStatistics enabled
     */
    public void testPrintStringDeduplicationStatistics() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+PrintStringDeduplicationStatistics -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PRINT_STRING_DEDUP_STATS_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PRINT_STRING_DEDUP_STATS_ENABLED));
    ***REMOVED***

    /**
     * Test if -XX:+TraceClassUnloading enabled
     */
    public void testTraceClassUnloading() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:+TraceClassUnloading -Xms2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_TRACE_CLASS_UNLOADING + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_TRACE_CLASS_UNLOADING));
    ***REMOVED***

    public void testSurvivorRatio() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:SurvivorRatio=6 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_SURVIVOR_RATIO + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_SURVIVOR_RATIO));
    ***REMOVED***

    public void testTargetSurvivorRatio() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss128k -XX:TargetSurvivorRatio=90 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_SURVIVOR_RATIO_TARGET + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_SURVIVOR_RATIO_TARGET));
    ***REMOVED***

    public void testThreadStackSizeTiny() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -XX:TargetSurvivorRatio=90 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_THREAD_STACK_SIZE_TINY + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_THREAD_STACK_SIZE_TINY));
    ***REMOVED***

    public void testServerFlag() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -server -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        // 64-bit is assumed
        Assert.assertTrue(Analysis.INFO_OPT_SERVER_REDUNDANT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_SERVER_REDUNDANT));
        // Specify 32-bit
        String logLine = "vm_info: OpenJDK Server VM (25.252-b09) for linux-x86 JRE (1.8.0_252-b09), built on "
                + "Apr 14 2020 14:55:17 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        fel.getAnalysis().clear();
        VmInfoEvent vmInfoEvent = new VmInfoEvent(logLine);
        fel.setVmInfoEvent(vmInfoEvent);
        fel.doAnalysis();
        Assert.assertFalse(Analysis.INFO_OPT_SERVER_REDUNDANT + " analysis incorreclty identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_SERVER_REDUNDANT));
    ***REMOVED***

    public void testCloudPerfDataDisk() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        ContainerInfoEvent containerInfoEvent = new ContainerInfoEvent("TEST");
        fel.getContainerInfoEvents().add(containerInfoEvent);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.WARN_OPT_CONTAINER_PERF_DATA_DISK + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_OPT_CONTAINER_PERF_DATA_DISK));
        Assert.assertFalse(Analysis.INFO_OPT_PERF_DATA_DISABLED + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PERF_DATA_DISABLED));
    ***REMOVED***

    public void testPerfDataDisabled() {
        FatalErrorLog fel = new FatalErrorLog();
        String jvm_args = "jvm_args: -Xss512 -XX:-UsePerfData -Xmx2048M";
        VmArgumentsEvent event = new VmArgumentsEvent(jvm_args);
        fel.getVmArgumentsEvents().add(event);
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_OPT_PERF_DATA_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_OPT_PERF_DATA_DISABLED));
    ***REMOVED***
***REMOVED***

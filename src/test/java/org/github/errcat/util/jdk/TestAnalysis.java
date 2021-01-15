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
package org.github.errcat.util.jdk;

import java.io.File;

import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.service.Manager;
import org.github.errcat.util.Constants;
import org.github.errcat.util.ErrUtil;
import org.github.errcat.util.jdk.JdkUtil.Application;
import org.github.errcat.util.jdk.JdkUtil.GarbageCollector;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestAnalysis extends TestCase {

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

    public void testLibjvm() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset10.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.ERROR_LIBJVM_SO + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_LIBJVM_SO));
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
        Assert.assertEquals("Release days diff not correct.", 116,
                ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)));
        Assert.assertEquals("Release ***REMOVED*** diff not correct.", 2,
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
        Assert.assertEquals("Application not correct.", Application.JBOSS, fel.getApplication());
        Assert.assertEquals("To stack frame not correct.", "v  ~StubRoutines::jbyte_disjoint_arraycopy",
                fel.getStackFrameTop());
        Assert.assertTrue("DirectByteBuffer class not identified in stack.",
                fel.isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER));
        Assert.assertTrue(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION));
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
        long physicalMemory = JdkUtil.convertSize(15995796, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(241892, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Physical memory free not correct.", physicalMemoryFree, fel.getJvmPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(10592252, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Swap not correct.", swap, fel.getJvmSwap());
        long swapFree = JdkUtil.convertSize(4, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Swap free not correct.", swapFree, fel.getJvmSwapFree());
        long heapMax = JdkUtil.convertSize(8192, 'M', Constants.BYTE_PRECISION);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocationYoung = JdkUtil.convertSize(2761728, 'K', Constants.BYTE_PRECISION);
        long heapAllocationOld = JdkUtil.convertSize(4838912, 'K', Constants.BYTE_PRECISION);
        long heapAllocation = heapAllocationYoung + heapAllocationOld;
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(0 + 2671671, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(8192, 'M', Constants.BYTE_PRECISION);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(471808, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(347525, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        long jvmMemory = JdkUtil.convertSize(17825792, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Jvm memory not correct.", jvmMemory, fel.getJvmMemory());
        Assert.assertEquals("Java thread count not correct.", 225, fel.getJavaThreadCount());
        Assert.assertTrue(Analysis.ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY));
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
        long physicalMemory = JdkUtil.convertSize(24609684, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long jvmMemory = JdkUtil.convertSize(18581504, 'K', Constants.BYTE_PRECISION);
        Assert.assertEquals("Jvm memory not correct.", jvmMemory, fel.getJvmMemory());
        Assert.assertTrue(Analysis.ERROR_OOME_EXTERNAL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_EXTERNAL));
    ***REMOVED***

    public void testPhysicalMemoryInsufficientJvmStartup() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset30.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("Out Of Memory Error not identified.", fel.isError("Out of Memory Error"));
        Assert.assertTrue(Analysis.ERROR_OOME_STARTUP + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_OOME_STARTUP));
    ***REMOVED***

    public void testShenandoahCollector() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset31.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(GarbageCollector.UNKNOWN + " incorrectly identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.UNKNOWN));
        Assert.assertTrue(GarbageCollector.SHENANDOAH + " collector not identified.",
                fel.getGarbageCollectors().contains(GarbageCollector.SHENANDOAH));
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

    public void testThreadStackSizeTiny() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset38.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.WARN_THREAD_STACK_SIZE_TINY + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_THREAD_STACK_SIZE_TINY));
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
        Assert.assertTrue(Analysis.ERROR_REMOTE_DEBUGGING_ENABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_REMOTE_DEBUGGING_ENABLED));
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
***REMOVED***

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

import java.io.File;

import org.github.errcat.service.Manager;
import org.github.errcat.util.Constants;
import org.github.errcat.util.Constants.Device;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.Constants.OsVersion;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Application;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.github.errcat.util.jdk.JdkUtil.JavaVendor;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestFatalErrorLog extends TestCase {

    public void testOsRhel() {
        FatalErrorLog fel = new FatalErrorLog();
        String os = "OS:Red Hat Enterprise Linux Server release 7.8 (Maipo)";
        OsEvent osEvent = new OsEvent(os);
        fel.getOsEvents().add(osEvent);
        Assert.assertEquals("OS not correct.", OsType.LINUX, fel.getOsType());
    ***REMOVED***

    public void testVendorAdoptOpenJdk() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.ADOPTOPENJDK, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorAzul() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.AZUL, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorOracle() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.25-b02) for windows-amd64 JRE (1.8.0_25-b18), "
                + "built on Oct  7 2014 14:25:37 by \"java_re\" with MS VC++ 10.0 (VS2010)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.ORACLE, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.242-b08) for linux-amd64 JRE (1.8.0_242-b08), built on "
                + "Jan 17 2020 09:36:23 by \"bob\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.UNKNOWN, fel.getJavaVendor());
    ***REMOVED***

    public void testSigSegvCompiledJavaCode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        StringBuffer causedBy = new StringBuffer("***REMOVED***  SIGSEGV (0xb) at pc=0x00007fcd2af94e64, pid=23171, tid=23172");
        causedBy.append(Constants.LINE_SEPARATOR);
        causedBy.append("***REMOVED*** C  [libcairo.so.2+0x66e64]  cairo_region_num_rectangles+0x4");
        Assert.assertEquals("Caused by incorrect.", causedBy.toString(), fel.getError());
    ***REMOVED***

    public void testSigSegvNativeCode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset2.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        StringBuffer causedBy = new StringBuffer(
                "***REMOVED***  SIGSEGV (0xb) at pc=0x0000000000000000, pid=44768, tid=0x00007f368f18d700");
        causedBy.append(Constants.LINE_SEPARATOR);
        causedBy.append("***REMOVED*** C  0x0000000000000000");
        Assert.assertEquals("Caused by incorrect.", causedBy.toString(), fel.getError());
    ***REMOVED***

    public void testHaveDebuggingSymbols() {
        FatalErrorLog fel = new FatalErrorLog();
        String headerLine = "***REMOVED*** V  [libjvm.so+0xa333a6]  ShenandoahUpdateRefsClosure::do_oop(oopDesc**)+0x26";
        HeaderEvent he = new HeaderEvent(headerLine);
        fel.getHeaderEvents().add(he);
        Assert.assertTrue("Debugging symbols not identified.", fel.haveJdkDebugSymbols());
    ***REMOVED***

    public void testNoDebuggingSymbols() {
        FatalErrorLog fel = new FatalErrorLog();
        String headerLine = "***REMOVED*** V  [libjvm.so+0xa41ea4]";
        HeaderEvent he = new HeaderEvent(headerLine);
        fel.getHeaderEvents().add(he);
        Assert.assertFalse("Debugging symbols incorrectly identified.", fel.haveJdkDebugSymbols());
    ***REMOVED***

    public void testInternalError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset3.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700"
                + Constants.LINE_SEPARATOR + "***REMOVED***  Error: ShouldNotReachHere()";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.getError());
        Assert.assertTrue("Debugging symbols incorrectly identified.", fel.haveJdkDebugSymbols());
    ***REMOVED***

    public void testRhelJdkNotRedHatBuild() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset4.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_RH_BUILD_NOT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_NOT));
        Assert.assertEquals("Time of crash not correct.", "Thu May  7 17:24:12 2020 (UTC)", fel.getCrashTime());
        Assert.assertEquals("JVM run time not correct.", "1d 7h 30m 19s", fel.getElapsedTime());
    ***REMOVED***

    public void testDebugSymbolsNoVmCodeInStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset5.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse(Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT));
        Assert.assertFalse("OS incorrectly identified as RHEL.", fel.isRhel());
        Assert.assertFalse(Analysis.WARN_DEBUG_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertFalse(Analysis.INFO_RH_BUILD_RPM + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_CENTOS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_CENTOS));
        Assert.assertFalse(Analysis.INFO_STACK_NO_VM_CODE + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_STACK_NO_VM_CODE));
    ***REMOVED***

    public void testJdkRedHatBuildUnknown() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset6.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as RHEL.", fel.isRhel());
        Assert.assertTrue("Red Hat rpm not identified.", fel.isRhRpmInstall());
        Assert.assertEquals("OS version not correct.", OsVersion.RHEL6, fel.getOsVersion());
        Assert.assertTrue(Analysis.WARN_RHEL6 + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_RHEL6));
    ***REMOVED***

    public void testSolaris() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset7.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Arch not correct.", Arch.SPARC, fel.getArch());
        Assert.assertEquals("Jdk release not correct.", "1.8.0_251-b08", fel.getJdkReleaseString());
        // No vm_info, so not possible to determine vendor
        Assert.assertEquals("Java vendor not correct.", JavaVendor.UNKNOWN, fel.getJavaVendor());
    ***REMOVED***

    public void testRhel7Jdk11() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset8.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as RHEL.", fel.isRhel());
        Assert.assertFalse(Analysis.WARN_DEBUG_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_RPM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertEquals("Jdk release not correct.", "11.0.7+10-LTS", fel.getJdkReleaseString());
        Assert.assertEquals("Java vendor not correct.", JavaVendor.RED_HAT, fel.getJavaVendor());
    ***REMOVED***

    public void testRhel8Jdk11() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset9.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as RHEL.", fel.isRhel());
        Assert.assertFalse(Analysis.WARN_DEBUG_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_RPM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertEquals("Jdk release not correct.", "11.0.8+10-LTS", fel.getJdkReleaseString());
        Assert.assertEquals("Java vendor not correct.", JavaVendor.RED_HAT, fel.getJavaVendor());
    ***REMOVED***

    public void testRhel7Jdk8() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset10.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as RHEL.", fel.isRhel());
        Assert.assertFalse(Analysis.WARN_DEBUG_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_RPM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertEquals("Jdk release not correct.", "1.8.0_131-b12", fel.getJdkReleaseString());
        Assert.assertEquals("Java vendor not correct.", JavaVendor.RED_HAT, fel.getJavaVendor());
    ***REMOVED***

    public void testWindowsOracleJdk8() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset12.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as Windows.", fel.isWindows());
        Assert.assertTrue(Analysis.WARN_DEBUG_SYMBOLS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertFalse(Analysis.INFO_RH_BUILD_WINDOWS_ZIP + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_WINDOWS_ZIP));
        Assert.assertEquals("Arch not correct.", Arch.X86_64, fel.getArch());
        Assert.assertEquals("Jdk release not correct.", "1.8.0_25-b18", fel.getJdkReleaseString());
        Assert.assertEquals("Java vendor not correct.", JavaVendor.ORACLE, fel.getJavaVendor());
    ***REMOVED***

    public void testWindowsRedHatJdk11() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset13.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as Windows.", fel.isWindows());
        Assert.assertTrue(Analysis.WARN_DEBUG_SYMBOLS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_DEBUG_SYMBOLS));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_WINDOWS_ZIP + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_WINDOWS_ZIP));
        Assert.assertEquals("Arch not correct.", Arch.X86_64, fel.getArch());
        Assert.assertEquals("Jdk release not correct.", "11.0.7+10-LTS", fel.getJdkReleaseString());
        Assert.assertEquals("Java vendor not correct.", JavaVendor.RED_HAT, fel.getJavaVendor());
    ***REMOVED***

    public void testRhBuildStringMockbuild() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.181-b13) for linux-ppc64 JRE (1.8.0_181-b13), "
                + "built on Jul 16 2018 11:33:43 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-28";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVmInfoEvent(vmInfoEvent);
        Assert.assertTrue("RH build string identified.", fel.isRedHatBuildString());
    ***REMOVED***

    public void testCurrentThread() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset20.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Current thread not correct.",
                "JavaThread \"ajp-/hostname:8109-16\" daemon [_thread_in_native, id=112672, "
                        + "stack(0x00007f11e11a2000,0x00007f11e12a3000)]",
                fel.getCurrentThread());
    ***REMOVED***

    public void testOutOfMemoryError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset21.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        StringBuilder error = new StringBuilder();
        error.append("***REMOVED*** There is insufficient memory for the Java Runtime Environment to continue.");
        error.append(Constants.LINE_SEPARATOR);
        error.append("***REMOVED*** Native memory allocation (mmap) failed to map 754974720 bytes for committing reserved memory.");
        error.append(Constants.LINE_SEPARATOR);
        error.append("***REMOVED***  Out of Memory Error (os_linux.cpp:2749), pid=25305, tid=0x00007f5ab28b7700");
        Assert.assertEquals(error.toString(), fel.getError());
    ***REMOVED***

    public void testRhWindowsReleaseWith2BuildDateTimes() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset25.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as Windows.", fel.isWindows());
        Assert.assertEquals("Arch not correct.", Arch.X86_64, fel.getArch());
        Assert.assertTrue(Analysis.INFO_RH_BUILD_WINDOWS_ZIP + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_WINDOWS_ZIP));
    ***REMOVED***

    public void testJavaThreadCount() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset26.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Java thread count not correct.", 37, fel.getJavaThreadCount());
    ***REMOVED***

    public void testMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset26.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long physicalMemory = JdkUtil.convertSize(16058700, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(1456096, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory free not correct.", physicalMemoryFree, fel.getJvmPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(8097788, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap not correct.", swap, fel.getJvmSwap());
        long swapFree = JdkUtil.convertSize(7612768, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap free not correct.", swapFree, fel.getJvmSwapFree());
        long heapMax = JdkUtil.convertSize(1024, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocation = JdkUtil.convertSize(244736 + 699392, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(103751 + 91187, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(1183744, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(155992, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(139716, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        Assert.assertEquals("CPU cores not correct.", 4, fel.getCpus());
    ***REMOVED***

    public void testShenandoah() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset31.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long physicalMemory = JdkUtil.convertSize(8388608, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(1334692, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory free not correct.", physicalMemoryFree, fel.getJvmPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(0, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap not correct.", swap, fel.getJvmSwap());
        long swapFree = JdkUtil.convertSize(0, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap free not correct.", swapFree, fel.getJvmSwapFree());
        long heapMax = JdkUtil.convertSize(5734, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocation = JdkUtil.convertSize(5734, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(3795, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(512, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(277632, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(257753, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        Assert.assertEquals("Application not correct.", Application.JBOSS_EAP7, fel.getApplication());
        Assert.assertEquals("CPU cores not correct.", 16, fel.getCpus());
        Assert.assertEquals("State not correct.", "at safepoint (normal execution)", fel.getVmState());
        Assert.assertTrue(Analysis.INFO_SWAP_DISABLED + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_SWAP_DISABLED));
        Assert.assertTrue(Analysis.ERROR_JDK8_SHENANDOAH_ROOT_UPDATER + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_JDK8_SHENANDOAH_ROOT_UPDATER));
    ***REMOVED***

    public void testG1() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset32.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Crash time not correct.", "Tue May  5 18:32:04 2020 CEST", fel.getCrashTime());
        Assert.assertEquals("Elapsed time not correct.", "0d 0h 15m 56s", fel.getElapsedTime());
        long physicalMemory = JdkUtil.convertSize(32780544, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory not correct.", physicalMemory, fel.getJvmPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(2698868, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Physical memory free not correct.", physicalMemoryFree, fel.getJvmPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(8191996, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap not correct.", swap, fel.getJvmSwap());
        long swapFree = JdkUtil.convertSize(8190972, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Swap free not correct.", swapFree, fel.getJvmSwapFree());
        long heapMax = JdkUtil.convertSize(12 * 1024, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocation = JdkUtil.convertSize(1933312, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(1030565, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(1189888, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(159168, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(147896, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        Assert.assertEquals("CPU cores not correct.", 8, fel.getCpus());
        Assert.assertEquals("Thread stack size not correct.", 5 * 1024, fel.getThreadStackMaxSize());
    ***REMOVED***

    public void testAws() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset34.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Java specification not correct.", JavaSpecification.JDK8, fel.getJavaSpecification());
        Assert.assertEquals("Rpm directory not correct.", "java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64",
                fel.getRpmDirectory());
        Assert.assertTrue("RH rpm install not identified.", fel.isRhRpmInstall());
        Assert.assertEquals("Storage device not correct.", Device.AWS_BLOCK_STORAGE, fel.getStorageDevice());
        Assert.assertTrue(Analysis.INFO_RH_BUILD_RPM + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertTrue(Analysis.ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB));
    ***REMOVED***

    public void testMeminfo() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset38.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long physicalMemory = JdkUtil.convertSize(1584737884, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System physical memory not correct.", physicalMemory, fel.getSystemPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(136528040, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System physical memory free not correct.", physicalMemoryFree,
                fel.getSystemPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(33554428, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System swap not correct.", swap, fel.getSystemSwap());
        long swapFree = JdkUtil.convertSize(33554428, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System swap free not correct.", swapFree, fel.getSystemSwapFree());
        long heapMax = JdkUtil.convertSize(220000, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocation = JdkUtil.convertSize(225041472, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(1908416, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(43008, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(41268, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(40246, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        Assert.assertEquals("Thread stack size not correct.", 0, fel.getThreadStackMaxSize());
    ***REMOVED***

    public void testTenuredGeneration() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset40.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long heapMax = JdkUtil.convertSize(3172, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocation = JdkUtil.convertSize(947392 + 2165440, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(396580 + 937560, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(1275904, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(262244, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(243180, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        Assert.assertEquals("Thread stack size not correct.", 512, fel.getThreadStackMaxSize());
    ***REMOVED***

    public void testHeapMaxGlobalFlag() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset41.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long heapMax = JdkUtil.convertSize(33554432, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long jvmMemory = JdkUtil.convertSize(33554432 + 1048576, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Jvm memory not correct.", jvmMemory, fel.getJvmMemory());
    ***REMOVED***

    public void testJBoss() {
        String logLine = "java_command: /path/to/jboss-modules.jar -Djboss.home.dir=/path/to/standalone";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
        VmArgumentsEvent event = new VmArgumentsEvent(logLine);
        FatalErrorLog fel = new FatalErrorLog();
        fel.getVmArgumentsEvents().add(event);
        Assert.assertEquals("JBoss application not identified.", Application.JBOSS, fel.getApplication());
    ***REMOVED***

    public void testWindows() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset49.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long physicalMemory = JdkUtil.convertSize(16776740, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System physical memory not correct.", physicalMemory, fel.getSystemPhysicalMemory());
        long physicalMemoryFree = JdkUtil.convertSize(674168, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System physical memory free not correct.", physicalMemoryFree,
                fel.getSystemPhysicalMemoryFree());
        long swap = JdkUtil.convertSize(20970784, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System swap not correct.", swap, fel.getSystemSwap());
        long swapFree = JdkUtil.convertSize(5252, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("System swap free not correct.", swapFree, fel.getSystemSwapFree());
    ***REMOVED***

    public void testDirectMemory() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset50.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        long heapMax = JdkUtil.convertSize(96, 'G', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap max size not correct.", heapMax, fel.getHeapMaxSize());
        long heapAllocation = JdkUtil.convertSize(96, 'G', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap allocation not correct.", heapAllocation, fel.getHeapAllocation());
        long heapUsed = JdkUtil.convertSize(78973, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Heap used not correct.", heapUsed, fel.getHeapUsed());
        long metaspaceMax = JdkUtil.convertSize(182272, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace max size not correct.", metaspaceMax, fel.getMetaspaceMaxSize());
        long metaspaceAllocation = JdkUtil.convertSize(180428, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace allocation not correct.", metaspaceAllocation, fel.getMetaspaceAllocation());
        long metaspaceUsed = JdkUtil.convertSize(176392, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Metaspace used not correct.", metaspaceUsed, fel.getMetaspaceUsed());
        long compressedClassSpace = JdkUtil.convertSize(1024, 'M', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Compressed Class Space size not correct.", compressedClassSpace,
                fel.getCompressedClassSpaceSize());
        long directMemoryMax = JdkUtil.convertSize(8, 'G', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Direct Memory mx not correct.", directMemoryMax, fel.getDirectMemoryMaxSize());
        Assert.assertEquals("Thread stack size not correct.", 1024, fel.getThreadStackMaxSize());
        Assert.assertEquals("Thread count not correct.", 720, fel.getJavaThreadCount());
        long threadMemory = JdkUtil.convertSize(1024 * 720, 'K', Constants.PRECISION_REPORTING);
        Assert.assertEquals("Thread memory not correct.", threadMemory, fel.getThreadStackMemory());
        Assert.assertEquals("Jvm memory not correct.",
                heapMax + metaspaceMax + compressedClassSpace + directMemoryMax + threadMemory, fel.getJvmMemory());
    ***REMOVED***
***REMOVED***
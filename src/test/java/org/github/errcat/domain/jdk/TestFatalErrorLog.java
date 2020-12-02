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
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.ErrUtil;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil;
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
        fel.setOs(osEvent);
        Assert.assertEquals("OS not correct.", OsType.Linux, fel.getOsType());
    ***REMOVED***

    public void testVendorAdoptOpenJdk() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.265-b01) for linux-amd64 JRE (1.8.0_265-b01), "
                + "built on Jul 28 2020 15:17:23 by \"jenkins\" with gcc 4.8.2 20140120 (Red Hat 4.8.2-15)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.ADOPTOPENJDK, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorAzul() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.AZUL, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorOracle() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.25-b02) for windows-amd64 JRE (1.8.0_25-b18), "
                + "built on Oct  7 2014 14:25:37 by \"java_re\" with MS VC++ 10.0 (VS2010)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.ORACLE, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorUnknown() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.242-b08) for linux-amd64 JRE (1.8.0_242-b08), built on "
                + "Jan 17 2020 09:36:23 by \"bob\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.UNKNOWN, fel.getJavaVendor());
    ***REMOVED***

    public void testSigSegvCompiledJavaCode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  SIGSEGV (0xb) at pc=0x00007fcd2af94e64, pid=23171, tid=23172" + Constants.LINE_SEPARATOR
                + "***REMOVED*** C  [libcairo.so.2+0x66e64]  cairo_region_num_rectangles+0x4";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.getCausedBy());
    ***REMOVED***

    public void testSigSegvNativeCode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset2.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  SIGSEGV (0xb) at pc=0x0000000000000000, pid=44768, tid=0x00007f368f18d700"
                + Constants.LINE_SEPARATOR + "***REMOVED*** C  0x0000000000000000";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.getCausedBy());
    ***REMOVED***

    public void testHaveDebuggingSymbols() {
        FatalErrorLog fel = new FatalErrorLog();
        String headerLine = "***REMOVED*** V  [libjvm.so+0xa333a6]  ShenandoahUpdateRefsClosure::do_oop(oopDesc**)+0x26";
        HeaderEvent he = new HeaderEvent(headerLine);
        fel.getHeader().add(he);
        Assert.assertTrue("Debugging symbols not identified.", fel.haveJdkDebugSymbols());
    ***REMOVED***

    public void testNoDebuggingSymbols() {
        FatalErrorLog fel = new FatalErrorLog();
        String headerLine = "***REMOVED*** V  [libjvm.so+0xa41ea4]";
        HeaderEvent he = new HeaderEvent(headerLine);
        fel.getHeader().add(he);
        Assert.assertFalse("Debugging symbols incorrectly identified.", fel.haveJdkDebugSymbols());
    ***REMOVED***

    public void testReleaseDiff() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), built on "
                + "Jul 12 2020 19:35:32 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        String os = "OS:Red Hat Enterprise Linux Server release 6.10 (Santiago)";
        OsEvent osEvent = new OsEvent(os);
        fel.setOs(osEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.RED_HAT, fel.getJavaVendor());
        Assert.assertEquals("Java specification not correct.", JavaSpecification.JDK8, fel.getJavaSpecification());
        Assert.assertEquals("Java specification not correct.", "1.8.0_262-b10", fel.getJdkReleaseString());
        Assert.assertFalse("JDK incorrectly identified as latest release.", JdkUtil.isLatestJdkRelease(fel));
        Assert.assertEquals("Release day diff not correct.", 120,
                ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)));
        Assert.assertEquals("Release number diff not correct.", 3,
                JdkUtil.getLatestJdkReleaseNumber(fel) - JdkUtil.getJdkReleaseNumber(fel));
    ***REMOVED***

    public void testInternalError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset3.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700"
                + Constants.LINE_SEPARATOR + "***REMOVED***  Error: ShouldNotReachHere()";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.getCausedBy());
        Assert.assertTrue("Debugging symbols incorrectly identified.", fel.haveJdkDebugSymbols());
    ***REMOVED***

    public void testRhelJdkNotRedHatBuild() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset4.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.INFO_RH_BUILD_NOT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_NOT));
    ***REMOVED***

    public void testDebugSymbolsNoVmCodeInStack() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset5.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertFalse("OS incorrectly identified as RHEL.", fel.isRhel());
        Assert.assertFalse(Analysis.ERROR_DEBUGGING_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DEBUGGING_SYMBOLS));
        Assert.assertFalse(Analysis.INFO_RH_BUILD_RPM + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_RPM));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_CENTOS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_CENTOS));
        Assert.assertTrue(Analysis.INFO_STACK_NO_VM_CODE + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_STACK_NO_VM_CODE));
    ***REMOVED***

    public void testJdkRedHatBuildUnknown() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset6.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue("OS not identified as RHEL.", fel.isRhel());
        Assert.assertTrue("Red Hat rpm not identified.", fel.isRhRpmInstall());
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
        Assert.assertFalse(Analysis.ERROR_DEBUGGING_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DEBUGGING_SYMBOLS));
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
        Assert.assertFalse(Analysis.ERROR_DEBUGGING_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DEBUGGING_SYMBOLS));
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
        Assert.assertFalse(Analysis.ERROR_DEBUGGING_SYMBOLS + " analysis incorrectly identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DEBUGGING_SYMBOLS));
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
        Assert.assertTrue(Analysis.ERROR_DEBUGGING_SYMBOLS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DEBUGGING_SYMBOLS));
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
        Assert.assertTrue(Analysis.ERROR_DEBUGGING_SYMBOLS + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_DEBUGGING_SYMBOLS));
        Assert.assertTrue(Analysis.INFO_RH_BUILD_WINDOWS_ZIP + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_BUILD_WINDOWS_ZIP));
        Assert.assertEquals("Arch not correct.", Arch.X86_64, fel.getArch());
        Assert.assertEquals("Jdk release not correct.", "11.0.7+10-LTS", fel.getJdkReleaseString());
        Assert.assertEquals("Java vendor not correct.", JavaVendor.RED_HAT, fel.getJavaVendor());
    ***REMOVED***
***REMOVED***
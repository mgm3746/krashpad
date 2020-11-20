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
import org.github.errcat.util.ErrUtil;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil;
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
        Assert.assertEquals("OS not correct.", "Red Hat Enterprise Linux Server release 7.8 (Maipo)", fel.getOs());
    ***REMOVED***

    public void testVendorOpenJdk() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.242-b08) for linux-amd64 JRE (1.8.0_242-b08), built on "
                + "Jan 17 2020 09:36:23 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.OpenJDK, fel.getJavaVendor());
    ***REMOVED***

    public void testVendorAzul() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE "
                + "(Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by \"zulu_re\" with gcc "
                + "4.4.7 20120313";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertEquals("JDK vendor not correct.", JavaVendor.Azul, fel.getJavaVendor());
    ***REMOVED***

    public void testSigSegvCompiledJavaCode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset1.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  SIGSEGV (0xb) at pc=0x00007fcd2af94e64, pid=23171, tid=23172" + Constants.LINE_SEPARATOR
                + "***REMOVED*** C  [libcairo.so.2+0x66e64]  cairo_region_num_rectangles+0x4";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.CausedBy());
    ***REMOVED***

    public void testSigSegvNativeCode() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset2.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  SIGSEGV (0xb) at pc=0x0000000000000000, pid=44768, tid=0x00007f368f18d700"
                + Constants.LINE_SEPARATOR + "***REMOVED*** C  0x0000000000000000";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.CausedBy());
    ***REMOVED***

    public void testHaveDebuggingSymbols() {
        FatalErrorLog fel = new FatalErrorLog();
        String headerLine = "***REMOVED*** V  [libjvm.so+0xa333a6]  ShenandoahUpdateRefsClosure::do_oop(oopDesc**)+0x26";
        HeaderEvent he = new HeaderEvent(headerLine);
        fel.getHeader().add(he);
        Assert.assertTrue("Debugging symbols not identified.", fel.haveDebuggingSymbols());
    ***REMOVED***

    public void testNoDebuggingSymbols() {
        FatalErrorLog fel = new FatalErrorLog();
        String headerLine = "***REMOVED*** V  [libjvm.so+0xa41ea4]";
        HeaderEvent he = new HeaderEvent(headerLine);
        fel.getHeader().add(he);
        Assert.assertFalse("Debugging symbols incorrectly identified.", fel.haveDebuggingSymbols());
    ***REMOVED***

    public void testReleaseDiff() {
        FatalErrorLog fel = new FatalErrorLog();
        String vmInfo = "vm_info: OpenJDK 64-Bit Server VM (25.262-b10) for linux-amd64 JRE (1.8.0_262-b10), built on "
                + "Jul 12 2020 19:35:32 by \"mockbuild\" with gcc 4.4.7 20120313 (Red Hat 4.4.7-23)";
        VmInfoEvent vmInfoEvent = new VmInfoEvent(vmInfo);
        fel.setVminfo(vmInfoEvent);
        Assert.assertFalse("JDK incorrectly identified as latest release.", JdkUtil.isLatestJdkRelease(fel));
        Assert.assertEquals("Release day diff not correct.", 114,
                ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)));
        Assert.assertEquals("Release number diff not correct.", 3,
                JdkUtil.getLatestJdkReleaseNumber(fel) - JdkUtil.getJdkReleaseNumber(fel));
    ***REMOVED***

    public void testRedHatRpmInstall() {
        FatalErrorLog fel = new FatalErrorLog();
        String logLine = "7f908ba68000-7f908c80e000 r-xp 00000000 fd:0a 140891                     "
                + "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        DynamicLibraryEvent event = new DynamicLibraryEvent(logLine);
        fel.getDynamicLibrary().add(event);
        Assert.assertTrue("Red Hat rpm install not identified.", JdkUtil.isRedHatRpmInstall(fel));
        fel.doAnalysis();
        Assert.assertTrue(Analysis.INFO_RH_RPM_INSTALL + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.INFO_RH_RPM_INSTALL));

    ***REMOVED***

    public void testInternalError() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset3.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        String causedBy = "***REMOVED***  Internal Error (ciEnv.hpp:172), pid=6570, tid=0x00007fe3d7dfd700"
                + Constants.LINE_SEPARATOR + "***REMOVED***  Error: ShouldNotReachHere()";
        Assert.assertEquals("Caused by incorrect.", causedBy, fel.CausedBy());
        Assert.assertTrue("Debugging symbols incorrectly identified.", fel.haveDebuggingSymbols());
    ***REMOVED***
***REMOVED***

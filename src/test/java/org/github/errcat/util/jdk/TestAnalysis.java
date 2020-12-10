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
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestAnalysis extends TestCase {

    public void testLatestRelease() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset14.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertTrue(Analysis.WARN_JDK_NOT_LATEST + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.WARN_JDK_NOT_LATEST));
        Assert.assertEquals("Release days diff not correct.", 100,
                ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(fel), JdkUtil.getLatestJdkReleaseDate(fel)));
        Assert.assertEquals("Release ***REMOVED*** diff not correct.", 1,
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

    public void testUndertowSslConduitNotSynchronized() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset24.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        Assert.assertEquals("Application not correct.", Application.JBOSS, fel.getApplication());
        Assert.assertEquals("To stack frame not correct.", "v  ~StubRoutines::jbyte_disjoint_arraycopy",
                fel.getStackFrameTop());
        Assert.assertTrue(Analysis.ERROR_RH_EAP7_UNDERTOW_SSL_CONDUIT + " analysis not identified.",
                fel.getAnalysis().contains(Analysis.ERROR_RH_EAP7_UNDERTOW_SSL_CONDUIT));
    ***REMOVED***
***REMOVED***

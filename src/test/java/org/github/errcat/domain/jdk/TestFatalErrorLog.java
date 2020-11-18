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
import org.github.errcat.util.jdk.JdkUtil.JdkVendor;
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
        Assert.assertEquals("JDK vendor not correct.", JdkVendor.OpenJDK, fel.getJdkVendor());
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
***REMOVED***

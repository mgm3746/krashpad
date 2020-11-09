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

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.Constants.OsVendor;
import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestUnameEvent extends TestCase {

    public void testIdentity() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.UNAME.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.UNAME);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof UnameEvent);
    ***REMOVED***

    public void testLinux() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Version not correct.", OsType.Linux, ((UnameEvent) event).getOsType());
    ***REMOVED***

    public void testSolaris() {
        String logLine = "uname:SunOS 5.11 11.4.23.69.3 sun4v";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("OS type not correct.", OsType.Solaris, ((UnameEvent) event).getOsType());
    ***REMOVED***

    public void testRedHat() {
        String logLine = "uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertEquals("Vendor not correct.", OsVendor.RedHat, ((UnameEvent) event).getOsVendor());
    ***REMOVED***
***REMOVED***

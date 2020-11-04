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
import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestVmInfoEvent extends TestCase {

    public void testJdkVersion8() {
        String logLine = "vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), "
                + "built on Oct  6 2018 06:46:09 by \"java_re\" with gcc 7.3.0";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertTrue(JdkUtil.LogEventType.JVM_INFO.toString() + " not parsed.", event instanceof JvmInfoEvent);
        Assert.assertEquals("Version not correct.", "8", ((JvmInfoEvent) event).getJdkVersion());
    ***REMOVED***

    public void testJdkVersion11() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertTrue(JdkUtil.LogEventType.JVM_INFO.toString() + " not parsed.", event instanceof JvmInfoEvent);
        Assert.assertEquals("Version not correct.", "11", ((JvmInfoEvent) event).getJdkVersion());
    ***REMOVED***

    public void testJdkRelease() {
        String logLine = "vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), "
                + "built on Oct  9 2019 18:41:22 by \"mockbuild\" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertTrue(JdkUtil.LogEventType.JVM_INFO.toString() + " not parsed.", event instanceof JvmInfoEvent);
        Assert.assertEquals("Version not correct.", "11.0.5+10-LTS", ((JvmInfoEvent) event).getJdkRelease());
    ***REMOVED***
***REMOVED***

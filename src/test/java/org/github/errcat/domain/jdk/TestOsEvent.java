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
import org.github.errcat.util.Constants;
import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestOsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.OS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof OsEvent);
    ***REMOVED***

    public void testLinux() {
        String logLine = "OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.", event instanceof OsEvent);
        Assert.assertEquals("Version not correct.", Constants.OsType.LINUX, ((OsEvent) event).getOs());
    ***REMOVED***

    public void testSolaris() {
        String logLine = "OS:                            Oracle Solaris 11.4 SPARC";
        LogEvent event = JdkUtil.parseLogLine(logLine);
        Assert.assertTrue(JdkUtil.LogEventType.OS.toString() + " not parsed.", event instanceof OsEvent);
        Assert.assertEquals("Version not correct.", Constants.OsType.SOLARIS, ((OsEvent) event).getOs());
    ***REMOVED***
***REMOVED***

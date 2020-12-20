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

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestLoggingEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Log output configuration:";
        Assert.assertTrue(JdkUtil.LogEventType.LOGGING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.LOGGING);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Log output configuration:";
        Assert.assertTrue(JdkUtil.LogEventType.LOGGING.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof LoggingEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Logging:";
        Assert.assertTrue(JdkUtil.LogEventType.LOGGING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.LOGGING);
    ***REMOVED***

    public void testConfiguration() {
        String logLine = " ***REMOVED***0: stdout all=warning uptime,level,tags";
        Assert.assertTrue(JdkUtil.LogEventType.LOGGING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.LOGGING);
    ***REMOVED***
***REMOVED***
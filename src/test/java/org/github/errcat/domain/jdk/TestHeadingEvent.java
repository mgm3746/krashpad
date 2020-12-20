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
public class TestHeadingEvent extends TestCase {

    public void testIdentity() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEADING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEADING);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEADING.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeadingEvent);
    ***REMOVED***

    public void testSystem() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEADING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEADING);
    ***REMOVED***

    public void testProcess() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.HEADING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEADING);
    ***REMOVED***

    public void testSummary() {
        String logLine = "---------------  S U M M A R Y ------------";
        Assert.assertTrue(JdkUtil.LogEventType.HEADING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEADING);
    ***REMOVED***
***REMOVED***
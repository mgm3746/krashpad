/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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
package org.github.krashpad.domain.jdk;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestCompilationEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Event: 6606.129 Thread 0x00007ff0ec201800 nmethod 21002 0x00007ff0e04fd110 code "
                + "[0x00007ff0e04fd360, 0x00007ff0e04fe1d0]";
        Assert.assertTrue(JdkUtil.LogEventType.COMPILATION.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.COMPILATION);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Event: 6606.129 Thread 0x00007ff0ec201800 nmethod 21002 0x00007ff0e04fd110 code "
                + "[0x00007ff0e04fd360, 0x00007ff0e04fe1d0]";
        Assert.assertTrue(JdkUtil.LogEventType.COMPILATION.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof CompilationEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.COMPILATION.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof CompilationEvent);
    ***REMOVED***
***REMOVED***

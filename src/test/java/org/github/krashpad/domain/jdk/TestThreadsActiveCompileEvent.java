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
public class TestThreadsActiveCompileEvent extends TestCase {

    public void testIdentity() {
        String logLine = "C2 CompilerThread0606385663 219105 %     4       com.example.SomeClass::toMethod @ 56 "
                + "(111 bytes)";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "C2 CompilerThread0606385663 219105 %     4       com.example.SomeClass::toMethod @ 56 "
                + "(111 bytes)";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof ThreadsActiveCompileEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Threads with active compile tasks:";
        Assert.assertTrue(JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE);
    ***REMOVED***
***REMOVED***

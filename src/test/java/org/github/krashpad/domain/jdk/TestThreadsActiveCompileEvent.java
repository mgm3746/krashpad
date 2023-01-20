/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestThreadsActiveCompileEvent {

    @Test
    void testC1CompilerThread() {
        String logLine = "C1 CompilerThread0 230968 20787       3       "
                + "javax.swing.text.html.parser.Parser::parseAttributeSpecificationList (798 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE,
                JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Threads with active compile tasks:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE,
                JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "C2 CompilerThread0606385663 219105 %     4       com.example.SomeClass::toMethod @ 56 "
                + "(111 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE,
                JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "C2 CompilerThread0606385663 219105 %     4       com.example.SomeClass::toMethod @ 56 "
                + "(111 bytes)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ThreadsActiveCompileEvent,
                JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***

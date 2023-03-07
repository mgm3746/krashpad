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
class TestCompilationEvent {

    @Test
    void testHeader() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.COMPILATION,
                JdkUtil.LogEventType.COMPILATION.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoEvents() {
        String priorLogLine = "***REMOVED***";
        CompilationEvent priorLogEvent = new CompilationEvent(priorLogLine);
        String logLine = "No Events";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testNumber() {
        String priorLogLine = "***REMOVED***";
        CompilationEvent priorLogEvent = new CompilationEvent(priorLogLine);
        String logLine = "Event: 0.021 Thread 0x00007fa9cc15ba60    1       3       "
                + "java.lang.Object::<init> (1 bytes)";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String priorLogLine = "***REMOVED***";
        CompilationEvent priorLogEvent = new CompilationEvent(priorLogLine);
        String logLine = "Event: 6606.129 Thread 0x00007ff0ec201800 nmethod 21002 0x00007ff0e04fd110 code "
                + "[0x00007ff0e04fd360, 0x00007ff0e04fe1d0]";
        assertTrue(JdkUtil.parseLogLine(logLine, priorLogEvent) instanceof CompilationEvent,
                JdkUtil.LogEventType.COMPILATION.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***

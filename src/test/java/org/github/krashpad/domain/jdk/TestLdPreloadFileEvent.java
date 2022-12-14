/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
class TestLdPreloadFileEvent {

    @Test
    void testDynatrace() {
        String logLine = "/$LIB/liboneagentproc.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LD_PRELOAD_FILE,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "/etc/ld.so.preload:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LD_PRELOAD_FILE,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "/$LIB/myagent.so";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LD_PRELOAD_FILE,
                JdkUtil.LogEventType.BITS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "/$LIB/myagent.so";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof LdPreloadFileEvent,
                JdkUtil.LogEventType.LD_PRELOAD_FILE.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
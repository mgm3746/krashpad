/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestLdPreloadFile {

    @Test
    void testHeader() {
        String logLine = "/etc/ld.so.preload:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LD_PRELOAD_FILE,
                JdkUtil.LogEventType.LD_PRELOAD_FILE.toString() + " not identified.");
        LdPreloadFile event = new LdPreloadFile(logLine);
        assertTrue(event.isHeader(), "Header not correct.");
    }

    @Test
    void testIdentity() {
        LdPreloadFile priorLogEvent = new LdPreloadFile("/etc/ld.so.preload:");
        String logLine = "/$LIB/myagent.so";
        assertEquals(JdkUtil.LogEventType.LD_PRELOAD_FILE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.LD_PRELOAD_FILE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        LdPreloadFile priorLogEvent = new LdPreloadFile("/etc/ld.so.preload:");
        String logLine = "/$LIB/myagent.so";
        assertEquals(JdkUtil.LogEventType.LD_PRELOAD_FILE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.LD_PRELOAD_FILE.toString() + " not identified.");
    }

    @Test
    void testPathEnvironmentVariable() {
        LdPreloadFile priorLogEvent = new LdPreloadFile("/etc/ld.so.preload:");
        String logLine = "/$LIB/liboneagentproc.so";
        assertEquals(JdkUtil.LogEventType.LD_PRELOAD_FILE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.LD_PRELOAD_FILE.toString() + " not identified.");
    }

    @Test
    void testPathFull() {
        LdPreloadFile priorLogEvent = new LdPreloadFile("/etc/ld.so.preload:");
        String logLine = "/opt/dynatrace/oneagent/agent/bin/current/linux-x86-64/liboneagentproc.so";
        assertEquals(JdkUtil.LogEventType.LD_PRELOAD_FILE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.LD_PRELOAD_FILE.toString() + " not identified.");
    }

}
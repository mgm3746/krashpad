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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestRlimit {

    @Test
    void testIdentity() {
        String logLine = "rlimit: STACK 10240k, CORE 0k, NPROC 16384, NOFILE 16384, AS infinity";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.RLIMIT,
                JdkUtil.LogEventType.RLIMIT.toString() + " not identified.");
    }

    @Test
    void testNofile() {
        String logLine = "rlimit: STACK 32768k, CORE infinity, NPROC 95259, NOFILE 10240, AS infinity";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Rlimit,
                JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.");
    }

    @Test
    void testNprocInfinity() {
        String logLine = "rlimit: STACK 8192k, CORE 0k, NPROC infinity, NOFILE 240000, AS infinity";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Rlimit,
                JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "rlimit: STACK 10240k, CORE 0k, NPROC 16384, NOFILE 16384, AS infinity";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Rlimit,
                JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.");
    }

    @Test
    void testSoftHard() {
        String logLine = "rlimit (soft/hard): STACK 8192k/infinity , CORE infinity/infinity , NPROC 62502/62502 , "
                + "NOFILE 262144/262144 , AS infinity/infinity , CPU infinity/infinity , DATA infinity/infinity , "
                + "FSIZE infinity/infinity , MEMLOCK 64k/64k";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Rlimit,
                JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.");
    }

    @Test
    void testSolaris() {
        String logLine = "rlimit: STACK 8192k, CORE infinity, NOFILE 65536, AS infinity";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Rlimit,
                JdkUtil.LogEventType.RLIMIT.toString() + " not parsed.");
    }
}
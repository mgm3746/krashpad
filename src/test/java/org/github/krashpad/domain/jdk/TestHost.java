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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestHost {

    @Test
    void testIdentity() {
        String logLine = "Host: Intel Core Processor (Skylake), 8 cores, 31G, Red Hat Enterprise Linux Workstation "
                + "release 7.4 (Maipo)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HOST,
                JdkUtil.LogEventType.HOST.toString() + " not identified.");
    }

    @Test
    void testOsString() {
        String logLine = "Host: Intel Core Processor (Skylake), 8 cores, 31G, Red Hat Enterprise Linux Workstation "
                + "release 7.4 (Maipo)";
        Host event = new Host(logLine);
        assertEquals("Red Hat Enterprise Linux Workstation release 7.4 (Maipo)", event.getOsString(),
                "OS string not correct.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Host: Intel Core Processor (Skylake), 8 cores, 31G, Red Hat Enterprise Linux Workstation "
                + "release 7.4 (Maipo)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Host,
                JdkUtil.LogEventType.HOST.toString() + " not parsed.");
    }

    @Test
    void testTruncated() {
        String logLine = "Host: ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HOST,
                JdkUtil.LogEventType.HOST.toString() + " not identified.");
    }
}
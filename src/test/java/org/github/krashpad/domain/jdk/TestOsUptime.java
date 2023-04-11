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
class TestOsUptime {

    @Test
    void testIdentity() {
        String logLine = "OS uptime: 3 days 8:33 hours";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.OS_UPTIME,
                JdkUtil.LogEventType.OS_UPTIME.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "OS uptime: 3 days 8:33 hours";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof OsUptime,
                JdkUtil.LogEventType.OS_UPTIME.toString() + " not parsed.");
    }
}

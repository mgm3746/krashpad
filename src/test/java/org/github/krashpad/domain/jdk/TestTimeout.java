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
class TestTimeout {

    @Test
    void testIdentity() {
        String logLine = "[timeout occurred during error reporting in step \"printing summary machine and OS info\"] "
                + "after 30 s.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TIMEOUT,
                JdkUtil.LogEventType.TIMEOUT.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "[timeout occurred during error reporting in step \"printing summary machine and OS info\"] "
                + "after 30 s.";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Timeout,
                JdkUtil.LogEventType.TIMEOUT.toString() + " not parsed.");
    }
}
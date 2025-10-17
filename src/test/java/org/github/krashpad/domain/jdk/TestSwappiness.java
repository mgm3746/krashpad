/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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
class TestSwappiness {

    @Test
    void testIdentity() {
        String logLine = "/proc/sys/vm/swappiness (control to define how aggressively the kernel swaps out anonymous "
                + "memory): 10";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.SWAPPINESS,
                JdkUtil.LogEventType.SWAPPINESS.toString() + " not identified.");
    }

    @Test
    void testNotAvailable() {
        String logLine = "/proc/sys/vm/swappiness (control to define how aggressively the kernel swaps out anonymous "
                + "memory): <Not Available>";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Swappiness,
                JdkUtil.LogEventType.SWAPPINESS.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "/proc/sys/vm/swappiness (control to define how aggressively the kernel swaps out anonymous "
                + "memory): 10";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Swappiness,
                JdkUtil.LogEventType.SWAPPINESS.toString() + " not parsed.");
    }
}
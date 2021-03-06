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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestTransparentHugepageEvent {

    @Test
    void testIdentity() {
        String logLine = "/sys/kernel/mm/transparent_hugepage/enabled:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "/sys/kernel/mm/transparent_hugepage/enabled:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof TransparentHugepageEvent,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE.toString() + " not parsed.");
    }

    @Test
    void testAlwaysBrackets() {
        String logLine = "[always] madvise never";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE.toString() + " not identified.");
    }

    @Test
    void testDefrag() {
        String logLine = "/sys/kernel/mm/transparent_hugepage/defrag (defrag/compaction efforts parameter):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE.toString() + " not identified.");
    }

    @Test
    void testAlwaysNoBrackets() {
        String logLine = "always defer defer+madvise [madvise] never";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE.toString() + " not identified.");
    }
}

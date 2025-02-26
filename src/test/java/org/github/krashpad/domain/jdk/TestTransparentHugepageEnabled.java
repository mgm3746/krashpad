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
class TestTransparentHugepageEnabled {

    @Test
    void testIdentity() {
        String logLine = "/sys/kernel/mm/transparent_hugepage/enabled:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED.toString() + " not identified.");
    }

    @Test
    void testModeAlways() {
        String logLine = "/sys/kernel/mm/transparent_hugepage/enabled: [always] madvise never";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED.toString() + " not identified.");
        TransparentHugepageEnabled event = new TransparentHugepageEnabled(logLine);
        assertTrue(event.isMode(), "THP mode not identified.");
        assertEquals(TransparentHugepageEnabled.MODE.ALWAYS, event.getMode(), "THP mode not correct.");
    }

    @Test
    void testModeAlwaysSeparateLine() {
        TransparentHugepageEnabled priorLogEvent = new TransparentHugepageEnabled(
                TransparentHugepageEnabled._REGEX_HEADER);
        String logLine = "[always] madvise never";
        assertTrue(
                JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED.toString() + " not identified.");
    }

    @Test
    void testModeMadvise() {
        TransparentHugepageEnabled priorLogEvent = new TransparentHugepageEnabled(
                TransparentHugepageEnabled._REGEX_HEADER);
        String logLine = "always [madvise] never";
        assertTrue(
                JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED.toString() + " not identified.");
        TransparentHugepageEnabled event = new TransparentHugepageEnabled(logLine);
        assertTrue(event.isMode(), "THP mode not identified.");
        assertEquals(TransparentHugepageEnabled.MODE.MADVISE, event.getMode(), "THP mode not correct.");
    }

    @Test
    void testModeNever() {
        TransparentHugepageEnabled priorLogEvent = new TransparentHugepageEnabled(
                TransparentHugepageEnabled._REGEX_HEADER);
        String logLine = "always madvise [never]";
        assertTrue(
                JdkUtil.identifyEventType(logLine, priorLogEvent) == JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED.toString() + " not identified.");
        TransparentHugepageEnabled event = new TransparentHugepageEnabled(logLine);
        assertTrue(event.isMode(), "THP mode not identified.");
        assertEquals(TransparentHugepageEnabled.MODE.NEVER, event.getMode(), "THP mode not correct.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "/sys/kernel/mm/transparent_hugepage/enabled:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof TransparentHugepageEnabled,
                JdkUtil.LogEventType.TRANSPARENT_HUGEPAGE_ENABLED.toString() + " not parsed.");
    }
}

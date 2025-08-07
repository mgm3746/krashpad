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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestCodeCache {

    @Test
    void testBounds() {
        String logLine = " bounds [0x00007ffb8051b000, 0x00007ffb8b60b000, 0x00007ffb8f51b000]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testCodeHeap() {
        String logLine = "CodeHeap 'non-profiled nmethods': size=128224Kb used=11542Kb max_used=14409Kb free=116681Kb";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testCompilationSmallC() {
        String logLine = " compilation: enabled";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testCompilationCapitalC() {
        String logLine = "Compilation: enabled, stopped_count=0, restarted_count=0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testFullCount() {
        String logLine = " full_count=0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "CodeCache: size=245760Kb used=145576Kb max_used=178661Kb free=100183Kb";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "CodeCache: size=245760Kb used=145576Kb max_used=178661Kb free=100183Kb";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CodeCache,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not parsed.");
    }

    @Test
    void testStoppedCount() {
        String logLine = "              stopped_count=0, restarted_count=0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

    @Test
    void testTotalBlobs() {
        String logLine = " total_blobs=24995 nmethods=23856 adapters=1049";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CODE_CACHE,
                JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.");
    }

}

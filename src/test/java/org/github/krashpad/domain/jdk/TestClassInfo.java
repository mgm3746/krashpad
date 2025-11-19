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
class TestClassInfo {

    @Test
    void testCompressedClassSpaceSize() {
        String logLine = "Compressed class space size: 1073741824 Address: 0x00000007c0000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testCompressedClassSpaceSizeJdk17() {
        String logLine = "Compressed class space mapped at: 0x0000000800c00000-0x0000000840c00000, reserved size: "
                + "1073741824";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ClassInfo,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not parsed.");
    }

    @Test
    void testEncodingRange() {
        String logLine = "Encoding Range: [0x0000000024000000 - 0x0000000124000000), (4294967296 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size "
                + "12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size "
                + "12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testKlassIdRange() {
        String logLine = "Klass ID Range:  [4096 - 1090519033) (1090514937)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testKlassRange() {
        String logLine = "Klass Range:    [0x0000000024001000 - 0x0000000065000000), (1090514944 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testNarrowKlassBase() {
        String logLine = "Narrow klass base: 0x0000000000000000, Narrow klass shift: 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testNarrowKlassPointerBits() {
        String logLine = "Narrow klass pointer bits 32, Max shift 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testNotMapped() {
        String logLine = "CDS archive(s) not mapped";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size "
                + "12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ClassInfo,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not parsed.");
    }

    @Test
    void testProtectionZone() {
        String logLine = "Protection zone: [0x0000000024000000 - 0x0000000024001000), (4096 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }

    @Test
    void testUseCompressedClassPointers() {
        String logLine = "UseCompressedClassPointers 1, UseCompactObjectHeaders 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASS_INFO,
                JdkUtil.LogEventType.CLASS_INFO.toString() + " not identified.");
    }
}
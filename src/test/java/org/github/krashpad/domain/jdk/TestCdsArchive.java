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
class TestCdsArchive {

    @Test
    void testIdentity() {
        String logLine = "CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size "
                + "12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CDS_ARCHIVE,
                JdkUtil.LogEventType.CDS_ARCHIVE.toString() + " not identified.");
    }

    @Test
    void testNotMapped() {
        String logLine = "CDS archive(s) not mapped";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CdsArchive,
                JdkUtil.LogEventType.CDS_ARCHIVE.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size "
                + "12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CdsArchive,
                JdkUtil.LogEventType.CDS_ARCHIVE.toString() + " not parsed.");
    }
}
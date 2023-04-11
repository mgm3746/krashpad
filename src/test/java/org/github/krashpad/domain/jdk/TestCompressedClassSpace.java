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
class TestCompressedClassSpace {

    @Test
    void testIdentity() {
        String logLine = "Compressed class space size: 1073741824 Address: 0x00000007c0000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.COMPRESSED_CLASS_SPACE,
                JdkUtil.LogEventType.COMPRESSED_CLASS_SPACE.toString() + " not identified.");
    }

    @Test
    void testJdk17() {
        String logLine = "Compressed class space mapped at: 0x0000000800c00000-0x0000000840c00000, reserved size: "
                + "1073741824";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CompressedClassSpace,
                JdkUtil.LogEventType.COMPRESSED_CLASS_SPACE.toString() + " not parsed.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Compressed class space size: 1073741824 Address: 0x00000007c0000000";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CompressedClassSpace,
                JdkUtil.LogEventType.COMPRESSED_CLASS_SPACE.toString() + " not parsed.");
    }
}
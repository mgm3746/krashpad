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
class TestDecodingCodeBlob {

    @Test
    void testIdentity() {
        String logLine = "Decoding CodeBlob, name: _new_array_nozero_Java, at  "
                + "[0x00007fabe8324180, 0x00007fabe83241e8]  104 bytes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.DECODING_CODE_BLOB,
                JdkUtil.LogEventType.DECODING_CODE_BLOB.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Decoding CodeBlob, name: _new_array_nozero_Java, at  "
                + "[0x00007fabe8324180, 0x00007fabe83241e8]  104 bytes";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof DecodingCodeBlob,
                JdkUtil.LogEventType.DECODING_CODE_BLOB.toString() + " not parsed.");
    }
}

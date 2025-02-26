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
class TestNativeDecoderState {

    @Test
    void testIdentity() {
        String logLine = "dbghelp: loaded successfully - version: 4.0.5 - missing functions: none";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_DECODER_STATE,
                JdkUtil.LogEventType.NATIVE_DECODER_STATE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "dbghelp: loaded successfully - version: 4.0.5 - missing functions: none";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof NativeDecoderState,
                JdkUtil.LogEventType.NATIVE_DECODER_STATE.toString() + " not parsed.");
    }

    @Test
    void testSymbolEngine() {
        String logLine = "symbol engine: initialized successfully - sym options: 0x614 - pdb path";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.NATIVE_DECODER_STATE,
                JdkUtil.LogEventType.NATIVE_DECODER_STATE.toString() + " not identified.");
    }
}
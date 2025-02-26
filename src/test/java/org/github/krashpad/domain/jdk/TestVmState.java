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
class TestVmState {

    @Test
    void testIdentity() {
        String logLine = "VM state:at safepoint (normal execution)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_STATE,
                JdkUtil.LogEventType.VM_STATE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "VM state:at safepoint (normal execution)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmState,
                JdkUtil.LogEventType.VM_STATE.toString() + " not parsed.");
    }

    @Test
    void testStateValue() {
        String logLine = "VM state:at safepoint (normal execution)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_STATE,
                JdkUtil.LogEventType.VM_STATE.toString() + " not identified.");
        VmState event = new VmState(logLine);
        assertEquals("at safepoint (normal execution)", event.getState(), "State not correct.");
    }
}

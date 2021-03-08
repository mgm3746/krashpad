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
class TestVmOperationEvent {

    @Test
    void testIdentity() {
        String logLine = "VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread "
                + "0x0000000001b2a";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_OPERATION,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread "
                + "0x0000000001b2a";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmOperationEvent,
                JdkUtil.LogEventType.VM_OPERATION.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
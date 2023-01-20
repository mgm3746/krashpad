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
class TestVmMutexEvent {

    @Test
    void testHeader() {
        String logLine = "VM Mutex/Monitor currently owned by a thread:  ([mutex/lock_event])";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_MUTEX,
                JdkUtil.LogEventType.VM_MUTEX.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "[0x00007fcbc8008420] Threads_lock - owner thread: 0x00007fcbc82b6000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.VM_MUTEX,
                JdkUtil.LogEventType.VM_MUTEX.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "[0x00007fcbc8008420] Threads_lock - owner thread: 0x00007fcbc82b6000";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof VmMutexEvent,
                JdkUtil.LogEventType.VM_MUTEX.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
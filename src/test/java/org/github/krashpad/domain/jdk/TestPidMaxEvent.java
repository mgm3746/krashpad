/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
class TestPidMaxEvent {

    @Test
    void testIdentity() {
        String logLine = "/proc/sys/kernel/pid_max (system-wide limit on number of process identifiers):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.PID_MAX,
                JdkUtil.LogEventType.PID_MAX.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testJdk17() {
        String logLine = "/proc/sys/kernel/pid_max (system-wide limit on number of process identifiers): 4194304";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.PID_MAX,
                JdkUtil.LogEventType.PID_MAX.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "/proc/sys/kernel/pid_max (system-wide limit on number of process identifiers):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof PidMaxEvent,
                JdkUtil.LogEventType.PID_MAX.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
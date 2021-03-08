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
class TestThreadsMaxEvent {

    @Test
    void testIdentity() {
        String logLine = "/proc/sys/kernel/threads-max (system-wide limit on the number of threads):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREADS_MAX,
                JdkUtil.LogEventType.THREADS_MAX.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "/proc/sys/kernel/threads-max (system-wide limit on the number of threads):";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ThreadsMaxEvent,
                JdkUtil.LogEventType.THREADS_MAX.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
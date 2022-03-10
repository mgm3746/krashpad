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
class TestUidEvent {

    @Test
    void testIdentity() {
        String logLine = "uid  : 22408 euid : 22408 gid  : 7001 egid : 7001";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.UID,
                JdkUtil.LogEventType.UID.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "uid  : 22408 euid : 22408 gid  : 7001 egid : 7001";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof UidEvent,
                JdkUtil.LogEventType.UID.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
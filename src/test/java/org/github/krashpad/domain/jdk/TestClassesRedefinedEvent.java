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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestClassesRedefinedEvent {

    @Test
    void testHeader() {
        String logLine = "Classes redefined (34 events):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASSES_REDEFINED,
                JdkUtil.LogEventType.CLASSES_REDEFINED.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "Event: 19.740 Thread 0x000055ae21eec800 redefined class name=org.jboss.modules.Main, "
                + "count=1";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASSES_REDEFINED,
                JdkUtil.LogEventType.CLASSES_REDEFINED.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNoEvents() {
        String logLine = "No events";
        assertFalse(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CLASSES_REDEFINED,
                JdkUtil.LogEventType.CLASSES_REDEFINED.toString() + " incorrectly identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Event: 19.740 Thread 0x000055ae21eec800 redefined class name=org.jboss.modules.Main, "
                + "count=1";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ClassesRedefinedEvent,
                JdkUtil.LogEventType.CLASSES_REDEFINED.toString() + " not parsed.");
    ***REMOVED***
***REMOVED***
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestActiveLocale {

    @Test
    void testAll() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_ALL=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testCollate() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_COLLATE=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testCType() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_CTYPE=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "Active Locale:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ACTIVE_LOCALE,
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testMessages() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_MESSAGES=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testMonetary() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_MONETARY=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testNumeric() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_NUMERIC=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Active Locale:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ActiveLocale,
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not parsed.");
    }

    @Test
    void testTime() {
        ActiveLocale priorLogEvent = new ActiveLocale("Active Locale:");
        String logLine = "LC_TIME=C";
        assertEquals(JdkUtil.LogEventType.ACTIVE_LOCALE, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ACTIVE_LOCALE.toString() + " not identified.");
    }

}
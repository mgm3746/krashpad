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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * NMETHOD_FLUSHES_EVENT
 * </p>
 * 
 * <p>
 * nmethod flushes information. Reported under generic <code>Event</code> prior to JDK21u4:
 * <a href="https://bugs.openjdk.org/browse/JDK-8329605">JDK-8329605: hs errfile generic events - move memory
 * protections and nmethod flushes to separate sections</a>.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Nmethod flushes (20 events):
 * Event: 39.992 Thread 0x00007f128cf57870 flushing  nmethod 0x00007f1235b2ae90
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class NmethodFlushesEvent implements LogEvent, HeaderEvent {
    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Nmethod flushes \\(\\d{1,} events\\):";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|Event: " + JdkRegEx.TIMESTAMP + " Thread "
            + JdkRegEx.ADDRESS + " flushing .+|No events)$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
    }

    /**
     * The log entry for the event.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public NmethodFlushesEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.NMETHOD_FLUSHES_EVENT;
    }

    public String getLogEntry() {
        return logEntry;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }
}

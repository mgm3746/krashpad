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
 * DLL_OPERATION_EVENT
 * </p>
 * 
 * <p>
 * Shared library information. JDK17+.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Dll operation events (18 events):
 * Event: 0.001 Loaded shared library /usr/lib/jvm/java-17-openjdk-17.0.6.0.10-3.el8_7.x86_64/lib/libjava.so
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class DllOperationEvent implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Dll operation events \\(\\d{1,} events\\):";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + DllOperationEvent._REGEX_HEADER + "|Event: " + JdkRegEx.TIMESTAMP
            + " Loaded shared library " + org.github.joa.util.JdkRegEx.FILE_PATH + ")$";

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
    public DllOperationEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.DLL_OPERATION_EVENT;
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

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * PID_MAX
 * </p>
 * 
 * <p>
 * PID max information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * /proc/sys/kernel/pid_max (system-wide limit on number of process identifiers):
 * 32768
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class PidMax implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "/proc/sys/kernel/pid_max \\(system-wide limit on number of process "
            + "identifiers\\):";

    /**
     * Regular expression for a single line (JDK17+).
     */
    public static final String _REGEX_SINGLE_LINE = "/proc/sys/kernel/pid_max \\(system-wide limit on number of process "
            + "identifiers\\): " + LogEvent.NUMBER;
    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_SINGLE_LINE + "|" + LogEvent.NUMBER + ")$";

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
    public PidMax(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.PID_MAX;
    }

    /**
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public Long getLimit() {
        Long limit = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(2) != null) {
                limit = Long.parseLong(matcher.group(2));
            } else {
                limit = Long.parseLong(matcher.group(3));
            }
        }
        return limit;
    }

    public String getLogEntry() {
        return logEntry;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches("^" + _REGEX_HEADER + "$");
        }
        return isHeader;
    }
}

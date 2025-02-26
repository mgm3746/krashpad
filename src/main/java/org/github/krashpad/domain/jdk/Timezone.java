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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * TIMEZONE
 * </p>
 * 
 * <p>
 * The timezone of the JVM crash box.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * timezone: UTC
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Timezone implements LogEvent {

    private static Pattern pattern = Pattern.compile(Timezone.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^timezone: (.+)$";

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
    public Timezone(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.TIMEZONE;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getTimezone() {
        String timezone = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            timezone = matcher.group(1);
        }
        return timezone;
    }
}

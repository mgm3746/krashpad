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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * TIME_ELAPSED_TIME
 * </p>
 * 
 * <p>
 * Combined <code>TimeEvent</code> and <code>ElapsedTimeEvent</code> in JDK11+.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TimeElapsedTime implements LogEvent {

    private static Pattern pattern = Pattern.compile(TimeElapsedTime.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^Time: (.+) elapsed time: (\\d{1,10}(\\.\\d{6})?) seconds \\((\\d{1,4}d "
            + "\\d{1,2}h \\d{1,2}m \\d{1,2}s)\\)$";

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
    public TimeElapsedTime(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.TIME_ELAPSED_TIME;
    }

    /**
     * @return The elapsed time for display purposes in #d #h #m #s format.
     */
    public String getLiteral() {
        String time = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(4) != null) {
                time = matcher.group(4);
            }
        }
        return time;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The date/time of the crash.
     */
    public String getTimeString() {
        String time = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            time = matcher.group(1);
        }
        return time;
    }

    /**
     * @return The uptime in milliseconds.
     */
    public Long getUptime() {
        Long uptime = Long.MIN_VALUE;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(2) != null) {
                BigDecimal millis = new BigDecimal(matcher.group(2)).movePointRight(3);
                millis.setScale(0, RoundingMode.HALF_EVEN);
                uptime = millis.longValue();
            }
        }
        return uptime;
    }
}

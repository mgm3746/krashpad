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
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * ELAPSED_TIME
 * </p>
 * 
 * <p>
 * How long the JVM was running before it crashed.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) Whole number of seconds:
 * </p>
 * 
 * <pre>
 * elapsed time: 855185 seconds (9d 21h 33m 4s)
 * </pre>
 * 
 * <p>
 * 2) Fractional seconds:
 * </p>
 * 
 * <pre>
 * elapsed time: 0.606413 seconds (0d 0h 0m 0s)
 * </pre>
 * 
 * <p>
 * 3) Seconds only.
 * </p>
 * 
 * <pre>
 * elapsed time: 228058 seconds
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ElapsedTime implements LogEvent {

    private static Pattern pattern = Pattern.compile(ElapsedTime.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^elapsed time: ((\\d{1,10}(\\.\\d{6})?) seconds)( \\((\\d{1,4}d \\d{1,2}h "
            + "\\d{1,2}m \\d{1,2}s)\\))?$";

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
    public ElapsedTime(String logEntry) {
        this.logEntry = logEntry;
    }

    /**
     * @return The elapsed time for display purposes in #d #h #m #s format if available, otherwise as seconds.
     */
    public String getLiteral() {
        String time = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(5) != null) {
                time = matcher.group(5);
            } else {
                time = matcher.group(1);
            }
        }
        return time;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.ELAPSED_TIME.toString();
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

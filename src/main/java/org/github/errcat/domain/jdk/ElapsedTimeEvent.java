/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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
package org.github.errcat.domain.jdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * ELAPSED_TIME
 * </p>
 * 
 * <p>
 * How long the JVM was running before it crashed.
 * </p>
 * 
 * <h3>Example Logging</h3>
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
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ElapsedTimeEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^elapsed time: \\d{1,10***REMOVED***(\\.\\d{6***REMOVED***)? seconds \\((\\d{1,4***REMOVED***d \\d{1,2***REMOVED***h \\d{1,2***REMOVED***m"
            + " \\d{1,2***REMOVED***s)\\)$";

    private static Pattern pattern = Pattern.compile(REGEX);

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
    public ElapsedTimeEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.ELAPSED_TIME.toString();
    ***REMOVED***

    public String getElapsedTime() {
        String timezone = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            timezone = matcher.group(2);
        ***REMOVED***
        return timezone;
    ***REMOVED***

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
    ***REMOVED***
***REMOVED***

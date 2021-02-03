/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * TIME_ELAPSED_TIME
 * </p>
 * 
 * <p>
 * Combined <code>TimeEvent</code> and <code>ElapsedTimeEvent</code>.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Time: Tue May  5 18:32:04 2020 CEST elapsed time: 956 seconds (0d 0h 15m 56s)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TimeElapsedTimeEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^Time: (.+) elapsed time: (\\d{1,10***REMOVED***(\\.\\d{6***REMOVED***)? seconds) \\((\\d{1,4***REMOVED***d "
            + "\\d{1,2***REMOVED***h \\d{1,2***REMOVED***m \\d{1,2***REMOVED***s)\\)$";

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
    public TimeElapsedTimeEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.TIME_ELAPSED_TIME.toString();
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

    public String getTime() {
        String time = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            time = matcher.group(1);
        ***REMOVED***
        return time;
    ***REMOVED***

    public String getElapsedTime() {
        String time = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(4) != null) {
                time = matcher.group(4);
            ***REMOVED***
        ***REMOVED***
        return time;
    ***REMOVED***
***REMOVED***

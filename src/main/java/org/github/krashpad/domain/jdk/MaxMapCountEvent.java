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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * MAX_MAP_COUNT
 * </p>
 * 
 * <p>
 * Max map count information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * /proc/sys/vm/max_map_count (maximum number of memory map areas a process may have):
 * 65530
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MaxMapCountEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^/proc/sys/vm/max_map_count \\(maximum number of memory map areas a process "
            + "may have\\):( (\\d{1,***REMOVED***))?$";

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
    public MaxMapCountEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    /**
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public Long getLimit() {
        Long limit = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(1) != null) {
                limit = Long.parseLong(matcher.group(2));
            ***REMOVED***
        ***REMOVED***
        return limit;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.MAX_MAP_COUNT.toString();
    ***REMOVED***
***REMOVED***

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
 * HOST
 * </p>
 * 
 * <p>
 * Host information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Host: Intel Core Processor (Skylake), 8 cores, 31G, Red Hat Enterprise Linux Workstation release 7.4 (Maipo)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Host implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^Host: (.+,.+,.+,(.+))?$";

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
    public Host(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.HOST;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getOsString() {
        String osString = null;
        if (logEntry != null) {
            Pattern pattern = Pattern.compile(REGEX);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find() && matcher.group(2) != null) {
                osString = matcher.group(2).trim();
            }
        }
        return osString;
    }

}

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
package org.github.krashpad.domain;

import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * TODO: Move to the associated events and remove this class.
 * 
 * <p>
 * NUMBER
 * </p>
 * 
 * <p>
 * A log line with a single number which belongs to the heading in the previous log line.
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
public class Number implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^\\d{1,}$";

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
     * The log entry for the event. Can be used for debugging purposes.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public Number(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.NUMBER;
    }

    public String getLogEntry() {
        return logEntry;
    }

}

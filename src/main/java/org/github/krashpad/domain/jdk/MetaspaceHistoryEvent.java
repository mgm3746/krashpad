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
 * METASPACE_HISTORY_EVENT
 * </p>
 * 
 * <p>
 * Metaspace history information. "GC invocations" is the number of minor collections since JVM startup. "full" is the
 * number of full GCs.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Metaspace Usage History (10 events):
 * Event: 0.301 {metaspace Before GC invocations=0 (full 0):
 *  Metaspace       used 6343K, committed 6528K, reserved 1114112K
 *   class space    used 769K, committed 832K, reserved 1048576K
 * }
 * Event: 0.303 {metaspace After GC invocations=1 (full 0):
 *  Metaspace       used 6343K, committed 6528K, reserved 1114112K
 *   class space    used 769K, committed 832K, reserved 1048576K
 * }
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MetaspaceHistoryEvent implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the beginning of the GC. For example:
     * 
     * Event: 0.301 {metaspace Before GC invocations=0 (full 0):
     */
    public static final String _REGEX_BEGIN = "Event: " + JdkRegEx.TIMESTAMP
            + " \\{metaspace Before GC invocations=\\d{1,} \\(full \\d{1,}\\):";

    /**
     * Regular expression for the end of the GC. For example:
     * 
     * Event: 0.488 {metaspace After GC invocations=2 (full 0):
     */
    public static final String _REGEX_END = "Event: " + JdkRegEx.TIMESTAMP
            + " \\{metaspace After GC invocations=\\d{1,} \\(full \\d{1,}\\):";

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Metaspace Usage History \\(\\d{1,} events\\):";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_BEGIN
            + "|  class space[ ]{1,}used \\d{1,}K, committed \\d{1,}K, reserved \\d{1,}K|"
            + " Metaspace[ ]{1,}used \\d{1,}K, committed \\d{1,}K, reserved \\d{1,}K|" + _REGEX_END
            + "|\\}|No events)$";

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
    public MetaspaceHistoryEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.METASPACE_HISTORY_EVENT;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return true if the log line is the beginning of a GC, false otherwise.
     */
    public boolean isBeginning() {
        return logEntry.matches(_REGEX_BEGIN);
    }

    /**
     * @return true if the log line is the end of a GC, false otherwise.
     */
    public boolean isEnd() {
        return logEntry.matches(_REGEX_END);
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

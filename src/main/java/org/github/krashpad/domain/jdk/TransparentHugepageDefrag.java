/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * TRANSPARENT_HUGEPAGE_DEFRAG
 * </p>
 * 
 * <p>
 * Transparent hugepage defrag information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * /sys/kernel/mm/transparent_hugepage/defrag (defrag/compaction efforts parameter):
 * always defer defer+madvise [madvise] never
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TransparentHugepageDefrag implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "/sys/kernel/mm/transparent_hugepage/defrag "
            + "\\(defrag/compaction efforts parameter\\):";

    /**
     * Regular expression for data.
     */
    private static final String _REGEX_DATA = "(\\[always\\] defer defer\\+madvise madvise never|"
            + "always \\[defer\\] defer\\+madvise madvise never|always defer \\[defer\\+madvise\\] madvise never|"
            + "always defer defer\\+madvise \\[madvise\\] never|always defer defer\\+madvise madvise \\[never\\]|"
            + "\\[always\\] madvise never)";

    /**
     * Regular expression for a single line (JDK17+).
     */
    public static final String _REGEX_SINGLE_LINE = _REGEX_HEADER + " " + _REGEX_DATA;

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_DATA + "|" + _REGEX_SINGLE_LINE + ")$";

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
    public TransparentHugepageDefrag(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.TRANSPARENT_HUGEPAGE_DEFRAG;
    }

    public String getLogEntry() {
        return logEntry;
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

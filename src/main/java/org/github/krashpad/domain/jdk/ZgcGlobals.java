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
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * ZGC_GLOBALS
 * </p>
 * 
 * <p>
 * ZGC globals information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * ZGC Globals:
 *  GlobalPhase:       2 (Relocate)
  * GlobalSeqNum:      753
  * Offset Max:        16384G (0x0000100000000000)
  * Page Size Small:   2M
  * Page Size Medium:  32M
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ZgcGlobals implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "ZGC Globals:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|"
            + " (GlobalPhase|GlobalSeqNum|Offset Max|Old Collection|Page Size (Medium|Small)|Young Collection):.+)$";

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
    public ZgcGlobals(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.ZGC_GLOBALS;
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

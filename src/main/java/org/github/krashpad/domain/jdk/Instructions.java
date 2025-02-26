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
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * INSTRUCTIONS
 * </p>
 * 
 * <p>
 * Instructions information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Instructions: (pc=0x00007fcbd05a3b71)
 * 0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03
 * 0x00007fcbd05a3b61:   00 00 00 e8 47 b3 12 00 eb 9d 0f 1f 44 00 00 55
 * 0x00007fcbd05a3b71:   8b 46 08 48 89 f2 48 89 e5 83 f8 00 7e 11 a8 01
 * 0x00007fcbd05a3b81:   75 0f c1 f8 03 5d c3 0f 1f 84 00 00 00 00 00 75
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Instructions implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "Instructions: \\(pc=" + JdkRegEx.ADDRESS + "\\)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + JdkRegEx.ADDRESS
            + ":(   [0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2} "
            + "[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2} "
            + "[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2} "
            + "[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2}[ ]{0,1}[0-9a-f?]{2})?|\\[error occurred during "
            + "error reporting \\(printing registers, top of stack, instructions near pc\\), id 0x[a-z0-9]\\])[ ]{0,}$";

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
    public Instructions(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.INSTRUCTIONS;
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

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
 * REGISTER
 * </p>
 * 
 * <p>
 * Register information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) Capital letters, 4 columns:
 * </p>
 * 
 * <pre>
 * Registers:
 * RAX=0x0000000000000001, RBX=0x00007f67383dc748, RCX=0x0000000000000004, RDX=0x00007f69b031f898
 * RSP=0x00007fcbcc676c50, RBP=0x00007fcbcc676cb0, RSI=0x0000000000000000, RDI=0x00007f69b031f898
 * R8 =0x0000000000000005, R9 =0x0000000000000010, R10=0x0000000000000000, R11=0x0000000000000000
 * R12=0x00007f673d50bfe0, R13=0x00007f6a3a004628, R14=0x00007f6a3a004620, R15=0x00007f673d50bdf0
 * RIP=0x00007fcbd05a3b71, EFLAGS=0x0000000000010293, CSGSFS=0x0000000000000033, ERR=0x0000000000000004
 *   TRAPNO=0x000000000000000e
 * </pre>
 * 
 * <p>
 * 2) Small letters, 3 columns, extra space instead of comma:
 * </p>
 * 
 * <pre>
 * Registers:
 * pc =0x00003fff7a9ddba0  lr =0x00003fff7a9ddb54  ctr=0x000000000000000f
 * r0 =0x00003fff7aa0c5d8  r1 =0x00003fff79b2ddb0  r2 =0x00003fff7b0b7e00
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Register implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Registers:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + JdkRegEx.BLANK_LINE + "|" + JdkRegEx.REGISTER
            + "([, ] " + JdkRegEx.REGISTER + "()?[, ])?( " + JdkRegEx.REGISTER + ")?(, " + JdkRegEx.REGISTER
            + ")?|[ ]{1,}(MXCSR|TRAPNO)=" + JdkRegEx.ADDRESS + "|XMM\\[\\d{1,}\\]=" + JdkRegEx.ADDRESS + " "
            + JdkRegEx.ADDRESS + ")[ ]{0,}$";

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
    public Register(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.REGISTER;
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

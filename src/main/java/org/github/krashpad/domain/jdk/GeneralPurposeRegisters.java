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
 * GENERAL_PURPOSE_REGISTERS
 * </p>
 * 
 * <p>
 * Processor state information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * General Purpose Registers:
 * --------------------------
 *   r0  = 0x0000000000000012    r1  = 0x0000000000000000  |  r0  =                      18    r1  =                       0
 *   r2  = 0x000003ff95561638    r3  = 0x0000000000000000  |  r2  =           4396256990776    r3  =                       0
 *   r4  = 0x000003fefc137708    r5  = 0x0000000000000000  |  r4  =           4393685710600    r5  =                       0
 *   r6  = 0x000003fefc28dea8    r7  = 0x000003fefc137708  |  r6  =           4393687113384    r7  =           4393685710601
 *   r8  = 0x000003ff95561638    r9  = 0x0000000000000000  |  r8  =           4396256990776    r9  =                       0
 *   r10 = 0x000003fefc28dea8    r11 = 0x000003ff95560e30  |  r10 =           4393687113384    r11 =           4396256988721
 *   r12 = 0x000003ff97e70818    r13 = 0x000003ff97c1b0d0  |  r12 =           4396300044312    r13 =           4396297597137
 *   r14 = 0x000003ff95560ef0    r15 = 0x000003ff95560e30  |  r14 =           4396256988912    r15 =           4396256988721
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class GeneralPurposeRegisters implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "General Purpose Registers:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|-{26}|  r\\d{1,}[ ]{1,}= .+)$";

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
    public GeneralPurposeRegisters(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.GENERAL_PURPOSE_REGISTERS;
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

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
 * FLOAT_REGISTERS
 * </p>
 * 
 * <p>
 * Processor state information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Float Registers:
 * ----------------
 *   fr0  = 0x0000000000000000    fr1  = 0x0000000000000000  |  fr0  =  2.172039548514854e-311    fr1  =  2.171996989160601e-311
 *   fr2  = 0x0000000000000000    fr3  = 0x0000000000000000  |  fr2  =  2.172059611647223e-311    fr3  =  2.172039549491127e-311
 *   fr4  = 0x0000000000000000    fr5  = 0x0000000000000000  |  fr4  =  2.172039548423946e-311    fr5  =  2.171996989161589e-311
 *   fr6  = 0x0000000000000000    fr7  = 0x0000000000000000  |  fr6  =  2.172039548526711e-311    fr7  =   1.525880000000000e-05
 *   fr8  = 0x0000000000000000    fr9  = 0x0000000000000000  |  fr8  =  2.170769168231045e-311    fr9  =  2.170670357995125e-311
 *   fr10 = 0x0000000000000000    fr11 = 0x0000000000000000  |  fr10 =  2.170769861298428e-311    fr11 =  2.172039548688765e-311
 *   fr12 = 0x0000000000000000    fr13 = 0x0000000000000000  |  fr12 =  2.170769861144280e-311    fr13 =  2.172039549491127e-311
 *   fr14 = 0x0000000000000000    fr15 = 0x0000000000000000  |  fr14 =  2.170670038611329e-311    fr15 =  2.170769854760952e-311
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class FloatRegisters implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Float Registers:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|-{16}|  fr\\d{1,}[ ]{1,}= .+)$";

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
    public FloatRegisters(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.FLOAT_REGISTERS;
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

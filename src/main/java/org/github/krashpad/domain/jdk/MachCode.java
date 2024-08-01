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
package org.github.krashpad.domain.jdk;

import java.util.regex.Pattern;

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * MACH_CODE
 * </p>
 * 
 * <p>
 * Machine code.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * [MachCode]
 *  0x00007ff4011cb4a0: 448b 5608 | 49bb 0000 | 0000 0800 | 0000 4d03 | d349 3bc2 | 0f85 c6b7 | f2f7 6690 | 0f1f 4000
 * 0x00007ff4011cb4c0: 8984 2400 | c0fe ff55 | 4883 ec30 | 448b 5e0c | 4183 fb01 | 0f84 7e00 | 0000 448b | 4624 48c1
 * 0x00007ff4011cb4e0: e203 4803 | 5628 488b | ea48 83c5 | 1047 8b54 | c408 4181 | fa20 3bf2 | 0075 3f4f | 8d14 c449
 * 0x00007ff4011cb500: 2b52 4849 | 0352 1845 | 8b42 3848 | 8bea 4883 | c510 478b | 54c4 0841 | 81fa 305d | f000 7528
 * 0x00007ff4011cb520: 4c8b d249 | 8b42 1048 | 83c4 305d | 493b a740 | 0300 000f | 874f 0000 | 00c3 bede | ffff ff44
 * 0x00007ff4011cb540: 8904 24e8 | b842 f3f7 | bede ffff | ff44 8904 | 2466 90e8 | a842 f3f7 | 4889 1424 | 4889 7424
 * 0x00007ff4011cb560: 0844 895c | 2410 be45 | ffff ffe8 | 9042 f3f7 | bef6 ffff | ff66 90e8 | 8442 f3f7 | bef6 ffff
 * 0x00007ff4011cb580: ff66 90e8 | 7842 f3f7 | 49ba 2cb5 | 1c01 f47f | 0000 4d89 | 9758 0300 | 00e9 e230 | f3f7 f4f4
 * 0x00007ff4011cb5a0: e95b 25fd | f7e8 0000 | 0000 4883 | 2c24 05e9 | 6c45 f3f7 | f4f4 f4f4
 * [/MachCode]
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MachCode implements LogEvent, HeaderEvent, ThrowAwayEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "^\\[MachCode\\]$";

    public static final Pattern PATTERN = Pattern.compile(MachCode.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|  # .+|[ ]{22};.+|Compiled method .+|\\[Entry Point\\]|\\[Exception Handler\\]| dependencies .+|"
            + " handler table .+|\\[/MachCode\\]| main code .+| metadata .+| nul chk table .+| relocation .+|"
            + " scopes (data|pcs) .+||\\[Stub Code\\]| stub code .+| total in heap .+|\\[Verified Entry Point\\]|  "
            + JdkRegEx.ADDRESS + ": .+)$";

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
    public MachCode(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.MACH_CODE;
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

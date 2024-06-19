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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.CompressedOopMode;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * HEAP_ADDRESS
 * </p>
 * 
 * <p>
 * Head address information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeapAddress implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^([h|H]eap address: " + JdkRegEx.ADDRESS + ", size: (\\d{1,}) MB|"
            + "\\[error occurred during error reporting \\(printing compressed oops mode[\\)]{0,1}, id 0x).*$";

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
    public HeapAddress(String logEntry) {
        this.logEntry = logEntry;
    }

    /**
     * @return The compressed oop mode.
     */
    public final CompressedOopMode getCompressedOopMode() {
        CompressedOopMode compressedOopMode = CompressedOopMode.UNKNOWN;
        if (logEntry.matches(".*Compressed Oops mode: 32-bit.*")) {
            compressedOopMode = CompressedOopMode.BIT32;
        } else if (logEntry.matches(".*Compressed Oops mode: Zero based.*")) {
            compressedOopMode = CompressedOopMode.ZERO;
        } else if (logEntry.matches(".*Compressed Oops mode: Non-zero based.*")) {
            compressedOopMode = CompressedOopMode.NON_ZERO;
        }
        return compressedOopMode;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.HEAP_ADDRESS;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The heap size reserved in bytes.
     */
    public final Long getSize() {
        long initialSize = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(1) != null) {
            initialSize = JdkUtil.convertSize(Long.parseLong(matcher.group(7)), 'M', 'B');
        }
        return initialSize;
    }

    /**
     * @return The heap starting address in bytes.
     */
    public final Long getStartingAddress() {
        long startingAddress = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(1) != null) {
            return JdkMath.convertHexToDecimal(matcher.group(2));
        }
        return startingAddress;
    }
}

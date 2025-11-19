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
 * CLASS_INFO
 * </p>
 * 
 * <p>
 * Class information that seems to be grouped together.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * It began as just Class data archive(s) information. Reference: <a href="https://openjdk.java.net/jeps/350">JEP 350:
 * Dynamic CDS Archives</a>. More and more entries have been added of time and JDK releases.
 * </p>
 * 
 * <p>
 * JDK 8/11:
 * </p>
 *
 * <pre>
 * CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size 12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.
 * Compressed class space size: 1073741824 Address: 0x00000007c0000000
 * </pre>
 * 
 * <p>
 * 2) JDK17:
 * </p>
 * 
 * <pre>
 * CDS archive(s) mapped at: [0x0000000800000000-0x0000000800be2000-0x0000000800be2000), size 12460032, SharedBaseAddress: 0x0000000800000000, ArchiveRelocationMode: 0.
 * Compressed class space mapped at: 0x0000000800c00000-0x0000000840c00000, reserved size: 1073741824
 * </pre>
 * 
 * <p>
 * JDK21:
 * </p>
 * 
 * <pre>
 * CDS archive(s) mapped at: [0x00007f4fcb000000-0x00007f4fcbc9f000-0x00007f4fcbc9f000), size 13234176, SharedBaseAddress: 0x00007f4fcb000000, ArchiveRelocationMode: 1.
 * Compressed class space mapped at: 0x00007f4fcc000000-0x00007f500c000000, reserved size: 1073741824
 * Narrow klass base: 0x00007f4fcb000000, Narrow klass shift: 0, Narrow klass range: 0x100000000
 * </pre>
 * 
 * <p>
 * JDK25:
 * </p>
 * 
 * <pre>
 * CDS archive(s) mapped at: [0x0000000024000000-0x0000000024d8d000-0x0000000024d8d000), size 14209024, SharedBaseAddress: 0x0000000024000000, ArchiveRelocationMode: 1.
 * Compressed class space mapped at: 0x0000000025000000-0x0000000065000000, reserved size: 1073741824
 * UseCompressedClassPointers 1, UseCompactObjectHeaders 0
 * Narrow klass pointer bits 32, Max shift 3
 * Narrow klass base: 0x0000000024000000, Narrow klass shift: 0
 * Encoding Range: [0x0000000024000000 - 0x0000000124000000), (4294967296 bytes)
 * Klass Range:    [0x0000000024001000 - 0x0000000065000000), (1090514944 bytes)
 * Klass ID Range:  [4096 - 1090519033) (1090514937)
 * Protection zone: [0x0000000024000000 - 0x0000000024001000), (4096 bytes)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ClassInfo implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "CDS archive\\(s\\) (mapped at:.*|not mapped)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|(Compressed class space (size|mapped at): .*|Encoding Range: .*|Klass (ID Range|Range): .*|"
            + "Narrow klass base: .*|Narrow klass pointer bits .*|Protection zone: .*|"
            + "UseCompressedClassPointers .*))$";

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
    public ClassInfo(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.TOP_OF_STACK;
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

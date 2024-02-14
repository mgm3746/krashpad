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
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * MEMORY
 * </p>
 * 
 * <p>
 * Memory information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) Linux:
 * </p>
 * 
 * <pre>
 * Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)
 * </pre>
 * 
 * <p>
 * 2) Windows JDK8:
 * </p>
 * 
 * <p>
 * Oddly enough, "swap" total is _not_ the swap (Windows page file) size. It is `ullTotalPageFile`, the current
 * committed memory limit for the system or the current process, whichever is smaller (i.e. total virtual memory).
 * Corresponds to the linux `CommitLimit`.
 * </p>
 * 
 * <p>
 * Oddly enough, "swap" free is _not_ the swap (Windows page file) free space. It is `ullAvailPageFile`, the maximum
 * amount of memory the current process can commit, equal to or smaller than the system-wide commit limit (i.e. free
 * virtual memory). Corresponds to the linux `CommitLimit` - `Commit_AS`.
 * <p>
 * 
 * <p>
 * See <a href="https://bugs.openjdk.org/browse/JDK-8202427">JDK-8202427: Enhance os::print_memory_info on Windows</a>
 * </p>
 * 
 * <pre>
 * Memory: 4k page, physical 83885040k(45900432k free), swap 85982192k(42352392k free)
 * </pre>
 * 
 * <p>
 * 3) Windows JDK11+:
 * </p>
 * 
 * <pre>
 * Memory: 4k page, system-wide physical 16383M (5994M free)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Memory implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     * 
     * On Windows, the swap total value includes physical memory, but
     */
    public static final String _REGEX_HEADER = "^Memory: (4|8|64)k page,( system-wide)? physical " + JdkRegEx.SIZE
            + "[ ]{0,1}\\(" + JdkRegEx.SIZE + " free\\)(, swap " + JdkRegEx.SIZE + "\\(" + JdkRegEx.SIZE
            + " free\\))?$";

    public static final Pattern PATTERN = Pattern.compile(Memory.REGEX);

    /**
     * Regular expression defining the logging.
     * 
     * commit charge: On Windows, the memory allocated to a process that cannot be shared with other processes. A good
     * approximation of process size.
     * 
     * WorkingSet: On Windows, the set of process pages in the virtual address space that is in physical memory (RAM).
     * 
     * <pre>
     * TotalPageFile size 20479M (AvailPageFile size 7532M)
     * </pre>
     * 
     * <p>
     * TotalPageFile: Oddly enough, this is _not_ the swap (Windows page file) size. It is `ullTotalPageFile`, the
     * current committed memory limit for the system or the current process, whichever is smaller (i.e. total virtual
     * memory). Corresponds to the linux `CommitLimit`.`ullTotalPageFile` On Windows, swap (disk) memory.
     * </p>
     * 
     * <p>
     * AvailPageFile: Oddly enough, this is _not_ the swap (Windows page file) free space. It is `ullAvailPageFile`, the
     * maximum amount of memory the current process can commit, equal to or smaller than the system-wide commit limit
     * (i.e. free virtual memory). Corresponds to the linux `CommitLimit` - `Commit_AS`.
     * </p>
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|current process (commit charge|WorkingSet).+|Page Sizes:.+|TotalPageFile size (\\d{1,})M "
            + "\\(AvailPageFile size (\\d{1,})M\\))$";

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
    public Memory(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.MEMORY.toString();
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

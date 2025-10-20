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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * MEMORY
 * </p>
 * 
 * <p>
 * Memory information for container (if memory limited) or OS.
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
 * TotalPageFile size 18943M (AvailPageFile size 1M)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Memory implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     * 
     * On Windows, oddly enough, "swap" is _not_ the swap (Windows page file) size:
     * 
     * 1) "swap" total is `ullTotalPageFile`, the commit limit for the system or the current process, whichever is
     * smaller (i.e. total virtual memory), which corresponds to the linux `CommitLimit`.
     * 
     * 2) "swap" free is `ullAvailPageFile`, the maximum amount of memory the current process can commit (i.e. free
     * virtual memory), which corresponds to the linux `CommitLimit` - `Commit_AS`.
     * 
     * By default, the Windows page file size is not fixed, but managed dynamically based on needs. Therefore, it is not
     * a hard limit (as it is with Linux, where swap space is fixed). It is possible to override the default behavior
     * and set a fixed page file size, or disable it entirely. The JVM has no way of knowing if the page file is dynamic
     * (default), fixed, or disabled.
     * 
     * Reference: https://learn.microsoft.com/en-us/windows/win32/api/sysinfoapi/ns-sysinfoapi-memorystatusex
     */
    public static final String _REGEX_HEADER = "^Memory: (4|8|64)k page(,( system-wide)? physical " + JdkRegEx.SIZE
            + "[ ]{0,1}\\(" + JdkRegEx.SIZE + " free\\))?(, swap " + JdkRegEx.SIZE + "\\(" + JdkRegEx.SIZE
            + " free\\))?$";

    /**
     * Regular expression for Windows page file information in JDK11+.
     * 
     * The oddness in JDK8, where "swap" is not actually swap (Windows page file), has been carried forward, and now
     * "PageFile" is not actually the Windows page file.
     * 
     * "TotalPageFile" is `ullTotalPageFile`, the commit limit for the system or the current process, whichever is
     * smaller (i.e. total virtual memory), which corresponds to the linux `CommitLimit`.
     * 
     * "AvailPageFile" is `ullAvailPageFile`, the maximum amount of memory the current process can commit (i.e. free
     * virtual memory), which corresponds to the linux `CommitLimit` - `Commit_AS`.
     * 
     * Reference: https://bugs.openjdk.org/browse/JDK-8202427
     */
    public static final String _REGEX_PAGE_FILE = "^TotalPageFile size " + JdkRegEx.SIZE + " \\(AvailPageFile size "
            + JdkRegEx.SIZE + "\\)$";

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
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_PAGE_FILE
            + "|current process (commit charge|WorkingSet).+|Page Sizes: .+|"
            + "\\[error occurred during error reporting \\(printing memory info\\).+)$";

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

    @Override
    public LogEventType getEventType() {
        return LogEventType.MEMORY;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * The Windows "AvailPageFile" value. Oddly enough, this is the available commit limit, not free page file space.
     * 
     * @return The "AvailPageFile" value, in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getPageFileFree() {
        long pageFileFree = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(_REGEX_PAGE_FILE);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(4) != null && matcher.group(6) != null) {
            pageFileFree = JdkUtil.convertSize(Long.parseLong(matcher.group(4)), matcher.group(6).charAt(0), 'B');
        }
        return pageFileFree;
    }

    /**
     * The Windows "TotalPageFile" value. Oddly enough, this is the commit limit, not the Windows page file size.
     * 
     * @return The "TotalPageFile" value, in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getPageFileTotal() {
        long pageFileTotal = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(_REGEX_PAGE_FILE);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(1) != null && matcher.group(3) != null) {
            pageFileTotal = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), matcher.group(3).charAt(0), 'B');
        }
        return pageFileTotal;
    }

    /**
     * The physical "free" value. This is the total physical free memory (e.g. /proc/meminfo MemFree).
     * 
     * @return The physical "free" value, in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getPhysicalFree() {
        long swapFree = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(_REGEX_HEADER);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(7) != null && matcher.group(9) != null) {
            swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(7)), matcher.group(9).charAt(0), 'B');
        }
        return swapFree;
    }

    /**
     * The "physical" value. This is the total physical memory (e.g. /proc/meminfo MemTotal).
     * 
     * @return The "physical" value, in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getPhysicalTotal() {
        long swap = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(_REGEX_HEADER);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(4) != null && matcher.group(6) != null) {
            swap = JdkUtil.convertSize(Long.parseLong(matcher.group(4)), matcher.group(6).charAt(0), 'B');
        }
        return swap;
    }

    /**
     * The swap "free" value. This is the actual free swap space, except on Windows, where it is, oddly enough, the
     * available commit limit.
     * 
     * @return The swap "free" value, in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getSwapFree() {
        long swapFree = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(_REGEX_HEADER);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(14) != null && matcher.group(16) != null) {
            swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(14)), matcher.group(16).charAt(0), 'B');
        }
        return swapFree;
    }

    /**
     * The "swap" value. This is the actual total swap size, except on Windows, where it is, oddly enough, the commit
     * limit.
     * 
     * @return The "swap" value, in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getSwapTotal() {
        long swap = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(_REGEX_HEADER);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find() && matcher.group(11) != null && matcher.group(13) != null) {
            swap = JdkUtil.convertSize(Long.parseLong(matcher.group(11)), matcher.group(13).charAt(0), 'B');
        }
        return swap;
    }

    public boolean isErrorOccurredDuringErrorReporting() {
        return logEntry.startsWith("[error occurred during error reporting");
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

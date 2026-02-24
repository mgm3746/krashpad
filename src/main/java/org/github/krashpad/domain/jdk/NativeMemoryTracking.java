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
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * NATIVE_MEMORY_TRACKING
 * </p>
 * 
 * <p>
 * Native memory tracking (NMT) information included when enabled (e.g. -XX:NativeMemoryTracking=detail).
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Native Memory Tracking:
 * 
 * Total: reserved=18369225KB, committed=17150661KB
 * -                 Java Heap (reserved=8388608KB, committed=8388608KB)
 *                             (mmap: reserved=8388608KB, committed=8388608KB)
 * 
 * -                     Class (reserved=1236886KB, committed=214626KB)
 *                             (classes #32343)
 *                             (malloc=6038KB #64830) 
 *                             (mmap: reserved=1230848KB, committed=208588KB)
 * 
 * -                    Thread (reserved=6278193KB, committed=6278193KB)
 *                             (thread #6079)
 *                             (stack: reserved=6250236KB, committed=6250236KB)
 *                             (malloc=20833KB #36474) 
 *                             (arena=7124KB #12155)
 * 
 * -                      Code (reserved=264372KB, committed=84452KB)
 *                             (malloc=14772KB #22729) 
 *                             (mmap: reserved=249600KB, committed=69680KB)
 * 
 * -                        GC (reserved=417612KB, committed=417612KB)
 *                             (malloc=73548KB #84041) 
 *                             (mmap: reserved=344064KB, committed=344064KB)
 * 
 * -                  Compiler (reserved=26862KB, committed=26862KB)
 *                             (malloc=13065KB #14908) 
 *                             (arena=13797KB #17)
 * 
 * -                  Internal (reserved=1385758KB, committed=1385758KB)
 *                             (malloc=1385726KB #173671) 
 *                             (mmap: reserved=32KB, committed=32KB)
 * 
 * -                    Symbol (reserved=36100KB, committed=36100KB)
 *                             (malloc=33694KB #379033) 
 *                             (arena=2406KB #1)
 * 
 * -    Native Memory Tracking (reserved=13441KB, committed=13441KB)
 *                             (malloc=823KB #9720) 
 *                             (tracking overhead=12618KB)
 * 
 * -               Arena Chunk (reserved=305008KB, committed=305008KB)
 *                             (malloc=305008KB)
 * 
 * -                   Unknown (reserved=16384KB, committed=0KB)
 *                             (mmap: reserved=16384KB, committed=0KB)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class NativeMemoryTracking implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Native Memory Tracking:";

    public static final String[] CATEGORY = { "Arena Chunk", "Arguments", "Class", "Code", "Compiler", "GC", "Internal",
            "Java Heap", "Logging", "Metaspace", "Module", "Native Memory Tracking", "Object Monitors", "Other",
            "Safepoint", "Shared class space", "Serviceability", "String Deduplication", "Symbol", "Synchronization",
            "Synchronizer", "Thread", "Tracing", "Unknown" };

    public static final String _REGEX_CATEGORY = "-[ ]{1,}(" + String.join("|", CATEGORY)
            + ") \\(reserved=\\d{1,}KB, committed=(\\d{1,})KB(, readonly=\\d{1,}KB)?\\)";

    public static final String _REGEX_TOTAL = "Total: reserved=\\d{1,}KB, committed=(\\d{1,})KB";

    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_CATEGORY + "|" + _REGEX_TOTAL + "|"
    // parentheses =
            + "[ ]{1,}\\([ ]{0,}(arena|free|malloc|mmap: reserved|reserved|stack: reserved|tracking overhead|used|"
            + "waste)=.*|"
            // parentheses :
            + "[ ]{1,}\\([ ]{0,}(Class space|Metadata):.*|"
            // parenthesis #
            + "[ ]{1,}\\([ ]{0,}(classes|instance classes|thread|threads) #.*|"
            // no parenthesis :
            + "[ ]{1,}(malloc|mmap): .*|"
            // No beginning spaces
            + "(\\(Omitting categories|MallocLimit:|pre-init mallocs:|Preinit state:).*"
            //
            + ")$";

    public static final Pattern PATTERN = Pattern.compile(REGEX);

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
    public NativeMemoryTracking(String logEntry) {
        this.logEntry = logEntry;
    }

    /**
     * @return memory category.
     */
    public String getCategory() {
        String category = null;
        if (isCategory()) {
            Pattern pattern = Pattern.compile(_REGEX_CATEGORY);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                category = matcher.group(1);
            }
        }
        return category;
    }

    /**
     * @return committed memory (kilobytes).
     */
    public int getCommitted() {
        int committed = Integer.MIN_VALUE;
        if (isCategory()) {
            Pattern pattern = Pattern.compile(_REGEX_CATEGORY);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                committed = Integer.valueOf(matcher.group(2));
            }
        }
        return committed;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.NATIVE_MEMORY_TRACKING;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return Total committed memory (kilobytes).
     */
    public int getTotalCommitted() {
        int totalCommitted = Integer.MIN_VALUE;
        if (isTotal()) {
            Pattern pattern = Pattern.compile(_REGEX_TOTAL);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                totalCommitted = Integer.valueOf(matcher.group(1));
            }
        }
        return totalCommitted;
    }

    /**
     * @return true if the log line is a memory category, false otherwise.
     */
    public boolean isCategory() {
        boolean isCategory = false;
        if (this.logEntry != null) {
            isCategory = logEntry.matches(_REGEX_CATEGORY);
        }
        return isCategory;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }

    /**
     * @return true if the log line is total memory committed, false otherwise.
     */
    public boolean isTotal() {
        boolean isTotal = false;
        if (this.logEntry != null) {
            isTotal = logEntry.matches(_REGEX_TOTAL);
        }
        return isTotal;
    }
}

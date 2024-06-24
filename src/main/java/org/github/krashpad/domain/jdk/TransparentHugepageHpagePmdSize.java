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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * TRANSPARENT_HUGEPAGE_HPAGE_PMD_SIZE
 * </p>
 * 
 * <p>
 * Transparent hugepage hpage_pmd_size information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * /sys/kernel/mm/transparent_hugepage/hpage_pmd_size: 2097152
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TransparentHugepageHpagePmdSize implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "/sys/kernel/mm/transparent_hugepage/hpage_pmd_size:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_HEADER + " (\\d{1,}))$";

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
    public TransparentHugepageHpagePmdSize(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.TRANSPARENT_HUGEPAGE_HPAGE_PMD_SIZE;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return THP hpage_pmd_size.
     */
    public long getSize() {
        long size = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(TransparentHugepageHpagePmdSize.REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(2) != null) {
                size = Long.parseLong(matcher.group(2));
            }
        }
        return size;
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
     * @return True if size setting, false otherwise.
     */
    public boolean isSize() {
        boolean isSize = false;
        Pattern pattern = Pattern.compile(TransparentHugepageHpagePmdSize.REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(2) != null) {
                isSize = true;
            }
        }
        return isSize;
    }

}

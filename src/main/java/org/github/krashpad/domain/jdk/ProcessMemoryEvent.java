/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * PROCESS_MEMORY
 * </p>
 * 
 * <p>
 * Process memory information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * 
 * <pre>
 * Process Memory:
 * Virtual Size: 11384200K (peak: 19821176K)
 * Resident Set Size: 9169564K (peak: 9198848K) (anon: 9144372K, file: 25192K, shmem: 0K)
 * Swapped out: 0K
 * C-Heap outstanding allocations: 323983K (may have wrapped)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ProcessMemoryEvent implements LogEvent {

    public static final Pattern PATTERN = Pattern.compile(ProcessMemoryEvent.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + ProcessMemoryEvent.REGEX_HEADER
            + "|glibc malloc tunables|C-Heap outstanding allocations:|Resident Set Size:|Swapped out:|"
            + "Virtual Size:).*$";

    /**
     * Regular expression for the header.
     */
    public static final String REGEX_HEADER = "^Process Memory:$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
    ***REMOVED***

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
    public ProcessMemoryEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.PROCESS_MEMORY.toString();
    ***REMOVED***

    /**
     * @return true if the log line is the header false otherwise.
     */
    public boolean isHeader() {
        return logEntry.matches(REGEX_HEADER);
    ***REMOVED***

***REMOVED***

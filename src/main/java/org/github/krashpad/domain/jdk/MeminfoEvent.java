/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * MEMINFO
 * </p>
 * 
 * <p>
 * Memory information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * MemTotal:       65305448 kB
 * MemFree:        30813412 kB
 * Buffers:          817980 kB
 * Cached:          4248912 kB
 * SwapCached:            0 kB
 * Active:         27699800 kB
 * Inactive:        1601588 kB
 * Active(anon):   24235196 kB
 * Inactive(anon):    47860 kB
 * Active(file):    3464604 kB
 * Inactive(file):  1553728 kB
 * ***REMOVED***
 * ***REMOVED***
 * SwapTotal:       4194300 kB
 * SwapFree:        4194300 kB
 * Dirty:              1732 kB
 * ***REMOVED***
 * AnonPages:      24234268 kB
 * Mapped:           378036 kB
 * Shmem:             48464 kB
 * Slab:            4564604 kB
 * SReclaimable:    4475112 kB
 * SUnreclaim:        89492 kB
 * KernelStack:       67264 kB
 * PageTables:        72112 kB
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * CommitLimit:    36847024 kB
 * Committed_AS:   32863880 kB
 * VmallocTotal:   34359738367 kB
 * VmallocUsed:      293044 kB
 * VmallocChunk:   34359435764 kB
 * ***REMOVED***
 * AnonHugePages:  20576256 kB
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * Hugepagesize:       2048 kB
 * DirectMap4k:        8192 kB
 * DirectMap2M:     2088960 kB
 * DirectMap1G:    65011712 kB
 * ***REMOVED***
 * ***REMOVED***
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MeminfoEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "***REMOVED***";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER
            + "|Active|Anon|Bounce|Buffers|Cached|Cma|Commit|Direct|Dirty|FileHugePages|FilePmdMapped|Hardware|Huge|"
            + "Inactive|Kernel|Mapped|MemAvailable|MemFree|MemTotal|Mlocked|NFS|Page|Percpu|[KS]Reclaimable|Shmem|Slab|"
            + "SUnreclaim|Swap|Unevictable|Vmalloc|Write).*$";

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
    public MeminfoEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.MEMINFO.toString();
    ***REMOVED***

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
***REMOVED***

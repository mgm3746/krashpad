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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * MEMINFO
 * </p>
 * 
 * <p>
 * Memory information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * /proc/meminfo:
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
 * Unevictable:           0 kB
 * Mlocked:               0 kB
 * SwapTotal:       4194300 kB
 * SwapFree:        4194300 kB
 * Dirty:              1732 kB
 * Writeback:             0 kB
 * AnonPages:      24234268 kB
 * Mapped:           378036 kB
 * Shmem:             48464 kB
 * Slab:            4564604 kB
 * SReclaimable:    4475112 kB
 * SUnreclaim:        89492 kB
 * KernelStack:       67264 kB
 * PageTables:        72112 kB
 * NFS_Unstable:          0 kB
 * Bounce:                0 kB
 * WritebackTmp:          0 kB
 * CommitLimit:    36847024 kB
 * Committed_AS:   32863880 kB
 * VmallocTotal:   34359738367 kB
 * VmallocUsed:      293044 kB
 * VmallocChunk:   34359435764 kB
 * HardwareCorrupted:     0 kB
 * AnonHugePages:  20576256 kB
 * HugePages_Total:       0
 * HugePages_Free:        0
 * HugePages_Rsvd:        0
 * HugePages_Surp:        0
 * Hugepagesize:       2048 kB
 * DirectMap4k:        8192 kB
 * DirectMap2M:     2088960 kB
 * DirectMap1G:    65011712 kB
 * FileHugePages:         0 kB
 * FilePmdMapped:         0 kB
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Meminfo implements LogEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "/proc/meminfo:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|Active|Anon|Bounce|Buffers|Cached|Cma|Commit|Direct|Dirty|FileHugePages|FilePmdMapped|Hardware|Huge|"
            + "Inactive|Kernel|KReclaimable|Mapped|MemAvailable|MemFree|MemTotal|Mlocked|NFS|Page|Percpu|"
            + "SecPageTables|SReclaimable|Shmem|Slab|SUnreclaim|SwapCached|SwapFree|SwapTotal|Unevictable|Vmalloc|"
            + "Write|Zswap).*$";

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
    public Meminfo(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.MEMINFO;
    }

    public String getLogEntry() {
        return logEntry;
    }

}

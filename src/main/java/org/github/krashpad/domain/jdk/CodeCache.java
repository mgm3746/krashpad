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
 * CODE_CACHE
 * </p>
 * 
 * <p>
 * Code cache information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) Single block of memory (prior to JDK8u40).
 * </p>
 * 
 * <p>
 * Example logging:
 * </p>
 * 
 * <pre>
 * CodeCache: size=245760Kb used=37495Kb max_used=37495Kb free=208264Kb
 *  bounds [0x00007fa287170000, 0x00007fa289650000, 0x00007fa296170000]
 *  total_blobs=10468 nmethods=9889 adapters=493
 *  compilation: enabled
 * </pre>
 * 
 * <p>
 * 2) Segmented memory (JDK8u40+, JDK9+).
 * </p>
 * 
 * <ul>
 * <li>'non-profiled nmethods': Tier 1/2 (C1), methods without profiling information.</li>
 * <li>'profiled nmethods': Tier 3 (C1), Tier 4 (C2).</li>
 * <li>'non-nmethods': JVM internal structures (.e.g. compiler buffers, bytecode interpreters, adapters, and runtime
 * stubs).</li>
 * </ul>
 * 
 * <p>
 * Example logging:
 * </p>
 * 
 * <pre>
 * CodeHeap 'non-profiled nmethods': size=128224Kb used=11542Kb max_used=14409Kb free=116681Kb
 *  bounds [0x00007fffdfc09000, 0x00007fffe0a29000, 0x00007fffe7941000]
 * CodeHeap 'profiled nmethods': size=128220Kb used=38331Kb max_used=45088Kb free=89888Kb
 *  bounds [0x00007fffd7ed2000, 0x00007fffdab82000, 0x00007fffdfc09000]
 * CodeHeap 'non-nmethods': size=5700Kb used=1496Kb max_used=1524Kb free=4203Kb
 *  bounds [0x00007fffd7941000, 0x00007fffd7bb1000, 0x00007fffd7ed2000]
 *  total_blobs=29416 nmethods=14571 adapters=913
 *  compilation: enabled
 *               stopped_count=0, restarted_count=0
 *  full_count=0
 * </pre>
 * 
 * 
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CodeCache implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "(CodeCache:)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "| bounds| compilation:|CodeHeap|Compilation:| full_count=|              stopped_count=| total_blobs)"
            + "(.*)$";

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
    public CodeCache(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.CODE_CACHE;
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

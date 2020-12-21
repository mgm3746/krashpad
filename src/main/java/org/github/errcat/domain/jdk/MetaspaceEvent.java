/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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
package org.github.errcat.domain.jdk;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * METASPACE
 * </p>
 * 
 * <p>
 * Metaspace information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Metaspace:
 * 
 * Usage:
 *   Non-class:    136.84 MB capacity,   129.90 MB ( 95%) used,     6.64 MB (  5%) free+waste,   305.00 KB ( &lt;1%) overhead.
 *       Class:     17.93 MB capacity,    14.53 MB ( 81%) used,     3.26 MB ( 18%) free+waste,   143.81 KB ( &lt;1%) overhead.
 *        Both:    154.76 MB capacity,   144.43 MB ( 93%) used,     9.90 MB (  6%) free+waste,   448.81 KB ( &lt;1%) overhead.
 * 
 * Virtual space:
 *   Non-class space:      138.00 MB reserved,     137.49 MB (&gt;99%) committed
 *       Class space:        1.00 GB reserved,      17.95 MB (  2%) committed
 *              Both:        1.13 GB reserved,     155.44 MB ( 13%) committed
 * 
 * Chunk freelists:
 *    Non-Class:  507.00 KB
 *        Class:  10.00 KB
 *         Both:  517.00 KB
 * 
 * MaxMetaspaceSize: unlimited
 * CompressedClassSpaceSize: 1.00 GB
 * 
 * CodeHeap 'non-profiled nmethods': size=128224Kb used=11542Kb max_used=14409Kb free=116681Kb
 *  bounds [0x00007fffdfc09000, 0x00007fffe0a29000, 0x00007fffe7941000]
 * CodeHeap 'profiled nmethods': size=128220Kb used=38331Kb max_used=45088Kb free=89888Kb
 *  bounds [0x00007fffd7ed2000, 0x00007fffdab82000, 0x00007fffdfc09000]
 * CodeHeap 'non-nmethods': size=5700Kb used=1496Kb max_used=1524Kb free=4203Kb
 *  bounds [0x00007fffd7941000, 0x00007fffd7bb1000, 0x00007fffd7ed2000]
 *  total_blobs=29416 nmethods=14571 adapters=913
 * ***REMOVED***
 *               stopped_count=0, restarted_count=0
 *  full_count=0
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MetaspaceEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "(Metaspace:)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER
            + "|[ ]{1,***REMOVED***Both:|[ ]{1,***REMOVED***Class( space)?:|Chunk freelists:|CompressedClassSpaceSize:|"
            + "MaxMetaspaceSize:|[ ]{1,***REMOVED***Non-[c|C]lass( space)?:|Usage:|Virtual space:).*$";

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
    public MetaspaceEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.METASPACE.toString();
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

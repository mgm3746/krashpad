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
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * METASPACE
 * </p>
 * 
 * <p>
 * Metaspace information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) JDK8:
 * </p>
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
 * <p>
 * 1) JDK11:
 * </p>
 *
 * <pre>
 * Metaspace:
 * 
 * Usage:
 *   414.40 MB capacity,   395.36 MB ( 95%) used,    17.73 MB (  4%) free+waste,     1.30 MB ( &lt;1%) overhead.
 * 
 * Virtual space:
 *     416.00 MB reserved,     414.50 MB (&gt;99%) committed
 * 
 * Chunk freelists:
 * 3.00 KB
 * 
 * MaxMetaspaceSize: 1.00 GB
 * </pre>
 * 
 * <p>
 * 2) JDK17:
 * </p>
 * 
 * <pre>
 * Metaspace:
 * 
 * Usage:
 *   Non-class:    110.87 KB used.
 *       Class:      6.34 KB used.
 *        Both:    117.20 KB used.
 * 
 * Virtual space:
 *   Non-class space:        8.00 MB reserved,     192.00 KB (  2%) committed,  1 nodes.
 *       Class space:        1.00 GB reserved,     128.00 KB ( &lt;1%) committed,  1 nodes.
 *              Both:        1.01 GB reserved,     320.00 KB ( &lt;1%) committed.
 * 
 * Chunk freelists:
 *    Non-Class:  4.00 MB
 *        Class:  3.75 MB
 *         Both:  7.74 MB
 * 
 * MaxMetaspaceSize: unlimited
 * CompressedClassSpaceSize: 1.00 GB
 * Initial GC threshold: 21.00 MB
 * Current GC threshold: 21.00 MB
 * CDS: on
 * MetaspaceReclaimPolicy: balanced
 *  - commit_granule_bytes: 65536.
 *  - commit_granule_words: 8192.
 *  - virtual_space_node_default_size: 1048576.
 *  - enlarge_chunks_in_place: 1.
 *  - new_chunks_are_fully_committed: 0.
 *  - uncommit_free_chunks: 1.
 *  - use_allocation_guard: 0.
 *  - handle_deallocations: 1.
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MetaspaceEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + MetaspaceEvent.REGEX_HEADER
            + "|[ ]{1,***REMOVED***BotD:|[ ]{1,***REMOVED***Both:|CDS:|[ ]{1,***REMOVED***Class( space)?:|Chunk freelists:|"
            + " - commit_granule_(bytes|words):|CompressedClassSpaceSize:| - enlarge_chunks_in_place:|"
            + "(Current|Initial) GC threshold|MaxMetaspaceSize:|MetaspaceReclaimPolicy:|No class space|"
            + " - handle_deallocations:| - new_chunks_are_fully_committed:|[ ]{1,***REMOVED***Non-[c|C]lass( space)?:|Usage:|"
            + " - uncommit_free_chunks:| - use_allocation_guard:| - virtual_space_node_default_size:|Virtual space:|"
            + JdkRegEx.SIZE2 + "|[ ]+" + JdkRegEx.SIZE2 + " reserved,[ ]+" + JdkRegEx.SIZE2 + " \\([>]{0,1***REMOVED***"
            + JdkRegEx.PERCENT + "\\) committed|  " + JdkRegEx.SIZE2 + " capacity,[ ]+" + JdkRegEx.SIZE2
            + " \\([ ]{0,2***REMOVED***" + JdkRegEx.PERCENT + "\\) used,[ ]+" + JdkRegEx.SIZE2 + " \\([ ]{0,2***REMOVED***" + JdkRegEx.PERCENT
            + "\\) free\\+waste,[ ]+" + JdkRegEx.SIZE2 + " \\( <" + JdkRegEx.PERCENT + "\\) overhead\\.)" + ".*$";

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "Metaspace:";

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
    public MetaspaceEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.METASPACE.toString();
    ***REMOVED***
***REMOVED***

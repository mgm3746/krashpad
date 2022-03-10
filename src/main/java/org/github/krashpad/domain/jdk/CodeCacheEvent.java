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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * CODE_CACHE
 * </p>
 * 
 * <p>
 * Heap information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Heap Regions:
 * EU=empty-uncommitted, EC=empty-committed, R=regular, H=humongous start, HC=humongous continuation, CS=collection set, T=trash, P=pinned
 * BTE=bottom/top/end, U=used, T=TLAB allocs, G=GCLAB allocs, S=shared allocs, L=live data
 * R=root, CP=critical pins, TAMS=top-at-mark-start, UWM=update watermark
 * SN=alloc sequence number
 * |    0|CS |BTE    67a200000,    67a400000,    67a400000|TAMS    67a400000|UWM    67a400000|U  2048K|T  2047K|G     0B|S    56B|L 31152B|CP   0
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CodeCacheEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + CodeCacheEvent.REGEX_HEADER
            + "| bounds| compilation:|CodeHeap| full_count=|              stopped_count=| total_blobs)(.*)$";

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "(CodeCache:)";

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
    public CodeCacheEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.CODE_CACHE.toString();
    ***REMOVED***
***REMOVED***

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

import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * NATIVE_MEMORY_TRACKING
 * </p>
 * 
 * <p>
 * Native memory tracking information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Native Memory Tracking:
 * 
 * Total: reserved=18369225KB, committed=17150661KB
 * -                 Java Heap (reserved=8388608KB, committed=8388608KB)
 *                             (mmap: reserved=8388608KB, committed=8388608KB)
 * 
 * -                     Class (reserved=1236886KB, committed=214626KB)
 *                             (classes ***REMOVED***32343)
 *                             (malloc=6038KB ***REMOVED***64830) 
 *                             (mmap: reserved=1230848KB, committed=208588KB)
 * 
 * -                    Thread (reserved=6278193KB, committed=6278193KB)
 *                             (thread ***REMOVED***6079)
 *                             (stack: reserved=6250236KB, committed=6250236KB)
 *                             (malloc=20833KB ***REMOVED***36474) 
 *                             (arena=7124KB ***REMOVED***12155)
 * 
 * -                      Code (reserved=264372KB, committed=84452KB)
 *                             (malloc=14772KB ***REMOVED***22729) 
 *                             (mmap: reserved=249600KB, committed=69680KB)
 * 
 * -                        GC (reserved=417612KB, committed=417612KB)
 *                             (malloc=73548KB ***REMOVED***84041) 
 *                             (mmap: reserved=344064KB, committed=344064KB)
 * 
 * -                  Compiler (reserved=26862KB, committed=26862KB)
 *                             (malloc=13065KB ***REMOVED***14908) 
 *                             (arena=13797KB ***REMOVED***17)
 * 
 * -                  Internal (reserved=1385758KB, committed=1385758KB)
 *                             (malloc=1385726KB ***REMOVED***173671) 
 *                             (mmap: reserved=32KB, committed=32KB)
 * 
 * -                    Symbol (reserved=36100KB, committed=36100KB)
 *                             (malloc=33694KB ***REMOVED***379033) 
 *                             (arena=2406KB ***REMOVED***1)
 * 
 * -    Native Memory Tracking (reserved=13441KB, committed=13441KB)
 *                             (malloc=823KB ***REMOVED***9720) 
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
public class NativeMemoryTrackingEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "Native Memory Tracking:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER
            + "|[-]{0,1***REMOVED***[ ]{0,***REMOVED***(\\(arena=|Arena Chunk|\\(classes|Class|Code|Compiler|GC|Internal|Java Heap|"
            + "Native Memory Tracking|\\(malloc|\\(mmap:|\\(stack|Symbol|\\(thread|Thread \\(|Total: reserved|"
            + "\\(tracking|Unknown)).*$";

    public static final Pattern PATTERN = Pattern.compile(REGEX);

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
    public NativeMemoryTrackingEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.NATIVE_MEMORY_TRACKING.toString();
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

    /**
     * @return true if the log line is the header false otherwise.
     */
    public boolean isHeader() {
        return logEntry.matches(REGEX_HEADER);
    ***REMOVED***
***REMOVED***

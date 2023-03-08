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
 * THREAD
 * </p>
 * 
 * <p>
 * Thread information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * 0x00007f19aa5128e0 JavaThread "Thread-8" daemon [_thread_blocked, id=18881, stack(0x00007f199cf04000,0x00007f199d005000)]
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Thread implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + Thread.REGEX_HEADER + "|(  |=>)" + JdkRegEx.ADDRESS
            + "( \\(exited\\))? (ConcurrentGCThread|GCTaskThread|JavaThread|Thread|VMThread|WatcherThread)|"
            + "\\[error occurred during error reporting \\(printing all threads\\)).*$";

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "(Java Threads: \\( => current thread \\)|***REMOVED***)";

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
    public Thread(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.THREAD.toString();
    ***REMOVED***
***REMOVED***

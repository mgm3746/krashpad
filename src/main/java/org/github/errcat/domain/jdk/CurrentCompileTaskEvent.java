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
 * CURRENT_COMPILE_TASK
 * </p>
 * 
 * <p>
 * Current compile information
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Current CompileTask:
 * C2:   1092  423       4       java.util.HashMap$KeyIterator::next (8 bytes)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CurrentCompileTaskEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "Current CompileTask:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|C2:).*$";

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
    public CurrentCompileTaskEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString();
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

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
 * THREADS_ACTIVE_COMPILE
 * </p>
 * 
 * <p>
 * Threads with active compile tasks information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Threads with active compile tasks:
 * C2 CompilerThread0606385663 219105 %     4       com.insight.common.elasticsearch.field.FieldService::toFieldDomainNamePart @ 56 (111 bytes)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ThreadsActiveCompileEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + ThreadsActiveCompileEvent.REGEX_HEADER + "|C[12] CompilerThread).*$";

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "Threads with active compile tasks:";

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
    public ThreadsActiveCompileEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.THREADS_ACTIVE_COMPILE.toString();
    ***REMOVED***

    /**
     * @return true if the log line is the header false otherwise.
     */
    public boolean isHeader() {
        return logEntry.matches(REGEX_HEADER);
    ***REMOVED***
***REMOVED***

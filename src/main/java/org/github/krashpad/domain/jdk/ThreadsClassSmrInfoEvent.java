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
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * THREADS_CLASS_SMR_INFO
 * </p>
 * 
 * <p>
 * Threads class SMR information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Threads class SMR info:
 * _java_thread_list=0x00000000020a0100, length=58, elements={
 * 0x00007ffff0017800, 0x00007ffff0450000, 0x00007ffff0452000, 0x00007ffff0460000,
 * ...
 * 0x00007fff5d5c6000, 0x0000000001b2a000
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ThreadsClassSmrInfoEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + ThreadsClassSmrInfoEvent.REGEX_HEADER + "|" + JdkRegEx.ADDRESS + ", "
            + JdkRegEx.ADDRESS + ", " + JdkRegEx.ADDRESS + ", " + JdkRegEx.ADDRESS + "(,)?|" + JdkRegEx.ADDRESS + ", "
            + JdkRegEx.ADDRESS + ", " + JdkRegEx.ADDRESS + "|" + JdkRegEx.ADDRESS + ", " + JdkRegEx.ADDRESS + "|"
            + JdkRegEx.ADDRESS + "|_java_thread_list=.*|***REMOVED***)$";

    /**
     * Regular expression for the header.
     */
    public static final String REGEX_HEADER = "Threads class SMR info:";

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
    public ThreadsClassSmrInfoEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.THREADS_CLASS_SMR_INFO.toString();
    ***REMOVED***
***REMOVED***

/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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
 * EXCEPTION_EVENT
 * </p>
 * 
 * <p>
 * Compilation information when methods are compiled from Java byte code to native code.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * Event: 101.811 Thread 0x00007ff0ec698000 Exception &lt;a 'java/lang/ArrayIndexOutOfBoundsException'&gt; (0x00000000ef71e968) thrown at [/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64/openjdk/hotspot/src/share/vm/runtime/sharedRuntime.cpp, line 609]
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ExceptionEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(Internal exceptions|Event: " + JdkRegEx.TIMESTAMP + " Thread "
            + JdkRegEx.ADDRESS + " Exception|<meta name).+$";

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
    public ExceptionEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.EXCEPTION_EVENT.toString();
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

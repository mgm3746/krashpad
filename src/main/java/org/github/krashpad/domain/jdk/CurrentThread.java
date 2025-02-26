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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * CURRENT_THREAD
 * </p>
 * 
 * <p>
 * The thread that was running when the JVM crashed.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Current thread (0x00007f127434f800):  JavaThread "ajp-/hostname:8109-16" daemon [_thread_in_native, id=112672, stack(0x00007f11e11a2000,0x00007f11e12a3000)]
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CurrentThread implements LogEvent {

    private static final Pattern PATTERN;

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^Current thread( \\(" + JdkRegEx.ADDRESS + "\\):)?[ ]{1,2}(.+)$";

    static {
        PATTERN = Pattern.compile(CurrentThread.REGEX);
    }

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
    public CurrentThread(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.CURRENT_THREAD;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The thread running when the JVM crashed.
     */
    public String getThreadName() {
        String currentThread = null;
        Matcher matcher = PATTERN.matcher(logEntry);
        if (matcher.find()) {
            currentThread = matcher.group(7);
        }
        return currentThread;
    }

    /**
     * @return True if the current thread is a CompilerThread, false otherwise.
     */
    public boolean isCompilerThread() {
        return logEntry.matches("^.+C[12] CompilerThread\\d{1,}.+$");
    }

    /**
     * @return True if the current thread is a VMThread, false otherwise.
     */
    public boolean isVmThread() {
        return logEntry.matches("^.+VMThread.+$");
    }
}

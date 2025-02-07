/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * ENVIRONMENT_VARIABLES
 * </p>
 * 
 * <p>
 * Environment variable information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Environment Variables:
 * PATH=/path/to/bin
 * LD_LIBRARY_PATH=:/path/to/lib 
 * SHELL=/bin/ksh
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class EnvironmentVariable implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Environment Variables:";
    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|_JAVA_SR_SIGNUM|ARCH|CLASSPATH|DISPLAY|DYLD_LIBRARY_PATH|_JAVA_OPTIONS|(JAVA|JRE)_HOME|"
            + "JAVA_TOOL_OPTIONS|HOSTTYPE|LANG|LC_ALL|LD_LIBRARY_PATH|LC_CTYPE|LD_PRELOAD|LIBPATH|MACHTYPE|OS=|OSTYPE|"
            + "PATH|PROCESSOR_IDENTIFIER|SHELL|TEMP|TERM|TMP|TMPDIR|TZ|USERNAME|XDG_CACHE_HOME|XDG_CONFIG_HOME).*$";

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
    public EnvironmentVariable(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.ENVIRONMENT_VARIABLES;
    }

    public String getLogEntry() {
        return logEntry;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }
}

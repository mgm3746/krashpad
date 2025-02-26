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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * LD_PRELOAD_FILE
 * </p>
 * 
 * <p>
 * ld.so.preload information.
 * </p>
 * <p>
 * Used to inject instrumentation at the host level (vs. at the JVM level with <code>-agentlib</code> or
 * <code>-agentpath</code>
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * /etc/ld.so.preload:
 * /$LIB/liboneagentproc.so
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class LdPreloadFile implements ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "^/etc/ld\\.so\\.preload:$";

    /**
     * Regular expression defining the logging.
     * 
     * It's not necessary to include the header because it matches {@link org.github.joa.util.JdkRegEx#FILE_PATH}.
     */
    private static final String REGEX = "^" + org.github.joa.util.JdkRegEx.FILE_PATH + "$";

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
     * The log entry for the event. Can be used for debugging purposes.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public LdPreloadFile(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.LD_PRELOAD_FILE;
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

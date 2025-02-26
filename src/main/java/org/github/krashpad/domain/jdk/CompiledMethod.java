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
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * COMPILED_METHOD
 * </p>
 * 
 * <p>
 * Compiled method information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Compiled method (c2)  377611 18632       4       org.jruby.runtime.callsite.CachingCallSite::cacheAndCall (70 bytes)
 *  total in heap  [0x00007fab24f57210,0x00007fab24f57770] = 1376
 *  relocation     [0x00007fab24f57370,0x00007fab24f573b0] = 64
 *  main code      [0x00007fab24f573c0,0x00007fab24f57540] = 384
 *  stub code      [0x00007fab24f57540,0x00007fab24f57578] = 56
 *  oops           [0x00007fab24f57578,0x00007fab24f57580] = 8
 *  metadata       [0x00007fab24f57580,0x00007fab24f57598] = 24
 *  scopes data    [0x00007fab24f57598,0x00007fab24f57638] = 160
 *  scopes pcs     [0x00007fab24f57638,0x00007fab24f576d8] = 160
 *  dependencies   [0x00007fab24f576d8,0x00007fab24f576e0] = 8
 *  handler table  [0x00007fab24f576e0,0x00007fab24f57758] = 120
 *  nul chk table  [0x00007fab24f57758,0x00007fab24f57770] = 24
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CompiledMethod implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "(Compiled method \\(.+)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "| (dependencies|handler table|main code|metadata|"
            + "nul chk table|oops|relocation|scopes data|scopes pcs|stub code|total in heap) .+)$";

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
    public CompiledMethod(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.CODE_CACHE;
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

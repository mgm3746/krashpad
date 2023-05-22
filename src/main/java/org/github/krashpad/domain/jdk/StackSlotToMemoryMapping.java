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
 * STACK_SLOT_TO_MEMORY_MAPPING
 * </p>
 * 
 * <p>
 * Stack slot to memory mapping information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Stack slot to memory mapping:
 * stack at sp + 0 slots: 0x00007ffff684e68d: &lt;offset 0x0000000000c1968d&gt; in /usr/lib/jvm/java-11-openjdk-11.0.5.10-0.el7_7.x86_64/lib/server/libjvm.so at 0x00007ffff5c35000
 * stack at sp + 1 slots: 0x000000000000000a is an unknown value
 * stack at sp + 2 slots: 0x00007fffb71a2950 points into unknown readable memory: 20 ac 62 aa ff 7f 00 00
 * stack at sp + 3 slots: 0x00007ffff0ca3800 is a thread
 * stack at sp + 4 slots: 0x00007fffaa62ac20 is pointing into the stack for thread: 0x0000000001b2a000
 * stack at sp + 5 slots: 0x0 is NULL
 * stack at sp + 6 slots: 0xe6e5c734e3f75200 is an unknown value
 * stack at sp + 7 slots: 0x00007fffb71a2970 points into unknown readable memory: a0 29 1a b7 ff 7f 00 00
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class StackSlotToMemoryMapping implements LogEvent {

    /**
     * Regular expression for the header.
     * 
     */
    public static final String _REGEX_HEADER = "Stack slot to memory mapping:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + StackSlotToMemoryMapping._REGEX_HEADER
            + "|stack at sp|\\[error occurred during error reporting \\(inspecting top of stack\\)|\\[CodeBlob|"
            + "method entry point|\\{" + JdkRegEx.ADDRESS + "\\} \\- klass:| - length:|" + JdkRegEx.CLASS
            + "|BufferBlob|Framesize:).*$";

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
    public StackSlotToMemoryMapping(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString();
    }
}

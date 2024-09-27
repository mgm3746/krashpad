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
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * VM_OPERATION_EVENT
 * </p>
 * 
 * <p>
 * VM operations leading up to the crash.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * VM Operations (2 events):
 * Event: 31.627 Executing VM operation: HandshakeAllThreads done
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmOperationEvent implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "VM Operations \\(\\d{1,} events\\):";

    private static final String _REGEX_OPERATIONS = "(CleanClassLoaderDataMetaspaces|Cleanup|FindDeadlocks|"
            + "G1CollectForAllocation|GenCollectForAllocation|GetAllStackTraces|GetThreadListStackTraces|"
            + "HandshakeAllThreads|ICBufferFull|PrintJNI|PrintThreads|RendezvousGCThreads|ThreadDump|XMarkStart|"
            + "ZMarkEnd|ZMarkFlushOperation|ZMarkStartYoung|ZMarkStartYoungAndOld|ZRelocateStart|"
            + "ZRendezvousGCThreads)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|Event: " + JdkRegEx.TIMESTAMP
            + " Executing VM operation: " + _REGEX_OPERATIONS + ".*|No [Ee]vents)$";

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
    public VmOperationEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.VM_OPERATION_EVENT;
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

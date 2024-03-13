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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * VMWARE_INFO
 * </p>
 * 
 * <p>
 * Container information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * VMWare virtualization detected
 * vSphere host information:
 * host.cpu.processorMHz = 2593
 * host.cpu.coresPerPkg = 14
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VirtualizationInfo implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     * 
     * When there is not header, use the first line output (e.g. 'Steal ticks...').
     */
    public static final String _REGEX_HEADER = "((Hyper[-]{0,1}V|KVM|VMWare) virtualization detected|Steal ticks.+)";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|guest\\.mem\\.|host\\.cpu\\.|ovhd\\.mem\\.|Steal ticks|vm\\.cpu\\.|vm\\.numa\\.|"
            + "vSphere host information:|vSphere resource information available now:|"
            + "vSphere resource information collected at VM startup:).*$";

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
    public VirtualizationInfo(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.VIRTUALIZATION_INFO;
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

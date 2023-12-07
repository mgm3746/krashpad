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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * CONTAINER_INFO
 * </p>
 * 
 * <p>
 * Container information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * container (cgroup) information:
 * container_type: cgroupv1
 * cpu_cpuset_cpus: 0-7
 * cpu_memory_nodes: 0
 * active_processor_count: 8
 * cpu_quota: -1
 * cpu_period: 100000
 * cpu_shares: -1
 * memory_limit_in_bytes: -1
 * memory_and_swap_limit_in_bytes: -1
 * memory_soft_limit_in_bytes: -1
 * memory_usage_in_bytes: 3469758464
 * memory_max_usage_in_bytes: 0
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ContainerInfo implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "^container \\(cgroup\\) information:$";

    /**
     * Regular expression for a setting.
     */
    private static final String _SETTING = "^(active_processor_count|container_type|"
            + "cpu_(cpuset_cpus|memory_nodes|period|shares|quota)|current number of tasks|"
            + "kernel_memory_(limit_in_bytes|max_usage_in_bytes|usage_in_bytes)|maximum number of tasks|"
            + "memory_(and_swap_limit_in_bytes|limit_in_bytes|max_usage_in_bytes|soft_limit_in_bytes|usage_in_bytes)):"
            + " (.+)$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(_REGEX_HEADER) || logLine.matches(_SETTING);
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
    public ContainerInfo(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.CONTAINER_INFO.toString();
    }

    /**
     * @return The setting name, or null if the log line is not a setting.
     */
    public String getSetting() {
        String setting = null;
        if (logEntry.matches(_SETTING)) {
            Pattern pattern = Pattern.compile(_SETTING);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                setting = matcher.group(1);
            }
        }
        return setting;
    }

    /**
     * @return The setting value, or null if the log line is not a setting.
     */
    public String getSettingValue() {
        String value = null;
        if (logEntry.matches(_SETTING)) {
            Pattern pattern = Pattern.compile(_SETTING);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                value = matcher.group(5);
            }
        }
        return value;
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

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
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * ***REMOVED***
 * cpu_cpuset_cpus: 0-7
 * cpu_memory_nodes: 0
 * active_processor_count: 8
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * ***REMOVED***
 * memory_usage_in_bytes: 3469758464
 * memory_max_usage_in_bytes: 0
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ContainerInfoEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "container \\(cgroup\\) information:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|active_processor|container_type|cpu_|KVM|memory_|"
            + "Steal ticks|VMWare virtualization detected).*$";

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
    public ContainerInfoEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.CONTAINER_INFO.toString();
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

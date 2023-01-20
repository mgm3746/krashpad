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
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * GC_PRECIOUS_LOG
 * </p>
 * 
 * <p>
 * GC precious information.
 * </p>
 * 
 * <p>
 * <a href="https://bugs.openjdk.java.net/browse/JDK-8246272">JDK-8246135</a>
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * GC Precious Log:
 *  CPUs: 12 total, 12 available
 *  Memory: 31907M
 *  Large Page Support: Disabled
 *  NUMA Support: Disabled
 *  Compressed Oops: Enabled (Zero based)
 *  Heap Region Size: 4M
 *  Heap Min Capacity: 8M
 *  Heap Initial Capacity: 500M
 *  Heap Max Capacity: 7980M
 *  Pre-touch: Disabled
 *  Parallel Workers: 10
 *  Concurrent Workers: 3
 *  Concurrent Refinement Workers: 10
 *  Periodic GC: Disabled
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class GcPreciousLogEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + GcPreciousLogEvent.REGEX_HEADER
            + "| Compressed Oops:| CPUs:| Heap ((Initial|Min|Max) Capacity|Region Size): | Large Page Support:"
            + "| Memory:| NUMA Support:| Periodic GC:| Pre-touch:| (Concurrent( Refinement)?|Parallel) Workers:).*$";

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "GC Precious Log:";

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
    public GcPreciousLogEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.GC_PRECIOUS_LOG.toString();
    ***REMOVED***
***REMOVED***

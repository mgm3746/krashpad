/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * VM_OPERATION
 * </p>
 * 
 * <p>
 * VM_Operation information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread 0x0000000001b2a000
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmOperationEvent implements LogEvent {

    private static Pattern pattern = Pattern.compile(VmOperationEvent.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^VM_Operation \\(" + JdkRegEx.ADDRESS
            + "\\): ((BulkRevokeBias|CGC_Operation|G1CollectFull|GetThreadListStackTraces|HeapDumper|"
            + "ParallelGCFailedAllocation|PrintThreads).+)$";

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
    public VmOperationEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.VM_OPERATION.toString();
    ***REMOVED***

    /**
     * @return The VM operation. For example:
     * 
     *         PrintThreads
     */
    public String getVmOperation() {
        String vmOperation = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            vmOperation = matcher.group(7);
        ***REMOVED***
        return vmOperation;
    ***REMOVED***

    /**
     * @return The VM operation string. For example:
     * 
     *         PrintThreads, mode: safepoint, requested by thread 0x0000000001b2a000
     */
    public String getVmOperationString() {
        String vmOperation = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            vmOperation = matcher.group(6);
        ***REMOVED***
        return vmOperation;
    ***REMOVED***
***REMOVED***

/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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
package org.github.errcat.domain.jdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * MEMORY
 * </p>
 * 
 * <p>
 * Memory information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <p>
 * 1) Linux:
 * </p>
 * 
 * <pre>
 * Memory: 4k page, physical 16058700k(1456096k free), swap 8097788k(7612768k free)
 * </pre>
 * 
 * <p>
 * 2) Windows:
 * </p>
 * 
 * <pre>
 * Memory: 4k page, system-wide physical 16383M (5994M free)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class MemoryEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^Memory: (4|8|64)k page,( system-wide)? physical " + JdkRegEx.SIZE
            + "[ ]{0,1***REMOVED***\\(" + JdkRegEx.SIZE + " free\\)(, swap " + JdkRegEx.SIZE + "\\(" + JdkRegEx.SIZE
            + " free\\))?$";

    private static Pattern pattern = Pattern.compile(REGEX);

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
    public MemoryEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.MEMORY.toString();
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

    /**
     * @return The total available physical memory (kilobytes).
     */
    public long getPhysicalMemory() {
        long physicalMemory = 0;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            physicalMemory = JdkUtil.convertSize(Long.parseLong(matcher.group(3)), matcher.group(5).charAt(0), 'K');
        ***REMOVED***
        return physicalMemory;
    ***REMOVED***

    /**
     * @return The total free physical memory (kilobytes).
     */
    public long getPhysicalMemoryFree() {
        long physicalMemoryFree = 0;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            physicalMemoryFree = JdkUtil.convertSize(Long.parseLong(matcher.group(6)), matcher.group(8).charAt(0), 'K');
        ***REMOVED***
        return physicalMemoryFree;
    ***REMOVED***

    /**
     * @return The total available swap (kilobytes).
     */
    public long getSwap() {
        long swap = Long.MIN_VALUE;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(9) != null) {
                swap = JdkUtil.convertSize(Long.parseLong(matcher.group(10)), matcher.group(12).charAt(0), 'K');
            ***REMOVED***
        ***REMOVED***
        return swap;
    ***REMOVED***

    /**
     * @return The total free swap (kilobytes).
     */
    public long getSwapFree() {
        long swapFree = Long.MIN_VALUE;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(9) != null) {
                swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(13)), matcher.group(15).charAt(0), 'K');
            ***REMOVED***
        ***REMOVED***
        return swapFree;
    ***REMOVED***
***REMOVED***

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
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.CompressedOopMode;

/**
 * <p>
 * HEAP_ADDRESS
 * </p>
 * 
 * <p>
 * Head address information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeapAddressEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^[h|H]eap address: " + JdkRegEx.ADDRESS + ", size: (\\d{1,***REMOVED***) MB.*$";

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
    public HeapAddressEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    /**
     * @return The compressed oop mode.
     */
    public final CompressedOopMode getCompressedOopMode() {
        CompressedOopMode compressedOopMode = CompressedOopMode.UNKNOWN;
        if (logEntry.matches(".*Compressed Oops mode: 32-bit.*")) {
            compressedOopMode = CompressedOopMode.BIT32;
        ***REMOVED*** else if (logEntry.matches(".*Compressed Oops mode: Zero based.*")) {
            compressedOopMode = CompressedOopMode.ZERO;
        ***REMOVED*** else if (logEntry.matches(".*Compressed Oops mode: Non-zero based.*")) {
            compressedOopMode = CompressedOopMode.NON_ZERO;
        ***REMOVED***
        return compressedOopMode;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.HEAP_ADDRESS.toString();
    ***REMOVED***

    /**
     * @return The heap size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public final Long getSize() {
        long initialSize = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            initialSize = JdkUtil.convertSize(Long.parseLong(matcher.group(6)), 'M', Constants.PRECISION_REPORTING);
        ***REMOVED***
        return initialSize;
    ***REMOVED***

    /**
     * @return The address the heap starts at in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public final Long getStartingAddress() {
        long startingAddress = Long.MIN_VALUE;
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            return JdkMath.convertHexToDecimal(matcher.group(1));
        ***REMOVED***
        return startingAddress;
    ***REMOVED***
***REMOVED***

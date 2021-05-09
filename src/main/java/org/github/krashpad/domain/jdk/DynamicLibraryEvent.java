/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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
import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * DYNAMIC_LIBRARY
 * </p>
 * 
 * <p>
 * Virtual memory regions at the time of the crash.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <p>
 * 1) Solaris:
 * </p>
 * 
 * <pre>
 * ***REMOVED***
 * 00400000-00401000 r-xp 00000000 fd:0d 201327127                          /path/to/jdk/bin/java
 * </pre>
 * 
 * <p>
 * 2) Solaris alternate format:
 * </p>
 * 
 * <pre>
 * 0xffffffff49400000      /apps/java/jdk1.8.0_251_no_compiler/jre/lib/sparcv9/server/libjvm.so
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class DynamicLibraryEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "***REMOVED***";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|(" + JdkRegEx.MEMORY_REGION + "|" + JdkRegEx.ADDRESS
            + ")( " + JdkRegEx.PERMISION + " " + JdkRegEx.FILE_OFFSET + " " + JdkRegEx.DEVICE_IDS + " " + JdkRegEx.INODE
            + ")?[ ]{1,***REMOVED***((" + JdkRegEx.FILE + "|" + JdkRegEx.AREA
            + "))?|(dbghelp|symbol engine):.+|Can not get library information for pid = \\d{1,***REMOVED***)$";

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
    public DynamicLibraryEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString();
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
     * @return Device.
     */
    public Device getDevice() {
        Device device = Device.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int deviceIdIndex = 12;
            String deviceId = matcher.group(deviceIdIndex);
            if (deviceId.matches("fd:[a-z0-9]{2***REMOVED***")) {
                device = Device.FIXED_DISK;
            ***REMOVED*** else if (deviceId.equals("103:03")) {
                device = Device.AWS_BLOCK_STORAGE;
            ***REMOVED*** else if (deviceId.matches("00:[0-9]{2***REMOVED***")) {
                device = Device.NFS;
            ***REMOVED***
        ***REMOVED***
        return device;
    ***REMOVED***

    /**
     * @return Dynamic library file path.
     */
    public String getFilePath() {
        String filePath = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int filePathIndex = 14;
            filePath = matcher.group(filePathIndex);
        ***REMOVED***
        return filePath;
    ***REMOVED***
***REMOVED***

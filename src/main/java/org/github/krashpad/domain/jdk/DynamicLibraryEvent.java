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
 * <p>
 * On Linux, it is output from the following:
 * </p>
 * 
 * <pre>
 * $ cat /proc/&lt;pid&gt;/maps
 * </pre>
 * 
 * <h3>Example Logging</h3>
 * 
 * <p>
 * 1) Solaris:
 * </p>
 * 
 * <pre>
 * Dynamic libraries:
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
 * <p>
 * 3) Linux format:
 * </p>
 * 
 * <pre>
 * 7fb19f83e000-7fb1a04ab000 r-xp 00000000 fc:30 12596294                   /usr/local/clo/ven/jdk1.8.0_25-64/jre/lib/amd64/server/libjvm.so
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class DynamicLibraryEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "Dynamic libraries:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|(" + JdkRegEx.MEMORY_REGION + "|" + JdkRegEx.ADDRESS
            + ")( " + JdkRegEx.PERMISION + " " + JdkRegEx.FILE_OFFSET + " " + JdkRegEx.DEVICE_IDS + " " + JdkRegEx.INODE
            + ")?[ ]{1,}((" + JdkRegEx.FILE + "|" + JdkRegEx.AREA
            + "))?|(dbghelp|symbol engine):.+|Can not get library information for pid = \\d{1,})$";

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
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString();
    }

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
     * @return Device.
     */
    public Device getDevice() {
        Device device = Device.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int deviceIdIndex = 12;
            String deviceId = matcher.group(deviceIdIndex);
            if (deviceId.matches("fd:[a-z0-9]{2}")) {
                device = Device.FIXED_DISK;
            } else if (deviceId.equals("103:03")) {
                device = Device.AWS_BLOCK_STORAGE;
            } else if (deviceId.matches("00:[a-z0-9]{2}")) {
                device = Device.NFS;
            } else if (deviceId.matches("08:[0-9]{2}")) {
                device = Device.SCSI_DISK;
            }
        }
        return device;
    }

    /**
     * @return Dynamic library file path.
     */
    public String getFilePath() {
        String filePath = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int filePathIndex = 14;
            filePath = matcher.group(filePathIndex);
        }
        return filePath;
    }
}

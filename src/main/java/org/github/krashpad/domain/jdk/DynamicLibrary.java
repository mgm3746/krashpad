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
import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * DYNAMIC_LIBRARY
 * </p>
 * 
 * <p>
 * Virtual memory regions at the time of the crash. The number of lines is the number of memory map areas, limited by
 * max_map_count ({@link org.github.krashpad.domain.jdk.MaxMapCount}).
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
 * <h2>Example Logging</h2>
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
public class DynamicLibrary implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String __REGEX_HEADER = "Dynamic libraries:";

    /**
     * Regular expression defining the logging.
     */
    private static final String _REGEX = "^(" + __REGEX_HEADER + "|(" + JdkRegEx.MEMORY_REGION + "|" + JdkRegEx.ADDRESS
            + ")( " + JdkRegEx.PERMISION + " " + JdkRegEx.FILE_OFFSET + " " + JdkRegEx.DEVICE_IDS + " " + JdkRegEx.INODE
            + ")?[\\s]{1,}((" + org.github.joa.util.JdkRegEx.FILE_PATH + "|" + JdkRegEx.AREA
            + "))?|(dbghelp|symbol engine):.+|Can not get library information for pid = \\d{1,})$";

    private static Pattern pattern = Pattern.compile(_REGEX);

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(_REGEX);
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
    public DynamicLibrary(String logEntry) {
        this.logEntry = logEntry;
    }

    /**
     * @return Device.
     */
    public Device getDevice() {
        Device device = Device.UNIDENTIFIED;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int deviceIdIndex = 14;
            String deviceId = matcher.group(deviceIdIndex);
            if (deviceId != null) {
                if (deviceId.matches("fd:[a-z0-9]{2}")) {
                    device = Device.FIXED_DISK;
                } else if (deviceId.matches("103:0[03]")) {
                    device = Device.AWS_BLOCK_STORAGE;
                } else if (deviceId.matches("00:[a-z0-9]{2}")) {
                    device = Device.NFS;
                } else if (deviceId.matches("08:[0-9]{2}")) {
                    device = Device.SCSI_DISK;
                }
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
            int filePathIndex = 16;
            filePath = matcher.group(filePathIndex);
        }
        return filePath;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.DYNAMIC_LIBRARY.toString();
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(__REGEX_HEADER);
        }
        return isHeader;
    }

    /**
     * @return True if a jar, false otherwise.
     */
    public boolean isJar() {
        return logEntry.matches(".+" + JdkRegEx.JAR + "$");
    }

    /**
     * @return True if a native library, false otherwise.
     */
    public boolean isNativeLibrary() {
        return logEntry.matches(".+" + JdkRegEx.NATIVE_LIBRARY + "( \\(deleted\\))?$");
    }
}

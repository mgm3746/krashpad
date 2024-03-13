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

import org.github.krashpad.domain.BlankLine;
import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

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
 * 7fb19f83e000-7fb1a04ab000 r-xp 00000000 fc:30 12596294                   /usr/local/jdk1.8.0_25-64/jre/lib/amd64/server/libjvm.so
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class DynamicLibrary implements LogEvent, HeaderEvent {

    /**
     * Regular expression for errors.
     */
    public static final String _REGEX_ERROR = "Can not get library information for pid = \\d{1,}";

    /**
     * Regular expression for the footer.
     */
    public static final String _REGEX_FOOTER = "Total number of mappings: \\d{1,}";

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Dynamic libraries:";

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_LIBRARY = "(" + JdkRegEx.MEMORY_REGION + "|" + JdkRegEx.ADDRESS + ")( "
            + JdkRegEx.PERMISION + " " + JdkRegEx.FILE_OFFSET + " " + JdkRegEx.DEVICE_IDS + " " + JdkRegEx.INODE
            + ")?[ \\t]{1,}(" + org.github.joa.util.JdkRegEx.FILE_PATH + ")?";

    private static Pattern pattern = Pattern.compile(DynamicLibrary.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_FOOTER + "|" + _REGEX_ERROR + "|"
            + _REGEX_LIBRARY + ")$";

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
     * @param logLine
     *            The log line to test.
     * @param priorEventType
     *            The prior log line <code>LogEventType</code>.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine, LogEventType priorEventType) {
        return priorEventType != null && priorEventType == LogEventType.DYNAMIC_LIBRARY && !BlankLine.match(logLine);
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

    @Override
    public LogEventType getEventType() {
        return LogEventType.DYNAMIC_LIBRARY;
    }

    /**
     * @return Dynamic library file path.
     */
    public String getFilePath() {
        String filePath = null;
        Pattern p = Pattern.compile(_REGEX_LIBRARY);
        Matcher m = p.matcher(logEntry);
        if (m.find()) {
            int filePathIndex = 15;
            filePath = m.group(filePathIndex);
            // Directories and file names can include spaces and parenthesis, but assume any file name that ends with "
            // (deleted)" indicates an mmapped file in a deleted state and should be removed from the file name.
            if (filePath != null) {
                int position = filePath.lastIndexOf(" (deleted)");
                if (position != -1) {
                    filePath = filePath.substring(0, position);
                }
            }
        }
        return filePath;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public boolean isError() {
        boolean isError = false;
        if (this.logEntry != null) {
            isError = logEntry.matches(_REGEX_ERROR);
        }
        return isError;
    }

    public boolean isFooter() {
        boolean isFooter = false;
        if (this.logEntry != null) {
            isFooter = logEntry.matches(_REGEX_FOOTER);
        }
        return isFooter;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }

    /**
     * @return True if the mapping is "interesting" (i.e. includes details useful for analysis vs. memory ranges without
     *         any details), false otherwise.
     */
    public boolean isInteresting() {
        boolean isInteresting = false;
        if (this.isHeader() || this.isFooter()) {
            isInteresting = true;
        } else if (logEntry != null) {
            isInteresting = (getFilePath() != null);
        }
        return isInteresting;
    }

    /**
     * @return True if a jar, false otherwise.
     */
    public boolean isJar() {
        return logEntry.matches(".+" + JdkRegEx.JAR + "$");
    }

    public boolean isMapping() {
        return !(isHeader() || isFooter() || isError());
    }

    /**
     * @return True if a native library, false otherwise.
     */
    public boolean isNativeLibrary() {
        return logEntry.matches(".+" + JdkRegEx.NATIVE_LIBRARY + "( \\(deleted\\))?$");
    }
}

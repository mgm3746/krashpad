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
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.SignalNumber;

/**
 * <p>
 * HEADER
 * </p>
 * 
 * <p>
 * Header information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * #
 * # A fatal error has been detected by the Java Runtime Environment:
 * #
 * #  SIGSEGV (0xb) at pc=0x00007fcbd05a3b71, pid=52385, tid=0x00007fcbcc677700
 * #
 * # JRE version: Java(TM) SE Runtime Environment (8.0_192-b12) (build 1.8.0_192-b12)
 * # Java VM: Java HotSpot(TM) 64-Bit Server VM (25.192-b12 mixed mode linux-amd64 )
 * # Problematic frame:
 * # V  [libjvm.so+0x645b71]  oopDesc::size_given_klass(Klass*)+0x1
 * #
 * # Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
 * #
 * # If you would like to submit a bug report, please visit:
 * #   http://bugreport.java.com/bugreport/crash.jsp
 * #
 * </pre>
 * 
 * <pre>
 * #
 * # There is insufficient memory for the Java Runtime Environment to continue.
 * # Native memory allocation (malloc) failed to allocate 32744 bytes for ChunkPool::allocate
 * # Possible reasons:
 * #   The system is out of physical RAM or swap space
 * #   The process is running with CompressedOops enabled, and the Java Heap may be blocking the growth of the native heap
 * # Possible solutions:
 * #   Reduce memory load on the system
 * #   Increase physical memory or swap space
 * #   Check if swap backing store is full
 * #   Decrease Java heap size (-Xmx/-Xms)
 * #   Decrease number of Java threads
 * #   Decrease Java thread stack sizes (-Xss)
 * #   Set larger code cache with -XX:ReservedCodeCacheSize=
 * # This output file may be truncated or incomplete.
 * #
 * #  Out of Memory Error (allocation.cpp:272), pid=273, tid=0x00000000000017c2
 * #
 * # JRE version: Java(TM) SE Runtime Environment (8.0_251-b08) (build 1.8.0_251-b08)
 * # Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed oops)
 * # Core dump written. Default location: /apps/opt/jboss-eap/v7.3-ejb3/bin/core or core.273
 * #
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeaderEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^#(.*)?$";

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
    public HeaderEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.OS.toString();
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
     * @return The OS.
     */
    public OsType getOs() {
        OsType osType = OsType.UNKNOWN;
        if (getOsString().matches(".+Linux.+")) {
            osType = OsType.LINUX;
        } else if (getOsString().matches(".+Solaris.+")) {
            osType = OsType.SOLARIS;
        }
        return osType;
    }

    /**
     * @return The OS string.
     */
    public String getOsString() {
        String os = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            os = matcher.group(1);
        }
        return os;
    }

    /**
     * @return True if the event is SIGSEGV, false otherwise.
     */
    public boolean isSignalNumber() {
        return logEntry.matches(
                "^#  (" + SignalNumber.SIGBUS + "|" + SignalNumber.SIGILL + "|" + SignalNumber.SIGSEGV + ").+$");
    }

    /**
     * @return True if the event is a problematic frame, false otherwise.
     */
    public boolean isProblematicFrame() {
        return logEntry.matches("^# (C  |J |v  |V  ).+$");
    }

    /**
     * @return True if the event is a VM frame, false otherwise.
     */
    public boolean isVmFrame() {
        return logEntry.matches("^# (V  ).+$");
    }

    /**
     * @return True if the event is an itnernal error, false otherwise.
     */
    public boolean isInternalError() {
        return logEntry.matches("^#  Internal Error.+$");
    }

    /**
     * @return True if the event involves a failure, false otherwise.
     */
    public boolean isFailed() {
        return logEntry.matches("^#.+failed.+$");
    }

    /**
     * @return True if the event is an error, false otherwise.
     */
    public boolean isError() {
        return logEntry.matches("^#  Error:.+$");
    }

    /**
     * @return True if the event is Out Of Memory Error, false otherwise.
     */
    public boolean isOutOfMemoryError() {
        return logEntry.matches("^#  Out of Memory Error .+$");
    }

    /**
     * @return True if the event is Java VM event, false otherwise.
     */
    public boolean isJavaVm() {
        return logEntry.matches("^# Java VM:.+$");
    }

    /**
     * @return True if the event is a JRE version event, false otherwise.
     */
    public boolean isJreVersion() {
        return logEntry.matches("^# JRE version:.+$");
    }
}

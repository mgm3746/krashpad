/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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

import org.github.joa.domain.Os;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;
import org.github.krashpad.util.jdk.JdkUtil.SignalNumber;

/**
 * <p>
 * HEADER
 * </p>
 * 
 * <p>
 * Header information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) Standard:
 * </p>
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
 * <p>
 * 2) Insufficient memory:
 * </p>
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
 * # Core dump written. Default location: /path/to/core or core.273
 * #
 * </pre>
 * 
 * <p>
 * 3) Timeout:
 * </p>
 * 
 * <pre>
 * #
 * # A fatal error has been detected by the Java Runtime Environment:
 * #
 * #  SIGSEGV (0xb) at pc=0x00007f8b949f02e3, pid=18099, tid=21918
 * #
 * # JRE version: Java(TM) SE Runtime Environment 18.9 (11.0.15.1+2) (build 11.0.15.1+2-LTS-10)
 * # Java VM: Java HotSpot(TM) 64-Bit Server VM 18.9 (11.0.15.1+2-LTS-10, mixed mode, tiered, compressed oops, g1 gc, linux-amd64)
 * # Problematic frame:
 * # C  [libc.so.6+0x822e3]
 * [timeout occurred during error reporting in step "printing problematic frame"] after 30 s.
 * # No core dump will be written. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
 * #
 * # If you would like to submit a bug report, please visit:
 * #   https://bugreport.java.com/bugreport/crash.jsp
 * # The crash happened outside the Java Virtual Machine in native code.
 * # See problematic frame for where to report the bug.
 * #
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Header implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(#|\\[error occurred during error reporting \\(printing "
            + "problematic frame\\), id 0x[a-z0-9]\\]|\\[timeout occurred during error reporting in step \"printing "
            + "problematic frame\"] after \\d{1,} s)(.*)?$";

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
     * The log entry for the event.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public Header(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.HEADER;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The <code>OsType</code>.
     */
    public Os getOsType() {
        Os osType = Os.UNIDENTIFIED;
        if (logEntry.matches(".+linux.+")) {
            osType = Os.LINUX;
        } else if (logEntry.matches(".+solaris.+")) {
            osType = Os.SOLARIS;
        } else if (logEntry.matches(".+windows.+")) {
            osType = Os.WINDOWS;
        }
        return osType;
    }

    /**
     * @return True if the event is an error, false otherwise.
     */
    public boolean isError() {
        return logEntry.matches("^#  (Error|fatal error):.+$");
    }

    public boolean isErrorOccurredDuringErrorReporting() {
        return logEntry.startsWith("[error occurred during error reporting");
    }

    /**
     * @return True if the event involves a failure, false otherwise.
     */
    public boolean isFailed() {
        return logEntry.matches("^#.+failed.+$");
    }

    /**
     * @return True if the event involves something insufficient, false otherwise.
     */
    public boolean isInsufficient() {
        return logEntry.matches("^#.+insufficient.+$");
    }

    /**
     * @return True if the event is an internal error, false otherwise.
     */
    public boolean isInternalError() {
        return logEntry.matches("^#  Internal Error.+$");
    }

    /**
     * Check for INVALID. For example:
     * 
     * # INVALID (0xe0000002) at pc=0x0000000000000000, pid=108047, tid=0x00007f67eb450700
     * 
     * @return True if the event is INVALID, false otherwise. For example:
     */
    public boolean isInvalid() {
        return logEntry.matches("^#  INVALID .+$");
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

    /**
     * @return True if the event involves something out of, false otherwise.
     */
    public boolean isOutOf() {
        return logEntry.matches("^#.+Out of.+$");
    }

    /**
     * @return True if the event is a problematic frame, false otherwise.
     */
    public boolean isProblematicFrame() {
        return logEntry.matches("^# (C  |J |v  |V  ).+$");
    }

    /**
     * @return True if the event includes a <code>SignalNumber</code>, false otherwise.
     */
    public boolean isSignalNumber() {
        return logEntry.matches("^#  (" + SignalNumber.EXCEPTION_ACCESS_VIOLATION + "|"
                + SignalNumber.EXCEPTION_STACK_OVERFLOW + "|" + SignalNumber.SIGBUS + "|" + SignalNumber.SIGFPE + "|"
                + SignalNumber.SIGILL + "|" + SignalNumber.SIGSEGV + ").+$");
    }

    /**
     * @return True if the event is a timeout, false otherwise.
     */
    public boolean isTimeout() {
        return logEntry.matches("^\\[timeout occurred during error reporting in step.+$");
    }

    /**
     * @return True if the event is the vendor bug url, false otherwise.
     */
    public boolean isVendorBugUrl() {
        return logEntry.matches("^#   http.+$");
    }

    /**
     * @return True if the event is a VM frame, false otherwise.
     */
    public boolean isVmFrame() {
        return logEntry.matches("^# (V  ).+$");
    }
}

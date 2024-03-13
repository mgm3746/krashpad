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

import org.github.joa.domain.Arch;
import org.github.joa.domain.Os;
import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.Constants.OsVendor;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * UNAME
 * </p>
 * 
 * <p>
 * uname information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) JDK8/11:
 * </p>
 * 
 * <pre>
 * uname:Linux 3.10.0-1127.19.1.el7.x86_64 #1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64
 * </pre>
 * 
 * <pre>
 * uname:SunOS 5.11 11.4.23.69.3 sun4v
 * </pre>
 * 
 * <pre>
 * uname:SunOS 5.11 11.3 i86pc  (T2 libthread)
 * </pre>
 * 
 * <p>
 * 2) Split across 2 lines:
 * </p>
 * 
 * <pre>
 * uname:SunOS 5.11 11.4.32.88.3 sun4v
 *   (T2 libthread)
 * </pre>
 * 
 * <p>
 * JDK17:
 * </p>
 * 
 * <pre>
 * uname: Linux 4.18.0-348.2.1.el8_5.x86_64 #1 SMP Mon Nov 8 13:30:15 EST 2021 x86_64
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Uname implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "uname:[ ]{0,1}((Linux|SunOS) .+(aarch64|i86pc|sun4v|ppc64(le)?|x86_64)"
            + ".*)";

    private static Pattern pattern = Pattern.compile(Uname.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|  \\(T2 libthread\\))$";

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
    public Uname(String logEntry) {
        this.logEntry = logEntry;
    }

    /**
     * @return The chip architecture.
     */
    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexArch = 4;
            if (matcher.group(indexArch).equals("x86_64")) {
                arch = Arch.X86_64;
            } else if (matcher.group(indexArch).equals("ppc64")) {
                arch = Arch.PPC64;
            } else if (matcher.group(indexArch).equals("ppc64le")) {
                arch = Arch.PPC64LE;
            } else if (matcher.group(indexArch).equals("sun4v")) {
                arch = Arch.SPARC;
            } else if (matcher.group(indexArch).equals("i86pc")) {
                arch = Arch.I86PC;
            } else if (matcher.group(indexArch).equals("aarch64")) {
                arch = Arch.AARCH64;
            }
        }
        return arch;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.UNAME;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The OS type.
     */
    public Os getOsType() {
        Os osType = Os.UNIDENTIFIED;
        if (getUname().matches("Linux.+")) {
            osType = Os.LINUX;
        } else if (getUname().matches("SunOS.+")) {
            osType = Os.SOLARIS;
        }
        return osType;
    }

    /**
     * @return The OS vendor.
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNIDENTIFIED;
        if (getUname().matches("Linux.+\\.el(6|7|(8|9)_\\d)\\..+")) {
            osVendor = OsVendor.REDHAT;
        } else if (getUname().matches("SunOS.+")) {
            osVendor = OsVendor.ORACLE;
        }
        return osVendor;
    }

    /**
     * @return The OS version.
     */
    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNIDENTIFIED;
        if (getUname().matches("Linux.+\\.el6\\..+")) {
            osVersion = OsVersion.RHEL6;
        } else if (getUname().matches("Linux.+\\.el7\\..+")) {
            osVersion = OsVersion.RHEL7;
        } else if (getUname().matches("Linux.+\\.el8_\\d\\..+")) {
            osVersion = OsVersion.RHEL8;
        } else if (getUname().matches("Linux.+\\.el9_\\d\\..+")) {
            osVersion = OsVersion.RHEL9;
        }
        return osVersion;
    }

    /**
     * @return The uname string.
     */
    public String getUname() {
        String uname = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            uname = matcher.group(2);
        }
        return uname;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }
}

/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;
import org.github.krashpad.util.jdk.JdkUtil.SignalCode;
import org.github.krashpad.util.jdk.JdkUtil.SignalNumber;

/**
 * <p>
 * SIGINFO
 * </p>
 * 
 * <p>
 * Signal information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) Linux:
 * </p>
 * 
 * <pre>
 * siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008
 * </pre>
 * 
 * <p>
 * 2) Windows:
 * </p>
 * 
 * <pre>
 * siginfo: ExceptionCode=0xc0000005, reading address 0x0000000000000048
 * siginfo: ExceptionCode=0xc00000fd, ExceptionInformation=0x0000000000000001 0x00000000c9dd0000
 * siginfo: EXCEPTION_ACCESS_VIOLATION (0xc0000005), reading address 0xffffffffffffffff
 * </pre>
 * 
 * <p>
 * 3) SI_USER:
 * </p>
 * 
 * <pre>
 * siginfo: si_signo: 11 (SIGSEGV), si_code: 0 (SI_USER), sent from pid: 107614 (uid: 1000)
 * siginfo: si_signo: 7 (SIGBUS), si_code: 0 (SI_USER), si_pid: 1008245, si_uid: 0
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class SigInfo implements LogEvent {

    private static final Pattern PATTERN;

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^siginfo: ((si_signo: \\d{1,2} \\((" + SignalNumber.SIGBUS + "|"
            + SignalNumber.SIGFPE + "|" + SignalNumber.SIGILL + "|" + SignalNumber.SIGSEGV
            + ")\\), si_code: [-]{0,1}\\d{1,3} \\((" + SignalCode.BUS_ADRALN + "|" + SignalCode.BUS_ADRERR + "|"
            + SignalCode.BUS_OBJERR + "|" + SignalCode.ILL_ILLOPN + "|" + SignalCode.SEGV_ACCERR + "|"
            + SignalCode.SEGV_MAPERR + "|" + SignalCode.SI_KERNEL + "|" + SignalCode.SI_TKILL + "|" + SignalCode.SI_USER
            + "|" + SignalCode.FPE_INTDIV + ")\\), (si_addr: " + JdkRegEx.ADDRESS
            + "|(sent from pid|si_pid): \\d{1,}[,]{0,1} [\\(]{0,1}(uid|si_uid): \\d{1,}[\\)]{0,1}))|ExceptionCode=("
            + JdkRegEx.WINDOWS_EXCEPTION_CODE_ACCESS_VIOLATION + "|" + JdkRegEx.WINDOWS_EXCEPTION_CODE_STACK_OVERFLOW
            + "), ((reading|writing) " + "address " + JdkRegEx.ADDRESS + "|ExceptionInformation=" + JdkRegEx.ADDRESS
            + " " + JdkRegEx.ADDRESS + ")|" + SignalNumber.EXCEPTION_ACCESS_VIOLATION + " \\(("
            + JdkRegEx.WINDOWS_EXCEPTION_CODE_ACCESS_VIOLATION + ")\\), reading address " + JdkRegEx.ADDRESS + ")[ ]*$";

    static {
        PATTERN = Pattern.compile(SigInfo.REGEX);
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
     * The log entry for the event.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public SigInfo(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.SIGINFO;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return Signal address.
     */
    public String getSignalAddress() {
        String address = null;
        Matcher matcher = PATTERN.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(6) != null) {
                // linux
                address = matcher.group(6);
            } else if (matcher.group(32) != null) {
                // windows
                address = matcher.group(32);
            }
        }
        return address;
    }

    /**
     * @return Signal code.
     */
    public SignalCode getSignalCode() {
        SignalCode code = SignalCode.UNKNOWN;
        Matcher matcher = PATTERN.matcher(logEntry);
        if (matcher.find()) {
            // Linux
            if (matcher.group(4) != null) {
                if (matcher.group(4).matches(SignalCode.BUS_ADRALN.toString())) {
                    code = SignalCode.BUS_ADRALN;
                } else if (matcher.group(4).matches(SignalCode.BUS_ADRERR.toString())) {
                    code = SignalCode.BUS_ADRERR;
                } else if (matcher.group(4).matches(SignalCode.BUS_OBJERR.toString())) {
                    code = SignalCode.BUS_OBJERR;
                } else if (matcher.group(4).matches(SignalCode.FPE_INTDIV.toString())) {
                    code = SignalCode.FPE_INTDIV;
                } else if (matcher.group(4).matches(SignalCode.ILL_ILLOPN.toString())) {
                    code = SignalCode.ILL_ILLOPN;
                } else if (matcher.group(4).matches(SignalCode.SEGV_ACCERR.toString())) {
                    code = SignalCode.SEGV_ACCERR;
                } else if (matcher.group(4).matches(SignalCode.SEGV_MAPERR.toString())) {
                    code = SignalCode.SEGV_MAPERR;
                } else if (matcher.group(4).matches(SignalCode.SI_KERNEL.toString())) {
                    code = SignalCode.SI_KERNEL;
                } else if (matcher.group(4).matches(SignalCode.SI_TKILL.toString())) {
                    code = SignalCode.SI_TKILL;
                } else if (matcher.group(4).matches(SignalCode.SI_USER.toString())) {
                    code = SignalCode.SI_USER;
                }
            }
        }
        return code;
    }

    /**
     * @return Signal number.
     */
    public SignalNumber getSignalNumber() {
        SignalNumber number = SignalNumber.UNKNOWN;
        Matcher matcher = PATTERN.matcher(logEntry);
        if (matcher.find()) {
            // Linux
            if (matcher.group(3) != null) {
                if (matcher.group(3).matches(SignalNumber.SIGBUS.toString())) {
                    number = SignalNumber.SIGBUS;
                } else if (matcher.group(3).matches(SignalNumber.SIGFPE.toString())) {
                    number = SignalNumber.SIGFPE;
                } else if (matcher.group(3).matches(SignalNumber.SIGILL.toString())) {
                    number = SignalNumber.SIGILL;
                } else if (matcher.group(3).matches(SignalNumber.SIGSEGV.toString())) {
                    number = SignalNumber.SIGSEGV;
                }
            } else if (matcher.group(13) != null) {
                // Windows format 1
                if (matcher.group(13).matches(JdkRegEx.WINDOWS_EXCEPTION_CODE_ACCESS_VIOLATION)) {
                    number = SignalNumber.EXCEPTION_ACCESS_VIOLATION;
                } else if (matcher.group(13).matches(JdkRegEx.WINDOWS_EXCEPTION_CODE_STACK_OVERFLOW)) {
                    number = SignalNumber.EXCEPTION_STACK_OVERFLOW;
                }
            } else if (matcher.group(31) != null) {
                // Windows format 2
                if (matcher.group(31).matches(JdkRegEx.WINDOWS_EXCEPTION_CODE_ACCESS_VIOLATION)) {
                    number = SignalNumber.EXCEPTION_ACCESS_VIOLATION;
                }
            }
        }
        return number;
    }
}

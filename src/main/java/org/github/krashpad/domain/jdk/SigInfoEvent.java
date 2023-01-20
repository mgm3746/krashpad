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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
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
 * ***REMOVED***
 * </pre>
 * 
 * <p>
 * 2) Windows:
 * </p>
 * 
 * <pre>
 * siginfo: ExceptionCode=0xc0000005, reading address 0x0000000000000048
 * siginfo: EXCEPTION_ACCESS_VIOLATION (0xc0000005), reading address 0xffffffffffffffff
 * </pre>
 * 
 * <p>
 * 3) SI_USER:
 * </p>
 * 
 * <pre>
 * siginfo: si_signo: 11 (SIGSEGV), si_code: 0 (SI_USER), sent from pid: 107614 (uid: 1000)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class SigInfoEvent implements LogEvent {

    private static final Pattern PATTERN;

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^siginfo: ((si_signo: \\d{1,2***REMOVED*** \\((" + SignalNumber.EXCEPTION_ACCESS_VIOLATION
            + "|" + SignalNumber.SIGBUS + "|" + SignalNumber.SIGFPE + "|" + SignalNumber.SIGILL + "|"
            + SignalNumber.SIGSEGV + ")\\), si_code: \\d{1,3***REMOVED*** \\((" + SignalCode.BUS_ADRALN + "|"
            + SignalCode.BUS_ADRERR + "|" + SignalCode.BUS_OBJERR + "|" + SignalCode.ILL_ILLOPN + "|"
            + SignalCode.SEGV_ACCERR + "|" + SignalCode.SEGV_MAPERR + "|" + SignalCode.SI_KERNEL + "|"
            + SignalCode.SI_USER + "|" + SignalCode.FPE_INTDIV + ")\\), (si_addr: " + JdkRegEx.ADDRESS
            + "|sent from pid: \\d{1,***REMOVED*** \\(uid: \\d{1,***REMOVED***\\)))|(ExceptionCode=|EXCEPTION_ACCESS_VIOLATION \\()"
            + JdkRegEx.ADDRESS + "[\\)]{0,1***REMOVED***, (reading|writing) address " + JdkRegEx.ADDRESS + ")$";

    static {
        PATTERN = Pattern.compile(SigInfoEvent.REGEX);
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
     * The log entry for the event.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public SigInfoEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.SIGINFO.toString();
    ***REMOVED***

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
            ***REMOVED*** else if (matcher.group(18) != null) {
                // windows
                address = matcher.group(18);
            ***REMOVED***
        ***REMOVED***
        return address;
    ***REMOVED***

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
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.BUS_ADRERR.toString())) {
                    code = SignalCode.BUS_ADRERR;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.BUS_OBJERR.toString())) {
                    code = SignalCode.BUS_OBJERR;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.FPE_INTDIV.toString())) {
                    code = SignalCode.FPE_INTDIV;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.ILL_ILLOPN.toString())) {
                    code = SignalCode.ILL_ILLOPN;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.SEGV_ACCERR.toString())) {
                    code = SignalCode.SEGV_ACCERR;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.SEGV_MAPERR.toString())) {
                    code = SignalCode.SEGV_MAPERR;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.SI_KERNEL.toString())) {
                    code = SignalCode.SI_KERNEL;
                ***REMOVED*** else if (matcher.group(4).matches(SignalCode.SI_USER.toString())) {
                    code = SignalCode.SI_USER;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return code;
    ***REMOVED***

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
                ***REMOVED*** else if (matcher.group(3).matches(SignalNumber.SIGFPE.toString())) {
                    number = SignalNumber.SIGFPE;
                ***REMOVED*** else if (matcher.group(3).matches(SignalNumber.SIGILL.toString())) {
                    number = SignalNumber.SIGILL;
                ***REMOVED*** else if (matcher.group(3).matches(SignalNumber.SIGSEGV.toString())) {
                    number = SignalNumber.SIGSEGV;
                ***REMOVED***
            ***REMOVED*** else if (matcher.group(13) != null) {
                // Windows
                if (matcher.group(13).matches("0xc0000005")) {
                    number = SignalNumber.EXCEPTION_ACCESS_VIOLATION;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return number;
    ***REMOVED***
***REMOVED***

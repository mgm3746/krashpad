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
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.SignalCode;
import org.github.errcat.util.jdk.JdkUtil.SignalNumber;

/**
 * <p>
 * SIGINFO
 * </p>
 * 
 * <p>
 * Signal information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class SigInfoEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^siginfo: si_signo: \\d{1,2***REMOVED*** \\((" + SignalNumber.SIGBUS + "|"
            + SignalNumber.SIGILL + "|" + SignalNumber.SIGSEGV + ")\\), si_code: \\d{1,2***REMOVED*** \\((" + SignalCode.BUS_ADRALN
            + "|" + SignalCode.BUS_ADRERR + "|" + SignalCode.BUS_OBJERR + "|" + SignalCode.SEGV_ACCERR + "|"
            + SignalCode.SEGV_MAPERR + ")\\), si_addr: " + JdkRegEx.ADDRESS + "$";

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
     * @return Signal number.
     */
    public SignalNumber getSignalNumber() {
        SignalNumber number = SignalNumber.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(1).matches(SignalNumber.SIGBUS.toString())) {
                number = SignalNumber.SIGBUS;
            ***REMOVED*** else if (matcher.group(1).matches(SignalNumber.SIGILL.toString())) {
                number = SignalNumber.SIGILL;
            ***REMOVED*** else if (matcher.group(1).matches(SignalNumber.SIGSEGV.toString())) {
                number = SignalNumber.SIGSEGV;
            ***REMOVED***
        ***REMOVED***
        return number;
    ***REMOVED***

    /**
     * @return Signal code.
     */
    public SignalCode getSignalCode() {
        SignalCode code = SignalCode.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(2).matches(SignalCode.BUS_ADRALN.toString())) {
                code = SignalCode.BUS_ADRALN;
            ***REMOVED*** else if (matcher.group(2).matches(SignalCode.BUS_ADRERR.toString())) {
                code = SignalCode.BUS_ADRERR;
            ***REMOVED*** else if (matcher.group(2).matches(SignalCode.BUS_OBJERR.toString())) {
                code = SignalCode.BUS_OBJERR;
            ***REMOVED*** else if (matcher.group(2).matches(SignalCode.SEGV_ACCERR.toString())) {
                code = SignalCode.SEGV_ACCERR;
            ***REMOVED*** else if (matcher.group(2).matches(SignalCode.SEGV_MAPERR.toString())) {
                code = SignalCode.SEGV_MAPERR;
            ***REMOVED***
        ***REMOVED***
        return code;
    ***REMOVED***
***REMOVED***

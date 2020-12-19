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

import org.github.errcat.domain.LogEvent;
import org.github.errcat.domain.ThrowAwayEvent;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * REGISTER
 * </p>
 * 
 * <p>
 * Register information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * RAX=0x0000000000000001, RBX=0x00007f67383dc748, RCX=0x0000000000000004, RDX=0x00007f69b031f898
 * RSP=0x00007fcbcc676c50, RBP=0x00007fcbcc676cb0, RSI=0x0000000000000000, RDI=0x00007f69b031f898
 * R8 =0x0000000000000005, R9 =0x0000000000000010, R10=0x0000000000000000, R11=0x0000000000000000
 * R12=0x00007f673d50bfe0, R13=0x00007f6a3a004628, R14=0x00007f6a3a004620, R15=0x00007f673d50bdf0
 * RIP=0x00007fcbd05a3b71, EFLAGS=0x0000000000010293, CSGSFS=0x0000000000000033, ERR=0x0000000000000004
 *   TRAPNO=0x000000000000000e
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class RegisterEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "***REMOVED***";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|" + JdkRegEx.REGISTER + ", " + JdkRegEx.REGISTER + ", "
            + JdkRegEx.REGISTER + ", " + JdkRegEx.REGISTER + "|  TRAPNO=" + JdkRegEx.ADDRESS + ")$";

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
    public RegisterEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.REGISTER.toString();
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
***REMOVED***

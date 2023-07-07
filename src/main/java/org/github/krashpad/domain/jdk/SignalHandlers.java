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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * SIGNAL_HANDLERS
 * </p>
 * 
 * <p>
 * Signal handlers information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Signal Handlers:
 * SIGSEGV: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGBUS: [libjvm.so+0xb73090], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGFPE: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGPIPE: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none
 * SIGXFSZ: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGILL: [libjvm.so+0x960f90], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGUSR1: SIG_DFL, sa_mask[0]=00000000000000000000000000000000, sa_flags=none
 * SIGUSR2: [libjvm.so+0x9628d0], sa_mask[0]=00000000000000000000000000000000, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGHUP: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none
 * SIGINT: SIG_IGN, sa_mask[0]=00000000000000000000000000000000, sa_flags=none
 * SIGTERM: [libjvm.so+0x964430], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGINFO
 * SIGQUIT: [libjvm.so+0x964430], sa_mask[0]=11111111011111111101111111111110, sa_flags=SA_RESTART|SA_SIGIN
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class SignalHandlers implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "Signal Handlers:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|[ ]{0,5}(\\*\\*\\* Expected: |\\*\\*\\* Handler was modified!|SIG39|SIG40|SIGSEGV|SIGBUS|SIGFPE|"
            + "SIGPIPE|SIGXFSZ|SIGILL|SIGUSR1|SIGUSR2|SIGHUP|SIGINT|SIGPWR|SIGTERM|SIGTRAP|SIGQUIT)).*$";

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
    public SignalHandlers(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.SIGNAL_HANDLERS.toString();
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

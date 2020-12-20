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
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * DEOPTIMIZATION_EVENT
 * </p>
 * 
 * <p>
 * Deoptimization information when the compiler has to recompile previously compiled code due to the compiled code no
 * longer being valid (e.g. a dynamic object has changed) or with tiered compilation when client compiled code is
 * replaced with server compiled code.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Deoptimization events (250 events):
 * Event: 5688.682 Thread 0x00007ff0ec053800 Uncommon trap: reason=unstable_if action=reinterpret pc=0x00007ff0dd93860c method=org.eclipse.swt.custom.StyledTextRenderer.disposeTextLayout(Lorg/eclipse/swt/graphics/TextLayout;)V @ 39
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class DeoptimizationEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "Deoptimization events \\(\\d{1,***REMOVED*** events\\):";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|Event: " + JdkRegEx.TIMESTAMP + " Thread "
            + JdkRegEx.ADDRESS
            + " Uncommon trap|\\[error occurred during error reporting \\(printing ring buffers\\), id 0xb\\]).*$";

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
    public DeoptimizationEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.DEOPTIMIZATION_EVENT.toString();
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

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
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * BITS
 * </p>
 * 
 * <p>
 * Marking bits and Mod Union Table information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * Marking Bits: (CMSBitMap*) 0x00007fcbc8249ce8
 *  Bits: [0x00007f677d83f000, 0x00007f6900a58c00)
 * </pre>
 * 
 * <pre>
 * Marking Bits (Prev, Next): (CMBitMap*) 0x00003fff74037098, (CMBitMap*) 0x00003fff740370f0
 * </pre>
 * 
 * <pre>
 *  Mod Union Table: (CMSBitMap*) 0x00007fcbc8249da8
 *  Bits: [0x00007f6777776000, 0x00007f677d83e670)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class BitsEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "(Marking Bits( \\(Prev, Next\\))?|Mod Union Table):";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|( (Begin|End|Next|Prev))? Bits:).*$";

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
    public BitsEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.BITS.toString();
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

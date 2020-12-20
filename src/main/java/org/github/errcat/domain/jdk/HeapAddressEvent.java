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
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * HEAP_ADDRESS
 * </p>
 * 
 * <p>
 * VM mutex/monitor information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop shift amount: 3
 * Narrow klass base: 0x0000000000000000, Narrow klass shift: 3
 * Compressed class space size: 1073741824 Address: 0x00000007c0000000
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeapAddressEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "[h|H]eap address:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|Narrow klass base:|Compressed class space size:).*$";

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
    public HeapAddressEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.HEAP_ADDRESS.toString();
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

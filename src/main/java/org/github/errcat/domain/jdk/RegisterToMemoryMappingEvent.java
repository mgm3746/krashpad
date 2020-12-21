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
 * REGISTER_TO_MEMORY_MAPPING
 * </p>
 * 
 * <p>
 * Register to memory mapping information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * 
 * RAX=0x0000000000000001 is an unknown value
 * RBX=0x00007f67383dc748 is an unknown value
 * RCX=0x0000000000000004 is an unknown value
 * RDX=0x00007f69b031f898 is an oop
 * java.util.LinkedList$Node 
  * - klass: 'java/util/LinkedList$Node'
 * RSP=0x00007fcbcc676c50 is an unknown value
 * RBP=0x00007fcbcc676cb0 is an unknown value
 * RSI=0x0000000000000000 is an unknown value
 * RDI=0x00007f69b031f898 is an oop
 * java.util.LinkedList$Node 
  * - klass: 'java/util/LinkedList$Node'
 * R8 =0x0000000000000005 is an unknown value
 * R9 =0x0000000000000010 is an unknown value
 * R10=0x0000000000000000 is an unknown value
 * R11=0x0000000000000000 is an unknown value
 * R12=0x00007f673d50bfe0 is pointing into metadata
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class RegisterToMemoryMappingEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "***REMOVED***";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER + "|" + JdkRegEx.REGISTER
            + "|[a-z]{1,***REMOVED***\\.|\\[[BCIL]|BufferBlob|\\[CodeBlob|Framesize| - (klass|length):|\\{" + JdkRegEx.ADDRESS
            + "\\***REMOVED*** - klass:|" + "(R[ ]{0,1***REMOVED***\\d{1,2***REMOVED***[ ]{0,1***REMOVED***|RBX|RCX|RDI|RSI)=|\\[error occurred during error reporting "
            + "\\(printing register info\\)|method entry point|StubRoutines).*$";

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
    public RegisterToMemoryMappingEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString();
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

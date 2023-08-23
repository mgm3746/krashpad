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
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * REGISTER_TO_MEMORY_MAPPING
 * </p>
 * 
 * <p>
 * Register to memory mapping information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Register to memory mapping:
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
public class RegisterToMemoryMapping implements LogEvent, ThrowAwayEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "Register to memory mapping:";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + JdkRegEx.REGISTER
            + "|Adapter for signature:.+|\\[[BCIL]([a-z]{1,}\\..+)?|BufferBlob.+|\\[CodeBlob.+|Framesize.+|"
            + " - (---- fields \\(total size \\d{1,} words\\)|---- non-static fields \\(\\d{1,} words\\)|"
            + "---- static fields \\(\\d{1,} words\\)|access|arrays|class annotations|class loader data|"
            + "class type annotations|constants|default_methods|default vtable indices|field annotations|"
            + "field type annotations|inner classes|instance size|java mirror|klass|klass size|length|local interfaces|"
            + "method ordering|methods|name|nest members|non-static oop maps|permitted subclasses|state|sub|super|"
            + "trans\\. interfaces):.*|\\{" + JdkRegEx.ADDRESS
            + "\\} - klass:.+|([R|r][ ]{0,1}\\d{1,2}[ ]{0,1}|RAX|RBP|RBX|RCX|RDX|RDI|RIP|RSI|RSP)=.*|"
            + "\\[error occurred during error reporting \\(printing register info\\).+|exception handling.+|"
            + "invoke return entry points.+| - itable length.+|method entry point.+|(i)?return.+| - fake entry for .+|"
            + " - (final|private|protected|transient|volatile) .+|StubRoutines.+| - signature: .+|"
            + " - '(next|size|value)' .+| - vtable length.+|" + JdkRegEx.CLASS + ".*)[ ]{0,}$";

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
    public RegisterToMemoryMapping(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString();
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

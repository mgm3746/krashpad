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
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * CURRENT_COMPILE_TASK
 * </p>
 * 
 * <p>
 * Current compile information
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Current CompileTask:
 * C2:   1092  423       4       java.util.HashMap$KeyIterator::next (8 bytes)
 * </pre>
 * 
 * <pre>
 * C2:   1360 2202 % !   4       org.jboss.modules.Module::addExportedPaths @ 1224 (1429 bytes)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CurrentCompileTask implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the compile id, sequentially incremented with every new compile.
     */
    private static final String _ID = "\\d{1,}";

    /**
     * Regular expression for the compilation level (0-4).
     * 
     * 0: interpreted (not compiled).
     * 
     * 1: C1 compiler without profiling information.
     * 
     * 2: C1 compiler with light profiling.
     * 
     * 3: C1 compiler with full profiling.
     * 
     * 4: C2 compiler (maximum performance).
     */
    private static final String _LEVEL = "\\d{1,}";

    /**
     * Regular expression for the attributes of the method being compiled.
     * 
     * https://github.com/openjdk/jdk/blob/6d30bbe62c10af0f2c80cb1eaac3d171fb7bffcb/src/hotspot/share/compiler/
     * compileTask.cpp#L227-L260
     * 
     * %: osr compilation (compilation was triggered by some loop rather than on method entry).
     * 
     * !: A method with exception handlers.
     * 
     * b: A blocking method.
     * 
     * n: A native method.
     * 
     * s: A synchronized method.
     */
    private static final String _METHOD_ATTRIBUTE = "([%!bns] )";

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "Current CompileTask:";

    /**
     * Regular expression for the compile timestamp.
     */
    private static final String _TIMESTAMP = "\\d{1,}";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|C[12]:[ ]{0,}" + _TIMESTAMP + "[ ]{1,}" + _ID
            + "[ ]{1,}" + _METHOD_ATTRIBUTE + "{0,}[ ]{1,}" + _LEVEL + ".+)$";

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
    public CurrentCompileTask(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString();
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

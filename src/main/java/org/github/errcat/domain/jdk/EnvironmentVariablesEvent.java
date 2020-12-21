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
 * ENVIRONMENT_VARIABLES
 * </p>
 * 
 * <p>
 * Environment variable information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * PATH=/path/to/bin
 * LD_LIBRARY_PATH=:/path/to/lib 
 * SHELL=/bin/ksh
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class EnvironmentVariablesEvent implements LogEvent, ThrowAwayEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "***REMOVED***";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER
            + "|CLASSPATH|DISPLAY|DYLD_LIBRARY_PATH|(JAVA|JRE)_HOME|LANG|LD_LIBRARY_PATH|LD_PRELOAD|OS=|PATH|"
            + "PROCESSOR_IDENTIFIER|SHELL|USERNAME).*$";

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
    public EnvironmentVariablesEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.ENVIRONMENT_VARIABLES.toString();
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

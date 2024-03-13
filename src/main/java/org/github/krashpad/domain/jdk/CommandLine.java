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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * COMMAND_LINE
 * </p>
 * 
 * <p>
 * Command line information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Command Line: -Xmx2048m -Xmx12G -Xms1G
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CommandLine implements LogEvent {

    private static Pattern pattern = Pattern.compile(CommandLine.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^Command Line: (.*)$";

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
    public CommandLine(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.COMMAND_LINE;
    }

    /**
     * @return The JVM options, or null if none exist.
     */
    public String getJvmOptions() {
        String jvmArgs = null;
        if (getValue() != null) {
            String[] options = getValue().split(org.github.joa.util.JdkRegEx.JVM_OPTIONS);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < options.length; i++) {
                if (i < options.length - 1) {
                    sb.append(options[i]);
                } else {
                    // strip java command from last option
                    Pattern opt = Pattern.compile("^( " + org.github.joa.util.JdkRegEx.JVM_OPTION + "[^ ]*)");
                    Matcher matcher = opt.matcher(options[i]);
                    if (matcher.find()) {
                        sb.append(matcher.group(1));
                    }
                }
            }
            if (sb.length() > 0) {
                jvmArgs = sb.toString();
            }
        }
        return jvmArgs;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The value of the VM argument.
     */
    public String getValue() {
        String value = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            value = matcher.group(1);
        }
        return value;
    }
}

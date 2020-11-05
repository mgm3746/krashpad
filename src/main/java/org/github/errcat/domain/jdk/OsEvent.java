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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * OS
 * </p>
 * 
 * <p>
 * OS information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * OS:                            Oracle Solaris 11.4 SPARC
 * </pre>
 * 
 * <pre>
 * OS:Red Hat Enterprise Linux Server release 7.7 (Maipo)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class OsEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^OS:[ ]{0,***REMOVED***(.+)$";

    private static Pattern pattern = Pattern.compile(REGEX);

    /**
     * The log entry for the event. Can be used for debugging purposes.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public OsEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.OS.toString();
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

    /**
     * @return The OS.
     */
    public OsType getOs() {
        OsType osType = OsType.UNKNOWN;
        if (getOsString().matches(".+Linux.+")) {
            osType = OsType.Linux;
        ***REMOVED*** else if (getOsString().matches(".+Solaris.+")) {
            osType = OsType.Solaris;
        ***REMOVED***
        return osType;
    ***REMOVED***

    /**
     * @return The OS string.
     */
    public String getOsString() {
        String os = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            os = matcher.group(1);
        ***REMOVED***
        return os;
    ***REMOVED***
***REMOVED***

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
import org.github.errcat.util.Constants.OsVendor;
import org.github.errcat.util.Constants.OsVersion;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * UNAME
 * </p>
 * 
 * <p>
 * uname information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64
 * </pre>
 * 
 * <pre>
 * uname:SunOS 5.11 11.4.23.69.3 sun4v
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class UnameEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^uname:(.+)$";

    private static Pattern pattern = Pattern.compile(REGEX);

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
    public UnameEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.UNAME.toString();
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
     * @return The OS type.
     */
    public OsType getOsType() {
        OsType osType = OsType.UNKNOWN;
        if (getOsString().matches("Linux.+")) {
            osType = OsType.Linux;
        ***REMOVED*** else if (getOsString().matches("SunOS.+")) {
            osType = OsType.Solaris;
        ***REMOVED***
        return osType;
    ***REMOVED***

    /**
     * @return The OS vendor.
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNKNOWN;
        if (getOsString().matches("Linux.+\\.el(6|7|8_\\d)\\..+")) {
            osVendor = OsVendor.RedHat;
        ***REMOVED***
        return osVendor;
    ***REMOVED***

    /**
     * @return The OS version.
     */
    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNKNOWN;
        if (getOsString().matches("Linux.+\\.el6\\..+")) {
            osVersion = OsVersion.RHEL6;
        ***REMOVED*** else if (getOsString().matches("Linux.+\\.el7\\..+")) {
            osVersion = OsVersion.RHEL7;
        ***REMOVED*** else if (getOsString().matches("Linux.+\\.el8_\\d\\..+")) {
            osVersion = OsVersion.RHEL8;
        ***REMOVED***
        return osVersion;
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

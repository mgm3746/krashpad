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
package org.github.errcat.util.jdk;

import java.util.Date;
import java.util.HashMap;

import org.github.errcat.domain.BlankLineEvent;
import org.github.errcat.domain.LogEvent;
import org.github.errcat.domain.UnknownEvent;
import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.domain.jdk.Release;
import org.github.errcat.domain.jdk.StackEvent;
import org.github.errcat.domain.jdk.UnameEvent;
import org.github.errcat.domain.jdk.VmInfoEvent;

/**
 * <p>
 * Utility methods and constants.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class JdkUtil {

    /**
     * OpenJDK8 release information.
     */
    private static HashMap<String, Release> openJdk8Releases;

    /**
     * Defined logging events.
     */
    public enum LogEventType {
        //
        BLANK_LINE, HEADER, JVM_INFO, OS, STACK, UNAME, UNKNOWN
    ***REMOVED***;

    /**
     * Defined Java vendors.
     */
    public enum JavaVendor {
        //
        Azul, OpenJDK, Oracle, UNKNOWN
    ***REMOVED***;

    /**
     * Defined Java specifications.
     */
    public enum JavaSpecification {
        //
        JDK6, JDK7, JDK8, JDK11, UNKNOWN
    ***REMOVED***;

    /**
     * Defined architectures.
     */
    public enum Arch {
        //
        X86_64, PPC64LE, UNKNOWN
    ***REMOVED***;

    /**
     * Defined crash reasons.
     */
    public enum CrashCause {
        //
        SIGSEGV, UNKNOWN
    ***REMOVED***;

    static {
        openJdk8Releases = new HashMap<String, Release>();
        openJdk8Releases.put("LATEST", new Release("11/05/2020", 4, "1.8.0_275-b01"));
        openJdk8Releases.put("1.8.0_272-b10", new Release("10/20/2020", 3, "1.8.0_272-b10"));
        openJdk8Releases.put("1.8.0_265-b01", new Release("07/14/2020", 2, "1.8.0_265-b01"));
        openJdk8Releases.put("1.8.0_262-b10", new Release("07/14/2020", 1, "1.8.0_262-b10"));
    ***REMOVED***

    /**
     * Create <code>LogEvent</code> from VM log line.
     * 
     * @param logLine
     *            The log line as it appears in the VM log.
     * @return The <code>LogEvent</code> corresponding to the log line.
     */
    public static final LogEvent parseLogLine(String logLine) {
        LogEventType eventType = identifyEventType(logLine);
        LogEvent event = null;
        switch (eventType) {

        case BLANK_LINE:
            event = new BlankLineEvent(logLine);
            break;
        case HEADER:
            event = new HeaderEvent(logLine);
            break;
        case JVM_INFO:
            event = new VmInfoEvent(logLine);
            break;
        case OS:
            event = new OsEvent(logLine);
            break;
        case STACK:
            event = new StackEvent(logLine);
            break;
        case UNAME:
            event = new UnameEvent(logLine);
            break;
        case UNKNOWN:
            event = new UnknownEvent(logLine);
            break;

        default:
            throw new AssertionError("Unexpected event type value: " + eventType);
        ***REMOVED***
        return event;
    ***REMOVED***

    /**
     * Identify the log line fatal error log event.
     * 
     * @param logLine
     *            The log entry.
     * @return The <code>LogEventType</code> of the log entry.
     */
    public static final LogEventType identifyEventType(String logLine) {
        LogEventType logEventType = LogEventType.UNKNOWN;
        if (BlankLineEvent.match(logLine)) {
            logEventType = LogEventType.BLANK_LINE;
        ***REMOVED*** else if (HeaderEvent.match(logLine)) {
            logEventType = LogEventType.HEADER;
        ***REMOVED*** else if (StackEvent.match(logLine)) {
            logEventType = LogEventType.STACK;
        ***REMOVED*** else if (VmInfoEvent.match(logLine)) {
            logEventType = LogEventType.JVM_INFO;
        ***REMOVED*** else if (OsEvent.match(logLine)) {
            logEventType = LogEventType.OS;
        ***REMOVED*** else if (UnameEvent.match(logLine)) {
            logEventType = LogEventType.UNAME;
        ***REMOVED***
        return logEventType;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The JDK releases for the JDK that produced the fatal error log.
     */
    public static final HashMap<String, Release> getJdkReleases(FatalErrorLog fatalErrorLog) {
        HashMap<String, Release> releases = null;
        if (fatalErrorLog.getJavaVendor().equals(JavaVendor.OpenJDK)
                && fatalErrorLog.getJavaSpecification().equals(JavaSpecification.JDK8)) {
            releases = openJdk8Releases;
        ***REMOVED***
        return releases;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return Latest JDK release date for the JDK that produced the fatal error log.
     */
    public static final Date getLatestJdkReleaseDate(FatalErrorLog fatalErrorLog) {
        Date date = null;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            date = releases.get("LATEST").getDate();
        ***REMOVED***
        return date;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return Latest JDK release string for the JDK that produced the fatal error log.
     */
    public static final String getLatestJdkReleaseString(FatalErrorLog fatalErrorLog) {
        String release = null;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            release = releases.get("LATEST").getVersion();
        ***REMOVED***
        return release;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return Latest JDK release number for the JDK that produced the fatal error log.
     */
    public static final int getLatestJdkReleaseNumber(FatalErrorLog fatalErrorLog) {
        int number = 0;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            Release latest = releases.get("LATEST");
            number = latest.getNumber();
        ***REMOVED***
        return number;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return true if the JDK that produced the fatal error log is the latest release, false otherwise.
     */
    public static final boolean isLatestJdkRelease(FatalErrorLog fatalErrorLog) {
        boolean isLatestRelease = true;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            Release latest = releases.get("LATEST");
            if (!latest.getVersion().equals(fatalErrorLog.getJdkReleaseString())) {
                isLatestRelease = false;
            ***REMOVED***
        ***REMOVED***
        return isLatestRelease;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The release date for the JDK that produced the fatal error log.
     */
    public static final Date getJdkReleaseDate(FatalErrorLog fatalErrorLog) {
        Date date = null;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            date = releases.get(fatalErrorLog.getJdkReleaseString()).getDate();
        ***REMOVED***
        return date;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The release number for the JDK that produced the fatal error log.
     */
    public static final int getJdkReleaseNumber(FatalErrorLog fatalErrorLog) {
        int number = 0;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases.size() > 0) {
            number = releases.get(fatalErrorLog.getJdkReleaseString()).getNumber();
        ***REMOVED***
        return number;
    ***REMOVED***

***REMOVED***

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
import java.util.Iterator;

import org.github.errcat.domain.BlankLineEvent;
import org.github.errcat.domain.LogEvent;
import org.github.errcat.domain.UnknownEvent;
import org.github.errcat.domain.jdk.DynamicLibraryEvent;
import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.domain.jdk.Release;
import org.github.errcat.domain.jdk.StackEvent;
import org.github.errcat.domain.jdk.UnameEvent;
import org.github.errcat.domain.jdk.VmInfoEvent;
import org.github.errcat.util.Constants.OsVersion;

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
     * OpenJDK8 RHEL6 rpm release information.
     */
    private static HashMap<String, Release> rhel6Jdk8RpmReleases;

    /**
     * OpenJDK8 RHEL7 rpm release information.
     */
    private static HashMap<String, Release> rhel7Jdk8RpmReleases;

    /**
     * OpenJDK8 RHEL8 rpm release information.
     */
    private static HashMap<String, Release> rhel8Jdk8RpmReleases;

    /**
     * OpenJDK8 RHEL zip release information.
     */
    private static HashMap<String, Release> rhelJdk8ZipReleases;

    /**
     * OpenJDK8 Windows release information.
     */
    private static HashMap<String, Release> windowsJdk8Releases;

    /**
     * OpenJDK11 RHEL7 rpm release information.
     */
    private static HashMap<String, Release> rhel7Jdk11RpmReleases;

    /**
     * OpenJDK11 RHEL8 rpm release information.
     */
    private static HashMap<String, Release> rhel8Jdk11RpmReleases;

    /**
     * OpenJDK11 RHEL zip release information.
     */
    private static HashMap<String, Release> rhelJdk11ZipReleases;

    /**
     * OpenJDK11 Windows release information.
     */
    private static HashMap<String, Release> redHatJdk11WindowsReleases;

    /**
     * Defined logging events.
     */
    public enum LogEventType {
        //
        BLANK_LINE, DYNAMIC_LIBRARY, HEADER, JVM_INFO, OS, STACK, UNAME, UNKNOWN
    ***REMOVED***;

    /**
     * Defined Java vendors.
     */
    public enum JavaVendor {
        //
        AZUL, RED_HAT, ORACLE, UNKNOWN
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
        X86_64, PPC64LE, SPARC, UNKNOWN
    ***REMOVED***;

    /**
     * Defined crash reasons.
     */
    public enum CrashCause {
        //
        SIGSEGV, UNKNOWN
    ***REMOVED***;

    static {
        /*
         * Note: Build date/time guesses have 00:00:00 time
         */

        rhel6Jdk8RpmReleases = new HashMap<String, Release>();
        rhel6Jdk8RpmReleases.put("LATEST", new Release("Nov 5 2020 00:00:01", 11, "1.8.0_275-b01"));
        rhel6Jdk8RpmReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 23:38:03", 10, "1.8.0_272-b10"));
        rhel6Jdk8RpmReleases.put("1.8.0_265-b01", new Release("Jul 27 2020 00:00:00", 9, "1.8.0_265-b01"));
        rhel6Jdk8RpmReleases.put("1.8.0_262-b10", new Release("Jul 12 2020 19:35:32", 8, "1.8.0_262-b10"));
        rhel6Jdk8RpmReleases.put("1.8.0_252-b09", new Release("Apr 14 2020 14:55:11", 7, "1.8.0_252-b09"));
        rhel6Jdk8RpmReleases.put("1.8.0_242-b08", new Release("Jan 19 2020 00:00:00", 6, "1.8.0_242-b08"));
        rhel6Jdk8RpmReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 00:00:00", 5, "1.8.0_232-b09"));
        rhel6Jdk8RpmReleases.put("1.8.0_222-b10", new Release("Jul 17 2019 00:00:00", 4, "1.8.0_222-b10"));
        rhel6Jdk8RpmReleases.put("1.8.0_212-b04", new Release("Apr 16 2019 00:00:00", 3, "1.8.0_212-b04"));
        rhel6Jdk8RpmReleases.put("1.8.0_202-b09", new Release("Jan 15 2019 00:00:00", 2, "1.8.0_202-b08"));
        rhel6Jdk8RpmReleases.put("1.8.0_201-b09", new Release("Jan 15 2019 00:00:00", 1, "1.8.0_201-b09"));

        rhel7Jdk8RpmReleases = new HashMap<String, Release>();
        rhel7Jdk8RpmReleases.put("LATEST", new Release("Nov 5 2020 00:00:01", 30, "1.8.0_275-b01"));
        rhel7Jdk8RpmReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 23:38:03", 29, "1.8.0_272-b10"));
        rhel7Jdk8RpmReleases.put("1.8.0_265-b01", new Release("Jul 28 2020 11:07:07", 28, "1.8.0_265-b01"));
        rhel7Jdk8RpmReleases.put("1.8.0_262-b10", new Release("Jul 12 2020 18:55:08", 27, "1.8.0_262-b10"));
        rhel7Jdk8RpmReleases.put("1.8.0_252-b09", new Release("Apr 14 2020 14:55:11", 26, "1.8.0_252-b09"));
        rhel7Jdk8RpmReleases.put("1.8.0_242-b08", new Release("Jan 19 2020 00:00:00", 25, "1.8.0_242-b08"));
        rhel7Jdk8RpmReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 00:00:00", 24, "1.8.0_232-b09"));
        rhel7Jdk8RpmReleases.put("1.8.0_222-b10", new Release("Jul 17 2019 00:00:00", 23, "1.8.0_222-b10"));
        rhel7Jdk8RpmReleases.put("1.8.0_212-b04", new Release("Apr 16 2019 00:00:00", 22, "1.8.0_212-b04"));
        rhel7Jdk8RpmReleases.put("1.8.0_202-b09", new Release("Jan 15 2019 00:00:00", 21, "1.8.0_202-b08"));
        rhel7Jdk8RpmReleases.put("1.8.0_201-b09", new Release("Jan 15 2019 00:00:00", 20, "1.8.0_201-b09"));
        rhel7Jdk8RpmReleases.put("1.8.0_191", new Release("Jan 15 2019 00:00:00", 19, "1.8.0_191"));
        rhel7Jdk8RpmReleases.put("1.8.0_181", new Release("Jan 15 2019 00:00:00", 18, "1.8.0_181"));
        rhel7Jdk8RpmReleases.put("1.8.0_171", new Release("Jan 15 2019 00:00:00", 17, "1.8.0_171"));
        rhel7Jdk8RpmReleases.put("1.8.0_161", new Release("Jan 15 2019 00:00:00", 16, "1.8.0_161"));
        rhel7Jdk8RpmReleases.put("1.8.0_151", new Release("Jan 15 2019 00:00:00", 15, "1.8.0_151"));
        rhel7Jdk8RpmReleases.put("1.8.0_141", new Release("Jan 15 2019 00:00:00", 14, "1.8.0_141"));
        rhel7Jdk8RpmReleases.put("1.8.0_131-b12", new Release("Jun 13 2017 11:27:53", 13, "1.8.0_131-b12"));
        rhel7Jdk8RpmReleases.put("1.8.0_121", new Release("Jan 15 2019 00:00:00", 12, "1.8.0_121"));
        rhel7Jdk8RpmReleases.put("1.8.0_111", new Release("Jan 15 2019 00:00:00", 11, "1.8.0_111"));
        rhel7Jdk8RpmReleases.put("1.8.0_102", new Release("Jan 15 2019 00:00:00", 10, "1.8.0_102"));
        rhel7Jdk8RpmReleases.put("1.8.0_101", new Release("Jan 15 2019 00:00:00", 9, "1.8.0_101"));
        rhel7Jdk8RpmReleases.put("1.8.0_91", new Release("Jan 15 2019 00:00:00", 8, "1.8.0_91"));
        rhel7Jdk8RpmReleases.put("1.8.0_77", new Release("Jan 15 2019 00:00:00", 7, "1.8.0_77"));
        rhel7Jdk8RpmReleases.put("1.8.0_71", new Release("Jan 15 2019 00:00:00", 6, "1.8.0_71"));
        rhel7Jdk8RpmReleases.put("1.8.0_65", new Release("Jan 15 2019 00:00:00", 5, "1.8.0_65"));
        rhel7Jdk8RpmReleases.put("1.8.0_60", new Release("Jan 15 2019 00:00:00", 4, "1.8.0_60"));
        rhel7Jdk8RpmReleases.put("1.8.0_51", new Release("Jan 15 2019 00:00:00", 3, "1.8.0_51"));
        rhel7Jdk8RpmReleases.put("1.8.0_45", new Release("Jan 15 2019 00:00:00", 2, "1.8.0_45"));
        rhel7Jdk8RpmReleases.put("1.8.0_31", new Release("Jan 15 2019 00:00:00", 1, "1.8.0_31"));

        rhel8Jdk8RpmReleases = new HashMap<String, Release>();
        rhel8Jdk8RpmReleases.put("LATEST", new Release("Nov 5 2020 00:00:01", 11, "1.8.0_275-b01"));
        rhel8Jdk8RpmReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 23:38:03", 10, "1.8.0_272-b10"));
        rhel8Jdk8RpmReleases.put("1.8.0_265-b01", new Release("Jul 27 2020 00:00:00", 9, "1.8.0_265-b01"));
        rhel8Jdk8RpmReleases.put("1.8.0_262-b10", new Release("Jul 12 2020 19:35:32", 8, "1.8.0_262-b10"));
        rhel8Jdk8RpmReleases.put("1.8.0_252-b09", new Release("Apr 14 2020 14:55:11", 7, "1.8.0_252-b09"));
        rhel8Jdk8RpmReleases.put("1.8.0_242-b08", new Release("Jan 19 2020 00:00:00", 6, "1.8.0_242-b08"));
        rhel8Jdk8RpmReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 00:00:00", 5, "1.8.0_232-b09"));
        rhel8Jdk8RpmReleases.put("1.8.0_222-b10", new Release("Jul 17 2019 00:00:00", 4, "1.8.0_222-b10"));
        rhel8Jdk8RpmReleases.put("1.8.0_212-b04", new Release("Apr 16 2019 00:00:00", 3, "1.8.0_212-b04"));
        rhel8Jdk8RpmReleases.put("1.8.0_202-b09", new Release("Jan 15 2019 00:00:00", 2, "1.8.0_202-b08"));
        rhel8Jdk8RpmReleases.put("1.8.0_201-b09", new Release("Jan 15 2019 00:00:00", 1, "1.8.0_201-b09"));

        rhelJdk8ZipReleases = new HashMap<String, Release>();
        // First RHEL zip was 1.8.0_222.
        rhelJdk8ZipReleases.put("LATEST", new Release("Nov 11 2020 12:18:48", 8, "1.8.0_275-b01"));
        rhelJdk8ZipReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 12:17:01", 7, "1.8.0_272-b10"));
        rhelJdk8ZipReleases.put("1.8.0_265-b01", new Release("Jul 28 2020 13:27:15", 6, "1.8.0_265-b01"));
        rhelJdk8ZipReleases.put("1.8.0_262-b10", new Release("Jul 14 2020 11:46:22", 5, "1.8.0_262-b10"));
        rhelJdk8ZipReleases.put("1.8.0_252-b09", new Release("Apr 13 2020 13:01:26", 4, "1.8.0_252-b09"));
        rhelJdk8ZipReleases.put("1.8.0_242-b08", new Release("Jan 17 2020 09:36:23", 3, "1.8.0_242-b08"));
        rhelJdk8ZipReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 05:49:57", 2, "1.8.0_232-b09"));
        rhelJdk8ZipReleases.put("1.8.0_222-b10", new Release("Aug 2 2019 08:16:48", 1, "1.8.0_222-b10"));

        windowsJdk8Releases = new HashMap<String, Release>();
        // First RH Windows release was 1.8.0_201. There was no RH Windows release for u202.
        windowsJdk8Releases.put("LATEST", new Release("Nov 5 2020 00:00:00", 11, "1.8.0_275-b01"));
        windowsJdk8Releases.put("1.8.0_272-b10", new Release("Oct 20 2020 00:00:00", 10, "1.8.0_272-b10"));
        windowsJdk8Releases.put("1.8.0_265-b01", new Release("Jul 27 2020 00:00:00", 9, "1.8.0_265-b01"));
        windowsJdk8Releases.put("1.8.0_262-b10", new Release("Jul 14 2020 00:00:00", 8, "1.8.0_262-b10"));
        windowsJdk8Releases.put("1.8.0_252-b09", new Release("Apr 14 2020 00:00:00", 7, "1.8.0_252-b09"));
        windowsJdk8Releases.put("1.8.0_242-b08", new Release("Jan 19 2020 00:00:00", 6, "1.8.0_242-b08"));
        windowsJdk8Releases.put("1.8.0_232-b09", new Release("Oct 15 2019 00:00:00", 5, "1.8.0_232-b09"));
        windowsJdk8Releases.put("1.8.0_222-b10", new Release("Jul 16 2019 00:00:00", 4, "1.8.0_222-b10"));
        windowsJdk8Releases.put("1.8.0_212-b04", new Release("Apr 16 2019 00:00:00", 3, "1.8.0_212-b04"));
        windowsJdk8Releases.put("1.8.0_201-b09", new Release("Jan 15 2019 00:00:00", 1, "1.8.0_201-b09"));

        rhel7Jdk11RpmReleases = new HashMap<String, Release>();
        rhel7Jdk11RpmReleases.put("LATEST", new Release("Oct 20 2020 20:47:31", 2, "11.0.9+11-LTS"));
        rhel7Jdk11RpmReleases.put("11.0.7+10-LTS", new Release("Apr 14 2020 21:38:20", 1, "11.0.7+10-LTS"));

        rhel8Jdk11RpmReleases = new HashMap<String, Release>();
        rhel8Jdk11RpmReleases.put("LATEST", new Release("Oct 20 2020 20:47:31", 2, "11.0.9+11-LTS"));
        rhel8Jdk11RpmReleases.put("11.0.8+10-LTS", new Release("Jul 11 2020 02:33:15", 1, "11.0.8+10-LTS"));
        rhel8Jdk11RpmReleases.put("11.0.7+10-LTS", new Release("Apr 14 2020 21:38:20", 1, "11.0.7+10-LTS"));

        rhelJdk11ZipReleases = new HashMap<String, Release>();
        // First RHEL zip was 11.0.4.11.
        rhelJdk11ZipReleases.put("LATEST", new Release("Nov 11 2020 12:19:11", 7, "11.0.9.1+1-LTS"));
        rhelJdk11ZipReleases.put("11.0.9+11-LTS", new Release("Oct 20 2020 12:01:49", 6, "11.0.9+11-LTS"));
        rhelJdk11ZipReleases.put("11.0.8+10-LTS", new Release("Jul 14 2020 06:26:42", 5, "11.0.8+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.7+10-LTS", new Release("Apr 9 2020 11:42:52", 4, "11.0.7+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.6+10-LTS", new Release("Jan 12 2020 10:38:53", 3, "11.0.6+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.5+10-LTS", new Release("Oct 15 2019 09:18:41", 2, "11.0.5+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.4+11-LTS", new Release("Aug 2 2019 08:21:47", 1, "11.0.4+11-LTS"));
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
        case DYNAMIC_LIBRARY:
            event = new DynamicLibraryEvent(logLine);
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
        ***REMOVED*** else if (DynamicLibraryEvent.match(logLine)) {
            logEventType = LogEventType.DYNAMIC_LIBRARY;
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
        if (fatalErrorLog.getJavaVendor().equals(JavaVendor.RED_HAT)) {
            if (fatalErrorLog.isRhel()) {
                if (isRhelRpmInstall(fatalErrorLog)) {
                    if (fatalErrorLog.getOsVersion() == OsVersion.RHEL6
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel6Jdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel7Jdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK11) {
                        releases = rhel7Jdk11RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel8Jdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK11) {
                        releases = rhel8Jdk11RpmReleases;
                    ***REMOVED***
                ***REMOVED*** else if (isRhelZipInstall(fatalErrorLog)) {
                    switch (fatalErrorLog.getJavaSpecification()) {
                    case JDK8:
                        releases = rhelJdk8ZipReleases;
                        break;
                    case JDK11:
                        releases = rhelJdk11ZipReleases;
                        break;
                    case UNKNOWN:
                    default:
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
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
            date = releases.get("LATEST").getBuildDate();
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
     * @return The known release date for the JDK build that produced the fatal error log.
     */
    public static final Date getJdkReleaseDate(FatalErrorLog fatalErrorLog) {
        Date date = null;
        if (fatalErrorLog != null) {
            HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
            if (releases != null && releases.size() > 0) {
                String jdkRelease = fatalErrorLog.getJdkReleaseString();
                if (jdkRelease != null) {
                    Release release = releases.get(jdkRelease);
                    if (release != null) {
                        date = release.getBuildDate();
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
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
        if (fatalErrorLog != null) {
            HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
            if (releases != null && releases.size() > 0) {
                String jdkRelease = fatalErrorLog.getJdkReleaseString();
                if (jdkRelease != null) {
                    Release release = releases.get(jdkRelease);
                    if (release != null) {
                        number = release.getNumber();
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return number;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return True if the JDK that produced the fatal error log is a Red Hat rpm install, false otherwise.
     */
    public static final boolean isRhelRpmInstall(FatalErrorLog fatalErrorLog) {
        boolean isRedHatRpmInstall = false;
        if (fatalErrorLog.isRhel()) {
            Iterator<DynamicLibraryEvent> iterator = fatalErrorLog.getDynamicLibrary().iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getFilePath() != null && (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH)
                        || event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH))) {
                    isRedHatRpmInstall = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRedHatRpmInstall;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return True if the JDK that produced the fatal error log is a Red Hat build of OpenJDK zip install on RHEL,
     *         false otherwise.
     */
    public static final boolean isRhelZipInstall(FatalErrorLog fatalErrorLog) {
        boolean isRedHatZipInstall = false;
        if (fatalErrorLog.isRhel() && !isRhelRpmInstall(fatalErrorLog) && fatalErrorLog.getArch() == Arch.X86_64
                && rhelJdk8ZipReleases.containsKey(fatalErrorLog.getJdkReleaseString())) {
            isRedHatZipInstall = true;
        ***REMOVED***
        return isRedHatZipInstall;
    ***REMOVED***
***REMOVED***

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
     * OpenJDK8 RHEL rpm release information.
     */
    private static HashMap<String, Release> redHatJdk8RhelRpmReleases;

    /**
     * OpenJDK8 RHEL zip release information.
     */
    private static HashMap<String, Release> redHatJdk8RhelZipReleases;

    /**
     * OpenJDK8 Windows release information.
     */
    private static HashMap<String, Release> redHatJdk8WindowsReleases;

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
        /*
         * Note: Build date/time guesses have 00:00:00 time
         */

        redHatJdk8RhelRpmReleases = new HashMap<String, Release>();
        redHatJdk8RhelRpmReleases.put("LATEST", new Release("Nov 5 2020 00:00:01", 11, "1.8.0_275-b01"));
        redHatJdk8RhelRpmReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 23:38:03", 10, "1.8.0_272-b10"));
        redHatJdk8RhelRpmReleases.put("1.8.0_265-b01", new Release("Jul 27 2020 00:00:00", 9, "1.8.0_265-b01"));
        redHatJdk8RhelRpmReleases.put("1.8.0_262-b10", new Release("Jul 12 2020 19:35:32", 8, "1.8.0_262-b10"));
        redHatJdk8RhelRpmReleases.put("1.8.0_252-b09", new Release("Apr 14 2020 00:00:00", 7, "1.8.0_252-b09"));
        redHatJdk8RhelRpmReleases.put("1.8.0_242-b08", new Release("Jan 19 2020 00:00:00", 6, "1.8.0_242-b08"));
        redHatJdk8RhelRpmReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 00:00:00", 5, "1.8.0_232-b09"));
        redHatJdk8RhelRpmReleases.put("1.8.0_222-b10", new Release("Jul 17 2019 00:00:00", 4, "1.8.0_222-b10"));
        redHatJdk8RhelRpmReleases.put("1.8.0_212-b04", new Release("Apr 16 2019 00:00:00", 3, "1.8.0_212-b04"));
        redHatJdk8RhelRpmReleases.put("1.8.0_202-b09", new Release("Jan 15 2019 00:00:00", 2, "1.8.0_202-b08"));
        redHatJdk8RhelRpmReleases.put("1.8.0_201-b09", new Release("Jan 15 2019 00:00:00", 1, "1.8.0_201-b09"));

        redHatJdk8RhelZipReleases = new HashMap<String, Release>();
        // First RHEL zip was 1.8.0_222
        redHatJdk8RhelZipReleases.put("LATEST", new Release("Nov 11 2020 12:18:48", 8, "1.8.0_275-b01"));
        redHatJdk8RhelZipReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 12:17:01", 7, "1.8.0_272-b10"));
        redHatJdk8RhelZipReleases.put("1.8.0_265-b01", new Release("Jul 28 2020 13:27:15", 6, "1.8.0_265-b01"));
        redHatJdk8RhelZipReleases.put("1.8.0_262-b10", new Release("Jul 14 2020 11:46:22", 5, "1.8.0_262-b10"));
        redHatJdk8RhelZipReleases.put("1.8.0_252-b09", new Release("Apr 13 2020 13:01:26", 4, "1.8.0_252-b09"));
        redHatJdk8RhelZipReleases.put("1.8.0_242-b08", new Release("Jan 17 2020 09:36:23", 3, "1.8.0_242-b08"));
        redHatJdk8RhelZipReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 05:49:57", 2, "1.8.0_232-b09"));
        redHatJdk8RhelZipReleases.put("1.8.0_222-b10", new Release("Aug 2 2019 08:16:48", 1, "1.8.0_222-b10"));

        redHatJdk8WindowsReleases = new HashMap<String, Release>();
        // There was no RH Windows release for u202
        redHatJdk8WindowsReleases.put("LATEST", new Release("Nov 5 2020 00:00:00", 11, "1.8.0_275-b01"));
        redHatJdk8WindowsReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 00:00:00", 10, "1.8.0_272-b10"));
        redHatJdk8WindowsReleases.put("1.8.0_265-b01", new Release("Jul 27 2020 00:00:00", 9, "1.8.0_265-b01"));
        redHatJdk8WindowsReleases.put("1.8.0_262-b10", new Release("Jul 14 2020 00:00:00", 8, "1.8.0_262-b10"));
        redHatJdk8WindowsReleases.put("1.8.0_252-b09", new Release("Apr 14 2020 00:00:00", 7, "1.8.0_252-b09"));
        redHatJdk8WindowsReleases.put("1.8.0_242-b08", new Release("Jan 19 2020 00:00:00", 6, "1.8.0_242-b08"));
        redHatJdk8WindowsReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 00:00:00", 5, "1.8.0_232-b09"));
        redHatJdk8WindowsReleases.put("1.8.0_222-b10", new Release("Jul 16 2019 00:00:00", 4, "1.8.0_222-b10"));
        redHatJdk8WindowsReleases.put("1.8.0_212-b04", new Release("Apr 16 2019 00:00:00", 3, "1.8.0_212-b04"));
        redHatJdk8WindowsReleases.put("1.8.0_201-b09", new Release("Jan 15 2019 00:00:00", 1, "1.8.0_201-b09"));
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
            if (fatalErrorLog.getJavaSpecification().equals(JavaSpecification.JDK8)) {
                if (fatalErrorLog.isRhel()) {
                    if (isRhelRpmInstall(fatalErrorLog)) {
                        releases = redHatJdk8RhelRpmReleases;
                    ***REMOVED*** else if (isRhelZipInstall(fatalErrorLog)) {
                        releases = redHatJdk8RhelZipReleases;
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
                if (event.isVmLibrary() && event.getFilePath().matches(JdkRegEx.RH_RPM_JDK8_FILE_PATH)) {
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
                && redHatJdk8RhelZipReleases.containsKey(fatalErrorLog.getJdkReleaseString())) {
            isRedHatZipInstall = true;
        ***REMOVED***
        return isRedHatZipInstall;
    ***REMOVED***
***REMOVED***

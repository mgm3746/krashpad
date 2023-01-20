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

import org.github.joa.domain.Os;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.Constants.OsVendor;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.Arch;

/**
 * <p>
 * UNAME
 * </p>
 * 
 * <p>
 * uname information.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) JDK8/11:
 * </p>
 * 
 * <pre>
 * uname:Linux 3.10.0-1127.19.1.el7.x86_64 ***REMOVED***1 SMP Tue Aug 11 19:12:04 EDT 2020 x86_64
 * </pre>
 * 
 * <pre>
 * uname:SunOS 5.11 11.4.23.69.3 sun4v
 * </pre>
 * 
 * <pre>
 * uname:SunOS 5.11 11.3 i86pc  (T2 libthread)
 * </pre>
 * 
 * <p>
 * 2) Split across 2 lines:
 * </p>
 * 
 * <pre>
 * uname:SunOS 5.11 11.4.32.88.3 sun4v
 *   (T2 libthread)
 * </pre>
 * 
 * <p>
 * JDK17:
 * </p>
 * 
 * <pre>
 * uname: Linux 4.18.0-348.2.1.el8_5.x86_64 ***REMOVED***1 SMP Mon Nov 8 13:30:15 EST 2021 x86_64
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class UnameEvent implements LogEvent {

    private static Pattern pattern = Pattern.compile(UnameEvent.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(uname:[ ]{0,1***REMOVED***((Linux|SunOS) .+(i86pc|sun4v|ppc64(le)?|x86_64).*)|"
            + "  \\(T2 libthread\\))$";

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

    /**
     * @return The chip architecture.
     */
    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexArch = 4;
            if (matcher.group(indexArch).equals("x86_64")) {
                arch = Arch.X86_64;
            ***REMOVED*** else if (matcher.group(indexArch).equals("ppc64")) {
                arch = Arch.PPC64;
            ***REMOVED*** else if (matcher.group(indexArch).equals("ppc64le")) {
                arch = Arch.PPC64LE;
            ***REMOVED*** else if (matcher.group(indexArch).equals("sun4v")) {
                arch = Arch.SPARC;
            ***REMOVED*** else if (matcher.group(indexArch).equals("i86pc")) {
                arch = Arch.I86PC;
            ***REMOVED***
        ***REMOVED***
        return arch;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.UNAME.toString();
    ***REMOVED***

    /**
     * @return The OS type.
     */
    public Os getOsType() {
        Os osType = Os.UNIDENTIFIED;
        if (getUname().matches("Linux.+")) {
            osType = Os.LINUX;
        ***REMOVED*** else if (getUname().matches("SunOS.+")) {
            osType = Os.SOLARIS;
        ***REMOVED***
        return osType;
    ***REMOVED***

    /**
     * @return The OS vendor.
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNIDENTIFIED;
        if (getUname().matches("Linux.+\\.el(6|7|8_\\d)\\..+")) {
            osVendor = OsVendor.REDHAT;
        ***REMOVED*** else if (getUname().matches("SunOS.+")) {
            osVendor = OsVendor.ORACLE;
        ***REMOVED***
        return osVendor;
    ***REMOVED***

    /**
     * @return The OS version.
     */
    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNIDENTIFIED;
        if (getUname().matches("Linux.+\\.el6\\..+")) {
            osVersion = OsVersion.RHEL6;
        ***REMOVED*** else if (getUname().matches("Linux.+\\.el7\\..+")) {
            osVersion = OsVersion.RHEL7;
        ***REMOVED*** else if (getUname().matches("Linux.+\\.el8_\\d\\..+")) {
            osVersion = OsVersion.RHEL8;
        ***REMOVED***
        return osVersion;
    ***REMOVED***

    /**
     * @return The uname string.
     */
    public String getUname() {
        String uname = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            uname = matcher.group(2);
        ***REMOVED***
        return uname;
    ***REMOVED***
***REMOVED***

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
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.github.errcat.util.jdk.JdkUtil.JavaVendor;

/**
 * <p>
 * JVM_INFO
 * </p>
 * 
 * <p>
 * JVM environment information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), built on Oct  6 2018 06:46:09 by "java_re" with gcc 7.3.0
 * </pre>
 * 
 * <pre>
 * vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE (Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by "zulu_re" with gcc 4.4.7 20120313 (Red Hat 4.4.7-3)
 * </pre>
 * 
 * <pre>
 * vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), built on Oct  9 2019 18:41:22 by "mockbuild" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmInfoEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^vm_info: (Java HotSpot\\(TM\\)|OpenJDK) 64-Bit Server VM \\(.+\\) for "
            + "linux-(amd64|ppc64le) JRE (\\(Zulu.+\\) )?\\((((1|11)\\.(0|6|7|8))\\.\\d.+)\\).+$";

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
    public VmInfoEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.JVM_INFO.toString();
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
     * @return The Java vendor.
     */
    public JavaVendor getJavaVendor() {
        JavaVendor vendor = JavaVendor.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexVendor = 1;
            int indexAzul = 3;
            if (matcher.group(indexVendor).equals("OpenJDK")) {
                if (matcher.group(indexAzul) != null) {
                    vendor = JavaVendor.Azul;
                ***REMOVED*** else {
                    vendor = JavaVendor.OpenJDK;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return vendor;
    ***REMOVED***

    /**
     * @return The JDK version.
     */
    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexJdkVersion = 5;
            if (matcher.group(indexJdkVersion).equals("11.0")) {
                version = JavaSpecification.JDK11;
            ***REMOVED*** else if (matcher.group(indexJdkVersion).equals("1.8")) {
                version = JavaSpecification.JDK8;
            ***REMOVED*** else if (matcher.group(indexJdkVersion).equals("1.7")) {
                version = JavaSpecification.JDK7;
            ***REMOVED*** else if (matcher.group(indexJdkVersion).equals("1.6")) {
                version = JavaSpecification.JDK6;
            ***REMOVED***
        ***REMOVED***
        return version;
    ***REMOVED***

    /**
     * @return The Java release string.
     */
    public String getJdkReleaseString() {
        String release = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            release = matcher.group(4);
        ***REMOVED***
        return release;
    ***REMOVED***

    /**
     * @return The chip architecture.
     */
    public Arch getArch() {
        Arch arch = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexArch = 2;
            if (matcher.group(indexArch).equals("amd64") || matcher.group(indexArch).equals("linux64")) {
                arch = Arch.X86_64;
            ***REMOVED*** else if (matcher.group(indexArch).equals("ppc64le")) {
                arch = Arch.PPC64LE;
            ***REMOVED***
        ***REMOVED***
        return arch;
    ***REMOVED***
***REMOVED***

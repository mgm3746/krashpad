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
 * HEADER
 * </p>
 * 
 * <p>
 * Header information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * ***REMOVED***
 * ***REMOVED*** A fatal error has been detected by the Java Runtime Environment:
 * ***REMOVED***
 * ***REMOVED***  SIGSEGV (0xb) at pc=0x00007fcbd05a3b71, pid=52385, tid=0x00007fcbcc677700
 * ***REMOVED***
 * ***REMOVED*** JRE version: Java(TM) SE Runtime Environment (8.0_192-b12) (build 1.8.0_192-b12)
 * ***REMOVED*** Java VM: Java HotSpot(TM) 64-Bit Server VM (25.192-b12 mixed mode linux-amd64 )
 * ***REMOVED*** Problematic frame:
 * ***REMOVED*** V  [libjvm.so+0x645b71]  oopDesc::size_given_klass(Klass*)+0x1
 * ***REMOVED***
 * ***REMOVED*** Failed to write core dump. Core dumps have been disabled. To enable core dumping, try "ulimit -c unlimited" before starting Java again
 * ***REMOVED***
 * ***REMOVED*** If you would like to submit a bug report, please visit:
 * ***REMOVED***   http://bugreport.java.com/bugreport/crash.jsp
 * ***REMOVED***
 * </pre>
 * 
 * <pre>
 * ***REMOVED***
 * ***REMOVED*** There is insufficient memory for the Java Runtime Environment to continue.
 * ***REMOVED*** Native memory allocation (malloc) failed to allocate 32744 bytes for ChunkPool::allocate
 * ***REMOVED*** Possible reasons:
 * ***REMOVED***   The system is out of physical RAM or swap space
 * ***REMOVED***   The process is running with CompressedOops enabled, and the Java Heap may be blocking the growth of the native heap
 * ***REMOVED*** Possible solutions:
 * ***REMOVED***   Reduce memory load on the system
 * ***REMOVED***   Increase physical memory or swap space
 * ***REMOVED***   Check if swap backing store is full
 * ***REMOVED***   Decrease Java heap size (-Xmx/-Xms)
 * ***REMOVED***   Decrease number of Java threads
 * ***REMOVED***   Decrease Java thread stack sizes (-Xss)
 * ***REMOVED***   Set larger code cache with -XX:ReservedCodeCacheSize=
 * ***REMOVED*** This output file may be truncated or incomplete.
 * ***REMOVED***
 * ***REMOVED***  Out of Memory Error (allocation.cpp:272), pid=273, tid=0x00000000000017c2
 * ***REMOVED***
 * ***REMOVED*** JRE version: Java(TM) SE Runtime Environment (8.0_251-b08) (build 1.8.0_251-b08)
 * ***REMOVED*** Java VM: Java HotSpot(TM) 64-Bit Server VM (25.251-b08 mixed mode solaris-sparc compressed oops)
 * ***REMOVED*** Core dump written. Default location: /apps/opt/jboss-eap/v7.3-ejb3/bin/core or core.273
 * ***REMOVED***
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeaderEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^***REMOVED***(.*)?$";

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
    public HeaderEvent(String logEntry) {
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

    public boolean isSigSegv() {
        return logEntry.matches("^***REMOVED***  SIGSEGV.+$");
    ***REMOVED***

    public boolean isProblematicFrame() {
        return logEntry.matches("^***REMOVED*** (C|V)  (\\[.+\\]|0x\\d{16***REMOVED***)(.+)?$");
    ***REMOVED***

    public boolean isInternalError() {
        return logEntry.matches("^***REMOVED***  Internal Error.+$");
    ***REMOVED***

    public boolean isError() {
        return logEntry.matches("^***REMOVED***  Error:.+$");
    ***REMOVED***

    public boolean isJavaVm() {
        return logEntry.matches("^***REMOVED*** Java VM:.+$");
    ***REMOVED***

    public boolean isJreVersion() {
        return logEntry.matches("^***REMOVED*** JRE version:.+$");
    ***REMOVED***
***REMOVED***

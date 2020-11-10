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

import org.github.errcat.domain.BlankLineEvent;
import org.github.errcat.domain.LogEvent;
import org.github.errcat.domain.UnknownEvent;
import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.OsEvent;
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
     * Defined logging events.
     */
    public enum LogEventType {
        //
        BLANK_LINE, HEADER, JVM_INFO, OS, STACK, UNAME, UNKNOWN
    ***REMOVED***;

    /**
     * Defined JDK vendors.
     */
    public enum JdkVendor {
        //
        OpenJDK, Oracle, UNKNOWN
    ***REMOVED***;

    /**
     * Defined JDK major versions.
     */
    public enum JdkVersionMajor {
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
***REMOVED***

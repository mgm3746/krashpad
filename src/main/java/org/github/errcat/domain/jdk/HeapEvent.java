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

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * HEAP
 * </p>
 * 
 * <p>
 * Heap information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <p>
 * 1) At time of crash:
 * </p>
 * 
 * <pre>
 * ***REMOVED***
 *  PSYoungGen      total 244736K, used 103751K [0x00000000eab00000, 0x0000000100000000, 0x0000000100000000)
 *   eden space 141312K, 24% used [0x00000000eab00000,0x00000000ecc7aef8,0x00000000f3500000)
 *   from space 103424K, 67% used [0x00000000f9b00000,0x00000000fded6e68,0x0000000100000000)
 *   to   space 103936K, 0% used [0x00000000f3500000,0x00000000f3500000,0x00000000f9a80000)
 *  ParOldGen       total 699392K, used 91187K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
 *   object space 699392K, 13% used [0x00000000c0000000,0x00000000c590cc08,0x00000000eab00000)
 *  Metaspace       used 139716K, capacity 155778K, committed 155992K, reserved 1183744K
 *   class space    used 16686K, capacity 21027K, committed 21080K, reserved 1048576K
 * </pre>
 * 
 * <p>
 * 2) GC Heap History:
 * </p>
 * 
 * <pre>
 * GC Heap History (48 events):
 * Event: 1.905 GC heap before
 * {Heap before GC invocations=1 (full 0):
 *  PSYoungGen      total 153088K, used 116252K [0x00000000eab00000, 0x00000000f5580000, 0x0000000100000000)
 *   eden space 131584K, 88% used [0x00000000eab00000,0x00000000f1c87328,0x00000000f2b80000)
 *   from space 21504K, 0% used [0x00000000f4080000,0x00000000f4080000,0x00000000f5580000)
 *   to   space 21504K, 0% used [0x00000000f2b80000,0x00000000f2b80000,0x00000000f4080000)
 *  ParOldGen       total 349696K, used 0K [0x00000000c0000000, 0x00000000d5580000, 0x00000000eab00000)
 *   object space 349696K, 0% used [0x00000000c0000000,0x00000000c0000000,0x00000000d5580000)
 *  Metaspace       used 19510K, capacity 21116K, committed 21248K, reserved 1069056K
 *   class space    used 1971K, capacity 2479K, committed 2560K, reserved 1048576K
 * Event: 1.941 GC heap after
 * Heap after GC invocations=1 (full 0):
 *  PSYoungGen      total 153088K, used 21493K [0x00000000eab00000, 0x00000000fd600000, 0x0000000100000000)
 *   eden space 131584K, 0% used [0x00000000eab00000,0x00000000eab00000,0x00000000f2b80000)
 *   from space 21504K, 99% used [0x00000000f2b80000,0x00000000f407d4c0,0x00000000f4080000)
 *   to   space 21504K, 0% used [0x00000000fc100000,0x00000000fc100000,0x00000000fd600000)
 *  ParOldGen       total 349696K, used 6340K [0x00000000c0000000, 0x00000000d5580000, 0x00000000eab00000)
 *   object space 349696K, 1% used [0x00000000c0000000,0x00000000c06311f0,0x00000000d5580000)
 *  Metaspace       used 19510K, capacity 21116K, committed 21248K, reserved 1069056K
 *   class space    used 1971K, capacity 2479K, committed 2560K, reserved 1048576K
 * ***REMOVED***
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeapEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(  class|Collection set:| concurrent mark-sweep|  eden|  from|"
            + " garbage-first heap|GC Heap History|Event: " + JdkRegEx.TIMESTAMP
            + " GC heap (after|before)|\\{Heap before GC|***REMOVED***|Heap after GC| Metaspace|  object| par new | ParOldGen|"
            + " PSYoungGen|  region size| \\d{1,5***REMOVED*** x " + JdkRegEx.SIZE
            + " regions|Reserved region:|Shenandoah Heap|Status:|  to| " + JdkRegEx.SIZE + " total|***REMOVED***| - )(.*)$";

    /**
     * Regular expression for the heap at crash header.
     */
    public static final String REGEX_HEAP_AT_CRASH_HEADER = "^***REMOVED***$";

    /**
     * Regular expression for a heap history header.
     */
    public static final String REGEX_HEAP_HISTORY_HEADER = "^(GC Heap History|Event:).+$";

    /**
     * Regular expression for a young generation event.
     */
    public static final String REGEX_YOUNG_GEN = "^ (par new generation|PSYoungGen)[ ]{1,6***REMOVED***total " + JdkRegEx.SIZE
            + ", used " + JdkRegEx.SIZE + ".+$";

    /**
     * Regular expression for a old generation event.
     */
    public static final String REGEX_OLD_GEN = "^ (concurrent mark-sweep generation|PSOldGen|ParOldGen)[ ]{1,7***REMOVED***total "
            + JdkRegEx.SIZE + ", used " + JdkRegEx.SIZE + ".+$";

    /**
     * Regular expression for Shenandoah combined event.
     */
    public static final String REGEX_SHENANDOAH = "^ " + JdkRegEx.SIZE + " total, " + JdkRegEx.SIZE + " committed, "
            + JdkRegEx.SIZE + " used$";

    /**
     * Regular expression for G1 combined event.
     */
    public static final String REGEX_G1 = "^ garbage-first heap   total " + JdkRegEx.SIZE + ", used " + JdkRegEx.SIZE
            + ".+$";

    /**
     * Regular expression for a metaspace event.
     */
    public static final String REGEX_METASPACE = "^ Metaspace[ ]{1,7***REMOVED***used " + JdkRegEx.SIZE + ", capacity "
            + JdkRegEx.SIZE + ", committed " + JdkRegEx.SIZE + ", reserved " + JdkRegEx.SIZE + "$";

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
    public HeapEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.HEAP.toString();
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
     * @return true if the log line contains young generation heap information, false otherwise.
     */
    public boolean isYoungGen() {
        return logEntry.matches(REGEX_YOUNG_GEN);
    ***REMOVED***

    /**
     * @return true if the log line contains old generation heap information, false otherwise.
     */
    public boolean isOldGen() {
        return logEntry.matches(REGEX_OLD_GEN);
    ***REMOVED***

    /**
     * @return true if the log line contains Shenandoah heap information, false otherwise.
     */
    public boolean isShenandoah() {
        return logEntry.matches(REGEX_SHENANDOAH);
    ***REMOVED***

    /**
     * @return true if the log line contains G1 heap information, false otherwise.
     */
    public boolean isG1() {
        return logEntry.matches(REGEX_G1);
    ***REMOVED***

    /**
     * @return true if the log line contains metaspace information, false otherwise.
     */
    public boolean isMetaspace() {
        return logEntry.matches(REGEX_METASPACE);
    ***REMOVED***
***REMOVED***

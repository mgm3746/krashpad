/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * HEAP
 * </p>
 * 
 * <p>
 * Heap and metaspace information at time of crash.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <p>
 * 1) PARALLEL_SCAVENGE + PARALLEL_OLD:
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
 * 2) PAR_NEW + SERIAL_OLD:
 * </p>
 *
 * <pre>
 * ***REMOVED***
 *  par new generation   total 947392K, used 396580K [0x00000006f9c00000, 0x000000073bd50000, 0x000000073bd50000)
 *   eden space 812096K,  46% used [0x00000006f9c00000, 0x0000000710f89f10, 0x000000072b510000)
 *   from space 135296K,  11% used [0x000000072b510000, 0x000000072c4cf2e0, 0x0000000733930000)
 *   to   space 135296K,   0% used [0x0000000733930000, 0x0000000733930000, 0x000000073bd50000)
 *  tenured generation   total 2165440K, used 937560K [0x000000073bd50000, 0x00000007c0000000, 0x00000007c0000000)
 *    the space 2165440K,  43% used [0x000000073bd50000, 0x00000007750e6118, 0x00000007750e6200, 0x00000007c0000000)
 *  Metaspace       used 243180K, capacity 261654K, committed 262244K, reserved 1275904K
 *   class space    used 31112K, capacity 35784K, committed 35940K, reserved 1048576K
 * </pre>
 * 
 * <p>
 * 3) G1:
 * </p>
 *
 * <pre>
 * ***REMOVED***
 *  garbage-first heap   total 33554432K, used 22395212K [0x00007f56fc000000, 0x00007f5efc000000)
 *   region size 16384K, 182 young (2981888K), 19 survivors (311296K)
 *  Metaspace       used 156484K, capacity 157356K, committed 160440K, reserved 161792K
 * </pre>
 *
 * <p>
 * 4) SHENANDOAH:
 * </p>
 * 
 * <pre>
 * ***REMOVED***
 * Shenandoah Heap
 *  5734M total, 5734M committed, 3099M used
 *  2867 x 2048K regions
 * Status: marking, not cancelled
 * Reserved region:
 *  - [0x000000067a200000, 0x00000007e0800000)
 * Collection set:
 *  - map (vanilla): 0x00007f2e5435b3d1
 *  - map (biased):  0x00007f2e54358000
 * 
 *  Metaspace       used 264944K, capacity 285249K, committed 306048K, reserved 1314816K
 *   class space    used 32477K, capacity 37071K, committed 40576K, reserved 1048576K
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class HeapEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + HeapEvent.REGEX_HEADER + "|" + JdkRegEx.YOUNG_GEN + "|"
            + JdkRegEx.OLD_GEN + "|" + JdkRegEx.SHENANDOAH + "|" + JdkRegEx.G1 + "|" + JdkRegEx.METASPACE + ")$";

    /**
     * Regular expression for the header.
     */
    public static final String REGEX_HEADER = "***REMOVED***";

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
     * @return true if the log line contains G1 heap information, false otherwise.
     */
    public boolean isG1() {
        return logEntry.matches(JdkRegEx.G1_SIZE);
    ***REMOVED***

    /**
     * @return true if the log line contains metaspace information, false otherwise.
     */
    public boolean isMetaspace() {
        return logEntry.matches(JdkRegEx.METASPACE_SIZE);
    ***REMOVED***

    /**
     * @return true if the log line contains old generation heap information, false otherwise.
     */
    public boolean isOldGen() {
        return logEntry.matches(JdkRegEx.OLD_GEN_SIZE);
    ***REMOVED***

    /**
     * @return true if the log line contains Shenandoah heap information, false otherwise.
     */
    public boolean isShenandoah() {
        return logEntry.matches(JdkRegEx.SHENANDOAH_SIZE);
    ***REMOVED***

    /**
     * @return true if the log line contains young generation heap information, false otherwise.
     */
    public boolean isYoungGen() {
        return logEntry.matches(JdkRegEx.YOUNG_GEN_SIZE);
    ***REMOVED***
***REMOVED***

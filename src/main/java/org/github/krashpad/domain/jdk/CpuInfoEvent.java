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

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * CPU_INFO
 * </p>
 * 
 * <p>
 * CPU information
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand aes vpmsumb mfdscr vsx sha
 * </pre>
 * 
 * <pre>
 * ***REMOVED***
 * processor       : 0
 * cpu             : POWER9 (architected), altivec supported
 * clock           : 2500.000000MHz
 * revision        : 2.2 (pvr 004e 0202)
 * </pre>
 * 
 * <pre>
 * timebase : 512000000
 * platform    : pSeries
 * model       : IBM,9008-22L
 * machine     : CHRP IBM,9008-22L
 * MMU     : Hash
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CpuInfoEvent implements LogEvent {

    /**
     * Regular expression for the CPU header with summary information.
     * 
     * For example:
     * 
     * <pre>
     * CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand aes vpmsumb mfdscr 
     * vsx sha
     * </pre>
     * 
     * <pre>
     * CPU:total 160 (initial active 160)
     * </pre>
     * 
     * <pre>
     * CPU:total 8 (2 cores per cpu, 1 threads per core) family 6 model 63 stepping 0, cmov, cx8, fxsr, mmx, sse, 
     * sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, erms, tsc, tscinvbit
     * </pre>
     */
    public static final String _REGEX_HEADER = "CPU:[ ]{0,1***REMOVED***total (\\d{1,3***REMOVED***)( \\(initial active (\\d{1,3***REMOVED***)\\))?"
            + "( \\((\\d{1,2***REMOVED***) cores per cpu, (\\d) threads per core\\))?.*";

    /**
     * Regular expression for values for multi line entries.
     * 
     * For example:
     * 
     * <pre>
     * cache type:
     * Data
     * </pre>
     */
    private static final String _REGEX_VALUE = "^(\\d{1,***REMOVED***|\\d{1,***REMOVED***-\\d{1,***REMOVED***|" + JdkRegEx.SIZE
            + "|\\d{1,2***REMOVED***KFrequency|Data|Instruction|Unified|performance( powersave)?)$";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_VALUE
            + "|<Not Available>|address sizes.+|apicid.+|Available cpu frequencies:.*|"
            + "(Available|Current) governor[s]{0,1***REMOVED***:.*|BIOS frequency limitation:.*|bogomips.+|bugs.+|"
            + "cache_alignment.+|cache coherency line size:|cache level:|cache size.+|cache type:|clflush size.+|"
            + "clock.+|core id.+|Core performance/turbo boost:.*|cpu.+|cpu cores|cpu family.+|"
            + "CPU Model and flags from \\/proc\\/cpuinfo:|cpuid level|cpu MHz|(Current|Maximum|"
            + "Minimum) cpu frequency:|flags.+|fpu.+|fpu_exception|Frequency switch latency \\(ns\\):.*|"
            + "initial apicid.+|machine.+|microcode.+|model.+|model name|MMU.+|(Off|On)line cpus:.*|ondemand|"
            + "performance|physical id.+|platform.+|power management:|\\/proc\\/cpuinfo:|processor.+|revision.+|"
            + "siblings.+|stepping.+|timebase.+|TLB size.+|vendor_id.+|wp.+)$";

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
    public CpuInfoEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.CPU_INFO.toString();
    ***REMOVED***

    /**
     * @return True if the event is the CPU header with summary information, false otherwise.
     */
    public boolean isCpuHeader() {
        return logEntry.matches(_REGEX_HEADER);
    ***REMOVED***
***REMOVED***

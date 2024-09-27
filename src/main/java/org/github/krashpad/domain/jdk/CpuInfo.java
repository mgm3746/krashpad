/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

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
 * <p>
 * Linux:
 * </p>
 * 
 * <pre>
 * CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand aes vpmsumb mfdscr vsx sha
 * </pre>
 * 
 * <pre>
 * /proc/cpuinfo:
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
 * <p>
 * Windows:
 * </p>
 * 
 * <pre>
 * CPU: total 8 (initial active 8) (8 cores per cpu, 1 threads per core) family 6 model 158 stepping 13 microcode 0xf0, cx8, cmov, fxsr, mmx, 3dnowpref, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, lzcnt, tsc, tscinvbit, avx, avx2, aes, erms, clmul, bmi1, bmi2, adx, fma, vzeroupper, clflush, clflushopt
 * Processor Information for all 8 processors :
 *  Max Mhz: 3000, Current Mhz: 3000, Mhz Limit: 3000
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class CpuInfo implements LogEvent, HeaderEvent {

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
    public static final String _REGEX_HEADER = "CPU:[ ]{0,1}total (\\d{1,3})( \\(initial active (\\d{1,3})\\))?"
            + "( \\((\\d{1,2}) cores per cpu, (\\d) threads per core\\))?.*";

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
    private static final String _REGEX_VALUE = "^(\\d{1,}|\\d{1,}-\\d{1,}|" + JdkRegEx.SIZE
            + "|\\d{1,2}KFrequency|Data|Instruction|Unified|performance|performance powersave|powersave)$";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_VALUE
            + "|<Not Available>|address sizes.+|apicid.+|Available cpu frequencies:.*|"
            + "(Available|Current) governor[s]{0,1}:.*|BIOS frequency limitation:.*|(bogomips|BogoMIPS).+|bugs.+|"
            + "cache_alignment.+|cache coherency line size:|cache level:|cache size.+|cache type:|"
            + "citextache_alignment.+|clflush size.+|clock.+|core id.+|Core performance/turbo boost:.*|(cpu|CPU).+|"
            + "cpu cores|cpu family.+|CPU Model and flags from \\/proc\\/cpuinfo:|cpuid level|cpu MHz|(Current|"
            + "Maximum|Minimum) cpu frequency:|Features.+|flags.+|fpu.+|fpu_exception|"
            + "Frequency switch latency \\(ns\\):.*|initial apicid.+|machine.+|  Max Mhz.+|microcode.+|model.+|"
            + "model name|MMU.+|(Off|On)line cpus:.*|ondemand|performance|physical id.+|platform.+|power management:|"
            + "\\/proc\\/cpuinfo:|[pP]rocessor.+|revision.+|siblings.+|stepping.+|timebase.+|TLB size.+|vendor_id.+|"
            + "wp.+)$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
    }

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
    public CpuInfo(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.CPU_INFO;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return True if the event is the CPU header with summary information, false otherwise.
     */
    public boolean isCpuHeader() {
        return logEntry.matches(_REGEX_HEADER);
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }
}

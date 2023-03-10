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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestCpuInfo {

    @Test
    void testAddressSizes() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "address sizes   : 39 bits physical, 48 bits virtual";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testApicid() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "apicid          : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testAvailableCpuFrequencies() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Available cpu frequencies: <Not Available>";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testAvailableCpuFrequenciesHeader() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Available cpu frequencies:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testAvailableGovernors() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Available governors:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBiosFrequencyLimitation() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "BIOS frequency limitation: <Not Available>";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBiosFrequencyLimitationHeader() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "BIOS frequency limitation:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBogomips() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "bogomips        : 5808.00";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBugs() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "bugs            : cpu_meltdown spectre_v1 spectre_v2";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheAlignment() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cache_alignment : 64";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheCoherencyLineSize() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cache coherency line size:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheLevel() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cache level:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheSize() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cache size      : 4096 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheSizeSlitFirstLine() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cache size:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheType() {
        String logLine = "cache type:";
        LogEvent priorEvent = new CpuInfo("");
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClflushSize() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "clflush size    : 64";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClock() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "clock           : 2500.000000MHz";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCoreId() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "core id         : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCorePerformanceTurboBoost() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Core performance/turbo boost: <Not Available>";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCorePerformanceTurboBoostHeader() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Core performance/turbo boost:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpu() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cpu             : POWER9 (architected), altivec supported";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuCores() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cpu cores       : 2";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuFamily() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cpu family      : 6";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuHeaderWithCoresWithThreads() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU:total 8 (initial active 8) (1 cores per cpu, 1 threads per core) family 6 model 94 "
                + "stepping 3, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx, fma";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfo event = new CpuInfo(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuHeaderWithoutCoresWithoutThreads() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfo event = new CpuInfo(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuHeaderWithoutInitialActiveWithCoresWithThreads() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU:total 8 (2 cores per cpu, 1 threads per core) family 6 model 63 stepping 0, cmov, cx8, "
                + "fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, erms, tsc, tscinvbit";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfo event = new CpuInfo(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuIdentifier() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "10-65";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuidLevel() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cpuid level     : 22";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuInfo() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuJdk17() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU: total 12 (initial active 12) (6 cores per cpu, 2 threads per core) family 6 model 165 "
                + "stepping 2 microcode 0xea, cx8, cmov, fxsr, ht, mmx, 3dnowpref, sse, sse2, sse3, ssse3, sse4.1, "
                + "sse4.2, popcnt, lzcnt, tsc, tscinvbit, avx, avx2, aes, erms, clmul, bmi1, bmi2, adx, fma, "
                + "vzeroupper, clflush, clflushopt";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfo event = new CpuInfo(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuMhz() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "cpu MHz         : 3630.932";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuModel() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "model           : 142";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuModelAndFlagsFrom() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU Model and flags from ***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuModelName() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "model name      : Intel(R) Core(TM) i7-7600U CPU @ 2.80GHz";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernor() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Current governor: performance";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernorHeader() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Current governor:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernorOnDemand() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "ondemand";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernorPerformance() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "performance";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentThread() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset20.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertEquals(
                "JavaThread \"ajp-/hostname:8109-16\" daemon [_thread_in_native, id=112672, "
                        + "stack(0x00007f11e11a2000,0x00007f11e12a3000)]",
                fel.getCurrentThreadName(), "Current thread not correct.");
    ***REMOVED***

    @Test
    void testData() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Data";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testExtensiveErrorReportsMultiLine() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset53.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.WARN_UNIDENTIFIED_LOG_LINE.getKey()),
                Analysis.WARN_UNIDENTIFIED_LOG_LINE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testFlags() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "flags           : fpu vme de pse tsc msr ";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testfpu() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "fpu             : yes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFpuException() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "fpu_exception   : yes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFrequencySwitchLatency() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Frequency switch latency (ns): 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFrequencySwitchLatencyHeader() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Frequency switch latency (ns):";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testInitialApicid() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "initial apicid  : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMachine() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "machine     : CHRP IBM,9008-22L";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMaximumCputFrequency() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Maximum cpu frequency:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMicrocode() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "microcode       : 0xd6";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMmu() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "MMU     : Hash";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testModel() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "model       : IBM,9008-22L";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testModelName() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "model name  : Intel(R) Core(TM) i7-10850H CPU @ 2.70GHz";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNotAvailable() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "<Not Available>";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNumber7() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "4899871";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOfflineCpus() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Offline cpus:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOnlineCpus() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Online cpus: 0-11";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOnlineCpusHeader() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "Online cpus:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        assertTrue(JdkUtil.parseLogLine(logLine, priorEvent) instanceof CpuInfo,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPerformancePorwersave() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "performance powersave";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPhysicalId() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "physical id     : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPlatform() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "platform    : pSeries";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPowerManagement() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "power management:";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testProcessor() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "processor       : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRevision() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "revision        : 2.2 (pvr 004e 0202)";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSiblings() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "siblings        : 4";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testStepping() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "stepping        : 9";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTimebase() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "timebase    : 512000000";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTlbSize() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "TLB size        : 2560 4K pages";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testValuePowerSave() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "powersave";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVendorId() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "vendor_id       : GenuineIntel";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testWp() {
        LogEvent priorEvent = new CpuInfo("");
        String logLine = "wp              : yes";
        assertTrue(JdkUtil.identifyEventType(logLine, priorEvent) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

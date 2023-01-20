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

import org.github.krashpad.service.Manager;
import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestCpuInfoEvent {

    @Test
    void testAddressSizes() {
        String logLine = "address sizes   : 39 bits physical, 48 bits virtual";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testApicid() {
        String logLine = "apicid          : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testAvailableCpuFrequencies() {
        String logLine = "Available cpu frequencies:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBiosFrequencyLimitation() {
        String logLine = "BIOS frequency limitation:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBogomips() {
        String logLine = "bogomips        : 5808.00";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBugs() {
        String logLine = "bugs            : cpu_meltdown spectre_v1 spectre_v2";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheAlignment() {
        String logLine = "cache_alignment : 64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheCoherencyLineSize() {
        String logLine = "cache coherency line size:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCacheSize() {
        String logLine = "cache size      : 4096 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCachwLevel() {
        String logLine = "cache level:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCachwType() {
        String logLine = "cache type:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClflushSize() {
        String logLine = "clflush size    : 64";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClock() {
        String logLine = "clock           : 2500.000000MHz";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCoreId() {
        String logLine = "core id         : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCorePerformanceTurboBoost() {
        String logLine = "Core performance/turbo boost:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpu() {
        String logLine = "cpu             : POWER9 (architected), altivec supported";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuCores() {
        String logLine = "cpu cores       : 2";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuFamily() {
        String logLine = "cpu family      : 6";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuHeaderWithCoresWithThreads() {
        String logLine = "CPU:total 8 (initial active 8) (1 cores per cpu, 1 threads per core) family 6 model 94 "
                + "stepping 3, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx, fma";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuHeaderWithoutCoresWithoutThreads() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuHeaderWithoutInitialActiveWithCoresWithThreads() {
        String logLine = "CPU:total 8 (2 cores per cpu, 1 threads per core) family 6 model 63 stepping 0, cmov, cx8, "
                + "fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, erms, tsc, tscinvbit";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuIdentifier() {
        String logLine = "10-65";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuidLevel() {
        String logLine = "cpuid level     : 22";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuInfo() {
        String logLine = "***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuJdk17() {
        String logLine = "CPU: total 12 (initial active 12) (6 cores per cpu, 2 threads per core) family 6 model 165 "
                + "stepping 2 microcode 0xea, cx8, cmov, fxsr, ht, mmx, 3dnowpref, sse, sse2, sse3, ssse3, sse4.1, "
                + "sse4.2, popcnt, lzcnt, tsc, tscinvbit, avx, avx2, aes, erms, clmul, bmi1, bmi2, adx, fma, "
                + "vzeroupper, clflush, clflushopt";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        assertTrue(event.isCpuHeader(), "CPU header not identified.");
    ***REMOVED***

    @Test
    void testCpuMhz() {
        String logLine = "cpu MHz         : 3630.932";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuModel() {
        String logLine = "model           : 142";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuModelAndFlagsFrom() {
        String logLine = "CPU Model and flags from ***REMOVED***";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCpuModelName() {
        String logLine = "model name      : Intel(R) Core(TM) i7-7600U CPU @ 2.80GHz";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernor() {
        String logLine = "Current governor:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernorOnDemand() {
        String logLine = "ondemand";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCurrentGovernorPerformance() {
        String logLine = "performance";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
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
                fel.getCurrentThread(), "Current thread not correct.");
    ***REMOVED***

    @Test
    void testExtensiveErrorReportsMultiLine() {
        File testFile = new File(Constants.TEST_DATA_DIR + "dataset53.txt");
        Manager manager = new Manager();
        FatalErrorLog fel = manager.parse(testFile);
        assertFalse(fel.hasAnalysis(Analysis.WARN_UNIDENTIFIED_LOG_LINE),
                Analysis.WARN_UNIDENTIFIED_LOG_LINE + " analysis incorrectly identified.");
    ***REMOVED***

    @Test
    void testFlags() {
        String logLine = "flags           : fpu vme de pse tsc msr ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testfpu() {
        String logLine = "fpu             : yes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFpuException() {
        String logLine = "fpu_exception   : yes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testFrequencySwitchLatency() {
        String logLine = "Frequency switch latency (ns):";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testInitialApicid() {
        String logLine = "initial apicid  : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMachine() {
        String logLine = "machine     : CHRP IBM,9008-22L";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMicrocode() {
        String logLine = "microcode       : 0xd6";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMmu() {
        String logLine = "MMU     : Hash";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testModel() {
        String logLine = "model       : IBM,9008-22L";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNotAvailable() {
        String logLine = "<Not Available>";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOfflineCpus() {
        String logLine = "Offline cpus:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testOnlineCpus() {
        String logLine = "Online cpus:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CpuInfoEvent,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testPhysicalId() {
        String logLine = "physical id     : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPlatform() {
        String logLine = "platform    : pSeries";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testPowerManagement() {
        String logLine = "power management:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testProcessor() {
        String logLine = "processor       : 0";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testRevision() {
        String logLine = "revision        : 2.2 (pvr 004e 0202)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testSiblings() {
        String logLine = "siblings        : 4";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testStepping() {
        String logLine = "stepping        : 9";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTimebase() {
        String logLine = "timebase    : 512000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testTlbSize() {
        String logLine = "TLB size        : 2560 4K pages";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVendorId() {
        String logLine = "vendor_id       : GenuineIntel";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testWp() {
        String logLine = "wp              : yes";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CPU_INFO,
                JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

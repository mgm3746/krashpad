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

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestCpuInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CpuInfoEvent);
    ***REMOVED***

    public void testCpuHeaderWithoutCoresWithoutThreads() {
        String logLine = "CPU:total 160 (initial active 160) ppc64 fsqrt isel lxarxeh cmpb popcntb popcntw fcfids vand "
                + "aes vpmsumb mfdscr vsx sha";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        Assert.assertTrue("CPU header not identified.", event.isCpuHeader());
    ***REMOVED***

    public void testCpuHeaderWithoutInitialActiveWithCoresWithThreads() {
        String logLine = "CPU:total 8 (2 cores per cpu, 1 threads per core) family 6 model 63 stepping 0, cmov, cx8, "
                + "fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, erms, tsc, tscinvbit";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        Assert.assertTrue("CPU header not identified.", event.isCpuHeader());
    ***REMOVED***

    public void testCpuHeaderWithCoresWithThreads() {
        String logLine = "CPU:total 8 (initial active 8) (1 cores per cpu, 1 threads per core) family 6 model 94 "
                + "stepping 3, cmov, cx8, fxsr, mmx, sse, sse2, sse3, ssse3, sse4.1, sse4.2, popcnt, avx, avx2, aes, "
                + "clmul, erms, rtm, 3dnowpref, lzcnt, tsc, bmi1, bmi2, adx, fma";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
        CpuInfoEvent event = new CpuInfoEvent(logLine);
        Assert.assertTrue("CPU header not identified.", event.isCpuHeader());
    ***REMOVED***

    public void testCpuInfo() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testProcessor() {
        String logLine = "processor       : 0";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpu() {
        String logLine = "cpu             : POWER9 (architected), altivec supported";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testClock() {
        String logLine = "clock           : 2500.000000MHz";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testTimebase() {
        String logLine = "timebase    : 512000000";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testPlatform() {
        String logLine = "platform    : pSeries";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testModel() {
        String logLine = "model       : IBM,9008-22L";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testMachine() {
        String logLine = "machine     : CHRP IBM,9008-22L";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testRevision() {
        String logLine = "revision        : 2.2 (pvr 004e 0202)";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testMmu() {
        String logLine = "MMU     : Hash";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testVendorId() {
        String logLine = "vendor_id       : GenuineIntel";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuFamily() {
        String logLine = "cpu family      : 6";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuModel() {
        String logLine = "model           : 142";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuModelAndFlagsFrom() {
        String logLine = "CPU Model and flags from ***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuModelName() {
        String logLine = "model name      : Intel(R) Core(TM) i7-7600U CPU @ 2.80GHz";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testStepping() {
        String logLine = "stepping        : 9";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testMicrocode() {
        String logLine = "microcode       : 0xd6";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuMhz() {
        String logLine = "cpu MHz         : 3630.932";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCacheSize() {
        String logLine = "cache size      : 4096 KB";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testPhysicalId() {
        String logLine = "physical id     : 0";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testSiblings() {
        String logLine = "siblings        : 4";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCoreId() {
        String logLine = "core id         : 0";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuCores() {
        String logLine = "cpu cores       : 2";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testApicid() {
        String logLine = "apicid          : 0";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testInitialApicid() {
        String logLine = "initial apicid  : 0";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testfpu() {
        String logLine = "fpu             : yes";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testFpuException() {
        String logLine = "fpu_exception   : yes";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCpuidLevel() {
        String logLine = "cpuid level     : 22";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testWp() {
        String logLine = "wp              : yes";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testFlags() {
        String logLine = "flags           : fpu vme de pse tsc msr ";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testBugs() {
        String logLine = "bugs            : cpu_meltdown spectre_v1 spectre_v2";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testBogomips() {
        String logLine = "bogomips        : 5808.00";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testClflushSize() {
        String logLine = "clflush size    : 64";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testCacheAlignment() {
        String logLine = "cache_alignment : 64";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testAddressSizes() {
        String logLine = "address sizes   : 39 bits physical, 48 bits virtual";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***

    public void testPowerManagement() {
        String logLine = "power management:";
        Assert.assertTrue(JdkUtil.LogEventType.CPU_INFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CPU_INFO);
    ***REMOVED***
***REMOVED***

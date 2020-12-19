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

import static org.github.errcat.util.jdk.JdkUtil.LogEventType.COMPILATION;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.domain.BlankLineEvent;
import org.github.errcat.domain.LogEvent;
import org.github.errcat.domain.UnknownEvent;
import org.github.errcat.domain.jdk.BitsEvent;
import org.github.errcat.domain.jdk.CardTableEvent;
import org.github.errcat.domain.jdk.ClassesRedefinedEvent;
import org.github.errcat.domain.jdk.CodeCacheEvent;
import org.github.errcat.domain.jdk.CompilationEvent;
import org.github.errcat.domain.jdk.CpuInfoEvent;
import org.github.errcat.domain.jdk.CurrentThreadEvent;
import org.github.errcat.domain.jdk.DeoptimizationEvent;
import org.github.errcat.domain.jdk.DynamicLibraryEvent;
import org.github.errcat.domain.jdk.ElapsedTimeEvent;
import org.github.errcat.domain.jdk.EnvironmentVariablesEvent;
import org.github.errcat.domain.jdk.ExceptionCountsEvent;
import org.github.errcat.domain.jdk.ExceptionEvent;
import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.HeadingEvent;
import org.github.errcat.domain.jdk.HeapAddressEvent;
import org.github.errcat.domain.jdk.HeapEvent;
import org.github.errcat.domain.jdk.HeapRegionsEvent;
import org.github.errcat.domain.jdk.InstructionsEvent;
import org.github.errcat.domain.jdk.LibcEvent;
import org.github.errcat.domain.jdk.LoadAverageEvent;
import org.github.errcat.domain.jdk.MeminfoEvent;
import org.github.errcat.domain.jdk.MemoryEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.domain.jdk.PollingPageEvent;
import org.github.errcat.domain.jdk.RegisterEvent;
import org.github.errcat.domain.jdk.RegisterToMemoryMappingEvent;
import org.github.errcat.domain.jdk.Release;
import org.github.errcat.domain.jdk.RlimitEvent;
import org.github.errcat.domain.jdk.SigInfoEvent;
import org.github.errcat.domain.jdk.SignalHandlersEvent;
import org.github.errcat.domain.jdk.StackEvent;
import org.github.errcat.domain.jdk.ThreadEvent;
import org.github.errcat.domain.jdk.TimeEvent;
import org.github.errcat.domain.jdk.TimezoneEvent;
import org.github.errcat.domain.jdk.TopOfStackEvent;
import org.github.errcat.domain.jdk.UnameEvent;
import org.github.errcat.domain.jdk.VmArgumentsEvent;
import org.github.errcat.domain.jdk.VmEvent;
import org.github.errcat.domain.jdk.VmInfoEvent;
import org.github.errcat.domain.jdk.VmMutexEvent;
import org.github.errcat.domain.jdk.VmStateEvent;
import org.github.errcat.util.Constants;
import org.github.errcat.util.Constants.OsVersion;

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
     * Defined Java applications.
     */
    public enum Application {
        //
        JBOSS, TOMCAT, UNKNOWN
    ***REMOVED***

    /**
     * Defined JDK architectures.
     */
    public enum Arch {
        PPC64, PPC64LE, SPARC, UNKNOWN, X86_64
    ***REMOVED***

    /**
     * Defined JDK builders
     */
    public enum BuiltBy {
        //
        BUILD, EMPTY, JAVA_RE, JENKINS, MOCKBUILD, UNKNOWN, ZULU_RE
    ***REMOVED***

    /**
     * Signal numbers.
     * 
     * SIGBUS: Invalid memory address.
     * 
     * SIGILL: Illegal instruction at the processor.
     * 
     * SIGSEGV: Segmentation fault. Accessing valid memory in an invalid way. For example: (1) Attempting to write to
     * read-only memory. (2) Attempting to write to protected (e.g. OS) memory. (3) Attempting to access an array at an
     * index greater than the array size (out of bounds).
     */
    public enum SignalNumber {
        //
        SIGBUS, SIGILL, SIGSEGV, UNKNOWN
    ***REMOVED***

    /**
     * Signal codes.
     * 
     * BUS_ADRALN: The memory address that has an invalid address alignment for the CPU.
     * 
     * BUS_ADRERR: The memory address does not exist. Rare on Linux but can happen when an mmap'ed file is truncated
     * (e.g. a threading issue where 2 threads access a file at the same time).
     * 
     * BUS_OBJERR: Hardware issue.
     * 
     * SEGV_ACCERR: The access is not allowed. For example: (1) Attempting to write to read-only memory. (2) Attempting
     * to write to protected (OS) memory. (3) Attempting to access an array at an index greater than the array size (out
     * of bounds).
     * 
     * SEGV_MAPERR: The memory address is not mapped to an object.
     */
    public enum SignalCode {
        //
        BUS_ADRALN, BUS_ADRERR, BUS_OBJERR, SEGV_MAPERR, SEGV_ACCERR, UNKNOWN
    ***REMOVED***

    /**
     * Defined Java specifications.
     */
    public enum JavaSpecification {
        JDK10, JDK11, JDK12, JDK13, JDK14, JDK15, //
        JDK6, JDK7, JDK8, JDK9, UNKNOWN
    ***REMOVED***

    /**
     * Defined Java vendors.
     */
    public enum JavaVendor {
        ADOPTOPENJDK, AZUL, ORACLE, RED_HAT, UNKNOWN
    ***REMOVED***

    /**
     * Defined logging events.
     */
    public enum LogEventType {
        //
        BITS, BLANK_LINE, CARD_TABLE, CLASSES_REDEFINED, CODE_CACHE, COMPILATION, CPU, CPU_INFO, CURRENT_THREAD,
        //
        DEOPTIMIZATION_EVENT, DYNAMIC_LIBRARY, ELAPSED_TIME, ENVIRONMENT_VARIABLES, EXCEPTION_COUNTS, EXCEPTION_EVENT,
        //
        HEADER, HEADING, HEAP, HEAP_ADDRESS, HEAP_REGIONS, INSTRUCTIONS, LIBC, LOAD_AVERAGE, MEMINFO, MEMORY, OS,
        //
        POLLING_PAGE, REGISTER, REGISTER_TO_MEMORY_MAPPING, RLIMIT, SIGINFO, SIGNAL_HANDLERS, STACK, THREAD, TIME,
        //
        TIMEZONE, TOP_OF_STACK, UNAME, UNKNOWN, VM_ARGUMENTS, VM_EVENT, VM_INFO, VM_MUTEX, VM_STATE
    ***REMOVED***

    /**
     * OpenJDK8 RHEL6 amd64 rpm release information.
     */
    public static final HashMap<String, Release> rhel6Amd64Jdk8RpmReleases;

    /**
     * OpenJDK11 RHEL7 amd64 rpm release information.
     */
    public static final HashMap<String, Release> rhel7Amd64Jdk11RpmReleases;

    /**
     * OpenJDK8 RHEL7 rpm amd64 release information.
     */
    public static final HashMap<String, Release> rhel7Amd64Jdk8RpmReleases;

    /**
     * OpenJDK8 RHEL7 rpm ppc64le release information.
     */
    public static final HashMap<String, Release> rhel7Ppc64leJdk8RpmReleases;;

    /**
     * OpenJDK11 RHEL8 rpm release information.
     */
    public static final HashMap<String, Release> rhel8Amd64Jdk11RpmReleases;;

    /**
     * OpenJDK8 RHEL8 amd64 rpm release information.
     */
    public static final HashMap<String, Release> rhel8Amd64Jdk8RpmReleases;;

    /**
     * OpenJDK11 RHEL zip release information.
     */
    public static final HashMap<String, Release> rhelJdk11ZipReleases;;

    /**
     * OpenJDK8 RHEL zip release information.
     */
    public static final HashMap<String, Release> rhelJdk8ZipReleases;;

    /**
     * OpenJDK11 Windows release information.
     */
    public static final HashMap<String, Release> windowsJdk11Releases;;

    /**
     * OpenJDK8 Windows release information.
     */
    public static final HashMap<String, Release> windowsJdk8Releases;;

    static {
        /*
         * Notes:
         * 
         * 1) Rpm key is the OpenJDK install directory.
         * 
         * 2) Zip key is build version.
         * 
         * 3) Jan 1 2000 00:00:00 means build date/time unknonw/TBD.
         * 
         * 4) Time 00:00:00 means build date/time is estimate.
         */

        // RHEL6 amd64 OpenJDK8 rpm
        rhel6Amd64Jdk8RpmReleases = new HashMap<String, Release>();
        rhel6Amd64Jdk8RpmReleases.put("LATEST", new Release("Nov 5 2020 00:00:00", 30, "1.8.0_275-b01"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el6_10.x86_64",
                new Release("Nov 5 2020 00:00:00", 30, "1.8.0_275-b01"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el6_10.x86_64",
                new Release("Oct 20 2020 23:38:03", 29, "1.8.0_272-b10"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el6_10.x86_64",
                new Release("Jul 29 2020 03:46:33", 28, "1.8.0_265-b01"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64",
                new Release("Jul 12 2020 19:35:32", 27, "1.8.0_262-b10"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el6_10.x86_64",
                new Release("Apr 14 2020 14:55:11", 26, "1.8.0_252-b09"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.242.b07-1.el6_10.x86_64",
                new Release("Jan 15 2020 00:00:00", 25, "1.8.0_242-b08"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.232.b09-1.el6_10.x86_64",
                new Release("Oct 15 2019 00:00:00", 24, "1.8.0_232-b09"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el6_10.x86_64",
                new Release("Jul 11 2019 00:00:00", 23, "1.8.0_222-b10"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.212.b04-0.el6_10.x86_64",
                new Release("Apr 11 2019 00:00:00", 22, "1.8.0_212-b04"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el6_10.x86_64",
                new Release("Mar 5 2019 00:00:00", 21, "1.8.0_201-b09"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.201.b09-1.el6_10.x86_64",
                new Release("Jan 17 2019 00:00:00", 21, "1.8.0_201-b09"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.191.b12-0.el6_10.x86_64",
                new Release("Oct 9 2018 00:00:00", 20, "1.8.0_191-b12"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el6_10.x86_64",
                new Release("Jul 16 2018 00:00:00", 19, "1.8.0_181-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.171-8.b10.el6_9.x86_64",
                new Release("May 16 2018 00:00:00", 18, "1.8.0_171-b10"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.171-3.b10.el6_9.x86_64",
                new Release("Apr 2 2018 00:00:00", 18, "1.8.0_171-b10"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.161-3.b14.el6_9.x86_64",
                new Release("Jan 10 2018 00:00:00", 17, "1.8.0_161-b14"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el6_9.x86_64",
                new Release("Oct 18 2017 00:00:00", 16, "1.8.0_151-b12"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.144-0.b01.el6_9.x86_64",
                new Release("Aug 21 2017 00:00:00", 15, "1.8.0_144-b01"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.141-3.b16.el6_9.x86_64",
                new Release("Jul 14 2017 00:00:00", 14, "1.8.0_141-b16"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.141-2.b16.el6_9.x86_64",
                new Release("Jul 14 2017 00:00:00", 14, "1.8.0_141-b16"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.131-0.b11.el6_9.x86_64",
                new Release("Apr 13 2017 00:00:00", 13, "1.8.0_131-b11"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.121-1.b13.el6.x86_64",
                new Release("Jan 17 2017 00:00:00", 12, "1.8.0_121-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.121-0.b13.el6_8.x86_64",
                new Release("Jan 17 2017 00:00:00", 12, "1.8.0_121-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.111-1.b15.el6_8.x86_64",
                new Release("Nov 8 2016 00:00:00", 11, "1.8.0_111-b15"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.111-0.b15.el6_8.x86_64",
                new Release("Nov 8 2016 00:00:00", 11, "1.8.0_111-b15"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.101-3.b13.el6_8.x86_64",
                new Release("Jul 11 2016 00:00:00", 10, "1.8.0_101-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.91-3.b14.el6_8.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.91-1.b14.el6.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.91-0.b14.el6_7.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.77-0.b03.el6_7.x86_64",
                new Release("Mar 23 2016 00:00:00", 8, "1.8.0_77-b03"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.71-5.b15.el6.x86_64",
                new Release("Feb 4 2016 00:00:00", 7, "1.8.0_71-b15"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.71-1.b15.el6_7.x86_64",
                new Release("Feb 4 2016 00:00:00", 7, "1.8.0_71-b15"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.65-0.b17.el6_7.x86_64",
                new Release("Oct 15 2015 00:00:00", 6, "1.8.0_65-b17"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.51-3.b16.el6_7.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.51-1.b16.el6_7.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.51-0.b16.el6_6.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.45-35.b13.el6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.45-30.b13.el6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.45-28.b13.el6_6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.31-1.b13.el6_6.x86_64",
                new Release("Apr 10 2015 00:00:00", 3, "1.8.0_31-b13"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.25-3.b17.el6_6.x86_64",
                new Release("Oct 24 2014 00:00:00", 2, "1.8.0_25-b17"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.25-1.b17.el6.x86_64",
                new Release("Oct 24 2014 00:00:00", 2, "1.8.0_25-b17"));
        rhel6Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.20-3.b26.el6.x86_64",
                new Release("Sep 12 2014 00:00:00", 1, "1.8.0_20-b26"));

        // RHEL7 amd64 OpenJDK8 rpm
        rhel7Amd64Jdk8RpmReleases = new HashMap<String, Release>();
        rhel7Amd64Jdk8RpmReleases.put("LATEST", new Release("Nov 6 2020 00:00:00", 27, "1.8.0_275-b01"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.x86_64",
                new Release("Nov 6 2020 00:00:00", 27, "1.8.0_275-b01"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64",
                new Release("Oct 19 2020 21:30:02", 26, "1.8.0_272-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.x86_64",
                new Release("Jul 28 2020 11:07:07", 25, "1.8.0_265-b01"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.262.b10-1.el7.x86_64",
                new Release("Jul 12 2020 00:00:00", 25, "1.8.0_262-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el7_8.x86_64",
                new Release("Jul 12 2020 18:55:08", 25, "1.8.0_262-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64",
                new Release("Apr 14 2020 14:55:11", 24, "1.8.0_252-b09"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.242.b08-1.el7.x86_64",
                new Release("Jan 19 2020 00:00:00", 23, "1.8.0_242-b08"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el7_7.x86_64",
                new Release("Jan 19 2020 00:00:00", 23, "1.8.0_242-b08"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64",
                new Release("Oct 15 2019 00:00:00", 22, "1.8.0_232-b09"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.222.b10-1.el7_7.x86_64",
                new Release("Jul 17 2019 00:00:00", 21, "1.8.0_222-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el7_6.x86_64",
                new Release("Jul 17 2019 00:00:00", 21, "1.8.0_222-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.222.b03-1.el7.x86_64",
                new Release("May 22 2019 13:05:27", 21, "1.8.0_222-ea-b03"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.212.b04-0.el7_6.x86_64",
                new Release("Apr 16 2019 00:00:00", 21, "1.8.0_212-b04"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el7_6.x86_64",
                new Release("Mar 1 2019 00:00:00", 20, "1.8.0_201-b09"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.201.b09-0.el7_6.x86_64",
                new Release("Mar 1 2019 00:00:00", 20, "1.8.0_201-b09"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.191.b12-1.el7_6.x86_64",
                new Release("Nov 19 2018 16:07:16", 19, "1.8.0_191-b12"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.191.b12-0.el7_5.x86_64",
                new Release("Oct 9 2018 00:00:00", 19, "1.8.0_191-b12"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.x86_64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.x86_64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.171-8.b10.el7_5.x86_64",
                new Release("May 16 2018 00:00:00", 17, "1.8.0_171-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.171-7.b10.el7.x86_64",
                new Release("Apr 2 2018 00:00:00", 17, "1.8.0_171-b10"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.161-2.b14.el7_4.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.161-2.b14.el7.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.151-5.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.144-0.b01.el7_4.x86_64",
                new Release("Aug 21 2017 00:00:00", 14, "1.8.0_144-b01"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.141-2.b16.el7_4.x86_64",
                new Release("Jul 14 2017 00:00:00", 13, "1.8.0_141-b16"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.141-1.b16.el7_3.x86_64",
                new Release("Jul 14 2017 00:00:00", 13, "1.8.0_141-b16"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64",
                new Release("Jun 13 2017 11:27:53", 12, "1.8.0_131-b11"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.131-3.b12.el7_3.x86_64",
                new Release("May 9 2017 21:36:32", 12, "1.8.0_131-b11"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.131-2.b11.el7_3.x86_64",
                new Release("Apr 13 2017 00:00:00", 12, "1.8.0_131-b11"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.121-0.b13.el7_3.x86_64",
                new Release("Jan 17 2017 00:00:00", 11, "1.8.0_121-b13"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.111-2.b15.el7_3.x86_64",
                new Release("Nov 8 2016 00:00:00", 10, "1.8.0_111-b15"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.111-1.b15.el7_2.x86_64",
                new Release("Nov 8 2016 00:00:00", 10, "1.8.0_111-b15"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.102-4.b14.el7.x86_64",
                new Release("Sep 14 2016 00:00:00", 9, "1.8.0_102-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.102-1.b14.el7_2.x86_64",
                new Release("Sep 14 2016 00:00:00", 9, "1.8.0_102-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.101-3.b13.el7_2.x86_64",
                new Release("Jul 11 2016 00:00:00", 8, "1.8.0_101-b13"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.91-1.b14.el7_2.x86_64",
                new Release("Jun 21 2016 00:00:00", 7, "1.8.0_91-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.91-0.b14.el7_2.x86_64",
                new Release("Jun 21 2016 00:00:00", 7, "1.8.0_91-b14"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.77-0.b03.el7_2.x86_64",
                new Release("Mar 23 2016 00:00:00", 6, "1.8.0_77-b03"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.71-2.b15.el7_2.x86_64",
                new Release("Feb 4 2016 00:00:00", 5, "1.8.0_71-b15"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.65-3.b17.el7.x86_64",
                new Release("Oct 19 2015 06:27:55", 4, "1.8.0_65-b17"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.65-2.b17.el7_1.x86_64",
                new Release("Oct 15 2015 00:00:00", 4, "1.8.0_65-b17"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.60-2.b27.el7_1.x86_64",
                new Release("Sep 8 2015 00:00:00", 3, "1.8.0_60-b27"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.51-2.b16.el7.x86_64",
                new Release("Sep 4 2015 00:00:00", 2, "1.8.0_51-b16"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.51-1.b16.el7_1.x86_64",
                new Release("Sep 4 2015 00:00:00", 2, "1.8.0_51-b16"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.45-30.b13.el7_1.x86_64",
                new Release("Apr 29 2015 00:00:00", 1, "1.8.0_45-b13"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.31-7.b13.el7_1.x86_64",
                new Release("Apr 10 2015 00:00:00", 1, "1.8.0_31-b13"));
        rhel7Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.31-2.b13.el7.x86_64",
                new Release("Apr 10 2015 00:00:00", 1, "1.8.0_31-b13"));

        // RHEL8 amd64 OpenJDK8 rpm
        rhel8Amd64Jdk8RpmReleases = new HashMap<String, Release>();
        rhel8Amd64Jdk8RpmReleases.put("LATEST", new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-1.el8_3.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_2.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_1.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_0.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-3.el8_3.x86_64",
                new Release("Oct 20 2020 23:38:03", 9, "1.8.0_272-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el8_2.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el8_1.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-4.el8.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_2.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_1.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_0.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64",
                new Release("Jul 12 2020 21:30:56", 7, "1.8.0_262-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_1.x86_64",
                new Release("Jul 12 2020 00:00:00", 7, "1.8.0_262-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_0.x86_64",
                new Release("Jul 12 2020 00:00:00", 7, "1.8.0_262-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.252.b09-3.el8_2.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el8_1.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el8_0.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.242.b08-4.el8.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el8_1.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el8_0.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.232.b09-2.el8_1.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "1.8.0_232-b09"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.232.b09-0.el8_0.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "1.8.0_232-b09"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.222.b10-1.el8.x86_64",
                new Release("Jul 11 2019 00:00:00", 3, "1.8.0_222-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el8_0.x86_64",
                new Release("Jul 11 2019 00:00:00", 3, "1.8.0_222-b10"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.212.b04-1.el8_0.x86_64",
                new Release("May 2 2019 00:00:00", 2, "1.8.0_212-b04"));
        rhel8Amd64Jdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el8.x86_64",
                new Release("Feb 7 2019 00:00:00", 1, "1.8.0_201-b09"));

        // RHEL amd64 OpenJDK8 zip
        rhelJdk8ZipReleases = new HashMap<String, Release>();
        // First RHEL zip was 1.8.0_222.
        rhelJdk8ZipReleases.put("LATEST", new Release("Nov 11 2020 12:18:48", 8, "1.8.0_275-b01"));
        rhelJdk8ZipReleases.put("1.8.0_275-b01", new Release("Nov 11 2020 12:18:48", 8, "1.8.0_275-b01"));
        rhelJdk8ZipReleases.put("1.8.0_272-b10", new Release("Oct 20 2020 12:17:01", 7, "1.8.0_272-b10"));
        rhelJdk8ZipReleases.put("1.8.0_265-b01", new Release("Jul 28 2020 13:27:15", 6, "1.8.0_265-b01"));
        rhelJdk8ZipReleases.put("1.8.0_262-b10", new Release("Jul 14 2020 11:46:22", 5, "1.8.0_262-b10"));
        rhelJdk8ZipReleases.put("1.8.0_252-b09", new Release("Apr 13 2020 13:01:26", 4, "1.8.0_252-b09"));
        rhelJdk8ZipReleases.put("1.8.0_242-b08", new Release("Jan 17 2020 09:36:23", 3, "1.8.0_242-b08"));
        rhelJdk8ZipReleases.put("1.8.0_232-b09", new Release("Oct 15 2019 05:49:57", 2, "1.8.0_232-b09"));
        rhelJdk8ZipReleases.put("1.8.0_222-b10", new Release("Aug 2 2019 08:16:48", 1, "1.8.0_222-b10"));

        // Windows amd64 OpenJDK8 zip
        windowsJdk8Releases = new HashMap<String, Release>();
        // First RH Windows release was 1.8.0_191-1-redhat-b12. There was no RH Windows release for u202.
        windowsJdk8Releases.put("LATEST", new Release("Nov 6 2020 21:03:52", 12, "1.8.0_275-b01"));
        windowsJdk8Releases.put("1.8.0_275-b01", new Release("Nov 6 2020 21:03:52", 12, "1.8.0_275-b01"));
        windowsJdk8Releases.put("1.8.0_272-b10", new Release("Oct 17 2020 18:45:37", 11, "1.8.0_272-b10"));
        windowsJdk8Releases.put("1.8.0_265-b01", new Release("Jul 31 2020 12:33:19", 10, "1.8.0_265-b01"));
        windowsJdk8Releases.put("1.8.0_262-b10", new Release("Jul 14 2020 12:47:22", 9, "1.8.0_262-b10"));
        windowsJdk8Releases.put("1.8.0_252-b09", new Release("Apr 9 2020 22:56:04", 8, "1.8.0_252-b09"));
        windowsJdk8Releases.put("1.8.0_242-b08", new Release("Jan 18 2020 12:08:54", 7, "1.8.0_242-b08"));
        windowsJdk8Releases.put("1.8.0_242-b07", new Release("Jan 13 2020 12:14:39", 6, "1.8.0_242-b07"));
        windowsJdk8Releases.put("1.8.0_232-b09", new Release("Oct 12 2019 23:47:20", 5, "1.8.0_232-b09"));
        windowsJdk8Releases.put("1.8.0_222-1-redhat-b10",
                new Release("Jul 12 2019 11:59:03", 4, "1.8.0_222-1-redhat-b10"));
        windowsJdk8Releases.put("1.8.0_212-2-redhat-b04",
                new Release("Apr 15 2019 20:39:59", 3, "1.8.0_212-2-redhat-b04"));
        windowsJdk8Releases.put("1.8.0_201-1-redhat-b09",
                new Release("Jan 18 2019 11:40:39", 2, "1.8.0_201-1-redhat-b09"));
        windowsJdk8Releases.put("1.8.0_191-1-redhat-b12",
                new Release("Oct 22 2018 23:16:39", 1, "1.8.0_191-1-redhat-b12"));

        // RHEL7 amd64 OpenJDK11 rpm
        rhel7Amd64Jdk11RpmReleases = new HashMap<String, Release>();
        rhel7Amd64Jdk11RpmReleases.put("LATEST", new Release("Oct 20 2020 00:00:00", 5, "11.0.9+11-LTS"));
        rhel7Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-0.el7_9.x86_64",
                new Release("Oct 20 2020 00:00:00", 5, "11.0.9+11-LTS"));
        rhel7Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.8.10-1.el7.x86_64",
                new Release("Jul 11 2020 00:00:00", 4, "11.0.8+10-LTS"));
        rhel7Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.8.10-0.el7_8.x86_64",
                new Release("Jul 11 2020 00:00:00", 4, "11.0.8+10-LTS"));
        rhel7Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.7.10-4.el7_8.x86_64",
                new Release("Apr 14 2020 21:38:20", 3, "11.0.7+10-LTS"));
        rhel7Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.5.10-0.el7_7.x86_64",
                new Release("Oct 9 2019 18:41:22", 1, "11.0.5+10-LTS"));

        // RHEL8 amd64 OpenJDK11 rpm
        rhel8Amd64Jdk11RpmReleases = new HashMap<String, Release>();
        rhel8Amd64Jdk11RpmReleases.put("LATEST", new Release("Nov 10 2020 00:00:00", 4, "11.0.9.1+1-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-3.el8_3.x86_64",
                new Release("Nov 10 2020 21:42:14", 4, "11.0.9.1+1-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-2.el8_3.x86_64",
                new Release("Oct 20 2020 00:00:00", 3, "11.0.9+11-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-1.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 3, "11.0.9+11-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-0.el8_2.x86_64",
                new Release("Oct 20 2020 00:00:00", 3, "11.0.9+11-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-0.el8_1.x86_64",
                new Release("Oct 20 2020 00:00:00", 3, "11.0.9+11-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.9.11-0.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 3, "11.0.9+11-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.8.10-6.el8.x86_64",
                new Release("Jul 11 2020 00:00:00", 2, "11.0.8+10-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.8.10-0.el8_2.x86_64",
                new Release("Jul 11 2020 02:33:15", 2, "11.0.8+10-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.8.10-0.el8_1.x86_64",
                new Release("Jul 11 2020 00:00:00", 2, "11.0.8+10-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.8.10-0.el8_0.x86_64",
                new Release("Jul 11 2020 00:00:00", 2, "11.0.8+10-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.7.10-1.el8_1.x86_64",
                new Release("Apr 14 2020 00:00:00", 1, "11.0.7+10-LTS"));
        rhel8Amd64Jdk11RpmReleases.put("java-11-openjdk-11.0.7.10-1.el8_0.x86_64",
                new Release("Apr 14 2020 00:00:00", 1, "11.0.7+10-LTS"));

        // RHEL amd64 OpenJDK11 zip
        rhelJdk11ZipReleases = new HashMap<String, Release>();
        // First RHEL zip was 11.0.4.11.
        rhelJdk11ZipReleases.put("LATEST", new Release("Nov 11 2020 12:19:11", 7, "11.0.9.1+1-LTS"));
        rhelJdk11ZipReleases.put("11.0.9.1+1-LTS", new Release("Nov 11 2020 12:19:11", 7, "11.0.9.1+1-LTS"));
        rhelJdk11ZipReleases.put("11.0.9+11-LTS", new Release("Oct 20 2020 12:01:49", 6, "11.0.9+11-LTS"));
        rhelJdk11ZipReleases.put("11.0.8+10-LTS", new Release("Jul 14 2020 06:26:42", 5, "11.0.8+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.7+10-LTS", new Release("Apr 9 2020 11:42:52", 4, "11.0.7+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.6+10-LTS", new Release("Jan 12 2020 10:38:53", 3, "11.0.6+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.5+10-LTS", new Release("Oct 15 2019 09:18:41", 2, "11.0.5+10-LTS"));
        rhelJdk11ZipReleases.put("11.0.4+11-LTS", new Release("Aug 2 2019 08:21:47", 1, "11.0.4+11-LTS"));

        // Windows amd64 OpenJDK11 zip
        windowsJdk11Releases = new HashMap<String, Release>();
        // First Windows zip was 11.0.1.13.
        windowsJdk11Releases.put("LATEST", new Release("Nov 10 2020 12:16:00", 13, "11.0.9.1+1-LTS"));
        windowsJdk11Releases.put("11.0.9.1+1-LTS", new Release("Nov 10 2020 12:16:00", 13, "11.0.9.1+1-LTS"));
        windowsJdk11Releases.put("11.0.9+11-LTS", new Release("Oct 17 2020 16:53:23", 12, "11.0.9+11-LTS"));
        windowsJdk11Releases.put("11.0.8+10-LTS", new Release("Jul 12 2020 15:20:55", 11, "11.0.8+10-LTS"));
        windowsJdk11Releases.put("11.0.7+10-LTS", new Release("Apr 9 2020 00:20:14", 10, "11.0.7+10-LTS"));
        // Release 11.0.6.10 and 11.0.6.10-2 have the same version, just different build date/time
        windowsJdk11Releases.put("11.0.6+10-LTS", new Release("Jan 18 2020 11:49:14", 9, "11.0.6+0-LTS"));
        windowsJdk11Releases.put("11.0.6+0-LTS", new Release("Jan 10 2020 09:52:45", 8, "11.0.6+0-LTS"));
        // Release 11.0.5.10 and 11.0.5.10-2 have the same version, just different build date/time
        windowsJdk11Releases.put("11.0.5+10-LTS", new Release("Nov 8 2019 01:41:57", 7, "11.0.5+10-LTS"));
        windowsJdk11Releases.put("11.0.5+10-LTS", new Release("Oct 12 2019 18:25:22", 6, "11.0.5+10-LTS"));
        windowsJdk11Releases.put("11.0.4-redhat+11-LTS",
                new Release("Jul 11 2019 23:20:34", 5, "11.0.4-redhat+11-LTS"));
        windowsJdk11Releases.put("11.0.3-redhat+7-LTS", new Release("Apr 10 2019 15:05:25", 4, "11.0.3-redhat+7-LTS"));
        // Release 11.0.2.7-1 and 11.0.2.7-5 have the same version, just different build date/time
        windowsJdk11Releases.put("11.0.2-redhat+7-LTS", new Release("Feb 27 2019 17:48:49", 3, "11.0.2-redhat+7-LTS"));
        windowsJdk11Releases.put("11.0.2-redhat+7-LTS", new Release("Jan 16 2019 17:49:21", 2, "11.0.2-redhat+7-LTS"));
        windowsJdk11Releases.put("11.0.1-redhat+13-LTS",
                new Release("Oct 25 2018 09:40:01", 1, "11.0.1-redhat+13-LTS"));

        // RHEL7 ppc64le OpenJDK8 rpm
        rhel7Ppc64leJdk8RpmReleases = new HashMap<String, Release>();
        rhel7Ppc64leJdk8RpmReleases.put("LATEST", new Release("Nov 6 2020 00:00:00", 27, "1.8.0_275-b01"));
        rhel7Ppc64leJdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64le",
                new Release("Nov 6 2020 00:00:00", 27, "1.8.0_275-b01"));
        rhel7Ppc64leJdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.ppc64le",
                new Release("Oct 20 2020 01:33:13", 26, "1.8.0_272-b10"));
        rhel7Ppc64leJdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le",
                new Release("Jul 28 2020 11:16:00", 25, "1.8.0_265-b01"));
        rhel7Ppc64leJdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.ppc64le",
                new Release("Jul 16 2018 15:46:59 ", 18, "1.8.0_181-b13"));
        rhel7Ppc64leJdk8RpmReleases.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.ppc64le",
                new Release("Jul 16 2018 11:33:43", 18, "1.8.0_181-b13"));
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The known release date for the JDK build that produced the fatal error log.
     */
    public static final Date getJdkReleaseDate(FatalErrorLog fatalErrorLog) {
        Date date = null;
        if (fatalErrorLog != null) {
            HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
            if (releases != null && releases.size() > 0) {
                Release release = null;
                if (fatalErrorLog.isRhRpmInstall()) {
                    release = releases.get(fatalErrorLog.getRpmDirectory());
                ***REMOVED*** else if (fatalErrorLog.isRhLinuxZipInstall() || fatalErrorLog.isRhWindowsZipInstall()) {
                    release = releases.get(fatalErrorLog.getJdkReleaseString());
                ***REMOVED***
                if (release != null) {
                    date = release.getBuildDate();
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return date;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The release number for the JDK that produced the fatal error log.
     */
    public static final int getJdkReleaseNumber(FatalErrorLog fatalErrorLog) {
        int number = 0;
        if (fatalErrorLog != null) {
            HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
            if (releases != null && releases.size() > 0) {
                Release release = null;
                if (fatalErrorLog.isRhRpmInstall()) {
                    release = releases.get(fatalErrorLog.getRpmDirectory());
                ***REMOVED*** else if (fatalErrorLog.isRhLinuxZipInstall() || fatalErrorLog.isRhWindowsZipInstall()) {
                    release = releases.get(fatalErrorLog.getJdkReleaseString());
                ***REMOVED***
                if (release != null) {
                    number = release.getNumber();
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return number;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The JDK releases for the JDK that produced the fatal error log.
     */
    public static final HashMap<String, Release> getJdkReleases(FatalErrorLog fatalErrorLog) {
        HashMap<String, Release> releases = null;
        if (fatalErrorLog.getJavaVendor().equals(JavaVendor.RED_HAT)) {
            if (fatalErrorLog.isRhel()) {
                if (fatalErrorLog.isRhRpmInstall()) {
                    if (fatalErrorLog.getOsVersion() == OsVersion.RHEL6
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel6Amd64Jdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7 && fatalErrorLog.getArch() == Arch.X86_64
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel7Amd64Jdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7
                            && fatalErrorLog.getArch() == Arch.PPC64LE
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel7Ppc64leJdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK11) {
                        releases = rhel7Amd64Jdk11RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = rhel8Amd64Jdk8RpmReleases;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK11) {
                        releases = rhel8Amd64Jdk11RpmReleases;
                    ***REMOVED***
                ***REMOVED*** else if (fatalErrorLog.isRhLinuxZipInstall()) {
                    switch (fatalErrorLog.getJavaSpecification()) {
                    case JDK8:
                        releases = rhelJdk8ZipReleases;
                        break;
                    case JDK11:
                        releases = rhelJdk11ZipReleases;
                        break;
                    case UNKNOWN:
                    default:
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED*** else if (fatalErrorLog.isRhWindowsZipInstall()) {
                switch (fatalErrorLog.getJavaSpecification()) {
                case JDK8:
                    releases = windowsJdk8Releases;
                    break;
                case JDK11:
                    releases = windowsJdk11Releases;
                    break;
                case UNKNOWN:
                default:
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return releases;

    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return Latest JDK release date for the JDK that produced the fatal error log.
     */
    public static final Date getLatestJdkReleaseDate(FatalErrorLog fatalErrorLog) {
        Date date = null;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            date = releases.get("LATEST").getBuildDate();
        ***REMOVED***
        return date;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return Latest JDK release number for the JDK that produced the fatal error log.
     */
    public static final int getLatestJdkReleaseNumber(FatalErrorLog fatalErrorLog) {
        int number = 0;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            Release latest = releases.get("LATEST");
            number = latest.getNumber();
        ***REMOVED***
        return number;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return Latest JDK release string for the JDK that produced the fatal error log.
     */
    public static final String getLatestJdkReleaseString(FatalErrorLog fatalErrorLog) {
        String release = null;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            release = releases.get("LATEST").getVersion();
        ***REMOVED***
        return release;
    ***REMOVED***

    /**
     * Parse out the JVM option scalar value. For example, the value for <code>-Xss128k</code> is 128k. The value for
     * <code>-XX:PermSize=128M</code> is 128M.
     * 
     * @param option
     *            The JVM option.
     * @return The JVM option value.
     */
    public static final String getOptionValue(String option) {
        String value = null;
        if (option != null) {
            String regex = "^-[a-zA-Z:.]+(=)?(\\d{1,12***REMOVED***(" + JdkRegEx.OPTION_SIZE + ")?)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(option);
            if (matcher.find()) {
                value = matcher.group(2);
            ***REMOVED***
        ***REMOVED***
        return value;
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
        if (BitsEvent.match(logLine)) {
            logEventType = LogEventType.BITS;
        ***REMOVED*** else if (BlankLineEvent.match(logLine)) {
            logEventType = LogEventType.BLANK_LINE;
        ***REMOVED*** else if (CardTableEvent.match(logLine)) {
            logEventType = LogEventType.CARD_TABLE;
        ***REMOVED*** else if (ClassesRedefinedEvent.match(logLine)) {
            logEventType = LogEventType.CLASSES_REDEFINED;
        ***REMOVED*** else if (CodeCacheEvent.match(logLine)) {
            logEventType = LogEventType.CODE_CACHE;
        ***REMOVED*** else if (CompilationEvent.match(logLine)) {
            logEventType = COMPILATION;
        ***REMOVED*** else if (CpuInfoEvent.match(logLine)) {
            logEventType = LogEventType.CPU_INFO;
        ***REMOVED*** else if (CurrentThreadEvent.match(logLine)) {
            logEventType = LogEventType.CURRENT_THREAD;
        ***REMOVED*** else if (DeoptimizationEvent.match(logLine)) {
            logEventType = LogEventType.DEOPTIMIZATION_EVENT;
        ***REMOVED*** else if (DynamicLibraryEvent.match(logLine)) {
            logEventType = LogEventType.DYNAMIC_LIBRARY;
        ***REMOVED*** else if (ElapsedTimeEvent.match(logLine)) {
            logEventType = LogEventType.ELAPSED_TIME;
        ***REMOVED*** else if (EnvironmentVariablesEvent.match(logLine)) {
            logEventType = LogEventType.ENVIRONMENT_VARIABLES;
        ***REMOVED*** else if (ExceptionCountsEvent.match(logLine)) {
            logEventType = LogEventType.EXCEPTION_COUNTS;
        ***REMOVED*** else if (ExceptionEvent.match(logLine)) {
            logEventType = LogEventType.EXCEPTION_EVENT;
        ***REMOVED*** else if (HeaderEvent.match(logLine)) {
            logEventType = LogEventType.HEADER;
        ***REMOVED*** else if (HeadingEvent.match(logLine)) {
            logEventType = LogEventType.HEADING;
        ***REMOVED*** else if (HeapEvent.match(logLine)) {
            logEventType = LogEventType.HEAP;
        ***REMOVED*** else if (HeapAddressEvent.match(logLine)) {
            logEventType = LogEventType.HEAP_ADDRESS;
        ***REMOVED*** else if (HeapRegionsEvent.match(logLine)) {
            logEventType = LogEventType.HEAP_REGIONS;
        ***REMOVED*** else if (InstructionsEvent.match(logLine)) {
            logEventType = LogEventType.INSTRUCTIONS;
        ***REMOVED*** else if (LibcEvent.match(logLine)) {
            logEventType = LogEventType.LIBC;
        ***REMOVED*** else if (LoadAverageEvent.match(logLine)) {
            logEventType = LogEventType.LOAD_AVERAGE;
        ***REMOVED*** else if (MeminfoEvent.match(logLine)) {
            logEventType = LogEventType.MEMINFO;
        ***REMOVED*** else if (MemoryEvent.match(logLine)) {
            logEventType = LogEventType.MEMORY;
        ***REMOVED*** else if (OsEvent.match(logLine)) {
            logEventType = LogEventType.OS;
        ***REMOVED*** else if (PollingPageEvent.match(logLine)) {
            logEventType = LogEventType.POLLING_PAGE;
        ***REMOVED*** else if (RegisterEvent.match(logLine)) {
            logEventType = LogEventType.REGISTER;
        ***REMOVED*** else if (RegisterToMemoryMappingEvent.match(logLine)) {
            logEventType = LogEventType.REGISTER_TO_MEMORY_MAPPING;
        ***REMOVED*** else if (RlimitEvent.match(logLine)) {
            logEventType = LogEventType.RLIMIT;
        ***REMOVED*** else if (SigInfoEvent.match(logLine)) {
            logEventType = LogEventType.SIGINFO;
        ***REMOVED*** else if (SignalHandlersEvent.match(logLine)) {
            logEventType = LogEventType.SIGNAL_HANDLERS;
        ***REMOVED*** else if (StackEvent.match(logLine)) {
            logEventType = LogEventType.STACK;
        ***REMOVED*** else if (ThreadEvent.match(logLine)) {
            logEventType = LogEventType.THREAD;
        ***REMOVED*** else if (TimeEvent.match(logLine)) {
            logEventType = LogEventType.TIME;
        ***REMOVED*** else if (TimezoneEvent.match(logLine)) {
            logEventType = LogEventType.TIMEZONE;
        ***REMOVED*** else if (TopOfStackEvent.match(logLine)) {
            logEventType = LogEventType.TOP_OF_STACK;
        ***REMOVED*** else if (UnameEvent.match(logLine)) {
            logEventType = LogEventType.UNAME;
        ***REMOVED*** else if (VmArgumentsEvent.match(logLine)) {
            logEventType = LogEventType.VM_ARGUMENTS;
        ***REMOVED*** else if (VmEvent.match(logLine)) {
            logEventType = LogEventType.VM_EVENT;
        ***REMOVED*** else if (VmInfoEvent.match(logLine)) {
            logEventType = LogEventType.VM_INFO;
        ***REMOVED*** else if (VmMutexEvent.match(logLine)) {
            logEventType = LogEventType.VM_MUTEX;
        ***REMOVED*** else if (VmStateEvent.match(logLine)) {
            logEventType = LogEventType.VM_STATE;
        ***REMOVED***
        return logEventType;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return true if the JDK that produced the fatal error log is the latest release, false otherwise.
     */
    public static final boolean isLatestJdkRelease(FatalErrorLog fatalErrorLog) {
        boolean isLatestRelease = true;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && releases.size() > 0) {
            Release latest = releases.get("LATEST");
            if (!latest.getVersion().equals(fatalErrorLog.getJdkReleaseString())) {
                isLatestRelease = false;
            ***REMOVED***
        ***REMOVED***
        return isLatestRelease;
    ***REMOVED***

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
        case BITS:
            event = new BitsEvent(logLine);
            break;
        case BLANK_LINE:
            event = new BlankLineEvent(logLine);
            break;
        case CARD_TABLE:
            event = new CardTableEvent(logLine);
            break;
        case CLASSES_REDEFINED:
            event = new ClassesRedefinedEvent(logLine);
            break;
        case CODE_CACHE:
            event = new CodeCacheEvent(logLine);
            break;
        case COMPILATION:
            event = new CompilationEvent(logLine);
            break;
        case CPU_INFO:
            event = new CpuInfoEvent(logLine);
            break;
        case CURRENT_THREAD:
            event = new CurrentThreadEvent(logLine);
            break;
        case DEOPTIMIZATION_EVENT:
            event = new DeoptimizationEvent(logLine);
            break;
        case DYNAMIC_LIBRARY:
            event = new DynamicLibraryEvent(logLine);
            break;
        case ELAPSED_TIME:
            event = new ElapsedTimeEvent(logLine);
            break;
        case ENVIRONMENT_VARIABLES:
            event = new EnvironmentVariablesEvent(logLine);
            break;
        case EXCEPTION_COUNTS:
            event = new ExceptionCountsEvent(logLine);
            break;
        case EXCEPTION_EVENT:
            event = new ExceptionEvent(logLine);
            break;
        case HEADER:
            event = new HeaderEvent(logLine);
            break;
        case HEADING:
            event = new HeadingEvent(logLine);
            break;
        case HEAP:
            event = new HeapEvent(logLine);
            break;
        case HEAP_ADDRESS:
            event = new HeapAddressEvent(logLine);
            break;
        case HEAP_REGIONS:
            event = new HeapRegionsEvent(logLine);
            break;
        case INSTRUCTIONS:
            event = new InstructionsEvent(logLine);
            break;
        case LIBC:
            event = new LibcEvent(logLine);
            break;
        case LOAD_AVERAGE:
            event = new LoadAverageEvent(logLine);
            break;
        case MEMINFO:
            event = new MeminfoEvent(logLine);
            break;
        case MEMORY:
            event = new MemoryEvent(logLine);
            break;
        case OS:
            event = new OsEvent(logLine);
            break;
        case POLLING_PAGE:
            event = new PollingPageEvent(logLine);
            break;
        case REGISTER:
            event = new RegisterEvent(logLine);
            break;
        case REGISTER_TO_MEMORY_MAPPING:
            event = new RegisterToMemoryMappingEvent(logLine);
            break;
        case RLIMIT:
            event = new RlimitEvent(logLine);
            break;
        case SIGINFO:
            event = new SigInfoEvent(logLine);
            break;
        case SIGNAL_HANDLERS:
            event = new SignalHandlersEvent(logLine);
            break;
        case STACK:
            event = new StackEvent(logLine);
            break;
        case THREAD:
            event = new ThreadEvent(logLine);
            break;
        case TIME:
            event = new TimeEvent(logLine);
            break;
        case TIMEZONE:
            event = new TimezoneEvent(logLine);
            break;
        case TOP_OF_STACK:
            event = new TopOfStackEvent(logLine);
            break;
        case UNAME:
            event = new UnameEvent(logLine);
            break;
        case UNKNOWN:
            event = new UnknownEvent(logLine);
            break;
        case VM_ARGUMENTS:
            event = new VmArgumentsEvent(logLine);
            break;
        case VM_EVENT:
            event = new VmEvent(logLine);
            break;
        case VM_INFO:
            event = new VmInfoEvent(logLine);
            break;
        case VM_MUTEX:
            event = new VmMutexEvent(logLine);
            break;
        case VM_STATE:
            event = new VmStateEvent(logLine);
            break;
        default:
            throw new AssertionError("Unexpected event type value: " + eventType);
        ***REMOVED***
        return event;
    ***REMOVED***

    /**
     * Convert JVM size option to kilobytes.
     * 
     * @param size
     *            The size in various units (e.g. 'K').
     * @return The size in bytes.
     */
    public static long convertOptionSizeToBytes(final String size) {

        String regex = "(\\d{1,12***REMOVED***)" + JdkRegEx.OPTION_SIZE + "?";

        String value = null;
        char units = 'b';

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(size);
        if (matcher.find()) {
            value = matcher.group(1);
            if (matcher.group(2) != null) {
                units = matcher.group(2).charAt(0);
            ***REMOVED***
        ***REMOVED***

        BigDecimal bytes = new BigDecimal(value);

        switch (units) {

        case 'b':
        case 'B':
            // do nothing
            break;
        case 'k':
        case 'K':
            bytes = bytes.multiply(Constants.KILOBYTE);
            break;
        case 'm':
        case 'M':
            bytes = bytes.multiply(Constants.MEGABYTE);
            break;
        case 'g':
        case 'G':
            bytes = bytes.multiply(Constants.GIGABYTE);
            break;
        default:
            throw new AssertionError("Unexpected units value: " + units);

        ***REMOVED***
        return bytes.longValue();
    ***REMOVED***

    /**
     * Convert from bytes to kilobytes.
     * 
     * @param bytes
     *            The number of bytes.
     * @return The number of kilobytes.
     */
    public static long convertBytesToKilobytes(final long bytes) {
        BigDecimal kilobytes = new BigDecimal(bytes);
        kilobytes = kilobytes.divide(new BigDecimal("1024"));
        kilobytes = kilobytes.setScale(0, RoundingMode.HALF_EVEN);
        return kilobytes.longValue();
    ***REMOVED***
***REMOVED***

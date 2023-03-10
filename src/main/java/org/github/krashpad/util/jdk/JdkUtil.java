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
package org.github.krashpad.util.jdk;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.joa.domain.Arch;
import org.github.joa.util.Constants;
import org.github.krashpad.domain.BlankLine;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.UnknownEvent;
import org.github.krashpad.domain.jdk.BitsEvent;
import org.github.krashpad.domain.jdk.CardTable;
import org.github.krashpad.domain.jdk.CdsArchive;
import org.github.krashpad.domain.jdk.ClassesRedefinedEvent;
import org.github.krashpad.domain.jdk.ClassesUnloadedEvent;
import org.github.krashpad.domain.jdk.CodeCache;
import org.github.krashpad.domain.jdk.CommandLine;
import org.github.krashpad.domain.jdk.CompilationEvent;
import org.github.krashpad.domain.jdk.CompressedClassSpace;
import org.github.krashpad.domain.jdk.ContainerInfo;
import org.github.krashpad.domain.jdk.CpuInfo;
import org.github.krashpad.domain.jdk.CurrentCompileTask;
import org.github.krashpad.domain.jdk.CurrentThread;
import org.github.krashpad.domain.jdk.DeoptimizationEvent;
import org.github.krashpad.domain.jdk.DllOperationEvent;
import org.github.krashpad.domain.jdk.DynamicLibrary;
import org.github.krashpad.domain.jdk.ElapsedTime;
import org.github.krashpad.domain.jdk.End;
import org.github.krashpad.domain.jdk.EnvironmentVariables;
import org.github.krashpad.domain.jdk.Event;
import org.github.krashpad.domain.jdk.ExceptionCounts;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.GcHeapHistoryEvent;
import org.github.krashpad.domain.jdk.GcPreciousLog;
import org.github.krashpad.domain.jdk.GlobalFlags;
import org.github.krashpad.domain.jdk.Header;
import org.github.krashpad.domain.jdk.Heading;
import org.github.krashpad.domain.jdk.Heap;
import org.github.krashpad.domain.jdk.HeapAddress;
import org.github.krashpad.domain.jdk.HeapRegions;
import org.github.krashpad.domain.jdk.Host;
import org.github.krashpad.domain.jdk.Instructions;
import org.github.krashpad.domain.jdk.InternalExceptionEvent;
import org.github.krashpad.domain.jdk.InternalStatistic;
import org.github.krashpad.domain.jdk.LdPreloadFile;
import org.github.krashpad.domain.jdk.Libc;
import org.github.krashpad.domain.jdk.LoadAverage;
import org.github.krashpad.domain.jdk.Logging;
import org.github.krashpad.domain.jdk.MaxMapCount;
import org.github.krashpad.domain.jdk.Meminfo;
import org.github.krashpad.domain.jdk.Memory;
import org.github.krashpad.domain.jdk.Metaspace;
import org.github.krashpad.domain.jdk.NarrowKlass;
import org.github.krashpad.domain.jdk.NativeMemoryTracking;
import org.github.krashpad.domain.jdk.NumberEvent;
import org.github.krashpad.domain.jdk.OsInfo;
import org.github.krashpad.domain.jdk.OsUptime;
import org.github.krashpad.domain.jdk.PidMax;
import org.github.krashpad.domain.jdk.PollingPage;
import org.github.krashpad.domain.jdk.ProcessMemory;
import org.github.krashpad.domain.jdk.Register;
import org.github.krashpad.domain.jdk.RegisterToMemoryMapping;
import org.github.krashpad.domain.jdk.Release;
import org.github.krashpad.domain.jdk.RlimitEvent;
import org.github.krashpad.domain.jdk.SigInfo;
import org.github.krashpad.domain.jdk.SignalHandlers;
import org.github.krashpad.domain.jdk.Stack;
import org.github.krashpad.domain.jdk.StackSlotToMemoryMapping;
import org.github.krashpad.domain.jdk.Thread;
import org.github.krashpad.domain.jdk.ThreadsActiveCompile;
import org.github.krashpad.domain.jdk.ThreadsClassSmrInfo;
import org.github.krashpad.domain.jdk.ThreadsMax;
import org.github.krashpad.domain.jdk.Time;
import org.github.krashpad.domain.jdk.TimeElapsedTime;
import org.github.krashpad.domain.jdk.Timezone;
import org.github.krashpad.domain.jdk.TopOfStack;
import org.github.krashpad.domain.jdk.TransparentHugepage;
import org.github.krashpad.domain.jdk.Uid;
import org.github.krashpad.domain.jdk.Umask;
import org.github.krashpad.domain.jdk.Uname;
import org.github.krashpad.domain.jdk.VirtualizationInfo;
import org.github.krashpad.domain.jdk.VmArguments;
import org.github.krashpad.domain.jdk.VmInfo;
import org.github.krashpad.domain.jdk.VmMutex;
import org.github.krashpad.domain.jdk.VmOperation;
import org.github.krashpad.domain.jdk.VmOperationEvent;
import org.github.krashpad.domain.jdk.VmState;
import org.github.krashpad.util.Constants.OsVersion;

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
        AMQ,
        //
        AMQ_CLI,
        //
        CASSANDRA,
        //
        JBOSS_EAP6,
        //
        JBOSS_EAP7,
        //
        JBOSS_VERSION,
        //
        KAFKA,
        // Red Hat Certificate System, Red Hat Enterprise Linux (RHEL) Identity Management (IdM), or upstream Dogtag
        // Certificate System
        PKI_TOMCAT,
        //
        RHSSO,
        //
        SPRING_BOOT,
        //
        TOMCAT,
        //
        TOMCAT_SHUTDOWN,
        //
        UNKNOWN,
        /**
         * Upstream WildFly or an unidentified derivative (e.g. EAP, RHSSO, etc.).
         */
        WILDFLY
    ***REMOVED***

    /**
     * Defined collector families.
     */
    public enum CollectorFamily {
        CMS, G1, PARALLEL, SERIAL, SHENANDOAH, UNKNOWN, Z
    ***REMOVED***

    /**
     * Compressed ordinary object pointer (oop) modes used by the JVM to encode 64-bit addresses as 32-bit pointers to
     * save heap space for heaps &lt; 32G.
     * 
     * The JVM checks first checks if Heap + HeapBaseMinAddress can fit in the first 4G address space. If yes, 32-bit
     * (BIT32) mode is used.
     * 
     * If Heap + HeapBaseMinAddress cannot fit in the first 4G address space, the JVM checks if it can fit in the first
     * 32G address space. If yes zero-based (ZERO) mode is used.
     * 
     * If Heap + HeapBaseMinAddress cannot fit in the first 32G address space, and Heap &lt; 32G, non-zero mode
     * (NON-ZERO) is used.
     * 
     * BIT32: Heap mapped into the first 4G of virtual memory. Lower 32 bits of 64 bit address are used for the pointer
     * reference because higher order bits are all 0 (encoding/decoding not necessary).
     * 
     * NONE: 64-bit pointers are used (e.g. Heap &gt;= 32G).
     * 
     * NON_ZERO: Same as ZERO except Heap is mapped above the first 32G of virtual memory. Significant bits are stored
     * in the lowest bits, and encoding/decoding is done by bit shifting and added the heap base address.
     * 
     * ZERO: Heap mapped into the first 32G of virtual memory. Significant bits are stored in the lowest bits, and
     * encoding/decoding is done by bit shifting.
     */
    public enum CompressedOopMode {
        //
        BIT32, NON_ZERO, NONE, UNKNOWN, ZERO
    ***REMOVED***

    /**
     * Defined Java specifications.
     */
    public enum JavaSpecification {
        JDK10, JDK11, JDK12, JDK13, JDK14, JDK15, JDK16, JDK17,
        //
        JDK6, JDK7, JDK8, JDK9, UNKNOWN
    ***REMOVED***

    /**
     * Defined Java vendors.
     */
    public enum JavaVendor {
        ADOPTIUM, ADOPTOPENJDK, AZUL, MICROSOFT, NOT_RED_HAT, ORACLE, RED_HAT, UNIDENTIFIED
    ***REMOVED***

    /**
     * Defined logging events.
     */
    public enum LogEventType {
        //
        BITS, BLANK_LINE, CARD_TABLE, CDS_ARCHIVE, CLASSES_REDEFINED_EVENT, CLASSES_UNLOADED_EVENT, CODE_CACHE,
        //
        COMMAND_LINE, COMPILATION_EVENT, COMPRESSED_CLASS_SPACE, CONTAINER_INFO, CPU, CPU_INFO, CURRENT_COMPILE_TASK,
        //
        CURRENT_THREAD, DEOPTIMIZATION_EVENT, DLL_OPERATION_EVENT, DYNAMIC_LIBRARY, ELAPSED_TIME, END,
        //
        ENVIRONMENT_VARIABLES, EVENT, EXCEPTION_COUNTS, GC_HEAP_HISTORY_EVENT, GC_PRECIOUS_LOG, GLOBAL_FLAGS, HEADER,
        //
        HEADING, HEAP, HEAP_ADDRESS, HEAP_REGIONS, HOST, INSTRUCTIONS, INTEGER, INTERNAL_EXCEPTION_EVENT,
        //
        INTERNAL_STATISTICS, LD_PRELOAD_FILE, LIBC, LOAD_AVERAGE, LOGGING, MAX_MAP_COUNT, MEMINFO, MEMORY, METASPACE,
        //
        NARROW_KLASS, NATIVE_MEMORY_TRACKING, NUMBER, OS_INFO, OS_UPTIME, PID_MAX, POLLING_PAGE, PROCESS_MEMORY,
        //
        REGISTER, REGISTER_TO_MEMORY_MAPPING, RLIMIT, SIGINFO, SIGNAL_HANDLERS, STACK, STACK_SLOT_TO_MEMORY_MAPPING,
        //
        THREAD, THREADS_ACTIVE_COMPILE, THREADS_CLASS_SMR_INFO, THREADS_MAX, TIME, TIME_ELAPSED_TIME, TIMEZONE,
        //
        TOP_OF_STACK, TRANSPARENT_HUGEPAGE, UID, UMASK, UNAME, UNKNOWN, VIRTUALIZATION_INFO, VM_ARGUMENTS, VM_INFO,
        //
        VM_MUTEX, VM_OPERATION, VM_OPERATION_EVENT, VM_STATE
    ***REMOVED***

    /**
     * Signal codes.
     * 
     * BUS_ADRALN: The memory address that has an invalid address alignment for the CPU.
     * 
     * BUS_ADRERR: The memory address does not exist. Two causes: (1) Internal JVM corruption. (2) A memory mapped
     * (mmap) file has been deleted or truncated (e.g. a threading issue where 2 threads access a file at the same
     * time).
     * 
     * BUS_OBJERR: Hardware issue.
     * 
     * FPE_INTDIV: Floating point error (division by 0, modulo by 0, integer overflow).
     * 
     * SEGV_ACCERR: The access is not allowed due to permissions. For example: (1) Attempting to write to read-only
     * memory. (2) Attempting to write to protected (OS) memory. (3) Attempting to access an array at an index greater
     * than the array size (out of bounds).
     * 
     * SEGV_MAPERR: The memory address is not in the application address space. For example: (1) Dereferencing a null
     * pointer. (2) A corrupted pointer. (3) Stack overflow.
     * 
     * SI_KERNEL: Sent by the kernel.
     * 
     * SI_USER: A process or thread in the current process is raising the signal by calling kill.
     */
    public enum SignalCode {
        //
        BUS_ADRALN, BUS_ADRERR, BUS_OBJERR, FPE_INTDIV, ILL_ILLOPN, SEGV_ACCERR, SEGV_MAPERR, SI_KERNEL, SI_USER,
        //
        UNKNOWN
    ***REMOVED***

    /**
     * Signal numbers.
     * 
     * SIGBUS: Invalid memory address.
     * 
     * SIGFPE: Floating point error (division by 0, modulo by 0, integer overflow).
     * 
     * SIGILL: Illegal instruction at the processor.
     * 
     * SIGSEGV, EXCEPTION_ACCESS_VIOLATION: Segmentation fault. Accessing valid memory in an invalid way.
     */
    public enum SignalNumber {
        //
        EXCEPTION_ACCESS_VIOLATION, SIGBUS, SIGFPE, SIGILL, SIGSEGV, UNKNOWN
    ***REMOVED***

    /**
     * OpenJDK11 RHEL zip release information.
     */
    public static final HashMap<String, Release> JDK11_RHEL_ZIPS;

    /**
     * OpenJDK11 RHEL7 amd64 rpm release information.
     */
    public static final HashMap<String, Release> JDK11_RHEL7_X86_64_RPMS;

    /**
     * OpenJDK11 RHEL8 rpm release information.
     */
    public static final HashMap<String, Release> JDK11_RHEL8_X86_64_RPMS;

    /**
     * OpenJDK11 RHEL9 rpm release information.
     */
    public static final HashMap<String, Release> JDK11_RHEL9_X86_64_RPMS;

    /**
     * OpenJDK11 Windows release information.
     */
    public static final HashMap<String, Release> JDK11_WINDOWS_ZIPS;

    /**
     * OpenJDK17 RHEL zip release information.
     */
    public static final HashMap<String, Release> JDK17_RHEL_ZIPS;

    /**
     * OpenJDK17 RHEL8 rpm release information.
     */
    public static final HashMap<String, Release> JDK17_RHEL8_X86_64_RPMS;

    /**
     * OpenJDK17 RHEL9 rpm release information.
     */
    public static final HashMap<String, Release> JDK17_RHEL9_X86_64_RPMS;

    /**
     * OpenJDK17 Windows release information.
     */
    public static final HashMap<String, Release> JDK17_WINDOWS_ZIPS;

    /**
     * OpenJDK8 RHEL zip release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL_ZIPS;

    /**
     * OpenJDK8 RHEL6 amd64 rpm release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL6_X86_64_RPMS;

    /**
     * OpenJDK8 RHEL7 rpm ppc64 release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL7_PPC64_RPMS;

    /**
     * OpenJDK8 RHEL7 rpm ppc64le release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL7_PPC64LE_RPMS;

    /**
     * OpenJDK8 RHEL7 rpm amd64 release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL7_X86_64_RPMS;

    /**
     * OpenJDK8 RHEL8 rpm ppc64le release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL8_PPC64LE_RPMS;

    /**
     * OpenJDK8 RHEL8 amd64 rpm release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL8_X86_64_RPMS;

    /**
     * OpenJDK8 RHEL9 amd64 rpm release information.
     */
    public static final HashMap<String, Release> JDK8_RHEL9_X86_64_RPMS;

    /**
     * OpenJDK8 Windows release information.
     */
    public static final HashMap<String, Release> JDK8_WINDOWS_ZIPS;

    static {
        /*
         * Notes:
         * 
         * 1) Rpm key is the OpenJDK install directory.
         * 
         * 2) Zip key is build version.
         * 
         * 3) Jan 1 2000 00:00:00 means build date/time unknown/TBD.
         * 
         * 4) Time 00:00:00 means build date/time is estimate.
         */

        // RHEL6 amd64 OpenJDK8 rpm
        JDK8_RHEL6_X86_64_RPMS = new HashMap<String, Release>();
        JDK8_RHEL6_X86_64_RPMS.put("LATEST", new Release("Nov 6 2020 02:01:23", 30, "1.8.0_275-b01"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el6_10.x86_64",
                new Release("Nov 6 2020 02:01:23", 30, "1.8.0_275-b01"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el6_10.x86_64",
                new Release("Oct 20 2020 23:38:03", 29, "1.8.0_272-b10"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el6_10.x86_64",
                new Release("Jul 29 2020 03:46:33", 28, "1.8.0_265-b01"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64",
                new Release("Jul 12 2020 19:35:32", 27, "1.8.0_262-b10"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el6_10.x86_64",
                new Release("Apr 14 2020 14:55:11", 26, "1.8.0_252-b09"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b07-1.el6_10.x86_64",
                new Release("Jan 15 2020 00:00:00", 25, "1.8.0_242-b08"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-1.el6_10.x86_64",
                new Release("Oct 15 2019 00:00:00", 24, "1.8.0_232-b09"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el6_10.x86_64",
                new Release("Jul 11 2019 00:00:00", 23, "1.8.0_222-b10"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.212.b04-0.el6_10.x86_64",
                new Release("Apr 11 2019 00:00:00", 22, "1.8.0_212-b04"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el6_10.x86_64",
                new Release("Mar 5 2019 00:00:00", 21, "1.8.0_201-b09"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-1.el6_10.x86_64",
                new Release("Jan 17 2019 00:00:00", 21, "1.8.0_201-b09"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.191.b12-0.el6_10.x86_64",
                new Release("Oct 9 2018 00:00:00", 20, "1.8.0_191-b12"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el6_10.x86_64",
                new Release("Jul 16 2018 00:00:00", 19, "1.8.0_181-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-8.b10.el6_9.x86_64",
                new Release("May 16 2018 00:00:00", 18, "1.8.0_171-b10"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-3.b10.el6_9.x86_64",
                new Release("Apr 2 2018 00:00:00", 18, "1.8.0_171-b10"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-3.b14.el6_9.x86_64",
                new Release("Jan 10 2018 00:00:00", 17, "1.8.0_161-b14"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el6_9.x86_64",
                new Release("Oct 18 2017 00:00:00", 16, "1.8.0_151-b12"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.144-0.b01.el6_9.x86_64",
                new Release("Aug 21 2017 00:00:00", 15, "1.8.0_144-b01"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-3.b16.el6_9.x86_64",
                new Release("Jul 14 2017 00:00:00", 14, "1.8.0_141-b16"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-2.b16.el6_9.x86_64",
                new Release("Jul 14 2017 00:00:00", 14, "1.8.0_141-b16"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-0.b11.el6_9.x86_64",
                new Release("Apr 13 2017 00:00:00", 13, "1.8.0_131-b11"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.121-1.b13.el6.x86_64",
                new Release("Jan 17 2017 00:00:00", 12, "1.8.0_121-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.121-0.b13.el6_8.x86_64",
                new Release("Jan 17 2017 00:00:00", 12, "1.8.0_121-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-1.b15.el6_8.x86_64",
                new Release("Nov 8 2016 00:00:00", 11, "1.8.0_111-b15"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-0.b15.el6_8.x86_64",
                new Release("Nov 8 2016 00:00:00", 11, "1.8.0_111-b15"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.101-3.b13.el6_8.x86_64",
                new Release("Jul 11 2016 00:00:00", 10, "1.8.0_101-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-3.b14.el6_8.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-1.b14.el6.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-0.b14.el6_7.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.77-0.b03.el6_7.x86_64",
                new Release("Mar 23 2016 00:00:00", 8, "1.8.0_77-b03"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.71-5.b15.el6.x86_64",
                new Release("Feb 4 2016 00:00:00", 7, "1.8.0_71-b15"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.71-1.b15.el6_7.x86_64",
                new Release("Jan 13 2016 21:08:08", 7, "1.8.0_71-b15"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.65-0.b17.el6_7.x86_64",
                new Release("Oct 15 2015 00:00:00", 6, "1.8.0_65-b17"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-3.b16.el6_7.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-1.b16.el6_7.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-0.b16.el6_6.x86_64",
                new Release("Jul 1 2015 23:31:39", 5, "1.8.0_51-b16"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-35.b13.el6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-30.b13.el6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-28.b13.el6_6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.31-1.b13.el6_6.x86_64",
                new Release("Apr 10 2015 00:00:00", 3, "1.8.0_31-b13"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.25-3.b17.el6_6.x86_64",
                new Release("Oct 24 2014 00:00:00", 2, "1.8.0_25-b17"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.25-1.b17.el6.x86_64",
                new Release("Oct 24 2014 00:00:00", 2, "1.8.0_25-b17"));
        JDK8_RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.20-3.b26.el6.x86_64",
                new Release("Sep 12 2014 00:00:00", 1, "1.8.0_20-b26"));

        // RHEL7 ppc64 OpenJDK8 rpm
        JDK8_RHEL7_PPC64_RPMS = new HashMap<String, Release>();
        JDK8_RHEL7_PPC64_RPMS.put("LATEST", new Release("Jan 13 2023 00:00:00", 36, "1.8.0_362-b08"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el7_9.ppc64",
                new Release("Jan 13 2023 00:00:00", 36, "1.8.0_362-b08"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.ppc64",
                new Release("Oct 16 2022 00:00:00", 35, "1.8.0_352-b08"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.ppc64",
                new Release("Aug 4 2022 00:00:00", 34, "1.8.0_345-b01"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.ppc64",
                new Release("Jul 19 2022 00:00:00", 33, "1.8.0_342-b07"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.ppc64",
                new Release("Apr 19 2022 00:00:00", 32, "1.8.0_332-b09"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0_322-b06-1.el7_9",
                new Release("Jan 21 2022 00:00:00", 31, "1.8.0_322-b06"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.ppc64",
                new Release("Oct 15 2021 00:00:00", 30, "1.8.0_312-b07"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el7_9.ppc64",
                new Release("Jul 16 2021 00:00:00", 29, "1.8.0_302-b08"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el7_9.ppc64",
                new Release("Apr 09 2021 00:00:00", 28, "1.8.0_292-b10"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64",
                new Release("Jan 17 2021 19:55:50", 28, "1.8.0_282-b08"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64",
                new Release("Nov 6 2020 00:00:00", 27, "1.8.0_275-b01"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.ppc64",
                new Release("Oct 20 2020 00:00:00", 26, "1.8.0_272-b10"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64",
                new Release("Jul 28 2020 00:00:00", 25, "1.8.0_265-b01"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.ppc64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        JDK8_RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.ppc64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));

        // RHEL7 ppc64le OpenJDK8 rpm
        JDK8_RHEL7_PPC64LE_RPMS = new HashMap<String, Release>();
        JDK8_RHEL7_PPC64LE_RPMS.put("LATEST", new Release("Jan 13 2023 00:00:00", 37, "1.8.0_362-b08"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el7_9.ppc64le",
                new Release("Jan 13 2023 00:00:00", 37, "1.8.0_362-b08"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.ppc64le",
                new Release("Oct 16 2022 00:00:00", 36, "1.8.0_352-b08"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.ppc64le",
                new Release("Aug 4 2022 00:00:00", 35, "1.8.0_345-b01"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.ppc64le",
                new Release("Jul 19 2022 00:00:00", 34, "1.8.0_342-b07"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.ppc64le",
                new Release("Apr 19 2022 00:00:00", 33, "1.8.0_332-b09"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0_322-b06-1.el7_9",
                new Release("Jan 21 2022 00:00:00", 32, "1.8.0_322-b06"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.ppc64le",
                new Release("Oct 15 2021 00:00:00", 31, "1.8.0_312-b07"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el7_9.ppc64le",
                new Release("Jul 16 2021 12:33:20", 30, "1.8.0_302-b08"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el7_9.ppc64le",
                new Release("Apr 09 2021 00:00:00", 29, "1.8.0_292-b10"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64le",
                new Release("Jan 17 2021 00:00:00", 28, "1.8.0_282-b08"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64le",
                new Release("Nov 6 2020 06:43:55", 27, "1.8.0_275-b01"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.ppc64le",
                new Release("Oct 20 2020 01:33:13", 26, "1.8.0_272-b10"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le",
                new Release("Jul 28 2020 11:16:00", 25, "1.8.0_265-b01"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.ppc64le",
                new Release("Jul 16 2018 15:46:59", 18, "1.8.0_181-b13"));
        JDK8_RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.ppc64le",
                new Release("Jul 16 2018 11:33:43", 18, "1.8.0_181-b13"));

        // RHEL7 amd64 OpenJDK8 rpm
        JDK8_RHEL7_X86_64_RPMS = new HashMap<String, Release>();
        JDK8_RHEL7_X86_64_RPMS.put("LATEST", new Release("Jan 13 2023 22:08:34", 37, "1.8.0_362-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el7_9.x86_64",
                new Release("Jan 13 2023 22:08:34", 37, "1.8.0_362-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.x86_64",
                new Release("Oct 16 2022 04:57:48", 36, "1.8.0_352-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.x86_64",
                new Release("Aug 4 2022 00:00:00", 35, "1.8.0_345-b01"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.x86_64",
                new Release("Jul 18 2022 23:53:30", 34, "1.8.0_342-b07"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.x86_64",
                new Release("Apr 19 2022 00:14:41", 33, "1.8.0_332-b09"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-1.el7_9.x86_64",
                new Release("Jan 21 2022 06:01:57", 32, "1.8.0_322-b06"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64",
                new Release("Oct 15 2021 04:33:40", 31, "1.8.0_312-b07"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el7_9.x86_64",
                new Release("Jul 16 2021 12:35:49", 30, "1.8.0_302-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el7_9.x86_64",
                new Release("Apr 09 2021 05:00:30", 29, "1.8.0_292-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.x86_64",
                new Release("Jan 17 2021 19:44:11", 28, "1.8.0_282-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.x86_64",
                new Release("Nov 6 2020 01:41:37", 27, "1.8.0_275-b01"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64",
                new Release("Oct 19 2020 21:30:02", 26, "1.8.0_272-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.x86_64",
                new Release("Jul 28 2020 11:07:07", 25, "1.8.0_265-b01"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-1.el7.x86_64",
                new Release("Jul 12 2020 18:53:50", 25, "1.8.0_262-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el7_8.x86_64",
                new Release("Jul 12 2020 18:55:08", 25, "1.8.0_262-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64",
                new Release("Apr 14 2020 14:55:11", 24, "1.8.0_252-b09"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-1.el7.x86_64",
                new Release("Jan 15 2020 18:14:12", 23, "1.8.0_242-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el7_7.x86_64",
                new Release("Jan 19 2020 00:00:00", 23, "1.8.0_242-b08"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64",
                new Release("Oct 13 2019 10:47:01", 22, "1.8.0_232-b09"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-1.el7_7.x86_64",
                new Release("Jul 11 2019 03:23:03", 21, "1.8.0_222-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el7_6.x86_64",
                new Release("Jul 17 2019 00:00:00", 21, "1.8.0_222-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b03-1.el7.x86_64",
                new Release("May 22 2019 13:05:27", 21, "1.8.0_222-ea-b03"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.212.b04-0.el7_6.x86_64",
                new Release("Apr 16 2019 00:00:00", 21, "1.8.0_212-b04"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el7_6.x86_64",
                new Release("Mar 1 2019 00:00:00", 20, "1.8.0_201-b09"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-0.el7_6.x86_64",
                new Release("Mar 1 2019 00:00:00", 20, "1.8.0_201-b09"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.191.b12-1.el7_6.x86_64",
                new Release("Oct 9 2018 08:21:20", 19, "1.8.0_191-b12"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.191.b12-0.el7_5.x86_64",
                new Release("Oct 9 2018 08:21:41", 19, "1.8.0_191-b12"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.x86_64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.x86_64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-8.b10.el7_5.x86_64",
                new Release("May 16 2018 00:00:00", 17, "1.8.0_171-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-7.b10.el7.x86_64",
                new Release("Apr 2 2018 00:00:00", 17, "1.8.0_171-b10"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-2.b14.el7_4.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-2.b14.el7.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64",
                new Release("Jan 9 2018 19:54:33", 16, "1.8.0_161-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-5.b12.el7_4.x86_64",
                new Release("Nov 20 2017 11:29:18", 15, "1.8.0_151-b12"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.144-0.b01.el7_4.x86_64",
                new Release("Aug 21 2017 00:00:00", 14, "1.8.0_144-b01"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-2.b16.el7_4.x86_64",
                new Release("Jul 14 2017 00:00:00", 13, "1.8.0_141-b16"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-1.b16.el7_3.x86_64",
                new Release("Jul 14 2017 00:00:00", 13, "1.8.0_141-b16"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64",
                new Release("Jun 13 2017 11:27:53", 12, "1.8.0_131-b11"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-3.b12.el7_3.x86_64",
                new Release("May 9 2017 21:36:32", 12, "1.8.0_131-b11"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-2.b11.el7_3.x86_64",
                new Release("Apr 13 2017 00:00:00", 12, "1.8.0_131-b11"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.121-0.b13.el7_3.x86_64",
                new Release("Jan 16 2017 09:14:03", 11, "1.8.0_121-b13"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-2.b15.el7_3.x86_64",
                new Release("Nov 8 2016 00:00:00", 10, "1.8.0_111-b15"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-1.b15.el7_2.x86_64",
                new Release("Nov 8 2016 00:00:00", 10, "1.8.0_111-b15"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.102-4.b14.el7.x86_64",
                new Release("Sep 14 2016 00:00:00", 9, "1.8.0_102-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.102-1.b14.el7_2.x86_64",
                new Release("Sep 14 2016 00:00:00", 9, "1.8.0_102-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.101-3.b13.el7_2.x86_64",
                new Release("Jul 11 2016 00:00:00", 8, "1.8.0_101-b13"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-1.b14.el7_2.x86_64",
                new Release("Jun 21 2016 00:00:00", 7, "1.8.0_91-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-0.b14.el7_2.x86_64",
                new Release("Jun 21 2016 00:00:00", 7, "1.8.0_91-b14"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.77-0.b03.el7_2.x86_64",
                new Release("Mar 23 2016 00:00:00", 6, "1.8.0_77-b03"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.71-2.b15.el7_2.x86_64",
                new Release("Feb 4 2016 00:00:00", 5, "1.8.0_71-b15"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.65-3.b17.el7.x86_64",
                new Release("Oct 19 2015 06:27:55", 4, "1.8.0_65-b17"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.65-2.b17.el7_1.x86_64",
                new Release("Oct 15 2015 00:00:00", 4, "1.8.0_65-b17"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.60-2.b27.el7_1.x86_64",
                new Release("Sep 8 2015 00:00:00", 3, "1.8.0_60-b27"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-2.b16.el7.x86_64",
                new Release("Sep 4 2015 00:00:00", 2, "1.8.0_51-b16"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-1.b16.el7_1.x86_64",
                new Release("Sep 4 2015 00:00:00", 2, "1.8.0_51-b16"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-30.b13.el7_1.x86_64",
                new Release("Apr 29 2015 00:00:00", 1, "1.8.0_45-b13"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.31-7.b13.el7_1.x86_64",
                new Release("Apr 10 2015 00:00:00", 1, "1.8.0_31-b13"));
        JDK8_RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.31-2.b13.el7.x86_64",
                new Release("Jan 16 2015 10:50:03", 1, "1.8.0_31-b13"));

        // RHEL8 ppc64le OpenJDK8 rpm
        JDK8_RHEL8_PPC64LE_RPMS = new HashMap<String, Release>();
        JDK8_RHEL8_PPC64LE_RPMS.put("LATEST", new Release("Jan 24 2023 00:00:00", 40, "1.8.0_362-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b09-2.el8_7.ppc64le",
                new Release("Jan 24 2023 00:00:00", 40, "1.8.0_362-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_6.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_4.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_2.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_1.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_7.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_6.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_4.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_2.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_1.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-5.el8.ppc64le",
                new Release("Aug 30 2022 00:00:00", 38, "1.8.0_345-b01"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_6.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_4.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_2.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_1.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-2.el8_6.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_4.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_2.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_1.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-2.el8_6.ppc64le",
                new Release("Apr 28 2022 13:02:09", 36, "1.8.0_332-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_5.ppc64le",
                new Release("Apr 18 2022 20:17:53", 36, "1.8.0_332-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_4.ppc64le",
                new Release("Apr 28 2022 00:00:00", 36, "1.8.0_332-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_2.ppc64le",
                new Release("Apr 28 2022 00:00:00", 36, "1.8.0_332-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_1.ppc64le",
                new Release("Apr 28 2022 00:00:00", 36, "1.8.0_332-b09"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_5.ppc64le",
                new Release("Jan 23 2022 21:33:55", 35, "1.8.0_322-b06"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_4.ppc64le",
                new Release("Jan 24 2022 00:00:00", 35, "1.8.0_322-b06"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_2.ppc64le",
                new Release("Jan 24 2022 00:00:00", 35, "1.8.0_322-b06"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_1.ppc64le",
                new Release("Jan 24 2022 00:00:00", 35, "1.8.0_322-b06"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.ppc64le",
                new Release("Oct 16 2021 19:04:33", 34, "1.8.0_312-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_4.ppc64le",
                new Release("Oct 15 2021 00:00:00", 34, "1.8.0_312-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_2.ppc64le",
                new Release("Oct 15 2021 00:00:00", 34, "1.8.0_312-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_1.ppc64le",
                new Release("Oct 15 2021 00:00:00", 34, "1.8.0_312-b07"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_4.ppc64le",
                new Release("Jul 16 2021 15:54:01", 33, "1.8.0_302-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_2.ppc64le",
                new Release("Jul 14 2021 00:00:00", 33, "1.8.0_302-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_1.ppc64le",
                new Release("Jul 14 2021 00:00:00", 33, "1.8.0_302-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_3.ppc64le",
                new Release("Apr 14 2021 00:00:00", 32, "1.8.0_292-b10"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_2.ppc64le",
                new Release("Apr 14 2021 00:00:00", 32, "1.8.0_292-b10"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_1.ppc64le",
                new Release("Apr 19 2021 00:00:00", 32, "1.8.0_292-b10"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.ppc64le",
                new Release("Jan 17 2021 16:29:12", 31, "1.8.0_282-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_2.ppc64le",
                new Release("Jan 17 2021 00:00:00", 31, "1.8.0_282-b08"));
        JDK8_RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_1.ppc64le",
                new Release("Jan 17 2021 00:00:00", 31, "1.8.0_282-b08"));

        // RHEL8 amd64 OpenJDK8 rpm
        JDK8_RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        JDK8_RHEL8_X86_64_RPMS.put("LATEST", new Release("Jan 23 2023 22:00:01", 20, "1.8.0_362-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b09-2.el8_7.x86_64",
                new Release("Jan 23 2023 22:00:01", 20, "1.8.0_362-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_6.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        JDK8_RHEL8_X86_64_RPMS.put(" java-1.8.0-openjdk-1.8.0.362.b08-1.el8_4.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        JDK8_RHEL8_X86_64_RPMS.put(" java-1.8.0-openjdk-1.8.0.362.b08-1.el8_2.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        JDK8_RHEL8_X86_64_RPMS.put(" java-1.8.0-openjdk-1.8.0.362.b08-1.el8_1.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_7.x86_64",
                new Release("Oct 17 2022 20:39:31", 19, "1.8.0_352-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_6.x86_64",
                new Release("Oct 16 2022 00:38:58", 19, "1.8.0_352-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_4.x86_64",
                new Release("Oct 16 2022 00:00:00", 19, "1.8.0_352-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_2.x86_64",
                new Release("Oct 16 2022 00:00:00", 19, "1.8.0_352-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_1.x86_64",
                new Release("Oct 16 2022 00:00:00", 19, "1.8.0_352-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-5.el8.x86_64",
                new Release("Aug 30 2022 00:26:51", 18, "1.8.0_345-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_6.x86_64",
                new Release("Aug 4 2022 13:59:22", 18, "1.8.0_345-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_4.x86_64",
                new Release("Aug 4 2022 00:00:00", 18, "1.8.0_345-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_1.x86_64",
                new Release("Aug 4 2022 00:00:00", 18, "1.8.0_345-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-2.el8_6.x86_64",
                new Release("Jul 19 2022 07:37:54", 17, "1.8.0_342-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_4.x86_64",
                new Release("Jul 19 2022 00:00:00", 17, "1.8.0_342-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_2.x86_64",
                new Release("Jul 19 2022 00:00:00", 17, "1.8.0_342-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_1.x86_64",
                new Release("Jul 19 2022 00:00:00", 17, "1.8.0_342-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-2.el8_6.x86_64",
                new Release("Apr 28 2022 12:36:43", 16, "1.8.0_332-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_5.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_4.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_2.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_1.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_5.x86_64",
                new Release("Jan 23 2022 21:19:20", 15, "1.8.0_322-b06"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_4.x86_64",
                new Release("Jan 24 2022 00:00:00", 15, "1.8.0_322-b06"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_2.x86_64",
                new Release("Jan 24 2022 00:00:00", 15, "1.8.0_322-b06"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_1.x86_64",
                new Release("Jan 24 2022 00:00:00", 15, "1.8.0_322-b06"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.x86_64",
                new Release("Oct 16 2021 17:36:49", 14, "1.8.0_312-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_4.x86_64",
                new Release("Oct 15 2021 01:36:39", 14, "1.8.0_312-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_2.x86_64",
                new Release("Oct 15 2021 00:00:00", 14, "1.8.0_312-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_1.x86_64",
                new Release("Oct 15 2021 00:00:00", 14, "1.8.0_312-b07"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_4.x86_64",
                new Release("Jul 16 2021 14:54:40", 13, "1.8.0_302-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_2.x86_64",
                new Release("Jul 16 2021 00:00:00", 13, "1.8.0_302-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_1.x86_64",
                new Release("Jul 16 2021 00:00:00", 13, "1.8.0_302-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el8_4.x86_64",
                new Release("Apr 14 2021 01:09:37", 12, "1.8.0_292-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_3.x86_64",
                new Release("Apr 14 2021 09:32:57", 12, "1.8.0_292-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_2.x86_64",
                new Release("Apr 14 2021 00:00:00", 12, "1.8.0_292-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_1.x86_64",
                new Release("Apr 19 2021 00:00:00", 12, "1.8.0_292-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.x86_64",
                new Release("Jan 17 2021 16:21:17", 11, "1.8.0_282-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_2.x86_64",
                new Release("Jan 17 2021 00:00:00", 11, "1.8.0_282-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_1.x86_64",
                new Release("Jan 17 2021 00:00:00", 11, "1.8.0_282-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-1.el8_3.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_2.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_1.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_0.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-3.el8_3.x86_64",
                new Release("Oct 20 2020 23:38:03", 9, "1.8.0_272-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el8_2.x86_64",
                new Release("Oct 19 2020 21:37:28", 9, "1.8.0_272-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el8_1.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-4.el8.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_2.x86_64",
                new Release("Jul 28 2020 04:38:18", 8, "1.8.0_265-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_1.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_0.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64",
                new Release("Jul 12 2020 21:30:56", 7, "1.8.0_262-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_1.x86_64",
                new Release("Jul 12 2020 00:00:00", 7, "1.8.0_262-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_0.x86_64",
                new Release("Jul 12 2020 00:00:00", 7, "1.8.0_262-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-3.el8_2.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el8_1.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el8_0.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-4.el8.x86_64",
                new Release("Mar 27 2020 05:18:48", 5, "1.8.0_242-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el8_1.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el8_0.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-2.el8_1.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "1.8.0_232-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-0.el8_0.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "1.8.0_232-b09"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-1.el8.x86_64",
                new Release("Jul 11 2019 00:00:00", 3, "1.8.0_222-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el8_0.x86_64",
                new Release("Jul 11 2019 03:36:28", 3, "1.8.0_222-b10"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.212.b04-1.el8_0.x86_64",
                new Release("May 2 2019 00:00:00", 2, "1.8.0_212-b04"));
        JDK8_RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el8.x86_64",
                new Release("Feb 7 2019 00:00:00", 1, "1.8.0_201-b09"));

        // RHEL9 amd64 OpenJDK8 rpm
        JDK8_RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        JDK8_RHEL9_X86_64_RPMS.put("LATEST", new Release("Jan 24 2023 00:00:00", 6, "1.8.0_362-b09"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b09-2.el9_1.x86_64",
                new Release("Jan 24 2023 00:00:00", 6, "1.8.0_362-b09"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-2.el9_0.x86_64",
                new Release("Jan 13 2023 00:00:00", 6, "1.8.0_362-b08"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el9_1.x86_64",
                new Release("Oct 19 2022 00:00:00", 5, "1.8.0_352-b08"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el9_0.x86_64",
                new Release("Oct 16 2022 00:00:00", 5, "1.8.0_352-b08"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-5.el9.x86_64",
                new Release("Aug 30 2022 00:00:00", 4, "1.8.0_345-b01"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el9_0.x86_64",
                new Release("Aug 4 2022 00:00:00", 4, "1.8.0_345-b01"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el9_0.x86_64",
                new Release("Jul 18 2022 00:00:00", 3, "1.8.0_342-b07"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el9_0.x86_64",
                new Release("Apr 28 2022 00:00:00", 2, "1.8.0_332-b09"));
        JDK8_RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-9.el9.x86_64",
                new Release("Feb 28 2022 00:00:00", 1, "1.8.0_332-b06"));

        // RHEL amd64 OpenJDK8 zip
        // First RHEL zip was 1.8.0_222.
        JDK8_RHEL_ZIPS = new HashMap<String, Release>();
        JDK8_RHEL_ZIPS.put("LATEST", new Release("Jan 13 2023 23:03:15", 18, "1.8.0_362-b08"));
        JDK8_RHEL_ZIPS.put("1.8.0_362-b08", new Release("Jan 13 2023 23:03:15", 18, "1.8.0_362-b08"));
        JDK8_RHEL_ZIPS.put("1.8.0_352-b08", new Release("Oct 16 2022 05:58:04", 17, "1.8.0_352-b08"));
        JDK8_RHEL_ZIPS.put("1.8.0_345-b01", new Release("Aug 4 2022 05:08:02", 16, "1.8.0_345-b01"));
        JDK8_RHEL_ZIPS.put("1.8.0_342-b07", new Release("Jul 19 2022 17:11:55", 15, "1.8.0_342-b07"));
        JDK8_RHEL_ZIPS.put("1.8.0_332-b09", new Release("Apr 20 2022 01:14:17", 14, "1.8.0_332-b09"));
        JDK8_RHEL_ZIPS.put("1.8.0_322-b06", new Release("Jan 25 2022 16:48:34", 13, "1.8.0_322-b06"));
        JDK8_RHEL_ZIPS.put("1.8.0_312-b07", new Release("Oct 18 2021 16:23:58", 12, "1.8.0_312-b07"));
        JDK8_RHEL_ZIPS.put("1.8.0_302-b08", new Release("Jul 17 2021 18:13:18", 11, "1.8.0_302-b08"));
        JDK8_RHEL_ZIPS.put("1.8.0_292-b10", new Release("Apr 14 2021 09:32:57", 10, "1.8.0_292-b10"));
        JDK8_RHEL_ZIPS.put("1.8.0_282-b08", new Release("Jan 18 2021 13:56:21", 9, "1.8.0_282-b08"));
        JDK8_RHEL_ZIPS.put("1.8.0_275-b01", new Release("Nov 11 2020 12:18:48", 8, "1.8.0_275-b01"));
        JDK8_RHEL_ZIPS.put("1.8.0_272-b10", new Release("Oct 20 2020 12:17:01", 7, "1.8.0_272-b10"));
        JDK8_RHEL_ZIPS.put("1.8.0_265-b01", new Release("Jul 28 2020 13:27:15", 6, "1.8.0_265-b01"));
        JDK8_RHEL_ZIPS.put("1.8.0_262-b10", new Release("Jul 14 2020 11:46:22", 5, "1.8.0_262-b10"));
        JDK8_RHEL_ZIPS.put("1.8.0_252-b09", new Release("Apr 13 2020 13:01:26", 4, "1.8.0_252-b09"));
        JDK8_RHEL_ZIPS.put("1.8.0_242-b08", new Release("Jan 17 2020 09:36:23", 3, "1.8.0_242-b08"));
        JDK8_RHEL_ZIPS.put("1.8.0_232-b09", new Release("Oct 15 2019 05:49:57", 2, "1.8.0_232-b09"));
        JDK8_RHEL_ZIPS.put("1.8.0_222-b10", new Release("Aug 2 2019 08:16:48", 1, "1.8.0_222-b10"));

        // Windows amd64 OpenJDK8 zip
        // First RH Windows release was 1.8.0_191-1-redhat-b12. There was no RH Windows release for u202.
        JDK8_WINDOWS_ZIPS = new HashMap<String, Release>();
        JDK8_WINDOWS_ZIPS.put("LATEST", new Release("Jan 13 2023 00:00:00", 23, "1.8.0_362-b08"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_362-b08", new Release("Jan 13 2023 00:00:00", 23, "1.8.0_362-b08"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_352-b08", new Release("Oct 16 2022 00:00:00", 22, "1.8.0_352-b08"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_345-b01", new Release("Aug 4 2022 00:00:00", 21, "1.8.0_345-b01"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_342-b07", new Release("Jul 19 2022 00:00:00", 20, "1.8.0_342-b07"));
        // 2 builds w/ the same release string
        JDK8_WINDOWS_ZIPS.put("1.8.0_332-b09-2", new Release("Apr 27 2022 21:29:19", 19, "1.8.0_332-b09"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_332-b09-1", new Release("Apr 19 2022 13:36:53", 18, "1.8.0_332-b09"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_322-b06", new Release("Jan 21 2022 12:43:13", 17, "1.8.0_322-b06"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_312-b07", new Release("Oct 16 2021 19:50:08", 16, "1.8.0_312-b07"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_302-b08", new Release("Jul 16 2021 19:06:52", 15, "1.8.0_302-b08"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_292-b10", new Release("Apr 15 2021 23:02:06", 14, "1.8.0_292-b10"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_282-b08", new Release("Jan 17 2021 20:58:15", 13, "1.8.0_282-b08"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_275-b01", new Release("Nov 6 2020 21:03:52", 12, "1.8.0_275-b01"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_272-b10", new Release("Oct 17 2020 18:45:37", 11, "1.8.0_272-b10"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_265-b01", new Release("Jul 31 2020 12:33:19", 10, "1.8.0_265-b01"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_262-b10", new Release("Jul 14 2020 12:47:22", 9, "1.8.0_262-b10"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_252-b09", new Release("Apr 9 2020 22:56:04", 8, "1.8.0_252-b09"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_242-b08", new Release("Jan 18 2020 12:08:54", 7, "1.8.0_242-b08"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_242-b07", new Release("Jan 13 2020 12:14:39", 6, "1.8.0_242-b07"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_232-b09", new Release("Oct 12 2019 23:47:20", 5, "1.8.0_232-b09"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_222-1-redhat-b10",
                new Release("Jul 12 2019 11:59:03", 4, "1.8.0_222-1-redhat-b10"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_212-2-redhat-b04",
                new Release("Apr 15 2019 20:39:59", 3, "1.8.0_212-2-redhat-b04"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_201-1-redhat-b09",
                new Release("Jan 18 2019 11:40:39", 2, "1.8.0_201-1-redhat-b09"));
        JDK8_WINDOWS_ZIPS.put("1.8.0_191-1-redhat-b12",
                new Release("Oct 22 2018 23:16:39", 1, "1.8.0_191-1-redhat-b12"));

        // RHEL7 amd64 OpenJDK11 rpm
        JDK11_RHEL7_X86_64_RPMS = new HashMap<String, Release>();
        JDK11_RHEL7_X86_64_RPMS.put("LATEST", new Release("Jan 11 2023 00:00:00", 21, "11.0.18+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el7_9.x86_64",
                new Release("Jan 11 2023 00:00:00", 21, "11.0.18+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el7_9.x86_64",
                new Release("Oct 16 2022 00:00:00", 20, "11.0.17+8-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el7_9.x86_64",
                new Release("Sep 1 2022 00:00:00", 19, "11.0.16+8-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el7_9.x86_64",
                new Release("Jul 18 2022 19:50:20", 18, "11.0.16+8-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el7_9.x86_64",
                new Release("Apr 16 2022 00:00:00", 17, "11.0.15+9-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-1.el7_9.x86_64",
                new Release("Feb 11 2022 00:00:00", 16, "11.0.14.1+1-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-1.el7_9.x86_64",
                new Release("Jan 18 2022 00:00:00", 15, "11.0.14+9-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el7_9.x86_64",
                new Release("Oct 13 2021 00:00:00", 14, "11.0.13+8-LTS)"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el7_9.x86_64",
                new Release("Jul 14 2021 00:06:01", 13, "11.0.12+7-LTS)"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-1.el7_9.x86_64",
                new Release("Apr 13 2021 02:52:48", 12, "11.0.11+9-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-0.el7_9.x86_64",
                new Release("Jan 14 2021 23:18:04", 11, "11.0.10+9-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-2.el7_9.x86_64",
                new Release("Nov 12 2020 18:10:11", 10, "11.0.9.1+1-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el7_9.x86_64",
                new Release("Oct 15 2020 11:45:12", 9, "11.0.9+11-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-1.el7.x86_64",
                new Release("Jul 11 2020 00:00:00", 8, "11.0.8+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el7_8.x86_64",
                new Release("Jul 11 2020 00:00:00", 8, "11.0.8+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.7.10-4.el7_8.x86_64",
                new Release("Apr 14 2020 21:38:20", 7, "11.0.7+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-3.el7.x86_64",
                new Release("Feb 16 2020 00:00:00", 6, "11.0.6+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-1.el7_7.x86_64",
                new Release("Jan 11 2020 00:00:00", 6, "11.0.6+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.5.10-0.el7_7.x86_64",
                new Release("Oct 9 2019 18:41:22", 5, "11.0.5+10-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-1.el7_7.x86_64",
                new Release("Jul 9 2019 00:00:00", 4, "11.0.4+11-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-0.el7_6.x86_64",
                new Release("Jul 9 2019 00:00:00", 4, "11.0.4+11-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.3.7-1.el7.x86_64",
                new Release("Apr 4 2019 00:00:00", 3, "11.0.3+7-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.3.7-0.el7_6.x86_64",
                new Release("Apr 9 2019 00:00:00", 3, "11.0.3+7-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.2.7-0.el7_6.x86_64",
                new Release("Jan 15 2019 00:00:00", 2, "11.0.2+7-LTS"));
        JDK11_RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.1.13-3.el7_6.x86_64",
                new Release("Oct 24 2018 00:00:00", 1, "11.0.1+13-LTS"));

        // RHEL8 amd64 OpenJDK11 rpm
        JDK11_RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        JDK11_RHEL8_X86_64_RPMS.put("LATEST", new Release("Jan 10 2023 23:55:47", 21, "11.0.18+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-2.el8_7.x86_64",
                new Release("Jan 10 2023 23:55:47", 21, "11.0.18+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_6.x86_64",
                new Release("Jan 13 2023 00:00:00", 21, "11.0.18+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_4.x86_64",
                new Release("Jan 13 2023 00:00:00", 21, "11.0.18+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_2.x86_64",
                new Release("Jan 13 2023 00:00:00", 21, "11.0.18+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_1.x86_64",
                new Release("Jan 13 2023 00:00:00", 21, "11.0.18+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_6.x86_64",
                new Release("Oct 15 2022 19:13:31", 20, "11.0.17+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_4.x86_64",
                new Release("Oct 15 2022 00:00:00", 20, "11.0.17+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_2.x86_64",
                new Release("Oct 15 2022 00:00:00", 20, "11.0.17+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_1.x86_64",
                new Release("Oct 15 2022 00:00:00", 20, "11.0.17+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_6.x86_64",
                new Release("Aug 25 2022 13:29:35", 19, "11.0.16.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_4.x86_64",
                new Release("Aug 25 2022 00:00:00", 19, "11.0.16.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_2.x86_64",
                new Release("Aug 25 2022 00:00:00", 19, "11.0.16.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_1.x86_64",
                new Release("Aug 25 2022 00:00:00", 19, "11.0.16.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_6.x86_64",
                new Release("Jul 18 2022 13:53:34", 18, "11.0.16+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_4.x86_64",
                new Release("Jul 18 2022 00:00:00", 18, "11.0.16+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_2.x86_64",
                new Release("Jul 18 2022 00:00:00", 18, "11.0.16+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_1.x86_64",
                new Release("Jul 18 2022 00:00:00", 18, "11.0.16+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.10-2.el8_6.x86_64",
                new Release("Apr 27 2022 21:58:19", 17, "11.0.15+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_5.x86_64",
                new Release("Apr 15 2022 23:31:28", 17, "11.0.15+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_4.x86_64",
                new Release("Apr 16 2022 00:00:00", 17, "11.0.15+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_2.x86_64",
                new Release("Apr 16 2022 00:00:00", 17, "11.0.15+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_1.x86_64",
                new Release("Apr 16 2022 00:00:00", 17, "11.0.15+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-2.el8_5.x86_64",
                new Release("Feb 23 2022 11:57:18", 16, "11.0.14.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-2.el8_1.x86_64",
                new Release("Feb 23 2022 00:00:00", 16, "11.0.14.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-1.el8_4.x86_64",
                new Release("Feb 11 2022 00:00:00", 16, "11.0.14.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-1.el8_2.x86_64",
                new Release("Feb 11 2022 10:50:37", 16, "11.0.14.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_5.x86_64",
                new Release("Jan 17 2022 22:54:29", 15, "11.0.14+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_4.x86_64",
                new Release("Jan 18 2022 00:00:00", 15, "11.0.14+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_2.x86_64",
                new Release("Jan 18 2022 00:00:00", 15, "11.0.14+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_1.x86_64",
                new Release("Jan 18 2022 00:00:00", 15, "11.0.14+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-4.el8_5.x86_64",
                new Release("Nov 7 2021 20:03:27", 14, "11.0.13+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-3.el8_5.x86_64",
                new Release("Oct 27 2021 22:03:57", 13, "11.0.13+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el8_4.x86_64",
                new Release("Oct 13 2021 11:20:31", 13, "11.0.13+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el8_2.x86_64",
                new Release("Oct 13 2021 00:00:00", 13, "11.0.13+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el8_1.x86_64",
                new Release("Oct 13 2021 00:00:00", 13, "11.0.13+8-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el8_4.x86_64",
                new Release("Jul 14 2021 11:31:14", 12, "11.0.12+7-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el8_2.x86_64",
                new Release("Jul 14 2021 00:00:00", 12, "11.0.12+7-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el8_1.x86_64",
                new Release("Jul 14 2021 00:00:00", 12, "11.0.12+7-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-2.el8_4.x86_64",
                new Release("Apr 15 2021 01:22:52", 11, "11.0.11+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-0.el8_3.x86_64",
                new Release("Apr 15 2021 01:33:28", 11, "11.0.11+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-0.el8_2.x86_64",
                new Release("Apr 15 2021 00:00:00", 11, "11.0.11+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-0.el8_1.x86_64",
                new Release("Apr 15 2021 00:00:00", 11, "11.0.11+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-8.el8.x86_64",
                new Release("Feb 22 2021 00:57:14", 10, "11.0.10+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-4.el8_3.x86_64",
                new Release("Jan 18 2021 00:04:32", 10, "11.0.10+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-0.el8_2.x86_64",
                new Release("Jan 15 2021 00:00:00", 10, "11.0.10+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-1.el8_1.x86_64",
                new Release("Jan 18 2021 00:00:00", 10, "11.0.10+9-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-3.el8_3.x86_64",
                new Release("Nov 10 2020 21:42:14", 9, "11.0.9.1+1-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-2.el8_3.x86_64",
                new Release("Oct 20 2020 00:00:00", 8, "11.0.9+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-1.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 8, "11.0.9+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el8_2.x86_64",
                new Release("Oct 20 2020 00:00:00", 8, "11.0.9+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el8_1.x86_64",
                new Release("Oct 20 2020 00:00:00", 8, "11.0.9+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 8, "11.0.9+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-6.el8.x86_64",
                new Release("Jul 11 2020 00:00:00", 7, "11.0.8+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el8_2.x86_64",
                new Release("Jul 11 2020 02:33:15", 7, "11.0.8+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el8_1.x86_64",
                new Release("Jul 11 2020 00:00:00", 7, "11.0.8+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el8_0.x86_64",
                new Release("Jul 11 2020 00:00:00", 7, "11.0.8+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.7.10-1.el8_1.x86_64",
                new Release("Apr 14 2020 00:00:00", 6, "11.0.7+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.7.10-1.el8_0.x86_64",
                new Release("Apr 14 2020 00:00:00", 6, "11.0.7+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-0.el8_1.x86_64",
                new Release("Jan 11 2020 04:53:43", 5, "11.0.6+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-0.el8_0.x86_64",
                new Release("Jan 11 2020 00:00:00", 5, "11.0.6+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.5.10-2.el8_1.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "11.0.5+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.5.10-0.el8_0.x86_64",
                new Release("Oct 11 2019 00:00:00", 4, "11.0.5+10-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-2.el8.x86_64",
                new Release("Jul 9 2019 00:00:00", 3, "11.0.4+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-0.el8_0.x86_64",
                new Release("Jul 9 2019 00:00:00", 3, "11.0.4+11-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.3.7-2.el8_0.x86_64",
                new Release("May 1 2019 00:00:00", 2, "11.0.3+7-LTS"));
        JDK11_RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.2.7-2.el8.x86_64",
                new Release("Feb 8 2019 00:00:00", 1, "11.0.2+7-LTS"));

        // RHEL9 amd64 OpenJDK11 rpm
        JDK11_RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        JDK11_RHEL9_X86_64_RPMS.put("LATEST", new Release("Jan 11 2023 00:00:00", 5, "11.0.18+10-LTS"));
        JDK11_RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-2.el9_1.x86_64",
                new Release("Jan 11 2023 00:00:00", 5, "11.0.18+10-LTS"));
        JDK11_RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el9_0.x86_64",
                new Release("Jan 13 2023 00:00:00", 5, "11.0.18+10-LTS"));
        JDK11_RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el9_0.x86_64",
                new Release("Oct 15 2022 00:00:00", 4, "11.0.17+8-LTS"));
        JDK11_RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el9_0.x86_64",
                new Release("Aug 25 2022 00:00:00", 3, "11.0.16.1+1-LTS"));
        JDK11_RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el9_0.x86_64",
                new Release("Jul 18 2022 00:00:00", 2, "11.0.16+8-LTS"));
        JDK11_RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.10-1.el9_0.x86_64",
                new Release("Apr 28 2022 00:00:00", 1, "11.0.15+10-LTS"));

        // RHEL amd64 OpenJDK11 zip
        // First RHEL zip was 11.0.4.11.
        JDK11_RHEL_ZIPS = new HashMap<String, Release>();
        JDK11_RHEL_ZIPS.put("LATEST", new Release("Jan 13 2023 04:46:48", 18, "11.0.18+10-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.18+10-LTS", new Release("Jan 13 2023 04:46:48", 18, "11.0.18+10-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.17+8-LTS", new Release("Oct 16 2022 03:03:26", 17, "11.0.17+8-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.16.1+1-LTS", new Release("Aug 25 2022 17:29:35", 16, "11.0.16.1+1-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.16+8-LTS", new Release("Jul 19 2022 14:18:09", 15, "11.0.16+8-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.15+9-LTS", new Release("Apr 16 2022 15:20:06", 14, "11.0.15+9-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.14.1+1-LTS", new Release("Feb 12 2022 05:34:35", 13, "11.0.14.1+1-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.14+9-LTS", new Release("Jan 19 2022 12:27:57", 12, "11.0.14+9-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.13+8-LTS", new Release("Oct 14 2021 19:43:57", 11, "11.0.13+8-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.12+7-LTS", new Release("Jul 15 2021 10:32:12", 10, "11.0.12+7-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.11+9-LTS", new Release("Apr 12 2021 13:04:24", 9, "11.0.11+9-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.10+9-LTS", new Release("Jan 18 2021 00:04:32", 8, "11.0.10+9-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.9.1+1-LTS", new Release("Nov 11 2020 12:19:11", 7, "11.0.9.1+1-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.9+11-LTS", new Release("Oct 20 2020 12:01:49", 6, "11.0.9+11-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.8+10-LTS", new Release("Jul 14 2020 06:26:42", 5, "11.0.8+10-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.7+10-LTS", new Release("Apr 9 2020 11:42:52", 4, "11.0.7+10-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.6+10-LTS", new Release("Jan 12 2020 10:38:53", 3, "11.0.6+10-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.5+10-LTS", new Release("Oct 15 2019 09:18:41", 2, "11.0.5+10-LTS"));
        JDK11_RHEL_ZIPS.put("11.0.4+11-LTS", new Release("Aug 2 2019 08:21:47", 1, "11.0.4+11-LTS"));

        // Windows amd64 OpenJDK11 zip
        // First Windows zip was 11.0.1.13.
        JDK11_WINDOWS_ZIPS = new HashMap<String, Release>();
        JDK11_WINDOWS_ZIPS.put("LATEST", new Release("Jan 13 2023 00:00:00", 25, "11.0.18+10-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.18+10-LTS", new Release("Jan 13 2023 00:00:00", 25, "11.0.18+10-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.17+8-LTS", new Release("Oct 16 2022 00:00:00", 24, "11.0.17+8-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.16.1+1-LTS", new Release("Aug 25 2022 00:00:00", 23, "11.0.16.1+1-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.16+8-LTS", new Release("Jul 19 2022 00:00:00", 22, "11.0.16+8-LTS"));
        // 2 builds w/ the same release string
        JDK11_WINDOWS_ZIPS.put("11.0.15+9-LTS-2", new Release("Apr 27 2022 19:12:18", 21, "11.0.15+9-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.15+9-LTS-1", new Release("Apr 17 2022 13:56:34", 20, "11.0.15+9-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.14.1+9-LTS", new Release("Feb 14 2022 21:03:13", 19, "11.0.14.1+9-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.14+9-LTS", new Release("Jan 17 2022 22:55:50", 18, "11.0.14+9-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.13+8-LTS", new Release("Oct 16 2021 19:46:00", 17, "11.0.13+8-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.12+7-LTS", new Release("Jul 15 2021 16:55:31", 16, "11.0.12+7-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.11+9-LTS", new Release("Apr 15 2021 21:44:00", 15, "11.0.11+9-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.10+9-LTS", new Release("Jan 16 2021 19:49:44", 14, "11.0.10+9-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.9.1+1-LTS", new Release("Nov 10 2020 12:16:00", 13, "11.0.9.1+1-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.9+11-LTS", new Release("Oct 17 2020 16:53:23", 12, "11.0.9+11-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.8+10-LTS", new Release("Jul 12 2020 15:20:55", 11, "11.0.8+10-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.7+10-LTS", new Release("Apr 9 2020 00:20:14", 10, "11.0.7+10-LTS"));
        // Release 11.0.6.10 and 11.0.6.10-2 have the same version, just different build date/time
        JDK11_WINDOWS_ZIPS.put("11.0.6+10-LTS", new Release("Jan 18 2020 11:49:14", 9, "11.0.6+0-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.6+0-LTS", new Release("Jan 10 2020 09:52:45", 8, "11.0.6+0-LTS"));
        // Release 11.0.5.10 and 11.0.5.10-2 have the same version, just different build date/time
        JDK11_WINDOWS_ZIPS.put("11.0.5+10-LTS", new Release("Nov 8 2019 01:41:57", 7, "11.0.5+10-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.5+10-LTS", new Release("Oct 12 2019 18:25:22", 6, "11.0.5+10-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.4-redhat+11-LTS", new Release("Jul 11 2019 23:20:34", 5, "11.0.4-redhat+11-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.3-redhat+7-LTS", new Release("Apr 10 2019 15:05:25", 4, "11.0.3-redhat+7-LTS"));
        // Release 11.0.2.7-1 and 11.0.2.7-5 have the same version, just different build date/time
        JDK11_WINDOWS_ZIPS.put("11.0.2-redhat+7-LTS", new Release("Feb 27 2019 17:48:49", 3, "11.0.2-redhat+7-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.2-redhat+7-LTS", new Release("Jan 16 2019 17:49:21", 2, "11.0.2-redhat+7-LTS"));
        JDK11_WINDOWS_ZIPS.put("11.0.1-redhat+13-LTS", new Release("Oct 25 2018 09:40:01", 1, "11.0.1-redhat+13-LTS"));

        // RHEL8 amd64 OpenJDK17 rpm
        JDK17_RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        JDK17_RHEL8_X86_64_RPMS.put("LATEST", new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-3.el8_7.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_6.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_4.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_2.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_1.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-2.el8_6.x86_64",
                new Release("Oct 15 2022 01:52:55", 7, "17.0.5+8-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-2.el8_4.x86_64",
                new Release("Oct 15 2022 00:00:00", 7, "17.0.5+8-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-2.el8_6.x86_64",
                new Release("Sep 2 2022 02:21:07", 6, "17.0.4.1+1-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-1.el8_4.x86_64",
                new Release("Sep 2 2022 00:00:00", 6, "17.0.4.1+1-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.0.8-2.el8_6.x86_64",
                new Release("Jul 20 2022 13:03:41", 5, "17.0.4+8-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.3.0.7-2.el8_6.x86_64",
                new Release("Apr 28 2022 01:08:31", 4, "17.0.3+7-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.3.0.6-2.el8_5.x86_64",
                new Release("Apr 16 2022 03:42:17", 4, "17.0.3+6-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.2.0.8-4.el8_5.x86_64",
                new Release("Jan 17 2022 04:30:26", 3, "17.0.2+8-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.1.0.12-2.el8_5.x86_64",
                new Release("Oct 28 2021 01:59:13", 2, "17.0.1+12-LTS"));
        JDK17_RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.0.0.35-4.el8.x86_64",
                new Release("Sep 27 2021 00:00:00", 1, "17+35"));

        // RHEL9 amd64 OpenJDK17 rpm
        JDK17_RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        JDK17_RHEL9_X86_64_RPMS.put("LATEST", new Release("Jan 14 2023 00:00:00", 5, "17.0.6+10-LTS"));
        JDK17_RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-3.el9_1.x86_64",
                new Release("Jan 14 2023 00:00:00", 5, "17.0.6+10-LTS"));
        JDK17_RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el9_0.x86_64",
                new Release("Jan 14 2023 00:00:00", 5, "17.0.6+10-LTS"));
        JDK17_RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.5.0.8-2.el9_0.x86_64",
                new Release("Oct 15 2022 00:00:00", 4, "17.0.5+8-LTS"));
        JDK17_RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-2.el9_0.x86_64",
                new Release("Sep 2 2022 00:00:00", 3, "17.0.4.1+1-LTS"));
        JDK17_RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.4.0.8-2.el9_0.x86_64",
                new Release("Jul 20 2022 00:00:00", 2, "17.0.4+8-LTS"));
        JDK17_RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.3.0.7-1.el9_0.x86_64",
                new Release("Apr 27 2022 00:00:00", 1, "17.0.3+7-LTS"));

        // RHEL amd64 OpenJDK17 zip
        JDK17_RHEL_ZIPS = new HashMap<String, Release>();
        JDK17_RHEL_ZIPS.put("LATEST", new Release("Jan 14 2023 04:10:00", 7, "17.0.6+10-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.6+10-LTS", new Release("Jan 14 2023 04:10:00", 7, "17.0.6+10-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.5+8-LTS", new Release("Oct 15 2022 02:39:47", 6, "17.0.5+8-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.4.1+1-LTS", new Release("Aug 25 2022 06:03:13", 5, "17.0.4.1+1-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.4+8-LTS", new Release("Jul 19 2022 12:31:23", 4, "17.0.4+8-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.3+6-LTS", new Release("Apr 16 2022 14:38:17", 3, "17.0.3+6-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.2+8-LTS", new Release("Jan 18 2022 16:03:37", 2, "17.0.2+8-LTS"));
        JDK17_RHEL_ZIPS.put("17.0.1+12-LTS", new Release("Oct 29 2021 08:37:58", 1, "17.0.1+12-LTS"));

        // Windows amd64 OpenJDK17 zip
        JDK17_WINDOWS_ZIPS = new HashMap<String, Release>();
        JDK17_WINDOWS_ZIPS.put("LATEST", new Release("Jan 14 2023 00:00:00", 8, "17.0.6+10-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.6+10-LTS", new Release("Jan 14 2023 00:00:00", 8, "17.0.6+10-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.5+8-LTS", new Release("Oct 15 2022 00:00:00", 7, "17.0.5+8-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.4.1+1-LTS", new Release("Aug 25 2022 00:00:00", 6, "17.0.4.1+1-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.4+8-LTS", new Release("Jul 19 2022 00:00:00", 5, "17.0.4+8-LTS"));
        // 2 builds w/ the same release string
        JDK17_WINDOWS_ZIPS.put("17.0.3+6-LTS-2", new Release("Apr 27 2022 11:51:42", 4, "17.0.3+6-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.3+6-LTS-1", new Release("Apr 17 2022 12:11:44", 3, "17.0.3+6-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.2+8-LTS", new Release("Jan 18 2022 00:00:00", 2, "17.0.2+8-LTS"));
        JDK17_WINDOWS_ZIPS.put("17.0.1+12-LTS", new Release("Oct 29 2021 00:00:00", 1, "17.0.1+12-LTS"));
    ***REMOVED***

    /**
     * @param size
     *            The size in fromUnits.
     * @param fromUnits
     *            Current units.
     * @param toUnits
     *            Conversion units.
     * @return The size in toUnits.
     */
    public static long convertSize(final long size, char fromUnits, char toUnits) {
        if (fromUnits == toUnits) {
            return size;
        ***REMOVED*** else {
            if (!"bBkKmMgG".matches("^.*" + Character.toString(toUnits) + ".*$")) {
                throw new AssertionError("Unexpected toUnits value: " + toUnits);
            ***REMOVED***
            BigDecimal newSize = new BigDecimal(size);
            switch (fromUnits) {
            case 'b':
            case 'B':
                if (toUnits == 'k' || toUnits == 'K') {
                    newSize = newSize.divide(Constants.KILOBYTE);
                ***REMOVED*** else if (toUnits == 'm' || toUnits == 'M') {
                    newSize = newSize.divide(Constants.MEGABYTE);
                ***REMOVED*** else if (toUnits == 'g' || toUnits == 'G') {
                    newSize = newSize.divide(Constants.GIGABYTE);
                ***REMOVED***
                break;
            case 'k':
            case 'K':
                if (toUnits == 'b' || toUnits == 'B') {
                    newSize = newSize.multiply(Constants.KILOBYTE);
                ***REMOVED*** else if (toUnits == 'm' || toUnits == 'M') {
                    newSize = newSize.divide(Constants.KILOBYTE);
                ***REMOVED*** else if (toUnits == 'g' || toUnits == 'G') {
                    newSize = newSize.divide(Constants.MEGABYTE);
                ***REMOVED***
                break;
            case 'm':
            case 'M':
                if (toUnits == 'b' || toUnits == 'B') {
                    newSize = newSize.multiply(Constants.MEGABYTE);
                ***REMOVED*** else if (toUnits == 'k' || toUnits == 'K') {
                    newSize = newSize.multiply(Constants.KILOBYTE);
                ***REMOVED*** else if (toUnits == 'g' || toUnits == 'G') {
                    newSize = newSize.divide(Constants.MEGABYTE);
                ***REMOVED***
                break;
            case 'g':
            case 'G':
                if (toUnits == 'b' || toUnits == 'B') {
                    newSize = newSize.multiply(Constants.GIGABYTE);
                ***REMOVED*** else if (toUnits == 'k' || toUnits == 'K') {
                    newSize = newSize.multiply(Constants.MEGABYTE);
                ***REMOVED*** else if (toUnits == 'm' || toUnits == 'M') {
                    newSize = newSize.multiply(Constants.KILOBYTE);
                ***REMOVED***
                break;
            default:
                throw new AssertionError("Unexpected fromUnits value: " + fromUnits);
            ***REMOVED***
            newSize = newSize.setScale(0, RoundingMode.HALF_EVEN);
            return newSize.longValue();
        ***REMOVED***
    ***REMOVED***

    /**
     * Get the bytes of a JVM option that specifies a byte value. For example, the bytes for <code>128k</code> is 128 x
     * 1024 = 131,072.
     * 
     * @param optionValue
     *            The JVM option value.
     * @return The JVM option value in bytes, or <code>Long.MIN_VALUE</code> if the option does not exist
     */
    public static final long getByteOptionBytes(final String optionValue) {
        long bytes = Long.MIN_VALUE;
        if (optionValue != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(optionValue);
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                char toUnits = 'B';
                if (fromUnits == toUnits) {
                    bytes = value;
                ***REMOVED*** else {
                    bytes = JdkUtil.convertSize(value, fromUnits, toUnits);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return bytes;
    ***REMOVED***

    /**
     * Get the value of a JVM option that specifies a byte value. For example, the value for <code>-Xss128k</code> is
     * 128k. The value for <code>-XX:PermSize=128M</code> is 128M.
     * 
     * @param option
     *            The JVM option.
     * @return The JVM option value, or null if the option does not exist.
     */
    public static final String getByteOptionValue(final String option) {
        String value = null;
        if (option != null) {
            String regex = "^-[a-zA-Z:.]+={0,1***REMOVED***(" + JdkRegEx.OPTION_SIZE_BYTES + ")$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(option);
            if (matcher.find()) {
                value = matcher.group(1);
            ***REMOVED***
        ***REMOVED***
        return value;
    ***REMOVED***

    /**
     * @param jdkReleaseString
     *            The JDK release string. For example: "11.0.15+10-LTS".
     * @param releases
     *            The JDK releases for a given OS and distribution method (e.g. zip, rpm).
     * @return The first release that matches the build string, or null if none found.
     */
    public static final Release getFirstReleaseFromReleases(String jdkReleaseString,
            HashMap<String, Release> releases) {
        Release firstRelease = null;
        Iterator<Entry<String, Release>> i = releases.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, Release> entry = i.next();
            Release release = entry.getValue();
            if (release.getVersion().equals(jdkReleaseString)) {
                firstRelease = release;
                break;
            ***REMOVED***
        ***REMOVED***
        return firstRelease;
    ***REMOVED***

    /**
     * @param version
     *            The JDK version.
     * @return The Java specification as a release number that can be used for comparing release order.
     */
    public static final int getJavaSpecificationNumber(JavaSpecification version) {
        int javaSpecificationNumber = Integer.MIN_VALUE;
        if (version != JavaSpecification.UNKNOWN) {
            javaSpecificationNumber = Integer.parseInt(version.toString().substring(3));
        ***REMOVED***
        return javaSpecificationNumber;
    ***REMOVED***

    /**
     * @param jdk11ReleaseString
     *            The JDK11 release string (e.g. 11.0.9+11-LTS).
     * @return The JDK11 update number (e.g. 9).
     */
    public static final int getJdk11UpdateNumber(String jdk11ReleaseString) {
        int jdk11UpdateNumber = Integer.MIN_VALUE;
        String regEx = "11.0.(\\d{1,***REMOVED***).+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(jdk11ReleaseString);
        if (matcher.find()) {
            jdk11UpdateNumber = Integer.parseInt(matcher.group(1));
        ***REMOVED***
        return jdk11UpdateNumber;
    ***REMOVED***

    /**
     * @param jdk17ReleaseString
     *            The JDK17 release string (e.g. 17.0.4+8-LTS).
     * @return The JDK update number (e.g. 4).
     */
    public static final int getJdk17UpdateNumber(String jdk17ReleaseString) {
        int jdk17UpdateNumber = Integer.MIN_VALUE;
        String regEx = "17.0.(\\d{1,***REMOVED***).+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(jdk17ReleaseString);
        if (matcher.find()) {
            jdk17UpdateNumber = Integer.parseInt(matcher.group(1));
        ***REMOVED***
        return jdk17UpdateNumber;
    ***REMOVED***

    /**
     * @param jdk8ReleaseString
     *            The JDK8 release string (e.g. 1.8.0_222-b10).
     * @return The JDK8 update number (e.g. 222).
     */
    public static final int getJdk8UpdateNumber(String jdk8ReleaseString) {
        int jdk8UpdateNumber = Integer.MIN_VALUE;
        String regEx = "(1.)?8.0_(\\d{1,***REMOVED***).+";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(jdk8ReleaseString);
        if (matcher.find()) {
            jdk8UpdateNumber = Integer.parseInt(matcher.group(2));
        ***REMOVED***
        return jdk8UpdateNumber;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return The known release date for the JDK build that produced the fatal error log.
     */
    public static final Date getJdkReleaseDate(FatalErrorLog fatalErrorLog) {
        Date date = null;
        if (fatalErrorLog != null) {
            Release release = null;
            HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
            if (releases != null && !releases.isEmpty()) {
                if (fatalErrorLog.isRhRpmInstall()) {
                    release = releases.get(fatalErrorLog.getRpmDirectory());
                ***REMOVED*** else if (fatalErrorLog.isRhLinuxZipInstall() || fatalErrorLog.isRhWindowsZipInstall()) {
                    release = releases.get(fatalErrorLog.getJdkReleaseString());
                ***REMOVED***
            ***REMOVED*** else {
                // Approximate release
                release = fatalErrorLog.getFirstRelease(fatalErrorLog.getJdkReleaseString());
            ***REMOVED***
            if (release != null) {
                date = release.getBuildDate();
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
            if (releases != null && !releases.isEmpty()) {
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
                        releases = JDK8_RHEL6_X86_64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7 && fatalErrorLog.getArch() == Arch.X86_64
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = JDK8_RHEL7_X86_64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7 && fatalErrorLog.getArch() == Arch.PPC64
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = JDK8_RHEL7_PPC64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7
                            && fatalErrorLog.getArch() == Arch.PPC64LE
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = JDK8_RHEL7_PPC64LE_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL7
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK11) {
                        releases = JDK11_RHEL7_X86_64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = JDK8_RHEL8_X86_64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL9
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK8) {
                        releases = JDK8_RHEL9_X86_64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK11) {
                        releases = JDK11_RHEL8_X86_64_RPMS;
                    ***REMOVED*** else if (fatalErrorLog.getOsVersion() == OsVersion.RHEL8
                            && fatalErrorLog.getJavaSpecification() == JavaSpecification.JDK17) {
                        releases = JDK17_RHEL8_X86_64_RPMS;
                    ***REMOVED***
                ***REMOVED*** else if (fatalErrorLog.isRhLinuxZipInstall()) {
                    switch (fatalErrorLog.getJavaSpecification()) {
                    case JDK8:
                        releases = JDK8_RHEL_ZIPS;
                        break;
                    case JDK11:
                        releases = JDK11_RHEL_ZIPS;
                        break;
                    case JDK17:
                        releases = JDK17_RHEL_ZIPS;
                        break;
                    case UNKNOWN:
                    default:
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED*** else if (fatalErrorLog.isRhWindowsZipInstall()) {
                switch (fatalErrorLog.getJavaSpecification()) {
                case JDK8:
                    releases = JDK8_WINDOWS_ZIPS;
                    break;
                case JDK11:
                    releases = JDK11_WINDOWS_ZIPS;
                    break;
                case JDK17:
                    releases = JDK17_WINDOWS_ZIPS;
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
        if (releases != null && releases.get("LATEST") != null) {
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
        if (releases != null && !releases.isEmpty()) {
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
        if (releases != null && !releases.isEmpty()) {
            release = releases.get("LATEST").getVersion();
        ***REMOVED***
        return release;
    ***REMOVED***

    /**
     * Get the value of a JVM option that specifies a number value.
     * 
     * For example:
     * <ul>
     * <li>The value for <code>-XX:MaxTenuringThreshold=9</code> is 9.</li>
     * <li>The value for <code>-Dsun.rmi.dgc.client.gcInterval=3600000</code> is 3600000.</li>
     * </ul>
     * 
     * @param option
     *            The JVM option or system property.
     * @return The JVM option or system property value, or <code>Integer.MIN_VALUE</code> if the option does not exist.
     */
    public static final long getNumberOptionValue(final String option) {
        long value = Long.MIN_VALUE;
        if (option != null) {
            String regex = "^.+=(\\d{1,19***REMOVED***)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(option);
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(1));
            ***REMOVED***
        ***REMOVED***
        return value;
    ***REMOVED***

    /**
     * Identify the log line fatal error log event.
     * 
     * @param logLine
     *            The log entry.
     * @param priorEvent
     *            The previous log line event.
     * @return The <code>LogEventType</code> of the log entry.
     */
    public static final LogEventType identifyEventType(String logLine, LogEvent priorEvent) {
        LogEventType logEventType = LogEventType.UNKNOWN;
        if (BitsEvent.match(logLine)) {
            logEventType = LogEventType.BITS;
        ***REMOVED*** else if (BlankLine.match(logLine)) {
            logEventType = LogEventType.BLANK_LINE;
        ***REMOVED*** else if (CardTable.match(logLine)) {
            logEventType = LogEventType.CARD_TABLE;
        ***REMOVED*** else if (CdsArchive.match(logLine)) {
            logEventType = LogEventType.CDS_ARCHIVE;
        ***REMOVED*** else if (ClassesRedefinedEvent.match(logLine) && (logLine.matches(ClassesRedefinedEvent._REGEX_HEADER)
                || priorEvent instanceof ClassesRedefinedEvent)) {
            logEventType = LogEventType.CLASSES_REDEFINED_EVENT;
        ***REMOVED*** else if (ClassesUnloadedEvent.match(logLine) && (logLine.matches(ClassesUnloadedEvent._REGEX_HEADER)
                || priorEvent instanceof ClassesUnloadedEvent)) {
            logEventType = LogEventType.CLASSES_UNLOADED_EVENT;
        ***REMOVED*** else if (CodeCache.match(logLine)) {
            logEventType = LogEventType.CODE_CACHE;
        ***REMOVED*** else if (CommandLine.match(logLine)) {
            logEventType = LogEventType.COMMAND_LINE;
        ***REMOVED*** else if (CompilationEvent.match(logLine)
                && (logLine.matches(CompilationEvent._REGEX_HEADER) || priorEvent instanceof CompilationEvent)) {
            logEventType = LogEventType.COMPILATION_EVENT;
        ***REMOVED*** else if (CompressedClassSpace.match(logLine)) {
            logEventType = LogEventType.COMPRESSED_CLASS_SPACE;
        ***REMOVED*** else if (ContainerInfo.match(logLine)) {
            logEventType = LogEventType.CONTAINER_INFO;
        ***REMOVED*** else if (CpuInfo.match(logLine)
                && (logLine.matches(CpuInfo._REGEX_HEADER) || priorEvent instanceof CpuInfo)) {
            logEventType = LogEventType.CPU_INFO;
        ***REMOVED*** else if (CurrentCompileTask.match(logLine)) {
            logEventType = LogEventType.CURRENT_COMPILE_TASK;
        ***REMOVED*** else if (CurrentThread.match(logLine)) {
            logEventType = LogEventType.CURRENT_THREAD;
        ***REMOVED*** else if (DeoptimizationEvent.match(logLine)
                && (logLine.matches(DeoptimizationEvent._REGEX_HEADER) || priorEvent instanceof DeoptimizationEvent)) {
            logEventType = LogEventType.DEOPTIMIZATION_EVENT;
        ***REMOVED*** else if (DllOperationEvent.match(logLine)
                && (logLine.matches(DllOperationEvent._REGEX_HEADER) || priorEvent instanceof DllOperationEvent)) {
            logEventType = LogEventType.DLL_OPERATION_EVENT;
        ***REMOVED*** else if (DynamicLibrary.match(logLine)) {
            logEventType = LogEventType.DYNAMIC_LIBRARY;
        ***REMOVED*** else if (ElapsedTime.match(logLine)) {
            logEventType = LogEventType.ELAPSED_TIME;
        ***REMOVED*** else if (End.match(logLine)) {
            logEventType = LogEventType.END;
        ***REMOVED*** else if (EnvironmentVariables.match(logLine)) {
            logEventType = LogEventType.ENVIRONMENT_VARIABLES;
        ***REMOVED*** else if (Event.match(logLine) && (logLine.matches(Event._REGEX_HEADER) || priorEvent instanceof Event)) {
            logEventType = LogEventType.EVENT;
        ***REMOVED*** else if (ExceptionCounts.match(logLine)) {
            logEventType = LogEventType.EXCEPTION_COUNTS;
        ***REMOVED*** else if (GcHeapHistoryEvent.match(logLine)
                && (logLine.matches(GcHeapHistoryEvent._REGEX_HEADER) || priorEvent instanceof GcHeapHistoryEvent)) {
            logEventType = LogEventType.GC_HEAP_HISTORY_EVENT;
        ***REMOVED*** else if (GcPreciousLog.match(logLine)) {
            logEventType = LogEventType.GC_PRECIOUS_LOG;
        ***REMOVED*** else if (GlobalFlags.match(logLine)) {
            logEventType = LogEventType.GLOBAL_FLAGS;
        ***REMOVED*** else if (Header.match(logLine)
                && (priorEvent == null || priorEvent instanceof UnknownEvent || priorEvent instanceof Header)) {
            logEventType = LogEventType.HEADER;
        ***REMOVED*** else if (Heading.match(logLine)) {
            logEventType = LogEventType.HEADING;
        ***REMOVED*** else if (Heap.match(logLine) && (logLine.matches(Heap._REGEX_HEADER) || priorEvent instanceof Heap)) {
            logEventType = LogEventType.HEAP;
        ***REMOVED*** else if (HeapAddress.match(logLine)) {
            logEventType = LogEventType.HEAP_ADDRESS;
        ***REMOVED*** else if (HeapRegions.match(logLine)) {
            logEventType = LogEventType.HEAP_REGIONS;
        ***REMOVED*** else if (Host.match(logLine)) {
            logEventType = LogEventType.HOST;
        ***REMOVED*** else if (Instructions.match(logLine)) {
            logEventType = LogEventType.INSTRUCTIONS;
        ***REMOVED*** else if (InternalExceptionEvent.match(logLine) && (logLine.matches(InternalExceptionEvent._REGEX_HEADER)
                || priorEvent instanceof InternalExceptionEvent)) {
            logEventType = LogEventType.INTERNAL_EXCEPTION_EVENT;
        ***REMOVED*** else if (InternalStatistic.match(logLine)
                && (logLine.matches(InternalStatistic._REGEX_HEADER) || priorEvent instanceof InternalStatistic)) {
            logEventType = LogEventType.INTERNAL_STATISTICS;
        ***REMOVED*** else if (LdPreloadFile.match(logLine)) {
            logEventType = LogEventType.LD_PRELOAD_FILE;
        ***REMOVED*** else if (Libc.match(logLine)) {
            logEventType = LogEventType.LIBC;
        ***REMOVED*** else if (LoadAverage.match(logLine)) {
            logEventType = LogEventType.LOAD_AVERAGE;
        ***REMOVED*** else if (Logging.match(logLine)) {
            logEventType = LogEventType.LOGGING;
        ***REMOVED*** else if (MaxMapCount.match(logLine)) {
            logEventType = LogEventType.MAX_MAP_COUNT;
        ***REMOVED*** else if (Meminfo.match(logLine)
                && (logLine.matches(Meminfo._REGEX_HEADER) || priorEvent instanceof Meminfo)) {
            logEventType = LogEventType.MEMINFO;
        ***REMOVED*** else if (Memory.match(logLine) && (logLine.matches(Memory._REGEX_HEADER) || priorEvent instanceof Memory)) {
            logEventType = LogEventType.MEMORY;
        ***REMOVED*** else if (Metaspace.match(logLine)) {
            logEventType = LogEventType.METASPACE;
        ***REMOVED*** else if (NarrowKlass.match(logLine)) {
            logEventType = LogEventType.NARROW_KLASS;
        ***REMOVED*** else if (NativeMemoryTracking.match(logLine)) {
            logEventType = LogEventType.NATIVE_MEMORY_TRACKING;
        ***REMOVED*** else if (NumberEvent.match(logLine)) {
            logEventType = LogEventType.NUMBER;
        ***REMOVED*** else if (OsInfo.match(logLine)) {
            logEventType = LogEventType.OS_INFO;
        ***REMOVED*** else if (OsUptime.match(logLine)) {
            logEventType = LogEventType.OS_UPTIME;
        ***REMOVED*** else if (PidMax.match(logLine)) {
            logEventType = LogEventType.PID_MAX;
        ***REMOVED*** else if (PollingPage.match(logLine)) {
            logEventType = LogEventType.POLLING_PAGE;
        ***REMOVED*** else if (ProcessMemory.match(logLine)) {
            logEventType = LogEventType.PROCESS_MEMORY;
        ***REMOVED*** else if (Register.match(logLine)) {
            logEventType = LogEventType.REGISTER;
        ***REMOVED*** else if (RegisterToMemoryMapping.match(logLine) || (priorEvent instanceof RegisterToMemoryMapping
                && (logLine.matches(JdkRegEx.CLASS) || logLine.matches(JdkRegEx.MEMORY_MAPPING)))) {
            logEventType = LogEventType.REGISTER_TO_MEMORY_MAPPING;
        ***REMOVED*** else if (RlimitEvent.match(logLine)) {
            logEventType = LogEventType.RLIMIT;
        ***REMOVED*** else if (SigInfo.match(logLine)) {
            logEventType = LogEventType.SIGINFO;
        ***REMOVED*** else if (SignalHandlers.match(logLine)) {
            logEventType = LogEventType.SIGNAL_HANDLERS;
        ***REMOVED*** else if (Stack.match(logLine)) {
            logEventType = LogEventType.STACK;
        ***REMOVED*** else if (StackSlotToMemoryMapping.match(logLine)
                || (priorEvent instanceof StackSlotToMemoryMapping) && logLine.matches(JdkRegEx.MEMORY_MAPPING)) {
            logEventType = LogEventType.STACK_SLOT_TO_MEMORY_MAPPING;
        ***REMOVED*** else if (Thread.match(logLine)) {
            logEventType = LogEventType.THREAD;
        ***REMOVED*** else if (ThreadsActiveCompile.match(logLine)) {
            logEventType = LogEventType.THREADS_ACTIVE_COMPILE;
        ***REMOVED*** else if (ThreadsClassSmrInfo.match(logLine)) {
            logEventType = LogEventType.THREADS_CLASS_SMR_INFO;
        ***REMOVED*** else if (ThreadsMax.match(logLine)) {
            logEventType = LogEventType.THREADS_MAX;
        ***REMOVED*** else if (Time.match(logLine)) {
            logEventType = LogEventType.TIME;
        ***REMOVED*** else if (TimeElapsedTime.match(logLine)) {
            logEventType = LogEventType.TIME_ELAPSED_TIME;
        ***REMOVED*** else if (Timezone.match(logLine)) {
            logEventType = LogEventType.TIMEZONE;
        ***REMOVED*** else if (TopOfStack.match(logLine)) {
            logEventType = LogEventType.TOP_OF_STACK;
        ***REMOVED*** else if (TransparentHugepage.match(logLine)) {
            logEventType = LogEventType.TRANSPARENT_HUGEPAGE;
        ***REMOVED*** else if (Uid.match(logLine)) {
            logEventType = LogEventType.UID;
        ***REMOVED*** else if (Umask.match(logLine)) {
            logEventType = LogEventType.UMASK;
        ***REMOVED*** else if (Uname.match(logLine)) {
            logEventType = LogEventType.UNAME;
        ***REMOVED*** else if (VmArguments.match(logLine)) {
            logEventType = LogEventType.VM_ARGUMENTS;
        ***REMOVED*** else if (VmInfo.match(logLine)) {
            logEventType = LogEventType.VM_INFO;
        ***REMOVED*** else if (VmMutex.match(logLine)) {
            logEventType = LogEventType.VM_MUTEX;
        ***REMOVED*** else if (VmOperation.match(logLine)) {
            logEventType = LogEventType.VM_OPERATION;
        ***REMOVED*** else if (VmOperationEvent.match(logLine)
                && (logLine.matches(VmOperationEvent._REGEX_HEADER) || priorEvent instanceof VmOperationEvent)) {
            logEventType = LogEventType.VM_OPERATION_EVENT;
        ***REMOVED*** else if (VmState.match(logLine)) {
            logEventType = LogEventType.VM_STATE;
        ***REMOVED*** else if (VirtualizationInfo.match(logLine)) {
            logEventType = LogEventType.VIRTUALIZATION_INFO;
        ***REMOVED***
        return logEventType;
    ***REMOVED***

    /**
     * Determine if a build date is a known date/time or an estimate. Estimate have 0 for hh:mm:ss.
     * 
     * The following build date/time is a known date/time:
     * 
     * Apr 19 2022 00:14:41
     * 
     * The following build date/time is an estimate:
     * 
     * Apr 19 2022 00:00:00
     * 
     * @param buildDate
     *            The JDK build date/time, or an estimate if unknown.
     * 
     * @return true if the JDK build date is known, false otherwise.
     */
    public static final boolean isBuildDateKnown(Date buildDate) {
        boolean isBuildDateKnown = false;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(buildDate);
        if (!(calendar.get(Calendar.HOUR) == 0 && calendar.get(Calendar.MINUTE) == 0
                && calendar.get(Calendar.SECOND) == 0)) {
            isBuildDateKnown = true;
        ***REMOVED***
        return isBuildDateKnown;
    ***REMOVED***

    /**
     * @param fatalErrorLog
     *            The fatal error log.
     * @return true if the JDK that produced the fatal error log is the latest release, false otherwise.
     */
    public static final boolean isLatestJdkRelease(FatalErrorLog fatalErrorLog) {
        boolean isLatestRelease = true;
        HashMap<String, Release> releases = getJdkReleases(fatalErrorLog);
        if (releases != null && !releases.isEmpty()) {
            Release latest = releases.get("LATEST");
            if (latest != null && latest.getVersion() != null
                    && !latest.getVersion().equals(fatalErrorLog.getJdkReleaseString())) {
                isLatestRelease = false;
            ***REMOVED***
        ***REMOVED***
        return isLatestRelease;
    ***REMOVED***

    /**
     * Determine if a JVM option is explicitly disabled. For example, <code>-XX:-TraceClassUnloading</code> is disabled.
     * 
     * @param option
     *            The JVM option.
     * @return True if the JVM option is disabled, false otherwise.
     */
    public static final boolean isOptionDisabled(final String option) {
        boolean disabled = false;
        if (option != null) {
            disabled = option.matches("^-XX:-.+$");
        ***REMOVED***
        return disabled;
    ***REMOVED***

    /**
     * Determine if a JVM option is explicitly enabled. For example, <code>-XX:+TraceClassUnloading</code> is enabled.
     * 
     * @param option
     *            The JVM option.
     * @return True if the JVM option is enabled, false otherwise.
     */
    public static final boolean isOptionEnabled(final String option) {
        boolean enabled = false;
        if (option != null) {
            enabled = option.matches("^-XX:\\+.+$");
        ***REMOVED***
        return enabled;
    ***REMOVED***

    /**
     * @param jdkReleaseString
     *            The JDK release string. For example: "11.0.15+10-LTS".
     * @param releases
     *            The JDK releases for a given OS and distribution method (e.g. zip, rpm).
     * @return True if the release string exists in the releases, false otherwise.
     */
    public static final boolean isReleaseStringInReleases(String jdkReleaseString, HashMap<String, Release> releases) {
        boolean containsJdkReleaseString = false;
        Iterator<Entry<String, Release>> i = releases.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, Release> entry = i.next();
            Release release = entry.getValue();
            if (release.getVersion().equals(jdkReleaseString)) {
                containsJdkReleaseString = true;
                break;
            ***REMOVED***
        ***REMOVED***
        return containsJdkReleaseString;
    ***REMOVED***

    /**
     * Create <code>LogEvent</code> from VM log line.
     * 
     * @param logLine
     *            The log line as it appears in the VM log.
     * @param priorEvent
     *            The previous log line event.
     * @return The <code>LogEvent</code> corresponding to the log line.
     */
    public static final LogEvent parseLogLine(String logLine, LogEvent priorEvent) {
        LogEventType eventType = identifyEventType(logLine, priorEvent);
        LogEvent event = null;
        switch (eventType) {
        case BITS:
            event = new BitsEvent(logLine);
            break;
        case BLANK_LINE:
            event = new BlankLine(logLine);
            break;
        case CARD_TABLE:
            event = new CardTable(logLine);
            break;
        case CDS_ARCHIVE:
            event = new CdsArchive(logLine);
            break;
        case CLASSES_REDEFINED_EVENT:
            event = new ClassesRedefinedEvent(logLine);
            break;
        case CLASSES_UNLOADED_EVENT:
            event = new ClassesUnloadedEvent(logLine);
            break;
        case CODE_CACHE:
            event = new CodeCache(logLine);
            break;
        case COMMAND_LINE:
            event = new CommandLine(logLine);
            break;
        case COMPRESSED_CLASS_SPACE:
            event = new CompressedClassSpace(logLine);
            break;
        case COMPILATION_EVENT:
            event = new CompilationEvent(logLine);
            break;
        case CONTAINER_INFO:
            event = new ContainerInfo(logLine);
            break;
        case CPU_INFO:
            event = new CpuInfo(logLine);
            break;
        case CURRENT_COMPILE_TASK:
            event = new CurrentCompileTask(logLine);
            break;
        case CURRENT_THREAD:
            event = new CurrentThread(logLine);
            break;
        case DEOPTIMIZATION_EVENT:
            event = new DeoptimizationEvent(logLine);
            break;
        case DLL_OPERATION_EVENT:
            event = new DllOperationEvent(logLine);
            break;
        case DYNAMIC_LIBRARY:
            event = new DynamicLibrary(logLine);
            break;
        case ELAPSED_TIME:
            event = new ElapsedTime(logLine);
            break;
        case END:
            event = new End(logLine);
            break;
        case EVENT:
            event = new Event(logLine);
            break;
        case ENVIRONMENT_VARIABLES:
            event = new EnvironmentVariables(logLine);
            break;
        case EXCEPTION_COUNTS:
            event = new ExceptionCounts(logLine);
            break;
        case GC_HEAP_HISTORY_EVENT:
            event = new GcHeapHistoryEvent(logLine);
            break;
        case GC_PRECIOUS_LOG:
            event = new GcPreciousLog(logLine);
            break;
        case GLOBAL_FLAGS:
            event = new GlobalFlags(logLine);
            break;
        case HEADER:
            event = new Header(logLine);
            break;
        case HEADING:
            event = new Heading(logLine);
            break;
        case HEAP:
            event = new Heap(logLine);
            break;
        case HEAP_ADDRESS:
            event = new HeapAddress(logLine);
            break;
        case HEAP_REGIONS:
            event = new HeapRegions(logLine);
            break;
        case HOST:
            event = new Host(logLine);
            break;
        case INSTRUCTIONS:
            event = new Instructions(logLine);
            break;
        case INTERNAL_EXCEPTION_EVENT:
            event = new InternalExceptionEvent(logLine);
            break;
        case INTERNAL_STATISTICS:
            event = new InternalStatistic(logLine);
            break;
        case LD_PRELOAD_FILE:
            event = new LdPreloadFile(logLine);
            break;
        case LIBC:
            event = new Libc(logLine);
            break;
        case LOAD_AVERAGE:
            event = new LoadAverage(logLine);
            break;
        case LOGGING:
            event = new Logging(logLine);
            break;
        case MAX_MAP_COUNT:
            event = new MaxMapCount(logLine);
            break;
        case MEMINFO:
            event = new Meminfo(logLine);
            break;
        case MEMORY:
            event = new Memory(logLine);
            break;
        case METASPACE:
            event = new Metaspace(logLine);
            break;
        case NARROW_KLASS:
            event = new NarrowKlass(logLine);
            break;
        case NATIVE_MEMORY_TRACKING:
            event = new NativeMemoryTracking(logLine);
            break;
        case NUMBER:
            event = new NumberEvent(logLine);
            break;
        case OS_INFO:
            event = new OsInfo(logLine);
            break;
        case OS_UPTIME:
            event = new OsUptime(logLine);
            break;
        case PID_MAX:
            event = new PidMax(logLine);
            break;
        case POLLING_PAGE:
            event = new PollingPage(logLine);
            break;
        case PROCESS_MEMORY:
            event = new ProcessMemory(logLine);
            break;
        case REGISTER:
            event = new Register(logLine);
            break;
        case REGISTER_TO_MEMORY_MAPPING:
            event = new RegisterToMemoryMapping(logLine);
            break;
        case RLIMIT:
            event = new RlimitEvent(logLine);
            break;
        case SIGINFO:
            event = new SigInfo(logLine);
            break;
        case SIGNAL_HANDLERS:
            event = new SignalHandlers(logLine);
            break;
        case STACK:
            event = new Stack(logLine);
            break;
        case STACK_SLOT_TO_MEMORY_MAPPING:
            event = new StackSlotToMemoryMapping(logLine);
            break;
        case THREAD:
            event = new Thread(logLine);
            break;
        case THREADS_ACTIVE_COMPILE:
            event = new ThreadsActiveCompile(logLine);
            break;
        case THREADS_CLASS_SMR_INFO:
            event = new ThreadsClassSmrInfo(logLine);
            break;
        case THREADS_MAX:
            event = new ThreadsMax(logLine);
            break;
        case TIME:
            event = new Time(logLine);
            break;
        case TIME_ELAPSED_TIME:
            event = new TimeElapsedTime(logLine);
            break;
        case TIMEZONE:
            event = new Timezone(logLine);
            break;
        case TOP_OF_STACK:
            event = new TopOfStack(logLine);
            break;
        case TRANSPARENT_HUGEPAGE:
            event = new TransparentHugepage(logLine);
            break;
        case UID:
            event = new Uid(logLine);
            break;
        case UMASK:
            event = new Umask(logLine);
            break;
        case UNAME:
            event = new Uname(logLine);
            break;
        case UNKNOWN:
            event = new UnknownEvent(logLine);
            break;
        case VM_ARGUMENTS:
            event = new VmArguments(logLine);
            break;
        case VM_OPERATION_EVENT:
            event = new VmOperationEvent(logLine);
            break;
        case VM_INFO:
            event = new VmInfo(logLine);
            break;
        case VM_MUTEX:
            event = new VmMutex(logLine);
            break;
        case VM_OPERATION:
            event = new VmOperation(logLine);
            break;
        case VM_STATE:
            event = new VmState(logLine);
            break;
        case VIRTUALIZATION_INFO:
            event = new VirtualizationInfo(logLine);
            break;
        default:
            throw new AssertionError("Unexpected event type value: " + eventType);
        ***REMOVED***
        return event;
    ***REMOVED***

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private JdkUtil() {

    ***REMOVED***
***REMOVED***

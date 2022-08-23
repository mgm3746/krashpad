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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.util.Constants;
import org.github.krashpad.util.Constants.CpuArch;
import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.Constants.OsType;
import org.github.krashpad.util.Constants.OsVendor;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.Application;
import org.github.krashpad.util.jdk.JdkUtil.Arch;
import org.github.krashpad.util.jdk.JdkUtil.BuiltBy;
import org.github.krashpad.util.jdk.JdkUtil.CompressedOopMode;
import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;
import org.github.krashpad.util.jdk.JdkUtil.JavaVendor;
import org.github.krashpad.util.jdk.JdkUtil.SignalCode;
import org.github.krashpad.util.jdk.JdkUtil.SignalNumber;

/**
 * Fatal error log data.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class FatalErrorLog {

    /**
     * Analysis.
     */
    private List<Analysis> analysis;

    /**
     * Command line information.
     */
    private CommandLineEvent commandLineEvent;

    /**
     * Compilation event information.
     */
    private List<CompilationEvent> compilationEvents;

    /**
     * Compressed class space information
     */
    private CompressedClassSpaceEvent compressedClassSpaceEvent;

    /**
     * Container information.
     */
    private List<ContainerInfoEvent> containerInfoEvents;

    /**
     * CPU information.
     */
    private List<CpuInfoEvent> cpuInfoEvents;

    /**
     * Current compile task information.
     */
    private List<CurrentCompileTaskEvent> currentCompileTaskEvents;

    /**
     * Current thread information.
     */
    private CurrentThreadEvent currentThreadEvent;

    /**
     * Deoptimization event information.
     */
    private List<DeoptimizationEvent> deoptimizationEvents;

    /**
     * Dynamic library information.
     */
    private List<DynamicLibraryEvent> dynamicLibraryEvents;

    /**
     * JVM run duration information in JDK8.
     */
    private ElapsedTimeEvent elapsedTimeEvent;

    /**
     * Environment variables information.
     */
    private List<EnvironmentVariablesEvent> environmentVariablesEvents;

    /**
     * Vm event information.
     */
    private List<EventEvent> eventEvents;

    /**
     * Exception counts information.
     */
    private List<ExceptionCountsEvent> exceptionCountsEvents;

    /**
     * GC heap history information.
     */
    private List<GcHeapHistoryEvent> gcHeapHistoryEvents;

    /**
     * GC precious log information.
     */
    private List<GcPreciousLogEvent> gcPreciousLogEvents;

    /**
     * Global flag information.
     */
    private List<GlobalFlagsEvent> globalFlagsEvents;

    /**
     * Header.
     */
    private List<HeaderEvent> headerEvents;

    /**
     * Heap address information
     */
    private HeapAddressEvent heapAddressEvent;

    /**
     * Heap information.
     */
    private List<HeapEvent> heapEvents;

    /**
     * Host information.
     */
    private HostEvent hostEvent;

    /**
     * JVM options convenience field
     */
    private JvmOptions jvmOptions;

    /**
     * max_map_count information.
     */
    private MaxMapCountEvent maxMapCountEvent;

    /**
     * Memory information.
     */
    private List<MeminfoEvent> meminfoEvents;

    /**
     * Memory information.
     */
    private List<MemoryEvent> memoryEvents;

    /**
     * Narrow klass information
     */
    private NarrowKlassEvent narrowKlassEvent;

    /**
     * Native memory tracking information.
     */
    private List<NativeMemoryTrackingEvent> nativeMemoryTrackingEvents;

    /**
     * OS information.
     */
    private List<OsEvent> osEvents;

    /**
     * pid_max information.
     */
    private PidMaxEvent pidMaxEvent;

    /**
     * rlimit information.
     */
    private RlimitEvent rlimitEvent;

    /**
     * Signal information.
     */
    private SigInfoEvent sigInfoEvent;

    /**
     * Stack information.
     */
    private List<StackEvent> stackEvents;

    /**
     * Statistics information.
     */
    private List<StatisticsEvent> statisticsEvents;

    /**
     * Thread information.
     */
    private List<ThreadEvent> threadEvents;

    /**
     * threads-max information.
     */
    private ThreadsMaxEvent threadsMaxEvent;

    /**
     * Combined time + elapsed time information.
     */
    private TimeElapsedTimeEvent timeElapsedTimeEvent;

    /**
     * JVM crash time information.
     */
    private TimeEvent timeEvent;

    /**
     * JVM crash time timezone information in JDK8.
     */
    private TimezoneEvent timezoneEvent;

    /**
     * uname information.
     */
    private UnameEvent unameEvent;

    /**
     * Log lines that do not match any existing logging patterns.
     */
    private List<String> unidentifiedLogLines;

    /**
     * VMware information.
     */
    private List<VirtualizationInfoEvent> virtualizationInfoEvents;

    /**
     * VM arguments information.
     */
    private List<VmArgumentsEvent> vmArgumentsEvents;

    /**
     * JVM environment information.
     */
    private VmInfoEvent vmInfoEvent;

    /**
     * VM operation information.
     */
    private VmOperationEvent vmOperationEvent;

    /**
     * VM state information.
     */
    private VmStateEvent vmStateEvent;

    /*
     * Default constructor.
     */
    public FatalErrorLog() {
        analysis = new ArrayList<Analysis>();
        compilationEvents = new ArrayList<CompilationEvent>();
        containerInfoEvents = new ArrayList<ContainerInfoEvent>();
        currentCompileTaskEvents = new ArrayList<CurrentCompileTaskEvent>();
        cpuInfoEvents = new ArrayList<CpuInfoEvent>();
        deoptimizationEvents = new ArrayList<DeoptimizationEvent>();
        dynamicLibraryEvents = new ArrayList<DynamicLibraryEvent>();
        environmentVariablesEvents = new ArrayList<EnvironmentVariablesEvent>();
        eventEvents = new ArrayList<EventEvent>();
        exceptionCountsEvents = new ArrayList<ExceptionCountsEvent>();
        gcHeapHistoryEvents = new ArrayList<GcHeapHistoryEvent>();
        gcPreciousLogEvents = new ArrayList<GcPreciousLogEvent>();
        globalFlagsEvents = new ArrayList<GlobalFlagsEvent>();
        headerEvents = new ArrayList<HeaderEvent>();
        heapEvents = new ArrayList<HeapEvent>();
        statisticsEvents = new ArrayList<StatisticsEvent>();
        meminfoEvents = new ArrayList<MeminfoEvent>();
        memoryEvents = new ArrayList<MemoryEvent>();
        nativeMemoryTrackingEvents = new ArrayList<NativeMemoryTrackingEvent>();
        osEvents = new ArrayList<OsEvent>();
        stackEvents = new ArrayList<StackEvent>();
        threadEvents = new ArrayList<ThreadEvent>();
        unidentifiedLogLines = new ArrayList<String>();
        vmArgumentsEvents = new ArrayList<VmArgumentsEvent>();
        virtualizationInfoEvents = new ArrayList<VirtualizationInfoEvent>();
    ***REMOVED***

    /**
     * Do analysis.
     */
    public void doAnalysis() {
        // Unidentified logging lines
        if (JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) >= 8 && !getUnidentifiedLogLines().isEmpty()) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE);
        ***REMOVED***
        String jvmArgs = getJvmArgs();
        if (jvmArgs != null) {
            jvmOptions = new JvmOptions(jvmArgs);
        ***REMOVED***
        doDataAnalysis();
        if (jvmOptions != null) {
            jvmOptions.doAnalysis(analysis, getJavaSpecification());
        ***REMOVED***
    ***REMOVED***

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // Check for ancient fatal error log
        if (ErrUtil.dayDiff(getCrashDate(), new Date()) > 30) {
            analysis.add(Analysis.WARN_FATAL_ERROR_LOG_ANCIENT);
        ***REMOVED***
        // Check for ancient JDK
        if (ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(this), JdkUtil.getLatestJdkReleaseDate(this)) > 365
                || ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(this), new Date()) > 365) {
            analysis.add(Analysis.INFO_JDK_ANCIENT);
        ***REMOVED***
        // Check for unknown JDK version
        if (getJavaSpecification() == JavaSpecification.UNKNOWN) {
            analysis.add(Analysis.ERROR_JDK_VERSION_UNKNOWN);
        ***REMOVED***
        // Check for unsupported JDK version
        if (getJavaSpecification() == JavaSpecification.JDK6 || getJavaSpecification() == JavaSpecification.JDK7) {
            analysis.add(Analysis.ERROR_JDK_VERSION_UNSUPPORTED);
        ***REMOVED***
        // Check for JVM failing to start
        if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
            analysis.add(Analysis.INFO_JVM_STARTUP_FAILS);
        ***REMOVED***
        // Check if JDK debugging symbols are installed
        if ((haveVmFrameInStack() || haveVmFrameInHeader()) && !haveJdkDebugSymbols()) {
            analysis.add(Analysis.WARN_DEBUG_SYMBOLS);
        ***REMOVED***
        // Check if latest JDK release
        if (!JdkUtil.isLatestJdkRelease(this)) {
            analysis.add(0, Analysis.WARN_JDK_NOT_LATEST);
        ***REMOVED***
        // Identify vendor/build
        if (isRhBuildOpenJdk()) {
            if (getOsType() == OsType.LINUX) {
                if (getOsVendor() == OsVendor.CENTOS) {
                    // CentOs redistributes RH build of OpenJDK
                    analysis.add(0, Analysis.INFO_RH_BUILD_CENTOS);
                ***REMOVED*** else if (getRpmDirectory() != null) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_RPM_INSTALL);
                    if (getCpuArch() == CpuArch.POWER9 && getJavaSpecification() == JavaSpecification.JDK8
                            && getOsString().matches(".+7\\.(7|8|9).+")) {
                        // power8 JDK8 deployed on power9 on RHEL 7
                        analysis.add(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9);
                    ***REMOVED***
                ***REMOVED*** else if (isRhRpm()) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_RPM_BASED);
                ***REMOVED*** else {
                    analysis.add(0, Analysis.INFO_RH_BUILD_LINUX_ZIP);
                ***REMOVED***
                // Check for RHEL6
                if (getOsVersion() == OsVersion.RHEL6) {
                    analysis.add(Analysis.WARN_RHEL6);
                ***REMOVED***
                // Check for RHEL7 Power9
                if (getOsVersion() == OsVersion.RHEL7 && (getArch() == Arch.PPC64 || getArch() == Arch.PPC64LE)) {
                    analysis.add(Analysis.WARN_RHEL7_POWER9);
                ***REMOVED***
                // Check for unnecessary use of -XX:+UnlockExperimentalVMOptions with Shenandoah on RH build.
                if (getJvmOptions() != null) {
                    if (JdkUtil.isOptionEnabled(getJvmOptions().getUseShenandoahGc())
                            && JdkUtil.isOptionEnabled(getJvmOptions().getUnlockExperimentalVmOptions())) {
                        analysis.add(Analysis.INFO_RH_OPT_EXPERIMENTAL_SHENANDOAH);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED*** else if (isWindows()) {
                analysis.add(0, Analysis.INFO_RH_BUILD_WINDOWS_ZIP);
            ***REMOVED***
        ***REMOVED*** else {
            if (isRhBuildString() && isRhVersion() && (isRhBuildDate() || isRhBuildDateUnknown())) {
                analysis.add(Analysis.INFO_RH_BUILD_POSSIBLE);
            ***REMOVED*** else if (isAdoptOpenJdkBuildString()) {
                analysis.add(Analysis.INFO_ADOPTOPENJDK_POSSIBLE);
            ***REMOVED*** else if (vmInfoEvent != null || getJavaVendor() == JavaVendor.NOT_RED_HAT) {
                analysis.add(0, Analysis.INFO_RH_BUILD_NOT);
            ***REMOVED***
        ***REMOVED***
        // Check if there is vm code in the stack
        if (haveFramesInStack() && !haveVmCodeInStack()) {
            analysis.add(Analysis.INFO_STACK_NO_VM_CODE);
        ***REMOVED***
        if (getJavaSpecification() != JavaSpecification.UNKNOWN && !isJdkLts()) {
            analysis.add(Analysis.WARN_JDK_NOT_LTS);
        ***REMOVED***
        // Check for crash in JNA
        if (isJnaCrash()) {
            if (getJavaVendor() == JavaVendor.RED_HAT) {
                analysis.add(Analysis.ERROR_JNA_RH);
            ***REMOVED*** else {
                analysis.add(Analysis.ERROR_JNA);
            ***REMOVED***
        ***REMOVED***
        // Check for JDK8 ZipFile contention
        if (JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) >= 6
                && JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) <= 8
                && getStackFrameTopCompiledJavaCode() != null
                && getStackFrameTopCompiledJavaCode().matches("^.+java\\.util\\.zip\\.ZipFile\\.getEntry.+$")) {
            analysis.add(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION);
        ***REMOVED***
        // Check for JDK8 Deflator contention
        if (getJavaSpecification() == JavaSpecification.JDK8 && getStackFrameTopCompiledJavaCode() != null
                && getStackFrameTopCompiledJavaCode().matches("^.+java\\.util\\.zip\\.Deflater\\.deflateBytes.+$")) {
            analysis.add(Analysis.ERROR_JDK8_DEFLATER_CONTENTION);
        ***REMOVED***
        // Check for unsynchronized access to DirectByteBuffer
        String stackFrameTop = "^v  ~StubRoutines::jbyte_disjoint_arraycopy$";
        if (getStackFrameTop() != null && getStackFrameTop().matches(stackFrameTop)) {
            if (isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER)) {
                analysis.add(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION);
            ***REMOVED*** else {
                analysis.add(Analysis.ERROR_STUBROUTINES);
            ***REMOVED***
        ***REMOVED***
        // Check for insufficient physical memory
        if (getJvmMemTotal() > 0 && getJvmMemoryMax() > Long.MIN_VALUE && getJvmMemoryMax() > getJvmMemTotal()) {
            if (getOsSwap() == 0 || getJvmSwap() == 0) {
                analysis.add(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP);
            ***REMOVED*** else {
                analysis.add(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP);
            ***REMOVED***
        ***REMOVED***
        // OOME, swap
        if (isError("There is insufficient memory for the Java Runtime Environment to continue.")) {
            if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
                if (getJvmMemoryInitial() > (Math.max(getJvmMemFree(), getOsMemAvailable()) + getJvmSwapFree())) {
                    if (getApplication() == Application.TOMCAT_SHUTDOWN) {
                        analysis.add(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN);
                    ***REMOVED*** else if (getApplication() == Application.JBOSS_VERSION) {
                        analysis.add(Analysis.ERROR_OOME_JBOSS_VERSION);
                    ***REMOVED*** else if (getApplication() == Application.AMQ_CLI) {
                        analysis.add(Analysis.ERROR_OOME_AMQ_CLI);
                    ***REMOVED*** else {
                        if (JdkMath.calcPercent(getJvmMemoryInitial(), getOsMemTotal()) < 50) {
                            analysis.add(Analysis.ERROR_OOME_STARTUP_EXTERNAL);
                        ***REMOVED*** else {
                            analysis.add(Analysis.ERROR_OOME_STARTUP);
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED*** else if (getCommitLimit() > 0 && getCommittedAs() > 0
                        && (getJvmMemoryInitial() > (getCommitLimit() - getCommittedAs()))) {
                    analysis.add(Analysis.ERROR_OOME_STARTUP_LIMIT_OVERCOMMIT);
                ***REMOVED*** else {
                    if (isInHeader("Java Heap may be blocking the growth of the native heap")) {
                        analysis.add(Analysis.ERROR_OOME_STARTUP_LIMIT_OOPS);
                    ***REMOVED*** else {
                        analysis.add(Analysis.ERROR_OOME_STARTUP_LIMIT);
                    ***REMOVED***
                ***REMOVED***
                // Don't double report the JVM failing to start
                analysis.remove(Analysis.INFO_JVM_STARTUP_FAILS);
            ***REMOVED*** else {
                // Check for failed allocation size in header
                long allocation = Long.MIN_VALUE;
                if (!headerEvents.isEmpty()) {
                    Iterator<HeaderEvent> iterator = headerEvents.iterator();
                    String regex = "^.+failed to (allocate|map) (\\d{1,***REMOVED***) bytes.+$";
                    while (iterator.hasNext()) {
                        HeaderEvent he = iterator.next();
                        if (he.getLogEntry().matches(regex)) {
                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(he.getLogEntry());
                            if (matcher.find()) {
                                allocation = JdkUtil.convertSize(Long.parseLong(matcher.group(2)), 'B',
                                        Constants.PRECISION_REPORTING);
                                break;
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                if (allocation >= 0 && getJvmMemFree() >= 0 && getJvmSwapFree() >= 0
                        && allocation > (getJvmMemFree() + getJvmSwapFree())) {
                    // Not enough physical memory
                    if (getJvmMemoryMax() > 0 && getJvmMemTotal() > 0) {
                        if (JdkMath.calcPercent(getJvmMemoryMax(), getJvmMemTotal()) >= 95) {
                            analysis.add(Analysis.ERROR_OOME_JVM);
                        ***REMOVED*** else {
                            analysis.add(Analysis.ERROR_OOME_EXTERNAL);
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED*** else if ((allocation >= 0 && getJvmMemFree() >= 0 && getJvmSwapFree() >= 0
                        && allocation < (getJvmMemFree() + getJvmSwapFree()))
                        || (getJvmMemFree() >= 0 && getJvmMemTotal() > 0
                                && JdkMath.calcPercent(getJvmMemFree(), getJvmMemTotal()) >= 50)
                        || (getJvmMemoryMax() >= 0 && getJvmMemTotal() > 0
                                && JdkMath.calcPercent(getJvmMemoryMax(), getJvmMemTotal()) < 50)) {
                    // allocation < available memory or free memory >= 50%
                    if (getCommitLimit() > 0 && getCommittedAs() > 0
                            && (getJvmMemoryMax() > (getCommitLimit() - getCommittedAs()))) {
                        analysis.add(Analysis.ERROR_OOME_LIMIT_OVERCOMMIT);
                    ***REMOVED*** else {
                        if (isInHeader("Java Heap may be blocking the growth of the native heap")
                                || isInHeader("compressed oops")) {
                            analysis.add(Analysis.ERROR_OOME_LIMIT_OOPS);
                        ***REMOVED*** else {
                            analysis.add(Analysis.ERROR_OOME_LIMIT);
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED*** else {
                    if (!(isTruncated() || isInHeader("Java Heap may be blocking the growth of the native heap")
                            || isInHeader("compressed oops"))) {
                        analysis.add(Analysis.ERROR_OOME);
                    ***REMOVED*** else {
                        analysis.add(Analysis.ERROR_OOME_OOPS);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            // G1 collector is not good when memory is tightINFO_VM_OPERATION_HEAP_DUMP
            if (getGarbageCollectors().contains(GarbageCollector.G1)) {
                analysis.add(Analysis.WARN_OOM_G1);
            ***REMOVED***
        ***REMOVED*** else if (getJvmSwap() > 0) {
            // Check for excessive swap usage
            int swapUsedPercent = 100 - JdkMath.calcPercent(getJvmSwapFree(), getJvmSwap());
            if (swapUsedPercent > 5 && swapUsedPercent < 20) {
                analysis.add(Analysis.INFO_SWAPPING);
            ***REMOVED*** else if (swapUsedPercent >= 20) {
                analysis.add(Analysis.WARN_SWAPPING);
            ***REMOVED***
        ***REMOVED***
        // Check for swap disabled
        if (getJvmSwap() == 0) {
            analysis.add(Analysis.INFO_SWAP_DISABLED);
            // Check if collector is appropriate for no-swap (e.g. container) use cases
            if (getGarbageCollectors().contains(GarbageCollector.G1) && getJvmSwap() == 0) {
                analysis.add(Analysis.WARN_SWAP_DISABLED_G1);
            ***REMOVED*** else if (getGarbageCollectors().contains(GarbageCollector.CMS) && getJvmSwap() == 0) {
                analysis.add(Analysis.WARN_SWAP_DISABLED_CMS);
            ***REMOVED***
        ***REMOVED***
        // libjvm.so/jvm.dll
        if (getJavaSpecification() == JavaSpecification.JDK8 && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 282 && getStackFrameTop() != null
                && getStackFrameTop()
                        .matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  ShenandoahUpdateRefsClosure::do_oop.+$")) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_ROOT_UPDATER);
        ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 312 && getStackFrameTop() != null
                && getStackFrameTop()
                        .matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  MetadataOnStackMark::~MetadataOnStackMark.+$")) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK);
        ***REMOVED*** else if (getCurrentThread() != null && getCurrentThread().matches("^.+CompilerThread\\d{1,***REMOVED***.+$")) {
            analysis.add(Analysis.ERROR_COMPILER_THREAD);
        ***REMOVED*** else if (getStackFrameTop() != null
                && getStackFrameTop().matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  (ModuleEntry::purge_reads|"
                        + "ModuleEntryTable::purge_all_module_reads).+$")) {
            analysis.add(Analysis.ERROR_MODULE_ENTRY_PURGE_READS);
        ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) >= 262
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 282 && getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+JfrEventClassTransformer::on_klass_creation.+$")) {
            analysis.add(Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED);
        ***REMOVED*** else if (getStackFrameTop() != null
                && !isError("There is insufficient memory for the Java Runtime Environment to continue")) {
            // Other libjvm.so/jvm.dll analysis
            if (getStackFrameTop().matches("^V  \\[libjvm\\.so.+\\](.+)?$")) {
                analysis.add(Analysis.ERROR_LIBJVM_SO);
            ***REMOVED*** else if (getStackFrameTop().matches("^V  \\[jvm\\.dll.+\\](.+)?$")) {
                analysis.add(Analysis.ERROR_JVM_DLL);
            ***REMOVED***
        ***REMOVED***
        // Signal numbers
        switch (getSignalNumber()) {
        case EXCEPTION_ACCESS_VIOLATION:
            analysis.add(Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION);
            break;
        case SIGBUS:
            analysis.add(Analysis.INFO_SIGNO_SIGBUS);
            break;
        case SIGFPE:
            analysis.add(Analysis.INFO_SIGNO_SIGFPE);
            break;
        case SIGILL:
            analysis.add(Analysis.INFO_SIGNO_SIGILL);
            break;
        case SIGSEGV:
            analysis.add(Analysis.INFO_SIGNO_SIGSEGV);
            break;
        case UNKNOWN:
        default:
            break;
        ***REMOVED***
        // Signal codes
        switch (getSignalCode()) {
        case BUS_ADRALN:
            analysis.add(Analysis.INFO_SIGCODE_BUS_ADRALN);
            break;
        case BUS_ADRERR:
            if (getOsType() == OsType.LINUX) {
                analysis.add(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX);
            ***REMOVED*** else {
                analysis.add(Analysis.INFO_SIGCODE_BUS_ADDERR);
            ***REMOVED***
            break;
        case BUS_OBJERR:
            analysis.add(Analysis.INFO_SIGCODE_BUS_OBJERR);
            break;
        case FPE_INTDIV:
            analysis.add(Analysis.INFO_SIGCODE_FPE_INTDIV);
            break;
        case ILL_ILLOPN:
            analysis.add(Analysis.INFO_SIGCODE_ILL_ILLOPN);
            break;
        case SEGV_ACCERR:
            analysis.add(Analysis.INFO_SIGCODE_SEGV_ACCERR);
            break;
        case SEGV_MAPERR:
            analysis.add(Analysis.INFO_SIGCODE_SEGV_MAPERR);
            break;
        case SI_KERNEL:
            analysis.add(Analysis.INFO_SIGCODE_SI_KERNEL);
            break;
        case SI_USER:
            analysis.add(Analysis.INFO_SIGCODE_SI_USER);
            break;
        case UNKNOWN:
        default:
            break;
        ***REMOVED***
        // Floating point error
        if (getSignalNumber().equals(SignalNumber.SIGFPE) || getSignalCode().equals(SignalCode.FPE_INTDIV)) {
            analysis.add(Analysis.ERROR_FPE);
        ***REMOVED***
        // pthread_getcpuclockid
        if (getStackFrameTop() != null
                && getStackFrameTop().matches("^C  \\[libpthread\\.so.+\\]  pthread_getcpuclockid.+$")) {
            analysis.add(Analysis.ERROR_PTHREAD_GETCPUCLOCKID);
        ***REMOVED***
        // BufferBlob::flush_icache_stub
        if (getStackFrameTop() != null && getStackFrameTop().matches("^v  ~BufferBlob::flush_icache_stub+$")) {
            analysis.add(Analysis.ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB);
        ***REMOVED***
        // StackOverflowError
        if (haveStackOverFlowError()) {
            analysis.add(Analysis.ERROR_STACKOVERFLOW);
        ***REMOVED*** else {
            if (getStackFreeSpace() > getThreadStackSize()) {
                analysis.add(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE);
            ***REMOVED***
        ***REMOVED***
        // LinkageError
        if (haveLinkageError()) {
            analysis.add(Analysis.ERROR_LINKAGE);
        ***REMOVED***
        // Thread stack size
        long threadStackMaxSize = getThreadStackSize();
        if (threadStackMaxSize < 1) {
            analysis.add(Analysis.WARN_THREAD_STACK_SIZE_TINY);
        ***REMOVED*** else if (threadStackMaxSize < 128) {
            analysis.add(Analysis.WARN_THREAD_STACK_SIZE_SMALL);
        ***REMOVED***
        // OutOfMemoryError other than "Metaspace" or "Compressed class space" caught and thrown
        if (haveOomeThrownJavaHeap()) {
            analysis.add(Analysis.ERROR_OOME_THROWN_JAVA_HEAP);
        ***REMOVED***
        // "OutOfMemoryError: Metaspace" caught and thrown
        if (haveOomeThrownMetaspace()) {
            analysis.add(Analysis.ERROR_OOME_THROWN_METASPACE);
        ***REMOVED***
        // "OutOfMemoryError: Compressed class space" caught and thrown
        if (haveOomeThrownCompressedClassSpace()) {
            analysis.add(Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE);
        ***REMOVED***
        // Stubroutines
        if ((getStackFrameTop() != null && getStackFrameTop().matches("^v  ~BufferBlob::StubRoutines.*"))
                || isError("v  ~BufferBlob::StubRoutines")) {
            analysis.add(Analysis.ERROR_STUBROUTINES);
        ***REMOVED***
        // ShenandoahConcurrentMark::mark_loop_work
        if ((getStackFrameTop() != null
                && getStackFrameTop().matches("^.+ShenandoahConcurrentMark::mark_loop_work.+"))) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK);
        ***REMOVED***
        // org.apache.activemq.artemis.nativo.jlibaio.LibaioContext.done()
        if ((getStackFrameTop() != null && getStackFrameTop()
                .matches("^.+ org.apache.activemq.artemis.nativo.jlibaio.LibaioContext.done().+"))) {
            analysis.add(Analysis.ERROR_LIBAIO_CONTEXT_DONE);
        ***REMOVED***
        // container
        if (!getContainerInfoEvents().isEmpty() && getJvmSwap() == 0) {
            analysis.add(Analysis.INFO_CGROUP);
        ***REMOVED***
        if (getJvmMemTotal() > 0 && getOsMemTotal() > 0 && getJvmMemTotal() != getOsMemTotal()) {
            analysis.add(Analysis.INFO_MEMORY_JVM_NE_SYSTEM);
            if (haveCgroupMemoryLimit()) {
                analysis.add(Analysis.INFO_CGROUP_MEMORY_LIMIT);
            ***REMOVED***
        ***REMOVED***
        // truncated fatal error log
        if (isTruncated()) {
            analysis.add(Analysis.INFO_TRUNCATED);
        ***REMOVED***
        // Storage analysis
        if (getOsType() == OsType.LINUX && !dynamicLibraryEvents.isEmpty()) {
            switch (getStorageDevice()) {
            case AWS_BLOCK_STORAGE:
                analysis.add(Analysis.INFO_STORAGE_AWS);
                break;
            case NFS:
                analysis.add(Analysis.INFO_STORAGE_NFS);
                break;
            case UNKNOWN:
                analysis.add(Analysis.INFO_STORAGE_UNKNOWN);
                break;
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        // Check if summarized remembered set processing information being output
        if (getGarbageCollectors().contains(GarbageCollector.G1) && jvmOptions != null
                && JdkUtil.isOptionEnabled(jvmOptions.getG1SummarizeRSetStats())
                && JdkUtil.getNumberOptionValue(jvmOptions.getG1SummarizeRSetStatsPeriod()) > 0) {
            analysis.add(Analysis.INFO_OPT_G1_SUMMARIZE_RSET_STATS_OUTPUT);
        ***REMOVED***
        // Check for CMS incremental mode with > 2 cpu
        if (getCpusLogical() > 2 && jvmOptions != null && !JdkUtil.isOptionDisabled(jvmOptions.getUseConcMarkSweepGc())
                && JdkUtil.isOptionEnabled(jvmOptions.getCmsIncrementalMode())) {
            analysis.add(Analysis.WARN_CMS_INCREMENTAL_MODE);
        ***REMOVED***
        // If CMS or G1, explicit gc is not handled concurrently by default
        List<GarbageCollector> garbageCollectors = getGarbageCollectors();
        if ((garbageCollectors.contains(GarbageCollector.CMS) || garbageCollectors.contains(GarbageCollector.G1))
                && jvmOptions != null && !JdkUtil.isOptionEnabled(jvmOptions.getExplicitGCInvokesConcurrent())
                && !JdkUtil.isOptionEnabled(jvmOptions.getDisableExplicitGc())) {
            analysis.add(Analysis.WARN_OPT_EXPLICIT_GC_NOT_CONCURRENT);
        ***REMOVED***
        // Check for explicit gc disabled on EAP7
        if (getApplication() == Application.JBOSS_EAP7 && jvmOptions != null
                && JdkUtil.isOptionEnabled(jvmOptions.getDisableExplicitGc())) {
            analysis.add(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7);
        ***REMOVED***
        // Check for redundant -server flag and ignored -client flag on 64-bit
        if (is64Bit() && jvmOptions != null) {
            if (jvmOptions.isD64()) {
                analysis.add(Analysis.INFO_OPT_D64_REDUNDANT);
            ***REMOVED***
            if (jvmOptions.isServer()) {
                analysis.add(Analysis.INFO_OPT_SERVER_REDUNDANT);
            ***REMOVED***
            if (jvmOptions.isClient()) {
                analysis.add(Analysis.INFO_OPT_CLIENT);
            ***REMOVED***
        ***REMOVED***
        // Check for crash caused trying to dereference a null pointer.
        if (sigInfoEvent != null && sigInfoEvent.getSignalAddress() != null
                && sigInfoEvent.getSignalAddress().matches(JdkRegEx.NULL_POINTER)) {
            analysis.add(Analysis.ERROR_NULL_POINTER);
        ***REMOVED***
        // Check if performance data is being written to disk in a container environment
        if (isContainer() && jvmOptions != null && !JdkUtil.isOptionDisabled(jvmOptions.getUsePerfData())
                && !JdkUtil.isOptionEnabled(jvmOptions.getPerfDisableSharedMem())) {
            analysis.add(Analysis.WARN_OPT_CONTAINER_PERF_DATA_DISK);
        ***REMOVED***
        // Check if performance data disabled
        if (jvmOptions != null && JdkUtil.isOptionDisabled(jvmOptions.getUsePerfData())) {
            analysis.add(Analysis.INFO_OPT_PERF_DATA_DISABLED);
        ***REMOVED***
        // Check JDK8 print gc details option missing
        if (getJavaSpecification() == JavaSpecification.JDK8 && jvmOptions != null
                && jvmOptions.getPrintGcDetails() == null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_GC_DETAILS_MISSING);
        ***REMOVED***
        // Check JDK11 print gc details option missing
        if (getJavaSpecification() == JavaSpecification.JDK11 && jvmOptions != null && !jvmOptions.getLog().isEmpty()) {
            Iterator<String> iterator = jvmOptions.getLog().iterator();
            boolean haveGcDetails = false;
            while (iterator.hasNext()) {
                String xLog = iterator.next();
                if (xLog.matches("^.+gc\\*=(?!off).+$")) {
                    haveGcDetails = true;
                    break;
                ***REMOVED***
            ***REMOVED***
            if (!haveGcDetails) {
                analysis.add(Analysis.INFO_OPT_JDK11_PRINT_GC_DETAILS_MISSING);
            ***REMOVED***
        ***REMOVED***
        // Check heap initial/max values non container
        if (getContainerInfoEvents().isEmpty() && jvmOptions != null && jvmOptions.getInitialHeapSize() != null
                && jvmOptions.getMaxHeapSize() != null
                && (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(jvmOptions.getInitialHeapSize())) != JdkUtil
                        .getByteOptionBytes(JdkUtil.getByteOptionValue(jvmOptions.getMaxHeapSize())))) {
            analysis.add(Analysis.INFO_OPT_HEAP_MIN_NOT_EQUAL_MAX);
        ***REMOVED***
        // Test extraneous use of -XX:LargePageSizeInBytes
        if (jvmOptions != null && jvmOptions.getLargePageSizeInBytes() != null) {
            if (getOsType() == OsType.LINUX) {
                analysis.add(Analysis.INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_LINUX);
            ***REMOVED*** else if (getOsType() == OsType.WINDOWS) {
                analysis.add(Analysis.INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS);
            ***REMOVED***
        ***REMOVED***
        // Test crash in Java compiled code
        if (getStackFrameTop() != null && getStackFrameTop().matches("^J \\d{1,***REMOVED***%{0,1***REMOVED*** C[12].+$")) {
            analysis.add(Analysis.ERROR_COMPILED_JAVA_CODE);
        ***REMOVED***
        // Check for possible JFFI usage
        if (!dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getFilePath() != null && event.getFilePath().matches("^.+[\\\\/](jffi|JFFI).+$")) {
                    analysis.add(Analysis.INFO_JFFI);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check for possible JNA usage
        if (!analysis.contains(Analysis.ERROR_JNA) && !analysis.contains(Analysis.ERROR_JNA_RH)
                && !dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getFilePath() != null && event.getFilePath().matches("^.+[\\\\/](jna|JNA).+$")) {
                    analysis.add(Analysis.INFO_JNA);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check for JVM crash due to temporary font file being removed from java.io.tmpdir.
        if (getStackFrameTopJava() != null
                && getStackFrameTopJava().matches("^.+sun\\.font\\.FreetypeFontScaler\\.getGlyphImageNative.+$")) {
            analysis.add(Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE);
        ***REMOVED***
        // Check for JDK8 Deflator contention
        if ((getArch().equals(Arch.PPC64) || getArch().equals(Arch.PPC64LE)) && getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+JavaThread::pd_get_top_frame_for_profiling.+$")) {
            analysis.add(Analysis.ERROR_JFR_PD_GET_TOP_FRAME);
        ***REMOVED***

        // Cannot get library information
        if (!dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry().matches("^Can not get library information for pid = \\d{1,***REMOVED***$")) {
                    analysis.add(Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check for G1ParScanThreadState::copy_to_survivor_space
        if (getJavaSpecification() == JavaSpecification.JDK8 && getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+G1ParScanThreadState::copy_to_survivor_space.+$")) {
            analysis.add(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE);

        ***REMOVED***
        // Check if JVM user ne USERNAME
        if (getJvmUser() != null && getUsername() != null && !getJvmUser().equals(getUsername())) {
            analysis.add(Analysis.INFO_JVM_USER_NE_USERNAME);
        ***REMOVED***
        // Check for no jvm options
        if (getJvmOptions() == null || getJvmOptions().getOptions().size() == 0) {
            analysis.add(Analysis.INFO_OPT_MISSING);
        ***REMOVED***
        // Check for many threads
        if (getJavaThreadCount() > 1000) {
            analysis.add(Analysis.INFO_THREADS_MANY);
        ***REMOVED***
        // Check environments
        if (isVMWareEnvironment()) {
            analysis.add(Analysis.INFO_VMWARE);
        ***REMOVED*** else if (isHyperVEnvironment()) {
            analysis.add(Analysis.INFO_HYPERV);
        ***REMOVED***
        // Check for mmap resources in deleted state
        if (!getDynamicLibraryEvents().isEmpty() && getMmapDeletedCount() > 0) {
            analysis.add(Analysis.WARN_MMAP_DELETED);
        ***REMOVED***
        // Check for RHEL/JDK rpm version mismatch
        if (isRhRpmInstall() && getRhelVersion() != null && getJdkRhelVersion() != null
                && !getRhelVersion().matches(getJdkRhelVersion())) {
            analysis.add(0, Analysis.ERROR_RHEL_JDK_RPM_MISMATCH);
            if (analysis.contains(Analysis.WARN_JDK_NOT_LATEST)) {
                analysis.remove(Analysis.WARN_JDK_NOT_LATEST);
            ***REMOVED***
        ***REMOVED***
        // Crash in HashMap
        if (getStackFrameTop() != null && getStackFrameTop().matches("^J.+java\\.util\\.HashMap.+$")) {
            analysis.add(Analysis.ERROR_HASHMAP);
        ***REMOVED***
        // Crash in Oracle JDBC driver
        if (getStackFrameTop() != null && getStackFrameTop().matches("^C  \\[libocijdbc.+$")) {
            analysis.add(Analysis.ERROR_ORACLE_JDBC_DRIVER);
        ***REMOVED***
        // Crash in CompilerThread
        if (getCurrentThread() != null && getCurrentThread().matches("^.+C2 CompilerThread\\d{1,***REMOVED***.+$")) {
            if (isInHeader("guarantee\\(n != NULL\\) failed: No Node.") && isInStack("IdealLoopTree::beautify_loops")) {
                analysis.add(Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS);
                // Don't double report
                analysis.remove(Analysis.ERROR_COMPILER_THREAD);
            ***REMOVED*** else if (getStackFrameTop() != null
                    && getStackFrameTop().matches("^.*MinINode::Ideal\\(PhaseGVN\\*, bool\\).*$")
                    && (getJavaSpecification() == JavaSpecification.JDK8
                            && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                            && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 275
                            || getJavaSpecification() == JavaSpecification.JDK11
                                    && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                                    && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 9)) {
                analysis.add(Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL);
                // Don't double report
                analysis.remove(Analysis.ERROR_COMPILER_THREAD);
            ***REMOVED***
        ***REMOVED***
        // Crash during shutdown
        if (getEventEvents().size() > 0) {
            EventEvent lastEventEvent = getEventEvents().get(getEventEvents().size() - 1);
            if (lastEventEvent.getLogEntry().matches("^.+Executing VM operation: Exit$")) {
                analysis.add(Analysis.INFO_SHUTDOWN);
            ***REMOVED***
        ***REMOVED***
        // iText
        if (!dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry() != null && event.getLogEntry().matches("^.+itext.*\\.jar$")) {
                    analysis.add(Analysis.INFO_ITEXT);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // IBM Toolkit
        if (!dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry() != null && event.getLogEntry().matches("^.+jt400\\.jar$")) {
                    analysis.add(Analysis.INFO_IBM_TOOLKIT);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // ERROR_JDK8_LIBC_CFREE
        if (getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 262) {
            if (!headerEvents.isEmpty()) {
                Iterator<HeaderEvent> iterator = headerEvents.iterator();
                while (iterator.hasNext()) {
                    HeaderEvent event = iterator.next();
                    if (event.isProblematicFrame() && event.getLogEntry().matches("^.+libc.+cfree\\+0x1c$")
                            && getJvmOptions().getUseGcLogFileRotation() != null) {
                        analysis.add(Analysis.ERROR_JDK8_LIBC_CFREE);
                        break;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // VM operations
        if (vmOperationEvent != null) {
            if (vmOperationEvent.getVmOperation().equals("BulkRevokeBias")) {
                analysis.add(Analysis.INFO_VM_OPERATION_BULK_REVOKE_BIAS);
            ***REMOVED*** else if (vmOperationEvent.getVmOperation().equals("CGC_Operation")) {
                analysis.add(Analysis.INFO_VM_OPERATION_CONCURRENT_GC);
            ***REMOVED*** else if (vmOperationEvent.getVmOperation().equals("HeapDumper")) {
                analysis.add(Analysis.INFO_VM_OPERATION_HEAP_DUMP);
            ***REMOVED*** else if (vmOperationEvent.getVmOperation().equals("PrintThreads")) {
                analysis.add(Analysis.INFO_VM_OPERATION_THREAD_DUMP);
            ***REMOVED***
        ***REMOVED***
        // DBCP2
        String orgApacheCommonsDbcp2 = "org[\\.\\/]apache[\\.\\/]commons[\\.\\/]dbcp2[\\.\\/]";
        if (isInStack(orgApacheCommonsDbcp2)) {
            analysis.add(Analysis.INFO_DBCP2);
        ***REMOVED***
        // PostgreSQL connection
        String postgreSqlConnection = "org[\\.\\/]postgresql[\\.\\/]Driver[\\.\\/]connect\\(";
        if (isInStack(postgreSqlConnection)) {
            analysis.add(Analysis.INFO_POSTGRESQL_CONNECTION);
        ***REMOVED***
    ***REMOVED***

    public List<Analysis> getAnalysis() {
        return analysis;
    ***REMOVED***

    /**
     * @return The application running on the JDK
     */
    public Application getApplication() {
        Application application = Application.UNKNOWN;
        // Check libraries
        if (!dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry().matches(JdkRegEx.JBOSS_EAP6_JAR)) {
                    application = Application.JBOSS_EAP6;
                    break;
                ***REMOVED*** else if (event.getLogEntry().matches(JdkRegEx.JBOSS_EAP7_JAR)) {
                    application = Application.JBOSS_EAP7;
                    break;
                ***REMOVED*** else if (event.getLogEntry().matches(JdkRegEx.TOMCAT_JAR)) {
                    application = Application.TOMCAT;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check threads
        if (application == Application.UNKNOWN) {
            if (!threadEvents.isEmpty()) {
                Iterator<ThreadEvent> iterator = threadEvents.iterator();
                while (iterator.hasNext()) {
                    ThreadEvent event = iterator.next();
                    if (event.getLogEntry() != null && event.getLogEntry().matches(JdkRegEx.RHSSO_THREAD)) {
                        application = Application.RHSSO;
                        break;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check java_command
        if (application == Application.UNKNOWN) {
            String javaCommand = getJavaCommand();
            if (javaCommand != null) {
                if (javaCommand.matches(JdkRegEx.TOMCAT_START_COMMAND)) {
                    application = Application.TOMCAT;
                ***REMOVED*** else if (javaCommand.matches(JdkRegEx.TOMCAT_STOP_COMMAND)) {
                    application = Application.TOMCAT_SHUTDOWN;
                ***REMOVED*** else if (javaCommand.matches(JdkRegEx.ARTEMIS_COMMAND)) {
                    application = Application.AMQ;
                ***REMOVED*** else if (javaCommand.matches(JdkRegEx.ARTEMIS_CLI_COMMAND)) {
                    application = Application.AMQ_CLI;
                ***REMOVED*** else if (javaCommand.matches(JdkRegEx.KAFKA_COMMAND)) {
                    application = Application.KAFKA;
                ***REMOVED*** else if (javaCommand.matches(JdkRegEx.JBOSS_VERSION_COMMAND)) {
                    application = Application.JBOSS_VERSION;
                ***REMOVED*** else if (javaCommand.matches(JdkRegEx.WILDFLY_JAR)) {
                    application = Application.WILDFLY;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return application;
    ***REMOVED***

    /**
     * @return <code>Arch</code>
     */
    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (unameEvent != null) {
            arch = unameEvent.getArch();
        ***REMOVED*** else if (vmInfoEvent != null) {
            arch = vmInfoEvent.getArch();
        ***REMOVED*** else if (!headerEvents.isEmpty()) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJavaVm()) {
                    if (he.getLogEntry().matches("^.+ppc64.+$")) {
                        arch = Arch.PPC64;
                    ***REMOVED*** else if (he.getLogEntry().matches("^.+ppc64le.+$")) {
                        arch = Arch.PPC64LE;
                    ***REMOVED*** else if (he.getLogEntry().matches("^.+solaris-sparc.+$")) {
                        arch = Arch.SPARC;
                    ***REMOVED*** else if (he.getLogEntry().matches("^.+amd64.+$")) {
                        arch = Arch.X86_64;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return arch;
    ***REMOVED***

    /**
     * @return The max code cache size in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getCodeCacheSize() {
        // Default is 420m
        long reservedCodeCacheSize = JdkUtil.convertSize(420, 'M', Constants.PRECISION_REPORTING);
        // 1st check [Global flags]
        if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExReservedCodeCacheSize = "^.+uintx ReservedCodeCacheSize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                Pattern pattern = Pattern.compile(regExReservedCodeCacheSize);
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    reservedCodeCacheSize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (jvmOptions != null
                && (jvmOptions.getReservedCodeCacheSize() != null || jvmOptions.getMaxjitcodesize() != null)) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher;
            if (jvmOptions.getReservedCodeCacheSize() != null) {
                matcher = pattern.matcher(jvmOptions.getReservedCodeCacheSize());
            ***REMOVED*** else {
                matcher = pattern.matcher(jvmOptions.getMaxjitcodesize());
            ***REMOVED***
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                reservedCodeCacheSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
            ***REMOVED***
        ***REMOVED***
        return reservedCodeCacheSize;
    ***REMOVED***

    public CommandLineEvent getCommandLineEvent() {
        return commandLineEvent;
    ***REMOVED***

    /**
     * The total amount of memory currently available to be allocated by the system, based on the overcommit ratio (vm.
     * overcommit_ratio), this is the total amount of memory currently available to be allocated on the system.
     * 
     * This limit is only adhered to if strict overcommit accounting is enabled (mode 2 in vm.overcommit_memory)
     * 
     * @return The total amount of memory currently available to be allocated by the system in
     *         <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getCommitLimit() {
        long commitLimit = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "CommitLimit:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    commitLimit = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return commitLimit;
    ***REMOVED***

    /**
     * The amount of memory currently allocated on the system.
     * 
     * The committed memory is a sum of all of the memory which has been allocated by processes, even if it has not been
     * "used" by them yet.
     * 
     * @return The amount of memory currently allocated on the system in <code>Constants.PRECISION_REPORTING</code>
     *         units.
     */
    public long getCommittedAs() {
        long committedAs = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "Committed_AS:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    committedAs = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return committedAs;
    ***REMOVED***

    public List<CompilationEvent> getCompilationEvents() {
        return compilationEvents;
    ***REMOVED***

    public CompressedClassSpaceEvent getCompressedClassSpaceEvent() {
        return compressedClassSpaceEvent;
    ***REMOVED***

    /**
     * @return The max compressed class size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getCompressedClassSpaceSize() {
        // 1) Determine if compressed pointers are being used.
        boolean usingCompressedPointers = true;

        // 2) Default is to use compressed pointers based on heap size
        BigDecimal thirtyTwoGigabytes = new BigDecimal("32").multiply(Constants.GIGABYTE);
        long heapMaxSize = getHeapMaxSize();
        if (heapMaxSize >= thirtyTwoGigabytes.longValue()) {
            usingCompressedPointers = false;
        ***REMOVED***

        // 3) Check if the default behavior is being overridden
        if (jvmOptions != null) {
            if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())) {
                usingCompressedPointers = false;
            ***REMOVED*** else {
                if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedClassPointers())) {
                    usingCompressedPointers = false;
                ***REMOVED*** else {
                    usingCompressedPointers = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            boolean useCompressedOops = true;
            boolean useCompressedClassPointers = true;
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExCompressedOopsDisabled = "^.+bool UseCompressedOops[ ]{1,***REMOVED***= false.+$";
                String regExCompressedClassPointersDisabled = "^.+bool UseCompressedClassPointers[ ]{1,***REMOVED***= false.+$";
                if (event.getLogEntry().matches(regExCompressedOopsDisabled)) {
                    useCompressedOops = false;
                ***REMOVED*** else if (event.getLogEntry().matches(regExCompressedClassPointersDisabled)) {
                    useCompressedClassPointers = false;
                ***REMOVED***
            ***REMOVED***
            if (!useCompressedOops) {
                usingCompressedPointers = false;
            ***REMOVED*** else {
                if (!useCompressedClassPointers) {
                    usingCompressedPointers = false;
                ***REMOVED*** else {
                    usingCompressedPointers = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        long compressedClassSpaceSize = 0;

        if (usingCompressedPointers) {
            // Default is 1g
            compressedClassSpaceSize = JdkUtil.convertSize(1, 'G', Constants.PRECISION_REPORTING);
            // 1st check [Global flags]
            if (!globalFlagsEvents.isEmpty()) {
                Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
                while (iterator.hasNext()) {
                    GlobalFlagsEvent event = iterator.next();
                    String regExCompressedClassSpaceSize = "^.+uintx CompressedClassSpaceSize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                    Pattern pattern = Pattern.compile(regExCompressedClassSpaceSize);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        compressedClassSpaceSize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B',
                                Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED*** else if (jvmOptions != null) {
                if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())
                        || JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedClassPointers())) {
                    compressedClassSpaceSize = 0;
                ***REMOVED*** else if (jvmOptions.getCompressedClassSpaceSize() != null) {
                    char fromUnits;
                    long value;
                    Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
                    Matcher matcher = pattern.matcher(jvmOptions.getCompressedClassSpaceSize());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        compressedClassSpaceSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return compressedClassSpaceSize;
    ***REMOVED***

    /**
     * @return The JVM compressed oop mode.
     */
    public CompressedOopMode getCompressedOopMode() {
        CompressedOopMode compressedOopMode = CompressedOopMode.UNKNOWN;
        if (!headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent event = iterator.next();
                if (event.isJavaVm()) {
                    if (!event.getLogEntry().matches(".*compressed oops.*")) {
                        compressedOopMode = CompressedOopMode.NONE;
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (compressedOopMode != CompressedOopMode.NONE && heapAddressEvent != null) {
            compressedOopMode = heapAddressEvent.getCompressedOopMode();
        ***REMOVED***
        return compressedOopMode;
    ***REMOVED***

    public List<ContainerInfoEvent> getContainerInfoEvents() {
        return containerInfoEvents;
    ***REMOVED***

    /**
     * @return The CPU architecture.
     */
    public CpuArch getCpuArch() {
        CpuArch cpuArch = CpuArch.UNKNOWN;
        if (!cpuInfoEvents.isEmpty()) {
            Iterator<CpuInfoEvent> iterator = cpuInfoEvents.iterator();
            while (iterator.hasNext()) {
                CpuInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^.+POWER9.+$")) {
                    cpuArch = CpuArch.POWER9;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return cpuArch;
    ***REMOVED***

    public List<CpuInfoEvent> getCpuInfoEvents() {
        return cpuInfoEvents;
    ***REMOVED***

    /**
     * @return The number of logical cpus (cpus x cpu cores x hyperthreading).
     */
    public int getCpusLogical() {
        int cpuLogical = Integer.MIN_VALUE;
        if (!cpuInfoEvents.isEmpty()) {
            Iterator<CpuInfoEvent> iterator = cpuInfoEvents.iterator();
            while (iterator.hasNext()) {
                CpuInfoEvent event = iterator.next();
                if (event.isCpuHeader()) {
                    Pattern pattern = Pattern.compile(CpuInfoEvent.REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find() && matcher.group(1) != null) {
                        int cpus = Integer.parseInt(matcher.group(1));
                        BigDecimal calc = new BigDecimal(cpus);
                        if (matcher.group(5) != null) {
                            int cores = Integer.parseInt(matcher.group(5));
                            calc = calc.multiply(new BigDecimal(cores));
                        ***REMOVED***
                        if (matcher.group(6) != null) {
                            int threads = Integer.parseInt(matcher.group(6));
                            calc = calc.multiply(new BigDecimal(threads));
                        ***REMOVED***
                        cpuLogical = calc.intValue();
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return cpuLogical;
    ***REMOVED***

    /**
     * The crash <code>Date</code> from a given crash date/time string (e.g. Tue Mar 1 09:13:16 2022 UTC).
     * 
     * Note: Timezone is ignored.
     * 
     * @return The <code>Date</code> of the crash or null if unknown.
     */
    public Date getCrashDate() {
        Date crashDate = null;
        if (getCrashTimeString() != null && getCrashTimeString().length() > 0) {
            String MMM = null;
            String d = null;
            String yyyy = null;
            String HH = null;
            String mm = null;
            String ss = null;
            Pattern pattern = Pattern.compile(JdkRegEx.CRASH_DATE_TIME);
            Matcher matcher = pattern.matcher(getCrashTimeString());
            if (matcher.find()) {
                MMM = matcher.group(2);
                d = matcher.group(3);
                HH = matcher.group(4);
                mm = matcher.group(5);
                ss = matcher.group(6);
                yyyy = matcher.group(7);
            ***REMOVED***
            crashDate = ErrUtil.getDate(MMM, d, yyyy, HH, mm, ss);
        ***REMOVED***
        return crashDate;
    ***REMOVED***

    /**
     * The crash date/time string.
     * 
     * For example:
     * 
     * Tue Mar 1 09:13:16 2022 UTC
     * 
     * @return The date/time string of the crash.
     */
    public String getCrashTimeString() {
        StringBuilder crashTime = new StringBuilder();
        if (timeEvent != null) {
            crashTime.append(timeEvent.getTimeString());
            if (timezoneEvent != null) {
                crashTime.append(" ");
                crashTime.append(timezoneEvent.getTimezone());
            ***REMOVED***
        ***REMOVED*** else if (timeElapsedTimeEvent != null) {
            crashTime.append(timeElapsedTimeEvent.getTimeString());
        ***REMOVED***
        return crashTime.toString();
    ***REMOVED***

    public List<CurrentCompileTaskEvent> getCurrentCompileTaskEvents() {
        return currentCompileTaskEvents;
    ***REMOVED***

    public String getCurrentThread() {
        String currentThread = null;
        if (currentThreadEvent != null) {
            currentThread = currentThreadEvent.getCurrentThread();
        ***REMOVED***
        return currentThread;
    ***REMOVED***

    public List<DeoptimizationEvent> getDeoptimizationEvents() {
        return deoptimizationEvents;
    ***REMOVED***

    /**
     * @return The max direct memory size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getDirectMemoryMaxSize() {
        long directMemorySize = 0;
        // 1st check [Global flags]
        if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExMaxDirectMemorySize = "^.+uintx MaxDirectMemorySize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                Pattern pattern = Pattern.compile(regExMaxDirectMemorySize);
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    directMemorySize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (jvmOptions != null && jvmOptions.getMaxDirectMemorySize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getMaxDirectMemorySize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                directMemorySize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
            ***REMOVED***
        ***REMOVED***
        return directMemorySize;
    ***REMOVED***

    public List<DynamicLibraryEvent> getDynamicLibraryEvents() {
        return dynamicLibraryEvents;
    ***REMOVED***

    /**
     * @return The duration of the JVM run.
     */
    public String getElapsedTime() {
        String elapsedTime = null;
        if (elapsedTimeEvent != null) {
            elapsedTime = elapsedTimeEvent.getElapsedTime();
        ***REMOVED*** else if (timeElapsedTimeEvent != null) {
            elapsedTime = timeElapsedTimeEvent.getElapsedTime();
        ***REMOVED***
        return elapsedTime;
    ***REMOVED***

    public List<EnvironmentVariablesEvent> getEnvironmentVariablesEvents() {
        return environmentVariablesEvents;
    ***REMOVED***

    /**
     * @return A <code>String</code> describing the cause of the crash.
     */
    public String getError() {
        StringBuilder causedBy = new StringBuilder();
        if (!headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSignalNumber() || he.isInternalError() || he.isError() || he.isFailed() || he.isInsufficient()
                        || he.isInvalid() || he.isOutOf() || he.isProblematicFrame()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    causedBy.append(he.getLogEntry());
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return causedBy.toString();
    ***REMOVED***

    public List<EventEvent> getEventEvents() {
        return eventEvents;
    ***REMOVED***

    public List<ExceptionCountsEvent> getExceptionCountsEvents() {
        return exceptionCountsEvents;
    ***REMOVED***

    public List<GarbageCollector> getGarbageCollectors() {
        // Check heap events
        List<GarbageCollector> garbageCollectors = new ArrayList<GarbageCollector>();
        if (!heapEvents.isEmpty()) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches("^[ ]{0,***REMOVED***Shenandoah.+$")
                        && !garbageCollectors.contains(GarbageCollector.SHENANDOAH)) {
                    garbageCollectors.add(GarbageCollector.SHENANDOAH);
                    break;
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***garbage-first.+$")
                        && !garbageCollectors.contains(GarbageCollector.G1)) {
                    garbageCollectors.add(GarbageCollector.G1);
                    break;
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***PSYoungGen.+$")
                        && !garbageCollectors.contains(GarbageCollector.PARALLEL_SCAVENGE)) {
                    garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***ParOldGen.+$")
                        && !garbageCollectors.contains(GarbageCollector.PARALLEL_OLD)) {
                    garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***par new.+$")
                        && !garbageCollectors.contains(GarbageCollector.PAR_NEW)) {
                    garbageCollectors.add(GarbageCollector.PAR_NEW);
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***concurrent mark-sweep.+$")
                        && !garbageCollectors.contains(GarbageCollector.CMS)) {
                    garbageCollectors.add(GarbageCollector.CMS);
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***ZHeap.+$")
                        && !garbageCollectors.contains(GarbageCollector.ZGC)) {
                    garbageCollectors.add(GarbageCollector.ZGC);
                ***REMOVED*** else if (event.getLogEntry().matches("^[ ]{0,***REMOVED***def new.+$")
                        && !garbageCollectors.contains(GarbageCollector.SERIAL)) {
                    garbageCollectors.add(GarbageCollector.SERIAL);
                ***REMOVED*** else if ((event.getLogEntry().matches("^[ ]{0,***REMOVED***PSOldGen.+$")
                        || event.getLogEntry().matches("^[ ]{0,***REMOVED***tenured.+$"))
                        && !garbageCollectors.contains(GarbageCollector.SERIAL_OLD)) {
                    garbageCollectors.add(GarbageCollector.SERIAL_OLD);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check JVM options if no heap events
        if (garbageCollectors.isEmpty() && jvmOptions != null && !jvmOptions.getGarbageCollectors().isEmpty()) {
            garbageCollectors.addAll(jvmOptions.getGarbageCollectors());
        ***REMOVED***
        // Assign JDK defaults JVM collector options
        if (garbageCollectors.isEmpty()) {
            if (getJavaSpecification() == JavaSpecification.JDK11
                    || getJavaSpecification() == JavaSpecification.JDK17) {
                garbageCollectors.add(GarbageCollector.G1);
            ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK8) {
                garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
                garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
            ***REMOVED***
        ***REMOVED***
        if (garbageCollectors.isEmpty()) {
            garbageCollectors.add(GarbageCollector.UNKNOWN);
        ***REMOVED***
        return garbageCollectors;
    ***REMOVED***

    public List<GcHeapHistoryEvent> getGcHeapHistoryEvents() {
        return gcHeapHistoryEvents;
    ***REMOVED***

    public List<GcPreciousLogEvent> getGcPreciousLogEvents() {
        return gcPreciousLogEvents;
    ***REMOVED***

    public List<GlobalFlagsEvent> getGlobalFlagsEvents() {
        return globalFlagsEvents;
    ***REMOVED***

    public List<HeaderEvent> getHeaderEvents() {
        return headerEvents;
    ***REMOVED***

    public HeapAddressEvent getHeapAddressEvent() {
        return heapAddressEvent;
    ***REMOVED***

    /**
     * @return The total heap allocation in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getHeapAllocation() {
        long heapAllocation = Long.MIN_VALUE;
        if (!heapEvents.isEmpty()) {
            heapAllocation = 0;
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.isYoungGen()) {
                    pattern = Pattern.compile(JdkRegEx.YOUNG_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(3));
                        if (matcher.group(5) != null) {
                            fromUnits = matcher.group(5).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED*** else if (event.isOldGen()) {
                    pattern = Pattern.compile(JdkRegEx.OLD_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(4) != null) {
                            fromUnits = matcher.group(4).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED*** else if (event.isShenandoah()) {
                    pattern = Pattern.compile(JdkRegEx.SHENANDOAH_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(9));
                        if (matcher.group(11) != null) {
                            fromUnits = matcher.group(11).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED*** else if (event.isG1()) {
                    pattern = Pattern.compile(JdkRegEx.G1_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return heapAllocation;
    ***REMOVED***

    public List<HeapEvent> getHeapEvents() {
        return heapEvents;
    ***REMOVED***

    /**
     * @return The heap initial size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getHeapInitialSize() {
        long heapInitialSize = Long.MIN_VALUE;
        // 1st check [Global flags]
        if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExInitialHeapSize = "^.+size_t InitialHeapSize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                Pattern pattern = Pattern.compile(regExInitialHeapSize);
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    heapInitialSize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (jvmOptions != null && jvmOptions.getInitialHeapSize() != null) {
            // Get from jvm_args
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getInitialHeapSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                heapInitialSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
            ***REMOVED***
        ***REMOVED*** else if (heapAddressEvent != null) {
            heapInitialSize = heapAddressEvent.getSize();
        ***REMOVED*** else if (getOsMemTotal() > 0) {
            // Use JVM default = 1/64 system memory
            BigDecimal systemPhysicalMemory = new BigDecimal(getOsMemTotal());
            systemPhysicalMemory = systemPhysicalMemory.divide(new BigDecimal(64));
            systemPhysicalMemory = systemPhysicalMemory.setScale(0, RoundingMode.HALF_EVEN);
            heapInitialSize = systemPhysicalMemory.longValue();
        ***REMOVED*** else if (getHeapAllocation() > 0) {
            // Use allocation
            heapInitialSize = getHeapAllocation();
        ***REMOVED***
        return heapInitialSize;
    ***REMOVED***

    /**
     * @return The heap max size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getHeapMaxSize() {
        long heapMaxSize = Long.MIN_VALUE;
        // 1st check [Global flags]
        if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExMaxHeapSize = "^.+size_t MaxHeapSize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                Pattern pattern = Pattern.compile(regExMaxHeapSize);
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    heapMaxSize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (jvmOptions != null && jvmOptions.getMaxHeapSize() != null) {
            // Get from jvm_args
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getMaxHeapSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                heapMaxSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
            ***REMOVED***
        ***REMOVED*** else if (heapAddressEvent != null) {
            heapMaxSize = heapAddressEvent.getSize();
        ***REMOVED*** else if (getOsMemTotal() > 0) {
            // Use JVM default = 1/4 system memory
            BigDecimal systemPhysicalMemory = new BigDecimal(getOsMemTotal());
            systemPhysicalMemory = systemPhysicalMemory.divide(new BigDecimal(4));
            systemPhysicalMemory = systemPhysicalMemory.setScale(0, RoundingMode.HALF_EVEN);
            heapMaxSize = systemPhysicalMemory.longValue();
        ***REMOVED*** else if (getHeapAllocation() > 0) {
            // Use allocation
            heapMaxSize = getHeapAllocation();
        ***REMOVED***
        return heapMaxSize;
    ***REMOVED***

    /**
     * @return The heap starting address in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getHeapStartingAddress() {
        long heapStartingAddress = Long.MIN_VALUE;
        if (heapAddressEvent != null) {
            heapStartingAddress = JdkUtil.convertSize(heapAddressEvent.getStartingAddress(), 'B',
                    Constants.PRECISION_REPORTING);
        ***REMOVED***
        return heapStartingAddress;
    ***REMOVED***

    /**
     * @return The total heap used in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getHeapUsed() {
        long heapUsed = Long.MIN_VALUE;
        if (!heapEvents.isEmpty()) {
            heapUsed = 0;
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.isYoungGen()) {
                    pattern = Pattern.compile(JdkRegEx.YOUNG_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(6));
                        if (matcher.group(8) != null) {
                            fromUnits = matcher.group(8).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED*** else if (event.isOldGen()) {
                    pattern = Pattern.compile(JdkRegEx.OLD_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(5));
                        if (matcher.group(7) != null) {
                            fromUnits = matcher.group(7).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED*** else if (event.isShenandoah()) {
                    pattern = Pattern.compile(JdkRegEx.SHENANDOAH_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(12));
                        if (matcher.group(14) != null) {
                            fromUnits = matcher.group(14).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED*** else if (event.isG1()) {
                    pattern = Pattern.compile(JdkRegEx.G1);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(5));
                        if (matcher.group(7) != null) {
                            fromUnits = matcher.group(7).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return heapUsed;
    ***REMOVED***

    public HostEvent getHostEvent() {
        return hostEvent;
    ***REMOVED***

    /**
     * @return The Java command used to start the JVM, or null if none exists.
     */
    public String getJavaCommand() {
        String javaCommand = null;
        if (!vmArgumentsEvents.isEmpty()) {
            Iterator<VmArgumentsEvent> iterator = vmArgumentsEvents.iterator();
            while (iterator.hasNext()) {
                VmArgumentsEvent event = iterator.next();
                if (event.isJavaCommand()) {
                    javaCommand = event.getValue();
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return javaCommand;
    ***REMOVED***

    /**
     * @return <code>JavaSpecificiation</code>
     */
    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJavaSpecification();
        ***REMOVED***
        // Get from header
        if (version == JavaSpecification.UNKNOWN && !headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                String regEx = "\\((\\d{1,2***REMOVED***)\\..+\\)";
                Pattern pattern = Pattern.compile(regEx);
                if (he.isJreVersion()) {
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        switch (Integer.parseInt(matcher.group(1))) {
                        case 6:
                            version = JavaSpecification.JDK6;
                            break;
                        case 7:
                            version = JavaSpecification.JDK7;
                            break;
                        case 8:
                            version = JavaSpecification.JDK8;
                            break;
                        case 11:
                            version = JavaSpecification.JDK11;
                            break;
                        case 17:
                            version = JavaSpecification.JDK17;
                            break;
                        default:
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return version;
    ***REMOVED***

    /**
     * @return The number of Java threads running when the JVM crashed.
     */
    public int getJavaThreadCount() {
        int javaThreadCount = 0;
        if (!threadEvents.isEmpty()) {
            Iterator<ThreadEvent> iterator = threadEvents.iterator();
            while (iterator.hasNext()) {
                ThreadEvent event = iterator.next();
                if (event.getLogEntry().matches("^***REMOVED***$")) {
                    break;
                ***REMOVED*** else if (!event.getLogEntry().matches("^Java Threads: \\( => current thread \\)$")) {
                    javaThreadCount++;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return javaThreadCount;
    ***REMOVED***

    /**
     * @return <code>JavaVendor</code>
     */
    public JavaVendor getJavaVendor() {
        JavaVendor vendor = JavaVendor.UNKNOWN;
        if (isRhBuildOpenJdk()) {
            vendor = JavaVendor.RED_HAT;
        ***REMOVED*** else {
            if (vmInfoEvent != null) {
                switch (vmInfoEvent.getBuiltBy()) {
                case JAVA_RE:
                case MACH5ONE:
                    vendor = JavaVendor.ORACLE;
                    break;
                case JENKINS:
                    vendor = JavaVendor.ADOPTOPENJDK;
                    break;
                case MOCKBUILD:
                    // Some other OpenJDK
                    vendor = JavaVendor.UNKNOWN;
                    break;
                case VSTS:
                    vendor = JavaVendor.MICROSOFT;
                    break;
                case TESTER:
                case ZULU_RE:
                    vendor = JavaVendor.AZUL;
                    break;
                case BUILD:
                case EMPTY:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (vendor == JavaVendor.UNKNOWN) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJreVersion()) {
                    if (he.getLogEntry().matches("^.+AdoptOpenJDK.+$")) {
                        vendor = JavaVendor.ADOPTOPENJDK;
                    ***REMOVED*** else if (getOsType() != OsType.UNKNOWN && !isRhVersion()) {
                        vendor = JavaVendor.NOT_RED_HAT;
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return vendor;
    ***REMOVED***

    /**
     * @return The JDK build date/time.
     */
    public Date getJdkBuildDate() {
        Date date = null;
        if (vmInfoEvent != null) {
            date = vmInfoEvent.getBuildDate();
        ***REMOVED***
        return date;
    ***REMOVED***

    /**
     * @return JDK release string, or UNKNOWN if it cannot be determined.
     */
    public String getJdkReleaseString() {
        String release = "UNKNOWN";
        if (vmInfoEvent != null) {
            release = vmInfoEvent.getJdkReleaseString();
            // TODO: Better solution than this hack to account for 2 windows builds based on the same upstream tag?
            if (vmInfoEvent.getOs() == OsType.WINDOWS) {
                if (vmInfoEvent.getJavaSpecification() == JavaSpecification.JDK8 && release.equals("1.8.0_332-b09")) {
                    if (vmInfoEvent.getBuildDate().equals(ErrUtil.getDate("Apr 19 2022 13:36:53"))) {
                        release = "1.8.0_332-b09-1";
                    ***REMOVED*** else if (vmInfoEvent.getBuildDate().equals(ErrUtil.getDate("Apr 27 2022 21:29:19"))) {
                        release = "1.8.0_332-b09-2";
                    ***REMOVED***
                ***REMOVED*** else if (vmInfoEvent.getJavaSpecification() == JavaSpecification.JDK11
                        && release.equals("11.0.15+9-LTS")) {
                    if (vmInfoEvent.getBuildDate().equals(ErrUtil.getDate("Apr 17 2022 13:56:34"))) {
                        release = "11.0.15+9-LTS-1";
                    ***REMOVED*** else if (vmInfoEvent.getBuildDate().equals(ErrUtil.getDate("Apr 27 2022 19:12:18"))) {
                        release = "11.0.15+9-LTS-2";
                    ***REMOVED***
                ***REMOVED*** else if (vmInfoEvent.getJavaSpecification() == JavaSpecification.JDK17
                        && release.equals("17.0.3+6-LTS")) {
                    if (vmInfoEvent.getBuildDate().equals(ErrUtil.getDate("Apr 17 2022 12:11:44"))) {
                        release = "17.0.3+6-LTS-1";
                    ***REMOVED*** else if (vmInfoEvent.getBuildDate().equals(ErrUtil.getDate("Apr 27 2022 11:51:42"))) {
                        release = "17.0.3+6-LTS-2";
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!headerEvents.isEmpty()) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJreVersion()) {
                    String regEx = "^.+\\(" + JdkRegEx.VERSION_STRING + "\\)( \\(build " + JdkRegEx.BUILD_STRING
                            + "\\))?.*$";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(4) != null) {
                            release = matcher.group(4);
                        ***REMOVED*** else if (matcher.group(1) != null) {
                            // Add leading "1."
                            if (matcher.group(1).matches("^[678].+$")) {
                                release = "1." + matcher.group(1);
                            ***REMOVED*** else {
                                release = matcher.group(1);
                            ***REMOVED***
                        ***REMOVED***
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return release;
    ***REMOVED***

    /**
     * @return The JDK rpm RHEL version, or null if unknown.
     */
    public String getJdkRhelVersion() {
        String jdkRhelVersion = null;
        String rpmDirectory = getRpmDirectory();
        if (rpmDirectory != null) {
            Pattern pattern = Pattern.compile(JdkRegEx.RH_RPM_DIR);
            Matcher matcher = pattern.matcher(rpmDirectory);
            if (matcher.find()) {
                if (matcher.group(2) != null) {
                    // JDK8
                    jdkRhelVersion = matcher.group(3) + "." + matcher.group(5);
                ***REMOVED*** else if (matcher.group(8) != null) {
                    // JDK11
                    jdkRhelVersion = matcher.group(10) + "." + matcher.group(12);
                ***REMOVED*** else if (matcher.group(13) != null) {
                    // JDK17
                    jdkRhelVersion = matcher.group(15) + "." + matcher.group(16);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return jdkRhelVersion;
    ***REMOVED***

    /**
     * @return The JVM options, or null if none exists.
     */
    public String getJvmArgs() {
        String jvmArgs = null;
        if (!vmArgumentsEvents.isEmpty()) {
            Iterator<VmArgumentsEvent> iterator = vmArgumentsEvents.iterator();
            while (iterator.hasNext()) {
                VmArgumentsEvent event = iterator.next();
                if (event.isJvmArgs()) {
                    jvmArgs = event.getValue();
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return jvmArgs;
    ***REMOVED***

    /**
     * Free memory as reported by the JVM <code>MemoryEvent</code>.
     * 
     * Note that free memory does not include Buffers or Cached memory, which can be reclaimed at any time. Therefore,
     * low free memory does not necessarily indicate swapping or out of memory is imminent.
     * 
     * @return The total free physical memory as reported by the JVM in <code>Constants.PRECISION_REPORTING</code>
     *         units.
     */
    public long getJvmMemFree() {
        long physicalMemoryFree = Long.MIN_VALUE;
        if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        physicalMemoryFree = JdkUtil.convertSize(Long.parseLong(matcher.group(7)),
                                matcher.group(9).charAt(0), Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return physicalMemoryFree;
    ***REMOVED***

    /**
     * @return Estimated JVM initial memory in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getJvmMemoryInitial() {
        long jvmMemoryInitial = Long.MIN_VALUE;
        if (getHeapMaxSize() > 0) {
            jvmMemoryInitial = getHeapInitialSize();
        ***REMOVED***
        if (getMetaspaceMaxSize() > 0) {
            if (jvmMemoryInitial > 0) {
                jvmMemoryInitial += getMetaspaceMaxSize();
            ***REMOVED*** else {
                jvmMemoryInitial = getMetaspaceMaxSize();
            ***REMOVED***
        ***REMOVED***
        // Thread stack space
        if (getThreadStackMemory() > 0) {
            if (jvmMemoryInitial > 0) {
                jvmMemoryInitial += getThreadStackMemory();
            ***REMOVED*** else {
                jvmMemoryInitial = getThreadStackMemory();
            ***REMOVED***
        ***REMOVED***
        // code cache
        if (jvmMemoryInitial > 0) {
            jvmMemoryInitial += getCodeCacheSize();
        ***REMOVED*** else {
            jvmMemoryInitial = getCodeCacheSize();
        ***REMOVED***
        // Direct memory
        if (jvmMemoryInitial > 0) {
            jvmMemoryInitial += getDirectMemoryMaxSize();
        ***REMOVED*** else {
            jvmMemoryInitial = getDirectMemoryMaxSize();
        ***REMOVED***
        return jvmMemoryInitial;
    ***REMOVED***

    /**
     * @return Estimated JVM maximum memory in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getJvmMemoryMax() {
        long jvmMemoryMax = Long.MIN_VALUE;
        if (getHeapMaxSize() > 0) {
            jvmMemoryMax = getHeapMaxSize();
        ***REMOVED***
        if (getMetaspaceMaxSize() > 0) {
            if (jvmMemoryMax > 0) {
                jvmMemoryMax += getMetaspaceMaxSize();
            ***REMOVED*** else {
                jvmMemoryMax = getMetaspaceMaxSize();
            ***REMOVED***
        ***REMOVED***
        // Thread stack space
        if (getThreadStackMemory() > 0) {
            if (jvmMemoryMax > 0) {
                jvmMemoryMax += getThreadStackMemory();
            ***REMOVED*** else {
                jvmMemoryMax = getThreadStackMemory();
            ***REMOVED***
        ***REMOVED***
        // code cache
        if (jvmMemoryMax > 0) {
            jvmMemoryMax += getCodeCacheSize();
        ***REMOVED*** else {
            jvmMemoryMax = getCodeCacheSize();
        ***REMOVED***
        // Direct memory
        if (jvmMemoryMax > 0) {
            jvmMemoryMax += getDirectMemoryMaxSize();
        ***REMOVED*** else {
            jvmMemoryMax = getDirectMemoryMaxSize();
        ***REMOVED***
        return jvmMemoryMax;
    ***REMOVED***

    /**
     * @return The total physical memory reported by the JVM in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getJvmMemTotal() {
        long physicalMemory = Long.MIN_VALUE;
        if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        physicalMemory = JdkUtil.convertSize(Long.parseLong(matcher.group(4)),
                                matcher.group(6).charAt(0), Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return physicalMemory;
    ***REMOVED***

    public JvmOptions getJvmOptions() {
        return jvmOptions;
    ***REMOVED***

    /**
     * @return The total available swap as reported by the JVM in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getJvmSwap() {
        long swap = Long.MIN_VALUE;
        if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(11) != null && matcher.group(13) != null) {
                            swap = JdkUtil.convertSize(Long.parseLong(matcher.group(11)), matcher.group(13).charAt(0),
                                    Constants.PRECISION_REPORTING);
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return swap;
    ***REMOVED***

    /**
     * @return The total free swap as reported by the JVM in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getJvmSwapFree() {
        long swapFree = Long.MIN_VALUE;
        if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(14) != null && matcher.group(16) != null) {
                            swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(14)),
                                    matcher.group(16).charAt(0), Constants.PRECISION_REPORTING);
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return swapFree;
    ***REMOVED***

    /**
     * Parse JVM user from hsperfdata string. For example, the following user is jb_admin:
     * 
     * 7ff0f61d2000-7ff0f61da000 rw-s 00000000 fd:01 33563495 /tmp/hsperfdata_jb_admin/92333
     * 
     * @return The user the JVM process is running under.
     */
    public String getJvmUser() {
        String jvmUser = null;
        if (!dynamicLibraryEvents.isEmpty()) {
            String regExHsPerfData = System.getProperty("file.separator") + "hsperfdata_([^"
                    + System.getProperty("file.separator") + "]+)";
            Pattern pattern = Pattern.compile(regExHsPerfData);
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getFilePath() != null) {
                    Matcher matcher = pattern.matcher(event.getFilePath());
                    if (matcher.find()) {
                        jvmUser = matcher.group(1);
                        break;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return jvmUser;
    ***REMOVED***

    /**
     * @return max_map_count, or a negative value if undefined.
     */
    public Long getMaxMapCount() {
        Long maxMapCount = Long.MIN_VALUE;
        if (maxMapCountEvent != null) {
            maxMapCount = maxMapCountEvent.getLimit();
        ***REMOVED***
        return maxMapCount;
    ***REMOVED***

    public MaxMapCountEvent getMaxMapCountEvent() {
        return maxMapCountEvent;
    ***REMOVED***

    public List<MeminfoEvent> getMeminfoEvents() {
        return meminfoEvents;
    ***REMOVED***

    public List<MemoryEvent> getMemoryEvents() {
        return memoryEvents;
    ***REMOVED***

    /**
     * @return The total metaspace allocation in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getMetaspaceAllocation() {
        long metaspaceAllocation = Long.MIN_VALUE;
        if (!heapEvents.isEmpty()) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.isMetaspace()) {
                    pattern = Pattern.compile(JdkRegEx.METASPACE_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(8));
                        if (matcher.group(10) != null) {
                            fromUnits = matcher.group(10).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        metaspaceAllocation = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                        break;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return metaspaceAllocation;
    ***REMOVED***

    /**
     * @return The metaspace max size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    /**
     * @return The Metaspace maximum size.
     */
    public long getMetaspaceMaxSize() {
        long metaspaceMaxSize = Long.MIN_VALUE;
        if (jvmOptions != null && jvmOptions.getMaxMetaspaceSize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getMaxMetaspaceSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                metaspaceMaxSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
            ***REMOVED***
        ***REMOVED***
        // If max metaspace size not set (recommended), get from <code>HeapEvent</code>
        if (metaspaceMaxSize == Long.MIN_VALUE) {
            if (!heapEvents.isEmpty()) {
                Iterator<HeapEvent> iterator = heapEvents.iterator();
                char fromUnits;
                long value;
                Pattern pattern = null;
                Matcher matcher = null;
                while (iterator.hasNext()) {
                    HeapEvent event = iterator.next();
                    if (event.isMetaspace()) {
                        pattern = Pattern.compile(JdkRegEx.METASPACE_SIZE);
                        matcher = pattern.matcher(event.getLogEntry());
                        if (matcher.find()) {
                            value = Long.parseLong(matcher.group(11));
                            if (matcher.group(13) != null) {
                                fromUnits = matcher.group(13).charAt(0);
                            ***REMOVED*** else {
                                fromUnits = 'B';
                            ***REMOVED***
                            metaspaceMaxSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return metaspaceMaxSize;
    ***REMOVED***

    /**
     * @return The total metaspace used in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getMetaspaceUsed() {
        long metaspaceUsed = Long.MIN_VALUE;
        if (!heapEvents.isEmpty()) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.isMetaspace()) {
                    pattern = Pattern.compile(JdkRegEx.METASPACE_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        metaspaceUsed = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                        break;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return metaspaceUsed;
    ***REMOVED***

    /**
     * @return The number of mmap resources in a deleted state.
     */
    public int getMmapDeletedCount() {
        int mmapDeletedCount = 0;
        if (!dynamicLibraryEvents.isEmpty()) {
            String regExMmapDeleted = "^.+ \\(deleted\\)$";
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry().matches(regExMmapDeleted)) {
                    mmapDeletedCount++;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return mmapDeletedCount;
    ***REMOVED***

    public NarrowKlassEvent getNarrowKlassEvent() {
        return narrowKlassEvent;
    ***REMOVED***

    public List<NativeMemoryTrackingEvent> getNativeMemoryTrackingEvents() {
        return nativeMemoryTrackingEvents;
    ***REMOVED***

    /**
     * @param stackFrame
     *            A stack frame in the stack.
     * @return The next stack frame, or null if the stack frame passed in does not exist or is at the bottom of the
     *         stack.
     */
    public String getNextStackFrame(String stackFrame) {
        String nextStackFrame = null;
        int stackIndex = 0;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^" + stackFrame + "$") && stackIndex < stackEvents.size()) {
                nextStackFrame = stackEvents.get(stackIndex + 1).getLogEntry();
                break;
            ***REMOVED***
            stackIndex++;
        ***REMOVED***
        return nextStackFrame;
    ***REMOVED***

    public List<OsEvent> getOsEvents() {
        return osEvents;
    ***REMOVED***

    /**
     * Available memory is an estimate of how much physical memory is available without swapping. It does not include
     * the memory used by the JVM process, as it is before the JVM process exits and its memory is freed.
     * 
     * @return The total available physical memory as reported by the OS in <code>Constants.PRECISION_REPORTING</code>
     *         units.
     */
    public long getOsMemAvailable() {
        long memAvailable = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "MemAvailable:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    memAvailable = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return memAvailable;
    ***REMOVED***

    /**
     * Free memory as reported by the OS. Free memory does not include Buffers or Cached memory, which can be reclaimed
     * at any time. Therefore, low free memory does not necessarily indicate swapping or out of memory is imminent.
     * 
     * @return The total free physical memory as reported by the OS in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getOsMemFree() {
        long memFree = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "MemFree:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    memFree = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Pattern pattern = Pattern.compile(MemoryEvent.REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        memFree = JdkUtil.convertSize(Long.parseLong(matcher.group(6)), matcher.group(8).charAt(0),
                                Constants.PRECISION_REPORTING);
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return memFree;
    ***REMOVED***

    /**
     * @return The total available physical memory reported by the OS in <code>Constants.PRECISION_REPORTING</code>
     *         units.
     */
    public long getOsMemTotal() {
        long memTotal = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "MemTotal:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    memTotal = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Pattern pattern = Pattern.compile(MemoryEvent.REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        memTotal = JdkUtil.convertSize(Long.parseLong(matcher.group(3)), matcher.group(5).charAt(0),
                                Constants.PRECISION_REPORTING);
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return memTotal;
    ***REMOVED***

    /**
     * The OS string. For example:
     * 
     * Red Hat Enterprise Linux Server release 7.7 (Maipo)
     * 
     * @return OS string, of null if it doesn't exist.
     */
    public String getOsString() {
        String osString = null;
        if (!osEvents.isEmpty()) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = OsEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(2) != null) {
                            osString = matcher.group(4).trim();
                        ***REMOVED*** else {
                            // OS is on separate line
                            event = iterator.next();
                            osString = event.getLogEntry();
                        ***REMOVED***
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (hostEvent != null && hostEvent.getOsString() != null) {
            osString = hostEvent.getOsString();
        ***REMOVED***
        return osString;
    ***REMOVED***

    /**
     * @return The total available swap as reported by the JVM in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getOsSwap() {
        long swap = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "SwapTotal:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    swap = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Pattern pattern = Pattern.compile(MemoryEvent.REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find() && matcher.group(9) != null) {
                        swap = JdkUtil.convertSize(Long.parseLong(matcher.group(10)), matcher.group(12).charAt(0),
                                Constants.PRECISION_REPORTING);
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return swap;
    ***REMOVED***

    /**
     * @return The total free swap as reported by the JVM in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getOsSwapFree() {
        long swapFree = Long.MIN_VALUE;
        if (!meminfoEvents.isEmpty()) {
            String regexMemTotal = "SwapFree:[ ]{0,***REMOVED***(\\d{1,***REMOVED***) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.PRECISION_REPORTING);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!memoryEvents.isEmpty()) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Pattern pattern = Pattern.compile(MemoryEvent.REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find() && matcher.group(9) != null) {
                        swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(13)), matcher.group(15).charAt(0),
                                Constants.PRECISION_REPORTING);
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return swapFree;
    ***REMOVED***

    /**
     * @return <code>OsType</code>
     */
    public OsType getOsType() {
        OsType osType = OsType.UNKNOWN;
        String osString = getOsString();
        if (osString != null) {
            if (osString.matches(".*Linux.*")) {
                osType = OsType.LINUX;
            ***REMOVED*** else if (osString.matches("^Windows.+$")) {
                osType = OsType.WINDOWS;
            ***REMOVED*** else if (osString.matches(".+Solaris.+")) {
                osType = OsType.SOLARIS;
            ***REMOVED***
        ***REMOVED*** else if (!headerEvents.isEmpty()) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJavaVm()) {
                    osType = he.getOsType();
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return osType;
    ***REMOVED***

    /**
     * @return <code>OsVendor</code>
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNKNOWN;
        if (!osEvents.isEmpty()) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    ***REMOVED***
                    if (event.getLogEntry().matches("^.*Red Hat.+$")) {
                        osVendor = OsVendor.REDHAT;
                    ***REMOVED*** else if (event.getLogEntry().matches(".*Windows.+$")) {
                        osVendor = OsVendor.MICROSOFT;
                    ***REMOVED*** else if (event.getLogEntry().matches("^.+Oracle.+$")) {
                        osVendor = OsVendor.ORACLE;
                    ***REMOVED*** else if (event.getLogEntry().matches("^.*CentOS.+$")) {
                        osVendor = OsVendor.CENTOS;
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        return osVendor;
    ***REMOVED***

    /**
     * @return <code>OsVersion</code>
     */
    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNKNOWN;
        if (!osEvents.isEmpty()) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS on next line
                        event = iterator.next();
                    ***REMOVED***
                    if (event.getLogEntry().matches("^.*Red Hat Enterprise Linux (Server|Workstation) release 6.+$")) {
                        osVersion = OsVersion.RHEL6;
                    ***REMOVED*** else if (event.getLogEntry()
                            .matches("^.*Red Hat Enterprise Linux (Server|Workstation) release 7.+$")) {
                        osVersion = OsVersion.RHEL7;
                    ***REMOVED*** else if (event.getLogEntry().matches("^.*Red Hat Enterprise Linux release 8.+$")) {
                        osVersion = OsVersion.RHEL8;
                    ***REMOVED*** else if (event.getLogEntry().matches("^.*CentOS Linux release 6.+$")) {
                        osVersion = OsVersion.CENTOS6;
                    ***REMOVED*** else if (event.getLogEntry().matches("^.*CentOS Linux release 7.+$")) {
                        osVersion = OsVersion.CENTOS7;
                    ***REMOVED*** else if (event.getLogEntry().matches("^.*CentOS Linux release 8.+$")) {
                        osVersion = OsVersion.CENTOS8;
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (osVersion == OsVersion.UNKNOWN && unameEvent != null) {
            osVersion = unameEvent.getOsVersion();
        ***REMOVED***
        return osVersion;
    ***REMOVED***

    /**
     * @return pid_max, or a negative value if undefined.
     */
    public Long getPidMax() {
        Long pidMax = Long.MIN_VALUE;
        if (pidMaxEvent != null) {
            pidMax = pidMaxEvent.getLimit();
        ***REMOVED***
        return pidMax;
    ***REMOVED***

    public PidMaxEvent getPidMaxEvent() {
        return pidMaxEvent;
    ***REMOVED***

    /**
     * @return The RHEL version, or null if unknown.
     */
    public String getRhelVersion() {
        String rhelVersion = null;
        String osString = getOsString();
        if (osString != null) {
            String regex = "^.*Red Hat Enterprise Linux (Server )?release (\\d\\.\\d{1,2***REMOVED***).*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(osString);
            if (matcher.find()) {
                rhelVersion = matcher.group(2);
            ***REMOVED***
        ***REMOVED***
        return rhelVersion;
    ***REMOVED***

    public RlimitEvent getRlimitEvent() {
        return rlimitEvent;
    ***REMOVED***

    /**
     * @return The rpm directory name, or null if not an rpm install.
     *
     *         For example:
     * 
     *         java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64
     * 
     *         java-11-openjdk-11.0.7.10-4.el7_8.x86_64
     */
    public String getRpmDirectory() {
        String rpmDirectory = null;
        if (getOsType() == OsType.LINUX) {
            if (!dynamicLibraryEvents.isEmpty()) {
                Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
                while (iterator.hasNext()) {
                    DynamicLibraryEvent event = iterator.next();
                    if (event.getFilePath() != null) {
                        Pattern pattern = null;
                        Matcher matcher = null;
                        if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            ***REMOVED***
                            break;
                        ***REMOVED*** else if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            ***REMOVED***
                            break;
                        ***REMOVED*** else if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK17_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK17_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            ***REMOVED***
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return rpmDirectory;
    ***REMOVED***

    public SigInfoEvent getSigInfoEvent() {
        return sigInfoEvent;
    ***REMOVED***

    /**
     * @return Signal code.
     */
    public SignalCode getSignalCode() {
        SignalCode signalCode = SignalCode.UNKNOWN;
        if (sigInfoEvent != null) {
            signalCode = sigInfoEvent.getSignalCode();
        ***REMOVED***
        return signalCode;
    ***REMOVED***

    /**
     * @return Signal number.
     */
    public SignalNumber getSignalNumber() {
        SignalNumber signalNumber = SignalNumber.UNKNOWN;
        if (sigInfoEvent != null) {
            signalNumber = sigInfoEvent.getSignalNumber();
        ***REMOVED***
        return signalNumber;
    ***REMOVED***

    public List<StackEvent> getStackEvents() {
        return stackEvents;
    ***REMOVED***

    /**
     * @param i
     *            The stack frame index (1 = top).
     * @return The stack frame at the specified position.
     */
    public String getStackFrame(int i) {
        String stackFrame = null;
        int stackIndex = 1;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (!event.getLogEntry().matches("^(Stack|(Java|Native) frames):.+$")) {
                if (stackIndex == i) {
                    stackFrame = event.getLogEntry();
                    break;
                ***REMOVED***
                stackIndex++;
            ***REMOVED***
        ***REMOVED***
        return stackFrame;
    ***REMOVED***

    /**
     * @return The top stack frame, or null if none exists.
     */
    public String getStackFrameTop() {
        String stackFrameTop = null;
        Iterator<StackEvent> iteratorStack = stackEvents.iterator();
        while (iteratorStack.hasNext()) {
            StackEvent event = iteratorStack.next();
            if (event.getLogEntry().matches("^(A|C|j|J|v|V)[ ]{1,2***REMOVED***.+$")) {
                stackFrameTop = event.getLogEntry();
                break;
            ***REMOVED***
        ***REMOVED***
        if (stackFrameTop == null && !headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iteratorHeader = headerEvents.iterator();
            while (iteratorHeader.hasNext()) {
                HeaderEvent he = iteratorHeader.next();
                if (he.isProblematicFrame()) {
                    stackFrameTop = he.getLogEntry().substring(2, he.getLogEntry().length());
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return stackFrameTop;
    ***REMOVED***

    /**
     * @return The top Compile Java Code (J) stack frame, or null if none exists.
     */
    public String getStackFrameTopCompiledJavaCode() {
        String stackFrameTopCompiledJavaCode = null;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^J[ ]{1,2***REMOVED***.+$")) {
                stackFrameTopCompiledJavaCode = event.getLogEntry();
                break;
            ***REMOVED***
        ***REMOVED***
        return stackFrameTopCompiledJavaCode;
    ***REMOVED***

    /**
     * @return The top Java stack frame (J=compiled Java code, j=interpreted), or null if none exists.
     */
    public String getStackFrameTopJava() {
        String stackFrameTopJava = null;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^[jJ][ ]{1,2***REMOVED***.+$")) {
                stackFrameTopJava = event.getLogEntry();
                break;
            ***REMOVED***
        ***REMOVED***
        return stackFrameTopJava;
    ***REMOVED***

    /**
     * @return The stack free space in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getStackFreeSpace() {
        long stackFreeSpace = Long.MIN_VALUE;
        if (!stackEvents.isEmpty()) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isHeader()) {
                    stackFreeSpace = event.getStackFreeSpace();
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return stackFreeSpace;
    ***REMOVED***

    public List<StatisticsEvent> getStatisticsEvents() {
        return statisticsEvents;
    ***REMOVED***

    /**
     * @return The storage device where the JDK is installed.
     */
    public Device getStorageDevice() {
        Device device = Device.UNKNOWN;
        if (getOsType() == OsType.LINUX && !dynamicLibraryEvents.isEmpty()) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getFilePath() != null && event.getFilePath().matches("^.+libjvm\\.so$")) {
                    device = event.getDevice();
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return device;
    ***REMOVED***

    public List<ThreadEvent> getThreadEvents() {
        return threadEvents;
    ***REMOVED***

    /**
     * @return threads-max, or a negative value if undefined.
     */
    public Long getThreadsMax() {
        Long threadsMax = Long.MIN_VALUE;
        if (threadsMaxEvent != null) {
            threadsMax = threadsMaxEvent.getLimit();
        ***REMOVED***
        return threadsMax;
    ***REMOVED***

    public ThreadsMaxEvent getThreadsMaxEvent() {
        return threadsMaxEvent;
    ***REMOVED***

    /**
     * @return The thread memory in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long getThreadStackMemory() {
        long threadStackMemory = Long.MIN_VALUE;
        if (getJavaThreadCount() > 0) {
            BigDecimal memoryPerThread = new BigDecimal(getThreadStackSize());
            BigDecimal threads = new BigDecimal(getJavaThreadCount());
            threadStackMemory = memoryPerThread.multiply(threads).longValue();
            threadStackMemory = JdkUtil.convertSize(threadStackMemory, 'K', Constants.PRECISION_REPORTING);
        ***REMOVED***
        return threadStackMemory;
    ***REMOVED***

    /**
     * @return The stack size reserved (kilobytes).
     */
    public long getThreadStackSize() {
        long stackSize;
        switch (getArch()) {
        case PPC64:
        case PPC64LE:
            stackSize = 2048;
            break;
        case SPARC:
        case I86PC:
        case X86:
        case X86_64:
        case UNKNOWN:
        default:
            stackSize = 1024;
            break;
        ***REMOVED***
        // 1st check [Global flags]
        if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExThreadStackSize = "^.+intx ThreadStackSize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                Pattern pattern = Pattern.compile(regExThreadStackSize);
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    stackSize = Long.parseLong(matcher.group(1));
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (jvmOptions != null && jvmOptions.getThreadStackSize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile("^-(X)?(ss|X:ThreadStackSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$");
            Matcher matcher = pattern.matcher(jvmOptions.getThreadStackSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(4));
                if (matcher.group(2) != null && matcher.group(2).equals("X:ThreadStackSize=")) {
                    // value is in kilobytes, multiply by 1024
                    value = JdkUtil.convertSize(value, 'K', 'B');
                ***REMOVED***
                if (matcher.group(5) != null) {
                    fromUnits = matcher.group(5).charAt(0);
                ***REMOVED*** else {
                    fromUnits = 'B';
                ***REMOVED***
                stackSize = JdkUtil.convertSize(value, fromUnits, 'K');
            ***REMOVED***
        ***REMOVED***
        return stackSize;

    ***REMOVED***

    public TimeElapsedTimeEvent getTimeElapsedTimeEvent() {
        return timeElapsedTimeEvent;
    ***REMOVED***

    public UnameEvent getUnameEvent() {
        return unameEvent;
    ***REMOVED***

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    ***REMOVED***

    /**
     * @return The USERNAME environment variable.
     */
    public String getUsername() {
        String username = null;
        if (!environmentVariablesEvents.isEmpty()) {
            String regExUsername = "^USERNAME=(.+)$";
            Pattern pattern = Pattern.compile(regExUsername);
            Iterator<EnvironmentVariablesEvent> iterator = environmentVariablesEvents.iterator();
            while (iterator.hasNext()) {
                EnvironmentVariablesEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    username = matcher.group(1);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return username;
    ***REMOVED***

    public List<VirtualizationInfoEvent> getVirtualizationInfoEvents() {
        return virtualizationInfoEvents;
    ***REMOVED***

    public List<VmArgumentsEvent> getVmArgumentsEvents() {
        return vmArgumentsEvents;
    ***REMOVED***

    public VmOperationEvent getVmOperationEvent() {
        return vmOperationEvent;
    ***REMOVED***

    /**
     * @return The VM state.
     */
    public String getVmState() {
        String vmState = null;
        if (vmStateEvent != null) {
            vmState = vmStateEvent.getState();
        ***REMOVED***
        return vmState;
    ***REMOVED***

    public VmStateEvent getVmStateEvent() {
        return vmStateEvent;
    ***REMOVED***

    /**
     * @return true if there is a cgroup memory limit, false otherwise.
     */
    public boolean haveCgroupMemoryLimit() {
        boolean isCgroupMemoryLimit = false;
        if (!containerInfoEvents.isEmpty()) {
            Iterator<ContainerInfoEvent> iterator = containerInfoEvents.iterator();
            while (iterator.hasNext()) {
                ContainerInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^memory_limit_in_bytes: \\d{1,***REMOVED***$")) {
                    isCgroupMemoryLimit = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isCgroupMemoryLimit;
    ***REMOVED***

    /**
     * @return true if the stack contains frames, false otherwise.
     */
    public boolean haveFramesInStack() {
        boolean haveFramesInStack = false;
        if (!stackEvents.isEmpty()) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isFrame()) {
                    haveFramesInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveFramesInStack;
    ***REMOVED***

    /**
     * @return true if JDK debug symbols are installed, false otherwise.
     */
    public boolean haveJdkDebugSymbols() {
        boolean haveJdkDebugSymbols = false;
        if (!headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iterator1 = headerEvents.iterator();
            while (iterator1.hasNext()) {
                HeaderEvent he = iterator1.next();
                if (he.isProblematicFrame()) {
                    haveJdkDebugSymbols = he.getLogEntry().matches("^***REMOVED*** V  \\[.+\\].+$");
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (!haveJdkDebugSymbols) {
            if (!stackEvents.isEmpty()) {
                Iterator<StackEvent> iterator2 = stackEvents.iterator();
                while (iterator2.hasNext() && !haveJdkDebugSymbols) {
                    StackEvent se = iterator2.next();
                    if (se.isVmFrame()) {
                        haveJdkDebugSymbols = se.getLogEntry().matches("^V  \\[.+\\].+$")
                                && !se.getLogEntry().matches("^V  \\[.+\\]  JVM_DoPrivileged.+$");
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveJdkDebugSymbols;
    ***REMOVED***

    /**
     * @return true if there were LinkageErrors before the crash, false otherwise.
     */
    public boolean haveLinkageError() {
        boolean haveLinkageError = false;
        if (!exceptionCountsEvents.isEmpty()) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^LinkageErrors=\\d{1,***REMOVED***$")) {
                    haveLinkageError = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveLinkageError;
    ***REMOVED***

    /**
     * @return true if there were "OutOfMemoryError: Compressed class space" or caught and thrown before the crash,
     *         false otherwise.
     */
    public boolean haveOomeThrownCompressedClassSpace() {
        boolean haveOomeThrownCompressedClassSpace = false;
        if (!exceptionCountsEvents.isEmpty()) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader() && exceptionCountsEvent.getLogEntry()
                        .matches("^OutOfMemoryError class_metaspace_errors=\\d{1,***REMOVED***$")) {
                    haveOomeThrownCompressedClassSpace = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveOomeThrownCompressedClassSpace;
    ***REMOVED***

    /**
     * @return true if there were OutOfMemoryError other than "Metaspace" or "Compressed class space" caught and thrown
     *         before the crash, false otherwise.
     */
    public boolean haveOomeThrownJavaHeap() {
        boolean haveOomeThrownJavaHeap = false;
        if (!exceptionCountsEvents.isEmpty()) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^OutOfMemoryError java_heap_errors=\\d{1,***REMOVED***$")) {
                    haveOomeThrownJavaHeap = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveOomeThrownJavaHeap;
    ***REMOVED***

    /**
     * @return true if there were "OutOfMemoryError: Metaspace" or caught and thrown before the crash, false otherwise.
     */
    public boolean haveOomeThrownMetaspace() {
        boolean haveOomeThrownMetaspace = false;
        if (!exceptionCountsEvents.isEmpty()) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^OutOfMemoryError metaspace_errors=\\d{1,***REMOVED***$")) {
                    haveOomeThrownMetaspace = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveOomeThrownMetaspace;
    ***REMOVED***

    /**
     * @return true if there were StackOverflowErrors before the crash, false otherwise.
     */
    public boolean haveStackOverFlowError() {
        boolean haveStackOverFlowError = false;
        if (!exceptionCountsEvents.isEmpty()) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^StackOverflowErrors=\\d{1,***REMOVED***$")) {
                    haveStackOverFlowError = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveStackOverFlowError;
    ***REMOVED***

    /**
     * @return true if the stack contains JDK VM frame code, false otherwise.
     */
    public boolean haveVmCodeInStack() {
        boolean haveVmCodeInStack = false;
        //
        if (stackEvents.size() > 2) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmFrame() || event.isVmGeneratedCodeFrame()) {
                    haveVmCodeInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmCodeInStack;
    ***REMOVED***

    /**
     * @return true if the header contains JDK VM frame code, false otherwise.
     */
    public boolean haveVmFrameInHeader() {
        boolean haveVmFrameInHeader = false;
        if (!headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent event = iterator.next();
                if (event.isVmFrame()) {
                    haveVmFrameInHeader = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmFrameInHeader;
    ***REMOVED***

    /**
     * @return true if the stack contains JDK VM frame, false otherwise.
     */
    public boolean haveVmFrameInStack() {
        boolean haveVmFrameInStack = false;
        if (!stackEvents.isEmpty()) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmFrame()) {
                    haveVmFrameInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmFrameInStack;
    ***REMOVED***

    /**
     * @return true if the stack contains JDK VM generated code frame, false otherwise.
     */
    public boolean haveVmGeneratedCodeFrameInStack() {
        boolean haveVmGeneratedCodeFrameInStack = false;
        if (!stackEvents.isEmpty()) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmGeneratedCodeFrame()) {
                    haveVmGeneratedCodeFrameInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmGeneratedCodeFrameInStack;
    ***REMOVED***

    /**
     * @return true if the JDK is 64-bit, false otherwise.
     */
    public boolean is64Bit() {
        boolean is64Bit = true;
        if (getArch() == Arch.X86) {
            is64Bit = false;
        ***REMOVED***
        return is64Bit;
    ***REMOVED***

    /**
     * AdoptOpenJDK has the same release versions as the RH build of OpenJDK but have a different build date/time and
     * builder string ("jenkins").
     * 
     * @return true if the fatal error log was created by a JDK build string used by AdoptOpenJDK, false otherwise.
     */
    public boolean isAdoptOpenJdkBuildString() {
        return vmInfoEvent != null && (vmInfoEvent.getBuiltBy() == BuiltBy.JENKINS);
    ***REMOVED***

    /**
     * @return true if there is evidence the crash happens in a container environment, false otherwise.
     */
    public boolean isContainer() {
        boolean isContainer = false;
        if (!containerInfoEvents.isEmpty() || getJvmSwap() == 0) {
            isContainer = true;
        ***REMOVED***
        return isContainer;
    ***REMOVED***

    /**
     * @param error
     *            The string to search for.
     * @return True if the crash error contains the string, false otherwise
     */
    public boolean isError(String error) {
        boolean isError = false;
        Pattern pattern = Pattern.compile(error);
        Matcher matcher = pattern.matcher(getError());
        if (matcher.find()) {
            isError = true;
        ***REMOVED***
        return isError;
    ***REMOVED***

    /**
     * @return true if HyperV environment, false otherwise.
     */
    public boolean isHyperVEnvironment() {
        boolean isVMWareEnvironment = false;
        if (!containerInfoEvents.isEmpty()) {
            Iterator<ContainerInfoEvent> iterator = containerInfoEvents.iterator();
            while (iterator.hasNext()) {
                ContainerInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^HyperV virtualization detected$")) {
                    isVMWareEnvironment = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isVMWareEnvironment;
    ***REMOVED***

    /**
     * @param regEx
     *            A regular expression.
     * @return true if the regex is in the header, false otherwise.
     */
    public boolean isInHeader(String regEx) {
        boolean isInHeader = false;
        if (!headerEvents.isEmpty()) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent event = iterator.next();
                if (event.getLogEntry().matches("^.*" + regEx + ".*$")) {
                    isInHeader = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isInHeader;
    ***REMOVED***

    /**
     * @param classRegEx
     *            A class name as a regular expression.
     * @return true if the class is in the stack, false otherwise.
     */
    public boolean isInStack(String classRegEx) {
        boolean isInStack = false;
        if (!stackEvents.isEmpty()) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.getLogEntry().matches("^.+" + classRegEx + ".+$")) {
                    isInStack = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isInStack;
    ***REMOVED***

    /**
     * @return true if the fatal error log was created by a JDK that is a Long Term Support (LTS) version, false
     *         otherwise.
     */
    public boolean isJdkLts() {
        boolean isJdkLts = false;
        switch (getJavaSpecification()) {
        case JDK6:
        case JDK7:
        case JDK8:
        case JDK11:
        case JDK17:
            isJdkLts = true;
            break;
        case JDK9:
        case JDK10:
        case JDK12:
        case JDK13:
        case JDK14:
        case JDK15:
        case JDK16:
        case UNKNOWN:
        default:
            break;
        ***REMOVED***
        return isJdkLts;
    ***REMOVED***

    /**
     * @return true if the crash happens in JNA code, false otherwise.
     */
    public boolean isJnaCrash() {
        boolean isJnaCrash = false;
        if (getStackEvents() != null && getStackEvents().size() >= 2) {
            String stackFrame2 = getStackFrame(2);
            if (stackFrame2 != null && stackFrame2.matches("^[jJ]( \\d{1,***REMOVED***)?[ ]{1,2***REMOVED***com\\.sun\\.jna\\..+$")) {
                isJnaCrash = true;
            ***REMOVED***
        ***REMOVED***
        return isJnaCrash;
    ***REMOVED***

    /**
     * @return true if the JDK build date/time is a Red Hat build date/time, false otherwise.
     */
    public boolean isRhBuildDate() {
        boolean isRhBuildDate = false;
        if (getJdkBuildDate() != null) {
            String releaseString = getJdkReleaseString();
            String rpmDirectory = getRpmDirectory();
            if (getOsType() == OsType.LINUX) {
                switch (getJavaSpecification()) {
                case JDK8:
                    isRhBuildDate = (JdkUtil.JDK8_RHEL_ZIPS.containsKey(releaseString)
                            && JdkUtil.isBuildDateKnown(JdkUtil.JDK8_RHEL_ZIPS.get(releaseString).getBuildDate())
                            && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK8_RHEL_ZIPS.get(releaseString).getBuildDate()) == 0)
                            || (JdkUtil.JDK8_RHEL6_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK8_RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK8_RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK8_RHEL7_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK8_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK8_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK8_RHEL7_PPC64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK8_RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK8_RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK8_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK8_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK8_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK8_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK8_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK8_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0);
                    break;
                case JDK11:
                    isRhBuildDate = (JdkUtil.JDK11_RHEL_ZIPS.containsKey(releaseString)
                            && JdkUtil.isBuildDateKnown(JdkUtil.JDK11_RHEL_ZIPS.get(releaseString).getBuildDate())
                            && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL_ZIPS.get(releaseString).getBuildDate()) == 0)
                            || (JdkUtil.JDK11_RHEL7_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK11_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK11_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK11_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK11_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK11_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK11_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK11_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK11_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0);
                    break;
                case JDK17:
                    isRhBuildDate = (JdkUtil.JDK17_RHEL_ZIPS.containsKey(releaseString)
                            && JdkUtil.isBuildDateKnown(JdkUtil.JDK17_RHEL_ZIPS.get(releaseString).getBuildDate())
                            && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK17_RHEL_ZIPS.get(releaseString).getBuildDate()) == 0)
                            || (JdkUtil.JDK17_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK17_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK17_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0)
                            || (JdkUtil.JDK17_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                                    && JdkUtil.isBuildDateKnown(
                                            JdkUtil.JDK17_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate())
                                    && getJdkBuildDate().compareTo(
                                            JdkUtil.JDK17_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0);
                    break;
                case JDK6:
                case JDK7:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getOsType() == OsType.WINDOWS) {
                switch (getJavaSpecification()) {
                case JDK8:
                    isRhBuildDate = JdkUtil.JDK8_WINDOWS_ZIPS.containsKey(releaseString)
                            && JdkUtil.isBuildDateKnown(JdkUtil.JDK8_WINDOWS_ZIPS.get(releaseString).getBuildDate())
                            && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK8_WINDOWS_ZIPS.get(releaseString).getBuildDate()) == 0;
                    break;
                case JDK11:
                    isRhBuildDate = JdkUtil.JDK11_WINDOWS_ZIPS.containsKey(releaseString)
                            && JdkUtil.isBuildDateKnown(JdkUtil.JDK11_WINDOWS_ZIPS.get(releaseString).getBuildDate())
                            && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_WINDOWS_ZIPS.get(releaseString).getBuildDate()) == 0;
                    break;
                case JDK17:
                    isRhBuildDate = JdkUtil.JDK17_WINDOWS_ZIPS.containsKey(releaseString)
                            && JdkUtil.isBuildDateKnown(JdkUtil.JDK17_WINDOWS_ZIPS.get(releaseString).getBuildDate())
                            && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK17_WINDOWS_ZIPS.get(releaseString).getBuildDate()) == 0;
                    break;
                case JDK6:
                case JDK7:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRhBuildDate;
    ***REMOVED***

    /**
     * @return true if the Red Hat build date/time for the JDK version is unknown (ends in "00:00:00"), false otherwise.
     */
    public boolean isRhBuildDateUnknown() {
        boolean isRhBuildDateUnknown = false;
        if (getJdkBuildDate() != null) {
            String releaseString = getJdkReleaseString();
            String rpmDirectory = getRpmDirectory();
            if (getOsType() == OsType.LINUX) {
                switch (getJavaSpecification()) {
                case JDK8:
                    isRhBuildDateUnknown = (JdkUtil.JDK8_RHEL_ZIPS.containsKey(releaseString)
                            && !JdkUtil.isBuildDateKnown(JdkUtil.JDK8_RHEL_ZIPS.get(releaseString).getBuildDate()))
                            || (JdkUtil.JDK8_RHEL6_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK8_RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK8_RHEL7_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK8_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK8_RHEL7_PPC64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK8_RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK8_RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK8_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK8_RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK8_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()));
                    break;
                case JDK11:
                    isRhBuildDateUnknown = (JdkUtil.JDK11_RHEL_ZIPS.containsKey(releaseString)
                            && !JdkUtil.isBuildDateKnown(JdkUtil.JDK11_RHEL_ZIPS.get(releaseString).getBuildDate()))
                            || (JdkUtil.JDK11_RHEL7_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK11_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK11_RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK11_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK11_RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil.isBuildDateKnown(
                                    JdkUtil.JDK11_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()));
                    break;
                case JDK17:
                    isRhBuildDateUnknown = (JdkUtil.JDK17_RHEL_ZIPS.containsKey(releaseString)
                            && !JdkUtil.isBuildDateKnown(JdkUtil.JDK17_RHEL_ZIPS.get(releaseString).getBuildDate()))
                            || (JdkUtil.JDK17_RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil
                                    .isBuildDateKnown(JdkUtil.JDK17_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()))
                            || (JdkUtil.JDK17_RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && !JdkUtil.isBuildDateKnown(
                                    JdkUtil.JDK17_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()));
                    break;
                case JDK6:
                case JDK7:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getOsType() == OsType.WINDOWS) {
                switch (getJavaSpecification()) {
                case JDK8:
                    isRhBuildDateUnknown = JdkUtil.JDK8_WINDOWS_ZIPS.containsKey(releaseString)
                            && !JdkUtil.isBuildDateKnown(JdkUtil.JDK8_WINDOWS_ZIPS.get(releaseString).getBuildDate());
                    break;
                case JDK11:
                    isRhBuildDateUnknown = JdkUtil.JDK11_WINDOWS_ZIPS.containsKey(releaseString)
                            && !JdkUtil.isBuildDateKnown(JdkUtil.JDK11_WINDOWS_ZIPS.get(releaseString).getBuildDate());
                    break;
                case JDK17:
                    isRhBuildDateUnknown = JdkUtil.JDK17_WINDOWS_ZIPS.containsKey(releaseString)
                            && !JdkUtil.isBuildDateKnown(JdkUtil.JDK17_WINDOWS_ZIPS.get(releaseString).getBuildDate());
                    break;
                case JDK6:
                case JDK7:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRhBuildDateUnknown;
    ***REMOVED***

    /**
     * @return true if the fatal error log was created by a RH build of OpenJDK, false otherwise.
     */
    public boolean isRhBuildOpenJdk() {
        return isRhRpmInstall() || isRhLinuxZipInstall() || isRhWindowsZipInstall() || isRhRpm();
    ***REMOVED***

    /**
     * @return true if the fatal error log was created by a JDK build string used by Red Hat, false otherwise.
     */
    public boolean isRhBuildString() {
        return vmInfoEvent != null && (vmInfoEvent.getBuiltBy() == BuiltBy.BUILD
                || vmInfoEvent.getBuiltBy() == BuiltBy.EMPTY || vmInfoEvent.getBuiltBy() == BuiltBy.MOCKBUILD);
    ***REMOVED***

    /**
     * @return true if the fatal error log was created on RHEL, false otherwise.
     */
    public boolean isRhel() {
        boolean isRhel = false;
        if (!osEvents.isEmpty()) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    ***REMOVED***
                    isRhel = event.getLogEntry().matches("^.*Red Hat Enterprise Linux.+$");
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRhel;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK RHEL tarball, false
     *         otherwise.
     */
    public boolean isRhLinuxZipInstall() {
        boolean isRhLinuxZipInstall = false;
        if (getOsType() == OsType.LINUX && getArch() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhLinuxZipInstall = JdkUtil.JDK8_RHEL_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(JdkUtil.JDK8_RHEL_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK11:
                isRhLinuxZipInstall = JdkUtil.JDK11_RHEL_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(JdkUtil.JDK11_RHEL_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK17:
                isRhLinuxZipInstall = JdkUtil.JDK17_RHEL_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(JdkUtil.JDK17_RHEL_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        return isRhLinuxZipInstall;
    ***REMOVED***

    /**
     * Check if the JDK that produced the fatal error log is a derivative of a Red Hat build of OpenJDK rpm (e.g.
     * created with jlink). The version and build date will match, but it will be installed in a different location than
     * an rpm install.
     * 
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK rpm, false otherwise.
     */
    public boolean isRhRpm() {
        boolean isRhelRpm = false;
        String jdkReleaseString = getJdkReleaseString();
        Iterator<Entry<String, Release>> iterator;
        Date jdkBuildDate = getJdkBuildDate();
        if (getJavaSpecification() == JavaSpecification.JDK8) {
            switch (getOsVersion()) {
            case CENTOS6:
            case RHEL6:
                iterator = JdkUtil.JDK8_RHEL6_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    ***REMOVED***
                ***REMOVED***
                break;
            case CENTOS7:
            case RHEL7:
                if (getArch() == Arch.X86_64) {
                    iterator = JdkUtil.JDK8_RHEL7_X86_64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && jdkBuildDate != null
                                && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED*** else if (getArch() == Arch.PPC64) {
                    iterator = JdkUtil.JDK8_RHEL7_PPC64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                    iterator = JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                break;
            case CENTOS8:
            case RHEL8:
                if (getArch() == Arch.X86_64) {
                    iterator = JdkUtil.JDK8_RHEL8_X86_64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                    iterator = JdkUtil.JDK_RHEL8_PPC64LE_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                break;
            case RHEL9:
                if (getArch() == Arch.X86_64) {
                    iterator = JdkUtil.JDK8_RHEL9_X86_64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
                break;
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK11) {
            switch (getOsVersion()) {
            case CENTOS7:
            case RHEL7:
                iterator = JdkUtil.JDK11_RHEL7_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    ***REMOVED***
                ***REMOVED***
                break;
            case CENTOS8:
            case RHEL8:
                iterator = JdkUtil.JDK11_RHEL8_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    ***REMOVED***
                ***REMOVED***
                break;
            case RHEL9:
                iterator = JdkUtil.JDK11_RHEL9_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    ***REMOVED***
                ***REMOVED***
                break;
            case CENTOS6:
            case RHEL6:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK17) {
            switch (getOsVersion()) {
            case CENTOS8:
            case RHEL8:
                iterator = JdkUtil.JDK17_RHEL8_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    ***REMOVED***
                ***REMOVED***
                break;
            case RHEL9:
                iterator = JdkUtil.JDK17_RHEL9_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    ***REMOVED***
                ***REMOVED***
                break;
            case CENTOS6:
            case RHEL6:
            case CENTOS7:
            case RHEL7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        return isRhelRpm;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK rpm install, false
     *         otherwise.
     */
    public boolean isRhRpmInstall() {
        boolean isRhelRpmInstall = false;
        String rpmDirectory = getRpmDirectory();
        if (rpmDirectory != null) {
            if (getJavaSpecification() == JavaSpecification.JDK8) {
                switch (getOsVersion()) {
                case CENTOS6:
                case RHEL6:
                    isRhelRpmInstall = JdkUtil.JDK8_RHEL6_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK8_RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS7:
                case RHEL7:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL7_X86_64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL7_PPC64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate()
                                        .compareTo(JdkUtil.JDK8_RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case CENTOS8:
                case RHEL8:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                        isRhelRpmInstall = JdkUtil.JDK_RHEL8_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK_RHEL8_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case RHEL9:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK11) {
                switch (getOsVersion()) {
                case CENTOS7:
                case RHEL7:
                    isRhelRpmInstall = JdkUtil.JDK11_RHEL7_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.JDK11_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = JdkUtil.JDK11_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK17) {
                switch (getOsVersion()) {
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.JDK17_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK17_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = JdkUtil.JDK17_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK17_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case CENTOS7:
                case RHEL7:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRhelRpmInstall;
    ***REMOVED***

    /**
     * @return true if the version matches a Red Hat build of OpenJDK, false otherwise.
     */
    public boolean isRhVersion() {
        boolean isRhVersion = false;
        if (getOsType() == OsType.LINUX) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhVersion = JdkUtil.JDK8_RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK8_RHEL6_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK8_RHEL7_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK8_RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK8_RHEL9_X86_64_RPMS);
                break;
            case JDK11:
                isRhVersion = JdkUtil.JDK11_RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK11_RHEL7_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK11_RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK11_RHEL9_X86_64_RPMS);
                break;
            case JDK17:
                isRhVersion = JdkUtil.JDK17_RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK17_RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), JdkUtil.JDK17_RHEL9_X86_64_RPMS);
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED*** else if (getOsType() == OsType.WINDOWS) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhVersion = JdkUtil.JDK8_WINDOWS_ZIPS.containsKey(getJdkReleaseString());
                break;
            case JDK11:
                isRhVersion = JdkUtil.JDK11_WINDOWS_ZIPS.containsKey(getJdkReleaseString());
                break;
            case JDK17:
                isRhVersion = JdkUtil.JDK17_WINDOWS_ZIPS.containsKey(getJdkReleaseString());
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        return isRhVersion;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK Windows zip install,
     *         false otherwise.
     */
    public boolean isRhWindowsZipInstall() {
        boolean isRhWindowsZipInstall = false;
        if (isWindows() && getArch() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhWindowsZipInstall = JdkUtil.JDK8_WINDOWS_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(JdkUtil.JDK8_WINDOWS_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK11:
                isRhWindowsZipInstall = JdkUtil.JDK11_WINDOWS_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(JdkUtil.JDK11_WINDOWS_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK17:
                isRhWindowsZipInstall = JdkUtil.JDK17_WINDOWS_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(JdkUtil.JDK17_WINDOWS_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        return isRhWindowsZipInstall;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK rpm install, false
     *         otherwise.
     */
    public boolean isRpmInstall() {
        boolean isRhelRpmInstall = false;
        String rpmDirectory = getRpmDirectory();
        if (rpmDirectory != null) {
            if (getJavaSpecification() == JavaSpecification.JDK8) {
                switch (getOsVersion()) {
                case CENTOS6:
                case RHEL6:
                    isRhelRpmInstall = JdkUtil.JDK8_RHEL6_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK8_RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS7:
                case RHEL7:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL7_X86_64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL7_PPC64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate()
                                        .compareTo(JdkUtil.JDK8_RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case CENTOS8:
                case RHEL8:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                        isRhelRpmInstall = JdkUtil.JDK_RHEL8_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK_RHEL8_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case RHEL9:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.JDK8_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate().compareTo(
                                        JdkUtil.JDK8_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK11) {
                switch (getOsVersion()) {
                case CENTOS7:
                case RHEL7:
                    isRhelRpmInstall = JdkUtil.JDK11_RHEL7_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.JDK11_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = JdkUtil.JDK11_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK11_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK17) {
                switch (getOsVersion()) {
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.JDK17_RHEL8_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK17_RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = JdkUtil.JDK17_RHEL9_X86_64_RPMS.containsKey(rpmDirectory)
                            && getJdkBuildDate() != null && getJdkBuildDate()
                                    .compareTo(JdkUtil.JDK17_RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case CENTOS7:
                case RHEL7:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRhelRpmInstall;
    ***REMOVED***

    /**
     * @return true if the fatal error is truncated, false otherwise.
     */
    public boolean isTruncated() {
        boolean isTruncated = false;
        if (vmInfoEvent == null) {
            isTruncated = true;
        ***REMOVED***
        return isTruncated;
    ***REMOVED***

    /**
     * @return true if VMWare environment, false otherwise.
     */
    public boolean isVMWareEnvironment() {
        boolean isVMWareEnvironment = false;
        if (!virtualizationInfoEvents.isEmpty()) {
            Iterator<VirtualizationInfoEvent> iterator = virtualizationInfoEvents.iterator();
            while (iterator.hasNext()) {
                VirtualizationInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^VMWare virtualization detected$")) {
                    isVMWareEnvironment = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isVMWareEnvironment;
    ***REMOVED***

    /**
     * @return true if the fatal error log was created on Windows, false otherwise.
     */
    public boolean isWindows() {
        boolean isWindows = false;
        if (!osEvents.isEmpty()) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    ***REMOVED***
                    isWindows = event.getLogEntry().matches("^OS: Windows.+$");
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isWindows;
    ***REMOVED***

    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    ***REMOVED***

    public void setCommandLineEvent(CommandLineEvent commandLineEvent) {
        this.commandLineEvent = commandLineEvent;
    ***REMOVED***

    public void setCompressedClassSpaceEvent(CompressedClassSpaceEvent compressedClassSpaceEvent) {
        this.compressedClassSpaceEvent = compressedClassSpaceEvent;
    ***REMOVED***

    public void setCurrentThreadEvent(CurrentThreadEvent currentThreadEvent) {
        this.currentThreadEvent = currentThreadEvent;
    ***REMOVED***

    public void setElapsedTimeEvent(ElapsedTimeEvent elapsedTimeEvent) {
        this.elapsedTimeEvent = elapsedTimeEvent;
    ***REMOVED***

    public void setHeapAddressEvent(HeapAddressEvent heapAddressEvent) {
        this.heapAddressEvent = heapAddressEvent;
    ***REMOVED***

    public void setHostEvent(HostEvent hostEvent) {
        this.hostEvent = hostEvent;
    ***REMOVED***

    public void setMaxMapCountEvent(MaxMapCountEvent maxMapCountEvent) {
        this.maxMapCountEvent = maxMapCountEvent;
    ***REMOVED***

    public void setNarrowKlassEvent(NarrowKlassEvent narrowKlassEvent) {
        this.narrowKlassEvent = narrowKlassEvent;
    ***REMOVED***

    public void setPidMaxEvent(PidMaxEvent pidMaxEvent) {
        this.pidMaxEvent = pidMaxEvent;
    ***REMOVED***

    public void setRlimitEvent(RlimitEvent rlimitEvent) {
        this.rlimitEvent = rlimitEvent;
    ***REMOVED***

    public void setSigInfoEvent(SigInfoEvent sigInfoEvent) {
        this.sigInfoEvent = sigInfoEvent;
    ***REMOVED***

    public void setThreadsMaxEvent(ThreadsMaxEvent threadsMaxEvent) {
        this.threadsMaxEvent = threadsMaxEvent;
    ***REMOVED***

    public void setTimeElapsedTimeEvent(TimeElapsedTimeEvent timeElapsedTimeEvent) {
        this.timeElapsedTimeEvent = timeElapsedTimeEvent;
    ***REMOVED***

    public void setTimeEvent(TimeEvent timeEvent) {
        this.timeEvent = timeEvent;
    ***REMOVED***

    public void setTimezoneEvent(TimezoneEvent timezoneEvent) {
        this.timezoneEvent = timezoneEvent;
    ***REMOVED***

    public void setUnameEvent(UnameEvent unameEvent) {
        this.unameEvent = unameEvent;
    ***REMOVED***

    public void setUnidentifiedLogLines(List<String> unidentifiedLogLines) {
        this.unidentifiedLogLines = unidentifiedLogLines;
    ***REMOVED***

    public void setVmInfoEvent(VmInfoEvent vmInfoEvent) {
        this.vmInfoEvent = vmInfoEvent;
    ***REMOVED***

    public void setVmOperationEvent(VmOperationEvent vmOperationEvent) {
        this.vmOperationEvent = vmOperationEvent;
    ***REMOVED***

    public void setVmStateEvent(VmStateEvent vmStateEvent) {
        this.vmStateEvent = vmStateEvent;
    ***REMOVED***

    /**
     * @return The max compressed class size reserved in <code>Constants.PRECISION_REPORTING</code> units.
     */
    public long th() {
        // 1) Determine if compressed pointers are being used.
        boolean usingCompressedPointers = true;

        // 2) Default is to use compressed pointers based on heap size
        BigDecimal thirtyTwoGigabytes = new BigDecimal("32").multiply(Constants.GIGABYTE);
        long heapMaxSize = getHeapMaxSize();
        if (heapMaxSize >= thirtyTwoGigabytes.longValue()) {
            usingCompressedPointers = false;
        ***REMOVED***

        // 3) Check if the default behavior is being overridden
        if (jvmOptions != null) {
            if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())) {
                usingCompressedPointers = false;
            ***REMOVED*** else {
                if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedClassPointers())) {
                    usingCompressedPointers = false;
                ***REMOVED*** else {
                    usingCompressedPointers = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED*** else if (!globalFlagsEvents.isEmpty()) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            boolean useCompressedOops = true;
            boolean useCompressedClassPointers = true;
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExCompressedOopsDisabled = "^.+bool UseCompressedOops[ ]{1,***REMOVED***= false.+$";
                String regExCompressedClassPointersDisabled = "^.+bool UseCompressedClassPointers[ ]{1,***REMOVED***= false.+$";
                if (event.getLogEntry().matches(regExCompressedOopsDisabled)) {
                    useCompressedOops = false;
                ***REMOVED*** else if (event.getLogEntry().matches(regExCompressedClassPointersDisabled)) {
                    useCompressedClassPointers = false;
                ***REMOVED***
            ***REMOVED***
            if (!useCompressedOops) {
                usingCompressedPointers = false;
            ***REMOVED*** else {
                if (!useCompressedClassPointers) {
                    usingCompressedPointers = false;
                ***REMOVED*** else {
                    usingCompressedPointers = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        long compressedClassSpaceSize = 0;

        if (usingCompressedPointers) {
            // Default is 1g
            compressedClassSpaceSize = JdkUtil.convertSize(1, 'G', Constants.PRECISION_REPORTING);
            // 1st check [Global flags]
            if (!globalFlagsEvents.isEmpty()) {
                Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
                while (iterator.hasNext()) {
                    GlobalFlagsEvent event = iterator.next();
                    String regExCompressedClassSpaceSize = "^.+uintx CompressedClassSpaceSize[ ]{1,***REMOVED***= (\\d{1,***REMOVED***).+$";
                    Pattern pattern = Pattern.compile(regExCompressedClassSpaceSize);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        compressedClassSpaceSize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B',
                                Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED*** else if (jvmOptions != null) {
                if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())
                        || JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedClassPointers())) {
                    compressedClassSpaceSize = 0;
                ***REMOVED*** else if (jvmOptions.getCompressedClassSpaceSize() != null) {
                    char fromUnits;
                    long value;
                    Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
                    Matcher matcher = pattern.matcher(jvmOptions.getCompressedClassSpaceSize());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        ***REMOVED*** else {
                            fromUnits = 'B';
                        ***REMOVED***
                        compressedClassSpaceSize = JdkUtil.convertSize(value, fromUnits, Constants.PRECISION_REPORTING);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return compressedClassSpaceSize;
    ***REMOVED***
***REMOVED***
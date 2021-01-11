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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.util.Constants;
import org.github.errcat.util.Constants.CpuArch;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.Constants.OsVendor;
import org.github.errcat.util.Constants.OsVersion;
import org.github.errcat.util.ErrUtil;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkMath;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Application;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.BuiltBy;
import org.github.errcat.util.jdk.JdkUtil.GarbageCollector;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.github.errcat.util.jdk.JdkUtil.JavaVendor;
import org.github.errcat.util.jdk.JdkUtil.SignalCode;
import org.github.errcat.util.jdk.JdkUtil.SignalNumber;

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
     * JVM run duration information.
     */
    private ElapsedTimeEvent elapsedTimeEvent;

    /**
     * Exception counts information.
     */
    private List<ExceptionCountsEvent> exceptionCountsEvents;

    /**
     * Global flag information.
     */
    private List<GlobalFlagsEvent> globalFlagsEvents;

    /**
     * Header.
     */
    private List<HeaderEvent> headerEvents;

    /**
     * Heap address information.
     */
    private List<HeapAddressEvent> heapAddressEvents;

    /**
     * Heap information.
     */
    private List<HeapEvent> heapEvents;

    /**
     * JVM options convenience field
     */
    private JvmOptions jvmOptions;

    /**
     * Memory information.
     */
    private List<MeminfoEvent> meminfoEvents;

    /**
     * Memory information.
     */
    private List<MemoryEvent> memoryEvents;

    /**
     * Native memory tracking information.
     */
    private List<NativeMemoryTrackingEvent> nativeMemoryTrackingEvents;

    /**
     * OS information.
     */
    private List<OsEvent> osEvents;

    /**
     * Signal information.
     */
    private SigInfoEvent sigInfoEvent;

    /**
     * Stack information.
     */
    private List<StackEvent> stackEvents;

    /**
     * Thread information.
     */
    private List<ThreadEvent> threadEvents;

    /**
     * Combined time + elapsed time information.
     */
    private TimeElapsedTimeEvent timeElapsedTimeEvent;

    /**
     * JVM crash time information.
     */
    private TimeEvent timeEvent;

    /**
     * JVM crash time timezone information.
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
     * VM arguments information.
     */
    private List<VmArgumentsEvent> vmArgumentsEvents;

    /**
     * Vm event information.
     */
    private List<VmEvent> vmEvents;

    /**
     * JVM environment information.
     */
    private VmInfoEvent vmInfoEvent;

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
        exceptionCountsEvents = new ArrayList<ExceptionCountsEvent>();
        globalFlagsEvents = new ArrayList<GlobalFlagsEvent>();
        headerEvents = new ArrayList<HeaderEvent>();
        heapAddressEvents = new ArrayList<HeapAddressEvent>();
        heapEvents = new ArrayList<HeapEvent>();
        meminfoEvents = new ArrayList<MeminfoEvent>();
        memoryEvents = new ArrayList<MemoryEvent>();
        nativeMemoryTrackingEvents = new ArrayList<NativeMemoryTrackingEvent>();
        osEvents = new ArrayList<OsEvent>();
        stackEvents = new ArrayList<StackEvent>();
        threadEvents = new ArrayList<ThreadEvent>();
        unidentifiedLogLines = new ArrayList<String>();
        vmArgumentsEvents = new ArrayList<VmArgumentsEvent>();
        vmEvents = new ArrayList<VmEvent>();
    }

    /**
     * Do analysis.
     */
    public void doAnalysis() {
        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        }
        String jvmArgs = getJvmArgs();
        if (jvmArgs != null) {
            jvmOptions = new JvmOptions(jvmArgs);
        }
        doDataAnalysis();
        doJvmOptionsAnalysis();
    }

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // Check for JVM failing to start
        if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
            analysis.add(Analysis.INFO_JVM_STARTUP_FAILS);
        }
        // Check if JDK debugging symbols are installed
        if ((haveVmFrameInStack() || haveVmFrameInHeader()) && !haveJdkDebugSymbols()) {
            analysis.add(Analysis.WARN_DEBUG_SYMBOLS);
        }
        // Check if latest JDK release
        if (!JdkUtil.isLatestJdkRelease(this)) {
            analysis.add(0, Analysis.WARN_JDK_NOT_LATEST);
        }
        // Identify vendor/build
        if (isRhBuildOpenJdk()) {
            if (getOsType() == OsType.LINUX) {
                if (getOsVendor() == OsVendor.CENTOS) {
                    // CentOs redistributes RH build of OpenJDK
                    analysis.add(0, Analysis.INFO_RH_BUILD_CENTOS);
                } else if (getRpmDirectory() != null) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_RPM);
                    if (getCpuArch() == CpuArch.POWER9 && getJavaSpecification() == JavaSpecification.JDK8
                            && getOsString().matches(".+7\\.(7|8|9).+")) {
                        // power8 JDK8 deployed on power9 on RHEL 7
                        analysis.add(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9);
                    }
                } else {
                    analysis.add(0, Analysis.INFO_RH_BUILD_LINUX_ZIP);
                }
                // Check for RHEL6
                if (getOsVersion() == OsVersion.RHEL6) {
                    analysis.add(Analysis.WARN_RHEL6);
                }
            } else if (isWindows()) {
                analysis.add(0, Analysis.INFO_RH_BUILD_WINDOWS_ZIP);
            }
        } else {
            if (isRedHatBuildString()) {
                analysis.add(Analysis.INFO_RH_BUILD_POSSIBLE);
            } else if (isAdoptOpenJdkBuildString()) {
                analysis.add(Analysis.INFO_ADOPTOPENJDK_POSSIBLE);
            } else if (vmInfoEvent != null) {
                analysis.add(0, Analysis.INFO_RH_BUILD_NOT);
            }
        }
        // Check if there is vm code in the stack
        if (!haveVmCodeInStack()) {
            analysis.add(Analysis.INFO_STACK_NO_VM_CODE);
        }
        // Check if LTS release
        if (getJavaSpecification() != JavaSpecification.UNKNOWN && !isJdkLts()) {
            analysis.add(Analysis.WARN_JDK_NOT_LTS);
        }
        // Check for ancient JDK
        if (ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(this), JdkUtil.getLatestJdkReleaseDate(this)) > 365) {
            analysis.add(Analysis.INFO_JDK_ANCIENT);
        }
        // Check for crash in JNA
        if (isJnaCrash()) {
            if (getJavaVendor() == JavaVendor.RED_HAT) {
                analysis.add(Analysis.ERROR_JNA_RH);
            } else {
                analysis.add(Analysis.ERROR_JNA);
            }
        }
        // Check for JDK8 ZipFile contention
        if (getJavaSpecification() == JavaSpecification.JDK8 && getStackFrameTopCompiledJavaCode() != null
                && getStackFrameTopCompiledJavaCode().matches("^.+java\\.util\\.zip\\.ZipFile\\.getEntry.+$")) {
            analysis.add(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION);
        }
        // Check for unsynchronized access to DirectByteBuffer
        String stackFrameTop = "^v  ~StubRoutines::jbyte_disjoint_arraycopy$";
        if (getStackFrameTop() != null && getStackFrameTop().matches(stackFrameTop)) {
            if (isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER)) {
                analysis.add(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION);
            } else {
                analysis.add(Analysis.ERROR_STUBROUTINES);
            }
        }
        // Check for insufficient physical memory
        if (getJvmPhysicalMemory() > 0 && getJvmMemory() > Long.MIN_VALUE) {
            if (getJvmMemory() > getJvmPhysicalMemory()) {
                analysis.add(Analysis.ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY);
            }
        }
        // OOME, swap
        if (isError("Out of Memory Error")) {
            if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
                analysis.add(Analysis.ERROR_OOME_STARTUP);
                // Don't double report the JVM failing to start
                analysis.remove(Analysis.INFO_JVM_STARTUP_FAILS);
            } else if (getJvmPhysicalMemoryFree() > 0
                    && JdkMath.calcPercent(getJvmPhysicalMemoryFree(), getJvmPhysicalMemory()) >= 5) {
                // Plenty of physical memory, check for other limits/causes
                if (isError("Native memory allocation \\(mmap\\) failed to map")
                        || isError("Out of swap space to map in thread stack")) {
                    analysis.add(Analysis.ERROR_OOME_COMPRESSED_OOPS);
                }
            } else {
                // Low physical memory
                if (getJvmMemory() > 0) {
                    if (JdkMath.calcPercent(getJvmMemory(), getJvmPhysicalMemory()) >= 95) {
                        analysis.add(Analysis.ERROR_OOME_JVM);
                    } else {
                        analysis.add(Analysis.ERROR_OOME_EXTERNAL);
                    }
                } else {
                    analysis.add(Analysis.ERROR_OOME);
                }
            }
        } else if (getJvmSwap() > 0) {
            // Check for excessive swap usage
            int swapUsedPercent = 100 - JdkMath.calcPercent(getJvmSwapFree(), getJvmSwap());
            if (swapUsedPercent > 5 && swapUsedPercent < 20) {
                analysis.add(Analysis.INFO_SWAPPING);
            } else if (swapUsedPercent >= 20) {
                analysis.add(Analysis.WARN_SWAPPING);
            }
        }
        // Check for swap disabled
        if (getJvmSwap() == 0) {
            analysis.add(Analysis.INFO_SWAP_DISABLED);
        }
        // Check for ShenadoahRootUpdater bug fixed in OpenJDK8 u282.
        if (getJavaSpecification() == JavaSpecification.JDK8 && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 282
                && getStackFrameTop() != null && getStackFrameTop()
                        .matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  ShenandoahUpdateRefsClosure::do_oop.+$")) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_ROOT_UPDATER);
        }
        // Signal numbers
        switch (getSignalNumber()) {
        case EXCEPTION_ACCESS_VIOLATION:
            analysis.add(Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION);
            break;
        case SIGBUS:
            analysis.add(Analysis.INFO_SIGNO_SIGBUS);
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
        }
        // Signal codes
        switch (getSignalCode()) {
        case BUS_ADRALN:
            analysis.add(Analysis.INFO_SIGCODE_BUS_ADRALN);
            break;
        case BUS_ADRERR:
            if (getOsType() == OsType.LINUX) {
                analysis.add(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX);
            } else {
                analysis.add(Analysis.INFO_SIGCODE_BUS_ADDERR);
            }
            break;
        case BUS_OBJERR:
            analysis.add(Analysis.INFO_SIGCODE_BUS_OBJERR);
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
        }
        // pthread_getcpuclockid
        if (getStackFrameTop() != null
                && getStackFrameTop().matches("^C  \\[libpthread\\.so.+\\]  pthread_getcpuclockid.+$")) {
            analysis.add(Analysis.ERROR_PTHREAD_GETCPUCLOCKID);
        }
        // BufferBlob::flush_icache_stub
        if (getStackFrameTop() != null && getStackFrameTop().matches("^v  ~BufferBlob::flush_icache_stub+$")) {
            analysis.add(Analysis.ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB);
        }
        // StackOverflowError
        if (haveStackOverFlowError()) {
            analysis.add(Analysis.ERROR_STACKOVERFLOW);
        } else {
            if (getStackFreeSpace() > getThreadStackMaxSize()) {
                analysis.add(Analysis.ERROR_STACK_FREESPACE_GT_STACK_SIZE);
            }
        }
        // Thread stack size
        long threadStackMaxSize = getThreadStackMaxSize();
        if (threadStackMaxSize < 1) {
            analysis.add(Analysis.WARN_THREAD_STACK_SIZE_TINY);
        } else if (threadStackMaxSize < 128) {
            analysis.add(Analysis.WARN_THREAD_STACK_SIZE_SMALL);
        }
        // OutOfMemoryError: Java heap space
        if (haveOomeJavaHeap()) {
            analysis.add(Analysis.ERROR_OOME_JAVA_HEAP);
        }
        // Stubroutines
        if ((getStackFrameTop() != null && getStackFrameTop().matches("^v  ~BufferBlob::StubRoutines.*"))
                || isError("v  ~BufferBlob::StubRoutines")) {
            analysis.add(Analysis.ERROR_STUBROUTINES);
        }
        // ShenandoahConcurrentMark::mark_loop_work
        if ((getStackFrameTop() != null
                && getStackFrameTop().matches("^.+ShenandoahConcurrentMark::mark_loop_work.+"))) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK);
        }
        // org.apache.activemq.artemis.nativo.jlibaio.LibaioContext.done()
        if ((getStackFrameTop() != null && getStackFrameTop()
                .matches("^.+ org.apache.activemq.artemis.nativo.jlibaio.LibaioContext.done().+"))) {
            analysis.add(Analysis.ERROR_LIBAIO_CONTEXT_DONE);
        }
        // container
        if (getContainerInfoEvents().size() > 0) {
            analysis.add(Analysis.INFO_CGROUP);
        }
        if (getJvmPhysicalMemory() != this.getSystemPhysicalMemory()) {
            analysis.add(Analysis.INFO_MEMORY_JVM_NE_SYSTEM);
            if (haveCgroupMemoryLimit()) {
                analysis.add(Analysis.INFO_CGROUP_MEMORY_LIMIT);
            }
        }
        // truncated fatal error log
        if (isTruncated()) {
            analysis.add(Analysis.INFO_TRUNCATED);
        }
    }

    /**
     * Do JVM options analysis.
     */
    private void doJvmOptionsAnalysis() {
        if (jvmOptions != null) {
            if (jvmOptions.getJpdaSocketTransport() != null) {
                analysis.add(Analysis.ERROR_REMOTE_DEBUGGING_ENABLED);
            }
        }
    }

    public List<Analysis> getAnalysis() {
        return analysis;
    }

    /**
     * @return The application running on the JDK
     */
    public Application getApplication() {
        Application application = Application.UNKNOWN;
        if (dynamicLibraryEvents.size() > 0) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry().matches(JdkRegEx.JBOSS_JAR)) {
                    application = Application.JBOSS;
                    break;
                } else if (event.getLogEntry().matches(JdkRegEx.TOMCAT_JAR)) {
                    application = Application.TOMCAT;
                    break;
                }
            }
        }
        // Check jvm_args
        if (application == Application.UNKNOWN && vmArgumentsEvents.size() > 0) {
            Iterator<VmArgumentsEvent> iterator = vmArgumentsEvents.iterator();
            while (iterator.hasNext()) {
                VmArgumentsEvent event = iterator.next();
                if (event.isJavaCommand()) {
                    if (event.getLogEntry().matches(JdkRegEx.JBOSS_JAR)) {
                        application = Application.JBOSS;
                        break;
                    } else if (event.getLogEntry().matches(JdkRegEx.TOMCAT_JAR)) {
                        application = Application.TOMCAT;
                        break;
                    }
                }
            }
        }
        return application;
    }

    /**
     * @return <code>Arch</code>
     */
    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (unameEvent != null) {
            arch = unameEvent.getArch();
        } else if (vmInfoEvent != null) {
            arch = vmInfoEvent.getArch();
        } else {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJavaVm() && he.getLogEntry().matches("^.+solaris-sparc.+$")) {
                    arch = Arch.SPARC;
                }
            }
        }
        return arch;
    }

    public CommandLineEvent getCommandLineEvent() {
        return commandLineEvent;
    }

    public List<CompilationEvent> getCompilationEvents() {
        return compilationEvents;
    }

    /**
     * @return The max compressed class size reserved (kilobytes).
     */
    public long getCompressedClassSpaceSize() {
        // Default is 1g
        long compressedClassSpaceSize = JdkUtil.convertSize(1, 'G', Constants.BYTE_PRECISION);
        if (jvmOptions != null && jvmOptions.getCompressedClassSpaceSize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getCompressedClassSpaceSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                } else {
                    fromUnits = 'B';
                }
                compressedClassSpaceSize = JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
            }
        }
        return compressedClassSpaceSize;
    }

    public List<ContainerInfoEvent> getContainerInfoEvents() {
        return containerInfoEvents;
    }

    /**
     * @return The CPU architecture.
     */
    public CpuArch getCpuArch() {
        CpuArch cpuArch = CpuArch.UNKNOWN;
        if (cpuInfoEvents.size() > 0) {
            Iterator<CpuInfoEvent> iterator = cpuInfoEvents.iterator();
            while (iterator.hasNext()) {
                CpuInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^.+POWER9.+$")) {
                    cpuArch = CpuArch.POWER9;
                    break;
                }
            }
        }
        return cpuArch;
    }

    /**
     * @return The number of cpu cores.
     */
    public int getCpuCores() {
        int cpuCores = Integer.MIN_VALUE;
        if (cpuInfoEvents.size() > 0) {
            Iterator<CpuInfoEvent> iterator = cpuInfoEvents.iterator();
            while (iterator.hasNext()) {
                CpuInfoEvent event = iterator.next();
                if (event.isCpuHeader()) {
                    Pattern pattern = Pattern.compile(CpuInfoEvent.REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        int cpus = Integer.parseInt(matcher.group(1));
                        int cores = 1;
                        if (matcher.group(4) != null) {
                            cores = Integer.parseInt(matcher.group(5));
                        }
                        cpuCores = cpus * cores;
                    }
                }
            }
        }
        return cpuCores;
    }

    public List<CpuInfoEvent> getCpuInfoEvents() {
        return cpuInfoEvents;
    }

    /**
     * @return The time of the crash.
     */
    public String getCrashTime() {
        StringBuilder crashTime = new StringBuilder();
        if (timeEvent != null) {
            crashTime.append(timeEvent.getTime());
            if (timezoneEvent != null) {
                crashTime.append(" (");
                crashTime.append(timezoneEvent.getTimezone());
                crashTime.append(")");
            }
        } else if (timeElapsedTimeEvent != null) {
            crashTime.append(timeElapsedTimeEvent.getTime());
        }
        return crashTime.toString();
    }

    public List<CurrentCompileTaskEvent> getCurrentCompileTaskEvents() {
        return currentCompileTaskEvents;
    }

    public String getCurrentThread() {
        String currentThread = null;
        if (currentThreadEvent != null) {
            currentThread = currentThreadEvent.getCurrentThread();
        }
        return currentThread;
    }

    public List<DeoptimizationEvent> getDeoptimizationEvents() {
        return deoptimizationEvents;
    }

    public List<DynamicLibraryEvent> getDynamicLibraryEvents() {
        return dynamicLibraryEvents;
    }

    /**
     * @return The duration of the JVM run.
     */
    public String getElapsedTime() {
        String elapsedTime = null;
        if (elapsedTimeEvent != null) {
            elapsedTime = elapsedTimeEvent.getElapsedTime();
        } else if (timeElapsedTimeEvent != null) {
            elapsedTime = timeElapsedTimeEvent.getElapsedTime();
        }
        return elapsedTime;
    }

    /**
     * @return A <code>String</code> describing the cause of the crash.
     */
    public String getError() {
        StringBuilder causedBy = new StringBuilder();
        if (headerEvents.size() > 0) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSignalNumber() || he.isInternalError() || he.isError() || he.isFailed() || he.isInsufficient()
                        || he.isOutOf() || he.isProblematicFrame()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(Constants.LINE_SEPARATOR);
                    }
                    causedBy.append(he.getLogEntry());
                }
            }
        }
        return causedBy.toString();
    }

    public List<ExceptionCountsEvent> getExceptionCountsEvents() {
        return exceptionCountsEvents;
    }

    public List<GarbageCollector> getGarbageCollectors() {
        List<GarbageCollector> garbageCollectors = new ArrayList<GarbageCollector>();
        if (heapEvents.size() > 0) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches("^[ ]{0,}Shenandoah.+$")
                        && !garbageCollectors.contains(GarbageCollector.SHENANDOAH)) {
                    garbageCollectors.add(GarbageCollector.SHENANDOAH);
                    break;
                } else if (event.getLogEntry().matches("^[ ]{0,}garbage-first.+$")
                        && !garbageCollectors.contains(GarbageCollector.G1)) {
                    garbageCollectors.add(GarbageCollector.G1);
                    break;
                } else if (event.getLogEntry().matches("^[ ]{0,}PSYoungGen.+$")
                        && !garbageCollectors.contains(GarbageCollector.PARALLEL_SCAVENGE)) {
                    garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
                } else if (event.getLogEntry().matches("^[ ]{0,}ParOldGen.+$")
                        && !garbageCollectors.contains(GarbageCollector.PARALLEL_OLD)) {
                    garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
                } else if (event.getLogEntry().matches("^[ ]{0,}par new.+$")
                        && !garbageCollectors.contains(GarbageCollector.PAR_NEW)) {
                    garbageCollectors.add(GarbageCollector.PAR_NEW);
                } else if (event.getLogEntry().matches("^[ ]{0,}concurrent mark-sweep.+$")
                        && !garbageCollectors.contains(GarbageCollector.CMS)) {
                    garbageCollectors.add(GarbageCollector.CMS);
                } else if (event.getLogEntry().matches("^[ ]{0,}def new.+$")
                        && !garbageCollectors.contains(GarbageCollector.SERIAL)) {
                    garbageCollectors.add(GarbageCollector.SERIAL);
                } else if ((event.getLogEntry().matches("^[ ]{0,}PSOldGen.+$")
                        || event.getLogEntry().matches("^[ ]{0,}tenured.+$"))
                        && !garbageCollectors.contains(GarbageCollector.SERIAL_OLD)) {
                    garbageCollectors.add(GarbageCollector.SERIAL_OLD);
                }
            }
        }
        if (garbageCollectors.size() == 0) {
            garbageCollectors.add(GarbageCollector.UNKNOWN);
        }
        return garbageCollectors;
    }

    public List<GlobalFlagsEvent> getGlobalFlagsEvents() {
        return globalFlagsEvents;
    }

    public List<HeaderEvent> getHeaderEvents() {
        return headerEvents;
    }

    public List<HeapAddressEvent> getHeapAddressEvents() {
        return heapAddressEvents;
    }

    /**
     * @return The total heap allocation (kilobytes).
     */
    public long getHeapAllocation() {
        long heapAllocation = Long.MIN_VALUE;
        if (heapEvents.size() > 0) {
            heapAllocation = 0;
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                } else if (heapAtCrash && event.isYoungGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_YOUNG_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(3));
                        if (matcher.group(5) != null) {
                            fromUnits = matcher.group(5).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (heapAtCrash && event.isOldGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_OLD_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(4) != null) {
                            fromUnits = matcher.group(4).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (heapAtCrash && event.isShenandoah()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_SHENANDOAH);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(4));
                        if (matcher.group(6) != null) {
                            fromUnits = matcher.group(6).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (heapAtCrash && event.isG1()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_G1);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapAllocation += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                }
            }

        }
        return heapAllocation;
    }

    public List<HeapEvent> getHeapEvents() {
        return heapEvents;
    }

    /**
     * @return The heap max size reserved (kilobytes).
     */
    public long getHeapMaxSize() {
        long heapMaxSize = Long.MIN_VALUE;
        // 1st check [Global flags]
        if (globalFlagsEvents.size() > 0) {
            Iterator<GlobalFlagsEvent> iterator = globalFlagsEvents.iterator();
            while (iterator.hasNext()) {
                GlobalFlagsEvent event = iterator.next();
                String regExMaxHeap = "^.+size_t MaxHeapSize[ ]{1,}= (\\d{1,}).+$";
                Pattern pattern = Pattern.compile(regExMaxHeap);
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    heapMaxSize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'B', Constants.BYTE_PRECISION);
                }
            }
        } else if (jvmOptions != null && jvmOptions.getMaxHeapSize() != null) {
            // Get from jvm_args
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getMaxHeapSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                } else {
                    fromUnits = 'B';
                }
                heapMaxSize = JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
            }
        }
        // Max heap size not set (e.g. container), use allocation
        if (heapMaxSize == Long.MIN_VALUE) {
            heapMaxSize = getHeapAllocation();
        }
        return heapMaxSize;
    }

    /**
     * @return The total heap used (kilobytes).
     */
    public long getHeapUsed() {
        long heapUsed = Long.MIN_VALUE;
        if (heapEvents.size() > 0) {
            heapUsed = 0;
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                } else if (heapAtCrash && event.isYoungGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_YOUNG_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(6));
                        if (matcher.group(8) != null) {
                            fromUnits = matcher.group(8).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (heapAtCrash && event.isOldGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_OLD_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(5));
                        if (matcher.group(7) != null) {
                            fromUnits = matcher.group(7).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (heapAtCrash && event.isShenandoah()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_SHENANDOAH);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(7));
                        if (matcher.group(9) != null) {
                            fromUnits = matcher.group(9).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (heapAtCrash && event.isG1()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_G1);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(4));
                        if (matcher.group(6) != null) {
                            fromUnits = matcher.group(6).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        heapUsed += JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                    }
                } else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                }
            }
            // heapUsed = JdkMath.convertBytesToKilobytes(heapUsed);
        }
        return heapUsed;
    }

    /**
     * @return <code>JavaSpecificiation</code>
     */
    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJavaSpecification();
        }
        return version;
    }

    /**
     * @return The number of Java threads running when the JVM crashed.
     */
    public int getJavaThreadCount() {
        int javaThreadCount = 0;
        if (threadEvents.size() > 0) {
            Iterator<ThreadEvent> iterator = threadEvents.iterator();
            while (iterator.hasNext()) {
                ThreadEvent event = iterator.next();
                if (event.getLogEntry().matches("^Other Threads:$")) {
                    break;
                } else if (!event.getLogEntry().matches("^Java Threads: \\( => current thread \\)$")) {
                    javaThreadCount++;
                }
            }
        }
        return javaThreadCount;
    }

    /**
     * @return <code>JavaVendor</code>
     */
    public JavaVendor getJavaVendor() {
        JavaVendor vendor = JavaVendor.UNKNOWN;
        if (isRhBuildOpenJdk()) {
            vendor = JavaVendor.RED_HAT;
        } else {
            if (vmInfoEvent != null) {
                switch (vmInfoEvent.getBuiltBy()) {
                case JAVA_RE:
                    vendor = JavaVendor.ORACLE;
                    break;
                case JENKINS:
                    vendor = JavaVendor.ADOPTOPENJDK;
                    break;
                case ZULU_RE:
                    vendor = JavaVendor.AZUL;
                    break;
                case BUILD:
                case MOCKBUILD:
                case EMPTY:
                case UNKNOWN:
                default:
                    break;
                }
            }
        }
        return vendor;
    }

    /**
     * @return The JDK build date/time.
     */
    public Date getJdkBuildDate() {
        Date date = null;
        if (vmInfoEvent != null) {
            date = vmInfoEvent.getBuildDate();
        }
        return date;
    }

    /**
     * @return JDK release string. For example:
     * 
     *         1.8.0_251-b08
     */
    public String getJdkReleaseString() {
        String release = "UNKNOWN";
        if (vmInfoEvent != null) {
            release = vmInfoEvent.getJdkReleaseString();
        } else if (headerEvents.size() > 0) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJreVersion()) {
                    String regEx = "^.+\\(build " + JdkRegEx.RELEASE_STRING + "\\)$";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        release = matcher.group(1);
                    }
                }
            }
        }
        return release;
    }

    /**
     * @return The JVM options, or null if none exist.
     */
    public String getJvmArgs() {
        String jvmArgs = null;
        if (vmArgumentsEvents.size() > 0) {
            Iterator<VmArgumentsEvent> iterator = vmArgumentsEvents.iterator();
            while (iterator.hasNext()) {
                VmArgumentsEvent event = iterator.next();
                if (event.isJvmArgs()) {
                    jvmArgs = event.getValue();
                    break;
                }
            }
        }
        return jvmArgs;
    }

    /**
     * @return The JVM memory (kilobytes).
     */
    public long getJvmMemory() {
        long jvmMemory = Long.MIN_VALUE;
        // Using compressed class pointers space
        if (getHeapMaxSize() > 0) {
            jvmMemory = getHeapMaxSize();
        }
        if (getMetaspaceMaxSize() > 0) {
            if (jvmMemory > 0) {
                jvmMemory += getMetaspaceMaxSize();
            } else {
                jvmMemory = getMetaspaceMaxSize();
            }
        }
        if (jvmMemory > 0 && jvmOptions != null && !jvmOptions.isUseCompressedOopsDisabled()
                && !jvmOptions.isUseCompressedClassPointersDisabled()) {
            // Using compressed class pointers space
            if (getCompressedClassSpaceSize() > 0) {
                if (jvmMemory > 0) {
                    jvmMemory += getCompressedClassSpaceSize();
                } else {
                    jvmMemory = getCompressedClassSpaceSize();
                }
            }
        }
        return jvmMemory;
    }

    /**
     * <pre>
     * 128M
     * </pre>
     * 
     * @return The maximum Metaspace value, or null if not set. For example:
     */
    public String getMaxMetaspaceValue() {
        String maxMetaspaceValue = null;
        if (jvmOptions != null && jvmOptions.getMaxMetaspaceSize() != null) {
            maxMetaspaceValue = JdkUtil.getOptionValue(jvmOptions.getMaxMetaspaceSize());
        }
        return maxMetaspaceValue;
    }

    public List<MeminfoEvent> getMeminfoEvents() {
        return meminfoEvents;
    }

    public List<MemoryEvent> getMemoryEvents() {
        return memoryEvents;
    }

    /**
     * @return The total metaspace allocation (kilobytes).
     */
    public long getMetaspaceAllocation() {
        long metaspaceAllocation = Long.MIN_VALUE;
        if (heapEvents.size() > 0) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                } else if (heapAtCrash && event.isMetaspace()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_METASPACE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(7));
                        if (matcher.group(9) != null) {
                            fromUnits = matcher.group(9).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        metaspaceAllocation = JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                        break;
                    }
                } else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                }
            }
        }
        return metaspaceAllocation;
    }

    /**
     * @return The metaspace max size reserved (kilobytes).
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
                } else {
                    fromUnits = 'B';
                }
                metaspaceMaxSize = JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
            }
        }
        // If max metaspace size not set (recommended), get from <code>HeapEvent</code>
        if (metaspaceMaxSize == Long.MIN_VALUE) {
            if (heapEvents.size() > 0) {
                Iterator<HeapEvent> iterator = heapEvents.iterator();
                boolean heapAtCrash = false;
                char fromUnits;
                long value;
                Pattern pattern = null;
                Matcher matcher = null;
                while (iterator.hasNext()) {
                    HeapEvent event = iterator.next();
                    if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                        heapAtCrash = true;
                    } else if (heapAtCrash && event.isMetaspace()) {
                        pattern = Pattern.compile(HeapEvent.REGEX_METASPACE);
                        matcher = pattern.matcher(event.getLogEntry());
                        if (matcher.find()) {
                            value = Long.parseLong(matcher.group(10));
                            if (matcher.group(12) != null) {
                                fromUnits = matcher.group(12).charAt(0);
                            } else {
                                fromUnits = 'B';
                            }
                            metaspaceMaxSize = JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                            break;
                        }
                    } else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                        heapAtCrash = false;
                    }
                }
            }
        }
        return metaspaceMaxSize;
    }

    /**
     * @return The total metaspace used (kilobytes).
     */
    public long getMetaspaceUsed() {
        long metaspaceUsed = Long.MIN_VALUE;
        if (heapEvents.size() > 0) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                } else if (heapAtCrash && event.isMetaspace()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_METASPACE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        metaspaceUsed = JdkUtil.convertSize(value, fromUnits, Constants.BYTE_PRECISION);
                        break;
                    }
                } else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                }
            }
        }
        return metaspaceUsed;
    }

    public List<NativeMemoryTrackingEvent> getNativeMemoryTrackingEvents() {
        return nativeMemoryTrackingEvents;
    }

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
            }
            stackIndex++;
        }
        return nextStackFrame;
    }

    public List<OsEvent> getOsEvents() {
        return osEvents;
    }

    /**
     * @return OS string.
     */
    public String getOsString() {
        String osString = "UNKNOWN";
        if (osEvents.size() > 0) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = OsEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        osString = matcher.group(3).trim();
                    }
                    break;
                }
            }
        }
        return osString;
    }

    /**
     * @return <code>OsType</code>
     */
    public OsType getOsType() {
        OsType osType = OsType.UNKNOWN;
        String osString = getOsString();
        if (osString != null) {
            if (osString.matches(".+Linux.+")) {
                osType = OsType.LINUX;
            } else if (osString.matches("^OS: Windows.+$")) {
                osType = OsType.WINDOWS;
            } else if (osString.matches(".+Solaris.+")) {
                osType = OsType.SOLARIS;
            }
        }
        return osType;
    }

    /**
     * @return <code>OsVendor</code>
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNKNOWN;
        if (osEvents.size() > 0) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:Red Hat.+$")) {
                        osVendor = OsVendor.REDHAT;
                    } else if (event.getLogEntry().matches("^OS: Windows.+$")) {
                        osVendor = OsVendor.MICROSOFT;
                    } else if (event.getLogEntry().matches("^.+Oracle.+$")) {
                        osVendor = OsVendor.ORACLE;
                    } else if (event.getLogEntry().matches("^OS:CentOS.+$")) {
                        osVendor = OsVendor.CENTOS;
                    }
                    break;
                }
            }
        }

        return osVendor;
    }

    /**
     * @return <code>OsVersion</code>
     */
    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNKNOWN;
        if (osEvents.size() > 0) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:Red Hat Enterprise Linux (Server|Workstation) release 6.+$")) {
                        osVersion = OsVersion.RHEL6;
                    } else if (event.getLogEntry()
                            .matches("^OS:Red Hat Enterprise Linux (Server|Workstation) release 7.+$")) {
                        osVersion = OsVersion.RHEL7;
                    } else if (event.getLogEntry().matches("^OS:Red Hat Enterprise Linux release 8.+$")) {
                        osVersion = OsVersion.RHEL8;
                    } else if (event.getLogEntry().matches("^OS:CentOS Linux release 6.+$")) {
                        osVersion = OsVersion.CENTOS6;
                    } else if (event.getLogEntry().matches("^OS:CentOS Linux release 7.+$")) {
                        osVersion = OsVersion.CENTOS7;
                    } else if (event.getLogEntry().matches("^OS:CentOS Linux release 8.+$")) {
                        osVersion = OsVersion.CENTOS8;
                    }
                    break;
                }
            }
        } else if (unameEvent != null) {
            osVersion = unameEvent.getOsVersion();
        }
        return osVersion;
    }

    /**
     * @return The total available physical memory (kilobytes) reported by the JVM.
     */
    public long getJvmPhysicalMemory() {
        long physicalMemory = Long.MIN_VALUE;
        if (memoryEvents.size() > 0) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        physicalMemory = JdkUtil.convertSize(Long.parseLong(matcher.group(4)),
                                matcher.group(6).charAt(0), Constants.BYTE_PRECISION);
                    }
                }
            }
        }
        return physicalMemory;
    }

    /**
     * @return The total available physical memory (kilobytes) reported by the OS.
     */
    public long getSystemPhysicalMemory() {
        long physicalMemory = Long.MIN_VALUE;
        if (meminfoEvents.size() > 0) {
            String regexMemTotal = "MemTotal:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    physicalMemory = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.BYTE_PRECISION);
                }
            }
        }
        return physicalMemory;
    }

    /**
     * @return The total free physical memory (kilobytes) as reported by the JVM.
     */
    public long getJvmPhysicalMemoryFree() {
        long physicalMemoryFree = Long.MIN_VALUE;
        if (memoryEvents.size() > 0) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        physicalMemoryFree = JdkUtil.convertSize(Long.parseLong(matcher.group(7)),
                                matcher.group(9).charAt(0), Constants.BYTE_PRECISION);
                    }
                }
            }
        }
        return physicalMemoryFree;
    }

    /**
     * @return The total free physical memory (kilobytes) as reported by the OS.
     */
    public long getSystemPhysicalMemoryFree() {
        long physicalMemoryFree = Long.MIN_VALUE;
        if (meminfoEvents.size() > 0) {
            String regexMemTotal = "MemFree:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    physicalMemoryFree = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K',
                            Constants.BYTE_PRECISION);
                }
            }
        }
        return physicalMemoryFree;
    }

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
            if (dynamicLibraryEvents.size() > 0) {
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
                            }
                            break;
                        } else if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return rpmDirectory;
    }

    public SigInfoEvent getSigInfoEvent() {
        return sigInfoEvent;
    }

    /**
     * @return Signal code.
     */
    public SignalCode getSignalCode() {
        SignalCode signalCode = SignalCode.UNKNOWN;
        if (sigInfoEvent != null) {
            signalCode = sigInfoEvent.getSignalCode();
        }
        return signalCode;
    }

    /**
     * @return Signal number.
     */
    public SignalNumber getSignalNumber() {
        SignalNumber signalNumber = SignalNumber.UNKNOWN;
        if (sigInfoEvent != null) {
            signalNumber = sigInfoEvent.getSignalNumber();
        }
        return signalNumber;
    }

    public List<StackEvent> getStackEvents() {
        return stackEvents;
    }

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
                }
                stackIndex++;
            }
        }
        return stackFrame;
    }

    /**
     * @return The top stack frame, or null if none exists.
     */
    public String getStackFrameTop() {
        String stackFrameTop = null;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^(A|C|j|J|v|V)[ ]{1,2}.+$")) {
                stackFrameTop = event.getLogEntry();
                break;
            }
        }
        return stackFrameTop;
    }

    /**
     * @return The top Compile Java Code (J) stack frame, or null if none exists.
     */
    public String getStackFrameTopCompiledJavaCode() {
        String stackFrameTopCompiledJavaCode = null;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^J[ ]{1,2}.+$")) {
                stackFrameTopCompiledJavaCode = event.getLogEntry();
                break;
            }
        }
        return stackFrameTopCompiledJavaCode;
    }

    /**
     * @return The stack free space (kilobytes).
     */
    public long getStackFreeSpace() {
        long stackFreeSpace = Long.MIN_VALUE;
        if (stackEvents.size() > 0) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isHeader()) {
                    stackFreeSpace = event.getStackFreeSpace();
                    break;
                }
            }
        }
        return stackFreeSpace;
    }

    /**
     * @return The total available swap (kilobytes) as reported by the JVM.
     */
    public long getSystemSwap() {
        long swap = Long.MIN_VALUE;
        if (meminfoEvents.size() > 0) {
            String regexMemTotal = "SwapTotal:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    swap = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', Constants.BYTE_PRECISION);
                }
            }
        }
        return swap;
    }

    /**
     * @return The total free swap (kilobytes) as reported by the JVM.
     */
    public long getSystemSwapFree() {
        long swapFree = Long.MIN_VALUE;
        if (meminfoEvents.size() > 0) {
            String regexMemTotal = "SwapFree:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<MeminfoEvent> iterator = meminfoEvents.iterator();
            while (iterator.hasNext()) {
                MeminfoEvent event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', Constants.BYTE_PRECISION);
                }
            }
        }
        return swapFree;
    }

    /**
     * @return The total available swap (kilobytes) as reported by the JVM.
     */
    public long getJvmSwap() {
        long swap = Long.MIN_VALUE;
        if (memoryEvents.size() > 0) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(11) != null && matcher.group(13) != null) {
                            swap = JdkUtil.convertSize(Long.parseLong(matcher.group(11)), matcher.group(13).charAt(0),
                                    Constants.BYTE_PRECISION);
                        }
                    }
                }
            }
        }
        return swap;
    }

    /**
     * @return The total free swap (kilobytes) as reported by the JVM.
     */
    public long getJvmSwapFree() {
        long swapFree = Long.MIN_VALUE;
        if (memoryEvents.size() > 0) {
            Iterator<MemoryEvent> iterator = memoryEvents.iterator();
            while (iterator.hasNext()) {
                MemoryEvent event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = MemoryEvent.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(14)), matcher.group(16).charAt(0),
                                Constants.BYTE_PRECISION);
                    }
                }
            }
        }
        return swapFree;
    }

    public List<ThreadEvent> getThreadEvents() {
        return threadEvents;
    }

    /**
     * @return The stack max size reserved (kilobytes).
     */
    public long getThreadStackMaxSize() {
        long stackMaxSize = 1024;
        if (jvmOptions != null && jvmOptions.getThreadStackSize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getThreadStackSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                } else {
                    fromUnits = 'B';
                }
                stackMaxSize = JdkUtil.convertSize(value, fromUnits, 'K');
            }
        }
        return stackMaxSize;
    }

    public TimeElapsedTimeEvent getTimeElapsedTimeEvent() {
        return timeElapsedTimeEvent;
    }

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    }

    public List<VmArgumentsEvent> getVmArgumentsEvents() {
        return vmArgumentsEvents;
    }

    public List<VmEvent> getVmEvents() {
        return vmEvents;
    }

    /**
     * @return The VM state.
     */
    public String getVmState() {
        String vmState = null;
        if (vmStateEvent != null) {
            vmState = vmStateEvent.getState();
        }
        return vmState;
    }

    public VmStateEvent getVmStateEvent() {
        return vmStateEvent;
    }

    /**
     * @return true if JDK debug symbols are installed, false otherwise.
     */
    public boolean haveJdkDebugSymbols() {
        boolean haveJdkDebugSymbols = false;
        if (headerEvents.size() > 0) {
            Iterator<HeaderEvent> iterator1 = headerEvents.iterator();
            while (iterator1.hasNext()) {
                HeaderEvent he = iterator1.next();
                if (he.isProblematicFrame()) {
                    haveJdkDebugSymbols = he.getLogEntry().matches("^# V  \\[.+\\].+$");
                    break;
                }
            }
        }
        if (!haveJdkDebugSymbols) {
            if (stackEvents.size() > 0) {
                Iterator<StackEvent> iterator2 = stackEvents.iterator();
                while (iterator2.hasNext() && !haveJdkDebugSymbols) {
                    StackEvent se = iterator2.next();
                    if (se.isVmFrame()) {
                        haveJdkDebugSymbols = se.getLogEntry().matches("^V  \\[.+\\].+$")
                                && !se.getLogEntry().matches("^V  \\[.+\\]  JVM_DoPrivileged.+$");
                    }
                }
            }
        }
        return haveJdkDebugSymbols;
    }

    /**
     * @return true if there were OutOfMemoryError: Java heap space before the crash, false otherwise.
     */
    public boolean haveOomeJavaHeap() {
        boolean haveStackOverFlowError = false;
        if (exceptionCountsEvents.size() > 0) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^OutOfMemoryError java_heap_errors=\\d{1,}$")) {
                    haveStackOverFlowError = true;
                    break;
                }
            }
        }
        return haveStackOverFlowError;
    }

    /**
     * @return true if there were StackOverflowErrors before the crash, false otherwise.
     */
    public boolean haveStackOverFlowError() {
        boolean haveStackOverFlowError = false;
        if (exceptionCountsEvents.size() > 0) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^StackOverflowErrors=\\d{1,}$")) {
                    haveStackOverFlowError = true;
                    break;
                }
            }
        }
        return haveStackOverFlowError;
    }

    /**
     * @return true if the stack contains JDK VM frame code, false otherwise.
     */
    public boolean haveVmCodeInStack() {
        boolean haveVmCodeInStack = false;
        if (stackEvents.size() > 0) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmFrame() || event.isVmGeneratedCodeFrame()) {
                    haveVmCodeInStack = true;
                    break;
                }
            }
        }
        return haveVmCodeInStack;
    }

    /**
     * @return true if the header contains JDK VM frame code, false otherwise.
     */
    public boolean haveVmFrameInHeader() {
        boolean haveVmFrameInHeader = false;
        if (headerEvents.size() > 0) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent event = iterator.next();
                if (event.isVmFrame()) {
                    haveVmFrameInHeader = true;
                    break;
                }
            }
        }
        return haveVmFrameInHeader;
    }

    /**
     * @return true if the stack contains JDK VM frame, false otherwise.
     */
    public boolean haveVmFrameInStack() {
        boolean haveVmFrameInStack = false;
        if (stackEvents.size() > 0) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmFrame()) {
                    haveVmFrameInStack = true;
                    break;
                }
            }
        }
        return haveVmFrameInStack;
    }

    /**
     * @return true if the stack contains JDK VM generated code frame, false otherwise.
     */
    public boolean haveVmGeneratedCodeFrameInStack() {
        boolean haveVmGeneratedCodeFrameInStack = false;
        if (stackEvents.size() > 0) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmGeneratedCodeFrame()) {
                    haveVmGeneratedCodeFrameInStack = true;
                    break;
                }
            }
        }
        return haveVmGeneratedCodeFrameInStack;
    }

    /**
     * AdoptOpenJDK has the same release versions as the RH build of OpenJDK but have a different build date/time and
     * builder string ("jenkins").
     * 
     * @return true if the fatal error log was created by a JDK build string used by AdoptOpenJDK, false otherwise.
     */
    public boolean isAdoptOpenJdkBuildString() {
        return vmInfoEvent != null && (vmInfoEvent.getBuiltBy() == BuiltBy.JENKINS);
    }

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
        }
        return isError;
    }

    /**
     * @param classRegEx
     *            A class name as a regular expression.
     * @return true if the class is in the stack, false otherwise.
     */
    public boolean isInStack(String classRegEx) {
        boolean isInStack = false;
        if (stackEvents.size() > 0) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.getLogEntry().matches("^.+" + classRegEx + ".+$")) {
                    isInStack = true;
                }
            }
        }
        return isInStack;
    }

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
            isJdkLts = true;
            break;
        case JDK9:
        case JDK10:
        case JDK12:
        case JDK13:
        case JDK14:
        case JDK15:
        case UNKNOWN:
        default:
            break;
        }
        return isJdkLts;
    }

    /**
     * @return true if the crash happens in JNA code, false otherwise.
     */
    public boolean isJnaCrash() {
        boolean isJnaCrash = false;
        if (getStackEvents() != null && getStackEvents().size() >= 2) {
            if (getStackFrame(1).matches("^C[ ]{1,2}\\[jna.+$")
                    && getStackFrame(2).matches("^j[ ]{1,2}com\\.sun\\.jna\\..+$")) {
                isJnaCrash = true;
            }
        }
        return isJnaCrash;
    }

    /**
     * @return true if the fatal error log was created by a JDK build string used by Red Hat, false otherwise.
     */
    public boolean isRedHatBuildString() {
        return vmInfoEvent != null && (vmInfoEvent.getBuiltBy() == BuiltBy.BUILD
                || vmInfoEvent.getBuiltBy() == BuiltBy.EMPTY || vmInfoEvent.getBuiltBy() == BuiltBy.MOCKBUILD);
    }

    /**
     * @return true if the fatal error log was created by a RH build of OpenJDK, false otherwise.
     */
    public boolean isRhBuildOpenJdk() {
        return isRhRpmInstall() || isRhLinuxZipInstall() || isRhWindowsZipInstall();
    }

    /**
     * @return true if the fatal error log was created on RHEL, false otherwise.
     */
    public boolean isRhel() {
        boolean isRhel = false;
        if (osEvents.size() > 0) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    isRhel = event.getLogEntry().matches("^OS:Red Hat Enterprise Linux.+$");
                    break;
                }
            }
        }
        return isRhel;
    }

    /**
     * @return true if there is a cgroup memory limit, false otherwise.
     */
    public boolean haveCgroupMemoryLimit() {
        boolean isCgroupMemoryLimit = false;
        if (containerInfoEvents.size() > 0) {
            Iterator<ContainerInfoEvent> iterator = containerInfoEvents.iterator();
            while (iterator.hasNext()) {
                ContainerInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^memory_limit_in_bytes: \\d{1,}$")) {
                    isCgroupMemoryLimit = true;
                    break;
                }
            }
        }
        return isCgroupMemoryLimit;
    }

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK RHEL zip install, false
     *         otherwise.
     */
    public boolean isRhLinuxZipInstall() {
        boolean isRhLinuxZipInstall = false;
        if (getOsType() == OsType.LINUX && getArch() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhLinuxZipInstall = JdkUtil.rhelJdk8ZipReleases.containsKey(getJdkReleaseString())
                        && getJdkBuildDate()
                                .compareTo(JdkUtil.rhelJdk8ZipReleases.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK11:
                isRhLinuxZipInstall = JdkUtil.rhelJdk11ZipReleases.containsKey(getJdkReleaseString())
                        && getJdkBuildDate()
                                .compareTo(JdkUtil.rhelJdk11ZipReleases.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            }
        }
        return isRhLinuxZipInstall;
    }

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
                    isRhelRpmInstall = JdkUtil.rhel6Amd64Jdk8RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel6Amd64Jdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS7:
                case RHEL7:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.rhel7Amd64Jdk8RpmReleases.containsKey(rpmDirectory)
                                && getJdkBuildDate().compareTo(
                                        JdkUtil.rhel7Amd64Jdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArch() == Arch.PPC64LE) {
                        isRhelRpmInstall = JdkUtil.rhel7Ppc64leJdk8RpmReleases.containsKey(rpmDirectory)
                                && getJdkBuildDate().compareTo(
                                        JdkUtil.rhel7Ppc64leJdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    }
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.rhel8Amd64Jdk8RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel8Amd64Jdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case UNKNOWN:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK11) {
                switch (getOsVersion()) {
                case CENTOS7:
                case RHEL7:
                    isRhelRpmInstall = JdkUtil.rhel7Amd64Jdk11RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel7Amd64Jdk11RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.rhel8Amd64Jdk11RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel8Amd64Jdk11RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case UNKNOWN:
                default:
                    break;
                }
            }
        }
        return isRhelRpmInstall;
    }

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK Windows zip install,
     *         false otherwise.
     */
    public boolean isRhWindowsZipInstall() {
        boolean isRhWindowsZipInstall = false;
        if (isWindows() && getArch() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhWindowsZipInstall = JdkUtil.windowsJdk8Releases.containsKey(getJdkReleaseString());
                break;
            case JDK11:
                isRhWindowsZipInstall = JdkUtil.windowsJdk11Releases.containsKey(getJdkReleaseString());
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            }
        }
        return isRhWindowsZipInstall;
    }

    /**
     * @return true if the fatal error log was created on Windows, false otherwise.
     */
    public boolean isWindows() {
        boolean isWindows = false;
        if (osEvents.size() > 0) {
            Iterator<OsEvent> iterator = osEvents.iterator();
            while (iterator.hasNext()) {
                OsEvent event = iterator.next();
                if (event.isHeader()) {
                    isWindows = event.getLogEntry().matches("^OS: Windows.+$");
                    break;
                }
            }
        }
        return isWindows;
    }

    /**
     * @return true if the fatal error is truncated, false otherwise.
     */
    public boolean isTruncated() {
        boolean isTruncated = false;
        if (vmInfoEvent == null) {
            isTruncated = true;
        }
        return isTruncated;
    }

    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    }

    public void setCommandLineEvent(CommandLineEvent commandLineEvent) {
        this.commandLineEvent = commandLineEvent;
    }

    public void setCurrentThreadEvent(CurrentThreadEvent currentThreadEvent) {
        this.currentThreadEvent = currentThreadEvent;
    }

    public void setElapsedTimeEvent(ElapsedTimeEvent elapsedTimeEvent) {
        this.elapsedTimeEvent = elapsedTimeEvent;
    }

    public void setSigInfoEvent(SigInfoEvent sigInfoEvent) {
        this.sigInfoEvent = sigInfoEvent;
    }

    public void setTimeElapsedTimeEvent(TimeElapsedTimeEvent timeElapsedTimeEvent) {
        this.timeElapsedTimeEvent = timeElapsedTimeEvent;
    }

    public void setTimeEvent(TimeEvent timeEvent) {
        this.timeEvent = timeEvent;
    }

    public void setTimezoneEvent(TimezoneEvent timezoneEvent) {
        this.timezoneEvent = timezoneEvent;
    }

    public void setUnameEvent(UnameEvent unameEvent) {
        this.unameEvent = unameEvent;
    }

    public void setUnidentifiedLogLines(List<String> unidentifiedLogLines) {
        this.unidentifiedLogLines = unidentifiedLogLines;
    }

    public void setVmInfoEvent(VmInfoEvent vmInfoEvent) {
        this.vmInfoEvent = vmInfoEvent;
    }

    public void setVmStateEvent(VmStateEvent vmStateEvent) {
        this.vmStateEvent = vmStateEvent;
    }
}

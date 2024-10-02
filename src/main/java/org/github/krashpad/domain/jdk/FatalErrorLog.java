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

import static java.math.RoundingMode.HALF_EVEN;
import static java.util.stream.Collectors.summingLong;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.github.joa.JvmOptions;
import org.github.joa.domain.Arch;
import org.github.joa.domain.Bit;
import org.github.joa.domain.BuiltBy;
import org.github.joa.domain.GarbageCollector;
import org.github.joa.domain.JvmContext;
import org.github.joa.domain.Os;
import org.github.krashpad.util.Constants.CpuArch;
import org.github.krashpad.util.Constants.Device;
import org.github.krashpad.util.Constants.OsVendor;
import org.github.krashpad.util.Constants.OsVersion;
import org.github.krashpad.util.KrashUtil;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.Jdk11;
import org.github.krashpad.util.jdk.Jdk17;
import org.github.krashpad.util.jdk.Jdk21;
import org.github.krashpad.util.jdk.Jdk8;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.Application;
import org.github.krashpad.util.jdk.JdkUtil.CompressedOopMode;
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
     * @param <T>
     * @param list
     * @param function
     * @return
     */
    private static <T> Stream<Long> longs(List<T> list, Function<T, Long> function) {
        return list.stream().map(function).filter(Objects::nonNull);
    }

    /**
     * Analysis.
     */
    private List<Analysis> analysis;

    /**
     * ZGC barrier set information.
     */
    private BarrierSet barrierSet;

    /**
     * Classes unloaded information.
     */
    private List<ClassesUnloadedEvent> classesUnloadedEvents;

    /**
     * Command line information.
     */
    private CommandLine commandLine;

    /**
     * Compilation event information.
     */
    private List<CompilationEvent> compilationEvents;

    /**
     * Compressed class space information
     */
    private CompressedClassSpace compressedClassSpaceEvent;

    /**
     * Container information.
     */
    private List<ContainerInfo> containerInfos;

    /**
     * CPU information.
     */
    private List<CpuInfo> cpuInfos;

    /**
     * Current compile task information.
     */
    private List<CurrentCompileTask> currentCompileTasks;

    /**
     * Current thread information.
     */
    private CurrentThread currentThread;

    /**
     * Deoptimization event information.
     */
    private List<DeoptimizationEvent> deoptimizationEvents;

    /**
     * Dll operation information.
     */
    private List<DllOperationEvent> dllOperationEvents;

    /**
     * Dynamic library information. Only "interesting" mappings (e.g. jars and native libraries) to improve the
     * performance processing crashes caused by thread leaks, which can have tens of thousands of "uninteresting"
     * mappings (e.g. a memory range).
     */
    private List<DynamicLibrary> dynamicLibraries;

    /**
     * Convenience field for the number of {@link org.github.krashpad.domain.jdk.DynamicLibrary} mappings to allow
     * retaining only "interesting" mappings in @{link {@link #dynamicLibraries}.
     */
    private int dynamicLibrariesMappingCount;

    /**
     * JVM run duration information in JDK8.
     */
    private ElapsedTime elapsedTime;

    /**
     * End line. Used to detect truncated log.
     */
    private End end;

    /**
     * Environment variables information.
     */
    private List<EnvironmentVariable> environmentVariables;

    /**
     * Vm event information.
     */
    private List<Event> events;

    /**
     * Exception counts information.
     */
    private List<ExceptionCounts> exceptionCounts;

    /**
     * Garbage collection events.
     */
    private List<GarbageCollection> garbageCollections = new ArrayList<>();

    /**
     * GC heap history information.
     */
    private List<GcHeapHistoryEvent> gcHeapHistoryEvents;

    /**
     * GC precious log information.
     */
    private List<GcPreciousLog> gcPreciousLogs;

    /**
     * Global flag information.
     */
    private List<GlobalFlag> globalFlags;

    /**
     * Header.
     */
    private List<Header> headers;

    /**
     * Heap address information
     */
    private HeapAddress heapAddress;

    /**
     * Heap information.
     */
    private List<Heap> heaps;

    /**
     * Host information.
     */
    private Host host;

    /**
     * Exceptions information.
     */
    private List<InternalExceptionEvent> internalExceptionEvents;

    /**
     * Statistics information.
     */
    private List<InternalStatistic> internalStatistics;

    /**
     * JvmOptions object.
     */
    private JvmOptions jvmOptions;

    /**
     * The user the JVM process is running under.
     */
    private String jvmUser;

    /**
     * Preloaded library information.
     */
    private List<LdPreloadFile> ldPreloadFiles;

    /**
     * max_map_count information.
     */
    private List<MaxMapCount> maxMapCounts;

    /**
     * Memory information.
     */
    private List<Meminfo> meminfos;

    /**
     * Memory information.
     */
    private List<Memory> memories;

    /**
     * Narrow klass information
     */
    private NarrowKlass narrowKlass;

    /**
     * Native libraries.
     */
    List<String> nativeLibraries;

    /**
     * Unknown native libraries (not OS, not Java).
     */
    private List<String> nativeLibrariesUnknown;

    /**
     * Native memory tracking information.
     */
    private List<NativeMemoryTracking> nativeMemoryTrackings;

    /**
     * OS information.
     */
    private List<OsInfo> osInfos;

    /**
     * Priodic native trim information.
     */
    private PeriodicNativeTrim periodicNativeTrim;

    /**
     * pid_max information.
     */
    private List<PidMax> pidMaxes;

    /**
     * Process memory information.
     */
    private List<ProcessMemory> processMemories;

    /**
     * Register to memory mapping information.
     */
    private List<RegisterToMemoryMapping> registerToMemoryMappings;

    /**
     * rlimit information.
     */
    private Rlimit rlimit;

    /**
     * The rpm directory name, or null if not an rpm install.
     * 
     * For example:
     * 
     * java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64
     * 
     * java-11-openjdk-11.0.7.10-4.el7_8.x86_64
     * 
     * java-17-openjdk-17.0.4.1.1-2.el9_0.x86_64
     */
    String rpmDirectory = null;

    /**
     * Signal information.
     */
    private SigInfo sigInfo;

    /**
     * Stack information.
     */
    private List<Stack> stacks;

    /**
     * Stack slot to memory mapping information.
     */
    private List<StackSlotToMemoryMapping> stackSlotToMemoryMappings;

    /**
     * Thread information.
     */
    private List<Thread> threads;

    /**
     * threads-max information.
     */
    private List<ThreadsMax> threadsMaxes;

    /**
     * JVM crash time information.
     */
    private Time time;

    /**
     * Combined time + elapsed time information.
     */
    private TimeElapsedTime timeElapsedTime;

    /**
     * Timeout.
     */
    private List<Timeout> timeouts;

    /**
     * JVM crash time timezone information in JDK8.
     */
    private Timezone timezone;

    /**
     * Transparent Huge Pages (THP) defrag (/sys/kernel/mm/transparent_hugepage/defrag) information.
     */
    private List<TransparentHugepageDefrag> transparentHugepageDefrags;

    /**
     * Transparent Huge Pages (THP) enabled (/sys/kernel/mm/transparent_hugepage/enabled) information.
     */
    private List<TransparentHugepageEnabled> transparentHugepageEnableds;

    /**
     * Transparent Huge Pages (THP) hpage_pmd_size (/sys/kernel/mm/transparent_hugepage/hpage_pmd_size) information.
     */
    private List<TransparentHugepageHpagePmdSize> transparentHugepageHpagePmdSizes;

    /**
     * uname information.
     */
    private Uname uname;

    /**
     * Log lines that do not match any existing logging patterns.
     */
    private List<String> unidentifiedLogLines;

    /**
     * VMware information.
     */
    private List<VirtualizationInfo> virtualizationInfos;

    /**
     * VM arguments information.
     */
    private List<VmArguments> vmArguments;

    /**
     * JVM environment information.
     */
    private VmInfo vmInfo;

    /**
     * VM operation information.
     */
    private VmOperation vmOperation;

    /**
     * VM state information.
     */
    private VmState vmState;

    /**
     * ZGC phase switch information.
     */
    private List<ZgcPhaseSwitchEvent> zgcPhaseSwitchEvents;

    /*
     * Default constructor.
     */
    public FatalErrorLog() {
        analysis = new ArrayList<Analysis>();
        classesUnloadedEvents = new ArrayList<ClassesUnloadedEvent>();
        compilationEvents = new ArrayList<CompilationEvent>();
        containerInfos = new ArrayList<ContainerInfo>();
        cpuInfos = new ArrayList<CpuInfo>();
        currentCompileTasks = new ArrayList<CurrentCompileTask>();
        deoptimizationEvents = new ArrayList<DeoptimizationEvent>();
        dllOperationEvents = new ArrayList<DllOperationEvent>();
        dynamicLibraries = new ArrayList<DynamicLibrary>();
        environmentVariables = new ArrayList<EnvironmentVariable>();
        events = new ArrayList<Event>();
        exceptionCounts = new ArrayList<ExceptionCounts>();
        gcHeapHistoryEvents = new ArrayList<GcHeapHistoryEvent>();
        gcPreciousLogs = new ArrayList<GcPreciousLog>();
        globalFlags = new ArrayList<GlobalFlag>();
        headers = new ArrayList<Header>();
        heaps = new ArrayList<Heap>();
        internalExceptionEvents = new ArrayList<InternalExceptionEvent>();
        internalStatistics = new ArrayList<InternalStatistic>();
        ldPreloadFiles = new ArrayList<LdPreloadFile>();
        maxMapCounts = new ArrayList<MaxMapCount>();
        meminfos = new ArrayList<Meminfo>();
        memories = new ArrayList<Memory>();
        nativeLibraries = new ArrayList<String>();
        nativeLibrariesUnknown = new ArrayList<String>();
        nativeMemoryTrackings = new ArrayList<NativeMemoryTracking>();
        osInfos = new ArrayList<OsInfo>();
        pidMaxes = new ArrayList<PidMax>();
        processMemories = new ArrayList<ProcessMemory>();
        registerToMemoryMappings = new ArrayList<RegisterToMemoryMapping>();
        stacks = new ArrayList<Stack>();
        stackSlotToMemoryMappings = new ArrayList<StackSlotToMemoryMapping>();
        threads = new ArrayList<Thread>();
        threadsMaxes = new ArrayList<ThreadsMax>();
        timeouts = new ArrayList<Timeout>();
        transparentHugepageDefrags = new ArrayList<TransparentHugepageDefrag>();
        transparentHugepageEnableds = new ArrayList<TransparentHugepageEnabled>();
        transparentHugepageHpagePmdSizes = new ArrayList<TransparentHugepageHpagePmdSize>();
        unidentifiedLogLines = new ArrayList<String>();
        virtualizationInfos = new ArrayList<VirtualizationInfo>();
        vmArguments = new ArrayList<VmArguments>();
        zgcPhaseSwitchEvents = new ArrayList<ZgcPhaseSwitchEvent>();
    }

    /**
     * Convenience method to add <code>Analysis</code>.
     * 
     * @param key
     *            The <code>Analysis</code> to add.
     */
    public void addAnalysis(Analysis key) {
        analysis.add(key);
    }

    /**
     * Convenience method to add <code>org.github.joa.util.Analysis</code>.
     * 
     * @param key
     *            The <code>org.github.joa.util.Analysis</code> to add.
     */
    public void addAnalysis(org.github.joa.util.Analysis key) {
        if (jvmOptions == null) {
            // Create JvmOptions for analysis
            JvmContext context = new JvmContext(null);
            JvmOptions options = new JvmOptions(context);
            jvmOptions = options;
        }
        jvmOptions.addAnalysis(key);
    }

    /**
     * Do analysis.
     */
    public void doAnalysis() {
        doHydrate();
        String opts = getJvmArgs();
        if (opts != null) {
            JvmContext context = new JvmContext(opts, getJavaVersionMajor(), getJavaVersionMinor());
            context.setGarbageCollectors(getGarbageCollectorsFromEvents());
            context.setContainer(isContainer());
            context.setOs(getOs());
            context.setBit(getBit());
            context.setContainer(isContainer());
            context.setMemory(getMemoryTotal());
            jvmOptions = new JvmOptions(context);
            jvmOptions.doAnalysis();
            if (jvmOptions.getOptions().size() == 0) {
                analysis.add(0, Analysis.INFO_OPTS_NONE);
            }
        } else {
            analysis.add(0, Analysis.INFO_OPTS_UNKNOWN);
        }
        // Unidentified logging lines
        if (JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) >= 8 && !getUnidentifiedLogLines().isEmpty()) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE);
        }
        // swap
        if (getSwapTotal() > 0 && getSwapFree() >= 0) {
            // Check for excessive swap usage
            int swapUsedPercent = 100 - JdkMath.calcPercent(getSwapFree(), getSwapTotal());
            if (swapUsedPercent > 5 && swapUsedPercent < 20) {
                analysis.add(Analysis.INFO_SWAPPING);
            } else if (swapUsedPercent >= 20) {
                analysis.add(Analysis.WARN_SWAPPING);
            }
        }
        // Check for swap disabled
        if (getSwapTotal() == 0) {
            analysis.add(Analysis.INFO_SWAP_DISABLED);
            // Check if collector is appropriate for no-swap (e.g. container) use cases
            if (getGarbageCollectors().contains(GarbageCollector.G1) && getSwapTotal() == 0) {
                analysis.add(Analysis.WARN_SWAP_DISABLED_G1);
            } else if (getGarbageCollectors().contains(GarbageCollector.CMS) && getSwapTotal() == 0) {
                analysis.add(Analysis.WARN_SWAP_DISABLED_CMS);
            }
        }
        // Check if JVM process has memory swapped out
        if (getJvmMemorySwappedOut() > 0) {
            analysis.add(Analysis.WARN_SWAPPED_OUT);
        }
        // Crashes related to Oracle JDBC OCI (native) driver
        if (getStackFrameTop() != null && getStackFrameTop().matches("^C  \\[libocijdbc.+$")) {
            analysis.add(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER);
        }
        if (getEventTimestamp("^Event: (\\d{1,}\\.\\d{3}) Loaded shared library .+libocijdbc.+.(dll|so)$") > 0
                && getUptime() > 0 && getUptime() - getEventTimestamp(
                        "^Event: (\\d{1,}\\.\\d{3}) Loaded shared library .+libocijdbc.+.(dll|so)$") <= 1000) {
            analysis.add(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING);
        }
        if ((getStackFrame(2) != null && getStackFrame(2).matches("^C  \\[libocijdbc.+$"))
                || isInStack("libclntsh.(dll|so)")) {
            analysis.add(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION);
        }
        // Check Oracle JDBC driver / JDK compatibility
        List<String> nativeLibrariesUnknown = getNativeLibrariesUnknown();
        if (!nativeLibrariesUnknown.isEmpty()) {
            Iterator<String> iterator = nativeLibrariesUnknown.iterator();
            Pattern pattern = Pattern.compile(JdkRegEx.ORACLE_JDBC_OCI_DRIVER_PATH);
            Matcher matcher;
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                matcher = pattern.matcher(nativeLibraryPath);
                if (matcher.find()) {
                    if (!analysis.contains(Analysis.INFO_ORACLE_JDBC_OCI)
                            && !analysis.contains(Analysis.ERROR_ORACLE_JDBC_OCI_DRIVER)
                            && !analysis.contains(Analysis.ERROR_ORACLE_JDBC_OCI_LOADING)
                            && !analysis.contains(Analysis.WARN_ORACLE_JDBC_OCI_CONNECION)) {
                        analysis.add(Analysis.INFO_ORACLE_JDBC_OCI);
                    }
                    String versionRegEx = "^.*[/\\\\]oracle[/\\\\]product[/\\\\](\\d{1,})\\.\\d{1,}\\.\\d{1,}"
                            + "(\\.\\d{1,})?[/\\\\].*$";
                    Pattern pattern2 = Pattern.compile(versionRegEx);
                    Matcher matcher2 = pattern2.matcher(nativeLibraryPath);
                    if (matcher2.find()) {
                        Integer oracleDatabaseVersion = Integer.parseInt(matcher2.group(1));
                        if (JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) > 11
                                && oracleDatabaseVersion < 21) {
                            analysis.add(Analysis.ERROR_ORACLE_JDBC_JDK_INCOMPATIBLE);
                            break;
                        }
                    }
                }
            }
        }
        // Check PostgreSQL JDBC driver / JDK8 compatibility
        List<String> jars = getJars();
        if (!jars.isEmpty()) {
            String postgresqlJdbcDriverPath = null;
            Iterator<String> iterator = jars.iterator();
            while (iterator.hasNext()) {
                String jar = iterator.next();
                if (jar.matches(JdkRegEx.POSTGRESQL_JDBC_DRIVER_PATH)) {
                    postgresqlJdbcDriverPath = jar;
                    break;
                }
            }
            if (postgresqlJdbcDriverPath != null) {
                Pattern pattern = Pattern.compile(JdkRegEx.POSTGRESQL_JDBC_DRIVER_PATH);
                Matcher matcher = pattern.matcher(postgresqlJdbcDriverPath);
                if (matcher.find()) {
                    Integer minorVersion = Integer.parseInt(matcher.group(3));
                    if (JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) == 8 && minorVersion < 5) {
                        analysis.add(Analysis.ERROR_POSTGRESQL_JDBC_JDK8_INCOMPATIBLE);
                    }
                }
            }
        }
        // Check for ancient fatal error log
        if (KrashUtil.dayDiff(getCrashDate(), new Date()) > 30) {
            analysis.add(Analysis.WARN_FATAL_ERROR_LOG_ANCIENT);
        }
        // Check for ancient JDK
        if (getJdkRelease() != null && getJdkRelease().getBuildDate() != null
                && KrashUtil.dayDiff(getJdkRelease().getBuildDate(), new Date()) > 365) {
            analysis.add(Analysis.INFO_JDK_ANCIENT);
        }
        // Check for unknown JDK version
        if (getJavaSpecification() == JavaSpecification.UNKNOWN) {
            analysis.add(Analysis.ERROR_JDK_VERSION_UNKNOWN);
        }
        // Check for unsupported JDK version
        if (getJavaSpecification() == JavaSpecification.JDK6 || getJavaSpecification() == JavaSpecification.JDK7) {
            analysis.add(Analysis.ERROR_JDK_VERSION_UNSUPPORTED);
        }
        // Check for JVM failing to start
        if (isCrashOnStartup()) {
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
            if (getOs() == Os.LINUX) {
                if (getOsVendor() == OsVendor.CENTOS) {
                    // CentOs redistributes RH build of OpenJDK
                    analysis.add(0, Analysis.INFO_RH_BUILD_CENTOS);
                } else if (getRpmDirectory() != null) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_RPM_INSTALL);
                    if (getCpuArch() == CpuArch.POWER9 && getJavaSpecification() == JavaSpecification.JDK8
                            && getOsString().matches(".+7\\.(7|8|9).+")) {
                        // power8 JDK8 deployed on power9 on RHEL 7
                        analysis.add(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9);
                    }
                } else if (isRhRpm()) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_RPM_BASED);
                } else {
                    analysis.add(0, Analysis.INFO_RH_BUILD_LINUX_ZIP);
                }
                // Check for RHEL6
                if (getOsVersion() == OsVersion.RHEL6) {
                    analysis.add(Analysis.WARN_RHEL6);
                }
                // Check for RHEL7 Power9
                if (getOsVersion() == OsVersion.RHEL7 && (getArchOs() == Arch.PPC64 || getArchOs() == Arch.PPC64LE)) {
                    analysis.add(Analysis.WARN_RHEL7_POWER9);
                }
                // Check for unnecessary use of -XX:+UnlockExperimentalVMOptions with Shenandoah on RH build.
                if (getJvmOptions() != null) {
                    if (JdkUtil.isOptionEnabled(getJvmOptions().getUseShenandoahGc())
                            && JdkUtil.isOptionEnabled(getJvmOptions().getUnlockExperimentalVmOptions())) {
                        analysis.add(Analysis.INFO_RH_OPT_EXPERIMENTAL_SHENANDOAH);
                    }
                }
            } else if (isWindows()) {
                analysis.add(0, Analysis.INFO_RH_BUILD_WINDOWS_ZIP);
            }
        } else if (getJavaVendor() == JavaVendor.UNIDENTIFIED) {
            if (getJdkReleaseString() != org.github.krashpad.util.Constants.PROPERTY_UNKNOWN) {
                if (!isRhVersion() || (isRhVersion() && getJdkBuiltBy() != BuiltBy.UNKNOWN && !isRhBuildString()
                        && getJdkBuildDate() != null && !isRhBuildDate())) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_NOT);
                } else if (isRhVersion() && (getJdkBuiltBy() == BuiltBy.UNKNOWN
                        || (getJdkBuiltBy() != BuiltBy.UNKNOWN && isRhBuildString()))) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_POSSIBLE);
                }
            }
        }
        // Check if there is vm code in the stack
        if (haveFramesInStack() && !haveVmCodeInStack()) {
            analysis.add(Analysis.INFO_STACK_NO_VM_CODE);
        }
        if (getJavaSpecification() != JavaSpecification.UNKNOWN && !isJdkLts()) {
            analysis.add(Analysis.WARN_JDK_NOT_LTS);
        }
        // JNA
        if (isJnaCrash()) {
            if (getStackFrameTop() != null && getStackFrameTop().matches("^C  .+ffi_prep_closure_loc.+$")) {
                analysis.add(Analysis.ERROR_JNA_FFI_PREP_CLOSURE_LOC);
            } else if (getJavaVendor() == JavaVendor.RED_HAT) {
                analysis.add(Analysis.ERROR_JNA_RH);
            } else {
                analysis.add(Analysis.ERROR_JNA);
            }
        } else {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getFilePath() != null && event.getFilePath().matches("^.+[\\\\/](jna|JNA).+$")) {
                    analysis.add(Analysis.INFO_JNA);
                    break;
                }
            }
        }
        // Check for JDK8 ZipFile contention
        if (JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) >= 6
                && JdkUtil.getJavaSpecificationNumber(getJavaSpecification()) <= 8) {
            if ((getStackFrameTopCompiledJavaCode() != null && getStackFrameTopCompiledJavaCode()
                    .matches("^.+java\\.util\\.zip\\.ZipFile\\.(getEntry|open).+$"))
                    || (getStackFrameTop() != null
                            && getStackFrameTop().matches("^C[ ]{1,}\\[libzip\\.so.*\\][ ]{1,}readCEN.*$"))) {
                analysis.add(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION);
            }
        }
        // Check for JDK8 Deflator contention
        if (getJavaSpecification() == JavaSpecification.JDK8 && getStackFrameTopCompiledJavaCode() != null
                && getStackFrameTopCompiledJavaCode().matches("^.+java\\.util\\.zip\\.Deflater\\.deflateBytes.+$")) {
            analysis.add(Analysis.ERROR_JDK8_DEFLATER_CONTENTION);
        }
        // Check for unsynchronized access to DirectByteBuffer
        String regexStubRoutines = "^v  ~(BufferBlob::)?StubRoutines.*$";
        if (getStackFrameTop() != null && getStackFrameTop().matches(regexStubRoutines)) {
            if (isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER)) {
                analysis.add(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION);
            } else if (isInStack("(com[\\./]itextpdf[\\./](text[\\./])?io|"
                    + "com[\\./]itextpdf[\\./]text[\\./]pdf[\\./]RandomAccessFileOrArray)")) {
                analysis.add(Analysis.ERROR_ITEXT_IO);
            } else {
                analysis.add(Analysis.ERROR_STUBROUTINES);
            }
        }
        // Check for insufficient physical memory
        if (getJvmMemoryHeapReserved() > 0 && getJvmMemoryMetaspaceReserved() > 0 && getMemoryTotal() > 0
                && (getJvmMemoryHeapReserved() + getJvmMemoryMetaspaceReserved()) > getMemoryTotal()) {
            if (getSwapTotal() == 0) {
                analysis.add(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP);
            } else {
                analysis.add(Analysis.WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP);
            }
        }
        // CrashOnOutOfMemoryError
        if (jvmOptions != null && JdkUtil.isOptionEnabled(jvmOptions.getCrashOnOutOfMemoryError())
                && isError("OutOfMemory encountered: Java heap space")) {
            analysis.add(Analysis.ERROR_CRASH_ON_OOME_HEAP);
        }
        // OOME
        if (isMemoryAllocationFail()) {
            if (isCrashOnStartup()) {
                if (getApplication() == Application.TOMCAT_SHUTDOWN) {
                    analysis.add(Analysis.ERROR_OOME_TOMCAT_SHUTDOWN);
                } else if (getApplication() == Application.JBOSS_VERSION) {
                    analysis.add(Analysis.ERROR_OOME_JBOSS_VERSION);
                } else if (getApplication() == Application.AMQ_CLI) {
                    analysis.add(Analysis.ERROR_OOME_AMQ_CLI);
                } else {
                    long allocation = Long.MIN_VALUE;
                    allocation = getFailedMemoryAllocation();
                    if (allocation < 0) {
                        // Use JVM estimated initial process size
                        allocation = getJvmMemoryTotalCommitted();
                    }
                    if (allocation > 0 && getOsCommitLimit() >= 0 && getOsCommitLimitUsed() >= 0
                            && allocation > (getOsCommitLimit() - getOsCommitLimitUsed()) && !isOvercommitted()) {
                        // Strong evidence for vm.overcommit_memory=2, but possible resource limit
                        analysis.add(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP);
                        if (jvmOptions == null
                                || (jvmOptions.getInitialHeapSize() != null && jvmOptions.getMaxHeapSize() != null
                                        && (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(
                                                jvmOptions.getInitialHeapSize())) == JdkUtil.getByteOptionBytes(
                                                        JdkUtil.getByteOptionValue(jvmOptions.getMaxHeapSize()))))) {
                            analysis.add(Analysis.INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX);
                        }
                    } else if (allocation >= 0 && getOsCommitLimit() >= 0 && getOsCommitLimitUsed() >= 0
                            && getOsCommitLimit() >= getOsCommitLimitUsed() && isOvercommitDisabled()
                            && allocation > (getOsCommitLimit() - getOsCommitLimitUsed()
                                    - JdkUtil.convertSize(Long.parseLong("136"), 'M', 'B'))) {
                        // Allocation > (available commit limit - user_reserve_kbytes [assume worse case 128M] -
                        // admin_reserve_kbytes [assume worse case 8M])
                        analysis.add(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP);
                    } else if (allocation >= 0 && (getMemoryFree() >= 0 || getSwapFree() >= 0)
                            && allocation >= (Math.max(getMemoryFree(), 0) + Math.max(getSwapFree(), 0))) {
                        if (JdkMath.calcPercent(allocation, getMemoryTotal()) < 50) {
                            analysis.add(Analysis.ERROR_OOME_EXTERNAL_STARTUP);
                        } else {
                            analysis.add(Analysis.ERROR_OOME_JVM_STARTUP);
                        }
                        if (jvmOptions == null
                                || (jvmOptions.getInitialHeapSize() != null && jvmOptions.getMaxHeapSize() != null
                                        && (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(
                                                jvmOptions.getInitialHeapSize())) == JdkUtil.getByteOptionBytes(
                                                        JdkUtil.getByteOptionValue(jvmOptions.getMaxHeapSize()))))) {
                            analysis.add(Analysis.INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX);
                        }
                    } else {
                        // Resource limit
                        if (Os.WINDOWS == getOs()) {
                            if (getSwapFree() == 0) {
                                analysis.add(Analysis.ERROR_OOME_WLIMIT_PAGE_FILE_STARTUP);
                                // Don't double report
                                analysis.remove(Analysis.WARN_SWAPPING);
                            } else {
                                analysis.add(Analysis.ERROR_OOME_WLIMIT_STARTUP);
                            }
                        } else {
                            analysis.add(Analysis.ERROR_OOME_RLIMIT_STARTUP);
                        }
                    }
                    // G1 collector is not good when memory is tight
                    if (getGarbageCollectors().contains(GarbageCollector.G1)) {
                        analysis.add(Analysis.WARN_OOM_G1);
                    }
                }
                // Don't double report the JVM failing to start
                analysis.remove(Analysis.INFO_JVM_STARTUP_FAILS);
            } else {
                // Crash after startup
                if (getJvmMemoryThreadStackReserved() > 0 && getMemoryTotal() > 0
                        && JdkMath.calcPercent(getJvmMemoryThreadStackReserved(), getMemoryTotal()) > 50) {
                    // thread leak
                    int executorPoolThreadCount = getJavaThreadCount(JdkRegEx.WILDFLY_EXECUTOR_POOL_THREAD);
                    if (executorPoolThreadCount > 0 && getJavaThreadCount() > 0
                            && JdkMath.calcPercent(executorPoolThreadCount, getJavaThreadCount()) > 50) {
                        analysis.add(Analysis.ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL);
                    } else {
                        analysis.add(Analysis.ERROR_OOME_THREAD_LEAK);
                    }
                } else {
                    if (Os.LINUX == getOs() && getFailedMemoryAllocation() >= 0 && getOsCommitLimit() >= 0
                            && getOsCommitLimitUsed() >= 0 && getOsCommitLimit() >= getOsCommitLimitUsed()
                            && getFailedMemoryAllocation() > (getOsCommitLimit() - getOsCommitLimitUsed())
                            && !isOvercommitDisabled()) {
                        // Allocation > available commit limit
                        analysis.add(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT);
                    } else if (Os.LINUX == getOs() && getFailedMemoryAllocation() >= 0 && getOsCommitLimit() >= 0
                            && getOsCommitLimitUsed() >= 0 && getOsCommitLimit() >= getOsCommitLimitUsed()
                            && isOvercommitDisabled() && getFailedMemoryAllocation() > (getOsCommitLimit()
                                    - getOsCommitLimitUsed() - JdkUtil.convertSize(Long.parseLong("136"), 'M', 'B'))) {
                        // Allocation > (available commit limit - user_reserve_kbytes [assume worse case 128M] -
                        // admin_reserve_kbytes [assume worse case 8M])
                        analysis.add(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT);
                    } else if (getFailedMemoryAllocation() >= 0 && (getMemoryFree() >= 0 || getSwapFree() >= 0)
                            && getFailedMemoryAllocation() >= (Math.max(getMemoryFree(), 0)
                                    + Math.max(getSwapFree(), 0))) {
                        // Allocation > available physical memory
                        if (getJvmMemoryTotalReserved() > 0 && getMemoryTotal() > 0) {
                            if (JdkMath.calcPercent(getJvmMemoryTotalReserved(), getMemoryTotal()) >= 95) {
                                analysis.add(Analysis.ERROR_OOME_JVM);
                            } else {
                                if (getJvmMemoryTotalUsed() > 0
                                        && JdkMath.calcPercent(getJvmMemoryTotalUsed(), getMemoryTotal()) < 95) {
                                    if (getMemBalloonedNow() > 0) {
                                        analysis.add(Analysis.ERROR_OOME_EXTERNAL_OR_HYPERVISOR);
                                    } else {
                                        analysis.add(Analysis.ERROR_OOME_EXTERNAL);
                                    }
                                } else {
                                    analysis.add(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL);
                                }
                            }
                        }
                    } else if ((getFailedMemoryAllocation() >= 0 && (getMemoryFree() >= 0 || getSwapFree() >= 0)
                            && getFailedMemoryAllocation() < (Math.max(getMemoryFree(), 0)
                                    + Math.max(getSwapFree(), 0)))
                            || ((getMemoryFree() >= 0 && getMemoryTotal() > 0
                                    && JdkMath.calcPercent(getMemoryFree(), getMemoryTotal()) >= 50)
                                    || (getJvmMemoryTotalCommitted() >= 0 && getMemoryTotal() > 0 && JdkMath
                                            .calcPercent(getJvmMemoryTotalCommitted(), getMemoryTotal()) < 50))) {
                        // Likely a limit if: (1) allocation < available physical memory. (2) JVM memory < 1/2 total
                        // memory.
                        if (Os.WINDOWS == getOs()) {
                            if (getSwapFree() == 0) {
                                analysis.add(Analysis.ERROR_OOME_WLIMIT_PAGE_FILE);
                                // Don't double report
                                analysis.remove(Analysis.WARN_SWAPPING);
                            } else {
                                if ((isInHeader("Java Heap may be blocking the growth of the native heap")
                                        || isInHeader("compressed oops")) && isCompressedOops()) {
                                    analysis.add(Analysis.ERROR_OOME_WLIMIT_OOPS);
                                } else {
                                    analysis.add(Analysis.ERROR_OOME_WLIMIT);
                                }
                            }
                        } else {
                            if ((isInHeader("Java Heap may be blocking the growth of the native heap")
                                    || isInHeader("compressed oops")) && isCompressedOops()) {
                                analysis.add(Analysis.ERROR_OOME_RLIMIT_OOPS);
                            } else if (isInHeader("Failed to map memory")) {
                                analysis.add(Analysis.ERROR_OOME_RLIMIT_MAX_MAP_COUNT);
                            } else {
                                analysis.add(Analysis.ERROR_OOME_RLIMIT);
                            }
                        }
                    } else {
                        if ((isTruncated() || isInHeader("Java Heap may be blocking the growth of the native heap")
                                || isInHeader("compressed oops")) && isCompressedOops()) {
                            analysis.add(Analysis.ERROR_OOME_OOPS);
                        } else {
                            analysis.add(Analysis.ERROR_OOME);
                        }
                    }
                }
                // G1 collector is not good when memory is tight
                if (getGarbageCollectors().contains(GarbageCollector.G1)) {
                    analysis.add(Analysis.WARN_OOM_G1);
                }
            }
        }
        // libjvm.so/jvm.dll
        if (getJavaSpecification() == JavaSpecification.JDK8 && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 282 && getStackFrameTop() != null
                && getStackFrameTop()
                        .matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  ShenandoahUpdateRefsClosure::do_oop.+$")) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_ROOT_UPDATER);
        } else if (getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 312 && getStackFrameTop() != null
                && getStackFrameTop()
                        .matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  MetadataOnStackMark::~MetadataOnStackMark.+$")) {
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK);
        } else if (getCurrentThreadName() != null && getCurrentThreadName().matches("^.+CompilerThread\\d{1,}.+$")) {
            analysis.add(Analysis.ERROR_COMPILER_THREAD);
        } else if (getStackFrameTop() != null
                && getStackFrameTop().matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  (ModuleEntry::purge_reads|"
                        + "ModuleEntryTable::purge_all_module_reads).+$")) {
            analysis.add(Analysis.ERROR_MODULE_ENTRY_PURGE_READS);
        } else if (getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) >= 262
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 282 && getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+JfrEventClassTransformer::on_klass_creation.+$")) {
            analysis.add(Analysis.ERROR_JDK8_JFR_CLASS_TRANSFORMED);
        } else if (getStackFrameTop() != null
                && !isError("There is insufficient memory for the Java Runtime Environment to continue")
                && !isError("#  fatal error: OutOfMemory encountered: Java heap space")) {
            // Other libjvm.so/jvm.dll analysis
            if (getStackFrameTop().matches("^V  \\[libjvm\\.so.+\\](.+)?$")) {
                analysis.add(Analysis.ERROR_LIBJVM_SO);
            } else if (getStackFrameTop().matches("^V  \\[jvm\\.dll.+\\](.+)?$")) {
                analysis.add(Analysis.ERROR_JVM_DLL);
            }
        }
        // Signal numbers
        switch (getSignalNumber()) {
        case EXCEPTION_ACCESS_VIOLATION:
            analysis.add(Analysis.INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION);
            break;
        case EXCEPTION_STACK_OVERFLOW:
            analysis.add(Analysis.INFO_SIGNO_EXCEPTION_STACK_OVERFLOW);
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
        }
        // Signal codes
        switch (getSignalCode()) {
        case BUS_ADRALN:
            analysis.add(Analysis.INFO_SIGCODE_BUS_ADRALN);
            break;
        case BUS_ADRERR:
            if (getOs() == Os.LINUX) {
                analysis.add(Analysis.INFO_SIGCODE_BUS_ADDERR_LINUX);
            } else {
                analysis.add(Analysis.INFO_SIGCODE_BUS_ADDERR);
            }
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
        case SI_TKILL:
            analysis.add(Analysis.INFO_SIGCODE_SI_TKILL);
            break;
        case SI_USER:
            analysis.add(Analysis.INFO_SIGCODE_SI_USER);
            break;
        case UNKNOWN:
        default:
            break;
        }
        // Floating point error
        if (getSignalNumber().equals(SignalNumber.SIGFPE) || getSignalCode().equals(SignalCode.FPE_INTDIV)) {
            analysis.add(Analysis.ERROR_FPE);
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
        if (getSignalNumber() != SignalNumber.EXCEPTION_STACK_OVERFLOW && haveStackOverFlowError()) {
            analysis.add(Analysis.ERROR_STACKOVERFLOW);
        } else {
            if (getThreadStackFreeSpace() > getThreadStackSize()) {
                // Applies only to ThreadStackSize (not CompilerThreadStackSize, VMThreadStackSize, MarkStackSize, the
                // JLI_Launch method in main.c that starts the JVM, or C code).
                if (currentThread != null && !(currentThread.isCompilerThread() || currentThread.isVmThread())
                        && stacks.size() > 0 && !isInStack("JLI_Launch")) {
                    analysis.add(Analysis.INFO_STACK_FREESPACE_GT_STACK_SIZE);
                }
            }
        }
        // LinkageError
        if (haveLinkageError()) {
            analysis.add(Analysis.ERROR_LINKAGE);
        }
        // Thread stack size
        long threadStackMaxSize = getThreadStackSize();
        if ((jvmOptions == null || !hasAnalysis(org.github.joa.util.Analysis.WARN_THREAD_STACK_SIZE_TINY.getKey()))
                && threadStackMaxSize < 1) {
            addAnalysis(org.github.joa.util.Analysis.WARN_THREAD_STACK_SIZE_TINY);
        } else if ((jvmOptions == null
                || !hasAnalysis(org.github.joa.util.Analysis.WARN_THREAD_STACK_SIZE_SMALL.getKey()))
                && threadStackMaxSize < 128) {
            addAnalysis(org.github.joa.util.Analysis.WARN_THREAD_STACK_SIZE_SMALL);
        }
        // OutOfMemoryError other than "Metaspace" or "Compressed class space" caught and thrown
        if (haveOomeThrownJavaHeap()) {
            analysis.add(Analysis.ERROR_OOME_THROWN_JAVA_HEAP);
        }
        // "OutOfMemoryError: Metaspace" caught and thrown
        if (haveOomeThrownMetaspace()) {
            analysis.add(Analysis.ERROR_OOME_THROWN_METASPACE);
        }
        // "OutOfMemoryError: Compressed class space" caught and thrown
        if (haveOomeThrownCompressedClassSpace()) {
            analysis.add(Analysis.ERROR_OOME_THROWN_COMP_CLASS_SPACE);
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
        // cgroup version
        if (getCgroupVersion() != null) {
            analysis.add(Analysis.INFO_CGROUP_VERSION);
        }
        if (haveCgroupMemoryLimit()) {
            analysis.add(Analysis.INFO_CGROUP_MEMORY_LIMIT);
        }
        // truncated fatal error log
        if (isTruncated()) {
            analysis.add(Analysis.INFO_TRUNCATED);
        }
        // Storage analysis
        if (getOs() == Os.LINUX && !dynamicLibraries.isEmpty()) {
            switch (getStorageDevice()) {
            case AWS_BLOCK_STORAGE:
                analysis.add(Analysis.INFO_STORAGE_AWS);
                break;
            case NFS:
                analysis.add(Analysis.INFO_STORAGE_NFS);
                break;
            case UNIDENTIFIED:
                analysis.add(Analysis.INFO_STORAGE_UNKNOWN);
                break;
            default:
                break;
            }
        }
        // Check for explicit gc disabled on EAP7
        if (getApplication() == Application.JBOSS_EAP7 && jvmOptions != null
                && JdkUtil.isOptionEnabled(jvmOptions.getDisableExplicitGc())) {
            // Don't double report
            if (hasAnalysis(org.github.joa.util.Analysis.WARN_EXPLICIT_GC_DISABLED.getKey())) {
                jvmOptions.removeAnalysis(org.github.joa.util.Analysis.WARN_EXPLICIT_GC_DISABLED);
            }
            analysis.add(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7);
        }
        // Check for CMS incremental mode with > 2 cpu
        if (getCpusLogical() > 2 && jvmOptions != null && !JdkUtil.isOptionDisabled(jvmOptions.getUseConcMarkSweepGc())
                && JdkUtil.isOptionEnabled(jvmOptions.getCmsIncrementalMode())) {
            analysis.add(Analysis.WARN_CMS_INCREMENTAL_MODE);
        }
        // Memory corruption
        if (sigInfo != null && sigInfo.getSignalAddress() != null
                && sigInfo.getSignalAddress().matches(JdkRegEx.POINTER_NULL)) {
            analysis.add(Analysis.ERROR_POINTER_NULL);
        } else if (sigInfo != null && sigInfo.getSignalAddress() != null
                && sigInfo.getSignalAddress().matches(JdkRegEx.POINTER_INVALID)) {
            analysis.add(Analysis.ERROR_POINTER_INVALID);
        } else if (isMemoryCorruption()) {
            analysis.add(Analysis.ERROR_MEMORY_CORRUPTION);
        }
        if (getStackFrameTop() != null && getStackFrameTop()
                .matches("J \\d{1,} C2 java\\.lang\\.String\\.compareTo\\(Ljava/lang/Object;\\)I")) {
            if (getCpuInfos().size() > 0) {

            }
        }
        // Crashes in Java compiled code
        if (getStackFrameTop() != null && getStackFrameTop().matches("^J \\d{1,}%{0,1} C[12].+$")) {
            if (getStackFrameTop().matches("^.+java\\.lang\\.String\\.compareTo\\(Ljava\\/lang/Object;\\)I.+$")
                    && hasCpuCapability("avx2") && (getJvmOptions() == null || !(getJvmOptions().getUseAvx() != null
                            && getJvmOptions().getUseAvx().equals("-XX:UseAVX=0")))) {
                analysis.add(Analysis.ERROR_AVX2_STRING_COMPARE_TO);
            } else {
                analysis.add(Analysis.ERROR_COMPILED_JAVA_CODE);
                if (hasCpuCapability("avx2") && (getJvmOptions() == null || (getJvmOptions().getUseAvx() != null
                        && !getJvmOptions().getUseAvx().equals("-XX:UseAVX=0")))) {
                    analysis.add(Analysis.INFO_COMPILED_JAVA_CODE_AVX2);
                }
            }
        }
        // Check for possible JFFI usage
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getFilePath() != null && event.getFilePath().matches("^.+[\\\\/](jffi|JFFI).+$")) {
                    analysis.add(Analysis.INFO_JFFI);
                    break;
                }
            }
        }
        // Check for JVM crash due to temporary font file being removed from java.io.tmpdir.
        if (getStackFrameTopJava() != null
                && getStackFrameTopJava().matches("^.+sun\\.font\\.FreetypeFontScaler\\.getGlyphImageNative.+$")) {
            analysis.add(Analysis.ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE);
        }
        // Check for JDK8 Deflator contention
        if ((getArchOs().equals(Arch.PPC64) || getArchOs().equals(Arch.PPC64LE)) && getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+JavaThread::pd_get_top_frame_for_profiling.+$")) {
            analysis.add(Analysis.ERROR_JFR_PD_GET_TOP_FRAME);
        }

        // Cannot get library information
        if (!dynamicLibraries.isEmpty() && dynamicLibraries.size() >= 2 && dynamicLibraries.size() <= 3) {
            // 3 because later JDKs include a footer that may be populated
            DynamicLibrary event = dynamicLibraries.get(1);
            if (event.getLogEntry().matches("^Can not get library information for pid = \\d{1,}$")) {
                analysis.add(Analysis.ERROR_CANNOT_GET_LIBRARY_INFORMATION);
            }
        }
        // Check for G1ParScanThreadState::copy_to_survivor_space
        if (getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+G1ParScanThreadState::copy_to_survivor_space.+$")
                && (getJavaVersionMajor() == 8 || (getJavaVersionMajor() == 11 && getJavaVersionMinor() < 10))) {
            analysis.add(Analysis.ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE);
            // Don't double report
            analysis.remove(Analysis.ERROR_LIBJVM_SO);
        }
        // Check for PSPromotionManager::copy_to_survivor_space
        if (getStackFrameTop() != null
                && getStackFrameTop().matches("^V.+PSPromotionManager::copy_to_survivor_space.+$")) {
            analysis.add(Analysis.ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE);
            // Don't double report
            analysis.remove(Analysis.ERROR_LIBJVM_SO);
        }
        // Check if JVM user ne USERNAME
        if (getJvmUser() != null && getUsername() != null && !getJvmUser().equals(getUsername())) {
            analysis.add(Analysis.INFO_JVM_USER_NE_USERNAME);
        }
        // Check for many threads
        if (getJavaThreadCount() > 1000) {
            if (getJavaThreadCount() > 5000) {
                analysis.add(Analysis.WARN_THREADS_MANY);
            } else {
                analysis.add(Analysis.INFO_THREADS_MANY);
            }
        }
        // Check environments
        if (isVMWareEnvironment()) {
            analysis.add(Analysis.INFO_VMWARE);
        } else if (isHyperVEnvironment()) {
            analysis.add(Analysis.INFO_HYPERV);
        }
        // Check for mmap resources in deleted state
        if (!getDynamicLibraries().isEmpty() && getMmapDeletedCount() > 0) {
            analysis.add(Analysis.WARN_MMAP_DELETED);
        }
        // Check for RHEL/JDK rpm version mismatch
        if (isRhRpmInstall() && getRhelVersion() != null && getJdkRhelVersion() != null) {
            if ((getJdkRhelVersion().indexOf('.') != -1 && !getRhelVersion().matches(getJdkRhelVersion()))
                    || (getJdkRhelVersion().indexOf('.') == -1
                            && !getRhelVersion().startsWith((getJdkRhelVersion())))) {
                analysis.add(0, Analysis.WARN_RHEL_JDK_RPM_MISMATCH);
                if (analysis.contains(Analysis.WARN_JDK_NOT_LATEST)) {
                    analysis.remove(Analysis.WARN_JDK_NOT_LATEST);
                }
            }
        }
        // Crash in HashMap
        if (getStackFrameTop() != null && getStackFrameTop().matches("^J.+java\\.util\\.HashMap.+$")) {
            analysis.add(Analysis.ERROR_HASHMAP);
        }
        // Specific CompilerThread crashes
        if (getCurrentThreadName() != null && getCurrentThreadName().matches("^.+C2 CompilerThread\\d{1,}.+$")) {
            if (isInHeader("guarantee\\(n != NULL\\) failed: No Node.") && isInStack("IdealLoopTree::beautify_loops")) {
                analysis.add(Analysis.ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS);
                // Don't double report
                analysis.remove(Analysis.ERROR_COMPILER_THREAD);
            } else if (getStackFrameTop() != null
                    && getStackFrameTop().matches("^.*MinINode::Ideal\\(PhaseGVN\\*, bool\\).*$")
                    && ((getJavaSpecification() == JavaSpecification.JDK8
                            && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) > 0
                            && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 275)
                            || (getJavaSpecification() == JavaSpecification.JDK11
                                    && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) > 0
                                    && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) < 10))) {
                analysis.add(Analysis.ERROR_COMPILER_THREAD_C2_MININODE_IDEAL);
                // Don't double report
                analysis.remove(Analysis.ERROR_COMPILER_THREAD);
            } else if ((isInHeader("ok_to_convert\\(Node\\*, Node\\*\\)")
                    || isInHeader("SubINode::Ideal\\(PhaseGVN\\*, bool\\)")
                    || isInStack("^.*IfNode::fold_compares\\(PhaseIterGVN\\*\\).*$"))
                    && ((getJavaSpecification() == JavaSpecification.JDK11
                            && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) > 0
                            && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) < 9))) {
                analysis.add(Analysis.ERROR_COMPILER_THREAD_C2_IFNODE_FOLDCOMPARES);
                // Don't double report
                analysis.remove(Analysis.ERROR_COMPILER_THREAD);
            } else if (!getCurrentCompileTasks().isEmpty()) {
                Iterator<CurrentCompileTask> iterator = getCurrentCompileTasks().iterator();
                while (iterator.hasNext()) {
                    CurrentCompileTask event = iterator.next();
                    if (event.getLogEntry() != null && event.getLogEntry()
                            .matches("^.+sun\\.security\\.ssl\\.SSLEngineInputRecord::decodeInputRecord.+")) {
                        if ((getJavaSpecification() == JavaSpecification.JDK11
                                && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) == 16)
                                || (getJavaSpecification() == JavaSpecification.JDK17
                                        && JdkUtil.getJdk17UpdateNumber(getJdkReleaseString()) == 4)) {
                            analysis.add(Analysis.ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE);
                            // Don't double report
                            analysis.remove(Analysis.ERROR_COMPILER_THREAD);
                            analysis.remove(Analysis.ERROR_OOME_NATIVE_OR_EXTERNAL);
                            analysis.remove(Analysis.ERROR_OOME_JVM);
                        }
                        break;
                    }
                }
            }
        }
        // Crash during shutdown
        if (!getEvents().isEmpty()) {
            Event lastEventEvent = getEvents().get(getEvents().size() - 1);
            if (lastEventEvent.getLogEntry().matches("^.+Executing VM operation: Exit$")) {
                analysis.add(Analysis.INFO_SHUTDOWN);
            }
        }
        // iText
        if (isInStack("com\\.itextpdf\\.text")) {
            analysis.add(0, Analysis.WARN_ITEXT);
        } else if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getLogEntry() != null && event.getLogEntry().matches("^.+itext.*\\.jar$")) {
                    analysis.add(Analysis.INFO_ITEXT);
                    break;
                }
            }
        }
        // Lucene
        if (isInStack("org\\.apache\\.lucene\\.")) {
            analysis.add(0, Analysis.WARN_LUCENE);
        }
        // IBM Toolkit
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getLogEntry() != null && event.getLogEntry().matches("^.+jt400\\.jar$")) {
                    analysis.add(Analysis.INFO_IBM_TOOLKIT);
                    break;
                }
            }
        }
        // ERROR_JDK8_LIBC_CFREE
        if (getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) < 262) {
            if (!headers.isEmpty()) {
                Iterator<Header> iterator = headers.iterator();
                while (iterator.hasNext()) {
                    Header event = iterator.next();
                    if (event.isProblematicFrame() && event.getLogEntry().matches("^.+libc.+cfree\\+0x1c$")
                            && (getJvmOptions() == null || getJvmOptions().getUseGcLogFileRotation() != null)
                            && getCurrentThreadName() != null
                            && getCurrentThreadName().matches("^ConcurrentGCThread .+$")) {
                        analysis.add(Analysis.ERROR_JDK8_LIBC_CFREE);
                        break;
                    }
                }
            }
        }
        // VM operations
        if (vmOperation != null) {
            if (vmOperation.getVmOperation().equals("BulkRevokeBias")) {
                analysis.add(Analysis.INFO_VM_OPERATION_BULK_REVOKE_BIAS);
            } else if (vmOperation.getVmOperation().equals("CGC_Operation")) {
                analysis.add(Analysis.INFO_VM_OPERATION_CONCURRENT_GC);
            } else if (vmOperation.getVmOperation().equals("GetThreadListStackTraces")) {
                analysis.add(Analysis.WARN_VM_OPERATION_THREAD_DUMP_JVMTI);
            } else if (vmOperation.getVmOperation().equals("HeapDumper")) {
                analysis.add(Analysis.INFO_VM_OPERATION_HEAP_DUMP);
            } else if (vmOperation.getVmOperation().equals("PrintThreads")) {
                analysis.add(Analysis.INFO_VM_OPERATION_THREAD_DUMP);
            }
        }
        // DBCP2
        String orgApacheCommonsDbcp2 = "org[\\.\\/]apache[\\.\\/]commons[\\.\\/]dbcp2[\\.\\/]";
        if (isInStack(orgApacheCommonsDbcp2)) {
            analysis.add(Analysis.INFO_DBCP2);
        }
        // PostgreSQL connection
        String postgreSqlConnection = "org[\\.\\/]postgresql[\\.\\/]Driver[\\.\\/]connect\\(";
        if (isInStack(postgreSqlConnection)) {
            analysis.add(Analysis.INFO_POSTGRESQL_CONNECTION);
        }
        // ld.so.preload
        if (!getLdPreloadFiles().isEmpty()) {
            analysis.add(Analysis.INFO_LD_SO_PRELOAD);
        }
        // Unknown native libraries
        if (!getNativeLibrariesUnknown().isEmpty()) {
            analysis.add(Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN);
        }
        // Dynatrace
        if (getStackFrameTop() != null
                && getStackFrameTop().matches("^.*" + JdkRegEx.NATIVE_LIBRARY_DYNATRACE + ".*$")) {
            // Crash in Dynatrace
            analysis.add(Analysis.ERROR_DYNATRACE);
        } else if (isInStack(JdkRegEx.NATIVE_LIBRARY_DYNATRACE)) {
            // Dynatrace in stack
            analysis.add(0, Analysis.WARN_DYNATRACE);
        } else if (!getNativeLibrariesUnknown().isEmpty()) {
            // Dynatrace detected
            Iterator<String> iterator = getNativeLibrariesUnknown().iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && nativeLibrary.matches(JdkRegEx.NATIVE_LIBRARY_DYNATRACE)) {
                    analysis.add(Analysis.INFO_DYNATRACE);
                    break;
                }
            }
        }
        // Wily/DX APM
        if ((getStackFrameTop() != null && getStackFrameTop().matches("^.*" + JdkRegEx.NATIVE_LIBRARY_WILY + ".*$"))
                || (getStackFrameTopJava() != null
                        && getStackFrameTopJava().matches("^.+ com\\.wily\\.introscope\\..+"))) {
            // Crash in Wily
            analysis.add(Analysis.ERROR_WILY);
        } else if (isInStack(" com\\.wily\\.introscope\\.")) {
            // Wily in stack
            analysis.add(0, Analysis.WARN_WILY);
        } else if (!getNativeLibrariesUnknown().isEmpty()) {
            // Wily detected
            Iterator<String> iterator = getNativeLibrariesUnknown().iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && nativeLibrary.matches(JdkRegEx.NATIVE_LIBRARY_WILY)) {
                    analysis.add(Analysis.INFO_WILY);
                    break;
                }
            }
        }
        // AppDynamics
        if (getJvmOptions() != null && getJvmOptions().getJavaagent() != null) {
            Iterator<String> iterator = getJvmOptions().getJavaagent().iterator();
            while (iterator.hasNext()) {
                String javaagent = iterator.next();
                if (javaagent.matches(JdkRegEx.JAVAAGENT_APP_DYNAMICS)) {
                    analysis.add(Analysis.INFO_APP_DYNAMICS_DETECTED);
                    break;
                }
            }
        }
        if (!analysis.contains(Analysis.INFO_APP_DYNAMICS_DETECTED) && compilationEvents != null
                && isInCompilationEvents(JdkRegEx.PACKAGE_APP_DYNAMICS)) {
            analysis.add(Analysis.INFO_APP_DYNAMICS_DETECTED);
        }
        if (!analysis.contains(Analysis.INFO_APP_DYNAMICS_DETECTED) && getJvmOptions() != null
                && getJvmOptions().getJavaagent() != null) {
            Iterator<String> iterator = getJvmOptions().getJavaagent().iterator();
            while (iterator.hasNext()) {
                String javaagent = iterator.next();
                if (javaagent.endsWith(JdkRegEx.JAR_APP_DYNAMICS)) {
                    analysis.add(Analysis.INFO_APP_DYNAMICS_POSSIBLE);
                    break;
                }
            }
        }
        // RHEL9 + JDK8
        if (getOsVersion() == OsVersion.RHEL9 && getJavaSpecification() == JavaSpecification.JDK8) {
            analysis.add(Analysis.INFO_RHEL9_JDK8);
        }
        // Crash in 3rd party or unknown library
        if (getNativeLibraryInCrash() != null) {
            if (!getNativeLibrariesUnknown().isEmpty()) {
                Iterator<String> iterator = getNativeLibrariesUnknown().iterator();
                while (iterator.hasNext()) {
                    String unknownNativeLibary = iterator.next();
                    if (unknownNativeLibary.contains(getNativeLibraryInCrash())) {
                        analysis.add(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN);
                        break;
                    }
                }
            }
        }
        // pki_tomcat
        if (getApplication() == Application.PKI_TOMCAT) {
            analysis.add(Analysis.INFO_PKI_TOMCAT);
        }
        // JSS
        if ((getStackFrameTop() != null && getStackFrameTop().matches("^.*" + JdkRegEx.NATIVE_LIBRARY_JSS + ".*$"))
                || (getStackFrameTopJava() != null && getStackFrameTopJava().matches("^.+ org\\.mozilla\\.jss\\..+"))) {
            // Crash in JSS
            analysis.add(Analysis.ERROR_JSS);
        } else if (isInStack(" org\\.mozilla\\.jss\\.")) {
            // JSS in stack
            analysis.add(0, Analysis.WARN_JSS);
        } else if (!nativeLibraries.isEmpty()) {
            // JSS detected
            Iterator<String> iterator = nativeLibraries.iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && nativeLibrary.matches(JdkRegEx.NATIVE_LIBRARY_JSS)) {
                    analysis.add(Analysis.INFO_JSS);
                    break;
                }
            }
        }
        // Microsoft SQL Server native driver
        if (!getNativeLibrariesUnknown().isEmpty()) {
            Iterator<String> iterator = getNativeLibrariesUnknown().iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && nativeLibrary.matches(JdkRegEx.NATIVE_LIBRARY_MICROSOFT_SQL_SERVER)) {
                    analysis.add(Analysis.INFO_MICROSOFT_SQL_SERVER_NATIVE);
                    break;
                }
            }
        }
        // Detective work when debug symbols are missing but many clues exist
        if ((analysis.contains(Analysis.ERROR_JVM_DLL) || analysis.contains(Analysis.ERROR_LIBJVM_SO))
                && analysis.contains(Analysis.WARN_DEBUG_SYMBOLS) && (analysis.contains(Analysis.ERROR_POINTER_NULL)
                        || analysis.contains(Analysis.ERROR_POINTER_INVALID))) {
            if (analysis.contains(Analysis.INFO_APP_DYNAMICS_DETECTED)
                    || analysis.contains(Analysis.INFO_APP_DYNAMICS_POSSIBLE)
                            && analysis.contains(Analysis.INFO_VM_OPERATION_CONCURRENT_GC)) {
                if (getJavaSpecification() == JavaSpecification.JDK11
                        && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) <= 12) {
                    analysis.add(Analysis.ERROR_MODULE_ENTRY_PURGE_READS_POSSIBLE);
                    analysis.remove(Analysis.ERROR_JVM_DLL);
                    analysis.remove(Analysis.ERROR_LIBJVM_SO);
                }
            }
        }
        // Google native library detection
        if (!getNativeLibrariesGoogle().isEmpty()) {
            analysis.add(Analysis.INFO_NATIVE_LIBRARIES_GOOGLE);
        }
        // JBoss native library detection
        if (!getNativeLibrariesJBoss().isEmpty()) {
            analysis.add(Analysis.INFO_NATIVE_LIBRARIES_JBOSS);
        }
        // JBoss native library detection
        if (!getNativeLibrariesTomcat().isEmpty()) {
            analysis.add(Analysis.INFO_NATIVE_LIBRARIES_TOMCAT);
        }
        // VMware native library detection
        if (!getNativeLibrariesVmWare().isEmpty()) {
            analysis.add(Analysis.INFO_NATIVE_LIBRARIES_VMWARE);
        }
        // Check max_map_count limit
        if (dynamicLibrariesMappingCount > 0) {
            if (getMaxMapCountLimit() > 0) {
                if (JdkMath.calcPercent(dynamicLibrariesMappingCount, getMaxMapCountLimit()) >= 99) {
                    analysis.add(Analysis.WARN_MAX_MAP_COUNT_RLIMIT);
                }
            } else {
                int defaultMaxMapCountLimit = 65530;
                if (JdkMath.calcPercent(dynamicLibrariesMappingCount, defaultMaxMapCountLimit) >= 99) {
                    analysis.add(Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE);
                }
            }
        }
        if ((getJavaSpecification() == JavaSpecification.JDK8
                && JdkUtil.getJdk8UpdateNumber(getJdkReleaseString()) == 372)
                || (getJavaSpecification() == JavaSpecification.JDK11
                        && JdkUtil.getJdk11UpdateNumber(getJdkReleaseString()) == 19)
                || (getJavaSpecification() == JavaSpecification.JDK17
                        && JdkUtil.getJdk17UpdateNumber(getJdkReleaseString()) == 7)) {
            if ((getStackFrameTopCompiledJavaCode() != null && getStackFrameTopCompiledJavaCode()
                    .matches("^.+java\\.util\\.GregorianCalendar\\.computeTime\\(\\).+$"))
                    || isInCompilationEvents("java\\.util\\.GregorianCalendar::computeTime")) {
                analysis.add(Analysis.ERROR_GREGORIANCALENDAR_COMPUTETIME);
                if (hasAnalysis(Analysis.ERROR_POINTER_NULL.getKey())) {
                    removeAnalysis(Analysis.ERROR_POINTER_NULL);
                }
            }
        }
        // Check for _JAVA_SR_SIGNUM environment variable
        if (isInEnvironmentVariables("_JAVA_SR_SIGNUM")) {
            analysis.add(Analysis.INFO_JAVA_SR_SIGNO);
        }
        // Check for experimental options being set by ergonomics
        if (!getGlobalFlagsExperimentalErgonomic().isEmpty()) {
            analysis.add(Analysis.WARN_EXPERIMENTAL_ERGONOMIC);
        }
        // Check for vm.overcommit_memory=2 and vm.overcommit_ratio=100
        if (getMemoryTotal() > 0 && getOsCommitLimit() > 0 && getMemoryTotal() == getOsCommitLimit()) {
            analysis.add(Analysis.INFO_OVERCOMMIT_DISABLED_RATIO_100);
        }
        // Check if MaxRAMPercentage is used without MaxRAM when available memory > 128g prior to JDK13
        if (jvmOptions != null && jvmOptions.getMaxRAMPercentage() != null && jvmOptions.getMaxHeapSize() == null
                && jvmOptions.getMaxRAM() == null && getJavaVersionMajor() > 0 && getJavaVersionMajor() < 13) {
            BigDecimal oneHundredTwentyEightGigabytes = new BigDecimal("128")
                    .multiply(org.github.joa.util.Constants.GIGABYTE);
            if (getMemoryTotal() > oneHundredTwentyEightGigabytes.longValue()) {
                analysis.add(Analysis.WARN_MAX_RAM_LIMIT);
            }
        }
        // Check for timeouts occurring during error reporting
        if (!timeouts.isEmpty()) {
            analysis.add(Analysis.ERROR_TIMEOUT);
        } else if (!headers.isEmpty()) {
            // Check header
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isTimeout()) {
                    analysis.add(Analysis.ERROR_TIMEOUT);
                    break;
                }
            }
        }
        // Large pages JVM/OS HugeTLB configuration analysis
        if (hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGES_LINUX_HUGETLBFS.toString())
                && getExplicitHugePagesPoolSize() <= 0) {
            analysis.add(Analysis.ERROR_LARGE_PAGES_HUGETLBFS_EXPLICIT_JVM_YES_OS_NO);
        } else {
            // JVM is not configured to use explicit huge pages
            if (getExplicitHugePagesPoolSize() > 0) {
                analysis.add(Analysis.WARN_EXPLICIT_HUGE_PAGES_OS_YES_JVM_NO);
            }
        }
        // Large pages JVM/OS Transparent Huge Pages configuration analysis
        if (hasAnalysis(org.github.joa.util.Analysis.ERROR_LARGE_PAGES_LINUX_HUGETLB_THP.toString())
                || hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGES_LINUX_THPS.toString())) {
            if (getTransparentHugePagesMode() != TransparentHugepageEnabled.MODE.UNKNOWN) {
                if (getTransparentHugePagesMode() == TransparentHugepageEnabled.MODE.ALWAYS) {
                    analysis.add(Analysis.ERROR_LARGE_PAGES_THP_JVM_YES_OS_ALWAYS);
                } else if (getTransparentHugePagesMode() == TransparentHugepageEnabled.MODE.MADVISE) {
                    analysis.add(Analysis.INFO_LARGE_PAGES_THP_JVM_YES_OS_MADVISE);
                } else if (getTransparentHugePagesMode() == TransparentHugepageEnabled.MODE.NEVER) {
                    analysis.add(Analysis.ERROR_LARGE_PAGES_THP_JVM_YES_OS_NEVER);
                }
            } else {
                analysis.add(Analysis.INFO_LARGE_PAGES_THP_JVM_YES_OS_UNDETERMINED);
            }
        }
        // Consider explicit large pages configuration for large heaps when Linux kernel THP = "all"
        if (hasAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGES_CONSIDER.toString())
                && getTransparentHugePagesMode() == TransparentHugepageEnabled.MODE.ALWAYS) {
            analysis.add(Analysis.INFO_LARGE_PAGES_CONSIDER_THP_OS_ALWAYS);
            // Remove generic analysis
            jvmOptions.removeAnalysis(org.github.joa.util.Analysis.INFO_LARGE_PAGES_CONSIDER);
        }
        if (getTransparentHugePagesMode() == TransparentHugepageEnabled.MODE.ALWAYS
                && !((getJavaVersionMajor() == 17 && getJavaVersionMinor() >= 10)
                        || (getJavaVersionMajor() == 21 && getJavaVersionMinor() >= 1))) {
            analysis.add(Analysis.WARN_THP_OS_ALWAYS);
        }
        // ZGC large pages analysis
        if (getJvmOptions() != null && JdkUtil.isOptionEnabled(getJvmOptions().getUseLargePages())
                && (getGarbageCollectors().contains(GarbageCollector.ZGC_NON_GENERATIONAL)
                        || getGarbageCollectors().contains(GarbageCollector.ZGC_GENERATIONAL))
                && getShmemHugePages() == 0) {
            analysis.add(Analysis.ERROR_LARGE_PAGES_ZGC_SHMEM_ZERO);
        }
        // RHEL7 ELS
        if (getCrashDate() != null && getCrashDate().compareTo(KrashUtil.RHEL7_ELS_START) >= 0
                && getOsVersion() == OsVersion.RHEL7 && getRhelVersion() != null && !getRhelVersion().equals("7.9")) {
            analysis.add(Analysis.WARN_RHEL7_ELS_UNSUPPORTED_VERSION);
        }
        // Windows small page file size
        if (Os.WINDOWS == getOs() && getSwapTotal() < getMemoryTotal()) {
            analysis.add(Analysis.WARN_PAGE_FILE_SMALL);
        }
        // 32-bit JDK
        if (Arch.X86 == getArchJdk()) {
            analysis.add(Analysis.INFO_JDK_32);
        }
        // FIPS mode
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getLogEntry() != null && event.getLogEntry().matches("^.+libsoftokn3\\.so$")) {
                    analysis.add(Analysis.INFO_FIPS_MODE);
                    break;
                }
            }
        }
        // External processes consuming significant memory
        if (getMemoryTotal() > 0 && getMemoryTotal() > 0) {
            long memoryExternal = getMemoryTotal() - getMemoryFree();
            if (JdkMath.calcPercent(memoryExternal, getMemoryTotal()) >= 10) {
                analysis.add(0, Analysis.WARN_MEMORY_EXTERNAL);
            }
        }
        // Inspection/printing errors
        if (!stackSlotToMemoryMappings.isEmpty()) {
            Iterator<StackSlotToMemoryMapping> iterator = stackSlotToMemoryMappings.iterator();
            while (iterator.hasNext()) {
                StackSlotToMemoryMapping event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_INSPECTING_TOP_OF_STACK);
                }
            }
        }
        if (!threads.isEmpty()) {
            Iterator<Thread> iterator = threads.iterator();
            while (iterator.hasNext()) {
                Thread event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_ALL_THREADS);
                }
            }
        }
        if (heapAddress != null && heapAddress.isErrorOccurredDuringErrorReporting()) {
            analysis.add(Analysis.ERROR_PRINTING_COMPRESSED_OOPS_MODE);
        }
        if (elapsedTime != null && elapsedTime.isErrorOccurredDuringErrorReporting()) {
            analysis.add(Analysis.ERROR_PRINTING_DATE_AND_TIME);
        }
        if (!heaps.isEmpty()) {
            Iterator<Heap> iterator = heaps.iterator();
            while (iterator.hasNext()) {
                Heap event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_HEAP_INFORMATION);
                }
            }
        }
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_MEMORY_INFO);
                }
            }
        }
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_OS_INFORMATION);
                }
            }
        }
        if (!headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_PROBLEMATIC_FRAME);
                }
            }
        }
        if (!registerToMemoryMappings.isEmpty()) {
            Iterator<RegisterToMemoryMapping> iterator = registerToMemoryMappings.iterator();
            while (iterator.hasNext()) {
                RegisterToMemoryMapping event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_REGISTER_INFO);
                }
            }
        }
        if (!deoptimizationEvents.isEmpty()) {
            Iterator<DeoptimizationEvent> iterator = deoptimizationEvents.iterator();
            while (iterator.hasNext()) {
                DeoptimizationEvent event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_RING_BUFFERS);
                }
            }
        }
        if (!stacks.isEmpty()) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
                if (event.isErrorOccurredDuringErrorReporting()) {
                    analysis.add(Analysis.ERROR_PRINTING_STACK);
                }
            }
        }
        // RHEL7 is only supported on JDK8/11.
        if (getOsVersion() == OsVersion.RHEL7 && getJavaVersionMajor() > 0
                && !(getJavaVersionMajor() == 8 || getJavaVersionMajor() == 11)) {
            analysis.add(Analysis.ERROR_RHEL7_JDK_VERSION);

        }
        // kubernets Qos Guaranteed
        if (getCpuQuota() > 0 && getCpuShares() > 0 && getCpuQuota() != getCpuShares()) {
            analysis.add(Analysis.WARN_KUBERNETES_QOS_GUARANTEED_NOT);
        }
        // multi-thread collector < 2 cpu/cores
        if (isMultithreadedGc() && getActiveProcessorCount() > 0 && getActiveProcessorCount() < 2) {
            analysis.add(Analysis.ERROR_MULTITHREADED_COLLECTOR_LT_2_CPU);
        }
    }

    /**
     * Hydrate values. A performance optimization (e.g. to avoid iterating multiple times over huge collections).
     */
    private void doHydrate() {
        hydrateGarbageCollections();
        hydrateJvmUser();
        hydrateNativeLibraries();
        hydrateNativeLibrariesUnknown();
        hydrateRpmDirectory();
    }

    /**
     * @return The active processor count.
     */
    public int getActiveProcessorCount() {
        int activeProcessorCount = Integer.MIN_VALUE;
        if (!containerInfos.isEmpty()) {
            Iterator<ContainerInfo> iterator = containerInfos.iterator();
            while (iterator.hasNext()) {
                ContainerInfo event = iterator.next();
                if (!event.isHeader() && event.getSetting().matches("active_processor_count")) {
                    activeProcessorCount = Integer.parseInt(event.getSettingValue());
                    break;
                }
            }
        }
        return activeProcessorCount;
    }

    /**
     * @return Analysis as a <code>List</code> of String arrays with 2 elements, the first the key, the second the
     *         display literal.
     */
    public List<String[]> getAnalysis() {
        List<String[]> a = new ArrayList<String[]>();
        Iterator<Analysis> itFelAnalysis = analysis.iterator();
        while (itFelAnalysis.hasNext()) {
            Analysis item = itFelAnalysis.next();
            if (item.getKey().equals(Analysis.ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                s.append(getNativeLibraryInCrash());
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.ERROR_CRASH_ON_OOME_HEAP.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                s.append(" Check the following location for a heap dump: ");
                s.append(getJvmOptions().getHeapDumpPath());
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.ERROR_INSPECTING_TOP_OF_STACK.toString())) {
                Iterator<StackSlotToMemoryMapping> iterator = stackSlotToMemoryMappings.iterator();
                while (iterator.hasNext()) {
                    StackSlotToMemoryMapping event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_OOME_RLIMIT_MAX_MAP_COUNT.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                if (getGcPreciousLogWarning() != null) {
                    s.append(" ");
                    s.append(getGcPreciousLogWarning());
                }
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_ALL_THREADS.toString())) {
                Iterator<Thread> iterator = threads.iterator();
                while (iterator.hasNext()) {
                    Thread event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_COMPRESSED_OOPS_MODE.toString())) {
                a.add(new String[] { item.getKey(), heapAddress.getLogEntry() });
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_DATE_AND_TIME.toString())) {
                a.add(new String[] { item.getKey(), elapsedTime.getLogEntry() });
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_HEAP_INFORMATION.toString())) {
                Iterator<Heap> iterator = heaps.iterator();
                while (iterator.hasNext()) {
                    Heap event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_MEMORY_INFO.toString())) {
                Iterator<Memory> iterator = memories.iterator();
                while (iterator.hasNext()) {
                    Memory event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_OS_INFORMATION.toString())) {
                Iterator<OsInfo> iterator = osInfos.iterator();
                while (iterator.hasNext()) {
                    OsInfo event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_PROBLEMATIC_FRAME.toString())) {
                Iterator<Header> iterator = headers.iterator();
                while (iterator.hasNext()) {
                    Header event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_REGISTER_INFO.toString())) {
                Iterator<RegisterToMemoryMapping> iterator = registerToMemoryMappings.iterator();
                while (iterator.hasNext()) {
                    RegisterToMemoryMapping event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_RING_BUFFERS.toString())) {
                Iterator<DeoptimizationEvent> iterator = deoptimizationEvents.iterator();
                while (iterator.hasNext()) {
                    DeoptimizationEvent event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_PRINTING_STACK.toString())) {
                Iterator<Stack> iterator = stacks.iterator();
                while (iterator.hasNext()) {
                    Stack event = iterator.next();
                    if (event.isErrorOccurredDuringErrorReporting()) {
                        a.add(new String[] { item.getKey(), event.getLogEntry() });
                    }
                }
            } else if (item.getKey().equals(Analysis.ERROR_RHEL7_JDK_VERSION.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                String replace = "JDK version";
                int position = s.toString().lastIndexOf(replace);
                StringBuffer with = new StringBuffer();
                with.append("JDK");
                with.append(getJavaVersionMajor());
                s.replace(position, position + replace.length(), with.toString());
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_CGROUP_VERSION.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                s.append(getCgroupVersion());
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_JDK_ANCIENT.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                String replace = ">1 yr";
                int position = s.toString().lastIndexOf(replace);
                StringBuffer with = new StringBuffer();
                BigDecimal years = new BigDecimal(KrashUtil.dayDiff(getJdkRelease().getBuildDate(), new Date()));
                years = years.divide(new BigDecimal(365), 1, HALF_EVEN);
                with.append(years.toString());
                with.append(" years");
                s.replace(position, position + replace.length(), with.toString());
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_NATIVE_LIBRARIES_GOOGLE.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                Iterator<String> iterator = getNativeLibrariesGoogle().iterator();
                boolean punctuate = false;
                while (iterator.hasNext()) {
                    String library = iterator.next();
                    if (punctuate) {
                        s.append(", ");
                    }
                    s.append(library);
                    punctuate = true;
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_NATIVE_LIBRARIES_JBOSS.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                Iterator<String> iterator = getNativeLibrariesJBoss().iterator();
                boolean punctuate = false;
                while (iterator.hasNext()) {
                    String library = iterator.next();
                    if (punctuate) {
                        s.append(", ");
                    }
                    s.append(library);
                    punctuate = true;
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_NATIVE_LIBRARIES_TOMCAT.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                Iterator<String> iterator = getNativeLibrariesTomcat().iterator();
                boolean punctuate = false;
                while (iterator.hasNext()) {
                    String library = iterator.next();
                    if (punctuate) {
                        s.append(", ");
                    }
                    s.append(library);
                    punctuate = true;
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_NATIVE_LIBRARIES_UNKNOWN.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                Iterator<String> iterator = getNativeLibrariesUnknown().iterator();
                boolean punctuate = false;
                while (iterator.hasNext()) {
                    String library = iterator.next();
                    if (punctuate) {
                        s.append(", ");
                    }
                    s.append(library);
                    punctuate = true;
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.INFO_NATIVE_LIBRARIES_VMWARE.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                Iterator<String> iterator = getNativeLibrariesVmWare().iterator();
                boolean punctuate = false;
                while (iterator.hasNext()) {
                    String library = iterator.next();
                    if (punctuate) {
                        s.append(", ");
                    }
                    s.append(library);
                    punctuate = true;
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_EXPERIMENTAL_ERGONOMIC.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                Iterator<GlobalFlag> iterator = getGlobalFlagsExperimentalErgonomic().iterator();
                boolean punctuate = false;
                while (iterator.hasNext()) {
                    GlobalFlag globalFlag = iterator.next();
                    if (punctuate) {
                        s.append(", ");
                    }
                    s.append(globalFlag.getFlag());
                    s.append("=");
                    s.append(globalFlag.getValue());
                    punctuate = true;
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_JDK_NOT_LATEST.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                // Add latest release info
                int releaseDayDiff = KrashUtil.dayDiff(getJdkRelease().getBuildDate(),
                        JdkUtil.getLatestJdkRelease(this).getBuildDate());
                int releaseNumberDiff = JdkUtil.getLatestJdkRelease(this).getNumber() - getJdkRelease().getNumber();
                if (releaseDayDiff > 0 && releaseNumberDiff > 0) {
                    s.append(", which is newer by " + releaseNumberDiff);
                    s.append(" release");
                    if (releaseNumberDiff > 1) {
                        s.append("s");
                    }
                    s.append(" and ");
                    s.append("" + releaseDayDiff);
                    s.append(" day");
                    if (releaseDayDiff > 1) {
                        s.append("s");
                    }
                }
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_MAX_MAP_COUNT_RLIMIT.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                String replace = "memory map areas";
                int position = s.toString().lastIndexOf(replace);
                StringBuffer with = new StringBuffer("memory map areas (");
                with.append(getDynamicLibrariesMappingCount());
                with.append(")");
                s.replace(position, position + replace.length(), with.toString());
                replace = "max_map_count limit";
                position = s.toString().lastIndexOf(replace);
                with.setLength(0);
                with.append("max_map_count limit (");
                with.append(getMaxMapCountLimit());
                with.append(")");
                s.replace(position, position + replace.length(), with.toString());
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_MAX_MAP_COUNT_RLIMIT_POSSIBLE.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                String replace = "memory map areas";
                int position = s.toString().lastIndexOf(replace);
                StringBuffer with = new StringBuffer("memory map areas (");
                with.append(getDynamicLibrariesMappingCount());
                with.append(")");
                s.replace(position, position + replace.length(), with.toString());
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_RHEL_JDK_RPM_MISMATCH.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                s.append("RHEL ");
                s.append(getRhelVersion());
                s.append(" + ");
                s.append(getRpmDirectory());
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_SWAPPED_OUT.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                if (JdkUtil.convertSize(getJvmMemorySwappedOut(), 'B', org.github.joa.util.Constants.UNITS) == 0) {
                    // Provide rounding clue
                    s.append("~");
                }
                s.append(JdkUtil.convertSize(getJvmMemorySwappedOut(), 'B', org.github.joa.util.Constants.UNITS));
                s.append(Character.toString(org.github.joa.util.Constants.UNITS));
                s.append(".");
                a.add(new String[] { item.getKey(), s.toString() });
            } else if (item.getKey().equals(Analysis.WARN_MEMORY_EXTERNAL.toString())) {
                StringBuffer s = new StringBuffer(item.getValue());
                long memoryExternal = getMemoryTotal() - getMemoryFree();
                int percent = JdkMath.calcPercent(memoryExternal, getMemoryTotal());
                if ((percent == 0 && memoryExternal > 0) || (percent == 100 && memoryExternal < getMemoryTotal())) {
                    // Provide rounding clue
                    s.append("~");
                }
                s.append(percent);
                s.append("%.");
                a.add(new String[] { item.getKey(), s.toString() });
            } else {
                a.add(new String[] { item.getKey(), item.getValue() });
            }
        }
        if (jvmOptions != null) {
            Iterator<String[]> itJvmOptionsAnalysis = jvmOptions.getAnalysis().iterator();
            while (itJvmOptionsAnalysis.hasNext()) {
                String[] item = itJvmOptionsAnalysis.next();
                if (item[0].equals(org.github.joa.util.Analysis.INFO_INSTRUMENTATION.toString())) {
                    StringBuffer s = new StringBuffer(item[1]);
                    Iterator<String> iterator = getJvmOptions().getJavaagent().iterator();
                    while (iterator.hasNext()) {
                        String option = iterator.next();
                        s.append(" ");
                        s.append(option);
                    }
                    s.append(".");
                    a.add(new String[] { item[0], s.toString() });
                } else if (item[0].equals(org.github.joa.util.Analysis.INFO_NATIVE_AGENT.toString())) {
                    StringBuffer s = new StringBuffer(item[1]);
                    if (!getJvmOptions().getAgentlib().isEmpty()) {
                        Iterator<String> iterator = getJvmOptions().getAgentlib().iterator();
                        while (iterator.hasNext()) {
                            String option = iterator.next();
                            s.append(" ");
                            s.append(option);
                        }
                    }
                    if (!getJvmOptions().getAgentpath().isEmpty()) {
                        Iterator<String> iterator = getJvmOptions().getAgentpath().iterator();
                        while (iterator.hasNext()) {
                            String option = iterator.next();
                            s.append(" ");
                            s.append(option);
                        }
                    }
                    s.append(".");
                    a.add(new String[] { item[0], s.toString() });
                } else if (item[0].equals(org.github.joa.util.Analysis.INFO_OPTS_UNDEFINED.toString())) {
                    StringBuffer s = new StringBuffer(item[1]);
                    s.append(" Please submit an issue so we can investigate: "
                            + "https://github.com/mgm3746/krashpad/issues. "
                            + "If attaching a fatal error log, be sure to review it and remove any sensitive "
                            + "information.");
                    a.add(new String[] { item[0], s.toString() });
                } else {
                    a.add(item);
                }
            }
        }
        return a;
    }

    /**
     * Convenience method to get the <code>Analysis</code> literal.
     * 
     * @param key
     *            The analysis identifier.
     * @return The analysis display literal, or null if it does not exist.
     */
    public String getAnalysisLiteral(String key) {
        String literal = null;
        Iterator<String[]> i = getAnalysis().iterator();
        while (i.hasNext()) {
            String[] item = i.next();
            if (item[0].equals(key)) {
                literal = item[1];
                break;
            }
        }
        return literal;
    }

    /**
     * The total Transparent Huge Pages (THP) used in bytes.
     * 
     * @return The total Transparent Huge Pages (THP) used in bytes.
     */
    public long getAnonHugePages() {
        long anonHugePages = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regex = "AnonHugePages:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regex);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    anonHugePages = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return anonHugePages;
    }

    /**
     * @return The application running on the JDK
     */
    public Application getApplication() {
        Application application = Application.UNKNOWN;
        // Check libraries
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getLogEntry().matches(JdkRegEx.JAR_JBOSS_EAP6)) {
                    application = Application.JBOSS_EAP6;
                    break;
                } else if (event.getLogEntry().matches(JdkRegEx.JAR_JBOSS_EAP7)) {
                    application = Application.JBOSS_EAP7;
                    break;
                } else if (event.getLogEntry().matches(JdkRegEx.JAR_JEUS)) {
                    application = Application.JEUS;
                    break;
                } else if (event.getLogEntry().matches(JdkRegEx.JAR_TOMCAT)) {
                    application = Application.TOMCAT;
                    // Continue checking for known applications built on top of tomcat
                } else if (event.getLogEntry().matches(JdkRegEx.JAR_PKI_TOMCAT)) {
                    application = Application.PKI_TOMCAT;
                    break;
                }
            }
        }
        // Check threads
        if (application == Application.UNKNOWN) {
            if (!threads.isEmpty()) {
                Iterator<Thread> iterator = threads.iterator();
                while (iterator.hasNext()) {
                    Thread event = iterator.next();
                    if (event.getLogEntry() != null && event.getLogEntry().matches(JdkRegEx.THREAD_JEUS)) {
                        application = Application.JEUS;
                        break;
                    } else if (event.getLogEntry() != null && event.getLogEntry().matches(JdkRegEx.THREAD_PKI_TOMCAT)) {
                        application = Application.PKI_TOMCAT;
                        break;
                    } else if (event.getLogEntry() != null && event.getLogEntry().matches(JdkRegEx.THREAD_RHSSO)) {
                        application = Application.RHSSO;
                        break;
                    }
                }
            }
        }
        // Check java_command
        if (application == Application.UNKNOWN) {
            String javaCommand = getJavaCommand();
            if (javaCommand != null) {
                if (javaCommand.matches(JdkRegEx.ARTEMIS_COMMAND)) {
                    application = Application.AMQ;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_ARTEMIS_CLI)) {
                    application = Application.AMQ_CLI;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_CASSANDRA)) {
                    application = Application.CASSANDRA;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_EAP_CLI)) {
                    application = Application.EAP_CLI;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_JBOSS_VERSION)) {
                    application = Application.JBOSS_VERSION;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_JEUS)) {
                    application = Application.JEUS;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_KAFKA)) {
                    application = Application.KAFKA;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_SPRING_BOOT)) {
                    application = Application.SPRING_BOOT;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_TOMCAT_START)) {
                    application = Application.TOMCAT;
                } else if (javaCommand.matches(JdkRegEx.COMMAND_TOMCAT_STOP)) {
                    application = Application.TOMCAT_SHUTDOWN;
                } else if (javaCommand.matches(JdkRegEx.JAR_WILDFLY)) {
                    application = Application.WILDFLY;
                }
            }
        }
        // Check JVM arguments
        if (application == Application.UNKNOWN) {
            if (getJvmArgs() != null && getJvmArgs().matches("^.*-Dcatalina.base=/var/lib/pki/pki-tomcat.*$")) {
                application = Application.PKI_TOMCAT;
            }
        }
        return application;
    }

    /**
     * @return JDK <code>Arch</code>
     */
    public Arch getArchJdk() {
        Arch arch = Arch.UNKNOWN;
        if (vmInfo != null) {
            arch = vmInfo.getArch();
        } else if (!headers.isEmpty()) {
            // Check header
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isJavaVm()) {
                    if (he.getLogEntry().matches("^.+ppc64.+$")) {
                        arch = Arch.PPC64;
                    } else if (he.getLogEntry().matches("^.+ppc64le.+$")) {
                        arch = Arch.PPC64LE;
                    } else if (he.getLogEntry().matches("^.+solaris-sparc.+$")) {
                        arch = Arch.SPARC;
                    } else if (he.getLogEntry().matches("^.+amd64.+$")) {
                        arch = Arch.X86_64;
                    } else if (he.getLogEntry().matches("^.+x86.+$")) {
                        arch = Arch.X86;
                    }
                }
            }
        }
        return arch;
    }

    /**
     * @return OS <code>Arch</code>
     */
    public Arch getArchOs() {
        Arch arch = Arch.UNKNOWN;
        if (uname != null) {
            arch = uname.getArch();
        } else {
            arch = getArchJdk();
        }
        return arch;
    }

    public BarrierSet getBarrierSet() {
        return barrierSet;
    }

    /**
     * @return <code>Bit</code>
     */
    public Bit getBit() {
        Bit bit = Bit.UNKNOWN;
        if (getArchOs() == Arch.X86) {
            bit = Bit.BIT32;
        }
        return bit;
    }

    /**
     * The cgroup version.
     * 
     * @return The cgroup version, or null if it cannot be determined.
     */
    public String getCgroupVersion() {
        String cgroupVersion = null;
        if (!containerInfos.isEmpty()) {
            Iterator<ContainerInfo> iterator = containerInfos.iterator();
            while (iterator.hasNext()) {
                ContainerInfo event = iterator.next();
                if (!event.isHeader() && event.getSetting().matches("container_type")) {
                    cgroupVersion = event.getSettingValue();
                    break;
                }
            }
        }
        return cgroupVersion;
    }

    public List<ClassesUnloadedEvent> getClassesUnloadedEvents() {
        return classesUnloadedEvents;
    }

    public CommandLine getCommandLine() {
        return commandLine;
    }

    public List<CompilationEvent> getCompilationEvents() {
        return compilationEvents;
    }

    public CompressedClassSpace getCompressedClassSpaceEvent() {
        return compressedClassSpaceEvent;
    }

    /**
     * @return The max compressed class size reserved in bytes.
     */
    public long getCompressedClassSpaceSize() {
        // 1) Determine if compressed pointers are being used.
        boolean usingCompressedPointers = true;

        // 2) Default is to use compressed pointers based on heap size
        BigDecimal thirtyTwoGigabytes = new BigDecimal("32").multiply(org.github.joa.util.Constants.GIGABYTE);
        long heapMaxSize = getHeapMaxSize();
        if (heapMaxSize >= thirtyTwoGigabytes.longValue()) {
            usingCompressedPointers = false;
        }

        // 3) Check if the default behavior is being overridden
        if (jvmOptions != null) {
            if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())) {
                usingCompressedPointers = false;
            } else {
                if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedClassPointers())) {
                    usingCompressedPointers = false;
                } else {
                    usingCompressedPointers = true;
                }
            }
        } else {
            boolean useCompressedOops = true;
            boolean useCompressedClassPointers = true;
            GlobalFlag globalFlagUseCompressedOops = getGlobalFlag("UseCompressedOops");
            if (globalFlagUseCompressedOops != null && globalFlagUseCompressedOops.getValue().equals("false")) {
                useCompressedOops = false;
            }
            GlobalFlag globalFlagUseCompressedClassPointers = getGlobalFlag("UseCompressedClassPointers");
            if (globalFlagUseCompressedClassPointers != null
                    && globalFlagUseCompressedClassPointers.getValue().equals("false")) {
                useCompressedClassPointers = false;
            }
            if (!useCompressedOops) {
                usingCompressedPointers = false;
            } else {
                if (!useCompressedClassPointers) {
                    usingCompressedPointers = false;
                } else {
                    usingCompressedPointers = true;
                }
            }
        }

        long compressedClassSpaceSize = 0;

        if (usingCompressedPointers) {
            // Default is 1g
            compressedClassSpaceSize = JdkUtil.convertSize(1, 'G', 'B');
            // 1st check [Global flags]
            GlobalFlag globalFlagCompressedClassSpaceSize = getGlobalFlag("CompressedClassSpaceSize");
            if (globalFlagCompressedClassSpaceSize != null) {
                compressedClassSpaceSize = Long.parseLong(globalFlagCompressedClassSpaceSize.getValue());
            } else if (jvmOptions != null) {
                if (JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())
                        || JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedClassPointers())) {
                    compressedClassSpaceSize = 0;
                } else if (jvmOptions.getCompressedClassSpaceSize() != null) {
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
                        compressedClassSpaceSize = JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                }
            }
        }
        return compressedClassSpaceSize;
    }

    /**
     * @return The JVM compressed oop mode.
     */
    public CompressedOopMode getCompressedOopMode() {
        CompressedOopMode compressedOopMode = CompressedOopMode.UNKNOWN;
        if (!headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header event = iterator.next();
                if (event.isJavaVm()) {
                    if (!event.getLogEntry().matches(".*compressed oops.*")) {
                        compressedOopMode = CompressedOopMode.NONE;
                    }
                    break;
                }
            }
        }
        if (compressedOopMode != CompressedOopMode.NONE && heapAddress != null) {
            compressedOopMode = heapAddress.getCompressedOopMode();
        }
        return compressedOopMode;
    }

    public List<ContainerInfo> getContainerInfos() {
        return containerInfos;
    }

    /**
     * @return The CPU architecture.
     */
    public CpuArch getCpuArch() {
        CpuArch cpuArch = CpuArch.UNIDENTIFIED;
        if (!cpuInfos.isEmpty()) {
            Iterator<CpuInfo> iterator = cpuInfos.iterator();
            while (iterator.hasNext()) {
                CpuInfo event = iterator.next();
                if (event.getLogEntry().matches("^.+POWER9.+$")) {
                    cpuArch = CpuArch.POWER9;
                    break;
                }
            }
        }
        return cpuArch;
    }

    public List<CpuInfo> getCpuInfos() {
        return cpuInfos;
    }

    /**
     * @return The cpu quota.
     */
    public int getCpuQuota() {
        int cpuQuota = Integer.MIN_VALUE;
        if (!containerInfos.isEmpty()) {
            Iterator<ContainerInfo> iterator = containerInfos.iterator();
            while (iterator.hasNext()) {
                ContainerInfo event = iterator.next();
                if (!event.isHeader() && event.getSetting().matches("cpu_quota")
                        && !event.getSettingValue().equals("no quota")) {
                    cpuQuota = Integer.parseInt(event.getSettingValue());
                    break;
                }
            }
        }
        return cpuQuota;
    }

    /**
     * @return The cpu shares.
     */
    public int getCpuShares() {
        int cpuShares = Integer.MIN_VALUE;
        if (!containerInfos.isEmpty()) {
            Iterator<ContainerInfo> iterator = containerInfos.iterator();
            while (iterator.hasNext()) {
                ContainerInfo event = iterator.next();
                if (!event.isHeader() && event.getSetting().matches("cpu_shares")
                        && !event.getSettingValue().equals("no shares")) {
                    cpuShares = Integer.parseInt(event.getSettingValue());
                    break;
                }
            }
        }
        return cpuShares;
    }

    /**
     * @return The number of logical cpus (cpus x cpu cores x hyperthreading).
     */
    public int getCpusLogical() {
        int cpuLogical = Integer.MIN_VALUE;
        if (!cpuInfos.isEmpty()) {
            Iterator<CpuInfo> iterator = cpuInfos.iterator();
            while (iterator.hasNext()) {
                CpuInfo event = iterator.next();
                if (event.isCpuHeader()) {
                    Pattern pattern = Pattern.compile(CpuInfo._REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find() && matcher.group(1) != null) {
                        int cpus = Integer.parseInt(matcher.group(1));
                        BigDecimal calc = new BigDecimal(cpus);
                        if (matcher.group(5) != null) {
                            int cores = Integer.parseInt(matcher.group(5));
                            calc = calc.multiply(new BigDecimal(cores));
                        }
                        if (matcher.group(6) != null) {
                            int threads = Integer.parseInt(matcher.group(6));
                            calc = calc.multiply(new BigDecimal(threads));
                        }
                        cpuLogical = calc.intValue();
                    }
                    break;
                }
            }
        }
        return cpuLogical;
    }

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
            }
            crashDate = KrashUtil.getDate(MMM, d, yyyy, HH, mm, ss);
        }
        return crashDate;
    }

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
        if (time != null) {
            crashTime.append(time.getTimeString());
            if (timezone != null) {
                crashTime.append(" ");
                crashTime.append(timezone.getTimezone());
            }
        } else if (timeElapsedTime != null) {
            crashTime.append(timeElapsedTime.getTimeString());
        }
        return crashTime.toString();
    }

    public List<CurrentCompileTask> getCurrentCompileTasks() {
        return currentCompileTasks;
    }

    public String getCurrentThreadName() {
        String currentThreadName = null;
        if (currentThread != null) {
            currentThreadName = currentThread.getThreadName();
        } else {
            currentThreadName = org.github.krashpad.util.Constants.PROPERTY_UNKNOWN;
        }
        return currentThreadName;
    }

    public List<DeoptimizationEvent> getDeoptimizationEvents() {
        return deoptimizationEvents;
    }

    public List<DllOperationEvent> getDllOperationEvents() {
        return dllOperationEvents;
    }

    public List<DynamicLibrary> getDynamicLibraries() {
        return dynamicLibraries;
    }

    public int getDynamicLibrariesMappingCount() {
        return dynamicLibrariesMappingCount;
    }

    /**
     * @return The duration of the JVM run in seconds.
     */
    public String getElapsedTime() {
        String elapsedTimeLiteral = null;
        if (elapsedTime != null) {
            elapsedTimeLiteral = elapsedTime.getLiteral();
        } else if (timeElapsedTime != null) {
            elapsedTimeLiteral = timeElapsedTime.getLiteral();
        }
        return elapsedTimeLiteral;
    }

    public End getEnd() {
        return end;
    }

    public List<EnvironmentVariable> getEnvironmentVariables() {
        return environmentVariables;
    }

    /**
     * @return A <code>String</code> describing the cause of the crash.
     */
    public String getError() {
        StringBuilder causedBy = new StringBuilder();
        if (!headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isSignalNumber() || he.isInternalError() || he.isError() || he.isFailed() || he.isInsufficient()
                        || he.isInvalid() || he.isOutOf() || he.isProblematicFrame()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(org.github.krashpad.util.Constants.LINE_SEPARATOR);
                    }
                    causedBy.append(he.getLogEntry());
                }
            }
        }
        return causedBy.toString();
    }

    public List<Event> getEvents() {
        return events;
    }

    /**
     * @param regex
     *            The event regex.
     * @return the timestamp of the first <code>EventEvent</code> that matches the regex in milliseconds, or
     *         Long.MIN_VALUE if no event matches.
     */
    public long getEventTimestamp(String regex) {
        long timestamp = Long.MIN_VALUE;
        if (!events.isEmpty()) {
            Pattern pattern = Pattern.compile(regex);
            Iterator<Event> iterator = events.iterator();
            while (iterator.hasNext()) {
                Event event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    BigDecimal millis = new BigDecimal(matcher.group(1)).movePointRight(3);
                    timestamp = millis.longValue();
                    break;
                }
            }
        }
        return timestamp;
    }

    public List<ExceptionCounts> getExceptionCounts() {
        return exceptionCounts;
    }

    /**
     * The explicit huge pages memory pool size in bytes.
     * 
     * 
     * @return The explicit huge pages memory pool size in bytes.
     */
    public long getExplicitHugePagesPoolSize() {
        long explicityHugePagesPoolSize = Long.MIN_VALUE;
        if (getHugetlb() > 0) {
            explicityHugePagesPoolSize = getHugetlb();
        } else if (getHugePagesTotal() >= 0 && getHugepagesize() >= 0) {
            BigDecimal calc = new BigDecimal(getHugePagesTotal());
            calc = calc.multiply(new BigDecimal(getHugepagesize()));
            explicityHugePagesPoolSize = calc.longValue();
        }
        return explicityHugePagesPoolSize;
    }

    /**
     * @return the memory allocation (in bytes) causing the crash, or a negative number if the crash is not related to a
     *         memory allocation.
     */
    public long getFailedMemoryAllocation() {
        long memoryAllocation = Long.MIN_VALUE;
        if (!headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            String regex = "^.+failed to (allocate|map) (\\d{1,}) bytes.+$";
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.getLogEntry().matches(regex)) {
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        memoryAllocation = JdkUtil.convertSize(Long.parseLong(matcher.group(2)), 'B', 'B');
                        break;
                    }
                }
            }
        }
        return memoryAllocation;
    }

    /**
     * @param releaseString
     *            The JDK release string (e.g. 17.0.4.1+1-LTS).
     * @return The first JDK release that matches a Red Hat build string, or null if none found. Used to get a an
     *         approximate release (e.g. to determine approximate JDK age).
     */
    public Release getFirstJdkRelease(String releaseString) {
        Release firstRelease = null;
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk8.RHEL6_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk8.RHEL7_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk8.RHEL8_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk8.RHEL9_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk8.RHEL_ZIPS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk8.WINDOWS_ZIPS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk11.RHEL7_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk11.RHEL8_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk11.RHEL9_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk11.RHEL_ZIPS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk11.WINDOWS_ZIPS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk17.RHEL8_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk17.RHEL9_X86_64_RPMS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk17.RHEL_ZIPS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk17.WINDOWS_ZIPS);
        }
        if (firstRelease == null) {
            firstRelease = JdkUtil.getFirstReleaseFromReleases(releaseString, Jdk21.RHEL8_X86_64_RPMS);
        }
        return firstRelease;
    }

    /**
     * The maximum {@link org.github.krashpad.domain.jdk.GarbageCollection} duration time.
     * 
     * @return maximum pause duration (milliseconds).
     */
    public synchronized long getGarbageCollectionDurationMax() {
        return longs(this.garbageCollections, GarbageCollection::getDuration).mapToLong(Long::valueOf).max().orElse(0);
    }

    /**
     * The total {@link org.github.krashpad.domain.jdk.GarbageCollection} pause time.
     * 
     * @return total pause duration (milliseconds).
     */
    public synchronized long getGarbageCollectionDurationTotal() {
        return longs(this.garbageCollections, GarbageCollection::getDuration).collect(summingLong(Long::valueOf));
    }

    public List<GarbageCollection> getGarbageCollections() {
        return garbageCollections;
    }

    /**
     * @return Garbage collection throughput as a percent rounded to the nearest integer. CG throughput is the percent
     *         of time not spent doing GC. 0 means all time was spent doing GC. 100 means no time was spent doing GC.
     */
    public long getGarbageCollectionThroughput() {
        long gcTimeSpan = garbageCollections.get(garbageCollections.size() - 1).getTimestampEndGc()
                - garbageCollections.get(0).getTimestampStartGc();
        long timeNotGc = gcTimeSpan - getGarbageCollectionDurationTotal();
        BigDecimal throughput = new BigDecimal(timeNotGc);
        throughput = throughput.divide(new BigDecimal(gcTimeSpan), 2, HALF_EVEN);
        return throughput.movePointRight(2).longValue();
    }

    /**
     * @return The list of garbage collectors identified from events, JVM options, or JDK defaults.
     */
    public List<GarbageCollector> getGarbageCollectors() {
        if (getGarbageCollectorsFromEvents().size() > 0) {
            return getGarbageCollectorsFromEvents();
        } else if (jvmOptions != null) {
            return jvmOptions.getExpectedGarbageCollectors();
        } else {
            ArrayList<GarbageCollector> garbageCollectors = new ArrayList<GarbageCollector>();
            garbageCollectors.add(GarbageCollector.UNKNOWN);
            return garbageCollectors;
        }
    }

    /**
     * @return The list of garbage collectors as determined by inspecting events.
     */
    public List<GarbageCollector> getGarbageCollectorsFromEvents() {
        List<GarbageCollector> garbageCollectors = new ArrayList<GarbageCollector>();
        if (!heaps.isEmpty()) {
            Iterator<Heap> iterator = heaps.iterator();
            while (iterator.hasNext()) {
                Heap event = iterator.next();
                if (event.getLogEntry().matches("^[ ]{0,}Shenandoah.+$")) {
                    garbageCollectors.add(GarbageCollector.SHENANDOAH);
                    break;
                } else if (event.getLogEntry().matches("^[ ]{0,}garbage-first.+$")) {
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
                } else if (event.getLogEntry().matches("^[ ]{0,}ZHeap.+$")) {
                    // generational and non-generational look the same
                    if (getJavaVersionMajor() < 21) {
                        garbageCollectors.add(GarbageCollector.ZGC_NON_GENERATIONAL);
                    } else {
                        if (getBarrierSet() != null && getBarrierSet().getLogEntry() != null
                                && getBarrierSet().getLogEntry().equals("ZBarrierSet")) {
                            garbageCollectors.add(GarbageCollector.ZGC_GENERATIONAL);
                        } else {
                            garbageCollectors.add(GarbageCollector.ZGC_NON_GENERATIONAL);
                        }
                    }
                    break;
                } else if (event.getLogEntry().matches("^[ ]{0,}def new.+$")
                        && !garbageCollectors.contains(GarbageCollector.SERIAL_NEW)) {
                    garbageCollectors.add(GarbageCollector.SERIAL_NEW);
                } else if ((event.getLogEntry().matches("^[ ]{0,}PSOldGen.+$")
                        || event.getLogEntry().matches("^[ ]{0,}tenured.+$"))
                        && !garbageCollectors.contains(GarbageCollector.SERIAL_OLD)) {
                    garbageCollectors.add(GarbageCollector.SERIAL_OLD);
                }
            }
        }
        return garbageCollectors;
    }

    public List<GcHeapHistoryEvent> getGcHeapHistoryEvents() {
        return gcHeapHistoryEvents;
    }

    public List<GcPreciousLog> getGcPreciousLogs() {
        return gcPreciousLogs;
    }

    /**
     * @return A <code>String</code> with the GC Precious Log warning, or null, if none.
     */
    public String getGcPreciousLogWarning() {
        String gcPreciousLogWarning = null;
        if (!gcPreciousLogs.isEmpty()) {
            StringBuilder warning = new StringBuilder();
            Iterator<GcPreciousLog> iterator = gcPreciousLogs.iterator();
            while (iterator.hasNext()) {
                GcPreciousLog gpl = iterator.next();
                if (gpl.getLogEntry().matches("^ (The system limit|least|limit|max).+")) {
                    if (warning.length() > 0) {
                        warning.append(" ");
                    }
                    warning.append(gpl.getLogEntry().trim());
                }
            }
            gcPreciousLogWarning = warning.toString();
        }
        return gcPreciousLogWarning;
    }

    /**
     * @param flag
     *            The flag to search for.
     * @return The <code>GlobalFlag</code> if it exists, null otherwise.
     */
    public GlobalFlag getGlobalFlag(String flag) {
        GlobalFlag globalFlag = null;
        if (!globalFlags.isEmpty()) {
            Iterator<GlobalFlag> iterator = globalFlags.iterator();
            while (iterator.hasNext()) {
                GlobalFlag event = iterator.next();
                if (!event.isHeader() && event.getFlag().matches(flag)) {
                    globalFlag = event;
                    break;
                }
            }
        }
        return globalFlag;
    }

    public List<GlobalFlag> getGlobalFlags() {
        return globalFlags;
    }

    public List<GlobalFlag> getGlobalFlagsExperimentalErgonomic() {
        List<GlobalFlag> globalFlagsExperimentalErgonomic = new ArrayList<GlobalFlag>();
        if (!globalFlags.isEmpty()) {
            Iterator<GlobalFlag> iterator = globalFlags.iterator();
            while (iterator.hasNext()) {
                GlobalFlag event = iterator.next();
                if (!event.isHeader() && event.getLogEntry().matches("^.+\\{experimental\\} \\{ergonomic\\}$")) {
                    globalFlagsExperimentalErgonomic.add(event);
                }
            }
        }
        return globalFlagsExperimentalErgonomic;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public HeapAddress getHeapAddress() {
        return heapAddress;
    }

    /**
     * @return The heap initial size in bytes, or Long.MIN_VALUE if undetermined.
     */
    private long getHeapInitialSize() {
        long heapInitialSize = Long.MIN_VALUE;
        // 1st check [Global flags]
        GlobalFlag globalFlagInitialHeapSize = getGlobalFlag("InitialHeapSize");
        if (globalFlagInitialHeapSize != null) {
            heapInitialSize = Long.parseLong(globalFlagInitialHeapSize.getValue());
        } else if (jvmOptions != null && jvmOptions.getInitialHeapSize() != null) {
            // Get from jvm_args
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getInitialHeapSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                } else {
                    fromUnits = 'B';
                }
                heapInitialSize = JdkUtil.convertSize(value, fromUnits, 'B');
            }
        } else if (heapAddress != null && !heapAddress.isErrorOccurredDuringErrorReporting()) {
            heapInitialSize = heapAddress.getSize();
        } else if (getMemoryTotal() > 0) {
            // Use JVM default = 1/64 system memory
            BigDecimal systemPhysicalMemory = new BigDecimal(getMemoryTotal());
            systemPhysicalMemory = systemPhysicalMemory.divide(new BigDecimal(64));
            systemPhysicalMemory = systemPhysicalMemory.setScale(0, RoundingMode.HALF_EVEN);
            heapInitialSize = systemPhysicalMemory.longValue();
        }
        return heapInitialSize;
    }

    /**
     * @return The heap max size in bytes, or Long.MIN_VALUE if undetermined.
     */
    private long getHeapMaxSize() {
        long heapMaxSize = Long.MIN_VALUE;
        // 1st check [Global flags]
        GlobalFlag globalFlagMaxHeapSize = getGlobalFlag("MaxHeapSize");
        if (globalFlagMaxHeapSize != null) {
            heapMaxSize = Long.parseLong(globalFlagMaxHeapSize.getValue());
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
                heapMaxSize = JdkUtil.convertSize(value, fromUnits, 'B');
            }
        } else if (heapAddress != null && !heapAddress.isErrorOccurredDuringErrorReporting()) {
            heapMaxSize = heapAddress.getSize();
        } else if (getMemoryTotal() > 0) {
            // Use JVM default = 1/4 system memory
            BigDecimal systemPhysicalMemory = new BigDecimal(getMemoryTotal());
            systemPhysicalMemory = systemPhysicalMemory.divide(new BigDecimal(4));
            systemPhysicalMemory = systemPhysicalMemory.setScale(0, RoundingMode.HALF_EVEN);
            heapMaxSize = systemPhysicalMemory.longValue();
        }
        return heapMaxSize;
    }

    public List<Heap> getHeaps() {
        return heaps;
    }

    /**
     * @return The heap starting address byte.
     */
    public long getHeapStartingAddress() {
        long heapStartingAddress = Long.MIN_VALUE;
        if (heapAddress != null) {
            heapStartingAddress = heapAddress.getStartingAddress();
        }
        return heapStartingAddress;
    }

    public Host getHost() {
        return host;
    }

    /**
     * The explicit huge page size.
     * 
     * @return The explicit huge page size in bytes.
     */
    public long getHugepagesize() {
        long hugepagesize = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regex = "Hugepagesize:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regex);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    hugepagesize = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return hugepagesize;
    }

    /**
     * The total number of explicit huge pages.
     * 
     * @return The total number of explicit huge pages.
     */
    public long getHugePagesTotal() {
        long hugePagesTotal = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regex = "HugePages_Total:[ ]{0,}(\\d{1,})";
            Pattern pattern = Pattern.compile(regex);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    hugePagesTotal = Long.parseLong(matcher.group(1));
                    break;
                }
            }
        }
        return hugePagesTotal;
    }

    /**
     * The explicit huge pages pool size in bytes. A convenience field in RHEL8 that is not available in earlier RHEL.
     * 
     * @return The explicit huge pages pool size in bytes.
     */
    public long getHugetlb() {
        long hugetlb = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regex = "Hugetlb:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regex);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    hugetlb = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return hugetlb;
    }

    public List<InternalExceptionEvent> getInternalExceptionEvents() {
        return internalExceptionEvents;
    }

    public List<InternalStatistic> getInternalStatistics() {
        return internalStatistics;
    }

    /**
     * @return Jar list (unique entries).
     */
    public List<String> getJars() {
        List<String> jars = new ArrayList<String>();
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.isJar()) {
                    if (!jars.contains(event.getFilePath())) {
                        jars.add(event.getFilePath());
                    }
                }
            }
        }
        return jars;
    }

    /**
     * @return The Java command used to start the JVM, or null if none exists.
     */
    public String getJavaCommand() {
        String javaCommand = null;
        if (!vmArguments.isEmpty()) {
            Iterator<VmArguments> iterator = vmArguments.iterator();
            while (iterator.hasNext()) {
                VmArguments event = iterator.next();
                if (event.isJavaCommand()) {
                    javaCommand = event.getValue();
                    break;
                }
            }
        }
        return javaCommand;
    }

    /**
     * @return <code>JavaSpecificiation</code>
     */
    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        if (vmInfo != null) {
            version = vmInfo.getJavaSpecification();
        }
        // Get from header
        if (version == JavaSpecification.UNKNOWN && !headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                String regEx = "\\((\\d{1,2})\\..+\\)";
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
                        case 21:
                            version = JavaSpecification.JDK21;
                            break;
                        default:
                            break;
                        }
                    }
                }
            }
        }
        // Check dynamic library (rpm)
        if (version == JavaSpecification.UNKNOWN && !dynamicLibraries.isEmpty()) {
            if (getRpmDirectory() != null) {
                String regEx = "^java-.+-openjdk-(1.8.0|11|17|21).+-.+$";
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(getRpmDirectory());
                if (matcher.find()) {
                    if (matcher.group(1).equals("1.8.0")) {
                        version = JavaSpecification.JDK8;
                    } else if (matcher.group(1).equals("11")) {
                        version = JavaSpecification.JDK11;
                    } else if (matcher.group(1).equals("17")) {
                        version = JavaSpecification.JDK17;
                    } else if (matcher.group(1).equals("21")) {
                        version = JavaSpecification.JDK21;
                    }
                }
            }
        }
        return version;
    }

    /**
     * @return The number of Java threads running when the JVM crashed.
     */
    public int getJavaThreadCount() {
        int javaThreadCount = 0;
        if (!threads.isEmpty()) {
            Iterator<Thread> iterator = threads.iterator();
            while (iterator.hasNext()) {
                Thread event = iterator.next();
                if (event.getLogEntry().matches("^Other Threads:$")) {
                    break;
                } else if (!(event.getLogEntry().matches(Thread.REGEX_HEADER)
                        || event.getLogEntry().matches(Thread.REGEX_FOOTER))) {
                    javaThreadCount++;
                }
            }
        }
        return javaThreadCount;
    }

    /**
     * @param regex
     *            The thread name regex.
     * @return The number of threads matching the pattern regex.
     */
    public int getJavaThreadCount(String regex) {
        int threadCount = 0;
        if (!threads.isEmpty()) {
            Iterator<Thread> iterator = threads.iterator();
            while (iterator.hasNext()) {
                Thread event = iterator.next();
                if (event.getLogEntry().matches("^.*" + regex + ".*$")) {
                    threadCount++;
                }
            }
        }
        return threadCount;
    }

    /**
     * @return <code>JavaVendor</code>
     */
    public JavaVendor getJavaVendor() {
        JavaVendor vendor = JavaVendor.UNIDENTIFIED;
        if (isRhBuildString() && isRhBuildOpenJdk()) {
            vendor = JavaVendor.RED_HAT;
        } else {
            if (vmInfo != null) {
                switch (vmInfo.getBuiltBy()) {
                case JAVA_RE:
                case MACH5ONE:
                    vendor = JavaVendor.ORACLE;
                    break;
                case JENKINS:
                    vendor = JavaVendor.ADOPTOPENJDK;
                    break;
                case VSTS:
                    vendor = JavaVendor.MICROSOFT;
                    break;
                case TEMURIN:
                    vendor = JavaVendor.ADOPTIUM;
                    break;
                case TESTER:
                case ZULU_RE:
                    vendor = JavaVendor.AZUL;
                    break;
                // Indeterminate
                case BUILD:
                case EMPTY:
                case MOCKBUILD:
                case UNKNOWN:
                default:
                    break;
                }
            }
        }
        if (vendor == JavaVendor.UNIDENTIFIED && !headers.isEmpty()) {
            // Check header
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isJreVersion()) {
                    if (he.getLogEntry().matches("^.+AdoptOpenJDK.+$")) {
                        vendor = JavaVendor.ADOPTOPENJDK;
                        break;
                    }
                } else if (he.isVendorBugUrl()) {
                    if (he.getLogEntry().matches("^#   https://github.com/adoptium/adoptium-support/issues$")) {
                        vendor = JavaVendor.ADOPTIUM;
                    } else if (he.getLogEntry().matches("^#   http://www.azulsystems.com/support/$")) {
                        vendor = JavaVendor.AZUL;
                    }
                    break;
                }
            }
        }
        if (vendor == JavaVendor.UNIDENTIFIED) {
            if (isRhVersion()) {
                // Vendors who rebuild RH source
                if (isOracleLinux()) {
                    vendor = JavaVendor.ORACLE;
                } else if (isCentOs()) {
                    vendor = JavaVendor.CENTOS;
                }
            }
        }
        return vendor;
    }

    /**
     * @return The Java major version, or Integer.MIN_VALUE if unknown.
     */
    public int getJavaVersionMajor() {
        return JdkUtil.getJavaSpecificationNumber(getJavaSpecification());
    }

    /**
     * @return The Java minor version number, or Integer.MIN_VALUE if unknown.
     */
    public int getJavaVersionMinor() {
        int javaVersionMinor = Integer.MIN_VALUE;
        switch (getJavaSpecification()) {
        case JDK8:
            javaVersionMinor = JdkUtil.getJdk8UpdateNumber(getJdkReleaseString());
            break;
        case JDK11:
            javaVersionMinor = JdkUtil.getJdk11UpdateNumber(getJdkReleaseString());
            break;
        case JDK17:
            javaVersionMinor = JdkUtil.getJdk17UpdateNumber(getJdkReleaseString());
            break;
        case JDK21:
            javaVersionMinor = JdkUtil.getJdk21UpdateNumber(getJdkReleaseString());
            break;
        case JDK6:
        case JDK7:
        case UNKNOWN:
        default:
            break;
        }
        return javaVersionMinor;
    }

    /**
     * @return The JDK build date/time in <code>VmInfo</code>.
     */
    public Date getJdkBuildDate() {
        Date date = null;
        if (vmInfo != null) {
            date = vmInfo.getBuildDate();
        }
        return date;
    }

    /**
     * @return JDK <code>BuiltBy</code> in <code>VmInfo</code>.
     */
    public BuiltBy getJdkBuiltBy() {
        BuiltBy builtBy = BuiltBy.UNKNOWN;
        if (vmInfo != null) {
            builtBy = vmInfo.getBuiltBy();
        }
        return builtBy;
    }

    /**
     * @return The JDK <code>Release</code> (or approximate based on release string) that produced the fatal error log,
     *         or null if unknown/undetermined.
     */
    public Release getJdkRelease() {
        Release release = null;
        HashMap<String, Release> releases = JdkUtil.getJdkReleases(this);
        if (releases != null && !releases.isEmpty()) {
            if (isRhRpmInstall()) {
                release = releases.get(getRpmDirectory());
            } else if (isRhLinuxZipInstall() || isRhWindowsZipInstall()) {
                release = releases.get(getJdkReleaseString());
            }
        } else {
            // Approximate release
            release = getFirstJdkRelease(getJdkReleaseString());
        }
        return release;
    }

    /**
     * The JDK release string. For example:
     * 
     * <pre>
     * 1.8.0_332-b09-1
     * 11.0.15+9-LTS-1
     * 17.0.3+6-LTS-2
     * </pre>
     * 
     * @return JDK release string, or UNKNOWN if it cannot be determined.
     */
    public String getJdkReleaseString() {
        String jdkReleaseString = null;
        if (vmInfo != null) {
            jdkReleaseString = vmInfo.getJdkReleaseString();
            // TODO: Better solution than this hack to account for 2 windows builds based on the same upstream tag?
            if (vmInfo.getOs() == Os.WINDOWS) {
                if (vmInfo.getJavaSpecification() == JavaSpecification.JDK8
                        && jdkReleaseString.equals("1.8.0_332-b09")) {
                    if (vmInfo.getBuildDate().equals(KrashUtil.getDate("Apr 19 2022 13:36:53"))) {
                        jdkReleaseString = "1.8.0_332-b09-1";
                    } else if (vmInfo.getBuildDate().equals(KrashUtil.getDate("Apr 27 2022 21:29:19"))) {
                        jdkReleaseString = "1.8.0_332-b09-2";
                    }
                } else if (vmInfo.getJavaSpecification() == JavaSpecification.JDK11
                        && jdkReleaseString.equals("11.0.15+9-LTS")) {
                    if (vmInfo.getBuildDate().equals(KrashUtil.getDate("Apr 17 2022 13:56:34"))) {
                        jdkReleaseString = "11.0.15+9-LTS-1";
                    } else if (vmInfo.getBuildDate().equals(KrashUtil.getDate("Apr 27 2022 19:12:18"))) {
                        jdkReleaseString = "11.0.15+9-LTS-2";
                    }
                } else if (vmInfo.getJavaSpecification() == JavaSpecification.JDK17
                        && jdkReleaseString.equals("17.0.3+6-LTS")) {
                    if (vmInfo.getBuildDate().equals(KrashUtil.getDate("Apr 17 2022 12:11:44"))) {
                        jdkReleaseString = "17.0.3+6-LTS-1";
                    } else if (vmInfo.getBuildDate().equals(KrashUtil.getDate("Apr 27 2022 11:51:42"))) {
                        jdkReleaseString = "17.0.3+6-LTS-2";
                    }
                }
            }
        }
        if (jdkReleaseString == null && !headers.isEmpty()) {
            // Check header
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isJreVersion()) {
                    String regEx = "^.+\\(" + JdkRegEx.VERSION_STRING + "\\)( \\(build " + JdkRegEx.BUILD_STRING
                            + "\\))?.*$";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(4) != null) {
                            jdkReleaseString = matcher.group(4);
                        } else if (matcher.group(1) != null) {
                            // Add leading "1."
                            if (matcher.group(1).matches("^[678].+$")) {
                                jdkReleaseString = "1." + matcher.group(1);
                            } else {
                                jdkReleaseString = matcher.group(1);
                            }
                        }
                    }
                    break;
                }
            }
        }
        if (jdkReleaseString == null && !dynamicLibraries.isEmpty()) {
            // Check dynamic libraries (rpm)
            if (getRpmDirectory() != null) {
                Iterator<Entry<String, Release>> iterator;
                if (getJavaSpecification() == JavaSpecification.JDK8) {
                    switch (getOsVersion()) {
                    case CENTOS6:
                    case RHEL6:
                        iterator = Jdk8.RHEL6_X86_64_RPMS.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Entry<String, Release> entry = iterator.next();
                            if (entry.getKey().equals(getRpmDirectory())) {
                                Release release = entry.getValue();
                                jdkReleaseString = release.getVersion();
                            }
                        }
                        break;
                    case CENTOS7:
                    case RHEL7:
                        if (getArchOs() == Arch.X86_64) {
                            iterator = Jdk8.RHEL7_X86_64_RPMS.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Entry<String, Release> entry = iterator.next();
                                if (entry.getKey().equals(getRpmDirectory())) {
                                    Release release = entry.getValue();
                                    jdkReleaseString = release.getVersion();
                                }
                            }
                        } else if (getArchOs() == Arch.PPC64) {
                            iterator = Jdk8.RHEL7_PPC64_RPMS.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Entry<String, Release> entry = iterator.next();
                                if (entry.getKey().equals(getRpmDirectory())) {
                                    Release release = entry.getValue();
                                    jdkReleaseString = release.getVersion();
                                }
                            }
                        } else if (getArchOs() == Arch.PPC64LE) {
                            iterator = Jdk8.RHEL7_PPC64LE_RPMS.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Entry<String, Release> entry = iterator.next();
                                if (entry.getKey().equals(getRpmDirectory())) {
                                    Release release = entry.getValue();
                                    jdkReleaseString = release.getVersion();
                                }
                            }
                        }
                        break;
                    case CENTOS8:
                    case RHEL8:
                        if (getArchOs() == Arch.X86_64) {
                            iterator = Jdk8.RHEL8_X86_64_RPMS.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Entry<String, Release> entry = iterator.next();
                                if (entry.getKey().equals(getRpmDirectory())) {
                                    Release release = entry.getValue();
                                    jdkReleaseString = release.getVersion();
                                }
                            }
                        } else if (getArchOs() == Arch.PPC64LE) {
                            iterator = Jdk8.RHEL8_PPC64LE_RPMS.entrySet().iterator();
                            while (iterator.hasNext()) {
                                Entry<String, Release> entry = iterator.next();
                                if (entry.getKey().equals(getRpmDirectory())) {
                                    Release release = entry.getValue();
                                    jdkReleaseString = release.getVersion();
                                }
                            }
                        }
                        break;
                    case CENTOS9:
                    case RHEL9:
                        iterator = Jdk8.RHEL9_X86_64_RPMS.entrySet().iterator();
                        while (iterator.hasNext()) {
                            Entry<String, Release> entry = iterator.next();
                            if (entry.getKey().equals(getRpmDirectory())) {
                                Release release = entry.getValue();
                                jdkReleaseString = release.getVersion();
                            }
                        }
                        break;
                    case UNIDENTIFIED:
                    default:
                        break;
                    }
                }
            }
        }
        if (jdkReleaseString == null) {
            return org.github.krashpad.util.Constants.PROPERTY_UNKNOWN;
        } else {
            return jdkReleaseString;
        }
    }

    /**
     * @return The JDK rpm RHEL version, or null if undetermined.
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
                    jdkRhelVersion = matcher.group(3);
                    if (matcher.group(5) != null) {
                        jdkRhelVersion = jdkRhelVersion + "." + matcher.group(5);
                    }
                } else if (matcher.group(8) != null) {
                    // JDK11
                    jdkRhelVersion = matcher.group(10);
                    if (matcher.group(12) != null) {
                        jdkRhelVersion = jdkRhelVersion + "." + matcher.group(12);
                    }
                } else if (matcher.group(14) != null) {
                    // JDK17
                    jdkRhelVersion = matcher.group(16);
                    if (matcher.group(18) != null) {
                        jdkRhelVersion = jdkRhelVersion + "." + matcher.group(18);
                    }
                } else if (matcher.group(19) != null) {
                    // JDK21
                    jdkRhelVersion = matcher.group(21);
                    if (matcher.group(23) != null) {
                        jdkRhelVersion = jdkRhelVersion + "." + matcher.group(23);
                    }
                }
            }
        }
        return jdkRhelVersion;
    }

    /**
     * @return The JVM options, or null if none exist.
     */
    public String getJvmArgs() {
        String jvmArgs = null;
        if (!vmArguments.isEmpty()) {
            Iterator<VmArguments> iterator = vmArguments.iterator();
            while (iterator.hasNext()) {
                VmArguments event = iterator.next();
                if (event.isJvmArgs()) {
                    jvmArgs = event.getValue();
                    break;
                }
            }
        } else if (commandLine != null) {
            if (commandLine.getJvmOptions() != null) {
                jvmArgs = commandLine.getJvmOptions();
            }
        }
        return jvmArgs;
    }

    /**
     * @return The JVM commit limit, in bytes.
     */
    public long getJvmCommitLimit() {
        long jvmCommitLimit = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (getOs() == Os.WINDOWS && event.getSwapTotal() > 0) {
                    jvmCommitLimit = event.getSwapTotal();
                    break;
                } else if (event.getPageFileTotal() > 0) {
                    jvmCommitLimit = event.getPageFileTotal();
                    break;
                }
            }
        }
        return jvmCommitLimit;
    }

    /**
     * @return The JVM commit limit used, in bytes.
     */
    public long getJvmCommitLimitUsed() {
        long swapTotal = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (event.getSwapTotal() >= 0) {
                    if (getOs() == Os.WINDOWS && getMemoryTotal() >= 0) {
                        swapTotal = event.getSwapTotal() - getMemoryTotal();
                    } else {
                        swapTotal = event.getSwapTotal();
                    }
                    break;
                } else if (event.getPageFileTotal() >= 0 && getMemoryTotal() >= 0) {
                    swapTotal = event.getPageFileTotal() - getMemoryTotal();
                    break;
                }
            }
        }
        return swapTotal;
    }

    /**
     * @return The code cache reserved in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryCodeCacheCommitted() {
        // TODO: Get the real committed value from <code>CodeCache</code>.
        return getJvmMemoryCodeCacheReserved();

    }

    /**
     * @return The code cache reserved in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryCodeCacheReserved() {
        long jvmMemoryCodeCacheReserved = Long.MIN_VALUE;
        // 1st check [Global flags]
        GlobalFlag globalFlagReservedCodeCacheSize = getGlobalFlag("ReservedCodeCacheSize");
        if (globalFlagReservedCodeCacheSize != null) {
            jvmMemoryCodeCacheReserved = Long.parseLong(globalFlagReservedCodeCacheSize.getValue());
        }
        // Next check JVM options
        if (jvmMemoryCodeCacheReserved < 0) {
            if (jvmOptions != null) {
                if (jvmOptions.getReservedCodeCacheSize() != null || jvmOptions.getMaxjitcodesize() != null) {
                    char fromUnits;
                    long value;
                    Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
                    Matcher matcher;
                    if (jvmOptions.getReservedCodeCacheSize() != null) {
                        matcher = pattern.matcher(jvmOptions.getReservedCodeCacheSize());
                    } else {
                        matcher = pattern.matcher(jvmOptions.getMaxjitcodesize());
                    }
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryCodeCacheReserved = JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                } else if (JdkUtil.isOptionEnabled(getJvmOptions().getSegmentedCodeCache())
                        && jvmOptions.getNonNMethodCodeHeapSize() != null
                        && jvmOptions.getNonProfiledCodeHeapSize() != null
                        && jvmOptions.getProfiledCodeHeapSize() != null) {
                    char fromUnits;
                    long value;
                    Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
                    Matcher matcher;
                    matcher = pattern.matcher(jvmOptions.getNonNMethodCodeHeapSize());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryCodeCacheReserved = JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                    matcher = pattern.matcher(jvmOptions.getNonProfiledCodeHeapSize());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryCodeCacheReserved += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                    matcher = pattern.matcher(jvmOptions.getProfiledCodeHeapSize());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryCodeCacheReserved += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                }
            }
        }
        // Use default 420m
        if (jvmMemoryCodeCacheReserved < 0) {
            jvmMemoryCodeCacheReserved = JdkUtil.convertSize(420, 'M', 'B');
        }
        return jvmMemoryCodeCacheReserved;
    }

    /**
     * @return The max direct memory committed in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryDirectMemoryCommitted() {
        // TODO: Can committed be determined?
        return getJvmMemoryDirectMemoryReserved();
    }

    /**
     * @return The max direct memory reserved in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryDirectMemoryReserved() {
        long jvmMemoryDirectMemoryReserved = Long.MIN_VALUE;
        // 1st check [Global flags]
        GlobalFlag globalFlagMaxDirectMemorySize = getGlobalFlag("MaxDirectMemorySize");
        if (globalFlagMaxDirectMemorySize != null) {
            jvmMemoryDirectMemoryReserved = Long.parseLong(globalFlagMaxDirectMemorySize.getValue());
        } else if (jvmOptions != null && jvmOptions.getMaxDirectMemorySize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile(JdkRegEx.OPTION_SIZE_BYTES);
            Matcher matcher = pattern.matcher(jvmOptions.getMaxDirectMemorySize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(2));
                if (matcher.group(3) != null) {
                    fromUnits = matcher.group(3).charAt(0);
                } else {
                    fromUnits = 'B';
                }
                jvmMemoryDirectMemoryReserved = JdkUtil.convertSize(value, fromUnits, 'B');
            }
        }
        return jvmMemoryDirectMemoryReserved;
    }

    /**
     * @return The total heap committed in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getJvmMemoryHeapCommitted() {
        long jvmMemoryHeapCommitted = Long.MIN_VALUE;
        if (!heaps.isEmpty()) {
            jvmMemoryHeapCommitted = 0;
            Iterator<Heap> iterator = heaps.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                Heap event = iterator.next();
                if (event.isYoungGen()) {
                    pattern = Pattern.compile(JdkRegEx.YOUNG_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(3));
                        if (matcher.group(5) != null) {
                            fromUnits = matcher.group(5).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryHeapCommitted += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                } else if (event.isOldGen()) {
                    pattern = Pattern.compile(JdkRegEx.OLD_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(2));
                        if (matcher.group(4) != null) {
                            fromUnits = matcher.group(4).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryHeapCommitted += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                } else if (event.isShenandoah()) {
                    pattern = Pattern.compile(JdkRegEx.SHENANDOAH_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(9));
                        if (matcher.group(11) != null) {
                            fromUnits = matcher.group(11).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryHeapCommitted += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                } else if (event.isG1()) {
                    pattern = Pattern.compile(JdkRegEx.G1_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryHeapCommitted += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                } else if (event.isZ()) {
                    pattern = Pattern.compile(JdkRegEx.Z_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(4));
                        if (matcher.group(6) != null) {
                            fromUnits = matcher.group(6).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryHeapCommitted += JdkUtil.convertSize(value, fromUnits, 'B');
                    }
                }
            }
        }
        if (jvmMemoryHeapCommitted < 0) {
            jvmMemoryHeapCommitted = getHeapInitialSize();
        }
        if (jvmMemoryHeapCommitted < 0) {
            jvmMemoryHeapCommitted = getJvmMemoryHeapUsed();
        }
        return jvmMemoryHeapCommitted;
    }

    /**
     * @return The heap reserved in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getJvmMemoryHeapReserved() {
        long jvmMemoryHeapReserved = getHeapMaxSize();
        if (jvmMemoryHeapReserved < 0) {
            // Use committed
            jvmMemoryHeapReserved = getJvmMemoryHeapCommitted();
        }
        return jvmMemoryHeapReserved;
    }

    /**
     * @return The total heap used in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getJvmMemoryHeapUsed() {
        long jvmMemoryHeapUsed = Long.MIN_VALUE;
        if (!heaps.isEmpty()) {
            Iterator<Heap> iterator = heaps.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                Heap event = iterator.next();
                if (event.isYoungGen()) {
                    pattern = Pattern.compile(JdkRegEx.YOUNG_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(6));
                        if (matcher.group(8) != null) {
                            fromUnits = matcher.group(8).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        if (jvmMemoryHeapUsed == Long.MIN_VALUE) {
                            jvmMemoryHeapUsed = JdkUtil.convertSize(value, fromUnits, 'B');
                        } else {
                            jvmMemoryHeapUsed += JdkUtil.convertSize(value, fromUnits, 'B');
                        }
                    }
                } else if (event.isOldGen()) {
                    pattern = Pattern.compile(JdkRegEx.OLD_GEN_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(5));
                        if (matcher.group(7) != null) {
                            fromUnits = matcher.group(7).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        if (jvmMemoryHeapUsed == Long.MIN_VALUE) {
                            jvmMemoryHeapUsed = JdkUtil.convertSize(value, fromUnits, 'B');
                        } else {
                            jvmMemoryHeapUsed += JdkUtil.convertSize(value, fromUnits, 'B');
                        }
                    }
                } else if (event.isShenandoah()) {
                    pattern = Pattern.compile(JdkRegEx.SHENANDOAH_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(12));
                        if (matcher.group(14) != null) {
                            fromUnits = matcher.group(14).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        if (jvmMemoryHeapUsed == Long.MIN_VALUE) {
                            jvmMemoryHeapUsed = JdkUtil.convertSize(value, fromUnits, 'B');
                        } else {
                            jvmMemoryHeapUsed += JdkUtil.convertSize(value, fromUnits, 'B');
                        }
                    }
                } else if (event.isG1()) {
                    pattern = Pattern.compile(JdkRegEx.G1);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(5));
                        if (matcher.group(7) != null) {
                            fromUnits = matcher.group(7).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        if (jvmMemoryHeapUsed == Long.MIN_VALUE) {
                            jvmMemoryHeapUsed = JdkUtil.convertSize(value, fromUnits, 'B');
                        } else {
                            jvmMemoryHeapUsed += JdkUtil.convertSize(value, fromUnits, 'B');
                        }
                    }
                } else if (event.isZ()) {
                    pattern = Pattern.compile(JdkRegEx.Z);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        if (jvmMemoryHeapUsed == Long.MIN_VALUE) {
                            jvmMemoryHeapUsed = JdkUtil.convertSize(value, fromUnits, 'B');
                        } else {
                            jvmMemoryHeapUsed += JdkUtil.convertSize(value, fromUnits, 'B');
                        }
                    }
                }
            }
        }
        return jvmMemoryHeapUsed;
    }

    /**
     * @return The total metaspace committed (allocated) in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getJvmMemoryMetaspaceCommitted() {
        long jvmMemoryMetaspaceCommitted = Long.MIN_VALUE;
        if (!heaps.isEmpty()) {
            Iterator<Heap> iterator = heaps.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                Heap event = iterator.next();
                if (event.isMetaspace()) {
                    pattern = Pattern.compile(JdkRegEx.METASPACE_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(8));
                        if (matcher.group(10) != null) {
                            fromUnits = matcher.group(10).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryMetaspaceCommitted = JdkUtil.convertSize(value, fromUnits, 'B');
                        break;
                    }
                }
            }
        }
        return jvmMemoryMetaspaceCommitted;
    }

    /**
     * @return The metaspace max size reserved in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    /**
     * @return The Metaspace maximum size.
     */
    public long getJvmMemoryMetaspaceReserved() {
        long jvmMemoryMetaspaceReserved = Long.MIN_VALUE;
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
                jvmMemoryMetaspaceReserved = JdkUtil.convertSize(value, fromUnits, 'B');
            }
        }
        // If max metaspace size not set (recommended), get from <code>HeapEvent</code> "reserved".
        if (jvmMemoryMetaspaceReserved == Long.MIN_VALUE) {
            if (!heaps.isEmpty()) {
                Iterator<Heap> iterator = heaps.iterator();
                char fromUnits;
                long value;
                Pattern pattern = null;
                Matcher matcher = null;
                while (iterator.hasNext()) {
                    Heap event = iterator.next();
                    if (event.isMetaspace()) {
                        pattern = Pattern.compile(JdkRegEx.METASPACE_SIZE);
                        matcher = pattern.matcher(event.getLogEntry());
                        if (matcher.find()) {
                            int committedBlock = 11;
                            int committedIndex = 13;
                            value = Long.parseLong(matcher.group(committedBlock));
                            if (matcher.group(committedIndex) != null) {
                                fromUnits = matcher.group(committedIndex).charAt(0);
                            } else {
                                fromUnits = 'B';
                            }
                            jvmMemoryMetaspaceReserved = JdkUtil.convertSize(value, fromUnits, 'B');
                            break;
                        }
                    }
                }
            }
        }
        return jvmMemoryMetaspaceReserved;
    }

    /**
     * @return The total metaspace used in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getJvmMemoryMetaspaceUsed() {
        long jvmMemoryMetaspaceUsed = Long.MIN_VALUE;
        if (!heaps.isEmpty()) {
            Iterator<Heap> iterator = heaps.iterator();
            char fromUnits;
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                Heap event = iterator.next();
                if (event.isMetaspace()) {
                    pattern = Pattern.compile(JdkRegEx.METASPACE_SIZE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        if (matcher.group(3) != null) {
                            fromUnits = matcher.group(3).charAt(0);
                        } else {
                            fromUnits = 'B';
                        }
                        jvmMemoryMetaspaceUsed = JdkUtil.convertSize(value, fromUnits, 'B');
                        break;
                    }
                }
            }
        }
        return jvmMemoryMetaspaceUsed;
    }

    /**
     * @return The JVM process RSS, or Long.MIN_VALUE if it cannot be determined.
     */
    private long getJvmMemoryRss() {
        long jvmMemoryRss = Long.MIN_VALUE;
        if (!processMemories.isEmpty()) {
            Iterator<ProcessMemory> iterator = processMemories.iterator();
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                ProcessMemory event = iterator.next();
                if (event.isRss()) {
                    pattern = Pattern.compile("^Resident Set Size: (\\d{1,})K .+$");
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        jvmMemoryRss = JdkUtil.convertSize(value, 'K', 'B');
                        break;
                    }
                }
            }
        }
        return jvmMemoryRss;
    }

    /**
     * @return The JVM process memory swapped out in bytes, or Long.MIN_VALUE if it cannot be determined.
     */
    public long getJvmMemorySwappedOut() {
        long jvmMemorySwappedOut = Long.MIN_VALUE;
        if (!processMemories.isEmpty()) {
            Iterator<ProcessMemory> iterator = processMemories.iterator();
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                ProcessMemory event = iterator.next();
                if (event.isSwapped()) {
                    pattern = Pattern.compile("^Swapped out: (\\d{1,})K$");
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        jvmMemorySwappedOut = JdkUtil.convertSize(value, 'K', 'B');
                        break;
                    }
                }
            }
        }
        return jvmMemorySwappedOut;
    }

    /**
     * @return The thread memory committed in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryThreadStackCommitted() {
        // TODO: Is there a way to get committed?
        return getJvmMemoryThreadStackReserved();
    }

    /**
     * @return The thread memory reserved in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryThreadStackReserved() {
        long jvmMemoryThreadStackReserved = Long.MIN_VALUE;
        if (getJavaThreadCount() > 0) {
            BigDecimal memoryPerThread = new BigDecimal(getThreadStackSize());
            BigDecimal threads = new BigDecimal(getJavaThreadCount());
            jvmMemoryThreadStackReserved = memoryPerThread.multiply(threads).longValue();
            jvmMemoryThreadStackReserved = JdkUtil.convertSize(jvmMemoryThreadStackReserved, 'K', 'B');
        }
        return jvmMemoryThreadStackReserved;
    }

    /**
     * @return Estimated JVM memory committed in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryTotalCommitted() {
        long jvmMemoryTotalCommitted = Long.MIN_VALUE;
        if (getJvmMemoryHeapCommitted() > 0) {
            jvmMemoryTotalCommitted = getJvmMemoryHeapCommitted();
        }
        if (getJvmMemoryMetaspaceCommitted() > 0) {
            if (jvmMemoryTotalCommitted > 0) {
                jvmMemoryTotalCommitted += getJvmMemoryMetaspaceCommitted();
            } else {
                jvmMemoryTotalCommitted = getJvmMemoryMetaspaceCommitted();
            }
        }
        // Thread stack space
        if (getJvmMemoryThreadStackCommitted() > 0) {
            if (jvmMemoryTotalCommitted > 0) {
                jvmMemoryTotalCommitted += getJvmMemoryThreadStackCommitted();
            } else {
                jvmMemoryTotalCommitted = getJvmMemoryThreadStackCommitted();
            }
        }
        // code cache
        if (getJvmMemoryCodeCacheCommitted() > 0) {
            if (jvmMemoryTotalCommitted > 0) {
                jvmMemoryTotalCommitted += getJvmMemoryCodeCacheCommitted();
            } else {
                jvmMemoryTotalCommitted = getJvmMemoryCodeCacheCommitted();
            }
        }
        // Direct memory
        if (getJvmMemoryDirectMemoryCommitted() > 0) {
            if (jvmMemoryTotalCommitted > 0) {
                jvmMemoryTotalCommitted += getJvmMemoryDirectMemoryCommitted();
            } else {
                jvmMemoryTotalCommitted = getJvmMemoryDirectMemoryCommitted();
            }
        }
        return jvmMemoryTotalCommitted;
    }

    /**
     * @return Estimated JVM memory reserved in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryTotalReserved() {
        long jvmMemoryTotalReserved = Long.MIN_VALUE;
        if (getJvmMemoryVirtual() > 0) {
            jvmMemoryTotalReserved = getJvmMemoryVirtual();
        }
        if (jvmMemoryTotalReserved < 0) {
            if (getJvmMemoryHeapReserved() > 0) {
                jvmMemoryTotalReserved = getJvmMemoryHeapReserved();
            }
            if (getJvmMemoryMetaspaceReserved() > 0) {
                if (jvmMemoryTotalReserved > 0) {
                    jvmMemoryTotalReserved += getJvmMemoryMetaspaceReserved();
                } else {
                    jvmMemoryTotalReserved = getJvmMemoryMetaspaceReserved();
                }
            }
            // Thread stack space
            if (getJvmMemoryThreadStackReserved() > 0) {
                if (jvmMemoryTotalReserved > 0) {
                    jvmMemoryTotalReserved += getJvmMemoryThreadStackReserved();
                } else {
                    jvmMemoryTotalReserved = getJvmMemoryThreadStackReserved();
                }
            }
            // code cache
            if (getJvmMemoryCodeCacheReserved() > 0) {
                if (jvmMemoryTotalReserved > 0) {
                    jvmMemoryTotalReserved += getJvmMemoryCodeCacheReserved();
                } else {
                    jvmMemoryTotalReserved = getJvmMemoryCodeCacheReserved();
                }
            }
            // Direct memory
            if (getJvmMemoryDirectMemoryReserved() > 0) {
                if (jvmMemoryTotalReserved > 0) {
                    jvmMemoryTotalReserved += getJvmMemoryDirectMemoryReserved();
                } else {
                    jvmMemoryTotalReserved = getJvmMemoryDirectMemoryReserved();
                }
            }
        }
        return jvmMemoryTotalReserved;
    }

    /**
     * @return Estimated JVM memory used in bytes, or Long.MIN_VALUE if undetermined.
     */
    public long getJvmMemoryTotalUsed() {
        long jvmMemoryTotalUsed = Long.MIN_VALUE;
        jvmMemoryTotalUsed = Math.max(getJvmMemoryRss(), getOsCommitCharge());
        return jvmMemoryTotalUsed;
    }

    /**
     * @return The JVM process virtual memory, or Long.MIN_VALUE if it cannot be determined.
     */
    private long getJvmMemoryVirtual() {
        long jvmMemoryVirtual = Long.MIN_VALUE;
        if (!processMemories.isEmpty()) {
            Iterator<ProcessMemory> iterator = processMemories.iterator();
            long value;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                ProcessMemory event = iterator.next();
                if (event.isVirtual()) {
                    pattern = Pattern.compile("^Virtual Size: (\\d{1,})K .+$");
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        value = Long.parseLong(matcher.group(1));
                        jvmMemoryVirtual = JdkUtil.convertSize(value, 'K', 'B');
                        break;
                    }
                }
            }
        }
        return jvmMemoryVirtual;
    }

    public JvmOptions getJvmOptions() {
        return jvmOptions;
    }

    public String getJvmUser() {
        return jvmUser;
    }

    public List<LdPreloadFile> getLdPreloadFiles() {
        return ldPreloadFiles;
    }

    /**
     * @return The VM state for display purposes.
     */
    public String getLiteral() {
        String vmStateState = null;
        if (vmState != null) {
            vmStateState = vmState.getState();
        }
        return vmStateState;
    }

    /**
     * @return max_map_count, or a negative value if undefined.
     */
    public Long getMaxMapCountLimit() {
        Long maxMapCountLimit = Long.MIN_VALUE;
        if (!maxMapCounts.isEmpty()) {
            Iterator<MaxMapCount> iterator = maxMapCounts.iterator();
            while (iterator.hasNext()) {
                MaxMapCount event = iterator.next();
                if (!event.isHeader()) {
                    maxMapCountLimit = event.getLimit();
                    break;
                }
            }
        }
        return maxMapCountLimit;
    }

    public List<MaxMapCount> getMaxMapCounts() {
        return maxMapCounts;
    }

    /**
     * Memory ballooned now. For example:
     * 
     * vSphere resource information available now:
     * 
     * guest.mem.ballooned = 2048
     * 
     * @return The total amount of ballooned memory in bytes.
     */
    public long getMemBalloonedNow() {
        long memBallooned = Long.MIN_VALUE;
        if (!virtualizationInfos.isEmpty()) {
            String regexGuestMemBallooned = "guest.mem.ballooned = (\\d{1,})";
            Pattern pattern = Pattern.compile(regexGuestMemBallooned);
            Iterator<VirtualizationInfo> iterator = virtualizationInfos.iterator();
            // Get information now, not at startup
            boolean now = false;
            while (iterator.hasNext()) {
                VirtualizationInfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (now && matcher.find()) {
                    memBallooned = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                } else if (event.getLogEntry().equals("vSphere resource information available now:")) {
                    now = true;
                }
            }
        }
        return memBallooned;
    }

    public List<Meminfo> getMeminfos() {
        return meminfos;
    }

    public List<Memory> getMemories() {
        return memories;
    }

    /**
     * @return Estimated memory available for allocations without causing swapping in bytes, or Long.MIN_VALUE if
     *         undetermined.
     */
    public long getMemoryFree() {
        long memoryFree = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (event.isHeader()) {
                    Pattern pattern = Pattern.compile(Memory._REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find() && matcher.group(7) != null && matcher.group(9) != null) {
                        memoryFree = JdkUtil.convertSize(Long.parseLong(matcher.group(7)), matcher.group(9).charAt(0),
                                'B');
                    }
                    break;
                }
            }
        }
        if (memoryFree < 0) {
            memoryFree = getOsMemoryFree();
        }
        return memoryFree;
    }

    /**
     * @return The total available physical memory in bytes.
     */
    public long getMemoryTotal() {
        long memoryTotal = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (event.isHeader()) {
                    Pattern pattern = Pattern.compile(Memory._REGEX_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find() && matcher.group(4) != null && matcher.group(6) != null) {
                        memoryTotal = JdkUtil.convertSize(Long.parseLong(matcher.group(4)), matcher.group(6).charAt(0),
                                'B');
                    }
                    break;
                }
            }
        }
        if (memoryTotal < 0) {
            memoryTotal = getOsMemoryTotal();
        }
        return memoryTotal;
    }

    /**
     * @return The number of mmap resources in a deleted state.
     */
    public int getMmapDeletedCount() {
        int mmapDeletedCount = 0;
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                int position = event.getLogEntry().lastIndexOf(" (deleted)");
                if (position != -1) {
                    mmapDeletedCount++;
                }
            }
        }
        return mmapDeletedCount;
    }

    public NarrowKlass getNarrowKlass() {
        return narrowKlass;
    }

    public List<String> getNativeLibraries() {
        return nativeLibraries;
    }

    /**
     * @return Google native libraries.
     */
    public List<String> getNativeLibrariesGoogle() {
        List<String> googleNativeLibraries = new ArrayList<String>();
        if (!nativeLibraries.isEmpty()) {
            Iterator<String> iterator = nativeLibraries.iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && KrashUtil.NATIVE_LIBRARIES_GOOGLE.contains(nativeLibrary)) {
                    googleNativeLibraries.add(nativeLibraryPath);
                }
            }
        }
        return googleNativeLibraries;
    }

    /**
     * @return JBoss native libraries.
     */
    public List<String> getNativeLibrariesJBoss() {
        List<String> jbossNativeLibraries = new ArrayList<String>();
        if (!nativeLibraries.isEmpty()) {
            Iterator<String> iterator = nativeLibraries.iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && KrashUtil.NATIVE_LIBRARIES_JBOSS.contains(nativeLibrary)) {
                    jbossNativeLibraries.add(nativeLibraryPath);
                }
            }
        }
        return jbossNativeLibraries;
    }

    /**
     * @return Tomcat native libraries.
     */
    public List<String> getNativeLibrariesTomcat() {
        List<String> tomcatNativeLibraries = new ArrayList<String>();
        if (!nativeLibraries.isEmpty()) {
            Iterator<String> iterator = nativeLibraries.iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && KrashUtil.NATIVE_LIBRARIES_TOMCAT.contains(nativeLibrary)) {
                    tomcatNativeLibraries.add(nativeLibraryPath);
                }
            }
        }
        return tomcatNativeLibraries;
    }

    public List<String> getNativeLibrariesUnknown() {
        return nativeLibrariesUnknown;
    }

    /**
     * @return VMware native libraries.
     */
    public List<String> getNativeLibrariesVmWare() {
        List<String> vmWareNativeLibraries = new ArrayList<String>();
        if (!nativeLibraries.isEmpty()) {
            Iterator<String> iterator = nativeLibraries.iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (nativeLibrary != null && KrashUtil.NATIVE_LIBRARIES_VMWARE.contains(nativeLibrary)) {
                    vmWareNativeLibraries.add(nativeLibraryPath);
                }
            }
        }
        return vmWareNativeLibraries;
    }

    /**
     * @return The native library the crash is happening in, or null if the crash does not happen in a native library.
     */
    public String getNativeLibraryInCrash() {
        String nativeLibraryInCrash = null;
        if (getStackFrameTop() != null) {
            Pattern pattern = Pattern.compile("^C[ ]{1,2}\\[(.+\\.(so|dll)).*\\].*$");
            Matcher matcher = pattern.matcher(getStackFrameTop());
            if (matcher.find()) {
                nativeLibraryInCrash = matcher.group(1);
            }
        }
        return nativeLibraryInCrash;
    }

    public List<NativeMemoryTracking> getNativeMemoryTrackings() {
        return nativeMemoryTrackings;
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
        Iterator<Stack> iterator = stacks.iterator();
        while (iterator.hasNext()) {
            Stack event = iterator.next();
            if (event.getLogEntry().matches("^" + stackFrame + "$") && stackIndex < stacks.size()) {
                nextStackFrame = stacks.get(stackIndex + 1).getLogEntry();
                break;
            }
            stackIndex++;
        }
        return nextStackFrame;
    }

    /**
     * @return <code>Os</code>
     */
    public Os getOs() {
        Os os = Os.UNIDENTIFIED;
        String osString = getOsString();
        if (osString != null) {
            if (osString.matches(".*Linux.*")) {
                os = Os.LINUX;
            } else if (osString.matches("^Windows.+$")) {
                os = Os.WINDOWS;
            } else if (osString.matches(".+Solaris.+")) {
                os = Os.SOLARIS;
            }
        } else if (!headers.isEmpty()) {
            // Check header
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isJavaVm()) {
                    os = he.getOsType();
                    break;
                }
            }
        } else if (vmInfo != null) {
            os = vmInfo.getOs();
        }
        return os;
    }

    /**
     * On Windows, the memory the process has asked for that cannot be shared with other processes. A good approximation
     * of the process size.
     * 
     * Can be physical memory, memory paged to disk, or memory in the standby page list (no longer in use, but not yet
     * paged to disk).
     * 
     * Excludes memory mapped files (shared DLLs), but does not necessarily exclude the memory allocated by those files.
     * 
     * @return the memory the process has asked for that cannot be shared with other processes, in bytes.
     */
    private long getOsCommitCharge() {
        long commitCharge = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            String regexCommitCharge = "current process commit charge \\(\"private bytes\"\\): (\\d{1,})M, peak: "
                    + "\\d{1,}M";
            Pattern pattern = Pattern.compile(regexCommitCharge);
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    commitCharge = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'M', 'B');
                    break;
                }
            }
        }
        return commitCharge;
    }

    /**
     * The total amount of memory that can be allocated by the system, based on the overcommit ratio (vm.
     * overcommit_ratio).
     * 
     * Formula: ([total RAM pages] - [total huge TLB pages]) * overcommit_ratio / 100 + [total swap pages]
     * 
     * This limit is only adhered to if strict overcommit accounting is enabled (mode 2 in vm.overcommit_memory), and
     * then some memory is reserved: (1) user_reserve_kbytes defaults to min(3% of the current process size, 128MB).
     * This is to prevent a single process from consuming all memory and tipping over the box. (2) admin_reserve_kbytes
     * defaults to min(3% of free pages, 8MB) for users with the capability cap_sys_admin. It should be large enough to
     * handle the full Virtual Memory Size of programs used to recover.
     * 
     * @return The total amount of memory currently available to be allocated by the system, in bytes.
     */
    public long getOsCommitLimit() {
        long osCommitLimit = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regexCommitLimit = "CommitLimit:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexCommitLimit);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    osCommitLimit = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        } else {
            // get from JVM
            getJvmCommitLimit();
        }
        return osCommitLimit;
    }

    /**
     * The amount of userspace virtual memory currently allocated on the system.
     * 
     * The committed memory is a sum of all of the memory which has been allocated by processes, even if it has not been
     * "used" by them yet.
     * 
     * On Linux, when vm.overcommit_memory=2, the cumulative VSS of all userspace processes is limited to
     * overcomit_ratio percent of ram + swap.
     * 
     * RHEL5: allocatable memory=(swap size + (RAM size * overcommit ratio / 100))
     * 
     * RHEL6/7/8: allocatable memory=(swap size + ((RAM size - huge tlb size) * overcommit ratio / 100))
     * 
     * @return The amount of userspace virtual memory currently allocated on the system, in bytes.
     */
    public long getOsCommitLimitUsed() {
        long osCommitLimitUsed = Long.MIN_VALUE;
        if (Os.LINUX == getOs() && !meminfos.isEmpty()) {
            String regexCommittedAs = "Committed_AS:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexCommittedAs);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    osCommitLimitUsed = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        } else {
            // get from JVM
            getJvmCommitLimitUsed();
        }
        return osCommitLimitUsed;
    }

    public List<OsInfo> getOsInfos() {
        return osInfos;
    }

    /**
     * @return The free OS physical memory in bytes.
     */
    public long getOsMemoryFree() {
        long osMemoryFree = getRhelMemAvailable();
        if (osMemoryFree < 0) {
            osMemoryFree = getRhelMemFree();
        }
        return osMemoryFree;
    }

    /**
     * @return The total available OS physical memory in bytes.
     */
    public long getOsMemoryTotal() {
        long osMemoryTotal = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regexMemTotal = "MemTotal:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    osMemoryTotal = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return osMemoryTotal;
    }

    /**
     * The OS string. For example:
     * 
     * Red Hat Enterprise Linux Server release 7.7 (Maipo)
     * 
     * @return OS string, of null if it doesn't exist.
     */
    public String getOsString() {
        String osString = null;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    Matcher matcher = OsInfo.PATTERN.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        if (matcher.group(2) != null) {
                            osString = matcher.group(4).trim();
                        } else if (iterator.hasNext()) {
                            // OS is on separate line
                            event = iterator.next();
                            osString = event.getLogEntry().trim();
                        }
                    }
                    break;
                }
            }
        } else if (host != null && host.getOsString() != null) {
            osString = host.getOsString();
        }
        return osString;
    }

    /**
     * @return The total free swap in bytes.
     */
    public long getOsSwapFree() {
        long osSwapFree = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regexMemTotal = "SwapFree:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    osSwapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return osSwapFree;
    }

    /**
     * @return The total available OS swap in bytes.
     */
    public long getOsSwapTotal() {
        long osSwapTotal = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regexMemTotal = "SwapTotal:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    osSwapTotal = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return osSwapTotal;
    }

    /**
     * @return <code>OsVendor</code>
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNIDENTIFIED;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    }
                    if (event.getLogEntry().matches("^.*Red Hat.+$")) {
                        osVendor = OsVendor.REDHAT;
                    } else if (event.getLogEntry().matches(".*Windows.+$")) {
                        osVendor = OsVendor.MICROSOFT;
                    } else if (event.getLogEntry().matches("^.+Oracle.+$")) {
                        osVendor = OsVendor.ORACLE;
                    } else if (event.getLogEntry().matches("^.*CentOS.+$")) {
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
        OsVersion osVersion = OsVersion.UNIDENTIFIED;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$") && iterator.hasNext()) {
                        // OS on next line
                        event = iterator.next();
                    }
                    if (event.getLogEntry().matches("^.*Red Hat Enterprise Linux (Server|Workstation) release 6.+$")) {
                        osVersion = OsVersion.RHEL6;
                    } else if (event.getLogEntry()
                            .matches("^.*Red Hat Enterprise Linux (Server|Workstation) release 7.+$")) {
                        osVersion = OsVersion.RHEL7;
                    } else if (event.getLogEntry().matches("^.*Red Hat Enterprise Linux release 8.+$")) {
                        osVersion = OsVersion.RHEL8;
                    } else if (event.getLogEntry().matches("^.*Red Hat Enterprise Linux release 9.+$")) {
                        osVersion = OsVersion.RHEL9;
                    } else if (event.getLogEntry().matches("^.*Windows 10.+$")) {
                        osVersion = OsVersion.WINDOWS10;
                    } else if (event.getLogEntry().matches("^.*CentOS Linux release 6.+$")) {
                        osVersion = OsVersion.CENTOS6;
                    } else if (event.getLogEntry().matches("^.*CentOS Linux release 7.+$")) {
                        osVersion = OsVersion.CENTOS7;
                    } else if (event.getLogEntry().matches("^.*CentOS Linux release 8.+$")) {
                        osVersion = OsVersion.CENTOS8;
                    }
                    break;
                }
            }
        }
        if (osVersion == OsVersion.UNIDENTIFIED && uname != null) {
            osVersion = uname.getOsVersion();
        }
        return osVersion;
    }

    public PeriodicNativeTrim getPeriodicNativeTrim() {
        return periodicNativeTrim;
    }

    public List<PidMax> getPidMaxes() {
        return pidMaxes;
    }

    /**
     * @return pid_max, or a negative value if undefined.
     */
    public Long getPidMaxLimit() {
        Long pidMaxLimit = Long.MIN_VALUE;
        if (!pidMaxes.isEmpty()) {
            Iterator<PidMax> iterator = pidMaxes.iterator();
            while (iterator.hasNext()) {
                PidMax event = iterator.next();
                if (!event.isHeader()) {
                    pidMaxLimit = event.getLimit();
                    break;
                }
            }
        }
        return pidMaxLimit;
    }

    public List<ProcessMemory> getProcessMemories() {
        return processMemories;
    }

    public List<RegisterToMemoryMapping> getRegisterToMemoryMappings() {
        return registerToMemoryMappings;
    }

    /**
     * MemAvailable is an estimate of how much physical memory is available without swapping. It does not include the
     * memory used by the JVM process, as it is before the JVM process exits and its memory is freed.
     * 
     * @return The total available physical memory as reported by RHEL in bytes, or Long.MIN_VALUE if undetermined.
     */
    private long getRhelMemAvailable() {
        long rhelMemAvailable = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regexMemTotal = "MemAvailable:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    rhelMemAvailable = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return rhelMemAvailable;
    }

    /**
     * MemFree does not include Buffers or Cached memory, which can be reclaimed at any time. Therefore, low free memory
     * does not necessarily indicate swapping or out of memory is imminent.
     * 
     * @return The total free physical memory in bytes.
     */
    private long getRhelMemFree() {
        long rhelMemFree = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regexMemTotal = "MemFree:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regexMemTotal);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    rhelMemFree = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return rhelMemFree;
    }

    /**
     * @return The RHEL version, or null if unknown.
     */
    public String getRhelVersion() {
        String rhelVersion = null;
        String osString = getOsString();
        if (osString != null) {
            String regex = "^.*Red Hat Enterprise Linux (Server )?release (\\d\\.\\d{1,2}).*$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(osString);
            if (matcher.find()) {
                rhelVersion = matcher.group(2);
            }
        }
        return rhelVersion;
    }

    public Rlimit getRlimit() {
        return rlimit;
    }

    public String getRpmDirectory() {
        return rpmDirectory;
    }

    /**
     * The total shmem huge pages available in bytes.
     * 
     * @return The total shmem huge pages available in bytes.
     */
    public long getShmemHugePages() {
        long shmemHugePages = Long.MIN_VALUE;
        if (!meminfos.isEmpty()) {
            String regex = "ShmemHugePages:[ ]{0,}(\\d{1,}) kB";
            Pattern pattern = Pattern.compile(regex);
            Iterator<Meminfo> iterator = meminfos.iterator();
            while (iterator.hasNext()) {
                Meminfo event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    shmemHugePages = JdkUtil.convertSize(Long.parseLong(matcher.group(1)), 'K', 'B');
                    break;
                }
            }
        }
        return shmemHugePages;
    }

    public SigInfo getSigInfo() {
        return sigInfo;
    }

    /**
     * @return Signal code.
     */
    public SignalCode getSignalCode() {
        SignalCode signalCode = SignalCode.UNKNOWN;
        if (sigInfo != null) {
            signalCode = sigInfo.getSignalCode();
        }
        return signalCode;
    }

    /**
     * @return Signal number.
     */
    public SignalNumber getSignalNumber() {
        SignalNumber signalNumber = SignalNumber.UNKNOWN;
        if (sigInfo != null) {
            signalNumber = sigInfo.getSignalNumber();
        }
        if (signalNumber == SignalNumber.UNKNOWN && !headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header he = iterator.next();
                if (he.isSignalNumber()) {
                    if (he.getLogEntry().indexOf(SignalNumber.EXCEPTION_ACCESS_VIOLATION.toString()) > 0) {
                        signalNumber = SignalNumber.EXCEPTION_ACCESS_VIOLATION;
                    } else if (he.getLogEntry().indexOf(SignalNumber.EXCEPTION_STACK_OVERFLOW.toString()) > 0) {
                        signalNumber = SignalNumber.EXCEPTION_STACK_OVERFLOW;
                    } else if (he.getLogEntry().indexOf(SignalNumber.SIGBUS.toString()) > 0) {
                        signalNumber = SignalNumber.SIGBUS;
                    } else if (he.getLogEntry().indexOf(SignalNumber.SIGFPE.toString()) > 0) {
                        signalNumber = SignalNumber.SIGFPE;
                    } else if (he.getLogEntry().indexOf(SignalNumber.SIGILL.toString()) > 0) {
                        signalNumber = SignalNumber.SIGILL;
                    } else if (he.getLogEntry().indexOf(SignalNumber.SIGSEGV.toString()) > 0) {
                        signalNumber = SignalNumber.SIGSEGV;
                    }
                    break;
                }
            }
        }
        return signalNumber;
    }

    /**
     * @param i
     *            The stack frame index (1 = top).
     * @return The stack frame at the specified position.
     */
    public String getStackFrame(int i) {
        String stackFrame = null;
        int stackIndex = 1;
        Iterator<Stack> iterator = stacks.iterator();
        while (iterator.hasNext()) {
            Stack event = iterator.next();
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
        Iterator<Stack> iteratorStack = stacks.iterator();
        while (iteratorStack.hasNext()) {
            Stack event = iteratorStack.next();
            if (event.getLogEntry().matches("^(A|C|j|J|v|V)[ ]{1,2}.+$")) {
                stackFrameTop = event.getLogEntry();
                break;
            }
        }
        if (stackFrameTop == null && !headers.isEmpty()) {
            Iterator<Header> iteratorHeader = headers.iterator();
            while (iteratorHeader.hasNext()) {
                Header he = iteratorHeader.next();
                if (he.isProblematicFrame()) {
                    stackFrameTop = he.getLogEntry().substring(2, he.getLogEntry().length());
                    break;
                }
            }
        }
        return stackFrameTop;
    }

    /**
     * @return The top Compile Java Code (J) stack frame, or null if none exists.
     */
    public String getStackFrameTopCompiledJavaCode() {
        String stackFrameTopCompiledJavaCode = null;
        Iterator<Stack> iterator = stacks.iterator();
        while (iterator.hasNext()) {
            Stack event = iterator.next();
            if (event.getLogEntry().matches("^J[ ]{1,2}.+$")) {
                stackFrameTopCompiledJavaCode = event.getLogEntry();
                break;
            }
        }
        return stackFrameTopCompiledJavaCode;
    }

    /**
     * @return The top Java stack frame (J=compiled Java code, j=interpreted), or null if none exists.
     */
    public String getStackFrameTopJava() {
        String stackFrameTopJava = null;
        Iterator<Stack> iterator = stacks.iterator();
        while (iterator.hasNext()) {
            Stack event = iterator.next();
            if (event.getLogEntry().matches("^[jJ][ ]{1,2}.+$")) {
                stackFrameTopJava = event.getLogEntry();
                break;
            }
        }
        return stackFrameTopJava;
    }

    public List<Stack> getStacks() {
        return stacks;
    }

    public List<StackSlotToMemoryMapping> getStackSlotToMemoryMappings() {
        return stackSlotToMemoryMappings;
    }

    /**
     * @return The storage device where the JDK is installed.
     */
    public Device getStorageDevice() {
        Device device = Device.UNIDENTIFIED;
        if (getOs() == Os.LINUX && !dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getFilePath() != null && event.getFilePath().matches("^.+libjvm\\.so$")) {
                    device = event.getDevice();
                    break;
                }
            }
        }
        return device;
    }

    /**
     * @return The total free swap in bytes.
     */
    public long getSwapFree() {
        long swapFree = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (event.getSwapFree() >= 0) {
                    if (getOs() == Os.WINDOWS && getMemoryFree() >= 0) {
                        // Assume page file becomes full before physical memory?
                        swapFree = Math.max(event.getSwapFree() - getMemoryFree(), 0);
                    } else {
                        Matcher matcher = Memory.PATTERN.matcher(event.getLogEntry());
                        if (matcher.find() && matcher.group(15) != null && matcher.group(17) != null) {
                            swapFree = JdkUtil.convertSize(Long.parseLong(matcher.group(15)),
                                    matcher.group(17).charAt(0), 'B');
                        }
                        break;
                    }
                    break;
                } else if (event.getPageFileFree() >= 0 && getMemoryFree() >= 0) {
                    // Assume page file becomes full before physical memory?
                    swapFree = Math.max(event.getPageFileFree() - getMemoryFree(), 0);
                    break;
                }
            }
        }
        if (swapFree < 0) {
            swapFree = getOsSwapFree();
        }
        return swapFree;
    }

    /**
     * @return The total available swap in bytes.
     */
    public long getSwapTotal() {
        long swapTotal = Long.MIN_VALUE;
        if (!memories.isEmpty()) {
            Iterator<Memory> iterator = memories.iterator();
            while (iterator.hasNext()) {
                Memory event = iterator.next();
                if (event.getSwapTotal() >= 0) {
                    if (getOs() == Os.WINDOWS && getMemoryTotal() >= 0) {
                        swapTotal = event.getSwapTotal() - getMemoryTotal();
                    } else {
                        swapTotal = event.getSwapTotal();
                    }
                    break;
                } else if (event.getPageFileTotal() >= 0 && getMemoryTotal() >= 0) {
                    swapTotal = event.getPageFileTotal() - getMemoryTotal();
                    break;
                }
            }
        }
        if (swapTotal < 0) {
            swapTotal = getOsSwapTotal();
        }
        return swapTotal;
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public List<ThreadsMax> getThreadsMaxes() {
        return threadsMaxes;
    }

    /**
     * @return threads-max, or a negative value if undefined.
     */
    public Long getThreadsMaxLimit() {
        Long threadsMaxLimit = Long.MIN_VALUE;
        if (!threadsMaxes.isEmpty()) {
            Iterator<ThreadsMax> iterator = threadsMaxes.iterator();
            while (iterator.hasNext()) {
                ThreadsMax event = iterator.next();
                if (!event.isHeader()) {
                    threadsMaxLimit = event.getLimit();
                    break;
                }
            }
        }
        return threadsMaxLimit;
    }

    /**
     * Applies only to ThreadStackSize (not CompilerThreadStackSize, VMThreadStackSize, MarkStackSize, or // the
     * JLI_Launch method in main.c that starts the JVM).
     * 
     * @return The stack free space in <code>org.github.joa.util.Constants.PRECISION_REPORTING</code> units.
     */
    public long getThreadStackFreeSpace() {
        long stackFreeSpace = Long.MIN_VALUE;
        if (!stacks.isEmpty()) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
                if (event.isHeader()) {
                    stackFreeSpace = event.getStackFreeSpace();
                    break;
                }
            }
        }
        return stackFreeSpace;
    }

    /**
     * Thread stack size for threads other than VMThread (VMThreadStackSize) and CompilerThread
     * (CompilerThreadStackSize).
     * 
     * @return The stack size reserved (kilobytes).
     */
    public long getThreadStackSize() {
        long stackSize;
        switch (getArchOs()) {
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
        }
        // 1st check [Global flags]
        GlobalFlag globalFlagThreadStackSize = getGlobalFlag("ThreadStackSize");
        if (globalFlagThreadStackSize != null) {
            stackSize = Long.parseLong(globalFlagThreadStackSize.getValue());
        } else if (jvmOptions != null && jvmOptions.getThreadStackSize() != null) {
            char fromUnits;
            long value;
            Pattern pattern = Pattern.compile("^-(X)?(ss|X:ThreadStackSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$");
            Matcher matcher = pattern.matcher(jvmOptions.getThreadStackSize());
            if (matcher.find()) {
                value = Long.parseLong(matcher.group(4));
                if (matcher.group(2) != null && matcher.group(2).equals("X:ThreadStackSize=")) {
                    // value is in kilobytes, multiply by 1024
                    value = JdkUtil.convertSize(value, 'K', 'B');
                }
                if (matcher.group(5) != null) {
                    fromUnits = matcher.group(5).charAt(0);
                } else {
                    fromUnits = 'B';
                }
                stackSize = JdkUtil.convertSize(value, fromUnits, 'K');
            }
        }
        return stackSize;

    }

    public TimeElapsedTime getTimeElapsedTime() {
        return timeElapsedTime;
    }

    public List<Timeout> getTimeouts() {
        return timeouts;
    }

    public List<TransparentHugepageDefrag> getTransparentHugepageDefrags() {
        return transparentHugepageDefrags;
    }

    public List<TransparentHugepageEnabled> getTransparentHugepageEnableds() {
        return transparentHugepageEnableds;
    }

    /**
     * The Transparent Huge Pages (THP) mode.
     * 
     * 
     * @return The Transparent Huge Pages (THP) mode.
     */
    public TransparentHugepageEnabled.MODE getTransparentHugePagesMode() {
        TransparentHugepageEnabled.MODE transparentHugePageMode = TransparentHugepageEnabled.MODE.UNKNOWN;
        if (!transparentHugepageEnableds.isEmpty()) {
            Iterator<TransparentHugepageEnabled> iterator = transparentHugepageEnableds.iterator();
            while (iterator.hasNext()) {
                TransparentHugepageEnabled event = iterator.next();
                if (event.isMode()) {
                    transparentHugePageMode = event.getMode();
                }
                break;
            }
        }
        return transparentHugePageMode;
    }

    /**
     * The Transparent Huge Pages (THP) pmd size.
     * 
     * 
     * @return The Transparent Huge Pages (THP) size.
     */
    public long getTransparentHugePagesPmdSize() {
        long transparentHugePagesPmdSize = Long.MIN_VALUE;
        if (!transparentHugepageHpagePmdSizes.isEmpty()) {
            Iterator<TransparentHugepageHpagePmdSize> iterator = transparentHugepageHpagePmdSizes.iterator();
            while (iterator.hasNext()) {
                TransparentHugepageHpagePmdSize event = iterator.next();
                if (event.isSize()) {
                    transparentHugePagesPmdSize = event.getSize();
                }
                break;
            }
        }
        return transparentHugePagesPmdSize;
    }

    public Uname getUname() {
        return uname;
    }

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    }

    /**
     * @return The duration of the JVM run in milliseconds.
     */
    public Long getUptime() {
        long uptime = Long.MIN_VALUE;
        if (timeElapsedTime != null && timeElapsedTime.getUptime() > 0) {
            uptime = timeElapsedTime.getUptime();
        } else if (elapsedTime != null && elapsedTime.getUptime() > 0) {
            uptime = elapsedTime.getUptime();
        }
        return uptime;
    }

    /**
     * @return The USERNAME environment variable.
     */
    public String getUsername() {
        String username = null;
        if (!environmentVariables.isEmpty()) {
            String regExUsername = "^USERNAME=(.+)$";
            Pattern pattern = Pattern.compile(regExUsername);
            Iterator<EnvironmentVariable> iterator = environmentVariables.iterator();
            while (iterator.hasNext()) {
                EnvironmentVariable event = iterator.next();
                Matcher matcher = pattern.matcher(event.getLogEntry());
                if (matcher.find()) {
                    username = matcher.group(1);
                    break;
                }
            }
        }
        return username;
    }

    public List<VirtualizationInfo> getVirtualizationInfos() {
        return virtualizationInfos;
    }

    public List<VmArguments> getVmArguments() {
        return vmArguments;
    }

    public VmInfo getVmInfo() {
        return vmInfo;
    }

    public VmOperation getVmOperation() {
        return vmOperation;
    }

    public VmState getVmState() {
        return vmState;
    }

    public List<ZgcPhaseSwitchEvent> getZgcPhaseSwitchEvents() {
        return zgcPhaseSwitchEvents;
    }

    /**
     * @param key
     *            The analysis to check.
     * @return True if the {@link org.github.joa.util.Analysis} exists, false otherwise.
     */
    public boolean hasAnalysis(String key) {
        boolean hasAnalysis = false;
        List<String[]> analysis = getAnalysis();
        if (!analysis.isEmpty()) {
            Iterator<String[]> i = analysis.iterator();
            while (i.hasNext()) {
                String[] a = i.next();
                if (a[0].equals(key)) {
                    hasAnalysis = true;
                    break;
                }
            }
        }
        return hasAnalysis;
    }

    /**
     * @param capability
     *            The cpu capability as a regex.
     * @return True if the CPU has the specified capability (specified as a regex), false otherwise.
     */
    public boolean hasCpuCapability(String capability) {
        boolean hasCpuCapability = false;
        if (!cpuInfos.isEmpty()) {
            Iterator<CpuInfo> iterator = cpuInfos.iterator();
            while (iterator.hasNext()) {
                CpuInfo event = iterator.next();
                if (event.isCpuHeader()) {
                    hasCpuCapability = event.getLogEntry().matches("^.*( " + capability + ",.+| " + capability + ")$");
                    break;
                }
            }
        }
        return hasCpuCapability;
    }

    /**
     * @return true if there is analysis related to rlimits, false otherwise.
     */
    public boolean hasRlimitAnalysis() {
        boolean hasRlimitAnalysis = false;
        if ((hasAnalysis(Analysis.ERROR_OOME_RLIMIT.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_RLIMIT_STARTUP.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_RLIMIT_OOPS.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_RLIMIT_OOPS_STARTUP.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_OVERCOMMIT_RLIMIT_STARTUP.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_THREAD_LEAK.getKey())
                || hasAnalysis(Analysis.ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL.getKey()))) {
            hasRlimitAnalysis = true;
        }
        return hasRlimitAnalysis;
    }

    /**
     * @return true if there is a cgroup memory limit, false otherwise.
     */
    public boolean haveCgroupMemoryLimit() {
        boolean isCgroupMemoryLimit = false;
        if (!containerInfos.isEmpty()) {
            Iterator<ContainerInfo> iterator = containerInfos.iterator();
            while (iterator.hasNext()) {
                ContainerInfo event = iterator.next();
                if (event.getLogEntry().matches("^memory_limit_in_bytes: \\d{1,}$")) {
                    isCgroupMemoryLimit = true;
                    break;
                }
            }
        }
        return isCgroupMemoryLimit;
    }

    /**
     * @return true if the stack contains frames, false otherwise.
     */
    public boolean haveFramesInStack() {
        boolean haveFramesInStack = false;
        if (!stacks.isEmpty()) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
                if (event.isFrame()) {
                    haveFramesInStack = true;
                    break;
                }
            }
        }
        return haveFramesInStack;
    }

    /**
     * @return true if JDK debug symbols are installed, false otherwise.
     */
    public boolean haveJdkDebugSymbols() {
        boolean haveJdkDebugSymbols = false;
        if (!headers.isEmpty()) {
            Iterator<Header> iterator1 = headers.iterator();
            while (iterator1.hasNext()) {
                Header he = iterator1.next();
                if (he.isProblematicFrame()) {
                    haveJdkDebugSymbols = he.getLogEntry().matches("^# V  \\[.+\\].+$");
                    break;
                }
            }
        }
        if (!haveJdkDebugSymbols) {
            if (!stacks.isEmpty()) {
                Iterator<Stack> iterator2 = stacks.iterator();
                while (iterator2.hasNext() && !haveJdkDebugSymbols) {
                    Stack se = iterator2.next();
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
     * @return true if there were LinkageErrors before the crash, false otherwise.
     */
    public boolean haveLinkageError() {
        boolean haveLinkageError = false;
        if (!exceptionCounts.isEmpty()) {
            Iterator<ExceptionCounts> iteratorExceptionCounts = exceptionCounts.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCounts exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^LinkageErrors=\\d{1,}$")) {
                    haveLinkageError = true;
                    break;
                }
            }
        }
        return haveLinkageError;
    }

    /**
     * @return true if there were "OutOfMemoryError: Compressed class space" or caught and thrown before the crash,
     *         false otherwise.
     */
    public boolean haveOomeThrownCompressedClassSpace() {
        boolean haveOomeThrownCompressedClassSpace = false;
        if (!exceptionCounts.isEmpty()) {
            Iterator<ExceptionCounts> iteratorExceptionCounts = exceptionCounts.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCounts exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader() && exceptionCountsEvent.getLogEntry()
                        .matches("^OutOfMemoryError class_metaspace_errors=\\d{1,}$")) {
                    haveOomeThrownCompressedClassSpace = true;
                    break;
                }
            }
        }
        return haveOomeThrownCompressedClassSpace;
    }

    /**
     * @return true if there were OutOfMemoryError other than "Metaspace" or "Compressed class space" caught and thrown
     *         before the crash, false otherwise.
     */
    public boolean haveOomeThrownJavaHeap() {
        boolean haveOomeThrownJavaHeap = false;
        if (!exceptionCounts.isEmpty()) {
            Iterator<ExceptionCounts> iteratorExceptionCounts = exceptionCounts.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCounts exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^OutOfMemoryError java_heap_errors=\\d{1,}$")) {
                    haveOomeThrownJavaHeap = true;
                    break;
                }
            }
        }
        return haveOomeThrownJavaHeap;
    }

    /**
     * @return true if there were "OutOfMemoryError: Metaspace" or caught and thrown before the crash, false otherwise.
     */
    public boolean haveOomeThrownMetaspace() {
        boolean haveOomeThrownMetaspace = false;
        if (!exceptionCounts.isEmpty()) {
            Iterator<ExceptionCounts> iteratorExceptionCounts = exceptionCounts.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCounts exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^OutOfMemoryError metaspace_errors=\\d{1,}$")) {
                    haveOomeThrownMetaspace = true;
                    break;
                }
            }
        }
        return haveOomeThrownMetaspace;
    }

    /**
     * @return true if there were StackOverflowErrors before the crash, false otherwise.
     */
    public boolean haveStackOverFlowError() {
        boolean haveStackOverFlowError = false;
        if (!exceptionCounts.isEmpty()) {
            Iterator<ExceptionCounts> iteratorExceptionCounts = exceptionCounts.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCounts exceptionCountsEvent = iteratorExceptionCounts.next();
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
        //
        if (stacks.size() > 2) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
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
        if (!headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header event = iterator.next();
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
        if (!stacks.isEmpty()) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
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
        if (!stacks.isEmpty()) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
                if (event.isVmGeneratedCodeFrame()) {
                    haveVmGeneratedCodeFrameInStack = true;
                    break;
                }
            }
        }
        return haveVmGeneratedCodeFrameInStack;
    }

    /**
     * Create {@link org.github.krashpad.domain.jdk.GarbageCollection}s from
     * {@link org.github.krashpad.domain.jdk.GcHeapHistoryEvent}s.
     */
    private void hydrateGarbageCollections() {
        if (!gcHeapHistoryEvents.isEmpty()) {
            Iterator<GcHeapHistoryEvent> iterator = gcHeapHistoryEvents.iterator();
            GarbageCollection gc = new GarbageCollection();
            Pattern patternBegin = Pattern.compile(GcHeapHistoryEvent._REGEX_BEGIN);
            Pattern patternEnd = Pattern.compile(GcHeapHistoryEvent._REGEX_END);
            boolean haveBeginning = false;
            while (iterator.hasNext()) {
                GcHeapHistoryEvent event = iterator.next();
                if (event.isBeginning()) {
                    Matcher matcher = patternBegin.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        gc.setTimestampStartGc(JdkMath.convertSecsToMillis(matcher.group(1)).longValue());
                    }
                    haveBeginning = true;
                } else if (event.isEnd() && haveBeginning) {
                    Matcher matcher = patternEnd.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        gc.setTimestampEndGc(JdkMath.convertSecsToMillis(matcher.group(1)).longValue());
                    }
                    garbageCollections.add(gc);
                    gc = null;
                    gc = new GarbageCollection();
                }
            }
        }
    }

    /**
     * Populate JVM user from hsperfdata string. For example, the following user is jb_admin:
     * 
     * 7ff0f61d2000-7ff0f61da000 rw-s 00000000 fd:01 33563495 /tmp/hsperfdata_jb_admin/92333
     */
    public void hydrateJvmUser() {
        String jvmUser = null;
        if (!dynamicLibraries.isEmpty()) {
            String regExHsPerfData = System.getProperty("file.separator") + "hsperfdata_([^"
                    + System.getProperty("file.separator") + "]+)";
            Pattern pattern = Pattern.compile(regExHsPerfData);
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.getFilePath() != null) {
                    Matcher matcher = pattern.matcher(event.getFilePath());
                    if (matcher.find()) {
                        jvmUser = matcher.group(1);
                        break;
                    }
                }
            }
        }
        this.jvmUser = jvmUser;
    }

    /**
     * Populate native libraries list (unique entries).
     */
    private void hydrateNativeLibraries() {
        List<String> nativeLibraries = new ArrayList<String>();
        if (!dynamicLibraries.isEmpty()) {
            Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
            while (iterator.hasNext()) {
                DynamicLibrary event = iterator.next();
                if (event.isNativeLibrary()) {
                    if (!nativeLibraries.contains(event.getFilePath())) {
                        nativeLibraries.add(event.getFilePath());
                    }
                }
            }
        }
        this.nativeLibraries = nativeLibraries;
    }

    /**
     * Populate unknown native libraries.
     */
    private void hydrateNativeLibrariesUnknown() {
        List<String> nativeLibrariesUnknown = new ArrayList<String>();
        if (!nativeLibraries.isEmpty()) {
            Iterator<String> iterator = nativeLibraries.iterator();
            while (iterator.hasNext()) {
                String nativeLibraryPath = iterator.next();
                String nativeLibrary = org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath);
                if (!KrashUtil.NATIVE_LIBRARIES_GOOGLE.contains(nativeLibrary)
                        && !KrashUtil.NATIVE_LIBRARIES_JBOSS.contains(nativeLibrary)
                        && !(KrashUtil.NATIVE_LIBRARIES_LINUX.contains(nativeLibrary)
                                && nativeLibraryPath.matches(KrashUtil.NATIVE_LIBRARY_LINUX_HOME + ".+"))
                        && !KrashUtil.NATIVE_LIBRARIES_LINUX_JAVA.contains(nativeLibrary)
                        && !KrashUtil.NATIVE_LIBRARIES_ORACLE.contains(nativeLibrary)
                        && !KrashUtil.NATIVE_LIBRARIES_TOMCAT
                                .contains(org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath))
                        && !KrashUtil.NATIVE_LIBRARIES_VMWARE
                                .contains(org.github.joa.util.JdkRegEx.getFile(nativeLibraryPath))
                        && !(KrashUtil.NATIVE_LIBRARIES_WINDOWS.contains(nativeLibrary)
                                && nativeLibraryPath.matches(KrashUtil.NATIVE_LIBRARY_WINDOWS_SYSTEM_HOME + ".+"))
                        && !KrashUtil.NATIVE_LIBRARIES_WINDOWS_JAVA.contains(nativeLibrary)) {
                    nativeLibrariesUnknown.add(nativeLibraryPath);
                }
            }
        }
        this.nativeLibrariesUnknown = nativeLibrariesUnknown;
    }

    /**
     * Populate rpm directory.
     */
    private void hydrateRpmDirectory() {
        String rpmDirectory = null;
        if (getOs() == Os.LINUX) {
            if (!dynamicLibraries.isEmpty()) {
                Iterator<DynamicLibrary> iterator = dynamicLibraries.iterator();
                while (iterator.hasNext()) {
                    DynamicLibrary event = iterator.next();
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
                        } else if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK17_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK17_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            }
                            break;
                        } else if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK21_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK21_LIBJVM_PATH);
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
        this.rpmDirectory = rpmDirectory;
    }

    /**
     * @return true if the fatal error log was created on CentOS, false otherwise.
     */
    public boolean isCentOs() {
        boolean isCentOs = false;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    }
                    isCentOs = event.getLogEntry().matches("^.*CentOS Linux release.+$");
                    break;
                }
            }
        }
        return isCentOs;
    }

    /**
     * @return true if the JVM is using compressed object pointers, false otherwise.
     */
    public boolean isCompressedOops() {
        boolean isCompressedOoops = true;
        BigDecimal thirtyTwoGigabytes = new BigDecimal("32").multiply(org.github.joa.util.Constants.GIGABYTE);
        if (getHeapMaxSize() >= thirtyTwoGigabytes.longValue()) {
            isCompressedOoops = false;
        } else if (jvmOptions != null && JdkUtil.isOptionDisabled(jvmOptions.getUseCompressedOops())) {
            isCompressedOoops = false;
        } else if (!isTruncated() && !headers.isEmpty() && !isInHeader("compressed oops")) {
            isCompressedOoops = false;
        }
        return isCompressedOoops;
    }

    /**
     * @return true if there is evidence the crash happens in a container environment, false otherwise.
     */
    public boolean isContainer() {
        boolean isContainer = false;
        if (!containerInfos.isEmpty() && getSwapTotal() == 0) {
            isContainer = true;
        }
        return isContainer;
    }

    /**
     * @return true if the crash happens when the JVM starts, false otherwise.
     */
    public boolean isCrashOnStartup() {
        boolean isCrashOnStartup = false;
        if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
            isCrashOnStartup = true;
        }
        return isCrashOnStartup;
    }

    /**
     * @param errorRegEx
     *            The error regex to search for.
     * @return True if the crash error contains the string, false otherwise.
     */
    public boolean isError(String errorRegEx) {
        boolean isError = false;
        Pattern pattern = Pattern.compile(errorRegEx);
        Matcher matcher = pattern.matcher(getError());
        if (matcher.find()) {
            isError = true;
        }
        return isError;
    }

    /**
     * @return true if HyperV environment, false otherwise.
     */
    public boolean isHyperVEnvironment() {
        boolean isVMWareEnvironment = false;
        if (!containerInfos.isEmpty()) {
            Iterator<ContainerInfo> iterator = containerInfos.iterator();
            while (iterator.hasNext()) {
                ContainerInfo event = iterator.next();
                if (event.getLogEntry().matches("^HyperV virtualization detected$")) {
                    isVMWareEnvironment = true;
                    break;
                }
            }
        }
        return isVMWareEnvironment;
    }

    /**
     * @param regEx
     *            A regular expression.
     * @return true if the regex is in the compilation events, false otherwise.
     */
    public boolean isInCompilationEvents(String regEx) {
        boolean isInCompilationEvents = false;
        if (!compilationEvents.isEmpty()) {
            Iterator<CompilationEvent> iterator = compilationEvents.iterator();
            while (iterator.hasNext()) {
                CompilationEvent event = iterator.next();
                if (event.getLogEntry().matches("^.*" + regEx + ".*$")) {
                    isInCompilationEvents = true;
                    break;
                }
            }
        }
        return isInCompilationEvents;
    }

    /**
     * @param regEx
     *            A regular expression.
     * @return true if the regex is in the environment variables, false otherwise.
     */
    public boolean isInEnvironmentVariables(String regEx) {
        boolean isInEnvironmentVariables = false;
        if (!environmentVariables.isEmpty()) {
            Iterator<EnvironmentVariable> iterator = environmentVariables.iterator();
            while (iterator.hasNext()) {
                EnvironmentVariable event = iterator.next();
                if (event.getLogEntry().matches("^.*" + regEx + ".*$")) {
                    isInEnvironmentVariables = true;
                    break;
                }
            }
        }
        return isInEnvironmentVariables;
    }

    /**
     * @param regEx
     *            A regular expression.
     * @return true if the regex is in the header, false otherwise.
     */
    public boolean isInHeader(String regEx) {
        boolean isInHeader = false;
        if (!headers.isEmpty()) {
            Iterator<Header> iterator = headers.iterator();
            while (iterator.hasNext()) {
                Header event = iterator.next();
                if (event.getLogEntry().matches("^.*" + regEx + ".*$")) {
                    isInHeader = true;
                }
            }
        }
        return isInHeader;
    }

    /**
     * @param classRegEx
     *            A class name as a regular expression.
     * @return true if the class is in the stack, false otherwise.
     */
    public boolean isInStack(String classRegEx) {
        boolean isInStack = false;
        if (!stacks.isEmpty()) {
            Iterator<Stack> iterator = stacks.iterator();
            while (iterator.hasNext()) {
                Stack event = iterator.next();
                if (event.getLogEntry().matches("^.+" + classRegEx + ".+$")) {
                    isInStack = true;
                    break;
                }
            }
        }
        if (!isInStack) {
            if (!stackSlotToMemoryMappings.isEmpty()) {
                Iterator<StackSlotToMemoryMapping> iterator = stackSlotToMemoryMappings.iterator();
                while (iterator.hasNext()) {
                    StackSlotToMemoryMapping event = iterator.next();
                    if (event.getLogEntry().matches("^.+" + classRegEx + ".+$")) {
                        isInStack = true;
                        break;
                    }
                }
            }
        }
        if (!isInStack) {
            if (!registerToMemoryMappings.isEmpty()) {
                Iterator<RegisterToMemoryMapping> iterator = registerToMemoryMappings.iterator();
                while (iterator.hasNext()) {
                    RegisterToMemoryMapping event = iterator.next();
                    if (event.getLogEntry().matches("^.+" + classRegEx + ".+$")) {
                        isInStack = true;
                        break;
                    }
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
        case JDK17:
        case JDK21:
            isJdkLts = true;
            break;
        case JDK9:
        case JDK10:
        case JDK12:
        case JDK13:
        case JDK14:
        case JDK15:
        case JDK16:
        case JDK18:
        case JDK19:
        case JDK20:
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
        if (getStacks() != null && getStacks().size() >= 2) {
            String stackFrame2 = getStackFrame(2);
            if (stackFrame2 != null && stackFrame2.matches("^[CjJ].+com[\\._]sun[\\._]jna[\\._].+$")) {
                isJnaCrash = true;
            }
        }
        return isJnaCrash;
    }

    /**
     * @return true if the crash is due to a memory allocation failing, false otherwise.
     */
    public boolean isMemoryAllocationFail() {
        boolean isMemoryAllocationFail = false;
        if (isError("There is insufficient memory for the Java Runtime Environment to continue.")
                || isError("Failed to map memory")) {
            isMemoryAllocationFail = true;
        }
        return isMemoryAllocationFail;
    }

    /**
     * @return true if the crash is due to memory corruption, false otherwise.
     */
    public boolean isMemoryCorruption() {
        boolean isMemoryCorruption = false;
        if (getSignalNumber() == SignalNumber.EXCEPTION_ACCESS_VIOLATION || getSignalNumber() == SignalNumber.SIGSEGV
                || getSignalNumber() == SignalNumber.SIGBUS || getSignalCode() == SignalCode.SEGV_MAPERR) {
            isMemoryCorruption = true;
        }
        return isMemoryCorruption;
    }

    /**
     * @return true if garbage collection is multithreaded, false otherwise.
     */
    public boolean isMultithreadedGc() {
        boolean isMultithreadedGc = false;
        Iterator<GarbageCollector> iterator = getGarbageCollectors().iterator();
        while (iterator.hasNext()) {
            GarbageCollector gc = iterator.next();
            switch (gc) {
            case CMS:
            case G1:
            case PARALLEL_OLD:
            case PARALLEL_SCAVENGE:
            case PAR_NEW:
            case SHENANDOAH:
            case ZGC_GENERATIONAL:
            case ZGC_NON_GENERATIONAL:
                isMultithreadedGc = true;
                break;
            case SERIAL_NEW:
            case SERIAL_OLD:
            case PARALLEL_SERIAL_OLD:
            case UNKNOWN:
            default:
                break;
            }
        }
        return isMultithreadedGc;
    }

    /**
     * @return true if the fatal error log was created on RHEL, false otherwise.
     */
    public boolean isOracleLinux() {
        boolean isOracleLinux = false;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    }
                    isOracleLinux = event.getLogEntry().matches("^.*Oracle Linux Server release.+$");
                    break;
                }
            }
        }
        return isOracleLinux;
    }

    /**
     * Try to determine if overcommit is disabled ((vm.overcommit_memory=2). This cannot be determined directly because
     * kernel flags are not output to the fatal error log (i.e. there can be false negatives).
     * 
     * @return true if it has been determined that overcommit is disabled (vm.overcommit_memory=2), false otherwise.
     */
    public boolean isOvercommitDisabled() {
        boolean isOvercommitDisabled = false;
        // vm.overcommit_memory=2, vm.overcommit_ratio=100)
        if (getMemoryTotal() > 0 && getOsCommitLimit() > 0 && getMemoryTotal() == getOsCommitLimit()) {
            isOvercommitDisabled = true;
        }
        return isOvercommitDisabled;
    }

    /**
     * @return true if memory is overcommitted, false otherwise.
     */
    private boolean isOvercommitted() {
        boolean isOvercommitted = false;
        if (getOsCommitLimit() > 0 && getOsCommitLimitUsed() > 0 && (getOsCommitLimitUsed() > getOsCommitLimit())) {
            isOvercommitted = true;
        }
        return isOvercommitted;
    }

    /**
     * @return true if the JDK build date/time is a Red Hat build date/time, false otherwise.
     */
    public boolean isRhBuildDate() {
        boolean isRhBuildDate = false;
        Date jdkBuildDate = getJdkBuildDate();
        if (jdkBuildDate != null) {
            Release release = getJdkRelease();
            if (release != null && release.getBuildDate() != null && !release.isBuildDateEstimate()
                    && jdkBuildDate.compareTo(release.getBuildDate()) == 0) {
                isRhBuildDate = true;
            }
        }
        return isRhBuildDate;
    }

    /**
     * @return true if the Red Hat build date/time for the JDK version is unknown, false otherwise.
     */
    public boolean isRhBuildDateUnknown() {
        boolean isRhBuildDateUnknown = false;
        Release release = getJdkRelease();
        if (release != null && release.isBuildDateEstimate()) {
            isRhBuildDateUnknown = true;
        }
        return isRhBuildDateUnknown;
    }

    /**
     * @return true if the fatal error log was created by a RH build of OpenJDK, false otherwise.
     */
    public boolean isRhBuildOpenJdk() {
        return isRhRpmInstall() || isRhLinuxZipInstall() || isRhWindowsZipInstall() || isRhRpm();
    }

    /**
     * @return true if the fatal error log was created by a JDK build string used by Red Hat, false otherwise.
     */
    public boolean isRhBuildString() {
        BuiltBy builtBy = getJdkBuiltBy();
        return builtBy == BuiltBy.BUILD || builtBy == BuiltBy.EMPTY || builtBy == BuiltBy.MOCKBUILD;
    }

    /**
     * @return true if the fatal error log was created on RHEL, false otherwise.
     */
    public boolean isRhel() {
        boolean isRhel = false;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$")) {
                        // OS string on next line
                        event = iterator.next();
                    }
                    isRhel = event.getLogEntry().matches("^.*Red Hat Enterprise Linux.+$");
                    break;
                }
            }
        }
        return isRhel;
    }

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK RHEL tarball, false
     *         otherwise.
     */
    public boolean isRhLinuxZipInstall() {
        boolean isRhLinuxZipInstall = false;
        if (getOs() == Os.LINUX && getArchOs() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhLinuxZipInstall = Jdk8.RHEL_ZIPS.containsKey(getJdkReleaseString()) && getJdkBuildDate() != null
                        && getJdkBuildDate().compareTo(Jdk8.RHEL_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK11:
                isRhLinuxZipInstall = Jdk11.RHEL_ZIPS.containsKey(getJdkReleaseString()) && getJdkBuildDate() != null
                        && getJdkBuildDate() != null
                        && getJdkBuildDate().compareTo(Jdk11.RHEL_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK17:
                isRhLinuxZipInstall = Jdk17.RHEL_ZIPS.containsKey(getJdkReleaseString()) && getJdkBuildDate() != null
                        && getJdkBuildDate() != null
                        && getJdkBuildDate().compareTo(Jdk17.RHEL_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
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
                iterator = Jdk8.RHEL6_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case CENTOS7:
            case RHEL7:
                if (getArchOs() == Arch.X86_64) {
                    iterator = Jdk8.RHEL7_X86_64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && jdkBuildDate != null
                                && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        }
                    }
                } else if (getArchOs() == Arch.PPC64) {
                    iterator = Jdk8.RHEL7_PPC64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        }
                    }
                } else if (getArchOs() == Arch.PPC64LE) {
                    iterator = Jdk8.RHEL7_PPC64LE_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        }
                    }
                }
                break;
            case CENTOS8:
            case RHEL8:
                if (getArchOs() == Arch.X86_64) {
                    iterator = Jdk8.RHEL8_X86_64_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        }
                    }
                } else if (getArchOs() == Arch.PPC64LE) {
                    iterator = Jdk8.RHEL8_PPC64LE_RPMS.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Entry<String, Release> entry = iterator.next();
                        Release release = entry.getValue();
                        if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                                && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                            isRhelRpm = true;
                            break;
                        }
                    }
                }
                break;
            case CENTOS9:
            case RHEL9:
                iterator = Jdk8.RHEL9_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case UNIDENTIFIED:
            default:
                break;
            }
        } else if (getJavaSpecification() == JavaSpecification.JDK11) {
            switch (getOsVersion()) {
            case CENTOS7:
            case RHEL7:
                iterator = Jdk11.RHEL7_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case CENTOS8:
            case RHEL8:
                iterator = Jdk11.RHEL8_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case RHEL9:
                iterator = Jdk11.RHEL9_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case CENTOS6:
            case RHEL6:
            case UNIDENTIFIED:
            default:
                break;
            }
        } else if (getJavaSpecification() == JavaSpecification.JDK17) {
            switch (getOsVersion()) {
            case CENTOS8:
            case RHEL8:
                iterator = Jdk17.RHEL8_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case RHEL9:
                iterator = Jdk17.RHEL9_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case CENTOS6:
            case RHEL6:
            case CENTOS7:
            case RHEL7:
            case UNIDENTIFIED:
            default:
                break;
            }
        } else if (getJavaSpecification() == JavaSpecification.JDK21) {
            switch (getOsVersion()) {
            case CENTOS8:
            case RHEL8:
                iterator = Jdk21.RHEL8_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case RHEL9:
                iterator = Jdk21.RHEL9_X86_64_RPMS.entrySet().iterator();
                while (iterator.hasNext()) {
                    Entry<String, Release> entry = iterator.next();
                    Release release = entry.getValue();
                    if (release.getVersion().equals(jdkReleaseString) && release.getBuildDate() != null
                            && release.getBuildDate().compareTo(jdkBuildDate) == 0) {
                        isRhelRpm = true;
                        break;
                    }
                }
                break;
            case CENTOS6:
            case RHEL6:
            case CENTOS7:
            case RHEL7:
            case UNIDENTIFIED:
            default:
                break;
            }
        }
        return isRhelRpm;
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
                    isRhelRpmInstall = Jdk8.RHEL6_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk8.RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS7:
                case RHEL7:
                    if (getArchOs() == Arch.X86_64) {
                        isRhelRpmInstall = Jdk8.RHEL7_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                                && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArchOs() == Arch.PPC64) {
                        isRhelRpmInstall = Jdk8.RHEL7_PPC64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                                && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArchOs() == Arch.PPC64LE) {
                        isRhelRpmInstall = Jdk8.RHEL7_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    }
                    break;
                case CENTOS8:
                case RHEL8:
                    if (getArchOs() == Arch.X86_64) {
                        isRhelRpmInstall = Jdk8.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                                && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArchOs() == Arch.PPC64LE) {
                        isRhelRpmInstall = Jdk8.RHEL8_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL8_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    }
                    break;
                case CENTOS9:
                case RHEL9:
                    isRhelRpmInstall = Jdk8.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk8.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case UNIDENTIFIED:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK11) {
                switch (getOsVersion()) {
                case CENTOS7:
                case RHEL7:
                    isRhelRpmInstall = Jdk11.RHEL7_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk11.RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = Jdk11.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk11.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = Jdk11.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk11.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case UNIDENTIFIED:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK17) {
                switch (getOsVersion()) {
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = Jdk17.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk17.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = Jdk17.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk17.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case CENTOS7:
                case RHEL7:
                case UNIDENTIFIED:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK21) {
                switch (getOsVersion()) {
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = Jdk21.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk21.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = Jdk21.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk21.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case CENTOS7:
                case RHEL7:
                case UNIDENTIFIED:
                default:
                    break;
                }
            }
        }
        return isRhelRpmInstall;
    }

    /**
     * @return true if the version matches a Red Hat build of OpenJDK, false otherwise.
     */
    public boolean isRhVersion() {
        boolean isRhVersion = false;
        if (getOs() == Os.LINUX) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhVersion = Jdk8.RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk8.RHEL6_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk8.RHEL7_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk8.RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk8.RHEL9_X86_64_RPMS);
                break;
            case JDK11:
                isRhVersion = Jdk11.RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk11.RHEL7_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk11.RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk11.RHEL9_X86_64_RPMS);
                break;
            case JDK17:
                isRhVersion = Jdk17.RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk17.RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk17.RHEL9_X86_64_RPMS);
                break;
            case JDK21:
                isRhVersion = Jdk21.RHEL_ZIPS.containsKey(getJdkReleaseString())
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk21.RHEL8_X86_64_RPMS)
                        || JdkUtil.isReleaseStringInReleases(getJdkReleaseString(), Jdk21.RHEL9_X86_64_RPMS);
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            }
        } else if (getOs() == Os.WINDOWS) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhVersion = Jdk8.WINDOWS_ZIPS.containsKey(getJdkReleaseString());
                break;
            case JDK11:
                isRhVersion = Jdk11.WINDOWS_ZIPS.containsKey(getJdkReleaseString());
                break;
            case JDK17:
                isRhVersion = Jdk17.WINDOWS_ZIPS.containsKey(getJdkReleaseString());
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            }
        }
        return isRhVersion;
    }

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK Windows zip install,
     *         false otherwise.
     */
    public boolean isRhWindowsZipInstall() {
        boolean isRhWindowsZipInstall = false;
        if (isWindows() && getArchOs() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhWindowsZipInstall = Jdk8.WINDOWS_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(Jdk8.WINDOWS_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK11:
                isRhWindowsZipInstall = Jdk11.WINDOWS_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(Jdk11.WINDOWS_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK17:
                isRhWindowsZipInstall = Jdk17.WINDOWS_ZIPS.containsKey(getJdkReleaseString())
                        && getJdkBuildDate() != null && getJdkBuildDate()
                                .compareTo(Jdk17.WINDOWS_ZIPS.get(getJdkReleaseString()).getBuildDate()) == 0;
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
                    isRhelRpmInstall = Jdk8.RHEL6_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk8.RHEL6_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS7:
                case RHEL7:
                    if (getArchOs() == Arch.X86_64) {
                        isRhelRpmInstall = Jdk8.RHEL7_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                                && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArchOs() == Arch.PPC64) {
                        isRhelRpmInstall = Jdk8.RHEL7_PPC64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                                && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL7_PPC64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArchOs() == Arch.PPC64LE) {
                        isRhelRpmInstall = Jdk8.RHEL7_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL7_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    }
                    break;
                case CENTOS8:
                case RHEL8:
                    if (getArchOs() == Arch.X86_64) {
                        isRhelRpmInstall = Jdk8.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                                && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    } else if (getArchOs() == Arch.PPC64LE) {
                        isRhelRpmInstall = Jdk8.RHEL8_PPC64LE_RPMS.containsKey(rpmDirectory)
                                && getJdkBuildDate() != null && getJdkBuildDate()
                                        .compareTo(Jdk8.RHEL8_PPC64LE_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    }
                    break;
                case CENTOS9:
                case RHEL9:
                    isRhelRpmInstall = Jdk8.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk8.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case UNIDENTIFIED:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK11) {
                switch (getOsVersion()) {
                case CENTOS7:
                case RHEL7:
                    isRhelRpmInstall = Jdk11.RHEL7_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk11.RHEL7_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = Jdk11.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk11.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = Jdk11.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk11.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case UNIDENTIFIED:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK17) {
                switch (getOsVersion()) {
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = Jdk17.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk17.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = Jdk17.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk17.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case CENTOS7:
                case RHEL7:
                case UNIDENTIFIED:
                default:
                    break;
                }
            } else if (getJavaSpecification() == JavaSpecification.JDK21) {
                switch (getOsVersion()) {
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = Jdk21.RHEL8_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk21.RHEL8_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case RHEL9:
                    isRhelRpmInstall = Jdk21.RHEL9_X86_64_RPMS.containsKey(rpmDirectory) && getJdkBuildDate() != null
                            && getJdkBuildDate()
                                    .compareTo(Jdk21.RHEL9_X86_64_RPMS.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case CENTOS7:
                case RHEL7:
                case UNIDENTIFIED:
                default:
                    break;
                }
            }
        }
        return isRhelRpmInstall;
    }

    /**
     * @return true if the fatal error is truncated, false otherwise.
     */
    public boolean isTruncated() {
        boolean isTruncated = false;
        // Don't consider it truncated if only "END." is missing.
        if (end == null && elapsedTime == null) {
            isTruncated = true;
        }
        return isTruncated;
    }

    /**
     * @return true if VMWare environment, false otherwise.
     */
    public boolean isVMWareEnvironment() {
        boolean isVMWareEnvironment = false;
        if (!virtualizationInfos.isEmpty()) {
            Iterator<VirtualizationInfo> iterator = virtualizationInfos.iterator();
            while (iterator.hasNext()) {
                VirtualizationInfo event = iterator.next();
                if (event.getLogEntry().matches("^VMWare virtualization detected$")) {
                    isVMWareEnvironment = true;
                    break;
                }
            }
        }
        if (!isVMWareEnvironment && !getNativeLibrariesVmWare().isEmpty()) {
            isVMWareEnvironment = true;
        }
        return isVMWareEnvironment;
    }

    /**
     * @return true if the fatal error log was created on Windows, false otherwise.
     */
    public boolean isWindows() {
        boolean isWindows = false;
        if (!osInfos.isEmpty()) {
            Iterator<OsInfo> iterator = osInfos.iterator();
            while (iterator.hasNext()) {
                OsInfo event = iterator.next();
                if (event.isHeader()) {
                    if (event.getLogEntry().matches("^OS:$") && iterator.hasNext()) {
                        // OS string on next line
                        event = iterator.next();
                    }
                    isWindows = event.getLogEntry().matches("^OS: Windows.+$");
                    break;
                }
            }
        }
        return isWindows;
    }

    /**
     * Convenience method to remove <code>Analysis</code>.
     * 
     * @param key
     *            The <code>Analysis</code> to check.
     */
    public void removeAnalysis(Analysis key) {
        analysis.remove(key);
    }

    public void setBarrierSet(BarrierSet barrierSet) {
        this.barrierSet = barrierSet;
    }

    public void setCommandLine(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public void setCompressedClassSpaceEvent(CompressedClassSpace compressedClassSpaceEvent) {
        this.compressedClassSpaceEvent = compressedClassSpaceEvent;
    }

    public void setCurrentThread(CurrentThread currentThread) {
        this.currentThread = currentThread;
    }

    public void setDynamicLibrariesMappingCount(int dynamicLibrariesMappingCount) {
        this.dynamicLibrariesMappingCount = dynamicLibrariesMappingCount;
    }

    public void setElapsedTime(ElapsedTime elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setEnd(End end) {
        this.end = end;
    }

    public void setHeapAddress(HeapAddress heapAddress) {
        this.heapAddress = heapAddress;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setNarrowKlass(NarrowKlass narrowKlass) {
        this.narrowKlass = narrowKlass;
    }

    public void setPeriodicNativeTrim(PeriodicNativeTrim periodicNativeTrim) {
        this.periodicNativeTrim = periodicNativeTrim;
    }

    public void setRlimit(Rlimit rlimit) {
        this.rlimit = rlimit;
    }

    public void setSigInfo(SigInfo sigInfo) {
        this.sigInfo = sigInfo;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setTimeElapsedTime(TimeElapsedTime timeElapsedTime) {
        this.timeElapsedTime = timeElapsedTime;
    }

    public void setTimezone(Timezone timezone) {
        this.timezone = timezone;
    }

    public void setUname(Uname uname) {
        this.uname = uname;
    }

    public void setUnidentifiedLogLines(List<String> unidentifiedLogLines) {
        this.unidentifiedLogLines = unidentifiedLogLines;
    }

    public void setVmInfo(VmInfo vmInfo) {
        this.vmInfo = vmInfo;
    }

    public void setVmOperation(VmOperation vmOperation) {
        this.vmOperation = vmOperation;
    }

    public void setVmState(VmState vmState) {
        this.vmState = vmState;
    }
}
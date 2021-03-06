/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                               *
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;

/**
 * <p>
 * JVM options. Null indicates the option is not explicitly set.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class JvmOptions {

    /**
     * ABRT option.
     * 
     * -ABRT %p
     */
    private String abrt;

    /**
     * The percentage weight to give to recent gc stats (vs historic) for ergonomic calculations. For example:
     * 
     * <pre>
     * -XX:AdaptiveSizePolicyWeight=90
     * </pre>
     */
    private String adaptiveSizePolicyWeight;

    /**
     * Runtime modules. For example:
     * 
     * <pre>
     * --add-modules=ALL-SYSTEM
     * </pre>
     */
    private String addModules;

    /**
     * JVM options java instrumentation.
     * 
     * For example:
     * 
     * -agentpath:/path/to/agent.so
     */
    private ArrayList<String> agentpath = new ArrayList<String>();

    /**
     * Option to enable/disable aggressive heap management for long running, memory intensive processes.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+AggressiveHeap
     * </pre>
     */
    private String aggressiveHeap;

    /**
     * Option to enable/disable various experimental performance optimizations that have varied over time. Disabled by
     * default and deprecated in JDK11.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+AggressiveOpts
     * </pre>
     */
    private String aggressiveOpts;

    /**
     * Option to enable/disable to touch all pages of the Java heap on startup to avoid the performance penalty at
     * runtime.
     * 
     * <pre>
     *-XX:+AlwaysPreTouch
     * </pre>
     */
    private String alwaysPreTouch;

    /**
     * The upper limit of Integers to cache. The lower limit is fixed at -128, and the upper limit defaults to 127. The
     * following option would cache Integers between -128 and 1000:
     * 
     * <pre>
     * -XX:AutoBoxCacheMax=1000
     * </pre>
     */
    private String autoBoxCacheMax;

    /**
     * Option to enable/disable background compilation of bytecode. For example:
     * 
     * <pre>
     * -XX:-BackgroundCompilation
     * </pre>
     */
    private String backgroundCompilation;

    /**
     * Option to disable background compilation of bytecode. For example:
     * 
     * <pre>
     * -Xbatch
     * </pre>
     */
    private boolean batch = false;

    /**
     * JVM options for bootstrap classes and resources.
     * 
     * For example:
     * 
     * -Xbootclasspath/p:/path/to/some.jar
     */
    private ArrayList<String> bootclasspath = new ArrayList<String>();

    /**
     * The number of compiler threads. For example:
     * 
     * <pre>
     * -XX:CICompilerCount=2
     * </pre>
     */
    private String ciCompilerCount;

    /**
     * The option to enable/disable class unloading during gc. For example:
     * 
     * <pre>
     * -XX:-ClassUnloading
     * </pre>
     */
    private String classUnloading;

    /**
     * Option to enable the client JIT compiler, a separate Java binary, optimized for fast startup and small footprint
     * (e.g. interactive GUI applications). Only available on 32-bit JDKS. Both the client and server binaries are
     * included in 32-bit JDKS. Only the client binary is included in 32-bit JREs. For example:
     * 
     * <pre>
     * -client
     * </pre>
     */
    private boolean client = false;

    /**
     * The option to enable/disable the CMS collector to collect perm/metaspace. For example:
     * 
     * <pre>
     * -XX:+CMSClassUnloadingEnabled
     * </pre>
     */
    private String cmsClassUnloadingEnabled;

    /**
     * The option to enable/disable the CMS collector running in incremental mode.
     * 
     * In incremental mode, the CMS collector does not hold the processor(s) for the entire long concurrent phases but
     * periodically stops them and yields the processor back to other threads in the application. It divides the work to
     * be done in concurrent phases into small chunks called duty cycles and schedules them between minor collections.
     * This is very useful for applications that need low pause times and are run on machines with a small number of
     * processors.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+CMSIncrementalMode
     * </pre>
     */
    private String cmsIncrementalMode;

    /**
     * The option for setting CMS initiating occupancy fraction, the tenured generation occupancy percentage that
     * triggers a concurrent collection. For example:
     * 
     * <pre>
     * -XX:CMSInitiatingOccupancyFraction=75
     * </pre>
     */
    private String cmsInitiatingOccupancyFraction;

    /**
     * Option to enable/disable CMS multi-threaded initial mark. For example:
     * 
     * <pre>
     * -XX:-CMSParallelInitialMarkEnabled
     * </pre>
     */
    private String cmsParallelInitialMarkEnabled;

    /**
     * Option to enable/disable CMS multi-threaded initial remark. For example:
     * 
     * <pre>
     * -XX:-CMSParallelRemarkEnabled
     * </pre>
     */
    private String cmsParallelRemarkEnabled;

    /**
     * The option to enable compilation of bytecode on first invocation. For example:
     * 
     * <pre>
     * -Xcomp
     * </pre>
     */
    private boolean comp = false;

    /**
     * The option for specifying a command for the Just in Time (JIT) compiler to execute on a method . For example:
     * 
     * <pre>
     * -XX:CompileCommand=exclude,com/example/MyClass
     * </pre>
     */
    private String compileCommand;

    /**
     * The option for setting the virtual (reserved) size of the compressed class space (a single area). For example:
     * 
     * <pre>
     * -XX:CompressedClassSpaceSize=768m
     * </pre>
     */
    private String compressedClassSpaceSize;

    /**
     * The number of concurrent GC threads. For example:
     * 
     * <pre>
     * -XX:ConcGCThreads=18
     * </pre>
     * 
     * various experimental performance optimizations that have varied over time. Disabled by default and deprecated in
     * JDK11.
     */
    private String concGcThreads;

    /**
     * The option for specifying 64-bit.
     * 
     * <pre>
     * -d64
     * </pre>
     */
    private boolean d64 = false;

    /**
     * The option to enable/disable the compiler generating metadata for code not at safe points to improve the accuracy
     * of Java Flight Recorder (JFR) Method Profiler.
     * 
     * <pre>
     * -XX:+DebugNonSafepoints
     * </pre>
     */
    private String debugNonSafepoints;

    /**
     * The option to enable/disable explicit garbage collection. For example:
     * 
     * <pre>
     * -XX:+DisableExplicitGC
     * </pre>
     */
    private String disableExplicitGc;

    /**
     * Option to enable/disable Just In Time (JIT) compiler optimization for objects created and referenced by a single
     * thread within the scope of a method:
     * 
     * 1) Objects are not created, and their fields are treated as local variables and allocated on the method stack or
     * in cpu registers. This effectively moves the allocation from the heap to the stack, which is much faster.
     * 
     * 2) Locking and memory synchronization is removed, reducing overhead.
     * 
     * Enabled by default in JDK 1.6+.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+DoEscapeAnalysis
     * </pre>
     */
    private String doEscapeAnalysis;

    /**
     * The option to enable/disable the compiler optimization to eliminate locks if the monitor is not reachable from
     * other threads. Enabled by default in JDK 1.8+.
     * 
     * <pre>
     * -XX:+EliminateLocks
     * </pre>
     */
    private String eliminateLocks;

    /**
     * The option to specify the location where a fatal error log will be written. For example:
     * 
     * <pre>
     *  -XX:ErrorFile=/mydir/hs_err_pid%p.log
     * </pre>
     */
    private String errorFile;

    /**
     * Option to enable/disable the JVM process exiting on OutOfMemoryError. For example:
     * 
     * <pre>
     * -XX:+ExitOnOutOfMemoryError
     * </pre>
     */
    private String exitOnOutOfMemoryError;

    /**
     * The option to enable/disable explicit garbage collection to be handled concurrently by the CMS and G1 collectors.
     * For example:
     * 
     * <pre>
     * -XX:+ExplicitGCInvokesConcurrent
     * </pre>
     */
    private String explicitGCInvokesConcurrent;

    /**
     * The option to enable/disable explicit garbage collection to be handled concurrently by the CMS and G1 collectors
     * and classes unloaded. For example:
     * 
     * <pre>
     * -XX:-ExplicitGCInvokesConcurrentAndUnloadsClasses
     * </pre>
     */
    private String explicitGCInvokesConcurrentAndUnloadsClasses;

    /**
     * Option to enable/disable outputting additional information in the fatal error logs. For example:
     * 
     * <pre>
     * -XX:+ExtensiveErrorReports
     * </pre>
     */
    private String extensiveErrorReports;

    /**
     * The option for starting Java Flight Recorder (JFR). For example:
     * 
     * <pre>
     * -XX:FlightRecorderOptions=stackdepth=256
     * </pre>
     */
    private String flightRecorderOptions;

    /**
     * The option to set the size of the G1 region. For example:
     * 
     * <pre>
     * -XX:G1HeapRegionSize=4m
     * </pre>
     */
    private String g1HeapRegionSize;

    /**
     * The option for setting the G1 heap waste percentage. For example:
     * 
     * <pre>
     * -XX:G1HeapWastePercent=5
     * </pre>
     */
    private String g1HeapWastePercent;

    /**
     * Experimental option (requires <code>-XX:+UnlockExperimentalVMOptions</code>) that sets the percentage of the heap
     * to use as the maximum new generation size (default 60%). Replaces <code>-XX:DefaultMaxNewGenPercent</code>.
     * 
     * <pre>
     * -XX:G1MaxNewSizePercent=30
     * </pre>
     */
    private String g1MaxNewSizePercent;

    /**
     * The option for setting the occupancy threshold for a region to be considered as a candidate region for a
     * G1_CLEANUP collection. For example:
     * 
     * <pre>
     * -XX:G1MixedGCLiveThresholdPercent=85
     * </pre>
     */
    private String g1MixedGCLiveThresholdPercent;

    /**
     * Option to enable/disable output of summarized remembered set processing info. For example:
     * 
     * <pre>
     * -XX:+G1SummarizeRSetStats
     * </pre>
     */
    private String g1SummarizeRSetStats;

    /**
     * The option for setting the # of GCs to output update buffer processing info (0 = disabled). For example:
     * 
     * <pre>
     * -XX:G1SummarizeRSetStatsPeriod=1
     * </pre>
     */
    private String g1SummarizeRSetStatsPeriod;

    /**
     * Size of gc log file that triggers rotation. For example:
     * 
     * <pre>
     * -XX:GCLogFileSize=3M
     * </pre>
     */
    private String gcLogFileSize;

    /**
     * The ratio of GC time to application time. For example:
     * 
     * <pre>
     * -XX:GCTimeRatio=4
     * </pre>
     */
    private String gcTimeRatio;

    /**
     * Diagnostic option (-XX:+UnlockDiagnosticVMOptions) to set a minimal safepoint interval (ms). For example:
     * 
     * <pre>
     * -XX:GuaranteedSafepointInterval=90000000
     * </pre>
     */
    private String guaranteedSafepointInterval;

    /**
     * The option to write out a heap dump when OutOfMemoryError. For example:
     * 
     * <pre>
     * -XX:+HeapDumpOnOutOfMemoryError
     * </pre>
     */
    private String heapDumpOnOutOfMemoryError;

    /**
     * The option to specify the location where a heap dump will be written on OutOfMemoryError. For example:
     * 
     * <pre>
     *  -XX:HeapDumpPath=/mydir/
     * </pre>
     */
    private String heapDumpPath;

    /**
     * Initial heap space size. Specified with the <code>-Xms</code> or <code>-XX:InitialHeapSize</code> option. For
     * example:
     * 
     * <pre>
     * -Xms1024m
     * -XX:InitialHeapSize=257839744
     * </pre>
     */
    private String initialHeapSize;

    /**
     * The heap occupancy threshold that G1 marking starts (default 45%). Lower it to start marking earlier to avoid
     * marking not finishing before heap fills up (analogous to CMS concurrent mode failure).
     * 
     * For example:
     * 
     * <pre>
     * -XX:InitiatingHeapOccupancyPercent=40
     * </pre>
     */
    private String initiatingHeapOccupancyPercent;

    /**
     * JVM options native libraries.
     * 
     * For example:
     * 
     * -javaagent:/path/to/appdynamics/javaagent.jar
     */
    private ArrayList<String> javaagent = new ArrayList<String>();

    /**
     * JPDA socket transport used for debugging. For example:
     * 
     * -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n
     */
    private String jpdaSocketTransport;

    /**
     * The option for setting the virtual (reserved) size of the compressed class space (a single area). Only has
     * meaning on Solaris. Other OS like Linux use the page size the kernel is set to support
     * (<code>Hugepagesize</code>). On Windows it cannot be set (like in Linux) and is fixed at 2MB.
     * 
     * For example:
     * 
     * <pre>
     * -XX:LargePageSizeInBytes=4m
     * </pre>
     */
    private String largePageSizeInBytes;

    /**
     * Option to specify gc logging options in JDK11+. For example:
     * 
     * <p>
     * 1) Single option:
     * </p>
     * 
     * <pre>
     * -Xlog:gc*,gc+age=trace,safepoint:file=/path/to/gc.log:utctime,pid,tags:filecount=4,filesize=64m
     * </pre>
     * 
     * <p>
     * 2) Multiple options:
     * </p>
     * 
     * <pre>
     * -Xlog:gc*=debug:file=/path/to/gc-%t.log:time,pid,tid,level,tags:filesize=1G 
     * -Xlog:all=info,exceptions=warning,gc*=off:file=/path/to/vm-%t.log:time,pid,tid,level,tags:filesize=100M
     * </pre>
     */
    private ArrayList<String> log = new ArrayList<String>();

    /**
     * The option to specify the location where safepoint logging will be written. For example:
     * 
     * <pre>
     *  -XX:LogFile=/path/to/vm.log
     * </pre>
     */
    private String logFile;

    /**
     * Option to specify gc log location in JDK8. For example:
     * 
     * <pre>
     * -Xloggc:/path/to/EAP-7.1.0/standalone/log/gc.log
     * </pre>
     */
    private String logGc;

    /**
     * Diagnostic option (-XX:+UnlockDiagnosticVMOptions) to enable/disable vm logging for safepoint analysis. For
     * example:
     * 
     * <pre>
     * -XX:+LogVMOutput
     * </pre>
     */
    private String logVmOutput;

    /**
     * Option to enable/disable JMX. For example:
     * 
     * <pre>
     * -XX:+ManagementServer.
     * </pre>
     */
    private String managementServer;

    /**
     * Maximum direct memory. Attempting to allocate direct memory that would cause the limit to be exceeded causes a
     * full GC to initiate reference processing and release of unreferenced buffers.
     * 
     * For example:
     * 
     * <pre>
     * -XX:MaxDirectMemorySize=8g
     * </pre>
     */
    private String maxDirectMemorySize;

    /**
     * The option for setting the maximum gc pause time ergonomic option. For example:
     * 
     * <pre>
     * -XX:MaxGCPauseMillis=500
     * </pre>
     */
    private String maxGcPauseMillis;

    /**
     * The maximum percentage of free space to avoid shrinking the heap size. For example:
     * 
     * <pre>
     * -XX:MaxHeapFreeRatio=20
     * </pre>
     */
    private String maxHeapFreeRatio;

    /**
     * Maximum heap space. Specified with the <code>-Xmx</code> or <code>-XX:MaxHeapSize</code> option. For example:
     * 
     * <pre>
     * -Xmx1024m
     * -XX:MaxHeapSize=1234567890
     * </pre>
     */
    private String maxHeapSize;

    /**
     * The option for setting the number of lines in stack trace output. For example:
     * 
     * <pre>
     * jvmOptions
     * -XX:MaxJavaStackTraceDepth=50000
     * </pre>
     */
    private String maxJavaStackTraceDepth;

    /**
     * Maximum committed metaspace (class metadata + compressed class space). For example:
     * 
     * <pre>
     * -XX:MaxMetaspaceSize=2048m
     * </pre>
     */
    private String maxMetaspaceSize;

    /**
     * Maximum new generation size. The following forumula is used to determine new generation size:
     * 
     * min(MaxNewSize, max(NewSize, heap/(NewRatio+1)))
     * 
     * For example:
     * 
     * <pre>
     * -XX:MaxNewSize=512m
     * </pre>
     */
    private String maxNewSize;

    /**
     * Maximum permanent generation size. In JDK8 the permanent generation space was replaced by the metaspace, so this
     * option is being ignored. For example:
     * 
     * <pre>
     * -XX:MaxPermSize=256mvarious experimental performance optimizations that have varied over time. Disabled by
     * default and deprecated in JDK11.
     * </pre>
     */
    private String maxPermSize;

    /**
     * The option for setting the maximum tenuring threshold option (the number of times objects surviving a young
     * collection are copied to a survivor space). For example:
     * 
     * <pre>
     * -XX:MaxTenuringThreshold=0
     * </pre>
     */
    private String maxTenuringThreshold;

    /**
     * The allocated class metadata space size that will trigger a garbage collection when it is exceeded for the first
     * time. The JVM may choose a new threshold after the initial threshold is exceeded. The default size is platform
     * dependent. For example:
     * 
     * <pre>
     * -XX:MetaspaceSize=1024M
     * </pre>
     */
    private String metaspaceSize;

    /**
     * The minimum percentage of free space to avoid expanding the heap size. For example:
     * 
     * <pre>
     * -XX:MinHeapFreeRatio=10
     * </pre>
     */
    private String minHeapFreeRatio;

    /**
     * Initial young generation size. Specified with either the <code>-XX:NewSize</code> or <code>-Xmn</code> option.
     * The following forumula is used to determine new generation size:
     * 
     * min(MaxNewSize, max(NewSize, heap/(NewRatio+1)))
     * 
     * For example:
     * 
     * <pre>
     * -XX:NewSize=1g
     * </pre>
     * 
     * <pre>
     * -Xmn1g
     * </pre>
     */
    private String newSize;

    /**
     * Option to disable the garbage collection of classes. For example:
     * 
     * <pre>
     * -Xnoclassgc
     * </pre>
     */
    private boolean noclassgc = false;

    /**
     * Option to specify the number of gc log files to keep when rotation is enabled. For example:
     * 
     * <pre>
     * -XX:NumberOfGCLogFiles=5
     * </pre>
     */
    private String numberOfGcLogFiles;

    /**
     * Option to enable/disable the use of preallocated exceptions, an optimization when an exception is thrown many
     * times the JVM stops including the stack trace. For example:
     * 
     * <pre>
     * -XX:-OmitStackTraceInFastThrow
     * </pre>
     */
    private String omitStackTraceInFastThrow;

    /**
     * The option to run a command or script when an irrecoverable error happens. For example:
     * 
     * <pre>
     * -XX:OnError=gcore %p
     * </pre>
     */
    private String onError;

    /**
     * The option to run a command or script when OutOfMemoryError happens. For example:
     * 
     * <pre>
     * -XX:OnOutOfMemoryError="pmap %p"
     * </pre>
     */
    private String onOutOfMemoryError;

    /**
     * Option to enable/disable optimizing String concatenation operations. For example:
     * 
     * <pre>
     * s
     * -XX:-OptimizeStringConcat
     * </pre>
     */
    private String optimizeStringConcat;

    /**
     * The number of parallel gc threads. For example:
     * 
     * <pre>
     * -XX:ParallelGCThreads=4
     * </pre>
     */
    private String parallelGcThreads;

    /**
     * Option to enable/disable the JVM outputting statistics to the hsperfdata file. For example:
     * 
     * <pre>
     * -XX:+PerfDisableSharedMem
     * </pre>
     */
    private String perfDisableSharedMem;

    /**
     * initial permanent generation size. In JDK8 the permanent generation space was replaced by the metaspace, so this
     * option is being ignored. For example:
     * 
     * <pre>
     * -XX:PermSize=128m
     * </pre>
     */
    private String permSize;

    /**
     * The option enable/disable Adaptive Resize Policy output. For example:
     * 
     * <pre>
     * -XX:+PrintAdaptiveSizePolicy
     * </pre>
     */
    private String printAdaptiveSizePolicy;

    /**
     * The option to enable/disable outputting a class histogram in the gc logging when a thread dump is taken. For
     * example:
     * 
     * <pre>
     * -XX:+PrintClassHistogram
     * </pre>
     */
    private String printClassHistogram;

    /**
     * The option to enable/disable outputting a class histogram in the gc logging after every full gc. For example:
     * 
     * <pre>
     * -XX:+PrintClassHistogramAfterFullGC
     * </pre>
     */
    private String printClassHistogramAfterFullGc;

    /**
     * The option to enable/disable outputting a class histogram in the gc logging before every full gc. For example:
     * 
     * <pre>
     * -XX:+PrintClassHistogramBeforeFullGC
     * </pre>
     */
    private String printClassHistogramBeforeFullGc;

    /**
     * The option to enable/disable outputting every JVM option and value to standard out on JVM startup. For example:
     * 
     * <pre>
     * -XX:+PrintFlagsFinal
     * </pre>
     */
    private String printFlagsFinal;

    /**
     * Option to enable printing CMS Free List Space statistics in gc logging. For example:
     * 
     * <pre>
     * -XX:PrintFLSStatistics=1
     * </pre>
     */
    private String printFLSStatistics;

    /**
     * Option to enable/disable displaying detailed information about each gc event. Equivalent to
     * <code>-verbose:gc</code>. For example:
     * 
     * <pre>
     * -XX:+PrintGC
     * </pre>
     */
    private String printGc;

    /**
     * The option to enable/disable printing application concurrent time in the gc logging. For example:
     * 
     * <pre>
     * -XX:+PrintGCApplicationConcurrentTime
     * </pre>
     */
    private String printGcApplicationConcurrentTime;

    /**
     * Option to enable/disable outputting application stopped time in gc logging. Deprecated in JDK9. For example:
     * 
     * <pre>
     * -XX:+PrintGCApplicationStoppedTime
     * </pre>
     */
    private String printGcApplicationStoppedTime;

    /**
     * Option to enable/disable gc logging datestamps. Deprecated in JDK9. For example:
     * 
     * <pre>
     * -XX:+PrintGCDateStamps
     * </pre>
     */
    private String printGcDateStamps;

    /**
     * Option to enable/disable printing gc details. Deprecated in JDK9. For example:
     * 
     * <pre>
     * -XX:+PrintGCDetails
     * </pre>
     */
    private String printGcDetails;

    /**
     * Option to enable/disable printing task timestamp for each GC thread. For example:
     * 
     * <pre>
     * -XX:+PrintGCTaskTimeStamps
     * </pre>
     */
    private String printGcTaskTimeStamps;

    /**
     * Option to enable/disable printing gc timestamps.
     * 
     * <pre>
     * -XX:+PrintGCTimeStamps
     * </pre>
     */
    private String printGcTimeStamps;

    /**
     * Option to enable/disable printing additional heap information in gc logging.
     * 
     * <pre>
     * -XX:+PrintHeapAtGC
     * </pre>
     */
    private String printHeapAtGc;

    /**
     * Option to enable/disable printing promotion failure information. For example:
     * 
     * <pre>
     * -XX:+PrintPromotionFailure
     * </pre>
     */
    private String printPromotionFailure;

    /**
     * The option to enable/disable outputting times for reference processing (weak, soft, JNI) in gc logging. For
     * example:
     * 
     * <pre>
     * -XX:+PrintReferenceGC
     * </pre>
     */
    private String printReferenceGc;

    /**
     * Diagnostic option (-XX:+UnlockDiagnosticVMOptions) to enable/disable printing safepoint information. For example:
     * 
     * <pre>
     *-XX:+PrintSafepointStatistics
     * </pre>
     */
    private String printSafepointStatistics;

    /**
     * The option to enable/disable outputting string deduplication statistics in gc logging. For example:
     * 
     * <pre>
     * -XX:+PrintStringDeduplicationStatistics
     * </pre>
     */
    private String printStringDeduplicationStatistics;

    /**
     * Option to enable/disable printing tenuring information in gc logging.
     * 
     * <pre>
     * -XX:+PrintTenuringDistribution
     * </pre>
     */
    private String printTenuringDistribution;

    /**
     * Code cache size (default 240m). For example:
     * 
     * <pre>
     * -XX:ReservedCodeCacheSize=256m
     * </pre>
     */
    private String reservedCodeCacheSize;

    /**
     * Option to enable the server JIT compiler, a separate Java binary, optimized for overall performance. The only JIT
     * compiler available on 64-bit. For example:
     * 
     * <pre>
     * -server
     * </pre>
     */
    private boolean server = false;

    /**
     * Option to define Shenandoah heuristics. Heuristics tell Shenandoah when to start the GC cycle and what regions to
     * use for evacuation. Some heuristics accept additional configuration options to tailor GC to specific use cases.
     * 
     * For example:
     * 
     * <p>
     * 1) adaptive (default)
     * </p>
     * 
     * <pre>
     * -XX:ShenandoahGCHeuristics=adaptive
     * 
     * -XX:ShenandoahInitFreeThreshold=#
     * -XX:ShenandoahMinFreeThreshold=# 
     * -XX:ShenandoahAllocSpikeFactor=# 
     * -XX:ShenandoahGarbageThreshold=#
     * </pre>
     * 
     * <p>
     * 2) static
     * </p>
     * 
     * <pre>
     * -XX:ShenandoahGCHeuristics=static
     * 
     * -XX:ShenandoahInitFreeThreshold=# 
     * -XX:ShenandoahGarbageThreshold=#
     * </pre>
     * 
     * <p>
     * 3) compact
     * </p>
     * 
     * <pre>
     * -XX:ShenandoahGCHeuristics=compact
     * 
     * -XX:ConcGCThreads=#
     * -XX:ShenandoahAllocationThreshold=#
     * </pre>
     * 
     * <p>
     * 4) aggressive
     * </p>
     * 
     * <pre>
     * -XX:ShenandoahGCHeuristics=aggressive
     * </pre>
     */
    private String shenandoahGcHeuristics;

    /**
     * The minimum percentage of free space at which heuristics triggers the GC unconditionally. For example:
     * 
     * <pre>
     * -XX:ShenandoahMinFreeThreshold=10
     * </pre>
     */
    private String shenandoahMinFreeThreshold;

    /**
     * The option for setting the size of the eden space compared to ONE survivor space. For example:
     * 
     * <pre>
     * -XX:SurvivorRatio=6
     * </pre>
     */
    private String survivorRatio;

    /**
     * JVM options used to define system properties.
     * 
     * For example:
     * 
     * -Dcatalina.base=/path/to/tomcat
     */
    private ArrayList<String> systemProperties = new ArrayList<String>();

    /**
     * The option for setting the percentage of the survivor space allowed to be occupied. For example:
     * 
     * <pre>
     * -XX:TargetSurvivorRatio=90
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    private String targetSurvivorRatio;

    /**
     * Thread stack size. Specified with either <code>-Xss</code> or <code>-ss</code> with optional units [kKmMgG] or
     * <code>-XX:ThreadStackSize</code> with an integer representing kilobytes. For example:
     * 
     * <pre>
     * -Xss256k
     * </pre>
     * 
     * <pre>
     * -ss512k
     * </pre>
     * 
     * <pre>
     * -XX:ThreadStackSize=128
     * </pre>
     * 
     * The <code>-Xss</code> option does not work on Solaris, only <code>-XX:ThreadStackSize</code>.
     */
    private String threadStackSize;

    /**
     * Option to enable/disable tiered compilation. For example:
     * 
     * <pre>
     * -XX:+TieredCompilation
     * </pre>
     */
    private String tieredCompilation;

    /**
     * Option to enable/disable class loading/unloading information in gc log. For example:
     * 
     * <pre>
     * -XX:-TraceClassUnloading
     * </pre>
     */
    private String traceClassUnloading;

    /**
     * Undefined JVM options.
     */
    private ArrayList<String> undefined = new ArrayList<String>();

    /**
     * Option to enable/disable diagnostic options. For example:
     * 
     * <pre>
     * -XX:+UnlockDiagnosticVMOptions
     * </pre>
     */
    private String unlockDiagnosticVmOptions;

    /**
     * Option to enable/disable experimental options. For example:
     * 
     * <pre>
     * -XX:+UnlockExperimentalVMOptions
     * </pre>
     */
    private String unlockExperimentalVmOptions;

    /**
     * Option to enable/disable ergonomic option that resizes generations to meet pause and throughput goals and
     * minimize footprint. For example:
     * 
     * <pre>
     * -XX:+UseAdaptiveSizePolicy
     * </pre>
     */
    private String useAdaptiveSizePolicy;

    /**
     * Option to enable/disable biased locking. For example:
     * 
     * <pre>
     * -XX:-UseBiasedLocking
     * </pre>
     */
    private String useBiasedLocking;

    /**
     * The option to enable/disabled cgroup memory limit for heap sizing.
     * 
     * <pre>
     * -XX:+UseCGroupMemoryLimitForHeap
     * </pre>
     */
    private String useCGroupMemoryLimitForHeap;

    /**
     * The option for disabling heuristics (calculating anticipated promotions) and use only the occupancy fraction to
     * determine when to trigger a CMS cycle. When an application has large variances in object allocation and young
     * generation promotion rates, the CMS collector is not able to accurately predict when to start the CMS cycle. For
     * example:
     * 
     * <pre>
     * -XX:+UseCMSInitiatingOccupancyOnlyvarious experimental performance optimizations that have varied over time. 
     * Disabled by default and deprecated in JDK11.
     * </pre>
     */
    private String useCmsInitiatingOccupancyOnly;

    /**
     * Option to enable/disable compressed class pointers. For example:
     * 
     * <pre>
     * -XX:-UseCompressedClassPointers
     * </pre>
     */
    private String useCompressedClassPointers;

    /**
     * Option to enable/disable compressed object pointers. For example:
     * 
     * <pre>
     * -XX:-UseCompressedOops
     * </pre>
     */
    private String useCompressedOops;

    /**
     * Option to enable/disable the CMS old collector. For example:
     * 
     * -XX:+UseConcMarkSweepGC
     */
    private String useConcMarkSweepGc;

    /**
     * Option to enable/disable using optimized versions of Get<Primitive>Field. Removed in JDK11.
     * 
     * For example:
     * 
     * <pre>
     * -XX:-UseFastAccessorMethods
     * </pre>
     */
    private String useFastAccessorMethods;

    /**
     * The option enable/disable fast unordered timestamps in gc logging.
     * 
     * <pre>
     * -XX:+UseFastUnorderedTimeStamps
     * </pre>
     */
    private String useFastUnorderedTimeStamps;

    /**
     * The option to enable/disable the G1 collector. For example:
     * 
     * <pre>
     * -XX:+UseG1GC
     * </pre>
     */
    private String useG1Gc;

    /**
     * Option to enable/disable gc log file rotation. For example:
     * 
     * -XX:+UseGCLogFileRotation
     */
    private String useGcLogFileRotation;

    /**
     * Linux option equivalent to -XX:+UseLargePages to enable/disable the preallocation of all large pages on JVM
     * startup, preventing the JVM from growing/shrinking large pages memory areas. For example:
     * 
     * <pre>
     * -XX:+UseHugeTLBFS
     * </pre>
     */
    private String useHugeTLBFS;

    /**
     * Option to enable/disable large pages. For example:
     * 
     * -XX:+UseLargePages
     */
    private String useLargePages;

    /**
     * The option to enable/disable a strict memory barrier. For example:
     * 
     * <pre>
     * -XX:+UseMembar
     * </pre>
     */
    private String useMembar;

    /**
     * Option to enable/disable dedicated memory space per processor. For example:
     * 
     * <pre>
     *-XX:+UseNUMA
     * </pre>
     */
    private String useNUMA;

    /**
     * Option to enable/disable the parallel scavenge young garbage collector. For example:
     * 
     * -XX:+UseParallelGC
     */
    private String useParallelGc;

    /**
     * Option to enable/disable the parallel multi-threaded old garbage collector. For example:
     * 
     * -XX:+UseParallelOldGC
     */
    private String useParallelOldGc;

    /**
     * The option to enable/disable the CMS young collector. For example:
     * 
     * <pre>
     * -XX:+UseParNewGC
     * </pre>
     */
    private String useParNewGc;

    /**
     * The option to enable/disable outputting performance data to disk (/tmp/hsperfdata*) and via JMX. For example:
     * 
     * <pre>
     * -XX:-UsePerfData
     * </pre>
     */
    private String usePerfData;

    /**
     * Option to enable/disable the Serial garbage collector. For example:
     * 
     * -XX:+UseSerialGC
     */
    private String useSerialGc;

    /**
     * Option to enable/disable the Shenandoah garbage collector. For example:
     * 
     * -XX:+UseShenandoahGC
     */
    private String useShenandoahGc;

    /**
     * The option to enable/disable string deduplication to minimize string footprint. The performance impact is minimal
     * (some cpu cycles to run the concurrent deduplication process.
     * 
     * <pre>
     * -XX:+UseStringDeduplication
     * </pre>
     */
    private String useStringDeduplication;

    /**
     * Option to enable/disable making Solaris interruptible blocking I/O noninterruptible as they are on Linux and
     * Windows. Deprecated in JDK8 and removed in JDK11.
     */
    private String useVmInterruptibleIo;

    /**
     * Option to enable logging (to standard out) class loading information.
     * 
     * -verbose:class
     */
    private boolean verboseClass = false;

    /**
     * Option to enable displaying detailed information about each gc event.
     * 
     * -verbose:gc
     */
    private boolean verboseGc = false;

    /**
     * Option to specify class verification during class loading.
     * 
     * For example:
     * 
     * <pre>
     * -Xverify
     * -Xverify:all
     * -Xverify:none
     * -Xverify:remote
     * </pre>
     */
    private String verify;

    /**
     * Option to disable just in time (JIT) compilation. For example:
     * 
     * <pre>
     * -Xint
     * </pre>
     */
    private boolean xInt = false;

    /**
     * Convert JVM argument string to JVM options.
     * 
     * @param jvmArgs
     *            The JVM arguments.debugNonSafepoints
     */
    public JvmOptions(String jvmArgs) {
        super();
        if (jvmArgs != null) {
            String[] options = jvmArgs.split("(?<!^)(?= -)");
            for (int i = 0; i < options.length; i++) {
                String option = options[i].trim();
                if (option.matches("^-ABRT.+$")) {
                    abrt = option;
                } else if (option.matches("^-agentlib:jdwp=transport=dt_socket.+$")) {
                    jpdaSocketTransport = option;
                } else if (option.matches("^-agentpath:.+$")) {
                    agentpath.add(option);
                } else if (option.matches("^--add-modules=.+$")) {
                    addModules = option;
                } else if (option.matches("^-client$")) {
                    client = true;
                } else if (option.matches("^-XX:AdaptiveSizePolicyWeight=\\d{1,3}$")) {
                    adaptiveSizePolicyWeight = option;
                } else if (option.matches("^-XX:[\\-+]AggressiveHeap$")) {
                    aggressiveHeap = option;
                } else if (option.matches("^-XX:[\\-+]AggressiveOpts$")) {
                    aggressiveOpts = option;
                } else if (option.matches("^-XX:[\\-+]AlwaysPreTouch$")) {
                    alwaysPreTouch = option;
                } else if (option.matches("^-XX:AutoBoxCacheMax=\\d{1,10}$")) {
                    autoBoxCacheMax = option;
                } else if (option.matches("^-XX:[\\-+]BackgroundCompilation$")) {
                    backgroundCompilation = option;
                } else if (option.matches("^-Xbatch$")) {
                    batch = true;
                } else if (option.matches("^-Xbootclasspath.+$")) {
                    bootclasspath.add(option);
                } else if (option.matches("^-XX:CICompilerCount=\\d{1,3}$")) {
                    ciCompilerCount = option;
                } else if (option.matches("^-XX:[\\-+]ClassUnloading$")) {
                    classUnloading = option;
                } else if (option.matches("^-XX:[\\-+]CMSClassUnloadingEnabled$")) {
                    cmsClassUnloadingEnabled = option;
                } else if (option.matches("^-XX:[\\-+]CMSIncrementalMode$")) {
                    cmsIncrementalMode = option;
                } else if (option.matches("^-XX:CMSInitiatingOccupancyFraction=\\d{1,3}$")) {
                    cmsInitiatingOccupancyFraction = option;
                } else if (option.matches("^-XX:[\\-+]CMSParallelInitialMarkEnabled$")) {
                    cmsParallelInitialMarkEnabled = option;
                } else if (option.matches("^-XX:[\\-+]CMSParallelRemarkEnabled$")) {
                    cmsParallelRemarkEnabled = option;
                } else if (option.matches("^-Xcomp$")) {
                    comp = true;
                } else if (option.matches("^-XX:CompileCommand=.+$")) {
                    compileCommand = option;
                } else if (option.matches("^-XX:CompressedClassSpaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    compressedClassSpaceSize = option;
                } else if (option.matches("^-XX:ConcGCThreads=\\d{1,3}$")) {
                    concGcThreads = option;
                } else if (option.matches("^-D.+$")) {
                    systemProperties.add(option);
                } else if (option.matches("^-d64$")) {
                    d64 = true;
                } else if (option.matches("^-XX:[\\-+]DebugNonSafepoints$")) {
                    debugNonSafepoints = option;
                } else if (option.matches("^-XX:[\\-+]DisableExplicitGC$")) {
                    disableExplicitGc = option;
                } else if (option.matches("^-XX:[\\-+]DoEscapeAnalysis$")) {
                    doEscapeAnalysis = option;
                } else if (option.matches("^-XX:[\\-+]EliminateLocks$")) {
                    eliminateLocks = option;
                } else if (option.matches("^-XX:ErrorFile=\\S+$")) {
                    errorFile = option;
                } else if (option.matches("^-XX:[\\-+]ExitOnOutOfMemoryError$")) {
                    exitOnOutOfMemoryError = option;
                } else if (option.matches("^-XX:[\\-+]ExplicitGCInvokesConcurrent$")) {
                    explicitGCInvokesConcurrent = option;
                } else if (option.matches("^-XX:[\\-+]ExplicitGCInvokesConcurrentAndUnloadsClasses$")) {
                    explicitGCInvokesConcurrentAndUnloadsClasses = option;
                } else if (option.matches("^-XX:[\\-+]ExtensiveErrorReports$")) {
                    extensiveErrorReports = option;
                } else if (option.matches("^-XX:FlightRecorderOptions=.+$")) {
                    flightRecorderOptions = option;
                } else if (option.matches("^-XX:G1HeapRegionSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    g1HeapRegionSize = option;
                } else if (option.matches("^-XX:G1MaxNewSizePercent=\\d{1,3}$")) {
                    g1MaxNewSizePercent = option;
                } else if (option.matches("^-XX:[\\-+]G1SummarizeRSetStats$")) {
                    g1SummarizeRSetStats = option;
                } else if (option.matches("^-XX:G1SummarizeRSetStatsPeriod=\\d$")) {
                    g1SummarizeRSetStatsPeriod = option;
                } else if (option.matches("^-XX:G1HeapWastePercent=\\d{1,3}$")) {
                    g1HeapWastePercent = option;
                } else if (option.matches("^-XX:G1MixedGCLiveThresholdPercent=\\d{1,3}$")) {
                    g1MixedGCLiveThresholdPercent = option;
                } else if (option.matches("^-XX:GuaranteedSafepointInterval=\\d{1,10}$")) {
                    guaranteedSafepointInterval = option;
                } else if (option.matches("^-XX:[\\-+]HeapDumpOnOutOfMemoryError$")) {
                    heapDumpOnOutOfMemoryError = option;
                } else if (option.matches("^-XX:HeapDumpPath=\\S+$")) {
                    heapDumpPath = option;
                } else if (option.matches("^-X(ms|X:InitialHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    initialHeapSize = option;
                } else if (option.matches("^-XX:InitiatingHeapOccupancyPercent=\\d{1,3}$")) {
                    initiatingHeapOccupancyPercent = option;
                } else if (option.matches("^-Xint$")) {
                    xInt = true;
                } else if (option.matches("^-javaagent:.+$")) {
                    javaagent.add(option);
                } else if (option.matches("^-XX:LargePageSizeInBytes=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    largePageSizeInBytes = option;
                } else if (option.matches("^-Xlog:.+$")) {
                    log.add(option);
                } else if (option.matches("^-XX:LogFile=\\S+$")) {
                    logFile = option;
                } else if (option.matches("^-Xloggc:.+$")) {
                    logGc = option;
                } else if (option.matches("^-XX:MaxGCPauseMillis=\\d{1,}$")) {
                    maxGcPauseMillis = option;
                } else if (option.matches("^-XX:MaxJavaStackTraceDepth=\\d{1,}$")) {
                    maxJavaStackTraceDepth = option;
                } else if (option.matches("^-XX:MaxNewSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxNewSize = option;
                } else if (option.matches("^-XX:MetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    metaspaceSize = option;
                } else if (option.matches("^-X(mx|X:MaxHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxHeapSize = option;
                } else if (option.matches("^-XX:GCLogFileSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    gcLogFileSize = option;
                } else if (option.matches("^-XX:GCTimeRatio=\\d{1,3}$")) {
                    gcTimeRatio = option;
                } else if (option.matches("^-XX:[\\-+]LogVMOutput$")) {
                    logVmOutput = option;
                } else if (option.matches("^-XX:[\\-+]ManagementServer$")) {
                    managementServer = option;
                } else if (option.matches("^-XX:MaxDirectMemorySize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxDirectMemorySize = option;
                } else if (option.matches("^-XX:MaxHeapFreeRatio=\\d{1,3}$")) {
                    maxHeapFreeRatio = option;
                } else if (option.matches("^-XX:MaxMetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxMetaspaceSize = option;
                } else if (option.matches("^-XX:MaxPermSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxPermSize = option;
                } else if (option.matches("^-XX:MaxTenuringThreshold=\\d{1,}$")) {
                    maxTenuringThreshold = option;
                } else if (option.matches("^-XX:MinHeapFreeRatio=\\d{1,3}$")) {
                    minHeapFreeRatio = option;
                } else if (option.matches("^-X(mn|X:NewSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    newSize = option;
                } else if (option.matches("^-Xnoclassgc$")) {
                    noclassgc = true;
                } else if (option.matches("^-XX:NumberOfGCLogFiles=\\d{1,}$")) {
                    numberOfGcLogFiles = option;
                } else if (option.matches("^-XX:[\\-+]OmitStackTraceInFastThrow$")) {
                    omitStackTraceInFastThrow = option;
                } else if (option.matches("^-XX:OnError=.+$")) {
                    onError = option;
                } else if (option.matches("^-XX:OnOutOfMemoryError=.+$")) {
                    onOutOfMemoryError = option;
                } else if (option.matches("^-XX:[\\-+]OptimizeStringConcat$")) {
                    optimizeStringConcat = option;
                } else if (option.matches("^-XX:ParallelGCThreads=\\d{1,3}$")) {
                    parallelGcThreads = option;
                } else if (option.matches("^-XX:[\\-+]PerfDisableSharedMem$")) {
                    perfDisableSharedMem = option;
                } else if (option.matches("^-XX:PermSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    permSize = option;
                } else if (option.matches("^-XX:[\\-+]PrintAdaptiveSizePolicy$")) {
                    printAdaptiveSizePolicy = option;
                } else if (option.matches("^-XX:[\\-+]PrintClassHistogram$")) {
                    printClassHistogram = option;
                } else if (option.matches("^-XX:[\\-+]PrintClassHistogramAfterFullGC$")) {
                    printClassHistogramAfterFullGc = option;
                } else if (option.matches("^-XX:[\\-+]PrintClassHistogramBeforeFullGC$")) {
                    printClassHistogramBeforeFullGc = option;
                } else if (option.matches("^-XX:[\\-+]PrintFlagsFinal$")) {
                    printFlagsFinal = option;
                } else if (option.matches("^-XX:PrintFLSStatistics=\\d$")) {
                    printFLSStatistics = option;
                } else if (option.matches("^-XX:[\\-+]PrintGC$")) {
                    printGc = option;
                } else if (option.matches("^-XX:[\\-+]PrintGCApplicationConcurrentTime$")) {
                    printGcApplicationConcurrentTime = option;
                } else if (option.matches("^-XX:[\\-+]PrintGCApplicationStoppedTime$")) {
                    printGcApplicationStoppedTime = option;
                } else if (option.matches("^-XX:[\\-+]PrintGCDateStamps$")) {
                    printGcDateStamps = option;
                } else if (option.matches("^-XX:[\\-+]PrintGCDetails$")) {
                    printGcDetails = option;
                } else if (option.matches("^-XX:[\\-+]PrintGCTaskTimeStamps$")) {
                    printGcTaskTimeStamps = option;
                } else if (option.matches("^-XX:[\\-+]PrintGCTimeStamps$")) {
                    printGcTimeStamps = option;
                } else if (option.matches("^-XX:[\\-+]PrintHeapAtGC$")) {
                    printHeapAtGc = option;
                } else if (option.matches("^-XX:[\\-+]PrintPromotionFailure$")) {
                    printPromotionFailure = option;
                } else if (option.matches("^-XX:[\\-+]PrintReferenceGC$")) {
                    printReferenceGc = option;
                } else if (option.matches("^-XX:[\\-+]PrintSafepointStatistics$")) {
                    printSafepointStatistics = option;
                } else if (option.matches("^-XX:[\\-+]PrintStringDeduplicationStatistics$")) {
                    printStringDeduplicationStatistics = option;
                } else if (option.matches("^-XX:[\\-+]PrintTenuringDistribution$")) {
                    printTenuringDistribution = option;
                } else if (option.matches("^-XX:ReservedCodeCacheSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    reservedCodeCacheSize = option;
                } else if (option.matches("^-server$")) {
                    server = true;
                } else if (option.matches("^-XX:ShenandoahGCHeuristics=(adaptive|aggressive|compact|static)$")) {
                    shenandoahGcHeuristics = option;
                } else if (option.matches("^-XX:ShenandoahMinFreeThreshold=\\d{1,3}$")) {
                    shenandoahMinFreeThreshold = option;
                } else if (option.matches("^-XX:SurvivorRatio=\\d{1,}$")) {
                    survivorRatio = option;
                } else if (option.matches("^-XX:TargetSurvivorRatio=\\d{1,3}$")) {
                    targetSurvivorRatio = option;
                } else if (option.matches("^-XX:[\\-+]TieredCompilation$")) {
                    tieredCompilation = option;
                } else if (option.matches("^-(X)?(ss|X:ThreadStackSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    threadStackSize = option;
                } else if (option.matches("^-XX:[\\-+]TraceClassUnloading$")) {
                    traceClassUnloading = option;
                } else if (option.matches("^-XX:[\\-+]UnlockDiagnosticVMOptions$")) {
                    unlockDiagnosticVmOptions = option;
                } else if (option.matches("^-XX:[\\-+]UnlockExperimentalVMOptions$")) {
                    unlockExperimentalVmOptions = option;
                } else if (option.matches("^-XX:[\\-+]UseAdaptiveSizePolicy$")) {
                    useAdaptiveSizePolicy = option;
                } else if (option.matches("^-XX:[\\-+]UseBiasedLocking$")) {
                    useBiasedLocking = option;
                } else if (option.matches("^-XX:[\\-+]UseCGroupMemoryLimitForHeap$")) {
                    useCGroupMemoryLimitForHeap = option;
                } else if (option.matches("^-XX:[\\-+]UseCMSInitiatingOccupancyOnly$")) {
                    useCmsInitiatingOccupancyOnly = option;
                } else if (option.matches("^-XX:[\\-+]UseConcMarkSweepGC$")) {
                    useConcMarkSweepGc = option;
                } else if (option.matches("^-XX:[\\-+]UseCompressedClassPointers$")) {
                    useCompressedClassPointers = option;
                } else if (option.matches("^-XX:[\\-+]UseCompressedOops$")) {
                    useCompressedOops = option;
                } else if (option.matches("^-XX:[\\-+]UseConcMarkSweepGC$")) {
                    useConcMarkSweepGc = option;
                } else if (option.matches("^-XX:[\\-+]UseFastAccessorMethods$")) {
                    useFastAccessorMethods = option;
                } else if (option.matches("^-XX:[\\-+]UseFastUnorderedTimeStamps$")) {
                    useFastUnorderedTimeStamps = option;
                } else if (option.matches("^-XX:[\\-+]UseG1GC$")) {
                    useG1Gc = option;
                } else if (option.matches("^-XX:[\\-+]UseGCLogFileRotation$")) {
                    useGcLogFileRotation = option;
                } else if (option.matches("^-XX:[\\-+]UseHugeTLBFS$")) {
                    useHugeTLBFS = option;
                } else if (option.matches("^-XX:[\\-+]UseMembar$")) {
                    useMembar = option;
                } else if (option.matches("^-XX:[\\-+]UseLargePages$")) {
                    useLargePages = option;
                } else if (option.matches("^-XX:[\\-+]UseNUMA$")) {
                    useNUMA = option;
                } else if (option.matches("^-XX:[\\-+]UseParallelGC$")) {
                    useParallelGc = option;
                } else if (option.matches("^-XX:[\\-+]UseParallelOldGC$")) {
                    useParallelOldGc = option;
                } else if (option.matches("^-XX:[\\-+]UseParNewGC$")) {
                    useParNewGc = option;
                } else if (option.matches("^-XX:[\\-+]UsePerfData$")) {
                    usePerfData = option;
                } else if (option.matches("^-XX:[\\-+]UseSerialGC$")) {
                    useSerialGc = option;
                } else if (option.matches("^-XX:[\\-+]UseShenandoahGC$")) {
                    useShenandoahGc = option;
                } else if (option.matches("^-XX:[\\-+]UseStringDeduplication$")) {
                    useStringDeduplication = option;
                } else if (option.matches("^-XX:[\\-+]UseVMInterruptibleIO$")) {
                    useVmInterruptibleIo = option;
                } else if (option.matches("^-verbose:class$")) {
                    verboseClass = true;
                } else if (option.matches("^-verbose:gc$")) {
                    verboseGc = true;
                } else if (option.matches("^-Xverify(:(all|none|remote))?$")) {
                    verify = option;
                } else {
                    undefined.add(option);
                }
            }
        }
    }

    /**
     * Do JVM options analysis.
     * 
     * @param analysis
     *            The fatal error log analysis.
     */
    public void doAnalysis(List<Analysis> analysis) {
        // Check for remote debugging enabled
        if (jpdaSocketTransport != null) {
            analysis.add(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED);
        }
        if (!undefined.isEmpty()) {
            analysis.add(Analysis.INFO_OPT_UNDEFINED);
        }
        // Check if initial or max metaspace size being set
        if (metaspaceSize != null || maxMetaspaceSize != null) {
            analysis.add(Analysis.INFO_OPT_METASPACE);
        }
        // Check if MaxMetaspaceSize is less than CompressedClassSpaceSize.
        if (maxMetaspaceSize != null) {
            long compressedClassSpaceBytes;
            if (compressedClassSpaceSize != null) {
                compressedClassSpaceBytes = JdkUtil
                        .getByteOptionBytes(JdkUtil.getByteOptionValue(compressedClassSpaceSize));
            } else {
                // Default is 1G
                compressedClassSpaceBytes = JdkUtil.convertSize(1, 'G', 'B');
            }
            if (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(maxMetaspaceSize)) < compressedClassSpaceBytes) {
                analysis.add(Analysis.WARN_OPT_METASPACE_LT_COMP_CLASS);
            }
        }
        // Check if heap prevented from growing beyond initial heap size
        if (initialHeapSize != null && maxHeapSize != null
                && (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(initialHeapSize)) != JdkUtil
                        .getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize)))
                && JdkUtil.isOptionDisabled(useAdaptiveSizePolicy)) {
            analysis.add(Analysis.WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED);
        }
        // Check for erroneous perm gen settings
        if (maxPermSize != null) {
            analysis.add(Analysis.INFO_OPT_MAX_PERM_SIZE);
        }
        if (permSize != null) {
            analysis.add(Analysis.INFO_OPT_PERM_SIZE);
        }
        // Check heap dump options
        if (heapDumpOnOutOfMemoryError == null) {
            analysis.add(Analysis.INFO_OPT_HEAP_DUMP_ON_OOME_MISSING);
        } else {
            if (JdkUtil.isOptionDisabled(heapDumpOnOutOfMemoryError)) {
                analysis.add(Analysis.WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED);
            } else {
                if (heapDumpPath == null) {
                    analysis.add(Analysis.INFO_OPT_HEAP_DUMP_PATH_MISSING);
                } else if (heapDumpPath.matches("^.+\\.(hprof|bin)$")) {
                    analysis.add(Analysis.WARN_OPT_HEAP_DUMP_PATH_FILENAME);
                }
            }
        }
        // Check for multi-threaded CMS initial mark disabled
        if (JdkUtil.isOptionDisabled(cmsParallelInitialMarkEnabled)) {
            analysis.add(Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED);
        }
        // Check for multi-threaded CMS remark disabled
        if (JdkUtil.isOptionDisabled(cmsParallelRemarkEnabled)) {
            analysis.add(Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED);
        }

        // Compressed object references should only be used when heap < 32G
        boolean heapLessThan32G = true;
        BigDecimal thirtyTwoGigabytes = new BigDecimal("32").multiply(Constants.GIGABYTE);
        if (maxHeapSize != null && JdkUtil
                .getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize)) >= thirtyTwoGigabytes.longValue()) {
            heapLessThan32G = false;
        }
        if (heapLessThan32G) {
            // Should use compressed object pointers
            if (JdkUtil.isOptionDisabled(useCompressedOops)) {
                if (maxHeapSize == null) {
                    // Heap size unknown
                    analysis.add(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK);
                } else {
                    // Heap < 32G
                    analysis.add(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G);
                }
                if (compressedClassSpaceSize != null) {
                    analysis.add(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED);
                }
            } else if (JdkUtil.isOptionDisabled(useCompressedClassPointers)) {
                // Should use compressed class pointers
                if (maxHeapSize == null) {
                    // Heap size unknown
                    analysis.add(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK);
                } else {
                    // Heap < 32G
                    analysis.add(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G);
                }
                if (compressedClassSpaceSize != null) {
                    analysis.add(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED);
                }
            } else {
                analysis.add(Analysis.INFO_OPT_METASPACE_CLASS_METADATA_AND_COMP_CLASS_SPACE);
            }
        } else {
            // Should not use compressed object pointers
            if (JdkUtil.isOptionEnabled(useCompressedOops)) {
                analysis.add(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G);
            } else if (JdkUtil.isOptionEnabled(useCompressedClassPointers)) {
                // Should not use compressed class pointers
                analysis.add(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G);
            } else {
                analysis.add(Analysis.INFO_OPT_METASPACE_CLASS_METADATA);
            }
            // Should not be setting class pointer space size
            if (compressedClassSpaceSize != null) {
                analysis.add(Analysis.WARN_OPT_COMP_CLASS_SIZE_HEAP_GT_32G);
            }
        }
        // Check for verbose class loading/unloading logging
        if (verboseClass) {
            analysis.add(Analysis.INFO_OPT_VERBOSE_CLASS);
        }
        // Check for -XX:+TieredCompilation.
        if (JdkUtil.isOptionEnabled(tieredCompilation)) {
            analysis.add(Analysis.INFO_OPT_TIERED_COMPILATION_ENABLED);
        }
        // Check for -XX:-UseBiasedLocking.
        if (JdkUtil.isOptionDisabled(useBiasedLocking) && useShenandoahGc == null) {
            analysis.add(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED);
        }
        // Check for -XX:+PrintHeapAtGC.
        if (printHeapAtGc != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_HEAP_AT_GC);
        }
        // Check for -XX:+PrintTenuringDistribution.
        if (printTenuringDistribution != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_TENURING_DISTRIBUTION);
        }
        // Check for -XX:PrintFLSStatistics=\\d.
        if (printFLSStatistics != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_FLS_STATISTICS);
        }
        // Experimental VM options
        if (JdkUtil.isOptionEnabled(useCGroupMemoryLimitForHeap)) {
            analysis.add(Analysis.WARN_OPT_CGROUP_MEMORY_LIMIT);
        } else if (JdkUtil.isOptionEnabled(useFastUnorderedTimeStamps)
                && JdkUtil.isOptionEnabled(unlockExperimentalVmOptions)) {
            analysis.add(Analysis.WARN_OPT_FAST_UNORDERED_TIMESTAMPS);
        } else if (g1MixedGCLiveThresholdPercent != null) {
            analysis.add(Analysis.WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT);
        } else if (JdkUtil.isOptionEnabled(unlockExperimentalVmOptions)) {
            // Generic -XX:+UnlockExperimentalVMOptions.
            analysis.add(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED);
        }
        // Check for -XX:+UnlockDiagnosticVMOptions.
        if (JdkUtil.isOptionEnabled(unlockDiagnosticVmOptions)) {
            analysis.add(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED);
        }
        // Check for instrumentation.
        if (!javaagent.isEmpty()) {
            analysis.add(Analysis.INFO_OPT_INSTRUMENTATION);
        }
        // If explicit gc is disabled, don't need to set explicit gc options
        if (JdkUtil.isOptionDisabled(explicitGCInvokesConcurrentAndUnloadsClasses)
                && JdkUtil.isOptionEnabled(disableExplicitGc)) {
            analysis.add(Analysis.INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA);
        }
        // Check if JDK8 gc log file rotation missing or disabled
        if (JdkUtil.isOptionDisabled(useGcLogFileRotation)) {
            analysis.add(Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_DISABLED);
            if (numberOfGcLogFiles != null) {
                analysis.add(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_NUM_ROTATION_DISABLED);
            }
        }
        // JDK11 gc log file rotation checks
        if (!log.isEmpty()) {
            Iterator<String> iterator = log.iterator();
            while (iterator.hasNext()) {
                String xLog = iterator.next();
                if (xLog.matches("^-Xlog:gc.+filecount=0.*$")) {
                    analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_ROTATION_DISABLED);
                    break;
                }
            }
        }
        // Check if JDK11 automatic gc log file rotation disabled
        if (!log.isEmpty()) {
            Iterator<String> iterator = log.iterator();
            while (iterator.hasNext()) {
                String xLog = iterator.next();
                if (xLog.matches("^-Xlog:gc.+filesize=0.*$")) {
                    analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_0);
                    break;
                }
            }
        }
        // Check if JDK8 log file size is small
        if (gcLogFileSize != null) {
            BigDecimal fiveGigabytes = new BigDecimal("5").multiply(Constants.MEGABYTE);
            if (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(gcLogFileSize)) < fiveGigabytes.longValue()) {
                analysis.add(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_SIZE_SMALL);
            }
        }
        // Check if JDK11 log file size is small
        if (!log.isEmpty()) {
            Iterator<String> iterator = log.iterator();
            while (iterator.hasNext()) {
                String xLog = iterator.next();
                String filesize = null;
                Pattern pattern = Pattern.compile("^-Xlog:gc.+filesize=" + JdkRegEx.OPTION_SIZE_BYTES + ".*$");
                Matcher matcher = pattern.matcher(xLog);
                if (matcher.find()) {
                    filesize = matcher.group(1);
                }
                BigDecimal fiveGigabytes = new BigDecimal("5").multiply(Constants.MEGABYTE);
                if (JdkUtil.getByteOptionBytes(filesize) < fiveGigabytes.longValue()) {
                    analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_SMALL);
                }
            }
        }

        // Check for JMX enabled
        if (JdkUtil.isOptionEnabled(managementServer) || systemProperties.contains("-Dcom.sun.management.jmxremote")) {
            analysis.add(Analysis.INFO_OPT_JMX_ENABLED);
        }
        // Check if native library being used.
        if (!agentpath.isEmpty()) {
            analysis.add(Analysis.INFO_OPT_NATIVE);
        }
        // Check for young space >= old space
        if (newSize != null && maxHeapSize != null
                && JdkMath.calcPercent(JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(newSize)),
                        JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize))) >= 50) {
            analysis.add(Analysis.INFO_OPT_NEW_RATIO_INVERTED);
        }
        // Check for -XX:-PrintAdaptiveSizePolicy / -XX:+PrintAdaptiveSizePolicy
        if (JdkUtil.isOptionDisabled(printAdaptiveSizePolicy)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED);
        } else if (JdkUtil.isOptionEnabled(printAdaptiveSizePolicy)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED);
        }
        // Check for -XX:+PrintPromotionFailure option being used
        if (JdkUtil.isOptionEnabled(printPromotionFailure)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_PROMOTION_FAILURE);
        }
        // Check if background compilation disabled.
        if (batch || JdkUtil.isOptionDisabled(backgroundCompilation)) {
            analysis.add(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED);
        }
        // Check if just in time (JIT) compilation disabled.
        if (xInt) {
            analysis.add(Analysis.WARN_OPT_BYTECODE_COMPILE_DISABLED);
        }
        // Check if compilation being forced on first invocation.
        if (comp) {
            analysis.add(Analysis.WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION);
        }
        // Check for class unloading disabled
        if (JdkUtil.isOptionDisabled(classUnloading)) {
            analysis.add(Analysis.WARN_OPT_CLASS_UNLOADING_DISABLED);
        }
        // Check if CMS handling metaspace collections is disabled
        if (JdkUtil.isOptionDisabled(cmsClassUnloadingEnabled)) {
            analysis.add(Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED);
        }
        // Check for incremental mode in combination with -XX:CMSInitiatingOccupancyFraction=<n>.
        if (JdkUtil.isOptionEnabled(cmsIncrementalMode) && cmsInitiatingOccupancyFraction != null) {
            analysis.add(Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT);
        }
        // Check for-XX:CMSInitiatingOccupancyFraction without -XX:+UseCMSInitiatingOccupancyOnly.
        if (cmsInitiatingOccupancyFraction != null && !JdkUtil.isOptionEnabled(useCmsInitiatingOccupancyOnly)) {
            analysis.add(Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING);
        }
        // Check if PAR_NEW collector disabled
        if (JdkUtil.isOptionDisabled(useParNewGc)) {
            analysis.add(Analysis.WARN_OPT_CMS_PAR_NEW_DISABLED);
        }
        // Check to see if explicit gc is disabled
        if (JdkUtil.isOptionEnabled(disableExplicitGc)
                && !analysis.contains(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7)) {
            analysis.add(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED);
            // Specifying that explicit gc being collected concurrently makes no sense if explicit gc is disabled.
            if (JdkUtil.isOptionEnabled(explicitGCInvokesConcurrent)) {
                analysis.add(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT);
            }
        }
        // Check for outputting application concurrent time
        if (JdkUtil.isOptionEnabled(printGcApplicationConcurrentTime)) {
            analysis.add(Analysis.INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME);

        }
        // Check for print class histogram output enabled with -XX:+PrintClassHistogram,
        // -XX:+PrintClassHistogramBeforeFullGC, or -XX:+PrintClassHistogramAfterFullGC.
        if (JdkUtil.isOptionEnabled(printClassHistogram)) {
            analysis.add(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM);
        }
        if (JdkUtil.isOptionEnabled(printClassHistogramAfterFullGc)) {
            analysis.add(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC);
        }
        if (JdkUtil.isOptionEnabled(printClassHistogramBeforeFullGc)) {
            analysis.add(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC);
        }
        // Check if print gc details option disabled
        if (JdkUtil.isOptionDisabled(printGcDetails)) {
            analysis.add(Analysis.WARN_OPT_JDK8_PRINT_GC_DETAILS_DISABLED);
        }
        // Check for tenuring disabled or default overriden
        long tenuring = JdkUtil.getNumberOptionValue(maxTenuringThreshold);
        if (tenuring == 0) {
            analysis.add(Analysis.WARN_OPT_TENURING_DISABLED);
        } else if (tenuring > 0 && tenuring < 15) {
            analysis.add(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE);
        }
        // Check for -XX:+UseMembar option being used
        if (JdkUtil.isOptionEnabled(useMembar)) {
            analysis.add(Analysis.WARN_OPT_USE_MEMBAR);
        }
        // Check for setting DGC intervals when explicit GC is disabled.
        if (JdkUtil.isOptionEnabled(disableExplicitGc)) {
            if (getSunRmiDgcClientGcInterval() != null) {
                analysis.add(Analysis.INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT);
            }
            if (getSunRmiDgcServerGcInterval() != null) {
                analysis.add(Analysis.INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT);
            }
        }
        // Check for small DGC intervals.
        if (getSunRmiDgcClientGcInterval() != null && !JdkUtil.isOptionEnabled(disableExplicitGc)) {
            long sunRmiDgcClientGcInterval = JdkUtil.getNumberOptionValue(getSunRmiDgcClientGcInterval());
            if (sunRmiDgcClientGcInterval < 3600000) {
                analysis.add(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL);
            }
        }
        if (getSunRmiDgcServerGcInterval() != null && !JdkUtil.isOptionEnabled(disableExplicitGc)) {
            long sunRmiDgcServerGcInterval = JdkUtil.getNumberOptionValue(getSunRmiDgcServerGcInterval());
            if (sunRmiDgcServerGcInterval < 3600000) {
                analysis.add(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL);
            }
        }
        // Check for -XX:+PrintReferenceGC.
        if (JdkUtil.isOptionEnabled(printReferenceGc)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_REFERENCE_GC_ENABLED);
        }
        // Check for -XX:+PrintStringDeduplicationStatistics.
        if (JdkUtil.isOptionEnabled(printStringDeduplicationStatistics)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_STRING_DEDUP_STATS_ENABLED);
        }
        // Check for trace class unloading enabled with -XX:+TraceClassUnloading
        if (JdkUtil.isOptionEnabled(traceClassUnloading)) {
            analysis.add(Analysis.INFO_OPT_TRACE_CLASS_UNLOADING);
        }
        // Check for -XX:SurvivorRatio option being used
        if (survivorRatio != null) {
            analysis.add(Analysis.INFO_OPT_SURVIVOR_RATIO);
        }
        // Check for -XX:TargetSurvivorRatio option being used
        if (targetSurvivorRatio != null) {
            analysis.add(Analysis.INFO_OPT_SURVIVOR_RATIO_TARGET);
        }
        // Check for JFR being used
        if (flightRecorderOptions != null) {
            analysis.add(Analysis.INFO_OPT_JFR);
        }
        // Check for -XX:+EliminateLocks
        if (eliminateLocks != null && JdkUtil.isOptionEnabled(eliminateLocks)) {
            analysis.add(Analysis.INFO_OPT_ELIMINATE_LOCKS_ENABLED);
        }
        // Check for -XX:-UseVMInterruptibleIO
        if (useVmInterruptibleIo != null) {
            analysis.add(Analysis.WARN_OPT_JDK8_USE_VM_INTERRUPTIBLE_IO);
        }
        // Check for class verifcation disabled
        if (verify != null && verify.equals("-Xverify:none")) {
            analysis.add(Analysis.WARN_OPT_VERIFY_NONE);
        }
        // Check for max heap size not being explicitly set
        if (this.maxHeapSize == null) {
            analysis.add(Analysis.INFO_OPT_HEAP_MAX_MISSING);
        }
    }

    public String getAbrt() {
        return abrt;
    }

    public String getAdaptiveSizePolicyWeight() {
        return adaptiveSizePolicyWeight;
    }

    public String getAddModules() {
        return addModules;
    }

    public ArrayList<String> getAgentpath() {
        return agentpath;
    }

    public String getAggressiveHeap() {
        return aggressiveHeap;
    }

    public String getAggressiveOpts() {
        return aggressiveOpts;
    }

    public String getAlwaysPreTouch() {
        return alwaysPreTouch;
    }

    public String getAutoBoxCacheMax() {
        return autoBoxCacheMax;
    }

    public String getBackgroundCompilation() {
        return backgroundCompilation;
    }

    public ArrayList<String> getBootclasspath() {
        return bootclasspath;
    }

    public String getCiCompilerCount() {
        return ciCompilerCount;
    }

    public String getClassUnloading() {
        return classUnloading;
    }

    public String getCmsClassUnloadingEnabled() {
        return cmsClassUnloadingEnabled;
    }

    public String getCmsIncrementalMode() {
        return cmsIncrementalMode;
    }

    public String getCmsInitiatingOccupancyFraction() {
        return cmsInitiatingOccupancyFraction;
    }

    public String getCmsParallelInitialMarkEnabled() {
        return cmsParallelInitialMarkEnabled;
    }

    public String getCmsParallelRemarkEnabled() {
        return cmsParallelRemarkEnabled;
    }

    public String getCompileCommand() {
        return compileCommand;
    }

    public String getCompressedClassSpaceSize() {
        return compressedClassSpaceSize;
    }

    public String getConcGcThreads() {
        return concGcThreads;
    }

    public String getDebugNonSafepoints() {
        return debugNonSafepoints;
    }

    public String getDisableExplicitGc() {
        return disableExplicitGc;
    }

    public String getDoEscapeAnalysis() {
        return doEscapeAnalysis;
    }

    public String getEliminateLocks() {
        return eliminateLocks;
    }

    public String getErrorFile() {
        return errorFile;
    }

    public String getExitOnOutOfMemoryError() {
        return exitOnOutOfMemoryError;
    }

    public String getExplicitGCInvokesConcurrent() {
        return explicitGCInvokesConcurrent;
    }

    public String getExplicitGCInvokesConcurrentAndUnloadsClasses() {
        return explicitGCInvokesConcurrentAndUnloadsClasses;
    }

    public String getExtensiveErrorReports() {
        return extensiveErrorReports;
    }

    public String getFlightRecorderOptions() {
        return flightRecorderOptions;
    }

    public String getG1HeapRegionSize() {
        return g1HeapRegionSize;
    }

    public String getG1HeapWastePercent() {
        return g1HeapWastePercent;
    }

    public String getG1MaxNewSizePercent() {
        return g1MaxNewSizePercent;
    }

    public String getG1MixedGCLiveThresholdPercent() {
        return g1MixedGCLiveThresholdPercent;
    }

    public String getG1SummarizeRSetStats() {
        return g1SummarizeRSetStats;
    }

    public String getG1SummarizeRSetStatsPeriod() {
        return g1SummarizeRSetStatsPeriod;
    }

    /**
     * @return The garbage collector(s) based on the JVM options.
     */
    public List<GarbageCollector> getGarbageCollectors() {
        List<GarbageCollector> garbageCollectors = new ArrayList<GarbageCollector>();
        if (JdkUtil.isOptionEnabled(useSerialGc)) {
            garbageCollectors.add(GarbageCollector.SERIAL);
            garbageCollectors.add(GarbageCollector.SERIAL_OLD);
        }
        if (JdkUtil.isOptionEnabled(useParallelOldGc)) {
            garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
            garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
        } else if (JdkUtil.isOptionEnabled(useParallelGc)) {
            garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
            if (JdkUtil.isOptionDisabled(useParallelOldGc)) {
                garbageCollectors.add(GarbageCollector.SERIAL_OLD);
            } else {
                garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
            }
        }
        if (JdkUtil.isOptionEnabled(useConcMarkSweepGc)) {
            garbageCollectors.add(GarbageCollector.PAR_NEW);
            garbageCollectors.add(GarbageCollector.CMS);
        } else if (JdkUtil.isOptionEnabled(useParNewGc)) {
            garbageCollectors.add(GarbageCollector.PAR_NEW);
            garbageCollectors.add(GarbageCollector.SERIAL_OLD);
        }
        if (JdkUtil.isOptionEnabled(useG1Gc)) {
            garbageCollectors.add(GarbageCollector.G1);
        }
        if (JdkUtil.isOptionEnabled(useShenandoahGc)) {
            garbageCollectors.add(GarbageCollector.SHENANDOAH);
        }
        return garbageCollectors;
    }

    public String getGcLogFileSize() {
        return gcLogFileSize;
    }

    public String getGcTimeRatio() {
        return gcTimeRatio;
    }

    public String getGuaranteedSafepointInterval() {
        return guaranteedSafepointInterval;
    }

    public String getHeapDumpOnOutOfMemoryError() {
        return heapDumpOnOutOfMemoryError;
    }

    public String getHeapDumpPath() {
        return heapDumpPath;
    }

    public String getInitialHeapSize() {
        return initialHeapSize;
    }

    public String getInitiatingHeapOccupancyPercent() {
        return initiatingHeapOccupancyPercent;
    }

    public ArrayList<String> getJavaagent() {
        return javaagent;
    }

    public String getJpdaSocketTransport() {
        return jpdaSocketTransport;
    }

    public String getLargePageSizeInBytes() {
        return largePageSizeInBytes;
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public String getLogFile() {
        return logFile;
    }

    public String getLogGc() {
        return logGc;
    }

    public String getLogVmOutput() {
        return logVmOutput;
    }

    public String getManagementServer() {
        return managementServer;
    }

    public String getMaxDirectMemorySize() {
        return maxDirectMemorySize;
    }

    public String getMaxGcPauseMillis() {
        return maxGcPauseMillis;
    }

    public String getMaxHeapFreeRatio() {
        return maxHeapFreeRatio;
    }

    public String getMaxHeapSize() {
        return maxHeapSize;
    }

    public String getMaxJavaStackTraceDepth() {
        return maxJavaStackTraceDepth;
    }

    public String getMaxMetaspaceSize() {
        return maxMetaspaceSize;
    }

    public String getMaxNewSize() {
        return maxNewSize;
    }

    public String getMaxPermSize() {
        return maxPermSize;
    }

    public String getMaxTenuringThreshold() {
        return maxTenuringThreshold;
    }

    public String getMetaspaceSize() {
        return metaspaceSize;
    }

    public String getMinHeapFreeRatio() {
        return minHeapFreeRatio;
    }

    public String getNewSize() {
        return newSize;
    }

    public String getNumberOfGcLogFiles() {
        return numberOfGcLogFiles;
    }

    public String getOmitStackTraceInFastThrow() {
        return omitStackTraceInFastThrow;
    }

    public String getOnError() {
        return onError;
    }

    public String getOnOutOfMemoryError() {
        return onOutOfMemoryError;
    }

    public String getOptimizeStringConcat() {
        return optimizeStringConcat;
    }

    public String getParallelGcThreads() {
        return parallelGcThreads;
    }

    public String getPerfDisableSharedMem() {
        return perfDisableSharedMem;
    }

    public String getPermSize() {
        return permSize;
    }

    public String getPrintAdaptiveSizePolicy() {
        return printAdaptiveSizePolicy;
    }

    public String getPrintClassHistogram() {
        return printClassHistogram;
    }

    public String getPrintClassHistogramAfterFullGc() {
        return printClassHistogramAfterFullGc;
    }

    public String getPrintClassHistogramBeforeFullGc() {
        return printClassHistogramBeforeFullGc;
    }

    public String getPrintFlagsFinal() {
        return printFlagsFinal;
    }

    public String getPrintFLSStatistics() {
        return printFLSStatistics;
    }

    public String getPrintGc() {
        return printGc;
    }

    public String getPrintGcApplicationConcurrentTime() {
        return printGcApplicationConcurrentTime;
    }

    public String getPrintGcApplicationStoppedTime() {
        return printGcApplicationStoppedTime;
    }

    public String getPrintGcDateStamps() {
        return printGcDateStamps;
    }

    public String getPrintGcDetails() {
        return printGcDetails;
    }

    public String getPrintGcTaskTimeStamps() {
        return printGcTaskTimeStamps;
    }

    public String getPrintGcTimeStamps() {
        return printGcTimeStamps;
    }

    public String getPrintHeapAtGc() {
        return printHeapAtGc;
    }

    public String getPrintHeapAtGC() {
        return printHeapAtGc;
    }

    public String getPrintPromotionFailure() {
        return printPromotionFailure;
    }

    public String getPrintReferenceGc() {
        return printReferenceGc;
    }

    public String getPrintSafepointStatistics() {
        return printSafepointStatistics;
    }

    public String getPrintStringDeduplicationStatistics() {
        return printStringDeduplicationStatistics;
    }

    public String getPrintTenuringDistribution() {
        return printTenuringDistribution;
    }

    public String getReservedCodeCacheSize() {
        return reservedCodeCacheSize;
    }

    public String getShenandoahGcHeuristics() {
        return shenandoahGcHeuristics;
    }

    public String getShenandoahMinFreeThreshold() {
        return shenandoahMinFreeThreshold;
    }

    /**
     * Client Distributed Garbage Collection (DGC) interval in milliseconds.
     * 
     * <pre>
     * -Dsun.rmi.dgc.client.gcInterval=14400000
     * </pre>
     * 
     * @return The client Distributed Garbage Collection (DGC), or null if not explicitly set.
     */
    public String getSunRmiDgcClientGcInterval() {
        String sunRmiDgcClientGcIntervalOption = null;
        if (!systemProperties.isEmpty()) {
            Iterator<String> iterator = systemProperties.iterator();
            while (iterator.hasNext()) {
                String property = iterator.next();
                if (property.matches("-Dsun.rmi.dgc.client.gcInterval=\\d{1,}")) {
                    sunRmiDgcClientGcIntervalOption = property;
                    break;
                }
            }
        }
        return sunRmiDgcClientGcIntervalOption;
    }

    /**
     * Server Distributed Garbage Collection (DGC) interval in milliseconds.
     * 
     * <pre>
     * -Dsun.rmi.dgc.server.gcInterval=14400000
     * </pre>
     * 
     * @return The server Distributed Garbage Collection (DGC), or null if not explicitly set.
     */
    public String getSunRmiDgcServerGcInterval() {
        String sunRmiDgcServerGcIntervalOption = null;
        if (!systemProperties.isEmpty()) {
            Iterator<String> iterator = systemProperties.iterator();
            while (iterator.hasNext()) {
                String property = iterator.next();
                if (property.matches("-Dsun.rmi.dgc.server.gcInterval=\\d{1,}")) {
                    sunRmiDgcServerGcIntervalOption = property;
                    break;
                }
            }
        }
        return sunRmiDgcServerGcIntervalOption;
    }

    public String getSurvivorRatio() {
        return survivorRatio;
    }

    public ArrayList<String> getSystemProperties() {
        return systemProperties;
    }

    public String getTargetSurvivorRatio() {
        return targetSurvivorRatio;
    }

    public String getThreadStackSize() {
        return threadStackSize;
    }

    public String getTieredCompilation() {
        return tieredCompilation;
    }

    public String getTraceClassUnloading() {
        return traceClassUnloading;
    }

    public ArrayList<String> getUndefined() {
        return undefined;
    }

    public String getUnlockDiagnosticVmOptions() {
        return unlockDiagnosticVmOptions;
    }

    public String getUnlockExperimentalVmOptions() {
        return unlockExperimentalVmOptions;
    }

    public String getUseAdaptiveSizePolicy() {
        return useAdaptiveSizePolicy;
    }

    public String getUseBiasedLocking() {
        return useBiasedLocking;
    }

    public String getUseCGroupMemoryLimitForHeap() {
        return useCGroupMemoryLimitForHeap;
    }

    public String getUseCmsInitiatingOccupancyOnly() {
        return useCmsInitiatingOccupancyOnly;
    }

    public String getUseCompressedClassPointers() {
        return useCompressedClassPointers;
    }

    public String getUseCompressedOops() {
        return useCompressedOops;
    }

    public String getUseConcMarkSweepGc() {
        return useConcMarkSweepGc;
    }

    public String getUseFastAccessorMethods() {
        return useFastAccessorMethods;
    }

    public String getUseFastUnorderedTimeStamps() {
        return useFastUnorderedTimeStamps;
    }

    public String getUseG1Gc() {
        return useG1Gc;
    }

    public String getUseGcLogFileRotation() {
        return useGcLogFileRotation;
    }

    public String getUseHugeTLBFS() {
        return useHugeTLBFS;
    }

    public String getUseLargePages() {
        return useLargePages;
    }

    public String getUseMembar() {
        return useMembar;
    }

    public String getUseNUMA() {
        return useNUMA;
    }

    public String getUseParallelGc() {
        return useParallelGc;
    }

    public String getUseParallelOldGc() {
        return useParallelOldGc;
    }

    public String getUseParNewGc() {
        return useParNewGc;
    }

    public String getUsePerfData() {
        return usePerfData;
    }

    public String getUseSerialGc() {
        return useSerialGc;
    }

    public String getUseShenandoahGc() {
        return useShenandoahGc;
    }

    public String getUseStringDeduplication() {
        return useStringDeduplication;
    }

    public String getUseVmInterruptibleIo() {
        return useVmInterruptibleIo;
    }

    public String getVerify() {
        return verify;
    }

    public boolean isBatch() {
        return batch;
    }

    public boolean isClient() {
        return client;
    }

    public boolean isComp() {
        return comp;
    }

    public boolean isD64() {
        return d64;
    }

    public boolean isNoclassgc() {
        return noclassgc;
    }

    public boolean isServer() {
        return server;
    }

    public boolean isVerboseClass() {
        return verboseClass;
    }

    public boolean isVerboseGc() {
        return verboseGc;
    }

    public boolean isxInt() {
        return xInt;
    }
}

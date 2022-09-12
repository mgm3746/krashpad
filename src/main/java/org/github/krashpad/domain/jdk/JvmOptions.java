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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.util.Constants;
import org.github.krashpad.util.jdk.Analysis;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.GarbageCollector;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;

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
     * The percentage weight to give to recent gc stats (vs historic) for ergonomic calculations. For example:
     * 
     * <pre>
     * -XX:AdaptiveSizePolicyWeight=90
     * </pre>
     */
    private String adaptiveSizePolicyWeight;

    /**
     * Module exports. For example:
     * 
     * <pre>
     * -add-exports=jdk.unsupported/sun.misc=ALL-UNNAMED
     * </pre>
     */
    private ArrayList<String> addExports = new ArrayList<String>();

    /**
     * Runtime modules. For example:
     * 
     * <pre>
     * --add-modules=ALL-SYSTEM
     * </pre>
     */
    private ArrayList<String> addModules = new ArrayList<String>();

    /**
     * Used to allow deep reflection to access nonpublic members. For example:
     * 
     * <pre>
     * --add-opens=java.base/java.security=ALL-UNNAMED
     * </pre>
     */
    private ArrayList<String> addOpens = new ArrayList<String>();

    /**
     * JVM option to load a native agent by library name.
     * 
     * For example:
     * 
     * -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n
     * 
     * -agentlib:am_sun_16=/opt/jbossapps/tgkcpmj02/itcam/runtime/jboss7.tagp3aps3.tgkcpmj02/dc.env.properties
     */
    private ArrayList<String> agentlib = new ArrayList<String>();

    /**
     * JVM option to load a native agent by full path.
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
     * The option to set the classpath. For example:
     * 
     * <pre>
     * -classpath /path/to/tomcat/bin/bootstrap.jar:/path/to/tomcat/bin/tomcat-juli.jar:/path/to/java/ant.jar:
     * /path/to/java/ant-launcher.jar:/path/to/java/lib/tools.jar
     * </pre>
     */
    private String classpath;

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
     * The option for setting starting the concurrent collection NN% sooner than the calculated time. For example:
     * 
     * <pre>
     * -XX:CMSIncrementalSafetyFactor=20
     * </pre>
     */
    private String cmsIncrementalSafetyFactor;

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
     * Option to enable a young collection before the CMS remark phase, pruning the young generation to minimize remark
     * pause times.
     * 
     * 
     * <pre>
     *-XX:+CMSScavengeBeforeRemark
     * </pre>
     */
    private String cmsScavengeBeforeRemark;

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
     * The option for setting the number of method executions before a method is compiled from bytecode to native code
     * by the Just in Time (JIT) compiler with "standard compilation" (when tiered compilation is disabled). Ignored
     * when tiered compilation is enabled.
     * 
     * Default is 10,000. Setting -XX:CompileThreshold=1 forces compiling at first execution.
     * 
     * For example:
     * 
     * <pre>
     * -XX:CompileThreshold=500
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    private String compileThreshold;

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
     */
    private String concGcThreads;

    /**
     * Option to enable/disable the jvm process exiting (and producing a fatal error log and core as applicable) when
     * out of memory occurs.
     * 
     * <pre>
     *-XX:+CrashOnOutOfMemoryError
     * </pre>
     */
    private String crashOnOutOfMemoryError;

    /**
     * The option for specifying 64-bit. Removed in JDK11.
     * 
     * <pre>
     * -d64
     * </pre>
     */
    private boolean d64 = false;

    /**
     * Option to enable debugging using the Java Virtual Machine Debug Interface (JVMDI). JVMDI has been removed, so
     * this option does nothing. For example:
     * 
     * <pre>
     * -Xdebug
     * </pre>
     */
    private boolean debug = false;

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
     * The option to disable the creation of the AttachListener socket file (/tmp/.java_pid<pid>) used by
     * jcmd/jmap/jstack to communicate with the JVM. Created the first time jcmd/jmap/jstack is run.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+DisableAttachMechanism
     * </pre>
     */
    private String disableAttachMechanism;

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
     * The number of G1 concurrent refinement threads. For example:
     * 
     * <pre>
     * -XX:G1ConcRefinementThreads=4
     * </pre>
     */
    private String g1ConcRefinementThreads;

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
     * The option for setting the percentage of heap space you're explicitly reserving for the to-survivor space for
     * promotion from eden. For example:
     * 
     * <pre>
     * -XX:G1ReservePercent=10
     * </pre>
     */
    private String g1ReservePercent;

    /**
     * Option to enable/disable output of summarized remembered set processing info. For example:
     * 
     * <pre>
     * -XX:+G1SummarizeRSetStats
     * </pre>
     */
    private String g1SummarizeRSetStats;

    /**
     * The option for setting the ***REMOVED*** of GCs to output update buffer processing info (0 = disabled). For example:
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
     * The option for setting the Java heap base memory address (where the heap allocation begins). For example:
     * 
     * <pre>
     * -XX:HeapBaseMinAddress=12g
     * </pre>
     */
    private String heapBaseMinAddress;

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
     * Option to enable/disable checking for unrecognized options passed to the JVM. For example:
     * 
     * <pre>
     * -XX:+IgnoreUnrecognizedVMOptions
     * </pre>
     */
    private String ignoreUnrecognizedVmOptions;

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
     * Instrumentation (byte code manipulation).
     * 
     * For example:
     * 
     * -javaagent:/path/to/appdynamics/javaagent.jar
     */
    private ArrayList<String> javaagent = new ArrayList<String>();

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
     * Maximum number of nested calls to inline.
     * 
     * For example:
     * 
     * <pre>
     * -XX:MaxInlineLevel=15
     * </pre>
     */
    private String maxInlineLevel;

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
     * Equivalent to {@link ***REMOVED***reservedCodeCacheSize***REMOVED***.
     * 
     * For example:
     * 
     * <pre>
     * -Xmaxjitcodesize1024m
     * </pre>
     */
    private String maxjitcodesize;

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
     * -XX:MaxPermSize=256m
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
     * The minimum amount to resize the heap space in bytes. For example:
     * 
     * <pre>
     * --XX:MinHeapDeltaBytes=123456
     * </pre>
     */
    private String minHeapDeltaBytes;

    /**
     * The minimum percentage of free space to avoid expanding the heap size. For example:
     * 
     * <pre>
     * -XX:MinHeapFreeRatio=10
     * </pre>
     */
    private String minHeapFreeRatio;

    /**
     * The option to enable native memory tracking. For example:
     * 
     * <pre>
     *  -XX:NativeMemoryTracking=detail
     * </pre>
     */
    private String nativeMemoryTracking;

    /**
     * Option to set the ratio of old/new generation sizes.
     * 
     * For example:
     * 
     * <pre>
     * -XX:NewRatio=3
     * </pre>
     */
    private String newRatio;

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
     * Option to disable class verification on JVM startup. For example:
     * 
     * <pre>
     * -noverify
     * </pre>
     */
    private boolean noverify = false;

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
     * Map of jvm options.
     */
    private Map<String, ArrayList<String>> options = new HashMap<String, ArrayList<String>>();

    /**
     * The number of parallel gc threads. For example:
     * 
     * <pre>
     * -XX:ParallelGCThreads=4
     * </pre>
     */
    private String parallelGcThreads;

    /**
     * Option to enable/disable multi-threaded reference processing.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+ParallelRefProcEnabled
     * </pre>
     */
    private String parallelRefProcEnabled;

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
     * -XX:+PrintFlagsFinalparallelRefProcEnabled
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
     * Option to enable/disable printing trigger information. Deprecated in JDK9, removed in JDK11. For example:
     * 
     * <pre>
     * -XX:+PrintGCCause
     * </pre>
     */
    private String printGcCause;

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
     * Code cache size (default 240m), where the JVM stores the assembly language instructions of compiled code.
     * 
     * It's only necessary to set the max size, not min and max, for the following reasons:
     * 
     * 1) Memory is not allocated until needed, so setting a large code cache size only impacts reserved (virtual)
     * memory, not allocated (physical) memory.
     * 
     * 2) Resizing the code cache is done in the background and does not affect performance.
     * 
     * For example:
     * 
     * <pre>
     * -XX:ReservedCodeCacheSize=256m
     * </pre>
     */
    private String reservedCodeCacheSize;

    /**
     * Option to disable JVM signal handling. For example:
     * 
     * <pre>
     * -Xrs
     * </pre>
     */
    private boolean rs = false;

    /**
     * JVM option to load the Java Debug Wire Protocol (JDWP) library. Equivalent to -agentlib:jdwp.
     * 
     * For example:
     * 
     * -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n
     */
    private ArrayList<String> runjdwp = new ArrayList<String>();

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
     * -XX:ShenandoahInitFreeThreshold=***REMOVED***
     * -XX:ShenandoahMinFreeThreshold=***REMOVED*** 
     * -XX:ShenandoahAllocSpikeFactor=***REMOVED*** 
     * -XX:ShenandoahGarbageThreshold=***REMOVED***
     * </pre>
     * 
     * <p>
     * 2) static
     * </p>
     * 
     * <pre>
     * -XX:ShenandoahGCHeuristics=static
     * 
     * -XX:ShenandoahInitFreeThreshold=***REMOVED*** 
     * -XX:ShenandoahGarbageThreshold=***REMOVED***
     * </pre>
     * 
     * <p>
     * 3) compact
     * </p>
     * 
     * <pre>
     * -XX:ShenandoahGCHeuristics=compact
     * 
     * -XX:ConcGCThreads=***REMOVED***
     * -XX:ShenandoahAllocationThreshold=***REMOVED***
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
     * The option for setting the number of method executions before a method is compiled with the C1 (client) compiler
     * with invocation and backedge counters.
     * 
     * For example:
     * 
     * <pre>
     * -XX:Tier2CompileThreshold=2000
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    private String tier2CompileThreshold;

    /**
     * The option for setting the number of method executions before a method is compiled with the C1 (client) compiler
     * with full profiling.
     * 
     * For example:
     * 
     * <pre>
     * -XX:Tier3CompileThreshold=2000
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    private String tier3CompileThreshold;

    /**
     * The option for setting the number of method executions before a method is compiled with the C2 (server) compiler.
     * 
     * For example:
     * 
     * <pre>
     * -XX:Tier4CompileThreshold=15000
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    private String tier4CompileThreshold;

    /**
     * Option to enable/disable tiered compilation.
     * 
     * The JVM contains 2 Just in Time (JIT) compilers:
     * 
     * C1: Called the "client" compiler because it was originally designed with GUI applications in mind, where fast
     * startup is required.
     * 
     * C2: Called the "server" compiler because it was originally designed with long running server applications in
     * mind, aggressive optimization and performance is required.
     * 
     * Tiered compilation is enabled by default. The C1 compiler first compiles the code quickly to provide better
     * startup performance. After the application is warmed up, the C2 compiler compiles the code again with more
     * aggressive optimizations for better performance.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+TieredCompilation
     * </pre>
     */
    private String tieredCompilation;

    /**
     * Option to enable/disable tracing class loading. Removed in JDK17. For example:
     * 
     * <pre>
     * -XX:+TraceClassLoading.
     * </pre>
     */
    private String traceClassLoading;

    /**
     * Option to enable/disable class loading/unloading information in gc log. Removed JDK17. For example:
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
     * Diagnostic option (-requires XX:+UnlockDiagnosticVMOptions) to enable/disable parallel class loading. Disabled by
     * default and deprecated in JDK11.
     * 
     * For example:
     * 
     * <pre>
     * -XX:+UnlockDiagnosticVMOptions -XX:+UnsyncloadClass
     * </pre>
     */
    private String unsyncloadClass;

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
     * -XX:+UseCMSInitiatingOccupancyOnly
     * </pre>
     */
    private String useCmsInitiatingOccupancyOnly;

    /**
     * Option to enable/disable code cache flushing. For example:
     * 
     * <pre>
     * -XX + UseCodeCacheFlushing
     * </pre>
     */
    private String useCodeCacheFlushing;

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
     * Option to enable/disable the CMS old collector. Deprecated in JDK9 and removed in JDK14.
     * 
     * For example:
     * 
     * -XX:+UseConcMarkSweepGC
     */
    private String useConcMarkSweepGc;

    /**
     * Option to enable/disable ergonomic option to manage the number of compiler threads. For example:
     * 
     * <pre>
     * -XX:+UseDynamicNumberOfCompilerThreads
     * </pre>
     */
    private String useDynamicNumberOfCompilerThreads;

    /**
     * Option to enable/disable ergonomic option to manage the number of parallel garbage collector threads. For
     * example:
     * 
     * <pre>
     * -XX:+UseDynamicNumberOfGCThreads
     * </pre>
     */
    private String useDynamicNumberOfGcThreads;

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
     * The option to enable/disable the G1 collector. G1 was made the default collector in JDK9.
     * 
     * For example:
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
     * Option to enable/disable "OutOfMemoryError: GC overhead limit exceeded" when 98% of the total time is spent in
     * garbage collection and less than 2% of the heap is recovered. This feature is a throttle to prevent applications
     * from running for an extended period of time while making little or no progress because the heap is too small.
     * Enabled by default.
     * 
     * For example:
     * 
     * -XX:-UseGCOverheadLimit
     */
    private String useGcOverheadLimit;

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
     * Option to enable/disable the parallel scavenge young garbage collector. The parallel collector was made the
     * default collector in JDK7u4.
     * 
     * For example:
     * 
     * -XX:+UseParallelGC
     */
    private String useParallelGc;

    /**
     * Option to enable/disable the parallel multi-threaded old garbage collector. Redundant in JDK7/8/11, deprecated in
     * JDK15, and removed in JDK16.
     * 
     * For example:
     * 
     * -XX:+UseParallelOldGC
     */
    private String useParallelOldGc;

    /**
     * The option to enable/disable the CMS young collector. The use case for this option is to -disable the CMS young
     * (parallel) collector with -XX:-UseParNewGC to force using the serial new collector.
     * 
     * Deprecated in JDK8 and removed in JDK9 (i.e. you can only use the CMS young collector in combination with the CMS
     * old collector in JDK9+).
     * 
     * For example:
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
     * The option to enable/disable using a new type checker introduced in JDK5 with StackMapTable attributes. Mandatory
     * in JDK8+. Ignored in JDK8 and unrecognized in JDK11+.
     * 
     * <pre>
     * -XX:-UseSplitVerifier
     * </pre>
     */
    private String useSplitVerifier;

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
     * Option to enable/disable a thread-local allocation buffer. Using thread-local object allocation blocks improve
     * concurrency by reducing contention on the shared heap lock, allowing for more scalable allocation for heavily
     * threaded applications, increasing allocation performance. Enabled by default on multiprocessor systems.
     * 
     * <pre>
     * -XX:+UseTLAB
     * </pre>
     */
    private String useTlab;

    /**
     * Option to enable/disable making Solaris interruptible blocking I/O noninterruptible as they are on Linux and
     * Windows. Deprecated in JDK8 and removed in JDK11.
     */
    private String useVmInterruptibleIo;

    /**
     * Option to enable/disable the Z garbage collector (ZGC). For example:
     * 
     * -XX:+UseZGC
     */
    private String useZGc;

    /**
     * Option to enable logging (to standard out) class loading information.
     * 
     * -verbose:class
     */
    private boolean verboseClass = false;

    /**
     * Option to enable displaying detailed information about each gc event. Equivalent to <code>-XX:+PrintGC</code>.
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
            // (?<!^) match whatever follows, but not the start of the string
            // (?= -) match "space dash" followed by jvm option starting patterns, but don't include the empty leading
            // substring in the result
            String[] opts = jvmArgs.split(
                    "(?<!^)(?= -(-add|agentlib|agentpath|classpath|client|d(32|64)|javaagent|noverify|server|verbose|D|"
                            + "X))");
            String key = null;
            for (int i = 0; i < opts.length; i++) {
                String option = opts[i].trim();
                if (option.matches("^--add-exports=.+$")) {
                    addExports.add(option);
                    key = option;
                ***REMOVED*** else if (option.matches("^--add-modules=.+$")) {
                    addModules.add(option);
                    key = option;
                ***REMOVED*** else if (option.matches("^--add-opens=.+$")) {
                    addOpens.add(option);
                    key = option;
                ***REMOVED*** else if (option.matches("^-agentlib:.+$")) {
                    agentlib.add(option);
                    key = "agentlib";
                ***REMOVED*** else if (option.matches("^-agentpath:.+$")) {
                    agentpath.add(option);
                    key = "agentpath";
                ***REMOVED*** else if (option.matches("^-classpath.+$")) {
                    classpath = option;
                    key = "classpath";
                ***REMOVED*** else if (option.matches("^-client$")) {
                    client = true;
                    key = "client";
                ***REMOVED*** else if (option.matches("^-d64$")) {
                    d64 = true;
                    key = "d64";
                ***REMOVED*** else if (option.matches("^-D.+$")) {
                    systemProperties.add(option);
                    key = "D";
                ***REMOVED*** else if (option.matches("^-javaagent:.+$")) {
                    javaagent.add(option);
                    key = option;
                ***REMOVED*** else if (option.matches("^-noverify$")) {
                    noverify = true;
                    key = "noverify";
                ***REMOVED*** else if (option.matches("^-server$")) {
                    server = true;
                    key = "server";
                ***REMOVED*** else if (option.matches("^-verbose:class$")) {
                    verboseClass = true;
                    key = "class";
                ***REMOVED*** else if (option.matches("^-verbose:gc$")) {
                    verboseGc = true;
                    key = "verbose";
                ***REMOVED*** else if (option.matches("^-Xbatch$")) {
                    batch = true;
                    key = "batch";
                ***REMOVED*** else if (option.matches("^-Xbootclasspath.+$")) {
                    bootclasspath.add(option);
                    key = "Xbootclasspath";
                ***REMOVED*** else if (option.matches("^-Xcomp$")) {
                    comp = true;
                    key = "comp";
                ***REMOVED*** else if (option.matches("^-Xdebug$")) {
                    debug = true;
                    key = "debug";
                ***REMOVED*** else if (option.matches("^-Xint$")) {
                    xInt = true;
                    key = "int";
                ***REMOVED*** else if (option.matches("^-Xlog:.+$")) {
                    log.add(option);
                    key = "log";
                ***REMOVED*** else if (option.matches("^-Xloggc:.+$")) {
                    logGc = option;
                    key = "loggc";
                ***REMOVED*** else if (option.matches("^-Xmaxjitcodesize\\d{1,***REMOVED***[kKmMgG]{0,1***REMOVED***$")) {
                    maxjitcodesize = option;
                    key = "maxjitcodesize";
                ***REMOVED*** else if (option.matches("^-X(mn|X:NewSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    newSize = option;
                    key = "NewSize";
                ***REMOVED*** else if (option.matches("^-X(ms|X:InitialHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    initialHeapSize = option;
                    key = "InitialHeapSize";
                ***REMOVED*** else if (option.matches("^-X(mx|X:MaxHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxHeapSize = option;
                    key = "MaxHeapSize";
                ***REMOVED*** else if (option.matches("^-Xnoclassgc$")) {
                    noclassgc = true;
                    key = "noclassgc";
                ***REMOVED*** else if (option.matches("^-Xrs$")) {
                    rs = true;
                    key = "rs";
                ***REMOVED*** else if (option.matches("^-Xverify(:(all|none|remote))?$")) {
                    verify = option;
                    key = "verify";
                ***REMOVED*** else if (option.matches("^-XX:AdaptiveSizePolicyWeight=\\d{1,3***REMOVED***$")) {
                    adaptiveSizePolicyWeight = option;
                    key = "AdaptiveSizePolicyWeight";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]AggressiveHeap$")) {
                    aggressiveHeap = option;
                    key = "AggressiveHeap";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]AggressiveOpts$")) {
                    aggressiveOpts = option;
                    key = "AggressiveOpts";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]AlwaysPreTouch$")) {
                    alwaysPreTouch = option;
                    key = "alwaysPreTouch";
                ***REMOVED*** else if (option.matches("^-XX:AutoBoxCacheMax=\\d{1,10***REMOVED***$")) {
                    autoBoxCacheMax = option;
                    key = "autoBoxCacheMax";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]BackgroundCompilation$")) {
                    backgroundCompilation = option;
                    key = "BackgroundCompilation";
                ***REMOVED*** else if (option.matches("^-XX:CICompilerCount=\\d{1,3***REMOVED***$")) {
                    ciCompilerCount = option;
                    key = "CICompilerCount";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ClassUnloading$")) {
                    classUnloading = option;
                    key = "ClassUnloading";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]CMSClassUnloadingEnabled$")) {
                    cmsClassUnloadingEnabled = option;
                    key = "CMSClassUnloadingEnabled";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]CMSIncrementalMode$")) {
                    cmsIncrementalMode = option;
                    key = "CMSIncrementalMode";
                ***REMOVED*** else if (option.matches("^-XX:CMSIncrementalSafetyFactor=\\d{1,3***REMOVED***$")) {
                    cmsIncrementalSafetyFactor = option;
                    key = "CMSIncrementalSafetyFactor";
                ***REMOVED*** else if (option.matches("^-XX:CMSInitiatingOccupancyFraction=\\d{1,3***REMOVED***$")) {
                    cmsInitiatingOccupancyFraction = option;
                    key = "CMSInitiatingOccupancyFraction";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]CMSParallelInitialMarkEnabled$")) {
                    cmsParallelInitialMarkEnabled = option;
                    key = "CMSParallelInitialMarkEnabled";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]CMSParallelRemarkEnabled$")) {
                    cmsParallelRemarkEnabled = option;
                    key = "CMSParallelRemarkEnabled";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]CMSScavengeBeforeRemark$")) {
                    cmsScavengeBeforeRemark = option;
                    key = "CMSScavengeBeforeRemark";
                ***REMOVED*** else if (option.matches("^-XX:CompileCommand=.+$")) {
                    compileCommand = option;
                    key = "CompileCommand";
                ***REMOVED*** else if (option.matches("^-XX:CompileThreshold=\\d{1,***REMOVED***$")) {
                    compileThreshold = option;
                    key = "CompileThreshold";
                ***REMOVED*** else if (option.matches("^-XX:CompressedClassSpaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    compressedClassSpaceSize = option;
                    key = "CompressedClassSpaceSize";
                ***REMOVED*** else if (option.matches("^-XX:ConcGCThreads=\\d{1,3***REMOVED***$")) {
                    concGcThreads = option;
                    key = "ConcGCThreads";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]CrashOnOutOfMemoryError$")) {
                    crashOnOutOfMemoryError = option;
                    key = "CrashOnOutOfMemoryError";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]DebugNonSafepoints$")) {
                    debugNonSafepoints = option;
                    key = "DebugNonSafepoints";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]DisableAttachMechanism$")) {
                    disableAttachMechanism = option;
                    key = "DisableAttachMechanism";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]DisableExplicitGC$")) {
                    disableExplicitGc = option;
                    key = "DisableExplicitGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]DoEscapeAnalysis$")) {
                    doEscapeAnalysis = option;
                    key = "DoEscapeAnalysis";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]EliminateLocks$")) {
                    eliminateLocks = option;
                    key = "EliminateLocks";
                ***REMOVED*** else if (option.matches("^-XX:ErrorFile=\\S+$")) {
                    errorFile = option;
                    key = "ErrorFile";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ExitOnOutOfMemoryError$")) {
                    exitOnOutOfMemoryError = option;
                    key = "ExitOnOutOfMemoryError";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ExplicitGCInvokesConcurrent$")) {
                    explicitGCInvokesConcurrent = option;
                    key = "ExplicitGCInvokesConcurrent";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ExplicitGCInvokesConcurrentAndUnloadsClasses$")) {
                    explicitGCInvokesConcurrentAndUnloadsClasses = option;
                    key = "ExplicitGCInvokesConcurrentAndUnloadsClasses";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ExtensiveErrorReports$")) {
                    extensiveErrorReports = option;
                    key = "ExtensiveErrorReports";
                ***REMOVED*** else if (option.matches("^-XX:FlightRecorderOptions=.+$")) {
                    flightRecorderOptions = option;
                    key = "FlightRecorderOptions";
                ***REMOVED*** else if (option.matches("^-XX:G1ConcRefinementThreads=\\d{1,***REMOVED***$")) {
                    g1ConcRefinementThreads = option;
                    key = "G1ConcRefinementThreads";
                ***REMOVED*** else if (option.matches("^-XX:G1HeapRegionSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    g1HeapRegionSize = option;
                    key = "G1HeapRegionSize";
                ***REMOVED*** else if (option.matches("^-XX:G1MaxNewSizePercent=\\d{1,3***REMOVED***$")) {
                    g1MaxNewSizePercent = option;
                    key = "G1MaxNewSizePercent";
                ***REMOVED*** else if (option.matches("^-XX:G1ReservePercent=\\d{1,3***REMOVED***$")) {
                    g1ReservePercent = option;
                    key = "G1ReservePercent";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]G1SummarizeRSetStats$")) {
                    g1SummarizeRSetStats = option;
                    key = "G1SummarizeRSetStats";
                ***REMOVED*** else if (option.matches("^-XX:G1SummarizeRSetStatsPeriod=\\d$")) {
                    g1SummarizeRSetStatsPeriod = option;
                    key = "G1SummarizeRSetStatsPeriod";
                ***REMOVED*** else if (option.matches("^-XX:G1HeapWastePercent=\\d{1,3***REMOVED***$")) {
                    g1HeapWastePercent = option;
                    key = "G1HeapWastePercent";
                ***REMOVED*** else if (option.matches("^-XX:G1MixedGCLiveThresholdPercent=\\d{1,3***REMOVED***$")) {
                    g1MixedGCLiveThresholdPercent = option;
                    key = "G1MixedGCLiveThresholdPercent";
                ***REMOVED*** else if (option.matches("^-XX:GuaranteedSafepointInterval=\\d{1,10***REMOVED***$")) {
                    guaranteedSafepointInterval = option;
                    key = "GuaranteedSafepointInterval";
                ***REMOVED*** else if (option.matches("^-XX:HeapBaseMinAddress=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    heapBaseMinAddress = option;
                    key = "HeapBaseMinAddress";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]HeapDumpOnOutOfMemoryError$")) {
                    heapDumpOnOutOfMemoryError = option;
                    key = "HeapDumpOnOutOfMemoryError";
                ***REMOVED*** else if (option.matches("^-XX:HeapDumpPath=\\S+$")) {
                    heapDumpPath = option;
                    key = "HeapDumpPath";
                ***REMOVED*** else if (option.matches("^-XX:InitiatingHeapOccupancyPercent=\\d{1,3***REMOVED***$")) {
                    initiatingHeapOccupancyPercent = option;
                    key = "InitiatingHeapOccupancyPercent";
                ***REMOVED*** else if (option.matches("^-XX:LargePageSizeInBytes=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    largePageSizeInBytes = option;
                    key = "LargePageSizeInBytes";
                ***REMOVED*** else if (option.matches("^-XX:LogFile=\\S+$")) {
                    logFile = option;
                    key = "LogFile";
                ***REMOVED*** else if (option.matches("^-XX:MaxGCPauseMillis=\\d{1,***REMOVED***$")) {
                    maxGcPauseMillis = option;
                    key = "MaxGCPauseMillis";
                ***REMOVED*** else if (option.matches("^-XX:MaxJavaStackTraceDepth=\\d{1,***REMOVED***$")) {
                    maxJavaStackTraceDepth = option;
                    key = "MaxJavaStackTraceDepth";
                ***REMOVED*** else if (option.matches("^-XX:MaxNewSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxNewSize = option;
                    key = "MaxNewSize";
                ***REMOVED*** else if (option.matches("^-XX:MetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    metaspaceSize = option;
                    key = "MetaspaceSize";
                ***REMOVED*** else if (option.matches("^-XX:GCLogFileSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    gcLogFileSize = option;
                    key = "GCLogFileSize";
                ***REMOVED*** else if (option.matches("^-XX:GCTimeRatio=\\d{1,3***REMOVED***$")) {
                    gcTimeRatio = option;
                    key = "GCTimeRatio";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]LogVMOutput$")) {
                    logVmOutput = option;
                    key = "LogVMOutput";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ManagementServer$")) {
                    managementServer = option;
                    key = "ManagementServer";
                ***REMOVED*** else if (option.matches("^-XX:MaxDirectMemorySize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxDirectMemorySize = option;
                    key = "MaxDirectMemorySize";
                ***REMOVED*** else if (option.matches("^-XX:MaxHeapFreeRatio=\\d{1,3***REMOVED***$")) {
                    maxHeapFreeRatio = option;
                    key = "MaxHeapFreeRatio";
                ***REMOVED*** else if (option.matches("^-XX:MaxInlineLevel=\\d{1,***REMOVED***$")) {
                    maxInlineLevel = option;
                    key = "MaxInlineLevel";
                ***REMOVED*** else if (option.matches("^-XX:MaxMetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxMetaspaceSize = option;
                    key = "MaxMetaspaceSize";
                ***REMOVED*** else if (option.matches("^-XX:MaxPermSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxPermSize = option;
                    key = "MaxPermSize";
                ***REMOVED*** else if (option.matches("^-XX:MaxTenuringThreshold=\\d{1,***REMOVED***$")) {
                    maxTenuringThreshold = option;
                    key = "MaxTenuringThreshold";
                ***REMOVED*** else if (option.matches("^-XX:MinHeapDeltaBytes=\\d{1,***REMOVED***$")) {
                    minHeapDeltaBytes = option;
                    key = "MinHeapDeltaBytes";
                ***REMOVED*** else if (option.matches("^-XX:MinHeapFreeRatio=\\d{1,3***REMOVED***$")) {
                    minHeapFreeRatio = option;
                    key = "MinHeapFreeRatio";
                ***REMOVED*** else if (option.matches("^-XX:NativeMemoryTracking=.+$")) {
                    nativeMemoryTracking = option;
                    key = "NativeMemoryTracking";
                ***REMOVED*** else if (option.matches("^-XX:NewRatio=.+$")) {
                    newRatio = option;
                    key = "NewRatio";
                ***REMOVED*** else if (option.matches("^-XX:NumberOfGCLogFiles=\\d{1,***REMOVED***$")) {
                    numberOfGcLogFiles = option;
                    key = "NumberOfGCLogFiles";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]OmitStackTraceInFastThrow$")) {
                    omitStackTraceInFastThrow = option;
                    key = "OmitStackTraceInFastThrow";
                ***REMOVED*** else if (option.matches("^-XX:OnError=.+$")) {
                    onError = option;
                    key = "OnError";
                ***REMOVED*** else if (option.matches("^-XX:OnOutOfMemoryError=.+$")) {
                    onOutOfMemoryError = option;
                    key = "OnOutOfMemoryError";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]OptimizeStringConcat$")) {
                    optimizeStringConcat = option;
                    key = "OptimizeStringConcat";
                ***REMOVED*** else if (option.matches("^-XX:ParallelGCThreads=\\d{1,3***REMOVED***$")) {
                    parallelGcThreads = option;
                    key = "ParallelGCThreads";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]ParallelRefProcEnabled$")) {
                    parallelRefProcEnabled = option;
                    key = "ParallelRefProcEnabled";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PerfDisableSharedMem$")) {
                    perfDisableSharedMem = option;
                    key = "PerfDisableSharedMem";
                ***REMOVED*** else if (option.matches("^-XX:PermSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    permSize = option;
                    key = "PermSize";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintAdaptiveSizePolicy$")) {
                    printAdaptiveSizePolicy = option;
                    key = "PrintAdaptiveSizePolicy";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintClassHistogram$")) {
                    printClassHistogram = option;
                    key = "PrintClassHistogram";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintClassHistogramAfterFullGC$")) {
                    printClassHistogramAfterFullGc = option;
                    key = "PrintClassHistogramAfterFullGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintClassHistogramBeforeFullGC$")) {
                    printClassHistogramBeforeFullGc = option;
                    key = "PrintClassHistogramBeforeFullGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintFlagsFinal$")) {
                    printFlagsFinal = option;
                    key = "PrintFlagsFinal";
                ***REMOVED*** else if (option.matches("^-XX:PrintFLSStatistics=\\d$")) {
                    printFLSStatistics = option;
                    key = "PrintFLSStatistics";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGC$")) {
                    printGc = option;
                    key = "PrintGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCApplicationConcurrentTime$")) {
                    printGcApplicationConcurrentTime = option;
                    key = "PrintGCApplicationConcurrentTime";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCApplicationStoppedTime$")) {
                    printGcApplicationStoppedTime = option;
                    key = "PrintGCApplicationStoppedTime";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCCause$")) {
                    printGcCause = option;
                    key = "PrintGCCause";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCDateStamps$")) {
                    printGcDateStamps = option;
                    key = "PrintGCDateStamps";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCDetails$")) {
                    printGcDetails = option;
                    key = "PrintGCDetails";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCTaskTimeStamps$")) {
                    printGcTaskTimeStamps = option;
                    key = "PrintGCTaskTimeStamps";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCTimeStamps$")) {
                    printGcTimeStamps = option;
                    key = "PrintGCTimeStamps";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintHeapAtGC$")) {
                    printHeapAtGc = option;
                    key = "PrintHeapAtGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintPromotionFailure$")) {
                    printPromotionFailure = option;
                    key = "PrintPromotionFailure";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintReferenceGC$")) {
                    printReferenceGc = option;
                    key = "PrintReferenceGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintSafepointStatistics$")) {
                    printSafepointStatistics = option;
                    key = "PrintSafepointStatistics";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintStringDeduplicationStatistics$")) {
                    printStringDeduplicationStatistics = option;
                    key = "PrintStringDeduplicationStatistics";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintTenuringDistribution$")) {
                    printTenuringDistribution = option;
                    key = "PrintTenuringDistribution";
                ***REMOVED*** else if (option.matches("^-XX:ReservedCodeCacheSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    reservedCodeCacheSize = option;
                    key = "ReservedCodeCacheSize";
                ***REMOVED*** else if (option.matches("^-Xrunjdwp:.+$")) {
                    runjdwp.add(option);
                    key = "runjdwp";
                ***REMOVED*** else if (option.matches("^-XX:ShenandoahGCHeuristics=(adaptive|aggressive|compact|static)$")) {
                    shenandoahGcHeuristics = option;
                    key = "ShenandoahGCHeuristics";
                ***REMOVED*** else if (option.matches("^-XX:ShenandoahMinFreeThreshold=\\d{1,3***REMOVED***$")) {
                    shenandoahMinFreeThreshold = option;
                    key = "ShenandoahMinFreeThreshold";
                ***REMOVED*** else if (option.matches("^-XX:SurvivorRatio=\\d{1,***REMOVED***$")) {
                    survivorRatio = option;
                    key = "SurvivorRatio";
                ***REMOVED*** else if (option.matches("^-XX:TargetSurvivorRatio=\\d{1,3***REMOVED***$")) {
                    targetSurvivorRatio = option;
                    key = "TargetSurvivorRatio";
                ***REMOVED*** else if (option.matches("^-XX:Tier2CompileThreshold=\\d{1,***REMOVED***$")) {
                    tier2CompileThreshold = option;
                    key = "Tier2CompileThreshold";
                ***REMOVED*** else if (option.matches("^-XX:Tier3CompileThreshold=\\d{1,***REMOVED***$")) {
                    tier3CompileThreshold = option;
                    key = "Tier3CompileThreshold";
                ***REMOVED*** else if (option.matches("^-XX:Tier4CompileThreshold=\\d{1,***REMOVED***$")) {
                    tier4CompileThreshold = option;
                    key = "Tier4CompileThreshold";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]TieredCompilation$")) {
                    tieredCompilation = option;
                    key = "TieredCompilation";
                ***REMOVED*** else if (option.matches("^-(X)?(ss|X:ThreadStackSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    threadStackSize = option;
                    key = "ThreadStackSize";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]TraceClassLoading$")) {
                    traceClassLoading = option;
                    key = "TraceClassLoading";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]TraceClassUnloading$")) {
                    traceClassUnloading = option;
                    key = "TraceClassUnloading";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UnlockDiagnosticVMOptions$")) {
                    unlockDiagnosticVmOptions = option;
                    key = "UnlockDiagnosticVMOptions";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UnlockExperimentalVMOptions$")) {
                    unlockExperimentalVmOptions = option;
                    key = "UnlockExperimentalVMOptions";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]IgnoreUnrecognizedVMOptions$")) {
                    ignoreUnrecognizedVmOptions = option;
                    key = "IgnoreUnrecognizedVMOptions";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UnsyncloadClass$")) {
                    unsyncloadClass = option;
                    key = "UnsyncloadClass";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseAdaptiveSizePolicy$")) {
                    useAdaptiveSizePolicy = option;
                    key = "UseAdaptiveSizePolicy";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseBiasedLocking$")) {
                    useBiasedLocking = option;
                    key = "UseBiasedLocking";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCGroupMemoryLimitForHeap$")) {
                    useCGroupMemoryLimitForHeap = option;
                    key = "UseCGroupMemoryLimitForHeap";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCMSInitiatingOccupancyOnly$")) {
                    useCmsInitiatingOccupancyOnly = option;
                    key = "UseCGroupMemoryLimitForHeap";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCodeCacheFlushing$")) {
                    useCodeCacheFlushing = option;
                    key = "UseCodeCacheFlushing";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseConcMarkSweepGC$")) {
                    useConcMarkSweepGc = option;
                    key = "UseConcMarkSweepGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCompressedClassPointers$")) {
                    useCompressedClassPointers = option;
                    key = "UseCompressedClassPointers";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCompressedOops$")) {
                    useCompressedOops = option;
                    key = "UseCompressedOops";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseConcMarkSweepGC$")) {
                    useConcMarkSweepGc = option;
                    key = "UseConcMarkSweepGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseDynamicNumberOfCompilerThreads$")) {
                    useDynamicNumberOfCompilerThreads = option;
                    key = "UseDynamicNumberOfCompilerThreads";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseDynamicNumberOfGCThreads$")) {
                    useDynamicNumberOfGcThreads = option;
                    key = "useDynamicNumberOfGcThreads";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseFastAccessorMethods$")) {
                    useFastAccessorMethods = option;
                    key = "UseFastAccessorMethods";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseFastUnorderedTimeStamps$")) {
                    useFastUnorderedTimeStamps = option;
                    key = "UseFastUnorderedTimeStamps";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseG1GC$")) {
                    useG1Gc = option;
                    key = "UseG1GC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseGCLogFileRotation$")) {
                    useGcLogFileRotation = option;
                    key = "UseGCLogFileRotation";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseGCOverheadLimit$")) {
                    useGcOverheadLimit = option;
                    key = "UseGCOverheadLimit";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseHugeTLBFS$")) {
                    useHugeTLBFS = option;
                    key = "UseHugeTLBFS";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseMembar$")) {
                    useMembar = option;
                    key = "UseMembar";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseLargePages$")) {
                    useLargePages = option;
                    key = "UseLargePages";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseNUMA$")) {
                    useNUMA = option;
                    key = "UseNUMA";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseParallelGC$")) {
                    useParallelGc = option;
                    key = "UseParallelGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseParallelOldGC$")) {
                    useParallelOldGc = option;
                    key = "UseParallelOldGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseParNewGC$")) {
                    useParNewGc = option;
                    key = "UseParNewGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UsePerfData$")) {
                    usePerfData = option;
                    key = "UsePerfData";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseSerialGC$")) {
                    useSerialGc = option;
                    key = "UseSerialGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseShenandoahGC$")) {
                    useShenandoahGc = option;
                    key = "UseShenandoahGC";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseSplitVerifier$")) {
                    useSplitVerifier = option;
                    key = "UseSplitVerifier";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseStringDeduplication$")) {
                    useStringDeduplication = option;
                    key = "UseStringDeduplication";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseTLAB$")) {
                    useTlab = option;
                    key = "UseTLAB";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseVMInterruptibleIO$")) {
                    useVmInterruptibleIo = option;
                    key = "UseVMInterruptibleIO";
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseZGC$")) {
                    useZGc = option;
                    key = "UseZGC";
                ***REMOVED*** else {
                    undefined.add(option);
                    key = "undefined";
                ***REMOVED***
                if (!this.options.containsKey(key)) {
                    this.options.put(key, new ArrayList<String>());
                ***REMOVED***
                this.options.get(key).add(option);
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    /**
     * Do JVM options analysis.
     * 
     * @param analysis
     *            The fatal error log analysis.
     * @param javaSpecification
     *            The JDK major version.
     */
    public void doAnalysis(List<Analysis> analysis, JavaSpecification javaSpecification) {
        // Check for remote debugging enabled
        if (!agentlib.isEmpty()) {
            Iterator<String> iterator = agentlib.iterator();
            Pattern pattern = Pattern.compile("^-agentlib:jdwp=transport=dt_socket.+$");
            while (iterator.hasNext()) {
                String agentlib = iterator.next();
                Matcher matcher = pattern.matcher(agentlib);
                if (matcher.find()) {
                    analysis.add(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (!analysis.contains(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED) && !runjdwp.isEmpty()) {
            Iterator<String> iterator = runjdwp.iterator();
            Pattern pattern = Pattern.compile("^-Xrunjdwp:transport=dt_socket.+$");
            while (iterator.hasNext()) {
                String runjdwp = iterator.next();
                Matcher matcher = pattern.matcher(runjdwp);
                if (matcher.find()) {
                    analysis.add(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (!undefined.isEmpty()) {
            analysis.add(Analysis.INFO_OPT_UNDEFINED);
        ***REMOVED***
        // Check if initial or max metaspace size being set
        if (metaspaceSize != null || maxMetaspaceSize != null) {
            analysis.add(Analysis.INFO_OPT_METASPACE);
        ***REMOVED***
        // Check if MaxMetaspaceSize is less than CompressedClassSpaceSize.
        if (maxMetaspaceSize != null) {
            long compressedClassSpaceBytes;
            if (compressedClassSpaceSize != null) {
                compressedClassSpaceBytes = JdkUtil
                        .getByteOptionBytes(JdkUtil.getByteOptionValue(compressedClassSpaceSize));
            ***REMOVED*** else {
                // Default is 1G
                compressedClassSpaceBytes = JdkUtil.convertSize(1, 'G', 'B');
            ***REMOVED***
            if (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(maxMetaspaceSize)) < compressedClassSpaceBytes) {
                analysis.add(Analysis.WARN_OPT_METASPACE_LT_COMP_CLASS);
            ***REMOVED***
        ***REMOVED***
        // Check if heap prevented from growing beyond initial heap size
        if (initialHeapSize != null && maxHeapSize != null
                && (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(initialHeapSize)) != JdkUtil
                        .getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize)))
                && JdkUtil.isOptionDisabled(useAdaptiveSizePolicy)) {
            analysis.add(Analysis.WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED);
        ***REMOVED***
        // Check for erroneous perm gen settings
        if (!(javaSpecification == JavaSpecification.JDK6 || javaSpecification == JavaSpecification.JDK7)) {
            if (maxPermSize != null) {
                analysis.add(Analysis.INFO_OPT_MAX_PERM_SIZE);
            ***REMOVED***
            if (permSize != null) {
                analysis.add(Analysis.INFO_OPT_PERM_SIZE);
            ***REMOVED***
        ***REMOVED***

        // Check heap dump options
        if (heapDumpOnOutOfMemoryError == null) {
            analysis.add(Analysis.INFO_OPT_HEAP_DUMP_ON_OOME_MISSING);
        ***REMOVED*** else {
            if (JdkUtil.isOptionDisabled(heapDumpOnOutOfMemoryError)) {
                analysis.add(Analysis.WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED);
            ***REMOVED*** else {
                if (heapDumpPath == null) {
                    analysis.add(Analysis.INFO_OPT_HEAP_DUMP_PATH_MISSING);
                ***REMOVED*** else if (heapDumpPath.matches("^.+\\.(hprof|bin)$")) {
                    analysis.add(Analysis.WARN_OPT_HEAP_DUMP_PATH_FILENAME);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check for multi-threaded CMS initial mark disabled
        if (!JdkUtil.isOptionDisabled(useConcMarkSweepGc) && JdkUtil.isOptionDisabled(cmsParallelInitialMarkEnabled)) {
            analysis.add(Analysis.WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED);
        ***REMOVED***
        // Check for multi-threaded CMS remark disabled
        if (!JdkUtil.isOptionDisabled(useConcMarkSweepGc) && JdkUtil.isOptionDisabled(cmsParallelRemarkEnabled)) {
            analysis.add(Analysis.WARN_OPT_CMS_PARALLEL_REMARK_DISABLED);
        ***REMOVED***

        // Compressed object references should only be used when heap < 32G
        if (!(javaSpecification == JavaSpecification.JDK6 || javaSpecification == JavaSpecification.JDK7)) {
            boolean heapLessThan32G = true;
            BigDecimal thirtyTwoGigabytes = new BigDecimal("32").multiply(Constants.GIGABYTE);
            if (maxHeapSize != null && JdkUtil
                    .getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize)) >= thirtyTwoGigabytes.longValue()) {
                heapLessThan32G = false;
            ***REMOVED***
            if (heapLessThan32G) {
                // Should use compressed object pointers
                if (JdkUtil.isOptionDisabled(useCompressedOops)) {
                    if (maxHeapSize == null) {
                        // Heap size unknown
                        analysis.add(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK);
                    ***REMOVED*** else {
                        // Heap < 32G
                        analysis.add(Analysis.WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G);
                    ***REMOVED***
                    if (compressedClassSpaceSize != null) {
                        analysis.add(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED);
                    ***REMOVED***
                ***REMOVED*** else if (JdkUtil.isOptionDisabled(useCompressedClassPointers)) {
                    // Should use compressed class pointers
                    if (maxHeapSize == null) {
                        // Heap size unknown
                        analysis.add(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK);
                    ***REMOVED*** else {
                        // Heap < 32G
                        analysis.add(Analysis.WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G);
                    ***REMOVED***
                    if (compressedClassSpaceSize != null) {
                        analysis.add(Analysis.INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED);
                    ***REMOVED***
                ***REMOVED*** else {
                    analysis.add(Analysis.INFO_OPT_METASPACE_CLASS_METADATA_AND_COMP_CLASS_SPACE);
                ***REMOVED***
            ***REMOVED*** else {
                // Should not use compressed object pointers
                if (JdkUtil.isOptionEnabled(useCompressedOops)) {
                    analysis.add(Analysis.WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G);
                ***REMOVED*** else if (JdkUtil.isOptionEnabled(useCompressedClassPointers)) {
                    // Should not use compressed class pointers
                    analysis.add(Analysis.WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G);
                ***REMOVED*** else {
                    analysis.add(Analysis.INFO_OPT_METASPACE_CLASS_METADATA);
                ***REMOVED***
                // Should not be setting class pointer space size
                if (compressedClassSpaceSize != null) {
                    analysis.add(Analysis.WARN_OPT_COMP_CLASS_SIZE_HEAP_GT_32G);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check for verbose class loading/unloading logging
        if (verboseClass) {
            analysis.add(Analysis.INFO_OPT_VERBOSE_CLASS);
        ***REMOVED***
        // Check for -XX:+TieredCompilation.
        if (JdkUtil.isOptionEnabled(tieredCompilation)) {
            analysis.add(Analysis.INFO_OPT_TIERED_COMPILATION_ENABLED);
        ***REMOVED***
        // Check for -XX:-UseBiasedLocking.
        if (JdkUtil.isOptionDisabled(useBiasedLocking) && useShenandoahGc == null) {
            analysis.add(Analysis.WARN_OPT_BIASED_LOCKING_DISABLED);
        ***REMOVED***
        // PrintGCCause checks
        if (printGcCause != null) {
            if (JdkUtil.isOptionDisabled(printGcCause)) {
                analysis.add(Analysis.WARN_OPT_JDK8_PRINT_GC_CAUSE_DISABLED);
            ***REMOVED*** else {
                analysis.add(Analysis.INFO_OPT_JDK8_PRINT_GC_CAUSE);
            ***REMOVED***
        ***REMOVED***
        // Check for -XX:+PrintHeapAtGC.
        if (printHeapAtGc != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_HEAP_AT_GC);
        ***REMOVED***
        // Check for -XX:+PrintTenuringDistribution.
        if (printTenuringDistribution != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_TENURING_DISTRIBUTION);
        ***REMOVED***
        // Check for -XX:PrintFLSStatistics=\\d.
        if (printFLSStatistics != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_FLS_STATISTICS);
        ***REMOVED***
        // Experimental VM options
        if (JdkUtil.isOptionEnabled(useCGroupMemoryLimitForHeap)) {
            analysis.add(Analysis.WARN_OPT_CGROUP_MEMORY_LIMIT);
        ***REMOVED*** else if (JdkUtil.isOptionEnabled(useFastUnorderedTimeStamps)
                && JdkUtil.isOptionEnabled(unlockExperimentalVmOptions)) {
            analysis.add(Analysis.WARN_OPT_FAST_UNORDERED_TIMESTAMPS);
        ***REMOVED*** else if (g1MixedGCLiveThresholdPercent != null) {
            analysis.add(Analysis.WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT);
        ***REMOVED*** else if (JdkUtil.isOptionEnabled(unlockExperimentalVmOptions)) {
            // Generic -XX:+UnlockExperimentalVMOptions.
            analysis.add(Analysis.INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED);
        ***REMOVED***
        // Check for -XX:+UnlockDiagnosticVMOptions.
        if (JdkUtil.isOptionEnabled(unlockDiagnosticVmOptions)) {
            analysis.add(Analysis.INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED);
        ***REMOVED***
        // Check for instrumentation.
        if (!javaagent.isEmpty()) {
            analysis.add(Analysis.INFO_OPT_INSTRUMENTATION);
        ***REMOVED***
        // If explicit gc is disabled, don't need to set explicit gc options
        if (JdkUtil.isOptionDisabled(explicitGCInvokesConcurrentAndUnloadsClasses)
                && JdkUtil.isOptionEnabled(disableExplicitGc)) {
            analysis.add(Analysis.INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA);
        ***REMOVED***
        // Check if JDK8 gc log file rotation missing or disabled
        if (JdkUtil.isOptionDisabled(useGcLogFileRotation)) {
            analysis.add(Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_DISABLED);
            if (numberOfGcLogFiles != null) {
                analysis.add(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_NUM_ROTATION_DISABLED);
            ***REMOVED***
            if (logGc != null && !logGc.contains("%")
                    && !analysis.contains(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE)) {
                analysis.add(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE);
            ***REMOVED***
        ***REMOVED***
        // JDK11 gc log file rotation checks
        if (!log.isEmpty()) {
            Iterator<String> iterator = log.iterator();
            Pattern pattern = Pattern.compile("^-Xlog:gc(.+)filecount=0.*$");
            while (iterator.hasNext()) {
                String xLog = iterator.next();
                Matcher matcher = pattern.matcher(xLog);
                if (matcher.find()) {
                    analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_ROTATION_DISABLED);
                    if (!matcher.group(1).contains("%")
                            && !analysis.contains(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_OVERWRITE)) {
                        analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_OVERWRITE);
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check if JDK11 automatic gc log file rotation disabled
        if (!log.isEmpty()) {
            Iterator<String> iterator = log.iterator();
            Pattern pattern = Pattern.compile("^-Xlog:gc(.+)filesize=0.*$");
            while (iterator.hasNext()) {
                String xLog = iterator.next();
                Matcher matcher = pattern.matcher(xLog);
                if (matcher.find()) {
                    analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_0);
                    if (!matcher.group(1).contains("%")
                            && !analysis.contains(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_OVERWRITE)) {
                        analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_OVERWRITE);
                    ***REMOVED***
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check if JDK8 log file size is small
        if (gcLogFileSize != null) {
            BigDecimal fiveGigabytes = new BigDecimal("5").multiply(Constants.MEGABYTE);
            if (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(gcLogFileSize)) < fiveGigabytes.longValue()) {
                analysis.add(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_SIZE_SMALL);
            ***REMOVED***
        ***REMOVED***
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
                ***REMOVED***
                BigDecimal fiveGigabytes = new BigDecimal("5").multiply(Constants.MEGABYTE);
                if (JdkUtil.getByteOptionBytes(filesize) < fiveGigabytes.longValue()) {
                    analysis.add(Analysis.WARN_OPT_JDK11_GC_LOG_FILE_SIZE_SMALL);
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***

        // Check for JMX enabled
        if (JdkUtil.isOptionEnabled(managementServer) || systemProperties.contains("-Dcom.sun.management.jmxremote")) {
            analysis.add(Analysis.INFO_OPT_JMX_ENABLED);
        ***REMOVED***
        // Check if native library being used.
        if (!agentlib.isEmpty() || !agentpath.isEmpty()) {
            analysis.add(Analysis.INFO_OPT_NATIVE);
        ***REMOVED***
        // Check for young space >= old space
        if (newSize != null && maxHeapSize != null
                && JdkMath.calcPercent(JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(newSize)),
                        JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize))) >= 50) {
            analysis.add(Analysis.INFO_OPT_NEW_RATIO_INVERTED);
        ***REMOVED***
        // Check for -XX:-PrintAdaptiveSizePolicy / -XX:+PrintAdaptiveSizePolicy
        if (JdkUtil.isOptionDisabled(printAdaptiveSizePolicy)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED);
        ***REMOVED*** else if (JdkUtil.isOptionEnabled(printAdaptiveSizePolicy)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED);
        ***REMOVED***
        // Check for -XX:+PrintPromotionFailure option being used
        if (JdkUtil.isOptionEnabled(printPromotionFailure)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_PROMOTION_FAILURE);
        ***REMOVED***
        // Check if background compilation disabled.
        if (batch || JdkUtil.isOptionDisabled(backgroundCompilation)) {
            analysis.add(Analysis.WARN_OPT_BYTECODE_BACK_COMP_DISABLED);
        ***REMOVED***
        // Check if just in time (JIT) compilation disabled.
        if (xInt) {
            analysis.add(Analysis.WARN_OPT_BYTECODE_COMPILE_DISABLED);
        ***REMOVED***
        // Check if compilation being forced on first invocation.
        if (comp) {
            analysis.add(Analysis.WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION);
        ***REMOVED***
        // Check for class unloading disabled
        if (JdkUtil.isOptionDisabled(classUnloading)) {
            analysis.add(Analysis.WARN_OPT_CLASS_UNLOADING_DISABLED);
        ***REMOVED***
        // Check if CMS handling metaspace collections is disabled
        if (!JdkUtil.isOptionDisabled(useConcMarkSweepGc) && JdkUtil.isOptionDisabled(cmsClassUnloadingEnabled)) {
            analysis.add(Analysis.WARN_OPT_CMS_CLASS_UNLOADING_DISABLED);
        ***REMOVED***
        // Check for incremental mode in combination with -XX:CMSInitiatingOccupancyFraction=<n>.
        if (!JdkUtil.isOptionDisabled(useConcMarkSweepGc) && JdkUtil.isOptionEnabled(cmsIncrementalMode)
                && cmsInitiatingOccupancyFraction != null) {
            analysis.add(Analysis.WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT);
        ***REMOVED***
        // Check for-XX:CMSInitiatingOccupancyFraction without -XX:+UseCMSInitiatingOccupancyOnly.
        if (!JdkUtil.isOptionDisabled(useConcMarkSweepGc) && cmsInitiatingOccupancyFraction != null
                && !JdkUtil.isOptionEnabled(useCmsInitiatingOccupancyOnly)) {
            analysis.add(Analysis.INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING);
        ***REMOVED***
        // Check PAR_NEW disabled, redundant, or cruft
        if (JdkUtil.isOptionEnabled(useConcMarkSweepGc)) {
            if (JdkUtil.isOptionDisabled(useParNewGc)) {
                analysis.add(Analysis.WARN_OPT_JDK8_CMS_PAR_NEW_DISABLED);
            ***REMOVED*** else if (JdkUtil.isOptionEnabled(useParNewGc)) {
                analysis.add(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_REDUNDANT);
            ***REMOVED***
        ***REMOVED*** else if (JdkUtil.isOptionDisabled(useConcMarkSweepGc)) {
            analysis.add(Analysis.INFO_OPT_CMS_DISABLED);
        ***REMOVED*** else if (useParNewGc != null) {
            analysis.add(Analysis.INFO_OPT_JDK8_CMS_PAR_NEW_CRUFT);
        ***REMOVED***
        // Check PARALLEL_OLD disabled, redundant, or cruft
        if (JdkUtil.isOptionEnabled(useParallelGc)) {
            if (JdkUtil.isOptionDisabled(useParallelOldGc)) {
                analysis.add(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED);
            ***REMOVED*** else if (JdkUtil.isOptionEnabled(useParallelOldGc)) {
                analysis.add(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT);
            ***REMOVED***
        ***REMOVED*** else if (useParallelOldGc != null) {
            boolean isParallelCollector = useParallelGc == null && useDefaultCollector() && javaSpecification != null
                    && JdkUtil.getJavaSpecificationNumber(javaSpecification) >= 7
                    && JdkUtil.getJavaSpecificationNumber(javaSpecification) <= 8;
            if (!isParallelCollector) {
                analysis.add(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_CRUFT);
            ***REMOVED*** else {
                if (JdkUtil.isOptionDisabled(useParallelOldGc)) {
                    analysis.add(Analysis.WARN_OPT_JDK11_PARALLEL_OLD_DISABLED);
                ***REMOVED*** else if (JdkUtil.isOptionEnabled(useParallelOldGc)) {
                    analysis.add(Analysis.INFO_OPT_JDK11_PARALLEL_OLD_REDUNDANT);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        // Check to see if explicit gc is disabled
        if (JdkUtil.isOptionEnabled(disableExplicitGc)
                && !analysis.contains(Analysis.ERROR_EXPLICIT_GC_DISABLED_EAP7)) {
            analysis.add(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED);
            // Specifying that explicit gc being collected concurrently makes no sense if explicit gc is disabled.
            if (JdkUtil.isOptionEnabled(explicitGCInvokesConcurrent)) {
                analysis.add(Analysis.WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT);
            ***REMOVED***
        ***REMOVED***
        // Check for outputting application concurrent time
        if (JdkUtil.isOptionEnabled(printGcApplicationConcurrentTime)) {
            analysis.add(Analysis.INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME);

        ***REMOVED***
        // Check for print class histogram output enabled with -XX:+PrintClassHistogram,
        // -XX:+PrintClassHistogramBeforeFullGC, or -XX:+PrintClassHistogramAfterFullGC.
        if (JdkUtil.isOptionEnabled(printClassHistogram)) {
            analysis.add(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM);
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(printClassHistogramAfterFullGc)) {
            analysis.add(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC);
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(printClassHistogramBeforeFullGc)) {
            analysis.add(Analysis.WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC);
        ***REMOVED***
        // Check if print gc details option disabled
        if (JdkUtil.isOptionDisabled(printGcDetails)) {
            analysis.add(Analysis.WARN_OPT_JDK8_PRINT_GC_DETAILS_DISABLED);
        ***REMOVED***
        // Check for tenuring disabled or default overriden
        long tenuring = JdkUtil.getNumberOptionValue(maxTenuringThreshold);
        if (tenuring == 0) {
            analysis.add(Analysis.WARN_OPT_TENURING_DISABLED);
        ***REMOVED*** else if (tenuring > 0 && tenuring < 15) {
            analysis.add(Analysis.INFO_OPT_MAX_TENURING_OVERRIDE);
        ***REMOVED***
        // Check for -XX:+UseMembar option being used
        if (JdkUtil.isOptionEnabled(useMembar)) {
            analysis.add(Analysis.WARN_OPT_USE_MEMBAR);
        ***REMOVED***
        // Check for setting DGC intervals when explicit GC is disabled.
        if (JdkUtil.isOptionEnabled(disableExplicitGc)) {
            if (getSunRmiDgcClientGcInterval() != null) {
                analysis.add(Analysis.INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT);
            ***REMOVED***
            if (getSunRmiDgcServerGcInterval() != null) {
                analysis.add(Analysis.INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT);
            ***REMOVED***
        ***REMOVED***
        // Check for small DGC intervals.
        if (getSunRmiDgcClientGcInterval() != null && !JdkUtil.isOptionEnabled(disableExplicitGc)) {
            long sunRmiDgcClientGcInterval = JdkUtil.getNumberOptionValue(getSunRmiDgcClientGcInterval());
            if (sunRmiDgcClientGcInterval < 3600000) {
                analysis.add(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL);
            ***REMOVED*** else if (sunRmiDgcClientGcInterval > 86400000) {
                analysis.add(Analysis.WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_LARGE);
            ***REMOVED***
        ***REMOVED***
        if (getSunRmiDgcServerGcInterval() != null && !JdkUtil.isOptionEnabled(disableExplicitGc)) {
            long sunRmiDgcServerGcInterval = JdkUtil.getNumberOptionValue(getSunRmiDgcServerGcInterval());
            if (sunRmiDgcServerGcInterval < 3600000) {
                analysis.add(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL);
            ***REMOVED*** else if (sunRmiDgcServerGcInterval > 86400000) {
                analysis.add(Analysis.WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_LARGE);
            ***REMOVED***
        ***REMOVED***
        // Check for -XX:+PrintReferenceGC.
        if (JdkUtil.isOptionEnabled(printReferenceGc)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_REFERENCE_GC_ENABLED);
        ***REMOVED***
        // Check for -XX:+PrintStringDeduplicationStatistics.
        if (JdkUtil.isOptionEnabled(printStringDeduplicationStatistics)) {
            analysis.add(Analysis.INFO_OPT_JDK8_PRINT_STRING_DEDUP_STATS_ENABLED);
        ***REMOVED***
        // Check for trace class loading enabled with -XX:+TraceClassLoading
        if (JdkUtil.isOptionEnabled(traceClassLoading)) {
            analysis.add(Analysis.INFO_OPT_TRACE_CLASS_LOADING);
        ***REMOVED***
        // Check for trace class unloading enabled with -XX:+TraceClassUnloading
        if (JdkUtil.isOptionEnabled(traceClassUnloading)) {
            analysis.add(Analysis.INFO_OPT_TRACE_CLASS_UNLOADING);
        ***REMOVED***
        // Check for -XX:SurvivorRatio option being used
        if (survivorRatio != null) {
            analysis.add(Analysis.INFO_OPT_SURVIVOR_RATIO);
        ***REMOVED***
        // Check for -XX:TargetSurvivorRatio option being used
        if (targetSurvivorRatio != null) {
            analysis.add(Analysis.INFO_OPT_SURVIVOR_RATIO_TARGET);
        ***REMOVED***
        // Check for JFR being used
        if (flightRecorderOptions != null) {
            analysis.add(Analysis.INFO_OPT_JFR);
        ***REMOVED***
        // Check for -XX:+EliminateLocks
        if (eliminateLocks != null && JdkUtil.isOptionEnabled(eliminateLocks)) {
            analysis.add(Analysis.INFO_OPT_ELIMINATE_LOCKS_ENABLED);
        ***REMOVED***
        // Check for -XX:-UseVMInterruptibleIO
        if (useVmInterruptibleIo != null) {
            analysis.add(Analysis.WARN_OPT_JDK8_USE_VM_INTERRUPTIBLE_IO);
        ***REMOVED***
        // Check for class verification disabled
        if (verify != null && verify.equals("-Xverify:none")) {
            analysis.add(Analysis.WARN_OPT_VERIFY_NONE);
        ***REMOVED***
        // Check for max heap size not being explicitly set
        if (maxHeapSize == null) {
            analysis.add(Analysis.INFO_OPT_HEAP_MAX_MISSING);
        ***REMOVED***
        // Check if JVM signal handling disabled
        if (rs) {
            analysis.add(Analysis.WARN_OPT_RS);
        ***REMOVED***
        // Check JDK8 gc log file rotation
        if (logGc != null && useGcLogFileRotation == null) {
            analysis.add(Analysis.INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED);
        ***REMOVED***
        // Check for JDK8 gc log file overwrite
        if ((useGcLogFileRotation == null || JdkUtil.isOptionDisabled(useGcLogFileRotation)) && logGc != null
                && !logGc.contains("%") && !analysis.contains(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE)) {
            analysis.add(Analysis.WARN_OPT_JDK8_GC_LOG_FILE_OVERWRITE);
        ***REMOVED***
        // Check for the creation of the AttachListener socket file (/tmp/.java_pid<pid>) disabled
        if (JdkUtil.isOptionEnabled(disableAttachMechanism)) {
            analysis.add(Analysis.WARN_OPT_DISABLE_ATTACH_MECHANISM);
        ***REMOVED***
        // Check for ignored -XX:CompileThreshold
        if (!JdkUtil.isOptionDisabled(tieredCompilation) && compileThreshold != null) {
            analysis.add(Analysis.INFO_OPT_COMPILE_THRESHOLD_IGNORED);
        ***REMOVED***
        // Check for parallel class loading -XX:+UnsyncloadClass
        if (JdkUtil.isOptionEnabled(unsyncloadClass)) {
            analysis.add(Analysis.WARN_OPT_DIAGNOSTIC_UNSYNCLOAD_CLASS);
        ***REMOVED***
        // Check for guaranteed safepoint interval being set
        if (guaranteedSafepointInterval != null) {
            analysis.add(Analysis.WARN_OPT_DIAGNOSTICS_GUARANTEED_SAFEPOINT_INTERVAL);
        ***REMOVED***
        // Check for safepoint logging
        if (JdkUtil.isOptionEnabled(printSafepointStatistics)) {
            analysis.add(Analysis.WARN_OPT_DIAGNOSTIC_PRINT_SAFEPOINT_STATISTICS);
        ***REMOVED***
        // Check for non safepoint debugging is enabled
        if (JdkUtil.isOptionEnabled(debugNonSafepoints)) {
            analysis.add(Analysis.WARN_OPT_DIAGNOSTIC_DEBUG_NON_SAFEPOINTS);
        ***REMOVED***
        // Check ParallelGCThreads
        if (parallelGcThreads != null) {
            if (JdkUtil.isOptionEnabled(useSerialGc)) {
                analysis.add(Analysis.INFO_OPT_PARALLEL_GC_THREADS_SERIAL);
            ***REMOVED*** else if (JdkUtil.getNumberOptionValue(parallelGcThreads) == 1) {
                analysis.add(Analysis.ERROR_OPT_PARALLEL_GC_THREADS_1);
            ***REMOVED*** else {
                analysis.add(Analysis.INFO_OPT_PARALLEL_GC_THREADS);
            ***REMOVED***
        ***REMOVED***
        if (ciCompilerCount != null) {
            analysis.add(Analysis.INFO_OPT_CI_COMPILER_COUNT);
        ***REMOVED***
        // Check for -XX:MinHeapDeltaBytes=N
        if (minHeapDeltaBytes != null) {
            analysis.add(Analysis.INFO_OPT_MIN_HEAP_DELTA_BYTES);
        ***REMOVED***
        // Check for -Xdebug
        if (debug) {
            analysis.add(Analysis.INFO_OPT_DEBUG);
        ***REMOVED***
    ***REMOVED***

    public String getAdaptiveSizePolicyWeight() {
        return adaptiveSizePolicyWeight;
    ***REMOVED***

    public ArrayList<String> getAddExports() {
        return addExports;
    ***REMOVED***

    public ArrayList<String> getAddModules() {
        return addModules;
    ***REMOVED***

    public ArrayList<String> getAddOpens() {
        return addOpens;
    ***REMOVED***

    public ArrayList<String> getAgentlib() {
        return agentlib;
    ***REMOVED***

    public ArrayList<String> getAgentpath() {
        return agentpath;
    ***REMOVED***

    public String getAggressiveHeap() {
        return aggressiveHeap;
    ***REMOVED***

    public String getAggressiveOpts() {
        return aggressiveOpts;
    ***REMOVED***

    public String getAlwaysPreTouch() {
        return alwaysPreTouch;
    ***REMOVED***

    public String getAutoBoxCacheMax() {
        return autoBoxCacheMax;
    ***REMOVED***

    public String getBackgroundCompilation() {
        return backgroundCompilation;
    ***REMOVED***

    public ArrayList<String> getBootclasspath() {
        return bootclasspath;
    ***REMOVED***

    public String getCiCompilerCount() {
        return ciCompilerCount;
    ***REMOVED***

    public String getClasspath() {
        return classpath;
    ***REMOVED***

    public String getClassUnloading() {
        return classUnloading;
    ***REMOVED***

    public String getCmsClassUnloadingEnabled() {
        return cmsClassUnloadingEnabled;
    ***REMOVED***

    public String getCmsIncrementalMode() {
        return cmsIncrementalMode;
    ***REMOVED***

    public String getCmsIncrementalSafetyFactor() {
        return cmsIncrementalSafetyFactor;
    ***REMOVED***

    public String getCmsInitiatingOccupancyFraction() {
        return cmsInitiatingOccupancyFraction;
    ***REMOVED***

    public String getCmsParallelInitialMarkEnabled() {
        return cmsParallelInitialMarkEnabled;
    ***REMOVED***

    public String getCmsParallelRemarkEnabled() {
        return cmsParallelRemarkEnabled;
    ***REMOVED***

    public String getCmsScavengeBeforeRemark() {
        return cmsScavengeBeforeRemark;
    ***REMOVED***

    public String getCompileCommand() {
        return compileCommand;
    ***REMOVED***

    public String getCompileThreshold() {
        return compileThreshold;
    ***REMOVED***

    public String getCompressedClassSpaceSize() {
        return compressedClassSpaceSize;
    ***REMOVED***

    public String getConcGcThreads() {
        return concGcThreads;
    ***REMOVED***

    public String getCrashOnOutOfMemoryError() {
        return crashOnOutOfMemoryError;
    ***REMOVED***

    public String getDebugNonSafepoints() {
        return debugNonSafepoints;
    ***REMOVED***

    public String getDisableAttachMechanism() {
        return disableAttachMechanism;
    ***REMOVED***

    public String getDisableExplicitGc() {
        return disableExplicitGc;
    ***REMOVED***

    public String getDoEscapeAnalysis() {
        return doEscapeAnalysis;
    ***REMOVED***

    /**
     * Duplicate JVM options.
     * 
     * @return The duplicate JVM options, or null if no duplicates.
     */
    public String getDuplicates() {
        String duplicates = null;
        if (options != null) {
            Iterator<Entry<String, ArrayList<String>>> iteratorOptions = getOptions().entrySet().iterator();
            StringBuffer options = new StringBuffer();
            boolean firstEntry = true;
            while (iteratorOptions.hasNext()) {
                Entry<String, ArrayList<String>> option = iteratorOptions.next();
                if (!option.getKey().equals("D") && !option.getKey().equals("undefined")
                        && option.getValue().size() > 1) {
                    ArrayList<String> opt = option.getValue();
                    Iterator<String> iteratorOption = opt.iterator();
                    while (iteratorOption.hasNext()) {
                        if (!firstEntry) {
                            options.append(" ");
                        ***REMOVED***
                        options.append(iteratorOption.next());
                        firstEntry = false;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            if (options.length() > 0) {
                duplicates = options.toString();
            ***REMOVED***
        ***REMOVED***
        return duplicates;
    ***REMOVED***

    public String getEliminateLocks() {
        return eliminateLocks;
    ***REMOVED***

    public String getErrorFile() {
        return errorFile;
    ***REMOVED***

    public String getExitOnOutOfMemoryError() {
        return exitOnOutOfMemoryError;
    ***REMOVED***

    public String getExplicitGCInvokesConcurrent() {
        return explicitGCInvokesConcurrent;
    ***REMOVED***

    public String getExplicitGCInvokesConcurrentAndUnloadsClasses() {
        return explicitGCInvokesConcurrentAndUnloadsClasses;
    ***REMOVED***

    public String getExtensiveErrorReports() {
        return extensiveErrorReports;
    ***REMOVED***

    public String getFlightRecorderOptions() {
        return flightRecorderOptions;
    ***REMOVED***

    public String getG1ConcRefinementThreads() {
        return g1ConcRefinementThreads;
    ***REMOVED***

    public String getG1HeapRegionSize() {
        return g1HeapRegionSize;
    ***REMOVED***

    public String getG1HeapWastePercent() {
        return g1HeapWastePercent;
    ***REMOVED***

    public String getG1MaxNewSizePercent() {
        return g1MaxNewSizePercent;
    ***REMOVED***

    public String getG1MixedGCLiveThresholdPercent() {
        return g1MixedGCLiveThresholdPercent;
    ***REMOVED***

    public String getG1ReservePercent() {
        return g1ReservePercent;
    ***REMOVED***

    public String getG1SummarizeRSetStats() {
        return g1SummarizeRSetStats;
    ***REMOVED***

    public String getG1SummarizeRSetStatsPeriod() {
        return g1SummarizeRSetStatsPeriod;
    ***REMOVED***

    /**
     * @return The garbage collector(s) based on the JVM options.
     */
    public List<GarbageCollector> getGarbageCollectors() {
        List<GarbageCollector> garbageCollectors = new ArrayList<GarbageCollector>();
        if (JdkUtil.isOptionEnabled(useSerialGc)) {
            garbageCollectors.add(GarbageCollector.SERIAL);
            garbageCollectors.add(GarbageCollector.SERIAL_OLD);
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(useParallelOldGc)) {
            garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
            garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
        ***REMOVED*** else if (JdkUtil.isOptionEnabled(useParallelGc)) {
            garbageCollectors.add(GarbageCollector.PARALLEL_SCAVENGE);
            if (JdkUtil.isOptionDisabled(useParallelOldGc)) {
                garbageCollectors.add(GarbageCollector.SERIAL_OLD);
            ***REMOVED*** else {
                garbageCollectors.add(GarbageCollector.PARALLEL_OLD);
            ***REMOVED***
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(useConcMarkSweepGc)) {
            garbageCollectors.add(GarbageCollector.PAR_NEW);
            garbageCollectors.add(GarbageCollector.CMS);
        ***REMOVED*** else if (JdkUtil.isOptionEnabled(useParNewGc)) {
            garbageCollectors.add(GarbageCollector.PAR_NEW);
            garbageCollectors.add(GarbageCollector.SERIAL_OLD);
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(useG1Gc)) {
            garbageCollectors.add(GarbageCollector.G1);
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(useShenandoahGc)) {
            garbageCollectors.add(GarbageCollector.SHENANDOAH);
        ***REMOVED***
        if (JdkUtil.isOptionEnabled(useZGc)) {
            garbageCollectors.add(GarbageCollector.ZGC);
        ***REMOVED***
        return garbageCollectors;
    ***REMOVED***

    public String getGcLogFileSize() {
        return gcLogFileSize;
    ***REMOVED***

    public String getGcTimeRatio() {
        return gcTimeRatio;
    ***REMOVED***

    public String getGuaranteedSafepointInterval() {
        return guaranteedSafepointInterval;
    ***REMOVED***

    public String getHeapBaseMinAddress() {
        return heapBaseMinAddress;
    ***REMOVED***

    public String getHeapDumpOnOutOfMemoryError() {
        return heapDumpOnOutOfMemoryError;
    ***REMOVED***

    public String getHeapDumpPath() {
        return heapDumpPath;
    ***REMOVED***

    public String getIgnoreUnrecognizedVmOptions() {
        return ignoreUnrecognizedVmOptions;
    ***REMOVED***

    public String getInitialHeapSize() {
        return initialHeapSize;
    ***REMOVED***

    public String getInitiatingHeapOccupancyPercent() {
        return initiatingHeapOccupancyPercent;
    ***REMOVED***

    public ArrayList<String> getJavaagent() {
        return javaagent;
    ***REMOVED***

    public String getLargePageSizeInBytes() {
        return largePageSizeInBytes;
    ***REMOVED***

    public ArrayList<String> getLog() {
        return log;
    ***REMOVED***

    public String getLogFile() {
        return logFile;
    ***REMOVED***

    public String getLogGc() {
        return logGc;
    ***REMOVED***

    public String getLogVmOutput() {
        return logVmOutput;
    ***REMOVED***

    public String getManagementServer() {
        return managementServer;
    ***REMOVED***

    public String getMaxDirectMemorySize() {
        return maxDirectMemorySize;
    ***REMOVED***

    public String getMaxGcPauseMillis() {
        return maxGcPauseMillis;
    ***REMOVED***

    public String getMaxHeapFreeRatio() {
        return maxHeapFreeRatio;
    ***REMOVED***

    public String getMaxHeapSize() {
        return maxHeapSize;
    ***REMOVED***

    public String getMaxInlineLevel() {
        return maxInlineLevel;
    ***REMOVED***

    public String getMaxJavaStackTraceDepth() {
        return maxJavaStackTraceDepth;
    ***REMOVED***

    public String getMaxjitcodesize() {
        return maxjitcodesize;
    ***REMOVED***

    public String getMaxMetaspaceSize() {
        return maxMetaspaceSize;
    ***REMOVED***

    public String getMaxNewSize() {
        return maxNewSize;
    ***REMOVED***

    public String getMaxPermSize() {
        return maxPermSize;
    ***REMOVED***

    public String getMaxTenuringThreshold() {
        return maxTenuringThreshold;
    ***REMOVED***

    public String getMetaspaceSize() {
        return metaspaceSize;
    ***REMOVED***

    public String getMinHeapDeltaBytes() {
        return minHeapDeltaBytes;
    ***REMOVED***

    public String getMinHeapFreeRatio() {
        return minHeapFreeRatio;
    ***REMOVED***

    public String getNativeMemoryTracking() {
        return nativeMemoryTracking;
    ***REMOVED***

    public String getNewRatio() {
        return newRatio;
    ***REMOVED***

    public String getNewSize() {
        return newSize;
    ***REMOVED***

    public String getNumberOfGcLogFiles() {
        return numberOfGcLogFiles;
    ***REMOVED***

    public String getOmitStackTraceInFastThrow() {
        return omitStackTraceInFastThrow;
    ***REMOVED***

    public String getOnError() {
        return onError;
    ***REMOVED***

    public String getOnOutOfMemoryError() {
        return onOutOfMemoryError;
    ***REMOVED***

    public String getOptimizeStringConcat() {
        return optimizeStringConcat;
    ***REMOVED***

    public Map<String, ArrayList<String>> getOptions() {
        return options;
    ***REMOVED***

    public String getParallelGcThreads() {
        return parallelGcThreads;
    ***REMOVED***

    public String getParallelRefProcEnabled() {
        return parallelRefProcEnabled;
    ***REMOVED***

    public String getPerfDisableSharedMem() {
        return perfDisableSharedMem;
    ***REMOVED***

    public String getPermSize() {
        return permSize;
    ***REMOVED***

    public String getPrintAdaptiveSizePolicy() {
        return printAdaptiveSizePolicy;
    ***REMOVED***

    public String getPrintClassHistogram() {
        return printClassHistogram;
    ***REMOVED***

    public String getPrintClassHistogramAfterFullGc() {
        return printClassHistogramAfterFullGc;
    ***REMOVED***

    public String getPrintClassHistogramBeforeFullGc() {
        return printClassHistogramBeforeFullGc;
    ***REMOVED***

    public String getPrintFlagsFinal() {
        return printFlagsFinal;
    ***REMOVED***

    public String getPrintFLSStatistics() {
        return printFLSStatistics;
    ***REMOVED***

    public String getPrintGc() {
        return printGc;
    ***REMOVED***

    public String getPrintGcApplicationConcurrentTime() {
        return printGcApplicationConcurrentTime;
    ***REMOVED***

    public String getPrintGcApplicationStoppedTime() {
        return printGcApplicationStoppedTime;
    ***REMOVED***

    public String getPrintGcDateStamps() {
        return printGcDateStamps;
    ***REMOVED***

    public String getPrintGcDetails() {
        return printGcDetails;
    ***REMOVED***

    public String getPrintGcTaskTimeStamps() {
        return printGcTaskTimeStamps;
    ***REMOVED***

    public String getPrintGcTimeStamps() {
        return printGcTimeStamps;
    ***REMOVED***

    public String getPrintHeapAtGc() {
        return printHeapAtGc;
    ***REMOVED***

    public String getPrintHeapAtGC() {
        return printHeapAtGc;
    ***REMOVED***

    public String getPrintPromotionFailure() {
        return printPromotionFailure;
    ***REMOVED***

    public String getPrintReferenceGc() {
        return printReferenceGc;
    ***REMOVED***

    public String getPrintSafepointStatistics() {
        return printSafepointStatistics;
    ***REMOVED***

    public String getPrintStringDeduplicationStatistics() {
        return printStringDeduplicationStatistics;
    ***REMOVED***

    public String getPrintTenuringDistribution() {
        return printTenuringDistribution;
    ***REMOVED***

    public String getReservedCodeCacheSize() {
        return reservedCodeCacheSize;
    ***REMOVED***

    public ArrayList<String> getRunjdwp() {
        return runjdwp;
    ***REMOVED***

    public String getShenandoahGcHeuristics() {
        return shenandoahGcHeuristics;
    ***REMOVED***

    public String getShenandoahMinFreeThreshold() {
        return shenandoahMinFreeThreshold;
    ***REMOVED***

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
                if (property.matches("-Dsun.rmi.dgc.client.gcInterval=\\d{1,***REMOVED***")) {
                    sunRmiDgcClientGcIntervalOption = property;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return sunRmiDgcClientGcIntervalOption;
    ***REMOVED***

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
                if (property.matches("-Dsun.rmi.dgc.server.gcInterval=\\d{1,***REMOVED***")) {
                    sunRmiDgcServerGcIntervalOption = property;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return sunRmiDgcServerGcIntervalOption;
    ***REMOVED***

    public String getSurvivorRatio() {
        return survivorRatio;
    ***REMOVED***

    public ArrayList<String> getSystemProperties() {
        return systemProperties;
    ***REMOVED***

    public String getTargetSurvivorRatio() {
        return targetSurvivorRatio;
    ***REMOVED***

    public String getThreadStackSize() {
        return threadStackSize;
    ***REMOVED***

    public String getTier2CompileThreshold() {
        return tier2CompileThreshold;
    ***REMOVED***

    public String getTier3CompileThreshold() {
        return tier3CompileThreshold;
    ***REMOVED***

    public String getTier4CompileThreshold() {
        return tier4CompileThreshold;
    ***REMOVED***

    public String getTieredCompilation() {
        return tieredCompilation;
    ***REMOVED***

    public String getTraceClassLoading() {
        return traceClassLoading;
    ***REMOVED***

    public String getTraceClassUnloading() {
        return traceClassUnloading;
    ***REMOVED***

    public ArrayList<String> getUndefined() {
        return undefined;
    ***REMOVED***

    public String getUnlockDiagnosticVmOptions() {
        return unlockDiagnosticVmOptions;
    ***REMOVED***

    public String getUnlockExperimentalVmOptions() {
        return unlockExperimentalVmOptions;
    ***REMOVED***

    public String getUnsyncloadClass() {
        return unsyncloadClass;
    ***REMOVED***

    public String getUseAdaptiveSizePolicy() {
        return useAdaptiveSizePolicy;
    ***REMOVED***

    public String getUseBiasedLocking() {
        return useBiasedLocking;
    ***REMOVED***

    public String getUseCGroupMemoryLimitForHeap() {
        return useCGroupMemoryLimitForHeap;
    ***REMOVED***

    public String getUseCmsInitiatingOccupancyOnly() {
        return useCmsInitiatingOccupancyOnly;
    ***REMOVED***

    public String getUseCodeCacheFlushing() {
        return useCodeCacheFlushing;
    ***REMOVED***

    public String getUseCompressedClassPointers() {
        return useCompressedClassPointers;
    ***REMOVED***

    public String getUseCompressedOops() {
        return useCompressedOops;
    ***REMOVED***

    public String getUseConcMarkSweepGc() {
        return useConcMarkSweepGc;
    ***REMOVED***

    public String getUseDynamicNumberOfCompilerThreads() {
        return useDynamicNumberOfCompilerThreads;
    ***REMOVED***

    public String getUseDynamicNumberOfGcThreads() {
        return useDynamicNumberOfGcThreads;
    ***REMOVED***

    public String getUseFastAccessorMethods() {
        return useFastAccessorMethods;
    ***REMOVED***

    public String getUseFastUnorderedTimeStamps() {
        return useFastUnorderedTimeStamps;
    ***REMOVED***

    public String getUseG1Gc() {
        return useG1Gc;
    ***REMOVED***

    public String getUseGcLogFileRotation() {
        return useGcLogFileRotation;
    ***REMOVED***

    public String getUseGcOverheadLimit() {
        return useGcOverheadLimit;
    ***REMOVED***

    public String getUseHugeTLBFS() {
        return useHugeTLBFS;
    ***REMOVED***

    public String getUseLargePages() {
        return useLargePages;
    ***REMOVED***

    public String getUseMembar() {
        return useMembar;
    ***REMOVED***

    public String getUseNUMA() {
        return useNUMA;
    ***REMOVED***

    public String getUseParallelGc() {
        return useParallelGc;
    ***REMOVED***

    public String getUseParallelOldGc() {
        return useParallelOldGc;
    ***REMOVED***

    public String getUseParNewGc() {
        return useParNewGc;
    ***REMOVED***

    public String getUsePerfData() {
        return usePerfData;
    ***REMOVED***

    public String getUseSerialGc() {
        return useSerialGc;
    ***REMOVED***

    public String getUseShenandoahGc() {
        return useShenandoahGc;
    ***REMOVED***

    public String getUseSplitVerifier() {
        return useSplitVerifier;
    ***REMOVED***

    public String getUseStringDeduplication() {
        return useStringDeduplication;
    ***REMOVED***

    public String getUseTlab() {
        return useTlab;
    ***REMOVED***

    public String getUseVmInterruptibleIo() {
        return useVmInterruptibleIo;
    ***REMOVED***

    public String getVerify() {
        return verify;
    ***REMOVED***

    public boolean isBatch() {
        return batch;
    ***REMOVED***

    public boolean isClient() {
        return client;
    ***REMOVED***

    public boolean isComp() {
        return comp;
    ***REMOVED***

    public boolean isD64() {
        return d64;
    ***REMOVED***

    public boolean isDebug() {
        return debug;
    ***REMOVED***

    public boolean isNoclassgc() {
        return noclassgc;
    ***REMOVED***

    public boolean isNoverify() {
        return noverify;
    ***REMOVED***

    public boolean isRs() {
        return rs;
    ***REMOVED***

    public boolean isServer() {
        return server;
    ***REMOVED***

    public boolean isVerboseClass() {
        return verboseClass;
    ***REMOVED***

    public boolean isVerboseGc() {
        return verboseGc;
    ***REMOVED***

    public boolean isxInt() {
        return xInt;
    ***REMOVED***

    /**
     * @return true if JVM options result in using the default garbage collector, false otherwise.
     */
    private final boolean useDefaultCollector() {
        boolean useDefaultCollector = false;
        if (useSerialGc == null && useConcMarkSweepGc == null && useParallelGc == null && useG1Gc == null
                && useShenandoahGc == null && useZGc == null) {
            useDefaultCollector = true;
        ***REMOVED***
        return useDefaultCollector;
    ***REMOVED***
***REMOVED***

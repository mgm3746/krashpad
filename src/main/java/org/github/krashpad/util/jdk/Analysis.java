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
package org.github.krashpad.util.jdk;

import org.github.krashpad.util.Constants;
import org.github.krashpad.util.ErrUtil;

/**
 * Analysis constants.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public enum Analysis {

    /**
     * Property key for a crash in BufferBlob::flush_icache_stub.
     */
    ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB("error.bufferblob.flush_icache_stub"),

    /**
     * Property key for a crash when executing compiled java code.
     */
    ERROR_COMPILED_JAVA_CODE("error.compiled.java.code"),

    /**
     * Property key for a crash in CompilerThread.
     */
    ERROR_COMPILER_THREAD("error.compiler.thread"),

    /**
     * Property key for a crash due to multiple threads access DirectByteBuffer at the same time.
     */
    ERROR_DIRECT_BYTE_BUFFER_CONTENTION("error.direct.byte.buffer.contention"),

    /**
     * Property key for explicit gc disabled running JBoss EAP7.
     */
    ERROR_EXPLICIT_GC_DISABLED_EAP7("error.explicit.gc.disabled.eap7"),

    /**
     * Property key for a crash due to a temporary font file being removed from java.io.tmpdir.
     */
    ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE("error.freetype.font.scaler.get.glyph.image.native"),

    /**
     * Property key for heap + metaspace &gt; physical memory.
     */
    ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY("error.heap.plus.metaspace.gt.physical.memory"),

    /**
     * Property key for a crash in JDK8 in Deflater when a file is attempting to be modified while Java has it open.
     */
    ERROR_JDK8_DEFLATER_CONTENTION("error.jdk8.deflater.contention"),

    /**
     * Property key for a crash on RHEL7 where a power8 rpm is deployed on power9. Power8 support is through the
     * rhel-7-for-power-le-rpms repo (least release 7.9). Power9 support is through the rhel-7-for-power-9-rpms repo
     * (last release 7.6).
     */
    ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9("error.jdk8.rhel7.power8.rpm.on.power9"),

    /**
     * Property key for ShenandoahConcurrentMark::mark_loop_work.
     */
    ERROR_JDK8_SHENANDOAH_MARK_LOOP_WORK("error.jdk8.shenandoah.mark.loop.work"),

    /**
     * Property key for ShenadoahRootUpdater bug fixed in OpenJDK8 u282.
     */
    ERROR_JDK8_SHENANDOAH_ROOT_UPDATER("error.jdk8.shenandoah.root.updater"),

    /**
     * Property key for a crash in JDK8 in ZipFile when a file is attempting to be modified while Java has it open.
     */
    ERROR_JDK8_ZIPFILE_CONTENTION("error.jdk8.zipfile.contention"),

    /**
     * Property key for a crash in JFR pd_get_top_frame_for_profiling.
     */
    ERROR_JFR_PD_GET_TOP_FRAME("error.jfr.pd.get.top.frame"),

    /**
     * Property key for a crash in JNA code.
     */
    ERROR_JNA("error.jna"),

    /**
     * Property key for a crash in JNA code on a Red Hat JDK.
     */
    ERROR_JNA_RH("error.jna.rh"),

    /**
     * Property key for a crash in jvm.dll.
     */
    ERROR_JVM_DLL("error.jvm.dll"),

    /**
     * Property key for a crash in org.apache.activemq.artemis.nativo.jlibaio.LibaioContext.done() method.
     */
    ERROR_LIBAIO_CONTEXT_DONE("error.libaio.context.done"),

    /**
     * Property key for a crash in libjvm.so.
     */
    ERROR_LIBJVM_SO("error.libjvm.so"),

    /**
     * Property key for crash caused trying to dereference a null pointer.
     */
    ERROR_NULL_POINTER("error.null.pointer"),

    /**
     * Property key for generic insufficient physical memory.
     */
    ERROR_OOME("error.oome"),

    /**
     * Property key for insufficient physical memory due to an external process.
     */
    ERROR_OOME_EXTERNAL("error.oome.external"),

    /**
     * Property key for OutOfMemoryError Java heap space.
     */
    ERROR_OOME_JAVA_HEAP("error.oome.java.heap"),

    /**
     * Property key for insufficient physical memory due to the JVM process.
     */
    ERROR_OOME_JVM("error.oome.jvm"),

    /**
     * Property key for failing to allocate memory due to a resource limit (rlimit).
     */
    ERROR_OOME_LIMIT("error.oome.limit"),

    /**
     * Property key for failing to allocate memory due to a resource limit (rlimit) being reached or the native heap
     * reaching the java heap base address.
     */
    ERROR_OOME_LIMIT_OOPS("error.oome.limit.oops"),

    /**
     * Property key for generic insufficient physical memory with oops enabled.
     */
    ERROR_OOME_OOPS("error.oome.oops"),

    /**
     * Property key for the JVM failing to start due to enough physical memory.
     */
    ERROR_OOME_STARTUP("error.oome.startup"),

    /**
     * Property key for the JVM failing to start due to enough physical memory when JVM memory &lt; 50% memory.
     */
    ERROR_OOME_STARTUP_EXTERNAL("error.oome.startup.external"),

    /**
     * Property key for the JVM failing to start due to a resource limit (rlimit).
     */
    ERROR_OOME_STARTUP_LIMIT("error.oome.startup.limit"),

    /**
     * Property key for the JVM failing to start due to a resource limit (rlimit) being reached or the native heap
     * reaching the java heap base address.
     */
    ERROR_OOME_STARTUP_LIMIT_OOPS("error.oome.startup.limit.oops"),

    /**
     * Property key for remote debugging enabled.
     */
    ERROR_OPT_REMOTE_DEBUGGING_ENABLED("error.opt.remote.debugging.enabled"),

    /**
     * Property key for error calling pthread_getcpuclockid
     */
    ERROR_PTHREAD_GETCPUCLOCKID("error.pthread.getcpuclockid"),

    /**
     * Property key for stack free space greater than stack size.
     */
    ERROR_STACK_FREESPACE_GT_STACK_SIZE("error.stack.freespace.gt.stack.size"),

    /**
     * Property key for StackOverflowError.
     */
    ERROR_STACKOVERFLOW("error.stackoverflow"),

    /**
     * Property key for crash in ~BufferBlob::StubRoutines.
     */
    ERROR_STUBROUTINES("error.stubroutines"),

    /**
     * Property key for AdoptOpenJDK build of OpenJDK.
     */
    INFO_ADOPTOPENJDK_POSSIBLE("info.adoptopenjdk.possible"),

    /**
     * Property key for cgroup environment.
     */
    INFO_CGROUP("info.cgroup"),

    /**
     * Property key for cgroup memory limit.
     */
    INFO_CGROUP_MEMORY_LIMIT("info.cgroup.memory.limit"),

    /**
     * Property key for a JDK that is more than 1 year older than the latest release.
     */
    INFO_JDK_ANCIENT("info.jdk.ancient"),

    /**
     * Property key for possible Java Foreign Function Interface (JFFI) usage.
     */
    INFO_JFFI("info.jffi"),

    /**
     * Property key for possible Java Native Interface (JNA) usage.
     */
    INFO_JNA("info.jna"),

    /**
     * Property key for crash on JVM startup.
     */
    INFO_JVM_STARTUP_FAILS("info.jvm.startup.fails"),

    /**
     * Property key for JVM memory not equal to system memory.
     * 
     * TODO: Remove this? It's basically a duplicate of INFO_CGROUP_MEMORY_LIMIT?
     */
    INFO_MEMORY_JVM_NE_SYSTEM("info.memory.jvm.ne.system"),

    /**
     * Property key for -XX:CMSInitiatingOccupancyFraction without -XX:+UseCMSInitiatingOccupancyOnly.
     */
    INFO_OPT_CMS_INIT_OCCUPANCY_ONLY_MISSING("info.opt.cms.init.occupancy.only.missing"),

    /**
     * Property key for compressed class pointers size set (-XX:CompressedClassSpaceSize) with compressed class pointers
     * disabled (-XX:+UseCompressedClassPointers).
     */
    INFO_OPT_COMP_CLASS_SIZE_COMP_CLASS_DISABLED("info.opt.comp.class.size.comp.class.disabled"),

    /**
     * Property key for compressed class pointers size set (-XX:CompressedClassSpaceSize) with compressed object
     * references disabled (-XX:-UseCompressedOops).
     */
    INFO_OPT_COMP_CLASS_SIZE_COMP_OOPS_DISABLED("info.opt.comp.class.size.comp.oops.disabled"),

    /**
     * Property key for -XX:-ExplicitGCInvokesConcurrentAndUnloadsClasses in combination with -XX:+DisableExplicitGC.
     */
    INFO_OPT_CRUFT_EXP_GC_INV_CON_AND_UNL_CLA("info.opt.cruft.exp.gc.inv.con.and.unl.cla"),

    /**
     * Property key for -XX:+UnlockDiagnosticVMOptions.
     */
    INFO_OPT_DIAGNOSTIC_VM_OPTIONS_ENABLED("info.opt.diagnostic.vm.options.enabled"),

    /**
     * Property key for -XX:+EliminateLocks.
     */
    INFO_OPT_ELIMINATE_LOCKS_ENABLED("info.opt.eliminate.locks.enabled"),

    /**
     * Property key for experimental jvm options enabled with <code>-XX:+UnlockExperimentalVMOptions</code>.
     */
    INFO_OPT_EXPERIMENTAL_VM_OPTIONS_ENABLED("info.opt.experimental.vm.options.enabled"),

    /**
     * Property key for summarized remembered set processing output.
     */
    INFO_OPT_G1_SUMMARIZE_RSET_STATS_OUTPUT("info.opt.g1.summarize.rset.stats.output"),

    /**
     * Property key for heap dump on out of memory error option missing.
     */
    INFO_OPT_HEAP_DUMP_ON_OOME_MISSING("info.opt.heap.dump.on.oome.missing"),

    /**
     * Property key for heap dump filename specified.
     */
    INFO_OPT_HEAP_DUMP_PATH_FILENAME("info.opt.heap.dump.path.filename"),

    /**
     * Property key for heap dumps enabled without specifying a location with the -XX:HeapDumpPath option.
     */
    INFO_OPT_HEAP_DUMP_PATH_MISSING("info.opt.heap.dump.path.missing"),

    /**
     * Property key for the maximum heap size not being explicitly set.
     */
    INFO_OPT_HEAP_MAX_MISSING("info.opt.heap.max.missing"),

    /**
     * Property key for min heap not equal to max heap.
     */
    INFO_OPT_HEAP_MIN_NOT_EQUAL_MAX("info.opt.heap.min.not.equal.max"),

    /**
     * Property key for instrumentation.
     */
    INFO_OPT_INSTRUMENTATION("info.opt.instrumentation"),

    /**
     * Property key for missing gc* to output details at gc needed for analysis.
     */
    INFO_OPT_JDK11_PRINT_GC_DETAILS_MISSING("info.opt.jdk11.print.gc.details.missing"),

    /**
     * Property key for GC log file rotation disabled (-XX:-UseGCLogFileRotation).
     */
    INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_DISABLED("info.opt.jdk8.gc.log.file.rotation.disabled"),

    /**
     * Property key for GC log file rotation not enabled in JDK8 (-XX:+UseGCLogFileRotation -XX:GCLogFileSize=N
     * -XX:NumberOfGCLogFiles=N).
     */
    INFO_OPT_JDK8_GC_LOG_FILE_ROTATION_NOT_ENABLED("info.opt.jdk8.gc.log.file.rotation.not.enabled"),

    /**
     * Property key for disabling Adaptive Resize Policy output with -XX:-PrintAdaptiveSizePolicy.
     */
    INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_DISABLED("info.opt.jdk8.print.adaptive.resize.plcy.disabled"),

    /**
     * Property key for enabling Adaptive Resize Policy output with -XX:+PrintAdaptiveSizePolicy.
     */
    INFO_OPT_JDK8_PRINT_ADAPTIVE_RESIZE_PLCY_ENABLED("info.opt.jdk8.print.adaptive.resize.plcy.enabled"),

    /**
     * Property key for CMS Free List Space statistics being output.
     */
    INFO_OPT_JDK8_PRINT_FLS_STATISTICS("info.opt.jdk8.print.fls.statistics"),

    /**
     * Property key for missing -XX:+PrintGCDetails to output details at gc needed for analysis.
     */
    INFO_OPT_JDK8_PRINT_GC_DETAILS_MISSING("info.opt.jdk8.print.gc.details.missing"),

    /**
     * Property key for printing additional heap data (-XX:+PrintHeapAtGC).
     */
    INFO_OPT_JDK8_PRINT_HEAP_AT_GC("info.opt.jdk8.print.heap.at.gc"),

    /**
     * Property key for -XX:+PrintPromotionFailure.
     */
    INFO_OPT_JDK8_PRINT_PROMOTION_FAILURE("info.opt.jdk8.print.promotion.failure"),

    /**
     * Property key for -XX:+PrintReferenceGC.
     */
    INFO_OPT_JDK8_PRINT_REFERENCE_GC_ENABLED("info.opt.jdk8.print.reference.gc.enabled"),

    /**
     * Property key for -XX:+PrintStringDeduplicationStatistics.
     */
    INFO_OPT_JDK8_PRINT_STRING_DEDUP_STATS_ENABLED("info.opt.jdk8.print.string.dedup.stats.enabled"),

    /**
     * Property key for outputting tenuring distribution information (-XX:+PrintTenuringDistribution).
     */
    INFO_OPT_JDK8_PRINT_TENURING_DISTRIBUTION("info.opt.jdk8.print.tenuring.distribution"),

    /**
     * Property key for min heap not equal to max heap.
     */
    INFO_OPT_JFR("info.opt.jfr"),

    /**
     * Property key for JMX enabled with -Dcom.sun.management.jmxremote or -XX:+ManagementServer.
     */
    INFO_OPT_JMX_ENABLED("info.opt.jmx.enabled"),

    /**
     * Property key for -XX:LargePageSizeInBytes being extraneous on Linux.
     */
    INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_LINUX("info.opt.large.page.size.in.bytes.linux"),

    /**
     * Property key for -XX:LargePageSizeInBytes being extraneous on Linux.
     */
    INFO_OPT_LARGE_PAGE_SIZE_IN_BYTES_WINDOWS("info.opt.large.page.size.in.bytes.windows"),

    /**
     * Property key for maximum permanent generation size being set.
     */
    INFO_OPT_MAX_PERM_SIZE("info.opt.max.perm.size"),

    /**
     * Property key for overriding the number of times an object is copied between survivor spaces being set with
     * -XX:MaxTenuringThreshold=N (0-15). 0 = disabled. 15 (default) = promote when the survivor space fills. Unless
     * testing has shown this improves performance, consider removing this option to allow the default value to be
     * applied.
     */
    INFO_OPT_MAX_TENURING_OVERRIDE("info.opt.max.tenuring.override"),

    /**
     * Property key for metaspace initial and/or max size being set.
     */
    INFO_OPT_METASPACE("info.opt.metaspace"),

    /**
     * Property key for metaspace including only class metadata.
     */
    INFO_OPT_METASPACE_CLASS_METADATA("info.opt.metaspace.class.metadata"),

    /**
     * Property key for metaspace including class metadata and compressed class space.
     */
    INFO_OPT_METASPACE_CLASS_METADATA_AND_COMP_CLASS_SPACE("info.opt.metaspace.class.metadata.and.comp.class.space"),

    /**
     * Property key for native library.
     */
    INFO_OPT_NATIVE("info.opt.native"),

    /**
     * Property key for young space &gt;= old space.
     */
    INFO_OPT_NEW_RATIO_INVERTED("info.opt.new.ratio.inverted"),

    /**
     * Property key for performance data disabled.
     */
    INFO_OPT_PERF_DATA_DISABLED("info.opt.perf.data.disabled"),

    /**
     * Property key for initial permanent generation size being set.
     */
    INFO_OPT_PERM_SIZE("info.opt.perm.size"),

    /**
     * Property key for printing application concurrent time (-XX:+PrintGCApplicationConcurrentTime).
     */
    INFO_OPT_PRINT_GC_APPLICATION_CONCURRENT_TIME("info.opt.print.gc.application.concurrent.time"),

    /**
     * Property key for -Dsun.rmi.dgc.client.gcInterval.redundant in combination with -XX:+DisableExplicitGC.
     */
    INFO_OPT_RMI_DGC_CLIENT_GCINTERVAL_REDUNDANT("info.opt.rmi.dgc.client.gcInterval.redundant"),

    /**
     * Property key for -Dsun.rmi.dgc.server.gcInterval.redundant in combination with -XX:+DisableExplicitGC.
     */
    INFO_OPT_RMI_DGC_SERVER_GCINTERVAL_REDUNDANT("info.opt.rmi.dgc.server.gcInterval.redundant"),

    /**
     * Property key for -server flag on 64-bit.
     */
    INFO_OPT_SERVER_REDUNDANT("info.opt.server.redundant"),

    /**
     * Property key for the survivor ratio being set with -XX:SurvivorRatio=N (e.g. -XX:SurvivorRatio=6 ).
     * 
     */
    INFO_OPT_SURVIVOR_RATIO("info.opt.survivor.ratio"),

    /**
     * Property key for the target survivor ratio being set with XX:TargetSurvivorRatio=N (e.g.
     * -XX:TargetSurvivorRatio=90).
     * 
     */
    INFO_OPT_SURVIVOR_RATIO_TARGET("info.opt.survivor.ratio.target"),

    /**
     * Property key for -XX:+TieredCompilation.
     */
    INFO_OPT_TIERED_COMPILATION_ENABLED("info.opt.tiered.compilation.enabled"),

    /**
     * Property key for outputting class unloading information (-XX:+TraceClassUnloading).
     */
    INFO_OPT_TRACE_CLASS_UNLOADING("info.opt.trace.class.unloading"),

    /**
     * Property key for undefined JVM option(s).
     */
    INFO_OPT_UNDEFINED("info.opt.undefined"),

    /**
     * Property key for class loading logging (sent to standard out) enabled with -verbose:class.
     */
    INFO_OPT_VERBOSE_CLASS("info.opt.verbose.class"),

    /**
     * Property key for Red Hat build of OpenJDK on CentOS.
     */
    INFO_RH_BUILD_CENTOS("info.rh.build.centos"),

    /**
     * Property key for Red Hat build of OpenJDK Linux zip install.
     */
    INFO_RH_BUILD_LINUX_ZIP("info.rh.build.linux.zip"),

    /**
     * Property key for a JDK that is not a RH build.
     */
    INFO_RH_BUILD_NOT("info.rh.build.not"),

    /**
     * Property key for a JDK that is possibly a RH build.
     */
    INFO_RH_BUILD_POSSIBLE("info.rh.build.possible"),

    /**
     * Property key for Red Hat build of OpenJDK from rpm (e.g. jlink custom runtime).
     */
    INFO_RH_BUILD_RPM_BASED("info.rh.build.rpm.based"),

    /**
     * Property key for Red Hat build of OpenJDK rpm install.
     */
    INFO_RH_BUILD_RPM_INSTALL("info.rh.build.rpm.install"),

    /**
     * Property key for Red Hat build of OpenJDK Windows zip install.
     */
    INFO_RH_BUILD_WINDOWS_ZIP("info.rh.build.windows.zip"),

    /**
     * Property key for BUS_ADDRERR.
     */
    INFO_SIGCODE_BUS_ADDERR("info.sigcode.bus.adrerr"),

    /**
     * Property key for BUS_ADDR crash on linux.
     */
    INFO_SIGCODE_BUS_ADDERR_LINUX("info.sigcode.bus.adrerr.linux"),

    /**
     * Property key for BUS_ADRALN.
     */
    INFO_SIGCODE_BUS_ADRALN("info.sigcode.bus.adraln"),

    /**
     * Property key for BUS_OBJERR.
     */
    INFO_SIGCODE_BUS_OBJERR("info.sigcode.bus.objerr"),

    /**
     * Property key for ILL_ILLOPN
     */
    INFO_SIGCODE_ILL_ILLOPN("info.sigcode.ill.illopn"),

    /**
     * Property key for SEGV_ACCERR.
     */
    INFO_SIGCODE_SEGV_ACCERR("info.sigcode.segv.accerr"),

    /**
     * Property key for SEGV_MAPERR.
     */
    INFO_SIGCODE_SEGV_MAPERR("info.sigcode.segv.maperr"),

    /**
     * Property key for SI_KERNEL crash.
     */
    INFO_SIGCODE_SI_KERNEL("info.sigcode.si.kernel"),

    /**
     * Property key for SI_USER crash.
     */
    INFO_SIGCODE_SI_USER("info.sigcode.si.user"),

    /**
     * Property key for ACCESS_VIOLATION crash.
     */
    INFO_SIGNO_EXCEPTION_ACCESS_VIOLATION("info.signo.exception.access.violation"),

    /**
     * Property key for SIGBUS crash.
     */
    INFO_SIGNO_SIGBUS("info.signo.sigbus"),

    /**
     * Property key for SIGILL crash.
     */
    INFO_SIGNO_SIGILL("info.signo.sigill"),

    /**
     * Property key for SIGSEGV crash.
     */
    INFO_SIGNO_SIGSEGV("info.signo.sigsegv"),

    /**
     * Property key for the stack not containing any VM code.
     */
    INFO_STACK_NO_VM_CODE("info.stack.no.vm.code"),

    /**
     * Property key for AWS block storage.
     */
    INFO_STORAGE_AWS("info.storage.aws"),

    /**
     * Property key for NFS storage.
     */
    INFO_STORAGE_NFS("info.storage.nfs"),

    /**
     * Property key for unknown storage.
     */
    INFO_STORAGE_UNKNOWN("info.storage.unknown"),

    /**
     * Property key for swapping disabled.
     */
    INFO_SWAP_DISABLED("info.swap.disabled"),

    /**
     * Property key for 5% &lt; swapping &lt; 20%.
     */
    INFO_SWAPPING("info.swapping"),

    /**
     * Property key for truncated fatal error log.
     */
    INFO_TRUNCATED("info.truncated"),

    /**
     * Property key for CMS collector running in incremental mode.
     */
    WARN_CMS_INCREMENTAL_MODE("warn.cms.incremental.mode"),

    /**
     * Property key for no evidence the JDK debug symbols are installed.
     */
    WARN_DEBUG_SYMBOLS("warn.jdk.debug.symbols"),

    /**
     * Property key for a JDK that is not the latest JDK release.
     */
    WARN_JDK_NOT_LATEST("warn.jdk.not.latest"),

    /**
     * Property key for a JDK that is not a Long Term Support (LTS) version.
     */
    WARN_JDK_NOT_LTS("warn.jdk.not.lts"),

    /**
     * Property key for adaptive size policy disabled with -XX:-UseAdaptiveSizePolicy.
     */
    WARN_OPT_ADAPTIVE_SIZE_POLICY_DISABLED("warn.opt.adaptive.size.policy.disabled"),

    /**
     * Property key for biased locking disabled (-XX:-UseBiasedLocking).
     */
    WARN_OPT_BIASED_LOCKING_DISABLED("warn.opt.biased.locking.disabled"),

    /**
     * Property key for disabling compiling bytecode in the background.
     */
    WARN_OPT_BYTECODE_BACK_COMP_DISABLED("warn.opt.bytecode.back.comp.disabled"),

    /**
     * Property key for bytecode compilation disabled.
     */
    WARN_OPT_BYTECODE_COMPILE_DISABLED("warn.opt.bytecode.compile.disabled"),

    /**
     * Property key for precompiling bytecode.
     */
    WARN_OPT_BYTECODE_COMPILE_FIRST_INVOCATION("warn.opt.bytecode.compile.first.invocation"),

    /**
     * Property key for -XX:+UseCGroupMemoryLimitForHeap.
     */
    WARN_OPT_CGROUP_MEMORY_LIMIT("warn.opt.cgroup.memory.limit"),

    /**
     * Property key for class unloading disabled with -XX:-ClassUnloading.
     */
    WARN_OPT_CLASS_UNLOADING_DISABLED("warn.opt.class.unloading.disabled"),

    /**
     * Property key for CMS collector class unloading disabled.
     */
    WARN_OPT_CMS_CLASS_UNLOADING_DISABLED("warn.opt.cms.class.unloading.disabled"),

    /**
     * Property key for specifying both the CMS collector running in incremental mode and an initiating occupancy
     * fraction.
     */
    WARN_OPT_CMS_INC_MODE_WITH_INIT_OCCUP_FRACT("warn.opt.cms.inc.mode.with.init.occup.fract"),

    /**
     * Property key for PAR_NEW collector disabled.
     */
    WARN_OPT_CMS_PAR_NEW_DISABLED("warn.opt.cms.par.new.disabled"),

    /**
     * Property key for multi-threaded CMS initial mark disabled with -XX:-CMSParallelInitialMarkEnabled.
     */
    WARN_OPT_CMS_PARALLEL_INITIAL_MARK_DISABLED("warn.opt.cms.parallel.initial.mark.disabled"),

    /**
     * Property key for multi-threaded CMS remark disabled with -XX:-CMSParallelRemarkEnabled.
     */
    WARN_OPT_CMS_PARALLEL_REMARK_DISABLED("warn.opt.cms.parallel.remark.disabled"),

    /**
     * Property key for compressed class pointers disabled (-XX:-UseCompressedClassPointers), and heap &lt; 32G.
     */
    WARN_OPT_COMP_CLASS_DISABLED_HEAP_LT_32G("warn.opt.comp.class.disabled.heap.lt.32g"),

    /**
     * Property key for compressed class pointers disabled (-XX:-UseCompressedClassPointers), and heap size unknown.
     */
    WARN_OPT_COMP_CLASS_DISABLED_HEAP_UNK("warn.opt.comp.class.disabled.heap.unk"),

    /**
     * Property key for compressed class pointers enabled (-XX:+UseCompressedClassPointers), and heap &gt;= 32G.
     */
    WARN_OPT_COMP_CLASS_ENABLED_HEAP_GT_32G("warn.opt.comp.class.enabled.heap.gt.32g"),

    /**
     * Property key for compressed class pointers space size set (-XX:CompressedClassSpaceSize), and heap &gt;= 32G.
     */
    WARN_OPT_COMP_CLASS_SIZE_HEAP_GT_32G("warn.opt.comp.class.size.heap.gt.32g"),

    /**
     * Property key for compressed object references disabled (-XX:-UseCompressedOops), and heap &lt; 32G.
     */
    WARN_OPT_COMP_OOPS_DISABLED_HEAP_LT_32G("warn.opt.comp.oops.disabled.heap.lt.32g"),

    /**
     * Property key for compressed object references disabled (-XX:-UseCompressedOops), and heap size unknown.
     */
    WARN_OPT_COMP_OOPS_DISABLED_HEAP_UNK("warn.opt.comp.oops.disabled.heap.unk"),

    /**
     * Property key for compressed object references enabled (-XX:+UseCompressedOops), and heap &gt;= 32G.
     */
    WARN_OPT_COMP_OOPS_ENABLED_HEAP_GT_32G("warn.opt.comp.oops.enabled.heap.gt.32g"),

    /**
     * Property key for performance data written to disk (/tmp/hsperfdata*) in a cloud environment.
     */
    WARN_OPT_CONTAINER_PERF_DATA_DISK("warn.opt.container.perf.data.disk"),

    /**
     * Property key for explicit garbage collection disabled.
     */
    WARN_OPT_EXPLICIT_GC_DISABLED("warn.opt.explicit.gc.disabled"),

    /**
     * Property key for explicit garbage collection disabled and specifying concurrent collections.
     */
    WARN_OPT_EXPLICIT_GC_DISABLED_CONCURRENT("warn.opt.explicit.gc.disabled.concurrent"),

    /**
     * Property key for explicit garbage not collected concurrently.
     */
    WARN_OPT_EXPLICIT_GC_NOT_CONCURRENT("warn.opt.explicit.gc.not.concurrent"),

    /**
     * Property key for fast unordered timestamps (experimental) enabled with
     * <code>-XX:+UseFastUnorderedTimeStamps</code>.
     */
    WARN_OPT_FAST_UNORDERED_TIMESTAMPS("warn.opt.fast.unordered.timestamps"),

    /**
     * Property key for the occupancy threshold for a region to be considered as a candidate region for a G1_CLEANUP
     * collection being specified with <code>-XX:G1MixedGCLiveThresholdPercent=NN</code>.
     */
    WARN_OPT_G1_MIXED_GC_LIVE_THRSHOLD_PRCNT("warn.opt.g1.mixed.gc.live.thrshld.prcnt"),

    /**
     * Property key for heap dump on memory error option disabled.
     */
    WARN_OPT_HEAP_DUMP_ON_OOME_DISABLED("warn.opt.heap.dump.on.oome.disabled"),

    /**
     * Property key for specifying the number of GC log files (filecount=N) to keep with log rotation is disabled
     * (filecount=0).
     */
    WARN_OPT_JDK11_GC_LOG_FILE_ROTATION_DISABLED("warn.opt.jdk11.gc.log.file.rotation.disabled"),

    /**
     * Property key for automatic GC log file rotation disabled with filesize=0.
     */
    WARN_OPT_JDK11_GC_LOG_FILE_SIZE_0("warn.opt.jdk11.gc.log.file.size.0"),

    /**
     * Property key for specifying the gc log file size that triggers rotation (filesize=N[K|M|G]) is small (&lt; 5M).
     */
    WARN_OPT_JDK11_GC_LOG_FILE_SIZE_SMALL("warn.opt.jdk11.gc.log.file.size.small"),

    /**
     * Property key for specifying the number of GC log files (-XX:NumberOfGCLogFiles) to keep with log rotation is
     * disabled (-XX:-UseGCLogFileRotation).
     */
    WARN_OPT_JDK8_GC_LOG_FILE_NUM_ROTATION_DISABLED("warn.opt.jdk8.gc.log.file.num.rotation.disabled"),

    /**
     * Property key for specifying the gc log file size that triggers rotation (-XX:GCLogFileSize=N[K|M|G]) is small
     * (&lt; 5M).
     */
    WARN_OPT_JDK8_GC_LOG_FILE_SIZE_SMALL("warn.opt.jdk8.gc.log.file.size.small"),

    /**
     * Property key for gc details option disabled.
     */
    WARN_OPT_JDK8_PRINT_GC_DETAILS_DISABLED("warn.opt.jdk8.print.gc.details.disabled"),

    /**
     * Property key for -XX:-UseVMInterruptibleIO.
     */
    WARN_OPT_JDK8_USE_VM_INTERRUPTIBLE_IO("warn.opt.jdk8.use.vm.interruptible.io"),

    /**
     * Property key for MaxMetaspaceSize less than CompressedClassSpaceSize. MaxMetaspaceSize includes
     * CompressedClassSpaceSize, so MaxMetaspaceSize should be larger than CompressedClassSpaceSize.
     */
    WARN_OPT_METASPACE_LT_COMP_CLASS("warn.opt.metaspace.lt.comp.class"),

    /**
     * Property key for printing a class histogram when a thread dump is initiated (-XX:+PrintClassHistogram).
     */
    WARN_OPT_PRINT_CLASS_HISTOGRAM("warn.opt.print.class.histogram"),

    /**
     * Property key for printing a class histogram when a thread dump is initiated
     * (-XX:+PrintClassHistogramAfterFullGC).
     */
    WARN_OPT_PRINT_CLASS_HISTOGRAM_AFTER_FULL_GC("warn.opt.print.class.histogram.after.full.gc"),

    /**
     * Property key for printing a class histogram when a thread dump is initiated
     * (-XX:+PrintClassHistogramBeforeFullGC).
     */
    WARN_OPT_PRINT_CLASS_HISTOGRAM_BEFORE_FULL_GC("warn.opt.print.class.histogram.before.full.gc"),

    /**
     * Property key for small sun.rmi.dgc.client.gcInterval.
     */
    WARN_OPT_RMI_DGC_CLIENT_GCINTERVAL_SMALL("warn.opt.rmi.dgc.client.gcInterval.small"),
    /**
     * Property key for small sun.rmi.dgc.server.gcInterval.
     */
    WARN_OPT_RMI_DGC_SERVER_GCINTERVAL_SMALL("warn.opt.rmi.dgc.server.gcInterval.small"),

    /**
     * Property key for disabling tenuring with -XX:MaxTenuringThreshold=0 or by setting it to a value greater than 15
     * (e.g. -XX:MaxTenuringThreshold=32).
     */
    WARN_OPT_TENURING_DISABLED("warn.opt.tenuring.disabled"),

    /**
     * Property key for -XX:+UseMembar.
     */
    WARN_OPT_USE_MEMBAR("warn.opt.use.membar"),

    /**
     * Property key for class verification on loading disabled with -Xverify:none.
     */
    WARN_OPT_VERIFY_NONE("warn.opt.verify.none"),

    /**
     * Property key for a JDK that is deployed on RHEL6.
     */
    WARN_RHEL6("warn.rhel6"),

    /**
     * Property key for swapping &gt;= 20%.
     */
    WARN_SWAPPING("warn.swapping"),

    /**
     * Property key for a small thread stack size (&lt; 128k).
     */
    WARN_THREAD_STACK_SIZE_SMALL("warn.thread.stack.size.small"),

    /**
     * Property key for a tiny thread stack size (&lt; 1k).
     */
    WARN_THREAD_STACK_SIZE_TINY("warn.thread.stack.size.tiny"),

    /**
     * Property key for unidentified line(s) needing reporting.
     */
    WARN_UNIDENTIFIED_LOG_LINE_REPORT("warn.unidentified.log.line.report");

    private String key;

    private Analysis(final String key) {
        this.key = key;
    ***REMOVED***

    /**
     * @return Analysis property file key.
     */
    public String getKey() {
        return key;
    ***REMOVED***

    /**
     * @return Analysis property file value.
     */
    public String getValue() {
        return ErrUtil.getPropertyValue(Constants.ANALYSIS_PROPERTY_FILE, key);
    ***REMOVED***

    @Override
    public String toString() {
        return this.getKey();
    ***REMOVED***
***REMOVED***

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
     * Property key for a crash in String.compareTo(Object) compiled with the AVX2 instruction set.
     */
    ERROR_AVX2_STRING_COMPARE_TO("error.avx2.string.compare.to"),

    /**
     * Property key for a crash in BufferBlob::flush_icache_stub.
     */
    ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB("error.bufferblob.flush_icache_stub"),

    /**
     * Property key for not being able to get library information.
     */
    ERROR_CANNOT_GET_LIBRARY_INFORMATION("error.cannot.get.library.information"),

    /**
     * Property key for a crash when executing compiled java code.
     */
    ERROR_COMPILED_JAVA_CODE("error.compiled.java.code"),

    /**
     * Property key for a crash in CompilerThread.
     */
    ERROR_COMPILER_THREAD("error.compiler.thread"),

    /**
     * Property key for a crash in C2 CompilerThread: "guarantee(n != NULL) failed: No Node."
     */
    ERROR_COMPILER_THREAD_C2_BEAUTIFY_LOOPS("error.compiler.thread.c2.beautify.loops"),

    /**
     * Property key for a crash in MinINode::Ideal(PhaseGVN*, bool).
     */
    ERROR_COMPILER_THREAD_C2_MININODE_IDEAL("error.compiler.thread.c2.mininode.ideal"),

    /**
     * Property key for a crash in a 3rd party or unknown library.
     */
    ERROR_CRASH_NATIVE_LIBRARY_UNKNOWN("error.crash.native.library.unknown"),

    /**
     * Property key for a crash due to "java.lang.OutOfMemoryError: Java heap space" in combination with
     * -XX:+CrashOnOutOfMemoryError.
     */
    ERROR_CRASH_ON_OOME_HEAP("error.crash.on.oome.heap"),

    /**
     * Property key for a crash due to multiple threads access DirectByteBuffer at the same time.
     */
    ERROR_DIRECT_BYTE_BUFFER_CONTENTION("error.direct.byte.buffer.contention"),

    /**
     * Property key for crash in Dynatrace code.
     */
    ERROR_DYNATRACE("error.dynatrace"),

    /**
     * Property key for explicit gc disabled running JBoss EAP7.
     */
    ERROR_EXPLICIT_GC_DISABLED_EAP7("error.explicit.gc.disabled.eap7"),

    /**
     * Property key for floating point error.
     */
    ERROR_FPE("error.fpe"),

    /**
     * Property key for a crash due to a temporary font file being removed from java.io.tmpdir.
     */
    ERROR_FREETYPE_FONT_SCALER_GET_GLYPH_IMAGE_NATIVE("error.freetype.font.scaler.get.glyph.image.native"),

    /**
     * Property key for a crash in G1ParScanThreadState::copy_to_survivor_space(InCSetState, oopDesc*, markOopDesc*)
     * fixed in JDK 11.0.10.
     */
    ERROR_G1_PAR_SCAN_THREAD_STATE_COPY_TO_SURVIVOR_SPACE("error.g1_par_scan_thread_state.copy_to_survivor_space"),

    /**
     * Property key for a crash in java.util.HashMap.
     */
    ERROR_HASHMAP("error.hashmap"),

    /**
     * Property key for a crash in iText I/O.
     */
    ERROR_ITEXT_IO("error.itext.io"),

    /**
     * Property key for an unknown JDK version.
     */
    ERROR_JDK_VERSION_UNKNOWN("error.jdk.version.unknown"),

    /**
     * Property key for an unsupported JDK version (prior to JDK8).
     */
    ERROR_JDK_VERSION_UNSUPPORTED("error.jdk.version.unsupported"),

    /**
     * Property key for a crash in JDK8 in Deflater when a file is attempting to be modified while Java has it open.
     */
    ERROR_JDK8_DEFLATER_CONTENTION("error.jdk8.deflater.contention"),

    /**
     * Property key for a crash in JDK8 when a JFR class is transformed.
     */
    ERROR_JDK8_JFR_CLASS_TRANSFORMED("error.jdk8.jfr.class.transformed"),

    /**
     * Property key for a crash in JDK8 in libc cfree+0x1c fixed in OpenJDK8 u262.
     * 
     */
    ERROR_JDK8_LIBC_CFREE("error.jdk8.libc.cfree"),

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
     * Property key for Shenadoah MetadataOnStackMark bug fixed in OpenJDK8 u312.
     */
    ERROR_JDK8_SHENANDOAH_METADATA_ON_STACK_MARK("error.jdk8.shenandoah.metadata.on.stack.mark"),

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
     * Property key for crash in JNA ffi_prep_closure_loc.
     */
    ERROR_JNA_FFI_PREP_CLOSURE_LOC("error.jna.ffi_prep_closure_loc"),

    /**
     * Property key for a crash in JNA code on a Red Hat JDK.
     */
    ERROR_JNA_RH("error.jna.rh"),

    /**
     * Property key for crash in Java Security Services (JSS) code.
     */
    ERROR_JSS("error.jss"),

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
     * Property key for LinkageError.
     */
    ERROR_LINKAGE("error.linkage"),

    /**
     * Property key for JVM crash in ModuleEntry::purge_reads.
     */
    ERROR_MODULE_ENTRY_PURGE_READS("error.module.entry.purge.reads"),

    /**
     * Property key for JVM crash in ModuleEntry::purge_reads possible.
     */
    ERROR_MODULE_ENTRY_PURGE_READS_POSSIBLE("error.module.entry.purge.reads.possible"),

    /**
     * Property key for generic insufficient physical memory.
     */
    ERROR_OOME("error.oome"),

    /**
     * Property key for the AMQ CLI failing to execute due to insufficient physical memory.
     */
    ERROR_OOME_AMQ_CLI("error.oome.amq.cli"),

    /**
     * Property key for VM crash in CompilerThread in sun.security.ssl.SSLEngineInputRecord::decodeInputRecord compile
     * task due to insufficient physical memory..
     */
    ERROR_OOME_COMPILER_THREAD_C2_SSL_DECODE("error.oome.compiler.thread.c2.ssl.decode"),

    /**
     * Property key for insufficient physical memory due to an external process.
     */
    ERROR_OOME_EXTERNAL("error.oome.external"),

    /**
     * Property key for insufficient physical memory due to an external process or hypervisor ballooning.
     */
    ERROR_OOME_EXTERNAL_OR_HYPERVISOR("error.oome.external.or.hypervisor"),

    /**
     * Property key for the JVM failing to start due to insufficient physical memory when JVM memory &lt; 50% memory.
     */
    ERROR_OOME_EXTERNAL_STARTUP("error.oome.external.startup"),

    /**
     * Property key for insufficient physical memory when doing a JBoss version check (e.g. standalone.sh --version).
     */
    ERROR_OOME_JBOSS_VERSION("error.oome.jboss.version"),

    /**
     * Property key for insufficient physical memory due to the JVM process.
     */
    ERROR_OOME_JVM("error.oome.jvm"),

    /**
     * Property key for the JVM failing to start due to insufficient physical memory.
     */
    ERROR_OOME_JVM_STARTUP("error.oome.jvm.startup"),

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
     * Property key for the JVM failing to start due to a resource limit (rlimit) being reached or the native heap
     * reaching the java heap base address.
     */
    ERROR_OOME_LIMIT_OOPS_STARTUP("error.oome.limit.oops.startup"),

    /**
     * Property key for the JVM failing to start due to a resource limit (rlimit).
     */
    ERROR_OOME_LIMIT_STARTUP("error.oome.limit.startup"),

    /**
     * Property key for insufficient physical memory due to JVM native memory, an external process, or hypervisor
     * ballooning.
     */
    ERROR_OOME_NATIVE_OR_EXTERNAL("error.oome.native.or.external"),

    /**
     * Property key for generic insufficient physical memory with oops enabled.
     */
    ERROR_OOME_OOPS("error.oome.oops"),

    /**
     * Property key for the JVM failing to allocate memory with strong evidence it is due to vm.overcommit_memory=2, but
     * a resource limit cannot be ruled out.
     */
    ERROR_OOME_OVERCOMMIT_LIMIT("error.oome.overcommit.limit"),

    /**
     * Property key for the JVM failing to start with strong evidence it is due to vm.overcommit_memory=2, but a
     * resource limit cannot be ruled out.
     */
    ERROR_OOME_OVERCOMMIT_LIMIT_STARTUP("error.oome.overcommit.limit.startup"),

    /**
     * Property key for a thread leak causing a limit to be reached.
     */
    ERROR_OOME_THREAD_LEAK("error.oome.thread.leak"),

    /**
     * Property key for a Wildfly or JBoss EAP executor pool leak causing a limit to be reached.
     */
    ERROR_OOME_THREAD_LEAK_EAP_EXECUTOR_POOL("error.oome.thread.leak.eap.executor.pool"),

    /**
     * Property key for "OutOfMemoryError: Compressed class space" caught and thrown.
     */
    ERROR_OOME_THROWN_COMP_CLASS_SPACE("error.oome.thrown.comp.class.space"),

    /**
     * Property key for OutOfMemoryError other than "Metaspace" or "Compressed class space" caught and thrown.
     */
    ERROR_OOME_THROWN_JAVA_HEAP("error.oome.thrown.java.heap"),

    /**
     * Property key for "OutOfMemoryError: Metaspace" caught and thrown.
     */
    ERROR_OOME_THROWN_METASPACE("error.oome.thrown.metaspace"),

    /**
     * Property key for the tomcat shutdown JVM failing to start due to insufficient physical memory.
     */
    ERROR_OOME_TOMCAT_SHUTDOWN("error.oome.tomcat.shutdown"),

    /**
     * Property key for incompatible Oracle JDBC driver / JDK versions.
     */
    ERROR_ORACLE_JDBC_JDK_INCOMPATIBLE("error.oracle.jdbc.jdk.incompatible"),

    /**
     * Property key for crash in Oracle JDBC OCI (native) driver code.
     */
    ERROR_ORACLE_JDBC_OCI_DRIVER("error.oracle.jdbc.oci.driver"),

    /**
     * Property key for a crash after loading Oracle JDBC OCI (native) driver.
     */
    ERROR_ORACLE_JDBC_OCI_LOADING("error.oracle.jdbc.oci.loading"),

    /**
     * Property key for crash caused trying to dereference an invalid pointer.
     */
    ERROR_POINTER_INVALID("error.pointer.invalid"),

    /**
     * Property key for crash caused trying to dereference a null pointer.
     */
    ERROR_POINTER_NULL("error.pointer.null"),

    /**
     * Property key for PostgreSQL JDBC driver incompatible with JDK8.
     */
    ERROR_POSTGRESQL_JDBC_JDK8_INCOMPATIBLE("error.postgresql.jdbc.jdk8.incompatible"),

    /**
     * Property key for a crash in PSPromotionManager::copy_to_survivor_space&lt;false&gt;(oopDesc*).
     */
    ERROR_PS_PROMOTION_MANAGER_COPY_TO_SURVIVOR_SPACE("error.ps_promotion_manager.copy_to_survivor_space"),

    /**
     * Property key for error calling pthread_getcpuclockid
     */
    ERROR_PTHREAD_GETCPUCLOCKID("error.pthread.getcpuclockid"),

    /**
     * Property key RHEL/JDK rpm version mismatch.
     */
    ERROR_RHEL_JDK_RPM_MISMATCH("error.rhel.jdk.rpm.mismatch"),

    /**
     * Property key for StackOverflowError.
     */
    ERROR_STACKOVERFLOW("error.stackoverflow"),

    /**
     * Property key for crash in StubRoutines.
     */
    ERROR_STUBROUTINES("error.stubroutines"),

    /**
     * Property key for crash in Wily/DX APM code.
     */
    ERROR_WILY("error.wily"),

    /**
     * Property key for AdoptOpenJDK build of OpenJDK.
     */
    INFO_ADOPTOPENJDK_POSSIBLE("info.adoptopenjdk.possible"),

    /**
     * Property key for AppDynamics instrumentation detected.
     */
    INFO_APP_DYNAMICS_DETECTED("info.app.dynamics.detected"),

    /**
     * Property key for AppDynamics instrumentation possible.
     */
    INFO_APP_DYNAMICS_POSSIBLE("info.app.dynamics.possible"),

    /**
     * Property key for cgroup environment.
     */
    INFO_CGROUP("info.cgroup"),

    /**
     * Property key for cgroup memory limit.
     */
    INFO_CGROUP_MEMORY_LIMIT("info.cgroup.memory.limit"),

    /**
     * Property key for a crash in Java code compiled with the AVX2 instruction set.
     */
    INFO_COMPILED_JAVA_CODE_AVX2("info.compiled.java.code.avx2"),

    /**
     * Property key for Apache Commons Database Database Connection Pool 2.
     */
    INFO_DBCP2("info.dbcp2"),

    /**
     * Property key for Dynatrace detected.
     */
    INFO_DYNATRACE("info.dynatrace"),

    /**
     * Property key for GC log being sent to stdout.
     */
    INFO_GC_LOG_STDOUT("info.gc.log.stdout"),

    /**
     * Property key for HyperV (Viridian) environment. Previously referred to as Windows Server Virtualization.
     */
    INFO_HYPERV("info.hyperv"),

    /**
     * Property key for IBM Toolkit for Java library detected.
     */
    INFO_IBM_TOOLKIT("info.ibm.toolkit"),

    /**
     * Property key for iText library detected.
     */
    INFO_ITEXT("info.itext"),

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
     * Property key for Java Security Services (JSS) library detected.
     */
    INFO_JSS("info.jss"),

    /**
     * Property key for crash on JVM startup.
     */
    INFO_JVM_STARTUP_FAILS("info.jvm.startup.fails"),

    /**
     * Property key for the USERNAME environment variable different than the user the JVM process is running under.
     */
    INFO_JVM_USER_NE_USERNAME("info.jvm.user.ne.username"),

    /**
     * Property key for /etc/ld.so.preload being used to preload libraries.
     */
    INFO_LD_SO_PRELOAD("info.ld.so.preload"),

    /**
     * Property key for JVM memory not equal to system memory.
     * 
     * TODO: Remove this? It's basically a duplicate of INFO_CGROUP_MEMORY_LIMIT?
     */
    INFO_MEMORY_JVM_NE_SYSTEM("info.memory.jvm.ne.system"),

    /**
     * Property key for Microsoft SQL Server native driver detected.
     */
    INFO_MICROSOFT_SQL_SERVER_NATIVE("info.microsoft.sql.server.native"),

    /**
     * Property key for JBoss native libraries detected.
     */
    INFO_NATIVE_LIBRARIES_JBOSS("info.native.libraries.jboss"),

    /**
     * Property key for unknown native libraries detected.
     */
    INFO_NATIVE_LIBRARIES_UNKNOWN("info.native.libraries.unknown"),

    /**
     * Property key for OOME on startup when initial heap size equal to maximum heap size.
     */
    INFO_OOME_STARTUP_HEAP_MIN_EQUAL_MAX("info.oome.startup.heap.min.equal.max"),

    /**
     * Property key for no JVM options.
     */
    INFO_OPTS_NONE("info.opts.none"),

    /**
     * Property key for Oracle JDBC OCI (native) driver detected.
     */
    INFO_ORACLE_JDBC_OCI("info.oracle.jdbc.oci"),

    /**
     * Property key for Red Hat Certificate System, Red Hat Enterprise Linux (RHEL) Identity Management (IdM), or
     * upstream Dogtag Certificate System detected.
     */
    INFO_PKI_TOMCAT("info.pki_tomcat"),

    /**
     * Property key for PostgreSQL connection.
     */
    INFO_POSTGRESQL_CONNECTION("info.postgresql.connection"),

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
     * Property key for -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC on a RH build.
     */
    INFO_RH_OPT_EXPERIMENTAL_SHENANDOAH("info.rh.opt.experimental.shenandoah"),

    /**
     * Property key RHEL9 + JDK8.
     */
    INFO_RHEL9_JDK8("info.rhel9.jdk8"),

    /**
     * Property key for JVM crash during shutdown safepoint.
     */
    INFO_SHUTDOWN("info.shutdown"),

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
     * Property key for FPE_INTDIV.
     */
    INFO_SIGCODE_FPE_INTDIV("info.sigcode.fpe.intdiv"),

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
     * Property key for STACK_OVERFLOW crash.
     */
    INFO_SIGNO_EXCEPTION_STACK_OVERFLOW("info.signo.exception.stack.overflow"),

    /**
     * Property key for SIGBUS crash.
     */
    INFO_SIGNO_SIGBUS("info.signo.sigbus"),

    /**
     * Property key for SIGFPE crash.
     */
    INFO_SIGNO_SIGFPE("info.signo.sigfpe"),

    /**
     * Property key for SIGILL crash.
     */
    INFO_SIGNO_SIGILL("info.signo.sigill"),

    /**
     * Property key for SIGSEGV crash.
     */
    INFO_SIGNO_SIGSEGV("info.signo.sigsegv"),

    /**
     * Property key for stack free space greater than stack size.
     */
    INFO_STACK_FREESPACE_GT_STACK_SIZE("info.stack.freespace.gt.stack.size"),

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
     * Property key for many threads (&gt;1,000).
     */
    INFO_THREADS_MANY("info.threads.many"),

    /**
     * Property key for truncated fatal error log.
     */
    INFO_TRUNCATED("info.truncated"),

    /**
     * Property key for a crash happening during a bulk operation when the compiler has to recompile previously compiled
     * code due to the compiled code no longer being valid (e.g. a dynamic object has changed) or with tiered
     * compilation when client compiled code is replaced with server compiled code.
     */
    INFO_VM_OPERATION_BULK_REVOKE_BIAS("info.vm.operation.bulk.revoke.bias"),

    /**
     * Property key for a crash happening during a concurrent GC stop-the-world operation (e.g. remark, cleanup).
     */
    INFO_VM_OPERATION_CONCURRENT_GC("info.vm.operation.concurrent.gc"),

    /**
     * Property key for a crash happening during a heap dump operation.
     */
    INFO_VM_OPERATION_HEAP_DUMP("info.vm.operation.heap.dump"),

    /**
     * Property key for a crash happening during a thread dump operation.
     */
    INFO_VM_OPERATION_THREAD_DUMP("info.vm.operation.thread.dump"),

    /**
     * Property key for VMWare environment.
     */
    INFO_VMWARE("info.vmware"),

    /**
     * Property key for Wily/DX APM detected.
     */
    INFO_WILY("info.wily"),

    /**
     * Property key for CMS collector running in incremental mode.
     */
    WARN_CMS_INCREMENTAL_MODE("warn.cms.incremental.mode"),

    /**
     * Property key for no evidence the JDK debug symbols are installed.
     */
    WARN_DEBUG_SYMBOLS("warn.jdk.debug.symbols"),

    /**
     * Property key for Dynatrace code in stack.
     */
    WARN_DYNATRACE("warn.dynatrace"),

    /**
     * Property key for a fatal error log that is more than 30 days old.
     */
    WARN_FATAL_ERROR_LOG_ANCIENT("warn.fatal.error.log.ancient"),

    /**
     * Property key for heap + metaspace &gt; physical memory (no swap).
     */
    WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_NOSWAP("warn.heap.plus.metaspace.gt.physical.memory.noswap"),

    /**
     * Property key for heap + metaspace &gt; physical memory (including swap).
     */
    WARN_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY_SWAP("warn.heap.plus.metaspace.gt.physical.memory.swap"),

    /**
     * Property key for iText code in stack.
     */
    WARN_ITEXT("warn.itext"),

    /**
     * Property key for a JDK that is not the latest JDK release.
     */
    WARN_JDK_NOT_LATEST("warn.jdk.not.latest"),

    /**
     * Property key for a JDK that is not a Long Term Support (LTS) version.
     */
    WARN_JDK_NOT_LTS("warn.jdk.not.lts"),

    /**
     * Property key for Java Security Services (JSS) code in stack.
     */
    WARN_JSS("warn.jss"),

    /**
     * Property key for Lucene code in stack.
     */
    WARN_LUCENE("warn.lucene"),

    /**
     * Property key mmapped files in a deleted state.
     */
    WARN_MMAP_DELETED("warn.mmap.deleted"),

    /**
     * Property key for OOM with G1 collector.
     */
    WARN_OOM_G1("warn.oom.g1"),

    /**
     * Property key for crash when connecting to Oracle database using the JDBC OCI (native) driver.
     */
    WARN_ORACLE_JDBC_OCI_CONNECION("warn.oracle.jdbc.oci.connection"),

    /**
     * Property key for a JDK that is deployed on RHEL6.
     */
    WARN_RHEL6("warn.rhel6"),

    /**
     * Property key for RHEL7 Power9 Extended Life Phase.
     */
    WARN_RHEL7_POWER9("warn.rhel7.power9"),

    /**
     * Property key for using the CMS collector when swap is disabled.
     */
    WARN_SWAP_DISABLED_CMS("warn.swap.disabled.cms"),

    /**
     * Property key for using the G1 collector when swap is disabled.
     */
    WARN_SWAP_DISABLED_G1("warn.swap.disabled.g1"),

    /**
     * Property key for swapping &gt;= 20%.
     */
    WARN_SWAPPING("warn.swapping"),

    /**
     * Property key for many threads (&gt;5,000).
     */
    WARN_THREADS_MANY("warn.threads.many"),

    /**
     * Property key for unidentified line(s) needing reporting.
     */
    WARN_UNIDENTIFIED_LOG_LINE("warn.unidentified.log.line"),

    /**
     * Property key for a crash happening during a thread dump operation initiated by an external tool calling the JVM
     * tool interface (JVM TI).
     */
    WARN_VM_OPERATION_THREAD_DUMP_JVMTI("warn.vm.operation.thread.dump.jvmti"),

    /**
     * Property key for Wily/DX APM code in stack.
     */
    WARN_WILY("warn.wily");

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

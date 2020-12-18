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

import org.github.errcat.util.Constants;
import org.github.errcat.util.ErrUtil;

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
     * Property key for a crash due to multiple threads access DirectByteBuffer at the same time.
     */
    ERROR_DIRECT_BYTE_BUFFER_CONTENTION("error.direct.byte.buffer.contention"),

    /**
     * Property key for heap + metaspace &gt; physical memory.
     */
    ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY("error.heap.plus.metaspace.gt.physical.memory"),

    /**
     * Property key for a crash on RHEL7 where a power8 rpm is deployed on power9. Power8 support is through the
     * rhel-7-for-power-le-rpms repo (least release 7.9). Power9 support is through the rhel-7-for-power-9-rpms repo
     * (last release 7.6).
     */
    ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9("error.jdk8.rhel7.power8.rpm.on.power9"),

    /**
     * Property key for ShenadoahRootUpdater bug fixed in OpenJDK8 u282.
     */
    ERROR_JDK8_SHENANDOAH_ROOT_UPDATER("error.jdk8.shenandoah.root.updater"),

    /**
     * Property key for a crash in JDK8 when a file is attempting to be modified while Java has it open.
     */
    ERROR_JDK8_ZIPFILE_CONTENTION("error.jdk8.zipfile.contention"),

    /**
     * Property key for a crash in JNA code.
     */
    ERROR_JNA("error.jna"),

    /**
     * Property key for a crash in JNA code on a Red Hat JDK.
     */
    ERROR_JNA_RH("error.jna.rh"),

    /**
     * Property key for OutOfMemoryError Java heap space.
     */
    ERROR_OOME_JAVA_HEAP("error.oome.java.heap"),

    /**
     * Property key for crash trying to allocate memory when heap + metaspace much less than physical memory.
     */
    ERROR_OOME_JVM_LT_PHYSICAL_MEMORY("error.oome.jvm.lt.physical.memory"),

    /**
     * Property key for not enough physical memory for the JVM to start.
     */
    ERROR_OOME_STARTUP("error.oome.startup"),

    /**
     * Property key for error calling pthread_getcpuclockid
     */
    ERROR_PTHREAD_GETCPUCLOCKID("error.pthread.getcpuclockid"),

    /**
     * Property key for StackOverflowError.
     */
    ERROR_STACKOVERFLOW("error.stackoverflow"),

    /**
     * Property key for AdoptOpenJDK build of OpenJDK.
     */
    INFO_ADOPTOPENJDK_POSSIBLE("info.adoptopenjdk.possible"),

    /**
     * Property key for a JDK that is more than 1 year older than the latest release.
     */
    INFO_JDK_ANCIENT("info.jdk.ancient"),

    /**
     * Property key for crash on JVM startup.
     */
    INFO_JVM_STARTUP_FAILS("info.jvm.startup.fails"),

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
     * Property key for Red Hat build of OpenJDK rpm install.
     */
    INFO_RH_BUILD_RPM("info.rh.build.rpm"),

    /**
     * Property key for Red Hat build of OpenJDK Windows zip install.
     */
    INFO_RH_BUILD_WINDOWS_ZIP("info.rh.build.windows.zip"),

    /**
     * Property key for SIGBUS crash.
     */
    INFO_SIGBUS("info.sigbus"),

    /**
     * Property key for SIGBUS crash on linux.
     */
    INFO_SIGBUS_LINUX("info.sigbus.linux"),

    /**
     * Property key for SIGSEGV crash.
     */
    INFO_SIGSEGV("info.sigsegv"),

    /**
     * Property key for the stack not containing any VM code.
     */
    INFO_STACK_NO_VM_CODE("info.stack.no.vm.code"),

    /**
     * Property key for swapping disabled.
     */
    INFO_SWAP_DISABLED("info.swap.disabled"),

    /**
     * Property key for swapping.
     */
    INFO_SWAPPING("info.swapping"),

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
     * Property key for a JDK that is deployed on RHEL6.
     */
    WARN_RHEL6("warn.rhel6"),

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

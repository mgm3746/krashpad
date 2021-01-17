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
import java.util.List;

import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;

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
     * The option for setting CompressedClassSpaceSize.
     * 
     * <pre>
     * -XX:CompressedClassSpaceSize=768m
     * </pre>
     */
    private String compressedClassSpaceSize;

    /**
     * Size of gc log file that triggers rotation. For example:
     * 
     * <pre>
     * -XX:GCLogFileSize=3M
     * </pre>
     */
    private String gcLogFileSize;

    /**
     * Initial heap space. Specified with the <code>-Xms</code> or <code>-XX:InitialHeapSize</code> option. For example:
     * 
     * <pre>
     * -Xms1024m
     * -XX:InitialHeapSize=257839744
     * </pre>
     */
    private String initialHeapSize;

    /**
     * Initial metaspace size (<code>-XX:MetaspaceSize</code>). For example:
     * 
     * <pre>
     * -XX:MetaspaceSize=1024M
     * </pre>
     */
    private String initialMetaspaceSize;

    /**
     * JPDA socket transport used for debugging. For example:
     * 
     * -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n
     */
    private String jpdaSocketTransport;

    /**
     * Option to specify gc log location. For example:
     * 
     * <pre>
     * -Xloggc:/path/to/EAP-7.1.0/standalone/log/gc.log
     * </pre>
     */
    private String logGc;

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
     * Maximum metaspace size (<code>-XX:MaxMetaspaceSize</code>). For example:
     * 
     * <pre>
     * -XX:MaxMetaspaceSize=2048m
     * </pre>
     */
    private String maxMetaspaceSize;

    /**
     * Option to specify the number of gc log files to keep when rotation is enabled. For example:
     * 
     * <pre>
     * -XX:NumberOfGCLogFiles=5
     * </pre>
     */
    private String numberOfGcLogFiles;

    /**
     * Option to enable/disable ergonomic option that resizes generations to meet pause and throughput goals and
     * minimize footprint. For example:
     * 
     * <pre>
     * -XX:+UseAdaptiveSizePolicy
     * </pre>
     */
    private String useAdaptiveSizePolicy;

    public String getUseAdaptiveSizePolicy() {
        return useAdaptiveSizePolicy;
    ***REMOVED***

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
     * JVM options used to define system properties.
     * 
     * For example:
     * 
     * -Dcatalina.base=/path/to/tomcat
     */
    private ArrayList<String> systemProperties = new ArrayList<String>();

    /**
     * Thread stack size. Specified with either the <code>-Xss</code>, <code>-ss</code>, or
     * <code>-XX:ThreadStackSize</code> options. For example:
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
     * Option to enable/disable gc log file rotation. For example:
     * 
     * -XX:+UseGCLogFileRotation
     */
    private String useGcLogFileRotation;

    /**
     * Flag to display information about each gc event.
     * 
     * -verbose:gc
     */
    private boolean verboseGc = false;

    /**
     * Convert JVM argument string to JVM options.
     * 
     * @param jvmArgs
     *            The JVM arguments.
     */
    public JvmOptions(String jvmArgs) {
        super();
        if (jvmArgs != null) {
            String[] options = jvmArgs.split("(?<!^)(?= -)");
            for (int i = 0; i < options.length; i++) {
                String option = options[i].trim();
                if (option.matches("^-agentlib:jdwp=transport=dt_socket.+$")) {
                    jpdaSocketTransport = option;
                ***REMOVED*** else if (option.matches("^-ABRT.+$")) {
                    abrt = option;
                ***REMOVED*** else if (option.matches("^-XX:CompressedClassSpaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    compressedClassSpaceSize = option;
                ***REMOVED*** else if (option.matches("^-D.+$")) {
                    systemProperties.add(option);
                ***REMOVED*** else if (option.matches("^-Xloggc:.+$")) {
                    logGc = option;
                ***REMOVED*** else if (option.matches("^-X(ms|X:InitialHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    initialHeapSize = option;
                ***REMOVED*** else if (option.matches("^-XX:MetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    initialMetaspaceSize = option;
                ***REMOVED*** else if (option.matches("^-X(mx|X:MaxHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxHeapSize = option;
                ***REMOVED*** else if (option.matches("^-(X)?(ss|X:ThreadStackSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    threadStackSize = option;
                ***REMOVED*** else if (option.matches("^-XX:GCLogFileSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    gcLogFileSize = option;
                ***REMOVED*** else if (option.matches("^-XX:MaxMetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxMetaspaceSize = option;
                ***REMOVED*** else if (option.matches("^-XX:NumberOfGCLogFiles=\\d{1,***REMOVED***$")) {
                    numberOfGcLogFiles = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCDateStamps$")) {
                    printGcDateStamps = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]PrintGCDetails$")) {
                    printGcDetails = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]TraceClassUnloading$")) {
                    traceClassUnloading = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseAdaptiveSizePolicy$")) {
                    useAdaptiveSizePolicy = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCompressedClassPointers$")) {
                    useCompressedClassPointers = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseCompressedOops$")) {
                    useCompressedOops = option;
                ***REMOVED*** else if (option.matches("^-XX:[\\-+]UseGCLogFileRotation$")) {
                    useGcLogFileRotation = option;
                ***REMOVED*** else if (option.matches("^-verbose:gc$")) {
                    verboseGc = true;
                ***REMOVED*** else {
                    undefined.add(option);
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    /**
     * Do JVM options analysis.
     */
    public void doAnalysis(List<Analysis> analysis) {
        // Check for remote debugging enabled
        if (jpdaSocketTransport != null) {
            analysis.add(Analysis.ERROR_OPT_REMOTE_DEBUGGING_ENABLED);
        ***REMOVED***
        if (undefined.size() > 0) {
            analysis.add(Analysis.INFO_OPT_UNDEFINED);
        ***REMOVED***
        // Check if initial or max metaspace size being set
        if (initialMetaspaceSize != null || maxMetaspaceSize != null) {
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
        if (initialHeapSize != null && maxHeapSize != null && useAdaptiveSizePolicy != null
                && (JdkUtil.getByteOptionBytes(JdkUtil.getByteOptionValue(initialHeapSize)) != JdkUtil
                        .getByteOptionBytes(JdkUtil.getByteOptionValue(maxHeapSize)))
                && JdkUtil.isOptionDisabled(useAdaptiveSizePolicy)) {
            analysis.add(Analysis.WARN_OPT_ADAPTIVE_RESIZE_POLICY_DISABLED);
        ***REMOVED***
    ***REMOVED***

    public String getAbrt() {
        return abrt;
    ***REMOVED***

    public String getCompressedClassSpaceSize() {
        return compressedClassSpaceSize;
    ***REMOVED***

    public String getGcLogFileSize() {
        return gcLogFileSize;
    ***REMOVED***

    public String getInitialHeapSize() {
        return initialHeapSize;
    ***REMOVED***

    public String getInitialMetaspaceSize() {
        return initialMetaspaceSize;
    ***REMOVED***

    public String getJpdaSocketTransport() {
        return jpdaSocketTransport;
    ***REMOVED***

    public String getLogGc() {
        return logGc;
    ***REMOVED***

    public String getMaxHeapSize() {
        return maxHeapSize;
    ***REMOVED***

    public String getMaxMetaspaceSize() {
        return maxMetaspaceSize;
    ***REMOVED***

    public String getNumberOfGcLogFiles() {
        return numberOfGcLogFiles;
    ***REMOVED***

    public String getPrintGcDateStamps() {
        return printGcDateStamps;
    ***REMOVED***

    public String getPrintGcDetails() {
        return printGcDetails;
    ***REMOVED***

    public ArrayList<String> getSystemProperties() {
        return systemProperties;
    ***REMOVED***

    public String getThreadStackSize() {
        return threadStackSize;
    ***REMOVED***

    public String getTraceClassUnloading() {
        return traceClassUnloading;
    ***REMOVED***

    public ArrayList<String> getUndefined() {
        return undefined;
    ***REMOVED***

    public String getUseCompressedClassPointers() {
        return useCompressedClassPointers;
    ***REMOVED***

    public String getUseCompressedOops() {
        return useCompressedOops;
    ***REMOVED***

    public String getUseGcLogFileRotation() {
        return useGcLogFileRotation;
    ***REMOVED***

    public boolean isVerboseGc() {
        return verboseGc;
    ***REMOVED***
***REMOVED***

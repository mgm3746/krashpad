/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * VM_OPERATION
 * </p>
 * 
 * <p>
 * VM_Operation information.
 * </p>
 * 
 * <ul>
 * <li>BulkRevokeBias: Bulk operation when the compiler has to recompile previously compiled code due to the compiled
 * code no longer being valid (e.g. a dynamic object has changed) or with tiered compilation when client compiled code
 * is replaced with server compiled code.</li>
 * <li>CGC_Operation: G1 cleanup or G1/CMS remark.</li>
 * <li>CollectForMetadataAllocation: When the Metaspace is resized. The JVM has failed to allocate memory for something
 * that should be stored in Metaspace and does a full collection before attempting to resize the Metaspace.</li>
 * <li>G1CollectForAllocation: G1 young, prepare mixed, or mixed collection.</li>
 * <li>G1CollectFull: G1 full GC.</li>
 * <li>G1IncCollectionPause: G1 incremental collection.</li>
 * <li>G1PauseRemark: G1 remark pause.</li>
 * <li>GC_HeapInspection: Prints class histogram on SIGBREAK if PrintClassHistogram is specified and also the attach
 * "inspectheap" operation (e.g. jcmd &lt;pid&gt; GC.class_histogram).</li>
 * <li>GetAllStackTraces: JVMTI method to get stack trace information in native code for all threads.</li>
 * <li>GetThreadListStackTraces: JVMTI methods to get stack trace information in native code for a list of threads.</li>
 * <li>HeapDumper: Full heap dump (a heap summary does not require a safepoint).</li>
 * <li>ParallelGCFailedAllocation: Parallel collection.</li>
 * <li>ParallelGCSystemGC: Parallel collection initiated by explicit gc.</li>
 * <li>PrintThreads: A thread dump initiated by jcmd, jstack, or "kill -3".</li>
 * <li>RedefineClasses: Redefine classes.</li>
 * <li>ShenandoahFullGC: Shenandoah full GC.</li>
 * <li>ThreadDump: A thread dump initiated with ThreadMXBean.dumpAllThreads().</li>
 * </ul>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * VM_Operation (0x00007fffaa62ab20): PrintThreads, mode: safepoint, requested by thread 0x0000000001b2a000
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmOperation implements LogEvent {

    private static final String __REGEX_OPERATIONS = "(BulkRevokeBias|CGC_Operation|CMS_Final_Remark|"
            + "CollectForMetadataAllocation|G1CollectForAllocation|G1CollectFull|G1IncCollectionPause|G1PauseRemark|"
            + "GC_HeapInspection|GenCollectForAllocation|GetAllStackTraces|GetThreadListStackTraces|HeapDumper|"
            + "ParallelGCFailedAllocation|ParallelGCSystemGC|PrintThreads|RedefineClasses|ShenandoahFullGC|"
            + "ThreadDump)";

    /**
     * Regular expression defining the logging.
     */
    private static final String _REGEX = "^VM_Operation \\(" + JdkRegEx.ADDRESS + "\\): (" + __REGEX_OPERATIONS
            + ".+)$";

    private static Pattern pattern = Pattern.compile(_REGEX);

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(_REGEX);
    }

    /**
     * The log entry for the event.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public VmOperation(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.VM_OPERATION;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The VM operation. For example:
     * 
     *         PrintThreads
     */
    public String getVmOperation() {
        String vmOperation = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            vmOperation = matcher.group(7);
        }
        return vmOperation;
    }

    /**
     * @return The VM operation string. For example:
     * 
     *         PrintThreads, mode: safepoint, requested by thread 0x0000000001b2a000
     */
    public String getVmOperationString() {
        String vmOperation = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            vmOperation = matcher.group(6);
        }
        return vmOperation;
    }
}

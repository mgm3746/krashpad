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

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkMath;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * VM_OPERATION_EVENT
 * </p>
 * 
 * <p>
 * VM operations leading up to the crash.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * VM Operations (2 events):
 * Event: 31.627 Executing VM operation: HandshakeAllThreads done
 * </pre>
 * 
 * <p>
 * See {@link org.github.krashpad.domain.jdk.VmOperation} for operation definitions.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmOperationEvent implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    public static final String _REGEX_HEADER = "VM Operations \\(\\d{1,} events\\):";

    // TODO: Replace strings with UnifiedSafepointEvent fields.

    private static final String _REGEX_OPERATION = "Event: " + JdkRegEx.TIMESTAMP
            + " Executing( (non-safepoint|safepoint))? VM operation: (BulkRevokeBias|ClassLoaderStatsOperation|"
            + "CleanClassLoaderDataMetaspaces|Cleanup|CollectForMetadataAllocation \\(Metadata GC Threshold\\)|"
            + "FindDeadlocks|G1CollectForAllocation( \\(G1 Humongous Allocation\\))?|G1CollectFull|G1PauseCleanup|"
            + "G1PauseRemark|G1TryInitiateConcMark|G1TryInitiateConcMark \\(G1 Humongous Allocation\\)|"
            + "GenCollectForAllocation|GetAllStackTraces|GetThreadListStackTraces|"
            + "HandshakeAllThreads( \\(Deoptimize\\))?|HeapDumper|ICBufferFull|JFRCheckpoint|"
            + "ParallelGCFailedAllocation( \\(Allocation Failure\\))?|ParallelGCSystemGC|PrintJNI|PrintThreads|"
            + "RedefineClasses|RendezvousGCThreads|SetNotifyJvmtiEventsMode|"
            + "Shenandoah Final Mark and Start Evacuation|Shenandoah Final Update References|Shenandoah Init Marking|"
            + "Shenandoah Init Update References|ThreadDump|XMarkStart||ZMarkEnd|ZMarkEndOld \\(Allocation Rate\\)|"
            + "ZMarkFlushOperation|ZMarkStart|ZMarkStartYoung \\(Allocation Rate\\)|"
            + "ZMarkStartYoungAndOld \\(Allocation Rate\\)|ZRelocateStart|ZRendezvousGCThreads)( done)?";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER + "|" + _REGEX_OPERATION + "|No [Ee]vents)$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
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
    public VmOperationEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.VM_OPERATION_EVENT;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The time of the VM Operation event in milliseconds after JVM startup, or Long.MIN_VALUE if
     *         unknown/undetermined.
     */
    public long getTimestamp() {
        long timestamp = Long.MIN_VALUE;
        if (logEntry.matches(_REGEX_OPERATION)) {
            Pattern pattern = Pattern.compile(_REGEX_OPERATION);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                timestamp = JdkMath.convertSecsToMillis(matcher.group(1)).longValue();
            }
        }
        return timestamp;
    }

    /**
     * @return true if the VM Operation is beginning, false otherwise.
     */
    public boolean isBeginning() {
        return !logEntry.matches("^.+ done$");
    }

    /**
     * @return true if the VM Operation is ending, false otherwise.
     */
    public boolean isEnding() {
        return logEntry.matches("^.+ done$");
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }

    /**
     * @return true if the VM Operation is a thread dump, false otherwise.
     */
    public boolean isThreadDump() {

        // JVMTI method to get stack trace information in native code for all threads.
        String GET_ALL_STACK_TRACES = "GetAllStackTraces";

        // JVMTI method to get stack trace information in native code for a list of threads.
        String GET_THREAD_LIST_STACK_TRACES = "GetThreadListStackTraces";

        // Printing a stack trace.
        String PRINT_THREADS = "PrintThreads";

        // Generating a thread dump.
        String THREAD_DUMP = "ThreadDump";

        return logEntry.matches(".+ (" + GET_ALL_STACK_TRACES + "|" + GET_THREAD_LIST_STACK_TRACES + "|" + PRINT_THREADS
                + "|" + THREAD_DUMP + ").*");
    }
}

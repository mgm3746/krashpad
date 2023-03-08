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
package org.github.krashpad.domain.jdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * VM_ARGUMENTS
 * </p>
 * 
 * <p>
 * VM arguments information.
 * </p>
 * 
 * <p>
 * jvm_args will be missing if no JVM options are being used, or the JVM crashes very early in startup.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * ***REMOVED***
 * jvm_args: -D[Standalone] -verbose:gc -Xloggc:/path/to/gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=3M -XX:-TraceClassUnloading -Xms4014m -Xmx5734m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=512m -Djava.net.preferIPv4Stack=true -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC -XX:+PerfDisableSharedMem -XX:+AlwaysPreTouch -XX:+ClassUnloadingWithConcurrentMark -XX:ShenandoahUnloadClassesFrequency=100 -XX:+MonitorInUseLists -XX:MinHeapFreeRatio=10 -XX:MaxHeapFreeRatio=20 -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -XX:ParallelGCThreads=6 -Djava.util.concurrent.ForkJoinPool.common.parallelism=6 -XX:CICompilerCount=2 -XX:+ExitOnOutOfMemoryError -javaagent:/path/to/jolokia.jar=config=/path/to/jolokia.properties -javaagent:/path/to/appdynamics/javaagent.jar 
 * java_command: /path/to/jboss-modules.jar -Djboss.home.dir=/path/to/standalone -Djboss.node.name=-nodename
 * java_class_path (initial): /path/to/jboss-modules.jar:/path/to/jolokia.jar:/path/to/appdynamics/javaagent.jar
 * ***REMOVED***
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmArguments implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "***REMOVED***";

    private static Pattern pattern = Pattern.compile(VmArguments.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|jvm_args: |java_command: |java_class_path \\(initial\\): |Launcher Type: )(.*)$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
    ***REMOVED***

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
    public VmArguments(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.VM_ARGUMENTS.toString();
    ***REMOVED***

    /**
     * @return The value of the VM argument.
     */
    public String getValue() {
        String value = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            value = matcher.group(2);
        ***REMOVED***
        return value;
    ***REMOVED***

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        ***REMOVED***
        return isHeader;
    ***REMOVED***

    /**
     * @return True if the event is java_command, false otherwise.
     */
    public boolean isJavaCommand() {
        return logEntry.matches("^java_command: .+$");
    ***REMOVED***

    /**
     * @return True if the event is jvm_args, false otherwise.
     */
    public boolean isJvmArgs() {
        return logEntry.matches("^jvm_args: .+$");
    ***REMOVED***
***REMOVED***

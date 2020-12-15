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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.domain.LogEvent;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * JVM_ARGS
 * </p>
 * 
 * <p>
 * JVM options.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * jvm_args: -Xloggc:gc.log -Dorg.eclipse.swt.internal.gtk.cairoGraphics=false -Xms512m -Xmx1024m -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=detail -XX:+PrintNMTStatistics -Dosgi.instance.area.default=@user.home/workspace
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class JvmArgsEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^jvm_args: (.+)$";

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
    public JvmArgsEvent(String logEntry) {
        this.logEntry = logEntry;
    ***REMOVED***

    /**
     * The option for setting CompressedClassSpaceSize.
     * 
     * <pre>
     * -XX:CompressedClassSpaceSize=768m
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    public String getCompressedClassSpaceSizeOption() {
        String regex = "(-XX:CompressedClassSpaceSize=((\\d{1,10***REMOVED***)(" + JdkRegEx.OPTION_SIZE + ")?))";
        return getJvmOption(regex);
    ***REMOVED***

    /**
     * The compressed class space size value. For example:
     * 
     * <pre>
     * 768m
     * </pre>
     * 
     * @return The compressed class space size value, or null if not set. For example:
     * 
     */
    public String getCompressedClassSpaceSizeValue() {
        return JdkUtil.getOptionValue(getCompressedClassSpaceSizeOption());
    ***REMOVED***

    /**
     * @param regex
     *            The option regular expression.
     * @return The JVM option, or null if not explicitly set.
     */
    public String getJvmOption(final String regex) {
        String option = null;
        if (logEntry != null) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                option = matcher.group(1);
            ***REMOVED***
        ***REMOVED***
        return option;
    ***REMOVED***

    public String getLogEntry() {
        return logEntry;
    ***REMOVED***

    /**
     * Maximum heap space. Specified with the <code>-Xmx</code> or <code>-XX:MaxHeapSize</code> option. For example:
     * 
     * <pre>
     * -Xmx1024m
     * -XX:MaxHeapSize=1234567890
     * </pre>
     * 
     * @return The maximum heap space, or null if not explicitly set.
     */
    public String getMaxHeapOption() {
        String regex = "(-X(mx|X:MaxHeapSize=)(\\d{1,12***REMOVED***)(" + JdkRegEx.OPTION_SIZE + ")?)";
        return getJvmOption(regex);
    ***REMOVED***

    /**
     * For example:
     * 
     * <pre>
     * 2048M
     * </pre>
     * 
     * @return The maximum heap space value, or null if not set. For example:
     */
    public String getMaxHeapValue() {
        return JdkUtil.getOptionValue(getMaxHeapOption());
    ***REMOVED***

    /**
     * Maximum Metaspace (<code>-XX:MaxMetaspaceSize</code>). For example:
     * 
     * <pre>
     * -XX:MaxMetaspaceSize=1M
     * </pre>
     * 
     * @return The maximum Metaspace, or null if not explicitly set.
     */
    public String getMaxMetaspaceOption() {
        String regex = "(-XX:MaxMetaspaceSize=(\\d{1,10***REMOVED***)(" + JdkRegEx.OPTION_SIZE + ")?)";
        return getJvmOption(regex);
    ***REMOVED***

    /**
     * <pre>
     * 128M
     * </pre>
     * 
     * @return The maximum Metaspace value, or null if not set. For example:
     */
    public String getMaxMetaspaceValue() {
        return JdkUtil.getOptionValue(getMaxMetaspaceOption());
    ***REMOVED***

    public String getName() {
        return JdkUtil.LogEventType.JVM_ARGS.toString();
    ***REMOVED***

    /**
     * The option for compressed class pointers disabled.
     * 
     * <pre>
     * -XX:-UseCompressedClassPointers
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    public String getUseCompressedClassPointersDisabled() {
        String regex = "(-XX:\\-UseCompressedClassPointers)";
        return getJvmOption(regex);
    ***REMOVED***

    /**
     * The option for disabling compressed object references.
     * 
     * <pre>
     * -XX:-UseCompressedOops
     * </pre>
     * 
     * @return the option if it exists, null otherwise.
     */
    public String getUseCompressedOopsDisabled() {
        String regex = "(-XX:\\-UseCompressedOops)";
        return getJvmOption(regex);
    ***REMOVED***
***REMOVED***

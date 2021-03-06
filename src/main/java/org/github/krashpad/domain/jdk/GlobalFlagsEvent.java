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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkUtil;

/**
 * <p>
 * GLOBAL_FLAGS
 * </p>
 * 
 * <p>
 * Global flags information.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * [Global flags]
 *      intx CICompilerCount                          = 4                                         {product} {ergonomic}
 *      uint ConcGCThreads                            = 2                                         {product} {ergonomic}
 *     ccstr ErrorFile                                = /tmp/path/to/eclipse_vm_crash_%p.log            {product} {command line}
 *      uint G1ConcRefinementThreads                  = 8                                         {product} {ergonomic}
 *    size_t G1HeapRegionSize                         = 2097152                                   {product} {ergonomic}
 *     uintx GCDrainStackTargetSize                   = 64                                        {product} {ergonomic}
 *    size_t InitialHeapSize                          = 1073741824                                {product} {command line}
 *    size_t MarkStackSize                            = 4194304                                   {product} {ergonomic}
 *    size_t MaxHeapSize                              = 12884901888                               {product} {command line}
 *    size_t MaxNewSize                               = 7730102272                                {product} {ergonomic}
 *    size_t MinHeapDeltaBytes                        = 2097152                                   {product} {ergonomic}
 *     uintx NonNMethodCodeHeapSize                   = 5836300                                {pd product} {ergonomic}
 *     uintx NonProfiledCodeHeapSize                  = 131299578                              {pd product} {ergonomic}
 *     uintx ProfiledCodeHeapSize                     = 131299578                              {pd product} {ergonomic}
 *     uintx ReservedCodeCacheSize                    = 268435456                              {pd product} {command line}
 *      bool SegmentedCodeCache                       = true                                      {product} {ergonomic}
 *      intx ThreadStackSize                          = 5120                                   {pd product} {command line}
 *      bool UseCompressedClassPointers               = true                                 {lp64_product} {ergonomic}
 *      bool UseCompressedOops                        = true                                 {lp64_product} {ergonomic}
 *      bool UseG1GC                                  = true                                      {product} {ergonomic}
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class GlobalFlagsEvent implements LogEvent {

    /**
     * Regular expression for the header.
     */
    private static final String REGEX_HEADER = "\\[Global flags\\]";

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + REGEX_HEADER
            + "|[ ]{0,}(bool|ccstr|ccstrlist|intx|size_t|uint|uintx))(.*)$";

    private static Pattern pattern = Pattern.compile(REGEX);

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
    public GlobalFlagsEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.GLOBAL_FLAGS.toString();
    }

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
     * @return The value of the VM argument.
     */
    public String getValue() {
        String value = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            value = matcher.group(3);
        }
        return value;
    }

    /**
     * @return true if the log line is the header false otherwise.
     */
    public boolean isHeader() {
        return logEntry.matches(REGEX_HEADER);
    }
}

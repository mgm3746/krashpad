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
 * GLOBAL_FLAGS
 * </p>
 * 
 * <p>
 * Global flags information.
 * </p>
 * 
 * <h2>Example Logging</h2>
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
public class GlobalFlag implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "\\[Global flags\\]";

    private static Pattern pattern = Pattern.compile(GlobalFlag.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|[ ]{0,}(bool|ccstr|ccstrlist|double|intx|size_t|uint||uint64_t|uintx) ([a-zA-Z0-9]+)[ ]{1,}= ([^\\{]+)"
            + "\\{(C2 product|diagnostic|experimental|lp64_product|manageable|pd product|product|"
            + "product lp64_product)\\}( \\{(command line|command line, ergonomic|environment|ergonomic)\\})?)$";

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
    public GlobalFlag(String logEntry) {
        this.logEntry = logEntry;
    }

    /**
     * @return The flag (or null for the header).
     */
    public String getFlag() {
        String flag = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            flag = matcher.group(3);
        }
        return flag;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.GLOBAL_FLAGS.toString();
    }

    /**
     * @return The flag value (or null for the header).
     */
    public String getValue() {
        String value = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            value = matcher.group(4);
            if (value != null) {
                value = value.trim();
            }
        }
        return value;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }
}

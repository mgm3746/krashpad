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

import org.github.errcat.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestStackSlotToMemoryMappingEvent extends TestCase {

    public void testIdentity() {
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        Assert.assertTrue(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "stack at sp + 5 slots: 0x0 is NULL";
        Assert.assertTrue(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof StackSlotToMemoryMappingEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Stack slot to memory mapping:";
        Assert.assertTrue(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testError() {
        String logLine = "[error occurred during error reporting (inspecting top of stack), id 0xb, SIGSEGV (0xb) at "
                + "pc=0x00007f68376aea9e]";
        Assert.assertTrue(JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.STACK_SLOT_TO_MEMORY_MAPPING);
    ***REMOVED***
***REMOVED***
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.CompressedOopMode;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestHeapAddress {

    @Test
    void testCapitalH() {
        String logLine = "Heap address: 0x0000000500000000, size: 12288 MB, Compressed Oops mode: Zero based, Oop "
                + "shift amount: 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
        HeapAddress event = new HeapAddress(logLine);
        assertEquals(12288, event.getSize(), "Size not correct.");
        assertEquals(CompressedOopMode.ZERO, event.getCompressedOopMode(), "Compressed oop mode not correct.");
    ***REMOVED***

    @Test
    void testErrorPrintingCompressedOopsMode() {
        String logLine = "[error occurred during error reporting (printing compressed oops mode), id 0xb, SIGSEGV "
                + "(0xb) at pc=0x00007f769228928f]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testIdentity() {
        String logLine = "heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop "
                + "shift amount: 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop "
                + "shift amount: 3";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof HeapAddress,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not parsed.");
        HeapAddress heapAddressEvent = new HeapAddress(logLine);
        assertEquals(16106127360L, heapAddressEvent.getStartingAddress(), "Heap starting address not correct.");
        assertEquals(16384L, heapAddressEvent.getSize(), "Heap size not correct.");
    ***REMOVED***
***REMOVED***
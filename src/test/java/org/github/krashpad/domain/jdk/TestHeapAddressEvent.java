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

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestHeapAddressEvent {

    @Test
    void testIdentity() {
        String logLine = "Narrow klass base: 0x0000000000000000, Narrow klass shift: 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Narrow klass base: 0x0000000000000000, Narrow klass shift: 3";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof HeapAddressEvent,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop "
                + "shift amount: 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testHeaderCapitalH() {
        String logLine = "Heap address: 0x0000000500000000, size: 12288 MB, Compressed Oops mode: Zero based, Oop "
                + "shift amount: 3";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCompressClassSpaceSize() {
        String logLine = "Compressed class space size: 1073741824 Address: 0x00000007c0000000";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.HEAP_ADDRESS,
                JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.");
    ***REMOVED***
***REMOVED***
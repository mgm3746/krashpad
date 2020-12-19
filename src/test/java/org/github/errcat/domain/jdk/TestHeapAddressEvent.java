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
public class TestHeapAddressEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Narrow klass base: 0x0000000000000000, Narrow klass shift: 3";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEAP_ADDRESS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Narrow klass base: 0x0000000000000000, Narrow klass shift: 3";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapAddressEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "heap address: 0x00000003c0000000, size: 16384 MB, Compressed Oops mode: Zero based, Oop "
                + "shift amount: 3";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEAP_ADDRESS);
    ***REMOVED***

    public void testCompressClassSpaceSize() {
        String logLine = "Compressed class space size: 1073741824 Address: 0x00000007c0000000";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_ADDRESS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEAP_ADDRESS);
    ***REMOVED***
***REMOVED***
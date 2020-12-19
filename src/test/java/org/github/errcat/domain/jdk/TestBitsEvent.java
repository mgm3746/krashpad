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
public class TestBitsEvent extends TestCase {

    public void testIdentity() {
        String logLine = " Bits: [0x00007f677d83f000, 0x00007f6900a58c00)";
        Assert.assertTrue(JdkUtil.LogEventType.BITS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.BITS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = " Bits: [0x00007f677d83f000, 0x00007f6900a58c00)";
        Assert.assertTrue(JdkUtil.LogEventType.BITS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof BitsEvent);
    ***REMOVED***

    public void testHeaderMarkingBits() {
        String logLine = "Marking Bits: (CMSBitMap*) 0x00007fcbc8249ce8";
        Assert.assertTrue(JdkUtil.LogEventType.BITS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.BITS);
    ***REMOVED***

    public void testHeaderModUnionTable() {
        String logLine = "Mod Union Table: (CMSBitMap*) 0x00007fcbc8249da8";
        Assert.assertTrue(JdkUtil.LogEventType.BITS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.BITS);
    ***REMOVED***
***REMOVED***
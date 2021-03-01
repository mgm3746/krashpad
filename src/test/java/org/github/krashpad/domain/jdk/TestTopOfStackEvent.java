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

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestTopOfStackEvent extends TestCase {

    public void testIdentity() {
        String logLine = "0x00007fcbcc676c50:   00007fcbcc676cb0 00007fcbd0596b86";
        Assert.assertTrue(JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "0x00007fcbcc676c50:   00007fcbcc676cb0 00007fcbd0596b86";
        Assert.assertTrue(JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof TopOfStackEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Top of Stack: (sp=0x00007fcbcc676c50)";
        Assert.assertTrue(JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK);
    ***REMOVED***

    public void testNotInstructions() {
        String logLine = "0x00007fcbd05a3b51:   5d c3 0f 1f 44 00 00 48 8d 35 01 db 4c 00 bf 03";
        Assert.assertFalse(JdkUtil.LogEventType.TOP_OF_STACK.toString() + " incorrectly identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK);
    ***REMOVED***

    public void testSpaceAtEnd() {
        String logLine = "0x00007fcbcc676e40:   00007fcbc8056a98 00000000000000d8 ";
        Assert.assertTrue(JdkUtil.LogEventType.TOP_OF_STACK.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.TOP_OF_STACK);
    ***REMOVED***
***REMOVED***
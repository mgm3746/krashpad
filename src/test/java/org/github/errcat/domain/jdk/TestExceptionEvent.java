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
public class TestExceptionEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Event: 101.811 Thread 0x00007ff0ec698000 Exception "
                + "<a 'java/lang/ArrayIndexOutOfBoundsException'> (0x00000000ef71fd30) thrown at "
                + "[/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64/openjdk/hotspot/src/share/"
                + "vm/runtime/sharedRuntime.cpp, line 609]";
        Assert.assertTrue(JdkUtil.LogEventType.EXCEPTION_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.EXCEPTION_EVENT);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Event: 101.811 Thread 0x00007ff0ec698000 Exception "
                + "<a 'java/lang/ArrayIndexOutOfBoundsException'> (0x00000000ef71fd30) thrown at "
                + "[/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64/openjdk/hotspot/src/share/"
                + "vm/runtime/sharedRuntime.cpp, line 609]";
        Assert.assertTrue(JdkUtil.LogEventType.EXCEPTION_EVENT.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof ExceptionEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.EXCEPTION_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.EXCEPTION_EVENT);
    ***REMOVED***

    public void testMeta() {
        String logLine = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=0> (0x000000079d702b90) "
                + "thrown at [/builddir/build/BUILD/java-1.8.0-openjdk-1.8.0.265";
        Assert.assertTrue(JdkUtil.LogEventType.EXCEPTION_EVENT.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.EXCEPTION_EVENT);
    ***REMOVED***
***REMOVED***
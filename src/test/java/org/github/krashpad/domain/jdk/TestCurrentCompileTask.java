/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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
class TestCurrentCompileTask {

    @Test
    void testC1() {
        String logLine = "C1:    234  385       3       org.jboss.modules.xml.MXParser::nextImpl (1195 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Current CompileTask:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "C2:   1092  423       4       java.util.HashMap$KeyIterator::next (8 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testMethodAttributes() {
        String logLine = "C2:   1360 2202 % !   4       org.jboss.modules.Module::addExportedPaths @ 1224 "
                + "(1429 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testMethodAttributesNoneLevelNone() {
        String logLine = "C2:    595   73             sun.nio.cs.UTF_8$Encoder::encode (359 bytes)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testMethodAttributesSynchronized() {
        String logLine = "C2:147541338 34397 %s!   4       com.example.DoSomthing::run @ 2888 (3436 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testNegativeCompileTimestamp() {
        String logLine = "C2:-913846888 246084       4       com.example.package.SomeClass::doSomething (52 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "C2:   1092  423       4       java.util.HashMap$KeyIterator::next (8 bytes)";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof CurrentCompileTask,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not parsed.");
    }

    @Test
    void testTimestampHuge() {
        String logLine = "C2:1234567890 178668       4       com.example.MyClass::doSomething (52 bytes)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CURRENT_COMPILE_TASK,
                JdkUtil.LogEventType.CURRENT_COMPILE_TASK.toString() + " not identified.");
    }
}
/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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
class TestThread {

    @Test
    void test32Bit() {
        String logLine = "  0x08f59000 JavaThread \"Service Thread\" daemon [_thread_blocked, id=29308, "
                + "stack(0xd6b0d000,0xd6b5e000)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testConcurrentGcThread() {
        String logLine = "  0x00007ffff0087000 ConcurrentGCThread \"G1 Main Marker\" [stack: 0x00007fffba4b0000,"
                + "0x00007fffba5b0000] [id=24734]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testCurrentThreadJavaThread() {
        String logLine = "=>0x00007ff0ec053800 JavaThread \"main\" [_thread_in_native, id=92334, "
                + "stack(0x00007ff0f60c5000,0x00007ff0f61c5000)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testCurrentThreadThread() {
        String logLine = "=>0x00007ffeb4ee1800 (exited) Thread [stack: 0x00007ffe956e0000,0x00007ffe958e0000] "
                + "[id=107493]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testError() {
        String logLine = "[error occurred during error reporting (printing all threads), id 0xb, SIGSEGV (0xb) at "
                + "pc=0x00007fc88aaa9a74]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
        Thread logEvent = new Thread(logLine);
        assertTrue(logEvent.isErrorOccurredDuringErrorReporting(), "Error not identified.");
    }

    @Test
    void testGcTaskThread() {
        String logLine = "=>0x00007fcbc8056000 (exited) GCTaskThread [stack: 0x00007fcbcc578000,0x00007fcbcc678000] "
                + "[id=52418]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "  0x00007f19aa5128e0 JavaThread \"Thread-8\" daemon [_thread_blocked, id=18881, "
                + "stack(0x00007f199cf04000,0x00007f199d005000)]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testJavaThreadHeader() {
        String logLine = "Java Threads: ( => current thread )";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testOtherThreadHeader() {
        String logLine = "Other Threads:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "  0x00007f19aa5128e0 JavaThread \"Thread-8\" daemon [_thread_blocked, id=18881, "
                + "stack(0x00007f199cf04000,0x00007f199d005000)]";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Thread,
                JdkUtil.LogEventType.THREAD.toString() + " not parsed.");
    }

    @Test
    void testTotal() {
        String logLine = "Total: 10";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testTruncated() {
        String logLine = "  0x00007fe6ec0cbaf0 ";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testWorkerThreadRuntimeWorker() {
        String logLine = "  0x00007fe6ec0cbaf0 WorkerThread \"RuntimeWorker#9\" [stack: 0x00007fdc63cfc000,"
                + "0x00007fdc63dfc000] [id=3003234]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }

    @Test
    void testWorkerThreadZWorker() {
        String logLine = "  0x00007fe6ec084ff0 WorkerThread \"ZWorker#54\" [stack: 0x00007fe660708000,"
                + "0x00007fe660808000] [id=3001345]";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.THREAD,
                JdkUtil.LogEventType.THREAD.toString() + " not identified.");
    }
}

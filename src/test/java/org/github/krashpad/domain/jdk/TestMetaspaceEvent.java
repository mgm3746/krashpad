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
class TestMetaspaceEvent {

    @Test
    void testIdentity() {
        String logLine = "Usage:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testParseLogLine() {
        String logLine = "Usage:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof MetaspaceEvent,
                JdkUtil.LogEventType.METASPACE.toString() + " not parsed.");
    ***REMOVED***

    @Test
    void testHeader() {
        String logLine = "Metaspace:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNonClass() {
        String logLine = "  Non-class:    136.84 MB capacity,   129.90 MB ( 95%) used,     6.64 MB (  5%) "
                + "free+waste,   305.00 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testNonClassCapitalC() {
        String logLine = "   Non-Class:  507.00 KB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testClass() {
        String logLine = "      Class:     17.93 MB capacity,    14.53 MB ( 81%) used,     3.26 MB ( 18%) "
                + "free+waste,   143.81 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testBoth() {
        String logLine = "       Both:    154.76 MB capacity,   144.43 MB ( 93%) used,     9.90 MB (  6%) "
                + "free+waste,   448.81 KB ( <1%) overhead.";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testVirtualSpace() {
        String logLine = "Virtual space:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testChunFreelists() {
        String logLine = "Chunk freelists:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testMaxMetaspaceSize() {
        String logLine = "MaxMetaspaceSize: unlimited";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***

    @Test
    void testCompressedClassSpaceSize() {
        String logLine = "CompressedClassSpaceSize: 1.00 GB";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.METASPACE,
                JdkUtil.LogEventType.METASPACE.toString() + " not identified.");
    ***REMOVED***
***REMOVED***

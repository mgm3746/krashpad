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
public class TestMetaspaceEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Usage:";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Usage:";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof MetaspaceEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Metaspace:";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testNonClass() {
        String logLine = "  Non-class:    136.84 MB capacity,   129.90 MB ( 95%) used,     6.64 MB (  5%) "
                + "free+waste,   305.00 KB ( <1%) overhead.";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testNonClassCapitalC() {
        String logLine = "   Non-Class:  507.00 KB";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testClass() {
        String logLine = "      Class:     17.93 MB capacity,    14.53 MB ( 81%) used,     3.26 MB ( 18%) "
                + "free+waste,   143.81 KB ( <1%) overhead.";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testBoth() {
        String logLine = "       Both:    154.76 MB capacity,   144.43 MB ( 93%) used,     9.90 MB (  6%) "
                + "free+waste,   448.81 KB ( <1%) overhead.";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testVirtualSpace() {
        String logLine = "Virtual space:";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testChunFreelists() {
        String logLine = "Chunk freelists:";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testMaxMetaspaceSize() {
        String logLine = "MaxMetaspaceSize: unlimited";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***

    public void testCompressedClassSpaceSize() {
        String logLine = "CompressedClassSpaceSize: 1.00 GB";
        Assert.assertTrue(JdkUtil.LogEventType.METASPACE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.METASPACE);
    ***REMOVED***
***REMOVED***

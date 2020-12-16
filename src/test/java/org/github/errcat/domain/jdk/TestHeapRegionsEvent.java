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
public class TestHeapRegionsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "|    0|CS |BTE    67a200000,    67a400000,    67a400000|TAMS    67a400000|UWM    "
                + "67a400000|U  2048K|T  2047K|G     0B|S    56B|L 31152B|CP   0";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.HEAP_REGIONS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "|    0|CS |BTE    67a200000,    67a400000,    67a400000|TAMS    67a400000|UWM    "
                + "67a400000|U  2048K|T  2047K|G     0B|S    56B|L 31152B|CP   0";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "Heap Regions:";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testHeaderEu() {
        String logLine = "EU=empty-uncommitted, EC=empty-committed, R=regular, H=humongous start, HC=humongous "
                + "continuation, CS=collection set, T=trash, P=pinned";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testHeaderBte() {
        String logLine = "BTE=bottom/top/end, U=used, T=TLAB allocs, G=GCLAB allocs, S=shared allocs, L=live data";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testHeaderR() {
        String logLine = "R=root, CP=critical pins, TAMS=top-at-mark-start, UWM=update watermark";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testHeaderSn() {
        String logLine = "SN=alloc sequence number";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testBarrierSet() {
        String logLine = "ShenandoahBarrierSet";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***

    public void testPollingPage() {
        String logLine = "Polling page: 0x00007ffb9210b000";
        Assert.assertTrue(JdkUtil.LogEventType.HEAP_REGIONS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof HeapRegionsEvent);
    ***REMOVED***
***REMOVED***

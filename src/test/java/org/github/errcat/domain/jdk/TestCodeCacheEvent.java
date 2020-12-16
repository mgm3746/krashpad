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
public class TestCodeCacheEvent extends TestCase {

    public void testIdentity() {
        String logLine = "CodeCache: size=245760Kb used=145576Kb max_used=178661Kb free=100183Kb";
        Assert.assertTrue(JdkUtil.LogEventType.CODE_CACHE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.CODE_CACHE);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "CodeCache: size=245760Kb used=145576Kb max_used=178661Kb free=100183Kb";
        Assert.assertTrue(JdkUtil.LogEventType.CODE_CACHE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CodeCacheEvent);
    ***REMOVED***

    public void testBounds() {
        String logLine = " bounds [0x00007ffb8051b000, 0x00007ffb8b60b000, 0x00007ffb8f51b000]";
        Assert.assertTrue(JdkUtil.LogEventType.CODE_CACHE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CodeCacheEvent);
    ***REMOVED***

    public void testTotalBlobs() {
        String logLine = " total_blobs=24995 nmethods=23856 adapters=1049";
        Assert.assertTrue(JdkUtil.LogEventType.CODE_CACHE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CodeCacheEvent);
    ***REMOVED***

    public void testCompilation() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.CODE_CACHE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof CodeCacheEvent);
    ***REMOVED***
***REMOVED***

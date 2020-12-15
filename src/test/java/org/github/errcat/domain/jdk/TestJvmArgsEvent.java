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
public class TestJvmArgsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "jvm_args: -Xloggc:gc.log -Dorg.eclipse.swt.internal.gtk.cairoGraphics=false -Xms512m "
                + "-Xmx1024m -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=detail -XX:+PrintNMTStatistics "
                + "-Dosgi.instance.area.default=@user.home/workspace";
        Assert.assertTrue(JdkUtil.LogEventType.JVM_ARGS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.JVM_ARGS);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "jvm_args: -Xloggc:gc.log -Dorg.eclipse.swt.internal.gtk.cairoGraphics=false -Xms512m "
                + "-Xmx1024m -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=detail -XX:+PrintNMTStatistics "
                + "-Dosgi.instance.area.default=@user.home/workspace";
        Assert.assertTrue(JdkUtil.LogEventType.JVM_ARGS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof JvmArgsEvent);
    ***REMOVED***

    public void testMaxValues() {
        String logLine = "jvm_args: -Xloggc:gc.log -Dorg.eclipse.swt.internal.gtk.cairoGraphics=false -Xms512m "
                + "-Xmx1024m -XX:MaxMetaspaceSize=2M -XX:+UnlockDiagnosticVMOptions -XX:NativeMemoryTracking=detail "
                + "-XX:+PrintNMTStatistics -Dosgi.instance.area.default=@user.home/workspace";
        JvmArgsEvent event = new JvmArgsEvent(logLine);
        Assert.assertEquals("Heap max not correct.", "1024m", event.getMaxHeapValue());
        Assert.assertEquals("Metaspace max not correct.", "2M", event.getMaxMetaspaceValue());
    ***REMOVED***
***REMOVED***

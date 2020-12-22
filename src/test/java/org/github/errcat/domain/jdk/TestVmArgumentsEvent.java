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
public class TestVmArgumentsEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Launcher Type: SUN_STANDARD";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testParseLogLine() {
        String logLine = "Launcher Type: SUN_STANDARD";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof VmArgumentsEvent);
    }

    public void testHeader() {
        String logLine = "VM Arguments:";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testJvmArgs() {
        String logLine = "jvm_args: -D[Standalone] -verbose:gc -Xloggc:/path/to/gc.log";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testJavaCommand() {
        String logLine = "java_command: /path/to/jboss-modules.jar -Djboss.home.dir=/path/to/standalone";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
        VmArgumentsEvent event = new VmArgumentsEvent(logLine);
        Assert.assertTrue("Java command not identified.", event.isJavaCommand());
    }

    public void testJavaClassPath() {
        String logLine = "java_class_path (initial): /path/to/jboss-modules.jar";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlags() {
        String logLine = "[Global flags]";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlagIntx() {
        String logLine = "     intx CICompilerCount                          = 4"
                + "                                         {product} {ergonomic}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlagUint() {
        String logLine = "     uint ConcGCThreads                            = 2"
                + "                                         {product} {ergonomic}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlagCcstr() {
        String logLine = "    ccstr ErrorFile                                = /path/to/vm_crash_%p.log            "
                + "{product} {command line}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlagSizeT() {
        String logLine = "   size_t G1HeapRegionSize                         = 2097152"
                + "                                   {product} {ergonomic}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlagUintx() {
        String logLine = "    uintx GCDrainStackTargetSize                   = 64"
                + "                                        {product} {ergonomic}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testGlobalFlagBool() {
        String logLine = "     bool SegmentedCodeCache                       = true"
                + "                                      {product} {ergonomic}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }

    public void testCcstrlist() {
        String logLine = "ccstrlist OnOutOfMemoryError                       = /u/search/bin/on-oom.sh"
                + "                   {product} {command line}";
        Assert.assertTrue(JdkUtil.LogEventType.VM_ARGUMENTS.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.VM_ARGUMENTS);
    }
}
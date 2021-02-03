/**********************************************************************************************************************
 * krashpad                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020-2021 Mike Millson                                                                                    *
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
public class TestRegisterToMemoryMappingEvent extends TestCase {

    public void testIdentity() {
        String logLine = "RAX=0x0000000000000001 is an unknown value";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "RAX=0x0000000000000001 is an unknown value";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof RegisterToMemoryMappingEvent);
    ***REMOVED***

    public void testHeader() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testClass() {
        String logLine = "java.util.LinkedList$Node";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testKlass() {
        String logLine = " - klass: 'java/util/LinkedList$Node'";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testKlassWithAddress() {
        String logLine = "{0x0000000090057468***REMOVED*** - klass: 'org/elasticsearch/common/util/concurrent/ThreadContext"
                + "$ContextPreservingAbstractRunnable'";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testNoRegisterInformation() {
        String logLine = "R13=";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testErrorReportingRegisterInfo() {
        String logLine = "[error occurred during error reporting (printing register info), id 0xb]";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testNoRbxInformation() {
        String logLine = "RBX=";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testB() {
        String logLine = "[B";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testLength() {
        String logLine = " - length: 4096";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testStubroutines() {
        String logLine = "StubRoutines::unsafe_arraycopy [0x00007feac1053080, 0x00007feac10530bb[ (59 bytes)";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testCodeBlob() {
        String logLine = "[CodeBlob (0x00007f45c5205f90)]";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testFramesize() {
        String logLine = "***REMOVED***";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testMethodEntryPoint() {
        String logLine = "method entry point (kind = zerolocals)  [0x00007f95e5519600, 0x00007f95e5519ac0]  "
                + "1216 bytes";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testR8Space() {
        String logLine = "R8 ={method***REMOVED*** {0x00007fdd9a9b91a8***REMOVED*** 'readBlock' '(Ljava/nio/ByteBuffer;II)I' in "
                + "'sun/font/TrueTypeFont'";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testI() {
        String logLine = "[I";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testC() {
        String logLine = "[C";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testL() {
        String logLine = "[Ljava.lang.Object;";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testRBP() {
        String logLine = "RBP=0x0000000000000003 is an unknown value";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testRDI() {
        String logLine = "RDI=0x0 is NULL";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testRSI() {
        String logLine = "RSI=";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testRSP() {
        String logLine = "RSP=0x00007f4c86318ce0 is pointing into the stack for thread: 0x00007f4e2de48c30";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testRCX() {
        String logLine = "RCX=";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testRDX() {
        String logLine = "RDX=0x0000000000000e8c is an unknown value";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testBufferBlob() {
        String logLine = "BufferBlob (0x00007ffae9049e10) used for adapters";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***

    public void testAdapterForSignature() {
        String logLine = "Adapter for signature: 0x00007fb285046c20 is at code_begin+0 in";
        Assert.assertTrue(JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.REGISTER_TO_MEMORY_MAPPING);
    ***REMOVED***
***REMOVED***
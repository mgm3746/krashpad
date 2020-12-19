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
***REMOVED***
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
package org.github.errcat.util.jdk;

import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestJdkUtil extends TestCase {

    public void testJdk8UpdateNumber() {
        String jdk8ReleaseString = "1.8.0_222-b10";
        Assert.assertEquals("JDK8 update number not correct.", 222, JdkUtil.getJdk8UpdateNumber(jdk8ReleaseString));
    ***REMOVED***

    public void testByteOptionValue() {
        Assert.assertEquals("-Xss not correct.", "512k", JdkUtil.getByteOptionValue("-Xss512k"));
        Assert.assertEquals("-XX:MaxMetaspaceSize not correct.", "2048m",
                JdkUtil.getByteOptionValue("-XX:MaxMetaspaceSize=2048m"));
    ***REMOVED***

    public void testOptionEnabled() {
        Assert.assertTrue("-XX:+PrintFlagsFinal not identified as enabled.",
                JdkUtil.isOptionEnabled("-XX:+PrintFlagsFinal"));
        Assert.assertFalse("-XX:-TraceClassUnloading incorrectly identified as enabled.",
                JdkUtil.isOptionEnabled("-XX:-TraceClassUnloading"));
    ***REMOVED***

    public void testOptionDisabled() {
        Assert.assertFalse("-XX:+PrintFlagsFinal incorrectly identified as disabled.",
                JdkUtil.isOptionDisabled("-XX:+PrintFlagsFinal"));
        Assert.assertTrue("-XX:-TraceClassUnloading not identified as enabled.",
                JdkUtil.isOptionDisabled("-XX:-TraceClassUnloading"));
    ***REMOVED***

    public void testByteOptionBytes() {
        Assert.assertEquals("-Xss not correct.", 512L * 1024, JdkUtil.getByteOptionBytes("512k"));
        Assert.assertEquals("-XX:MaxMetaspaceSize not correct.", 2048L * 1024 * 1024,
                JdkUtil.getByteOptionBytes("2048m"));
    ***REMOVED***

***REMOVED***

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

import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestJvmOptions extends TestCase {

    public void testCommonOptions() {
        String jvmArgs = "-Xmx1500m -Xms1000m -Xss512k -XX:MaxMetaspaceSize=2048m";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("Thread stack size not correct.", "-Xss512k", jvmOptions.getThreadStackSize());
        Assert.assertEquals("Max heap size not correct.", "-Xmx1500m", jvmOptions.getMaxHeapSize());
        Assert.assertEquals("Max metaspace size not correct.", "-XX:MaxMetaspaceSize=2048m",
                jvmOptions.getMaxMetaspaceSize());
    ***REMOVED***

    public void testAbrt() {
        String jvmArgs = "-XX:OnOutOfMemoryError=/bin/kill -ABRT %p";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("ABRT not correct.", "-ABRT %p", jvmOptions.getAbrt());
    ***REMOVED***

    public void testDebugging() {
        String jvmArgs = "-XX:OnOutOfMemoryError=/bin/kill "
                + "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n -ABRT %p";
        JvmOptions jvmOptions = new JvmOptions(jvmArgs);
        Assert.assertEquals("JDPA socket transport (debugging) not correct.",
                "-agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n",
                jvmOptions.getJpdaSocketTransport());
    ***REMOVED***
***REMOVED***
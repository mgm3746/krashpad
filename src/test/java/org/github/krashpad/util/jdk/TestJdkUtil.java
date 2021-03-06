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
package org.github.krashpad.util.jdk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestJdkUtil {

    @Test
    void testJdk8UpdateNumber() {
        String jdk8ReleaseString = "1.8.0_222-b10";
        assertEquals(222, JdkUtil.getJdk8UpdateNumber(jdk8ReleaseString), "JDK8 update number not correct.");
    }

    @Test
    void testByteOptionValue() {
        assertEquals("512k", JdkUtil.getByteOptionValue("-Xss512k"), "-Xss not correct.");
        assertEquals("2048m", JdkUtil.getByteOptionValue("-XX:MaxMetaspaceSize=2048m"),
                "-XX:MaxMetaspaceSize not correct.");
    }

    @Test
    void testOptionEnabled() {
        assertTrue(JdkUtil.isOptionEnabled("-XX:+PrintFlagsFinal"), "-XX:+PrintFlagsFinal not identified as enabled.");
        assertFalse(JdkUtil.isOptionEnabled("-XX:-TraceClassUnloading"),
                "-XX:-TraceClassUnloading incorrectly identified as enabled.");
    }

    @Test
    void testOptionDisabled() {
        assertFalse(JdkUtil.isOptionDisabled("-XX:+PrintFlagsFinal"),
                "-XX:+PrintFlagsFinal incorrectly identified as disabled.");
        assertTrue(JdkUtil.isOptionDisabled("-XX:-TraceClassUnloading"),
                "-XX:-TraceClassUnloading not identified as enabled.");
    }

    @Test
    void testByteOptionBytes() {
        assertEquals(512L * 1024, JdkUtil.getByteOptionBytes("512k"), "-Xss not correct.");
        assertEquals(2048L * 1024 * 1024, JdkUtil.getByteOptionBytes("2048m"), "-XX:MaxMetaspaceSize not correct.");
    }

}

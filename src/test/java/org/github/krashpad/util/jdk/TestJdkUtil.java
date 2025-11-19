/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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

import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestJdkUtil {

    @Test
    void testIsReleaseStringInReleases() {
        String jdkReleaseString = "11.0.15+10-LTS";
        assertTrue(JdkUtil.isReleaseStringInReleases(jdkReleaseString, Jdk11.RHEL8_X86_64_RPMS),
                jdkReleaseString + " not identified as a known release string.");
        assertFalse(JdkUtil.isReleaseStringInReleases(jdkReleaseString, Jdk8.RHEL8_X86_64_RPMS),
                jdkReleaseString + " incorrectly identified as a known release string.");
    }

    @Test
    void testJdkVersionNumber17() {
        assertEquals(17, JdkUtil.getJavaSpecificationNumber(JavaSpecification.JDK17), "Update number not correct.");
    }

    @Test
    void testJdkVersionNumber21() {
        assertEquals(21, JdkUtil.getJavaSpecificationNumber(JavaSpecification.JDK21), "Update number not correct.");
    }

    @Test
    void testJdkVersionNumber8() {
        assertEquals(8, JdkUtil.getJavaSpecificationNumber(JavaSpecification.JDK8), "Update number not correct.");
    }

    @Test
    void testOptionDisabled() {
        assertFalse(JdkUtil.isOptionDisabled("-XX:+PrintFlagsFinal"),
                "-XX:+PrintFlagsFinal incorrectly identified as disabled.");
        assertTrue(JdkUtil.isOptionDisabled("-XX:-TraceClassUnloading"),
                "-XX:-TraceClassUnloading not identified as enabled.");
    }

    @Test
    void testOptionEnabled() {
        assertTrue(JdkUtil.isOptionEnabled("-XX:+PrintFlagsFinal"), "-XX:+PrintFlagsFinal not identified as enabled.");
        assertFalse(JdkUtil.isOptionEnabled("-XX:-TraceClassUnloading"),
                "-XX:-TraceClassUnloading incorrectly identified as enabled.");
    }

    @Test
    void testUpdateNumberFromBuildStringJdk8() {
        assertEquals(282, JdkUtil.getJdkUpdateNumber("1.8.0_282-b08"), "Update number not correct.");
    }

    @Test
    void testUpdateNumberFromVersionStringJdk8() {
        assertEquals(282, JdkUtil.getJdkUpdateNumber("8.0_282-b08"), "Update number not correct.");
    }

    @Test
    void testUpdateNumberJdk11() {
        String jdk11ReleaseString = "11.0.9+11-LTS";
        assertEquals(9, JdkUtil.getJdkUpdateNumber(jdk11ReleaseString), "JDK11 update number not correct.");
    }

    @Test
    void testUpdateNumberJdk17() {
        String jdk17ReleaseString = "17.0.4+8-LTS";
        assertEquals(4, JdkUtil.getJdkUpdateNumber(jdk17ReleaseString), "JDK17 update number not correct.");
    }

    @Test
    void testUpdateNumberJdk8() {
        String jdk8ReleaseString = "1.8.0_222-b10";
        assertEquals(222, JdkUtil.getJdkUpdateNumber(jdk8ReleaseString), "JDK8 update number not correct.");
    }
}

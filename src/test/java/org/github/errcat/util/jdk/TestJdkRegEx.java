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
public class TestJdkRegEx extends TestCase {

    public void testMemoryRegion() {
        String s = "7f0f159f8000-7f0f159f9000";
        Assert.assertTrue("Memory region not identified.", s.matches(JdkRegEx.MEMORY_REGION));
    }

    public void testPermission() {
        String s = "rw-p";
        Assert.assertTrue("Permission not identified.", s.matches(JdkRegEx.PERMISION));
    }

    public void testFileOffset() {
        String s = "0001a000";
        Assert.assertTrue("File offset not identified.", s.matches(JdkRegEx.FILE_OFFSET));
    }

    public void testDeviceIds() {
        String s = "fd:0d";
        Assert.assertTrue("Device ids not identified.", s.matches(JdkRegEx.DEVICE_IDS));
    }

    public void testInode() {
        String s = "135188646";
        Assert.assertTrue("Inode not identified.", s.matches(JdkRegEx.INODE));
    }

    public void testFile() {
        String s = "/usr/lib64/libaio.so.1.0.1";
        Assert.assertTrue("Inode not identified.", s.matches(JdkRegEx.FILE));
    }

    public void testRegion() {
        String s = "[vsyscall]";
        Assert.assertTrue("Inode not identified.", s.matches(JdkRegEx.AREA));
    }

    public void testRhel6Amd64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64";
        Assert.assertTrue("Red Hat RPM OpenJDK 11 directory not identified.",
                dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR));
    }

    public void testRhel6Amd64RpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        Assert.assertTrue("Red Hat RPM file path not identified.", path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH));
    }

    public void testRhel7Amd64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64";
        Assert.assertTrue("Red Hat RPM OpenJDK 11 directory not identified.",
                dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR));
    }

    public void testRhel7Ppc64leRpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le";
        Assert.assertTrue("Red Hat RPM OpenJDK 11 directory not identified.",
                dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR));
    }

    public void testRhel7Amd64RpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        Assert.assertTrue("Red Hat RPM file path not identified.", path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH));
    }

    public void testRhel7Ppc64leRpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        Assert.assertTrue("Red Hat RPM file path not identified.", path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH));
    }

    public void testRhel7Amd64RpmOpenjdk11Dir() {
        String dir = "java-11-openjdk-11.0.7.10-4.el7_8.x86_64";
        Assert.assertTrue("Red Hat RPM OpenJDK 11 directory not identified.",
                dir.matches(JdkRegEx.RH_RPM_OPENJDK11_DIR));
    }

    public void testRhel7Amd64RpmOpenjdk11LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so";
        Assert.assertTrue("Red Hat RPM file path not identified.", path.matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH));
    }

    public void testRhel8Amd64RpmOpenjdk11Dir() {
        String dir = "java-11-openjdk-11.0.8.10-0.el8_2.x86_64";
        Assert.assertTrue("Red Hat RPM OpenJDK 11 directory not identified.",
                dir.matches(JdkRegEx.RH_RPM_OPENJDK11_DIR));
    }

    public void testRhel8Amd64RpmOpenjdk11LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-11-openjdk-11.0.8.10-0.el8_2.x86_64/lib/server/libjvm.so";
        Assert.assertTrue("Red Hat RPM file path not identified.", path.matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH));
    }

    public void testJdk12ReleaseString() {
        String release = "12.0.1+12";
        Assert.assertTrue("Release not identified.", release.matches(JdkRegEx.RELEASE_STRING));
    }

    public void testAddress64Bit() {
        String release = "0x000000000232c800";
        Assert.assertTrue("Address not identified.", release.matches(JdkRegEx.ADDRESS64));
    }

    public void testAddress32Bit() {
        String release = "0x08ec6400";
        Assert.assertTrue("Address not identified.", release.matches(JdkRegEx.ADDRESS32));
    }

    public void testTimestampWithCharacter() {
        String timestamp = "A.123";
        Assert.assertFalse("Timestamps are decimal numbers.", timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testTimestampWithFewerDecimalPlaces() {
        String timestamp = "1.12";
        Assert.assertFalse("Timestamps have 3 decimal places.", timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testTimestampWithMoreDecimalPlaces() {
        String timestamp = "1.1234";
        Assert.assertFalse("Timestamps have 3 decimal places.", timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testTimestampWithNoDecimal() {
        String timestamp = "11234";
        Assert.assertFalse("Timestamps have 3 decimal places.", timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testTimestampLessThanOne() {
        String timestamp = ".123";
        Assert.assertTrue("Timestamps less than one do not have a leading zero.",
                timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testTimestampValid() {
        String timestamp = "1.123";
        Assert.assertTrue("'" + timestamp + "' is a valid timestamp.", timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testTimestampDecimalComma() {
        String timestamp = "1,123";
        Assert.assertTrue("'" + timestamp + "' is a valid timestamp.", timestamp.matches(JdkRegEx.TIMESTAMP));
    }

    public void testSizeK() {
        String size = "1234k";
        Assert.assertTrue("Size not recognized.", size.matches(JdkRegEx.SIZE));
    }
}

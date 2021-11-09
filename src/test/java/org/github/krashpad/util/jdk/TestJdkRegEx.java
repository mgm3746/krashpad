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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestJdkRegEx {

    @Test
    void testMemoryRegion() {
        String s = "7f0f159f8000-7f0f159f9000";
        assertTrue(s.matches(JdkRegEx.MEMORY_REGION), "Memory region not identified.");
    ***REMOVED***

    @Test
    void testPermission() {
        String s = "rw-p";
        assertTrue(s.matches(JdkRegEx.PERMISION), "Permission not identified.");
    ***REMOVED***

    @Test
    void testFileOffset() {
        String s = "0001a000";
        assertTrue(s.matches(JdkRegEx.FILE_OFFSET), "File offset not identified.");
    ***REMOVED***

    @Test
    void testDeviceIds() {
        String s = "fd:0d";
        assertTrue(s.matches(JdkRegEx.DEVICE_IDS), "Device ids not identified.");
    ***REMOVED***

    @Test
    void testInode() {
        String s = "135188646";
        assertTrue(s.matches(JdkRegEx.INODE), "Inode not identified.");
    ***REMOVED***

    @Test
    void testFile() {
        String s = "/usr/lib64/libaio.so.1.0.1";
        assertTrue(s.matches(JdkRegEx.FILE), "Inode not identified.");
    ***REMOVED***

    @Test
    void testRegion() {
        String s = "[vsyscall]";
        assertTrue(s.matches(JdkRegEx.AREA), "Inode not identified.");
    ***REMOVED***

    @Test
    void testRhel6Amd64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel6Amd64RpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel7Amd64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Ppc64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Ppc64leRpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Amd64RpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel7Ppc64leRpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel7Amd64RpmOpenjdk11Dir() {
        String dir = "java-11-openjdk-11.0.7.10-4.el7_8.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK11_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Amd64RpmOpenjdk11LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel8Amd64RpmOpenjdk11Dir() {
        String dir = "java-11-openjdk-11.0.8.10-0.el8_2.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK11_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel8Amd64RpmOpenjdk11Dir4PointRelease() {
        String dir = "java-11-openjdk-11.0.10.0.9-0.el7_9.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK11_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel8Amd64RpmOpenjdk11LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-11-openjdk-11.0.8.10-0.el8_2.x86_64/lib/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testJdk12ReleaseString() {
        String release = "12.0.1+12";
        assertTrue(release.matches(JdkRegEx.RELEASE_STRING), "Release not identified.");
    ***REMOVED***

    @Test
    void testAddress64Bit() {
        String release = "0x000000000232c800";
        assertTrue(release.matches(JdkRegEx.ADDRESS64), "Address not identified.");
    ***REMOVED***

    @Test
    void testAddress32Bit() {
        String release = "0x08ec6400";
        assertTrue(release.matches(JdkRegEx.ADDRESS32), "Address not identified.");
    ***REMOVED***

    @Test
    void testTimestampWithCharacter() {
        String timestamp = "A.123";
        assertFalse(timestamp.matches(JdkRegEx.TIMESTAMP), "Timestamps are decimal numbers.");
    ***REMOVED***

    @Test
    void testTimestampWithFewerDecimalPlaces() {
        String timestamp = "1.12";
        assertFalse(timestamp.matches(JdkRegEx.TIMESTAMP), "Timestamps have 3 decimal places.");
    ***REMOVED***

    @Test
    void testTimestampWithMoreDecimalPlaces() {
        String timestamp = "1.1234";
        assertFalse(timestamp.matches(JdkRegEx.TIMESTAMP), "Timestamps have 3 decimal places.");
    ***REMOVED***

    @Test
    void testTimestampWithNoDecimal() {
        String timestamp = "11234";
        assertFalse(timestamp.matches(JdkRegEx.TIMESTAMP), "Timestamps have 3 decimal places.");
    ***REMOVED***

    @Test
    void testTimestampLessThanOne() {
        String timestamp = ".123";
        assertTrue(timestamp.matches(JdkRegEx.TIMESTAMP), "Timestamps less than one do not have a leading zero.");
    ***REMOVED***

    @Test
    void testTimestampValid() {
        String timestamp = "1.123";
        assertTrue(timestamp.matches(JdkRegEx.TIMESTAMP), "'" + timestamp + "' is a valid timestamp.");
    ***REMOVED***

    @Test
    void testTimestampDecimalComma() {
        String timestamp = "1,123";
        assertTrue(timestamp.matches(JdkRegEx.TIMESTAMP), "'" + timestamp + "' is a valid timestamp.");
    ***REMOVED***

    @Test
    void testSizeK() {
        String size = "1234k";
        assertTrue(size.matches(JdkRegEx.SIZE), "Size not recognized.");
    ***REMOVED***

    @Test
    void testNullPointer32Bit() {
        String address = "0x00000000";
        assertTrue(address.matches(JdkRegEx.NULL_POINTER), "Null pointer not recognized.");
    ***REMOVED***

    @Test
    void testNullPointer64Bit() {
        String address = "0x0000000000000000";
        assertTrue(address.matches(JdkRegEx.NULL_POINTER), "Null pointer not recognized.");
    ***REMOVED***

    @Test
    void testSizeKb() {
        String address = "3.00 KB";
        assertTrue(address.matches(JdkRegEx.SIZE2), "SIZE2 not recognized.");
    ***REMOVED***

    @Test
    void testSizeMb() {
        String address = "395.36 MB";
        assertTrue(address.matches(JdkRegEx.SIZE2), "SIZE2 not recognized.");
    ***REMOVED***

    @Test
    void testSizeGb() {
        String address = "1.00 GB";
        assertTrue(address.matches(JdkRegEx.SIZE2), "SIZE2 not recognized.");
    ***REMOVED***

    @Test
    void testPercent() {
        String address = "54%";
        assertTrue(address.matches(JdkRegEx.PERCENT), "PERCENT not recognized.");
    ***REMOVED***

    @Test
    void testAmq() {
        String javaCommand = "java_command: org.apache.activemq.artemis.boot.Artemis run";
        assertTrue(javaCommand.matches(JdkRegEx.ARTEMIS), "AMQ not recognized.");
        assertFalse(javaCommand.matches(JdkRegEx.ARTEMIS_CLI), "AMQ CLI incorrectly recognized.");
    ***REMOVED***

    @Test
    void testAmqCli() {
        String javaCommand = "java_command: org.apache.activemq.artemis.boot.Artemis queue purge --name ExpiryQueue "
                + "--url tcp://mydomain:12345 --user myuser --password mypassword";
        assertFalse(javaCommand.matches(JdkRegEx.ARTEMIS), "AMQ incorrectly recognized.");
        assertTrue(javaCommand.matches(JdkRegEx.ARTEMIS_CLI), "AMQ CLI not recognized.");
    ***REMOVED***
***REMOVED***

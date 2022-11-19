/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
    void testAddress32Bit() {
        String release = "0x08ec6400";
        assertTrue(release.matches(JdkRegEx.ADDRESS32), "Address not identified.");
    ***REMOVED***

    @Test
    void testAddress64Bit() {
        String release = "0x000000000232c800";
        assertTrue(release.matches(JdkRegEx.ADDRESS64), "Address not identified.");
    ***REMOVED***

    @Test
    void testAmq() {
        String javaCommand = "java_command: org.apache.activemq.artemis.boot.Artemis run";
        assertTrue(javaCommand.matches(JdkRegEx.ARTEMIS_COMMAND), "AMQ not recognized.");
        assertFalse(javaCommand.matches(JdkRegEx.COMMAND_ARTEMIS_CLI), "AMQ CLI incorrectly recognized.");
    ***REMOVED***

    @Test
    void testAmqCli() {
        String javaCommand = "java_command: org.apache.activemq.artemis.boot.Artemis queue purge --name ExpiryQueue "
                + "--url tcp://mydomain:12345 --user myuser --password mypassword";
        assertFalse(javaCommand.matches(JdkRegEx.ARTEMIS_COMMAND), "AMQ incorrectly recognized.");
        assertTrue(javaCommand.matches(JdkRegEx.COMMAND_ARTEMIS_CLI), "AMQ CLI not recognized.");
    ***REMOVED***

    @Test
    void testAppDynamicsJar() {
        String jar = "javaagent.jar";
        assertTrue(jar.matches(JdkRegEx.JAR_APP_DYNAMICS), "AppDynamics jar not identified.");
    ***REMOVED***

    @Test
    void testAppDynamicsJavaAgentLinux() {
        String javaagent = "-javaagent:/path/to/appdynamics/javaagent.jar";
        assertTrue(javaagent.matches(JdkRegEx.JAVAAGENT_APP_DYNAMICS), "AppDynamics javaagent not identified.");
    ***REMOVED***

    @Test
    void testAppDynamicsJavaAgentWindows() {
        String javaagent = "-javaagent:C:\\appdynamics\\appagent\\javaagent\\javaagent.jar";
        assertTrue(javaagent.matches(JdkRegEx.JAVAAGENT_APP_DYNAMICS), "AppDynamics javaagent not identified.");
    ***REMOVED***

    @Test
    void testAppDynamicsPackage() {
        String pckage = "com.singularity.ee.agent";
        assertTrue(pckage.matches(JdkRegEx.PACKAGE_APP_DYNAMICS), "AppDynamics package not identified.");
    ***REMOVED***

    @Test
    void testClass() {
        String address = "com.example.MyClass";
        assertTrue(address.matches(JdkRegEx.CLASS), "CLASS not recognized.");
    ***REMOVED***

    @Test
    void testClassInner() {
        String address = "com.example.MyClass$MyInnerClass";
        assertTrue(address.matches(JdkRegEx.CLASS), "CLASS not recognized.");
    ***REMOVED***

    @Test
    void testClassStartsWithNumber() {
        String address = "com.example.1MyClass";
        assertFalse(address.matches(JdkRegEx.CLASS), "CLASS incorrectly recognized.");
    ***REMOVED***

    @Test
    void testClassStartsWithUnderscore() {
        String address = "com.example._MyClass";
        assertTrue(address.matches(JdkRegEx.CLASS), "CLASS not recognized.");
    ***REMOVED***

    @Test
    void testCrashDate() {
        String time = "Tue Aug 18 14:10:59 2020";
        assertTrue(time.matches(JdkRegEx.CRASH_DATE_TIME), "'" + time + "' is a valid crash time.");
    ***REMOVED***

    @Test
    void testCrashDatewithTimeZone() {
        String time = "Tue Mar  1 09:13:16 2022 UTC";
        assertTrue(time.matches(JdkRegEx.CRASH_DATE_TIME), "'" + time + "' is a valid crash time.");
    ***REMOVED***

    @Test
    void testDeviceIds() {
        String s = "fd:0d";
        assertTrue(s.matches(JdkRegEx.DEVICE_IDS), "Device ids not identified.");
    ***REMOVED***

    @Test
    void testFileLinux() {
        String s = "/usr/lib64/libaio.so.1.0.1";
        assertTrue(s.matches(JdkRegEx.FILE), "Inode not identified.");
    ***REMOVED***

    @Test
    void testFileOffset() {
        String s = "0001a000";
        assertTrue(s.matches(JdkRegEx.FILE_OFFSET), "File offset not identified.");
    ***REMOVED***

    @Test
    void testFileWindows() {
        String file = "E:\\path\\java\\bin\\server\\jvm.dll";
        assertTrue(file.matches(JdkRegEx.FILE), "File not recognized.");
    ***REMOVED***

    @Test
    void testInode() {
        String s = "135188646";
        assertTrue(s.matches(JdkRegEx.INODE), "Inode not identified.");
    ***REMOVED***

    @Test
    void testJBossVersionDoubleDashVersion() {
        String javaCommand = "java_command: C:\\path\\to\\jboss-modules.jar -mp "
                + "C:\\path\\to\\modules org.jboss.as.standalone -Djboss.home.dir=C:\\path\\to --version";
        assertTrue(javaCommand.matches(JdkRegEx.COMMAND_JBOSS_VERSION), "JBoss version check not recognized.");
    ***REMOVED***

    @Test
    void testJBossVersionLowercaseV() {
        String javaCommand = "java_command: C:\\path\\to\\jboss-modules.jar -mp "
                + "C:\\path\\to\\modules org.jboss.as.standalone -Djboss.home.dir=C:\\path\\to -v";
        assertTrue(javaCommand.matches(JdkRegEx.COMMAND_JBOSS_VERSION), "JBoss version check not recognized.");
    ***REMOVED***

    @Test
    void testJBossVersionSingleDashVersion() {
        String javaCommand = "java_command: C:\\path\\to\\jboss-modules.jar -mp "
                + "C:\\path\\to\\modules org.jboss.as.standalone -Djboss.home.dir=C:\\path\\to -version";
        assertTrue(javaCommand.matches(JdkRegEx.COMMAND_JBOSS_VERSION), "JBoss version check not recognized.");
    ***REMOVED***

    @Test
    void testJBossVersionUppercaseV() {
        String javaCommand = "java_command: C:\\path\\to\\jboss-modules.jar -mp "
                + "C:\\path\\to\\modules org.jboss.as.standalone -Djboss.home.dir=C:\\path\\to -V";
        assertTrue(javaCommand.matches(JdkRegEx.COMMAND_JBOSS_VERSION), "JBoss version check not recognized.");
    ***REMOVED***

    @Test
    void testJdk12ReleaseString() {
        String release = "12.0.1+12";
        assertTrue(release.matches(JdkRegEx.VERSION_STRING), "Version string not identified.");
    ***REMOVED***

    @Test
    void testJdk8BuildString() {
        String release = "1.8.0_251-b08";
        assertTrue(release.matches(JdkRegEx.BUILD_STRING), "Build string not identified.");
    ***REMOVED***

    @Test
    void testMemoryRegionLinux() {
        String s = "7f0f159f8000-7f0f159f9000";
        assertTrue(s.matches(JdkRegEx.MEMORY_REGION), "Memory region not identified.");
    ***REMOVED***

    @Test
    void testMemoryRegionWindows() {
        String s = "0x0000000052380000 - 0x0000000052bda000";
        assertTrue(s.matches(JdkRegEx.MEMORY_REGION), "Memory region not identified.");
    ***REMOVED***

    @Test
    void testNativeLibrary() {
        assertTrue("jvm.so".matches(JdkRegEx.NATIVE_LIBRARY), "jvm.so not identified as native library.");
        assertTrue("my_app.so".matches(JdkRegEx.NATIVE_LIBRARY), "my_app.so not identified as native library.");
        assertTrue("my.app.so".matches(JdkRegEx.NATIVE_LIBRARY), "my.app.so not identified as native library.");
        assertTrue("my-app.so".matches(JdkRegEx.NATIVE_LIBRARY), "my-app.so not identified as native library.");
        assertTrue("jvm.dll".matches(JdkRegEx.NATIVE_LIBRARY), "jvm.dll not identified as native library.");
        assertTrue("my44.dll".matches(JdkRegEx.NATIVE_LIBRARY), "my44.dll not identified as native library.");
        assertTrue("PSAPI.DLL".matches(JdkRegEx.NATIVE_LIBRARY), "PSAPI.DLL not identified as native library.");
        assertFalse("app.jar".matches(JdkRegEx.NATIVE_LIBRARY), "app.jar incorreclty identified as native library.");
        assertFalse("app.dll.jar".matches(JdkRegEx.NATIVE_LIBRARY),
                "app.jar incorreclty identified as native library.");
    ***REMOVED***

    @Test
    void testNativeLibraryDynatrace() {
        assertTrue("liboneagentjava.so".matches(JdkRegEx.NATIVE_LIBRARY),
                "liboneagentjava.so not identified as Dynatrace native library.");
        assertTrue("liboneagentloader.so".matches(JdkRegEx.NATIVE_LIBRARY),
                "liboneagentloader.so not identified as Dynatrace native library.");
        assertTrue("liboneagentproc.so".matches(JdkRegEx.NATIVE_LIBRARY),
                "liboneagentproc.so not identified as Dynatrace native library.");
    ***REMOVED***

    @Test
    void testPercent() {
        String address = "54%";
        assertTrue(address.matches(JdkRegEx.PERCENT), "PERCENT not recognized.");
    ***REMOVED***

    @Test
    void testPermission() {
        String s = "rw-p";
        assertTrue(s.matches(JdkRegEx.PERMISION), "Permission not identified.");
    ***REMOVED***

    @Test
    void testPointerInvalid32Bit() {
        String address = "0xffffffff";
        assertTrue(address.matches(JdkRegEx.POINTER_INVALID), "Invalid pointer not recognized.");
    ***REMOVED***

    @Test
    void testPointerInvalid64Bit() {
        String address = "0xffffffffffffffff";
        assertTrue(address.matches(JdkRegEx.POINTER_INVALID), "Invalid pointer not recognized.");
    ***REMOVED***

    @Test
    void testPointerNull32Bit() {
        String address = "0x00000000";
        assertTrue(address.matches(JdkRegEx.POINTER_NULL), "Null pointer not recognized.");
    ***REMOVED***

    @Test
    void testPointerNull64Bit() {
        String address = "0x0000000000000000";
        assertTrue(address.matches(JdkRegEx.POINTER_NULL), "Null pointer not recognized.");
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
    void testRhel7Amd64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Amd64RpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64/jre/lib/amd64/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel7Amd64RpmOpenjdk8u342Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Ppc64leRpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel7Ppc64leRpmOpenjdk8LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel7Ppc64RpmOpenjdk8Dir() {
        String dir = "java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK8_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel8Amd64RpmOpenjdk11Dir() {
        String dir = "java-11-openjdk-11.0.10.0.9-8.el8.x86_64";
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
    void testRhel8Dot2Amd64RpmOpenjdk11Dir() {
        String dir = "java-11-openjdk-11.0.8.10-0.el8_2.x86_64";
        assertTrue(dir.matches(JdkRegEx.RH_RPM_OPENJDK11_DIR), "Red Hat RPM OpenJDK directory not identified.");
    ***REMOVED***

    @Test
    void testRhel9Amd64RpmOpenjdk11LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-11-openjdk-11.0.17.0.8-2.el9_0.x86_64/lib/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testRhel9Amd64RpmOpenjdk17LibjvmFilePath() {
        String path = "/usr/lib/jvm/java-17-openjdk-17.0.5.0.8-2.el9_0.x86_64/lib/server/libjvm.so";
        assertTrue(path.matches(JdkRegEx.RH_RPM_OPENJDK17_LIBJVM_PATH), "Red Hat RPM file path not identified.");
    ***REMOVED***

    @Test
    void testSizeGb() {
        String address = "1.00 GB";
        assertTrue(address.matches(JdkRegEx.SIZE2), "SIZE2 not recognized.");
    ***REMOVED***

    @Test
    void testSizeK() {
        String size = "1234k";
        assertTrue(size.matches(JdkRegEx.SIZE), "Size not recognized.");
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
    void testTimestampDecimalComma() {
        String timestamp = "1,123";
        assertTrue(timestamp.matches(JdkRegEx.TIMESTAMP), "'" + timestamp + "' is a valid timestamp.");
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
***REMOVED***

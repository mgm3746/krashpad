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
    ***REMOVED***

    public void testPermission() {
        String s = "rw-p";
        Assert.assertTrue("Permission not identified.", s.matches(JdkRegEx.PERMISION));
    ***REMOVED***

    public void testFileOffset() {
        String s = "0001a000";
        Assert.assertTrue("File offset not identified.", s.matches(JdkRegEx.FILE_OFFSET));
    ***REMOVED***

    public void testDeviceIds() {
        String s = "fd:0d";
        Assert.assertTrue("Device ids not identified.", s.matches(JdkRegEx.DEVICE_IDS));
    ***REMOVED***

    public void testInode() {
        String s = "135188646";
        Assert.assertTrue("Inode not identified.", s.matches(JdkRegEx.INODE));
    ***REMOVED***

    public void testFile() {
        String s = "/usr/lib64/libaio.so.1.0.1";
        Assert.assertTrue("Inode not identified.", s.matches(JdkRegEx.FILE));
    ***REMOVED***

    public void testRegion() {
        String s = "[vsyscall]";
        Assert.assertTrue("Inode not identified.", s.matches(JdkRegEx.AREA));
    ***REMOVED***

    public void testJvmLibraryLibjvm() {
        String s = "libjvm.so";
        Assert.assertTrue("JVM library not identified.", s.matches(JdkRegEx.JVM_LIBRARY));
    ***REMOVED***
    
    public void testJvmLibraryJava() {
        String s = "java";
        Assert.assertTrue("JVM library not identified.", s.matches(JdkRegEx.JVM_LIBRARY));
    ***REMOVED***
***REMOVED***

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

import java.util.HashMap;

import org.github.krashpad.domain.jdk.Release;

/**
 * <p>
 * OpenJDK21 release information.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Jdk21 {

    /**
     * RHEL zip release information.
     */
    public static final HashMap<String, Release> RHEL_ZIPS;

    /**
     * RHEL8 rpm release information.
     */
    public static final HashMap<String, Release> RHEL8_X86_64_RPMS;

    /**
     * RHEL9 rpm release information.
     */
    public static final HashMap<String, Release> RHEL9_X86_64_RPMS;

    /**
     * Windows release information.
     */
    public static final HashMap<String, Release> WINDOWS_ZIPS;

    static {
        /*
         * Notes:
         * 
         * 1) Rpm key is the OpenJDK install directory.
         * 
         * 2) Zip key is build version.
         * 
         * 3) 2024-07-16 means build date/time unknown.
         * 
         * 4) 2024-07-16T00:00:00Z means build date/time unspecified (e.g. to support reproducible builds).
         * 
         */

        // RHEL8 amd64 OpenJDK21 rpm
        RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        RHEL8_X86_64_RPMS.put("LATEST", new Release("2025-10-21T00:00:00Z", 11, "21.0.9+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.9.0.10-1.el8.x86_64",
                new Release("2025-10-21T00:00:00Z", 11, "21.0.9+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.8.0.9-1.el8.x86_64",
                new Release("2025-07-15T00:00:00Z", 10, "21.0.8+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.7.0.6-2.el8.x86_64",
                new Release("2025-04-15T00:00:00Z", 9, "21.0.7+6-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.6.0.7-1.el8.x86_64",
                new Release("2025-04-15T00:00:00Z", 9, "21.0.7+6-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.6.0.7-1.el8.x86_64",
                new Release("2025-01-21T00:00:00Z", 8, "21.0.6+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.5.0.10-3.el8.x86_64",
                new Release("2024-10-15T00:00:00Z", 7, "21.0.5+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.4.0.7-1.el8.x86_64",
                new Release("2024-07-16T00:00:00Z", 6, "21.0.4+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.3.0.9-1.el8.x86_64",
                new Release("2024-04-16T00:00:00Z", 5, "21.0.3+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.2.0.13-1.el8.x86_64",
                new Release("2024-01-09T22:49:35Z", 4, "21.0.2+13-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.1.0.12-3.el8.x86_64",
                new Release("2023-11-06T21:59:41Z", 3, "21.0.1+12-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.1.0.12-2.el8.x86_64",
                new Release("2023-10-30T00:33:46Z", 2, "21.0.1+12-LTS"));
        RHEL8_X86_64_RPMS.put("java-21-openjdk-21.0.0.0.35-2.el8.x86_64",
                new Release("2023-08-27T04:16:29Z", 1, "21+35-LTS"));

        // RHEL9 amd64 OpenJDK21 rpm
        RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        RHEL9_X86_64_RPMS.put("LATEST", new Release("2025-10-21T00:00:00Z", 12, "21.0.9+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.9.0.10-1.el9.x86_64",
                new Release("2025-10-21T00:00:00Z", 12, "21.0.9+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.8.0.9-1.el10.x86_64",
                new Release("2025-07-15T00:00:00Z", 11, "21.0.8+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.7.0.6-1.el9.x86_64",
                new Release("2025-04-15T00:00:00Z", 10, "21.0.7+6-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.6.0.7-1.el9.x86_64",
                new Release("2025-01-21T00:00:00Z", 9, "21.0.6+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.5.0.11-2.el9.x86_64",
                new Release("2024-10-23T00:00:00Z", 8, "21.0.5+11-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.5.0.10-3.el9.x86_64",
                new Release("2024-10-23T00:00:00Z", 7, "21.0.5+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.4.0.7-1.el9.x86_64",
                new Release("2024-07-13T00:00:00Z", 6, "21.0.4+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.3.0.9-1.el9.x86_64",
                new Release("2024-04-16T00:00:00Z", 5, "21.0.3+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.2.0.13-1.el9.x86_64",
                new Release("2024-01-12T00:00:00Z", 4, "21.0.2+13-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.1.0.12-3.el9.x86_64",
                new Release("2023-11-06T00:00:00Z", 3, "21.0.1+12-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.1.0.12-2.el9.x86_64",
                new Release("2023-10-30T00:00:00Z", 2, "21.0.1+12-LTS"));
        RHEL9_X86_64_RPMS.put("java-21-openjdk-21.0.0.0.35-2.el9.x86_64",
                new Release("2023-08-27T00:00:00Z", 1, "21+35-LTS"));

        // RHEL amd64 OpenJDK21 zip
        RHEL_ZIPS = new HashMap<String, Release>();
        RHEL_ZIPS.put("LATEST", new Release("2025-10-21T00:00:00Z", 9, "21.0.9+10-LTS"));
        RHEL_ZIPS.put("21.0.9+10-LTS", new Release("2025-10-21T00:00:00Z", 9, "21.0.9+10-LTS"));
        RHEL_ZIPS.put("21.0.8+9-LTS", new Release("2025-07-15T00:00:00Z", 8, "21.0.8+9-LTS"));
        RHEL_ZIPS.put("21.0.7+6-LTS", new Release("2025-04-15T00:00:00Z", 7, "21.0.7+6-LTS"));
        RHEL_ZIPS.put("21.0.6+7-LTS", new Release("2025-01-21T00:00:00Z", 6, "21.0.6+7-LTS"));
        RHEL_ZIPS.put("21.0.5+11-LTS", new Release("2024-10-15T00:00:00Z", 5, "21.0.5+11-LTS"));
        RHEL_ZIPS.put("21.0.4+7-LTS", new Release("2024-07-16T00:00:00Z", 4, "21.0.4+7-LTS"));
        RHEL_ZIPS.put("21.0.3+9-LTS", new Release("2024-04-16T00:00:00Z", 3, "21.0.3+9-LTS"));
        RHEL_ZIPS.put("21.0.2+13-LTS", new Release("2024-01-09T22:49:35Z", 2, "21.0.2+13-LTS"));
        RHEL_ZIPS.put("21.0.1+12-LTS", new Release("2023-10-30T00:33:46Z", 1, "21.0.1+12-LTS"));

        // Windows amd64 OpenJDK21 zip
        WINDOWS_ZIPS = new HashMap<String, Release>();
        WINDOWS_ZIPS.put("LATEST", new Release("2025-10-21T00:00:00Z", 9, "21.0.9+10-LTS"));
        WINDOWS_ZIPS.put("21.0.9+10-LTS", new Release("2025-10-21T00:00:00Z", 9, "21.0.9+10-LTS"));
        WINDOWS_ZIPS.put("21.0.8+9-LTS", new Release("2025-07-15T00:00:00Z", 8, "21.0.8+9-LTS"));
        WINDOWS_ZIPS.put("21.0.7+6-LTS", new Release("2025-04-15T00:00:00Z", 7, "21.0.7+6-LTS"));
        WINDOWS_ZIPS.put("21.0.6+7-LTS", new Release("2025-01-21T00:00:00Z", 6, "21.0.6+7-LTS"));
        WINDOWS_ZIPS.put("21.0.5+11-LTS", new Release("2024-10-15T00:00:00Z", 5, "21.0.5+11-LTS"));
        WINDOWS_ZIPS.put("21.0.4+7-LTS", new Release("2024-07-16T00:00:00Z", 4, "21.0.4+7-LTS"));
        WINDOWS_ZIPS.put("21.0.3+9-LTS", new Release("2024-04-16T00:00:00Z", 3, "21.0.3+9-LTS"));
        // The release actually happened at 2024-01-16T00:00:00Z
        // TODO: Come up with a better way to represent an estimate so it doesn't conflict with a real datetime.
        WINDOWS_ZIPS.put("21.0.2+13-LTS", new Release("2024-01-16T00:00:00Z", 2, "21.0.2+13-LTS"));
        WINDOWS_ZIPS.put("21.0.1+12-LTS", new Release("2023-10-30T00:00:00Z", 1, "21.0.1+12-LTS"));
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Jdk21() {

    }
}

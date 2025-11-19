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
 * OpenJDK17 release information.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Jdk17 {

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
         * 3) Jan 1 2000 means build date/time unknown.
         * 
         * 4) Jan 1 2000 00:00:00 means build date/time unspecified (e.g. to support reproducible builds).
         * 
         */

        // RHEL8 amd64 OpenJDK17 rpm
        RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        RHEL8_X86_64_RPMS.put("LATEST", new Release("Oct 21 2025 00:00:00", 19, "17.0.17+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.17.0.10-1.el8.x86_64",
                new Release("Oct 21 2025 00:00:00", 19, "17.0.17+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.16.0.8-2.el8.x86_64",
                new Release("Jul 15 2025 00:00:00", 18, "17.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.16.0.8-1.el8_6.x86_64",
                new Release("Jul 15 2025 00:00:00", 18, "17.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.16.0.8-1.el8_4.x86_64",
                new Release("Jul 15 2025 00:00:00", 18, "17.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.15.0.6-2.el8.x86_64",
                new Release("Apr 15 2025 00:00:00", 17, "17.0.15+6-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.15.0.6-2.el8_6.x86_64",
                new Release("Apr 15 2025 00:00:00", 17, "17.0.15+6-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.15.0.6-2.el8_4.x86_64",
                new Release("Apr 15 2025 00:00:00", 17, "17.0.15+6-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.14.0.7-3.el8.x86_64",
                new Release("Jan 21 2025 00:00:00", 16, "17.0.14+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.14.0.7-1.el8_6.x86_64",
                new Release("Jan 21 2025 00:00:00", 16, "17.0.14+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.14.0.7-1.el8_4.x86_64",
                new Release("Jan 21 2025 00:00:00", 16, "17.0.14+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.13.0.11-3.el8.x86_64",
                new Release("Oct 15 2024 00:00:00", 15, "17.0.13+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.13.0.11-1.el8_6.x86_64",
                new Release("Oct 15 2024 00:00:00", 15, "17.0.13+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.13.0.11-1.el8_4.x86_64",
                new Release("Oct 15 2024 00:00:00", 15, "17.0.13+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.12.0.7-2.el8.x86_64",
                new Release("Jul 16 2024 00:00:00", 14, "17.0.12+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.12.0.7-1.el8_6.x86_64",
                new Release("Jul 16 2024 00:00:00", 14, "17.0.12+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.12.0.7-1.el8_4.x86_64",
                new Release("Jul 16 2024", 14, "17.0.12+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.11.0.9-2.el8.x86_64",
                new Release("Apr 16 2024 00:00:00", 13, "17.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.11.0.9-1.el8_6.x86_64",
                new Release("Apr 16 2024 00:00:00", 13, "17.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.11.0.9-1.el8_4.x86_64",
                new Release("Apr 16 2024 00:00:00", 13, "17.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.10.0.7-2.el8.x86_64",
                new Release("Jan 11 2024 20:53:13", 12, "17.0.10+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.10.0.7-1.el8_6.x86_64",
                new Release("Jan 11 2024", 12, "17.0.10+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.10.0.7-1.el8_4.x86_64",
                new Release("Jan 11 2024", 12, "17.0.10+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.9.0.9-2.el8.x86_64",
                new Release("Oct 14 2023 16:02:27", 11, "17.0.9+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.9.0.9-1.el8_6.x86_64",
                new Release("Oct 14 2023", 11, "17.0.9+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.9.0.9-1.el8_4.x86_64",
                new Release("Oct 14 2023", 11, "17.0.9+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.8.0.7-2.el8.x86_64",
                new Release("Jul 14 2023 17:37:12", 10, "17.0.8+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.8.0.7-1.el8_6.x86_64",
                new Release("Jul 14 2023", 10, "17.0.8+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.8.0.7-1.el8_4.x86_64",
                new Release("Jul 14 2023", 10, "17.0.8+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-3.el8.x86_64",
                new Release("Apr 26 2023 00:27:10", 9, "17.0.7+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el8_7.x86_64",
                new Release("Apr 14 2023 16:37:06", 9, "17.0.7+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el8_6.x86_64",
                new Release("Apr 14 2023", 9, "17.0.7+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el8_4.x86_64",
                new Release("Apr 14 2023", 9, "17.0.7+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el8_2.x86_64",
                new Release("Apr 14 2023", 9, "17.0.7+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el8_1.x86_64",
                new Release("Apr 14 2023", 9, "17.0.7+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-3.el8_7.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_6.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_4.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_2.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el8_1.x86_64",
                new Release("Jan 14 2023 03:44:30", 8, "17.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.5.0.8-3.el8_6.x86_64",
                new Release("Oct 15 2022 01:52:55", 7, "17.0.5+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.5.0.8-2.el8_6.x86_64",
                new Release("Oct 15 2022 01:52:55", 7, "17.0.5+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.5.0.8-2.el8_6.x86_64",
                new Release("Oct 15 2022 01:52:55", 7, "17.0.5+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.5.0.8-2.el8_4.x86_64",
                new Release("Oct 15 2022 01:52:55", 7, "17.0.5+8-LTS"));
        RHEL8_X86_64_RPMS.put(" java-17-openjdk-17.0.5.0.8-1.el8_7.x86_64",
                new Release("Oct 15 2022", 7, "17.0.5+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-2.el8_6.x86_64",
                new Release("Sep 2 2022 02:21:07", 6, "17.0.4.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-1.el8_4.x86_64",
                new Release("Sep 2 2022", 6, "17.0.4.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.4.0.8-2.el8_6.x86_64",
                new Release("Jul 20 2022 13:03:41", 5, "17.0.4+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.3.0.7-2.el8_6.x86_64",
                new Release("Apr 28 2022 01:08:31", 4, "17.0.3+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.3.0.6-2.el8_5.x86_64",
                new Release("Apr 16 2022 03:42:17", 4, "17.0.3+6-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.2.0.8-4.el8_5.x86_64",
                new Release("Jan 17 2022 04:30:26", 3, "17.0.2+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.1.0.12-2.el8_5.x86_64",
                new Release("Oct 28 2021 01:59:13", 2, "17.0.1+12-LTS"));
        RHEL8_X86_64_RPMS.put("java-17-openjdk-17.0.0.0.35-4.el8.x86_64", new Release("Sep 27 2021", 1, "17+35"));

        // RHEL9 amd64 OpenJDK17 rpm
        RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        RHEL9_X86_64_RPMS.put("LATEST", new Release("Oct 21 2025 00:00:00", 16, "17.0.17+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.17.0.10-1.el9.x86_64",
                new Release("Oct 21 2025 00:00:00", 16, "17.0.17+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.16.0.8-2.el9.x86_64",
                new Release("Jul 15 2025 00:00:00", 15, "17.0.16+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.16.0.8-1.el9_0.x86_64",
                new Release("Jul 15 2025 00:00:00", 15, "17.0.16+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.15.0.6-2.el9.x86_64",
                new Release("Apr 15 2025 00:00:00", 14, "17.0.15+6-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.15.0.6-2.el9_0.x86_64",
                new Release("Apr 15 2025 00:00:00", 14, "17.0.15+6-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.14.0.7-2.el9.x86_64",
                new Release("Jan 21 2025 00:00:00", 13, "17.0.14+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.14.0.7-1.el9_0.x86_64",
                new Release("Jan 21 2025 00:00:00", 13, "17.0.14+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.13.0.11-4.el9.x86_64",
                new Release("Oct 18 2024 00:00:00", 12, "17.0.13+11-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.13.0.11-3.el9.x86_64",
                new Release("Oct 18 2024 00:00:00", 12, "17.0.13+11-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.13.0.11-1.el9_0.x86_64",
                new Release("Oct 18 2024 00:00:00", 12, "17.0.13+11-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.12.0.7-2.el9.x86_64",
                new Release("Jul 16 2024 00:00:00", 11, "17.0.12+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.12.0.7-1.el9_0.x86_64",
                new Release("Jul 16 2024 00:00:00", 11, "17.0.12+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.11.0.9-2.el9.x86_64",
                new Release("Apr 16 2024 00:00:00", 10, "17.0.11+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.11.0.9-1.el9_0.x86_64",
                new Release("Apr 16 2024 00:00:00", 10, "17.0.11+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.10.0.7-2.el9.x86_64",
                new Release("Jan 11 2024 20:53:13", 9, "17.0.10+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.10.0.7-1.el9_0.x86_64",
                new Release("Jan 12 2024", 9, "17.0.10+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.9.0.9-2.el9.x86_64", new Release("Oct 14 2023", 8, "17.0.9+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.9.0.9-1.el9_0.x86_64",
                new Release("Oct 14 2023", 8, "17.0.9+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.8.0.7-2.el9.x86_64", new Release("Jul 19 2023", 7, "17.0.8+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.8.0.7-1.el9_0.x86_64",
                new Release("Jul 14 2023", 7, "17.0.8+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-3.el9.x86_64", new Release("Apr 27 2023", 6, "17.0.7+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el9_1.x86_64",
                new Release("Jan 14 2023", 6, "17.0.7+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.7.0.7-1.el9_0.x86_64",
                new Release("Jan 14 2023", 6, "17.0.7+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-3.el9_1.x86_64",
                new Release("Jan 14 2023", 5, "17.0.6+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.6.0.10-2.el9_0.x86_64",
                new Release("Jan 14 2023", 5, "17.0.6+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.5.0.8-2.el9_0.x86_64",
                new Release("Oct 15 2022", 4, "17.0.5+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.4.1.1-2.el9_0.x86_64",
                new Release("Sep 2 2022", 3, "17.0.4.1+1-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.4.0.8-2.el9_0.x86_64",
                new Release("Jul 20 2022", 2, "17.0.4+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-17-openjdk-17.0.3.0.7-1.el9_0.x86_64",
                new Release("Apr 27 2022", 1, "17.0.3+7-LTS"));

        // RHEL amd64 OpenJDK17 zip
        RHEL_ZIPS = new HashMap<String, Release>();
        RHEL_ZIPS.put("LATEST", new Release("Oct 21 2025 00:00:00", 19, "17.0.17+10-LTS"));
        RHEL_ZIPS.put("17.0.17+10-LTS", new Release("Oct 21 2025 00:00:00", 19, "17.0.17+10-LTS"));
        RHEL_ZIPS.put("17.0.16+8-LTS", new Release("Jul 15 2025 00:00:00", 18, "17.0.16+8-LTS"));
        RHEL_ZIPS.put("17.0.15+6-LTS", new Release("Apr 15 2025 00:00:00", 17, "17.0.15+6-LTS"));
        RHEL_ZIPS.put("17.0.14+7-LTS", new Release("Jan 21 2025 00:00:00", 16, "17.0.14+7-LTS"));
        RHEL_ZIPS.put("17.0.13+11-LTS", new Release("Oct 15 2024 00:00:00", 15, "17.0.13+11-LTS"));
        RHEL_ZIPS.put("17.0.12+7-LTS", new Release("Jul 16 2024 00:00:00", 14, "17.0.12+7-LTS"));
        RHEL_ZIPS.put("17.0.11+9-LTS", new Release("Apr 16 2024 00:00:00", 13, "17.0.11+9-LTS"));
        // Newer RHEL7 compatible download
        RHEL_ZIPS.put("17.0.10+7-LTS", new Release("Jan 12 2024 14:16:06", 12, "17.0.10+7-LTS"));
        // Version that was not RHEL7 compatible and was removed from the downloads
        // RHEL_ZIPS.put("17.0.10+7-LTS", new Release("Jan 11 2024 20:53:13", 12, "17.0.10+7-LTS"));
        RHEL_ZIPS.put("17.0.9+9-LTS", new Release("Oct 12 2023 16:25:04", 11, "17.0.9+9-LTS"));
        RHEL_ZIPS.put("17.0.8.1+1-LTS", new Release("Sep 4 2023 06:15:40", 10, "17.0.8.1+1-LTS"));
        RHEL_ZIPS.put("17.0.8+7-LTS", new Release("Jul 14 2023 15:48:52", 9, "17.0.8+7-LTS"));
        RHEL_ZIPS.put("17.0.7+7-LTS", new Release("Apr 13 2023 02:10:25", 8, "17.0.7+7-LTS"));
        RHEL_ZIPS.put("17.0.6+10-LTS", new Release("Jan 14 2023 04:10:00", 7, "17.0.6+10-LTS"));
        RHEL_ZIPS.put("17.0.5+8-LTS", new Release("Oct 15 2022 02:39:47", 6, "17.0.5+8-LTS"));
        RHEL_ZIPS.put("17.0.4.1+1-LTS", new Release("Aug 25 2022 06:03:13", 5, "17.0.4.1+1-LTS"));
        RHEL_ZIPS.put("17.0.4+8-LTS", new Release("Jul 19 2022 12:31:23", 4, "17.0.4+8-LTS"));
        RHEL_ZIPS.put("17.0.3+6-LTS", new Release("Apr 16 2022 14:38:17", 3, "17.0.3+6-LTS"));
        RHEL_ZIPS.put("17.0.2+8-LTS", new Release("Jan 18 2022 16:03:37", 2, "17.0.2+8-LTS"));
        RHEL_ZIPS.put("17.0.1+12-LTS", new Release("Oct 29 2021 08:37:58", 1, "17.0.1+12-LTS"));

        // Windows amd64 OpenJDK17 zip
        WINDOWS_ZIPS = new HashMap<String, Release>();
        WINDOWS_ZIPS.put("LATEST", new Release("Oct 21 2025 00:00:00", 20, "17.0.17+10-LTS"));
        WINDOWS_ZIPS.put("17.0.17+10-LTS", new Release("Oct 21 2025 00:00:00", 20, "17.0.17+10-LTS"));
        WINDOWS_ZIPS.put("17.0.16+8-LTS", new Release("Jul 15 2025", 19, "17.0.16+8-LTS"));
        WINDOWS_ZIPS.put("17.0.15+6-LTS", new Release("Apr 15 2025", 18, "17.0.15+6-LTS"));
        WINDOWS_ZIPS.put("17.0.14+7-LTS", new Release("Jan 21 2025", 17, "17.0.14+7-LTS"));
        WINDOWS_ZIPS.put("17.0.13+11-LTS", new Release("Oct 15 2024", 16, "17.0.13+11-LTS"));
        WINDOWS_ZIPS.put("17.0.12+7-LTS", new Release("Jul 16 2024", 15, "17.0.12+7-LTS"));
        WINDOWS_ZIPS.put("17.0.11+9-LTS", new Release("Apr 10 2024 18:27:01", 14, "17.0.11+9-LTS"));
        WINDOWS_ZIPS.put("17.0.10+7-LTS", new Release("Jan 15 2024 10:20:35", 13, "17.0.10+7-LTS"));
        WINDOWS_ZIPS.put("17.0.9+9-LTS", new Release("Oct 12 2023", 12, "17.0.9+9-LTS"));
        WINDOWS_ZIPS.put("17.0.8.1+1-LTS", new Release("Sep 4 2023", 11, "17.0.8.1+1-LTS"));
        WINDOWS_ZIPS.put("17.0.8+7-LTS", new Release("Jul 14 2023", 10, "17.0.8+7-LTS"));
        WINDOWS_ZIPS.put("17.0.7+7-LTS", new Release("Apr 13 2023", 9, "17.0.7+7-LTS"));
        WINDOWS_ZIPS.put("17.0.6+10-LTS", new Release("Jan 14 2023", 8, "17.0.6+10-LTS"));
        WINDOWS_ZIPS.put("17.0.5+8-LTS", new Release("Oct 15 2022", 7, "17.0.5+8-LTS"));
        WINDOWS_ZIPS.put("17.0.4.1+1-LTS", new Release("Aug 25 2022", 6, "17.0.4.1+1-LTS"));
        WINDOWS_ZIPS.put("17.0.4+8-LTS", new Release("Jul 19 2022", 5, "17.0.4+8-LTS"));
        // 2 builds w/ the same release string
        WINDOWS_ZIPS.put("17.0.3+6-LTS-2", new Release("Apr 27 2022 11:51:42", 4, "17.0.3+6-LTS"));
        WINDOWS_ZIPS.put("17.0.3+6-LTS-1", new Release("Apr 17 2022 12:11:44", 3, "17.0.3+6-LTS"));
        WINDOWS_ZIPS.put("17.0.2+8-LTS", new Release("Jan 18 2022", 2, "17.0.2+8-LTS"));
        WINDOWS_ZIPS.put("17.0.1+12-LTS", new Release("Oct 29 2021", 1, "17.0.1+12-LTS"));
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Jdk17() {

    }
}

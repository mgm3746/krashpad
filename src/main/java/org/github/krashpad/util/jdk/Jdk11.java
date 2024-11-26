/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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
 * OpenJDK11 release information.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Jdk11 {

    /**
     * RHEL zip release information.
     */
    public static final HashMap<String, Release> RHEL_ZIPS;

    /**
     * RHEL7 amd64 rpm release information.
     */
    public static final HashMap<String, Release> RHEL7_X86_64_RPMS;

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

        // RHEL7 amd64 OpenJDK11 rpm
        RHEL7_X86_64_RPMS = new HashMap<String, Release>();
        RHEL7_X86_64_RPMS.put("TEST", new Release("Oct 10 2024", 28, "11.0.25+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-1.el7_9.x86_64",
                new Release("Oct 10 2024", 28, "11.0.25+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-1.el7_9.x86_64",
                new Release("Jul 12 2024", 27, "11.0.24+8-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-2.el7_9.x86_64",
                new Release("Apr 12 2024", 26, "11.0.23+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-1.el7_9.x86_64",
                new Release("Jan 9 2024 20:49:29", 25, "11.0.22+7-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-1.el7_9.x86_64",
                new Release("Oct 14 2023", 24, "11.0.21+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-1.el7_9.x86_64",
                new Release("Jul 12 2023", 23, "11.0.20+8-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el7_9.x86_64",
                new Release("Apr 14 2023 17:54:41", 22, "11.0.19+7-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el7_9.x86_64",
                new Release("Jan 11 2023 04:53:58", 21, "11.0.18+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el7_9.x86_64",
                new Release("Oct 16 2022 01:27:24", 20, "11.0.17+8-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el7_9.x86_64",
                new Release("Sep 1 2022", 19, "11.0.16+8-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el7_9.x86_64",
                new Release("Jul 18 2022 19:50:20", 18, "11.0.16+8-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el7_9.x86_64",
                new Release("Apr 16 2022", 17, "11.0.15+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-1.el7_9.x86_64",
                new Release("Feb 11 2022", 16, "11.0.14.1+1-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-1.el7_9.x86_64",
                new Release("Jan 18 2022", 15, "11.0.14+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el7_9.x86_64",
                new Release("Oct 13 2021", 14, "11.0.13+8-LTS)"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el7_9.x86_64",
                new Release("Jul 14 2021 00:06:01", 13, "11.0.12+7-LTS)"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-1.el7_9.x86_64",
                new Release("Apr 13 2021 02:52:48", 12, "11.0.11+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-0.el7_9.x86_64",
                new Release("Jan 14 2021 23:18:04", 11, "11.0.10+9-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-2.el7_9.x86_64",
                new Release("Nov 12 2020 18:10:11", 10, "11.0.9.1+1-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el7_9.x86_64",
                new Release("Oct 15 2020 11:45:12", 9, "11.0.9+11-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-1.el7.x86_64", new Release("Jul 11 2020", 8, "11.0.8+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el7_8.x86_64",
                new Release("Jul 11 2020", 8, "11.0.8+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.7.10-4.el7_8.x86_64",
                new Release("Apr 14 2020 21:38:20", 7, "11.0.7+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-3.el7.x86_64", new Release("Feb 16 2020", 6, "11.0.6+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-1.el7_7.x86_64",
                new Release("Jan 11 2020", 6, "11.0.6+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.5.10-0.el7_7.x86_64",
                new Release("Oct 9 2019 18:41:22", 5, "11.0.5+10-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-1.el7_7.x86_64",
                new Release("Jul 9 2019", 4, "11.0.4+11-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-0.el7_6.x86_64",
                new Release("Jul 9 2019", 4, "11.0.4+11-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.3.7-1.el7.x86_64", new Release("Apr 4 2019", 3, "11.0.3+7-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.3.7-0.el7_6.x86_64", new Release("Apr 9 2019", 3, "11.0.3+7-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.2.7-0.el7_6.x86_64", new Release("Jan 15 2019", 2, "11.0.2+7-LTS"));
        RHEL7_X86_64_RPMS.put("java-11-openjdk-11.0.1.13-3.el7_6.x86_64",
                new Release("Oct 24 2018", 1, "11.0.1+13-LTS"));

        // RHEL8 amd64 OpenJDK11 rpm
        RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        RHEL8_X86_64_RPMS.put("LATEST", new Release("Oct 10 2024 11:15:47", 29, "11.0.25+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-2.el8.x86_64",
                new Release("Oct 10 2024 11:15:47", 29, "11.0.25+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-1.el8_6.x86_64",
                new Release("Oct 10 2024", 29, "11.0.25+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-1.el8_4.x86_64",
                new Release("Oct 10 2024", 29, "11.0.25+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-1.el8_2.x86_64",
                new Release("Oct 10 2024", 29, "11.0.25+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-3.el8.x86_64",
                new Release("Jul 11 2024 09:53:31", 28, "11.0.24+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-1.el8_6.x86_64",
                new Release("Jul 11 2024", 28, "11.0.24+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-1.el8_4.x86_64",
                new Release("Jul 11 2024", 28, "11.0.24+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-1.el8_2.x86_64",
                new Release("Jul 11 2024", 28, "11.0.24+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-3.el8.x86_64",
                new Release("Apr 11 2024 22:34:11", 27, "11.0.23+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-2.el8_6.x86_64",
                new Release("Apr 11 2024", 27, "11.0.23+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-2.el8_4.x86_64",
                new Release("Apr 11 2024", 27, "11.0.23+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-2.el8_2.x86_64",
                new Release("Apr 11 2024", 27, "11.0.23+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-2.el8.x86_64",
                new Release("Jan 10 2024 17:36:47", 26, "11.0.22+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-1.el8_6.x86_64",
                new Release("Jan 10 2024", 26, "11.0.22+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-1.el8_4.x86_64",
                new Release("Jan 10 2024", 26, "11.0.22+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-1.el8_2.x86_64",
                new Release("Jan 10 2024", 26, "11.0.22+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-2.el8.x86_64",
                new Release("Oct 14 2023 03:04:04", 25, "11.0.21+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-1.el8_6.x86_64",
                new Release("Oct 14 2023", 25, "11.0.21+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-1.el8_4.x86_64",
                new Release("Oct 14 2023", 25, "11.0.21+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-1.el8_2.x86_64",
                new Release("Oct 14 2023", 25, "11.0.21+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-1.el8_1.x86_64",
                new Release("Oct 14 2023", 25, "11.0.21+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.1.1-2.el8.x86_64",
                new Release("Sep 08 2023 00:41:55", 24, "11.0.20.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.1.1-1.el8_1.x86_64",
                new Release("Sep 06 2023", 24, "11.0.20.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-3.el8.x86_64",
                new Release("Jul 15 2023 00:41:55", 23, "11.0.20+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-2.el8.x86_64",
                new Release("Jul 15 2023 00:41:55", 23, "11.0.20+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-1.el8_6.x86_64",
                new Release("Jul 12 2023 16:19:58", 23, "11.0.20+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-1.el8_4.x86_64",
                new Release("Jul 12 2023", 23, "11.0.20+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-1.el8_2.x86_64",
                new Release("Jul 12 2023", 23, "11.0.20+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-1.el8_1.x86_64",
                new Release("Jul 12 2023", 23, "11.0.20+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-4.el8.x86_64",
                new Release("Apr 24 2023 19:14:16", 22, "11.0.19+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el8_7.x86_64",
                new Release("Apr 14 2023 17:03:28", 22, "11.0.19+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el8_6.x86_64",
                new Release("Apr 20 2023", 22, "11.0.19+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el8_4.x86_64",
                new Release("Apr 20 2023", 22, "11.0.19+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el8_2.x86_64",
                new Release("Apr 20 2023", 22, "11.0.19+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el8_1.x86_64",
                new Release("Apr 20 2023", 22, "11.0.19+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-2.el8_7.x86_64",
                new Release("Jan 10 2023 23:55:47", 21, "11.0.18+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_6.x86_64",
                new Release("Jan 13 2023", 21, "11.0.18+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_4.x86_64",
                new Release("Jan 13 2023", 21, "11.0.18+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_2.x86_64",
                new Release("Jan 13 2023", 21, "11.0.18+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el8_1.x86_64",
                new Release("Jan 13 2023", 21, "11.0.18+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_6.x86_64",
                new Release("Oct 15 2022 19:13:31", 20, "11.0.17+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_4.x86_64",
                new Release("Oct 15 2022", 20, "11.0.17+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_2.x86_64",
                new Release("Oct 15 2022", 20, "11.0.17+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el8_1.x86_64",
                new Release("Oct 15 2022", 20, "11.0.17+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_6.x86_64",
                new Release("Aug 25 2022 13:29:35", 19, "11.0.16.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_4.x86_64",
                new Release("Aug 25 2022", 19, "11.0.16.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_2.x86_64",
                new Release("Aug 25 2022", 19, "11.0.16.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el8_1.x86_64",
                new Release("Aug 25 2022", 19, "11.0.16.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_6.x86_64",
                new Release("Jul 18 2022 13:53:34", 18, "11.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_4.x86_64",
                new Release("Jul 18 2022", 18, "11.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_2.x86_64",
                new Release("Jul 18 2022", 18, "11.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el8_1.x86_64",
                new Release("Jul 18 2022", 18, "11.0.16+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.10-2.el8_6.x86_64",
                new Release("Apr 27 2022 21:58:19", 17, "11.0.15+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_5.x86_64",
                new Release("Apr 15 2022 23:31:28", 17, "11.0.15+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_4.x86_64",
                new Release("Apr 16 2022", 17, "11.0.15+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_2.x86_64",
                new Release("Apr 16 2022", 17, "11.0.15+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.9-2.el8_1.x86_64",
                new Release("Apr 16 2022", 17, "11.0.15+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-2.el8_5.x86_64",
                new Release("Feb 23 2022 11:57:18", 16, "11.0.14.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-2.el8_1.x86_64",
                new Release("Feb 23 2022", 16, "11.0.14.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-1.el8_4.x86_64",
                new Release("Feb 11 2022", 16, "11.0.14.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.1.1-1.el8_2.x86_64",
                new Release("Feb 11 2022 10:50:37", 16, "11.0.14.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_5.x86_64",
                new Release("Jan 17 2022 22:54:29", 15, "11.0.14+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_4.x86_64",
                new Release("Jan 18 2022", 15, "11.0.14+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_2.x86_64",
                new Release("Jan 18 2022", 15, "11.0.14+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.14.0.9-2.el8_1.x86_64",
                new Release("Jan 18 2022", 15, "11.0.14+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-4.el8_5.x86_64",
                new Release("Nov 7 2021 20:03:27", 14, "11.0.13+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-3.el8_5.x86_64",
                new Release("Oct 27 2021 22:03:57", 13, "11.0.13+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el8_4.x86_64",
                new Release("Oct 13 2021 11:20:31", 13, "11.0.13+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el8_2.x86_64",
                new Release("Oct 13 2021", 13, "11.0.13+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.13.0.8-1.el8_1.x86_64",
                new Release("Oct 13 2021", 13, "11.0.13+8-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el8_4.x86_64",
                new Release("Jul 14 2021 11:31:14", 12, "11.0.12+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el8_2.x86_64",
                new Release("Jul 14 2021", 12, "11.0.12+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.12.0.7-0.el8_1.x86_64",
                new Release("Jul 14 2021", 12, "11.0.12+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-2.el8_4.x86_64",
                new Release("Apr 15 2021 01:22:52", 11, "11.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-0.el8_3.x86_64",
                new Release("Apr 15 2021 01:33:28", 11, "11.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-0.el8_2.x86_64",
                new Release("Apr 15 2021", 11, "11.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.11.0.9-0.el8_1.x86_64",
                new Release("Apr 15 2021", 11, "11.0.11+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-8.el8.x86_64",
                new Release("Feb 22 2021 00:57:14", 10, "11.0.10+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-4.el8_3.x86_64",
                new Release("Jan 18 2021 00:04:32", 10, "11.0.10+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-0.el8_2.x86_64",
                new Release("Jan 15 2021", 10, "11.0.10+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.10.0.9-1.el8_1.x86_64",
                new Release("Jan 18 2021", 10, "11.0.10+9-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-3.el8_3.x86_64",
                new Release("Nov 10 2020 21:42:14", 9, "11.0.9.1+1-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-2.el8_3.x86_64",
                new Release("Oct 20 2020", 8, "11.0.9+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-1.el8_0.x86_64",
                new Release("Oct 20 2020", 8, "11.0.9+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el8_2.x86_64",
                new Release("Oct 16 2020 04:18:29", 8, "11.0.9+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el8_1.x86_64",
                new Release("Oct 20 2020", 8, "11.0.9+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.9.11-0.el8_0.x86_64",
                new Release("Oct 20 2020", 8, "11.0.9+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-6.el8.x86_64", new Release("Jul 11 2020", 7, "11.0.8+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el8_2.x86_64",
                new Release("Jul 11 2020 02:33:15", 7, "11.0.8+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el8_1.x86_64",
                new Release("Jul 11 2020", 7, "11.0.8+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.8.10-0.el8_0.x86_64",
                new Release("Jul 11 2020", 7, "11.0.8+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.7.10-1.el8_1.x86_64",
                new Release("Apr 15 2020 12:25:53", 6, "11.0.7+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.7.10-1.el8_0.x86_64",
                new Release("Apr 15 2020", 6, "11.0.7+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-4.el8.x86_64",
                new Release("Mar 27 2020 04:27:09", 5, "11.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-0.el8_1.x86_64",
                new Release("Jan 11 2020 04:53:43", 5, "11.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.6.10-0.el8_0.x86_64",
                new Release("Jan 11 2020", 5, "11.0.6+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.5.10-2.el8_1.x86_64",
                new Release("Oct 25 2019", 4, "11.0.5+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.5.10-0.el8_0.x86_64",
                new Release("Oct 11 2019", 4, "11.0.5+10-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-2.el8.x86_64", new Release("Jul 9 2019", 3, "11.0.4+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.4.11-0.el8_0.x86_64",
                new Release("Jul 9 2019", 3, "11.0.4+11-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.3.7-2.el8_0.x86_64", new Release("May 1 2019", 2, "11.0.3+7-LTS"));
        RHEL8_X86_64_RPMS.put("java-11-openjdk-11.0.2.7-2.el8.x86_64", new Release("Feb 8 2019", 1, "11.0.2+7-LTS"));

        // RHEL9 amd64 OpenJDK11 rpm
        RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        RHEL9_X86_64_RPMS.put("LATEST", new Release("Oct 18 2024", 12, "11.0.25+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-3.el9.x86_64",
                new Release("Oct 18 2024", 12, "11.0.25+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-2.el9.x86_64",
                new Release("Oct 18 2024", 12, "11.0.25+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.25.0.9-1.el9_0.x86_64",
                new Release("Oct 18 2024", 12, "11.0.25+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-2.el9.x86_64",
                new Release("Jul 11 2024", 11, "11.0.24+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.24.0.8-1.el9_0.x86_64",
                new Release("Jul 11 2024", 11, "11.0.24+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-3.el9.x86_64",
                new Release("Apr 11 2024 22:34:11", 10, "11.0.23+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.23.0.9-2.el9_0.x86_64",
                new Release("Apr 12 2024", 10, "11.0.23+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-2.el9.x86_64",
                new Release("Jan 12 2024", 9, "11.0.22+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.22.0.7-1.el9_0.x86_64",
                new Release("Jan 12 2024", 9, "11.0.22+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-2.el9.x86_64",
                new Release("Oct 14 2023", 8, "11.0.21+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.21.0.9-1.el9_0.x86_64",
                new Release("Oct 14 2023", 8, "11.0.21+9-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-3.el9.x86_64",
                new Release("Jul 20 2023", 7, "11.0.20+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-2.el9.x86_64",
                new Release("Jul 19 2023", 7, "11.0.20+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.20.0.8-1.el9_0.x86_64",
                new Release("Jul 12 2023", 7, "11.0.20+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-4.el9.x86_64",
                new Release("Apr 28 2023", 6, "11.0.19+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el9_1.x86_64",
                new Release("Apr 14 2023", 6, "11.0.19+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.19.0.7-1.el9_0.x86_64",
                new Release("Apr 14 2023", 6, "11.0.19+7-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-2.el9_1.x86_64",
                new Release("Jan 11 2023", 5, "11.0.18+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.18.0.10-1.el9_0.x86_64",
                new Release("Jan 13 2023", 5, "11.0.18+10-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.17.0.8-2.el9_0.x86_64",
                new Release("Oct 15 2022", 4, "11.0.17+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.16.1.1-1.el9_0.x86_64",
                new Release("Aug 25 2022", 3, "11.0.16.1+1-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.16.0.8-1.el9_0.x86_64",
                new Release("Jul 18 2022", 2, "11.0.16+8-LTS"));
        RHEL9_X86_64_RPMS.put("java-11-openjdk-11.0.15.0.10-1.el9_0.x86_64",
                new Release("Apr 28 2022", 1, "11.0.15+10-LTS"));

        // RHEL amd64 OpenJDK11 zip
        // First RHEL zip was 11.0.4.11.
        RHEL_ZIPS = new HashMap<String, Release>();
        RHEL_ZIPS.put("LATEST", new Release("Oct 10 2024 10:26:43", 26, "11.0.25+9-LTS"));
        RHEL_ZIPS.put("11.0.25+9-LTS", new Release("Oct 10 2024 10:26:43", 26, "11.0.25+9-LTS"));
        RHEL_ZIPS.put("11.0.24+8-LTS", new Release("Jul 11 2024 09:54:04", 25, "11.0.24+8-LTS"));
        RHEL_ZIPS.put("11.0.23+9-LTS", new Release("Apr 12 2024 14:33:51", 24, "11.0.23+9-LTS"));
        // Newer RHEL7 compatible download
        RHEL_ZIPS.put("11.0.22+7-LTS", new Release("Jan 10 2024 17:22:41", 23, "11.0.22+7-LTS"));
        // Version that was not RHEL7 compatible and was removed from the downloads
        // RHEL_ZIPS.put("11.0.22+7-LTS", new Release("Jan 10 2024 17:36:47", 23, "11.0.22+7-LTS"));
        RHEL_ZIPS.put("11.0.21+9-LTS", new Release("Oct 11 2023 02:37:53", 22, "11.0.21+9-LTS"));
        RHEL_ZIPS.put("11.0.20.1+1-LTS", new Release("Sep 7 2023 05:19:18", 21, "11.0.20.1+1-LTS"));
        RHEL_ZIPS.put("11.0.20+8-LTS", new Release("Jul 17 2023 15:08:10", 20, "11.0.20+8-LTS"));
        RHEL_ZIPS.put("11.0.19+7-LTS", new Release("Apr 14 2023 02:59:33", 19, "11.0.19+7-LTS"));
        RHEL_ZIPS.put("11.0.18+10-LTS", new Release("Jan 13 2023 04:46:48", 18, "11.0.18+10-LTS"));
        RHEL_ZIPS.put("11.0.17+8-LTS", new Release("Oct 16 2022 03:03:26", 17, "11.0.17+8-LTS"));
        RHEL_ZIPS.put("11.0.16.1+1-LTS", new Release("Aug 25 2022 17:29:35", 16, "11.0.16.1+1-LTS"));
        RHEL_ZIPS.put("11.0.16+8-LTS", new Release("Jul 19 2022 14:18:09", 15, "11.0.16+8-LTS"));
        RHEL_ZIPS.put("11.0.15+9-LTS", new Release("Apr 16 2022 15:20:06", 14, "11.0.15+9-LTS"));
        RHEL_ZIPS.put("11.0.14.1+1-LTS", new Release("Feb 12 2022 05:34:35", 13, "11.0.14.1+1-LTS"));
        RHEL_ZIPS.put("11.0.14+9-LTS", new Release("Jan 19 2022 12:27:57", 12, "11.0.14+9-LTS"));
        RHEL_ZIPS.put("11.0.13+8-LTS", new Release("Oct 14 2021 19:43:57", 11, "11.0.13+8-LTS"));
        RHEL_ZIPS.put("11.0.12+7-LTS", new Release("Jul 15 2021 10:32:12", 10, "11.0.12+7-LTS"));
        RHEL_ZIPS.put("11.0.11+9-LTS", new Release("Apr 12 2021 13:04:24", 9, "11.0.11+9-LTS"));
        RHEL_ZIPS.put("11.0.10+9-LTS", new Release("Jan 18 2021 00:04:32", 8, "11.0.10+9-LTS"));
        RHEL_ZIPS.put("11.0.9.1+1-LTS", new Release("Nov 11 2020 12:19:11", 7, "11.0.9.1+1-LTS"));
        RHEL_ZIPS.put("11.0.9+11-LTS", new Release("Oct 20 2020 12:01:49", 6, "11.0.9+11-LTS"));
        RHEL_ZIPS.put("11.0.8+10-LTS", new Release("Jul 14 2020 06:26:42", 5, "11.0.8+10-LTS"));
        RHEL_ZIPS.put("11.0.7+10-LTS", new Release("Apr 9 2020 11:42:52", 4, "11.0.7+10-LTS"));
        RHEL_ZIPS.put("11.0.6+10-LTS", new Release("Jan 12 2020 10:38:53", 3, "11.0.6+10-LTS"));
        RHEL_ZIPS.put("11.0.5+10-LTS", new Release("Oct 15 2019 09:18:41", 2, "11.0.5+10-LTS"));
        RHEL_ZIPS.put("11.0.4+11-LTS", new Release("Aug 2 2019 08:21:47", 1, "11.0.4+11-LTS"));

        // Windows amd64 OpenJDK11 zip
        // First Windows zip was 11.0.1.13.
        WINDOWS_ZIPS = new HashMap<String, Release>();
        WINDOWS_ZIPS.put("LATEST", new Release("Oct 10 2024", 33, "11.0.25+9-LTS"));
        WINDOWS_ZIPS.put("11.0.25+9-LTS", new Release("Oct 10 2024", 33, "11.0.25+9-LTS"));
        WINDOWS_ZIPS.put("11.0.24+8-LTS", new Release("Jul 11 2024", 32, "11.0.24+8-LTS"));
        WINDOWS_ZIPS.put("11.0.23+9-LTS", new Release("Apr 12 2024", 31, "11.0.23+9-LTS"));
        WINDOWS_ZIPS.put("11.0.22+7-LTS", new Release("Jan 10 2024 18:51:53", 30, "11.0.22+7-LTS"));
        WINDOWS_ZIPS.put("11.0.21+9-LTS", new Release("Oct 11 2023", 29, "11.0.21+9-LTS"));
        WINDOWS_ZIPS.put("11.0.20.1+1-LTS", new Release("Sep 7 2023", 28, "11.0.20.1+1-LTS"));
        WINDOWS_ZIPS.put("11.0.20+8-LTS", new Release("Jul 17 2023", 27, "11.0.20+8-LTS"));
        WINDOWS_ZIPS.put("11.0.19+7-LTS", new Release("Apr 14 2023 13:52:19", 26, "11.0.19+7-LTS"));
        WINDOWS_ZIPS.put("11.0.18+10-LTS", new Release("Jan 13 2023", 25, "11.0.18+10-LTS"));
        WINDOWS_ZIPS.put("11.0.17+8-LTS", new Release("Oct 16 2022", 24, "11.0.17+8-LTS"));
        WINDOWS_ZIPS.put("11.0.16.1+1-LTS", new Release("Aug 25 2022", 23, "11.0.16.1+1-LTS"));
        WINDOWS_ZIPS.put("11.0.16+8-LTS", new Release("Jul 19 2022 12:27:55", 22, "11.0.16+8-LTS"));
        // 2 builds w/ the same release string
        WINDOWS_ZIPS.put("11.0.15+9-LTS-2", new Release("Apr 27 2022 19:12:18", 21, "11.0.15+9-LTS"));
        WINDOWS_ZIPS.put("11.0.15+9-LTS-1", new Release("Apr 17 2022 13:56:34", 20, "11.0.15+9-LTS"));
        WINDOWS_ZIPS.put("11.0.14.1+9-LTS", new Release("Feb 14 2022 21:03:13", 19, "11.0.14.1+9-LTS"));
        WINDOWS_ZIPS.put("11.0.14+9-LTS", new Release("Jan 17 2022 22:55:50", 18, "11.0.14+9-LTS"));
        WINDOWS_ZIPS.put("11.0.13+8-LTS", new Release("Oct 16 2021 19:46:00", 17, "11.0.13+8-LTS"));
        WINDOWS_ZIPS.put("11.0.12+7-LTS", new Release("Jul 15 2021 16:55:31", 16, "11.0.12+7-LTS"));
        WINDOWS_ZIPS.put("11.0.11+9-LTS", new Release("Apr 15 2021 21:44:00", 15, "11.0.11+9-LTS"));
        WINDOWS_ZIPS.put("11.0.10+9-LTS", new Release("Jan 16 2021 19:49:44", 14, "11.0.10+9-LTS"));
        WINDOWS_ZIPS.put("11.0.9.1+1-LTS", new Release("Nov 10 2020 12:16:00", 13, "11.0.9.1+1-LTS"));
        WINDOWS_ZIPS.put("11.0.9+11-LTS", new Release("Oct 17 2020 16:53:23", 12, "11.0.9+11-LTS"));
        WINDOWS_ZIPS.put("11.0.8+10-LTS", new Release("Jul 12 2020 15:20:55", 11, "11.0.8+10-LTS"));
        WINDOWS_ZIPS.put("11.0.7+10-LTS", new Release("Apr 9 2020 00:20:14", 10, "11.0.7+10-LTS"));
        // Release 11.0.6.10 and 11.0.6.10-2 have the same version, just different build date/time
        WINDOWS_ZIPS.put("11.0.6+10-LTS", new Release("Jan 18 2020 11:49:14", 9, "11.0.6+0-LTS"));
        WINDOWS_ZIPS.put("11.0.6+0-LTS", new Release("Jan 10 2020 09:52:45", 8, "11.0.6+0-LTS"));
        // Release 11.0.5.10 and 11.0.5.10-2 have the same version, just different build date/time
        WINDOWS_ZIPS.put("11.0.5+10-LTS", new Release("Nov 8 2019 01:41:57", 7, "11.0.5+10-LTS"));
        WINDOWS_ZIPS.put("11.0.5+10-LTS", new Release("Oct 12 2019 18:25:22", 6, "11.0.5+10-LTS"));
        WINDOWS_ZIPS.put("11.0.4-redhat+11-LTS", new Release("Jul 11 2019 23:20:34", 5, "11.0.4-redhat+11-LTS"));
        WINDOWS_ZIPS.put("11.0.3-redhat+7-LTS", new Release("Apr 10 2019 15:05:25", 4, "11.0.3-redhat+7-LTS"));
        // Release 11.0.2.7-1 and 11.0.2.7-5 have the same version, just different build date/time
        WINDOWS_ZIPS.put("11.0.2-redhat+7-LTS", new Release("Feb 27 2019 17:48:49", 3, "11.0.2-redhat+7-LTS"));
        WINDOWS_ZIPS.put("11.0.2-redhat+7-LTS", new Release("Jan 16 2019 17:49:21", 2, "11.0.2-redhat+7-LTS"));
        WINDOWS_ZIPS.put("11.0.1-redhat+13-LTS", new Release("Oct 25 2018 09:40:01", 1, "11.0.1-redhat+13-LTS"));
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Jdk11() {

    }
}

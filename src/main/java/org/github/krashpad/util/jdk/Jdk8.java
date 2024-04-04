/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
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
 * OpenJDK8 release information.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Jdk8 {

    /**
     * RHEL zip release information.
     */
    public static final HashMap<String, Release> RHEL_ZIPS;

    /**
     * RHEL6 amd64 rpm release information.
     */
    public static final HashMap<String, Release> RHEL6_X86_64_RPMS;

    /**
     * RHEL7 rpm ppc64 release information.
     */
    public static final HashMap<String, Release> RHEL7_PPC64_RPMS;

    /**
     * RHEL7 rpm ppc64le release information.
     */
    public static final HashMap<String, Release> RHEL7_PPC64LE_RPMS;

    /**
     * RHEL7 rpm amd64 release information.
     */
    public static final HashMap<String, Release> RHEL7_X86_64_RPMS;

    /**
     * RHEL8 rpm ppc64le release information.
     */
    public static final HashMap<String, Release> RHEL8_PPC64LE_RPMS;

    /**
     * RHEL8 amd64 rpm release information.
     */
    public static final HashMap<String, Release> RHEL8_X86_64_RPMS;

    /**
     * RHEL9 amd64 rpm release information.
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
         * 3) Jan 1 2000 00:00:00 means build date/time unknown/TBD.
         * 
         * 4) Time 00:00:00 means build date/time is estimate.
         */

        // RHEL6 amd64 OpenJDK8 rpm
        RHEL6_X86_64_RPMS = new HashMap<String, Release>();
        RHEL6_X86_64_RPMS.put("LATEST", new Release("Nov 6 2020 02:01:23", 30, "1.8.0_275-b01"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el6_10.x86_64",
                new Release("Nov 6 2020 02:01:23", 30, "1.8.0_275-b01"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el6_10.x86_64",
                new Release("Oct 20 2020 23:38:03", 29, "1.8.0_272-b10"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el6_10.x86_64",
                new Release("Jul 29 2020 03:46:33", 28, "1.8.0_265-b01"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64",
                new Release("Jul 12 2020 19:35:32", 27, "1.8.0_262-b10"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el6_10.x86_64",
                new Release("Apr 14 2020 14:55:11", 26, "1.8.0_252-b09"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b07-1.el6_10.x86_64",
                new Release("Jan 15 2020 00:00:00", 25, "1.8.0_242-b08"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-1.el6_10.x86_64",
                new Release("Oct 15 2019 00:00:00", 24, "1.8.0_232-b09"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el6_10.x86_64",
                new Release("Jul 11 2019 00:00:00", 23, "1.8.0_222-b10"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.212.b04-0.el6_10.x86_64",
                new Release("Apr 11 2019 00:00:00", 22, "1.8.0_212-b04"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el6_10.x86_64",
                new Release("Mar 5 2019 00:00:00", 21, "1.8.0_201-b09"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-1.el6_10.x86_64",
                new Release("Jan 17 2019 00:00:00", 21, "1.8.0_201-b09"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.191.b12-0.el6_10.x86_64",
                new Release("Oct 9 2018 00:00:00", 20, "1.8.0_191-b12"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el6_10.x86_64",
                new Release("Jul 16 2018 00:00:00", 19, "1.8.0_181-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-8.b10.el6_9.x86_64",
                new Release("May 16 2018 00:00:00", 18, "1.8.0_171-b10"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-3.b10.el6_9.x86_64",
                new Release("Apr 2 2018 00:00:00", 18, "1.8.0_171-b10"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-3.b14.el6_9.x86_64",
                new Release("Jan 10 2018 00:00:00", 17, "1.8.0_161-b14"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el6_9.x86_64",
                new Release("Oct 18 2017 00:00:00", 16, "1.8.0_151-b12"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.144-0.b01.el6_9.x86_64",
                new Release("Aug 21 2017 00:00:00", 15, "1.8.0_144-b01"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-3.b16.el6_9.x86_64",
                new Release("Jul 14 2017 00:00:00", 14, "1.8.0_141-b16"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-2.b16.el6_9.x86_64",
                new Release("Jul 14 2017 00:00:00", 14, "1.8.0_141-b16"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-0.b11.el6_9.x86_64",
                new Release("Apr 13 2017 00:00:00", 13, "1.8.0_131-b11"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.121-1.b13.el6.x86_64",
                new Release("Jan 17 2017 00:00:00", 12, "1.8.0_121-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.121-0.b13.el6_8.x86_64",
                new Release("Jan 17 2017 00:00:00", 12, "1.8.0_121-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-1.b15.el6_8.x86_64",
                new Release("Nov 8 2016 00:00:00", 11, "1.8.0_111-b15"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-0.b15.el6_8.x86_64",
                new Release("Nov 8 2016 00:00:00", 11, "1.8.0_111-b15"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.101-3.b13.el6_8.x86_64",
                new Release("Jul 11 2016 00:00:00", 10, "1.8.0_101-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-3.b14.el6_8.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-1.b14.el6.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-0.b14.el6_7.x86_64",
                new Release("Jun 21 2016 00:00:00", 9, "1.8.0_91-b14"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.77-0.b03.el6_7.x86_64",
                new Release("Mar 23 2016 00:00:00", 8, "1.8.0_77-b03"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.71-5.b15.el6.x86_64",
                new Release("Feb 4 2016 00:00:00", 7, "1.8.0_71-b15"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.71-1.b15.el6_7.x86_64",
                new Release("Jan 13 2016 21:08:08", 7, "1.8.0_71-b15"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.65-0.b17.el6_7.x86_64",
                new Release("Oct 15 2015 00:00:00", 6, "1.8.0_65-b17"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-3.b16.el6_7.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-1.b16.el6_7.x86_64",
                new Release("Sep 4 2015 00:00:00", 5, "1.8.0_51-b16"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-0.b16.el6_6.x86_64",
                new Release("Jul 1 2015 23:31:39", 5, "1.8.0_51-b16"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-35.b13.el6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-30.b13.el6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-28.b13.el6_6.x86_64",
                new Release("Apr 29 2015 00:00:00", 4, "1.8.0_45-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.31-1.b13.el6_6.x86_64",
                new Release("Apr 10 2015 00:00:00", 3, "1.8.0_31-b13"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.25-3.b17.el6_6.x86_64",
                new Release("Oct 24 2014 00:00:00", 2, "1.8.0_25-b17"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.25-1.b17.el6.x86_64",
                new Release("Oct 24 2014 00:00:00", 2, "1.8.0_25-b17"));
        RHEL6_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.20-3.b26.el6.x86_64",
                new Release("Sep 12 2014 00:00:00", 1, "1.8.0_20-b26"));

        // RHEL7 ppc64 OpenJDK8 rpm
        RHEL7_PPC64_RPMS = new HashMap<String, Release>();
        RHEL7_PPC64_RPMS.put("LATEST", new Release("Jan 12 2024 00:00:00", 40, "1.8.0_402-b06"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el7_9.ppc64",
                new Release("Jan 12 2024 00:00:00", 40, "1.8.0_402-b06"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el7_9.ppc64",
                new Release("Oct 14 2023 00:00:00", 39, "1.8.0_392-b08"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el7_9.ppc64",
                new Release("Jul 14 2023 00:00:00", 38, "1.8.0_382-b05"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el7_9.ppc64",
                new Release("Apr 19 2023 00:00:00", 37, "1.8.0_372-b07"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el7_9.ppc64",
                new Release("Jan 13 2023 00:00:00", 36, "1.8.0_362-b08"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.ppc64",
                new Release("Oct 16 2022 00:00:00", 35, "1.8.0_352-b08"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.ppc64",
                new Release("Aug 4 2022 00:00:00", 34, "1.8.0_345-b01"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.ppc64",
                new Release("Jul 19 2022 00:00:00", 33, "1.8.0_342-b07"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.ppc64",
                new Release("Apr 19 2022 00:00:00", 32, "1.8.0_332-b09"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0_322-b06-1.el7_9",
                new Release("Jan 21 2022 00:00:00", 31, "1.8.0_322-b06"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.ppc64",
                new Release("Oct 15 2021 00:00:00", 30, "1.8.0_312-b07"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el7_9.ppc64",
                new Release("Jul 16 2021 00:00:00", 29, "1.8.0_302-b08"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el7_9.ppc64",
                new Release("Apr 09 2021 00:00:00", 28, "1.8.0_292-b10"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64",
                new Release("Jan 17 2021 19:55:50", 28, "1.8.0_282-b08"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64",
                new Release("Nov 6 2020 00:00:00", 27, "1.8.0_275-b01"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.ppc64",
                new Release("Oct 20 2020 00:00:00", 26, "1.8.0_272-b10"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64",
                new Release("Jul 28 2020 00:00:00", 25, "1.8.0_265-b01"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.ppc64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        RHEL7_PPC64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.ppc64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));

        // RHEL7 ppc64le OpenJDK8 rpm
        RHEL7_PPC64LE_RPMS = new HashMap<String, Release>();
        RHEL7_PPC64LE_RPMS.put("LATEST", new Release("Jan 12 2024 00:00:00", 41, "1.8.0_402-b06"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el7_9.ppc64le",
                new Release("Jan 12 2024 00:00:00", 41, "1.8.0_402-b06"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el7_9.ppc64le",
                new Release("Oct 14 2023 00:00:00", 40, "1.8.0_392-b08"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el7_9.ppc64le",
                new Release("Jul 14 2023 00:00:00", 39, "1.8.0_382-b05"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el7_9.ppc64le",
                new Release("Apr 19 2023 00:00:00", 38, "1.8.0_372-b07"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el7_9.ppc64le",
                new Release("Jan 13 2023 00:00:00", 37, "1.8.0_362-b08"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.ppc64le",
                new Release("Oct 16 2022 00:00:00", 36, "1.8.0_352-b08"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.ppc64le",
                new Release("Aug 4 2022 00:00:00", 35, "1.8.0_345-b01"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.ppc64le",
                new Release("Jul 19 2022 00:00:00", 34, "1.8.0_342-b07"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.ppc64le",
                new Release("Apr 19 2022 00:00:00", 33, "1.8.0_332-b09"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0_322-b06-1.el7_9",
                new Release("Jan 21 2022 00:00:00", 32, "1.8.0_322-b06"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.ppc64le",
                new Release("Oct 15 2021 00:00:00", 31, "1.8.0_312-b07"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el7_9.ppc64le",
                new Release("Jul 16 2021 12:33:20", 30, "1.8.0_302-b08"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el7_9.ppc64le",
                new Release("Apr 09 2021 00:00:00", 29, "1.8.0_292-b10"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64le",
                new Release("Jan 17 2021 00:00:00", 28, "1.8.0_282-b08"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.ppc64le",
                new Release("Nov 6 2020 06:43:55", 27, "1.8.0_275-b01"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.ppc64le",
                new Release("Oct 20 2020 01:33:13", 26, "1.8.0_272-b10"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le",
                new Release("Jul 28 2020 11:16:00", 25, "1.8.0_265-b01"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.ppc64le",
                new Release("Jul 16 2018 15:46:59", 18, "1.8.0_181-b13"));
        RHEL7_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.ppc64le",
                new Release("Jul 16 2018 11:33:43", 18, "1.8.0_181-b13"));

        // RHEL7 amd64 OpenJDK8 rpm
        RHEL7_X86_64_RPMS = new HashMap<String, Release>();
        RHEL7_X86_64_RPMS.put("LATEST", new Release("Jan 12 2024 00:00:00", 41, "1.8.0_402-b06"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el7_9.x86_64",
                new Release("Jan 12 2024 00:00:00", 41, "1.8.0_402-b06"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el7_9.x86_64",
                new Release("Oct 16 2023 11:52:35", 40, "1.8.0_392-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el7_9.x86_64",
                new Release("Jul 14 2023 06:06:28", 39, "1.8.0_382-b05"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el7_9.x86_64",
                new Release("Apr 19 2023 03:00:36", 38, "1.8.0_372-b07"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el7_9.x86_64",
                new Release("Jan 13 2023 22:08:34", 37, "1.8.0_362-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el7_9.x86_64",
                new Release("Oct 16 2022 04:57:48", 36, "1.8.0_352-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el7_9.x86_64",
                new Release("Aug 4 2022 00:00:00", 35, "1.8.0_345-b01"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.x86_64",
                new Release("Jul 18 2022 23:53:30", 34, "1.8.0_342-b07"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el7_9.x86_64",
                new Release("Apr 19 2022 00:14:41", 33, "1.8.0_332-b09"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-1.el7_9.x86_64",
                new Release("Jan 21 2022 06:01:57", 32, "1.8.0_322-b06"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el7_9.x86_64",
                new Release("Oct 15 2021 04:33:40", 31, "1.8.0_312-b07"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el7_9.x86_64",
                new Release("Jul 16 2021 12:35:49", 30, "1.8.0_302-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el7_9.x86_64",
                new Release("Apr 09 2021 05:00:30", 29, "1.8.0_292-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.x86_64",
                new Release("Jan 17 2021 19:44:11", 28, "1.8.0_282-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el7_9.x86_64",
                new Release("Nov 6 2020 01:41:37", 27, "1.8.0_275-b01"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el7_9.x86_64",
                new Release("Oct 19 2020 21:30:02", 26, "1.8.0_272-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.x86_64",
                new Release("Jul 28 2020 11:07:07", 25, "1.8.0_265-b01"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-1.el7.x86_64",
                new Release("Jul 12 2020 18:53:50", 25, "1.8.0_262-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el7_8.x86_64",
                new Release("Jul 12 2020 18:55:08", 25, "1.8.0_262-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64",
                new Release("Apr 14 2020 14:55:11", 24, "1.8.0_252-b09"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-1.el7.x86_64",
                new Release("Jan 15 2020 18:14:12", 23, "1.8.0_242-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el7_7.x86_64",
                new Release("Jan 19 2020 00:00:00", 23, "1.8.0_242-b08"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-0.el7_7.x86_64",
                new Release("Oct 13 2019 10:47:01", 22, "1.8.0_232-b09"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-1.el7_7.x86_64",
                new Release("Jul 11 2019 03:23:03", 21, "1.8.0_222-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el7_6.x86_64",
                new Release("Jul 17 2019 00:00:00", 21, "1.8.0_222-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b03-1.el7.x86_64",
                new Release("May 22 2019 13:05:27", 21, "1.8.0_222-ea-b03"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.212.b04-0.el7_6.x86_64",
                new Release("Apr 16 2019 00:00:00", 21, "1.8.0_212-b04"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el7_6.x86_64",
                new Release("Mar 1 2019 00:00:00", 20, "1.8.0_201-b09"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-0.el7_6.x86_64",
                new Release("Mar 1 2019 00:00:00", 20, "1.8.0_201-b09"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.191.b12-1.el7_6.x86_64",
                new Release("Oct 9 2018 08:21:20", 19, "1.8.0_191-b12"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.191.b12-0.el7_5.x86_64",
                new Release("Oct 9 2018 08:21:41", 19, "1.8.0_191-b12"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-7.b13.el7.x86_64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.181-3.b13.el7_5.x86_64",
                new Release("Jul 16 2018 00:00:00", 18, "1.8.0_181-b13"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-8.b10.el7_5.x86_64",
                new Release("May 16 2018 00:00:00", 17, "1.8.0_171-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.171-7.b10.el7.x86_64",
                new Release("Apr 2 2018 00:00:00", 17, "1.8.0_171-b10"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-2.b14.el7_4.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-2.b14.el7.x86_64",
                new Release("Jan 10 2018 00:00:00", 16, "1.8.0_161-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.161-0.b14.el7_4.x86_64",
                new Release("Jan 9 2018 19:54:33", 16, "1.8.0_161-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-5.b12.el7_4.x86_64",
                new Release("Nov 20 2017 11:29:18", 15, "1.8.0_151-b12"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.151-1.b12.el7_4.x86_64",
                new Release("Oct 18 2017 00:00:00", 15, "1.8.0_151-b12"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.144-0.b01.el7_4.x86_64",
                new Release("Aug 21 2017 00:00:00", 14, "1.8.0_144-b01"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-2.b16.el7_4.x86_64",
                new Release("Jul 14 2017 00:00:00", 13, "1.8.0_141-b16"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.141-1.b16.el7_3.x86_64",
                new Release("Jul 14 2017 00:00:00", 13, "1.8.0_141-b16"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64",
                new Release("Jun 13 2017 11:27:53", 12, "1.8.0_131-b11"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-3.b12.el7_3.x86_64",
                new Release("May 9 2017 21:36:32", 12, "1.8.0_131-b11"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.131-2.b11.el7_3.x86_64",
                new Release("Apr 13 2017 00:00:00", 12, "1.8.0_131-b11"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.121-0.b13.el7_3.x86_64",
                new Release("Jan 16 2017 09:14:03", 11, "1.8.0_121-b13"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-2.b15.el7_3.x86_64",
                new Release("Nov 8 2016 00:00:00", 10, "1.8.0_111-b15"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.111-1.b15.el7_2.x86_64",
                new Release("Nov 8 2016 00:00:00", 10, "1.8.0_111-b15"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.102-4.b14.el7.x86_64",
                new Release("Sep 14 2016 00:00:00", 9, "1.8.0_102-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.102-1.b14.el7_2.x86_64",
                new Release("Sep 14 2016 00:00:00", 9, "1.8.0_102-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.101-3.b13.el7_2.x86_64",
                new Release("Jul 11 2016 00:00:00", 8, "1.8.0_101-b13"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-1.b14.el7_2.x86_64",
                new Release("Jun 21 2016 00:00:00", 7, "1.8.0_91-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.91-0.b14.el7_2.x86_64",
                new Release("Jun 21 2016 00:00:00", 7, "1.8.0_91-b14"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.77-0.b03.el7_2.x86_64",
                new Release("Mar 23 2016 00:00:00", 6, "1.8.0_77-b03"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.71-2.b15.el7_2.x86_64",
                new Release("Feb 4 2016 00:00:00", 5, "1.8.0_71-b15"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.65-3.b17.el7.x86_64",
                new Release("Oct 19 2015 06:27:55", 4, "1.8.0_65-b17"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.65-2.b17.el7_1.x86_64",
                new Release("Oct 15 2015 00:00:00", 4, "1.8.0_65-b17"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.60-2.b27.el7_1.x86_64",
                new Release("Sep 8 2015 00:00:00", 3, "1.8.0_60-b27"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-2.b16.el7.x86_64",
                new Release("Sep 4 2015 00:00:00", 2, "1.8.0_51-b16"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.51-1.b16.el7_1.x86_64",
                new Release("Sep 4 2015 00:00:00", 2, "1.8.0_51-b16"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.45-30.b13.el7_1.x86_64",
                new Release("Apr 29 2015 00:00:00", 1, "1.8.0_45-b13"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.31-7.b13.el7_1.x86_64",
                new Release("Apr 10 2015 00:00:00", 1, "1.8.0_31-b13"));
        RHEL7_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.31-2.b13.el7.x86_64",
                new Release("Jan 16 2015 10:50:03", 1, "1.8.0_31-b13"));

        // RHEL8 ppc64le OpenJDK8 rpm
        RHEL8_PPC64LE_RPMS = new HashMap<String, Release>();
        RHEL8_PPC64LE_RPMS.put("LATEST", new Release("Jan 12 2024 00:00:00", 44, "1.8.0_402-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-2.el8.ppc64le",
                new Release("Jan 12 2024 00:00:00", 44, "1.8.0_402-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el8_6.ppc64le",
                new Release("Jan 12 2024 00:00:00", 44, "1.8.0_402-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el8_4.ppc64le",
                new Release("Jan 12 2024 00:00:00", 44, "1.8.0_402-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el8_2.ppc64le",
                new Release("Jan 12 2024 00:00:00", 44, "1.8.0_402-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-4.el8.ppc64le",
                new Release("Oct 14 2023 00:00:00", 43, "1.8.0_392-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_6.ppc64le",
                new Release("Oct 14 2023 00:00:00", 43, "1.8.0_392-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_4.ppc64le",
                new Release("Oct 14 2023 00:00:00", 43, "1.8.0_392-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_2.ppc64le",
                new Release("Oct 14 2023 00:00:00", 43, "1.8.0_392-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_1.ppc64le",
                new Release("Oct 14 2023 00:00:00", 43, "1.8.0_392-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-2.el8.ppc64le",
                new Release("Jul 14 2023 00:00:00", 42, "1.8.0_382-b05"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el8_6.ppc64le",
                new Release("Jul 14 2023 00:00:00", 42, "1.8.0_382-b05"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el8_4.ppc64le",
                new Release("Jul 14 2023 00:00:00", 42, "1.8.0_382-b05"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el8_2.ppc64le",
                new Release("Jul 14 2023 00:00:00", 42, "1.8.0_382-b05"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el8_1.ppc64le",
                new Release("Jul 14 2023 00:00:00", 42, "1.8.0_382-b05"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_7.ppc64le",
                new Release("Apr 19 2023 00:00:00", 41, "1.8.0_372-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_6.ppc64le",
                new Release("Apr 19 2023 00:00:00", 41, "1.8.0_372-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_4.ppc64le",
                new Release("Apr 19 2023 00:00:00", 41, "1.8.0_372-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_2.ppc64le",
                new Release("Apr 19 2023 00:00:00", 41, "1.8.0_372-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_1.ppc64le",
                new Release("Apr 19 2023 00:00:00", 41, "1.8.0_372-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b09-2.el8_7.ppc64le",
                new Release("Jan 24 2023 00:00:00", 40, "1.8.0_362-b09"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_6.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_4.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_2.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_1.ppc64le",
                new Release("Jan 13 2023 00:00:00", 40, "1.8.0_362-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_7.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_6.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_4.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_2.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_1.ppc64le",
                new Release("Oct 16 2022 00:00:00", 39, "1.8.0_352-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-5.el8.ppc64le",
                new Release("Aug 30 2022 00:00:00", 38, "1.8.0_345-b01"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_6.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_4.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_2.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_1.ppc64le",
                new Release("Aug 4 2022 00:00:00", 38, "1.8.0_345-b01"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-2.el8_6.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_4.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_2.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_1.ppc64le",
                new Release("Jul 19 2022 00:00:00", 37, "1.8.0_342-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-2.el8_6.ppc64le",
                new Release("Apr 28 2022 13:02:09", 36, "1.8.0_332-b09"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_5.ppc64le",
                new Release("Apr 18 2022 20:17:53", 36, "1.8.0_332-b09"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_4.ppc64le",
                new Release("Apr 28 2022 00:00:00", 36, "1.8.0_332-b09"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_2.ppc64le",
                new Release("Apr 28 2022 00:00:00", 36, "1.8.0_332-b09"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_1.ppc64le",
                new Release("Apr 28 2022 00:00:00", 36, "1.8.0_332-b09"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_5.ppc64le",
                new Release("Jan 23 2022 21:33:55", 35, "1.8.0_322-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_4.ppc64le",
                new Release("Jan 24 2022 00:00:00", 35, "1.8.0_322-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_2.ppc64le",
                new Release("Jan 24 2022 00:00:00", 35, "1.8.0_322-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_1.ppc64le",
                new Release("Jan 24 2022 00:00:00", 35, "1.8.0_322-b06"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.ppc64le",
                new Release("Oct 16 2021 19:04:33", 34, "1.8.0_312-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_4.ppc64le",
                new Release("Oct 15 2021 00:00:00", 34, "1.8.0_312-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_2.ppc64le",
                new Release("Oct 15 2021 00:00:00", 34, "1.8.0_312-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_1.ppc64le",
                new Release("Oct 15 2021 00:00:00", 34, "1.8.0_312-b07"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_4.ppc64le",
                new Release("Jul 16 2021 15:54:01", 33, "1.8.0_302-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_2.ppc64le",
                new Release("Jul 14 2021 00:00:00", 33, "1.8.0_302-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_1.ppc64le",
                new Release("Jul 14 2021 00:00:00", 33, "1.8.0_302-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_3.ppc64le",
                new Release("Apr 14 2021 00:00:00", 32, "1.8.0_292-b10"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_2.ppc64le",
                new Release("Apr 14 2021 00:00:00", 32, "1.8.0_292-b10"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_1.ppc64le",
                new Release("Apr 19 2021 00:00:00", 32, "1.8.0_292-b10"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.ppc64le",
                new Release("Jan 17 2021 16:29:12", 31, "1.8.0_282-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_2.ppc64le",
                new Release("Jan 17 2021 00:00:00", 31, "1.8.0_282-b08"));
        RHEL8_PPC64LE_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_1.ppc64le",
                new Release("Jan 17 2021 00:00:00", 31, "1.8.0_282-b08"));

        // RHEL8 amd64 OpenJDK8 rpm
        RHEL8_X86_64_RPMS = new HashMap<String, Release>();
        RHEL8_X86_64_RPMS.put("LATEST", new Release("Jan 12 2024 11:41:49", 24, "1.8.0_402-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-2.el8.x86_64",
                new Release("Jan 12 2024 11:41:49", 24, "1.8.0_402-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el8_6.x86_64",
                new Release("Jan 12 2024 00:00:00", 24, "1.8.0_402-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el8_4.x86_64",
                new Release("Jan 12 2024 00:00:00", 24, "1.8.0_402-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el8_2.x86_64",
                new Release("Jan 12 2024 00:00:00", 24, "1.8.0_402-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-4.el8.x86_64",
                new Release("Oct 14 2023 13:53:39", 23, "1.8.0_392-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_6.x86_64",
                new Release("Oct 14 2023 00:00:00", 23, "1.8.0_392-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_4.x86_64",
                new Release("Oct 14 2023 00:00:00", 23, "1.8.0_392-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_2.x86_64",
                new Release("Oct 14 2023 00:00:00", 23, "1.8.0_392-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el8_1.x86_64",
                new Release("Oct 14 2023 00:00:00", 23, "1.8.0_392-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-2.el8.x86_64",
                new Release("Jul 14 2023 13:00:51", 22, "1.8.0_382-b05"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-6.el8_1.x86_64",
                new Release("Jul 14 2023 00:00:00", 22, "1.8.0_382-b05"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-4.el8_1.x86_64",
                new Release("Jul 14 2023 00:00:00", 22, "1.8.0_382-b05"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-2.el8_1.x86_64",
                new Release("Jul 14 2023 00:00:00", 22, "1.8.0_382-b05"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el8_1.x86_64",
                new Release("Jul 14 2023 00:00:00", 22, "1.8.0_382-b05"));        
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-4.el8.x86_64",
                new Release("Apr 27 2023 02:51:51", 21, "1.8.0_372-b07"));        
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_7.x86_64",
                new Release("Apr 18 2023 22:30:07", 21, "1.8.0_372-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_6.x86_64",
                new Release("Apr 18 2023 00:00:00", 21, "1.8.0_372-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_4.x86_64",
                new Release("Apr 18 2023 00:00:00", 21, "1.8.0_372-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_2.x86_64",
                new Release("Apr 18 2023 00:00:00", 21, "1.8.0_372-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el8_1.x86_64",
                new Release("Apr 18 2023 00:00:00", 21, "1.8.0_372-b07"));                        
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b09-2.el8_7.x86_64",
                new Release("Jan 23 2023 22:00:01", 20, "1.8.0_362-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_6.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_4.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_2.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-1.el8_1.x86_64",
                new Release("Jan 23 2023 00:00:00", 20, "1.8.0_362-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_7.x86_64",
                new Release("Oct 17 2022 20:39:31", 19, "1.8.0_352-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_6.x86_64",
                new Release("Oct 16 2022 00:38:58", 19, "1.8.0_352-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_4.x86_64",
                new Release("Oct 16 2022 00:00:00", 19, "1.8.0_352-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_2.x86_64",
                new Release("Oct 16 2022 00:00:00", 19, "1.8.0_352-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el8_1.x86_64",
                new Release("Oct 16 2022 00:00:00", 19, "1.8.0_352-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-5.el8.x86_64",
                new Release("Aug 30 2022 00:26:51", 18, "1.8.0_345-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_6.x86_64",
                new Release("Aug 4 2022 13:59:22", 18, "1.8.0_345-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_4.x86_64",
                new Release("Aug 4 2022 00:00:00", 18, "1.8.0_345-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el8_1.x86_64",
                new Release("Aug 4 2022 00:00:00", 18, "1.8.0_345-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-2.el8_6.x86_64",
                new Release("Jul 19 2022 07:37:54", 17, "1.8.0_342-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_4.x86_64",
                new Release("Jul 19 2022 00:00:00", 17, "1.8.0_342-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_2.x86_64",
                new Release("Jul 19 2022 00:00:00", 17, "1.8.0_342-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el8_1.x86_64",
                new Release("Jul 19 2022 00:00:00", 17, "1.8.0_342-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-2.el8_6.x86_64",
                new Release("Apr 28 2022 12:36:43", 16, "1.8.0_332-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_5.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_4.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_2.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el8_1.x86_64",
                new Release("Apr 28 2022 00:00:00", 16, "1.8.0_332-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_5.x86_64",
                new Release("Jan 23 2022 21:19:20", 15, "1.8.0_322-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_4.x86_64",
                new Release("Jan 24 2022 00:00:00", 15, "1.8.0_322-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_2.x86_64",
                new Release("Jan 24 2022 00:00:00", 15, "1.8.0_322-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-2.el8_1.x86_64",
                new Release("Jan 24 2022 00:00:00", 15, "1.8.0_322-b06"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-2.el8_5.x86_64",
                new Release("Oct 16 2021 17:36:49", 14, "1.8.0_312-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_4.x86_64",
                new Release("Oct 15 2021 01:36:39", 14, "1.8.0_312-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_2.x86_64",
                new Release("Oct 15 2021 00:00:00", 14, "1.8.0_312-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.312.b07-1.el8_1.x86_64",
                new Release("Oct 15 2021 00:00:00", 14, "1.8.0_312-b07"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_4.x86_64",
                new Release("Jul 16 2021 14:54:40", 13, "1.8.0_302-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_2.x86_64",
                new Release("Jul 16 2021 00:00:00", 13, "1.8.0_302-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.302.b08-0.el8_1.x86_64",
                new Release("Jul 16 2021 00:00:00", 13, "1.8.0_302-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-1.el8_4.x86_64",
                new Release("Apr 14 2021 01:09:37", 12, "1.8.0_292-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_3.x86_64",
                new Release("Apr 14 2021 09:32:57", 12, "1.8.0_292-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_2.x86_64",
                new Release("Apr 14 2021 00:00:00", 12, "1.8.0_292-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.292.b10-0.el8_1.x86_64",
                new Release("Apr 19 2021 00:00:00", 12, "1.8.0_292-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-4.el8.x86_64",
                new Release("Feb 17 2021 10:47:13", 11, "1.8.0_282-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-2.el8_3.x86_64",
                new Release("Jan 17 2021 16:21:17", 11, "1.8.0_282-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_2.x86_64",
                new Release("Jan 17 2021 00:00:00", 11, "1.8.0_282-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.282.b08-1.el8_1.x86_64",
                new Release("Jan 17 2021 00:00:00", 11, "1.8.0_282-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-1.el8_3.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_2.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_1.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.275.b01-0.el8_0.x86_64",
                new Release("Nov 6 2020 00:00:00", 10, "1.8.0_275-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-3.el8_3.x86_64",
                new Release("Oct 20 2020 23:38:03", 9, "1.8.0_272-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-1.el8_2.x86_64",
                new Release("Oct 19 2020 21:37:28", 9, "1.8.0_272-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el8_1.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.272.b10-0.el8_0.x86_64",
                new Release("Oct 20 2020 00:00:00", 9, "1.8.0_272-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-4.el8.x86_64",
                new Release("Sep 21 2020 12:16:45", 8, "1.8.0_265-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_2.x86_64",
                new Release("Jul 28 2020 04:38:18", 8, "1.8.0_265-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_1.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.265.b01-0.el8_0.x86_64",
                new Release("Sep 21 2020 00:00:00", 8, "1.8.0_265-b01"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_2.x86_64",
                new Release("Jul 12 2020 21:30:56", 7, "1.8.0_262-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_1.x86_64",
                new Release("Jul 12 2020 00:00:00", 7, "1.8.0_262-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.262.b10-0.el8_0.x86_64",
                new Release("Jul 12 2020 00:00:00", 7, "1.8.0_262-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-3.el8_2.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el8_1.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.252.b09-2.el8_0.x86_64",
                new Release("Apr 19 2020 00:00:00", 6, "1.8.0_252-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-4.el8.x86_64",
                new Release("Mar 27 2020 05:18:48", 5, "1.8.0_242-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el8_1.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.242.b08-0.el8_0.x86_64",
                new Release("Mar 27 2020 00:00:00", 5, "1.8.0_242-b08"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-2.el8_1.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "1.8.0_232-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.232.b09-0.el8_0.x86_64",
                new Release("Oct 25 2019 00:00:00", 4, "1.8.0_232-b09"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-1.el8.x86_64",
                new Release("Jul 11 2019 00:00:00", 3, "1.8.0_222-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.222.b10-0.el8_0.x86_64",
                new Release("Jul 11 2019 03:36:28", 3, "1.8.0_222-b10"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.212.b04-1.el8_0.x86_64",
                new Release("May 2 2019 00:00:00", 2, "1.8.0_212-b04"));
        RHEL8_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.201.b09-2.el8.x86_64",
                new Release("Feb 7 2019 00:00:00", 1, "1.8.0_201-b09"));

        // RHEL9 amd64 OpenJDK8 rpm
        RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        RHEL9_X86_64_RPMS.put("LATEST", new Release("Jan 12 2024 00:00:00", 10, "1.8.0_402-b06"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-2.el9.x86_64",
                new Release("Jan 12 2024 00:00:00", 10, "1.8.0_402-b06"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.402.b06-1.el9_0.x86_64",
                new Release("Jan 12 2024 00:00:00", 10, "1.8.0_402-b06"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-3.el9.x86_64",
                new Release("Oct 14 2023 00:00:00", 9, "1.8.0_392-b08"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.392.b08-2.el9_0.x86_64",
                new Release("Oct 14 2023 00:00:00", 9, "1.8.0_392-b08"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-2.el9.x86_64",
                new Release("Jul 14 2023 00:00:00", 8, "1.8.0_382-b05"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.382.b05-1.el9_0.x86_64",
                new Release("Jul 14 2023 00:00:00", 8, "1.8.0_382-b05"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-2.el9.x86_64",
                new Release("Apr 27 2023 02:51:51", 7, "1.8.0_372-b07"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el9_1.x86_64",
                new Release("Apr 19 2023 00:00:00", 7, "1.8.0_372-b07"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.372.b07-1.el9_0.x86_64",
                new Release("Apr 19 2023 00:00:00", 7, "1.8.0_372-b07"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b09-2.el9_1.x86_64",
                new Release("Jan 24 2023 00:00:00", 6, "1.8.0_362-b09"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.362.b08-2.el9_0.x86_64",
                new Release("Jan 13 2023 00:00:00", 6, "1.8.0_362-b08"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el9_1.x86_64",
                new Release("Oct 19 2022 00:00:00", 5, "1.8.0_352-b08"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.352.b08-2.el9_0.x86_64",
                new Release("Oct 16 2022 00:00:00", 5, "1.8.0_352-b08"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-5.el9.x86_64",
                new Release("Aug 30 2022 00:00:00", 4, "1.8.0_345-b01"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.345.b01-1.el9_0.x86_64",
                new Release("Aug 4 2022 00:00:00", 4, "1.8.0_345-b01"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.342.b07-1.el9_0.x86_64",
                new Release("Jul 18 2022 00:00:00", 3, "1.8.0_342-b07"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.332.b09-1.el9_0.x86_64",
                new Release("Apr 28 2022 00:00:00", 2, "1.8.0_332-b09"));
        RHEL9_X86_64_RPMS.put("java-1.8.0-openjdk-1.8.0.322.b06-9.el9.x86_64",
                new Release("Feb 28 2022 00:00:00", 1, "1.8.0_332-b06"));

        // RHEL amd64 OpenJDK8 zip
        // First RHEL zip was 1.8.0_222.
        RHEL_ZIPS = new HashMap<String, Release>();
        RHEL_ZIPS.put("LATEST", new Release("Jan 10 2024 22:48:26", 22, "1.8.0_402-b06"));
        RHEL_ZIPS.put("1.8.0_402-b06", new Release("Jan 10 2024 22:48:26", 22, "1.8.0_402-b06"));
        RHEL_ZIPS.put("1.8.0_392-b08", new Release("Oct 10 2023 21:52:47", 21, "1.8.0_392-b08"));
        RHEL_ZIPS.put("1.8.0_382-b05", new Release("Jul 16 2023 11:17:45", 20, "1.8.0_382-b05"));
        RHEL_ZIPS.put("1.8.0_372-b07", new Release("Apr 19 2023 00:32:44", 19, "1.8.0_372-b07"));
        RHEL_ZIPS.put("1.8.0_362-b08", new Release("Jan 13 2023 23:03:15", 18, "1.8.0_362-b08"));
        RHEL_ZIPS.put("1.8.0_352-b08", new Release("Oct 16 2022 05:58:04", 17, "1.8.0_352-b08"));
        RHEL_ZIPS.put("1.8.0_345-b01", new Release("Aug 4 2022 05:08:02", 16, "1.8.0_345-b01"));
        RHEL_ZIPS.put("1.8.0_342-b07", new Release("Jul 19 2022 17:11:55", 15, "1.8.0_342-b07"));
        RHEL_ZIPS.put("1.8.0_332-b09", new Release("Apr 20 2022 01:14:17", 14, "1.8.0_332-b09"));
        RHEL_ZIPS.put("1.8.0_322-b06", new Release("Jan 25 2022 16:48:34", 13, "1.8.0_322-b06"));
        RHEL_ZIPS.put("1.8.0_312-b07", new Release("Oct 18 2021 16:23:58", 12, "1.8.0_312-b07"));
        RHEL_ZIPS.put("1.8.0_302-b08", new Release("Jul 17 2021 18:13:18", 11, "1.8.0_302-b08"));
        RHEL_ZIPS.put("1.8.0_292-b10", new Release("Apr 19 2021 11:42:07", 10, "1.8.0_292-b10"));
        RHEL_ZIPS.put("1.8.0_282-b08", new Release("Jan 18 2021 13:56:21", 9, "1.8.0_282-b08"));
        RHEL_ZIPS.put("1.8.0_275-b01", new Release("Nov 11 2020 12:18:48", 8, "1.8.0_275-b01"));
        RHEL_ZIPS.put("1.8.0_272-b10", new Release("Oct 20 2020 12:17:01", 7, "1.8.0_272-b10"));
        RHEL_ZIPS.put("1.8.0_265-b01", new Release("Jul 28 2020 13:27:15", 6, "1.8.0_265-b01"));
        RHEL_ZIPS.put("1.8.0_262-b10", new Release("Jul 14 2020 11:46:22", 5, "1.8.0_262-b10"));
        RHEL_ZIPS.put("1.8.0_252-b09", new Release("Apr 13 2020 13:01:26", 4, "1.8.0_252-b09"));
        RHEL_ZIPS.put("1.8.0_242-b08", new Release("Jan 17 2020 09:36:23", 3, "1.8.0_242-b08"));
        RHEL_ZIPS.put("1.8.0_232-b09", new Release("Oct 15 2019 05:49:57", 2, "1.8.0_232-b09"));
        RHEL_ZIPS.put("1.8.0_222-b10", new Release("Aug 2 2019 08:16:48", 1, "1.8.0_222-b10"));

        // Windows amd64 OpenJDK8 zip
        // First RH Windows release was 1.8.0_191-1-redhat-b12. There was no RH Windows release for u202.
        WINDOWS_ZIPS = new HashMap<String, Release>();
        WINDOWS_ZIPS.put("LATEST", new Release("Jan 11 2024 13:24:11", 27, "1.8.0_402-b06"));
        WINDOWS_ZIPS.put("1.8.0_402-b06", new Release("Jan 11 2024 13:24:11", 27, "1.8.0_402-b06"));
        WINDOWS_ZIPS.put("1.8.0_392-b08", new Release("Oct 11 2023 14:51:24", 26, "1.8.0_392-b08"));
        WINDOWS_ZIPS.put("1.8.0_382-b05", new Release("Jul 14 2023 00:00:00", 25, "1.8.0_382-b05"));
        WINDOWS_ZIPS.put("1.8.0_372-b07", new Release("Apr 19 2023 00:00:00", 24, "1.8.0_372-b07"));
        WINDOWS_ZIPS.put("1.8.0_362-b08", new Release("Jan 13 2023 00:00:00", 23, "1.8.0_362-b08"));
        WINDOWS_ZIPS.put("1.8.0_352-b08", new Release("Oct 16 2022 00:00:00", 22, "1.8.0_352-b08"));
        WINDOWS_ZIPS.put("1.8.0_345-b01", new Release("Aug 4 2022 00:00:00", 21, "1.8.0_345-b01"));
        WINDOWS_ZIPS.put("1.8.0_342-b07", new Release("Jul 19 2022 00:00:00", 20, "1.8.0_342-b07"));
        // 2 builds w/ the same release string
        WINDOWS_ZIPS.put("1.8.0_332-b09-2", new Release("Apr 27 2022 21:29:19", 19, "1.8.0_332-b09"));
        WINDOWS_ZIPS.put("1.8.0_332-b09-1", new Release("Apr 19 2022 13:36:53", 18, "1.8.0_332-b09"));
        WINDOWS_ZIPS.put("1.8.0_322-b06", new Release("Jan 21 2022 12:43:13", 17, "1.8.0_322-b06"));
        WINDOWS_ZIPS.put("1.8.0_312-b07", new Release("Oct 16 2021 19:50:08", 16, "1.8.0_312-b07"));
        WINDOWS_ZIPS.put("1.8.0_302-b08", new Release("Jul 16 2021 19:06:52", 15, "1.8.0_302-b08"));
        WINDOWS_ZIPS.put("1.8.0_292-b10", new Release("Apr 15 2021 23:02:06", 14, "1.8.0_292-b10"));
        WINDOWS_ZIPS.put("1.8.0_282-b08", new Release("Jan 17 2021 20:58:15", 13, "1.8.0_282-b08"));
        WINDOWS_ZIPS.put("1.8.0_275-b01", new Release("Nov 6 2020 21:03:52", 12, "1.8.0_275-b01"));
        WINDOWS_ZIPS.put("1.8.0_272-b10", new Release("Oct 17 2020 18:45:37", 11, "1.8.0_272-b10"));
        WINDOWS_ZIPS.put("1.8.0_265-b01", new Release("Jul 31 2020 12:33:19", 10, "1.8.0_265-b01"));
        WINDOWS_ZIPS.put("1.8.0_262-b10", new Release("Jul 14 2020 12:47:22", 9, "1.8.0_262-b10"));
        WINDOWS_ZIPS.put("1.8.0_252-b09", new Release("Apr 9 2020 22:56:04", 8, "1.8.0_252-b09"));
        WINDOWS_ZIPS.put("1.8.0_242-b08", new Release("Jan 18 2020 12:08:54", 7, "1.8.0_242-b08"));
        WINDOWS_ZIPS.put("1.8.0_242-b07", new Release("Jan 13 2020 12:14:39", 6, "1.8.0_242-b07"));
        WINDOWS_ZIPS.put("1.8.0_232-b09", new Release("Oct 12 2019 23:47:20", 5, "1.8.0_232-b09"));
        WINDOWS_ZIPS.put("1.8.0_222-1-redhat-b10", new Release("Jul 12 2019 11:59:03", 4, "1.8.0_222-1-redhat-b10"));
        WINDOWS_ZIPS.put("1.8.0_212-2-redhat-b04", new Release("Apr 15 2019 20:39:59", 3, "1.8.0_212-2-redhat-b04"));
        WINDOWS_ZIPS.put("1.8.0_201-1-redhat-b09", new Release("Jan 18 2019 11:40:39", 2, "1.8.0_201-1-redhat-b09"));
        WINDOWS_ZIPS.put("1.8.0_191-1-redhat-b12", new Release("Oct 22 2018 23:16:39", 1, "1.8.0_191-1-redhat-b12"));
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Jdk8() {

    }
}

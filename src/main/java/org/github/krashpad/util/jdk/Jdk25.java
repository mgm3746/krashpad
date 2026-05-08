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
 * OpenJDK25 release information.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Jdk25 {

    /**
     * RHEL zip release information.
     */
    public static final HashMap<String, Release> RHEL_ZIPS;

    /**
     * RHEL9 rpm release information.
     */
    public static final HashMap<String, Release> RHEL9_X86_64_RPMS;

    /**
     * RHEL10 rpm release information.
     */
    public static final HashMap<String, Release> RHEL10_X86_64_RPMS;

    /**
     * Windows release information.
     */
    public static final HashMap<String, Release> WINDOWS_ZIPS;

    static {
        /*
         * Notes:
         * 
         * 1) Rpm key is version string. JAVA_HOME does not include the rpm name and is generic
         * (/usr/lib/jvm/java-25-openjdk).
         * 
         * 2) Zip key is version string.
         * 
         * 3) 2024-07-16 means build date/time unknown.
         * 
         * 4) 2024-07-16T00:00:00Z means build date/time unspecified (e.g. to support reproducible builds).
         * 
         * FIXME: The path is /usr/lib/jvm/java-25-openjdk/ for RHEL9 too.
         * 
         */

        // RHEL9 amd64 OpenJDK25 rpm
        RHEL9_X86_64_RPMS = new HashMap<String, Release>();
        RHEL9_X86_64_RPMS.put("LATEST", new Release("2026-04-21T00:00:00Z", 3, "25.0.3+9-LTS"));
        RHEL9_X86_64_RPMS.put("25.0.3+9-LTS", new Release("2026-04-21T00:00:00Z", 3, "25.0.3+9-LTS"));
        RHEL9_X86_64_RPMS.put("25.0.2+10-LTS", new Release("2026-01-20T00:00:00Z", 2, "25.0.2+10-LTS"));
        RHEL9_X86_64_RPMS.put("25.0.1+8-LTS", new Release("2025-12-09T00:00:00Z", 1, "25.0.1+8-LTS"));

        // RHEL10 amd64 OpenJDK25 rpm. Note directory name is common, not rpm specific.
        RHEL10_X86_64_RPMS = new HashMap<String, Release>();
        RHEL10_X86_64_RPMS.put("LATEST", new Release("2026-04-21T00:00:00Z", 4, "25.0.3+9-LTS"));
        RHEL10_X86_64_RPMS.put("25.0.3+9-LTS", new Release("2026-04-21T00:00:00Z", 4, "25.0.3+9-LTS"));
        RHEL10_X86_64_RPMS.put("25.0.2+10-LTS", new Release("2026-01-20T00:00:00Z", 3, "25.0.2+10-LTS"));
        RHEL10_X86_64_RPMS.put("25.0.1+8-LTS", new Release("2025-10-21T00:00:00Z", 2, "25.0.1+8-LTS"));
        RHEL10_X86_64_RPMS.put("22.0.2+9-LTS", new Release("2024-07-16T00:00:00Z", 1, "22.0.2+9-LTS"));

        // RHEL amd64 OpenJDK25 zip
        RHEL_ZIPS = new HashMap<String, Release>();
        RHEL_ZIPS.put("LATEST", new Release("2026-04-21T00:00:00Z", 3, "25.0.3+9-LTS"));
        RHEL_ZIPS.put("25.0.3+9-LTS", new Release("2026-04-21T00:00:00Z", 3, "25.0.3+9-LTS"));
        RHEL_ZIPS.put("25.0.2+10-LTS", new Release("2026-01-20T00:00:00Z", 2, "25.0.2+10-LTS"));
        RHEL_ZIPS.put("25.0.1+8-LTS", new Release("2025-10-21T00:00:00Z", 1, "25.0.1+8-LTS"));

        // Windows amd64 OpenJDK21 zip
        WINDOWS_ZIPS = new HashMap<String, Release>();
        WINDOWS_ZIPS.put("LATEST", new Release("2026-04-21T00:00:00Z", 2, "25.0.3+9-LTS"));
        WINDOWS_ZIPS.put("25.0.3+9-LTS", new Release("2026-04-21T00:00:00Z", 2, "25.0.3+9-LTS"));
        WINDOWS_ZIPS.put("25.0.2+10-LTS", new Release("2026-01-20T00:00:00Z", 1, "25.0.2+10-LTS"));
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Jdk25() {

    }
}

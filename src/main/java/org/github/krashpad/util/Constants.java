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
package org.github.krashpad.util;

/**
 * Global constants.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Constants {

    /**
     * Defined CPU architectures.
     */
    public enum CpuArch {
        POWER8, POWER9, UNIDENTIFIED, X86_64
    }

    /**
     * Defined devices.
     */
    public enum Device {
        AWS_BLOCK_STORAGE, FIXED_DISK, NFS, SCSI_DISK, UNIDENTIFIED
    }

    /**
     * Defined OS types.
     */
    public enum OsVendor {
        //
        CENTOS, IBM, MICROSOFT, ORACLE, REDHAT, UNIDENTIFIED
    }

    /**
     * Defined OS versions.
     */
    public enum OsVersion {
        //
        CENTOS6, CENTOS7, CENTOS8, CENTOS9, RHEL6, RHEL7, RHEL8, RHEL9, UNIDENTIFIED
    }

    /**
     * Analysis property file.
     */
    public static final String ANALYSIS_PROPERTY_FILE = "analysis";

    /**
     * The minimum throughput (percent of time spent not doing garbage collection for a given time interval) to not be
     * flagged a bottleneck.
     */
    public static final int DEFAULT_BOTTLENECK_THROUGHPUT_THRESHOLD = 90;

    /**
     * The threshold for the time (seconds) for the first log entry for a VM log to be considered complete. First log
     * entries with timestamps below the threshold may indicate a partial VN log or VM events that were not a
     * recognizable format.
     */
    public static final int FIRST_TIMESTAMP_THRESHOLD = 60;

    /**
     * Line separator used for report and preparsing.
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Help command line long option.
     */
    public static final String OPTION_HELP_LONG = "help";

    /**
     * Help command line short option.
     */
    public static final String OPTION_HELP_SHORT = "h";

    /**
     * Output (name of report file) command line long option.
     */
    public static final String OPTION_OUTPUT_LONG = "output";

    /**
     * Output (name of report file) command line short option.
     */
    public static final String OPTION_OUTPUT_SHORT = "o";

    /**
     * Report console command line long option.
     */
    public static final String OPTION_REPORT_CONSOLE_LONG = "console";

    /**
     * Report console command line short option.
     */
    public static final String OPTION_REPORT_CONSOLE_SHORT = "c";

    /**
     * Default output file name.
     */
    public static final String OUTPUT_FILE_NAME = "report.txt";

    /**
     * Literal for reporting and unknown property.
     */
    public static final String PROPERTY_UNKNOWN = ("UNKNOWN");

    /**
     * Test data directory.
     */
    public static final String TEST_DATA_DIR = "src" + System.getProperty("file.separator") + "test"
            + System.getProperty("file.separator") + "data" + System.getProperty("file.separator");

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Constants() {

    }
}

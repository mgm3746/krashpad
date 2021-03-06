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
package org.github.krashpad.util;

import java.math.BigDecimal;

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
        POWER8, POWER9, UNKNOWN, X86_64
    }

    /**
     * Defined devices.
     */
    public enum Device {
        AWS_BLOCK_STORAGE, FIXED_DISK, NFS, SCSI_DISK, UNKNOWN
    }

    /**
     * Defined OS types.
     */
    public enum OsType {
        //
        LINUX, SOLARIS, UNKNOWN, WINDOWS
    }

    /**
     * Defined OS types.
     */
    public enum OsVendor {
        //
        CENTOS, IBM, MICROSOFT, ORACLE, REDHAT, UNKNOWN
    }

    /**
     * Defined OS versions.
     */
    public enum OsVersion {
        //
        CENTOS6, CENTOS7, CENTOS8, RHEL6, RHEL7, RHEL8, UNKNOWN
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
     * gigabyte
     */
    public static final BigDecimal GIGABYTE = new BigDecimal("1073741824");

    /**
     * kilobyte
     */
    public static final BigDecimal KILOBYTE = new BigDecimal("1024");

    /**
     * Line separator used for report and preparsing.
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * megabyte
     */
    public static final BigDecimal MEGABYTE = new BigDecimal("1048576");

    /**
     * Help command line long option.
     */
    public static final String OPTION_HELP_LONG = "help";

    /**
     * Help command line short option.
     */
    public static final String OPTION_HELP_SHORT = "h";

    /**
     * Latest version command line long option.
     */
    public static final String OPTION_LATEST_VERSION_LONG = "latest";

    /**
     * Latest version command line short option.
     */
    public static final String OPTION_LATEST_VERSION_SHORT = "l";

    /**
     * Output (name of report file) command line long option.
     */
    public static final String OPTION_OUTPUT_LONG = "output";

    /**
     * Output (name of report file) command line short option.
     */
    public static final String OPTION_OUTPUT_SHORT = "o";

    /**
     * Threshold command line long option.
     */
    public static final String OPTION_THRESHOLD_LONG = "threshold";

    /**
     * Threshold command line short option.
     */
    public static final String OPTION_THRESHOLD_SHORT = "t";

    /**
     * Version command line long option.
     */
    public static final String OPTION_VERSION_LONG = "version";

    /**
     * Version command line short option.
     */
    public static final String OPTION_VERSION_SHORT = "v";

    /**
     * Default output file name.
     */
    public static final String OUTPUT_FILE_NAME = "report.txt";

    /**
     * Precision for reporting bytes.
     */
    public static final char PRECISION_REPORTING = 'M';

    /**
     * Test data directory.
     */
    public static final String TEST_DATA_DIR = "src" + System.getProperty("file.separator") + "test"
            + System.getProperty("file.separator") + "data" + System.getProperty("file.separator");;

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private Constants() {

    }
}

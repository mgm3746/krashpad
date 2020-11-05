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

package org.github.errcat.domain;

import java.util.ArrayList;
import java.util.List;

import org.github.errcat.domain.jdk.JvmInfoEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.JdkVersion;

/**
 * Fatal error log data.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class FatalErrorLog {

    /**
     * JVM environment information.
     */
    private JvmInfoEvent jvmInfo;

    /**
     * OS information.
     */
    private OsEvent os;

    /**
     * Log lines that do not match any existing logging patterns.
     */
    private List<String> unidentifiedLogLines;

    /**
     * Analysis.
     */
    private List<Analysis> analysis;

    /*
     * Default constructor.
     */
    public FatalErrorLog() {

        analysis = new ArrayList<Analysis>();
        unidentifiedLogLines = new ArrayList<String>();
    }

    public void setJvminfo(JvmInfoEvent jvmInfo) {
        this.jvmInfo = jvmInfo;
    }

    public void setOs(OsEvent os) {
        this.os = os;
    }

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    }

    public void setUnidentifiedLogLines(List<String> unidentifiedLogLines) {
        this.unidentifiedLogLines = unidentifiedLogLines;
    }

    public List<Analysis> getAnalysis() {
        return analysis;
    }

    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    }

    public JdkVersion getJdkVersion() {
        JdkVersion version = JdkVersion.UNKNOWN;
        if (jvmInfo != null) {
            version = jvmInfo.getJdkVersion();
        }
        return version;
    }

    public OsType getOsType() {
        OsType osType = OsType.UNKNOWN;
        if (os != null) {
            osType = os.getOs();
        }
        return osType;
    }

    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (jvmInfo != null) {
            arch = jvmInfo.getArch();
        }
        return arch;
    }

    /**
     * Do analysis.
     */
    public void doAnalysis() {

        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        }

        // TODO:
        if (haveData()) {
            doDataAnalysis();
        }
    }

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // Check for partial log
        /*
         * if (this.firstSafepointEvent != null && VmUtil.isPartialLog(firstSafepointEvent.getTimestamp())) {
         * analysis.add(Analysis.INFO_FIRST_TIMESTAMP_THRESHOLD_EXCEEDED); }
         */
    }

    /**
     * @return true if there is data, false otherwise (e.g. no fatal error log lines recognized).
     */
    public boolean haveData() {
        boolean haveData = true;
        // TODO:
        return haveData;
    }

    /**
     * Do JVM options analysis.
     */
    private void doJvmOptionsAnalysis() {

        // TODO:
    }
}

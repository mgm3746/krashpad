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

package org.github.errcat.domain.jdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.github.errcat.util.Constants;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.CrashCause;
import org.github.errcat.util.jdk.JdkUtil.JdkVendor;
import org.github.errcat.util.jdk.JdkUtil.JdkVersionMajor;

/**
 * Fatal error log data.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class FatalErrorLog {

    /**
     * The reason for the crash.
     */
    private CrashCause crashCause;

    /**
     * JVM environment information.
     */
    private VmInfoEvent vmInfoEvent;

    /**
     * OS information.
     */
    private OsEvent osEvent;

    /**
     * uname information.
     */
    private UnameEvent unameEvent;

    /**
     * Header.
     */
    private List<HeaderEvent> header;

    /**
     * Stack.
     */
    private List<StackEvent> stack;

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
        crashCause = CrashCause.UNKNOWN;
        header = new ArrayList<HeaderEvent>();
        stack = new ArrayList<StackEvent>();
        analysis = new ArrayList<Analysis>();
        unidentifiedLogLines = new ArrayList<String>();
    }

    public void setVminfo(VmInfoEvent vmInfoEvent) {
        this.vmInfoEvent = vmInfoEvent;
    }

    public void setOs(OsEvent os) {
        this.osEvent = os;
    }

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    }

    public List<HeaderEvent> getHeader() {
        return header;
    }

    public List<StackEvent> getStack() {
        return stack;
    }

    public void setUname(UnameEvent uname) {
        this.unameEvent = uname;
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

    public CrashCause getCrashCause() {
        return crashCause;
    }

    public JdkVendor getJdkVendor() {
        JdkVendor version = JdkVendor.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJdkVendor();
        }
        return version;
    }

    public JdkVersionMajor getJdkVersionMajor() {
        JdkVersionMajor version = JdkVersionMajor.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJdkVersionMajor();
        }
        return version;
    }

    public String getOs() {
        String os = OsType.UNKNOWN.toString();
        if (osEvent != null) {
            os = osEvent.getOsString();
        }
        return os;
    }

    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (vmInfoEvent != null) {
            arch = vmInfoEvent.getArch();
        }
        return arch;
    }

    public String CausedBy() {
        StringBuilder causedBy = new StringBuilder();
        if (header != null) {
            Iterator<HeaderEvent> iterator = header.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSigSegv() || he.isProblematicFrame()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(Constants.LINE_SEPARATOR);
                    }
                    causedBy.append(he.getLogEntry());
                }
            }
        }
        return causedBy.toString();
    }

    public boolean haveDebuggingSymbols() {
        boolean haveDebuggingSymbols = false;
        if (header != null) {
            Iterator<HeaderEvent> iterator = header.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isProblematicFrame()) {
                    haveDebuggingSymbols = he.getLogEntry().matches("^# (V)  \\[.+\\].+$");
                }
            }
        }
        return haveDebuggingSymbols;
    }

    /**
     * Do analysis.
     */
    public void doAnalysis() {

        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        }

        if (haveData()) {
            doDataAnalysis();
            if (!haveDebuggingSymbols()) {
                analysis.add(Analysis.ERROR_DEBUGGING_SYMBOLS);
            }
        }

        doJvmOptionsAnalysis();
    }

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // TODO:
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

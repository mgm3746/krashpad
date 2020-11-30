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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.util.Constants;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.Constants.OsVendor;
import org.github.errcat.util.Constants.OsVersion;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.CrashCause;
import org.github.errcat.util.jdk.JdkUtil.JavaSpecification;
import org.github.errcat.util.jdk.JdkUtil.JavaVendor;

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
     * Stack information.
     */
    private List<StackEvent> stack;

    /**
     * Dynamic library information..
     */
    private List<DynamicLibraryEvent> dynamicLibrary;

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
        dynamicLibrary = new ArrayList<DynamicLibraryEvent>();
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

    public List<DynamicLibraryEvent> getDynamicLibrary() {
        return dynamicLibrary;
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

    public JavaVendor getJavaVendor() {
        JavaVendor vendor = JavaVendor.UNKNOWN;
        if (vmInfoEvent != null) {
            vendor = vmInfoEvent.getJavaVendor();
        } else {
            if (getArch() == Arch.SPARC) {
                vendor = JavaVendor.ORACLE;
            }
        }
        return vendor;
    }

    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJavaSpecification();
        }
        return version;
    }

    public String getJdkReleaseString() {
        String release = "UNKNOWN";
        if (vmInfoEvent != null) {
            release = vmInfoEvent.getJdkReleaseString();
        } else {
            // Check header
            Iterator<HeaderEvent> iterator = header.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJreVersion()) {
                    String regEx = "^.+\\(build (1.8.0_251-b08)\\)$";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        release = matcher.group(1);
                    }
                }
            }
        }
        return release;
    }

    /**
     * @return The JDK build date/time.
     */
    public Date getJdkBuildDate() {
        Date date = null;
        if (vmInfoEvent != null) {
            date = vmInfoEvent.getBuildDate();
        }
        return date;
    }

    public String getOs() {
        String os = OsType.UNKNOWN.toString();
        if (osEvent != null) {
            os = osEvent.getOsString();
        } else if (unameEvent != null) {
            os = unameEvent.getOsString();
        }
        return os;
    }

    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNKNOWN;
        if (unameEvent != null) {
            osVersion = unameEvent.getOsVersion();
        }
        return osVersion;
    }

    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (vmInfoEvent != null) {
            arch = vmInfoEvent.getArch();
        } else {
            // Check header
            Iterator<HeaderEvent> iterator = header.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJavaVm() && he.getLogEntry().matches("^.+solaris-sparc.+$")) {
                    arch = Arch.SPARC;
                }
            }
        }
        return arch;
    }

    public String getCausedBy() {
        StringBuilder causedBy = new StringBuilder();
        if (header != null) {
            Iterator<HeaderEvent> iterator = header.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSigSegv() || he.isProblematicFrame() || he.isInternalError() || he.isError()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(Constants.LINE_SEPARATOR);
                    }
                    causedBy.append(he.getLogEntry());
                }
            }
        }
        return causedBy.toString();
    }

    public boolean haveJdkDebugSymbols() {
        boolean haveJdkDebugSymbols = false;
        if (header != null) {
            Iterator<HeaderEvent> iterator1 = header.iterator();
            while (iterator1.hasNext()) {
                HeaderEvent he = iterator1.next();
                if (he.isProblematicFrame()) {
                    haveJdkDebugSymbols = he.getLogEntry().matches("^# V  \\[.+\\].+$");
                    break;
                }
            }
        }
        if (!haveJdkDebugSymbols) {
            if (stack != null) {
                Iterator<StackEvent> iterator2 = stack.iterator();
                while (iterator2.hasNext() && !haveJdkDebugSymbols) {
                    StackEvent se = iterator2.next();
                    if (se.isVmCode()) {
                        haveJdkDebugSymbols = se.getLogEntry().matches("^V  \\[.+\\].+$");
                    }
                }
            }
        }
        return haveJdkDebugSymbols;
    }

    public boolean haveVmCodeInStack() {
        boolean haveVmCodeInStack = false;
        if (stack != null) {
            Iterator<StackEvent> iterator = stack.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmCode()) {
                    haveVmCodeInStack = true;
                    break;
                }
            }
        }
        return haveVmCodeInStack;
    }

    public boolean isRhel() {
        boolean isRhel = false;
        if (osEvent != null) {
            isRhel = osEvent.isRhel();
        }
        return isRhel;
    }

    public boolean isJdkRhBuild() {
        boolean isJdkRhBuild = false;
        if (JdkUtil.isRhelRpmInstall(this) || JdkUtil.isRhelZipInstall(this)) {
            isJdkRhBuild = true;
        }
        return isJdkRhBuild;
    }

    /**
     * Do analysis.
     */
    public void doAnalysis() {

        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        }

        doDataAnalysis();
        doJvmOptionsAnalysis();
    }

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // Check if debugging symbols are installed
        if (haveVmCodeInStack() && !haveJdkDebugSymbols()) {
            analysis.add(Analysis.ERROR_DEBUGGING_SYMBOLS);
        }
        if (!JdkUtil.isLatestJdkRelease(this)) {
            analysis.add(0, Analysis.WARN_JDK_NOT_LATEST);
        }
        if (osEvent != null) {
            if (osEvent.isRhel()) {
                if (JdkUtil.isRhelRpmInstall(this)) {
                    analysis.add(0, Analysis.INFO_RH_INSTALL_RPM);
                } else if (JdkUtil.isRhelZipInstall(this)) {
                    analysis.add(0, Analysis.INFO_RH_INSTALL_ZIP);
                } else {
                    analysis.add(0, Analysis.INFO_JDK_NOT_RH_BUILD);
                }
            } else if (osEvent.getOsType() == OsType.Linux && osEvent.getOsVendor() != OsVendor.RedHat) {
                analysis.add(0, Analysis.INFO_RH_UNSUPPORTED_OS);
            }
        }
        if (!haveVmCodeInStack()) {
            analysis.add(Analysis.INFO_STACK_NO_VM_CODE);
        }
        if (isJdkRhBuild()) {
            // Check for missing or non-matching build data
            if (JdkUtil.getJdkReleaseDate(this) == null || (getJdkBuildDate() != null
                    && getJdkBuildDate().compareTo(JdkUtil.getJdkReleaseDate(this)) != 0)) {
                analysis.add(Analysis.INFO_RH_BUILD_UNKNOWN);
            }
        }
    }

    /**
     * Do JVM options analysis.
     */
    private void doJvmOptionsAnalysis() {
        // TODO:
    }
}

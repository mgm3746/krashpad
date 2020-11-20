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
    ***REMOVED***

    public void setVminfo(VmInfoEvent vmInfoEvent) {
        this.vmInfoEvent = vmInfoEvent;
    ***REMOVED***

    public void setOs(OsEvent os) {
        this.osEvent = os;
    ***REMOVED***

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    ***REMOVED***

    public List<HeaderEvent> getHeader() {
        return header;
    ***REMOVED***

    public List<StackEvent> getStack() {
        return stack;
    ***REMOVED***

    public List<DynamicLibraryEvent> getDynamicLibrary() {
        return dynamicLibrary;
    ***REMOVED***

    public void setUname(UnameEvent uname) {
        this.unameEvent = uname;
    ***REMOVED***

    public void setUnidentifiedLogLines(List<String> unidentifiedLogLines) {
        this.unidentifiedLogLines = unidentifiedLogLines;
    ***REMOVED***

    public List<Analysis> getAnalysis() {
        return analysis;
    ***REMOVED***

    public void setAnalysis(List<Analysis> analysis) {
        this.analysis = analysis;
    ***REMOVED***

    public CrashCause getCrashCause() {
        return crashCause;
    ***REMOVED***

    public JavaVendor getJavaVendor() {
        JavaVendor version = JavaVendor.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJavaVendor();
        ***REMOVED***
        return version;
    ***REMOVED***

    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJavaSpecification();
        ***REMOVED***
        return version;
    ***REMOVED***

    public String getJdkReleaseString() {
        String release = "UNKNOWN";
        if (vmInfoEvent != null) {
            release = vmInfoEvent.getJdkReleaseString();
        ***REMOVED***
        return release;
    ***REMOVED***

    public String getOs() {
        String os = OsType.UNKNOWN.toString();
        if (osEvent != null) {
            os = osEvent.getOsString();
        ***REMOVED***
        return os;
    ***REMOVED***

    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (vmInfoEvent != null) {
            arch = vmInfoEvent.getArch();
        ***REMOVED***
        return arch;
    ***REMOVED***

    public String CausedBy() {
        StringBuilder causedBy = new StringBuilder();
        if (header != null) {
            Iterator<HeaderEvent> iterator = header.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSigSegv() || he.isProblematicFrame() || he.isInternalError() || he.isError()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    causedBy.append(he.getLogEntry());
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return causedBy.toString();
    ***REMOVED***

    public boolean haveDebuggingSymbols() {
        boolean haveDebuggingSymbols = false;
        if (header != null) {
            Iterator<HeaderEvent> iterator1 = header.iterator();
            while (iterator1.hasNext()) {
                HeaderEvent he = iterator1.next();
                if (he.isProblematicFrame()) {
                    haveDebuggingSymbols = he.getLogEntry().matches("^***REMOVED*** (V)  \\[.+\\].+$");
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (!haveDebuggingSymbols) {
            if (stack != null) {
                Iterator<StackEvent> iterator2 = stack.iterator();
                while (iterator2.hasNext() && !haveDebuggingSymbols) {
                    StackEvent se = iterator2.next();
                    if (se.isVmCode()) {
                        haveDebuggingSymbols = se.getLogEntry().matches("^V  \\[.+\\].+$");
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveDebuggingSymbols;
    ***REMOVED***

    /**
     * Do analysis.
     */
    public void doAnalysis() {

        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        ***REMOVED***

        doDataAnalysis();
        doJvmOptionsAnalysis();
    ***REMOVED***

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // Check if debugging symbols are installed
        if (!haveDebuggingSymbols()) {
            analysis.add(Analysis.ERROR_DEBUGGING_SYMBOLS);
        ***REMOVED***
        if (!JdkUtil.isLatestJdkRelease(this)) {
            analysis.add(0, Analysis.WARN_JDK_NOT_LATEST);
        ***REMOVED***
        if (JdkUtil.isRedHatRpmInstall(this)) {
            analysis.add(0, Analysis.INFO_RH_RPM_INSTALL);
        ***REMOVED***
    ***REMOVED***

    /**
     * Do JVM options analysis.
     */
    private void doJvmOptionsAnalysis() {
        // TODO:
    ***REMOVED***
***REMOVED***

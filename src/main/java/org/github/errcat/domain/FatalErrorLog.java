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

import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.domain.jdk.UnameEvent;
import org.github.errcat.domain.jdk.VmInfoEvent;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkUtil.Arch;
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
    private List<HeaderEvent> headerEvent;

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
        headerEvent = new ArrayList<HeaderEvent>();
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
        return headerEvent;
    ***REMOVED***

    public void setHeader(List<HeaderEvent> header) {
        this.headerEvent = header;
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

    public JdkVendor getJdkVendor() {
        JdkVendor version = JdkVendor.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJdkVendor();
        ***REMOVED***
        return version;
    ***REMOVED***

    public JdkVersionMajor getJdkVersionMajor() {
        JdkVersionMajor version = JdkVersionMajor.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJdkVersionMajor();
        ***REMOVED***
        return version;
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

    /**
     * Do analysis.
     */
    public void doAnalysis() {

        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        ***REMOVED***

        if (haveData()) {
            doDataAnalysis();
        ***REMOVED***

        doJvmOptionsAnalysis();
    ***REMOVED***

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // TODO:
    ***REMOVED***

    /**
     * @return true if there is data, false otherwise (e.g. no fatal error log lines recognized).
     */
    public boolean haveData() {
        boolean haveData = true;
        // TODO:
        return haveData;
    ***REMOVED***

    /**
     * Do JVM options analysis.
     */
    private void doJvmOptionsAnalysis() {

        // TODO:
    ***REMOVED***
***REMOVED***

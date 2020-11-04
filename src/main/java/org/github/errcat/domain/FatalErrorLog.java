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
import org.github.errcat.util.jdk.Analysis;

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
    private JvmInfoEvent jvminfo;

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
    ***REMOVED***

    public JvmInfoEvent getJvminfo() {
        return jvminfo;
    ***REMOVED***

    public void setJvminfo(JvmInfoEvent jvminfo) {
        this.jvminfo = jvminfo;
    ***REMOVED***

    public OsEvent getOs() {
        return os;
    ***REMOVED***

    public void setOs(OsEvent os) {
        this.os = os;
    ***REMOVED***

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
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

    /**
     * Do analysis.
     */
    public void doAnalysis() {

        // Unidentified logging lines
        if (getUnidentifiedLogLines().size() > 0) {
            analysis.add(0, Analysis.WARN_UNIDENTIFIED_LOG_LINE_REPORT);
        ***REMOVED***

        // TODO:
        if (haveData()) {
            doDataAnalysis();
        ***REMOVED***
    ***REMOVED***

    /**
     * Do data analysis.
     */
    private void doDataAnalysis() {
        // Check for partial log
        /*
         * if (this.firstSafepointEvent != null && VmUtil.isPartialLog(firstSafepointEvent.getTimestamp())) {
         * analysis.add(Analysis.INFO_FIRST_TIMESTAMP_THRESHOLD_EXCEEDED); ***REMOVED***
         */
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

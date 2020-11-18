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
package org.github.errcat.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.github.errcat.Main;
import org.github.errcat.domain.LogEvent;
import org.github.errcat.domain.UnknownEvent;
import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.domain.jdk.StackEvent;
import org.github.errcat.domain.jdk.UnameEvent;
import org.github.errcat.domain.jdk.VmInfoEvent;
import org.github.errcat.util.jdk.JdkUtil;

/**
 * <p>
 * Provides fatal error log analysis services to other layers.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Manager {

    /**
     * The fatal error log object.
     */
    private FatalErrorLog fatalErrorLog;

    /**
     * Default constructor.
     */
    public Manager() {
        this.fatalErrorLog = new FatalErrorLog();
    ***REMOVED***

    /**
     * Parse the fatal error log.
     * 
     * @param logFile
     * @return The fatal error log object.
     */
    public FatalErrorLog parse(File logFile) {
        if (logFile != null) {

            // Parse vm log file
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(logFile));
                String logLine = bufferedReader.readLine();
                while (logLine != null) {
                    LogEvent event = JdkUtil.parseLogLine(logLine);
                    // ThrowAwayEvents are ignored
                    if (event instanceof VmInfoEvent) {
                        fatalErrorLog.setVminfo((VmInfoEvent) event);
                    ***REMOVED*** else if (event instanceof HeaderEvent) {
                        fatalErrorLog.getHeader().add((HeaderEvent) event);
                    ***REMOVED*** else if (event instanceof StackEvent) {
                        fatalErrorLog.getStack().add((StackEvent) event);
                    ***REMOVED*** else if (event instanceof OsEvent) {
                        fatalErrorLog.setOs((OsEvent) event);
                    ***REMOVED*** else if (event instanceof UnameEvent) {
                        fatalErrorLog.setUname((UnameEvent) event);
                    ***REMOVED*** else if (event instanceof UnknownEvent) {
                        if (fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                            fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                        ***REMOVED***
                    ***REMOVED***
                    logLine = bufferedReader.readLine();
                ***REMOVED***
            ***REMOVED*** catch (FileNotFoundException e) {
                e.printStackTrace();
            ***REMOVED*** catch (IOException e) {
                e.printStackTrace();
            ***REMOVED*** finally {
                // Close streams
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    ***REMOVED*** catch (IOException e) {
                        e.printStackTrace();
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        fatalErrorLog.doAnalysis();
        return fatalErrorLog;
    ***REMOVED***
***REMOVED***
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
import org.github.errcat.domain.ThrowAwayEvent;
import org.github.errcat.domain.UnknownEvent;
import org.github.errcat.domain.jdk.CommandLineEvent;
import org.github.errcat.domain.jdk.CompilationEvent;
import org.github.errcat.domain.jdk.CpuInfoEvent;
import org.github.errcat.domain.jdk.CurrentCompileTaskEvent;
import org.github.errcat.domain.jdk.CurrentThreadEvent;
import org.github.errcat.domain.jdk.DeoptimizationEvent;
import org.github.errcat.domain.jdk.DynamicLibraryEvent;
import org.github.errcat.domain.jdk.ElapsedTimeEvent;
import org.github.errcat.domain.jdk.ExceptionCountsEvent;
import org.github.errcat.domain.jdk.FatalErrorLog;
import org.github.errcat.domain.jdk.HeaderEvent;
import org.github.errcat.domain.jdk.HeapAddressEvent;
import org.github.errcat.domain.jdk.HeapEvent;
import org.github.errcat.domain.jdk.MeminfoEvent;
import org.github.errcat.domain.jdk.MemoryEvent;
import org.github.errcat.domain.jdk.NativeMemoryTrackingEvent;
import org.github.errcat.domain.jdk.OsEvent;
import org.github.errcat.domain.jdk.SigInfoEvent;
import org.github.errcat.domain.jdk.StackEvent;
import org.github.errcat.domain.jdk.ThreadEvent;
import org.github.errcat.domain.jdk.TimeElapsedTimeEvent;
import org.github.errcat.domain.jdk.TimeEvent;
import org.github.errcat.domain.jdk.TimezoneEvent;
import org.github.errcat.domain.jdk.UnameEvent;
import org.github.errcat.domain.jdk.VmArgumentsEvent;
import org.github.errcat.domain.jdk.VmEvent;
import org.github.errcat.domain.jdk.VmInfoEvent;
import org.github.errcat.domain.jdk.VmStateEvent;
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
     *            The fatal error log to parse.
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
                    if (event instanceof CommandLineEvent) {
                        fatalErrorLog.setCommandLineEvent((CommandLineEvent) event);
                        ;
                    ***REMOVED*** else if (event instanceof CompilationEvent) {
                        fatalErrorLog.getCompilationEvents().add((CompilationEvent) event);
                    ***REMOVED*** else if (event instanceof CpuInfoEvent) {
                        fatalErrorLog.getCpuInfoEvents().add((CpuInfoEvent) event);
                    ***REMOVED*** else if (event instanceof CurrentCompileTaskEvent) {
                        fatalErrorLog.getCurrentCompileTaskEvents().add((CurrentCompileTaskEvent) event);
                    ***REMOVED*** else if (event instanceof CurrentThreadEvent) {
                        fatalErrorLog.setCurrentThreadEvent((CurrentThreadEvent) event);
                    ***REMOVED*** else if (event instanceof DeoptimizationEvent) {
                        fatalErrorLog.getDeoptimizationEvents().add((DeoptimizationEvent) event);
                    ***REMOVED*** else if (event instanceof DynamicLibraryEvent) {
                        fatalErrorLog.getDynamicLibraryEvents().add((DynamicLibraryEvent) event);
                    ***REMOVED*** else if (event instanceof ElapsedTimeEvent) {
                        fatalErrorLog.setElapsedTimeEvent((ElapsedTimeEvent) event);
                    ***REMOVED*** else if (event instanceof ExceptionCountsEvent) {
                        fatalErrorLog.getExceptionCountsEvents().add((ExceptionCountsEvent) event);
                    ***REMOVED*** else if (event instanceof HeaderEvent) {
                        fatalErrorLog.getHeaderEvents().add((HeaderEvent) event);
                    ***REMOVED*** else if (event instanceof HeapAddressEvent) {
                        fatalErrorLog.getHeapAddressEvents().add((HeapAddressEvent) event);
                    ***REMOVED*** else if (event instanceof HeapEvent) {
                        fatalErrorLog.getHeapEvents().add((HeapEvent) event);
                    ***REMOVED*** else if (event instanceof MeminfoEvent) {
                        fatalErrorLog.getMeminfoEvents().add((MeminfoEvent) event);
                    ***REMOVED*** else if (event instanceof MemoryEvent) {
                        fatalErrorLog.getMemoryEvents().add((MemoryEvent) event);
                    ***REMOVED*** else if (event instanceof NativeMemoryTrackingEvent) {
                        fatalErrorLog.getNativeMemoryTrackingEvents().add((NativeMemoryTrackingEvent) event);
                    ***REMOVED*** else if (event instanceof OsEvent) {
                        fatalErrorLog.getOsEvents().add((OsEvent) event);
                    ***REMOVED*** else if (event instanceof SigInfoEvent) {
                        fatalErrorLog.setSigInfoEvent((SigInfoEvent) event);
                    ***REMOVED*** else if (event instanceof StackEvent) {
                        fatalErrorLog.getStackEvents().add((StackEvent) event);
                    ***REMOVED*** else if (event instanceof ThreadEvent) {
                        fatalErrorLog.getThreadEvents().add((ThreadEvent) event);
                    ***REMOVED*** else if (event instanceof ThrowAwayEvent) {
                        // ThrowAwayEvents are ignored
                    ***REMOVED*** else if (event instanceof TimeEvent) {
                        fatalErrorLog.setTimeEvent((TimeEvent) event);
                    ***REMOVED*** else if (event instanceof TimeElapsedTimeEvent) {
                        fatalErrorLog.setTimeElapsedTimeEvent((TimeElapsedTimeEvent) event);
                    ***REMOVED*** else if (event instanceof TimezoneEvent) {
                        fatalErrorLog.setTimezoneEvent((TimezoneEvent) event);
                    ***REMOVED*** else if (event instanceof UnameEvent) {
                        fatalErrorLog.setUnameEvent((UnameEvent) event);
                    ***REMOVED*** else if (event instanceof UnknownEvent) {
                        if (fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                            fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                        ***REMOVED***
                    ***REMOVED*** else if (event instanceof VmArgumentsEvent) {
                        fatalErrorLog.getVmArgumentsEvents().add((VmArgumentsEvent) event);
                    ***REMOVED*** else if (event instanceof VmEvent) {
                        fatalErrorLog.getVmEvents().add((VmEvent) event);
                    ***REMOVED*** else if (event instanceof VmInfoEvent) {
                        fatalErrorLog.setVmInfoEvent((VmInfoEvent) event);
                    ***REMOVED*** else if (event instanceof VmStateEvent) {
                        fatalErrorLog.setVmStateEvent((VmStateEvent) event);
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
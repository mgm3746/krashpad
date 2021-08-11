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
package org.github.krashpad.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.github.krashpad.Main;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.domain.UnknownEvent;
import org.github.krashpad.domain.jdk.CommandLineEvent;
import org.github.krashpad.domain.jdk.CompilationEvent;
import org.github.krashpad.domain.jdk.ContainerInfoEvent;
import org.github.krashpad.domain.jdk.CpuInfoEvent;
import org.github.krashpad.domain.jdk.CurrentCompileTaskEvent;
import org.github.krashpad.domain.jdk.CurrentThreadEvent;
import org.github.krashpad.domain.jdk.DeoptimizationEvent;
import org.github.krashpad.domain.jdk.DynamicLibraryEvent;
import org.github.krashpad.domain.jdk.ElapsedTimeEvent;
import org.github.krashpad.domain.jdk.EnvironmentVariablesEvent;
import org.github.krashpad.domain.jdk.ExceptionCountsEvent;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.GlobalFlagsEvent;
import org.github.krashpad.domain.jdk.HeaderEvent;
import org.github.krashpad.domain.jdk.HeapAddressEvent;
import org.github.krashpad.domain.jdk.HeapEvent;
import org.github.krashpad.domain.jdk.MeminfoEvent;
import org.github.krashpad.domain.jdk.MemoryEvent;
import org.github.krashpad.domain.jdk.NativeMemoryTrackingEvent;
import org.github.krashpad.domain.jdk.OsEvent;
import org.github.krashpad.domain.jdk.RlimitEvent;
import org.github.krashpad.domain.jdk.SigInfoEvent;
import org.github.krashpad.domain.jdk.StackEvent;
import org.github.krashpad.domain.jdk.ThreadEvent;
import org.github.krashpad.domain.jdk.TimeElapsedTimeEvent;
import org.github.krashpad.domain.jdk.TimeEvent;
import org.github.krashpad.domain.jdk.TimezoneEvent;
import org.github.krashpad.domain.jdk.UnameEvent;
import org.github.krashpad.domain.jdk.VmArgumentsEvent;
import org.github.krashpad.domain.jdk.VmEvent;
import org.github.krashpad.domain.jdk.VmInfoEvent;
import org.github.krashpad.domain.jdk.VmStateEvent;
import org.github.krashpad.util.jdk.JdkUtil;

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
                LogEvent priorEvent = null;
                while (logLine != null) {
                    LogEvent event = JdkUtil.parseLogLine(logLine, priorEvent);
                    if (event instanceof CommandLineEvent) {
                        fatalErrorLog.setCommandLineEvent((CommandLineEvent) event);
                    ***REMOVED*** else if (event instanceof CompilationEvent) {
                        fatalErrorLog.getCompilationEvents().add((CompilationEvent) event);
                    ***REMOVED*** else if (event instanceof ContainerInfoEvent) {
                        fatalErrorLog.getContainerInfoEvents().add((ContainerInfoEvent) event);
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
                    ***REMOVED*** else if (event instanceof EnvironmentVariablesEvent) {
                        fatalErrorLog.getEnvironmentVariablesEvents().add((EnvironmentVariablesEvent) event);
                    ***REMOVED*** else if (event instanceof ElapsedTimeEvent) {
                        fatalErrorLog.setElapsedTimeEvent((ElapsedTimeEvent) event);
                    ***REMOVED*** else if (event instanceof ExceptionCountsEvent) {
                        fatalErrorLog.getExceptionCountsEvents().add((ExceptionCountsEvent) event);
                    ***REMOVED*** else if (event instanceof GlobalFlagsEvent) {
                        fatalErrorLog.getGlobalFlagsEvents().add((GlobalFlagsEvent) event);
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
                    ***REMOVED*** else if (event instanceof RlimitEvent) {
                        fatalErrorLog.setRlimitEvent((RlimitEvent) event);
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
                    ***REMOVED*** else if (event instanceof UnknownEvent
                            && fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                        fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                    ***REMOVED*** else if (event instanceof VmArgumentsEvent) {
                        fatalErrorLog.getVmArgumentsEvents().add((VmArgumentsEvent) event);
                    ***REMOVED*** else if (event instanceof VmEvent) {
                        fatalErrorLog.getVmEvents().add((VmEvent) event);
                    ***REMOVED*** else if (event instanceof VmInfoEvent) {
                        fatalErrorLog.setVmInfoEvent((VmInfoEvent) event);
                    ***REMOVED*** else if (event instanceof VmStateEvent) {
                        fatalErrorLog.setVmStateEvent((VmStateEvent) event);
                    ***REMOVED***
                    priorEvent = event;
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
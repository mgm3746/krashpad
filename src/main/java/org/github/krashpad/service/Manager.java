/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2023 Mike Millson                                                                               *
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
import org.github.krashpad.domain.BlankLine;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.domain.ThrowAwayEvent;
import org.github.krashpad.domain.UnknownEvent;
import org.github.krashpad.domain.jdk.ClassesUnloadedEvent;
import org.github.krashpad.domain.jdk.CommandLine;
import org.github.krashpad.domain.jdk.CompilationEvent;
import org.github.krashpad.domain.jdk.CompressedClassSpace;
import org.github.krashpad.domain.jdk.ContainerInfo;
import org.github.krashpad.domain.jdk.CpuInfo;
import org.github.krashpad.domain.jdk.CurrentCompileTask;
import org.github.krashpad.domain.jdk.CurrentThread;
import org.github.krashpad.domain.jdk.DeoptimizationEvent;
import org.github.krashpad.domain.jdk.DllOperationEvent;
import org.github.krashpad.domain.jdk.DynamicLibrary;
import org.github.krashpad.domain.jdk.ElapsedTime;
import org.github.krashpad.domain.jdk.EnvironmentVariables;
import org.github.krashpad.domain.jdk.Event;
import org.github.krashpad.domain.jdk.ExceptionCounts;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.GcHeapHistoryEvent;
import org.github.krashpad.domain.jdk.GcPreciousLog;
import org.github.krashpad.domain.jdk.GlobalFlags;
import org.github.krashpad.domain.jdk.Header;
import org.github.krashpad.domain.jdk.Heap;
import org.github.krashpad.domain.jdk.HeapAddress;
import org.github.krashpad.domain.jdk.Host;
import org.github.krashpad.domain.jdk.InternalExceptionEvent;
import org.github.krashpad.domain.jdk.InternalStatistic;
import org.github.krashpad.domain.jdk.LdPreloadFile;
import org.github.krashpad.domain.jdk.MaxMapCount;
import org.github.krashpad.domain.jdk.Meminfo;
import org.github.krashpad.domain.jdk.Memory;
import org.github.krashpad.domain.jdk.NarrowKlass;
import org.github.krashpad.domain.jdk.NativeMemoryTracking;
import org.github.krashpad.domain.jdk.NumberEvent;
import org.github.krashpad.domain.jdk.OsInfo;
import org.github.krashpad.domain.jdk.PidMax;
import org.github.krashpad.domain.jdk.RegisterToMemoryMapping;
import org.github.krashpad.domain.jdk.RlimitEvent;
import org.github.krashpad.domain.jdk.SigInfo;
import org.github.krashpad.domain.jdk.Stack;
import org.github.krashpad.domain.jdk.StackSlotToMemoryMapping;
import org.github.krashpad.domain.jdk.Thread;
import org.github.krashpad.domain.jdk.ThreadsMax;
import org.github.krashpad.domain.jdk.Time;
import org.github.krashpad.domain.jdk.TimeElapsedTime;
import org.github.krashpad.domain.jdk.Timezone;
import org.github.krashpad.domain.jdk.Uname;
import org.github.krashpad.domain.jdk.VirtualizationInfo;
import org.github.krashpad.domain.jdk.VmArguments;
import org.github.krashpad.domain.jdk.VmInfo;
import org.github.krashpad.domain.jdk.VmOperation;
import org.github.krashpad.domain.jdk.VmState;
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
                    if (event instanceof ClassesUnloadedEvent) {
                        fatalErrorLog.getClassesUnloadedEvents().add((ClassesUnloadedEvent) event);
                    ***REMOVED*** else if (event instanceof CommandLine) {
                        fatalErrorLog.setCommandLineEvent((CommandLine) event);
                    ***REMOVED*** else if (event instanceof CompilationEvent) {
                        fatalErrorLog.getCompilationEvents().add((CompilationEvent) event);
                    ***REMOVED*** else if (event instanceof CompressedClassSpace) {
                        fatalErrorLog.setCompressedClassSpaceEvent((CompressedClassSpace) event);
                    ***REMOVED*** else if (event instanceof ContainerInfo) {
                        fatalErrorLog.getContainerInfoEvents().add((ContainerInfo) event);
                    ***REMOVED*** else if (event instanceof CpuInfo) {
                        fatalErrorLog.getCpuInfoEvents().add((CpuInfo) event);
                    ***REMOVED*** else if (event instanceof CurrentCompileTask) {
                        fatalErrorLog.getCurrentCompileTaskEvents().add((CurrentCompileTask) event);
                    ***REMOVED*** else if (event instanceof CurrentThread) {
                        fatalErrorLog.setCurrentThreadEvent((CurrentThread) event);
                    ***REMOVED*** else if (event instanceof DeoptimizationEvent) {
                        fatalErrorLog.getDeoptimizationEvents().add((DeoptimizationEvent) event);
                    ***REMOVED*** else if (event instanceof DllOperationEvent) {
                        fatalErrorLog.getDllOperationEvents().add((DllOperationEvent) event);
                    ***REMOVED*** else if (event instanceof DynamicLibrary) {
                        fatalErrorLog.getDynamicLibraryEvents().add((DynamicLibrary) event);
                    ***REMOVED*** else if (event instanceof EnvironmentVariables) {
                        fatalErrorLog.getEnvironmentVariablesEvents().add((EnvironmentVariables) event);
                    ***REMOVED*** else if (event instanceof ElapsedTime) {
                        fatalErrorLog.setElapsedTimeEvent((ElapsedTime) event);
                    ***REMOVED*** else if (event instanceof Event) {
                        fatalErrorLog.getEventEvents().add((Event) event);
                    ***REMOVED*** else if (event instanceof ExceptionCounts) {
                        fatalErrorLog.getExceptionCountsEvents().add((ExceptionCounts) event);
                    ***REMOVED*** else if (event instanceof GcHeapHistoryEvent) {
                        fatalErrorLog.getGcHeapHistoryEvents().add((GcHeapHistoryEvent) event);
                    ***REMOVED*** else if (event instanceof GcPreciousLog) {
                        fatalErrorLog.getGcPreciousLogEvents().add((GcPreciousLog) event);
                    ***REMOVED*** else if (event instanceof GlobalFlags) {
                        fatalErrorLog.getGlobalFlagsEvents().add((GlobalFlags) event);
                    ***REMOVED*** else if (event instanceof Header) {
                        fatalErrorLog.getHeaderEvents().add((Header) event);
                    ***REMOVED*** else if (event instanceof HeapAddress) {
                        fatalErrorLog.setHeapAddressEvent((HeapAddress) event);
                    ***REMOVED*** else if (event instanceof Heap) {
                        fatalErrorLog.getHeapEvents().add((Heap) event);
                    ***REMOVED*** else if (event instanceof LdPreloadFile) {
                        fatalErrorLog.getLdPreloadFileEvents().add((LdPreloadFile) event);
                    ***REMOVED*** else if (event instanceof Host) {
                        fatalErrorLog.setHostEvent((Host) event);
                    ***REMOVED*** else if (event instanceof MaxMapCount) {
                        fatalErrorLog.setMaxMapCountEvent((MaxMapCount) event);
                    ***REMOVED*** else if (event instanceof Meminfo) {
                        fatalErrorLog.getMeminfoEvents().add((Meminfo) event);
                    ***REMOVED*** else if (event instanceof Memory) {
                        fatalErrorLog.getMemoryEvents().add((Memory) event);
                    ***REMOVED*** else if (event instanceof NativeMemoryTracking) {
                        fatalErrorLog.getNativeMemoryTrackingEvents().add((NativeMemoryTracking) event);
                    ***REMOVED*** else if (event instanceof NarrowKlass) {
                        fatalErrorLog.setNarrowKlassEvent((NarrowKlass) event);
                    ***REMOVED*** else if (event instanceof NumberEvent) {
                        // Add number to prior event
                        String combinedLogLine = priorEvent.getLogEntry() + " " + event.getLogEntry();
                        if (priorEvent instanceof CpuInfo) {
                            fatalErrorLog.getCpuInfoEvents().remove(priorEvent);
                            CpuInfo combinedCpuInfoEvent = new CpuInfo(combinedLogLine);
                            fatalErrorLog.getCpuInfoEvents().add(combinedCpuInfoEvent);
                        ***REMOVED*** else if (priorEvent instanceof MaxMapCount) {
                            fatalErrorLog.setMaxMapCountEvent(new MaxMapCount(combinedLogLine));
                        ***REMOVED*** else if (priorEvent instanceof PidMax) {
                            fatalErrorLog.setPidMaxEvent(new PidMax(combinedLogLine));
                        ***REMOVED*** else if (priorEvent instanceof ThreadsMax) {
                            fatalErrorLog.setThreadsMaxEvent(new ThreadsMax(combinedLogLine));
                        ***REMOVED*** else if (fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                            // catch for future handling
                            fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                        ***REMOVED***
                    ***REMOVED*** else if (event instanceof OsInfo) {
                        fatalErrorLog.getOsEvents().add((OsInfo) event);
                    ***REMOVED*** else if (event instanceof PidMax) {
                        fatalErrorLog.setPidMaxEvent((PidMax) event);
                    ***REMOVED*** else if (event instanceof RegisterToMemoryMapping) {
                        fatalErrorLog.getRegisterToMemoryMappingEvents().add((RegisterToMemoryMapping) event);
                    ***REMOVED*** else if (event instanceof RlimitEvent) {
                        fatalErrorLog.setRlimitEvent((RlimitEvent) event);
                    ***REMOVED*** else if (event instanceof SigInfo) {
                        fatalErrorLog.setSigInfoEvent((SigInfo) event);
                    ***REMOVED*** else if (event instanceof Stack) {
                        fatalErrorLog.getStackEvents().add((Stack) event);
                    ***REMOVED*** else if (event instanceof StackSlotToMemoryMapping) {
                        fatalErrorLog.getStackSlotToMemoryMappingEvents().add((StackSlotToMemoryMapping) event);
                    ***REMOVED*** else if (event instanceof InternalExceptionEvent) {
                        fatalErrorLog.getInternalExceptionEvents().add((InternalExceptionEvent) event);
                    ***REMOVED*** else if (event instanceof InternalStatistic) {
                        fatalErrorLog.getInternalStatisticEvents().add((InternalStatistic) event);
                    ***REMOVED*** else if (event instanceof Thread) {
                        fatalErrorLog.getThreadEvents().add((Thread) event);
                    ***REMOVED*** else if (event instanceof ThreadsMax) {
                        fatalErrorLog.setThreadsMaxEvent((ThreadsMax) event);
                    ***REMOVED*** else if (event instanceof ThrowAwayEvent) {
                        // ThrowAwayEvents are ignored
                    ***REMOVED*** else if (event instanceof Time) {
                        fatalErrorLog.setTimeEvent((Time) event);
                    ***REMOVED*** else if (event instanceof TimeElapsedTime) {
                        fatalErrorLog.setTimeElapsedTimeEvent((TimeElapsedTime) event);
                    ***REMOVED*** else if (event instanceof Timezone) {
                        fatalErrorLog.setTimezoneEvent((Timezone) event);
                    ***REMOVED*** else if (event instanceof Uname) {
                        // some uname information is split across 2 lines
                        if (fatalErrorLog.getUnameEvent() == null) {
                            fatalErrorLog.setUnameEvent((Uname) event);
                        ***REMOVED*** else {
                            Uname unameEvent = new Uname(
                                    fatalErrorLog.getUnameEvent().getLogEntry() + ((Uname) event).getLogEntry());
                            fatalErrorLog.setUnameEvent(unameEvent);
                        ***REMOVED***
                    ***REMOVED*** else if (event instanceof UnknownEvent
                            && fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                        fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                    ***REMOVED*** else if (event instanceof VirtualizationInfo) {
                        fatalErrorLog.getVirtualizationInfoEvents().add((VirtualizationInfo) event);
                    ***REMOVED*** else if (event instanceof VmArguments) {
                        fatalErrorLog.getVmArgumentsEvents().add((VmArguments) event);
                    ***REMOVED*** else if (event instanceof VmInfo) {
                        fatalErrorLog.setVmInfoEvent((VmInfo) event);
                    ***REMOVED*** else if (event instanceof VmOperation) {
                        fatalErrorLog.setVmOperationEvent((VmOperation) event);
                    ***REMOVED*** else if (event instanceof VmState) {
                        fatalErrorLog.setVmStateEvent((VmState) event);
                    ***REMOVED***
                    if (!(event instanceof BlankLine)) {
                        // throw away blank lines
                        priorEvent = event;
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
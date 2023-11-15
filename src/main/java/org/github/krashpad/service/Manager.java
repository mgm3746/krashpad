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
import org.github.krashpad.domain.jdk.End;
import org.github.krashpad.domain.jdk.EnvironmentVariable;
import org.github.krashpad.domain.jdk.Event;
import org.github.krashpad.domain.jdk.ExceptionCounts;
import org.github.krashpad.domain.jdk.FatalErrorLog;
import org.github.krashpad.domain.jdk.GcHeapHistoryEvent;
import org.github.krashpad.domain.jdk.GcPreciousLog;
import org.github.krashpad.domain.jdk.GlobalFlag;
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
import org.github.krashpad.domain.jdk.PeriodicNativeTrim;
import org.github.krashpad.domain.jdk.PidMax;
import org.github.krashpad.domain.jdk.RegisterToMemoryMapping;
import org.github.krashpad.domain.jdk.Rlimit;
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
import org.github.krashpad.domain.jdk.ZgcPhaseSwitchEvent;
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
    }

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
                    } else if (event instanceof CommandLine) {
                        fatalErrorLog.setCommandLine((CommandLine) event);
                    } else if (event instanceof CompilationEvent) {
                        fatalErrorLog.getCompilationEvents().add((CompilationEvent) event);
                    } else if (event instanceof CompressedClassSpace) {
                        fatalErrorLog.setCompressedClassSpaceEvent((CompressedClassSpace) event);
                    } else if (event instanceof ContainerInfo) {
                        fatalErrorLog.getContainerInfos().add((ContainerInfo) event);
                    } else if (event instanceof CpuInfo) {
                        fatalErrorLog.getCpuInfos().add((CpuInfo) event);
                    } else if (event instanceof CurrentCompileTask) {
                        fatalErrorLog.getCurrentCompileTasks().add((CurrentCompileTask) event);
                    } else if (event instanceof CurrentThread) {
                        fatalErrorLog.setCurrentThread((CurrentThread) event);
                    } else if (event instanceof DeoptimizationEvent) {
                        fatalErrorLog.getDeoptimizationEvents().add((DeoptimizationEvent) event);
                    } else if (event instanceof DllOperationEvent) {
                        fatalErrorLog.getDllOperationEvents().add((DllOperationEvent) event);
                    } else if (event instanceof DynamicLibrary) {
                        fatalErrorLog.getDynamicLibraries().add((DynamicLibrary) event);
                    } else if (event instanceof EnvironmentVariable) {
                        fatalErrorLog.getEnvironmentVariables().add((EnvironmentVariable) event);
                    } else if (event instanceof ElapsedTime) {
                        fatalErrorLog.setElapsedTime((ElapsedTime) event);
                    } else if (event instanceof End) {
                        fatalErrorLog.setEnd((End) event);
                    } else if (event instanceof Event) {
                        fatalErrorLog.getEvents().add((Event) event);
                    } else if (event instanceof ExceptionCounts) {
                        fatalErrorLog.getExceptionCounts().add((ExceptionCounts) event);
                    } else if (event instanceof GcHeapHistoryEvent) {
                        fatalErrorLog.getGcHeapHistoryEvents().add((GcHeapHistoryEvent) event);
                    } else if (event instanceof GcPreciousLog) {
                        fatalErrorLog.getGcPreciousLogs().add((GcPreciousLog) event);
                    } else if (event instanceof GlobalFlag) {
                        fatalErrorLog.getGlobalFlags().add((GlobalFlag) event);
                    } else if (event instanceof Header) {
                        fatalErrorLog.getHeaders().add((Header) event);
                    } else if (event instanceof HeapAddress) {
                        fatalErrorLog.setHeapAddress((HeapAddress) event);
                    } else if (event instanceof Heap) {
                        fatalErrorLog.getHeaps().add((Heap) event);
                    } else if (event instanceof LdPreloadFile) {
                        fatalErrorLog.getLdPreloadFiles().add((LdPreloadFile) event);
                    } else if (event instanceof Host) {
                        fatalErrorLog.setHost((Host) event);
                    } else if (event instanceof MaxMapCount) {
                        fatalErrorLog.setMaxMapCount((MaxMapCount) event);
                    } else if (event instanceof Meminfo) {
                        fatalErrorLog.getMeminfos().add((Meminfo) event);
                    } else if (event instanceof Memory) {
                        fatalErrorLog.getMemories().add((Memory) event);
                    } else if (event instanceof NativeMemoryTracking) {
                        fatalErrorLog.getNativeMemoryTrackings().add((NativeMemoryTracking) event);
                    } else if (event instanceof NarrowKlass) {
                        fatalErrorLog.setNarrowKlass((NarrowKlass) event);
                    } else if (event instanceof NumberEvent) {
                        // Add number to prior event
                        String combinedLogLine = priorEvent.getLogEntry() + " " + event.getLogEntry();
                        if (priorEvent instanceof CpuInfo) {
                            fatalErrorLog.getCpuInfos().remove(priorEvent);
                            CpuInfo combinedCpuInfoEvent = new CpuInfo(combinedLogLine);
                            fatalErrorLog.getCpuInfos().add(combinedCpuInfoEvent);
                        } else if (priorEvent instanceof MaxMapCount) {
                            fatalErrorLog.setMaxMapCount(new MaxMapCount(combinedLogLine));
                        } else if (priorEvent instanceof PidMax) {
                            fatalErrorLog.setPidMax(new PidMax(combinedLogLine));
                        } else if (priorEvent instanceof ThreadsMax) {
                            fatalErrorLog.setThreadsMax(new ThreadsMax(combinedLogLine));
                        } else if (fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                            // catch for future handling
                            fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                        }
                    } else if (event instanceof OsInfo) {
                        fatalErrorLog.getOsInfos().add((OsInfo) event);
                    } else if (event instanceof PeriodicNativeTrim) {
                        fatalErrorLog.setPeriodicNativeTrim((PeriodicNativeTrim) event);
                    } else if (event instanceof PidMax) {
                        fatalErrorLog.setPidMax((PidMax) event);
                    } else if (event instanceof RegisterToMemoryMapping) {
                        fatalErrorLog.getRegisterToMemoryMappings().add((RegisterToMemoryMapping) event);
                    } else if (event instanceof Rlimit) {
                        fatalErrorLog.setRlimit((Rlimit) event);
                    } else if (event instanceof SigInfo) {
                        fatalErrorLog.setSigInfo((SigInfo) event);
                    } else if (event instanceof Stack) {
                        fatalErrorLog.getStacks().add((Stack) event);
                    } else if (event instanceof StackSlotToMemoryMapping) {
                        fatalErrorLog.getStackSlotToMemoryMappings().add((StackSlotToMemoryMapping) event);
                    } else if (event instanceof InternalExceptionEvent) {
                        fatalErrorLog.getInternalExceptionEvents().add((InternalExceptionEvent) event);
                    } else if (event instanceof InternalStatistic) {
                        fatalErrorLog.getInternalStatistics().add((InternalStatistic) event);
                    } else if (event instanceof Thread) {
                        fatalErrorLog.getThreads().add((Thread) event);
                    } else if (event instanceof ThreadsMax) {
                        fatalErrorLog.setThreadsMax((ThreadsMax) event);
                    } else if (event instanceof ThrowAwayEvent) {
                        // ThrowAwayEvents are ignored
                    } else if (event instanceof Time) {
                        fatalErrorLog.setTime((Time) event);
                    } else if (event instanceof TimeElapsedTime) {
                        fatalErrorLog.setTimeElapsedTime((TimeElapsedTime) event);
                    } else if (event instanceof Timezone) {
                        fatalErrorLog.setTimezone((Timezone) event);
                    } else if (event instanceof Uname) {
                        // some uname information is split across 2 lines
                        if (fatalErrorLog.getUname() == null) {
                            fatalErrorLog.setUname((Uname) event);
                        } else {
                            Uname unameEvent = new Uname(
                                    fatalErrorLog.getUname().getLogEntry() + ((Uname) event).getLogEntry());
                            fatalErrorLog.setUname(unameEvent);
                        }
                    } else if (event instanceof UnknownEvent
                            && fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                        fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                    } else if (event instanceof VirtualizationInfo) {
                        fatalErrorLog.getVirtualizationInfos().add((VirtualizationInfo) event);
                    } else if (event instanceof VmArguments) {
                        fatalErrorLog.getVmArguments().add((VmArguments) event);
                    } else if (event instanceof VmInfo) {
                        fatalErrorLog.setVmInfo((VmInfo) event);
                    } else if (event instanceof VmOperation) {
                        fatalErrorLog.setVmOperation((VmOperation) event);
                    } else if (event instanceof VmState) {
                        fatalErrorLog.setVmState((VmState) event);
                    } else if (event instanceof ZgcPhaseSwitchEvent) {
                        fatalErrorLog.getZgcPhaseSwitchEvents().add((ZgcPhaseSwitchEvent) event);
                    }
                    if (!(event instanceof BlankLine)) {
                        // throw away blank lines
                        priorEvent = event;
                    }
                    logLine = bufferedReader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Close streams
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        fatalErrorLog.doProcessing();
        fatalErrorLog.doAnalysis();
        return fatalErrorLog;
    }
}
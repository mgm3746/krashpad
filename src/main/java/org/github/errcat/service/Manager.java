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
                while (logLine != null) {
                    LogEvent event = JdkUtil.parseLogLine(logLine);
                    if (event instanceof CommandLineEvent) {
                        fatalErrorLog.setCommandLineEvent((CommandLineEvent) event);
                        ;
                    } else if (event instanceof CompilationEvent) {
                        fatalErrorLog.getCompilationEvents().add((CompilationEvent) event);
                    } else if (event instanceof CpuInfoEvent) {
                        fatalErrorLog.getCpuInfoEvents().add((CpuInfoEvent) event);
                    } else if (event instanceof CurrentCompileTaskEvent) {
                        fatalErrorLog.getCurrentCompileTaskEvents().add((CurrentCompileTaskEvent) event);
                    } else if (event instanceof CurrentThreadEvent) {
                        fatalErrorLog.setCurrentThreadEvent((CurrentThreadEvent) event);
                    } else if (event instanceof DeoptimizationEvent) {
                        fatalErrorLog.getDeoptimizationEvents().add((DeoptimizationEvent) event);
                    } else if (event instanceof DynamicLibraryEvent) {
                        fatalErrorLog.getDynamicLibraryEvents().add((DynamicLibraryEvent) event);
                    } else if (event instanceof ElapsedTimeEvent) {
                        fatalErrorLog.setElapsedTimeEvent((ElapsedTimeEvent) event);
                    } else if (event instanceof ExceptionCountsEvent) {
                        fatalErrorLog.getExceptionCountsEvents().add((ExceptionCountsEvent) event);
                    } else if (event instanceof HeaderEvent) {
                        fatalErrorLog.getHeaderEvents().add((HeaderEvent) event);
                    } else if (event instanceof HeapAddressEvent) {
                        fatalErrorLog.getHeapAddressEvents().add((HeapAddressEvent) event);
                    } else if (event instanceof HeapEvent) {
                        fatalErrorLog.getHeapEvents().add((HeapEvent) event);
                    } else if (event instanceof MeminfoEvent) {
                        fatalErrorLog.getMeminfoEvents().add((MeminfoEvent) event);
                    } else if (event instanceof MemoryEvent) {
                        fatalErrorLog.getMemoryEvents().add((MemoryEvent) event);
                    } else if (event instanceof NativeMemoryTrackingEvent) {
                        fatalErrorLog.getNativeMemoryTrackingEvents().add((NativeMemoryTrackingEvent) event);
                    } else if (event instanceof OsEvent) {
                        fatalErrorLog.getOsEvents().add((OsEvent) event);
                    } else if (event instanceof SigInfoEvent) {
                        fatalErrorLog.setSigInfoEvent((SigInfoEvent) event);
                    } else if (event instanceof StackEvent) {
                        fatalErrorLog.getStackEvents().add((StackEvent) event);
                    } else if (event instanceof ThreadEvent) {
                        fatalErrorLog.getThreadEvents().add((ThreadEvent) event);
                    } else if (event instanceof ThrowAwayEvent) {
                        // ThrowAwayEvents are ignored
                    } else if (event instanceof TimeEvent) {
                        fatalErrorLog.setTimeEvent((TimeEvent) event);
                    } else if (event instanceof TimeElapsedTimeEvent) {
                        fatalErrorLog.setTimeElapsedTimeEvent((TimeElapsedTimeEvent) event);
                    } else if (event instanceof TimezoneEvent) {
                        fatalErrorLog.setTimezoneEvent((TimezoneEvent) event);
                    } else if (event instanceof UnameEvent) {
                        fatalErrorLog.setUnameEvent((UnameEvent) event);
                    } else if (event instanceof UnknownEvent) {
                        if (fatalErrorLog.getUnidentifiedLogLines().size() < Main.REJECT_LIMIT) {
                            fatalErrorLog.getUnidentifiedLogLines().add(logLine);
                        }
                    } else if (event instanceof VmArgumentsEvent) {
                        fatalErrorLog.getVmArgumentsEvents().add((VmArgumentsEvent) event);
                    } else if (event instanceof VmEvent) {
                        fatalErrorLog.getVmEvents().add((VmEvent) event);
                    } else if (event instanceof VmInfoEvent) {
                        fatalErrorLog.setVmInfoEvent((VmInfoEvent) event);
                    } else if (event instanceof VmStateEvent) {
                        fatalErrorLog.setVmStateEvent((VmStateEvent) event);
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
        fatalErrorLog.doAnalysis();
        return fatalErrorLog;
    }
}
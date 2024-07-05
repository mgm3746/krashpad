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
package org.github.krashpad.domain.jdk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.HeaderEvent;
import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil.LogEventType;

/**
 * <p>
 * STACK
 * </p>
 * 
 * <p>
 * Stack.
 * </p>
 * 
 * <h2>Example Logging</h2>
 * 
 * <pre>
 * Stack: [0x00007fe1bc2b9000,0x00007fe1bc3b9000],  sp=0x00007fe1bc3b7bd0,  free space=1018k
 * Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
 * V  [libjvm.so+0x65a9e1]  oopDesc::size_given_klass(Klass*)+0x1
 * V  [libjvm.so+0x95f950]  ParScanThreadState::trim_queues(int)+0x160
 * V  [libjvm.so+0x9634af]  ParEvacuateFollowersClosure::do_void()+0x3f
 * V  [libjvm.so+0x95ca6c]  ParNewGenTask::work(unsigned int)+0x1bc
 * V  [libjvm.so+0xb7398a]  GangWorker::loop()+0xca
 * V  [libjvm.so+0x93a382]  java_start(Thread*)+0xf2
 * </pre>
 * 
 * <pre>
 * Stack: [0x00007fcd94e31000,0x00007fcd94f32000],  sp=0x00007fcd94f2ef28,  free space=1015k
 * Native frames: (J=compiled Java code, A=aot compiled Java code, j=interpreted, Vv=VM code, C=native code)
 * C  [libcairo.so.2+0x66e64]  cairo_region_num_rectangles+0x4
 * 
 * Java frames: (J=compiled Java code, j=interpreted, Vv=VM code)
 * J 7595  org.eclipse.swt.internal.gtk.GTK.gtk_main_do_event(J)V (0 bytes) @ 0x00007fcd7c4b2f91 [0x00007fcd7c4b2f40+0x0000000000000051]
 * J 27145 c2 org.eclipse.swt.widgets.Display.eventProc(JJ)J (212 bytes) @ 0x00007fcd7d41684c [0x00007fcd7d4167a0+0x00000000000000ac]
 * v  ~StubRoutines::call_stub
 * J 7577  org.eclipse.swt.internal.gtk.OS.g_main_context_iteration(JZ)Z (0 bytes) @ 0x00007fcd7c4ad514 [0x00007fcd7c4ad4c0+0x0000000000000054]
 * J 22407 c2 org.eclipse.swt.widgets.Display.readAndDispatch()Z (77 bytes) @ 0x00007fcd7cfba89c [0x00007fcd7cfba740+0x000000000000015c]
 * J 29763% c2 org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine$5.run()V (690 bytes) @ 0x00007fcd7d61d720 [0x00007fcd7d61d500+0x0000000000000220]
 * j  org.eclipse.core.databinding.observable.Realm.runWithDefault(Lorg/eclipse/core/databinding/observable/Realm;Ljava/lang/Runnable;)V+12
 * j  org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine.run(Lorg/eclipse/e4/ui/model/application/MApplicationElement;Lorg/eclipse/e4/core/contexts/IEclipseContext;)Ljava/lang/Object;+57
 * j  org.eclipse.e4.ui.internal.workbench.E4Workbench.createAndRunUI(Lorg/eclipse/e4/ui/model/application/MApplicationElement;)V+20
 * j  org.eclipse.ui.internal.Workbench.lambda$3(Lorg/eclipse/swt/widgets/Display;Lorg/eclipse/ui/application/WorkbenchAdvisor;[I)V+393
 * j  org.eclipse.ui.internal.Workbench$$Lambda$166.run()V+12
 * j  org.eclipse.core.databinding.observable.Realm.runWithDefault(Lorg/eclipse/core/databinding/observable/Realm;Ljava/lang/Runnable;)V+12
 * j  org.eclipse.ui.internal.Workbench.createAndRunWorkbench(Lorg/eclipse/swt/widgets/Display;Lorg/eclipse/ui/application/WorkbenchAdvisor;)I+16
 * j  org.eclipse.ui.PlatformUI.createAndRunWorkbench(Lorg/eclipse/swt/widgets/Display;Lorg/eclipse/ui/application/WorkbenchAdvisor;)I+2
 * j  org.eclipse.ui.internal.ide.application.IDEApplication.start(Lorg/eclipse/equinox/app/IApplicationContext;)Ljava/lang/Object;+113
 * j  org.eclipse.equinox.internal.app.EclipseAppHandle.run(Ljava/lang/Object;)Ljava/lang/Object;+138
 * j  org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher.runApplication(Ljava/lang/Object;)Ljava/lang/Object;+85
 * j  org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher.start(Ljava/lang/Object;)Ljava/lang/Object;+79
 * j  org.eclipse.core.runtime.adaptor.EclipseStarter.run(Ljava/lang/Object;)Ljava/lang/Object;+99
 * j  org.eclipse.core.runtime.adaptor.EclipseStarter.run([Ljava/lang/String;Ljava/lang/Runnable;)Ljava/lang/Object;+132
 * v  ~StubRoutines::call_stub
 * j  jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Ljava/lang/reflect/Method;Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+0 java.base@11.0.9
 * j  jdk.internal.reflect.NativeMethodAccessorImpl.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+100 java.base@11.0.9
 * j  jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+6 java.base@11.0.9
 * j  java.lang.reflect.Method.invoke(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;+59 java.base@11.0.9
 * j  org.eclipse.equinox.launcher.Main.invokeFramework([Ljava/lang/String;[Ljava/net/URL;)V+201
 * j  org.eclipse.equinox.launcher.Main.basicRun([Ljava/lang/String;)V+159
 * j  org.eclipse.equinox.launcher.Main.run([Ljava/lang/String;)I+4
 * j  org.eclipse.equinox.launch
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Stack implements LogEvent, HeaderEvent {

    /**
     * Regular expression for the header.
     */
    private static final String _REGEX_HEADER = "Stack: \\[" + JdkRegEx.ADDRESS + "," + JdkRegEx.ADDRESS + "\\](,  sp="
            + JdkRegEx.ADDRESS + ",  free space=(\\d{1,})k)?";

    private static Pattern pattern = Pattern.compile(Stack.REGEX);

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^(" + _REGEX_HEADER
            + "|([CjJvV]) |(Java|Native) frames:|JavaThread|\\[error occurred during error reporting \\(printing "
            + "(Java stack|native stack|stack bounds)\\)|...<more frames>...).*$";

    /**
     * Determine if the logLine matches the logging pattern(s) for this event.
     * 
     * @param logLine
     *            The log line to test.
     * @return true if the log line matches the event pattern, false otherwise.
     */
    public static final boolean match(String logLine) {
        return logLine.matches(REGEX);
    }

    /**
     * The log entry for the event.
     */
    private String logEntry;

    /**
     * Create event from log entry.
     * 
     * @param logEntry
     *            The log entry for the event.
     */
    public Stack(String logEntry) {
        this.logEntry = logEntry;
    }

    @Override
    public LogEventType getEventType() {
        return LogEventType.STACK;
    }

    public String getLogEntry() {
        return logEntry;
    }

    /**
     * @return The stack free space (kilobytes).
     */
    public long getStackFreeSpace() {
        long stackFreeSpace = Long.MIN_VALUE;
        if (isHeader()) {
            Matcher matcher = pattern.matcher(logEntry);
            if (matcher.find()) {
                if (matcher.group(18) != null) {
                    stackFreeSpace = Long.parseLong(matcher.group(18));
                }
            }
        }
        return stackFreeSpace;
    }

    /**
     * @return true if the stack frame is a Java or native frame, false otherwise.
     * 
     *         For example:
     * 
     *         V [libjvm.so+0x93a382] java_start(Thread*)+0xf2
     * 
     *         C [java.exe+0x1234]
     */
    public boolean isFrame() {
        boolean isFrame = false;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(19) != null) {
                isFrame = true;
            }
        }
        return isFrame;
    }

    @Override
    public boolean isHeader() {
        boolean isHeader = false;
        if (this.logEntry != null) {
            isHeader = logEntry.matches(_REGEX_HEADER);
        }
        return isHeader;
    }

    /**
     * @return true if the stack frame is vm code, false otherwise.
     * 
     *         For example:
     * 
     *         V [libjvm.so+0x93a382] java_start(Thread*)+0xf2
     */
    public boolean isVmFrame() {
        boolean isVmCode = false;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(19) != null && matcher.group(19).equals("V")) {
                isVmCode = true;
            }
        }
        return isVmCode;
    }

    /**
     * @return true if the stack frame is vm generated code, false otherwise.
     * 
     *         For example:
     * 
     *         v ~StubRoutines::call_stub
     */
    public boolean isVmGeneratedCodeFrame() {
        boolean isVmGeneratedCodeFrame = false;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            if (matcher.group(19) != null && matcher.group(19).equals("v")) {
                isVmGeneratedCodeFrame = true;
            }
        }
        return isVmGeneratedCodeFrame;
    }
}

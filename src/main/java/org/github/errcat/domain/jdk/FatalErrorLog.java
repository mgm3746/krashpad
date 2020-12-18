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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.errcat.util.Constants;
import org.github.errcat.util.Constants.CpuArch;
import org.github.errcat.util.Constants.OsType;
import org.github.errcat.util.Constants.OsVendor;
import org.github.errcat.util.Constants.OsVersion;
import org.github.errcat.util.ErrUtil;
import org.github.errcat.util.jdk.Analysis;
import org.github.errcat.util.jdk.JdkMath;
import org.github.errcat.util.jdk.JdkRegEx;
import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.Application;
import org.github.errcat.util.jdk.JdkUtil.Arch;
import org.github.errcat.util.jdk.JdkUtil.BuiltBy;
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
    private List<HeaderEvent> headerEvents;

    /**
     * Stack information.
     */
    private List<StackEvent> stackEvents;

    /**
     * Dynamic library information.
     */
    private List<DynamicLibraryEvent> dynamicLibraryEvents;

    /**
     * Current thread information.
     */
    private CurrentThreadEvent currentThreadEvent;

    /**
     * CPU information.
     */
    private List<CpuInfoEvent> cpuInfoEvents;

    /**
     * JVM crash time information.
     */
    private TimeEvent timeEvent;

    /**
     * JVM crash time timezone information.
     */
    private TimezoneEvent timezoneEvent;

    /**
     * JVM run duration information.
     */
    private ElapsedTimeEvent elapsedTimeEvent;

    /**
     * Thread information.
     */
    private List<ThreadEvent> threadEvents;

    /**
     * Compilation event information.
     */
    private List<CompilationEvent> compilationEvents;

    /**
     * Deoptimization event information.
     */
    private List<DeoptimizationEvent> deoptimizationEvents;

    /**
     * Vm event information.
     */
    private List<VmEvent> vmEvents;

    /**
     * Heap information.
     */
    private List<HeapEvent> heapEvents;

    /**
     * Memory information.
     */
    private MemoryEvent memoryEvent;

    /**
     * JVM options information.
     */
    private JvmArgsEvent jvmArgsEvent;

    /**
     * Exception counts information.
     */
    private List<ExceptionCountsEvent> exceptionCountsEvents;

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
        headerEvents = new ArrayList<HeaderEvent>();
        stackEvents = new ArrayList<StackEvent>();
        dynamicLibraryEvents = new ArrayList<DynamicLibraryEvent>();
        analysis = new ArrayList<Analysis>();
        unidentifiedLogLines = new ArrayList<String>();
        cpuInfoEvents = new ArrayList<CpuInfoEvent>();
        threadEvents = new ArrayList<ThreadEvent>();
        compilationEvents = new ArrayList<CompilationEvent>();
        deoptimizationEvents = new ArrayList<DeoptimizationEvent>();
        vmEvents = new ArrayList<VmEvent>();
        heapEvents = new ArrayList<HeapEvent>();
        exceptionCountsEvents = new ArrayList<ExceptionCountsEvent>();
    ***REMOVED***

    public void setVmInfoEvent(VmInfoEvent vmInfoEvent) {
        this.vmInfoEvent = vmInfoEvent;
    ***REMOVED***

    public void setOsEvent(OsEvent os) {
        this.osEvent = os;
    ***REMOVED***

    public List<String> getUnidentifiedLogLines() {
        return unidentifiedLogLines;
    ***REMOVED***

    public List<HeaderEvent> getHeaderEvents() {
        return headerEvents;
    ***REMOVED***

    public List<StackEvent> getStackEvents() {
        return stackEvents;
    ***REMOVED***

    public List<DynamicLibraryEvent> getDynamicLibraryEvents() {
        return dynamicLibraryEvents;
    ***REMOVED***

    public void setUnameEvent(UnameEvent unameEvent) {
        this.unameEvent = unameEvent;
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

    public void setCurrentThreadEvent(CurrentThreadEvent currentThreadEvent) {
        this.currentThreadEvent = currentThreadEvent;
    ***REMOVED***

    public List<CpuInfoEvent> getCpuInfoEvents() {
        return cpuInfoEvents;
    ***REMOVED***

    public void setTimeEvent(TimeEvent timeEvent) {
        this.timeEvent = timeEvent;
    ***REMOVED***

    public void setTimezoneEvent(TimezoneEvent timezoneEvent) {
        this.timezoneEvent = timezoneEvent;
    ***REMOVED***

    public void setElapsedTimeEvent(ElapsedTimeEvent elapsedTimeEvent) {
        this.elapsedTimeEvent = elapsedTimeEvent;
    ***REMOVED***

    public List<ThreadEvent> getThreadEvents() {
        return threadEvents;
    ***REMOVED***

    public List<CompilationEvent> getCompilationEvents() {
        return compilationEvents;
    ***REMOVED***

    public List<DeoptimizationEvent> getDeoptimizationEvents() {
        return deoptimizationEvents;
    ***REMOVED***

    public List<VmEvent> getVmEvents() {
        return vmEvents;
    ***REMOVED***

    public List<HeapEvent> getHeapEvents() {
        return heapEvents;
    ***REMOVED***

    public MemoryEvent getMemoryEvent() {
        return memoryEvent;
    ***REMOVED***

    public void setMemoryEvent(MemoryEvent memoryEvent) {
        this.memoryEvent = memoryEvent;
    ***REMOVED***

    public JvmArgsEvent getJvmArgsEvent() {
        return jvmArgsEvent;
    ***REMOVED***

    public void setJvmArgsEvent(JvmArgsEvent jvmArgsEvent) {
        this.jvmArgsEvent = jvmArgsEvent;
    ***REMOVED***

    public List<ExceptionCountsEvent> getExceptionCountsEvents() {
        return exceptionCountsEvents;
    ***REMOVED***

    /**
     * @return <code>JavaVendor</code>
     */
    public JavaVendor getJavaVendor() {
        JavaVendor vendor = JavaVendor.UNKNOWN;
        if (isRhBuildOpenJdk()) {
            vendor = JavaVendor.RED_HAT;
        ***REMOVED*** else {
            if (vmInfoEvent != null) {
                switch (vmInfoEvent.getBuiltBy()) {
                case JAVA_RE:
                    vendor = JavaVendor.ORACLE;
                    break;
                case JENKINS:
                    vendor = JavaVendor.ADOPTOPENJDK;
                    break;
                case ZULU_RE:
                    vendor = JavaVendor.AZUL;
                    break;
                case BUILD:
                case MOCKBUILD:
                case EMPTY:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return vendor;
    ***REMOVED***

    /**
     * @return <code>JavaSpecificiation</code>
     */
    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        if (vmInfoEvent != null) {
            version = vmInfoEvent.getJavaSpecification();
        ***REMOVED***
        return version;
    ***REMOVED***

    /**
     * @return JDK release string. For example:
     * 
     *         1.8.0_251-b08
     */
    public String getJdkReleaseString() {
        String release = "UNKNOWN";
        if (vmInfoEvent != null) {
            release = vmInfoEvent.getJdkReleaseString();
        ***REMOVED*** else if (headerEvents != null) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJreVersion()) {
                    String regEx = "^.+\\(build " + JdkRegEx.RELEASE_STRING + "\\)$";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(he.getLogEntry());
                    if (matcher.find()) {
                        release = matcher.group(1);
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return release;
    ***REMOVED***

    /**
     * @return The JDK build date/time.
     */
    public Date getJdkBuildDate() {
        Date date = null;
        if (vmInfoEvent != null) {
            date = vmInfoEvent.getBuildDate();
        ***REMOVED***
        return date;
    ***REMOVED***

    /**
     * @return <code>OsType</code>
     */
    public OsType getOsType() {
        OsType osType = OsType.UNKNOWN;
        if (osEvent != null) {
            osType = osEvent.getOsType();
        ***REMOVED*** else if (unameEvent != null) {
            osType = unameEvent.getOsType();
        ***REMOVED***
        return osType;
    ***REMOVED***

    /**
     * @return <code>OsVendor</code>
     */
    public OsVendor getOsVendor() {
        OsVendor osVendor = OsVendor.UNKNOWN;
        if (osEvent != null) {
            osVendor = osEvent.getOsVendor();
        ***REMOVED*** else if (unameEvent != null) {
            osVendor = unameEvent.getOsVendor();
        ***REMOVED***
        return osVendor;
    ***REMOVED***

    /**
     * @return <code>OsVersion</code>
     */
    public OsVersion getOsVersion() {
        OsVersion osVersion = OsVersion.UNKNOWN;
        if (osEvent != null) {
            osVersion = osEvent.getOsVersion();
        ***REMOVED*** else if (unameEvent != null) {
            osVersion = unameEvent.getOsVersion();
        ***REMOVED***
        return osVersion;
    ***REMOVED***

    /**
     * @return OS string.
     */
    public String getOsString() {
        String osString = "UNKNOWN";
        if (osEvent != null) {
            osString = osEvent.getOsString();
        ***REMOVED***
        return osString;
    ***REMOVED***

    /**
     * @return <code>Arch</code>
     */
    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        if (unameEvent != null) {
            arch = unameEvent.getArch();
        ***REMOVED*** else if (vmInfoEvent != null) {
            arch = vmInfoEvent.getArch();
        ***REMOVED*** else {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isJavaVm() && he.getLogEntry().matches("^.+solaris-sparc.+$")) {
                    arch = Arch.SPARC;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return arch;
    ***REMOVED***

    /**
     * @return A <code>String</code> describing the cause of the crash.
     */
    public String getError() {
        StringBuilder causedBy = new StringBuilder();
        if (headerEvents != null) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSigBus() || he.isSigIll() || he.isSigSegv() || he.isInternalError() || he.isError()
                        || he.isOutOfMemoryError() || he.isFailed()) {
                    if (causedBy.length() > 0) {
                        causedBy.append(Constants.LINE_SEPARATOR);
                    ***REMOVED***
                    causedBy.append(he.getLogEntry());
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return causedBy.toString();
    ***REMOVED***

    /**
     * @return true if JDK debug symbols are installed, false otherwise.
     */
    public boolean haveJdkDebugSymbols() {
        boolean haveJdkDebugSymbols = false;
        if (headerEvents != null) {
            Iterator<HeaderEvent> iterator1 = headerEvents.iterator();
            while (iterator1.hasNext()) {
                HeaderEvent he = iterator1.next();
                if (he.isProblematicFrame()) {
                    haveJdkDebugSymbols = he.getLogEntry().matches("^***REMOVED*** V  \\[.+\\].+$");
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        if (!haveJdkDebugSymbols) {
            if (stackEvents != null) {
                Iterator<StackEvent> iterator2 = stackEvents.iterator();
                while (iterator2.hasNext() && !haveJdkDebugSymbols) {
                    StackEvent se = iterator2.next();
                    if (se.isVmFrame()) {
                        haveJdkDebugSymbols = se.getLogEntry().matches("^V  \\[.+\\].+$")
                                && !se.getLogEntry().matches("^V  \\[.+\\]  JVM_DoPrivileged.+$");
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveJdkDebugSymbols;
    ***REMOVED***

    /**
     * @return true if the stack contains JDK VM frame code, false otherwise.
     */
    public boolean haveVmCodeInStack() {
        boolean haveVmCodeInStack = false;
        if (stackEvents != null) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmFrame() || event.isVmGeneratedCodeFrame()) {
                    haveVmCodeInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmCodeInStack;
    ***REMOVED***

    /**
     * @return true if the stack contains JDK VM generated code frame, false otherwise.
     */
    public boolean haveVmGeneratedCodeFrameInStack() {
        boolean haveVmGeneratedCodeFrameInStack = false;
        if (stackEvents != null) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmGeneratedCodeFrame()) {
                    haveVmGeneratedCodeFrameInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmGeneratedCodeFrameInStack;
    ***REMOVED***

    /**
     * @return true if the stack contains JDK VM frame, false otherwise.
     */
    public boolean haveVmFrameInStack() {
        boolean haveVmFrameInStack = false;
        if (stackEvents != null) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.isVmFrame()) {
                    haveVmFrameInStack = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmFrameInStack;
    ***REMOVED***

    /**
     * @return true if the header contains JDK VM frame code, false otherwise.
     */
    public boolean haveVmFrameInHeader() {
        boolean haveVmFrameInHeader = false;
        if (headerEvents != null) {
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent event = iterator.next();
                if (event.isVmFrame()) {
                    haveVmFrameInHeader = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveVmFrameInHeader;
    ***REMOVED***

    /**
     * @return true if the fatal error log was created on RHEL, false otherwise.
     */
    public boolean isRhel() {
        boolean isRhel = false;
        if (osEvent != null) {
            isRhel = osEvent.isRhel();
        ***REMOVED***
        return isRhel;
    ***REMOVED***

    /**
     * @return true if the fatal error log was created on Windows, false otherwise.
     */
    public boolean isWindows() {
        boolean isWindows = false;
        if (osEvent != null) {
            isWindows = osEvent.isWindows();
        ***REMOVED***
        return isWindows;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK rpm install, false
     *         otherwise.
     */
    public boolean isRhRpmInstall() {
        boolean isRhelRpmInstall = false;
        String rpmDirectory = getRpmDirectory();
        if (rpmDirectory != null) {
            if (getJavaSpecification() == JavaSpecification.JDK8) {
                switch (getOsVersion()) {
                case CENTOS6:
                case RHEL6:
                    isRhelRpmInstall = JdkUtil.rhel6Amd64Jdk8RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel6Amd64Jdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS7:
                case RHEL7:
                    if (getArch() == Arch.X86_64) {
                        isRhelRpmInstall = JdkUtil.rhel7Amd64Jdk8RpmReleases.containsKey(rpmDirectory)
                                && getJdkBuildDate().compareTo(
                                        JdkUtil.rhel7Amd64Jdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED*** else if (getArch() == Arch.PPC64LE) {
                        isRhelRpmInstall = JdkUtil.rhel7Ppc64leJdk8RpmReleases.containsKey(rpmDirectory)
                                && getJdkBuildDate().compareTo(
                                        JdkUtil.rhel7Ppc64leJdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    ***REMOVED***
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.rhel8Amd64Jdk8RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel8Amd64Jdk8RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED*** else if (getJavaSpecification() == JavaSpecification.JDK11) {
                switch (getOsVersion()) {
                case CENTOS7:
                case RHEL7:
                    isRhelRpmInstall = JdkUtil.rhel7Amd64Jdk11RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel7Amd64Jdk11RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS8:
                case RHEL8:
                    isRhelRpmInstall = JdkUtil.rhel8Amd64Jdk11RpmReleases.containsKey(rpmDirectory) && getJdkBuildDate()
                            .compareTo(JdkUtil.rhel8Amd64Jdk11RpmReleases.get(rpmDirectory).getBuildDate()) == 0;
                    break;
                case CENTOS6:
                case RHEL6:
                case UNKNOWN:
                default:
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isRhelRpmInstall;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK RHEL zip install, false
     *         otherwise.
     */
    public boolean isRhLinuxZipInstall() {
        boolean isRhLinuxZipInstall = false;
        if (getOsType() == OsType.LINUX && getArch() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhLinuxZipInstall = JdkUtil.rhelJdk8ZipReleases.containsKey(getJdkReleaseString())
                        && getJdkBuildDate()
                                .compareTo(JdkUtil.rhelJdk8ZipReleases.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK11:
                isRhLinuxZipInstall = JdkUtil.rhelJdk11ZipReleases.containsKey(getJdkReleaseString())
                        && getJdkBuildDate()
                                .compareTo(JdkUtil.rhelJdk11ZipReleases.get(getJdkReleaseString()).getBuildDate()) == 0;
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        return isRhLinuxZipInstall;
    ***REMOVED***

    /**
     * @return true if the JDK that produced the fatal error log is a Red Hat build of OpenJDK Windows zip install,
     *         false otherwise.
     */
    public boolean isRhWindowsZipInstall() {
        boolean isRhWindowsZipInstall = false;
        if (isWindows() && getArch() == Arch.X86_64) {
            switch (getJavaSpecification()) {
            case JDK8:
                isRhWindowsZipInstall = JdkUtil.windowsJdk8Releases.containsKey(getJdkReleaseString());
                break;
            case JDK11:
                isRhWindowsZipInstall = JdkUtil.windowsJdk11Releases.containsKey(getJdkReleaseString());
                break;
            case JDK6:
            case JDK7:
            case UNKNOWN:
            default:
                break;
            ***REMOVED***
        ***REMOVED***
        return isRhWindowsZipInstall;
    ***REMOVED***

    /**
     * @return true if the fatal error log was created by a RH build of OpenJDK, false otherwise.
     */
    public boolean isRhBuildOpenJdk() {
        return isRhRpmInstall() || isRhLinuxZipInstall() || isRhWindowsZipInstall();
    ***REMOVED***

    /**
     * @return true if the fatal error log was created by a JDK build string used by Red Hat, false otherwise.
     */
    public boolean isRedHatBuildString() {
        return vmInfoEvent != null && (vmInfoEvent.getBuiltBy() == BuiltBy.BUILD
                || vmInfoEvent.getBuiltBy() == BuiltBy.EMPTY || vmInfoEvent.getBuiltBy() == BuiltBy.MOCKBUILD);
    ***REMOVED***

    /**
     * AdoptOpenJDK has the same release versions as the RH build of OpenJDK but have a different build date/time and
     * builder string ("jenkins").
     * 
     * @return true if the fatal error log was created by a JDK build string used by AdoptOpenJDK, false otherwise.
     */
    public boolean isAdoptOpenJdkBuildString() {
        return vmInfoEvent != null && (vmInfoEvent.getBuiltBy() == BuiltBy.JENKINS);
    ***REMOVED***

    /**
     * @return true if the fatal error log was created by a JDK that is a Long Term Support (LTS) version, false
     *         otherwise.
     */
    public boolean isJdkLts() {
        boolean isJdkLts = false;
        switch (getJavaSpecification()) {
        case JDK6:
        case JDK7:
        case JDK8:
        case JDK11:
            isJdkLts = true;
            break;
        case JDK9:
        case JDK10:
        case JDK12:
        case JDK13:
        case JDK14:
        case JDK15:
        case UNKNOWN:
        default:
            break;
        ***REMOVED***
        return isJdkLts;
    ***REMOVED***

    /**
     * @return The rpm directory name, or null if not an rpm install.
     *
     *         For example:
     * 
     *         java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64
     * 
     *         java-11-openjdk-11.0.7.10-4.el7_8.x86_64
     */
    public String getRpmDirectory() {
        String rpmDirectory = null;
        if (getOsType() == OsType.LINUX) {
            if (dynamicLibraryEvents != null) {
                Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
                while (iterator.hasNext()) {
                    DynamicLibraryEvent event = iterator.next();
                    if (event.getFilePath() != null) {
                        Pattern pattern = null;
                        Matcher matcher = null;
                        if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK8_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            ***REMOVED***
                            break;
                        ***REMOVED*** else if (event.getFilePath().matches(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH)) {
                            pattern = Pattern.compile(JdkRegEx.RH_RPM_OPENJDK11_LIBJVM_PATH);
                            matcher = pattern.matcher(event.getFilePath());
                            if (matcher.find()) {
                                rpmDirectory = matcher.group(1);
                            ***REMOVED***
                            break;
                        ***REMOVED***
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return rpmDirectory;
    ***REMOVED***

    public String getCurrentThread() {
        String currentThread = null;
        if (currentThreadEvent != null) {
            currentThread = currentThreadEvent.getCurrentThread();
        ***REMOVED***
        return currentThread;
    ***REMOVED***

    /**
     * @return true if the crash happens in JNA code, false otherwise.
     */
    public boolean isJnaCrash() {
        boolean isJnaCrash = false;
        if (getStackEvents() != null && getStackEvents().size() >= 2) {
            if (getStackFrame(1).matches("^C[ ]{1,2***REMOVED***\\[jna.+$")
                    && getStackFrame(2).matches("^j[ ]{1,2***REMOVED***com\\.sun\\.jna\\..+$")) {
                isJnaCrash = true;
            ***REMOVED***
        ***REMOVED***
        return isJnaCrash;
    ***REMOVED***

    /**
     * @return true if the crash happens due to Out Of Memory Error, false otherwise.
     */
    public boolean isOomeCrash() {
        boolean isOomeCrash = false;
        String regExOome = "Out of Memory Error";
        Pattern pattern = Pattern.compile(regExOome);
        Matcher matcher = pattern.matcher(getError());
        if (matcher.find()) {
            isOomeCrash = true;
        ***REMOVED***
        return isOomeCrash;
    ***REMOVED***

    /**
     * @param i
     *            The stack frame index (1 = top).
     * @return The stack frame at the specified position.
     */
    public String getStackFrame(int i) {
        String stackFrame = null;
        int stackIndex = 1;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (!event.getLogEntry().matches("^(Stack|(Java|Native) frames):.+$")) {
                if (stackIndex == i) {
                    stackFrame = event.getLogEntry();
                    break;
                ***REMOVED***
                stackIndex++;
            ***REMOVED***
        ***REMOVED***
        return stackFrame;
    ***REMOVED***

    /**
     * @param stackFrame
     *            A stack frame in the stack.
     * @return The next stack frame, or null if the stack frame passed in does not exist or is at the bottom of the
     *         stack.
     */
    public String getNextStackFrame(String stackFrame) {
        String nextStackFrame = null;
        int stackIndex = 0;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^" + stackFrame + "$") && stackIndex < stackEvents.size()) {
                nextStackFrame = stackEvents.get(stackIndex + 1).getLogEntry();
                break;
            ***REMOVED***
            stackIndex++;
        ***REMOVED***
        return nextStackFrame;
    ***REMOVED***

    /**
     * @return The top Compile Java Code (J) stack frame, or null if none exists.
     */
    public String getStackFrameTopCompiledJavaCode() {
        String stackFrameTopCompiledJavaCode = null;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^J[ ]{1,2***REMOVED***.+$")) {
                stackFrameTopCompiledJavaCode = event.getLogEntry();
                break;
            ***REMOVED***
        ***REMOVED***
        return stackFrameTopCompiledJavaCode;
    ***REMOVED***

    /**
     * @return The top stack frame, or null if none exists.
     */
    public String getStackFrameTop() {
        String stackFrameTop = null;
        Iterator<StackEvent> iterator = stackEvents.iterator();
        while (iterator.hasNext()) {
            StackEvent event = iterator.next();
            if (event.getLogEntry().matches("^(A|C|j|J|v|V)[ ]{1,2***REMOVED***.+$")) {
                stackFrameTop = event.getLogEntry();
                break;
            ***REMOVED***
        ***REMOVED***
        return stackFrameTop;
    ***REMOVED***

    /**
     * @return The CPU architecture.
     */
    public CpuArch getCpuArch() {
        CpuArch cpuArch = CpuArch.UNKNOWN;
        if (cpuInfoEvents != null) {
            Iterator<CpuInfoEvent> iterator = cpuInfoEvents.iterator();
            while (iterator.hasNext()) {
                CpuInfoEvent event = iterator.next();
                if (event.getLogEntry().matches("^.+POWER9.+$")) {
                    cpuArch = CpuArch.POWER9;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return cpuArch;
    ***REMOVED***

    /**
     * @return The application running on the JDK
     */
    public Application getApplication() {
        Application application = Application.UNKNOWN;
        if (cpuInfoEvents != null) {
            Iterator<DynamicLibraryEvent> iterator = dynamicLibraryEvents.iterator();
            while (iterator.hasNext()) {
                DynamicLibraryEvent event = iterator.next();
                if (event.getLogEntry().matches(JdkRegEx.JBOSS_JAR)) {
                    application = Application.JBOSS;
                    break;
                ***REMOVED*** else if (event.getLogEntry().matches(JdkRegEx.TOMCAT_JAR)) {
                    application = Application.TOMCAT;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return application;
    ***REMOVED***

    /**
     * @param classRegEx
     *            A class name as a regular expression.
     * @return true if the class is in the stack, false otherwise.
     */
    public boolean isInStack(String classRegEx) {
        boolean isInStack = false;
        if (getStackEvents() != null) {
            Iterator<StackEvent> iterator = stackEvents.iterator();
            while (iterator.hasNext()) {
                StackEvent event = iterator.next();
                if (event.getLogEntry().matches("^.+" + classRegEx + ".+$")) {
                    isInStack = true;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return isInStack;
    ***REMOVED***

    /**
     * @return The duration of the JVM run.
     */
    public String getElapsedTime() {
        String elapsedTime = null;
        if (elapsedTimeEvent != null) {
            elapsedTime = elapsedTimeEvent.getElapsedTime();
        ***REMOVED***
        return elapsedTime;
    ***REMOVED***

    /**
     * @return The time of the crash.
     */
    public String getCrashTime() {
        StringBuilder crashTime = new StringBuilder();
        if (timeEvent != null) {
            crashTime.append(timeEvent.getTime());
        ***REMOVED***
        if (timezoneEvent != null) {
            crashTime.append(" (");
            crashTime.append(timezoneEvent.getTimezone());
            crashTime.append(")");
        ***REMOVED***
        return crashTime.toString();
    ***REMOVED***

    /**
     * @return The number of Java threads running when the JVM crashed.
     */
    public int getJavaThreadCount() {
        int javaThreadCount = 0;
        if (threadEvents != null) {
            Iterator<ThreadEvent> iterator = threadEvents.iterator();
            while (iterator.hasNext()) {
                ThreadEvent event = iterator.next();
                if (event.getLogEntry().matches("^***REMOVED***$")) {
                    break;
                ***REMOVED*** else if (!event.getLogEntry().matches("^Java Threads: \\( => current thread \\)$")) {
                    javaThreadCount++;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return javaThreadCount;
    ***REMOVED***

    /**
     * @return The total available physical memory (kilobytes).
     */
    public long getPhysicalMemory() {
        long physicalMemory = Long.MIN_VALUE;
        if (memoryEvent != null) {
            physicalMemory = memoryEvent.getPhysicalMemory();
        ***REMOVED***
        return physicalMemory;
    ***REMOVED***

    /**
     * @return The total free physical memory (kilobytes).
     */
    public long getPhysicalMemoryFree() {
        long physicalMemoryFree = Long.MIN_VALUE;
        if (memoryEvent != null) {
            physicalMemoryFree = memoryEvent.getPhysicalMemoryFree();
        ***REMOVED***
        return physicalMemoryFree;
    ***REMOVED***

    /**
     * @return The total available swap (kilobytes).
     */
    public long getSwap() {
        long swap = Long.MIN_VALUE;
        if (memoryEvent != null) {
            swap = memoryEvent.getSwap();
        ***REMOVED***
        return swap;
    ***REMOVED***

    /**
     * @return The total free swap (kilobytes).
     */
    public long getSwapFree() {
        long swapFree = Long.MIN_VALUE;
        if (memoryEvent != null) {
            swapFree = memoryEvent.getSwapFree();
        ***REMOVED***
        return swapFree;
    ***REMOVED***

    /**
     * @return The heap max size reserved (kilobytes).
     */
    public long getHeapMaxSize() {
        long heapMaxSize = Long.MIN_VALUE;
        if (jvmArgsEvent != null && jvmArgsEvent.getMaxHeapOption() != null) {
            long bytes = JdkUtil.convertOptionSizeToBytes(jvmArgsEvent.getMaxHeapValue());
            heapMaxSize = JdkUtil.convertBytesToKilobytes(bytes);
        ***REMOVED***
        // Max heap size not set (e.g. container), use allocation
        if (heapMaxSize == Long.MIN_VALUE) {
            heapMaxSize = getHeapAllocation();
        ***REMOVED***
        return heapMaxSize;
    ***REMOVED***

    /**
     * @return The total heap allocation (kilobytes).
     */
    public long getHeapAllocation() {
        long heapAllocation = Long.MIN_VALUE;
        if (heapEvents != null) {
            heapAllocation = 0;
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                ***REMOVED*** else if (heapAtCrash && event.isYoungGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_YOUNG_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapAllocation += +JdkUtil.convertOptionSizeToBytes(matcher.group(2) + matcher.group(4));
                    ***REMOVED***
                ***REMOVED*** else if (heapAtCrash && event.isOldGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_OLD_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapAllocation += +JdkUtil.convertOptionSizeToBytes(matcher.group(2) + matcher.group(4));
                    ***REMOVED***
                ***REMOVED*** else if (heapAtCrash && event.isShenandoah()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_SHENANDOAH);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapAllocation += +JdkUtil.convertOptionSizeToBytes(matcher.group(4) + matcher.group(6));
                    ***REMOVED***
                ***REMOVED*** else if (heapAtCrash && event.isG1()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_G1);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapAllocation += +JdkUtil.convertOptionSizeToBytes(matcher.group(1) + matcher.group(3));
                    ***REMOVED***
                ***REMOVED*** else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                ***REMOVED***
            ***REMOVED***
            heapAllocation = JdkMath.convertBytesToKilobytes(heapAllocation);
        ***REMOVED***
        return heapAllocation;
    ***REMOVED***

    /**
     * @return The total heap used (kilobytes).
     */
    public long getHeapUsed() {
        long heapUsed = Long.MIN_VALUE;
        if (heapEvents != null) {
            heapUsed = 0;
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                ***REMOVED*** else if (heapAtCrash && event.isYoungGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_YOUNG_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapUsed = heapUsed + JdkUtil.convertOptionSizeToBytes(matcher.group(5) + matcher.group(7));
                    ***REMOVED***
                ***REMOVED*** else if (heapAtCrash && event.isOldGen()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_OLD_GEN);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapUsed = heapUsed + JdkUtil.convertOptionSizeToBytes(matcher.group(5) + matcher.group(7));
                    ***REMOVED***
                ***REMOVED*** else if (heapAtCrash && event.isShenandoah()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_SHENANDOAH);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapUsed = heapUsed + JdkUtil.convertOptionSizeToBytes(matcher.group(7) + matcher.group(9));
                    ***REMOVED***
                ***REMOVED*** else if (heapAtCrash && event.isG1()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_G1);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        heapUsed = heapUsed + JdkUtil.convertOptionSizeToBytes(matcher.group(4) + matcher.group(6));
                    ***REMOVED***
                ***REMOVED*** else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                ***REMOVED***
            ***REMOVED***
            heapUsed = JdkMath.convertBytesToKilobytes(heapUsed);
        ***REMOVED***
        return heapUsed;
    ***REMOVED***

    /**
     * @return The metaspace max size reserved (kilobytes).
     */
    public long getMetaspaceMaxSize() {
        long metaspaceMaxSize = Long.MIN_VALUE;
        if (jvmArgsEvent != null && jvmArgsEvent.getMaxMetaspaceOption() != null) {
            long bytes = JdkUtil.convertOptionSizeToBytes(jvmArgsEvent.getMaxMetaspaceValue());
            metaspaceMaxSize = JdkMath.convertBytesToKilobytes(bytes);
        ***REMOVED***
        // If max metaspace size not set (recommended), get from <code>HeapEvent</code>
        if (metaspaceMaxSize == Long.MIN_VALUE) {
            if (heapEvents != null) {
                Iterator<HeapEvent> iterator = heapEvents.iterator();
                boolean heapAtCrash = false;
                Pattern pattern = null;
                Matcher matcher = null;
                while (iterator.hasNext()) {
                    HeapEvent event = iterator.next();
                    if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                        heapAtCrash = true;
                    ***REMOVED*** else if (heapAtCrash && event.isMetaspace()) {
                        pattern = Pattern.compile(HeapEvent.REGEX_METASPACE);
                        matcher = pattern.matcher(event.getLogEntry());
                        if (matcher.find()) {
                            metaspaceMaxSize = JdkUtil.convertOptionSizeToBytes(matcher.group(10) + matcher.group(12));
                        ***REMOVED***
                    ***REMOVED*** else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                        heapAtCrash = false;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
            metaspaceMaxSize = JdkMath.convertBytesToKilobytes(metaspaceMaxSize);
        ***REMOVED***
        return metaspaceMaxSize;
    ***REMOVED***

    /**
     * @return The total metaspace allocation (kilobytes).
     */
    public long getMetaspaceAllocation() {
        long metaspaceAllocation = Long.MIN_VALUE;
        ;
        if (heapEvents != null) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                ***REMOVED*** else if (heapAtCrash && event.isMetaspace()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_METASPACE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        metaspaceAllocation = JdkUtil.convertOptionSizeToBytes(matcher.group(7) + matcher.group(9));
                    ***REMOVED***
                ***REMOVED*** else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                ***REMOVED***
            ***REMOVED***
            metaspaceAllocation = JdkMath.convertBytesToKilobytes(metaspaceAllocation);
        ***REMOVED***
        return metaspaceAllocation;
    ***REMOVED***

    /**
     * @return The total metaspace used (kilobytes).
     */
    public long getMetaspaceUsed() {
        long metaspaceUsed = Long.MIN_VALUE;
        ;
        if (heapEvents != null) {
            Iterator<HeapEvent> iterator = heapEvents.iterator();
            boolean heapAtCrash = false;
            Pattern pattern = null;
            Matcher matcher = null;
            while (iterator.hasNext()) {
                HeapEvent event = iterator.next();
                if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_AT_CRASH_HEADER)) {
                    heapAtCrash = true;
                ***REMOVED*** else if (heapAtCrash && event.isMetaspace()) {
                    pattern = Pattern.compile(HeapEvent.REGEX_METASPACE);
                    matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        metaspaceUsed = JdkUtil.convertOptionSizeToBytes(matcher.group(1) + matcher.group(3));
                    ***REMOVED***
                ***REMOVED*** else if (event.getLogEntry().matches(HeapEvent.REGEX_HEAP_HISTORY_HEADER)) {
                    heapAtCrash = false;
                ***REMOVED***
            ***REMOVED***
            metaspaceUsed = JdkMath.convertBytesToKilobytes(metaspaceUsed);
        ***REMOVED***
        return metaspaceUsed;
    ***REMOVED***

    /**
     * @return The max compressed class size reserved (kilobytes).
     */
    public long getCompressedClassSpaceSize() {
        // Default is 1g
        long compressedClassSpaceSize = Constants.GIGABYTE.divide(Constants.KILOBYTE).longValue();
        if (jvmArgsEvent != null && jvmArgsEvent.getCompressedClassSpaceSizeOption() != null) {
            long bytes = JdkUtil.convertOptionSizeToBytes(jvmArgsEvent.getCompressedClassSpaceSizeValue());
            compressedClassSpaceSize = JdkMath.convertBytesToKilobytes(bytes);
        ***REMOVED***
        return compressedClassSpaceSize;
    ***REMOVED***

    /**
     * @return The <code>CrashCause</code>.
     */
    public CrashCause getCrashCause() {
        CrashCause crashCause = CrashCause.UNKNOWN;
        if (headerEvents != null) {
            // Check header
            Iterator<HeaderEvent> iterator = headerEvents.iterator();
            while (iterator.hasNext()) {
                HeaderEvent he = iterator.next();
                if (he.isSigBus()) {
                    crashCause = CrashCause.SIGBUS;
                    break;
                ***REMOVED*** else if (he.isSigSegv()) {
                    crashCause = CrashCause.SIGSEGV;
                    break;
                ***REMOVED*** else if (he.isSigIll()) {
                    crashCause = CrashCause.SIGILL;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return crashCause;
    ***REMOVED***

    /**
     * @return The JVM memory (kilobytes).
     */
    public long getJvmMemory() {
        long jvmMemory = Long.MIN_VALUE;
        if (jvmArgsEvent != null && jvmArgsEvent.getUseCompressedOopsDisabled() == null
                && jvmArgsEvent.getUseCompressedClassPointersDisabled() == null) {
            // Using compressed class pointers space
            jvmMemory = getHeapMaxSize() + getMetaspaceMaxSize() + getCompressedClassSpaceSize();
        ***REMOVED*** else {
            // Not using compressed class pointers space
            jvmMemory = getHeapMaxSize() + getMetaspaceMaxSize();
        ***REMOVED***
        return jvmMemory;
    ***REMOVED***

    /**
     * @return The number of cpu cores.
     */
    public int getCpuCores() {
        int cpuCores = Integer.MIN_VALUE;
        if (cpuInfoEvents != null) {
            Iterator<CpuInfoEvent> iterator = cpuInfoEvents.iterator();
            while (iterator.hasNext()) {
                CpuInfoEvent event = iterator.next();
                if (event.isCpuHeader()) {
                    Pattern pattern = Pattern.compile(CpuInfoEvent.REGEX_CPU_HEADER);
                    Matcher matcher = pattern.matcher(event.getLogEntry());
                    if (matcher.find()) {
                        int cpus = Integer.parseInt(matcher.group(1));
                        int cores = 1;
                        if (matcher.group(3) != null) {
                            cores = Integer.parseInt(matcher.group(4));
                        ***REMOVED***
                        cpuCores = cpus * cores;
                    ***REMOVED***
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return cpuCores;
    ***REMOVED***

    /**
     * @return true if there were StackOverflowErrors before the crash, false otherwise.
     */
    public boolean haveStackOverFlowError() {
        boolean haveStackOverFlowError = false;
        if (exceptionCountsEvents.size() > 0) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^StackOverflowErrors=\\d{1,***REMOVED***$")) {
                    haveStackOverFlowError = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveStackOverFlowError;
    ***REMOVED***

    /**
     * @return true if there were OutOfMemoryError: Java heap space before the crash, false otherwise.
     */
    public boolean haveOomeJavaHeap() {
        boolean haveStackOverFlowError = false;
        if (exceptionCountsEvents.size() > 0) {
            Iterator<ExceptionCountsEvent> iteratorExceptionCounts = exceptionCountsEvents.iterator();
            while (iteratorExceptionCounts.hasNext()) {
                ExceptionCountsEvent exceptionCountsEvent = iteratorExceptionCounts.next();
                if (!exceptionCountsEvent.isHeader()
                        && exceptionCountsEvent.getLogEntry().matches("^OutOfMemoryError java_heap_errors=\\d{1,***REMOVED***$")) {
                    haveStackOverFlowError = true;
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return haveStackOverFlowError;
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
        // Check for JVM failing to start
        if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
            analysis.add(Analysis.INFO_JVM_STARTUP_FAILS);
        ***REMOVED***
        // Check if JDK debugging symbols are installed
        if ((haveVmFrameInStack() || haveVmFrameInHeader()) && !haveJdkDebugSymbols()) {
            analysis.add(Analysis.WARN_DEBUG_SYMBOLS);
        ***REMOVED***
        // Check if latest JDK release
        if (!JdkUtil.isLatestJdkRelease(this)) {
            analysis.add(0, Analysis.WARN_JDK_NOT_LATEST);
        ***REMOVED***
        // Identify vendor/build
        if (isRhBuildOpenJdk()) {
            if (getOsType() == OsType.LINUX) {
                if (getOsVendor() == OsVendor.CENTOS) {
                    // CentOs redistributes RH build of OpenJDK
                    analysis.add(0, Analysis.INFO_RH_BUILD_CENTOS);
                ***REMOVED*** else if (getRpmDirectory() != null) {
                    analysis.add(0, Analysis.INFO_RH_BUILD_RPM);
                    if (getCpuArch() == CpuArch.POWER9 && getJavaSpecification() == JavaSpecification.JDK8
                            && getOsString().matches(".+7\\.(7|8|9).+")) {
                        // power8 JDK8 deployed on power9 on RHEL 7
                        analysis.add(Analysis.ERROR_JDK8_RHEL7_POWER8_RPM_ON_POWER9);
                    ***REMOVED***
                ***REMOVED*** else {
                    analysis.add(0, Analysis.INFO_RH_BUILD_LINUX_ZIP);
                ***REMOVED***
                // Check for RHEL6
                if (getOsVersion() == OsVersion.RHEL6) {
                    analysis.add(Analysis.WARN_RHEL6);
                ***REMOVED***
            ***REMOVED*** else if (isWindows()) {
                analysis.add(0, Analysis.INFO_RH_BUILD_WINDOWS_ZIP);
            ***REMOVED***
        ***REMOVED*** else {
            if (isRedHatBuildString()) {
                analysis.add(Analysis.INFO_RH_BUILD_POSSIBLE);
            ***REMOVED*** else if (isAdoptOpenJdkBuildString()) {
                analysis.add(Analysis.INFO_ADOPTOPENJDK_POSSIBLE);
            ***REMOVED*** else {
                analysis.add(0, Analysis.INFO_RH_BUILD_NOT);
            ***REMOVED***
        ***REMOVED***
        // Check if there is vm code in the stack
        if (!haveVmCodeInStack()) {
            analysis.add(Analysis.INFO_STACK_NO_VM_CODE);
        ***REMOVED***
        // Check if LTS release
        if (!isJdkLts()) {
            analysis.add(Analysis.WARN_JDK_NOT_LTS);
        ***REMOVED***
        // Check for ancient JDK
        if (ErrUtil.dayDiff(JdkUtil.getJdkReleaseDate(this), JdkUtil.getLatestJdkReleaseDate(this)) > 365) {
            analysis.add(Analysis.INFO_JDK_ANCIENT);
        ***REMOVED***
        // Check for crash in JNA
        if (isJnaCrash()) {
            if (getJavaVendor() == JavaVendor.RED_HAT) {
                analysis.add(Analysis.ERROR_JNA_RH);
            ***REMOVED*** else {
                analysis.add(Analysis.ERROR_JNA);
            ***REMOVED***
        ***REMOVED***
        // Check for JDK8 ZipFile contention
        if (getJavaSpecification() == JavaSpecification.JDK8 && getStackFrameTopCompiledJavaCode() != null
                && getStackFrameTopCompiledJavaCode().matches("^.+java\\.util\\.zip\\.ZipFile\\.getEntry.+$")) {
            analysis.add(Analysis.ERROR_JDK8_ZIPFILE_CONTENTION);
        ***REMOVED***
        // Check for unsychronized access to DirectByteBuffer
        String stackFrameTop = "^v  ~StubRoutines::jbyte_disjoint_arraycopy$";
        if (getStackFrameTop() != null && getStackFrameTop().matches(stackFrameTop)
                && isInStack(JdkRegEx.JAVA_NIO_BYTEBUFFER)) {
            analysis.add(Analysis.ERROR_DIRECT_BYTE_BUFFER_CONTENTION);
        ***REMOVED***
        // Check for insufficient physical memory
        if (getPhysicalMemory() > 0 && jvmArgsEvent != null) {
            if (getJvmMemory() > getPhysicalMemory()) {
                analysis.add(Analysis.ERROR_HEAP_PLUS_METASPACE_GT_PHYSICAL_MEMORY);
            ***REMOVED***
        ***REMOVED***
        // OOME, swap
        if (isOomeCrash()) {
            if (getElapsedTime() != null && getElapsedTime().matches("0d 0h 0m 0s")) {
                analysis.add(Analysis.ERROR_OOME_STARTUP);
                // Don't double report the JVM failing to start
                analysis.remove(Analysis.INFO_JVM_STARTUP_FAILS);
            ***REMOVED*** else if (getJvmMemory() > 0 && JdkMath.calcPercent(getJvmMemory(), getPhysicalMemory()) < 95) {
                analysis.add(Analysis.ERROR_OOME_JVM_LT_PHYSICAL_MEMORY);
            ***REMOVED***
        ***REMOVED*** else {
            // Check for excessive swap usage
            if (getSwap() > 0 && JdkMath.calcPercent(getSwapFree(), getSwap()) < 95) {
                analysis.add(Analysis.INFO_SWAPPING);
            ***REMOVED***
        ***REMOVED***
        // Check for swap disabled
        if (getSwap() == 0) {
            analysis.add(Analysis.INFO_SWAP_DISABLED);
        ***REMOVED***
        // Check for ShenadoahRootUpdater bug fixed in OpenJDK8 u282.
        if (getJavaSpecification() == JavaSpecification.JDK8 && getStackFrameTop() != null && getStackFrameTop()
                .matches("^V  \\[(libjvm\\.so|jvm\\.dll).+\\]  ShenandoahUpdateRefsClosure::do_oop.+$")) {
            // TODO: Verify current JDK version < u282
            analysis.add(Analysis.ERROR_JDK8_SHENANDOAH_ROOT_UPDATER);
        ***REMOVED***
        // Check for SIGBUS
        if (getCrashCause() == CrashCause.SIGBUS) {
            if (getOsType() == OsType.LINUX) {
                analysis.add(Analysis.INFO_SIGBUS_LINUX);
            ***REMOVED*** else {
                analysis.add(Analysis.INFO_SIGBUS);
            ***REMOVED***
        ***REMOVED***
        // Check for SIGSEGV
        if (getCrashCause() == CrashCause.SIGSEGV) {
            analysis.add(Analysis.INFO_SIGSEGV);
        ***REMOVED***
        // pthread_getcpuclockid
        if (getStackFrameTop() != null
                && getStackFrameTop().matches("^C  \\[libpthread\\.so.+\\]  pthread_getcpuclockid.+$")) {
            analysis.add(Analysis.ERROR_PTHREAD_GETCPUCLOCKID);
        ***REMOVED***
        // BufferBlob::flush_icache_stub
        if (getStackFrameTop() != null && getStackFrameTop().matches("^v  ~BufferBlob::flush_icache_stub+$")) {
            analysis.add(Analysis.ERROR_BUFFERBLOB_FLUSH_ICACHE_STUB);
        ***REMOVED***
        // StackOverflowError
        if (haveStackOverFlowError()) {
            analysis.add(Analysis.ERROR_STACKOVERFLOW);
        ***REMOVED***
        // OutOfMemoryError: Java heap space
        if (haveOomeJavaHeap()) {
            analysis.add(Analysis.ERROR_OOME_JAVA_HEAP);
        ***REMOVED***
    ***REMOVED***

    /**
     * Do JVM options analysis.
     */
    private void doJvmOptionsAnalysis() {
        // TODO
    ***REMOVED***
***REMOVED***

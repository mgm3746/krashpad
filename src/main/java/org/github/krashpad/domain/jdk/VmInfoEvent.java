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
package org.github.krashpad.domain.jdk;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.domain.LogEvent;
import org.github.krashpad.util.ErrUtil;
import org.github.krashpad.util.jdk.JdkRegEx;
import org.github.krashpad.util.jdk.JdkUtil;
import org.github.krashpad.util.jdk.JdkUtil.Arch;
import org.github.krashpad.util.jdk.JdkUtil.BuiltBy;
import org.github.krashpad.util.jdk.JdkUtil.JavaSpecification;

/**
 * <p>
 * JVM_INFO
 * </p>
 * 
 * <p>
 * JVM environment information unique to the JDK build. A version string embedded in libjvm.so/jvm.dll.
 * </p>
 * 
 * <h3>Example Logging</h3>
 * 
 * <pre>
 * vm_info: Java HotSpot(TM) 64-Bit Server VM (25.192-b12) for linux-amd64 JRE (1.8.0_192-b12), built on Oct  6 2018 06:46:09 by "java_re" with gcc 7.3.0
 * </pre>
 * 
 * <pre>
 * vm_info: OpenJDK 64-Bit Server VM (25.252-b14) for linux-amd64 JRE (Zulu 8.46.0.52-SA-linux64) (1.8.0_252-b14), built on Apr 22 2020 07:39:02 by "zulu_re" with gcc 4.4.7 20120313 (Red Hat 4.4.7-3)
 * </pre>
 * 
 * <pre>
 * vm_info: OpenJDK 64-Bit Server VM (11.0.5+10-LTS) for linux-amd64 JRE (11.0.5+10-LTS), built on Oct  9 2019 18:41:22 by "mockbuild" with gcc 4.8.5 20150623 (Red Hat 4.8.5-39)
 * </pre>
 * 
 * <pre>
 * vm_info: Java HotSpot(TM) 64-Bit Server VM (25.231-b11) for solaris-sparc JRE (1.8.0_231-b11), built on Oct  5 2019 10:35:34 by "java_re" with Sun Studio 12u1
 * </pre>
 * 
 * <pre>
 * vm_info: OpenJDK 64-Bit Server VM (11.0.10+9) for linux-amd64 JRE (11.0.10+9), built on Jan 22 2021 19:24:16 by "vsts" with gcc 7.3.0
 * </pre>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class VmInfoEvent implements LogEvent {

    /**
     * Regular expression defining the logging.
     */
    private static final String REGEX = "^vm_info: (Java HotSpot\\(TM\\)|OpenJDK)( 64-Bit)? Server VM \\(.+\\) for "
            + "(linux|windows|solaris)-(amd64|ppc64|ppc64le|sparc|x86) JRE (\\(Zulu.+\\) )?\\("
            + JdkRegEx.RELEASE_STRING + "\\).+ built on " + JdkRegEx.BUILD_DATE_TIME + ".+$";

    private static Pattern pattern = Pattern.compile(REGEX);

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
    public VmInfoEvent(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public String getName() {
        return JdkUtil.LogEventType.VM_INFO.toString();
    }

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
     * @return The JDK version.
     */
    public JavaSpecification getJavaSpecification() {
        JavaSpecification version = JavaSpecification.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexJdkVersion = 7;
            if (matcher.group(indexJdkVersion).equals("12.0")) {
                version = JavaSpecification.JDK12;
            } else if (matcher.group(indexJdkVersion).equals("11.0")) {
                version = JavaSpecification.JDK11;
            } else if (matcher.group(indexJdkVersion).equals("1.8")) {
                version = JavaSpecification.JDK8;
            } else if (matcher.group(indexJdkVersion).equals("1.7")) {
                version = JavaSpecification.JDK7;
            } else if (matcher.group(indexJdkVersion).equals("1.6")) {
                version = JavaSpecification.JDK6;
            }
        }
        return version;
    }

    /**
     * @return The Java release string.
     */
    public String getJdkReleaseString() {
        String release = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            release = matcher.group(6);
        }
        return release;
    }

    /**
     * @return The chip architecture.
     */
    public Arch getArch() {
        Arch arch = Arch.UNKNOWN;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            int indexArch = 4;
            if (matcher.group(indexArch).equals("amd64") || matcher.group(indexArch).equals("linux64")) {
                arch = Arch.X86_64;
            } else if (matcher.group(indexArch).equals("ppc64le")) {
                arch = Arch.PPC64LE;
            } else if (matcher.group(indexArch).equals("ppc64")) {
                arch = Arch.PPC64;
            } else if (matcher.group(indexArch).equals("x86")) {
                arch = Arch.X86;
            }
        }
        return arch;
    }

    /**
     * @return The JDK build date/time.
     */
    public Date getBuildDate() {
        Date date = null;
        Matcher matcher = pattern.matcher(logEntry);
        if (matcher.find()) {
            date = ErrUtil.getDate(matcher.group(10), matcher.group(11), matcher.group(12), matcher.group(13),
                    matcher.group(14), matcher.group(15));
        }
        return date;
    }

    /**
     * @return JDK builder.
     */
    public BuiltBy getBuiltBy() {
        BuiltBy builtBy = BuiltBy.UNKNOWN;
        if (logEntry.matches(".+\"build\".+")) {
            builtBy = BuiltBy.BUILD;
        } else if (logEntry.matches(".+\"\".+")) {
            builtBy = BuiltBy.EMPTY;
        } else if (logEntry.matches(".+\"jenkins\".+")) {
            // AdoptOpenJDK
            builtBy = BuiltBy.JENKINS;
        } else if (logEntry.matches(".+\"java_re\".+")) {
            // Oracle
            builtBy = BuiltBy.JAVA_RE;
        } else if (logEntry.matches(".+\"mockbuild\".+")) {
            // Red Hat, CentOS
            builtBy = BuiltBy.MOCKBUILD;
        } else if (logEntry.matches(".+\"vsts\".+")) {
            // Microsoft
            builtBy = BuiltBy.VSTS;
        } else if (logEntry.matches(".+\"zulu_re\".+")) {
            // Azul
            builtBy = BuiltBy.ZULU_RE;
        }
        return builtBy;
    }
}

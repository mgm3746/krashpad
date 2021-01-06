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

import org.github.errcat.util.jdk.JdkRegEx;

/**
 * <p>
 * JVM options. Null indicates the option is not explicitly set.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class JvmOptions {

    /**
     * ABRT option. For example:
     * 
     * -ABRT %p
     */
    private String abrt;

    /**
     * JPDA socket transport used for debugging. For example:
     * 
     * -agentlib:jdwp=transport=dt_socket,address=8787,server=y,suspend=n
     */
    private String jpdaSocketTransport;

    /**
     * Thread stack size. Specified with either the <code>-Xss</code>, <code>-ss</code>, or
     * <code>-XX:ThreadStackSize</code> options. For example:
     * 
     * <pre>
     * -Xss256k
     * </pre>
     * 
     * <pre>
     * -ss512k
     * </pre>
     * 
     * <pre>
     * -XX:ThreadStackSize=128
     * </pre>
     * 
     * The <code>-Xss</code> option does not work on Solaris, only <code>-XX:ThreadStackSize</code>.
     */
    private String threadStackSize;

    /**
     * JVM options used to define system properties.
     * 
     * For example:
     * 
     * -Dcatalina.base=/path/to/tomcat
     */
    private ArrayList<String> systemProperties = new ArrayList<String>();

    /**
     * The option for setting CompressedClassSpaceSize.
     * 
     * <pre>
     * -XX:CompressedClassSpaceSize=768m
     * </pre>
     */
    private String compressedClassSpaceSizeOption;

    /**
     * Maximum Metaspace (<code>-XX:MaxMetaspaceSize</code>). For example:
     * 
     * <pre>
     * -XX:MaxMetaspaceSize=2048m
     * </pre>
     */
    private String maxMetaspaceSize;

    /**
     * Maximum heap space. Specified with the <code>-Xmx</code> or <code>-XX:MaxHeapSize</code> option. For example:
     * 
     * <pre>
     * -Xmx1024m
     * -XX:MaxHeapSize=1234567890
     * </pre>
     */
    private String maxHeapSize;

    /**
     * Option to enable/disable compressed object pointers. For example:
     * 
     * <pre>
     * -XX:-UseCompressedOops
     * </pre>
     */
    private String useCompressedOops;

    /**
     * Option to enable/disable compressed class pointers. For example:
     * 
     * <pre>
     * -XX:-UseCompressedClassPointers
     * </pre>
     */
    private String useCompressedClassPointers;

    /**
     * Undefined JVM options.
     */
    private ArrayList<String> undefined = new ArrayList<String>();

    /**
     * Convert JVM argument string to JVM options.
     * 
     * @param jvmArgs
     *            The JVM arguments.
     */
    public JvmOptions(String jvmArgs) {
        super();
        if (jvmArgs != null) {
            String[] options = jvmArgs.split("(?<!^)(?=-)");
            for (int i = 0; i < options.length; i++) {
                String option = options[i].trim();
                if (option.matches("^-agentlib:jdwp=transport=dt_socket.+$")) {
                    jpdaSocketTransport = option;
                } else if (option.matches("^-ABRT.+$")) {
                    abrt = option;
                } else if (option.matches("^-XX:CompressedClassSpaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    compressedClassSpaceSizeOption = option;
                } else if (option.matches("^-D.+$")) {
                    systemProperties.add(option);
                } else if (option.matches("^-X(mx|X:MaxHeapSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxHeapSize = option;
                } else if (option.matches("^-XX:MaxMetaspaceSize=" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    maxMetaspaceSize = option;
                } else if (option.matches("^-(X)?(ss|X:ThreadStackSize=)" + JdkRegEx.OPTION_SIZE_BYTES + "$")) {
                    threadStackSize = option;
                } else if (option.matches("^-XX:[\\-+]UseCompressedClassPointers$")) {
                    useCompressedClassPointers = option;
                } else if (option.matches("^-XX:[\\-+]UseCompressedOops$")) {
                    useCompressedOops = option;
                } else {
                    undefined.add(option);
                }
            }
        }
    }

    public String getAbrt() {
        return abrt;
    }

    public ArrayList<String> getSystemProperties() {
        return systemProperties;
    }

    public ArrayList<String> getUnknown() {
        return undefined;
    }

    public String getCompressedClassSpaceSize() {
        return compressedClassSpaceSizeOption;
    }

    public String getThreadStackSize() {
        return threadStackSize;
    }

    public String getJpdaSocketTransport() {
        return jpdaSocketTransport;
    }

    public String getMaxMetaspaceSize() {
        return maxMetaspaceSize;
    }

    public String getMaxHeapSize() {
        return maxHeapSize;
    }

    /**
     * @return True if compressed object references are disabled, false otherwise.
     */
    public boolean isUseCompressedOopsDisabled() {
        return useCompressedOops != null && useCompressedOops.matches("-XX:\\-UseCompressedOops");
    }

    /**
     * @return True if compressed object references are disabled, false otherwise.
     */
    public boolean isUseCompressedClassPointersDisabled() {
        return useCompressedClassPointers != null
                && useCompressedClassPointers.matches("-XX:\\-UseCompressedClassPointers");
    }
}

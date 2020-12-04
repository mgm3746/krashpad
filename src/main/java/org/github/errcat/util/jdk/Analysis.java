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
package org.github.errcat.util.jdk;

import org.github.errcat.util.Constants;
import org.github.errcat.util.ErrUtil;

/**
 * Analysis constants.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public enum Analysis {

    /**
     * Property key for JDK debug symbols not installed.
     */
    ERROR_DEBUGGING_SYMBOLS("error.jdk.debug.symbols"),

    /**
     * Property key for AdoptOpenJDK build of OpenJDK.
     */
    INFO_ADOPTOPENJDK_POSSIBLE("info.adoptopenjdk.possible"),

    /**
     * Property key for a JDK that is more than 1 year older than the latest release.
     */
    INFO_JDK_ANCIENT("info.jdk.ancient"),

    /**
     * Property key for Red Hat build of OpenJDK on CentOS.
     */
    INFO_RH_BUILD_CENTOS("info.rh.build.centos"),

    /**
     * Property key for Red Hat build of OpenJDK Linux zip install.
     */
    INFO_RH_BUILD_LINUX_ZIP("info.rh.build.linux.zip"),

    /**
     * Property key for a JDK that is possibly a RH build.
     */
    INFO_RH_BUILD_POSSIBLE("info.rh.build.possible"),

    /**
     * Property key for a JDK that is not a RH build.
     */
    INFO_RH_BUILD_NOT("info.rh.build.not"),

    /**
     * Property key for Red Hat build of OpenJDK rpm install.
     */
    INFO_RH_BUILD_RPM("info.rh.build.rpm"),

    /**
     * Property key for Red Hat build of OpenJDK Windows zip install.
     */
    INFO_RH_BUILD_WINDOWS_ZIP("info.rh.build.windows.zip"),

    /**
     * Property key for the stack not containing any VM code.
     */
    INFO_STACK_NO_VM_CODE("info.stack.no.vm.code"),

    /**
     * Property key for a JDK that is not the latest JDK release.
     */
    WARN_JDK_NOT_LATEST("warn.jdk.not.latest"),

    /**
     * Property key for a JDK that is not a Long Term Support (LTS) version.
     */
    WARN_JDK_NOT_LTS("warn.jdk.not.lts"),

    /**
     * Property key for unidentified line(s) needing reporting.
     */
    WARN_UNIDENTIFIED_LOG_LINE_REPORT("warn.unidentified.log.line.report");

    private String key;

    private Analysis(final String key) {
        this.key = key;
    ***REMOVED***

    /**
     * @return Analysis property file key.
     */
    public String getKey() {
        return key;
    ***REMOVED***

    /**
     * @return Analysis property file value.
     */
    public String getValue() {
        return ErrUtil.getPropertyValue(Constants.ANALYSIS_PROPERTY_FILE, key);
    ***REMOVED***

    @Override
    public String toString() {
        return this.getKey();
    ***REMOVED***
***REMOVED***

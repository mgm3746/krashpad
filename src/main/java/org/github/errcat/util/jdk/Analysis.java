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
     * Property key for a JDK that is not a RH build.
     */
    INFO_JDK_NOT_RH_BUILD("info.jdk.not.rh.build"),

    /**
     * Property key for unrecognized Red Hat build.
     */
    INFO_RH_BUILD_UNKNOWN("info.rh.build.unknown"),

    /**
     * Property key for Red Hat build of OpenJDK rpm install.
     */
    INFO_RH_INSTALL_RPM("info.rh.install.rpm"),

    /**
     * Property key for Red Hat build of OpenJDK zip install.
     */
    INFO_RH_INSTALL_ZIP("info.rh.install.zip"),

    /**
     * Property key for Red Hat build of OpenJDK unsupported OS.
     */
    INFO_RH_UNSUPPORTED_OS("info.rh.unsupported.os"),

    /**
     * Property key for the stack not containing any VM code.
     */
    INFO_STACK_NO_VM_CODE("info.stack.no.vm.code"),

    /**
     * Property key for not using the latest JDK release.
     */
    WARN_JDK_NOT_LATEST("warn.jdk.not.latest"),

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

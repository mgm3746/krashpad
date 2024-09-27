/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2024 Mike Millson                                                                               *
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

import org.github.krashpad.util.KrashUtil;
import org.github.krashpad.util.jdk.JdkRegEx;

/**
 * <p>
 * JDK release information.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class Release {

    /**
     * Release build date.
     */
    private Date buildDate;

    /**
     * Flag to indicate if build date is an estimate (unknown).
     */
    private boolean isBuildDateEstimate;

    /**
     * Release number (1..x).
     */
    private int number;

    /**
     * The release version string.
     */
    private String version;

    public Release(String buildDate, int number, String version) {
        super();
        if (buildDate.matches(JdkRegEx.BUILD_DATE)) {
            this.buildDate = KrashUtil.getDate(buildDate + " 00:00:00");
            this.isBuildDateEstimate = true;
        } else if (buildDate.matches(JdkRegEx.BUILD_DATETIME)) {
            this.buildDate = KrashUtil.getDate(buildDate);
            this.isBuildDateEstimate = false;
        } else if (buildDate.matches(JdkRegEx.BUILD_DATE_21)) {
            this.buildDate = KrashUtil.getDate21(buildDate + "T00:00:00Z");
            this.isBuildDateEstimate = true;
        } else if (buildDate.matches(JdkRegEx.BUILD_DATETIME_21)) {
            this.buildDate = KrashUtil.getDate21(buildDate);
            this.isBuildDateEstimate = false;
        }
        this.number = number;
        this.version = version;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public int getNumber() {
        return number;
    }

    public String getVersion() {
        return version;
    }

    public boolean isBuildDateEstimate() {
        return isBuildDateEstimate;
    }
}

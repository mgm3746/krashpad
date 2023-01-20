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

import java.util.Date;

import org.github.krashpad.util.ErrUtil;

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
     * Release number (1..x).
     */
    private int number;

    /**
     * The release version string.
     */
    private String version;

    public Release(String buildDate, int number, String version) {
        super();
        this.buildDate = ErrUtil.getDate(buildDate);
        this.number = number;
        this.version = version;
    ***REMOVED***

    public Date getBuildDate() {
        return buildDate;
    ***REMOVED***

    public int getNumber() {
        return number;
    ***REMOVED***

    public String getVersion() {
        return version;
    ***REMOVED***
***REMOVED***

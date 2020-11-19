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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
     * Release date.
     */
    private Date date;

    /**
     * Release number (1..x).
     */
    private int number;

    /**
     * The release version string.
     */
    private String version;

    public Release(String releaseDate, int number, String version) {
        super();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        try {
            this.date = formatter.parse(releaseDate);
        ***REMOVED*** catch (ParseException e) {
            throw new RuntimeException("Bad release date: " + releaseDate);
        ***REMOVED***
        this.number = number;
        this.version = version;
    ***REMOVED***

    public Date getDate() {
        return date;
    ***REMOVED***

    public int getNumber() {
        return number;
    ***REMOVED***

    public String getVersion() {
        return version;
    ***REMOVED***
***REMOVED***

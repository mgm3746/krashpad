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
package org.github.errcat.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Common vm collection utility methods and constants.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ErrUtil {

    /**
     * <p>
     * Regular expression for valid JVM start date/time in yyyy-MM-dd HH:mm:ss,SSS format (see
     * <code>SimpleDateFormat</code> for date and time pattern definitions).
     * </p>
     * 
     * For example:
     * 
     * <pre>
     * 2009-09-18 00:00:08,172
     * </pre>
     */
    public static final String START_DATE_TIME_REGEX = "^(\\d{4***REMOVED***)-(\\d{2***REMOVED***)-(\\d{2***REMOVED***) (\\d{2***REMOVED***):(\\d{2***REMOVED***):(\\d{2***REMOVED***),"
            + "(\\d{3***REMOVED***)$";

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private ErrUtil() {

    ***REMOVED***

    /**
     * Check if the <code>TagHtmlEvent</code> is a start tag.
     * 
     * @param htmlTag
     *            The html tag.
     * @return true if a start tag, false otherwise.
     */
    public static final boolean isHtmlEventStartTag(String htmlTag) {
        return htmlTag.matches("^<[^/].+$");
    ***REMOVED***

    /**
     * Determine whether the first JVM event timestamp indicates a partial log file or events that were not in a
     * recognizable format.
     * 
     * @param firstTimestamp
     *            The first JVM event timestamp (milliseconds).
     * @return True if the first timestamp is within the first timestamp threshold, false otherwise.
     */
    public static final boolean isPartialLog(long firstTimestamp) {
        return (firstTimestamp > Constants.FIRST_TIMESTAMP_THRESHOLD * 1000);
    ***REMOVED***

    /**
     * Retrieve the value for a given property file and key.
     * 
     * @param propertyFile
     *            The property file.
     * @param key
     *            The property key.
     * @return The value for the given property file and key.
     */
    public static final String getPropertyValue(String propertyFile, String key) {
        ResourceBundle rb = ResourceBundle.getBundle("META-INF" + System.getProperty("file.separator") + propertyFile);
        return rb.getString(key);
    ***REMOVED***

    /**
     * Add milliseconds to a given <code>Date</code>.
     * 
     * @param start
     *            Start <code>Date</code>.
     * @param timestamp
     *            Time interval in milliseconds.
     * @return start <code>Date</code> + timestamp.
     */
    public static final Date getDatePlusTimestamp(Date start, long timestamp) {
        long millis = start.getTime() + timestamp;
        return new Date(millis);
    ***REMOVED***

    /**
     * Calculate the number of milliseconds between two dates.
     * 
     * @param start
     *            Start <code>Date</code>.
     * @param end
     *            End <code>Date</code>.
     * @return The interval between two dates in milliseconds.
     */
    public static final long millisDiff(Date start, Date end) {
        return end.getTime() - start.getTime();
    ***REMOVED***

    /**
     * Check to see if the entered startdatetime is a valid format.
     * 
     * @param startDateTime
     *            The startdatetime <code>String</code>.
     * @return true if a valid format, false otherwise.
     */
    public static final boolean isValidStartDateTime(String startDateTime) {
        return startDateTime.matches(START_DATE_TIME_REGEX);
    ***REMOVED***

    /**
     * Calculate the number of whole days (24 hour periods) for a given number of milliseconds
     * 
     * @param timestamp
     *            Time in milliseconds.
     * @return the number of whole days.
     */
    public static final int daysInMilliSeconds(long timestamp) {
        BigDecimal days = new BigDecimal(timestamp);
        return days.divideToIntegralValue(new BigDecimal(1000 * 60 * 60 * 24)).intValue();
    ***REMOVED***

    /**
     * Calculate the number of whole days (24 hour periods) for a given number of milliseconds
     * 
     * @param timestamp
     *            Time in milliseconds.
     * @return the number of whole days.
     */
    public static final int dayDiff(Date start, Date end) {
        long millisDiff = millisDiff(start, end);
        return daysInMilliSeconds(millisDiff);
    ***REMOVED***
***REMOVED***

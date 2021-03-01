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
package org.github.krashpad.util;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.github.krashpad.util.jdk.JdkRegEx;

/**
 * Common vm collection utility methods and constants.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ErrUtil {

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
     * Retrieve the value for a given property file and key.
     * 
     * @param propertyFile
     *            The property file.
     * @param key
     *            The property key.
     * @return The value for the given property file and key.
     */
    public static final String getPropertyValue(String propertyFile, String key) {
        ResourceBundle rb = ResourceBundle.getBundle("META-INF." + propertyFile);
        return rb.getString(key);
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
        long millisDiff = 0;
        if (start != null && end != null) {
            millisDiff = end.getTime() - start.getTime();
        ***REMOVED***
        return millisDiff;
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
     * @param start
     *            Start date.
     * @param end
     *            End date.
     * @return The number of days between 2 dates.
     */
    public static final int dayDiff(Date start, Date end) {
        long millisDiff = millisDiff(start, end);
        return daysInMilliSeconds(millisDiff);
    ***REMOVED***

    /**
     * Convert date parts to a <code>Date</code>.
     * 
     * @param MMM
     *            The month.
     * @param d
     *            The day.
     * @param yyyy
     *            The year.
     * @param HH
     *            The hour.
     * @param mm
     *            The minute.
     * @param ss
     *            The seconds.
     * @return The date part strings converted to a <code>Date</code>
     */
    public static final Date getDate(String MMM, String d, String yyyy, String HH, String mm, String ss) {
        if (MMM == null || d == null || yyyy == null || HH == null || mm == null || ss == null) {
            throw new IllegalArgumentException("One or more date parts are missing.");
        ***REMOVED***

        Calendar calendar = Calendar.getInstance();
        // Java Calendar month is 0 based
        switch (MMM) {
        case "Jan":
            calendar.set(Calendar.MONTH, 0);
            break;
        case "Feb":
            calendar.set(Calendar.MONTH, 1);
            break;
        case "Mar":
            calendar.set(Calendar.MONTH, 2);
            break;
        case "Apr":
            calendar.set(Calendar.MONTH, 3);
            break;
        case "May":
            calendar.set(Calendar.MONTH, 4);
            break;
        case "Jun":
            calendar.set(Calendar.MONTH, 5);
            break;
        case "Jul":
            calendar.set(Calendar.MONTH, 6);
            break;
        case "Aug":
            calendar.set(Calendar.MONTH, 7);
            break;
        case "Sep":
            calendar.set(Calendar.MONTH, 8);
            break;
        case "Oct":
            calendar.set(Calendar.MONTH, 9);
            break;
        case "Nov":
            calendar.set(Calendar.MONTH, 10);
            break;
        case "Dec":
            calendar.set(Calendar.MONTH, 11);
            break;
        default:
            throw new IllegalArgumentException("Unexpected month: " + MMM);
        ***REMOVED***
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(d).intValue());
        calendar.set(Calendar.YEAR, Integer.valueOf(yyyy));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HH).intValue());
        calendar.set(Calendar.MINUTE, Integer.valueOf(mm).intValue());
        calendar.set(Calendar.SECOND, Integer.valueOf(ss).intValue());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    ***REMOVED***

    public static final Date getDate(String buildDate) {
        String MMM = null;
        String d = null;
        String yyyy = null;
        String HH = null;
        String mm = null;
        String ss = null;
        Pattern pattern = Pattern.compile(JdkRegEx.BUILD_DATE_TIME);
        Matcher matcher = pattern.matcher(buildDate);
        if (matcher.find()) {
            MMM = matcher.group(1);
            d = matcher.group(2);
            yyyy = matcher.group(3);
            HH = matcher.group(4);
            mm = matcher.group(5);
            ss = matcher.group(6);
        ***REMOVED***
        return getDate(MMM, d, yyyy, HH, mm, ss);
    ***REMOVED***
***REMOVED***

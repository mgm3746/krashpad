/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2022 Mike Millson                                                                               *
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
     * Java linux native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_JAVA_LINUX;

    /**
     * Java windows native libaries.
     */
    public static final List<String> NATIVE_LIBRARIES_JAVA_WINDOWS;

    /**
     * Linux native libraries
     */
    public static final List<String> NATIVE_LIBRARIES_LINUX;

    /**
     * Windows native libraries
     */
    public static final List<String> NATIVE_LIBRARIES_WINDOWS;

    static {
        NATIVE_LIBRARIES_JAVA_LINUX = new ArrayList<String>();
        NATIVE_LIBRARIES_JAVA_LINUX.add("i965_dri.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libawt.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libawt_headless.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libextnet.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libfontmanager.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libjava.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libjavajpeg.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libjimage.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libinstrument.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libjli.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libjvm.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("liblcms.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libmanagement.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libmanagement_ext.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libnet.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libnio.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libsunec.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libverify.so");
        NATIVE_LIBRARIES_JAVA_LINUX.add("libzip.so");

        NATIVE_LIBRARIES_JAVA_WINDOWS = new ArrayList<String>();
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("attach.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("awt.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("dt_shmem.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("dt_socket.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("fontmanager.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("freetype.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("hprof.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("instrument.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("j2pcsc.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("j2pkcs11.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("JAWTAccessBridge-64.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("JavaAccessBridge-64.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jaas_nt.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("java.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("java_crw_demo.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jawt.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jdwp.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jli.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jpeg.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jsdt.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jsound.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jsoundds.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("jvm.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("lcms.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("management.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("mlib_image.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("msvcr100.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("net.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("nio.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("npt.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("sawindbg.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("spashscreen.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("sunec.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("sunmscapi.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("ucrtbase.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("unpack.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("vcruntime140.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("verify.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("WindowsAccessBridge-64.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("w2k_lsa_auth.dll");
        NATIVE_LIBRARIES_JAVA_WINDOWS.add("zip.dll");

        NATIVE_LIBRARIES_LINUX = new ArrayList<String>();
        NATIVE_LIBRARIES_LINUX.add("im-ibus.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libdconfsettings.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libdw-0.160.so");
        NATIVE_LIBRARIES_LINUX.add("libelf-0.160.so");
        NATIVE_LIBRARIES_LINUX.add("libfreeblpriv3.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libgvfscommon.so");
        NATIVE_LIBRARIES_LINUX.add("libgvfsdbus.so");
        NATIVE_LIBRARIES_LINUX.add("libnspr4.so");
        NATIVE_LIBRARIES_LINUX.add("libnss3.so");
        NATIVE_LIBRARIES_LINUX.add("libnssutil3.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_dns-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_dns-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libnsl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libplc4.so");
        NATIVE_LIBRARIES_LINUX.add("libplds4.so");
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-bmp.so");
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-gif.so");
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-svg.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libresolv-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libresolv-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libsmime3.so");
        NATIVE_LIBRARIES_LINUX.add("libssl3.so");
        NATIVE_LIBRARIES_LINUX.add("libutil-2.28.so");

        NATIVE_LIBRARIES_WINDOWS = new ArrayList<String>();
        NATIVE_LIBRARIES_WINDOWS.add("ADVAPI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("bcryptPrimitives.dll");
        NATIVE_LIBRARIES_WINDOWS.add("COMCTL32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cfgmgr32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("combase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("DBGHELP.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dbgcore.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("GDI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("gdi32full.dll");
        NATIVE_LIBRARIES_WINDOWS.add("KERNEL32.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("KERNELBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msvcp_win.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msvcrt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntdll.dll");
        NATIVE_LIBRARIES_WINDOWS.add("PSAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("RPCRT4.dll");
        NATIVE_LIBRARIES_WINDOWS.add("sechost.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USER32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ucrtbase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("VERSION.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMMBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMM.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WSOCK32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WS2_32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("win32u.dll");
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
     * Make default constructor private so the class cannot be instantiated.
     */
    private ErrUtil() {

    ***REMOVED***
***REMOVED***

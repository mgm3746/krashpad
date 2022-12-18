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
 * Utility methods and constants.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class ErrUtil {

    /**
     * Linux native libraries
     */
    public static final List<String> NATIVE_LIBRARIES_LINUX;

    /**
     * Java linux native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_LINUX_JAVA;

    /**
     * Windows native libraries
     */
    public static final List<String> NATIVE_LIBRARIES_WINDOWS;

    /**
     * Java windows native libaries.
     */
    public static final List<String> NATIVE_LIBRARIES_WINDOWS_JAVA;

    static {
        NATIVE_LIBRARIES_LINUX = new ArrayList<String>();
        // mesa-dri-drivers
        NATIVE_LIBRARIES_LINUX.add("i965_dri.so");
        // ibus-gtk*
        NATIVE_LIBRARIES_LINUX.add("im-ibus.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("ld-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.28.so");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add("libc-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.28.so");
        // dconf
        NATIVE_LIBRARIES_LINUX.add("libdconfsettings.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libcrypt-2.17.so");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add("libdl-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.28.so");
        // elfutils-devel
        NATIVE_LIBRARIES_LINUX.add("libdw-0.160.so");
        NATIVE_LIBRARIES_LINUX.add("libdw-0.176.so");
        // elfutils-libelf-devel
        NATIVE_LIBRARIES_LINUX.add("libelf-0.160.so");
        NATIVE_LIBRARIES_LINUX.add("libelf-0.176.so");
        // nss-softokn-freebl
        NATIVE_LIBRARIES_LINUX.add("libfreebl3.so");
        // nss-softokn-freebl
        NATIVE_LIBRARIES_LINUX.add("libfreeblpriv3.so");
        // jss
        NATIVE_LIBRARIES_LINUX.add("libjss4.so");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add("libm-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.28.so");
        // gvfs-client
        NATIVE_LIBRARIES_LINUX.add("libgvfscommon.so");
        // gvfs-client
        NATIVE_LIBRARIES_LINUX.add("libgvfsdbus.so");
        // nspr
        NATIVE_LIBRARIES_LINUX.add("libnspr4.so");
        // nss
        NATIVE_LIBRARIES_LINUX.add("libnss3.so");
        // nss-util
        NATIVE_LIBRARIES_LINUX.add("libnssutil3.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libnss_dns-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_dns-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libnsl-2.17.so");
        // nspr
        NATIVE_LIBRARIES_LINUX.add("libplc4.so");
        // nspr
        NATIVE_LIBRARIES_LINUX.add("libplds4.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-bmp.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-gif.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-svg.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libresolv-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libresolv-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("librt-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.28.so");
        // nss
        NATIVE_LIBRARIES_LINUX.add("libsmime3.so");
        // nss-softokn
        NATIVE_LIBRARIES_LINUX.add("libsoftokn3.so");
        // nss
        NATIVE_LIBRARIES_LINUX.add("libssl3.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libutil-2.28.so");
        // p11-kit-trust
        NATIVE_LIBRARIES_LINUX.add("p11-kit-trust.so");
        // softhsm
        NATIVE_LIBRARIES_LINUX.add("libsofthsm2.so");

        NATIVE_LIBRARIES_LINUX_JAVA = new ArrayList<String>();
        // java-(1.8.0|11|17)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libattach.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt_headless.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libextnet.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libfontmanager.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libj2pkcs11.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjava.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjavajpeg.so");
        // java-(1.8.0-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjava_crw_demo.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjimage.so");
        // java-(1.8.0-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libinstrument.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjli.so");
        // java-17-openjdk-headless: Vector API
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjsvml.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjvm.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("liblcms.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement.so");
        // java-(11|17)-openjdk-headless
        // uncomment when seen in crash
        // NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement_agent.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement_ext.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libnet.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libnio.so");
        // java-(1.8.0|11)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsunec.so");
        // java-(1.8.0|11)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsystemconf.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libverify.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libzip.so");

        NATIVE_LIBRARIES_WINDOWS = new ArrayList<String>();
        NATIVE_LIBRARIES_WINDOWS.add("apphelp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ADVAPI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("bcrypt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("bcryptPrimitives.dll");
        NATIVE_LIBRARIES_WINDOWS.add("COMCTL32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("CRYPT32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("CRYPTBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cfgmgr32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("combase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cryptdll.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cryptsp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("CRYPTSP.dll");
        NATIVE_LIBRARIES_WINDOWS.add("DBGHELP.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dbgcore.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dhcpcsvc.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dhcpcsvc6.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("DNSAPI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("GDI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("fwpuclnt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("gdi32full.dll");
        NATIVE_LIBRARIES_WINDOWS.add("iertutil.dll");
        NATIVE_LIBRARIES_WINDOWS.add("IPHLPAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("KERNEL32.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("KERNELBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("kerberos.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("kernel.appcore.dll");
        NATIVE_LIBRARIES_WINDOWS.add("MSASN1.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msv1_0.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("msvcp_win.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msvcrt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("mswsock.dll");
        NATIVE_LIBRARIES_WINDOWS.add("napinsp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ncrypt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("netutils.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NLAapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NSI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntdll.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntdsapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NtlmShared.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntmarta.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NTASN1.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ole32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("OLEAUT32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("PSAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("powrprof.dll");
        NATIVE_LIBRARIES_WINDOWS.add("profapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("RPCRT4.dll");
        NATIVE_LIBRARIES_WINDOWS.add("rasadhlp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("rsaenh.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SHELL32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SSPICLI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("sechost.dll");
        NATIVE_LIBRARIES_WINDOWS.add("secur32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("shcore.dll");
        NATIVE_LIBRARIES_WINDOWS.add("shlwapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("srvcli.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USER32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USERENV.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ucrtbase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("urlmon.dll");
        NATIVE_LIBRARIES_WINDOWS.add("VERSION.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINHTTP.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMMBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMM.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WSOCK32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WS2_32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("windows.storage.dll");
        NATIVE_LIBRARIES_WINDOWS.add("win32u.dll");
        NATIVE_LIBRARIES_WINDOWS.add("winrnr.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wshbth.dll");

        NATIVE_LIBRARIES_WINDOWS_JAVA = new ArrayList<String>();
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("attach.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("awt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("dt_shmem.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("dt_socket.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("fontmanager.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("freetype.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("hprof.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("instrument.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("j2pcsc.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("j2pkcs11.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("JAWTAccessBridge-64.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("JavaAccessBridge-64.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jaas_nt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("java.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("java_crw_demo.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jawt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jdwp.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jimage.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jli.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jpeg.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsdt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsound.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsoundds.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jvm.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("lcms.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("management.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("management_ext.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("mlib_image.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("msvcr100.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("msvcp140.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("net.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("nio.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("npt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("sawindbg.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("spashscreen.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("sunec.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("sunmscapi.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("ucrtbase.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("unpack.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("vcruntime140.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("VCRUNTIME140.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("verify.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("WindowsAccessBridge-64.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("w2k_lsa_auth.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("zip.dll");
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

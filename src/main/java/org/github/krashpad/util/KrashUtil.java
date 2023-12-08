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
public class KrashUtil {

    /**
     * JBoss native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_JBOSS;

    /**
     * Linux native libraries
     */
    public static final List<String> NATIVE_LIBRARIES_LINUX;

    /**
     * Java linux native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_LINUX_JAVA;

    /**
     * Oracle JDK native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_ORACLE;

    /**
     * Tomcat native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_TOMCAT;

    /**
     * VMware native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_VMWARE;

    /**
     * Windows native libraries
     */
    public static final List<String> NATIVE_LIBRARIES_WINDOWS;

    /**
     * Java windows native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_WINDOWS_JAVA;

    /**
     * Linux native library home.
     */
    public static final String NATIVE_LIBRARY_LINUX_HOME = "/usr/lib64/";

    /**
     * Windows system native library home regular expression.
     */
    public static final String NATIVE_LIBRARY_WINDOWS_SYSTEM_HOME = "C:\\\\Windows\\\\((system|System|SYSTEM)32|"
            + "WinSxS\\\\amd64_microsoft\\.windows\\.common\\-controls_.+)\\\\";

    static {
        // ***** JBOSS *****
        NATIVE_LIBRARIES_JBOSS = new ArrayList<String>();
        NATIVE_LIBRARIES_JBOSS.add("libartemis-native-64.so");

        // ***** LINUX OS *****
        NATIVE_LIBRARIES_LINUX = new ArrayList<String>();
        // glibc-gconv-extra
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "EUC-JP.so");
        // glibc (/usr/lib64/gconv/)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "ISO8859-1.so");
        // mesa-dri-drivers
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "dri/i965_dri.so");
        // ibus-gtk*
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gtk-3.0/3.0.0/immodules/im-ibus.so");
        // mesa-dri-drivers
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "iris_dri.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "ld-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "ld-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "ld-2.28.so");
        // libaio
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libaio.so.1.0.1");
        // apr (/usr/lib64/):
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libapr-1.so.0.6.3");
        // alsa-lib: library to interface with ALSA in the Linux kernel and virtual devices
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libasound.so.2.0.0");
        // alsa-plugins-pulseaudio: library for accessing a PulseAudio sound daemon (to play and record sound across
        // network)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libasound_module_pcm_pulse.so");
        // aspell
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libaspell.so.15.1.5");
        // atk
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libatk-1.0.so.0.22810.1");
        // at-spi2-atk
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libatk-bridge-2.0.so.0.0.0");
        // libatomic
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libatomic.so.1.2.0");
        // at-spi2-core
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libatspi.so.0.0.1");
        // libattr
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libattr.so.1.1.0");
        // lib-audit (audit framework)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libaudit.so.1.0.0");
        // libavahi-client (DNS service discovery and multicast DNS)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libavahi-client.so.3.2.9");
        // avahi-libs (libraries need to run programs that use avahi)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libavahi-common.so.3.5.3");
        // brotli (compression library)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libbrotlicommon.so.1.0.6");
        // brotli (compression library)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libbrotlidec.so.1.0.6");
        // bzip2-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libbz2.so.1.0.6");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libc-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libc-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libc-2.28.so");
        // cairo
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcairo.so.2.11512.0");
        // cairo-gobject
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcairo-gobject.so.2.11512.0");
        // libpcap
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcap.so.2.22");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcap.so.2.26");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcap.so.2.48");
        // libpcap-ng
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcap-ng.so.0.0.0");
        // libcom-err
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcom_err.so.2.1");
        // libcroco
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcroco-0.6.so.3.0.1");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcrypt-2.17.so");
        // cups-libs (native cups)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcups.so.2");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcupsimage.so.2");
        // dconf
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gio/modules/libdconfsettings.so");
        // libdatrie
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdatrie.so.1.3.2");
        // dbus-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdbus-1.so.3.14.14");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdbus-1.so.3.19.7");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdl-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdl-2.28.so");
        // libblkid
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libblkid.so.1.1.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdrm.so.2.4.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdrm_amdgpu.so.1.0.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdrm_nouveau.so.2.0.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdrm_radeon.so.1.0.1");
        // elfutils-devel
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdw-0.160.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libdw-0.176.so");
        // libglvnd-egl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libEGL.so.1.1.0");
        // mesa-libEGL
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libEGL_mesa.so.0.0.0");
        // elfutils-libelf-devel
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libelf-0.160.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libelf-0.176.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libelf-0.187.so");
        // enchant2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libenchant-2.so.2.2.3");
        // libepoxy
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libepoxy.so.0.0.0");
        // expat
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libexpat.so.1.6.7");
        // flac-libs: reference implementation for FLAC (Free Lossless Audio Codec), an audio coding format for
        // lossless compression of digital audio
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libFLAC.so.8.3.0");
        // libffi
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libffi.so.6.0.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libffi.so.6.0.2");
        // fontconfig
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libfontconfig.so.1.12.0");
        // nss-softokn-freebl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libfreebl3.so");
        // nss-softokn-freebl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libfreeblpriv3.so");
        // freetype
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libfreetype.so.6.14.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libfreetype.so.6.16.1");
        // fribidi
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libfribidi.so.0.4.0");
        // libglvnd-glx
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libGL.so.1.7.0");
        // libglvnd-gles
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libGLESv2.so.2.1.0");
        // libglvnd-glx
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libGLX.so.0.0.0");
        // libglvnd
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libGLdispatch.so.0.0.0");
        // mesa-libglapi
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgbm.so.1.0.0");
        // libgcc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcc_s-4.4.7-20120601.so.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcc_s-4.8.5-20150702.so.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcc_s-8-20191121.so.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcc_s-8-20200928.so.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcc_s-8-20210514.so.1");
        // libgcrypt
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcrypt.so.11.8.2");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcrypt.so.20.2.3");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgcrypt.so.20.2.5");
        // gtk3
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgdk-3.so.0.2200.30");
        // gdk-pixbuf2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgdk_pixbuf-2.0.so.0.3612.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgio-2.0.so.0.5600.4");
        // mesa-libglapi
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libglapi.so.0.0.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libglib-2.0.so.0.5600.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libglib-2.0.so.0.5600.4");
        // libglvnd-opengl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libOpenGL.so.0.0.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgmodule-2.0.so.0.5600.4");
        // gmp
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgmp.so.10.3.2");
        // gnome-keyring
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgnome-keyring.so.0.2.0");
        // gnutls
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgnutls.so.30.24.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgnutls.so.30.28.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgnutls.so.30.28.2");
        // glib2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgobject-2.0.so.0.5600.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgobject-2.0.so.0.5600.4");
        // libgpg-error
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgpg-error.so.0.10.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgpg-error.so.0.24.2");
        // graphite2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgraphite2.so.3.0.1");
        // libgs (ghostscript)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgs.so.9.27");
        // gsm: library for lossy speech compression
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgsm.so.1.0.17");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgssapi_krb5.so.2.2");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstapp-1.0.so.0.1601.0");
        // gstreamer1
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstbase-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstallocators-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstaudio-1.0.so.0.1601.0");
        // gstreamer1
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstbase-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstfft-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstgl-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstpbutils-1.0.so.0.1601.0");
        // gstreamer1
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstreamer-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgsttag-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgstvideo-1.0.so.0.1601.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgthread-2.0.so.0.5600.4");
        // gtk3
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libgtk-3.so.0.2200.30");
        // gvfs-client
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gvfs/libgvfscommon.so");
        // gvfs-client
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gio/modules/libgvfsdbus.so");
        // harfbuzz
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libharfbuzz.so.0.10705.0");
        // harfbuzz-icu
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libharfbuzz-icu.so.0.10705.0");
        // nettle
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libhogweed.so.4.5");
        // hyphen
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libhyphen.so.0.3.0");
        // libICE
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libICE.so.6.3.0");
        // libibus
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libibus-1.0.so.5.0.519");
        // libicu
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libicudata.so.60.3");
        // libicu
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libicui18n.so.60.3");
        // libicu
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libicuuc.so.60.3");
        // libijs (IJS raster image library)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libijs-0.35.so");
        // glibc-gconv-extra
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libJIS.so");
        // webkit2gtk3-jsc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjavascriptcoregtk-4.0.so.18.13.7");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjavascriptcoregtk-4.0.so.18.20.11");
        // jbigkit-libs (compression/decompression)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjbig.so.2.1");
        // jbig2dec-libs (JBIG2 image decompression format decoder)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjbig2dec.so.0.0.0");
        // libjpeg-turbo
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjpeg.so.62.1.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjpeg.so.62.2.0");
        // jss
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libjss4.so");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libk5crypto.so.3.1");
        // keyutils-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libkeyutils.so.1.5");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libkeyutils.so.1.6");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libkrb5.so.3.3");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libkrb5support.so.0.1");
        // llvm-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libLLVM-14.so");
        // lcms2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "liblcms2.so.2.0.8");
        // libtool-ltdl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libltdl.so.7.3.0");
        // xz-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "liblzma.so.5.2.2");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "liblzma.so.5.2.4");
        // lz4-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "liblz4.so.1.8.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "liblz4.so.1.8.3");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libm-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libm-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libm-2.28.so");
        // libmount
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libmount.so.1.1.0");
        // libidn (international string handling)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libidn.so.11.6.18");
        // libidn2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libidn2.so.0.3.6");
        // nettle
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnettle.so.6.5");
        // libnotify
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnotify.so.4.0.0");
        // nspr
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnspr4.so");
        // nss
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss3.so");
        // nss-sysinit
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnsssysinit.so");
        // nss-util
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnssutil3.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_compat-2.17.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_dns-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_dns-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_files-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_files-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_files-2.28.so");
        // systemd-libs (/usr/lib64/):
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_myhostname.so.2");
        // glibc, libnsl (/usr/lib64/): the public client interface for NIS(YP). libnsl breaks out NIS library that used
        // to be in glibc.
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnsl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnsl-2.28.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnsl.so.2.0.0");
        // nss_nis (/usr/lib64/): Name Service Switch (NSS) module using NIS.
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_nis.so.2.0.0");
        // sssd-client
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnss_sss.so.2");
        // numactl-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libnuma.so.1.0.0");
        // libvogg: reference implementation to create, decode, and work with Ogg multimedia container
        // format bitstreams
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libogg.so.0.8.2");
        // openjpeg2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libopenjp2.so.2.3.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libopenjp2.so.2.4.0");
        // orc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "liborc-0.4.so.0.28.0");
        // p11-kit
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libp11-kit.so.0.3.0");
        // pam (pluggable authentication modules)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpam.so.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpam.so.0.83.1");
        // pango
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpango-1.0.so.0.4200.3");
        // pango
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpangocairo-1.0.so.0.4200.3");
        // pango
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpangoft2-1.0.so.0.4200.3");
        // libpaper (getting information on page sizes)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpaper.so.1.1.2");
        // pcre
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpcre.so.1.2.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpcre.so.1.2.10");
        // pcre2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpcre2-8.so.0.7.1");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gdk-pixbuf-2.0/2.10.0/loaders/libpixbufloader-bmp.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gdk-pixbuf-2.0/2.10.0/loaders/libpixbufloader-gif.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "gdk-pixbuf-2.0/2.10.0/loaders/libpixbufloader-svg.so");
        // pixman
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpixman-1.so.0.38.4");
        // nspr
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libplc4.so");
        // nspr
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libplds4.so");
        // libpng
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpng15.so.15.13.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpng16.so.16.34.0");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpthread-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpthread-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpthread-2.28.so");
        // pulseaudio-libs: sound proxy server for sound applications
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpulse.so.0.23.0");
        // pulseaudio-libs: sound proxy server for sound applications
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libpulsecommon-14.0.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libresolv-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libresolv-2.28.so");
        // librsvg2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "librsvg-2.so.2.42.7");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "librt-2.12.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "librt-2.17.so");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "librt-2.28.so");
        // libSM
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libSM.so.6.0.1");
        // libsecret
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsecret-1.so.0.0.0");
        // nss
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsmime3.so");
        // softhsm
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsofthsm2.so");
        // libasyncns: library for asynchronous name service queries
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libasyncns.so.0.3.1");
        // libselinux
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libselinux.so.1");
        // libsndfile: library for reading and writing files containing sampled audio data
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsndfile.so.1.0.28");
        // nss-softokn
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsoftokn3.so");
        // libsoup
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsoup-2.4.so.1.8.0");
        // sqlite-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsqlite3.so.0.8.6");
        // nss
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libssl3.so");
        // systemd-libs (/usr/lib64/):
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsystemd.so.0.6.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libsystemd.so.0.23.0");
        // libtasn1
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libtasn1.so.6.5.5");
        // libthai
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libthai.so.0.3.0");
        // libtiff (tiff image library)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libtiff.so.5.3.0");
        // ncurses-libs
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libtinfo.so.6.1");
        // libtirpc (/usr/lib64/): a port of Suns Transport-Independent RPC library.
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libtirpc.so.3.0.0");
        // libunistring
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libunistring.so.2.1.0");
        // glibc
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libutil-2.28.so");
        // libuuid
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libuuid.so.1.3.0");
        // libvorbis: reference implementation for the Vorbis codec
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libvorbis.so.0.4.8");
        // libvorbis: reference implementation for the Vorbis codec
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libvorbisenc.so.2.0.11");
        // libwayland-client
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwayland-client.so.0.3.0");
        // libwayland-cursor
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwayland-cursor.so.0.0.0");
        // libwayland-egl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwayland-egl.so.1.0.0");
        // libwayland-server
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwayland-server.so.0.1.0");
        // webkit2gtk3
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwebkit2gtk-4.0.so.37.37.6");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwebkit2gtk-4.0.so.37.56.11");
        // libwebp
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwebp.so.7.0.2");
        // libwebp
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwebpdemux.so.2.0.4");
        // woff2 (library for converting fonts from TTF WOFF 2.0 format)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwoff2common.so.1.0.2");
        // woff2 (library for converting fonts from TTF WOFF 2.0 format)
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libwoff2dec.so.1.0.2");
        // libX11
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libX11.so.6.3.0");
        // libX11-xcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libX11-xcb.so.1.0.0");
        // libXau
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXau.so.6.0.0");
        // libXcomposite
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXcomposite.so.1.0.0");
        // libXcursor
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXcursor.so.1.0.2");
        // libXdamage
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXdamage.so.1.1.0");
        // libXext
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXext.so.6.4.0");
        // libXfixes
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXfixes.so.3.1.0");
        // libXinerama
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXinerama.so.1.0.0");
        // libXi
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXi.so.6.1.0");
        // libXrandr
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXrandr.so.2.2.0");
        // libXrender
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXrender.so.1.3.0");
        // libXt
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXt.so.6.0.0");
        // libXtst: X window system client interface
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libXtst.so.6.1.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb.so.1.1.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-dri2.so.0.0.0");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-dri3.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-present.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-render.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-shm.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-sync.so.1.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxcb-xfixes.so.0.0.0");
        // libxcrypt (/usr/lib64/): library for one-way hashing of passcodes
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libcrypt.so.1.1.0");
        // libxkbcommon
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxkbcommon.so.0.0.0");
        // libxml2
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxml2.so.2.9.1");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxml2.so.2.9.7");
        // xmlsec1
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxmlsec1.so.1.2.20");
        // xmlsec1-openssl
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxmlsec1-openssl.so.1.2.20");
        // libxshmfence
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxshmfence.so.1.0.0");
        // libxslt
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxslt.so.1.1.28");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libxslt.so.1.1.32");
        // zlib
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libz.so.1.2.3");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libz.so.1.2.7");
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "libz.so.1.2.11");
        // p11-kit-trust
        NATIVE_LIBRARIES_LINUX.add(NATIVE_LIBRARY_LINUX_HOME + "p11-kit-trust.so");

        // ***** LINUX JAVA *****
        NATIVE_LIBRARIES_LINUX_JAVA = new ArrayList<String>();
        // java-(1.8.0|11|17)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libattach.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt.so");
        // java-(1.8.0|11|17)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt_xawt.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt_headless.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libextnet.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libfontmanager.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libfreetype.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libj2pkcs11.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjaas.so");
        // java-1.8.0-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjaas_unix.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjava.so");
        // java-(11|17)-openjdk-headless. Replaces libjpeg.so in 1.8.0?
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjavajpeg.so");
        // java-(1.8.0-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjava_crw_demo.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjimage.so");
        // java-(1.8.0-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libinstrument.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjli.so");
        // java-8-openjdk-headless: Replaced by libjavajpeg.so in 11+?
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjpeg.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjsound.so");
        // java-17-openjdk-headless: Vector API
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjsvml.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjvm.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("liblcms.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement_agent.so");
        // java-(11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement_ext.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libnet.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libnio.so");
        // java-(11|17)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("librmi.so");
        // java-(1.8.0|11|17)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsplashscreen.so");
        // java-(1.8.0|11)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsunec.so");
        // java-(1.8.0|11)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsystemconf.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libverify.so");
        // java-(1.8.0|11|17)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libzip.so");

        // ***** ORACLE *****
        NATIVE_LIBRARIES_ORACLE = new ArrayList<String>();
        // proprietary T2K font library
        NATIVE_LIBRARIES_ORACLE.add("libt2k.so");
        NATIVE_LIBRARIES_ORACLE.add("libt2k.dll");

        // ***** TOMCAT *****
        NATIVE_LIBRARIES_TOMCAT = new ArrayList<String>();
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.dll");
        NATIVE_LIBRARIES_TOMCAT.add("libapr-1.so.0");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.17");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.21");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.23");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.25");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.26");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.30");
        NATIVE_LIBRARIES_TOMCAT.add("libtcnative-1.so.0.2.31");
        NATIVE_LIBRARIES_TOMCAT.add("libcrypto.so.1.1");
        NATIVE_LIBRARIES_TOMCAT.add("libssl.so.1.1");

        // ***** VMWARE *****
        NATIVE_LIBRARIES_VMWARE = new ArrayList<String>();
        NATIVE_LIBRARIES_VMWARE.add("vmGuestLib.DLL");
        NATIVE_LIBRARIES_VMWARE.add("vsocklib.dll");

        // ***** WINDOWS OS *****
        NATIVE_LIBRARIES_WINDOWS = new ArrayList<String>();
        NATIVE_LIBRARIES_WINDOWS.add("AcLayers.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("apphelp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ADVAPI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("bcrypt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("bcryptPrimitives.dll");
        // Common control library
        NATIVE_LIBRARIES_WINDOWS.add("COMCTL32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("CRYPT32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("CRYPTBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ColorAdapterClient.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cfgmgr32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("clbcatq.dll");
        NATIVE_LIBRARIES_WINDOWS.add("combase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("comdlg32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cryptdll.dll");
        NATIVE_LIBRARIES_WINDOWS.add("cryptsp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("CRYPTSP.dll");
        NATIVE_LIBRARIES_WINDOWS.add("DBGHELP.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dbgcore.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dhcpcsvc.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dhcpcsvc6.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("DNSAPI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("DWMAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("fastprox.dll");
        // Manage filters and search functions
        NATIVE_LIBRARIES_WINDOWS.add("FLTLIB.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("GDI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("GLU32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("gdiplus.dll");
        NATIVE_LIBRARIES_WINDOWS.add("gpapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("fwpuclnt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("gdi32full.dll");
        NATIVE_LIBRARIES_WINDOWS.add("iertutil.dll");
        NATIVE_LIBRARIES_WINDOWS.add("IMM32.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("IPHLPAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("iphlpapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("KERNEL32.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("KERNELBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("kerberos.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("kernel.appcore.dll");
        NATIVE_LIBRARIES_WINDOWS.add("MSASN1.dll");
        NATIVE_LIBRARIES_WINDOWS.add("mscms.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msv1_0.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("msvcp_win.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msvcrt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("MSWSOCK.DLL");
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
        NATIVE_LIBRARIES_WINDOWS.add("OLEAUT32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Ole32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("OleAut32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ole32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("opengl32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("pnrpnsp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("POWRPROF.dll");
        NATIVE_LIBRARIES_WINDOWS.add("PROPSYS.dll");
        NATIVE_LIBRARIES_WINDOWS.add("PSAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("Pdh.dll");
        NATIVE_LIBRARIES_WINDOWS.add("perfproc.dll");
        NATIVE_LIBRARIES_WINDOWS.add("powrprof.dll");
        NATIVE_LIBRARIES_WINDOWS.add("profapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("RPCRT4.dll");
        NATIVE_LIBRARIES_WINDOWS.add("rasadhlp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("rsaenh.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SHELL32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SHLWAPI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SSPICLI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("sechost.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Secur32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("secur32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("sfc.dll");
        NATIVE_LIBRARIES_WINDOWS.add("sfc_os.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("shcore.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SHCORE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("shlwapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("srvcli.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USER32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USERENV.dll");
        NATIVE_LIBRARIES_WINDOWS.add("UxTheme.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ucrtbase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("UMPDC.dll");
        NATIVE_LIBRARIES_WINDOWS.add("urlmon.dll");
        NATIVE_LIBRARIES_WINDOWS.add("VERSION.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINHTTP.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMMBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMM.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINNSI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("WINSTA.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wshunix.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WSOCK32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WS2_32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Wtsapi32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wbemcomn.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wbemprox.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wbemsvc.dll");
        NATIVE_LIBRARIES_WINDOWS.add("windows.storage.dll");
        NATIVE_LIBRARIES_WINDOWS.add("win32u.dll");
        NATIVE_LIBRARIES_WINDOWS.add("winrnr.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINTRUST.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Wldp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wshbth.dll");

        // ***** WINDOWS JAVA *****
        NATIVE_LIBRARIES_WINDOWS_JAVA = new ArrayList<String>();
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("attach.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("awt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("dt_shmem.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("dt_socket.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("fontmanager.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("freetype.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("harfbuzz.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("hprof.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("instrument.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("j2pcsc.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("j2pkcs11.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("JAWTAccessBridge-64.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("JavaAccessBridge-64.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jaas_nt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("java.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("javajpeg.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("java_crw_demo.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jawt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jdwp.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jimage.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jli.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jpeg.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsdt.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsound.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsoundds.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("jsvml.dll");
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
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("prefs.dll");
        // JDK(11|17|21)
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("rmi.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("sawindbg.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("spashscreen.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("sunec.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("sunmscapi.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("ucrtbase.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("unpack.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("vcruntime140.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("VCRUNTIME140.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("vcruntime140_1.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("verify.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("WindowsAccessBridge-64.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("w2k_lsa_auth.dll");
        NATIVE_LIBRARIES_WINDOWS_JAVA.add("zip.dll");
    }

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
    }

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
    }

    /**
     * @param buildDate
     *            The build date in {@link org.github.krashpad.util.jdk.JdkRegEx#BUILD_DATE_TIME} format.
     * @return The build <code>Date</code>.
     */
    public static final Date getDate(String buildDate) {
        Date date = null;
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
            date = getDate(MMM, d, yyyy, HH, mm, ss);
        }
        return date;
    }

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
        }
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
        }
        calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(d).intValue());
        calendar.set(Calendar.YEAR, Integer.valueOf(yyyy).intValue());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(HH).intValue());
        calendar.set(Calendar.MINUTE, Integer.valueOf(mm).intValue());
        calendar.set(Calendar.SECOND, Integer.valueOf(ss).intValue());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @param buildDate
     *            The build date in {@link org.github.krashpad.util.jdk.JdkRegEx#BUILD_DATE_TIME_21} format.
     * @return The build <code>Date</code>.
     */
    public static final Date getDate21(String buildDate) {
        Date date = null;
        Pattern pattern = Pattern.compile(JdkRegEx.BUILD_DATE_TIME_21);
        Matcher matcher = pattern.matcher(buildDate);
        if (matcher.find()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.valueOf(matcher.group(1)).intValue());
            calendar.set(Calendar.MONTH, Integer.valueOf(matcher.group(2)).intValue() - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(matcher.group(3)).intValue());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(matcher.group(4)).intValue());
            calendar.set(Calendar.MINUTE, Integer.valueOf(matcher.group(5)).intValue());
            calendar.set(Calendar.SECOND, Integer.valueOf(matcher.group(6)).intValue());
            calendar.set(Calendar.MILLISECOND, 0);
            date = calendar.getTime();
        }
        return date;
    }

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
        ResourceBundle rb = ResourceBundle.getBundle("org.github.krashpad." + propertyFile);
        return rb.getString(key);
    }

    /**
     * Check if the <code>TagHtmlEvent</code> is a start tag.
     * 
     * @param htmlTag
     *            The html tag.
     * @return true if a start tag, false otherwise.
     */
    public static final boolean isHtmlEventStartTag(String htmlTag) {
        return htmlTag.matches("^<[^/].+$");
    }

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
        }
        return millisDiff;
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private KrashUtil() {

    }
}

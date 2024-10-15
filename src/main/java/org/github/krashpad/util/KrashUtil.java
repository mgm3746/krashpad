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
     * Google native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_GOOGLE;

    /**
     * VMware native libraries.
     */
    public static final List<String> NATIVE_LIBRARIES_VMWARE;

    /**
     * Netty native transport library.
     */
    public static final String NATIVE_LIBRARY_NETTY = "libnetty_transport_native_epoll_x86_\\d{21}.so";

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
    public static final String NATIVE_LIBRARY_LINUX_HOME = "/usr/(lib|lib64)/";

    /**
     * Windows system native library home regular expression.
     */
    public static final String NATIVE_LIBRARY_WINDOWS_SYSTEM_HOME = "C:\\\\(WINDOWS|Windows|windows)\\\\"
            + "((system|System|SYSTEM)32|WinSxS\\\\amd64_microsoft\\.windows\\.common\\-controls_.+)\\\\";

    /**
     * RHEL7 extended life cycle support (ELS) phase start date.
     */
    public static final Date RHEL7_ELS_START = KrashUtil.getDate("Jul 1 2024 00:00:00");

    static {
        // ***** JBOSS *****
        NATIVE_LIBRARIES_JBOSS = new ArrayList<String>();
        NATIVE_LIBRARIES_JBOSS.add("libartemis-native-64.so");

        // ***** GOOGLE *****
        NATIVE_LIBRARIES_GOOGLE = new ArrayList<String>();
        // google-compute-engine-oslogin
        NATIVE_LIBRARIES_GOOGLE.add("libnss_cache_oslogin-20191014.00.so");
        NATIVE_LIBRARIES_GOOGLE.add("libnss_oslogin-20191014.00.so");

        // ***** LINUX OS *****
        NATIVE_LIBRARIES_LINUX = new ArrayList<String>();
        // glibc-gconv-extra
        NATIVE_LIBRARIES_LINUX.add("EUC-JP.so");
        // glibc (/usr/lib64/gconv/)
        NATIVE_LIBRARIES_LINUX.add("ISO8859-1.so");
        // mesa-dri-drivers
        NATIVE_LIBRARIES_LINUX.add("i965_dri.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("gconv/ISO8859-1.so");
        // ibus-gtk*
        NATIVE_LIBRARIES_LINUX.add("im-ibus.so");
        // mesa-dri-drivers
        NATIVE_LIBRARIES_LINUX.add("iris_dri.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("ld-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("ld-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("ld-linux-x86-64.so.2");
        // libaio
        NATIVE_LIBRARIES_LINUX.add("libaio.so.1.0.1");
        // apr:
        NATIVE_LIBRARIES_LINUX.add("libapr-1.so.0.6.3");
        NATIVE_LIBRARIES_LINUX.add("libapr-1.so.0.7.4");
        // alsa-lib: library to interface with ALSA in the Linux kernel and virtual devices
        NATIVE_LIBRARIES_LINUX.add("libasound.so.2.0.0");
        // alsa-plugins-pulseaudio: library for accessing a PulseAudio sound daemon (to play and record sound across
        // network)
        NATIVE_LIBRARIES_LINUX.add("libasound_module_pcm_pulse.so");
        // aspell
        NATIVE_LIBRARIES_LINUX.add("libaspell.so.15.1.5");
        // libasyncns: library for asynchronous name service queries
        NATIVE_LIBRARIES_LINUX.add("libasyncns.so.0.3.1");
        // atk
        NATIVE_LIBRARIES_LINUX.add("libatk-1.0.so.0.22810.1");
        // at-spi2-atk
        NATIVE_LIBRARIES_LINUX.add("libatk-bridge-2.0.so.0.0.0");
        // libatomic
        NATIVE_LIBRARIES_LINUX.add("libatomic.so.1.2.0");
        // at-spi2-core
        NATIVE_LIBRARIES_LINUX.add("libatspi.so.0.0.1");
        // libattr
        NATIVE_LIBRARIES_LINUX.add("libattr.so.1.1.0");
        // lib-audit (audit framework)
        NATIVE_LIBRARIES_LINUX.add("libaudit.so.1.0.0");
        // libavahi-client (DNS service discovery and multicast DNS)
        NATIVE_LIBRARIES_LINUX.add("libavahi-client.so.3.2.9");
        // avahi-libs (libraries need to run programs that use avahi)
        NATIVE_LIBRARIES_LINUX.add("libavahi-common.so.3.5.3");
        // blas (Basic Linear Algebra Subpackage)
        NATIVE_LIBRARIES_LINUX.add("libblas.so.3.8.0");
        // brotli (compression library)
        NATIVE_LIBRARIES_LINUX.add("libbrotlicommon.so.1.0.6");
        // brotli (compression library)
        NATIVE_LIBRARIES_LINUX.add("libbrotlidec.so.1.0.6");
        // boost-date-time (C++ date/time library)
        NATIVE_LIBRARIES_LINUX.add("libboost_date_time.so.1.66.0");
        // boost-regex (C++ regex library)
        NATIVE_LIBRARIES_LINUX.add("libboost_regex.so.1.53.0");
        NATIVE_LIBRARIES_LINUX.add("libboost_regex.so.1.66.0");
        // boost-serialization (C++ serialization library)
        NATIVE_LIBRARIES_LINUX.add("libboost_serialization.so.1.66.0");
        // boost-system (C++ error reporting library)
        NATIVE_LIBRARIES_LINUX.add("libboost_system.so.1.66.0");
        // boost-thread (C++ multi-threading library)
        NATIVE_LIBRARIES_LINUX.add("libboost_thread.so.1.66.0");
        // bzip2-libs
        NATIVE_LIBRARIES_LINUX.add("libbz2.so.1.0.6");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add("libc-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libc-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libc.so.6");
        // cairo
        NATIVE_LIBRARIES_LINUX.add("libcairo.so.2.11512.0");
        // cairo-gobject
        NATIVE_LIBRARIES_LINUX.add("libcairo-gobject.so.2.11512.0");
        // libcanberra (event sounds)
        NATIVE_LIBRARIES_LINUX.add("libcanberra.so.0.2.5");
        // libcanberra-gtk3 (translate gtk widget signals to event sounds)
        NATIVE_LIBRARIES_LINUX.add("libcanberra-gtk3.so.0.1.9");
        // libpcap
        NATIVE_LIBRARIES_LINUX.add("libcap.so.2.22");
        NATIVE_LIBRARIES_LINUX.add("libcap.so.2.26");
        NATIVE_LIBRARIES_LINUX.add("libcap.so.2.48");
        // libpcap-ng
        NATIVE_LIBRARIES_LINUX.add("libcap-ng.so.0.0.0");
        // libcom-err
        NATIVE_LIBRARIES_LINUX.add("libcom_err.so.2.1");
        // cracklib
        NATIVE_LIBRARIES_LINUX.add("libcrack.so.2.9.0");
        // libcroco
        NATIVE_LIBRARIES_LINUX.add("libcroco-0.6.so.3.0.1");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libcrypt-2.17.so");
        // libxcrypt: library for one-way hashing of passcodes
        NATIVE_LIBRARIES_LINUX.add("libcrypt.so.1.1.0");
        // cups-libs (native cups)
        NATIVE_LIBRARIES_LINUX.add("libcups.so.2");
        NATIVE_LIBRARIES_LINUX.add("libcupsimage.so.2");
        // dconf
        NATIVE_LIBRARIES_LINUX.add("libdconfsettings.so");
        // libdatrie
        NATIVE_LIBRARIES_LINUX.add("libdatrie.so.1.3.2");
        // dbus-libs
        NATIVE_LIBRARIES_LINUX.add("libdbus-1.so.3.14.14");
        NATIVE_LIBRARIES_LINUX.add("libdbus-1.so.3.19.7");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add("libdl-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libdl-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libdl.so.2");
        // libblkid
        NATIVE_LIBRARIES_LINUX.add("libblkid.so.1.1.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add("libdrm.so.2.4.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add("libdrm_amdgpu.so.1.0.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add("libdrm_nouveau.so.2.0.0");
        // libdrm
        NATIVE_LIBRARIES_LINUX.add("libdrm_radeon.so.1.0.1");
        // elfutils-devel
        NATIVE_LIBRARIES_LINUX.add("libdw-0.160.so");
        NATIVE_LIBRARIES_LINUX.add("libdw-0.176.so");
        // libglvnd-egl
        NATIVE_LIBRARIES_LINUX.add("libEGL.so.1.1.0");
        // mesa-libEGL
        NATIVE_LIBRARIES_LINUX.add("libEGL_mesa.so.0.0.0");
        // elfutils-libelf-devel
        NATIVE_LIBRARIES_LINUX.add("libelf-0.160.so");
        NATIVE_LIBRARIES_LINUX.add("libelf-0.176.so");
        NATIVE_LIBRARIES_LINUX.add("libelf-0.187.so");
        NATIVE_LIBRARIES_LINUX.add("libelf-0.190.so");
        // enchant2
        NATIVE_LIBRARIES_LINUX.add("libenchant-2.so.2.2.3");
        // libepoxy
        NATIVE_LIBRARIES_LINUX.add("libepoxy.so.0.0.0");
        // expat
        NATIVE_LIBRARIES_LINUX.add("libexpat.so.1.6.7");
        // flac-libs: reference implementation for FLAC (Free Lossless Audio Codec), an audio coding format for
        // lossless compression of digital audio
        NATIVE_LIBRARIES_LINUX.add("libFLAC.so.8.3.0");
        // libffi
        NATIVE_LIBRARIES_LINUX.add("libffi.so.6.0.1");
        NATIVE_LIBRARIES_LINUX.add("libffi.so.6.0.2");
        // fontconfig
        NATIVE_LIBRARIES_LINUX.add("libfontconfig.so.1.12.0");
        // nss-softokn-freebl
        NATIVE_LIBRARIES_LINUX.add("libfreebl3.so");
        // nss-softokn-freebl
        NATIVE_LIBRARIES_LINUX.add("libfreeblpriv3.so");
        // freetype
        NATIVE_LIBRARIES_LINUX.add("libfreetype.so.6.14.0");
        NATIVE_LIBRARIES_LINUX.add("libfreetype.so.6.16.1");
        // fribidi
        NATIVE_LIBRARIES_LINUX.add("libfribidi.so.0.4.0");
        // libglvnd-glx
        NATIVE_LIBRARIES_LINUX.add("libGL.so.1.7.0");
        // libglvnd-gles
        NATIVE_LIBRARIES_LINUX.add("libGLESv2.so.2.1.0");
        // libglvnd-glx
        NATIVE_LIBRARIES_LINUX.add("libGLX.so.0.0.0");
        // mesa-libGL (Mesa implementation GLU OpenGL API)
        NATIVE_LIBRARIES_LINUX.add("libGLX_mesa.so.0.0.0");
        // mesa-libGLU (Mesa implementation GLU OpenGL API)
        NATIVE_LIBRARIES_LINUX.add("libGLU.so.1.3.1");
        // libglvnd
        NATIVE_LIBRARIES_LINUX.add("libGLdispatch.so.0.0.0");
        // mesa-libglapi
        NATIVE_LIBRARIES_LINUX.add("libgbm.so.1.0.0");
        // libgcc
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-4.4.7-20120601.so.1");
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-4.8.5-20150702.so.1");
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-8-20191121.so.1");
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-8-20200928.so.1");
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-8-20210514.so.1");
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-11-20230605.so.1");
        NATIVE_LIBRARIES_LINUX.add("libgcc_s-11-20231218.so.1");
        // libgcrypt
        NATIVE_LIBRARIES_LINUX.add("libgcrypt.so.11.8.2");
        NATIVE_LIBRARIES_LINUX.add("libgcrypt.so.20.2.3");
        NATIVE_LIBRARIES_LINUX.add("libgcrypt.so.20.2.5");
        // gtk3
        NATIVE_LIBRARIES_LINUX.add("libgdk-3.so.0.2200.30");
        // gdk-pixbuf2
        NATIVE_LIBRARIES_LINUX.add("libgdk_pixbuf-2.0.so.0.3612.0");
        // libgfortran
        NATIVE_LIBRARIES_LINUX.add("libgfortran.so.5.0.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add("libgio-2.0.so.0.5600.4");
        // mesa-libglapi
        NATIVE_LIBRARIES_LINUX.add("libglapi.so.0.0.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add("libglib-2.0.so.0.5600.1");
        NATIVE_LIBRARIES_LINUX.add("libglib-2.0.so.0.5600.4");
        // glib2
        NATIVE_LIBRARIES_LINUX.add("libgmodule-2.0.so.0.5600.4");
        // gmp
        NATIVE_LIBRARIES_LINUX.add("libgmp.so.10.3.2");
        // gnome-keyring
        NATIVE_LIBRARIES_LINUX.add("libgnome-keyring.so.0.2.0");
        // gnutls
        NATIVE_LIBRARIES_LINUX.add("libgnutls.so.30.24.0");
        NATIVE_LIBRARIES_LINUX.add("libgnutls.so.30.28.0");
        NATIVE_LIBRARIES_LINUX.add("libgnutls.so.30.28.2");
        // glib2
        NATIVE_LIBRARIES_LINUX.add("libgobject-2.0.so.0.5600.1");
        NATIVE_LIBRARIES_LINUX.add("libgobject-2.0.so.0.5600.4");
        // libgpg-error
        NATIVE_LIBRARIES_LINUX.add("libgpg-error.so.0.10.0");
        NATIVE_LIBRARIES_LINUX.add("libgpg-error.so.0.24.2");
        // graphite2
        NATIVE_LIBRARIES_LINUX.add("libgraphite2.so.3.0.1");
        // libgs (ghostscript)
        NATIVE_LIBRARIES_LINUX.add("libgs.so.9.27");
        // gsm: library for lossy speech compression
        NATIVE_LIBRARIES_LINUX.add("libgsm.so.1.0.17");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add("libgssapi_krb5.so.2.2");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstapp-1.0.so.0.1601.0");
        // gstreamer1
        NATIVE_LIBRARIES_LINUX.add("libgstbase-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstallocators-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstaudio-1.0.so.0.1601.0");
        // gstreamer1
        NATIVE_LIBRARIES_LINUX.add("libgstbase-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstfft-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstgl-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstpbutils-1.0.so.0.1601.0");
        // gstreamer1
        NATIVE_LIBRARIES_LINUX.add("libgstreamer-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgsttag-1.0.so.0.1601.0");
        // gstreamer1-plugins-base
        NATIVE_LIBRARIES_LINUX.add("libgstvideo-1.0.so.0.1601.0");
        // glib2
        NATIVE_LIBRARIES_LINUX.add("libgthread-2.0.so.0.5600.4");
        // gtk3
        NATIVE_LIBRARIES_LINUX.add("libgtk-3.so.0.2200.30");
        // gvfs-client
        NATIVE_LIBRARIES_LINUX.add("libgvfscommon.so");
        // gvfs-client
        NATIVE_LIBRARIES_LINUX.add("libgvfsdbus.so");
        // harfbuzz
        NATIVE_LIBRARIES_LINUX.add("libharfbuzz.so.0.10705.0");
        // harfbuzz-icu
        NATIVE_LIBRARIES_LINUX.add("libharfbuzz-icu.so.0.10705.0");
        // nettle
        NATIVE_LIBRARIES_LINUX.add("libhogweed.so.4.5");
        // hyphen
        NATIVE_LIBRARIES_LINUX.add("libhyphen.so.0.3.0");
        // libICE
        NATIVE_LIBRARIES_LINUX.add("libICE.so.6.3.0");
        // libibus
        NATIVE_LIBRARIES_LINUX.add("libibus-1.0.so.5.0.519");
        // libicu
        NATIVE_LIBRARIES_LINUX.add("libicudata.so.50.2");
        NATIVE_LIBRARIES_LINUX.add("libicudata.so.60.3");
        // libicu
        NATIVE_LIBRARIES_LINUX.add("libicui18n.so.50.2");
        NATIVE_LIBRARIES_LINUX.add("libicui18n.so.60.3");
        // libicu
        NATIVE_LIBRARIES_LINUX.add("libicuuc.so.50.2");
        NATIVE_LIBRARIES_LINUX.add("libicuuc.so.60.3");
        // libijs (IJS raster image library)
        NATIVE_LIBRARIES_LINUX.add("libijs-0.35.so");
        // glibc-gconv-extra
        NATIVE_LIBRARIES_LINUX.add("libJIS.so");
        // webkit2gtk3-jsc
        NATIVE_LIBRARIES_LINUX.add("libjavascriptcoregtk-4.0.so.18.13.7");
        NATIVE_LIBRARIES_LINUX.add("libjavascriptcoregtk-4.0.so.18.20.11");
        // jbigkit-libs (compression/decompression)
        NATIVE_LIBRARIES_LINUX.add("libjbig.so.2.1");
        // jbig2dec-libs (JBIG2 image decompression format decoder)
        NATIVE_LIBRARIES_LINUX.add("libjbig2dec.so.0.0.0");
        // libjpeg-turbo:
        NATIVE_LIBRARIES_LINUX.add("libjpeg.so.62.1.0");
        NATIVE_LIBRARIES_LINUX.add("libjpeg.so.62.2.0");
        // json-c
        NATIVE_LIBRARIES_LINUX.add("libjson-c.so.2.0.1");
        // jss
        NATIVE_LIBRARIES_LINUX.add("libjss4.so");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add("libk5crypto.so.3.1");
        // keyutils-libs
        NATIVE_LIBRARIES_LINUX.add("libkeyutils.so.1.5");
        NATIVE_LIBRARIES_LINUX.add("libkeyutils.so.1.6");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add("libkrb5.so.3.3");
        // krb5-libs
        NATIVE_LIBRARIES_LINUX.add("libkrb5support.so.0.1");
        // lapack (Linear Algebra Package)
        NATIVE_LIBRARIES_LINUX.add("liblapack.so.3.8.0");
        // llvm-libs
        NATIVE_LIBRARIES_LINUX.add("libLLVM-14.so");
        NATIVE_LIBRARIES_LINUX.add("libLLVM-17.so");
        // lcms2
        NATIVE_LIBRARIES_LINUX.add("liblcms2.so.2.0.8");
        // libtool-ltdl
        NATIVE_LIBRARIES_LINUX.add("libltdl.so.7.3.0");
        NATIVE_LIBRARIES_LINUX.add("libltdl.so.7.3.1");
        // xz-libs
        NATIVE_LIBRARIES_LINUX.add("liblzma.so.5.2.2");
        NATIVE_LIBRARIES_LINUX.add("liblzma.so.5.2.4");
        // lz4-libs
        NATIVE_LIBRARIES_LINUX.add("liblz4.so.1.8.1");
        NATIVE_LIBRARIES_LINUX.add("liblz4.so.1.8.3");
        // glibc-devel
        NATIVE_LIBRARIES_LINUX.add("libm-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libm-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libm.so.6");
        // libmount
        NATIVE_LIBRARIES_LINUX.add("libmount.so.1.1.0");
        // libidn (international string handling)
        NATIVE_LIBRARIES_LINUX.add("libidn.so.11.6.18");
        // libidn2
        NATIVE_LIBRARIES_LINUX.add("libidn2.so.0.3.6");
        // ncurses-devl
        NATIVE_LIBRARIES_LINUX.add("libncurses.so.5.9");
        // nettle
        NATIVE_LIBRARIES_LINUX.add("libnettle.so.6.5");
        // libnotify
        NATIVE_LIBRARIES_LINUX.add("libnotify.so.4.0.0");
        // nspr
        NATIVE_LIBRARIES_LINUX.add("libnspr4.so");
        // nss
        NATIVE_LIBRARIES_LINUX.add("libnss3.so");
        // nss-sysinit
        NATIVE_LIBRARIES_LINUX.add("libnsssysinit.so");
        // nss-util
        NATIVE_LIBRARIES_LINUX.add("libnssutil3.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libnss_compat-2.17.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libnss_dns-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_dns-2.28.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnss_files-2.28.so");
        // nss-pam-ldap
        NATIVE_LIBRARIES_LINUX.add("libnss_ldap.so.2");
        // systemd-libs:
        NATIVE_LIBRARIES_LINUX.add("libnss_myhostname.so.2");
        // glibc, libnsl: the public client interface for NIS(YP). libnsl breaks out NIS library that used
        // to be in glibc.
        NATIVE_LIBRARIES_LINUX.add("libnsl-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libnsl-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libnsl.so.1");
        NATIVE_LIBRARIES_LINUX.add("libnsl.so.2.0.0");
        // nss_nis: Name Service Switch (NSS) module using NIS.
        NATIVE_LIBRARIES_LINUX.add("libnss_nis.so.2.0.0");
        // sssd-client
        NATIVE_LIBRARIES_LINUX.add("libnss_sss.so.2");
        NATIVE_LIBRARIES_LINUX.add("pam_sss.so");
        // numactl-libs
        NATIVE_LIBRARIES_LINUX.add("libnuma.so.1.0.0");
        // libglvnd-opengl
        NATIVE_LIBRARIES_LINUX.add("libOpenGL.so.0.0.0");
        // libvogg: reference implementation to create, decode, and work with Ogg multimedia container
        // format bitstreams
        NATIVE_LIBRARIES_LINUX.add("libogg.so.0.8.2");
        // openjpeg2
        NATIVE_LIBRARIES_LINUX.add("libopenjp2.so.2.3.1");
        NATIVE_LIBRARIES_LINUX.add("libopenjp2.so.2.4.0");
        // orc
        NATIVE_LIBRARIES_LINUX.add("liborc-0.4.so.0.28.0");
        // p11-kit
        NATIVE_LIBRARIES_LINUX.add("libp11-kit.so.0.3.0");
        // pango
        NATIVE_LIBRARIES_LINUX.add("libpango-1.0.so.0.4200.3");
        // pango
        NATIVE_LIBRARIES_LINUX.add("libpangocairo-1.0.so.0.4200.3");
        // pango
        NATIVE_LIBRARIES_LINUX.add("libpangoft2-1.0.so.0.4200.3");
        // libpaper (getting information on page sizes)
        NATIVE_LIBRARIES_LINUX.add("libpaper.so.1.1.2");
        // pcre
        NATIVE_LIBRARIES_LINUX.add("libpcre.so.1.2.0");
        NATIVE_LIBRARIES_LINUX.add("libpcre.so.1.2.10");
        // pcre2
        NATIVE_LIBRARIES_LINUX.add("libpcre2-8.so.0.7.1");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-bmp.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-gif.so");
        // gdk-pixbuf2-modules
        NATIVE_LIBRARIES_LINUX.add("libpixbufloader-svg.so");
        // pixman
        NATIVE_LIBRARIES_LINUX.add("libpixman-1.so.0.38.4");
        // nspr
        NATIVE_LIBRARIES_LINUX.add("libplc4.so");
        // nspr
        NATIVE_LIBRARIES_LINUX.add("libplds4.so");
        // libpng
        NATIVE_LIBRARIES_LINUX.add("libpng15.so.15.13.0");
        NATIVE_LIBRARIES_LINUX.add("libpng16.so.16.34.0");
        // libpwquality
        NATIVE_LIBRARIES_LINUX.add("libpwquality.so.1.0.2");
        NATIVE_LIBRARIES_LINUX.add("pam_pwquality.so");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libpthread.so.0");
        // pulseaudio-libs: sound proxy server for sound applications
        NATIVE_LIBRARIES_LINUX.add("libpulse.so.0.23.0");
        // pulseaudio-libs: sound proxy server for sound applications
        NATIVE_LIBRARIES_LINUX.add("libpulsecommon-14.0.so");
        // libquadmath
        NATIVE_LIBRARIES_LINUX.add("libquadmath.so.0.0.0");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libresolv-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("libresolv-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("libresolv.so.2");
        // librsvg2
        NATIVE_LIBRARIES_LINUX.add("librsvg-2.so.2.42.7");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("librt-2.12.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.17.so");
        NATIVE_LIBRARIES_LINUX.add("librt-2.28.so");
        NATIVE_LIBRARIES_LINUX.add("librt.so.1");
        // open-vm-tools: loaded to provide extended VMWare/vSphere virtualization information in the fatal error log.
        NATIVE_LIBRARIES_LINUX.add("libguestlib.so.0.0.0");
        // libSM
        NATIVE_LIBRARIES_LINUX.add("libSM.so.6.0.1");
        // libsecret
        NATIVE_LIBRARIES_LINUX.add("libsecret-1.so.0.0.0");
        // nss
        NATIVE_LIBRARIES_LINUX.add("libsmime3.so");
        // softhsm
        NATIVE_LIBRARIES_LINUX.add("libsofthsm2.so");
        // libselinux
        NATIVE_LIBRARIES_LINUX.add("libselinux.so.1");
        // libsndfile: library for reading and writing files containing sampled audio data
        NATIVE_LIBRARIES_LINUX.add("libsndfile.so.1.0.28");
        // nss-softokn: loaded when RHEL running in FIPS mode
        NATIVE_LIBRARIES_LINUX.add("libsoftokn3.so");
        // libsoup
        NATIVE_LIBRARIES_LINUX.add("libsoup-2.4.so.1.8.0");
        // sqlite-libs
        NATIVE_LIBRARIES_LINUX.add("libsqlite3.so.0.8.6");
        // nss
        NATIVE_LIBRARIES_LINUX.add("libssl3.so");
        // systemd-libs:
        NATIVE_LIBRARIES_LINUX.add("libsystemd.so.0.6.0");
        NATIVE_LIBRARIES_LINUX.add("libsystemd.so.0.23.0");
        // libtasn1
        NATIVE_LIBRARIES_LINUX.add("libtasn1.so.6.5.5");
        // libtdb (Trivial Database)
        NATIVE_LIBRARIES_LINUX.add("libtdb.so.1.4.9");
        // libthai
        NATIVE_LIBRARIES_LINUX.add("libthai.so.0.3.0");
        // libtiff (tiff image library)
        NATIVE_LIBRARIES_LINUX.add("libtiff.so.5.3.0");
        // ncurses-libs
        NATIVE_LIBRARIES_LINUX.add("libtinfo.so.5.9");
        NATIVE_LIBRARIES_LINUX.add("libtinfo.so.6.1");
        // libtirpc: a port of Suns Transport-Independent RPC library.
        NATIVE_LIBRARIES_LINUX.add("libtirpc.so.3.0.0");
        // libunistring
        NATIVE_LIBRARIES_LINUX.add("libunistring.so.2.1.0");
        // glibc
        NATIVE_LIBRARIES_LINUX.add("libutil-2.28.so");
        // libuuid
        NATIVE_LIBRARIES_LINUX.add("libuuid.so.1.3.0");
        // libvorbis: reference implementation for the Vorbis codec
        NATIVE_LIBRARIES_LINUX.add("libvorbis.so.0.4.8");
        // libvorbis: reference implementation for the Vorbis codec
        NATIVE_LIBRARIES_LINUX.add("libvorbisenc.so.2.0.11");
        // libvorbis: reference implementation for the Vorbis codec
        NATIVE_LIBRARIES_LINUX.add("libvorbisfile.so.3.3.7");
        // libwayland-client
        NATIVE_LIBRARIES_LINUX.add("libwayland-client.so.0.3.0");
        NATIVE_LIBRARIES_LINUX.add("libwayland-client.so.0.21.0");
        // libwayland-cursor
        NATIVE_LIBRARIES_LINUX.add("libwayland-cursor.so.0.0.0");
        NATIVE_LIBRARIES_LINUX.add("libwayland-cursor.so.0.21.0");
        // libwayland-egl
        NATIVE_LIBRARIES_LINUX.add("libwayland-egl.so.1.0.0");
        NATIVE_LIBRARIES_LINUX.add("libwayland-egl.so.1.21.0");
        // libwayland-server
        NATIVE_LIBRARIES_LINUX.add("libwayland-server.so.0.1.0");
        // webkit2gtk3
        NATIVE_LIBRARIES_LINUX.add("libwebkit2gtk-4.0.so.37.37.6");
        NATIVE_LIBRARIES_LINUX.add("libwebkit2gtk-4.0.so.37.56.11");
        // libwebp
        NATIVE_LIBRARIES_LINUX.add("libwebp.so.7.0.2");
        // libwebp
        NATIVE_LIBRARIES_LINUX.add("libwebpdemux.so.2.0.4");
        // woff2 (library for converting fonts from TTF WOFF 2.0 format)
        NATIVE_LIBRARIES_LINUX.add("libwoff2common.so.1.0.2");
        // woff2 (library for converting fonts from TTF WOFF 2.0 format)
        NATIVE_LIBRARIES_LINUX.add("libwoff2dec.so.1.0.2");
        // libX11
        NATIVE_LIBRARIES_LINUX.add("libX11.so.6.3.0");
        // libX11-xcb
        NATIVE_LIBRARIES_LINUX.add("libX11-xcb.so.1.0.0");
        // libXau
        NATIVE_LIBRARIES_LINUX.add("libXau.so.6.0.0");
        // libXcomposite
        NATIVE_LIBRARIES_LINUX.add("libXcomposite.so.1.0.0");
        // libXcursor
        NATIVE_LIBRARIES_LINUX.add("libXcursor.so.1.0.2");
        // libXdamage
        NATIVE_LIBRARIES_LINUX.add("libXdamage.so.1.1.0");
        // libXext
        NATIVE_LIBRARIES_LINUX.add("libXext.so.6.4.0");
        // libXfixes
        NATIVE_LIBRARIES_LINUX.add("libXfixes.so.3.1.0");
        // libXinerama
        NATIVE_LIBRARIES_LINUX.add("libXinerama.so.1.0.0");
        // libXi
        NATIVE_LIBRARIES_LINUX.add("libXi.so.6.1.0");
        // libXrandr
        NATIVE_LIBRARIES_LINUX.add("libXrandr.so.2.2.0");
        // libXrender
        NATIVE_LIBRARIES_LINUX.add("libXrender.so.1.3.0");
        // libXt
        NATIVE_LIBRARIES_LINUX.add("libXt.so.6.0.0");
        // libXtst: X window system client interface
        NATIVE_LIBRARIES_LINUX.add("libXtst.so.6.1.0");
        // libXxf86vm
        NATIVE_LIBRARIES_LINUX.add("libXxf86vm.so.1.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb.so.1.1.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-dri2.so.0.0.0");
        NATIVE_LIBRARIES_LINUX.add("libxcb-dri3.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-glx.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-present.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-randr.so.0.1.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-render.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-shm.so.0.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-sync.so.1.0.0");
        // libxcb
        NATIVE_LIBRARIES_LINUX.add("libxcb-xfixes.so.0.0.0");
        // libxkbcommon
        NATIVE_LIBRARIES_LINUX.add("libxkbcommon.so.0.0.0");
        // libxml2
        NATIVE_LIBRARIES_LINUX.add("libxml2.so.2.9.1");
        NATIVE_LIBRARIES_LINUX.add("libxml2.so.2.9.7");
        // libxshmfence
        NATIVE_LIBRARIES_LINUX.add("libxshmfence.so.1.0.0");
        // libxslt
        NATIVE_LIBRARIES_LINUX.add("libxslt.so.1.1.28");
        NATIVE_LIBRARIES_LINUX.add("libxslt.so.1.1.32");
        // xmlsec1
        NATIVE_LIBRARIES_LINUX.add("libxmlsec1.so.1.2.20");
        // xmlsec1-openssl
        NATIVE_LIBRARIES_LINUX.add("libxmlsec1-openssl.so.1.2.20");
        // zlib
        NATIVE_LIBRARIES_LINUX.add("libz.so.1.2.3");
        NATIVE_LIBRARIES_LINUX.add("libz.so.1.2.7");
        NATIVE_LIBRARIES_LINUX.add("libz.so.1.2.11");
        // libzstd-devel
        NATIVE_LIBRARIES_LINUX.add("libzstd.so.1.4.4");
        // p11-kit-trust
        NATIVE_LIBRARIES_LINUX.add("p11-kit-trust.so");
        // pam (pluggable authentication modules)
        NATIVE_LIBRARIES_LINUX.add("libpam.so.0");
        NATIVE_LIBRARIES_LINUX.add("libpam.so.0.83.1");
        NATIVE_LIBRARIES_LINUX.add("libpam.so.0.84.2");
        NATIVE_LIBRARIES_LINUX.add("libpam_misc.so.0.82.1");
        NATIVE_LIBRARIES_LINUX.add("pam_deny.so");
        NATIVE_LIBRARIES_LINUX.add("pam_echo.so");
        NATIVE_LIBRARIES_LINUX.add("pam_env.so");
        NATIVE_LIBRARIES_LINUX.add("pam_faildelay.so");
        NATIVE_LIBRARIES_LINUX.add("pam_faillock.so");
        NATIVE_LIBRARIES_LINUX.add("pam_keyinit.so");
        NATIVE_LIBRARIES_LINUX.add("pam_lastlog.so");
        NATIVE_LIBRARIES_LINUX.add("pam_limits.so");
        NATIVE_LIBRARIES_LINUX.add("pam_localuser.so");
        NATIVE_LIBRARIES_LINUX.add("pam_mkhomedir.so");
        NATIVE_LIBRARIES_LINUX.add("pam_nologin.so");
        NATIVE_LIBRARIES_LINUX.add("pam_permit.so");
        NATIVE_LIBRARIES_LINUX.add("pam_pwhistory.so");
        NATIVE_LIBRARIES_LINUX.add("pam_succeed_if.so");
        NATIVE_LIBRARIES_LINUX.add("pam_unix.so");

        // systemd-pam
        NATIVE_LIBRARIES_LINUX.add("pam_systemd.so");

        // ***** LINUX JAVA *****
        NATIVE_LIBRARIES_LINUX_JAVA = new ArrayList<String>();
        // java-(1.8.0|11|17|21)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libattach.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt.so");
        // java-(1.8.0|11|17|21)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt_xawt.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libawt_headless.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libextnet.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libfontmanager.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libfreetype.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libj2pkcs11.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjaas.so");
        // java-1.8.0-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjaas_unix.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjava.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjavajpeg.so");
        // java-1.8.0-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjava_crw_demo.so");
        // java-(1.8.0|11|17|21)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjawt.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjimage.so");
        // java-(1.8.0-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libinstrument.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjli.so");
        // RHEL7: libjpeg-turbo
        // RHEL8+: java-1.8.0-openjdk-headless
        // Why does RHEL7 have 2 jpeg libraries (libjavajpeg.so and libjavajpeg.so) spit into 2 packages (libjpeg-turbo
        // and java-1.8.0-openjdk-headless)?
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjpeg.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjsound.so");
        // java-17-openjdk-headless: Vector API
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjsvml.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libjvm.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("liblcms.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement_agent.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmanagement_ext.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libmlib_image.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libnet.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libnio.so");
        // java-(11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libprefs.so");
        // java-(11|17|21)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("librmi.so");
        // java-(1.8.0|11|17|21)-openjdk
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsplashscreen.so");
        // java-(1.8.0|11)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsunec.so");
        // java-(1.8.0|11)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libsystemconf.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
        NATIVE_LIBRARIES_LINUX_JAVA.add("libverify.so");
        // java-(1.8.0|11|17|21)-openjdk-headless
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
        NATIVE_LIBRARIES_TOMCAT.add("libapr-1.so.0.6.3");
        NATIVE_LIBRARIES_TOMCAT.add("libapr-1.so.0.7.0");
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
        NATIVE_LIBRARIES_WINDOWS.add("amsi.dll");
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
        NATIVE_LIBRARIES_WINDOWS.add("d2d1.dll");
        NATIVE_LIBRARIES_WINDOWS.add("DBGHELP.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("dbghelp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("dbgcore.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("DEVOBJ.dll");
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
        NATIVE_LIBRARIES_WINDOWS.add("LOGONCLI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("MPR.dll");
        NATIVE_LIBRARIES_WINDOWS.add("MSASN1.dll");
        NATIVE_LIBRARIES_WINDOWS.add("mscms.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msv1_0.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("msvcp_win.dll");
        NATIVE_LIBRARIES_WINDOWS.add("msvcrt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("MSWSOCK.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("MSWSOCK.dll");
        NATIVE_LIBRARIES_WINDOWS.add("mswsock.dll");
        NATIVE_LIBRARIES_WINDOWS.add("napinsp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ncrypt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NETAPI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NETUTILS.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("netutils.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NLAapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("nlansp_c.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NSI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntdll.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NTDSAPI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntdsapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NtlmShared.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ntmarta.dll");
        NATIVE_LIBRARIES_WINDOWS.add("NTASN1.dll");
        NATIVE_LIBRARIES_WINDOWS.add("OLEAUT32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Ole32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("OleAut32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ole32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("opengl32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("perfos.dll");
        NATIVE_LIBRARIES_WINDOWS.add("pnrpnsp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("POWRPROF.dll");
        NATIVE_LIBRARIES_WINDOWS.add("PROPSYS.dll");
        NATIVE_LIBRARIES_WINDOWS.add("PSAPI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("Pdh.dll");
        NATIVE_LIBRARIES_WINDOWS.add("pdh.dll");
        NATIVE_LIBRARIES_WINDOWS.add("perfproc.dll");
        NATIVE_LIBRARIES_WINDOWS.add("pfclient.dll");
        NATIVE_LIBRARIES_WINDOWS.add("powrprof.dll");
        NATIVE_LIBRARIES_WINDOWS.add("profapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("prntvpt.dll");
        NATIVE_LIBRARIES_WINDOWS.add("RPCRT4.dll");
        NATIVE_LIBRARIES_WINDOWS.add("RpcRtRemote.dll");
        NATIVE_LIBRARIES_WINDOWS.add("rasadhlp.dll");
        NATIVE_LIBRARIES_WINDOWS.add("rsaenh.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SAMCLI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("SHELL32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SHLWAPI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SSPICLI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("SETUPAPI.dll");
        NATIVE_LIBRARIES_WINDOWS.add("sechost.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Secur32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("secur32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("sfc.dll");
        NATIVE_LIBRARIES_WINDOWS.add("sfc_os.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("shcore.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SHCORE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("shlwapi.dll");
        NATIVE_LIBRARIES_WINDOWS.add("SRVCLI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("srvcli.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USER32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("USERENV.dll");
        NATIVE_LIBRARIES_WINDOWS.add("UxTheme.dll");
        NATIVE_LIBRARIES_WINDOWS.add("ucrtbase.dll");
        NATIVE_LIBRARIES_WINDOWS.add("UMPDC.dll");
        NATIVE_LIBRARIES_WINDOWS.add("urlmon.dll");
        NATIVE_LIBRARIES_WINDOWS.add("VERSION.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINHTTP.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WININET.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMMBASE.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINMM.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINNSI.DLL");
        NATIVE_LIBRARIES_WINDOWS.add("WINSTA.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WinSCard.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WLDAP32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wshunix.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WSOCK32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WS2_32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WTSAPI32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("Wtsapi32.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wbemcomn.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wbemprox.dll");
        NATIVE_LIBRARIES_WINDOWS.add("wbemsvc.dll");
        NATIVE_LIBRARIES_WINDOWS.add("windows.storage.dll");
        NATIVE_LIBRARIES_WINDOWS.add("win32u.dll");
        NATIVE_LIBRARIES_WINDOWS.add("winrnr.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WINTRUST.dll");
        NATIVE_LIBRARIES_WINDOWS.add("WKSCLI.DLL");
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
     *            The build date in {@link org.github.krashpad.util.jdk.JdkRegEx#BUILD_DATETIME} format.
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
        Pattern pattern = Pattern.compile(JdkRegEx.BUILD_DATETIME);
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
     *            The build date in {@link org.github.krashpad.util.jdk.JdkRegEx#BUILD_DATETIME_21} format.
     * @return The build <code>Date</code>.
     */
    public static final Date getDate21(String buildDate) {
        Date date = null;
        Pattern pattern = Pattern.compile(JdkRegEx.BUILD_DATETIME_21);
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

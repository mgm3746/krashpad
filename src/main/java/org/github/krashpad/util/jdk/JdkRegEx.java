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
package org.github.krashpad.util.jdk;

/**
 * Regular expression utility methods and constants for OpenJDK.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class JdkRegEx {

    /**
     * A 32-bit or 64-bit memory address.
     */
    public static final String ADDRESS = "(" + JdkRegEx.ADDRESS32 + "|" + JdkRegEx.ADDRESS64 + ")";

    /**
     * A 32-bit memory address.
     */
    public static final String ADDRESS32 = "((0x)?[0-9a-f]{8})";

    /**
     * A 64-bit memory address.
     */
    public static final String ADDRESS64 = "((0x)?[0-9a-f]{16})";

    /**
     * Memory map area.
     * 
     * For example: [vsyscall]
     */
    public static final String AREA = "(\\[(stack|vdso|vsyscall)\\])";

    /**
     * ActiveMQ main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * org.apache.activemq.artemis.boot.Artemis run
     * </pre>
     */
    public static final String ARTEMIS_COMMAND = "^.*org\\.apache\\.activemq\\.artemis\\.boot\\.Artemis run.*$";

    /**
     * Blank line.
     */
    public static final String BLANK_LINE = "^\\s+$";

    /**
     * <p>
     * Regular expression for JDK build date/time in MMM d yyyy HH:mm:ss format (see <code>SimpleDateFormat</code> for
     * date and time pattern definitions).
     * </p>
     * 
     * For example:
     * 
     * <pre>
     * Oct  6 2018 06:46:09
     * </pre>
     */
    public static final String BUILD_DATE_TIME = "([a-zA-Z]{3})[ ]{1,2}(\\d{1,2}) (\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})";

    /**
     * <p>
     * Regular expression for a JDK release string.
     * </p>
     * 
     * For example:
     * 
     * <pre>
     * 1.7.0_85-b15
     * 1.8.0_131-b11
     * 11.0.9+11-LTS
     * 12.0.1+12
     * </pre>
     */
    public static final String BUILD_STRING = "((1.6.0|1.7.0|1.8.0|9|10|11|12|13|14|15|16|17)[^\\)]{1,})";

    /**
     * Byte units identifier.
     */
    public static final String BYTES = "bB";

    /**
     * Regular expression for a Java class.
     * 
     * com.example.MyClass
     * 
     * com.example.MyClass$MyInnerClass
     */
    public static final String CLASS = "([a-z]{1,}\\.){1,}[a-zA-Z$_]{1}[a-zA-Z$_\\d]{1,}+[ ]*";

    /**
     * ActiveMQ CLI main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * org.apache.activemq.artemis.boot.Artemis queue stat --url tcp://mydomain:12345 --user myuser 
     * --password mypassword --maxRows 1234
     * 
     * org.apache.activemq.artemis.boot.Artemis queue purge --name ExpiryQueue --url tcp://mydomain:12345 
     * --user myuser --password mypassword
     * </pre>
     */
    public static final String COMMAND_ARTEMIS_CLI = "^.*org\\.apache\\.activemq\\.artemis\\.boot\\.Artemis (?!run).*$";

    /**
     * Cassandra main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * org.apache.cassandra.service.CassandraDaemon
     * </pre>
     */
    public static final String COMMAND_CASSANDRA = "^.*org\\.apache\\.cassandra\\.service\\.CassandraDaemon.*$";

    /**
     * JBoss version check string used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * org.jboss.as.standalone -Djboss.home.dir=C:\path\to -version
     * </pre>
     */
    public static final String COMMAND_JBOSS_VERSION = "^.+org\\.jboss\\.as\\.standalone .*(-[vV]|[-]{1,2}version)$";

    /**
     * JEUS main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * jeus.server.ServerBootstrapper -domain mydomain -u myuser -server myserver
     * </pre>
     */
    public static final String COMMAND_JEUS = "^.*jeus\\.server\\.ServerBootstrapper.*$";

    /**
     * Kafka main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * kafka.Kafka / path / to / my.properties
     * </pre>
     */
    public static final String COMMAND_KAFKA = "^.*kafka\\.Kafka.*$";

    /**
     * Spring Boot main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * <pre>
     * org.springframework.boot.loader.WarLauncher
     * </pre>
     */
    public static final String COMMAND_SPRING_BOOT = "^.*org\\.springframework\\.boot\\.loader\\.WarLauncher.*$";

    /**
     * Tomcat start main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * org.apache.catalina.startup.Bootstrap start
     */
    public static final String COMMAND_TOMCAT_START = "^.*org\\.apache\\.catalina\\.startup\\.Bootstrap start$";

    /**
     * Tomcat stop main class used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * org.apache.catalina.startup.Bootstrap stop stop
     */
    public static final String COMMAND_TOMCAT_STOP = "^.*org\\.apache\\.catalina\\.startup\\.Bootstrap stop.*$";

    /**
     * <p>
     * Regular expression for crash date/time in MMM d HH:mm:ss yyyy format (see <code>SimpleDateFormat</code> for date
     * and time pattern definitions).
     * </p>
     * 
     * For example:
     * 
     * <p>
     * 1) Without timezone:
     * </p>
     *
     * <pre>
     * Tue Aug 18 14:10:59 2020
     * </pre>
     * 
     * <p>
     * 2) With timezone:
     * </p>
     *
     * <pre>
     * Tue Mar  1 09:13:16 2022 UTC
     * </pre>
     */
    public static final String CRASH_DATE_TIME = "([a-zA-Z]{3}) ([a-zA-Z]{3})[ ]{1,2}(\\d{1,2}) "
            + "(\\d{2}):(\\d{2}):(\\d{2}) (\\d{4}).*";

    /**
     * Major ID and minor ID of the device where the file is located in hexadecimal.
     * 
     * To determine the device, convert the major id to decimal and cross reference lsblk output.
     * 
     * For example:
     * 
     * 7f6d941a5000-7f6d95338000 r-xp 00000000 fd:01 34113771
     * /usr/lib/jvm/java-11-openjdk-11.0.11.0.9-0.el8_3.x86_64/lib/server/libjvm.so
     * 
     * fd = 253, and 253:1 maps to fixed disk "/":
     * 
     * <code>
     * $ lsblk
     * NAME                                          MAJ:MIN RM   SIZE RO TYPE  MOUNTPOINT
     * nvme0n1                                       259:0    0   477G  0 disk  
     * ├─nvme0n1p1                                   259:1    0   600M  0 part  /boot/efi
     * ├─nvme0n1p2                                   259:2    0     1G  0 part  /boot
     * └─nvme0n1p3                                   259:3    0 475.4G  0 part  
     *   └─luks-43efaec5-acef-4c6a-b96e-3fa67b5a0a15 253:0    0 475.3G  0 crypt 
     *     ├─rhel-root                               253:1    0    50G  0 lvm   /
     *     ├─rhel-swap                               253:2    0  15.7G  0 lvm   [SWAP]
     *     └─rhel-home                               253:3    0 409.7G  0 lvm   /home
     *</code>
     * 
     * <p>
     * 1) Fixed disk:
     * </p>
     * 
     * <pre>
     * fd:0d
     * </pre>
     * 
     * <p>
     * 2) AWS block storage:
     * </p>
     * 
     * <pre>
     * 103:03
     * </pre>
     * 
     * <p>
     * 3) NFS:
     * </p>
     * 
     * <pre>
     * 00:38
     * </pre>
     */
    public static final String DEVICE_IDS = "([0-9a-f]{2,3}:[0-9a-f]{2,4})";

    /**
     * Memory map file path.
     * 
     * For example:
     * 
     * <p>
     * 1) Linux/Unix:
     * </p>
     * 
     * <pre>
     * /usr/lib64/libaio.so.1.0.1
     * </pre>
     * 
     * <p>
     * 2) Windows:
     * </p>
     * 
     * <pre>
     * E:\path\java\bin\server\jvm.dll
     * </pre>
     */
    public static final String FILE = "([A-Z]:)?(.*[/\\\\])*(.+)";

    /**
     * File offset
     * 
     * For example: 00016000
     */
    public static final String FILE_OFFSET = "([0-9a-f]{8})";

    /**
     * Regular expression for G1 gc data.
     * 
     * <pre>
     *  garbage-first heap   total 33554432K, used 22395212K [0x00007f56fc000000, 0x00007f5efc000000)
     *   region size 16384K, 182 young (2981888K), 19 survivors (311296K)
     * </pre>
     */
    public static final String G1 = "(" + JdkRegEx.G1_SIZE + "|  region size.+)";

    /**
     * Regular expression for G1 heap size data.
     * 
     * <pre>
     *  garbage-first heap   total 33554432K, used 22395212K [0x00007f56fc000000, 0x00007f5efc000000)
     * </pre>
     */
    public static final String G1_SIZE = " garbage-first heap   total " + JdkRegEx.SIZE + ", used " + JdkRegEx.SIZE
            + ".+";

    /**
     * Gigabyte units identifier.
     */
    public static final String GIGABYTES = "gG";

    /**
     * File inode number
     * 
     * For example: 134326056
     */
    public static final String INODE = "([0-9]{1,})";

    /**
     * Jar.
     * 
     * For example catalina.jar
     */
    public static final String JAR = "[a-zA-Z\\d-_\\.]{1,}\\.jar";

    /**
     * AppDynamics jar.
     * 
     * For example:
     * 
     * javaagent.jar
     */
    public static final String JAR_APP_DYNAMICS = "javaagent.jar";

    /**
     * JBoss EAP6 jar used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 7fb99ed0d000-7fb99ed15000 r--s 0006e000 f9:00 792511 /path/to/jbossweb-7.5.7.Final-redhat-1.jar
     */
    public static final String JAR_JBOSS_EAP6 = "^.+jbossweb.+\\.jar$";

    /**
     * JBoss EAP6 jar used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 7fb99ed0d000-7fb99ed15000 r--s 0006e000 f9:00 792511 /path/to/undertow-core-2.0.22.Final-redhat-00001.jar
     */
    public static final String JAR_JBOSS_EAP7 = "^.+undertow-core.+\\.jar$";

    /**
     * JEUS (Java Enterprise User Solution) jar used for {@link org.github.krashpad.util.jdk.JdkUtil.Application}
     * identification.
     * 
     * For example:
     * 
     * 7ff41c726000-7ff41c764000 r--s 004dd000 fd:02 9611207 /sw/was/tmax/jeus8/lib/system/jeus.jar
     */
    public static final String JAR_JEUS = "^.+jeus\\.jar.*$";

    /**
     * Red Hat Certificate System (RHCS), Red Hat Enterprise Linux (RHEL) Identity Management (IdM), or upstream Dogtag
     * Certificate System jar used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 7f47d77d3000-7f47d77d5000 r--s 00003000 fd:00 135061429 /usr/share/java/pki/pki-tomcat.jar
     */
    public static final String JAR_PKI_TOMCAT = "^.+pki-tomcat\\.jar$";

    /**
     * Tomcat jar used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 7f0c4b92c000-7f0c4b93e000 r--s 00183000 fd:04 51406344 /path/to/WEB-INF/lib/catalina.jar
     */
    public static final String JAR_TOMCAT = "^.+catalina\\.jar$";

    /**
     * JBoss jar used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 7fb99ed0d000-7fb99ed15000 r--s 0006e000 f9:00 792511 /path/to/jboss-eap-7.2/jboss-modules.jar
     */
    public static final String JAR_WILDFLY = "^.+jboss-modules\\.jar.*$";

    /**
     * Regular expression for java.nio.ByteBuffer class.
     * 
     * 
     */
    public static final String JAVA_NIO_BYTEBUFFER = "java[\\.\\/]nio[\\.\\/]ByteBuffer";

    /**
     * AppDynamics instrumentation.
     * 
     * For example:
     * 
     * -javaagent:/path/to/appdynamics/javaagent.jar
     * 
     * -javaagent:C:\appdynamics\appagent\javaagent\javaagent.jar
     */
    public static final String JAVAAGENT_APP_DYNAMICS = "-javaagent:([A-Z]:)?(.*[/\\\\])*appdynamics[/\\\\](.*[/\\\\])*"
            + "javaagent.jar";

    /**
     * Kilobyte units identifier.
     */
    public static final String KILOBYTES = "kK";

    /**
     * Megabyte units identifier.
     */
    public static final String MEGABYTES = "mM";

    /**
     * Object memory mapping.
     * 
     * For example:
     * 
     * <pre>
     * org.xnio.nio.WorkerThread {0x0000000800c89d28}
     * </pre>
     */
    public static final String MEMORY_MAPPING = "^" + CLASS + " \\{" + ADDRESS + "\\}$";

    /**
     * Memory region
     * 
     * For example:
     * 
     * 7f95caa94000-7f95caaa3000 (Linux)
     * 
     * 0x00000000742b0000 - 0x0000000074b70000 (Windows)
     */
    public static final String MEMORY_REGION = "((0x)?[0-9a-f]{8,16}[ ]{0,1}-[ ]{0,1}(0x)?[0-9a-f]{8,16})";

    /**
     * Regular expression for a metaspace event.
     * 
     * <pre>
     * Metaspace used 19510K, capacity 21116K, committed 21248K, reserved 1069056K
     *  class space    used 32477K, capacity 37071K, committed 40576K, reserved 1048576K
     * </pre>
     */
    public static final String METASPACE = "(" + JdkRegEx.METASPACE_SIZE + "|  class space.+)";

    /**
     * Regular expression for a metaspace event.
     * 
     * <pre>
     * Metaspace used 19510K, capacity 21116K, committed 21248K, reserved 1069056K
     * </pre>
     */
    public static final String METASPACE_SIZE = " Metaspace[ ]{1,7}used " + JdkRegEx.SIZE + ", (capacity "
            + JdkRegEx.SIZE + ", )?committed " + JdkRegEx.SIZE + ", reserved " + JdkRegEx.SIZE;

    /**
     * Native library.
     * 
     * For example:
     * 
     * <pre>
     * libjvm.so 
     * jvm.dll 
     * libpcre2-8.so.0.7.1
     * </pre>
     */
    public static final String NATIVE_LIBRARY = "[a-zA-Z\\d-_\\.]{1,}\\.(DLL|dll|so)(\\.\\d{1,}){0,}";

    /**
     * Dynatrace native library.
     * 
     * For example liboneagentjava.so, liboneagentloader.so, liboneagentproc.so, dtagent.dll, dtagentcore.dll
     */
    public static final String NATIVE_LIBRARY_DYNATRACE = "(liboneagent(java|loader|proc)|dtagent(core)?)"
            + "\\.(DLL|dll|so)";

    /**
     * JSS native library.
     * 
     * For example libjss4.so
     */
    public static final String NATIVE_LIBRARY_JSS = "libjss4\\.so";

    /**
     * Microsoft SQL Server native library.
     * 
     * For example mssql-jdbc_auth-8.2.2.x64.dll
     */
    public static final String NATIVE_LIBRARY_MICROSOFT_SQL_SERVER = "mssql-jdbc_auth-\\d\\.\\d\\.\\d\\.x64\\.dll";

    /**
     * Wily/DX APM native library.
     * 
     * For example libIntroscopeLinuxIntelAmd64Stats.so
     */
    public static final String NATIVE_LIBRARY_WILY = "libIntroscopeLinuxIntelAmd64Stats\\.so";

    /**
     * Regular expression for a old generation gc data.
     * 
     * 1) <code>GarbageCollection.PARALLEL_OLD</code>:
     * 
     * <pre>
     * ParOldGen       total 341504K, used 94378K [0x00000005cd600000, 0x00000005e2380000, 0x0000000719d00000)
     *  object space 341504K, 27% used [0x00000005cd600000,0x00000005d322aa70,0x00000005e2380000)
     * </pre>
     * 
     * 2) <code>GarbageCollection.SERIAL_OLD</code> when in combination with
     * <code>GarbageCollection.PARALLEL_SCAVENGE</code>:
     * 
     * ParOldGen total 699392K, used 91187K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
     * 
     * 3) <code>GarbageCollection.SERIAL_OLD</code> when in combination with <code>GarbageCollection.SERIAL</code>:
     * 
     * <pre>
     *   tenured generation   total 2165440K, used 937560K [0x000000073bd50000, 0x00000007c0000000, 0x00000007c0000000)
     *     the space 2165440K,  43% used [0x000000073bd50000, 0x00000007750e6118, 0x00000007750e6200, 
     *     0x00000007c0000000)
     * </pre>
     * 
     * 4) <code>GarbageCollection.CMS</code>:
     * 
     * concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, 0x00000007c0000000,
     * 0x00000007c0000000)
     */
    public static final String OLD_GEN = "(" + JdkRegEx.OLD_GEN_SIZE + "|  (object| the) space.+)";

    /**
     * Regular expression for a old generation heap size data.
     * 
     * 1) <code>GarbageCollection.PARALLEL_OLD</code>:
     * 
     * <pre>
     * ParOldGen       total 341504K, used 94378K [0x00000005cd600000, 0x00000005e2380000, 0x0000000719d00000)
     *  object space 341504K, 27% used [0x00000005cd600000,0x00000005d322aa70,0x00000005e2380000)
     * </pre>
     * 
     * 2) <code>GarbageCollection.SERIAL_OLD</code> when in combination with
     * <code>GarbageCollection.PARALLEL_SCAVENGE</code>:
     * 
     * ParOldGen total 699392K, used 91187K [0x00000000c0000000, 0x00000000eab00000, 0x00000000eab00000)
     * 
     * 3) <code>GarbageCollection.SERIAL_OLD</code> when in combination with <code>GarbageCollection.SERIAL</code>:
     * 
     * <pre>
     *   tenured generation   total 2165440K, used 937560K [0x000000073bd50000, 0x00000007c0000000, 0x00000007c0000000)
     * </pre>
     * 
     * 4) <code>GarbageCollection.CMS</code>:
     * 
     * <pre>
     * concurrent mark-sweep generation total 21676032K, used 6923299K [0x0000000295000000, 0x00000007c0000000, 
     * 0x00000007c0000000)
     * </pre>
     */
    public static final String OLD_GEN_SIZE = " (concurrent mark-sweep generation|PSOldGen|ParOldGen|"
            + "tenured generation)[ ]{1,7}total " + JdkRegEx.SIZE + ", used " + JdkRegEx.SIZE + ".+";

    /**
     * Units for JVM options that take a byte number.
     * 
     * For example: -Xss128k -Xmx2048m -Xms2G -XX:ThreadStackSize=256
     */
    public static final String OPTION_SIZE_BYTES = "((\\d{1,})(b|B|k|K|m|M|g|G)?)";

    /**
     * Oracle JDBC OCI driver.
     */
    public static final String ORACLE_JDBC_OCI_DRIVER = "libocijdbc.+.(dll|so)";

    /**
     * Oracle JDBC OCI driver path.
     */
    public static final String ORACLE_JDBC_OCI_DRIVER_PATH = "([A-Z]:)?(.*[/\\\\])*" + ORACLE_JDBC_OCI_DRIVER;

    /**
     * AppDynamics package.
     * 
     * For example:
     * 
     * com.singularity.ee.agent.util.reflect.ReflectionUtility
     */
    public static final String PACKAGE_APP_DYNAMICS = "com\\.singularity\\.ee\\.agent";

    /**
     * Percent rounded to whole number.
     * 
     * For example: 54%
     */
    public static final String PERCENT = "\\d{1,3}%";

    /**
     * Permission
     * 
     * For example: r--s
     */
    public static final String PERMISION = "([rwxps\\-]{4})";

    /**
     * An invalid pointer address.
     */
    public static final String POINTER_INVALID = "0x([f]{8}|[f]{16})";

    /**
     * A null pointer address.
     */
    public static final String POINTER_NULL = "0x([0]{8}|[0]{16})";

    /**
     * PostgreSQL JDBC driver.
     */
    public static final String POSTGRESQL_JDBC_DRIVER = "postgresql-42\\.(\\d)\\.\\d{1,}\\.jar";

    /**
     * PostgreSQL JDBC driver path.
     */
    public static final String POSTGRESQL_JDBC_DRIVER_PATH = "([A-Z]:)?(.*[/\\\\])*" + POSTGRESQL_JDBC_DRIVER;

    /**
     * A 32-bit or 64-bit register.
     */
    public static final String REGISTER = "(CR2|CSGSFS|ctr|EAX|EBP|EBX|ECX|EDI|EDX|EFLAGS|EIP|ERR|ESI|ESP|lr |pc |RAX|"
            + "RBP|RBX|RCX|RDI|RDX|RIP|RSI|RSP|[Rr]" + "\\d{1,2}[ ]{0,1})=(" + ADDRESS32 + "|" + ADDRESS64 + ")";

    /**
     * Red Hat OpenJDK rpm directory.
     * 
     * For example:
     * 
     * java-11-openjdk-11.0.9.11-0.el8_2.x86_64
     * 
     * java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le
     */
    public static final String RH_RPM_DIR = "(" + JdkRegEx.RH_RPM_OPENJDK8_DIR + "|" + JdkRegEx.RH_RPM_OPENJDK11_DIR
            + "|" + JdkRegEx.RH_RPM_OPENJDK17_DIR + ")";

    /**
     * Red Hat OpenJDK 17 rpm directory.
     * 
     * For example:
     * 
     * java-11-openjdk-11.0.7.10-4.el7_8.x86_64
     * 
     * java-11-openjdk-11.0.9.11-2.el8_3.x86_64
     * 
     * java-11-openjdk-11.0.10.0.9-0.el7_9.x86_64
     * 
     * java-11-openjdk-11.0.10.0.9-8.el8.x86_64
     * 
     * java-11-openjdk-11.0.17.0.8-2.el9_0.x86_64
     */
    public static final String RH_RPM_OPENJDK11_DIR = "(java\\-11\\-openjdk\\-11\\.0\\.\\d{1,2}\\.\\d{1,2}"
            + "(\\.\\d{1,2})?-\\d\\.el([789])(_(\\d{1,2}))?\\.x86_64)";
    /**
     * Red Hat OpenJDK 11 rpm libjvm.so file path.
     * 
     * For example:
     * 
     * /usr/lib/jvm/java-11-openjdk-11.0.7.10-4.el7_8.x86_64/lib/server/libjvm.so
     * 
     * /usr/lib/jvm/java-11-openjdk-11.0.17.0.8-2.el9_0.x86_64/lib/server/libjvm.so
     */
    public static final String RH_RPM_OPENJDK11_LIBJVM_PATH = "^\\/usr\\/lib\\/jvm\\/" + JdkRegEx.RH_RPM_OPENJDK11_DIR
            + "\\/lib\\/server\\/libjvm\\.so$";

    /**
     * Red Hat OpenJDK 17 rpm directory.
     * 
     * For example:
     * 
     * java-17-openjdk-17.0.1.0.12-2.el8_5.x86_64
     */
    public static final String RH_RPM_OPENJDK17_DIR = "(java\\-17\\-openjdk\\-17\\.0\\.\\d{1,2}\\.\\d{1,2}"
            + "(\\.\\d{1,2})?-\\d\\.el([89])_(\\d{1,2})\\.x86_64)";

    /**
     * Red Hat OpenJDK 17 rpm libjvm.so file path.
     * 
     * For example:
     * 
     * /usr/lib/jvm/java-17-openjdk-17.0.1.0.12-2.el8_5.x86_64/lib/server/libjvm.so
     */
    public static final String RH_RPM_OPENJDK17_LIBJVM_PATH = "^\\/usr\\/lib\\/jvm\\/" + JdkRegEx.RH_RPM_OPENJDK17_DIR
            + "\\/lib\\/server\\/libjvm\\.so$";

    /**
     * Red Hat OpenJDK 8 rpm directory.
     * 
     * For example:
     * 
     * java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64
     * 
     * java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64
     * 
     * java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le
     */
    public static final String RH_RPM_OPENJDK8_DIR = "(java\\-1\\.8\\.0\\-openjdk\\-1\\.8\\.0\\..+\\.el([678])"
            + "(_(\\d{1,2}))?\\.(ppc64(le)?|x86_64))";

    /**
     * Red Hat OpenJDK 8 rpm file path.
     * 
     * For example:
     * 
     * /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.262.b10-0.el6_10.x86_64/jre/lib/amd64/server/libjvm.so
     * 
     * /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.131-11.b12.el7.x86_64/jre/lib/amd64/server/libjvm.so
     * 
     * /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.342.b07-1.el7_9.x86_64/jre/lib/amd64/server/libjvm.so
     * 
     * /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.282.b08-1.el7_9.ppc64/jre/lib/ppc64/server/libjvm.so
     * 
     * /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.265.b01-1.el7_9.ppc64le/jre/lib/ppc64le/server/libjvm.so
     */
    public static final String RH_RPM_OPENJDK8_LIBJVM_PATH = "\\/usr\\/lib\\/jvm\\/" + JdkRegEx.RH_RPM_OPENJDK8_DIR
            + "\\/jre\\/lib\\/(amd64|ppc64(le)?)\\/server\\/libjvm\\.so";

    /**
     * Regular expression for Shenandoah gc data.
     * 
     * <pre>
     * Shenandoah Heap
     *  5734M total, 5734M committed, 3099M used
     *  2867 x 2048K regions
     * Status: marking, not cancelled
     * Reserved region:
     *  - [0x000000067a200000, 0x00000007e0800000)
     * Collection set:
     *  - map (vanilla): 0x00007f2e5435b3d1
     *  - map (biased):  0x00007f2e54358000
     * </pre>
     * 
     * <pre>
     * 3456M max, 3456M soft max, 3200M committed, 2325M used
     * </pre>
     */
    public static final String SHENANDOAH = "(Shenandoah Heap|" + JdkRegEx.SHENANDOAH_SIZE + "| \\d{1,5} x "
            + JdkRegEx.SIZE + " regions|Status:.+|Reserved region:| - \\[.+|Collection set:| - map.+)";

    /**
     * Regular expression for Shenandoah heap size data.
     * 
     * <pre>
     *  5734M total, 5734M committed, 3099M used
     * </pre>
     * 
     * <pre>
     * 3456M max, 3456M soft max, 3200M committed, 2325M used
     * </pre>
     */
    public static final String SHENANDOAH_SIZE = " " + JdkRegEx.SIZE + " (total|max)(, " + JdkRegEx.SIZE
            + " soft max)?, " + JdkRegEx.SIZE + " committed, " + JdkRegEx.SIZE + " used";

    /**
     * The size of memory in bytes (B), kilobytes (K), megabytes (M), or gigabytes (G) to a whole number or to one
     * decimal place.
     * 
     * See with G1 collector <code>-XX:+PrintGCDetails</code>.
     * 
     * With the G1 collector units are not consistent line to line or even within a single logging line.
     * 
     * Whole number examples: 2128K, 30M, 30G
     * 
     * Decimal examples: 0.0B, 8192.0K, 28.0M, 30.0G
     * 
     * With comma: 306,0M
     */
    public static final String SIZE = "(\\d{1,10}([\\.,]\\d)?)([" + BYTES + KILOBYTES + MEGABYTES + GIGABYTES + "])";

    /**
     * The size of memory in kilobytes (KB), megabytes (MB), or gigabytes (GB) to two decimal places. For example:
     * 
     * 3.00 KB, 395.36 MB, 1.00 GB
     */
    public static final String SIZE2 = "(\\d{1,}([\\.,]\\d{2})?) (KB|MB|GB|bytes)";

    /**
     * JEUS thread used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 0x00007ff434057000 JavaThread "jeus.server.Server" [_thread_blocked, id=2663909,
     * stack(0x00007ff43b657000,0x00007ff43b757000)]
     */
    public static final String THREAD_JEUS = "^.+\"jeus.server.Server\".+$";

    /**
     * Red Hat Certificate System (RHCS), Red Hat Enterprise Linux (RHEL) Identity Management (IdM), or upstream Dogtag
     * Certificate System thread used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 0x00007f47f9449800 JavaThread "ACMEEngineConfigFileSource" [_thread_blocked, id=369917,
     * stack(0x00007f47cc792000,0x00007f47cc893000)]
     */
    public static final String THREAD_PKI_TOMCAT = "^.+\"ACMEEngineConfigFileSource\".+$";

    /**
     * RHSSO thread used for {@link org.github.krashpad.util.jdk.JdkUtil.Application} identification.
     * 
     * For example:
     * 
     * 0x00005587a9039000 JavaThread "Brute Force Protector" [_thread_blocked, id=6073,
     * stack(0x00007f5abb897000,0x00007f5abb998000)]
     */
    public static final String THREAD_RHSSO = "^.+\"Brute Force Protector\".+$";

    /**
     * Timestamp. Milliseconds since JVM started.
     * 
     * For example: 487.020
     */
    public static final String TIMESTAMP = "(\\d{0,12}[\\.\\,]\\d{3})";

    /**
     * <p>
     * Regular expression for a JDK version string. Can be used for sorting purposes.
     * </p>
     * 
     * For example, given the following header:
     * 
     * <pre>
     * 7.0_85-b15
     * 8.0_131-b11
     * 11.0.9+11-LTS
     * 12.0.1+12
     * </pre>
     */
    public static final String VERSION_STRING = "((6.0|7.0|8.0|9|10|11|12|13|14|15|16|17)[^\\)]{1,})";

    /**
     * Wildfly or JBoss Enterprise Application Platform (EAP) executor pool thread name.
     * 
     * For example:
     * 
     * 0x00000000a3715000 JavaThread "pool-711-thread-1" [_thread_blocked, id=6848,
     * stack(0x00000000dd6e0000,0x00000000dd7e0000)
     */
    public static final String WILDFLY_EXECUTOR_POOL_THREAD = "pool-\\d{1,}-thread-\\d{1,}";

    /**
     * Windows exception code for access violation.
     */
    public static final String WINDOWS_EXCEPTION_CODE_ACCESS_VIOLATION = "0xc0000005";

    /**
     * Windows exception code for stack overflow.
     */
    public static final String WINDOWS_EXCEPTION_CODE_STACK_OVERFLOW = "0xc00000fd";

    /**
     * Regular expression for a young generation gc data.
     * 
     * 1) <code>GarbageCollection.PARALLEL_SCAVENGE</code>:
     * 
     * PSYoungGen total 153088K, used 116252K [0x00000000eab00000, 0x00000000f5580000, 0x0000000100000000)
     * 
     * 2) <code>GarbageCollection.SERIAL</code>:
     * 
     * def new generation total 629440K, used 511995K [0x00000006c0000000, 0x00000006eaaf0000, 0x0000000715550000)
     * 
     * 3) <code>GarbageCollection.PAR_NEW</code>:
     * 
     * par new generation total 766784K, used 37193K [0x0000000261000000, 0x0000000295000000, 0x0000000295000000)
     */
    public static final String YOUNG_GEN = "(" + JdkRegEx.YOUNG_GEN_SIZE + "|  (eden|from|to).+)";

    /**
     * Regular expression for a young generation heap size data.
     * 
     * 1) <code>GarbageCollection.PARALLEL_SCAVENGE</code>:
     * 
     * PSYoungGen total 153088K, used 116252K [0x00000000eab00000, 0x00000000f5580000, 0x0000000100000000)
     * 
     * 2) <code>GarbageCollection.SERIAL</code>:
     * 
     * def new generation total 629440K, used 511995K [0x00000006c0000000, 0x00000006eaaf0000, 0x0000000715550000)
     * 
     * 3) <code>GarbageCollection.PAR_NEW</code>:
     * 
     * par new generation total 766784K, used 37193K [0x0000000261000000, 0x0000000295000000, 0x0000000295000000)
     */
    public static final String YOUNG_GEN_SIZE = " ((def|par) new generation|PSYoungGen)[ ]{1,6}total " + JdkRegEx.SIZE
            + ", used " + JdkRegEx.SIZE + ".+";

    /**
     * Regular expression for Z gc data.
     * 
     * <pre>
     *  ZHeap           used 3999154M, capacity 4710400M, max capacity 4710400M
     * </pre>
     */
    public static final String Z = " ZHeap[ ]{1,}used " + JdkRegEx.SIZE + ", capacity " + JdkRegEx.SIZE
            + ", max capacity " + JdkRegEx.SIZE;

    /**
     * Regular expression for Z heap size data (convenience variable).
     * 
     */
    public static final String Z_SIZE = Z;

}

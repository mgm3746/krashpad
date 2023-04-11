# krashpad
A command line tool to parse Java fatal error logs.

## Supports

OpenJDK derivatives:
* Adoptium/AdoptOpenJDK
* Azul
* Microsoft Build of OpenJDK
* Oracle JDK
* Red Hat build of OpenJDK
* etc.
  
## Running

### docker/podman

Fastest/easiest way to run the latest version. The image in github.io is built/updated with every commit to the `main` branch.

Run the following in the directory where `hs_err_pid12345` exists:

```
$ docker run --pull=always -v "$PWD":/home/krashpad/files:z ghcr.io/mgm3746/krashpad:main -c /home/krashpad/files/hs_err_pid12345.log > hs_err_pid12345.log.pad
```

Name the analysis report after the log file with a ".pad" extension (or whatever convention you want to use).

### jar

Check out the `main` branch and build it.

Get source:
```
git clone https://github.com/mgm3746/krashpad.git
```

Build it:
```
cd krashpad
mvn clean (rebuilding)
mvn --settings settings.xml package
mvn --settings settings.xml javadoc:javadoc
```

Run it:
```
java -jar krashpad-LATEST.jar /path/to/hs_err_pid12345
```

## Usage

```
usage: krashpad [OPTION]... [FILE]
 -c,--console        print report to stdout instead of file
 -h,--help           help
 -o,--output <arg>   output file name (default report.txt)
```

Notes:
  1. The custom output file name option only applies when running as a jar. It is useful when analyzing multiple fatal error logs.
  
## Report
  
The report below is based on the following fatal error log:

https://github.com/mgm3746/krashpad/blob/main/src/test/data/dataset23.txt

```
$ cd /path/to/krashpad/src/test/data/
$ docker run --pull=always -v "$PWD":/home/krashpad/files:z ghcr.io/mgm3746/krashpad:main -o mgm.txt -c /home/krashpad/files/dataset23.txt > dataset23.txt.pad
```

A file called report.txt is created in the directory where the krashpad tool is run with analysis identifying the issue:


```
dataset23.txt
========================================
OS:
----------------------------------------
Version: Red Hat Enterprise Linux Server release 7.7 (Maipo)
ARCH: X86_64
CPUs (cpu x cpu cores x hyperthreading): 6
Memory: 25929M
Memory Free: 1500M (6%)
Memory Available: 18164M (70%)
Swap: 7632M
Swap Free: 7626M (100%)
========================================
JVM:
----------------------------------------
RPM: java-1.8.0-openjdk-1.8.0.222.b03-1.el7.x86_64
Vendor: RED_HAT
VM State: not at safepoint (normal execution)
Crash Date: Tue Dec  1 14:30:22 2020 KST
Run Time: 0d 0h 13m 8s
Garbage Collector(s): PARALLEL_SCAVENGE, PARALLEL_OLD
Heap Max: 1024M
Heap Allocation: 972M (95% Heap Max)
Heap Starting Address: 3072M
Compressed oops mode: BIT32
Metaspace Max: 256M
Metaspace Allocation: 84M (33% Metaspace Max)
Metaspace Used: 77M (92% Metaspace Allocation)
Thread Stack Size: 1024K
Thread Stack Memory: 136M
Code Cache Max: 420M
JVM Memory Max: >1836M (7% OS Memory)
========================================
Application:
----------------------------------------
ID: UNKNOWN
JVM Args: -Dcall_P203 -Xms1024m -Xmx1024m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=256m -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:/path/to/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/path/to/  -XX:+UnlockDiagnosticVMOptions -XX:+LogVMOutput -XX:LogFile=/path/to/jvm.log 
========================================
Threads:
----------------------------------------
Current thread: JavaThread "domain-10" daemon [_thread_in_native, id=25945, stack(0x00007fa1bf03b000,0x00007fa1bf13c000)]
# Java threads: 136
========================================
Error(s):
----------------------------------------
#  SIGSEGV (0xb) at pc=0x00007fa2a4353667, pid=25779, tid=0x00007fa1bf13b700
# C  [libc.so.6+0x14d667]  __memcpy_ssse3+0xb57
========================================
Stack:
----------------------------------------
Stack: [0x00007fa1bf03b000,0x00007fa1bf13c000],  sp=0x00007fa1bf138608,  free space=1013k
Native frames: (J=compiled Java code, j=interpreted, Vv=VM code, C=native code)
C  [libc.so.6+0x14d667]  __memcpy_ssse3+0xb57
C  [libzip.so+0x6034]  ZIP_GetEntry2+0xf4
C  [libzip.so+0x3c1d]  Java_java_util_zip_ZipFile_getEntry+0xfd
J 302  java.util.zip.ZipFile.getEntry(J[BZ)J (0 bytes) @ 0x00007fa287303dce [0x00007fa287303d00+0xce]
J 473 C2 java.util.jar.JarFile.getEntry(Ljava/lang/String;)Ljava/util/zip/ZipEntry; (22 bytes) @ 0x00007fa2873a5e94 [0x00007fa2873a5b40+0x354]
J 4848 C1 jeus.deploy.archivist.InputJarArchive.getEntry(Ljava/lang/String;)Ljava/io/InputStream; (104 bytes) @ 0x00007fa28763726c [0x00007fa2876371c0+0xac]
J 14034 C1 jeus.service.archive.ArchiveClassLoader.findClass0(Ljava/lang/String;Ljeus/deploy/archivist/AbstractArchive;)Ljava/lang/Class; (206 bytes) @ 0x00007fa28963fbfc [0x00007fa28963f5e0+0x61c]
j  jeus.servlet.loader.ContextLoader.findClass0(Ljava/lang/String;Ljeus/deploy/archivist/AbstractArchive;)Ljava/lang/Class;+3
...
========================================
ANALYSIS:
----------------------------------------
error
----------------------------------------
*There is an application or operations invalid use case resulting in an attempt to modify a file while Java has it open. A workaround to avoid the crash is to disable memory mapping in ZipFile with -Dsun.zip.disableMemoryMapping=true (at the cost of some loss in performance), or upgrade to JDK 11. Backporting a fix to JDK8 would not address the root cause, and the OpenJDK developers have determined it is too risky. References: (1) https://access.redhat.com/solutions/65104. (2) https://bugs.openjdk.java.net/browse/JDK-8142508.
----------------------------------------
warn
----------------------------------------
*JDK is not the latest version. Latest version is 1.8.0_345-b01 (newer by 14 versions and 1169 days).
*The fatal error log is very old (>30 days).
*MaxMetaspaceSize is less than CompressedClassSpaceSize. MaxMetaspaceSize includes CompressedClassSpaceSize, so MaxMetaspaceSize should be larger than CompressedClassSpaceSize. If MaxMetaspaceSize is set smaller than CompressedClassSpaceSize, the JVM auto adjusts CompressedClassSpaceSize as follows: CompressedClassSpaceSize = MaxMetaspaceSize - (2 * InitialBootClassLoaderMetaspaceSize).
*The gc log file has a static name and will be overwritten on JVM startup. Enable log file rotation and/or include process id or datestamp in the file name (e.g. -Xloggc:gc_%p_%t.log).
----------------------------------------
info
----------------------------------------
*Red Hat build of OpenJDK rpm install.
*The JDK is very old (>1 yr). Has the application been running without issue in production for a long time? Has something changed recently (e.g. application upgrade, load, etc.) that might have triggered the issue?
*Initial and/or max metaspace size is set. This is generally not recommended. Reference: https://access.redhat.com/solutions/1489263.
*Metaspace includes class metadata plus compressed class space.
*The -XX:+PrintHeapAtGC option is causing additional heap information to be output in the gc log. The additional data is not typically used for gc analysis. If there is not a good use case for enabling this option, remove it to reduce gc logging overhead.
*Diagnostic JVM options are enabled with -XX:+UnlockDiagnosticVMOptions. Diagnostic options add additional overhead and are intended for troubleshooting issues, not general production use.
*Consider enabling gc log file rotation with GC log file rotation (-XX:+UseGCLogFileRotation -XX:GCLogFileSize=N[K|M|G] -XX:NumberOfGCLogFiles=N) to protect disk space.
========================================
1 UNIDENTIFIED LOG LINE(S):
----------------------------------------
MGM was here!
========================================
```

Notes:
  1. The report contains nine sections: (1) OS, (2) Container, (3) JVM, (4) Application, (5) Threads, (6) Errors, (7) Stack, (8) ANALYSIS, and (9) UNIDENTIFIED LOG LINES.
  1. Some sections will only be displayed if relevant (e.g. *Container* if a container environment is identified).
  1. There is a limit of 1000 unidentified log lines that will be reported.
  1. Please report unidentified log lines by opening an issue: https://github.com/mgm3746/krashpad/issues. Attach the fatal error log after reviewing it and removing any sensitive information.
  
## Copyright

Copyright (c) 2020-2023 Mike Millson

All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse Public License - v 2.0 which accompanies this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/.    

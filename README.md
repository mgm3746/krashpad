***REMOVED*** krashpad
A command line tool to parse Java fatal error logs.

***REMOVED******REMOVED*** Supports

  * OpenJDK
  * AdoptOpenJDK
  * Oracle JDK
  * JDK 1.8 and higher
  
***REMOVED******REMOVED*** Building

Get source:
```
git clone https://github.com/mgm3746/krashpad.git
```

Build it:
```
cd krashpad
mvn clean (rebuilding)
mvn assembly:assembly
mvn javadoc:javadoc
```

***REMOVED******REMOVED*** Usage

```
usage: krashpad [OPTION]... [FILE]
 -h,--help           help
 -l,--latest         latest version
 -o,--output <arg>   output file name (default report.txt)
 -v,--version        version
```

Notes:
  1. By default a report called report.txt is created in the directory where the krashpad tool is run. Specifying a custom name for the output file is useful when analyzing multiple fatal error logs.
  1. Version information is included in the report by using the version and/or latest version options.
  
***REMOVED******REMOVED*** Report


```
hs_err_pid12345.log
========================================
Running krashpad version: 1.0.0-SNAPSHOT
========================================
OS:
----------------------------------------
Version: Red Hat Enterprise Linux Server release 7.4 (Maipo)
ARCH: X86_64
CPUs (cpu x cpu cores x hyperthreading): 16
Memory: 32174M
Memory Free: 446M (1%)
Swap: 16384M
Swap Free: 13803M (84%)
NPROC: 4096
========================================
Container:
----------------------------------------
Memory: 64276M (100%)
Memory Free: 21449M (33%)
Swap: 16000M (100%)
Swap Free: 16000M (100%)
NPROC: infinity
========================================
JVM:
----------------------------------------
Version: 1.8.0_131-b12
Vendor: RED_HAT
Application: JBOSS
VM State: not at safepoint (not fully initialized)
Crash Date: Tue Aug 18 12:34:56 2020
Run Time: 0d 0h 0m 0s
Garbage Collector(s): PARALLEL_SCAVENGE, PARALLEL_OLD
Heap Max: 8044M
Thread Stack Size: 1024K
JVM Memory: >9068M (28% Available Memory)
========================================
Threads:
----------------------------------------
Current thread: JavaThread "Unknown thread" [_thread_in_vm, id=30608, stack(0x00007f177a33b000,0x00007f177a43c000)]
***REMOVED*** Java threads: 0
========================================
Error(s):
----------------------------------------
***REMOVED*** There is insufficient memory for the Java Runtime Environment to continue.
***REMOVED*** Cannot create GC thread. Out of system resources.
***REMOVED***  Out of Memory Error (gcTaskThread.cpp:48), pid=28987, tid=0x00007f1af4875740
========================================
Stack:
----------------------------------------
Stack: [0x00007fffef80c000,0x00007fffef90c000],  sp=0x00007fffef909b20,  free space=1014k
***REMOVED***
V  [libjvm.so+0xad33a5]  VMError::report_and_die()+0x2e5
V  [libjvm.so+0x4e04c7]  report_vm_out_of_memory(char const*, int, unsigned long, VMErrorType, char const*)+0x67
V  [libjvm.so+0x5c3a6f]  GCTaskThread::GCTaskThread(GCTaskManager*, unsigned int, unsigned int)+0x13f
V  [libjvm.so+0x5c2c3d]  GCTaskManager::initialize()+0x36d
V  [libjvm.so+0x93ad32]  ParallelScavenge***REMOVED***:initialize()+0x3a2
V  [libjvm.so+0xa96fca]  Universe::initialize_heap()+0x16a
V  [libjvm.so+0xa972b3]  universe_init()+0x33
V  [libjvm.so+0x632110]  init_globals()+0x50
...
========================================
ANALYSIS:
----------------------------------------
error
----------------------------------------
*It appears a thread limit is preventing the JVM from starting. Check if the max user processes (nproc) or kernal max number of threads (kernel.pid_max) is being reached. Reference: https://access.redhat.com/solutions/46410.
----------------------------------------
warn
----------------------------------------
*JDK is not the latest version. Latest version is 1.8.0_282-b08 (newer by 16 versions and 1314 days).
----------------------------------------
info
----------------------------------------
*Red Hat build of OpenJDK rpm install.
*The JDK is very old. Has the application been running without issue in production for a long time? Has something changed recently (e.g. application upgrade, load, etc.) that might have triggered the issue?
*Consider adding -XX:+HeapDumpOnOutOfMemoryError, a standard recommended option to generate a heap dump when the first thread throws OutOfMemoryError. It does not impact performance (until the heap is actually written out) and generally should always be used, as it provides critical information in case of a memory error.
*GC log file rotation is not enabled. Consider enabling rotation (-XX:+UseGCLogFileRotation -XX:GCLogFileSize=N[K|M|G] -XX:NumberOfGCLogFiles=N) to protect disk space.
*Consider adding -XX:+PrintGCDetails, a standard recommended gc logging option that outputs details needed for GC analysis (e.g. generation, metaspace, times data).
========================================
1 UNIDENTIFIED LOG LINE(S):
----------------------------------------
***REMOVED***
========================================

```

Notes:
  1. The report contains nine sections: (1) Version, (2) OS, (3) Container, (4) JVM, (5) Threads, (6) Error(s), (7) Stack, (8) Analysis, and (9) Unidentified log lines.
  1. Some sections will only be displayed if relevant (e.g. Version if -v or -l options are used, Container if cgroups is used, etc.).
  1. There is a limit of 1000 unidentified log lines that will be reported.
  1. Please report unidentified log lines by opening an issue: https://github.com/mgm3746/krashpad/issues. Attach the fatal error log after reviewing it and removing any sensitive information.
  
***REMOVED******REMOVED*** Example

https://github.com/mgm3746/krashpad/blob/main/src/test/data/dataset23.txt

```
java -jar krashpad-1.0.0-SNAPSHOT.jar -v /path/to/dataset23.txt
```

A file called report.txt is created in the directory where the krashpad tool is run which contains the following information and analsyis identifying the issue:


```
dataset23.txt
========================================
Running krashpad version: 1.0.0-SNAPSHOT
========================================
OS:
----------------------------------------
Version: Red Hat Enterprise Linux Server release 7.7 (Maipo)
ARCH: X86_64
CPUs (cpu x cpu cores x hyperthreading): 6
Memory: 25929M
Memory Free: 1500M (6%)
Swap: 7632M
Swap Free: 7626M (100%)
========================================
Container:
----------------------------------------
Memory: 25929M (100%)
Memory Free: 1500M (6%)
Swap: 7632M (100%)
Swap Free: 7626M (100%)
========================================
JVM:
----------------------------------------
Version: 1.8.0_222-ea-b03
Vendor: RED_HAT
Application: UNKNOWN
VM State: not at safepoint (normal execution)
Crash Date: Tue Dec  1 14:30:22 2020 (KST)
Run Time: 0d 0h 13m 8s
Garbage Collector(s): PARALLEL_SCAVENGE, PARALLEL_OLD
Heap Max: 1024M
Heap Allocation: 972M (95% Heap Max)
Heap Used: 513M (53% Heap Allocation)
Metaspace Max: 256M
Metaspace Allocation: 84M (33% Metaspace Max)
Metaspace Used: 77M (92% Metaspace Allocation)
Thread Stack Size: 1024K
Thread Stack Memory: 136M
JVM Memory: >2440M (9% Available Memory)
========================================
Threads:
----------------------------------------
Current thread: JavaThread "domain-10" daemon [_thread_in_native, id=25945, stack(0x00007fa1bf03b000,0x00007fa1bf13c000)]
***REMOVED*** Java threads: 136
========================================
Error(s):
----------------------------------------
***REMOVED***  SIGSEGV (0xb) at pc=0x00007fa2a4353667, pid=25779, tid=0x00007fa1bf13b700
***REMOVED*** C  [libc.so.6+0x14d667]  __memcpy_ssse3+0xb57
========================================
Stack:
----------------------------------------
Stack: [0x00007fa1bf03b000,0x00007fa1bf13c000],  sp=0x00007fa1bf138608,  free space=1013k
***REMOVED***
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
*JDK is not the latest version. Latest version is 1.8.0_282-b08 (newer by 7 versions and 606 days).
*If this is a container environment, it is recommended to disable the writing of performance data to disk (/tmp/hsperfdata*) with -XX:+PerfDisableSharedMem. Disk IOPS (Input/output Operations Per Second) is shared among containers. Eliminating disk access will prevent one workload monopolizing disk from impacting all containers.
*MaxMetaspaceSize is less than CompressedClassSpaceSize. MaxMetaspaceSize includes CompressedClassSpaceSize, so MaxMetaspaceSize should be larger than CompressedClassSpaceSize. If MaxMetaspaceSize is set smaller than CompressedClassSpaceSize, the JVM auto adjusts CompressedClassSpaceSize as follows: CompressedClassSpaceSize = MaxMetaspaceSize - (2 * InitialBootClassLoaderMetaspaceSize).
----------------------------------------
info
----------------------------------------
*Red Hat build of OpenJDK rpm install.
*The JDK is very old. Has the application been running without issue in production for a long time? Has something changed recently (e.g. application upgrade, load, etc.) that might have triggered the issue?
*The JVM is running in a cgroup environment. This can be an indication the JVM is running in a container environment.
*Initial and/or max metaspace size is being set. This is generally not recommended. Reference: https://access.redhat.com/solutions/1489263.
*The -XX:+PrintHeapAtGC option is causing additional heap information to be output in the gc log. The additional data is not typically used for gc analysis. If there is not a good use case for enabling this option, remove it to reduce gc logging overhead.
*Diagnostic JVM options are enabled with -XX:+UnlockDiagnosticVMOptions. Diagnostic options add additional overhead and are intended for troubleshooting issues, not general production use. Remove diagnostic options after completing troubleshooting: (1) safepoint statistics -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1, (2) logging vm output -XX:+LogVMOutput, (3) -XX:+UnsyncloadClass).
*GC log file rotation is not enabled. Consider enabling rotation (-XX:+UseGCLogFileRotation -XX:GCLogFileSize=N[K|M|G] -XX:NumberOfGCLogFiles=N) to protect disk space.
========================================
```
  
***REMOVED******REMOVED*** Copyright

Copyright (c) 2021-2021 Mike Millson

All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse Public License - v 2.0 which accompanies this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/.    

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
package org.github.krashpad.domain.jdk;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestLogging {

    @Test
    void testAvailableLogDecorators() {
        String logLine = "Available log decorators: time (t), utctime (utc), uptime (u), timemillis (tm), "
                + "uptimemillis (um), timenanos (tn), uptimenanos (un), hostname (hn), pid (p), tid (ti), level (l), "
                + "tags (tg)";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testAvailableLogLevels() {
        String logLine = "Available log levels: off, trace, debug, info, warning, error";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testAvailableLogTags() {
        String logLine = "Available log tags: add, age, alloc, annotation, arguments, attach, barrier, biasedlocking, "
                + "blocks, bot, breakpoint, bytecode, cds, census, class, classhisto, cleanup, codecache, compaction, "
                + "compilation, condy, constantpool, constraints, container, coops, cpu, cset, data, datacreation, "
                + "dcmd, decoder, defaultmethods, director, dump, dynamic, ergo, event, exceptions, exit, fingerprint, "
                + "free, freelist, gc, handshake, hashtables, heap, humongous, ihop, iklass, indy, init, inlining, "
                + "install, interpreter, itables, jfr, jit, jni, jvmci, jvmti, lambda, library, liveness, load, "
                + "loader, logging, malloc, map, mark, marking, membername, memops, metadata, metaspace, "
                + "methodcomparator, methodhandles, mirror, mmu, module, monitorinflation, monitormismatch, nestmates, "
                + "nmethod, nmt, normalize, numa, objecttagging, obsolete, oldobject, oom, oopmap, oops, oopstorage, "
                + "os, owner, pagesize, parser, patch, path, perf, periodic, phases, plab, placeholders, preorder, "
                + "preview, promotion, protectiondomain, ptrqueue, purge, record, redefine, ref, refine, region, "
                + "reloc, remset, resolve, safepoint, sampling, scavenge, sealed, setting, smr, stackbarrier, "
                + "stackmap, stacktrace, stackwalk, start, startup, startuptime, state, stats, streaming, stringdedup, "
                + "stringtable, subclass, survivor, suspend, sweep, symboltable, system, table, task, thread, "
                + "throttle, time, timer, tlab, tracking, unload, unshareable, update, valuebasedclasses, "
                + "verification, verify, vmmutex, vmoperation, vmthread, vtables, vtablestubs, workgang";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testConfiguration() {
        String logLine = " #0: stdout all=warning uptime,level,tags";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testDescribedTagSets() {
        String logLine = "Described tag sets:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testHeader() {
        String logLine = "Logging:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testIdentity() {
        String logLine = "Log output configuration:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testLogging() {
        String logLine = " logging: Logging for the log framework itself";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.LOGGING,
                JdkUtil.LogEventType.LOGGING.toString() + " not identified.");
    }

    @Test
    void testParseLogLine() {
        String logLine = "Log output configuration:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof Logging,
                JdkUtil.LogEventType.LOGGING.toString() + " not parsed.");
    }
}
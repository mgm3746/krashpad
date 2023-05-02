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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestZgcMetadataBits {

    @Test
    void testBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Bad:               0x0000b00000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }
    
    
    @Test
    void testGood() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Good:              0x0000400000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }
    
    @Test
    void testIdentity() {
        String logLine = "ZGC Metadata Bits:";
        assertTrue(JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.ZGC_METADATA_BITS,
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }
    
    @Test
    void testMarked() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Marked:            0x0000100000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }
    
    @Test
    void testParseLogLine() {
        String logLine = "ZGC Metadata Bits:";
        assertTrue(JdkUtil.parseLogLine(logLine, null) instanceof ZgcMetadataBits,
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not parsed.");
    }
    
    

    @Test
    void testRemapped() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " Remapped:          0x0000400000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }


    @Test
    void testWeakBad() {
        ZgcMetadataBits priorLogEvent = new ZgcMetadataBits("ZGC Metadata Bits:");
        String logLine = " WeakBad:           0x0000300000000000";
        assertEquals(JdkUtil.LogEventType.ZGC_METADATA_BITS, JdkUtil.identifyEventType(logLine, priorLogEvent),
                JdkUtil.LogEventType.ZGC_METADATA_BITS.toString() + " not identified.");
    }

}
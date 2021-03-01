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
package org.github.krashpad.domain.jdk;

import org.github.krashpad.util.jdk.JdkUtil;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestCardTableEvent extends TestCase {

    public void testIdentity() {
        String logLine = "Card table byte_map: [0x00007f69332bf000,0x00007f6964000000] byte_map_base: "
                + "0x00007f297e79f000";
        Assert.assertTrue(JdkUtil.LogEventType.CARD_TABLE.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine, null) == JdkUtil.LogEventType.CARD_TABLE);
    ***REMOVED***

    public void testParseLogLine() {
        String logLine = "Card table byte_map: [0x00007f69332bf000,0x00007f6964000000] byte_map_base: "
                + "0x00007f297e79f000";
        Assert.assertTrue(JdkUtil.LogEventType.CARD_TABLE.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine, null) instanceof CardTableEvent);
    ***REMOVED***
***REMOVED***
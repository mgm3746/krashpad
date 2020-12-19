/**********************************************************************************************************************
 * errcat                                                                                                             *
 *                                                                                                                    *
 * Copyright (c) 2020 Mike Millson                                                                                    *
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
package org.github.errcat.domain.jdk;

import org.github.errcat.util.jdk.JdkUtil;
import org.github.errcat.util.jdk.JdkUtil.SignalCode;
import org.github.errcat.util.jdk.JdkUtil.SignalNumber;
import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestSigInfoEvent extends TestCase {

    public void testIdentity() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not identified.",
                JdkUtil.identifyEventType(logLine) == JdkUtil.LogEventType.SIGINFO);
    }

    public void testParseLogLine() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008";
        Assert.assertTrue(JdkUtil.LogEventType.SIGINFO.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof SigInfoEvent);
    }

    public void testSigsegvSegvMaperr() {
        String logLine = "siginfo: si_signo: 11 (SIGSEGV), si_code: 1 (SEGV_MAPERR), si_addr: 0x0000000000000008";
        SigInfoEvent event = new SigInfoEvent(logLine);
        Assert.assertEquals("Signal number not correct.", SignalNumber.SIGSEGV, event.getSignalNumber());
        Assert.assertEquals("Signal code not correct.", SignalCode.SEGV_MAPERR, event.getSignalCode());
    }
}
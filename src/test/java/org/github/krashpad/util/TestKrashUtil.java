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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestKrashUtil {

    @Test
    void testConvertStringToDate() {
        String MMM = "Jul";
        String d = "12";
        String yyyy = "2020";
        String HH = "18";
        String mm = "55";
        String ss = "08";
        Date date = KrashUtil.getDate(MMM, d, yyyy, HH, mm, ss);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Java Calendar month is 0 based
        assertEquals(6, calendar.get(Calendar.MONTH), "Start month not parsed correctly.");
        assertEquals(12, calendar.get(Calendar.DAY_OF_MONTH), "Start day not parsed correctly.");
        assertEquals(2020, calendar.get(Calendar.YEAR), "Start year not parsed correctly.");
        assertEquals(18, calendar.get(Calendar.HOUR_OF_DAY), "Start hour not parsed correctly.");
        assertEquals(55, calendar.get(Calendar.MINUTE), "Start minute not parsed correctly.");
        assertEquals(8, calendar.get(Calendar.SECOND), "Start second not parsed correctly.");
    }

    @Test
    void testWindowsNativeLibrary() {
        String library = "rmi.dll";
        assertTrue(KrashUtil.NATIVE_LIBRARIES_WINDOWS_JAVA.contains("rmi.dll"), library + " not found");
    }

}

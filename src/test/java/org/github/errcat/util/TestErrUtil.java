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
package org.github.errcat.util;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestErrUtil extends TestCase {

    public void testConvertStringToDate() {
        String MMM = "Jul";
        String d = "12";
        String yyyy = "2020";
        String HH = "18";
        String mm = "55";
        String ss = "08";
        Date date = ErrUtil.getDate(MMM, d, yyyy, HH, mm, ss);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Java Calendar month is 0 based
        Assert.assertEquals("Start month not parsed correctly.", 6, calendar.get(Calendar.MONTH));
        Assert.assertEquals("Start day not parsed correctly.", 12, calendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals("Start year not parsed correctly.", 2020, calendar.get(Calendar.YEAR));
        Assert.assertEquals("Start hour not parsed correctly.", 18, calendar.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals("Start minute not parsed correctly.", 55, calendar.get(Calendar.MINUTE));
        Assert.assertEquals("Start second not parsed correctly.", 8, calendar.get(Calendar.SECOND));
    ***REMOVED***
***REMOVED***

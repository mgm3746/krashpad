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
package org.github.krashpad.util.jdk;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Math utility methods and constants for OpenJDK.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class JdkMath {

    /**
     * Calculate percent.
     * 
     * @param part
     *            The numerator.
     * @param whole
     *            The denominator.
     * 
     * @return Percent part:whole rounded to the nearest whole number.
     */
    public static int calcPercent(final long part, final long whole) {
        if (part < 0) {
            throw new IllegalArgumentException("part: " + part);
        }
        if (whole < 0) {
            throw new IllegalArgumentException("whole: " + whole);
        }
        int percent;
        if (whole == 0) {
            if (part == 0 && whole == 0) {
                percent = 100;
            } else {
                percent = Integer.MAX_VALUE;
            }
        } else {
            BigDecimal calc = new BigDecimal(part);
            BigDecimal hundred = new BigDecimal("100");
            calc = calc.multiply(hundred);
            calc = calc.divide(new BigDecimal(whole), 0, RoundingMode.HALF_EVEN);
            percent = calc.intValue();
        }
        return percent;
    }

    /**
     * Convert a hexadecimal number to a decimal number.
     * 
     * For example:
     * 
     * 0x000000060d600000 = 25994199040
     * 
     * @param hex
     *            The hexadecimal number with or without a lead "0x".
     * 
     * @return The hexadecimal number converted to a decimal number.
     */
    public static long convertHexToDecimal(String hex) {
        long decimal = Long.MIN_VALUE;
        if (hex != null) {
            decimal = 0;
            int power = 0;
            int pos = 0;
            while (pos < hex.length()) {
                Character place = hex.charAt(hex.length() - pos - 1);
                if (!place.equals('x')) {
                    if (!place.equals('0')) {
                        int digit = Integer.parseInt(place.toString(), 16);
                        decimal = decimal + digit * (long) Math.pow(16, power);
                    }
                    pos++;
                    power++;
                } else {
                    break;
                }
            }
        }
        return decimal;
    }

    /**
     * Convert milliseconds to seconds.
     * 
     * For example: Convert 123456 to 123.456.
     * 
     * @param millis
     *            Milliseconds as a whole number.
     * @return Seconds rounded to 3 decimal places.
     */
    public static BigDecimal convertMillisToSecs(long millis) {
        BigDecimal secs = new BigDecimal(millis);
        return secs.movePointLeft(3).setScale(3, RoundingMode.HALF_EVEN);
    }

    /**
     * Convert seconds to milliseconds.
     * 
     * For example: Convert 0.0225213 to 23.
     * 
     * @param secs
     *            Seconds as a whole number or decimal.
     * @return Milliseconds rounded to a whole number.
     */
    public static BigDecimal convertSecsToMillis(String secs) {
        // BigDecimal does not accept decimal commas, only decimal periods
        BigDecimal millis = new BigDecimal(secs.replace(",", ".")).movePointRight(3);
        // Round down to avoid TimeWarpExceptions when events are spaced close together
        return millis.setScale(0, RoundingMode.DOWN);
    }

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private JdkMath() {
    }
}

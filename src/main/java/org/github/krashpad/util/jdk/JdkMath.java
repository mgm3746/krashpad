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
        ***REMOVED***
        if (whole < 0) {
            throw new IllegalArgumentException("whole: " + whole);
        ***REMOVED***
        int percent;
        if (whole == 0) {
            if (part == 0 && whole == 0) {
                percent = 100;
            ***REMOVED*** else {
                percent = Integer.MAX_VALUE;
            ***REMOVED***
        ***REMOVED*** else {
            BigDecimal calc = new BigDecimal(part);
            BigDecimal hundred = new BigDecimal("100");
            calc = calc.multiply(hundred);
            calc = calc.divide(new BigDecimal(whole), 0, RoundingMode.HALF_EVEN);
            percent = calc.intValue();
        ***REMOVED***
        return percent;
    ***REMOVED***

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
                    ***REMOVED***
                    pos++;
                    power++;
                ***REMOVED*** else {
                    break;
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
        return decimal;
    ***REMOVED***

    /**
     * Make default constructor private so the class cannot be instantiated.
     */
    private JdkMath() {
    ***REMOVED***
***REMOVED***

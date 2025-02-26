/**********************************************************************************************************************
 * krashpad                                                                                                           *
 *                                                                                                                    *
 * Copyright (c) 2020-2025 Mike Millson                                                                               *
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
class TestRelease {

    @Test
    void testBuildDateEstimateOrUnknown() {
        String buildDate = "Apr 12 2024";
        Release release = new Release(buildDate, 26, "11.0.23+9-LTS");
        assertTrue(release.isBuildDateEstimate(), buildDate + " not identified as an estimated build date.");
    }

    @Test
    void testBuildDateMidnight() {
        String buildDate = "Apr 12 2024 00:00:00 ";
        Release release = new Release(buildDate, 26, "11.0.23+9-LTS");
        assertFalse(release.isBuildDateEstimate(),
                buildDate + " incorrectly identified as an estimated/unknown build date.");
    }
}
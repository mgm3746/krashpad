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

/**
 * <p>
 * <code>NativeMemoryTracking</code> summary used for reporting.
 * </p>
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class NativeMemoryTrackingSummary {

    /**
     * Memory category.
     */
    private String category;

    /**
     * Memory committed (kilobytes).
     */
    private int committed;

    /**
     * Default constructor.
     * 
     * @param category
     *            Memory category.
     * @param committed
     *            Memory committed (kilobytes).
     */
    public NativeMemoryTrackingSummary(String category, int committed) {
        this.category = category;
        this.committed = committed;
    }

    public String getCategory() {
        return category;
    }

    public int getCommitted() {
        return committed;
    }

}

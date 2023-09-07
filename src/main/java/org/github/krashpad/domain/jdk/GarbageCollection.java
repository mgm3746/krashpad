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

/**
 * Garbage collection safepoint (stop the world) event parsed from
 * {@link org.github.krashpad.domain.jdk.GcHeapHistoryEvent}s.
 * 
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class GarbageCollection {

    /**
     * The time when the GC event ended in milliseconds after JVM startup.
     */
    private long timestampEndGc;

    /**
     * The time when the GC event started in milliseconds after JVM startup.
     */
    private long timestampStartGc;

    /**
     * @return The elapsed clock time for the GC event in milliseconds.
     */
    public long getDuration() {
        return timestampEndGc - timestampStartGc;
    }

    public long getTimestampEndGc() {
        return timestampEndGc;
    }

    public long getTimestampStartGc() {
        return timestampStartGc;
    }

    public void setTimestampEndGc(long timestampEndGc) {
        this.timestampEndGc = timestampEndGc;
    }

    public void setTimestampStartGc(long timestampStartGc) {
        this.timestampStartGc = timestampStartGc;
    }

}

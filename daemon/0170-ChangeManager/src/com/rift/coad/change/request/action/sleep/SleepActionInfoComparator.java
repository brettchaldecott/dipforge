/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2013  2015 Burntjam
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * SleepActionInfoComparator.java
 */
package com.rift.coad.change.request.action.sleep;

import java.util.Comparator;
import java.util.Date;

/**
 * This object is used to compare the two objects.
 * 
 * @author brett chaldecott
 */
public class SleepActionInfoComparator implements Comparator<SleepActionInfo> {

    /**
     * Returns a negative integer, zero, or a positive integer as the first 
     * argument is less than, equal to, or greater than the second.
     * 
     * @param t The argument to perform the comparison on.
     * @param t1 The argument to use as the comparison object
     * @return True if equal
     */
    public int compare(SleepActionInfo t, SleepActionInfo t1) {
        String tName = t.getActionInstanceId();
        String t1Name = t1.getActionInstanceId();
        if (tName.equals(t1Name)) {
            return 0;
        }
        Date currentTime = new Date();
        long tDifference = ((currentTime.getTime() - t.getStart().getTime()) -
                t.getPeriod()) * -1;
        long t1Difference = ((currentTime.getTime() - t1.getStart().getTime()) -
                t1.getPeriod()) * -1;
        if (tDifference == t1Difference) {
            return tName.compareTo(t1Name);
        }
        return (int)(tDifference - t1Difference);
    }
    
    
}

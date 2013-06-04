/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2013  Rift IT Contracting
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
 * SleepManager.java
 */

package com.rift.coad.change.request.action.sleep;

import java.util.List;

/**
 * This interfaces provides access to the sleep functionality.
 * 
 * @author brett chaldecott
 */
public interface SleepManager {
    
    /**
     * This method provides 
     * 
     * @param actionInstanceId The id of the action instance that needs to sleep
     * @param period The period of time to sleep for.
     * @throws SleepManagerException 
     */
    public void addAction(String actionInstanceId, long period) 
            throws SleepManagerException;
    
    
    /**
     * This method returns the list of actions id's that are currently sleeping.
     * @return The 
     * @throws SleepManagerException 
     */
    public List<String> listSleepingActions() throws SleepManagerException;
    
    
    /**
     * This method returns the action instance sleep period.
     * 
     * @param actionInstanceId The id of the action instance.
     * @return The sleep manager.
     * @throws SleepManagerException 
     */
    public long getActionInstanceSleepPeriod(String actionInstanceId)
            throws SleepManagerException;
}

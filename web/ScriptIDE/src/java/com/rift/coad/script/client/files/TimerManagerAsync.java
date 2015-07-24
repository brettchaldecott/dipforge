/*
 * ScriptIDE: The coadunation ide for editing scripts in coadunation.
 * Copyright (C) 2010  2015 Burntjam
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
 * TimerManagerAsync.java
 */

package com.rift.coad.script.client.files;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This is the async version of the timer manager
 *
 * @author brett chaldecott
 */
public interface TimerManagerAsync {

    /**
     * This method registers the timer event.
     *
     * @param JNDI The jndi reference for the timer entry.
     * @param month The month.
     * @param day The day.
     * @param hour The hour.
     * @param minute The minute.
     * @param event The script.
     * @param recure does it recure.
     * @param asyncCallback
     */
    public void register(java.lang.String JNDI, int month, 
            int day, int hour, int minute, java.lang.String event,
            boolean recure, AsyncCallback asyncCallback);


    /**
     * This method is called to retrieve list of recurring events.
     *
     * @param asyncCallback The call back.
     */
    public void listEvents(AsyncCallback asyncCallback);

    
    /**
     * The async delete method.
     *
     * @param event The event to delete.
     * @param asyncCallback The call back.
     */
    public void deleteEvent(java.lang.String event, AsyncCallback asyncCallback);
    
}

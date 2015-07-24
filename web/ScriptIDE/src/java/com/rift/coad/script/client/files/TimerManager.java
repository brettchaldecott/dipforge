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
 * TimerManager.java
 */


package com.rift.coad.script.client.files;
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * The timer manager RPC service
 *
 * @author brett chaldecott
 */
public interface TimerManager extends RemoteService{
    /**
     * This method is called in order to register an event on the database.
     *
     * @param JNDI This is a string for the JNDI of the daemon that is to be
     *          called by the event.
     * @param month The month of the event. -1 will occur monthly.
     * @param day The day of the event. -1 will occur daily.
     * @param hour The hour of the event. -1 will occur hourly.
     * @param minute The minute of the event. -1 will occur every minute.
     * @param event This is a serializable object used to identify an individual
     *          event.
     * @param recure The value of this indicates whether an event will occur
     *          more then once of be deleted from the database after a single
     *          occurence. True will cause it to recure, false will result in it
     *          being deleted after a single occurence
     */
    public void register(String JNDI, int month, int day, int hour, int minute,
            String event, boolean recure)
            throws TimerManagerException;
    
    
    /**
     * This method returns a list of all the events currently stored in the
     * database.
     *
     * @return The method returns a List of TimerEvent objects. The TimerEvent
     *          object contains all the properties of an event from the
     *          database.
     */
    public TimerEvent[] listEvents() throws TimerManagerException;


    /**
     * This method deletes the event identified by the string.
     *
     * @param event The event to delete.
     * @throws com.rift.coad.script.client.files.TimerManagerException
     */
    public void deleteEvent(String event) throws TimerManagerException;
}

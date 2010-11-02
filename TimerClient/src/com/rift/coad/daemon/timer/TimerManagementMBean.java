/*
 * Timer: The timer class
 * Copyright (C) 2008  Rift IT Contracting
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
 * TimerManagementMBean.java
 */

package com.rift.coad.daemon.timer;

// java imports
import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Version;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.ExceptionInfo;


/**
 * The timer management interface.
 *
 * @author brett chaldecott
 */
public interface TimerManagementMBean {
    
    /**
     * This method is called in order to register an event on the database.
     *
     * @param JNDI This is a string for the JNDI of the daemon that is to be 
     *          called by the event.
     * @param month The month of the event. -1 will occur monthly.
     * @param day The day of the event. -1 will occur daily.
     * @param hour The hour of the event. -1 will occur hourly.
     * @param minute The minute of the event. -1 will occur every minute.
     * @param event The string containing the event name.
     * @param recure The value of this indicates whether an event will occur 
     *          more then once of be deleted from the database after a single 
     *          occurence. True will cause it to recure, false will result in it
     *          being deleted after a single occurence
     */
    @MethodInfo(description="This method registers an event with the timer.")
    @Version(number="1.0")
    public void register(@ParamInfo(name="JNDI",
            description="A string containing the JNDI URL.")String JNDI, 
            @ParamInfo(name="month",
            description="The month of the year 0-11 (0=January, 11=December) " +
            "or -1 for monthly.")int month, 
            @ParamInfo(name="day",
            description="The day of the month 1-31 or -1 for daily.")int day,
            @ParamInfo(name="hour",
            description="The hour of the day 1-24 or -1 for hourly.")int hour, 
            @ParamInfo(name="minute",
            description="The minute of the hour 0-59 or -1 for every minute.")int minute, 
            @ParamInfo(name="event",
            description="The string containing the event value.")String event, 
            @ParamInfo(name="recure",
            description="TRUE if the event must recure, FALSE if not.")boolean recure) 
            throws RemoteException, TimerException;
    
    
    /**
     * This method returns a list of all the events currently stored in the 
     * database.
     *
     * @return The method returns a List of TimerEvent objects. The TimerEvent 
     *          object contains all the properties of an event from the 
     *          database.
     */
    @MethodInfo(description="This method returns a list of events registered with the timer.")
    @Version(number="1.0")
    @Result(description="The list of events registered with the timer server.")
    public List listEvents() throws RemoteException, TimerException;
    
    
    /**
     * This method deletes an event based on a supplied event ID which 
     * corresponds to the database ID.
     *
     * @param eventID The database ID of the event.
     */
    @MethodInfo(description="This method deletes the event with the specified id.")
    @Version(number="1.0")
    public void deleteEvent(@ParamInfo(name="eventId",
            description="The id of the evnet to delete.")int eventId)
            throws RemoteException, TimerException;
    
}

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

// the package path
package com.rift.coad.daemon.timer;

// java imports
import java.util.List;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;


// log4j imports
import org.apache.log4j.Logger;


/**
 * The time management object is the implementation of the TimerManagementMBean
 * interface.
 *
 * @author brett chaldecott
 */
public class TimerManagement implements TimerManagementMBean {
    
    // class constants
    private final static String TIMER_DAEMON = "java:comp/env/bean/timer/Daemon";
    
    // private member variables
    private static Logger log = Logger.getLogger(TimerManagement.class);
    private Timer timer = null;
    
    
    /**
     * Creates a new instance of TimerManagement
     */
    public TimerManagement() {
    }
    
    
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
    public void register(String JNDI, int month, int day, int hour, int minute, 
            String event, boolean recure) 
            throws TimerException {
        try {
            Timer timer = this.getTimer();
            timer.register(JNDI,month,day,hour,minute,event,recure);
        } catch (TimerException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to register the event : " + ex.getMessage(),ex);
            throw new TimerException(
                    "Failed to register the event : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a list of all the events currently stored in the 
     * database.
     *
     * @return The method returns a List of TimerEvent objects. The TimerEvent 
     *          object contains all the properties of an event from the 
     *          database.
     */
    public List listEvents() throws TimerException {
        try {
            Timer timer = this.getTimer();
            TimerEvent[] events = timer.listEvents();
            ArrayList list = new ArrayList();
            for (int index = 0; index < events.length; index++) {
                list.add(events[index]);
            }
            return list;
        } catch (TimerException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of events : " + 
                    ex.getMessage(),ex);
            throw new TimerException(
                    "Failed to retrieve the list of events : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method deletes an event based on a supplied event ID which 
     * corresponds to the database ID.
     *
     * @param eventID The database ID of the event.
     */
    public void deleteEvent(int eventId)
            throws TimerException {
        try {
            Timer timer = this.getTimer();
            timer.deleteEvent(eventId);
        } catch (TimerException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to delete the event : " + ex.getMessage(),ex);
            throw new TimerException(
                    "Failed to delete the event : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the timer object.
     */
    public Timer getTimer() throws TimerException {
        try {
            if (timer != null) {
                return timer;
            }
            // resolve the local reference for the timer
            Context context = new InitialContext();
            timer = (Timer)context.lookup(TIMER_DAEMON);
            return timer;
        } catch (Throwable ex) {
            log.error("Failed to connect to the timer object : " + 
                    ex.getMessage(),ex);
            throw new TimerException("Failed to connect to the timer object : " + 
                    ex.getMessage(),ex);
        }
    }
}

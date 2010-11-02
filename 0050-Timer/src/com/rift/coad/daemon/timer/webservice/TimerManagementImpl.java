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
package com.rift.coad.daemon.timer.webservice;

// java imports
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


// log4j imports
import org.apache.log4j.Logger;


/**
 * The time management object is the implementation of the
 * interface.
 *
 * @author brett chaldecott
 */
public class TimerManagementImpl implements TimerManagement {
    
    // class constants
    private final static String TIMER_DAEMON = 
            "java:comp/env/bean/timer/Daemon";
    
    // private member variables
    private static Logger log = Logger.getLogger(TimerManagementImpl.class);
    private com.rift.coad.daemon.timer.Timer timer = null;
    
    
    /**
     * Creates a new instance of TimerManagement
     */
    public TimerManagementImpl() {
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
            com.rift.coad.daemon.timer.Timer timer = this.getTimer();
            timer.register(JNDI,month,day,hour,minute,event,recure);
        } catch (TimerException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to register the event : " + ex.getMessage(),ex);
            throwTimerException(
                    "Failed to register the event : " + ex.getMessage(),ex);
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
    public TimerEvent[] listEvents() throws TimerException {
        try {
            com.rift.coad.daemon.timer.Timer timer = this.getTimer();
            com.rift.coad.daemon.timer.TimerEvent[] events = timer.listEvents();
            TimerEvent[] timerEvents = new TimerEvent[events.length];
            for (int index = 0; index < events.length; index++) {
                timerEvents[index] = new TimerEvent();
                timerEvents[index].id = events[index].getId();
                timerEvents[index].jndi = events[index].getJndi();
                timerEvents[index].event = events[index].getEvent().toString();
                timerEvents[index].month = events[index].getMonth();
                timerEvents[index].day = events[index].getDay();
                timerEvents[index].hour = events[index].getHour();
                timerEvents[index].minute = events[index].getMinute();
                timerEvents[index].recure = events[index].getRecure();
            }
            return timerEvents;
        } catch (TimerException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of events : " + 
                    ex.getMessage(),ex);
            throwTimerException(
                    "Failed to retrieve the list of events : " + 
                    ex.getMessage(),ex);
            // this is here for the compiler
            return null;
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
            com.rift.coad.daemon.timer.Timer timer = this.getTimer();
            timer.deleteEvent(eventId);
        } catch (TimerException ex) {
            throw ex;
        } catch (Throwable ex) {
            log.error("Failed to delete the event : " + ex.getMessage(),ex);
            throwTimerException(
                    "Failed to delete the event : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns a reference to the timer object.
     */
    public com.rift.coad.daemon.timer.Timer getTimer() throws TimerException {
        try {
            if (timer != null) {
                return timer;
            }
            // resolve the local reference for the timer
            Context context = new InitialContext();
            timer = (com.rift.coad.daemon.timer.Timer)context.lookup(TIMER_DAEMON);
            return timer;
        } catch (Throwable ex) {
            log.error("Failed to connect to the timer object : " + 
                    ex.getMessage(),ex);
            throwTimerException("Failed to connect to the timer object : " + 
                    ex.getMessage(),ex);
            return null;
        }
    }
    
    
    /**
     * This method wrapps the throwing of the dns exception.
     *
     * @param message The message to put in the exception
     * @param ex The exception stack.
     * @exception DNSException
     */
    private void throwTimerException(String message, Throwable ex) throws
            TimerException {
        TimerException exception = new TimerException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        throw exception;
    }
}

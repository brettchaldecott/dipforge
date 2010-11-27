/*
 * TimerManagerImpl.java
 *
 * Created on 05 February 2010, 3:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.rift.coad.script.server.files;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.daemon.timer.Timer;
import com.rift.coad.script.client.files.TimerEvent;
import com.rift.coad.script.client.files.TimerManager;
import com.rift.coad.script.client.files.TimerManagerException;
import com.rift.coad.util.connection.ConnectionManager;
import org.apache.log4j.Logger;

/**
 * The timer management implementation.
 *
 * @author brett chaldecott
 */
public class TimerManagerImpl extends RemoteServiceServlet implements
        TimerManager {

    // private member variables
    private static Logger log = Logger.getLogger(TimerManagerImpl.class);

    /**
     * The default default constructor for the timer object.
     */
    public TimerManagerImpl() {
    }


    /**
     * This method is called to register the event with the timer object.
     *
     * @param JNDI The jndi reference.
     * @param month The month reference.
     * @param day The day
     * @param hour The hour
     * @param minute The minute.
     * @param event The event to call.
     * @param recure If recurring.
     * @throws com.rift.coad.script.client.files.TimerManagerException
     */
    public void register(String JNDI, int month, int day, int hour, int minute, String event, boolean recure) throws TimerManagerException {
        try {
            Timer daemon = (Timer)
                    ConnectionManager.getInstance().getConnection(Timer.class,
                    "timer/Daemon");
            // check for a duplicate
            com.rift.coad.daemon.timer.TimerEvent[] events = daemon.listEvents();
            for (com.rift.coad.daemon.timer.TimerEvent timerEvent : events) {
                String file = timerEvent.getEvent().toString();
                if (file.equals(event)) {
                    daemon.deleteEvent(timerEvent.getId());
                    break;
                }
            }
            daemon.register(JNDI, month, day, hour, minute, event, recure);
        } catch (Exception ex) {
            log.error("Failed to register the event : " + ex.getMessage(),ex);
            throw new TimerManagerException("Failed to register the event : " +
                    ex.getMessage());
        }
    }


    /**
     * The list of events.
     *
     * @return The timer events.
     * @throws com.rift.coad.script.client.files.TimerManagerException
     */
    public TimerEvent[] listEvents() throws TimerManagerException {
        try {
            Timer daemon = (Timer)
                    ConnectionManager.getInstance().getConnection(Timer.class,
                    "timer/Daemon");
            com.rift.coad.daemon.timer.TimerEvent[] events = daemon.listEvents();
            TimerEvent[] result = new TimerEvent[events.length];
            for (int index = 0; index < result.length; index++) {
                com.rift.coad.daemon.timer.TimerEvent event = events[index];
                result[index] = new TimerEvent(
                        event.getId(), event.getJndi(), event.getEvent().toString(),
                        event.getMonth(),event.getDay(),event.getHour(),
                        event.getMinute(),event.getRecure());
            }
            return result;
        } catch (Exception ex) {
            log.error("Failed to register the event : " + ex.getMessage(),ex);
            throw new TimerManagerException("Failed to register the event : " +
                    ex.getMessage());
        }
    }


    /**
     * This method is called to delete an event.
     *
     * @param event The string containing the event to delete.
     * @throws com.rift.coad.script.client.files.TimerManagerException
     */
    public void deleteEvent(String event) throws TimerManagerException {
        try {
            Timer daemon = (Timer)
                    ConnectionManager.getInstance().getConnection(Timer.class,
                    "timer/Daemon");
            // check for a duplicate
            com.rift.coad.daemon.timer.TimerEvent[] events = daemon.listEvents();
            for (com.rift.coad.daemon.timer.TimerEvent timerEvent : events) {
                String file = timerEvent.getEvent().toString();
                if (file.equals(event)) {
                    daemon.deleteEvent(timerEvent.getId());
                    break;
                }
            }
        } catch (Exception ex) {
            log.error("Failed to delete the event : " + ex.getMessage(),ex);
            throw new TimerManagerException("Failed to delete the event : " +
                    ex.getMessage());
        }
    }



}

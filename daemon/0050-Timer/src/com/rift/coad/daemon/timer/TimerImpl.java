/*
 * Timer: The timer class
 * Copyright (C) 2006-2007  2015 Burntjam
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
 * TimerImpl.java
 */

package com.rift.coad.daemon.timer;

// java imports
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.naming.Context;
import javax.naming.InitialContext;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.common.CommonException;
import com.rift.coad.lib.common.ObjectSerializer;
import com.rift.coad.lib.bean.BeanRunnable;
//import com.rift.coad.daemon.timer.db.util.HibernateUtil;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.daemon.timer.db.Schedule;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.lib.thread.CoadunationThread;
import com.rift.coad.util.connection.ConnectionManager;
import com.rift.coad.util.transaction.UserTransactionWrapper;

// hibernate imports
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * The Timer implementation implements the Timer interface. All the logic 
 * involved in creating, processing, listing and deleting events is within the 
 * methods of this class.
 *
 * @author Glynn Chaldecott
 */
public class TimerImpl implements Timer, BeanRunnable {
    
    /**
     * The timer thread is responsible for peforming the processing on the
     * target.
     */
    public class TimerThread extends CoadunationThread {
        
        // private member variables
        private TimerImpl timer;
        private int id;
        private String jndi;
        private byte[] b_event;
        private byte recure;
        
        /**
         * The constructor of the timer thread
         */
        public TimerThread(TimerImpl timer, int id, String jndi, 
                byte[] b_event, byte recure) throws Exception {
            this.timer = timer;
            this.id = id;
            this.jndi = jndi;
            this.b_event = b_event;
            this.recure = recure;
        }
        
        /**
         * This method is reponsible for performing the processing for this 
         * object.
         */
        public void process() {
            UserTransactionWrapper utw = null;
            try {
                utw = new UserTransactionWrapper();
                utw.begin();
                log.info("Finding service [" + jndi + "] to invoke from timer.");
                com.rift.coad.daemon.timer.TimerEventHandler
                        beanInterface = (com.rift.coad.daemon.timer.TimerEventHandler)
                        ConnectionManager.getInstance().
                    getConnection(com.rift.coad.daemon.timer.TimerEventHandler.class,
                            jndi);
                Serializable event = (Serializable)
                ObjectSerializer.deserialize(b_event);
                beanInterface.processEvent(event);
                
                if (recure == 0) {
                    try {
                        Session session = HibernateUtil
                                .getInstance(com.rift.coad.daemon.timer.TimerImpl.class)
                                .getSession();
                        session.createQuery(
                                "DELETE FROM Schedule as sche WHERE " +
                                "sche.id = ?").
                                setInteger(0,id).executeUpdate();
                    } catch (Exception ex) {
                        log.warn("Failed to remove timer event [" + id 
                            + "] because :" + ex.getMessage(), ex);
                    }
                }
                
                utw.commit();
                
            } catch (Exception ex) {
                log.warn("Failed to process timer event for [" + jndi 
                        + "] because :" + ex.getMessage(), ex);
            } finally {
                if (utw != null){
                    utw.release();
                }
            }
        }
        
        
        /**
         * The terminate method
         */
        public void terminate() {
            // ignore
        }
    }
    
    // class constants
    private final static String TIMER_USERNAME = "timer_user";
    
    // the class log variable
    protected Logger log =
            Logger.getLogger(TimerImpl.class.getName());
    
    private Context ctx = null;
    private ThreadStateMonitor state = null;
    private String username = null;
    
    /** Creates a new instance of TimerImpl */
    public TimerImpl() throws NamingException, TimerException {
        ctx = new InitialContext();
//        HibernateUtil.init();
        state = new ThreadStateMonitor(60000);
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(TimerImpl.class);
            username = config.getString(TIMER_USERNAME);
        } catch (Exception ex) {
            log.error("Failed to instanciate the timer : " + 
                    ex.getMessage(),ex);
            throw new TimerException("Failed to instanciate the timer : " + 
                    ex.getMessage());
        }
    }
    
    /**
     * This method will register an event on the database.
     * 
     * @param JNDI This is a string for the JNDI of the daemon that is to be 
     *          called by the event.
     * @param month The month of the event. -1 will occur monthly. (This
     *      parameter is zero indexed)
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
            Serializable event, boolean recure) throws RemoteException,
            TimerException {
        try {
            
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.timer.TimerImpl.class)
                        .getSession();
            
            byte[] b_event;
            byte recure2 = 0;
            if (recure == false) {
                recure2 = 0;
            } else {
                recure2 = 1;
            }
            b_event = ObjectSerializer.serialize(event);
            Schedule schedule = new Schedule(JNDI,month,day,hour,minute,b_event,
                    recure2);
            session.save(schedule);
            
            log.info("Registered a new event for : " + JNDI);
            
        } catch (Exception ex) {
            log.error("Failed to register timer events :"
                    + ex.getMessage(),ex);
            throw new TimerException("Failed to register timer events :" + 
                    ex.getMessage());
        }
    }
    
    /**
     * This method terminates the thread.
     */
    public void terminate() {
        state.terminate(true);
    }
    
    
    /**
     * This method is the thread. It loops once every minute checking the 
     * database for events assigned to that time. It then processes the event 
     * and deletes it from the database should recure be set to false.
     */
    public void process() {
        while (!state.isTerminated()) {
            // sleep so that other things can load
            state.monitor();
            if (state.isTerminated()) {
                // break out as this is terminated
                break;
            }
            
            UserTransactionWrapper utw = null;
            try {
                
                utw = new UserTransactionWrapper();
                utw.begin();
                
                Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.timer.TimerImpl.class)
                        .getSession();
                
                Calendar currentDate = Calendar.getInstance();
                
                List list = session.createSQLQuery("SELECT Schedule.id, "
                        + "Schedule.jndi, Schedule.event, Schedule.recure FROM " +
                        "Schedule WHERE " +
                        "(month=? OR month=-1)" +
                        " AND " +
                        "(day=? OR day=-1)" +
                        " AND " +
                        "(hour=? OR hour=-1)" +
                        " AND " +
                        "(minute=? OR minute=-1)")
                        .setInteger(0,currentDate.get(Calendar.MONTH))
                        .setInteger(1,currentDate.get(Calendar.DAY_OF_MONTH))
                        .setInteger(2,currentDate.get(Calendar.HOUR))
                        .setInteger(3,currentDate.get(Calendar.MINUTE))
                        .list();
                
                java.util.ArrayList copy = new java.util.ArrayList();
                copy.addAll(list);
                
                
                for (int index = 0; index < list.size() && !state.isTerminated(); 
                        index++){
                    Object[] row = (Object[])list.
                            get(index);
                    TimerThread timerThread = new TimerThread(this,(int)row[0],
                        (String)row[1], (byte[])row[2], (byte)row[3]);
                    timerThread.start(username);
                }
                utw.commit();
                
            } catch (Exception ex) {
                log.error("Failed to process timer events :" + ex.getMessage(),
                        ex);
                
            } finally {
                if (utw != null) {
                    try {
                        utw.release();
                    } catch (Exception ex) {
                        // ignore
                    }
                }
            }
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
    public TimerEvent[] listEvents() throws RemoteException, TimerException {
        String returnString = "";
        TimerEvent[] returnList = null;
        try {
            
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.timer.TimerImpl.class)
                        .getSession();
            
            List list = session.createSQLQuery("SELECT id,jndi,month,day," +
                    "hour,minute,event,recure FROM Schedule ORDER BY id").list();
            
            returnList = new TimerEvent[list.size()];
            for (int i = 0; i < list.size(); i ++) {
                Object[] row = (Object[]) list.get(i);
                TimerEvent tempEvent = new TimerEvent();
                tempEvent.setId(((Integer)row[0]).intValue());
                tempEvent.setJndi(row[1].toString());
                tempEvent.setMonth(((Integer)row[2]).intValue());
                tempEvent.setDay(((Integer)row[3]).intValue());
                tempEvent.setHour(((Integer)row[4]).intValue());
                tempEvent.setMinute(((Integer)row[5]).intValue());
                byte[] b_event = (byte[]) row[6];
                Serializable event = (Serializable) 
                        ObjectSerializer.deserialize(b_event);
                tempEvent.setEvent(event);
                if (((Byte)row[7]).byteValue() == 0) {
                    tempEvent.setRecure(false);
                } else {
                    tempEvent.setRecure(true);
                }
                returnList[i] = tempEvent;
            }
            
        } catch (Exception ex) {
            log.error("Failed to list timer events :" + ex.getMessage(),
                    ex);
            throw new TimerException("Failed to list timer events :" + 
                    ex.getMessage());
        }
        return returnList;
    }
    
    /**
     * This method deletes an event based on a supplied event ID which 
     * corresponds to the database ID.
     *
     * @param eventID The database ID of the event.
     */
    public void deleteEvent(int eventId) throws RemoteException,
            TimerException {
        try {
            log.info("Remove event Event id : " + eventId);
            
            Session session = HibernateUtil
                        .getInstance(com.rift.coad.daemon.timer.TimerImpl.class)
                        .getSession();
            
            session.createQuery(
                    "DELETE FROM Schedule as sche WHERE sche.id = ?")
                    .setInteger(0,eventId)
                    .executeUpdate();
            
        } catch (Exception ex) {
            log.error("Failed to delete timer event :" + ex.getMessage(),
                    ex);
            throw new TimerException("Failed to delete timer event :" +
                    ex.getMessage());
        }
    }
    
}

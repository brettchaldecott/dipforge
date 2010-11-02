/*
 * News Feed Server: This is the implementation of the news feed server.
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
 * FeedManagerImpl.java
 */

// package path
package com.rift.coad.daemon.event;

// java imports
import java.rmi.RemoteException;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;


// coadunation imports
import com.rift.coad.daemon.event.db.*;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.util.transaction.UserTransactionWrapper;
import java.util.Date;

/**
 * The implementation of the feed manager.
 * 
 * @author brett chaldecott
 */
public class FeedManagerImpl implements FeedManager, BeanRunnable {
    
    // class constants
    private final static String FEED_PURGE_INTERVAL = "feed_purge_interval";
    private final static String FEED_PURGE_DIFFERENCE = "feed_purge_difference";
    private final static long FEED_PURGE_INTERVAL_DEFAULT = 60 * 60 * 1000;
    private final static long FEED_PURGE_DIFFERENCE_DEFAULT = 6 * 60 * 60 * 1000;
    
    // private member variables
    private static Logger log = Logger.getLogger(FeedManagerImpl.class);
    private ThreadStateMonitor monitor = null;
    private long purgeDifference = 0;
    
    /**
     * The constructor of the feed manager.
     */
    public FeedManagerImpl() throws EventException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().getConfig(FeedManagerImpl.class);
            monitor = new ThreadStateMonitor(conf.getLong(FEED_PURGE_INTERVAL,
                    this.FEED_PURGE_INTERVAL_DEFAULT));
            purgeDifference = conf.getLong(FEED_PURGE_DIFFERENCE,
                    FEED_PURGE_DIFFERENCE_DEFAULT);
            HibernateUtil.getInstance(FeedManagerImpl.class);
        } catch (Exception ex) {
            log.error("Failed to instanciate the feed manager : " + ex.getMessage(),ex);
            throw new EventException(
                    "Failed to instanciate the feed manager : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to register an event with the feed manager.
     * @param event
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.event.EventException
     */
    public void registerEvent(FeedEvent event) throws RemoteException, EventException {
        try {
            Session session = HibernateUtil.getInstance(FeedManagerImpl.class).getSession();
            DBFeedEvent dbEvent = new DBFeedEvent(event.getName(), event.getExternalId(), 
                    event.getUsername(), event.getDescription(), event.getUrl(), event.getApplication(),event.getRole(),
                    new java.sql.Timestamp(new java.util.Date().getTime()));
            
            session.persist(dbEvent);
            DBFeedEventMeta applicationMeta = new DBFeedEventMeta("x-application", 
                    event.getApplication(), dbEvent);
            session.persist(applicationMeta);
        } catch (Exception ex) {
            log.error("Failed to register the event :" + ex.getMessage(),ex);
            throw new EventException("Failed to register the event :" + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is called to process the requests.
     */
    public void process() {
        log.info("The beginning of the processor");
        try {
            while (!monitor.isTerminated()) {
                UserTransactionWrapper transaction = null;
                try {
                    log.info("Purge old events");
                    transaction = new UserTransactionWrapper();
                    transaction.begin();
                    Session session = HibernateUtil.getInstance(FeedManagerImpl.class).getSession();
                    Date purgeDate = new Date(new Date().getTime() - this.purgeDifference);
                    session.createSQLQuery("DELETE FROM DBFeedEventMeta WHERE " +
                            "eventId IN (SELECT id FROM DBFeedEvent WHERE eventDate < ?)").setDate(0, 
                            purgeDate).executeUpdate();
                    session.createSQLQuery("DELETE FROM DBFeedEvent WHERE eventDate < ?").setDate(0, 
                            purgeDate).executeUpdate();
                    transaction.commit();
                } catch (Exception ex) {
                    log.error("Failed to purge the data : " + ex.getMessage(),ex);
                } finally {
                    if (transaction != null) {
                        transaction.release();
                    }
                }
                log.info("Wait for new events to become old for purging.");
                monitor.monitor();
            }
        } catch (Exception ex) {
            log.error("Failed to process : " + ex.getMessage(),ex);
        }
    }

    /**
     * This method is called to terminate the processing of the feed manager.
     */
    public void terminate() {
        monitor.terminate(true);
    }
    
    
    
}


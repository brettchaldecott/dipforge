/*
 * RSSReaderClient: The RSS Reader.
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
 * RSSClientManagement.java
 */

// package path
package com.rift.coad.daemon.rss;

// java imports
import com.rift.coad.lib.bean.BeanRunnable;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.net.URL;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.Query;
import org.hibernate.Session;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.ThreadStateMonitor;
import com.rift.coad.hibernate.util.HibernateUtil;
import com.rift.coad.util.transaction.UserTransactionWrapper;


// rss imports
import com.rift.coad.daemon.rss.db.*;
import com.sun.syndication.feed.module.DCModuleImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * This object is responsible for managing the rss client.
 * 
 * @author brett chaldecott
 */
public class RSSClientManagement implements RSSClientManagementMBean, BeanRunnable {
    // class constants
    private final static String FEED_CONFIG = "feed_config";
    private final static String RSS_INTERVAL = "rss_interval";
    private final static long RSS_INTERVAL_DEFAULT = 60 * 5 * 1000;
    
    
    // private member variables
    private static Logger log = Logger.getLogger(RSSClientManagement.class);
    private Configuration config = null;
    private ThreadStateMonitor monitor = null;
    
    /**
     * The implementation of the RSS Client.
     */
    public RSSClientManagement() throws RSSClientException {
        try {
            config =ConfigurationFactory.getInstance().getConfig(RSSClientManagement.class);
            HibernateUtil.getInstance(RSSClientManagement.class);
            // set the interval
            monitor = new ThreadStateMonitor(config.getLong(RSS_INTERVAL,RSS_INTERVAL_DEFAULT));
        } catch (Exception ex) {
            log.error("Failed to read the configuration : " + ex.getMessage(),ex);
            throw new RSSClientException(
                    "Failed to read the configuration : " + ex.getMessage(),ex);
        }
        
    }
    
    
    /**
     * This method returns the version information.
     * 
     * @return
     */
    public String getVersion() {
        return "1.0";
    }
    
    
    /**
     * This method returns the name of the RSS client.
     * 
     * @return The string containing the name of the RSS client.
     */
    public String getName() {
        return this.getClass().getName();
    }

    
    /**
     * The description of the RSS client. 
     * @return The RSS client.
     */
    public String getDescription() {
        return "The RSS client";
    }
    
    
    /**
     * This method returns a list of feeds.
     * @return The list feeds.
     * @throws com.rift.coad.daemon.rss.RSSClientException
     */
    public List<Feed> listFeeds() throws RSSClientException {
        try {
            Session session = HibernateUtil.getInstance(RSSClientManagement.class).getSession();
            List result = session.createQuery("FROM RSSDBFeed").list();
            List<Feed> feeds = new ArrayList<Feed>();
            for (Iterator iter = result.iterator(); iter.hasNext();) {
                RSSDBFeed feed = (RSSDBFeed)iter.next();
                feeds.add(new Feed(feed.getName(), feed.getApplication(), feed.getUrl()));
            }
            return feeds;
        } catch (Exception ex) {
            log.error("Failed to retrieve the list of feeds : " + ex.getMessage(),ex);
            throw new RSSClientException(
                    "Failed to retrieve the list of feeds : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method adds a new feed item.
     * @param name The name of the feed to add.
     * @param application The application that this feed is attached to.
     * @param url The url of the feed.
     * @throws com.rift.coad.daemon.rss.RSSClientException
     * @throws java.rmi.RemoteException
     */
    public void addFeed(String name, String application, String url) throws RSSClientException {
        try {
            Session session = HibernateUtil.getInstance(RSSClientManagement.class).getSession();
            session.persist(new RSSDBFeed(name,application,url));
        } catch (Exception ex) {
            log.error("Failed to add a feed entry : " + ex.getMessage(), ex);
            throw new RSSClientException(
                    "Failed to add a feed entry : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method removes the specified feed.
     * @param name The name of the feed to remove.
     * @throws com.rift.coad.daemon.rss.RSSClientException
     * @throws java.rmi.RemoteException
     */
    public void removeFeed(String name) throws RSSClientException, RemoteException {
        try {
            Session session = HibernateUtil.getInstance(RSSClientManagement.class).getSession();
            List results = session.createQuery(
                    "FROM RSSDBFeed AS feed WHERE feed.name = ?").setString(0, name).list();
            if (results.size() != 1) { 
                return;
            }
            session.createSQLQuery("DELETE FROM RSSDBFeedEntry WHERE " +
                    "rssDBFeedId IN (SELECT id FROM RSSDBFeed WHERE name = ?)").setString(0, name).executeUpdate();
            RSSDBFeed feed = (RSSDBFeed)results.get(0);
            session.delete(feed);
        } catch (Exception ex) {
            log.error("Failed to delete a feed entry : " + ex.getMessage(), ex);
            throw new RSSClientException(
                    "Failed to delete a feed entry : " + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method is called to execute the process loop.
     */
    public void process() {
        initDBFromConfig();
        com.rift.coad.lib.deployment.DeploymentMonitor.
                getInstance().waitUntilInitDeployComplete();
        try {
            Thread.sleep(1000 * 60);
        } catch (Exception ex) {
            
        }
        while(!monitor.isTerminated()) {
            pullFeeds();
            monitor.monitor();
        }
    }
    
    
    /**
     * This method terminates the processing of the RSS client.
     */
    public void terminate() {
        monitor.terminate(true);
    }
    
    
    /**
     * this method is called to initialized the transaction wrapper.
     */
    public void initDBFromConfig() {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session = HibernateUtil.getInstance(RSSClientManagement.class).getSession();
            List result = session.createSQLQuery("SELECT count(*) FROM RSSDBFeed").list();
            if (((Integer)result.get(0)).intValue() != 0) {
                return;
            }
            RSSXMLConfigHelper rssXMLConfig = new RSSXMLConfigHelper(config.getString(FEED_CONFIG));
            Map<String,Feed> feeds = rssXMLConfig.getFeedMap();
            for (Iterator iter = feeds.keySet().iterator(); iter.hasNext();) {
                Feed feed = feeds.get(iter.next());
                session.persist(new RSSDBFeed(feed.getName(),feed.getApplication(),feed.getUrl()));
            }
            transaction.commit();
        } catch (Exception ex) {
            log.error("Failed init the db config : " + ex.getMessage(),ex);
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
    
    /**
     * This method is called to pull the news feeds.
     */
    public void pullFeeds() {
        List<Feed> feeds = null;
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            feeds = this.listFeeds();
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of feeds : " + ex.getMessage(),ex);
            return;
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
        
        // loop through the feed entries
        for (Feed feed : feeds) {
            try{
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed syndFeed = input.build(new XmlReader(new URL(feed.getUrl())));
                processFeedEntries(feed, syndFeed.getEntries());
            } catch (Throwable ex){
                log.error("Failed to read the syndicated feed");
            }
        }
    }
    
    
    /**
     * process the entries
     */
    private void processFeedEntries(Feed feed, List entries) {
        UserTransactionWrapper transaction = null;
        try {
            transaction = new UserTransactionWrapper();
            transaction.begin();
            Session session = HibernateUtil.getInstance(RSSClientManagement.class).getSession();
            List dbFeeds = session.createQuery(
                    "FROM RSSDBFeed AS feed WHERE feed.name = ?").setString(0, feed.getName()).list();
            if (dbFeeds.size() != 1) {
                log.error("The feed [" + feed.getName() + "] does not exist");
                return;
            }
            RSSDBFeed dbFeed = (RSSDBFeed)dbFeeds.get(0);
            for (int index = entries.size() - 1; index >= 0; index--) {
                SyndEntryImpl entry = (SyndEntryImpl)entries.get(index);
                log.debug("Check feed title : " + entry.getTitle());
                List rssDBFeedEntries = session.createQuery(
                    "FROM RSSDBFeedEntry AS entry WHERE entry.subject = ? and entry.dbFeed.id = ?").
                    setString(0,entry.getTitle().trim()).setLong(1,dbFeed.getId()).list();
                if (rssDBFeedEntries.size() != 0) {
                    continue;
                }
                log.debug("Send title : " + entry.getTitle());
                LogEvent.getLog().logEvent(entry.getTitle(), entry.getTitle(), "desktop", 
                        feed.getApplication(), entry.getDescription().getValue(), entry.getUri());
                session.persist(new RSSDBFeedEntry(dbFeed, entry.getTitle().trim()));
            }
            
            List rssDBFeedEntries = session.createQuery(
                    "FROM RSSDBFeedEntry AS entry WHERE entry.dbFeed.id = ?").
                    setLong(0,dbFeed.getId()).list();
            for (Iterator iter = rssDBFeedEntries.iterator(); iter.hasNext();) {
                RSSDBFeedEntry rssDBEntry = (RSSDBFeedEntry)iter.next();
                boolean found = false;
                for (Iterator entriesIter = entries.iterator(); entriesIter.hasNext();) {
                    SyndEntryImpl entry = (SyndEntryImpl)entriesIter.next();
                    if (entry.getTitle().trim().equals(rssDBEntry.getSubject())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    log.info("Delete rss entry : " + rssDBEntry.getSubject());
                    session.delete(rssDBEntry);
                }
            }
            log.debug("Commit the changes");
            transaction.commit();
        } catch (Throwable ex) {
            log.error("Failed to retrieve the list of feeds : " + ex.getMessage(),ex);
            return;
        } finally {
            if (transaction != null) {
                transaction.release();
            }
        }
    }
    
}

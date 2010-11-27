/*
 * RSSReader: The RSS Reader.
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
 * RSSClientManagementImpl.java
 */


// package path
package com.rift.coad.daemon.rss.webservice;

// java imports
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * This object is responsible for implementing the Web service interface.
 * 
 * @author brett chaldecott
 */
public class RSSClientManagementImpl implements RSSClientManagement {
    // class constants
    private final static String RSS_MANAGER_JNDI =
            "java:comp/env/bean/rss/Management";    // private member variables
    
    // private member variables
    private Logger log = Logger.getLogger(RSSClientManagementImpl.class);
    
    /**
     * The default constructor.
     */
    public RSSClientManagementImpl() {
    }
    
    
    /**
     * This method returns the version information for the rss client
     * 
     * @return The version information for this interface.
     * @throws java.rmi.RemoteException
     */
    public String getVersion() {
        return "1.0";
    }
    
    /**
     * This method returns the name of this object.
     * 
     * @return This method returns the name of this object.
     */
    public String getName() throws RemoteException {
        return this.getClass().getName();
    }
    
    
    /**
     * This method returns the description of this object.
     * 
     * @return The string contaning he description of this object.
     */
    public String getDescription() {
        return "The RSS client web service.";
    }
    
    
    /**
     * This method lists the feeds
     * 
     * @return The list of feeds.
     * @throws com.rift.coad.daemon.rss.webservice.RSSClientException
     */
    public Feed[] listFeeds() throws RSSClientException {
        try {
            com.rift.coad.daemon.rss.RSSClientManagement manager =
                    (com.rift.coad.daemon.rss.RSSClientManagement) ConnectionManager.getInstance().getConnection(
                    com.rift.coad.daemon.rss.RSSClientManagement.class,RSS_MANAGER_JNDI);
            List<com.rift.coad.daemon.rss.Feed> managerFeeds = manager.listFeeds();
            List<Feed> feeds = new ArrayList<Feed>();
            for (com.rift.coad.daemon.rss.Feed managerFeed : managerFeeds) {
                Feed feed = new Feed();
                feed.name = managerFeed.getName();
                feed.application = managerFeed.getApplication();
                feed.url = managerFeed.getUrl();
                feeds.add(feed);
            }
            return feeds.toArray(new Feed[0]);
        } catch (Throwable ex) {
            log.error("Failed to retrieve a list of feeds :" + ex.getMessage(), ex);
            throw throwRSSClientException("Failed to retrieve a list of feeds :" + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * 
     * @param name
     * @param application
     * @param url
     * @throws com.rift.coad.daemon.rss.webservice.RSSClientException
     * @throws java.rmi.RemoteException
     */
    public void addFeed(String name, String application, String url) throws RSSClientException, RemoteException {
        try {
            com.rift.coad.daemon.rss.RSSClientManagement manager =
                    (com.rift.coad.daemon.rss.RSSClientManagement) ConnectionManager.getInstance().getConnection(
                    com.rift.coad.daemon.rss.RSSClientManagement.class,RSS_MANAGER_JNDI);
            manager.addFeed(name, application, url);
        } catch (Throwable ex) {
            log.error("Failed to add a feed :" + ex.getMessage(), ex);
            throw throwRSSClientException("Failed to add the feeds :" + ex.getMessage(), ex);
        }
    }

    public void removeFeed(String name) throws RSSClientException, RemoteException {
        try {
            com.rift.coad.daemon.rss.RSSClientManagement manager =
                    (com.rift.coad.daemon.rss.RSSClientManagement) ConnectionManager.getInstance().getConnection(
                    com.rift.coad.daemon.rss.RSSClientManagement.class,RSS_MANAGER_JNDI);
            manager.removeFeed(name);
        } catch (Throwable ex) {
            log.error("Failed to remove the feed :" + ex.getMessage(), ex);
            throw throwRSSClientException("Failed to remove the feed :" + ex.getMessage(), ex);
        }
    }
    
    
    /**
     * This method wrapps the throwing of the rss client exception.
     *
     * @param message The message to put in the exception
     * @param ex The exception stack.
     * @exception DNSException
     */
    private RSSClientException throwRSSClientException(String message, Throwable ex) {
        RSSClientException exception = new RSSClientException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        return exception;
    }
}

/*
 * News Feed Server: This is the implementation of the news feed server.
 * Copyright (C) 2008  2015 Burntjam
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
 * FeedRPCServiceImpl.java
 */

package com.rift.coad.desktop.server.desk.feed;
// java imports
import java.util.ArrayList;
import java.util.List;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// apache imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.daemon.event.FeedServer;
import com.rift.coad.desktop.client.desk.feed.EventException;
import com.rift.coad.desktop.client.desk.feed.FeedEvent;
import com.rift.coad.desktop.client.desk.feed.FeedRPCService;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This is the implementation of the RPC service.
 * 
 * @author brett chaldecott
 */
public class FeedRPCServiceImpl extends RemoteServiceServlet implements
    FeedRPCService {
    
    // logger imports
    private static Logger log = Logger.getLogger(FeedRPCServiceImpl.class);
    
    // private member variables
    private int width = 600;
    private int height = 400;
    
    /**
     * The default constructor of the RPC feed service.
     */
    public FeedRPCServiceImpl() throws EventException {
        try {
            Configuration conf = ConfigurationFactory.getInstance().
                    getConfig(FeedRPCServiceImpl.class);
            width = (int)conf.getLong("width",width);
            height = (int)conf.getLong("height",height);
        } catch (Exception ex) {
            log.error("Failed to retrieve the configuration information : " + 
                    ex.getMessage(),ex);
            throw new EventException("Failed to retrieve the configuration information : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for returning the list of tabs.
     * @param desktop The name of the desktop to retrieve the list of tabs for.
     * @return This method returns the list of tabs.
     */
    public String[] getTabs(String desktop) throws EventException {
        try {
            FeedServer server = 
                    (FeedServer)ConnectionManager.getInstance().getConnection(FeedServer.class, "event/Server");
            List<String> tabs = server.listFeedIdentifiers(desktop);
            return (String[])tabs.toArray(new String[0]);
        } catch (Throwable ex) {
            log.error("Failed to retrieve the tabs : " + ex.getMessage(),ex);
            throw new EventException(
                    "Failed to retrieve the tabs : " + ex.getMessage());
        }
    }
    
    /**
     * This method retrieves the evens.
     * 
     * @param tab The string containing the tab name.
     * @return The list of feed events for the given tab.
     * @throws com.rift.coad.desktop.client.desk.feed.EventException
     */
    public List<FeedEvent> getEvents(String tab) throws EventException {
        try {
            FeedServer server = 
                    (FeedServer)ConnectionManager.getInstance().getConnection(FeedServer.class, "event/Server");
            List<com.rift.coad.daemon.event.FeedEvent> events = server.getEvents(tab);
            List<FeedEvent> listFeeds = new ArrayList<FeedEvent>();
            for (com.rift.coad.daemon.event.FeedEvent event : events) {
                listFeeds.add(new FeedEvent(event.getName(), event.getExternalId(), event.getUsername(),
                        event.getApplication(), event.getDescription(), event.getUrl(),
                        width, height));
            }
            return listFeeds;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the tabs : " + ex.getMessage(),ex);
            throw new EventException(
                    "Failed to retrieve the tabs : " + ex.getMessage());
        }
    }
    
}

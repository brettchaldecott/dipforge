/*
 * EventServerClient: The event server client libraries.
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
 * EventException.java
 */

// package path
package com.rift.coad.office.server.events;

// log 4j
import com.rift.coad.daemon.event.FeedEvent;
import com.rift.coad.daemon.event.FeedManager;
import com.rift.coad.daemon.event.FeedManagerAsync;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.security.SessionManager;
import org.apache.log4j.Logger;

/**
 * This object is responsibe for logging events to the Coadunation Feed Server.
 *
 * @author brett chaldecott
 */
public class LogEvent {
    // class constants
    private final static String FEED_MANAGER_JNDI = "feed_manager_jndi";
    private final static String FEED_MANAGER_JNDI_DEFAULT = "event/Manager";
    private final static String APPLICATION = "File Manager";
    private final static String FILE_MANAGER_ROLE = "file_manager_role";
    private final static String FILE_MANAGER_ROLE_DEFAULT = "desktop";


    // private member variables
    private static LogEvent singleton = null;
    private static Logger log = Logger.getLogger(LogEvent.class);
    private String feedJNDI = null;
    private String role = null;
    /**
     * The default constructor for the log event.
     */
    private LogEvent() {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(LogEvent.class);
            feedJNDI = config.getString(FEED_MANAGER_JNDI,FEED_MANAGER_JNDI_DEFAULT);
            role = config.getString(FILE_MANAGER_ROLE,FILE_MANAGER_ROLE_DEFAULT);
        } catch (Exception ex) {
            log.error("Failed to retrieve the coadunation configuraton : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method return an instance of the log event object.
     *
     * @return This method returns the reference to the log object.
     */
    public static synchronized LogEvent getLog() {
        if (singleton == null) {
            singleton = new LogEvent();
        }
        return singleton;
    }

    /**
     * This method is responsible for
     * @param name The name of the event.
     * @param externalId The external id of the event.
     * @param username The username of the event.
     * @param application The application.
     * @param description The description of the event.
     * @param url The url to launch.
     */
    public void logEvent(String name, String externalId, String description, String url) {
        try {
            String username = "unknown";
            try {
                 username = SessionManager.getInstance().getSession().getUser().getName();
            } catch (Exception ex) {
                log.info("Failed to get the user information : " + ex.getMessage(),ex);
            }
            
            FeedManagerAsync feedManager = (FeedManagerAsync)RPCMessageClient.createOneWay(
                    "FileManager", FeedManager.class, FeedManagerAsync.class, feedJNDI);
            feedManager.registerEvent(new FeedEvent(name, externalId, username, APPLICATION, description, url,role));
        } catch (Throwable ex) {
            log.error("Failed to log an event : " + ex.getMessage(),ex);
        }
    }
}

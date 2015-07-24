/*
 * CoadunationDesktop: The implementation of the coadunation desktop.
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

// package path
package com.rift.coad.desktop.server.desk.event;

// java imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Date;


// log4j imports
import org.apache.log4j.Logger;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.daemon.desktop.MimeManager;
import com.rift.coad.daemon.event.FeedServer;
import com.rift.coad.desktop.client.desk.event.EventException;
import com.rift.coad.desktop.client.desk.event.EventInfo;
import com.rift.coad.desktop.client.desk.event.EventRPCService;
import com.rift.coad.desktop.client.desk.MimeType;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;

/**
 * This object is responsible for RPC services.
 * 
 * @author brett chaldecott
 */
public class EventRPCServiceImpl extends RemoteServiceServlet implements
    EventRPCService {
    
    // private member variables
    private static Logger log = Logger.getLogger(EventRPCServiceImpl.class);
    
    /**
     * The default constructor of the event rpc object.
     */
    public EventRPCServiceImpl() throws EventException {
        
    }
    
    
    /**
     * This method is used to retrieve the mime events for the applications.
     * 
     * @return The map containg the results.
     * @throws com.rift.coad.desktop.client.desk.event.EventException
     */
    public List<EventInfo> getMimeEvents() throws EventException {
        try {
            FeedServer server = 
                    (FeedServer)ConnectionManager.getInstance().getConnection(FeedServer.class, "event/Server");
            List<com.rift.coad.daemon.event.EventInfo> serverInfo = server.getMimeEvents();
            List<EventInfo> appInfo = new ArrayList<EventInfo>();
            for (Iterator<com.rift.coad.daemon.event.EventInfo> iter = serverInfo.iterator(); iter.hasNext();) {
                com.rift.coad.daemon.event.EventInfo eventInfo = iter.next();
                com.rift.coad.daemon.desktop.MimeType mimeType = eventInfo.getMime();
                appInfo.add(new EventInfo(new MimeType(mimeType.getName(), 
                        mimeType.getUrl(), mimeType.getIcon(), mimeType.getWidth(), mimeType.getHeight()),
                        eventInfo.getEventNum()));
            }
            return appInfo;
        } catch (Throwable ex) {
            log.error("Failed to retrieve the applications : " + ex.getMessage(),ex);
            throw new EventException(
                    "Failed to retrieve the applications : " + ex.getMessage());
        }
    }
    
}

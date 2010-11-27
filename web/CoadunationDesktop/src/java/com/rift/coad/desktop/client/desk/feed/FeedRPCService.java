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
 * FeedRPCService.java
 */


package com.rift.coad.desktop.client.desk.feed;
import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;

/**
 * This is the implementation of the rpc service responsible for retrieving feed
 * information.
 * 
 * @author brett chaldecott
 */
public interface FeedRPCService extends RemoteService{
    
    /**
     * This method returns the list of tabs for the given desktop.
     * @param desktop
     * @return The list of tabs.
     */
    public String[] getTabs(String desktop) throws EventException;
    
    
    /**
     * This method returns the list of feed events for a given tab.
     * 
     * @return The list of feed events for the given tab.
     * @param identifier  The identifying string for the feed events.
     * @exception RemoteException
     * @exception EventException
     */
    public List<FeedEvent> getEvents(String feedIdentifier) throws EventException;
    
}

/*
 * EventServerClient: The client interface to the desktop server.
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
 * DesktopManager.java
 */

package com.rift.coad.daemon.event;

// the java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

// coadunation imports
import com.rift.coad.daemon.desktop.MimeType;


/**
 * This interface defines the methods that manage the feed events.
 * 
 * @author brett chalecott
 */
public interface FeedServer extends Remote {
    /**
     * This method lists of feed identifiers.
     * 
     * @return The list of feed identifiers
     * @param desktop The name of the desktop to retrieve events for.
     * @exception RemoteException
     * @exception EventException
     */
    public List<String> listFeedIdentifiers(String desktop) throws RemoteException, EventException;
    
    /**
     * This method returns the list of feed events for a given tab.
     * 
     * @return The list of feed events for the given tab.
     * @param identifier  The identifying string for the feed events.
     * @exception RemoteException
     * @exception EventException
     */
    public List<FeedEvent> getEvents(String feedIdentifier) throws RemoteException, EventException;
    
    
    /**
     * This method returns the mime based events.
     * 
     * @return The list of feed events.
     * @exception RemoteException
     * @exception EventException
     */
    public List<EventInfo> getMimeEvents() throws RemoteException, EventException;
    
}

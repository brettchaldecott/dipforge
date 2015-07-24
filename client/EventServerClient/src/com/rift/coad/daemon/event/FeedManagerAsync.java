/*
 * EventServerClient: The event server client libraries.
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
 * FeedManagerAsync.java
 */

// package path
package com.rift.coad.daemon.event;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This object is responible for agregatting and managing the feeds from
 * various sources. All events are pushed to this manager and not pulled.
 * 
 * @author brett chaldecott
 */
public interface FeedManagerAsync  extends Remote {
    
    /**
     * This method registers an event.
     * @return The string containing the event id.
     * @param event
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.event.EventException
     */
    public String registerEvent(FeedEvent event) throws RemoteException;
}

/*
 * EventServer: The event server libraries.
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
 * FeedManager.java
 */


package com.rift.coad.daemon.event.webservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This object is responible for agregatting and managing the feeds from
 * various sources. All events are pushed to this manager and not pulled.
 * 
 * @author brett chaldecott
 */
public interface FeedManager  extends Remote {
    
    /**
     * This method registers an event.
     * @param event
     * @throws java.rmi.RemoteException
     * @throws com.rift.coad.daemon.event.EventException
     */
    public void registerEvent(FeedEvent event) throws RemoteException, EventException;
}

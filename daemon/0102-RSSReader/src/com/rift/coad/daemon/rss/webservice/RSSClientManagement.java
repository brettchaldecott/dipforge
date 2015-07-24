/*
 * RSSReader: The RSS Reader.
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
 * RSSClientManagement.java
 */

// package path
package com.rift.coad.daemon.rss.webservice;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation annotation imports


/**
 * This management bean is responsible for managing the RSSClient.
 * 
 * @author brett chaldecott
 */
public interface RSSClientManagement extends Remote {
    /**
     * This method return the version of this object.
     * 
     * @return This method returns the version information about the RSS client.
     * @throws RemoteException
     */
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name of this client.
     * 
     * @return The string containing the name of this RSS client management.
     * @throws RemoteException
     */
    public String getName() throws RemoteException;
    
    
    /**
     * This method returns the description of this client.
     * 
     * @return The string containing the description of this RSS client management.
     * @throws RemoteException
     */
    public String getDescription() throws RemoteException;
    
    
    /**
     * This method returns te list of feeds.
     * @return This method returns a list of feeds.
     * @throws RSSClientException
     * @throws java.rmi.RemoteException
     */
    public Feed[] listFeeds() throws RSSClientException, RemoteException;
    
    
    /**
     * This method adds a feed event
     * 
     * @param feed The feed to add.
     * @throws RSSClientException
     * @throws RemoteException
     */
    public void addFeed(String name,String application, String url) 
            throws RSSClientException, RemoteException;
    
    
    /**
     * This method removes a feed entry.
     * 
     * @param name The name of the feed to remove.
     * @throws com.rift.coad.daemon.rss.RSSClientException
     * @throws java.rmi.RemoteException
     */
    public void removeFeed(String name)
            throws RSSClientException, RemoteException;
    
}

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
 * RSSClientManagementMBean.java
 */

// package path
package com.rift.coad.daemon.rss;

// java imports
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

// coadunation annotation imports
import com.rift.coad.annotation.MethodInfo;
import com.rift.coad.annotation.ExceptionInfo;
import com.rift.coad.annotation.ParamInfo;
import com.rift.coad.annotation.Result;
import com.rift.coad.annotation.Version;

/**
 * This management bean is responsible for managing the RSSClient.
 * 
 * @author brett chaldecott
 */
public interface RSSClientManagementMBean extends Remote {
    /**
     * This method return the version of this object.
     * 
     * @return This method returns the version information about the RSS client.
     * @throws RemoteException
     */
    @MethodInfo(description="This method return the version information")
    @Version(number="1.0")
    @Result(description="The string containing the version of this implementation")
    public String getVersion() throws RemoteException;
    
    
    /**
     * This method returns the name of this client.
     * 
     * @return The string containing the name of this RSS client management.
     * @throws RemoteException
     */
    @MethodInfo(description="This method returns the name of this interface")
    @Version(number="1.0")
    @Result(description="The string containing the name of this management bean.")
    public String getName() throws RemoteException;
    
    
    /**
     * This method returns the description of this client.
     * 
     * @return The string containing the description of this RSS client management.
     * @throws RemoteException
     */
    @MethodInfo(description="This method the description of this object.")
    @Version(number="1.0")
    @Result(description="The string containing the description of this management bean.")
    public String getDescription() throws RemoteException;
    
    
    /**
     * This method returns te list of feeds.
     * @return This method returns a list of feeds.
     * @throws RSSClientException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method lists the feeds being processed.")
    @Version(number="1.0")
    @Result(description="The list of feeds being processed.")
    public List<Feed> listFeeds() throws RSSClientException, RemoteException;
    
    
    /**
     * This method adds a feed event
     * 
     * @param feed The feed to add.
     * @throws RSSClientException
     * @throws RemoteException
     */
    @MethodInfo(description="This method adds a feed.")
    @Version(number="1.0")
    public void addFeed(@ParamInfo(name="name",
            description="The name of the feed")String name,
            @ParamInfo(name="application",
            description="The name of application associated with this feed")String application, 
            @ParamInfo(name="url",
            description="The string containing the url")String url) 
            throws RSSClientException, RemoteException;
    
    
    /**
     * This method removes a feed entry.
     * 
     * @param name The name of the feed to remove.
     * @throws com.rift.coad.daemon.rss.RSSClientException
     * @throws java.rmi.RemoteException
     */
    @MethodInfo(description="This method removes the named feed")
    @Version(number="1.0")
    public void removeFeed(@ParamInfo(name="name",
            description="The name of the feed to remove")String name)
            throws RSSClientException, RemoteException;
    
}

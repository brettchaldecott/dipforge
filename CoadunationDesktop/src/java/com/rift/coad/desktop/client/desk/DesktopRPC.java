/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
 * Copyright (C) 2007  Rift IT Contracting
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
 * DesktopRPC.java
 */

// package path
package com.rift.coad.desktop.client.desk;

// java imports
import java.util.List;

// gwt imports
import com.google.gwt.user.client.rpc.RemoteService;


/**
 * This object deals with RPC based requests.
 * 
 * @author brett chaldecott
 */
public interface DesktopRPC extends RemoteService{
    
    /**
     * This method returns the list of desktops.
     * 
     * @return The list of desktops.
     */
    public List<DesktopInfo> listDesktops() throws DesktopRPCException;
    
    
    /**
     * This method lists the desktop applications.
     * 
     * @return The list of mime types.
     * @parm desktop The name of the desktop.
     * @exception DesktopRPCException
     */
    public List<MimeType> listDesktopApplications(String desktop) throws DesktopRPCException;
    
    
    /**
     *  This method returns a list of all the mime types.
     * @return The list of mime types
     * @exception DesktopRPCException
     */
    public List<MimeType> listMimeTypes() throws DesktopRPCException;
}

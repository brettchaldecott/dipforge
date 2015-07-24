/*
 * DesktopServerClient: The client interface to the desktop server.
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
 * UserInfo.java
 */

// package path
package com.rift.coad.desktop.client;

// gwt imports
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * This interface represents the user information object.
 * 
 * @author brett chaldecott
 */
public interface UserInfo extends RemoteService{
    
    /**
     * This method is responsible for retrieving the user name of the current frontend user.
     * 
     * @return The name of the current frontend user.
     */
    public String getUsername();
}

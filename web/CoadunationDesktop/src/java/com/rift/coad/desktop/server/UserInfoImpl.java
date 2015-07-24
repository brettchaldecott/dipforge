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

package com.rift.coad.desktop.server;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.rift.coad.desktop.client.UserInfo;

/**
 *
 * @author brett chaldecott
 */
public class UserInfoImpl extends RemoteServiceServlet implements
    UserInfo {
    
    
    /**
     * This method returns the name of the user making the request.
     * 
     * @return The string containing the name of the current user.
     */
    public String getUsername() {
        return this.getThreadLocalRequest().getRemoteUser();
    }
}

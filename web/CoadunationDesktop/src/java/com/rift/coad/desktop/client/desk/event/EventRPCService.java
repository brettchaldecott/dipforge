/*
 * CoadunationDesktop: The desktop interface to the Coadunation Server.
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
 * EventPanel.java
 */

// the package path
package com.rift.coad.desktop.client.desk.event;

// java imports
import com.rift.coad.desktop.client.desk.MimeType;
import com.google.gwt.user.client.rpc.RemoteService;
import java.util.List;

/**
 * The interface that defines the event rpc methods
 * 
 * @author brett chaldecott
 */
public interface EventRPCService extends RemoteService{
    /**
     *  This method returns a list of all the mime events.
     * @return The list of mime events.
     * @exception EventException
     */
    public List<EventInfo> getMimeEvents() throws EventException;
    
    
}

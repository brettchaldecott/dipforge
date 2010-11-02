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
 * DesktopRPCAsync.java
 */

package com.rift.coad.desktop.client.desk;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This interface is the asynchronious counter part to the DesktopRPC object.
 * 
 * @author brett chaldecott
 */
public interface DesktopRPCAsync {
    /**
     * This method is called to process the asynchronis list desktops.
     * 
     * @param asyncCallback The async call back handler.
     */
    public abstract void listDesktops(AsyncCallback asyncCallback);
    
    
    /**
     * This method returns a list of the desktop applications.
     * 
     * @param desktop The name of the desktop.
     * @param asyncCallback
     */
    public abstract void listDesktopApplications(String desktop, AsyncCallback asyncCallback);
    
    
    /**
     * The asynchronious version of the list mime types method.
     * @param asyncCallback The call back object.
     */
    public abstract void listMimeTypes(AsyncCallback asyncCallback);
}

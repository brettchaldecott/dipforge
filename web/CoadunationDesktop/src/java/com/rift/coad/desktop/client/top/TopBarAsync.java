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
 * TopBarAsync.java
 */

// package path
package com.rift.coad.desktop.client.top;

// gwt imports
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This interface defines the async counter parts to the top bar method.
 * 
 * @author brett chaldecott
 */
public interface TopBarAsync {
    
    /**
     * This method will be called to retrieve the menu information.
     * 
     * @param asyncCallback
     */
    public void getMenus(AsyncCallback asyncCallback);
    
    
    /**
     * This method is the asynchronis version of the get gadgets method.
     * 
     * @param asyncCallback The async call method.
     */
    public void getGadgets(AsyncCallback asyncCallback);
}

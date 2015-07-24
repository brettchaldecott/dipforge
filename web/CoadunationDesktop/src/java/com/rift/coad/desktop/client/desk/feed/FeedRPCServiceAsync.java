/*
 * News Feed Server: This is the implementation of the news feed server.
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
 * FeedRPCServiceAsync.java
 */

package com.rift.coad.desktop.client.desk.feed;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This interface is the asynchronious version.
 * 
 * @author brett chaldecott
 */
public interface FeedRPCServiceAsync {
    
    /**
     * The asynchronous version of this function.
     * @param desktop The name of the desktop to retrieve the list of tabs for.
     * @param asyncCallback The acync reference for call back.
     */
    public abstract void getTabs(java.lang.String desktop, AsyncCallback asyncCallback);
    
    
    /**
     * The asynchronous version of this function.
     * 
     * @param feedIdentifier
     * @param asyncCallback
     */
    public abstract void getEvents(java.lang.String feedIdentifier, AsyncCallback asyncCallback);
}

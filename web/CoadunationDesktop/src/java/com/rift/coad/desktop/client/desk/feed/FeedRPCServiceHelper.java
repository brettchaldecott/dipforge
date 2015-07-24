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
 * FeedRPCServiceHelper.java
 */

package com.rift.coad.desktop.client.desk.feed;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * Example class using the FeedRPCService service. 
 *
 * @author brett
 */
public class FeedRPCServiceHelper {
    
    /**
     * The constructor of the feed RPC service helper.
     */
    public FeedRPCServiceHelper() {
        
    }
    
    /**
     * This method returns a reference to the feed object.
     *  
     * @return This method returns the reference to the feed.
     */
    public static FeedRPCServiceAsync getService(){
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        FeedRPCServiceAsync service = (FeedRPCServiceAsync) GWT.create(FeedRPCService.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "/desk/feed/feedrpcservice";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}

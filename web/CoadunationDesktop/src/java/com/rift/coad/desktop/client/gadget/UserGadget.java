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
 * UserGadget.java
 */

// package path
package com.rift.coad.desktop.client.gadget;

// gwt imports
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.rift.coad.desktop.client.UserInfo;
import com.rift.coad.desktop.client.UserInfoAsync;



/**
 * This gadget displays the user name.
 * 
 * @author brett chaldecott
 */
public class UserGadget extends Composite {
    // private member variables
    private Label username = new Label();
    
    /**
     * The default constructor of the user gadget.
     */
    public UserGadget() {
        username.setWidth("100%");
        initWidget(username);
        this.setWidth("100%");
        
        // Create an asynchronous callback to handle the result.
        final AsyncCallback callback = new AsyncCallback() {
            public void onSuccess(Object result) {
                username.setText((String)result);
            }
            
            public void onFailure(Throwable caught) {
                username.setText("Communication failed");
            }
        };
        
        // retrieve the username
        getService().getUsername(callback);
        
    }
    
    
    public static UserInfoAsync getService(){
        // Create the client proxy. Note that although you are creating the
        // service interface proper, you cast the result to the asynchronous
        // version of
        // the interface. The cast is always safe because the generated proxy
        // implements the asynchronous interface automatically.
        UserInfoAsync service = (UserInfoAsync) GWT.create(UserInfo.class);
        // Specify the URL at which our service implementation is running.
        // Note that the target URL must reside on the same domain and port from
        // which the host page was served.
        //
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "userinfo";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        return service;
    }
}

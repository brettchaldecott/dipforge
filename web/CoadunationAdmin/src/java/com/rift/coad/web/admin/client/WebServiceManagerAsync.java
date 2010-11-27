/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * WebServiceManagerAsync.java
 */

// package defintion
package com.rift.coad.web.admin.client;

// asynchronous call back
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This method defines the async manager methods.
 *
 * @author brett chaldecott
 */
public interface WebServiceManagerAsync {
    /**
     * This method returns the list of web services.
     *
     * @param async The reference to the async call back handler
     */
    public void getServices(AsyncCallback async);
    
    
    /**
     * This method returns a web service definition.
     *
     * @param name The name of the web service.
     * @param async The reference to the async call back handler
     */
    public void getWebServiceDef(String name, AsyncCallback async);
}

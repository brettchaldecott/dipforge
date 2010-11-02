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
 * WebServiceManager.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * This interface provides access to the web service list.
 *
 * @author brett chaldecott
 */
public interface WebServiceManager extends RemoteService{
    
    /**
     * This method returns the list of web services.
     *
     * @return The list of web services.
     * @exception WebServiceException
     */
    public String[] getServices() throws WebServiceException;
    
    
    /**
     * This method returns a web service definition.
     *
     * @return The web service defintion.
     * @param name The name of the web service.
     * @exception WebServiceException
     */
    public WebServiceDef getWebServiceDef(String name) throws 
            WebServiceException;
}

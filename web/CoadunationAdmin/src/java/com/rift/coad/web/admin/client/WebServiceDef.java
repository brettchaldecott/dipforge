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
 * WebServiceDef.java
 */

// package defintion
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * The class that contains the web service definition information.
 *
 * @author brett chaldecott
 */
public class WebServiceDef implements IsSerializable {
    
    // private member variables
    private String url = null;
    private String wsdl = null;
    
    /**
     * Creates a new instance of WebServiceDef
     */
    public WebServiceDef() {
    }
    
    
    /**
     * This constructor is responsible for setting up the url and wsdl.
     *
     * @param url The url of the web service.
     * @param wsdl The wsdl content.
     */
    public WebServiceDef(String url, String wsdl) {
        this.url = url;
        this.wsdl = wsdl;
    }
    
    
    /**
     * This method returns the url of the web service.
     *
     * @return The url of the web service.
     */
    public String getURL() {
        return url;
    }
    
    
    /**
     * This method returns the url of the web service.
     *
     * @param url The url of the web service.
     */
    public void setURL(String url) {
        this.url = url;
    }
    
    
    /**
     * This method returns the wsdl of the web service.
     *
     * @return The wsdl of the web service.
     */
    public String getWSDL() {
        return wsdl;
    }
    
    
    /**
     * The setter for the wsdl value.
     *
     * @param wsdl The wsdl value.
     */
    public void setWSDL(String wsdl) {
        this.wsdl = wsdl;
    }
}

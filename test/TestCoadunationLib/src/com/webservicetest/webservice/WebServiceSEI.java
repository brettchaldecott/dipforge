/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * WebServiceSEI.java
 *
 * The service end point interface for the web service test.
 */

package com.webservicetest.webservice;

/**
 * The service end point interface for the web service
 *
 * @author Brett Chaldecott
 */
public interface WebServiceSEI extends java.rmi.Remote {
    /**
     * This method will be called to test the web service end point interface.
     *
     * @param msg The string containing the message for the server.
     * @return The containing the message from the server.
     */
    public String helloWorld(String msg);
}

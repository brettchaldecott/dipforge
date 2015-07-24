/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  2015 Burntjam
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
 * MXManager.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.RemoteService;

/**
 * 
 *
 * @author brett chaldecott
 */
public interface MXManager extends RemoteService{
    /**
     * This method returns the list of mx beans.
     *
     * @return A list of daemons. 
     * @exception DaemonException
     */
    public String[] getMXBeans() throws MXException;
    
    
    /**
     * This method returns the list of methods.
     *
     * @return A list of methods. 
     * @param name The name of the object.
     * @exception DaemonException
     */
    public String[] getMethods(String name) throws MXException;
    
    
    /**
     * This method returns definition of the method.
     *
     * @return A list of methods. 
     * @param objectName The name of the object.
     * @param methodName The name of the method.
     * @exception DaemonException
     */
    public MethodDef getMethod(String objectName, String methodName) 
    throws MXException;
    
    
    /**
     * This method is called to invoke the daemon.
     *
     * @return The result of the method call.
     * @param objectName The name of the object.
     * @param method The method to call.
     * @exception DaemonException
     */
    public String invokeMethod(String objectName, MethodDef method) 
    throws MXException;

}

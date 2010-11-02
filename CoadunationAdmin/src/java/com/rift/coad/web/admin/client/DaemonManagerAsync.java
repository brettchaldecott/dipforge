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
 * DaemonManagerAsync.java
 */

// the packages
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * The asynchronious interfaced for the daemon manager.
 *
 * @author brett chaldecott
 */
public interface DaemonManagerAsync {
    
    
    /**
     * This method returns the list of daemons.
     *
     * @param callback The call back handler.
     */
    public void getDaemons(AsyncCallback callback);
    
    
    /**
     * This method returns the list of methods.
     *
     * @param name The name of the object that the methods are related to.
     * @param callback The call back handler.
     */
    public void getMethods(String name,AsyncCallback callback);
    
    
    /**
     * This method returns the method defintion for the requested method.
     *
     * @param objectName The name of the object to return.
     * @param methodName The name of the method to return.
     * @param callback The call back handler.
     */
    public void getMethod(String objectName, String methodName,
            AsyncCallback callback);
    
    
    /**
     * This method is called to invoke the daemon.
     *
     * @param objectName The name of the object to return.
     * @param method The method to call.
     * @param callBack The result of the call.
     */
    public void invokeMethod(String objectName, MethodDef method, 
            AsyncCallback callback);
}

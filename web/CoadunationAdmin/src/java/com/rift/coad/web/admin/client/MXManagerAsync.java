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
 * MXManagerAsync.java
 */

// package path
package com.rift.coad.web.admin.client;

// imports
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * This is the asynchronious interface for the mx manager.
 *
 * @author brett chaldecott
 */
public interface MXManagerAsync {
    /**
     * This method returns the list of mx beans.
     *
     * @param asynCallBack The async call back
     */
    public void getMXBeans(AsyncCallback asyncCallBack);
    
    
    /**
     * This method returns the list of methods.
     *
     * @param name The name of the object.
     * @param asynCallBack The async call back
     */
    public void getMethods(String name,AsyncCallback asyncCallBack);
    
    
    /**
     * This method returns definition of the method.
     *
     * @param objectName The name of the object.
     * @param methodName The name of the method.
     * @param asynCallBack The async call back
     */
    public void getMethod(String objectName, String methodName,
            AsyncCallback asyncCallBack);
    
    
    /**
     * This method is called to invoke the daemon.
     *
     * @param objectName The name of the object.
     * @param method The method to call.
     * @param asynCallBack The async call back
     */
    public void invokeMethod(String objectName, MethodDef method,
            AsyncCallback asyncCallBack);
}

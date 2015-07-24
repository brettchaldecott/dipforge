/*
 * CoadunationInclude: A test library.
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
 * Bean2Impl.java
 *
 * The implementation of a test bean.
 */

package com.extra.bean;

// coaduntion imports
import com.rift.coad.lib.deployment.test.TestMonitor;
import com.rift.coad.lib.bean.BeanRunnable;

// test imports
import com.extra.TestUtil;

/**
 * The implementation of a test bean.
 *
 * @author Brett Chaldecott
 */
public class Bean2Impl implements Bean2Interface {
    
    /**
     * 
     * Creates a new instance of Bean2Impl
     */
    public Bean2Impl() {
    }
    
    /**
     * This method will return the hello world message
     *
     * @return The string containing the hello world msg.
     * @param msg The message to print on the server
     */
    public String helloWorld(String msg) {
        System.out.println("#########################################");
        System.out.println("Message from bean 2:" + msg);
        System.out.println("#########################################");
        return "The included bean uncle \n";
    }
    
    
    /**
     * This method returns a top interface.
     *
     * @return A reference to the top interface.
     * @param name The name of the insterface to retrieve.
     * @exception java.rmi.RemoteException
     */
    public TopInterface getTopInter(String name) {
        System.out.println("#########################################");
        System.out.println("Retrieve a top interface:" + name);
        System.out.println("#########################################");
        return new BottomInterfaceImpl(name,"This is a test");
    }
    
    /**
     * This method takes the value given to it.
     *
     * @param value The value to take.
     * @exception RemoteException
     */
    public void takeValue(ValueObject2 value) {
        System.out.println("#########################################");
        System.out.println("The value passed in is :" + value.getValue());
        System.out.println("#########################################");
    }
}

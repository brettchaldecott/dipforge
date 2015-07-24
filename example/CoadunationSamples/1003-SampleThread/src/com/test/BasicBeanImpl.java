/*
 * SampleThread: The coaduntion sample thread.
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
 * BasicBeanImpl.java
 *
 * This is an example of a simple thread.
 */

package com.test;

import java.rmi.RemoteException;
import com.rift.coad.lib.bean.BeanRunnable;

/**
 * Not this is a very simple example
 *
 * @author Glynn Chaldecott
 */
public class BasicBeanImpl implements BasicBeanInterface, BeanRunnable {
    boolean flag = false;
    
    /** Creates a new instance of BeanImpl */
    public BasicBeanImpl() {
    }

    public void testMethod() throws RemoteException {
        System.out.println("The test method has been called");
        
    }

    /**
     * The method that performs the processing.
     */
    public void process() {
        while (!getFlag()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("The thread is running.");
        }
    }

    /**
     * the terminate method
     */
    public synchronized void terminate() {
        flag = true;
    }
    
    /**
     * This method returns the sychronized value
     */
    private synchronized boolean getFlag() {
        return flag;
    }
}

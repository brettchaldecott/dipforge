/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * BeanImpl.java
 *
 * A test bean implementation.
 */

package com.test3;

// java imports
import java.rmi.RemoteException;

// coaduntion imports
import com.rift.coad.lib.deployment.test.TestMonitor;
import com.rift.coad.lib.bean.BeanRunnable;
import com.rift.coad.lib.bean.test.CalledObjects;

// test imports
import com.extra.TestUtil;

/**
 * The bean implementation.
 *
 * @author Brett Chaldecott
 */
public class BeanImpl implements BeanInterface, BeanRunnable, TestNoneRemoteInter {
    
    /**
     * The class responsible for controling the terminate flag.
     */
    public class RunState {
        // terminated flag
        private boolean terminated = false;
        
        /**
         * This method will check the terminated flag.
         */
        public RunState() {
        }
        
        
        /**
         * This method will return true if this object has been terminated.
         *
         * @return TRUE if terminated, FALSE if not.
         */
        public synchronized boolean isTerminated() {
            try {
                if (terminated) {
                    return terminated;
                }
                wait(500);
                return terminated;
            } catch(Exception ex) {
                // ignore any exeption
                return terminated;
            }
        }
        
        
        /**
         * This method will set the state of the terminated flag to true.
         */
        public synchronized void terminate() {
            terminated = true;
            notify();
        }
    }
    
    // class member variables
    private boolean notified = false;
    private RunState state = new RunState();
    private TestUtil testUtil = new TestUtil();
    
    /** 
     * Creates a new instance of BeanImpl 
     */
    public BeanImpl() {
    }
    
    /**
     * This method will return the hello world message
     *
     * @return The string containing the hello world msg.
     * @param msg The message to print on the server
     */
    public String helloWorld(String msg) throws TestException {
        System.out.println("Message from client:" + msg);
        testUtil.printMessage("Message from : " +  this.getClass().getName());
        return "Bob is your uncle";
    }
    
    
    /**
     * This method will return the hello world message
     *
     * @return The string containing the hello world msg.
     * @param msg The message to print on the server
     */
    public void exceptionMethod(String msg) throws TestException {
        throw new TestException(msg);
    }
    
    
    /**
     * Retrieve the value
     */
    public ValueObject getValue() {
        ValueObject vo = new ValueObject();
        vo.setValue("test");
        return vo;
    }
    
    /**
     * Set the value
     */
    public void setValue(ValueObject value) {
        System.out.println("The value passed in is : " + value.getValue());
    }
    
    /**
     * A void method.
     */
    public void voidMethod() {
        
    }
    
    
    /**
     * This method returns a factory object
     */
    public FactoryObjectImpl connectToObject() {
        return new FactoryObjectImpl("test");
    }
    
    
    /**
     * This method adds a test object.
     */
    public FactoryInterface addObject(String key) {
        return new FactoryObjectImpl(key);
    }
    
    
    /**
     * This method adds a test object.
     */
    public FactoryParentInterface getObject(String key) {
        CalledObjects.called++;
        return new FactoryObjectImpl(key);
    }
    
    
    /**
     * This method adds a test object.
     */
    public void removeObject(String key) {
        
    }
    
    
    
    /**
     * This method will be called to perform the processing. This method
     * replaces the traditional run method.
     */
    public void process() {
        int count = 0;
        do {
            if (notified == false) {
                try {
                    count++;
                    testUtil.printMessage("Message number [" + count 
                            + "] from : " +  this.getClass().getName());
                    TestMonitor.getInstance().alert(this.getClass().getName());
                } catch (Exception ex) {
                    // ignore
                }
                notified = true;
            }
        } while (state.isTerminated() == false);
    }
    
    
    /**
     * This method is called to soft terminate the processing thread.
     */
    public void terminate() {
        state.terminate();
    }
}

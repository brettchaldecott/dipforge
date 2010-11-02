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
 * MainInterface1Impl.java
 *
 * The main interface implementation for a test object.
 */

package com.rift.coad.lib.bean.test;

/**
 * The main interface implementation for a test object.
 *
 * @author Brett Chaldecott
 */
public class MainInterface1Impl implements MainInterface1 {
    
    // static variables
    public static int calledBean = 0;
    
    /** Creates a new instance of MainInterface1Impl */
    public MainInterface1Impl() {
    }
    
    
    /**
     * The void method
     */
    public void callVoid() {
        System.out.println("Calling void");
    }
    
    
    /**
     * This method throws and exceptions
     */
    public void throwException() throws TestProxyException {
        throw new TestProxyException("Failed to get result.");
    }
    
    /**
     * Return an int value
     */
    public String getString() {
        return "Test1";
    }
    
    
    /**
     * Return an int value
     */
    public int getInt(){
        return 1;
    }
    
    
    /**
     * This method returns a key value.
     */
    public TestKey getAKeyValue(){
        return new TestKey("1","key1");
    }
    
    
    /**
     * This method addes a sub interface
     */
    public SubInterface1 addSubInterface(String key){
        return new SubInterface1Impl(key);
    }
    
    
    /**
     * This method retrieves a sub interface
     */
    public SubInterface1 getSubInterface(String key) throws Exception {
        calledBean++;
        return new SubInterface1Impl(key);
    }
    
    /**
     * This method removes a sub interface
     */
    public void removeSubInterface(String key){
        
    }
}

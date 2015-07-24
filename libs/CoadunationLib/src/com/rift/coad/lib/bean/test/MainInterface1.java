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
 * MainInterface1.java
 *
 * This is a testing interface.
 */

package com.rift.coad.lib.bean.test;

/**
 * This is a testing interface.
 *
 * @author Brett Chaldecott
 */
public interface MainInterface1 {
    /**
     * The void method
     */
    public void callVoid();
    
    /**
     * This method throws and exceptions
     */
    public void throwException() throws TestProxyException;
    
    
    /**
     * Return an int value
     */
    public String getString();
    
    
    /**
     * Return an int value
     */
    public int getInt();
    
    
    /**
     * This method returns a key value.
     */
    public TestKey getAKeyValue();
    
    
    /**
     * This method addes a sub interface
     */
    public SubInterface1 addSubInterface(String key);
    
    /**
     * This method retrieves a sub interface
     */
    public SubInterface1 getSubInterface(String key) throws Exception ;
    
    /**
     * This method removes a sub interface
     */
    public void removeSubInterface(String key);
}

/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2012  Rift IT Contracting
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
 * LeviathanLog.java
 */

// package path
package com.rift.coad.change.request.action.leviathan;

// java import
import java.io.Serializable;

/**
 * This object wraps the standard java print functionality with a serializable
 * object.
 * 
 * @author brett chaldecott
 */
public class LeviathanPrint implements Serializable {

    /**
     * The default constructor
     */
    public LeviathanPrint() {
    }
    
    
    /**
     * This method wrapps the out print statement.
     * 
     * @param value The value to print
     */
    public void print(Object value) {
        System.out.print(value);
    }
    
    /**
     * This wraps the print call.
     * 
     * @param value The print call.
     */
    public void print(String value) {
        System.out.print(value);
    }
    
    /**
     * This method wraps the println object call.
     * 
     * @param value The value to print
     */
    public void println(Object value) {
        System.out.println(value);
    }
    
    
    /**
     * This method prints the value.
     * 
     * @param value The value to print
     */
    public void println(String value) {
        System.out.println(value);
    }
}

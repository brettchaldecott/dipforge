/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2013  2015 Burntjam
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
 * ActionCall.java
 */

// package path
package com.rift.coad.change.request.action.leviathan.action;

// imports
import java.io.Serializable;

/**
 * This object represents an action call.
 * 
 * @author brett chaldecott
 */
public class ActionCall implements Serializable {

    // class constants
    private final static String NAME = "action";
    
    /**
     * The default constructor
     */
    public ActionCall() {
    }
    
    
    /**
     * This method returns the name of the action.
     * 
     * @return The name of this action.
     */
    public String getName() {
        return NAME;
    }
}

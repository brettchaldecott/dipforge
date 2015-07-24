/*
 * ChangeControlRDF: The rdf information for the change control system.
 * Copyright (C) 2009  2015 Burntjam
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
 * ActionConstants.java
 */

package com.rift.coad.change;

/**
 * This class defines the constants used by the
 *
 * @author brett chaldecott
 */
public class ActionConstants {
    
    /**
     * The init method.
     */
    public final static String INIT = "INIT";
    
    /**
     * This status means that the action instance is currently running.
     */
    public final static String RUNNING = "RUNNING";

    
    /**
     * This means that the action instance has completed processing.
     */
    public final static String COMPLETE  = "COMPLETE";


    /**
     * This means that there was an error.
     */
    public final static String ERROR = "ERROR";


    /**
     * This status value indicates that the action instance has completed processing.
     */
    public final static String FINISHED = "FINISHED";
}

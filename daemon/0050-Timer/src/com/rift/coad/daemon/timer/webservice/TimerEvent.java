/*
 * Timer: The timer class
 * Copyright (C) 2008  2015 Burntjam
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
 * TimerEvent.java
 */

package com.rift.coad.daemon.timer.webservice;

//java import
import java.io.Serializable;

/**
 * This object stores the values of an event from the database.
 *
 * @author Brett Chaldecott
 */
public class TimerEvent implements Serializable {
    
    // private member variables
    public int id = 0;
    public String jndi = null;
    public String event = null;
    public int month = -1;
    public int day = -1;
    public int hour = -1;
    public int minute = -1;
    public boolean recure = false;
}

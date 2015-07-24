/*
 * EventServerClient: The event server client libraries.
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
 * FeedEvent.java
 */


// package path
package com.rift.coad.daemon.event.webservice;

// The serializable object
import java.io.Serializable;


/**
 * This object represents a single feed event entry.
 * 
 * @author brett chaldecott
 */
public class FeedEvent implements Serializable{
    
    // private member variables
    public String name;
    public String externalId;
    public String username;
    public String application;
    public String description;
    public String url;
    public String role;
    
}

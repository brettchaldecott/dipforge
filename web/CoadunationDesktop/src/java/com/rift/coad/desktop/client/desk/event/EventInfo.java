/*
 * DesktopServerClient: The client interface to the desktop server.
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
 * EventInfo.java
 */

// package path
package com.rift.coad.desktop.client.desk.event;

// imports
import com.rift.coad.desktop.client.desk.MimeType;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * This object is responsible for representing an event.
 * 
 * @author brett chaldecott
 */
public class EventInfo implements IsSerializable {
    
    // private member variables
    private MimeType mime;
    private int eventNum;

    
    /**
     * The default constructor
     */
    public EventInfo() {
    }

    /**
     * The constructor that sets the member variables.
     * @param mime
     * @param eventNum
     */
    public EventInfo(MimeType mime, int eventNum) {
        this.mime = mime;
        this.eventNum = eventNum;
    }
    
    
    /**
     * Get the value of mime
     *
     * @return the value of mime
     */
    public MimeType getMime() {
        return mime;
    }

    /**
     * Set the value of mime
     *
     * @param mime new value of mime
     */
    public void setMime(MimeType mime) {
        this.mime = mime;
    }
    
    
    /**
     * Get the value of eventNum
     *
     * @return the value of eventNum
     */
    public int getEventNum() {
        return eventNum;
    }

    /**
     * Set the value of eventNum
     *
     * @param eventNum new value of eventNum
     */
    public void setEventNum(int eventNum) {
        this.eventNum = eventNum;
    }


}

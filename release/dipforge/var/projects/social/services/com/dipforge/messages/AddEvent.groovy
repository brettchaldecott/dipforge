/*
 * social: Description
 * Copyright (C) Fri Jan 25 05:34:39 SAST 2013 owner 
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
 * AddEvent.groovy
 * @author brett chaldecott
 */

package com.dipforge.messages

// imports
import com.rift.coad.util.connection.ConnectionManager
import java.util.Date
import org.apache.log4j.Logger;
import com.rift.coad.daemon.event.FeedManager;
import com.rift.coad.daemon.event.FeedEvent;

/**
 * This object is responsible for adding a new event
 */
class AddEvent {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.messages.AddEvent");
    
    
    /**
     * This is a test call.
     */
    def addEvent(def Message) {
        log.info("#######  This is a message by id " + Message.getId())
        
        try {
            FeedManager manageDaemon = (FeedManager)ConnectionManager.getInstance().
                    getConnection(FeedManager.class,"event/Manager");
            def title = Message.getUsername() + ":" + Message.getCreated().toString()
            FeedEvent event = new FeedEvent(title, Message.getId(),
                Message.getUsername(),'Social Feed',Message.getMessage(),
                '/DipforgeWeb/social/','guest');       
            manageDaemon.registerEvent(event)
            log.info("##################### The event has been registered : " + event.getRole());
        } catch (Exception ex) {
            log.error("Failed to add the message : " + ex.getMessage(),ex);
        }
    }

}




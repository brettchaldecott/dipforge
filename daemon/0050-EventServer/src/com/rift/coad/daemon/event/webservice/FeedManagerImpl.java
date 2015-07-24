/*
 * EventServer: The event server libraries.
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
 * FeedManagerImpl.java
 */// package path
package com.rift.coad.daemon.event.webservice;

// java imports
import java.rmi.RemoteException;

// log4j import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.util.connection.ConnectionManager;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * The class responsible for managing the inbound web service requests.
 * 
 * @author brett chaldecott
 */
public class FeedManagerImpl implements FeedManager {
    // class constants
    private final static String DEFAULT_FEED_MANAGER_JNDI =
            "java:comp/env/bean/event/Manager";    // private member variables
    private static Logger log = Logger.getLogger(FeedManagerImpl.class);

    /**
     * The constructor of the feed manager implementation.
     */
    public FeedManagerImpl() {
    }

    /**
     * This method is responsible for registering an event.
     * 
     * @param event The event to register.
     * @throws com.rift.coad.daemon.event.webservice.EventException
     */
    public void registerEvent(FeedEvent event) throws EventException {
        try {
            com.rift.coad.daemon.event.FeedManager manager =
                    (com.rift.coad.daemon.event.FeedManager) ConnectionManager.getInstance().getConnection(
                    com.rift.coad.daemon.event.FeedManager.class,
                    DEFAULT_FEED_MANAGER_JNDI);
            manager.registerEvent(new com.rift.coad.daemon.event.FeedEvent(
                    event.name, event.externalId, event.username, event.application, event.description, event.url, event.role));
        } catch (Throwable ex) {
            log.error("Failed register the event :" + ex.getMessage(), ex);
            throw throwEventException("Failed register the event :" + ex.getMessage(), ex);
        }
    }

    
    /**
     * This method wrapps the throwing of the event exception.
     *
     * @param message The message to put in the exception
     * @param ex The exception stack.
     * @exception DNSException
     */
    private EventException throwEventException(String message, Throwable ex) {
        EventException exception = new EventException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        return exception;
    }
}

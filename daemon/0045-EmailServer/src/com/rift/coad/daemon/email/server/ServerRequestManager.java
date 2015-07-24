/*
 * Email: The email server implementation.
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
 * ServerRequestManager.java
 */

// package path
package com.rift.coad.daemon.email.server;

// java imports
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// coadunation imports
import com.rift.coad.lib.thread.ThreadStateMonitor;

/**
 * This class is responsible for handling server requests.
 *
 * @author brett chaldecott
 */
public class ServerRequestManager {
    
    // private singleton reference.
    private static ServerRequestManager singleton = null;
    
    // private member variables
    private ThreadStateMonitor state = new ThreadStateMonitor();
    private BlockingQueue<ServerRequest> entries = 
            new LinkedBlockingQueue<ServerRequest>();
            
    /**
     * Creates a new instance of ServerRequestManager
     */
    private ServerRequestManager() {
    }
    
    
    /**
     * This method is responsible for creating a new instance of the server
     * request manager.
     *
     * @return This method returns a reference to the server request manager.
     */
    public synchronized static ServerRequestManager getInstance() {
        if (singleton == null) {
            singleton = new ServerRequestManager();
        }
        return singleton;
    }
    
    
    /**
     * This method returns a reference to the next request or null if there are
     * no more and it has been terminated.
     *
     * @return A reference to the next request or null if there will be no more.
     */
    public synchronized ServerRequest getRequest() {
        while(!state.isTerminated()) {
            ServerRequest request = entries.poll();
            if (request != null) {
                return request;
            }
            // wait until an entry is inserted or this manager is terminated.
            try {
                wait();
            } catch (Exception ex) {
                // ignore any exception
            }
        }
        return null;
    }
    
    
    /**
     * This method adds a new request to the queue.
     */
    public synchronized void addRequest(ServerRequest request) {
        entries.add(request);
        notifyAll();
    }
    
    
    /**
     * This method terminates the server request manager.
     */
    public synchronized void terminate() {
        state.terminate(true);
        notifyAll();
    }
}

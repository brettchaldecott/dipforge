/*
 * DNSServer: The dns server implementation.
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
 * ServerRequestProcessor.java
 */

// package path
package com.rift.coad.daemon.dns.server;

// coadunation imports
import com.rift.coad.lib.thread.pool.Task;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;

/**
 * This object is responsible for processing a server request.
 *
 * @author brett chaldecott
 */
public class ServerRequestProcessor implements Task {
    
    /** 
     * Creates a new instance of ServerRequestProcessor 
     */
    public ServerRequestProcessor() {
    }
    
    
    /**
     * This method is called to process a request.
     *
     * @param pool The reference to the pool manager so that it can be notified
     *      to release another thread.
     * @exception Exception
     */
    public void process(ThreadPoolManager pool) throws Exception {
        ServerRequest request = ServerRequestManager.getInstance().getRequest();
        
        // the request manager has been terminated.
        if (request == null) {
            return;
        }
        
        pool.releaseThread();
        request.processRequest();
    }
    
    
}

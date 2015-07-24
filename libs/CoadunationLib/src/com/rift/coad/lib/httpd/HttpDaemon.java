/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  2015 Burntjam
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
 * HttpDaemon.java
 *
 * The class responsible for controlling the http interface into Coadunation.
 */

package com.rift.coad.lib.httpd;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.thread.CoadunationThreadGroup;
import com.rift.coad.lib.security.ThreadsPermissionContainer;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;


/**
 * The class responsible for controlling the http interface into Coadunation.
 *
 * @author Brett Chaldecott
 */
public class HttpDaemon {
    
    // Global Constants
    public final static String USERNAME_KEY = "daemon_username";
    public final static int DEFAULT_PORT = 8085;
    
    // the class log variable
    protected Logger log =
        Logger.getLogger(HttpDaemon.class.getName());
    
    
    // the java variables
    private CoadunationThreadGroup threadGroup = null;
    private Configuration configuration = null;
    private HttpRequestManager httpRequestManager = null;
    
    
    /**
     * 
     * Creates a new instance of HttpDaemon 
     * 
     * 
     * @param threadGroup A grouping of threads.
     * @param threadPermissions The object containing the permissions for all
     *      threads.
     * @exception HHttpdException
     */
    public HttpDaemon(CoadunationThreadGroup threadGroup) throws HttpdException {
        try {
            // retrieve the configuration
            configuration = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            
            // set the references
            this.threadGroup = threadGroup.createThreadGroup();
            
            // init the request manager
            httpRequestManager = new HttpRequestManager(threadGroup);
            
            // instanciate the request listener
            RequestListenerThread requestListenerThread = 
                    new RequestListenerThread(httpRequestManager);
            this.threadGroup.addThread(requestListenerThread,
                    configuration.getString(HttpDaemon.USERNAME_KEY));
            requestListenerThread.start();
            
        } catch (Exception ex) {
            throw new HttpdException("Failed to instanciate the HttpDaemon :" +
                    ex.getMessage(),ex);
        }
    }
    
    
    
    /**
     * This method shuts down the http daemon.
     */
    public void shutdown() {
        log.info("Shutting down the web services interface.");
        threadGroup.terminate();
        httpRequestManager.shutdown();
    }
}

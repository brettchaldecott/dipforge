/*
 * Email Server: The email server
 * Copyright (C) 2008  Rift IT Contracting
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
 * Server.java
 */

// package path
package com.rift.coad.daemon.email.server.fetchmail;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.thread.pool.ThreadPoolManager;


/**
 * This server is responsible for acting as the fetch mail server.
 *
 * @author brett chaldecott
 */
public class Server {
    
    /**
     * The error message
     */
    public class Error {
        private Date time = new Date();
        private String msg = null;
        
        
        /**
         * Constructor
         */
        public Error (String msg) {
            this.msg = msg;
        }
        
        /**
         * The to string message
         */
        public String toString() {
            return "[" + 
                    new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time) + 
                    "][" + msg + "]";
        }
    }
    
    // class constants
    private final static String POOL_USER = "fetchmail_pool_user";
    private final static String POOL_SIZE = "fetchmail_pool_size";
    private final static long DEFAULT_POOL_SIZE = 3;
    private final static String NUMBER_ERROR = "fetchmail_number_errors";
    private final static int DEFAULT_NUMBER_ERROR = 10;
    
    
    // class static variables
    private static Server singleton = null;
    private static Logger log = Logger.getLogger(Server.class);
    
    // private member variables
    private ThreadPoolManager threadPool = null;
    private int numErrors = 0;
    private List errors = new ArrayList();
    
    /**
     * Creates a new instance of Server
     */
    private Server() throws ServerException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(Server.class);
            
            // instanciate the email server thread pool
            threadPool = new ThreadPoolManager(
                    (int)config.getLong(POOL_SIZE,DEFAULT_POOL_SIZE), 
                    FetchProcessor.class, config.getString(POOL_USER));
            
            // number errors
            numErrors = (int)config.getLong(NUMBER_ERROR,DEFAULT_NUMBER_ERROR);
        } catch (Exception ex) {
            log.error("Failed to initialize the server : " + 
                    ex.getMessage(),ex);
            throw new ServerException("Failed to initialize the server : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is responsible for initializing the server.
     */
    public static void initialize() throws ServerException {
        
    }
    
    
    /**
     * This method is called to get an instance of the server.
     *
     * @return The server instance.
     * @exception ServerException
     */
    public static Server getInstance() throws ServerException {
        if (singleton == null) {
            singleton = new Server();
        }
        return singleton;
    }
    
    
    /**
     * This method terminates the fetch mail server.
     */
    public void terminate() {
        try {
            threadPool.terminate();
        } catch (Exception ex) {
            log.error("Failed to terminate the fetchmail thread pool : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the status of the fetch mail server.
     */
    public synchronized String getStatus() throws ServerException {
        String result =  "Pool Size: " + threadPool.getSize();
        result += "\nAccounts : " + FetchMailManager.getInstance().size();
        result += "\nErrors [" + errors.size() + "]";
        for (int index = 0; index < errors.size();index++) {
            result += "\n\t" + errors.get(index).toString();
        }
        return result;
    }
    
    
    /**
     * This method adds a new error.
     */
    public synchronized void addError(String error) {
        errors.add(0,new Error(error));
        if (errors.size() > this.numErrors) {
            errors = errors.subList(0,this.numErrors);
        }
    }
}

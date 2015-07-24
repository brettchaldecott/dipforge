/*
 * CoadunationUtil: The coadunation util library.
 * Copyright (C) 2007  2015 Burntjam
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
 * ConnectionManager.java
 */

// package
package com.rift.coad.util.connection;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.InitialContext;

// logging import
import org.apache.log4j.Logger;


/**
 * This object is responsible for managing the connections to various JNDI bound
 * objects. This object assumes it is using a Coadunation based JNDI name
 * service.
 *
 * @author Brett Chaldecott
 */
public class ConnectionManager {
    
    // class singleton methods
    private static ConnectionManager singleton = null;
    private static Map singletonMap = new ConcurrentHashMap();
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(ConnectionManager.class.getName());
    
    
    // private member variables
    private Context context = null;
    private Map lookupCache = new ConcurrentHashMap();
    private Map synchKeyCache = new HashMap();
    
    /** 
     * Creates a new instance of ConnectionManager 
     */
    private ConnectionManager() throws ConnectionException {
        try {
            context = new InitialContext();
        } catch (Exception ex) {
            log.error("Failed init the connection manager : " + ex.getMessage(),
                    ex);
        }
    }
    
    
    /**
     * The contructor of the context manager.
     *
     * @param context The context that will be used to lookup names.
     */
    private ConnectionManager(Context context) {
        this.context = context;
    }
    
    
    /**
     * This method is responsible for returning a reference to the connection
     * manager singleton instance.
     *
     * @return The reference to the connection manager.
     * @exception ConnectionException
     */
    public synchronized static ConnectionManager getInstance() throws 
            ConnectionException {
        if (singleton == null) {
            singleton = new ConnectionManager();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the connection manager instance for the context.
     *
     * @return The reference to the connection manager.
     * @param context The reference to the context object.
     * @exception ConnectionException
     */
    public static ConnectionManager getInstance(Context context) throws 
            ConnectionException {
        synchronized (context) {
            if (!singletonMap.containsKey(context)) {
                singletonMap.put(context,new ConnectionManager());
            }
            return (ConnectionManager)singletonMap.get(context);
        }
    }
    
    
    /**
     * This method returns a connection to the the requested object.
     *
     * @param type The class type for the return result.
     * @param jndiURL The jndi url of the object.
     * @exception ConnectionException
     * @exception java.lang.ClassCastException
     */
    public Object getConnection(Class type, String jndiURL) throws
            ConnectionException, java.lang.ClassCastException {
        Object syncEntry = getSyncKey(jndiURL);
        synchronized(syncEntry) {
            Connection connection = (Connection)lookupCache.get(jndiURL);
            if (connection == null) {
                if (RMIConnection.isRMIConnection(jndiURL)) {
                    connection = new RMIConnection(context, jndiURL);
                } else {
                    connection = new ProxyConnection(context, jndiURL);
                }
                lookupCache.put(jndiURL,connection);
            }
            return connection.getConnection(type);
        }
    }
    
    
    /**
     * This method returns the object to synchronize the connection on.
     *
     * @return The object to synchronize the connection on.
     * @param name The name of the object to synchronize the object on.
     */
    private synchronized Object getSyncKey(String name) {
        Object entry = synchKeyCache.get(name);
        if (entry != null) {
            return entry;
        }
        entry = new String(name);
        synchKeyCache.put(name,entry);
        return entry;
    }
}

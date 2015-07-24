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
 * ProxyConnection.java
 */

// package path
package com.rift.coad.util.connection;

// java imports
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;

// logging import
import org.apache.log4j.Logger;


/**
 * This class manages a proxy connection.
 *
 * @author Brett Chaldecott
 */
public class ProxyConnection implements Connection {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(ProxyConnection.class.getName());
    
    // private member variables
    private Context context = null;
    private String jndiURL = null;
    private Object ref = null;
    
    /** 
     * Creates a new instance of ProxyConnection 
     *
     * @param context The context object responsible for returning information.
     * @param jndiURL The url identifying the object.
     */
    public ProxyConnection(Context context, String jndiURL) {
        this.context = context;
        this.jndiURL = jndiURL;
    }
    
    
    /**
     * This object returns the connection to the object.
     *
     * @return The retrieve connection to the object.
     * @param type The type of object to narrow.
     * @exception ConnectionException
     * @exception java.lang.ClassCastException
     */
    public synchronized Object getConnection(Class type) throws 
            ConnectionException,java.lang.ClassCastException {
        try {
            if (ref == null) {
                ref = context.lookup(jndiURL);
            }
            return ref;
        } catch (Exception ex) {
            log.error("Failed to retrieve a connection : " + ex.getMessage(),
                    ex);
            throw new ConnectionException("Failed to retrieve a connection : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns true if the object being passed contains a local
     * url.
     *
     * @return TRUE if a local URL, FALSE if not.
     * @param jndiURL The string url to perform the check on.
     */
    public static boolean isLocalURL(String jndiURL) {
        if (jndiURL.trim().indexOf("java:comp") == 0) {
            return true;
        }
        return false;
    }
}

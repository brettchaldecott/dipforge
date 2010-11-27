/*
 * CoadunationUtil: The coadunation util library.
 * Copyright (C) 2007  Rift IT Contracting
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
 * RMIConnection.java
 */

// package path
package com.rift.coad.util.connection;

// java imports
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

// logging import
import org.apache.log4j.Logger;

/**
 * This object controls the RMI based connection.
 *
 * @author Brett Chaldecott
 */
public class RMIConnection implements Connection {
    
    // the logger reference
    protected static Logger log =
            Logger.getLogger(RMIConnection.class.getName());
    
    // the private member variables
    private Context context = null;
    private String jndiURL = null;
    private Object ref = null;
    private Map connectionMap = new ConcurrentHashMap();
    
    
    /** 
     * Creates a new instance of RMIConnection 
     *
     * @param context The context object responsible for returning information.
     * @param jndiURL The url identifying the object.
     */
    public RMIConnection(Context context, String jndiURL) {
        this.context = context;
        this.jndiURL = jndiURL;
    }
    
    
    /**
     * This object returns the connection to the object.
     *
     * @return The retrieve connection to the object.
     * @param type The type of object to narrow.
     * @exception ConnectionException
     */
    public Object getConnection(Class type) throws ConnectionException, 
            java.lang.ClassCastException {
        try {
            synchronized (type) {
                Object proxy = connectionMap.get(type);
                if (proxy != null) {
                    return proxy;
                }
                
                // check the reference
                Object ref = null;
                synchronized (this) {
                    if (this.ref == null) {
                        this.ref = context.lookup(jndiURL);
                    }
                    ref = this.ref;
                }
                Object reference = PortableRemoteObject.narrow(ref,type);
                proxy = createProxy(type, reference);
                connectionMap.put(type,proxy);
                return proxy;
            }
        } catch (javax.naming.NameNotFoundException ex) {
            log.error("The name [" + jndiURL + "] is not found : " + 
                    ex.getMessage(),ex);
            throw new com.rift.coad.util.connection.NameNotFound("The name [" +
                    jndiURL + "] is not found : " + ex.getMessage(),ex);
        } catch (java.lang.ClassCastException ex) {
            log.error("Failed to cast object refered to by [" + jndiURL 
                    + "] to a [" + type.getName() + "] because : " + 
                    ex.getMessage(),ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to make the connection : " +
                    ex.getMessage(),ex);
            throw new ConnectionException("Failed to make the connection : " +
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
    public static boolean isRMIConnection(String jndiURL) {
        if (jndiURL.trim().indexOf("java:comp") == -1) {
            return true;
        }
        return false;
    }
    
    
    /**
     * This method is called to invalidate a connection object.
     */
    public synchronized void invalidateConnection() {
        log.debug("Calling clear");
        ref = null;
        connectionMap.clear();
    }
    
    
    /**
     * This method creates the new proxy.
     *
     * @return The reference to the newly create proxy.
     * @param type The type to create the proxy for.
     * @param rmiRef The reference to object to make the call on.
     */
    private Object createProxy(Class type, Object rmiRef) {
        ConnectionHandler handler = new ConnectionHandler(this,rmiRef);
        return (Object)Proxy.newProxyInstance(
                type.getClassLoader(),
                new Class[] {type},handler);
    }
}

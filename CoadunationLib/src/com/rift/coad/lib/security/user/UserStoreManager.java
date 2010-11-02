/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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
 * UserStoreManager.java
 *
 * This object is responsible for managing access to the user store objects. It
 * will not run as a singleton. This will prevent un-wanted threads and objects
 * from accessing it directly. This means it has to be initialized intentionally 
 * and at the correct place.
 */

// package path
package com.rift.coad.lib.security.user;

// java imports
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.Iterator;

// logging import
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.lib.configuration.ConfigurationFactory;
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.security.UserSession;

/**
 * This object is responsible for managing access to the user store objects. It
 * will not run as a singleton. This will prevent un-wanted threads and objects
 * from accessing it directly. This means it has to be initialized intentionally 
 * and at the correct place.
 *
 * @author Brett Chaldecott
 */
public class UserStoreManager {
    
    // the class constants
    private final static String USER_STORE_CONNECTORS = "connectors";
    
    // the classes private member variables
    private Logger log =
        Logger.getLogger(UserStoreManager.class.getName());
    private Map connectors = null;
    
    
    /** 
     * Creates a new instance of UserStoreManager.
     *
     * @exception UserException
     */
    public UserStoreManager() throws UserException {
        try {
            Configuration config = ConfigurationFactory.getInstance().getConfig(
                    this.getClass());
            StringTokenizer connectorList = new StringTokenizer(
                    config.getString(USER_STORE_CONNECTORS),",");
            connectors = new HashMap();
            while(connectorList.hasMoreTokens()) {
                String connectorName = connectorList.nextToken().trim();
                try {
                    log.info("Load the connector [" + 
                         connectorName + "]");
                    UserStoreConnector connector = (UserStoreConnector)Class.
                            forName(connectorName).newInstance();
                    connectors.put(connector.getName(),connector);
                } catch (Exception ex) {
                    log.error("Failed to load the connector [" + 
                         connectorName + "]",ex);
                }
            }
        } catch (Exception ex) {
            throw new UserException("Failed to load the user stores : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will return the list of handlers for the given auth type.
     *
     * @return The list of handlers for the given authentication type.
     * @param authType The type of authentication that is required.
     * @exception UserException
     */
    public Vector getLoginHandlers(String authType) throws UserException {
        try {
            Vector handlerList = new Vector();
            for (Iterator iter = connectors.keySet().iterator(); 
            iter.hasNext();) {
                UserStoreConnector connector = (UserStoreConnector)connectors.
                        get(iter.next());
                if (connector.handleAuthType(authType)) {
                    handlerList.add(connector.getLoginHandler(authType));
                }
            }
            return handlerList;
        } catch (Exception ex) {
            throw new UserException("Fail to retrieve the handlers : "
                    + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method will return the user information for the requested user name.
     *
     * @return The user object containing the user information
     * @param username The name of the user.
     * @exception UserException
     */
    public UserSession getUserInfo(String username) throws UserException {
        try {
            for (Iterator iter = connectors.keySet().iterator(); 
            iter.hasNext();) {
                UserStoreConnector connector = (UserStoreConnector)connectors.
                        get(iter.next());
                UserSession userInfo = connector.getUserInfo(username);
                if (userInfo != null) {
                    return userInfo;
                }
            }
            throw new UserException("Failed to retrieve the user info for [" +
                    username + "]");
        } catch (Exception ex) {
            throw new UserException("Fail to retrieve the handlers : "
                    + ex.getMessage(),ex);
        }
    }
}

/*
 * EMail: The email server
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
 * ServerConfigValidator.java
 */

// package path
package com.rift.coad.daemon.email.server.config;

// java imports
import java.util.List;
import java.util.ArrayList;

// log4j imports
import org.apache.log4j.Logger;

// hibernate imports
import org.hibernate.*;
import org.hibernate.cfg.*;


/**
 * This object is responsible for perfroming common configuration validation on
 * behalf of the server configuration objects.
 *
 * @author brett chaldecott
 */
public class ServerConfigValidator {
    
    
    // private member variables
    private static Logger log = Logger.getLogger(ServerConfigValidator.class);
    
    /**
     * Creates a new instance of ServerConfigValidator
     */
    private ServerConfigValidator() {
    }
    
    
    /**
     * This method performs a check for an address class.
     *
     * @param address The address to perform the check on.
     * @param session The session to retrieve the database connections for.
     * @exception ConfigException
     */
    public static void checkForAddressClash(String address, Session session)
    throws ConfigException {
        try {
            // check for clash on alias tables
            List list = session.createSQLQuery(
                    "SELECT id FROM EmailAlias WHERE address = ?").
                    setString(0,address).list();
            if (list.size() == 1) {
                log.info("There is an alias with the  [" + address + "]");
                throw new ConfigException(
                        "There is an alias with the  [" + address + "]");
            }
            
            // check for clash on forward tables
            list = session.createSQLQuery(
                    "SELECT id FROM EmailForward WHERE address = ?").
                    setString(0,address).list();
            if (list.size() == 1) {
                log.info("There is a forward with the  [" + address + "]");
                throw new ConfigException(
                        "There is an forward with the  [" + address + "]");
            }
            
            
            // check for clash on forward tables
            list = session.createSQLQuery(
                    "SELECT id FROM Emailbox WHERE address = ?").
                    setString(0,address).list();
            if (list.size() == 1) {
                log.info("There is an email address with the  [" + address + "]");
                throw new ConfigException(
                        "There is an email address with the  [" + address + "]");
            }
        } catch (ConfigException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to check for clash on [" + address 
                    + "] because : " + ex.getMessage(),ex);
            throw new ConfigException(
                    "Failed to check for clash on [" + address 
                    + "] because : " + ex.getMessage(),ex);
        }
    }
}

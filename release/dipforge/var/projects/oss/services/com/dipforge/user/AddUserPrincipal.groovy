/*
 * oss: Description
 * Copyright (C) Sun Aug 26 08:43:40 SAST 2012 owner 
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
 * AddUserPrincipal.groovy
 * @author admin
 */

package com.dipforge.user

// imports
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.rdbusermanager.RDBUserManagementMBean
import java.util.Date
import org.apache.log4j.Logger;


/**
 * This object is responsible for creating a new user
 */
class AddUserPrincipal {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.user.AddUserPrincipal");
    
    /**
     * This is a test call.
     */
    def addNewPrincipal(def User, def principal) {
        try {
            if (principal == "none") {
                return "success"
            }
            RDBUserManagementMBean manageDaemon = (RDBUserManagementMBean)ConnectionManager.getInstance().
                    getConnection(RDBUserManagementMBean.class,"user/RDBUserManagement");
            List users = manageDaemon.listUsers()
            String username = User.getUsername()
            boolean found = false;
            for (int index = 0; index < users.size(); index++) {
                String user = users[index].toString()
                if (user.equals(username)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                log.error("The user [$User.getUsername()] was not found")
                return "failed"
            }
            
            // check for an existing principal
            def principals = manageDaemon.listPrincipals()
            found = false;
            principals.each {currentPrincipal ->
                if (currentPrincipal.toString() == principal) {
                    found = true;
                    return;
                }
            }
            if (!found) {
                manageDaemon.addPrincipal(principal)
            }
            def rdbUser = manageDaemon.getUser(User.getUsername())
            def userPrincipals = rdbUser.getPrincipals()
            found = false;
            userPrincipals.each {currentPrincipal ->
                if (currentPrincipal.toString() == principal) {
                    found = true;
                    return;
                }
            }
            if (found) {
                log.error("The principal [${principal}] is already assigned to the user [$User.getUsername()]")
                return "success"
            }
            userPrincipals.add(principal)
            manageDaemon.updateUserPrincipal(User.getUsername(),userPrincipals.toArray(new String[0]));
            
        } catch (Exception ex) {
            log.error("Failed to remove the user principal : ${ex.getMessage()}",ex)
            return "failure"
        }
        return "success"
    }
    
}
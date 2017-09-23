/*
 * oss: Description
 * Copyright (C) Fri Aug 24 05:47:25 SAST 2012 owner 
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
 * RemoveUser.groovy
 * @author admin
 */

package com.dipforge.user

// imports
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.rdbusermanager.RDBUserManagementMBean
import java.util.Date
import org.apache.log4j.Logger;


class RemoveUser {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.user.RemoveUser");
    
    /**
     * This is a test call.
     */
    def removeTheUser(def User) {
        try {
            RDBUserManagementMBean manageDaemon = (RDBUserManagementMBean)ConnectionManager.getInstance().
                    getConnection(RDBUserManagementMBean.class,"user/RDBUserManagement");
            List users = manageDaemon.listUsers()
            String username = User.getUsername()
            boolean found = false;
            for (int index = 0; index < users.size(); index++) {
                String user = users[index].toString()
                if (user.equals(username)) {
                    found = true;
                    break
                }
            }
            if (!found) {
                return "success"
            }
            
            manageDaemon.removeUser(User.getUsername());
            
        } catch (Exception ex) {
            log.error("Failed to remove the user : ${ex.getMessage()}",ex)
            return "failure"
        }
        return "success"
    }
    
}


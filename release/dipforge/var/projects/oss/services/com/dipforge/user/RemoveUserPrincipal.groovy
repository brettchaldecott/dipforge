/*
 * oss: Description
 * Copyright (C) Mon Aug 27 06:54:31 SAST 2012 owner 
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
 * RemoveUserPrincipal.groovy
 * @author admin
 */

package com.dipforge.user

class RemoveUserPrincipal {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.user.AddUserPrincipal");
    
    /**
     * This is a test call.
     */
    def removePrincipal(def User, def principal) {
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
            
            def rdbUser = manageDaemon.getUser(User.getUsername())
            def userPrincipals = rdbUser.getPrincipals()
            userPrincipals.remove(principal)
            manageDaemon.updateUserPrincipal(User.getUsername(),userPrincipals);
            
        } catch (Exception ex) {
            log.error("Failed to remove the user principal : ${ex.getMessage()}",ex)
            return "failure"
        }
        return "success"
    }
    
}
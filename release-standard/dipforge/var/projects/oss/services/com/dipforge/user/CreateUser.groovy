/*
 * oss: Description
 * Copyright (C) Fri Aug 24 05:47:10 SAST 2012 owner 
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
 * CreateUser.groovy
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
class CreateUser {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.user.CreateUser");
    
    /**
     * This is a test call.
     */
    def createNewUser(def User) {
        try {
            RDBUserManagementMBean manageDaemon = (RDBUserManagementMBean)ConnectionManager.getInstance().
                    getConnection(RDBUserManagementMBean.class,"user/RDBUserManagement");
            List users = manageDaemon.listUsers()
            String username = User.getUsername()
            for (int index = 0; index < users.size(); index++) {
                String user = users[index].toString()
                if (user.equals(username)) {
                    return "success"
                }
            }
            
            String[] principals = new String[0];
            // this is a nasty way of setting the profiles up for a user. This should be managed via a type system from
            // the frontend but this will do for the time being as time is not on my side
            if (User.getPrincipals().equals("admin")) {
                principals = ["admin", "audit_trail", "change", "daemon", "data_mapper_broker",
                    "desktop", "dns", "email", "groovy_manager", "guest", "request_broker", 
                    "script_manager", "special", "type_manager","office_suite", "package",
                    "package_manager","product","product_manager","category","category_manager",
                    "isp_manager","organisation","base","organisation"];
            } else if (User.getPrincipals().equals("technical")) {
                principals =["audit_trail", "change", "daemon", "data_mapper_broker",
                    "desktop", "dns", "email", "groovy_manager", "guest", "request_broker", 
                    "script_manager", "special", "type_manager","office_suite"];
            } else if (User.getPrincipals().equals("user")) {
                principals = ["audit_trail", "daemon", 
                    "desktop", "guest", "office_suite"];
            } else if (User.getPrincipals().equals("guest")) {
                principals =["desktop", "guest"];
            } else {
                principals = User.getPrincipals().split(",")
            }
            
            manageDaemon.addUser(User.getUsername(),User.getPassword(),principals);
            
        } catch (Exception ex) {
            log.error("Failed to create the user : ${ex.getMessage()}",ex)
            return "failure"
        }
        return "success"
    }
    
}
/*
 * Fri Feb 19 14:41:48 SAST 2010
 * CreateUserAccount.groovy
 * @author admin
 */

package com.rift.coad.user.accounts

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.rdbusermanager.RDBUserManagementMBean
import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString
import com.rift.coad.rdf.objmapping.base.password.ClearTextPassword
import java.util.List




GenericString result = new GenericString()
result.setDataName("result")
try {
    RDBUserManagementMBean manageDaemon = (RDBUserManagementMBean)ConnectionManager.getInstance().
                    getConnection(RDBUserManagementMBean.class,"user/RDBUserManagement");
    List users = manageDaemon.listUsers()
    boolean duplicate = false
    String username = User.getUsername()
    for (int index = 0; index < users.size(); index++) {
        String user = users[index].toString()
        if (user.equals(username)) {
            duplicate = true
            break;
        }
    }
    
    if (!duplicate) {
        ClearTextPassword password = User.getPassword()
        String profile = User.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"profile").getValue()
        
        String[] principals = new String[0];
        // this is a nasty way of setting the profiles up for a user. This should be managed via a type system from
        // the frontend but this will do for the time being as time is not on my side
        if (profile.equals("admin")) {
            principals = ["admin", "audit_trail", "change", "daemon", "data_mapper_broker",
                "desktop", "dns", "email", "groovy_manager", "guest", "request_broker", 
                "script_manager", "special", "type_manager","office_suite"];
        } else if (profile.equals("technical")) {
            principals =["audit_trail", "change", "daemon", "data_mapper_broker",
                "desktop", "dns", "email", "groovy_manager", "guest", "request_broker", 
                "script_manager", "special", "type_manager","office_suite"];
        } else if (profile.equals("user")) {
            principals = ["audit_trail", "daemon", 
                "desktop", "guest", "office_suite"];
        } else if (profile.equals("guest")) {
            principals =["desktop", "guest"];
        }
        
        manageDaemon.addUser(username,password.getValue(),principals);
        
        
        result.setValue("successfull")
    } else {
        result.setValue("duplicate")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result

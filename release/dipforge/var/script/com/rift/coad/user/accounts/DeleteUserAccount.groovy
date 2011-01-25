/*
 * Mon Feb 22 05:21:24 SAST 2010
 * DeleteUserAccount.groovy
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
    boolean found = false
    String username = User.getUsername()
    for (int index = 0; index < users.size(); index++) {
        String user = users[index].toString()
        if (user.equals(username)) {
            found = true
            break;
        }
    }
    
    if (found) {
        manageDaemon.removeUser(username)
        
        result.setValue("successfull")
    } else {
        result.setValue("not-found")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
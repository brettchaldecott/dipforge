/*
 * Tue Feb 23 04:49:15 SAST 2010
 * CreateFetchMailAccount.groovy
 * @author admin
 */

package com.rift.coad.service.fetchmail

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.email.FetchMailServerMBean
import java.util.Date
import com.rift.coad.rdf.objmapping.service.IPService
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


FetchMailServerMBean daemon = (FetchMailServerMBean)ConnectionManager.getInstance().
                    getConnection(FetchMailServerMBean.class,"email/FetchMail");

GenericString result = new GenericString()
result.setDataName("result")
try {
    List popAccounts = daemon.listPOPAccounts()
    boolean found = false
    String email = FetchMailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"email").getValue()
    for (int index = 0; index < popAccounts.size(); index++) {
        String account = popAccounts[index].toString()
        if (account.equals(email)) {
            found = true
            break;
        }
    }
    
    
    
    if (found) {
        result.setValue("duplicate")
    } else {
        daemon.addPOPAccount(email,FetchMailAccount.getName(),FetchMailAccount.getHostname(),
            FetchMailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.password.ClearTextPassword,"password").getValue())
        result.setValue("successfull")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
/*
 * Mon Feb 22 10:06:54 SAST 2010
 * DeleteMailAccount.groovy
 * @author admin
 */

package com.rift.coad.service.mail.accounts

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.email.EMailServerMBean
import java.util.Date
import com.rift.coad.rdf.objmapping.service.IPService
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


EMailServerMBean daemon = (EMailServerMBean)ConnectionManager.getInstance().
                    getConnection(EMailServerMBean.class,"email/MBeanManager");

GenericString result = new GenericString()
result.setDataName("result")
try {
    List domains = daemon.listDomains()
    boolean found = false
    String domainName = MailAccount.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains[index].toString()
        if (domain.equals(domainName)) {
            found = true
            break;
        }
    }
    
    
    
    if (!found) {
        result.setValue("no-domain")
    } else {
        daemon.removeMailBox(domainName,MailAccount.getName())
        result.setValue("successfull")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
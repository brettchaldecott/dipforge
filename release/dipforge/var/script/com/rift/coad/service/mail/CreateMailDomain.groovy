/*
 * Sat Feb 13 09:40:16 SAST 2010
 * CreateMailDomain.groovy
 * @author admin
 */

package com.rift.coad.service.mail

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.email.EMailServerMBean
import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


EMailServerMBean daemon = (EMailServerMBean)ConnectionManager.getInstance().
                    getConnection(EMailServerMBean.class,"email/MBeanManager");

GenericString result = new GenericString()
result.setDataName("result")
try {
    List domains = daemon.listDomains()
    boolean duplicate = false
    String domainName = MailDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains[index].toString()
        if (domain.equals(domainName)) {
            duplicate = true
            break;
        }
    }
    if (!duplicate) {
        daemon.addDomain(domainName)
        result.setValue("successfull")
    } else {
        result.setValue("duplicate")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
/*
 * Mon Feb 22 15:03:58 SAST 2010
 * DeleteMailRelay.groovy
 * @author admin
 */

package com.rift.coad.service.mail.relays

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.email.EMailServerMBean
import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


EMailServerMBean daemon = (EMailServerMBean)ConnectionManager.getInstance().
                    getConnection(EMailServerMBean.class,"email/MBeanManager");
                    
GenericString result = new GenericString()
try {
    List domains = daemon.listRelays()
    boolean exists = false
    String domainName = MailRelay.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains.get(index).toString()
        if (domain.equals(domainName)) {
            exists = true
            break;
        }
    }
    if (exists) {
        
        daemon.removeRelay(domainName)
        result.setValue("successfull")
    } else {
        result.setValue("no-domain")
    }
    
} catch (Exception ex) {
    result.setValue(ex.getMessage())
}
output = result
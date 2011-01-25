/*
 * Sat Feb 13 10:03:08 SAST 2010
 * DeleteMailDomain.groovy
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
try {
    List domains = daemon.listDomains()
    boolean exists = false
    String domainName = MailDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains.get(index).toString()
        if (domain.equals(domainName)) {
            exists = true
            break;
        }
    }
    if (exists) {
        // retrieve the list of accounts and delete.
        List mailboxes = daemon.listMailBoxes(domainName)
        for (Object mailbox : mailboxes) {
            String[] components = mailbox.toString().split("[@]")
            daemon.removeMailBox(components[1],components[0])
        }
    
        daemon.removeDomain(domainName)
        result.setValue("successfull")
    } else {
        result.setValue("no-domain")
    }
    
} catch (Exception ex) {
    result.setValue(ex.getMessage())
}
output = result
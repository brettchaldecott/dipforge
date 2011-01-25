/*
 * Wed Feb 17 11:36:23 SAST 2010
 * DeleteDNSDomain.groovy
 * @author admin
 */

package com.rift.coad.service.dns

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.dns.DNSServerMBean
import com.rift.coad.daemon.dns.DNSManagement
import com.rift.coad.daemon.dns.SOARecord
import com.rift.coad.daemon.dns.DNSRecord
import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString
import java.util.List


DNSServerMBean manageDaemon = (DNSServerMBean)ConnectionManager.getInstance().
                    getConnection(DNSServerMBean.class,"dns/MBeanManager");
DNSManagement daemon = (DNSManagement)ConnectionManager.getInstance().
                    getConnection(DNSManagement.class,"dns/Management");

GenericString result = new GenericString()
result.setDataName("result")
try {
    List domains = manageDaemon.listZones(3)
    boolean found = false
    String domainName = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains.get(index).toString()
        if (domain.equals(domainName)) {
            found = true
            break;
        }
    }
    if (found) {
        daemon.removeZone(domainName)
        result.setValue("successfull")
    } else {
        result.setValue("not-found")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
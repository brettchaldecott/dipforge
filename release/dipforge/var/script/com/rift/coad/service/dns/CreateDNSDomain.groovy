/*
 * Wed Feb 17 08:01:52 SAST 2010
 * CreateDNSDomain.groovy
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
    boolean duplicate = false
    String domainName = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains[index].toString()
        if (domain.equals(domainName)) {
            duplicate = true
            break;
        }
    }
    if (!duplicate) {
        String contact = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Email.class,"contact").getValue()
        long ttl = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"ttl").getValue()
        long serial = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.base.number.RDFLong.class,"serial").getValue()
        
        Network nsRecord = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"ns")
        Network nsARecord = DNSDomain.getAttribute(com.rift.coad.rdf.objmapping.inventory.Network.class,"nsa")
        
        List records = new java.util.ArrayList()
        
        records.add(new DNSRecord(domainName, 
            ttl, "NS", nsRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").getValue()))
        records.add(new DNSRecord(nsARecord.getId(), 
            ttl, "A", nsARecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").getValue()))
        
        daemon.createZone(domainName,new SOARecord(domainName, ttl, contact.replace("@","."),serial, 
            (long)172800, (long)900, (long)1209600, (long)3600),records)
        result.setValue("successfull")
    } else {
        result.setValue("duplicate")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
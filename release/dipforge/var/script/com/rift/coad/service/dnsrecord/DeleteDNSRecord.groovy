/*
 * Fri Feb 19 08:48:30 SAST 2010
 * DeleteDNSRecord.groovy
 * @author admin
 */

package com.rift.coad.service.dnsrecord


import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.dns.DNSServerMBean
import com.rift.coad.daemon.dns.DNSManagement
import com.rift.coad.daemon.dns.ZoneManagement
import com.rift.coad.daemon.dns.SOARecord
import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString
import java.util.List
import java.util.ArrayList



GenericString result = new GenericString()
result.setDataName("result")
try {
    DNSServerMBean manageDaemon = (DNSServerMBean)ConnectionManager.getInstance().
                    getConnection(DNSServerMBean.class,"dns/MBeanManager");
    DNSManagement daemon = (DNSManagement)ConnectionManager.getInstance().
                    getConnection(DNSManagement.class,"dns/Management");

    List domains = manageDaemon.listZones(3)
    boolean found = false
    String domainName = DNSRecord.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()
    for (int index = 0; index < domains.size(); index++) {
        String domain = domains[index].toString()
        if (domain.equals(domainName)) {
            found = true
            break;
        }
    }
    
    
    if (found) {
    	ZoneManagement zone = daemon.getZone(domainName)
    	List records = zone.getRecords()
    	
    	String prefix = DNSRecord.getName()
        String suffix = DNSRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"suffix").getValue()
        String type = DNSRecord.getAttribute(com.rift.coad.rdf.objmapping.base.str.GenericString.class,"type").getValue()
        
        List newRecords = new ArrayList()
        print "Record to be deleted record ${prefix} ${type} ${suffix}\n" 
    	for (com.rift.coad.daemon.dns.DNSRecord record : records) {
    	    print "The current record is ${record.getPrefix()} ${record.getType()} ${record.getSuffix()}\n"
    	    if (!(record.getPrefix().equals(prefix) && record.getType().equals(type) && record.getSuffix().equals(suffix))) {
    	        newRecords.add(record)
    	    } else {
                print "Deleting the record ${prefix} ${type} ${suffix}\n"    	        
    	    }
    	}
    
        zone.updateRecords(newRecords)
        SOARecord soa = zone.getSOA()
        soa.setSerial((long)new java.util.Date().getTime()/1000)
        zone.updateSOA(soa)
        
        result.setValue("successfull")
    } else {
        result.setValue("not-found")
    }
    
} catch (Exception ex) {
    print "Error on create : " + ex.getMessage()
    result.setValue(ex.getMessage())
}
output = result
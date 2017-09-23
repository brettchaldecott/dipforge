/*
 * oss: Description
 * Copyright (C) Fri Jan 11 05:30:18 SAST 2013 owner 
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * CreateMailDomain.groovy
 * @author admin
 */

package com.dipforge.domain

// imports
import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.daemon.rdbusermanager.RDBUserManagementMBean
import java.util.Date
import org.apache.log4j.Logger;
import com.rift.coad.daemon.dns.DNSServerMBean;
import java.net.InetAddress;

/**
 * This object is responsible for creating a new user
 */
class CreateMailDomain {
    
    static Logger log = Logger.getLogger("com.dipforge.log.pckg.com.dipforge.domain.CreateMailDomain");
    
    
    /**
     * This is a test call.
     */
    def createMailDomain(def Mail) {
        log.info("#######  This is a domain test " + Mail.getDomain().getName())
        try {
            DNSServerMBean manageDaemon = (DNSServerMBean)ConnectionManager.getInstance().
                    getConnection(DNSServerMBean.class,"dns/MBeanManager");
            InetAddress localhost = InetAddress.getLocalHost();
            def fqdn = "${Mail.getDomain().getName()}.${Mail.getDomain().getTld()}"
            def zone = manageDaemon.getZone(fqdn)
            zone = zone + 
                "${fqdn}.        86400    IN MX  10 mail.${fqdn}.\n" +
                "mail.${fqdn}.        604800    IN    A    ${localhost.getHostAddress()}\n"
            log.info("zone\n" +
                "${zone}.")
            manageDaemon.updateZone(fqdn,zone);
        } catch (Exception ex) {
            log.error("Failed to update the domain : " + ex.getMessage());
        }
        
    }

}

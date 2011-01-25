/*
 * Sun Feb 14 09:24:38 SAST 2010
 * ErrorMailDomain.groovy
 * @author admin
 */

package com.rift.coad.service.mail

import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


String domainName = MailDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()

print "ERROR: Failed to create the domain ${domainName}\n"

GenericString result = new GenericString()
result.setValue("successfull")
output = result
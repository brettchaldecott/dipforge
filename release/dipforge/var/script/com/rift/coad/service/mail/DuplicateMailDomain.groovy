/*
 * Sun Feb 14 09:18:55 SAST 2010
 * DuplicateMailDomain.groovy
 * @author admin
 */

package com.rift.coad.service.mail

import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


String domainName = MailDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()

print "DUPLICATE: The domain ${domainName} is a duplicate\n"

GenericString result = new GenericString()
result.setValue("successfull")
output = result

/*
 * Sun Feb 14 09:25:57 SAST 2010
 * SuccessfullMailDomain.groovy
 * @author admin
 */

package com.rift.coad.service.mail

import java.util.Date
import com.rift.coad.rdf.objmapping.inventory.Network
import com.rift.coad.rdf.objmapping.base.str.GenericString


String domainName = MailDomain.getAttribute(com.rift.coad.rdf.objmapping.base.Domain.class,"domain").getValue()

print "SUCESS: The domain was created sucessfully ${domainName}\n"

GenericString result = new GenericString()
result.setValue("successfull")
output = result
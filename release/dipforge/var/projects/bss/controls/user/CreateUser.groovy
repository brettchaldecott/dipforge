/*
 * bss: Description
 * Copyright (C) Tue Aug 28 06:47:56 SAST 2012 owner 
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
 * CreateUser.groovy
 * @author admin
 */

package user


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import com.dipforge.offering.OfferingUtil;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;

def log = Logger.getLogger("com.dipforge.log.user.CreateUser");

log.info("Create a catalog entry")
log.info(params)

// perform a check for a duplicate
def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#id> ?id . "+
    "FILTER (?id = \"${params.userType}\")}")

if (result.size() >= 1) {
    
    def offering = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering/${params.userType}")
    def offeringUtil = new OfferingUtil(offering)
    def organisation = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Organisation#Organisation/base")
    
    def organisationOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/OrganisationOffering#OrganisationOffering")
    organisationOffering.setId("base-${params.userId}")
    organisationOffering.setOffering(offering)
    organisationOffering.setOrganisation(organisation)
    organisationOffering.setInstalled(new java.util.Date())
    organisationOffering.setStatus("active")
    
    def request = RequestHandler.getInstance("bss", "CreateOrganisationOffering", organisationOffering)
    
    // get hold of the user from the offering
    def userConfig = offeringUtil.getProductConfig("user")
    def principals = userConfig.getData()
    
    def user = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/oss/User#User");
    user.setId(params.userId)
    user.setUsername(params.username)
    user.setEmail(params.email)
    user.setPassword(params.password)
    user.setUserType(params.userType)
    user.setPrincipals(principals)
    user.setCreated(new java.util.Date())
    user.setModified(new java.util.Date())
    
    def userRequest = request.createChild('oss', 'CreateUser', user)
    
    def userProduct = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product/user")
    def userOrganisationOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/OrganisationOfferingComponent#OrganisationOfferingComponent")
    userOrganisationOffering.setId("user-${params.userId}")
    userOrganisationOffering.setProduct(userProduct)
    userOrganisationOffering.setUri("http://dipforge.sourceforge.net/schema/rdf/1.0/oss/User#User/${params.userId}")
    userOrganisationOffering.setOrganisationOffering(organisationOffering)
    
    userRequest.createChild('bss', 'CreateOrganisationOfferingComponent', userOrganisationOffering)
    
    def desktop = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#Desktop");
    
    desktop.setId(params.userId)
    def desktopConfig = offeringUtil.getProductConfig("desktop")
    desktop.setName(desktopConfig.getData())
    desktop.setCreated(new java.util.Date())
    desktop.setModified(new java.util.Date())
    desktop.setUser(user)
    
    def desktopRequest = userRequest.createChild('oss', 'CreateDesktop', desktop)
    
    def desktopOrganisationOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/OrganisationOfferingComponent#OrganisationOfferingComponent")
    def desktopProduct = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product/desktop")
    desktopOrganisationOffering.setId("desktop-${params.userId}")
    desktopOrganisationOffering.setProduct(desktopProduct)
    desktopOrganisationOffering.setUri("http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#Desktop/${params.userId}")
    desktopOrganisationOffering.setOrganisationOffering(organisationOffering)
    
    desktopRequest.createChild('bss', 'CreateOrganisationOfferingComponent', desktopOrganisationOffering)
    
    // create the applications
    def applicationConfig = offeringUtil.getProductConfig("application")
    def appList = applicationConfig.getData().split(":")
    def applicationProduct = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product/application")
    appList.each { appEntry ->
        def desktopApplication = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#DesktopApplication");
        
        def appInfo = processAppEntry(log, appEntry)
        def appName = appInfo["name"]
        desktopApplication.setId("application-${params.userId}-${appName}")
        desktopApplication.setName(appName)
        desktopApplication.setUrl(appInfo["url"])
        desktopApplication.setPrincipal(appInfo["principle"])
        desktopApplication.setThumbnail(appInfo["thumbnail"])
        desktopApplication.setIcon(appInfo["icon"])
        desktopApplication.setCreated(new java.util.Date())
        desktopApplication.setModified(new java.util.Date())
        desktopApplication.setDesktop(desktop)
        desktopApplication.setUser(user)
        
        def applicationRequest = 
            desktopRequest.createChild('oss', 'CreateDesktopApplication', desktopApplication)
            
        
        def applicationOrganisationOffering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/OrganisationOfferingComponent#OrganisationOfferingComponent")
        applicationOrganisationOffering.setId("application-${params.userId}-${appName}")
        applicationOrganisationOffering.setProduct(applicationProduct)
        applicationOrganisationOffering.setUri("http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#DesktopApplication/application-${params.userId}-${appName}")
        applicationOrganisationOffering.setOrganisationOffering(organisationOffering)
        
        applicationRequest.createChild('bss', 'CreateOrganisationOfferingComponent', applicationOrganisationOffering)
        
    }
    
    request.makeRequest()
    
    print "success"
} else {
    
    print "Failed to create the user, no matching product ${params.userType}"
}


def processAppEntry(def log, def appEntry) {
    log.info("Entry : " + appEntry)
    def result = [:]
    def appEntryValues = appEntry.split(",")
    
    appEntryValues.each {value ->
        log.info("Value is : " + value)
        def keyValue = value.split("=")
        if (keyValue.length == 2) {
            result[keyValue[0]] = keyValue[1]
        }
    }
    return result
}

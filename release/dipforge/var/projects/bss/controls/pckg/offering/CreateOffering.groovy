/*
 * bss: the bss functionality
 * Copyright (C) Thu Jun 28 20:19:31 SAST 2012 owner 
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
 * CreateOffering.groovy
 * @author brett chaldecott
 */

package pckg.offering


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("com.dipforge.log.pckg.offering.CreateOffering");

log.debug("Parameters : " + params)

// perform a check for a duplicate
def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#id> ?id . "+
    "FILTER (?id = \"${params.offeringId}\")}")
if (result.size() == 0) {
    log.debug("Create a new instance of the offering")
    def offering = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering")
    
    log.debug("Set the values")
    offering.setId(params.offeringId)
    offering.setName(params.offeringName)
    offering.setDescription(params.offeringDescription)
    offering.setThumbnail(params.thumbnail)
    offering.setIcon(params.icon)
    offering.setPckg(RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg/${params.offeringPackage}"))
    offering.setCatalog(RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry/${params.offeringCatalog}"))
    
    // setup costing
    def costs = []
    if (params.costId instanceof String[]) {
        for (def index = 0; index < params.costId.size(); index++) {
            def cost = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Cost#Cost")
            if (params.costLineItem[index] != "") {
                cost.setId(params.costId[index])
                cost.setLineItem(params.costLineItem[index])
                cost.setType(params.costType[index])
                cost.setAmount(params.costValue[index])
                costs.add(cost)
            }
        }
    } else if (params.costLineItem != null && params.costLineItem != "") {
        log.info("Adding the cost item : " + params.costLineItem)
        def cost = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Cost#Cost")
        cost.setId(params.costId)
        cost.setLineItem(params.costLineItem)
        cost.setType(params.costType)
        cost.setAmount(params.costValue)
        costs.add(cost)
    }
    
    offering.setCosts(costs)
    offering.setCreated(new java.util.Date());
    offering.setStatus("active");
    
    log.debug("##### Init the request : " + offering.toXML())
    log.debug("##### Package xml : " + offering.getPckg().toXML())
    RequestHandler.getInstance("bss", "CreateOffering", offering).makeRequest()
    print "success"
} else {
    print "Fail: Attempting to add a duplicate offering identified by ID."
}


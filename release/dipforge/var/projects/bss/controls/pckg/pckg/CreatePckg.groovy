/*
 * bss: Description
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
 * CreatePckg.groovy
 * @author brett chaldecott
 */

package pckg.pckg


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("pckg.pckg.CreatePckg");

log.info("Parameters : " + params)

// perform a check for a duplicate
def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#id> ?id . "+
    "FILTER (?id = \"${params.pckgId}\")}")
if (result.size() == 0) {
    log.info("Create a new instance of the pckg")
    def pckg = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg")
    
    def category = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category/${params.pckgCategory}")
    def vendor = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor/${params.pckgVendor}")
    
    log.info("Set the values")
    pckg.setId(params.pckgId)
    pckg.setName(params.pckgName)
    pckg.setDescription(params.pckgDescription)
    pckg.setDataType(params.pckgDataType)
    pckg.setCategory(category)
    pckg.setVendor(vendor)
    if (params.pckgDependency != null && params.pckgDependency != "") {
        def dependency = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg/${params.pckgDependency}")
        pckg.setDependency(dependency)
    }
    pckg.setThumbnail(params.thumbnail)
    pckg.setIcon(params.icon)
    
    log.info("##### Init the request : " + pckg.toXML())
    RequestHandler.getInstance("bss", "CreatePckg", pckg).makeRequest()
    print "success"
} else {
    print "Fail: Attempting to add a duplicate pckg identified by ID."
}


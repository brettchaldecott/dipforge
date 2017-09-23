/*
 * bss: Description
 * Copyright (C) Tue Jul 10 21:18:15 SAST 2012 owner 
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
 * RemoveVendor.groovy
 * @author brett chaldecott
 */

package pckg.vendor



import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("com.dipforge.log.pckg.vendor.RemoveVendor");

log.debug("Parameters : " + params)

// perform a check for a duplicate
def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#id> ?id . "+
    "FILTER (?id = \"${params.vendorId}\")}")
if (result.size() >= 1) {
    log.debug("Before callign get from store")
    def vendor = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor/${params.vendorId}")
    
    log.debug("##### Init the request : " + vendor.toXML())
    
    RequestHandler.getInstance("bss", "RemoveVendor", vendor).makeRequest()
    
    print "success"
} else {
    print "Fail: No vendor [${params.vendorId}] found"
}



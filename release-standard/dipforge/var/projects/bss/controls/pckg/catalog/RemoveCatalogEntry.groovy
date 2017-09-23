/*
 * bss: Description
 * Copyright (C) Tue Aug 07 21:08:32 SAST 2012 owner 
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
 * RemoveCatalogEntry.groovy
 * @author brett chaldecott
 */

package pckg.catalog

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;

def log = Logger.getLogger("com.dipforge.log.pckg.catalog.RemoveCatalogEntry");


try {
    log.info("Remove a catalog entry")
    log.info(params)
    def catalogEntry = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry/${params.catalogEntryId}")
    
    log.info("##### Init the request : " + catalogEntry.toXML())
    RequestHandler.getInstance("bss", "RemoveCatalogEntry", catalogEntry).makeRequest()
    
    print "success"
} catch (Exception ex) {
    log.error("Failed to init the request : " + ex.getMessage(),ex);
    print "fail"
}


/*
 * bss: Description
 * Copyright (C) Tue Aug 07 21:08:11 SAST 2012 owner 
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
 * AddCatalogEntry.groovy
 * @author admin
 */

package pckg.catalog

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;

def log = Logger.getLogger("com.dipforge.log.pckg.catalog.AddCatalogEntry");

try {
    log.info("Create a catalog entry")
    log.info(params)
    def catalogEntry = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry")
    
    log.info("Setup the values")
    catalogEntry.setId(params.catalogEntryId)
    catalogEntry.setName(params.catalogEntryName)
    catalogEntry.setDescription(params.categoryEntryDescription)
    catalogEntry.setThumbnail(params.thumbnail)
    catalogEntry.setIcon(params.icon)
    
    log.info("##### Init the request : " + catalogEntry.toXML())
    RequestHandler.getInstance("bss", "CreateCatalogEntry", catalogEntry).makeRequest()
    
    if (params.parentId == "home") {
        def parentEntry = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Catalog#Catalog/${params.parentId}") 
        if (parentEntry.getEntries() == null) {
            parentEntry.setEntries([catalogEntry]);
        } else {
            if (!(parentEntry.getEntries() instanceof java.util.List)) {
                parentEntry.setEntries([parentEntry.getEntries()])
            }
            parentEntry.getEntries().add(catalogEntry)
        }
        
        RequestHandler.getInstance("bss", "UpdateCatalog", parentEntry).makeRequest()
    } else {
        def parentEntry = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#CatalogEntry/${params.parentId}") 
        if (parentEntry.getChildren() == null) {
            parentEntry.setChildren([catalogEntry]);
        } else {
            if (!(parentEntry.getChildren() instanceof java.util.List)) {
                parentEntry.setChildren([parentEntry.getChildren()])
            }
            parentEntry.getChildren().add(catalogEntry)
        }
        RequestHandler.getInstance("bss", "UpdateCatalogEntry", parentEntry).makeRequest()
    }
    
    print "success"
} catch (Exception ex) {
    log.error("Failed to init the request : " + ex.getMessage(),ex);
    print "fail"
}

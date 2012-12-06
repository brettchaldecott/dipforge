/*
 * bss: Description
 * Copyright (C) Wed Jun 27 05:43:23 SAST 2012 owner 
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
 * List.groovy
 * @author brett chaldecott
 */

package shopping

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("com.dipforge.log.pckg.offering.List");

def offerings = []
log.info("########################################### The shopping list parameters : " + params)
if (params.catalogId != null) {
    offerings = RDF.query("SELECT ?s WHERE {" +
        "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> .  " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#name> ?name . " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#catalog> ?catalog ." +
        "?catalog <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#id> ?catalogId . " +
        "FILTER(?catalogId = '${params.catalogId}') } " +
        "ORDER BY ?name ")
} else {
    offerings = RDF.query("SELECT ?s WHERE {" +
        "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> . " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#name> ?name . } " +
        "ORDER BY ?name ")
}

log.debug("query result " + offerings)
offerings.each { prods ->
    def offering = prods[0]
    log.debug("Offering configuration " + offering.getCosts());
    //log.info("Offering package" + offering.getPckg());
    //log.debug("Offering catalog" + offering.getCatalog());
}

// this code is designed to use a small catalog.
// it needs to be optimsed for larger catalogs
def catalog = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Catalog#Catalog> . } ")

def catalogEntries = []
if (catalog.size() != 0) {
    catalog[0][0].getEntries()?.each { entry ->
        walkCatalogEntry(entry)
        catalogEntries.add(entry)
    }
}


PageManager.includeWithResult("list.gsp", request, response, ["offerings" : offerings, "catalogEntries": catalogEntries])

/**
 * This method walks the catalog
 */
def walkCatalogEntry(def entry) {
    entry.getChildren()?.each { child ->
        walkCatalogEntry(child)
    }
}

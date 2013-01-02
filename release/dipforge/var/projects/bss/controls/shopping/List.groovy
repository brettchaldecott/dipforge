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


def log = Logger.getLogger("com.dipforge.log.shopping.List");

def offerings = []
log.debug("########################################### The shopping list parameters : " + params)
if (params.catalogId != null) {
    offerings = RDF.query("SELECT ?s WHERE {" +
        "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> .  " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#name> ?name . " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#catalog> ?catalog ." +
        "?catalog <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#id> ?catalogId . " +
        "FILTER(?catalogId = '${params.catalogId}' && ?catalogId != 'base' && ?catalogId != 'user-base') } " +
        "ORDER BY ?name ")
} else {
    offerings = RDF.query("SELECT ?s WHERE {" +
        "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> . " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#name> ?name . " +
        "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#catalog> ?catalog ." +
        "?catalog <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/CatalogEntry#id> ?catalogId . " +
        "FILTER(?catalogId != 'base' && ?catalogId != 'user-base') } " +
        "ORDER BY ?name ")
}

log.debug("query result " + offerings)
//offerings.each { prods ->
//    def offering = prods[0]
    // perform the deep copies of the objects
//    log.debug("Offering configuration " + offering.getCosts());
//    log.debug("Offering package" + offering.getPckg());
    //log.debug("Offering catalog" + offering.getCatalog());
//}

RDF.deapCopy(offerings, ["pckg.products.product"])

PageManager.includeWithResult("list.gsp", request, response, ["offerings" : offerings])


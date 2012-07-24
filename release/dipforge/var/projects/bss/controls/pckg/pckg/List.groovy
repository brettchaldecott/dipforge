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

package pckg.pckg

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("pckg.pckg.List");

def pckgs = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#name> ?name . } " +
    "ORDER BY ?name ")

log.info("query result " + pckgs)
pckgs.each { prods ->
    def pckg = prods[0]
    log.info("Pckg id is " + pckg.getCategory()?.getId());
}

def products = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#name> ?name . } " +
    "ORDER BY ?name ")

log.info("query result " + products)

PageManager.includeWithResult("list.gsp", request, response, ["pckgs" : pckgs, "products": products])


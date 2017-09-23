/*
 * bss: Description
 * Copyright (C) Sun Jul 12 09:26:38 SAST 2015 owner 
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
 * @author admin
 */

package setup

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;

def log = Logger.getLogger("com.dipforge.log.pckg.vendor.List");

def offerings = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering> . }")

def catalog = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Catalog#Catalog> . } ")

if (catalog.size() == 0) {
    catalog = null
} else {
    catalog[0][0].getEntries()?.each { entry ->
    }
    catalog = catalog[0][0]
}

def packages = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg> . } ")

log.debug("query result " + packages)

def products = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#name> ?name . } " +
    "ORDER BY ?name ")

log.debug("query result " + products)

def categories = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#name> ?name . } " +
    "ORDER BY ?name ")

log.debug("query result " + categories)

def vendors = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#name> ?name . } " +
    "ORDER BY ?name ")

log.debug("query result " + vendors)

PageManager.includeWithResult("list.gsp", request, response, ["offerings": offerings, "catalog": catalog, "packages" : packages, "products": products, "categories": categories, "vendors": vendors])


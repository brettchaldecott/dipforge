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

package pckg.product

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("com.dipforge.log.pckg.product.List");

def products = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#name> ?name . } " +
    "ORDER BY ?name ")

log.debug("query result " + products)
products.each { prods ->
    def product = prods[0]
    log.debug("Product id is " + product.getCategory()?.getId());
    log.debug("Product configuration " + product.getConfigurationManager());
    log.debug("Product dependancy " + product.getDependency());
    log.debug("Product vendor " + product.getVendor());
}

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

PageManager.includeWithResult("list.gsp", request, response, ["products" : products, "categories": categories, "vendors": vendors])


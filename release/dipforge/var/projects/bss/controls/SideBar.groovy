/*
 * bss: Description
 * Copyright (C) Fri Aug 17 07:23:42 SAST 2012 owner 
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
 * SideBar.groovy
 * @author brett chaldecott
 */

import com.dipforge.utils.PageManager;
import org.apache.log4j.Logger;
import com.dipforge.semantic.RDF;

def log = Logger.getLogger("com.dipforge.log.manage.shopping.List");

def values = request.getAttribute("GROOVY_RESULT")

if (values != null && values instanceof java.util.Map && values.menuItem == "shopping") {
    def result = RDF.query("SELECT ?s WHERE {" +
        "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Catalog#Catalog> . } ")
    if (result.size() != 0) {
        result[0][0].getEntries()?.each { entry ->
            walkCatalogEntry(entry)
        }
    }
    log.info("With include with result")
    PageManager.includeWithResult("sidebar.gsp", request, response, ["catalog": result[0][0]])
} else {
    PageManager.include("sidebar.gsp", request, response)
}




/**
 * This method walks the catalog
 */
def walkCatalogEntry(def entry) {
    entry.getChildren()?.each { child ->
        walkCatalogEntry(child)
    }
}

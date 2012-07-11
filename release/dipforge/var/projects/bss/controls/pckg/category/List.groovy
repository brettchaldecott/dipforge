/*
 * bss: Description
 * Copyright (C) Thu Jun 28 06:23:41 SAST 2012 owner 
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

package pckg.category

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("pckg.category.List");

def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#name> ?name . } " +
    "ORDER BY ?name ")

log.info("query result " + result)

PageManager.includeWithResult("list.gsp", request, response, ["categories" : result])


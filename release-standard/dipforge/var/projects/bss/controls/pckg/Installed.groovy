/*
 * bss: Description
 * Copyright (C) Tue Jun 26 19:45:47 SAST 2012 owner 
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
 * Installed.groovy
 * @author admin
 */

package pckg

import com.dipforge.utils.PageManager;
import org.apache.log4j.Logger;
import com.dipforge.semantic.RDF;

// setup the log
def log = Logger.getLogger("com.dipforge.log.pckg.pckg.installed");

// the results
def results = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/OrganisationOffering#OrganisationOffering> . }")

log.info("query result " + results)

// preform pre-load of required data
results.each { orgOffering ->
    def offering = orgOffering[0]
    log.info("Offering " + offering.getOffering());
}

// the include
PageManager.includeWithResult("installed.gsp", request, response, ["installed" : results])

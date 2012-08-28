/*
 * bss: Description
 * Copyright (C) Sat Aug 18 08:14:06 SAST 2012 owner 
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
 * Users.groovy
 * @author brett chaldecott
 */

import com.dipforge.utils.PageManager;
import org.apache.log4j.Logger;
import com.dipforge.semantic.RDF;


def log = Logger.getLogger("com.dipforge.log.pckg.user.management");

def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/User#User> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/User#username> ?username . } " +
    "ORDER BY ?username ")

log.debug("query result " + result)

PageManager.includeWithResult("user-management.gsp", request, response , ["users" : result])

/*
 * social: Description
 * Copyright (C) Tue Jan 22 06:18:00 SAST 2013 owner 
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

package messages

import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import groovy.json.*


def log = Logger.getLogger("com.dipforge.log.message.List");

def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/social/Message#Message> . " + 
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/social/Message#created> ?created .} " +
    "ORDER BY DESC(?created) LIMIT 30" )

def builder = new JsonBuilder()
def messages = []
result.each { row ->
    def message = row[0]
    messages.add(
             [
                id: message.getId(),
                username: message.getUsername(),
                message: message.getMessage(),
                created: message.getCreated().toString(),
            ])
}

builder(messages)
println builder.toString()
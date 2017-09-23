/*
 * ide: Description
 * Copyright (C) Wed Nov 30 17:31:24 SAST 2011 owner 
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
 * ExecuteScript.groovy
 * @author Brett Chaldecott
 */

package scripts

import com.rift.coad.util.connection.ConnectionManager

import com.rift.coad.util.connection.ConnectionManager
import com.rift.coad.groovy.GroovyDaemon
import com.dipforge.utils.HTMLCharacterEscaper
import java.util.Date
import files.mimes.MimeTypeMapper
    
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("scripts.ExecuteScript");

log.info(params)

try {
    def daemon = ConnectionManager.getInstance().getConnection(
			GroovyDaemon.class,"groovy/Daemon")
    //def path = params.path + "/" + params.fileName
    print daemon.execute(params.project,params.path)
} catch (Exception ex) {
    log.error("Failed to execute the in the project [" + params.project + 
        "] with the path [" + params.path + "]" + ex.getMessage());
    throw ex
}



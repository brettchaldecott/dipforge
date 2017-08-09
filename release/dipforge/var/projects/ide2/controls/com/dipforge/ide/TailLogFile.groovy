/*
 * ide2: Description
 * Copyright (C) Wed Aug 09 18:54:20 UTC 2017 owner 
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
 * TailLogFile.groovy
 * @author admin
 */

package com.dipforge.ide

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.logs.LogViewer
import java.util.Date

import groovy.json.*;
import org.apache.log4j.Logger;

def builder = new JsonBuilder()
def log = Logger.getLogger("com.dipforge.log.project.com.dipforge.ide.TailLogFile");


try {
    
    def daemon = ConnectionManager.getInstance().getConnection(
        	LogViewer.class,"project/logs/LogViewer")
    
    builder(daemon.tailLog(params.logFile,Integer.parseInt(params.endLine)));
    
} catch (ex) {
    builder([])
    log.error("Failed to retrieve a list of files : " + ex.getMessage(),ex)
}


log.info("Returning the log results")

response.setContentType("application/json");

println builder.toString()


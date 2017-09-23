/*
 * ide2: Description
 * Copyright (C) Wed Aug 09 17:00:22 UTC 2017 owner 
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
 * ListLogs.groovy
 * @author admin
 */

package com.dipforge.ide

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.logs.LogViewer
import java.util.Date
    
import groovy.json.*;
import org.apache.log4j.Logger;



try {
    
    def result = []
    def builder = new JsonBuilder()
    def log = Logger.getLogger("com.dipforge.log.project.com.dipforge.ide.ListLogs");

    def daemon = ConnectionManager.getInstance().getConnection(
    		LogViewer.class,"project/logs/LogViewer")
    
    def logs = daemon.getLogList();
    log.info("The logs are ${logs}")
    logs.each { logName ->
        result += [
            ["file" : logName]
            ]
    }
    
    log.info("logs " + result)
    builder(result)
    
    response.setContentType("application/json");
    
    println builder.toString()
        
} catch (ex) {
    
    log.error("Failed to retrieve a list of files : " + ex.getMessage(),ex)
}



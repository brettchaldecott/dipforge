/*
 * ide2: Description
 * Copyright (C) Sun Jun 04 18:00:43 UTC 2017 owner 
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
 * ExecuteFile.groovy
 * @author admin
 */

package com.dipforge.ide

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.dipforge.utils.HTMLCharacterEscaper
import java.util.Date
import com.dipforge.utils.HttpRequestUtil
import com.rift.coad.groovy.GroovyDaemon
import com.burntjam.dipforge.python.jep.PythonJepDaemon
import groovy.json.*;
import org.apache.log4j.Logger;
import java.io.StringWriter;
import java.io.PrintWriter;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("com.dipforge.log.ide.CreateFile");
def resultJson = [
    status: "ok",
    result: ""
]


log.info(params)

def fileContent = ""
try {
    def json = HttpRequestUtil.requestContentToJson(request)
    
    def file = json.path.trim()
    def daemon = null
    if (file.endsWith(".py")) {
        daemon = ConnectionManager.getInstance().getConnection(
			PythonJepDaemon.class,"python/Daemon")
    } else {
        daemon = ConnectionManager.getInstance().getConnection(
			GroovyDaemon.class,"groovy/Daemon")
    }
    
    
    resultJson.result = daemon.execute(json.project,json.path)
} catch (Throwable ex) {
    log.error("Failed to create the file [" + params.fileName + "] in the project [" + params.project + "]" + ex.getMessage());
    resultJson.status = "failed"
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    ex.printStackTrace(pw);
    resultJson.result = sw.toString();
}

builder(resultJson)
response.setContentType("application/json");
print builder.toString()
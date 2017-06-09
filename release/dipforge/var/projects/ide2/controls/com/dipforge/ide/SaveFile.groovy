/*
 * ide2: Description
 * Copyright (C) Thu May 25 06:07:37 UTC 2017 owner 
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
 * SaveFile.groovy
 * @author admin
 */

package com.dipforge.ide

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.dipforge.utils.HTMLCharacterEscaper
import java.util.Date
import com.dipforge.utils.HttpRequestUtil
	
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("com.dipforge.log.ide.SaveFile");

log.info(params)

def fileContent = ""
try {
    def jsonData = HttpRequestUtil.requestContentToJson(request)
    
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
	daemon.updateFile(jsonData.project,jsonData.path,jsonData.content)
	
	response.setContentType("application/json");
    log.info("The json response is [${jsonData}]");
    builder(jsonData)
    println builder.toString()
	log.info("After saving the file")
} catch (Exception ex) {
    log.error("Failed to save the project file " + ex.getMessage(),ex);
}

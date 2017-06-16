/*
 * ide2: Description
 * Copyright (C) Wed May 24 07:45:38 UTC 2017 owner 
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
 * GetFile.groovy
 * @author admin
 */

package com.dipforge.ide

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.dipforge.utils.HTMLCharacterEscaper
import java.util.Date
import org.apache.commons.io.FilenameUtils;
import groovy.json.*;
import org.apache.log4j.Logger;

def log = Logger.getLogger("com.dipforge.log.ide.GetFile");

log.info("parameters [" + params + "]")
try {
    def builder = new JsonBuilder()
    
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
    def fileData = daemon.getFileData(params.project,params.path)
	def fileExtension = FilenameUtils.getExtension(params.path)

    response.setContentType("application/json");
    
    builder([project:params.project,path:params.path,contents:fileData.getContents(),fileHash:fileData.getHash(),fileExtension:fileExtension])
    println builder.toString()
	
} catch (Exception ex) {
    log.error("Failed to retrieve the project file : " + ex.getMessage(),ex);
    throw ex
}


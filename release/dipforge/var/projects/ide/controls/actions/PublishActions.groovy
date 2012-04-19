/*
 * ide: The 
 * Copyright (C) Wed Apr 18 11:13:17 SAST 2012 owner 
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
 * PublishActions.groovy
 * @author admin
 */

package actions

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.rift.dipforge.project.ProjectActionManager
import java.util.Date
import files.mimes.MimeTypeMapper
    
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("actions.PublishActions");

try {
    log.info("parameters[" + params + "]")
    def daemon = ConnectionManager.getInstance().getConnection(
    		ProjectFileManager.class,"project/FileManager")
	daemon.updateFile(params.project,params.path,params.content)
    daemon = ConnectionManager.getInstance().getConnection(
			ProjectActionManager.class,"project/ActionManager")
	daemon.publishActions(params.content)
} catch (Exception ex) {
    log.error("Failed to actions the project methods : " + ex.getMessage());
    throw ex;
}

builder([success:true])
println builder.toString()

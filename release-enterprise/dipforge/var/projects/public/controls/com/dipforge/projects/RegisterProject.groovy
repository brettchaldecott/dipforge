/*
 * public: Description
 * Copyright (C) Mon Feb 06 02:09:51 UTC 2017 owner 
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
 * RegisterProject.groovy
 * @author brett chaldecott
 */

package com.dipforge.projects

import groovy.json.*
import com.dipforge.utils.PageManager;
import org.apache.log4j.Logger;
import com.dipforge.semantic.RDF;
import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.rift.dipforge.project.ProjectTypeManager
import com.rift.dipforge.project.ProjectTimerManager
import com.rift.dipforge.project.ProjectMethodManager
import com.rift.dipforge.project.ProjectActionManager
import java.util.Date
import groovy.json.*;
import org.apache.log4j.Logger;

def builder = new JsonBuilder()
def log = Logger.getLogger("com.dipforge.projects.RegisterProject");
response.setContentType("application/json");


if (params.project == null) {
    builder(["status" : "no-project","description" : "no projects"])
    println builder.toString()
    return
}

def projectFiles = "project_methods.xml,project_times.xml"
if (params.files != null) {
    projectFiles = params.files
}

try {
    def projectName = params.project
    
	log.info("parameters[" + params + "]")
    def fileDaemon = ConnectionManager.getInstance().getConnection(
    		ProjectFileManager.class,"project/FileManager")
    		
    // project types
	def projectTypeDaemon = ConnectionManager.getInstance().getConnection(
			ProjectTypeManager.class,"project/TypeManager")
	projectTypeDaemon.publishTypes(fileDaemon.getFile(params.project,"/config/project_types.xml"))
	
	if (projectFiles.contains("project_times.xml")) {
    	def projectTimeDaemon = ConnectionManager.getInstance().getConnection(
    			ProjectTimerManager.class,"project/TimerManager")
    	projectTimeDaemon.publishTimes(fileDaemon.getFile(params.project,"/config/project_times.xml"))
	}
	
	if (projectFiles.contains("project_methods.xml")) {
    	def projectMethodDaemon = ConnectionManager.getInstance().getConnection(
			    ProjectMethodManager.class,"project/MethodManager")
    	projectMethodDaemon.publishMethods(fileDaemon.getFile(params.project,"/config/project_methods.xml"))
	}
	
	projectActionDaemon = ConnectionManager.getInstance().getConnection(
			ProjectActionManager.class,"project/ActionManager")
	projectActionDaemon.publishActions(fileDaemon.getFile(params.project,"/config/project_actions.xml"))
	
} catch (Exception ex) {
    log.error("Failed to publish the project types : " + ex.getMessage());
    throw ex;
}


def status = [
                    status: 'registered'
             ]
builder(status)
println builder.toString()

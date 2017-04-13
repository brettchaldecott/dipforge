/*
 * swagger: Description
 * Copyright (C) Wed Apr 12 08:01:13 UTC 2017 owner 
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
 * ProjectList.groovy
 * @author admin
 */

package app.projects


import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import java.util.Date
import groovy.json.*;
import org.apache.log4j.Logger;
import com.dipforge.utils.PageManager;

def log = Logger.getLogger("com.dipforge.swagger.ProjectList");

try {

    def daemon = ConnectionManager.getInstance().getConnection(
		ProjectManager.class,"project/Manager")
	def projects = daemon.listProjects()
	def projectList = []
	projects.each { project ->
	    projectList += [
	        [
	            name: project.getName(),
	            project: project.getName()
	        ]
	        ]
	}
	
	PageManager.includeWithResult("list.gsp", request, response, ["projects" : projectList])

} catch (Exception ex) {
    log.error("Failed to retrieve the list of projects ${ex.getMessage()}",ex)
}
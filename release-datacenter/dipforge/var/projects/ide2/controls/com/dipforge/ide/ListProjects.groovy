/*
 * ide2: Description
 * Copyright (C) Fri May 12 18:02:41 UTC 2017 owner 
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
 * ListProjects.groovy
 * @author admin
 */

package com.dipforge.ide


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;
import com.dipforge.utils.HttpRequestUtil;
import groovy.json.*
import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager


def log = Logger.getLogger("com.dipforge.log.ide.ListProjects");

try {
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectManager.class,"project/Manager")
	def projects = daemon.listProjects()
	def jsonData = []
    projects.each { project ->
	    jsonData.add(
	        [
	            name: project.getName(),
	            description: project.getDescription()
	        ])
	}
    def builder = new JsonBuilder()
    
    
    response.setContentType("application/json");
    log.info("The json response is [${jsonData}]");
    builder(jsonData)
    print builder.toString()
    
} catch (Exception ex) {
    log.error("Failed to retrieve specified order ${ex.getMessage()}",ex)
}
/*
 * ide2: Description
 * Copyright (C) Mon May 29 01:53:21 UTC 2017 owner 
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
 * ListFolders.groovy
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
import org.apache.commons.io.FilenameUtils;


def log = Logger.getLogger("com.dipforge.log.ide.ListFolders");

try {
    response.setContentType("application/json");
    
    if (!params.project) {
        log.error("Parameters missing require project and path")
        print "[]"
        return
    }
    
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
	def files = daemon.listFolders(params.project,params.directoryCommaList)
	def jsonData = []
    files.each { file ->
        def leafNode = false
        def iconCls = "file"
        def fileExtension = ""
        if (file.getType() == 1) {
		    log.info("This is a file and leaf node")			
	        leafNode = true
	        fileExtension = FilenameUtils.getExtension(file.getPath())
	    } else if (file.getType() == 0) {
		    log.info("This is a directory")
		    leafNode = false
            if (file.getPath().equals("/config")) {
                iconCls = 'config'
            } else if (file.getPath().equals("/controls")) {
                iconCls = 'controls'
            } else if (file.getPath().equals("/flows")) {
                iconCls = 'flows'
            } else if (file.getPath().equals("/lib")) {
                iconCls = 'lib'
            } else if (file.getPath().equals("/packages")) {
                iconCls = 'packages'
            } else if (file.getPath().equals("/services")) {
                iconCls = 'services'
            } else if (file.getPath().equals("/views")) {
                iconCls = 'views'
            }
        }
    
	    jsonData.add(
	        [
	            project: params.project,
	            label: file.getPath().substring(file.getPath().lastIndexOf("/") + 1),
	            path: (file.getPath().startsWith("//") ? file.getPath().substring(1) : file.getPath()),
	            leafNode: leafNode,
	            iconCls: iconCls,
	            fileExtension: fileExtension
	        ])
	}
    def builder = new JsonBuilder()
    
    
    log.info("The json response is [${jsonData}]");
    builder(jsonData)
    print builder.toString()
    
} catch (Exception ex) {
    log.error("Failed to retrieve specified order ${ex.getMessage()}",ex)
}
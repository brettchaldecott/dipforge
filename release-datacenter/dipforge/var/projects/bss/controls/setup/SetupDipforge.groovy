/*
 * bss: Description
 * Copyright (C) Sun Jul 12 07:10:52 SAST 2015 owner 
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
 * SetupDipforge.groovy
 * @author admin
 */

package setup

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.rift.dipforge.project.ProjectTypeManager
import com.rift.dipforge.project.ProjectMethodManager
import com.rift.dipforge.project.ProjectActionManager
import com.dipforge.utils.HTMLCharacterEscaper
import java.util.Date
import com.dipforge.setup.SetupBase
import org.apache.log4j.Logger;
import com.dipforge.semantic.RDF;
import com.dipforge.request.RequestHandler;
import com.rift.coad.lib.common.RandomGuid;

def log = Logger.getLogger("com.dipforge.log.setup.SetupDipforge");


try {
    def fileManager = ConnectionManager.getInstance().getConnection(
    			ProjectFileManager.class,"project/FileManager")
    
    
    // publish the types
    def typeManager = ConnectionManager.getInstance().getConnection(
    			ProjectTypeManager.class,"project/TypeManager")
    typeManager.publishTypes(fileManager.getFile("oss","config/project_types.xml"))
    typeManager.publishTypes(fileManager.getFile("social","config/project_types.xml"))
    typeManager.publishTypes(fileManager.getFile("bss","config/project_types.xml"))
    
    // publish the methods
    def methodManager = ConnectionManager.getInstance().getConnection(
    			ProjectMethodManager.class,"project/MethodManager")
    methodManager.publishMethods(fileManager.getFile("oss","config/project_methods.xml"))
    methodManager.publishMethods(fileManager.getFile("social","config/project_methods.xml"))
    
    // publish the actions
    def actionManager = ConnectionManager.getInstance().getConnection(
    			ProjectActionManager.class,"project/ActionManager")
    actionManager.publishActions(fileManager.getFile("oss","config/project_actions.xml"))
    actionManager.publishActions(fileManager.getFile("social","config/project_actions.xml"))
    actionManager.publishActions(fileManager.getFile("bss","config/project_actions.xml"))
    
    // publish the base
    def setup = new SetupBase()
    setup.executeSetup()
    
    // invoke call on message system to notify it of completed
    def message = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/social/Message#Message")
    message.setId(RandomGuid.getInstance().getGuid())
    message.setUsername(request.getRemoteUser())
    message.setMessage("Base setup complete visit <a href='http://dipforge.net'>Dipforge.net</a> for more information.")
    message.setCreated(new java.util.Date())
    
    log.info("Init the request : " + message)
    RequestHandler.getInstance("social", "CreateMessage", message).makeRequest()
    
    print "success"
    
} catch (Exception ex) {
    log.error("Failed to setup the system : ${ex.getMessage()}",ex)
    print "Failed: setup failed : " + ex.getMessage()
}



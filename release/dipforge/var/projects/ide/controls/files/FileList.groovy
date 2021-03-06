/**
 * The file list groovy object
 */
// package path
package files;

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import java.util.Date
import files.mimes.MimeTypeMapper
	
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("files.FileList");

try {
	log.info("the parameters are " + params)
	
	if (params.node == "root") {
		def daemon = ConnectionManager.getInstance().getConnection(
			ProjectManager.class,"project/Manager")
		def projects = daemon.listProjects()
		projects.each { project ->
		    tree += [
		        [
		            id: "P:" + project.getName(),
		            project: project.getName(),
		            file: project.getName(),
		            user: project.getModifiedBy(),
		            leaf: false,
		            project_dir: true,
		  			iconCls: 'project'        
		        ]
		        ]
		}
    } else if (params.node.startsWith("P:")) {
		
		// handle the rest of the nodes		
		def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
		def nodeValues = params.node.split(":")
		def project = nodeValues[1]
		def filePath = ""
		if (nodeValues.length == 3) {
		    filePath = nodeValues[2]
		}
		def files = daemon.listFiles(project,filePath)
		def mimeTypeMapper = new MimeTypeMapper()
		files.each { file ->
			if (!file.getName().startsWith(".")) {
			    log.info("The icon type for " + file.getPath() + " is : " + file.getType())
			    def mode = "directory"
			    def editor = "directory"
			    def fileName = file.getName() 
			    iconCls = 'file'
    		    if (file.getType() == 1) {
				    log.info("This is a file and leaf node")			
			        leafNode = true
			        def pos = fileName.lastIndexOf(".")
			        def fileSuffix = "text"
			        // types file
                    if (file.getPath().equals("/config/project_types.xml") ||
                    file.getPath().startsWith("/config/types")) {
			        	editor = "ace-project-types"
			        	mode = mimeTypeMapper.getMode("xml")
			        } else if (file.getPath().equals("/config/project_methods.xml") ||
                        file.getPath().startsWith("/config/methods")) {
    		        	editor = "ace-project-methods"
			        	mode = mimeTypeMapper.getMode("xml")
			        } else if (file.getPath().equals("/config/project_actions.xml") ||
                        file.getPath().startsWith("/config/actions")) {
        	        	editor = "ace-project-actions"
			        	mode = mimeTypeMapper.getMode("xml")
			        } else if (file.getPath().equals("/config/project_times.xml") ||
                        file.getPath().startsWith("/config/times")) {
                    	editor = "ace-project-times"
			        	mode = mimeTypeMapper.getMode("xml")
			        } else if (file.getPath().startsWith("/services/")) {
        	        	if (pos != -1) {
    			            fileSuffix = fileName.substring(pos + 1).toLowerCase();
				        }
				        editor = "ace-project-services"
			        	mode = mimeTypeMapper.getMode(fileSuffix)
			        }// normal files 
			        else {
				        if (pos != -1) {
				            fileSuffix = fileName.substring(pos + 1).toLowerCase();
				        }
				        editor = mimeTypeMapper.getEditor(fileSuffix)
				        mode = mimeTypeMapper.getMode(fileSuffix)
				    }
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
		        tree += [
		            [
		                id: "P:" + project + ":" + file.getPath(),
		                project: project,
		                file: fileName,
		                path: file.getPath(),
		                user: file.getModifier(),
		                leaf: leafNode,
		  			    iconCls: iconCls,
		  			    editor: editor,
		  			    project_dir: false,
		  			    mode: mode
		            ]
		        ]
		    }
		}
	}
} catch (Exception ex) {
   log.error("This is an error" + ex.getMessage());
}
log.info("Tree " + tree)
builder(tree)

response.setContentType("application/json");

println builder.toString()

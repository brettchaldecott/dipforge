
// package path
package files;

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import java.util.Date
	
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger(String.class);

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
		  			iconCls: 'project'        
		        ]
		        ]
		}
    } else if (params.node.startsWith("P:")) {
		def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
		def nodeValues = params.node.split(":")
		def project = nodeValues[1]
		def filePath = ""
		if (nodeValues.length == 3) {
		    filePath = nodeValues[2]
		}
		def files = daemon.listFiles(project,filePath)
		files.each { file ->
			if (!file.getName().startsWith(".")) {
			   log.info("The icon type for " + file.getPath() + " is : " + file.getType())			
			    if (file.getType() == 1) {
				    log.info("This is a file and leaf node")			
			        leafNode = true
			        iconCls = 'file'
			    } else if (file.getType() == 0) {
				    log.info("This is a directory")
				    leafNode = false
			        iconCls = 'directory'
			    }
		        tree += [
		            [
		                id: "P:" + project + ":" + file.getPath(),
		                project: project,
		                file: file.getName(),
		                path: file.getPath(),
		                user: file.getModifier(),
		                leaf: leafNode,
		  			    iconCls: iconCls 
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
println builder.toString()
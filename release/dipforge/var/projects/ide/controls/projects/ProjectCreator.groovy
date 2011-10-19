/**
 * The project remove script
 */
// package path
package projects;

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import java.util.Date
import files.mimes.MimeTypeMapper
	
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("files.ProjectCreator");

try {
	log.info("parameters[" + params + "]")
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectManager.class,"project/Manager")
	daemon.createProject(params.project,params.description)
} catch (Exception ex) {
    log.error("Failed to create the project file " + ex.getMessage());
    throw ex;
}

builder([success:true])
println builder.toString()

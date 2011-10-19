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
def log = Logger.getLogger("projects.ProjectRemover");

try {
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectManager.class,"project/Manager")
	daemon.deleteProject(params.project)
} catch (Exception ex) {
    log.error("Failed to delete the project file " + ex.getMessage());
    throw ex;
}

builder([success:true])
println builder.toString()



/**
 * The file remove groovy script
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
def log = Logger.getLogger("files.FileRename");
log.info("params : [" + params + "]");

def fileContent = ""
try {
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
	daemon.renameFile(params.project,params.source,params.target)
} catch (Exception ex) {
    log.error("Failed to rename the file in project " + ex.getMessage());
    throw ex;
}

builder([success:true])

response.setContentType("application/json");

println builder.toString()

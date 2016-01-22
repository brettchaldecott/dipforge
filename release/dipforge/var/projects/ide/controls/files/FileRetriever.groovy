/**
 * The file list groovy object
 */
// package path
package files;

import com.rift.coad.util.connection.ConnectionManager
import com.rift.dipforge.project.ProjectManager
import com.rift.dipforge.project.ProjectFileManager
import com.dipforge.utils.HTMLCharacterEscaper
import java.util.Date
import files.mimes.MimeTypeMapper
	
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("files.FileRetriever");

def fileContent = ""
log.info("parameters [" + params + "]")
try {
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
	fileContent = new HTMLCharacterEscaper(daemon.getFile(params.project,params.path))
	
} catch (Exception ex) {
    log.error("Failed to retrieve the project file " + ex.getMessage());
}

builder([contents:fileContent.escape()])

response.setContentType("application/json");

println builder.toString()

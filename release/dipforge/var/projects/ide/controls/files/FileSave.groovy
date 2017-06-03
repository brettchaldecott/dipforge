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
import com.dipforge.utils.HttpRequestUtil
	
import groovy.json.*;
import org.apache.log4j.Logger;

def tree = []
def builder = new JsonBuilder()
def log = Logger.getLogger("files.FileSave");

log.info(params)

def fileContent = ""
try {
    
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
	daemon.updateFile(params.project,params.path,params.content)
	
} catch (Exception ex) {
    log.error("Failed to update the project file " + ex.getMessage());
}



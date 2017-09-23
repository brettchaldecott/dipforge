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
def log = Logger.getLogger("com.dipforge.log.ide.CreateProject");

log.info(params)

def fileContent = ""
try {
    def daemon = ConnectionManager.getInstance().getConnection(
			ProjectFileManager.class,"project/FileManager")
	def path = params.path + "/" + params.fileName
	if (params.fileType == "folder") {
		daemon.createDirectory(params.project,path)
	} else {
		daemon.createFile(params.project,path,params.fileType)
	}
} catch (Exception ex) {
    log.error("Failed to create the file [" + params.fileName + "] in the project [" + params.project + "]" + ex.getMessage());
    throw ex
}



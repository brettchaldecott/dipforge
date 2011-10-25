/**
 * The file list groovy object
 */
// package path
package files;

import java.util.Date
import files.mimes.MimeTypeMapper
	
import groovy.json.*;
import org.apache.log4j.Logger;

def fileTypes = []
def builder = new JsonBuilder()
def log = Logger.getLogger("files.FileTypes");

try {
	def mimeTypeMapper = new MimeTypeMapper()
    fileTypes += [
        [
            name: 'groovy',
            suffix: 'groovy',
            editor: mimeTypeMapper.getEditor('groovy'),
            mode: mimeTypeMapper.getEditor('groovy')
        ],
        [
            name: 'javascript',
            suffix: 'js',
            editor: mimeTypeMapper.getEditor('js'),
            mode: mimeTypeMapper.getEditor('js')
        ],
        [
            name: 'html',
            suffix: 'html',
            editor: mimeTypeMapper.getEditor('html'),
            mode: mimeTypeMapper.getEditor('html')
        ],
        [
            name: 'css',
            suffix: 'css',
            editor: mimeTypeMapper.getEditor('css'),
            mode: mimeTypeMapper.getEditor('css')
        ],
        [
            name: 'xml',
            suffix: 'xml',
            editor: mimeTypeMapper.getEditor('xml'),
            mode: mimeTypeMapper.getEditor('xml')
        ],
        [
            name: 'json',
            suffix: 'json',
            editor: mimeTypeMapper.getEditor('json'),
            mode: mimeTypeMapper.getEditor('json')
        ],
        [
            name: 'java',
            suffix: 'java',
            editor: mimeTypeMapper.getEditor('java'),
            mode: mimeTypeMapper.getEditor('java')
        ],
        [
            name: 'folder',
            suffix: 'folder',
            editor: null,
            mode: null
        ]]

} catch (Exception ex) {
   log.error("This is an error" + ex.getMessage());
}
log.info("FileTypes " + fileTypes)
builder(fileTypes)
println builder.toString()
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
            mode: mimeTypeMapper.getMode('groovy')
        ],
        [
            name: 'js',
            suffix: 'js',
            editor: mimeTypeMapper.getEditor('js'),
            mode: mimeTypeMapper.getMode('js')
        ],
        [
            name: 'html',
            suffix: 'html',
            editor: mimeTypeMapper.getEditor('html'),
            mode: mimeTypeMapper.getMode('html')
        ],
        [
            name: 'gsp',
            suffix: 'gsp',
            editor: mimeTypeMapper.getEditor('gsp'),
            mode: mimeTypeMapper.getMode('html')
        ],
        [
            name: 'css',
            suffix: 'css',
            editor: mimeTypeMapper.getEditor('css'),
            mode: mimeTypeMapper.getMode('css')
        ],
        [
            name: 'xml',
            suffix: 'xml',
            editor: mimeTypeMapper.getEditor('xml'),
            mode: mimeTypeMapper.getMode('xml')
        ],
        [
            name: 'json',
            suffix: 'json',
            editor: mimeTypeMapper.getEditor('json'),
            mode: mimeTypeMapper.getMode('json')
        ],
        [
            name: 'lwf',
            suffix: 'lwf',
            editor: mimeTypeMapper.getEditor('leviathan'),
            mode: mimeTypeMapper.getMode('lwf')
        ]/*,
        [
            name: 'java',
            suffix: 'java',
            editor: mimeTypeMapper.getEditor('java'),
            mode: mimeTypeMapper.getEditor('java')
        ]*/,
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

response.setContentType("application/json");

println builder.toString()

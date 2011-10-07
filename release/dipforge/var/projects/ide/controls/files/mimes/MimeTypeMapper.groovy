/**
 * The file list groovy object
 */
// package path
package files.mimes;


class MimeTypeMapper {

    
    def getMode(String suffix) {
        if (suffix.equals('groovy')) {
            return "ace/mode/groovy";
        } else if (suffix.equals('js')) {
            return "ace/mode/javascript";
        } else if (suffix.equals('html') || suffix.equals('htm')) {
            return "ace/mode/html";
        } else if (suffix.equals('css')) {
            return "ace/mode/css";
        } else if (suffix.equals('xml')) {
            return "ace/mode/xml";
        } else if (suffix.equals('json')) {
            return "ace/mode/json";
        } else if (suffix.equals('java')) {
            return "ace/mode/java";
        } else if (suffix.equals('png') || suffix.equals('jpeg') || suffix.equals('png')
            || suffix.equals('ico') || suffix.equals('gif')) {
            return "image";
        }
        return "ace/mode/text";
    }


    def getEditor(String suffix) {
    	if (suffix.equals('groovy')) {
            return "ace";
        } else if (suffix.equals('js')) {
            return "ace";
        } else if (suffix.equals('html') || suffix.equals('htm')) {
            return "ace";
        } else if (suffix.equals('css')) {
            return "ace";
        } else if (suffix.equals('xml')) {
            return "ace";
        } else if (suffix.equals('json')) {
            return "ace";
        } else if (suffix.equals('java')) {
            return "ace";
        } else if (suffix.equals('png') || suffix.equals('jpeg') || suffix.equals('png')
            || suffix.equals('ico') || suffix.equals('gif')) {
            return "image";
        }
        return "ace";
    }

}

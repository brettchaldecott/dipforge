package com.dipforge.utils
/**
 * This file contains the definition of the HTML escaper.
 *
 * @author Brett Chaldecott
 */

class HTMLCharacterEscaper {

    def content = null;

    /**
     * The constructor of the html character escaper.
     *
     * @param content The content to escape
     */    
    HTMLCharacterEscaper(content) {
        this.content = content
    }
    
    /**
     * This method escapes the string
     */
    def escape() {
        def result = escape(this.content,"&","&amp;")
        result = escape(result,"<","&lt;")
        return escape(result,">","&gt;") 
    }
    
    
    def escape(content,character,replace) {
        def pos = 0
        def lastFound = 0
        def result = ""
        while ( (pos = content.indexOf(character,lastFound)) != -1) {
            result += content.substring(lastFound,pos)
            if (content.indexOf(replace) == pos) {
            	result += replace
            	lastFound = pos + replace.length() + 1
            } else {
                result += replace
                lastFound = pos + 1
            }
        }
        result += content.substring(lastFound)
        return result
    }
}
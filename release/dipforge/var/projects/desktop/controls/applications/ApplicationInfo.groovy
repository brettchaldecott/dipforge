package applications

import groovy.json.*
import com.dipforge.utils.PageManager;
import org.apache.log4j.Logger;
import com.dipforge.semantic.RDF;


def log = Logger.getLogger("com.dipforge.log.desktop.application");

def result = RDF.query("SELECT ?d WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/User#User> . " +
    "?d a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#Desktop> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/User#username> ?username . " + 
    "?d <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#user> ?s . " + 
    "FILTER (?username = '${request.getRemoteUser()}')} " +
    "ORDER BY ?username ")
    
log.debug("#########################   query result " + result)

def builder = new JsonBuilder()


if (result.size() != 1) {
    def menu = [
                 [
                    image: 'images/icon/Social.png',                
                    title: 'Feed',
                    url: '/DipforgeWeb/social/'
                 ],
                 [
                    image: 'images/icon/IDE.png',
                    title: 'IDE',
                    url: '/DipforgeWeb/ide/'
                ],
                [
                    image: 'images/icon/Documents.png',                
                    title: 'Documentation',
                    url: '/DipforgeWeb/documentation/'
                ],
                [
                    image: 'images/icon/FileManager.png',                
                    title: 'File Manager',
                    url: '/FileManager/path/'
                ],
                [
                    image: 'images/icon/Admin.png',                
                    title: 'Admin',
                    url: '/DipforgeAdmin/'
                ],
                [
                    image: 'images/icon/Admin.png',                
                    title: 'Tomcat',
                    url: '/manager/html'
                ],
                [
                    image: 'images/icon/Audit.png',                
                    title: 'Audit Trail Console',
                    url: '/AuditTrailConsole/'
                ],
                [
                    image: 'images/icon/Organisation.png',                
                    title: 'Administration',
                    url: '/DipforgeWeb/bss/'
                ]/*,
                [
                    image: 'images/icon/Admin.png',                
                    title: 'Shop',
                    url: '/shop/'
                ]*/]
    
    builder(menu)
} else {
    // get the desktop
    def desktop = result[0][0]
    
    def applicationResults = RDF.query("SELECT ?a WHERE {" +
            "?d a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#Desktop> . " +
            "?a a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#DesktopApplication> . " +
            "?d <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/Desktop#id> ?id . " + 
            "?a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#desktop> ?d . " + 
            "?a <http://dipforge.sourceforge.net/schema/rdf/1.0/oss/DesktopApplication#name> ?appName . " + 
            "FILTER (?id = '${desktop.getId()}')} " +
            "ORDER BY ?appName ")
    
    log.debug("#########################  application query result " + applicationResults)
    
    
    def menu = [
                [
                    image: 'images/icon/Social.png',                
                    title: 'Feed',
                    url: '/DipforgeWeb/social/'
                ]
            ]
    
    // return the default for now
    applicationResults.each { row ->
        def app = row[0]
        menu.add([
                    image: app.getIcon(),
                    title: app.getName(),
                    url: app.getUrl()
                ])
    }
    
    builder(menu)
}

println builder.toString()

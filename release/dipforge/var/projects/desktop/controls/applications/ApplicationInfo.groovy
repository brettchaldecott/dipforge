package applications

import groovy.json.*

def builder = new JsonBuilder()


def menu = [
             [
                image: 'images/icon/IDE.png',
                title: 'IDE',
                url: '/desktop/ide/'
            ],
            [
                image: 'images/icon/Documents.png',                
                title: 'Documentation',
                url: '/desktop/documentation/'
            ],
            [
                image: 'images/icon/FileManager.png',                
                title: 'File Manager',
                url: '/desktop/FileManager/path/'
            ],
            [
                image: 'images/icon/Admin.png',                
                title: 'Admin',
                url: '/desktop/DipforgeAdmin/'
            ],
            [
                image: 'images/icon/Admin.png',                
                title: 'Tomcat',
                url: '/desktop/manager/html'
            ],
            [
                image: 'images/icon/Audit.png',                
                title: 'Audit Trail Console',
                url: '/desktop/AuditTrailConsole/'
            ],
            [
                image: 'images/icon/Organisation.png',                
                title: 'Administration',
                url: '/desktop/bss/'
            ],
            [
                image: 'images/icon/Admin.png',                
                title: 'Shop',
                url: '/shop/'
            ]]

builder(menu)

println builder.toString()

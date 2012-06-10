package applications

import groovy.json.*

def builder = new JsonBuilder()


def menu = [
             [
                image: 'images/icon/IDE.png',
                title: 'IDE',
                url: '/ide/'
            ],
            [
                image: 'images/icon/Documents.png',                
                title: 'Documentation',
                url: '/documentation/'
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
                url: '/AuditTrailConsole'
            ]]

builder(menu)

println builder.toString()
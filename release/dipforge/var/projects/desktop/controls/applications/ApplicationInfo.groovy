package applications

import groovy.json.*

def builder = new JsonBuilder()


def menu = [
             [
                image: 'favicon.ico',
                title: 'IDE',
                url: '/ide/'
            ],
            [
                image: 'favicon.ico',                
                title: 'Documentation',
                url: '/documentation/'
            ],
            [
                image: 'favicon.ico',                
                title: 'File Manager',
                url: '/FileManager/path/'
            ],
            [
                image: 'favicon.ico',                
                title: 'Admin',
                url: '/DipforgeAdmin/'
            ],
            [
                image: 'favicon.ico',                
                title: 'Tomcat',
                url: '/manager/html'
            ]]

builder(menu)

println builder.toString()
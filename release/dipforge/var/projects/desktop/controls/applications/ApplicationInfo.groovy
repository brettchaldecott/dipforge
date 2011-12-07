package applications

import groovy.json.*

def builder = new JsonBuilder()


def menu = [
             [
                image: 'favicon.ico',
                title: '<b>IDE</b>',
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
            ]]

builder(menu)

println builder.toString()
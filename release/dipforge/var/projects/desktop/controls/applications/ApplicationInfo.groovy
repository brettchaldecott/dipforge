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
                title: 'CNN',
                url: 'http://www.cnn.com'
            ],
            [
                image: 'favicon.ico',                
                title: 'SKY',
                url: 'http://www.sky.com'
            ],
            [
                image: 'favicon.ico',                
                title: 'News 24',
                url: 'http://www.news24.com'
            ],
            [
                image: 'favicon.ico',                
                title: 'Space.com',
                url: 'http://www.space.com'
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
            ]]

builder(menu)

println builder.toString()
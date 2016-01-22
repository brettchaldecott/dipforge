package applications

import groovy.json.*

def builder = new JsonBuilder()

def userFeeds = [
             [
                image: 'favicon.ico',
                author: 'test',
                msg: 'there is news',
                url: 'http://news.bbc.co.uk/'
            ],
            [
                image: 'favicon.ico',
                author: 'test',
                msg: 'this is cnn',
                url: 'http://www.cnn.com/'
            ],
            [
                image: 'favicon.ico',
                author: 'test',
                msg: 'there is skyjljljljl jljljljljlkl jljljljljljk xxxxx bbbbb dddd ccccc eeee ffff',
                url: 'http://www.sky.com/'
            ],
            [
                image: 'favicon.ico',
                author: 'test',
                msg: 'there is skyjljljljl jljljljljlkl jljljljljljk xxxxx bbbbb dddd ccccc eeee ffff',
                url: 'http://www.sky.com/'
            ]]

builder(userFeeds)

response.setContentType("application/json");

println builder.toString()

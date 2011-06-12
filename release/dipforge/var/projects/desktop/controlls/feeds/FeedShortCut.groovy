package applications

import groovy.json.*

def builder = new JsonBuilder()

def feeds = [
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
                msg: 'there is sky well I do not know',
                url: 'http://www.sky.com/'
            ],
            [
                image: 'favicon.ico',
                author: 'test',
                msg: 'there is sky well I do not know',
                url: 'http://www.sky.com/'
            ],
            [
                image: 'favicon.ico',
                author: 'test',
                msg: 'there is sky well I do not know',
                url: 'http://www.sky.com/'
            ],
            [
                image: 'favicon.ico',
                author: 'test',
                msg: 'there is sky well I do not know',
                url: 'http://www.sky.com/'
            ]]

builder(feeds)

println builder.toString()
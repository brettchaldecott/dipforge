/*
 * test: Description
 * Copyright (C) Mon Apr 10 10:58:24 UTC 2017 owner 
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Swagger.groovy
 * @author admin
 */

package com.dipforge.project


response.setContentType("application/json");

println """
{
   "swagger":"2.0",
   "info":{
      "description":"This is the API for the IDE2 for Dipforge.",
      "version":"1.0.0",
      "title":"IDE2 Dipforge",
      "termsOfService":"",
      "contact":{
         "email":"info@burntjam.com"
      },
      "license":{
         "name":"Comercial",
         "url":""
      }
   },
   "host":"${request.getServerName()}:${request.getServerPort()}",
   "basePath":"/DipforgeWeb/test/",
   "tags":[
      {
         "name":"user",
         "description":"Operations about user",
         "externalDocs":{
            "description":"Find out more about our store",
            "url":"http://swagger.io"
         }
      }
   ],
   "schemes":[
      "http"
   ],
   "paths":{
      "/com/dipforge/file/UploadFile.groovy":{
         "post":{
            "tags":[
               "files"
            ],
            "summary":"Upload a File",
            "description":"Upload a File",
            "operationId":"UploadFile",
            "consumes":[
               "multipart/form-data"
            ],
            "produces":[
               "application/json"
            ],
            "parameters":[
               {
                  "in":"formData",
                  "name":"id",
                  "description":"id of scene of guide to attache this to",
                  "required":true,
                  "type": "string"
               },
               {
                  "in":"formData",
                  "name":"file",
                  "description":"id of scene of guide to attache this to",
                  "required":true,
                  "type": "file"
               }
            ],
            "responses":{
               "200":{
                  "description":"successful operation",
               },
               "405":{
                  "description":"Invalid input"
               }
            }
         },
      },
      
   },
   "securityDefinitions":{
   },
   "definitions":{
   },
   "externalDocs":{
      "description":"Find out more about Swagger",
      "url":"http://swagger.io"
   }
}
"""




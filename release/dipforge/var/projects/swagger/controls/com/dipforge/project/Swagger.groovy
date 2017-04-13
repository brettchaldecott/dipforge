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

println '''
{
   "swagger":"2.0",
   "info":{
      "description":"The API for the swagger project",
      "version":"1.0.0",
      "title":"BSS API",
      "termsOfService":"http://swagger.io/terms/",
      "contact":{
         "email":"info@burntjam.com"
      },
      "license":{
         "name":"Commercial",
         "url":"http://burntam.com/license/"
      }
   },
   "host":"",
   "basePath":"/DipforgeWeb/swagger/",
   "tags":[
   ],
   "schemes":[
      "http"
   ],
   "paths":{
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
'''




/*
 * dipforge: Description
 * Copyright (C) Mon Apr 10 07:10:52 UTC 2017 owner 
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
 * SwaggerJson.groovy
 * @author admin
 */

package com.dipforge.swagger



class SwaggerJson {
    
    
    
    // class constants
    final static def SWAGGER_VERSION = "2.0";
    final static def SCHEMES = "http";
    
    // member variables
    def description
    def apiVersion
    def title
    def terms
    def contacts
    def apiLicense
    def host
    def basePath
    def tags
    def paths
    
    
    
    
}
/*
 * dipforge: Description
 * Copyright (C) Tue Mar 24 14:01:33 SAST 2015 owner 
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
 * HttpRequestUtil.groovy
 * @author admin
 */

package com.dipforge.utils


// imports
import groovy.json.JsonSlurper;
import org.apache.log4j.Logger;

/**
 * This class supplies a set of utils for managing the request.
 */
class HttpRequestUtil {
    
    static def log = Logger.getLogger("com.dipforge.log.com.dipforge.utils.HttpRequestUtil");
    
    /**
     * This method is called to process the request and convert the string to json
     */
    static def requestContentToJson(def request) {
        def inputStream = request.getInputStream()
        byte[] buffer = new byte[1024]
        def stringBuilder = new java.lang.StringBuilder()
        int length = 0
        while((length= inputStream.readLine(buffer,0,1024)) != -1) {
            stringBuilder.append(new String(buffer,0,length));
        }
        def jsonSlurper = new JsonSlurper()
        log.info("The content " + stringBuilder.toString())
        return jsonSlurper.parseText(stringBuilder.toString())
        
    }
    
    
    /**
     * This method is called to process the request and convert the string to json
     */
    static def requestContentToString(def request) {
        def inputStream = request.getInputStream()
        byte[] buffer = new byte[1024]
        def stringBuilder = new java.lang.StringBuilder()
        int length = 0
        while((length= inputStream.readLine(buffer,0,1024)) != -1) {
            stringBuilder.append(new String(buffer,0,length));
        }
        return stringBuilder.toString();

    }


    /**
     * This method is called to process the request and convert the string to json
     */
    static def requestContentFromStringToJson(def request) {
        def jsonSlurper = new JsonSlurper()
        return jsonSlurper.parseText(request)

    }

}
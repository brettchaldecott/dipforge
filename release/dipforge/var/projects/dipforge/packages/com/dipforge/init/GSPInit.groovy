/*
 * dipforge: Description
 * Copyright (C) Thu May 17 10:23:29 SAST 2012 owner 
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
 * GSPInit.groovy
 * @author admin
 */

package com.dipforge.init

import org.apache.log4j.Logger;


class GSPInit {
    
    static def log = Logger.getLogger("com.dipforge.init.GSPInit");
    
    /**
     * This method is called to init the parameters
     */
    static def initParams(def request) {
        
        def groovyResult = request.getAttribute("GROOVY_RESULT") 
        if (groovyResult == null) {
            return [:]
        }
        if (!(groovyResult instanceof java.util.Map)) {
            return [result: groovyResult]
        }
        groovyResult = GSPInit.reprocessList(groovyResult)
        request.setAttribute("GROOVY_RESULT",groovyResult)
        return groovyResult
    }
    
    /**
     * This method is called to reprosess the list
     */
    static def reprocessList(def groovyResult) {
        log.info("Call reprocess list")
        if (groovyResult instanceof java.util.List) {
            def result = []
            groovyResult.each{value ->
                log.info("Loop entry is [" + value.getClass().getName() + "]");
                if (value.getClass().getName().equals("groovy.util.Expando")) {
                    result.add(GSPInit.reprocessExpando(value))
                } else if ((value instanceof java.util.List) ||
                    (value instanceof java.util.Map)) {
                    result.add(GSPInit.reprocessList(value))
                } else {
                    result.add(value)
                }
            }
            log.info("Reprocessed the list [" + groovyResult.getClass().getName() + "]");
            return result
        } else if (groovyResult instanceof java.util.Map){
            def result = [:]
            groovyResult.each{key,value ->
                if (value.getClass().getName().equals("groovy.util.Expando")) {
                    result.put(key,GSPInit.reprocessExpando(value))
                } else if ((value instanceof java.util.List) ||
                    (value instanceof java.util.Map)) {
                    result.put(key,GSPInit.reprocessList(value))    
                } else {
                    result.put(key,value)
                }
            }
            log.info("Reprocessed the map [" + groovyResult.getClass().getName() + "]")
            return result;
        }
        log.info("No reprocessing performed")
        return groovyResult
    }
    
    
    /**
     * This method is called to resproces an expando object.
     */
    static def reprocessExpando(def expandoValue) {
        log.info("Reprocess the expando")
        def result = new Expando()
        expandoValue.getProperties().each {key,value ->
            if (!key.matches("(set|get)[A-Z][A-Za-z0-9-]*")) {
                def upperPropertyName = key.substring(0,1).toUpperCase() + 
                    key.substring(1)
                result."${key}" = value
                
                // add the getter and the setter
                result."get${upperPropertyName}" = {->
                    return result."${key}"
                }
                
                // add the getter and the setter
                result."set${upperPropertyName}" = {_local_variable->
                    result."${key}" = _local_variable
                }
            }
        }
        return result
    }
}


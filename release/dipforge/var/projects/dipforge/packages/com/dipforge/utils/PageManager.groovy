/*
 * dipforge: the common library.
 * Copyright (C) Sat Apr 21 08:02:49 SAST 2012 owner 
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
 * PageManager.groovy
 */

package com.dipforge.utils

import org.apache.log4j.Logger;


/**
 * The page manager object
 * @author Brett Chaldecott
 */
class PageManager {
    // private member variables    
    static def log = Logger.getLogger("com.dipforge.utils.PageManager");
    
    
    
    static def forward(page, req, res){
        log.debug("Get the request dispatcher : " + new java.util.Date().getTime());
        def dis = req.getRequestDispatcher(page);
        log.debug("Forward to the dispatcher : " + new java.util.Date().getTime());
        dis.include(req, res);
        log.debug("After forwarding : " + new java.util.Date().getTime());
    }
    
    
    static def forwardWithResult(page, req, res, results){
        def dis = req.getRequestDispatcher(page);
        if (req.getAttribute("GROOVY_RESULT") != null) {
            def tempResult = req.getAttribute("GROOVY_RESULT")
            if (tempResult instanceof java.util.Map) {
                tempResult.putAll(results)
                req.setAttribute("GROOVY_RESULT",tempResult);
            } else {
                req.setAttribute("GROOVY_RESULT",results);
            }
        } else {
            req.setAttribute("GROOVY_RESULT",results);
        }
        dis.include(req, res);
    }
    
    
    static def include(page, req, res) {
        def dis = req.getRequestDispatcher(page);
        dis.include(req, res);
    }
    
    
    static def includeWithResult(page, req, res, results){
        def dis = req.getRequestDispatcher(page);
        if (req.getAttribute("GROOVY_RESULT") != null) {
            def tempResult = req.getAttribute("GROOVY_RESULT")
            if (tempResult instanceof java.util.Map) {
                tempResult.putAll(results)
                req.setAttribute("GROOVY_RESULT",tempResult);
            } else {
                req.setAttribute("GROOVY_RESULT",results);
            }
        } else {
            req.setAttribute("GROOVY_RESULT",results);
        }
        dis.include(req, res);
    }
}

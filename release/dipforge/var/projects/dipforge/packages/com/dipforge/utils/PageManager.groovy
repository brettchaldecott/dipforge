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
        log.info("Get the request dispatcher : " + new java.util.Date().getTime());
        def dis = req.getRequestDispatcher(page);
        log.info("Forward to the dispatcher : " + new java.util.Date().getTime());
        dis.forward(req, res);
        log.info("After forwarding : " + new java.util.Date().getTime());
    }
    
    
    static def forwardWithResult(page, req, res, results){
        def dis = req.getRequestDispatcher(page);
        req.setAttribute("GROOVY_RESULT",results);
        dis.forward(req, res);
    }
}

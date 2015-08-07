/*
 * test: Description
 * Copyright (C) Wed May 02 11:34:13 SAST 2012 owner 
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
 * testrequest.groovy
 * @author brett chaldecott
 */


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;



def log = Logger.getLogger("com.dipforge.log.test.index.groovy");

try {

    def test2 = RDF.create("http://dipforge.sourceforge.net/test2#type2")
    log.info("Set the values on : " + test2)
    test2.setId(RandomGuid.getInstance().getGuid())
    test2.setProperty3(params.name)
    test2.setProperty4(params.description)
    test2.setProperty5(null)
    
    RequestHandler.getInstance("test", "test2", test2).makeRequest()
    
    def test1 = RDF.create("http://dipforge.sourceforge.net/test1#type1")
    log.info("Set the values on : " + test1)
    test1.setId(RandomGuid.getInstance().getGuid())
    test1.setProperty1(params.name)
    test1.setProperty2(params.description)
    test1.setProperty3(test2)
    
    log.info("Init the request : " + test1)
    RequestHandler.getInstance("test", "test1", test1).makeRequest()
    
    
    PageManager.forward("index.gsp", request, response)
} catch (Exception ex) {
    log.error("Failed to make request : ${ex.getMessage()}",ex)
    throw ex
}


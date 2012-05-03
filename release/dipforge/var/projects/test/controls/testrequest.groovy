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


def log = Logger.getLogger("test.index.groovy");

def test1 = RDF.create("http://dipforge.sourceforge.net/test1#type1")

test1.setId(RandomGuid.getInstance().getGuid())
test1.setProperty1(params.name)
test1.setProperty2(params.name)

RequestHandler.getInstance("test", "test1", test1).makeRequest()


PageManager.forward("index.gsp", request, response)


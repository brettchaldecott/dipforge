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
 * index.groovy
 * @author brett chaldecott
 */

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import java.util.Date

def log = Logger.getLogger("test.index.groovy");
def startDate = new Date()

def test1 = RDF.create("http://dipforge.sourceforge.net/test1#type1")
def test01Date = new Date()

test1.setId(RandomGuid.getInstance().getGuid())
test1.setProperty1("fred")
test1.setProperty2("bob")
log.debug("##########################################")
log.debug("ID value : " +test1.getId())
log.debug("Property 1 value : " +test1.getProperty1())
log.debug("Property 2 value : " +test1.getProperty2())
log.debug("XML [" +test1.toXML())
log.debug("##########################################")

def test1Date = new Date()
def test2 = RDF.create("http://dipforge.sourceforge.net/test2#type2")
def test11Date = new Date()
test2.setId(RandomGuid.getInstance().getGuid())
test2.setProperty3("fred")
test2.setProperty4("bob")
test2.getProperty5().setProperty1("man")
log.debug("##########################################")
log.debug("ID value : " +test2.getId())
log.debug("Property 1 value : " +test2.getProperty3())
log.debug("Property 2 value : " +test2.getProperty4())
log.debug("Property 2 value : " +test2.getProperty5().getProperty1())
log.debug("XML [" +test2.toXML())
log.debug("##########################################")

def test2Date = new Date()
log.debug("################# retrieve from xml")
def test3 = RDF.getFromXML(test1.toXML(),"http://dipforge.sourceforge.net/test1#type1/" + test1.getId())
log.debug("################# after retrieving from xml")
def test22Date = new Date()
log.debug("##########################################")
log.debug("ID value : " +test3.getId())
log.debug("Property 1 value : " +test3.getProperty1())
log.debug("Property 2 value : " +test3.getProperty2())
log.debug("XML [" +test3.toXML())
log.debug("##########################################")

def test3Date = new Date()
log.debug("################# retrieve from xml")
def test4 = RDF.getFromXML(test2.toXML(),"http://dipforge.sourceforge.net/test2#type2/" + test2.getId())
log.debug("################# after retrieving from xml")

log.debug("##########################################")
log.debug("ID value : " +test4.getId())
log.debug("Property 1 value : " +test4.getProperty3())
log.debug("Property 2 value : " +test4.getProperty4())
log.debug("Property 2 value : " +test4.getProperty5().getProperty1())
log.debug("XML [" +test4.toXML())
log.debug("##########################################")

def results = RDF.queryXML(test2.toXML(),
    "SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/test2#type2> . }")

for (result in results) {
    test4 = result[0]
    log.debug("##########################################")
    log.debug("ID value : " +test4.getId())
    log.debug("Property 1 value : " +test4.getProperty3())
    log.debug("Property 2 value : " +test4.getProperty4())
    log.debug("Property 2 value : " +test4.getProperty5().getProperty1())
    log.debug("XML [" +test4.toXML())
    log.debug("##########################################")
}

def test4Date = new Date()

PageManager.forward("index.gsp", request, response)

def test5Date = new Date()
def endDate = new Date()
log.info("###################### Complete time is [" + (endDate.getTime() - startDate.getTime()) + "]")
log.info("###################### Test 0.1 time is [" + (endDate.getTime() - test01Date.getTime()) + "]")
log.info("###################### Test 1 time is [" + (endDate.getTime() - test1Date.getTime()) + "]")
log.info("###################### Test 1.1 time is [" + (endDate.getTime() - test11Date.getTime()) + "]")
log.info("###################### Test 2 time is [" + (endDate.getTime() - test2Date.getTime()) + "]")
log.info("###################### Test 2.2 time is [" + (endDate.getTime() - test22Date.getTime()) + "]")
log.info("###################### Test 3 time is [" + (endDate.getTime() - test3Date.getTime()) + "]")
log.info("###################### Test 4 time is [" + (endDate.getTime() - test4Date.getTime()) + "]")
log.info("###################### Test 5 time is [" + (endDate.getTime() - test5Date.getTime()) + "]")

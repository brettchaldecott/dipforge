/*
 * test: Description
 * Copyright (C) Wed May 16 06:14:20 SAST 2012 owner 
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
 * list.groovy
 * @author brett chaldecott
 */

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("test.index.groovy");

def result = RDF.executeQuery("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/test1#type1> . }")

/*
<property name="id" datatype="http://www.w3.org/2001/XMLSchema#string"/>
        <property name="property1" datatype="http://www.w3.org/2001/XMLSchema#string"/>
        <property name="property2" datatype="http://www.w3.org/2001/XMLSchema#string"/>
        <property name="propertyInt" datatype="http://www.w3.org/2001/XMLSchema#integer"/>
        <property name="propertyDecimal" datatype="http://www.w3.org/2001/XMLSchema#decimal"/>
*/


def result2 = RDF.executeQuery("""SELECT ?property1 ?property2 ?propertyInt ?propertyDecimal WHERE {
    ?s <http://dipforge.sourceforge.net/test1#property1> ?property1 .
    ?s <http://dipforge.sourceforge.net/test1#property2> ?property2 . 
    ?s <http://dipforge.sourceforge.net/test1#propertyInt> ?propertyInt . 
    ?s <http://dipforge.sourceforge.net/test1#propertyDecimal> ?propertyDecimal . 
}""")

result2.each { row2 ->
    log.info("Property1 ${row2.property1} Property2 ${row2.property2} PropertyInt ${row2.propertyInt} PropertyDecimal ${row2.propertyDecimal} ")
    
}


def result3 = RDF.executeMapQuery("""SELECT ?property1 ?property2 ?propertyInt ?propertyDecimal WHERE {
    ?s <http://dipforge.sourceforge.net/test1#property1> ?property1 .
    ?s <http://dipforge.sourceforge.net/test1#property2> ?property2 . 
    ?s <http://dipforge.sourceforge.net/test1#propertyInt> ?propertyInt . 
    ?s <http://dipforge.sourceforge.net/test1#propertyDecimal> ?propertyDecimal . 
}""")

result3.each { row3 ->
    log.info("Property1 ${row3.property1} Property2 ${row3.property2} PropertyInt ${row3.propertyInt} PropertyDecimal ${row3.propertyDecimal} ")
    
}

PageManager.includeWithResult("list.gsp", request, response, ["tests" : result])
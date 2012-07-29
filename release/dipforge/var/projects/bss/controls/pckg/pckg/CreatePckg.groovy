/*
 * bss: Description
 * Copyright (C) Thu Jun 28 20:19:31 SAST 2012 owner 
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
 * CreatePckg.groovy
 * @author brett chaldecott
 */

package pckg.pckg


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("pckg.pckg.CreatePckg");

log.info("Parameters : " + params)

// perform a check for a duplicate
def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#id> ?id . "+
    "FILTER (?id = \"${params.pckgId}\")}")
if (result.size() == 0) {
    log.info("Create a new instance of the pckg")
    def pckg = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg")
    
    log.info("Set the values")
    pckg.setId(params.pckgId)
    pckg.setName(params.pckgName)
    pckg.setDescription(params.pckgDescription)
    pckg.setThumbnail(params.thumbnail)
    pckg.setIcon(params.icon)
    if (pckg.pckgTarget != null && pckg.pckgTarget != "" && pckg.pckgTarget != "null") {
        def target = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product/${params.pckgTarget}")
        if (target == null) {
            print "Fail: the target ${params.pckgTarget} does not exist"
            return;
        }
        pckg.setTarget(target)
    }
    if (pckg.pckgPckgTarget != null && pckg.pckgPckgTarget != "" && pckg.pckgPckgTarget != "null") {
        def pckgTarget = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Pckg#Pckg/${params.pckgPckgTarget}")
        if (target == null) {
            print "Fail: the package target ${params.pckgPckgTarget} does not exist"
            return;
        }
        pckg.setPckgTarget(pckgTarget)
    }
    
    def products = []
    params.pckgProducts?.each { productId ->
        def product = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product/${productId}")
        product.getConfigurationManager()?.each{ config ->
            if (config.getName() == "Groovy") {
                def configObject = this.class.classLoader.loadClass( config.getUrl(), true, false )?.newInstance()
                def pckgConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfig#ProductConfig")
                pckgConfig.setId(productId + ":" + params.pckgId)
                pckgConfig.setProduct(product)
                pckgConfig.setData(configObject."generateData"(params))
                products.add(pckgConfig)
                return
            }
        }
    }
    pckg.setProducts(products)
    
    log.info("##### Init the request : " + pckg.toXML())
    RequestHandler.getInstance("bss", "CreatePckg", pckg).makeRequest()
    print "success"
} else {
    print "Fail: Attempting to add a duplicate pckg identified by ID."
}


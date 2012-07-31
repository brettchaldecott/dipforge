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
 * CreateProduct.groovy
 * @author brett chaldecott
 */

package pckg.product


import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("com.dipforge.log.pckg.product.CreateProduct");

log.debug("Parameters : " + params)

// perform a check for a duplicate
def result = RDF.query("SELECT ?s WHERE {" +
    "?s a <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product> . " +
    "?s <http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#id> ?id . "+
    "FILTER (?id = \"${params.productId}\")}")
if (result.size() == 0) {
    log.debug("Create a new instance of the product")
    def product = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product")
    
    def category = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Category#Category/${params.productCategory}")
    def vendor = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Vendor#Vendor/${params.productVendor}")
    
    log.debug("Set the values")
    product.setId(params.productId)
    product.setName(params.productName)
    product.setDescription(params.productDescription)
    product.setDataType(params.productDataType)
    
    // setup the configuration
    def configuration = []
    if (params.productJavascriptConfigUrl != null) {
        def productConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
        productConfig.setId("Javascript:" + params.productId)
        productConfig.setName("Javascript")
        productConfig.setUrl(params.productJavascriptConfigUrl)
        configuration.add(productConfig)
    }
    
    if (params.productGroovyConfigUrl != null) {
        def productConfig = RDF.create("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/ProductConfigManager#ProductConfigManager")
        productConfig.setId("Groovy:" + params.productId)
        productConfig.setName("Groovy")
        productConfig.setUrl(params.productGroovyConfigUrl)
        configuration.add(productConfig)
    }
    product.setConfigurationManager(configuration)
    
    product.setCategory(category)
    product.setVendor(vendor)
    if (params.productDependency != null && params.productDependency != "") {
        def dependency = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Product#Product/${params.productDependency}")
        product.setDependency(dependency)
    }
    product.setThumbnail(params.thumbnail)
    product.setIcon(params.icon)
    
    log.debug("##### Init the request : " + product.toXML())
    RequestHandler.getInstance("bss", "CreateProduct", product).makeRequest()
    print "success"
} else {
    print "Fail: Attempting to add a duplicate product identified by ID."
}


/*
 * bss: Description
 * Copyright (C) Wed Jan 02 06:11:42 SAST 2013 owner 
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
 * BuyBasket.groovy
 * @author brett chaldecott
 */

package shopping

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import com.dipforge.offering.OfferingUtil;
import org.apache.log4j.Logger;
import com.rift.coad.lib.common.RandomGuid;
import com.dipforge.request.RequestHandler;


def log = Logger.getLogger("com.dipforge.log.shopping.BuyBasket");

def cartInfo = session.getAttribute("shopping-cart")

if (cartInfo != null) {
    for (element in cartInfo) {
        def offering = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering/${element.value.offeringId}")
        offering.getPckg().getProducts().each { productConfig ->
            productConfig.getProduct().getConfigurationManager().each { config ->
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
        
    }
}

response.sendRedirect("../finish.gsp")






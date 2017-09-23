/*
 * bss: Description
 * Copyright (C) Fri Dec 28 08:57:05 SAST 2012 owner 
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
 * ShoppingList.groovy
 * @author brett chaldecott
 */

package shopping

import com.dipforge.utils.PageManager;
import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("com.dipforge.log.shopping.ShoppingList");

log.info("###### shopping cart : " + session.getAttribute("shopping-cart"))
def cartInfo = session.getAttribute("shopping-cart")

if (cartInfo != null) {
    def mapOfferings = [:]
    for (element in cartInfo) {
        def offering = null
        if (mapOfferings.containsKey(element.value.offeringId)) {
            offering = mapOfferings.get(element.value.offeringId)
        } else {
            offering = RDF.getFromStore("http://dipforge.sourceforge.net/schema/rdf/1.0/bss/Offering#Offering/${element.value.offeringId}")
            RDF.deapCopy(offering, ["pckg.products.product"])
            mapOfferings.put(element.value.offeringId,offering)
        }
        element.value.put("offering",offering)
    }
} else {
    cartInfo = [:]
}

PageManager.includeWithResult("shopping-list.gsp", request, response, ["cartInfo":cartInfo])


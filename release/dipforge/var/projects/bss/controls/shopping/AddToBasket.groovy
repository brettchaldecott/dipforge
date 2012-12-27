/*
 * bss: Description
 * Copyright (C) Fri Dec 07 05:47:29 SAST 2012 rift it 
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
 * AddToBasket.groovy
 * @author brett chaldecott
 */

package shopping

import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("com.dipforge.log.shopping.List");

log.info("########################################### From the add to basket : " + params)

def shoppingCart = [:]
if (session.getAttribute("shopping-cart") != null) {
    shoppingCart = session.getAttribute("shopping-cart")
}
log.info("####################################### shopping cart : "+ shoppingCart)

shoppingCart[params.installedOfferingId] = params
session.setAttribute("shopping-cart",shoppingCart)

log.info("####################################### shopping cart : "+ shoppingCart)
if (session.getAttribute("shopping-cart") != null) {
    shoppingCart = session.getAttribute("shopping-cart")
    log.info("####################################### shopping cart : "+ shoppingCart)
}
if (session.isNew()) {
    log.info("####################################### Session not initialized properly")
}
log.info("####################################### session id : "+ session.getId())

print "success"
/*
 * bss: Description
 * Copyright (C) Mon Dec 31 06:13:05 SAST 2012 owner 
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
 * RemoveFromBasket.groovy
 * @author brett chaldecott
 */

package shopping

import com.dipforge.semantic.RDF;
import org.apache.log4j.Logger;


def log = Logger.getLogger("com.dipforge.log.shopping.RemoveFromBasket");

log.info("Params : " + params)

if (session.getAttribute("shopping-cart") != null) {
    def shoppingCart = session.getAttribute("shopping-cart")
    log.info("Shopping cart : " + shoppingCart)
    shoppingCart.remove(params.offeringId)
    session.setAttribute("shopping-cart",shoppingCart)
}

print "success"


/*
 * bss: Description
 * Copyright (C) Wed Sep 05 06:14:45 SAST 2012 owner 
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
 * OfferingUtil.groovy
 * @author brett chaldecott
 */

package com.dipforge.offering


/**
 * This util wraps an manages an offering.
 */
public class OfferingUtil {
    
    def offering
    
    /**
     * The constructor of the offering util
     * 
     * @param offering The offering
     */
    public OfferingUtil(def offering) {
        this.offering = offering;
    }
    
    
    /**
     * This method gets the product config for the offering
     * 
     * @return The reference to the product configuration
     * @param productId The id of the product to retrieve.
     */
    public def getProductConfig(def productId) {
        // check if there are any products attached to the package offering.
        def products = offering.getPckg().getProducts();
        if (products == null) {
            return null;
        }
        if (products instanceof java.util.List) {
            // loop through the list of product configs and find the
            // appropriate one by matching the mapping product/
            def result = null;
            products.each { productConfig->
                def product = productConfig.getProduct()
                if (product.getId() == productId) {
                    result = productConfig
                    return;
                }
            }
            return result
        } else {
            // assume a single entry and that it will not be a list
            // retrieve the product from the config and check constants
            def product = products.getProduct()
            if (product.getId() == productId) {
                return products;
            }
        }
        return null;
    }
    
}



/*
 * bss: Description
 * Copyright (C) Thu Jul 26 06:20:49 SAST 2012 owner 
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
 * Config.groovy
 * @author admin
 */

package products.web


/**
 * The configuration object
 */
class Config {
    
    /**
     * This method generates the data
     */
    def generateData(params) {
        return params.webQuota;
    }
    
    
    /**
     * This method is responsible for populating the data type.
     * 
     * @param dataType The data type to populate
     * @param data The data values
     */
    def populateDataType(dataType,data,parentDataType) {
        parentProducts.each { parentDataType ->
            if (parentDataType._getUri().contains("Domain")) {
                dataType.setDomain(parentDataType)
            }
        }
        dataType.setName("www")
        dataType.setCreated(new java.util.Date())
        dataType.setModified(new java.util.Date())
        return dataType;
    }
}

/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2010  Rift IT Contracting
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
 * IDGenerator.java
 */

// package path
package com.rift.coad.rdf.objmapping.util.client.id;

import java.util.Date;

/**
 * This object represents an id value.
 *
 * @author brett chaldecott
 */
public class IDGenerator {
    
    /**
     * The current id holder
     */
    public static class IdHolder {
        // private member variables
        private long date;
        private long sequence;


        /**
         * The constructor of the holder.
         */
        public IdHolder() {
            date = new Date().getTime();
            sequence = 0;
        }


        /**
         * Generate a new id.
         *
         * @return The newly generated id.
         */
        public String getId() {
            return "" + date + "-" + (++sequence);
        }

    }

    // singleton
    private static IdHolder singleton;

    /**
     * This method returns the id for the generator.
     * 
     * @return The string containing the ID.
     */
    public static String getId() {
        if (singleton == null) {
            singleton = new IdHolder();
        }
        return singleton.getId();
    }
}

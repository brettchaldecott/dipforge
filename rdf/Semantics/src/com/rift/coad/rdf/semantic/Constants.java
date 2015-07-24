/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2009  2015 Burntjam
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
 * Constants.java
 */

package com.rift.coad.rdf.semantic;

/**
 * This class defines all the constants
 *
 * @author brett chaldecott
 */
public class Constants {
    /**
     * The default url of the type id used by the semantic library
     */
    //public final static String TYPE_URL = "http://www.coadunation.net/schema/rdf/1.0/semantic#TypeId";

    /**
     * This constant defines the format mask that will be used to generate an
     * parse dates placed in the RDF store.
     */
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SS Z";


    /**
     * This is the format of the id uri.
     */
    public final static String RESOURCE_URI_FORMAT = "%s#%s/%s";
    
}

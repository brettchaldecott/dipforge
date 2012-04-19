/*
 * CoaduntionSemantics: The semantic library for coadunation os
 * Copyright (C) 2012  Rift IT Contracting
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
 * JenaEscaperFactory.java
 */

// package path
package com.rift.coad.rdf.semantic.persistance.jena;

// java imports
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This object is responsible for managing the escaper objects
 * 
 * @author brett chaldecott
 */
public class JenaEscaperFactory {
    
    // class singleton
    private static JenaEscaperFactory singleton = null;
    
    // private member variables
    private Map<Object,JenaEscaper> escapers = new ConcurrentHashMap<Object,JenaEscaper>();
    
    /**
     * The default constructor
     */
    public JenaEscaperFactory() {
    }
    
    
    /**
     * This method returns the reference to the jena factory escaper.
     * 
     * @return The reference to the jena factory escaper
     */
    public synchronized static JenaEscaperFactory getInstance() {
        if (singleton == null) {
            singleton = new JenaEscaperFactory();
        }
        return singleton;
    }
    
    
    /**
     * This method returns the escaper object reference.
     * @param key The key to identify the object by.
     * @return reference to the object.
     */
    public JenaEscaper getEscaper(Object key) {
        if (escapers.containsKey(key)) {
            return escapers.get(key);
        }
        return new BasicJenaEscaper();
    }
    
    
    /**
     * This method adds a new escaper to the map of escapers.
     * 
     * @param key The key to identify the escaper by
     * @param escaper The escaper value.
     */
    public void setEscaper(Object key, JenaEscaper escaper) {
        this.escapers.put(key, escaper);
    }
}

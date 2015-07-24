/*
 * Email Server: The email server.
 * Copyright (C) 2008  2015 Burntjam
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
 * RouteEntry.java
 */

// package path
package com.rift.coad.daemon.email.server.smtp.route;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

// coadunation imports
import com.rift.coad.daemon.email.smtp.Header;
import com.rift.coad.daemon.email.smtp.MessageInfo;

/**
 * This object is responsible for representing a route entry.
 *
 * @author brett chaldecott
 */
public class RouteEntry {
    
    // private member variables
    private String name = null;
    private String jndi = null;
    private List entries = new ArrayList();
    private Set types = new HashSet();
    private List headers = new ArrayList();
    
    
    /**
     * Creates a new instance of RouteEntry
     */
    public RouteEntry(String name, String jndi) {
        this.name = name;
        this.jndi = jndi;
    }
    
    
    /**
     * This method returns the name of the route entry.
     *
     * @return The name of the route entry.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method returns the JNDI reference.
     *
     * @return The string containing the JNDI reference.
     */
    public String getJNDI() {
        return jndi;
    }
    
    
    /**
     * This method addes a new header to the list of headers.
     *
     * @param key The key for the header.
     * @param value The value for the header.
     */
    public void addHeader(String key, String value) {
        headers.add(new Header(key,value));
    }
    
    
    /**
     * This method sets the list of headers.
     *
     * @param The list of headers to set.
     */
    public void setHeaders(List headers) {
        this.headers = headers;
    }
    
    /**
     * This method adds the type to the list of types.
     *
     * @param type The type value.
     */
    public void addType(Integer type) {
        types.add(type);
    }
    
    
    /**
     * This method sets the types.
     *
     * @param types The list of types.
     */
    public void setTypes(Set types) {
        this.types = types;
    }
    
    /**
     * This method returns true if the message should be handled by this entry.
     */
    public boolean checkEntry(MessageInfo info) {
        // check if this object handles this type of entry.
        if ((types.size() != 0) && 
                !types.contains(new Integer(info.getType()))) {
            return false;
        }
        
        // check the headers.
        List infoHeaders = info.getHeaders();
        boolean found = true;
        for (Iterator iter = headers.iterator(); iter.hasNext() && found; ) {
            found = false;
            Header testHeader = (Header)iter.next();
            for (Iterator infoIter = infoHeaders.iterator(); 
            infoIter.hasNext();) {
                Header currentHeader = (Header)infoIter.next();
                if (currentHeader.getKey().equals(testHeader.getKey()) &&
                        currentHeader.getValue().matches(testHeader.getValue())) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }
    
    
    /**
     * This method adds a child entry.
     */
    public void addEntry(RouteEntry entry) {
        entries.add(entry);
    }
    
    
    /**
     * This method test the entries to see if this object can be handled.
     *
     * @return The route entry that matches the message information.
     * @param info The information to match
     */
    public RouteEntry getEntry(MessageInfo info) {
        for (Iterator iter = entries.iterator(); iter.hasNext();) {
            RouteEntry entry = (RouteEntry)iter.next();
            if (entry.checkEntry(info)) {
                return entry;
            }
        }
        return null;
    }
}

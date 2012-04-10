/*
 * CoadunationLib: The coaduntion implementation library.
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
 * ThreadPermissionSession.java
 *
 * This object is responsible for generating a random guid. It is a copy of a
 * class found at http://javaexchange.com/aboutRandomGUID.html. With some 
 * modifications to support Coadunation better.
 */

// the package path
package com.rift.coad.lib.common;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * The id generator class using a bunch of keys to generate a composite keys
 * 
 * @author brett chaldecott
 */
public class IDGenerator {
    
    // private member variables
    private List<String> keys = new ArrayList<String>();
    
    /**
     * private desctructor
     */
    private IDGenerator() {
        
    }
    
    
    /**
     * This method retrieves the id generator.
     * 
     * @return The id generator
     */
    public static IDGenerator init() {
        return new IDGenerator();
    }
    
    
    /**
     * This method adds a new key
     * 
     * @param key The key to add
     */
    public void addKey(String key) {
        this.keys.add(key);
    }
    
    
    /**
     * This method returns the id of this object.
     * 
     * @return The string id
     * @exception CommonException
     */
    public String id() throws CommonException {
        try {
            StringBuffer sbValueBeforeMD5 = new StringBuffer();
            for (String key: this.keys) {
                sbValueBeforeMD5.append(key).append(":");
            }
            MessageDigest md5 = null;
            md5 = MessageDigest.getInstance("MD5");
            
            md5.update(sbValueBeforeMD5.toString().getBytes());
            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new CommonException("Failed to generate the id : " + 
                    ex.getMessage(),ex);
        }
    }
}

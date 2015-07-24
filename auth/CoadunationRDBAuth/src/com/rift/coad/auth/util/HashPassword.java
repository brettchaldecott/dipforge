/*
 * CoadunationRDBAuth: The coadunation RDB authentication library.
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
 * HashPassword.java
 */

// the package path
package com.rift.coad.auth.util;

// java imports
import java.security.MessageDigest;


/**
 * This object is responsible for generating one way hashes for passwords
 * 
 * @author brett chaldecott
 */
public class HashPassword {
    
    /**
     * This method is responsible for generating a sha for the password
     * 
     * @param password The password to generate the sha for.
     * @return The string containing the sha value for the password.
     * @throws UtilException
     */
    public static String generateSha(String password) throws UtilException {
        try {
            // generete the sha from the password
            MessageDigest sha = MessageDigest.getInstance("SHA");
            sha.update(password.getBytes());
            
            // hex encode the value
            byte[] array = sha.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new UtilException("Failed to generate a new sha value : " +
                    ex.getMessage(),ex);
        }
    }
}

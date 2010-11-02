/*
 * CoadunationRDFResources: The rdf resource object mappings.
 * Copyright (C) 2009  Rift IT Contracting
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
 * Country.java
 */

package com.rift.coad.rdf.objmapping.utils;

// java imports
import java.security.SecureRandom;
import java.security.MessageDigest;

// the object exception
import com.rift.coad.rdf.objmapping.exception.ObjException;

// base64 encoding
import com.rift.coad.lib.thirdparty.base64.Base64;

/**
 * This utility class generates a hash from the supplied object.
 *
 * @author Brett
 */
public class HashUtil {
    
    /**
     * This class generates an MD5 from the original source string.
     * 
     * @param source The source string to generate the hash from.
     * @return The string to generate the hash from.
     */
    public static String md5(String source) {
        return hash("md5",source);
    }


    /**
     * This class generates an MD5 from the original source string.
     *
     * @param source The source string to generate the hash from.
     * @return The string to generate the hash from.
     */
    public static String sha(String source) {
        return hash("sha",source);
    }

    /**
     * This class generates the hash using the supplied digest name.
     *
     * @param digestName The name of the digest to generate the sha.
     * @param source The source string to generate the hash from.
     * @return The string to generate the hash from.
     */
    public static String hash(String digestName, String source) {
        try {
            MessageDigest digest = MessageDigest.getInstance(digestName);
            digest.update(source.getBytes());
            return Base64.encodeBytes(digest.digest());
        } catch (Exception ex) {
            throw new java.lang.InternalError("Failed to generate the hash map : " +
                    ex.getMessage());
        }
    }
    
}

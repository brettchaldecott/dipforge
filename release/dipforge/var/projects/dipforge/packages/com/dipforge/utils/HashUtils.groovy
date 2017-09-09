/*
 * dipforge: Description
 * Copyright (C) Sat Sep 09 03:12:54 UTC 2017 owner 
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
 * HashUtils.groovy
 * @author admin
 */

package com.dipforge.utils


class HashUtils {
    
    static def hashString(text) {
        def sha256Hash = java.security.MessageDigest.getInstance("SHA-256")   
            .digest(text.getBytes("UTF-8")).encodeBase64().toString()  
        return sha256Hash
    }
    
    
    static def hashHexString(text) {
        return new BigInteger(1, java.security.MessageDigest.getInstance("SHA-256")   
            .digest(text.getBytes("UTF-8"))).toString(16).padLeft(32, '0').toString()
    }

}


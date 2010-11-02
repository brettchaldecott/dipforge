/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2006  Rift IT Contracting
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

// package
package com.rift.coad.lib.common;

// java
import com.rift.coad.lib.common.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.security.MessageDigest;


/**
 * This object is responsible for generating a random guid. It is a copy of a
 * class found at http://javaexchange.com/aboutRandomGUID.html. With some 
 * modifications to support Coadunation better.
 *
 * @author Marc (copied from http://javaexchange.com/aboutRandomGUID.html)
 */
public class RandomGuid {
    
    // singleton member variables
    private static SecureRandom mySecureRand;
    
    // private member variables
    private String s_id = null;
    public String guid = "";
    
    
    /*
     * Static block to take care of one time secureRandom seed.
     * It takes a few seconds to initialize SecureRandom.  You might
     * want to consider removing this static block or replacing
     * it with a "time since first loaded" seed to reduce this time.
     * This block will run only once per JVM instance.
     */

    static {
        mySecureRand = new SecureRandom();
    }


    /*
     * Default constructor.  With no specification of security option,
     * this constructor defaults to lower security, high performance.
     *
     * @exception Exception
     */
    private RandomGuid() throws Exception {
        try {
            s_id = InetAddress.getLocalHost().toString();
        } catch (UnknownHostException ex) {
            throw new Exception("Failed to retrieve the host information [" 
                    + ex.getMessage(),ex);
        }
        
        // generate the random guid
        generateRandomGUID();
    }
    
    
    /**
     * This method returns a random guid object.
     *
     * @return The reference to the instance of the random guid.
     * @exception Exception
     */
    public static RandomGuid getInstance() throws Exception {
        return new RandomGuid();
    }
    
    
    /*
     * This method generates the random GUID string
     *
     * @exception Exception
     */
    private void generateRandomGUID() throws Exception  {
        try {
            StringBuffer sbValueBeforeMD5 = new StringBuffer();
            // init the md5 hash
            MessageDigest md5 = null;
            md5 = MessageDigest.getInstance("MD5");
            
            
            long time = new java.util.Date().getTime();
            String rand = "";
            
            // generate the random value
            synchronized (mySecureRand) {
                for (int count = 0; count < 20; count++)
                {
                    rand += Long.toString(mySecureRand.nextLong());
                }
            }
            
            // This StringBuffer can be a long as you need; the MD5
            // hash will always return 128 bits.  You can change
            // the seed to include anything you want here.
            // You could even stream a file through the MD5 making
            // the odds of guessing it at least as great as that
            // of guessing the contents of the file!
            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(rand);
            
            // generate the md5 hash value.
            String valueBeforeMD5 = sbValueBeforeMD5.toString();
            md5.update(valueBeforeMD5.getBytes());
            byte[] array = md5.digest();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) sb.append('0');
                sb.append(Integer.toHexString(b));
            }
            
            guid = sb.toString();
        } catch (Exception ex) {
            throw new Exception("Failed to generate the GUID : " + 
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the guid value.
     *
     * @return The string containing the guid value.
     */
    public String getGuid() {
        return guid;
    }
    
    
    /**
     * Retrieve the string value contained within.
     *
     * @return The string value.
     */
    public String toString() {
        return guid;
    }
}

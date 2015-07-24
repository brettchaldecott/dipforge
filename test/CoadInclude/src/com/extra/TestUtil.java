/*
 * CoadunationInclude: A test library.
 * Copyright (C) 2006  2015 Burntjam
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
 * TestUtil.java
 *
 * This is a class used by the coadunation test library.
 */

package com.extra;

/**
 * This is a class used by the coadunation test library.
 *
 * @author Brett Chaldecott
 */
public class TestUtil {
    
    /** Creates a new instance of TestUtil */
    public TestUtil() {
    }
    
    
    /**
     * This prints a message to standard out.
     *
     * @param msg The message to print
     */
    public void printMessage(String msg) {
        System.out.println("From include : " + msg);
    }
}

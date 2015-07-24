/*
 * CoadunationLib: The coaduntion implementation library.
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
 * MimeTypes.java
 *
 * This class defines the MIME encoding types.
 */

package com.rift.coad.lib.httpd;

/**
 * This class defines the MIME encoding types.
 *
 * @author Brett Chaldecott
 */
public class MimeTypes {
    
    /** Plain Text */
    public final static String PLAIN = "text/xml";
    /** XML */
    public final static String XML = "text/xml";
    /** UTF-8 */
    public final static String UTF_8 = "UTF-8";
    /**
     * Private constructor so this class cannot be instanciated.
     */
    private MimeTypes() {
    }
    
}

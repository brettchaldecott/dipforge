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
 * BeanPattern.java
 *
 * This file contains bean pattern information.
 */

package com.rift.coad.lib.bean;

/**
 * This file contains bean pattern information.
 *
 * @author Brett Chaldecott
 */
public class BeanPattern {
    
    public final static String ADD_PATTERN = "add|create";
    public final static String FIND_PATTERN = "find|get";
    public final static String REMOVE_PATTERN = "remove|delete";
    public final static String TIE_SUFFIX = "_CoadTie";
    
    /** Creates a new instance of BeanPattern */
    private BeanPattern() {
    }
    
}

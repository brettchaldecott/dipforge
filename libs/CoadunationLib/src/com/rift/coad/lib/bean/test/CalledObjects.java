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
 * CalledObjects.java
 *
 * This class is used to test calls onto RMI based objects within the same JVM.
 */

package com.rift.coad.lib.bean.test;

/**
 * This class is used to test calls onto RMI based objects within the same JVM.
 *
 * @author Brett Chaldecott
 */
public class CalledObjects {
    
    public static int called = 0;
    public static int releasedCount = 0;
    
    /** Creates a new instance of CalledObjects */
    private CalledObjects() {
    }
    
}

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
 * BeanRunnable.java
 *
 * This interface identifies a bean as being processable. It will have a thread
 * dedicated to its own processing, much like a main method within a tradional
 * program.
 */

package com.rift.coad.lib.bean;

/**
 * This interface identifies a bean as being processable. It will have a thread
 * dedicated to its own processing, much like a main method within a tradional
 * program.
 *
 * @author Brett Chaldecott
 */
public interface BeanRunnable {
    /**
     * This method will be called to perform the processing. This method
     * replaces the traditional run method.
     */
    public void process();
    
    
    /**
     * This method is called to soft terminate the processing thread.
     */
    public void terminate();
    
}

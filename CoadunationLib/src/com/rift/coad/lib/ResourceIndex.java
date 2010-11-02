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
 * ResourceIndex.java
 *
 * This interface is implemented by resources wishing to be indexed.
 */

package com.rift.coad.lib;

/**
 * This interface is implemented by resources wishing to be indexed.
 *
 * @author Brett Chaldecott
 */
public interface ResourceIndex {
    /**
     * This method returns the primary key of this resource to enable indexing.
     *
     * @return The primary key of this object.
     */
    public Object getPrimaryKey();
}

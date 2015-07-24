/*
 * CoadunationLib: The coaduntion implementation library.
 * Copyright (C) 2007 2015 Burntjam
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
 * Change.java
 */

// package path
package com.rift.coad.util.change;

// java imports
import java.io.Serializable;

/**
 * This object represents a change to the change log. It will be called to apply
 * the changes it represents. If it fails to apply the changes it will be called
 * again and again untill the changes are commited. The change log will not 
 * go onto another change entry until all changes are apply to the current entry.
 *
 * @author Brett Chaldecott
 */
public interface Change extends Serializable {
    
    /**
     * The definition of the apply method.
     */
    public void applyChanges() throws ChangeException;
}

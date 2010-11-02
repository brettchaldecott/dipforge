/*
 * CoadunationCRMClient: The CRM client library
 * Copyright (C) 2008  Rift IT Contracting
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
 * CRMResultSet.java
 */


package com.rift.coad.crm.result;

// java imports
import java.util.List;
import java.util.Iterator;

/**
 * The CRM result set object.
 * 
 * @author brett chaldecott
 */
public class CRMResultSet implements java.io.Serializable {
    
    private List<CRMColumnMetaData> meta;
    private List<CRMRecord> entries;
    private Iterator<CRMRecord> currentPos;

    
    public CRMResultSet() {
    }

    
    public CRMResultSet(List<CRMColumnMetaData> meta, List<CRMRecord> entries) {
        this.meta = meta;
        this.entries = entries;
        
    }
    
    
    /**
     * This method resets the current pos pointer.
     */
    public void reset() {
        currentPos = null;
    }
    
    /**
     * The next entry in the result set.
     * 
     * @return The next result in the set.
     */
    public CRMRecord getNext() {
        if (currentPos == null) {
            currentPos = entries.iterator();
        }
        return currentPos.next();
    }
    
    
    /**
     * The size of this result set.
     * 
     * @return The size of this result set.
     */
    public int size() {
        return entries.size();
    }
    
    
    /**
     * This method returns the list of meta data.
     * 
     * @return The list of meta data describing this result set.
     */
    public List<CRMColumnMetaData> getMeta() {
        return meta;
    }
    
    
    /**
     * This method returns the string value of the result set.
     * 
     * @return The string value for this result set.
     */
    @Override
    public String toString() {
        return String.format("Meta %s%nRecords%s%n", meta.toString(), 
                this.entries.toString());
    }
    
    
    
    
}

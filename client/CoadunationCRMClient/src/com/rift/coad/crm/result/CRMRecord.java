/*
 * CoadunationCRMClient: The CRM client library
 * Copyright (C) 2008  2015 Burntjam
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
 * CRMRecord.java
 */

package com.rift.coad.crm.result;

// java imports
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * This object represents a record
 * 
 * @author brett chaldecott
 */
public class CRMRecord implements java.io.Serializable {
    // private member variables
    private List<CRMRecordEntry> recordList;
    private Map<String,CRMRecordEntry> recordMap;

    
    /**
     * The default constructor of the CRM record object.
     */
    public CRMRecord() {
    }

    
    /**
     * This constructor is responsible for setting the values of all internal members.
     * 
     * @param recordList The list of records.
     * @param recordMap The record map.
     */
    public CRMRecord(List<CRMRecordEntry> recordList, Map<String, CRMRecordEntry> recordMap) {
        this.recordList = recordList;
        this.recordMap = recordMap;
    }
    
    
    /**
     * This method returns the number of entries within the record.
     * 
     * @return The number of entries found in the record.
     */
    public int size() {
        return recordList.size();
    }
    
    
    /**
     * This method returns record identified by the index.
     * @return
     */
    public CRMRecordEntry get(int index) {
        return recordList.get(index);
    }
    
    
    /**
     * This method sets the record list.
     * 
     * @param recordList The record list to retrieve.
     */
    public void setRecordList(List<CRMRecordEntry> recordList) {
        this.recordList = recordList;
    }
    
    
    /**
     * The method returns the entry identified by the key.
     * 
     * @param key The key to identify the entry.
     * @return The entry identified by the key.
     */
    public CRMRecordEntry get(String key) {
        return recordMap.get(key);
    }

    
    /**
     * This method sets the record map.
     * 
     * @param recordMap Map of entries for this record.
     */
    public void setRecordMap(Map<String, CRMRecordEntry> recordMap) {
        this.recordMap = recordMap;
    }
    
    
    /**
     * This method adds the entry to the record.
     * 
     * @param entry The entry to add to the record.
     */
    public void add(CRMRecordEntry entry) {
        this.recordList.add(entry);
        this.recordMap.put(entry.getName(), entry);
    }
    
    /***
     * This method returns a string value identifying this record.
     * 
     * @return The internal string value.
     */
    @Override
    public String toString() {
        return this.recordList.toString();
    }
    
    
    
}

/*
 * DNS: The dns server interface
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
 * DNSRecord.java
 */

package com.rift.coad.daemon.dns;

/**
 * This object is responsible for represenging a record in a zone.
 *
 * @author brett chaldecott
 */
public class DNSRecord implements java.io.Serializable {
    
    private String prefix = null;
    private long ttl = 0;
    private String type = null;
    private String suffix = null;
    
    
    
    /**
     * The default constructor of the dns record
     */
    public DNSRecord() {
    }
    
    
    /**
     * The default constructor of the dns record.
     *
     * @param prefix The prefix of the dns record.
     * @param ttl The ttl of the dns record.
     * @param type The type of record to add.
     * @param suffix The suffix of the record.
     */
    public DNSRecord(String prefix, long ttl, String type, String suffix) {
        this.prefix = prefix;
        this.ttl = ttl;
        this.type = type;
        this.suffix = suffix;
    }
    
    
    /**
     * This method returns the prefix of the dns record.
     *
     * @return The string containing the prefix of the dns record.
     */
    public String getPrefix() {
        return prefix;
    }
    
    
    /**
     * This method sets the prefix of the dns record.
     *
     * @param prefix The string containing the prefix of the dns record.
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    
    /**
     * This method returns the ttl of the record.
     *
     * @return The ttl of the record.
     */
    public long getTtl() {
        return ttl;
    }
    
    
    /**
     * This method sets the ttl of the record.
     *
     * @param ttl The ttl of the dns record.
     */
    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
    
    
    /**
     * This method returns the type of the dns record.
     *
     * @return The string containing the type of the dns record.
     */
    public String getType() {
        return type;
    }
    
    
    /**
     * This method returns the type of the dns record.
     *
     * @param type The type of the dns record.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    
    /**
     * This method returns the suffix of the dns record.
     *
     * @return The string containing the suffix of the dns record.
     */
    public String getSuffix() {
        return suffix;
    }
    
    
    /**
     * This method returns the suffix of the dns record.
     *
     * @param suffix The suffix of the dns record
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
    
    /**
     * This method returns the string value of this object.
     *
     * @return The string value of this object.
     */
    public String toString() {
        return prefix + "\t" + ttl + "\t" + type + "\t" + suffix;
    }
}

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
 * SOARecord.java
 */


// package path
package com.rift.coad.daemon.dns;

// java import
import java.io.Serializable;


/**
 * This object represents the SOA of a zone.
 *
 * @author brett
 */
public class SOARecord implements Serializable {
    
    // private member variables
    private String host = null;
    private String admin = null;
    private long ttl = 0;
    private long serial = 0;
    private long refresh = 0;
    private long retry = 0;
    private long expire = 0;
    private long minimum = 0;
    
    /** 
     * Creates a new instance of SOARecord 
     *
     * @param host The host value.
     * @param admin The admin of the zone.
     * @param serial The serial.
     * @param refresh The refresh time.
     * @param retry The retry time.
     * @param expire The expiry time.
     * @param minimum The minimum period to hold data.
     */
    public SOARecord(String host, long ttl, String admin, long serial, 
            long refresh, long retry, long expire, long minimum) {
        this.host = host;
        this.ttl = ttl;
        this.admin = admin;
        this.serial = serial;
        this.refresh = refresh;
        this.retry = retry;
        this.expire = expire;
        this.minimum = minimum;
    }
    
    
    /**
     * This method gets the admin for a zone.
     *
     * @return The string containing the admin name.
     * @throws RemoteException
     */
    public String getHost() {
        return host;
    }
    
    
    /**
     * This method returns the TTL for the zone management.
     *
     * @param ttl The ttl.
     */
    public long getTTL() {
        return ttl;
    }
    
    
    /**
     * This method gets the admin for a zone.
     *
     * @return The string containing the admin name.
     * @throws RemoteException
     */
    public String getAdmin(){
        return admin;
    }
    
    
    /**
     * This method sets the admin of the zone.
     *
     * @param admin The string containing the admin name.
     * @throws DNSException
     * @throws RemoteException
     */
    public void setAdmin(String admin){
        this.admin = admin;
    }
    
    
    /**
     * This method returns the current serial number for the zone.
     *
     * @return The string containing the serial number for the zone.
     * @throws RemoteException
     */
    public long getSerial(){
        return serial;
    }
    
    
    /**
     * This method returns the current serial number for the zone.
     *
     * @return The string containing the serial number for the zone.
     * @throws RemoteException
     */
    public void setSerial(long serial){
        this.serial = serial;
    }
    
    
    /**
     * This method returns the refresh time of the zone management object.
     *
     * @return The long containing the refresh time of the zone.
     * @throws RemoteException
     */
    public long getRefresh() {
        return refresh;
    }
    
    
    /**
     * This method sets the refresh time of the zone management object.
     *
     * @param refresh The new refresh time for the zone.
     * @throws DNSException
     * @throws RemoteException
     */
    public void setRefresh(long refresh) {
        this.refresh = refresh;
    }
    
    
    /**
     * This method returns the retry time of the zone.
     *
     * @return The long containing the retry time of the zone.
     * @throws RemoteException
     */
    public long getRetry() {
        return retry;
    }
    
    
    /**
     * This method sets the retry time of the zone.
     *
     * @param retry The new retry time for the zone.
     * @throws DNSException
     * @throws RemoteException
     */
    public void setRetry(long retry) {
        this.retry = retry;
    }
    
    
    /**
     * This method returns the expiry time of the zone.
     *
     * @return The long containing the expiry time of the zone.
     * @throws RemoteException
     */
    public long getExpiry() {
        return expire;
    }
    
    
    /**
     * This method sets the expiry time of the zone.
     *
     * @param expiry The new expiry time for the zone.
     * @throws DNSException
     * @throws RemoteException
     */
    public void setExpire(long expire) {
        this.expire = expire;
    }
    
            
    /**
     * This method returns the minimun time of the zone.
     *
     * @return The long containing the expiry time of the zone.
     * @throws RemoteException
     */
    public long getMinimum() {
        return minimum;
    }
    
    
    /**
     * This method sets the minimum time of the zone.
     *
     * @param minimum The new minimum time for the zone.
     * @throws DNSException
     * @throws RemoteException
     */
    public void setMinimum(long minimum) {
        this.minimum = minimum;
    }
    

}

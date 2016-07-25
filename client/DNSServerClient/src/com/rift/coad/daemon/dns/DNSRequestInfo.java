/*
 * CoadunationLib: The coadunation libraries.
 * Copyright (C) 2007  2015 Burntjam
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
 * DNSRequestInfo.java
 */

package com.rift.coad.daemon.dns;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import com.googlecode.cqengine.attribute.MultiValueAttribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.option.QueryOptions;

/**
 * This is object contains information about a single dns request
 * 
 * @author brett chaldecott
 */
public class DNSRequestInfo implements Serializable {
    
    private long requestId;
    private String sourceIp;
    private Date time;
    private String query;
    private int type;
    private String response; 

    public DNSRequestInfo() {
    }

    public DNSRequestInfo(long requestId, String sourceIp, Date time, String query, int type, String response) {
        this.requestId = requestId;
        this.sourceIp = sourceIp;
        this.time = time;
        this.query = query;
        this.type = type;
        this.response = response;
    }

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
    
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    // cg query methods
    public static SimpleAttribute<DNSRequestInfo, Long> REQUEST_ID = new SimpleAttribute<DNSRequestInfo, Long>("requestId") {
        public Long getValue(DNSRequestInfo dnsRequestInfo, QueryOptions queryOptions) { return dnsRequestInfo.requestId; }
    };
    
    public static SimpleAttribute<DNSRequestInfo, String> SOURCE_IP = new SimpleAttribute<DNSRequestInfo, String>("sourceIp") {
        public String getValue(DNSRequestInfo dnsRequestInfo, QueryOptions queryOptions) { return dnsRequestInfo.sourceIp; }
    };
    
    public static SimpleAttribute<DNSRequestInfo, Date> TIME = new SimpleAttribute<DNSRequestInfo, Date>("time") {
        public Date getValue(DNSRequestInfo dnsRequestInfo, QueryOptions queryOptions) { return dnsRequestInfo.time; }
    };
    
    public static SimpleAttribute<DNSRequestInfo, String> DNS_QUERY = new SimpleAttribute<DNSRequestInfo, String>("dnsQuery") {
        public String getValue(DNSRequestInfo dnsRequestInfo, QueryOptions queryOptions) { return dnsRequestInfo.query; }
    };
    
    public static SimpleAttribute<DNSRequestInfo, Integer> TYPE = new SimpleAttribute<DNSRequestInfo, Integer>("type") {
        public Integer getValue(DNSRequestInfo dnsRequestInfo, QueryOptions queryOptions) { return dnsRequestInfo.type; }
    };

    @Override
    public String toString() {
        return "DNSRequestInfo{" + "requestId=" + requestId + ", sourceIp=" + sourceIp + ", time=" + time + ", query=" + query + ", type=" + type + ", response=" + response + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (int) (this.requestId ^ (this.requestId >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DNSRequestInfo other = (DNSRequestInfo) obj;
        if (this.requestId != other.requestId) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}

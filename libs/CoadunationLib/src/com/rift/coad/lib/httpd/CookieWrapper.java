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
 * CookieWrapper.java
 *
 * This class is responsible for wrapping the access to the cookie information
 * containied within, and for parsing cookie headers.
 */

// package path
package com.rift.coad.lib.httpd;

// logging import
import org.apache.log4j.Logger;

// java imports
import java.net.InetAddress;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

// coadunation imports
import com.rift.coad.lib.configuration.Configuration;
import com.rift.coad.lib.configuration.ConfigurationFactory;

/**
 * This class is responsible for wrapping the access to the cookie information
 * containied within, and for parsing cookie headers.
 * 
 * @author Brett Chaldecott
 */
public class CookieWrapper {
    
    // class constants
    private final static String PATH = "Path";
    private final static String DOMAIN = "Domain";
    public final static String VERSION = "Version";
    public final static String DOLLAR = "$";
    private final static String VERSION_NUMBER = "1";
    private final static String COOKIE_HOST = "cookie_host";
    
    // private member variables
    private Logger log =
            Logger.getLogger(CookieWrapper.class.getName());
    private String name = null;
    private String value = null;
    private String domain = null;
    private String path = null;
    
    
    /**
     * The constructor of the class responsible for processing the raw source.
     *
     * @param rawSrouce The raw source to process.
     * @exception HttpdException
     */
    public CookieWrapper(String rawSource) 
            throws HttpdException {
        // check for valid formatting of cookie
        if (rawSource.contains("=") == false) {
            throw new HttpdException(
                    "Incorrectly formated cookie [" + rawSource + "]");
        }
        name = rawSource.substring(0,rawSource.indexOf('='))
                .trim().toLowerCase();
        value = stripInvertedCommas(
                rawSource.substring(rawSource.indexOf('=') + 1).trim())
                .trim();
    }
    
    
    /**
     * 
     * Creates a new instance of CookieWrapper 
     * 
     * 
     * @param name The name associated with this cookie.
     * @param value The value wrapped within.
     * @exception HttpdException
     */
    public CookieWrapper(String name, String value) throws HttpdException {
        try {
            Configuration config = ConfigurationFactory.getInstance().
                    getConfig(CookieWrapper.class);
            this.name = name.toLowerCase().trim();
            this.value = value;
        } catch (Exception ex) {
            throw new HttpdException("Failed to init the cookie wrapper : " +
                    ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method returns the name of this cookie.
     *
     * @return The string containing the name of this cookie.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * This method returns the value contained within.
     *
     * @return The string containing the value of the cookie.
     */
    public String getValue() {
        return value;
    }
    
    
    /**
     * This method sets the value of the cookie.
     *
     * @param value The value to store in the cookie.
     */
    public void setValue(String value) {
        this.value = value;
    }
    
    
    /**
     * This method returns the domain name.
     *
     * @return The string containing the domain name.
     */
    public String getDomain() {
        return domain;
    }
    
    
    /**
     * This method sets the domain key value.
     *
     * @param domain The new domain to set.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    
    /**
     * This method returns the path under which the cookie will be available.
     *
     * @return The path the cookie can be used on.
     */
    public String getPath() {
        return path;
    }
    
    
    /**
     * This method sets the path.
     *
     * @param path The path to the object.
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    
    /**
     * This method generates a cookie string that can be used by Set-Cookie.
     *
     * @return A string that can be used as a Set cookie header.
     */
    public String getSetCookieString() {
        StringBuffer cookieBuffer = new StringBuffer();
        cookieBuffer.append(name).append("=").append(value).append(";").
                append(VERSION).append("=").append(VERSION_NUMBER);
        if (domain != null) {
            cookieBuffer.append(";").append(DOMAIN).append("=").append(domain);
        }
        if (path != null) {
            cookieBuffer.append(";").append(PATH).append("=").append(path);
        }
        return cookieBuffer.toString();
    }
    
    
    /**
     * This method strips the inverted commas off a string.
     *
     * @return The stripped string.
     * @param value The string to strip the values off of.
     */
    private String stripInvertedCommas(String value) {
        int beginPos = value.indexOf('"');
        if (beginPos == -1) {
            return value;
        }
        int endPos = value.indexOf('"',beginPos);
        return value.substring(beginPos,endPos);
    }
}

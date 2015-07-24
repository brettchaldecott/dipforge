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
 * HttpCookieManager.java
 *
 * This class is responsible for managing the cookies sent between server and
 * browser and browser and server.
 */

// package path
package com.rift.coad.lib.httpd;

// java imports
import java.util.Map;
import java.util.HashMap;

// logging import
import org.apache.log4j.Logger;

// apache imports
import org.apache.http.HttpServerConnection;
import org.apache.http.protocol.HttpService;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;


/**
 * This class is responsible for managing the cookies sent between server and
 * browser and browser and server.
 *
 * @author Brett Chaldecott
 */
public class HttpRequestCookieManager {
    
    private final static String COOKIE = "Cookie";
    private final static String COOKIE_2 = "Cookie2";
    private final static String SET_COOKIE = "Set-Cookie";
    private final static String SET_COOKIE_2 = "Set-Cookie2";
    
    
    // the classes member variables
    private Logger log =
            Logger.getLogger(HttpRequestCookieManager.class.getName());
    private Map cookies = new HashMap();
    private HttpRequest request = null;
    private HttpResponse response = null;
    
    /**
     * Creates a new instance of HttpCookieManager
     *
     * @param request The object containing the request value.
     * @param response The method that encloses the http response value.
     * @exception HttpdException
     */
    public HttpRequestCookieManager(HttpRequest request, HttpResponse response)
    throws HttpdException {
        this.request = request;
        this.response = response;
        
        // check for basic auth
        if (request.containsHeader(COOKIE)) {
            processHeaders(request.getHeaders(COOKIE));
        }
        if (request.containsHeader(COOKIE_2)) {
            processHeaders(request.getHeaders(COOKIE_2));
        }
        
    }
    
    
    /**
     * This method is responsible for adding a cookie to the response
     *
     * @param cookie The cookie wrapper to add.
     */
    public void addCookie(CookieWrapper cookie) {
        cookies.put(cookie.getName(),cookie);
        log.debug("Set cookie [" + cookie.getSetCookieString() + "]");
        response.addHeader(new BasicHeader(SET_COOKIE,cookie.getSetCookieString()));
    }
    
    
    /**
     * This method returns the cookie reference matching the name.
     *
     * @return The name of the cookie.
     * @param name The name of the cookie to retrieve.
     */
    public CookieWrapper getCookie(String name) {
        return (CookieWrapper)cookies.get(name.trim().toLowerCase());
    }
    
    
    /**
     * This method is responsible for processing the headers passed to it.
     *
     * @param headers The reference to the headers
     * @exception HttpException
     */
    private void processHeaders(Header[] headers) throws HttpdException {
        
        log.debug("There are [" + headers.length + "] cookies");
        for (int index = 0; index < headers.length; index++) {
            Header header = headers[index];
            String value = header.getValue().trim();
            log.debug("Process cookie : " + value);
            if (value.length() == 0) {
                continue;
            }
            CookieWrapper cookie = new CookieWrapper(value);
            cookies.put(cookie.getName(),cookie);
        }
    }
}

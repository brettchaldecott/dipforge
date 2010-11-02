/*
 * CoadunationAdmin: The admin frontend for coadunation.
 * Copyright (C) 2007 - 2008  Rift IT Contracting
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
 * WebServiceManagerImpl.java
 */

package com.rift.coad.web.admin.server;

// java imports
import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Vector;
import java.lang.reflect.Method;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.PrintStream;

// import log4j
import org.apache.log4j.Logger;

// gwt imports
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

// coadunation imports
import com.rift.coad.lib.deployment.webservice.WebServiceConnector;
import com.rift.coad.lib.deployment.webservice.WebServiceManager;
import com.rift.coad.lib.webservice.WebServiceWrapper;
import com.rift.coad.web.admin.client.WebServiceDef;
import com.rift.coad.web.admin.client.WebServiceException;



/**
 * This class implements the web service call for the web service manager.
 *
 * @author brett chaldecott
 */
public class WebServiceManagerImpl extends RemoteServiceServlet implements
        com.rift.coad.web.admin.client.WebServiceManager {
    
    // private member variables
    private static Logger log = Logger.getLogger(WebServiceManagerImpl.class);

    
    /**
     * This method returns the list of web services.
     *
     * @return The list of web services.
     */
    public String[] getServices() throws WebServiceException {
        try {
            Set entries = WebServiceConnector.getInstance().getServices();
            String[] result = new String[entries.size()];
            int index = 0;
            for (Iterator iter = entries.iterator(); iter.hasNext(); index++) {
                result[index] = (String)iter.next();
            }
            Arrays.sort(result);
            return result;
        } catch (Exception ex) {
            log.error("Failed to retrieve the web service list : "
                    + ex.getMessage(),ex);
            throw new WebServiceException("Failed to retrieve the web service " +
                    "list : " + ex.getMessage());
        }

    }
    
    
    /**
     * This method returns a web service definition.
     *
     * @return The web service defintion.
     * @param name The name of the web service.
     */
    public WebServiceDef getWebServiceDef(String name) throws 
            WebServiceException {
        try {
            WebServiceWrapper service = (WebServiceWrapper)
                    WebServiceConnector.getInstance().getService(name);
            WebServiceDef def = new WebServiceDef();
            def.setURL("http://"+ java.net.InetAddress.getLocalHost().
                    getHostName() +":8085" + service.getPath() + "?WSDL");
            def.setWSDL(service.generateWSDL());
            return def;
        } catch (Exception ex) {
            log.error("Failed to retrieve the web service def : "
                    + ex.getMessage(),ex);
            throw new WebServiceException("Failed to retrieve web service def : "
                    + ex.getMessage());
        }
    }
}

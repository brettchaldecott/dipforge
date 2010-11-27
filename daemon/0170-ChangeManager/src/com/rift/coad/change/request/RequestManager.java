/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2009  Rift IT Contracting
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
 * RequestManager.java
 */


package com.rift.coad.change.request;

// java imports

// apache imports
import com.rift.coad.change.rdf.objmapping.change.Request;
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.config.Basic;
import java.rmi.RemoteException;
import java.util.List;
import org.apache.log4j.Logger;

// coadunation import
import com.rift.coad.util.connection.ConnectionManager;



/**
 * The request manager object
 *
 * @author brett chaldecott
 */
public class RequestManager implements RequestManagerMBean {

    // class singleton
    private Logger log = Logger.getLogger(RequestManager.class);

    /**
     * The default constructor
     */
    public RequestManager() throws RequestException {
        
    }
    
    
    /**
     * This method returns the version information for this object.
     *
     * @return The version information for this object.
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the request manager.
     *
     * @return The name of the request manager.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns a description of the request.
     *
     * @return The description of this object.
     */
    public String getDescription() {
        return "The request manager";
    }


    /**
     * This method is responsible for initializing a request using the xml and the
     * id of the request.
     *
     * @param id The id of the request to initialize.
     * @param xml The xml definining the request.
     * @throws com.rift.coad.change.request.RequestException
     */
    public void initRequestFromXML(String id, String xml) throws RequestException {
        try {
            Session session = Basic.initSessionManager().getSession();
            session.persist(xml);
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class, 
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            daemon.createRequest(session.get(Request.class, Request.class.getName(), id));
        } catch (Exception ex) {
            log.error("Failed to init a request : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to init a request : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for returning an XML version of the request.
     *
     * @param id The id of the request
     * @return The string containing the XML definition of the request.
     * @throws com.rift.coad.change.request.RequestException
     */
    public String getRequestAsXML(String id) throws RequestException {
        try {
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class,
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            Session session = Basic.initSessionManager().getSession();
            session.persist(daemon.getRequest(id));
            return session.dump(RDFFormats.XML_ABBREV);
        } catch (Exception ex) {
            log.error("Failed to retrieve a request : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to retrieve a request : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method is responsible for removing the request from the store.
     *
     * @param id The id of the request to remove.
     * @throws com.rift.coad.change.request.RequestException
     */
    public void removeRequest(String id) throws RequestException {
        try {
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class,
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            daemon.removeRequest(id);
        } catch (Exception ex) {
            log.error("Failed to remove a request : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to remove a request : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns a list of the active requests.
     *
     * @return The string containing a list of the active request ids
     * @throws com.rift.coad.change.request.RequestException
     */
    public List<String> listRequests() throws RequestException {
        try {
            RequestFactoryDaemon daemon = (RequestFactoryDaemon)ConnectionManager.getInstance().
                    getConnection(RequestFactoryDaemon.class,
                    "java:comp/env/bean/change/request/RequestFactoryDaemon");
            return daemon.listRequests();
        } catch (Exception ex) {
            log.error("Failed to retrieve a list of requests : " + ex.getMessage(),ex);
            throw new RequestException
                ("Failed to retrieve a list of requests : " + ex.getMessage(),ex);
        }
    }

}

/*
 * RequestBroker: The request broker daemon.
 * Copyright (C) 2010  2015 Burntjam
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
 * RequestBrokerManager.java
 */

package com.rift.coad.request;


// java imports
import com.rift.coad.change.rdf.RequestRDF;
import java.util.List;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.request.rdf.RequestInfoRDF;
import com.rift.coad.util.connection.ConnectionManager;


/**
 * The implementation of the request broker.
 *
 * @author brett chaldecott
 */
public class RequestBrokerManager implements RequestBrokerManagerMBean {

    // class singletons
    private static Logger log = Logger.getLogger(RequestBrokerManager.class);

    /**
     * The constructor responsible for the request broker.
     */
    public RequestBrokerManager() {
    }

    
    /**
     * This method returns the version information for this object.
     * 
     * @return The string containing the version information
     */
    public String getVersion() {
        return "1.0";
    }


    /**
     * This method returns the name of the request broker.
     *
     * @return The string containing the request broker information.
     */
    public String getName() {
        return this.getClass().getName();
    }


    /**
     * This method returns the description information.
     *
     * @return The string containing the description information.
     */
    public String getDescription() {
        return "The request broker information";
    }


    /**
     * This method creates the request from xml.
     *
     * @param xmlRequest The request information.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public void createRequestFromXML(String requestId,String xmlRequest) throws RequestBrokerException {
        try {
            Session session = XMLSemanticUtil.getSession();
            session.persist(xmlRequest);
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            daemon.createRequest(session.get(RequestRDF.class, requestId).toRequest());
        } catch (Exception ex) {
            log.error("Failed to create a request : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                ("Failed to create a request : " + ex.getMessage());
        }
    }


    /**
     * This method returns the list of requests.
     *
     * @return The list of requests.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public List<String> listRequests() throws RequestBrokerException {
        try {
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            return daemon.listRequests();
        } catch (Exception ex) {
            log.error("Failed to list the requests : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                ("Failed to list the request : " + ex.getMessage());
        }
    }


    /**
     * This method retrieves the request identified by the id as XML.
     *
     * @param id The id that identifies the request.
     * @return The string containing the XML for the request.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public String getRequestAsXML(String id) throws RequestBrokerException {
        try {
            Session session = XMLSemanticUtil.getSession();
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            session.persist(new RequestRDF(daemon.getRequest(id)));
            return session.dumpXML();
        } catch (Exception ex) {
            log.error("Failed to get a request : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                ("Failed to get a request : " + ex.getMessage());
        }
    }


    /**
     * This method returns the request information.
     *
     * @param id The of the request.
     * @return The request information.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public String getRequestInfoAsXML(String id) throws RequestBrokerException {
        try {
            Session session = XMLSemanticUtil.getSession();
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            session.persist(new RequestInfoRDF(daemon.getRequestInfo(id)));
            return session.dumpXML();
        } catch (Exception ex) {
            log.error("Failed to get the request information : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                ("Failed to get the request information: " + ex.getMessage());
        }
    }


    /**
     * This method removes the request identified by the id.
     *
     * @param id The id of the request to remove.
     * @throws com.rift.coad.request.RequestBrokerException
     */
    public void removeRequest(String id) throws RequestBrokerException {
        try {
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            daemon.removeRequest(id);
        } catch (Exception ex) {
            log.error("Failed to remove the request information : " + ex.getMessage(),ex);
            throw new RequestBrokerException
                ("Failed to remove the request information: " + ex.getMessage());
        }
    }
    

}

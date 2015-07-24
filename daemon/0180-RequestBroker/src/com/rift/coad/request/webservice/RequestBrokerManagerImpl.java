/*
 * RequestBroker: The the request broker.
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

// package path
package com.rift.coad.request.webservice;

// java imports
import com.rift.coad.change.rdf.RequestRDF;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

// log4j imports
import org.apache.log4j.Logger;

// coadunation imports
import com.rift.coad.rdf.semantic.RDFFormats;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.XMLSemanticUtil;
import com.rift.coad.request.RequestBrokerDaemon;
import com.rift.coad.util.connection.ConnectionManager;

/**
 * The implementation of the request broker manager.
 *
 * @author brett chaldecott
 */
public class RequestBrokerManagerImpl implements RequestBrokerManager {

    // private member variables
    private static Logger log = Logger.getLogger(RequestBrokerManagerImpl.class);
    
    /**
     * The default constructor
     */
    public RequestBrokerManagerImpl() {
    }
    
    /**
     * The implementation of the create request.
     *
     * @param requestId The request id.
     * @param xmlRequest The xml for the request structure.
     * @throws com.rift.coad.request.webservice.RequestBrokerException
     */
    public void createRequestFromXML(String requestId, String xmlRequest)
            throws RequestBrokerException {
        try {
            Session session = XMLSemanticUtil.getSession();
            session.persist(xmlRequest);
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            daemon.createRequest(session.get(RequestRDF.class, requestId).toRequest());
        } catch (Exception ex) {
            log.error("Failed to create a request : " + ex.getMessage(),ex);
            throw throwRequestBrokerException
                ("Failed to create a request : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the list of request.
     * @return
     * @throws com.rift.coad.request.webservice.RequestBrokerException
     */
    public String[] listRequests() throws RequestBrokerException {
        try {
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            return daemon.listRequests().toArray(new String[0]);
        } catch (Exception ex) {
            log.error("Failed to list the requests : " + ex.getMessage(),ex);
            throw throwRequestBrokerException
                ("Failed to list the requests : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the request information.
     *
     * @param id The string containing the request information.
     * @return The string containing the id.
     * @throws com.rift.coad.request.webservice.RequestBrokerException
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
            throw throwRequestBrokerException
                ("Failed to get a request : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns the request information.
     *
     * @param id The string containing the id of the request.
     * @return The string encapsulating the formated xml.
     * @throws com.rift.coad.request.webservice.RequestBrokerException
     */
    public String getRequestInfoAsXML(String id) throws RequestBrokerException {
        try {
            Session session = XMLSemanticUtil.getSession();
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            session.persist(daemon.getRequestInfo(id));
            return session.dumpXML();
        } catch (Exception ex) {
            log.error("Failed to get the request information : " + ex.getMessage(),ex);
            throw throwRequestBrokerException
                ("Failed to get the request information: " + ex.getMessage(),ex);
        }
    }


    /**
     * This method removes the specified request.
     *
     * @param id The id of the request to remove.
     * @throws com.rift.coad.request.webservice.RequestBrokerException
     */
    public void removeRequest(String id) throws RequestBrokerException {
        try {
            RequestBrokerDaemon daemon = (RequestBrokerDaemon)ConnectionManager.getInstance().
                    getConnection(RequestBrokerDaemon.class,
                    "java:comp/env/bean/request/RequestBrokerDaemon");
            daemon.removeRequest(id);
        } catch (Exception ex) {
            log.error("Failed to remove the request information : " + ex.getMessage(),ex);
            throw throwRequestBrokerException
                ("Failed to remove the request information: " + ex.getMessage(),ex);
        }
    }


    /**
     * This method wrapps the throwing of the request broker exception.
     *
     * @return The reference to the request broker exception
     * @param message The message to put in the exception
     * @param ex The exception stack.
     */
    private RequestBrokerException throwRequestBrokerException(
            String message, Throwable ex) {
        RequestBrokerException exception = new RequestBrokerException();
        exception.message = message;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream outStream = new PrintStream(output);
        ex.printStackTrace(outStream);
        outStream.flush();
        exception.cause = output.toString();
        return exception;
    }
}

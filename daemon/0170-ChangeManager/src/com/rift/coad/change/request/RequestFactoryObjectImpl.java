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
 * RequestFactoryObjectImpl.java
 */


package com.rift.coad.change.request;


// the request information
import com.rift.coad.audit.client.rdf.AuditLogger;
import com.rift.coad.change.ChangeManagerDaemonImpl;
import com.rift.coad.change.rdf.objmapping.change.Request;

// import log4j
import com.rift.coad.change.rdf.objmapping.change.RequestConstants;
import com.rift.coad.change.rdf.objmapping.change.RequestEvent;
import com.rift.coad.change.rdf.objmapping.change.SystemActionTypes;
import com.rift.coad.change.request.action.ActionHandler;
import com.rift.coad.change.request.action.ActionHandlerAsync;
import com.rift.coad.change.request.rdf.MasterRequest;
import com.rift.coad.daemon.messageservice.rpc.RPCMessageClient;
import com.rift.coad.lib.Resource;
import com.rift.coad.lib.ResourceIndex;
import com.rift.coad.rdf.semantic.Session;
import com.rift.coad.rdf.semantic.coadunation.SemanticUtil;
import org.apache.log4j.Logger;

// change log imports
import com.rift.coad.util.change.Change;
import com.rift.coad.util.change.ChangeException;
import com.rift.coad.util.change.ChangeLog;


/**
 * The factory object implementation.
 *
 * @author brett chaldecott
 */
public class RequestFactoryObjectImpl implements RequestFactoryObject,ResourceIndex,Resource {

    /**
     * The enum defining the type of action to perform on the object.
     */
    public enum TYPE {
        ADD,
        UPDATE,
        DELETE
    };


    /**
     * This constructor reflects the request change.
     */
    public static class RequestChangeEntry implements Change {

        private TYPE changeType;
        private MasterRequest request;

        
        public RequestChangeEntry(TYPE changeType, MasterRequest request)
                throws RequestException {
            try {
                this.changeType = changeType;
                this.request = (MasterRequest)request.clone();
            } catch (Exception ex) {
                log.error("Failed to instanciate the change entry : " +
                        ex.getMessage(),ex);
                throw new RequestException
                        ("Failed to instanciate the change entry : " +
                        ex.getMessage(),ex);

            }
        }


        /**
         * This constructor is responsible for applying the changes request object.
         *
         * @throws com.rift.coad.util.change.ChangeException
         */
        public void applyChanges() throws ChangeException {
            try {
                Session session = SemanticUtil.getInstance(ChangeManagerDaemonImpl.class).getSession();
                if ((changeType == TYPE.ADD)|| (changeType == TYPE.UPDATE)) {
                    session.persist(request);
                } else {
                    session.remove(request);
                }
            } catch (Exception ex) {
                log.error("Failed to apply the changes : " + ex.getMessage(),ex);
            }
        }

    }

    // class singletons
    private static Logger log = Logger.getLogger(RequestFactoryObjectImpl.class);
    private static AuditLogger auditLog = AuditLogger.getLogger(
            RequestFactoryObjectImpl.class);

    // private member variable
    private MasterRequest request;


    /**
     * The default constructor of the request factory object.
     *
     * @param request The request information.
     *
     */
    public RequestFactoryObjectImpl(Request request) throws RequestException {
        this.request = new MasterRequest(request);
        try {
            if (request.getAction().equals(SystemActionTypes.BATCH)) {
                for (Request child : request.getChildren()) {
                    initRequest(child);
                }
            } else {
                initRequest(request);
            }
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.ADD,this.request));

        } catch (RequestException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to add the change log entry : " + ex.getMessage(),ex);
            throw new RequestException
                    ("Failed to add the change log entry : " + ex.getMessage(),ex);
        }
    }


    /**
     * The constructor of the request factory object that sets up the internal value.
     *
     * @param request The reference to the master request
     */
    public RequestFactoryObjectImpl(MasterRequest request) {
        this.request = request;
    }



    /**
     * This method is responsible for returning the id of the request information.
     *
     * @return The string containg the id of the request.
     * @throws com.rift.coad.change.request.RequestException
     */
    public String getId() throws RequestException {
        return request.getId();
    }


    /**
     * This method returns the information about the request.
     * 
     * @return The information about the request.
     * @throws com.rift.coad.change.request.RequestException
     */
    public Request getInfo() throws RequestException {
        return request.getRequest();
    }


    /**
     * This method updates the request information.
     *
     * @param request The request information.
     * @throws com.rift.coad.change.request.RequestException
     */
    public void update(Request request) throws RequestException {
        this.request.setRequest(request);
        try {
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.UPDATE,this.request));
            auditLog.create("Request [%s] updated", this.getId()).
                    addData(this.request.getRequest()).complete();
        } catch (RequestException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to update the request entry : " + ex.getMessage(),ex);
            throw new RequestException
                    ("Failed to update the request entry : " + ex.getMessage(),ex);
        }
    }

    
    /**
     * This method is responsible for adding the remove change log entry.
     * 
     * @throws com.rift.coad.change.request.RequestException
     */
    public void remove() throws RequestException {
        try {
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.DELETE,request));
        } catch (RequestException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to delete the request entry : " + ex.getMessage(),ex);
            throw new RequestException
                    ("Failed to delete the request entry : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method sets the status information.
     *
     * @param requestId Thid id of the request to set the status on.
     * @param status The new status value.
     * @param message The event message associated with the status change.
     * @throws com.rift.coad.change.request.RequestException
     */
    public String setStatus(String requestId, String status, String message)
            throws RequestException {
        try {
            if (!setStatus(this.request.getRequest(),requestId,status,message)) {
                throw new RequestException("The request [" + requestId + "] was not found");
            }
            boolean complete = false;
            if (this.request.getRequest().getAction().equals(SystemActionTypes.BATCH)) {
                if (this.request.getRequest().getStatus().equals(RequestConstants.COMPLETE)) {
                    complete = true;
                } else {
                    boolean allComplete = true;
                    for (Request child : this.request.getRequest().getChildren()) {
                        if (!isComplete(child)) {
                            allComplete = false;
                            break;
                        }
                    }
                    if (allComplete) {
                        initRequest(this.request.getRequest());
                    }
                }
            } else {
                complete = isComplete(this.request.getRequest());
            }
            ChangeLog.getInstance().addChange(new RequestChangeEntry(TYPE.UPDATE,this.request));
            auditLog.create("Request [%s] sub request [%s] status [%s] updated",
                    this.getId(),requestId,status).complete();
            if (complete) {
                return RequestConstants.COMPLETE;
            }
            return RequestConstants.RUNNING;
        } catch (RequestException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Failed to set the status of a request : " + ex.getMessage(),ex);
            throw new RequestException
                    ("Failed to set the status of a request : " + ex.getMessage(),ex);
        }
    }
    
    
    /**
     * This method is called to set the status of a request item.
     *
     * @param request The request the status must be set on.
     * @param requestId The request id.
     * @param status The new status string.
     * @param message The message.
     * @throws RequestException
     */
    private boolean setStatus(Request request,
            String requestId, String status, String message) throws RequestException {
        if (request.getId().equals(requestId)) {
            request.addEvent(new RequestEvent(status,message));
            request.setStatus(status);
            if (request.getChildren() != null) {
                for (Request child : request.getChildren()) {
                    initRequest(child);
                }
            }
            return true;
        }
        if (request.getChildren() != null) {
            for (Request subRequest : request.getChildren()) {
                if (setStatus(subRequest,requestId,status,message)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method returns the primary key
     *
     * @return The primary key
     */
    public Object getPrimaryKey() {
        return request.getId();
    }

    
    /**
     * This method returns the resource name for this object.
     *
     * @return The string containing the resource name for this object.
     */
    public String getResourceName() {
        return this.getClass().getName();
    }


    /**
     * This method is called to release any resources that might be used up by
     * this object when it is bound to the interface
     */
    public void releaseResource() {
        // do nothing
    }


    /**
     * This method is called to init sub requests.
     *
     * @param The request to invoke.
     */
    private void initRequest(Request request) throws RequestException {
        try {
            if (request.getStatus().equals(RequestConstants.UNPROCESSED)) {
                ActionHandlerAsync handler = (ActionHandlerAsync)RPCMessageClient.
                        createOneWay("change/request/RequestFactoryDaemon", ActionHandler.class,
                    ActionHandlerAsync.class, "change/request/action/ActionHandler");
                Request duplicate = (Request)request.clone();
                duplicate.setChildren(null);
                handler.invokeAction(this.request.getId(), duplicate);
                request.setStatus(RequestConstants.RUNNING);
            }
        } catch (Exception ex) {
            log.error("Failed to init the requests : " + ex.getMessage(),ex);
            throw new RequestException
                    ("Failed to init the requests : " + ex.getMessage(),ex);
        }
    }


    /**
     * This method returns true if all sub requests are now complete.
     *
     * @return TRUE if the sub requests are all complete.
     */
    private boolean isComplete(Request request) {
        if (!request.getStatus().equals(RequestConstants.COMPLETE)) {
            return false;
        }
        if (request.getChildren() != null) {
            for (Request child : request.getChildren()) {
                if (!isComplete(child)) {
                    return false;
                }
            }
        }
        return true;
    }
}

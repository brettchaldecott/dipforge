/*
 * ChangeControlManager: The manager for the change events.
 * Copyright (C) 2013  Rift IT Contracting
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
 * SleepingActionInfo.java
 */
package com.rift.coad.change.request.action.sleep;

// java imports
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// dipforge imports
import com.rift.coad.lib.security.SessionManager;
import com.rift.coad.lib.security.ThreadPermissionSession;


/**
 * The sleeping action information object.
 * 
 * @author brett chaldecott
 */
public class SleepActionInfo implements Serializable {
    
    // private member variables
    private String actionInstanceId;
    private long period;
    private Date start;
    private String creator;
    private String sessionId;
    private List principals;

    
    /**
     * This constructor sets up all internal parameters.
     * 
     * @param actionInstanceId The action instance.
     * @param period The period.
     * @param start The start.
     * @param creator The creator.
     * @param sessionId The session id.
     * @param principals The principals.
     */
    public SleepActionInfo(String actionInstanceId, long period, Date start, 
            String creator, String sessionId, List principals) {
        this.actionInstanceId = actionInstanceId;
        this.period = period;
        this.start = start;
        this.creator = creator;
        this.sessionId = sessionId;
        this.principals = principals;
    }

    
    /**
     * This constructor sets up the default values.
     * 
     * @param actionInstanceId The id of this action.
     * @param period The period to sleep for.
     */
    public SleepActionInfo(String actionInstanceId, long period) throws SleepManagerException {
        try {
            this.actionInstanceId = actionInstanceId;
            this.period = period;
            this.start = new Date();
            
            ThreadPermissionSession session = SessionManager.getInstance().
                        getSession();
            creator = session.getUser().getName();
            sessionId = session.getUser().getSessionId();
            principals = new ArrayList();
            principals.addAll(session.getPrincipals());
        } catch (Exception ex) {
            throw new SleepManagerException(
                    "Failed to create the new sleep action :" + ex.getMessage(),
                    ex);
        }
    }

    
    /**
     * The getter for the action instance id.
     * 
     * @return Action instance id.
     */
    public String getActionInstanceId() {
        return actionInstanceId;
    }

    /**
     * The setter for the action instance id.
     * 
     * @param actionInstanceId The id of the action instance.
     */
    public void setActionInstanceId(String actionInstanceId) {
        this.actionInstanceId = actionInstanceId;
    }

    /**
     * The getter for the sleep period.
     * 
     * @return The period that the sleep must run for.
     */
    public long getPeriod() {
        return period;
    }

    /**
     * The setter for the sleep period.
     * @param period The period that the sleep must run for.
     */
    public void setPeriod(long period) {
        this.period = period;
    }

    /**
     * The getter for the sleep start time.
     * 
     * @return The time the sleep must start.
     */
    public Date getStart() {
        return start;
    }

    
    /**
     * The setter for the sleep start time.
     * 
     * @param start The sleep start time.
     */
    public void setStart(Date start) {
        this.start = start;
    }

    
    /**
     * The getter for the creator name.
     * 
     * @return The string containing the creator name.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * The setter for the creator name.
     * 
     * @param creator The new creator name.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    
    /**
     * The getter for the session id.
     * 
     * @return The string containing the session id.
     */
    public String getSessionId() {
        return sessionId;
    }

    
    /**
     * The setter for the session id.
     * 
     * @param sessionId The string containing the session id.
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    /**
     * The getter for the list of principals.
     * 
     * @return The list of principals.
     */
    public List getPrincipals() {
        return principals;
    }

    
    /**
     * The setter for the list of principals.
     * 
     * @param principals The list of principals.
     */
    public void setPrincipals(List principals) {
        this.principals = principals;
    }

    
    /**
     * The overriden hash method.
     * 
     * @return The integer containing the hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.actionInstanceId != null ? 
                this.actionInstanceId.hashCode() : 0);
        hash = 83 * hash + (int) (this.period ^ (this.period >>> 32));
        hash = 83 * hash + (this.start != null ? this.start.hashCode() : 0);
        return hash;
    }

    
    /**
     * The overriden equals operator.
     * 
     * @param obj The object to perform the equals operation on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SleepActionInfo other = (SleepActionInfo) obj;
        if ((this.actionInstanceId == null) ? (other.actionInstanceId != null) : 
                !this.actionInstanceId.equals(other.actionInstanceId)) {
            return false;
        }
        if (this.period != other.period) {
            return false;
        }
        if (this.start != other.start && (this.start == null || 
                !this.start.equals(other.start))) {
            return false;
        }
        return true;
    }

    
    /**
     * The to string operator.
     * 
     * @return The to string operator.
     */
    @Override
    public String toString() {
        return "SleepingActionInfo{" + "actionInstanceId=" + actionInstanceId + 
                ", period=" + period + ", start=" + start + ", creator=" + 
                creator + ", sessionId=" + sessionId + ", principals=" + 
                principals + '}';
    }
    
    
    
}

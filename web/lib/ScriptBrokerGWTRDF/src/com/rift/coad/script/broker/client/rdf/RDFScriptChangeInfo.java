/*
 * ScriptBrokerGWTRDF: The rdf information for the script broker
 * Copyright (C) 2009  2015 Burntjam
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
 * RDFScriptChangeInfo.java
 */

// package path
package com.rift.coad.script.broker.client.rdf;

// java imports
import java.util.Date;

// coaduantion imports
import com.rift.coad.rdf.objmapping.client.base.DataType;
import com.rift.coad.revision.client.rdf.RDFRevisionInfo;

/**
 * This object stores the change information about a script.
 *
 * @author brett chaldecott
 */
public class RDFScriptChangeInfo extends DataType {
    // private member variables
    private String id;
    private RDFScriptInfo script;
    private String change;
    private String messageId;
    private String status;
    private RDFRevisionInfo revision;
    private Date created;


    /**
     * The default constructor for the script change information object.
     */
    public RDFScriptChangeInfo() {
    }

    
    /**
     * This constructor sets up the script information.
     * 
     * @param script The script information.
     * @param change The type of change.
     */
    public RDFScriptChangeInfo(RDFScriptInfo script, String change) {
        this.script = script;
        this.change = change;
        this.created = new Date();
    }


    /**
     * This constructor populates all the values.
     *
     * @param id The id of the object.
     * @param script The script.
     * @param change The change.
     * @param created The date the change was create.
     */
    public RDFScriptChangeInfo(String id, RDFScriptInfo script, String change, Date created) {
        this.id = id;
        this.script = script;
        this.change = change;
        this.created = created;
    }


    /**
     * This string contains the change that must be applied.
     *
     * @return The string containing the changes to apply.
     */
    public String getChange() {
        return change;
    }


    /**
     * This method sets the change information.
     *
     * @param change This method sets the change description.
     */
    public void setChange(String change) {
        this.change = change;
    }


    /**
     * This method is called to retrieve the created date.
     *
     * @return The date the entry was created.
     */
    public Date getCreated() {
        return created;
    }


    /**
     * This method sets the created date.
     *
     * @param created The date the entry was created.
     */
    public void setCreated(Date created) {
        this.created = created;
    }


    /**
     * This method gets the id of the script change.
     *
     * @return The string containing the id of the change.
     */
    public String getId() {
        return id;
    }


    /**
     * The setter for the script change method.
     *
     * @param id The setter for the id.
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the script information object.
     *
     * @return The script information object.
     */
    public RDFScriptInfo getScript() {
        return script;
    }


    /**
     * The setter for the script information object.
     *
     * @param script The reference to the script object.
     */
    public void setScript(RDFScriptInfo script) {
        this.script = script;
    }

    
    /**
     * This method gets the message id.
     *
     * @return The string containing the message id.
     */
    public String getMessageId() {
        return messageId;
    }


    /**
     * This method sets the message id.
     *
     * @param messageId The string containing the message id.
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }



    /**
     * This method returns the status of the script change.
     *
     * @return The string containing the status
     */
    public String getStatus() {
        return status;
    }

    
    /**
     * This method sets the change status
     *
     * @param status The string containing the change status.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    

    /**
     * This method gets the revision information.
     *
     * @return The revisionion information.
     */
    public RDFRevisionInfo getRevision() {
        return revision;
    }


    /**
     * This method sets the revision information.
     *
     * @param revision The reference to the revision object.
     */
    public void setRevision(RDFRevisionInfo revision) {
        this.revision = revision;
    }

    
    /**
     * This method checks to see if the objects are equal.
     * 
     * @param obj The object to perform the equals on.
     * @return TRUE if equal, FALSE if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RDFScriptChangeInfo other = (RDFScriptChangeInfo) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for this object.
     *
     * @return The hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 31 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    /**
     * This method generates a string representation this object.
     *
     * @return The string representation of this object.
     */
    @Override
    public String toString() {
        return "[ID: " + id + "\n" +
                "Script: {" + script.toString() + "}\n" +
                "Change: " + change + "\n" +
                "Created: " + created.toString() + "]";
    }



    
}

/*
 * ScriptBrokerRDF: The rdf information for the script broker
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
package com.rift.coad.script.broker.rdf;

// java imports
import com.rift.coad.lib.common.RandomGuid;
import java.util.Date;

// the semantic imports
import thewebsemantic.Namespace;
import thewebsemantic.RdfType;
import thewebsemantic.RdfProperty;

// coaduantion imports
import com.rift.coad.rdf.objmapping.base.DataType;
import com.rift.coad.revision.rdf.RDFRevisionInfo;
import thewebsemantic.Identifier;

/**
 * This object stores the change information about a script.
 *
 * @author brett chaldecott
 */
@Namespace("http://www.coadunation.net/schema/rdf/1.0/script#")
@RdfType("ScriptChangeInfo")
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
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
    }

    
    /**
     * This constructor sets up the script information.
     * 
     * @param script The script information.
     * @param change The type of change.
     */
    public RDFScriptChangeInfo(RDFScriptInfo script, String change) {
        try {
            id = RandomGuid.getInstance().getGuid();
        } catch (Exception ex) {
            // ignore
        }
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
     * This method returns the id of the script change information.
     * 
     * @return The object containing the change set informatin.
     */
    @Override
    public String getObjId() {
        return id;
    }


    /**
     * This string contains the change that must be applied.
     *
     * @return The string containing the changes to apply.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoChange")
    public String getChange() {
        return change;
    }


    /**
     * This method sets the change information.
     *
     * @param change This method sets the change description.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoChange")
    public void setChange(String change) {
        this.change = change;
    }


    /**
     * This method is called to retrieve the created date.
     *
     * @return The date the entry was created.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoCreated")
    public Date getCreated() {
        return created;
    }


    /**
     * This method sets the created date.
     *
     * @param created The date the entry was created.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoCreated")
    public void setCreated(Date created) {
        this.created = created;
    }


    /**
     * This method gets the id of the script change.
     *
     * @return The string containing the id of the change.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoId")
    @Identifier()
    public String getId() {
        return id;
    }


    /**
     * The setter for the script change method.
     *
     * @param id The setter for the id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoId")
    public void setId(String id) {
        this.id = id;
    }


    /**
     * This method returns the script information object.
     *
     * @return The script information object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoScriptInfo")
    public RDFScriptInfo getScript() {
        return script;
    }


    /**
     * The setter for the script information object.
     *
     * @param script The reference to the script object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeInfoScriptInfo")
    public void setScript(RDFScriptInfo script) {
        this.script = script;
    }

    
    /**
     * This method gets the message id.
     *
     * @return The string containing the message id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptMessageId")
    public String getMessageId() {
        return messageId;
    }


    /**
     * This method sets the message id.
     *
     * @param messageId The string containing the message id.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptMessageId")
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }



    /**
     * This method returns the status of the script change.
     *
     * @return The string containing the status
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeStatus")
    public String getStatus() {
        return status;
    }

    
    /**
     * This method sets the change status
     *
     * @param status The string containing the change status.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptChangeStatus")
    public void setStatus(String status) {
        this.status = status;
    }
    

    /**
     * This method gets the revision information.
     *
     * @return The revisionion information.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptRevisionInfo")
    public RDFRevisionInfo getRevision() {
        return revision;
    }


    /**
     * This method sets the revision information.
     *
     * @param revision The reference to the revision object.
     */
    @RdfProperty("http://www.coadunation.net/schema/rdf/1.0/script#ScriptRevisionInfo")
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

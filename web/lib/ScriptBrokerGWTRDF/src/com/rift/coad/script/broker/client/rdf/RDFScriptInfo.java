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
 * RDFScriptInfo.java
 */

package com.rift.coad.script.broker.client.rdf;

// java imports
import java.util.Date;

// the data type
import com.rift.coad.rdf.objmapping.client.base.DataType;


/**
 * This object contains the script information.
 *
 * @author brett chaldecott
 */
public class RDFScriptInfo extends DataType {

    // private member variables
    private String scope;
    private String fileName;
    private String type;
    private Date lastModified;
    private String version;

    /**
     * The default constructor for the rdf script
     */
    public RDFScriptInfo() {

    }


    /**
     * The constructor that sets up the rdf script information.
     *
     * @param scope The scope the script is in.
     * @param fileName The file name.
     * @param lastModified The last modified time.
     */
    public RDFScriptInfo(String scope, String fileName, Date lastModified) {
        this.scope = scope;
        this.fileName = fileName;
        type = "unknown";
        if ((fileName != null) && (fileName.contains("."))) {
            type = fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        this.lastModified = lastModified;
    }

    
    /**
     * This constructor sets up all the internal member variables
     *
     * @param scope The scope the file is in.
     * @param fileName The name of the script.
     * @param type The type of the file.
     * @param lastModified The last modified date of the file.
     */
    public RDFScriptInfo(String scope, String fileName, String type, Date lastModified) {
        this.scope = scope;
        this.fileName = fileName;
        this.type = type;
        this.lastModified = lastModified;
    }

    
    /**
     * This constructor sets all the information on a script.
     * 
     * @param scope This method sets the scope of the file.
     * @param fileName The name of the file.
     * @param type The type of the file.
     * @param lastModified The last modified date.
     * @param version The version of the script.
     */
    public RDFScriptInfo(String scope, String fileName, String type, Date lastModified, String version) {
        this.scope = scope;
        this.fileName = fileName;
        this.type = type;
        this.lastModified = lastModified;
        this.version = version;
    }




    /**
     * This method returns the name of the file.
     *
     * @return The name of the file to retrieve.
     */
    public String getFileName() {
        return fileName;
    }


    /**
     * This method sets the file name.
     *
     * @param fileName The string containing the file name.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    /**
     * This method returns the file path for the script object.
     *
     * @return The string containing the unique file path for this object.
     */
    public String getScope() {
        return scope;
    }

    
    /**
     * This method sets the scope of the object
     * 
     * @param scope The string containing the file path for this script.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    
    /**
     * This method returns the type information.
     * 
     * @return The string containing the type information.
     */
    public String getType() {
        return type;
    }


    /**
     * This method sets the type information.
     *
     * @param type The type information
     */
    public void setType(String type) {
        this.type = type;
    }





    /**
     * This method returns the last modified date of the rdf script.
     *
     * @return The object containing the last modified date of this object.
     */
    public Date getLastModified() {
        return lastModified;
    }


    /**
     * The last modified date of this object.
     *
     * @param lastModified The last modified date of this object.
     */
    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }


    /**
     * This method returns the version information for this script.
     *
     * @return The string containing the version information for this script.
     */
    public String getVersion() {
        return version;
    }


    /**
     * This method sets the version information.
     *
     * @param version The version information for this script.
     */
    public void setVersion(String version) {
        this.version = version;
    }




    /**
     * This method performs the equals comparison on this object.
     * @param obj The object to perform the comparison on.
     * @return The
     */
    @Override
    public boolean equals(Object obj) {
        // make the parent call
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RDFScriptInfo other = (RDFScriptInfo) obj;
        if ((this.scope == null) ? (other.scope != null) : !this.scope.equals(other.scope)) {
            return false;
        }
        if ((this.fileName == null) ? (other.fileName != null) : !this.fileName.equals(other.fileName)) {
            return false;
        }
        return true;
    }


    /**
     * This method returns the hash code for the rdf script.
     *
     * @return The integer containing the hash code for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.scope != null ? this.scope.hashCode() : 0);
        hash = 17 * hash + (this.fileName != null ? this.fileName.hashCode() : 0);
        return hash;
    }
    

    /**
     * This method returns the file path.
     *
     * @return The string containing the file path
     */
    @Override
    public String toString() {
        return "[Name : " + fileName + "\n" +
                "Scope : " + scope + "\n" +
                "Type : " + type + "\n" +
                "Modified : " + (lastModified == null ? "" : lastModified.toString()) + "\n" +
                "Version : " + version + "]\n";

    }

    
}
